package sepm.ss13.e0828.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import sepm.ss13.e0828.dao.exceptions.InvoiceNotExistentException;
import sepm.ss13.e0828.dao.exceptions.NoClassGivenException;
import sepm.ss13.e0828.dao.exceptions.PersistenceException;
import sepm.ss13.e0828.domain.Invoice;
import sepm.ss13.e0828.domain.TherapyUnit;
import sepm.ss13.e0828.domain.Therapytype;

/**
 * This class implements the InvoiceDAO interface and provides
 * manipulation functionalities for the Invoice and Therapyunit class that take effect
 * on the database
 * @author Maurizio Rinder u082882
 */

public class DBInvoiceDAO implements InvoiceDAO{

	private Logger logger = Logger.getLogger(DBInvoiceDAO.class);

	/* ----- constants ------*/
	private static final String STMTEXECERROR = "Couldn't create or execute Statement!";

	private static final String STMTCREATEINVOICE = "Insert into Invoice(invoice_id, invoice_date, invoice_clientfname, invoice_clientsname, invoice_address, invoice_postcode, invoice_place, invoice_sum) " +
			"Values(NULL,?,?,?,?,?,?,?);";
	private static final String STMTCREATETHERAPYUNIT = "Insert into therapy(therapy_id, horse, invoice, therapy_hour, therapy_price, therapy_type) Values(?,?,?,?,?,?);";
	
	private String STMTINVOICELISTING = "Select  invoice.*, therapy_id, horse_name, therapy_hour, therapy_price, therapy_type from horse, therapy, invoice where horse_id = horse and invoice_id = invoice and invoice_id = ?";
	private static final String STMTGETALLINVOICES = "Select * from invoice order by invoice_date desc;";	


	/** The general preparedstatement object that will be used by every method. */
	private PreparedStatement pstmt = null;
	/** The general resultset object that mainly select-methods will use*/
	ResultSet resultSet = null;
	/** the connection object that is gathered from the Connector-class */
	private Connection conn = null;

	
	@Override
	public void createInvoice(Invoice newInvoice) throws PersistenceException, NoClassGivenException{

		if( newInvoice == null ) throw new NoClassGivenException();
		int invoiceid = -1;
		
		try{
			conn = Connector.getConnection();
			conn.setAutoCommit(false);
			
			long invoiceDate = newInvoice.getInvoiceDate();
			String invoiceClientfname = newInvoice.getInvoiceClientfirstname();
			String invoiceClientsname = newInvoice.getInvoiceClientsurname();
			String invoiceAddress = newInvoice.getInvoiceAddress();
			String invoicePostcode = newInvoice.getInvoicePostcode();
			String invoicePlace = newInvoice.getInvoicePlace();
			List<TherapyUnit> units = newInvoice.getTherapyunits();
			float invoiceSum = newInvoice.getInvoiceSum();
			
			pstmt = conn.prepareStatement(STMTCREATEINVOICE, Statement.RETURN_GENERATED_KEYS);
			pstmt.setDate(1, new Date(invoiceDate) );
			pstmt.setString(2, invoiceClientfname);
			pstmt.setString(3, invoiceClientsname);
			pstmt.setString(4, invoiceAddress);
			pstmt.setString(5, invoicePostcode);
			pstmt.setString(6, invoicePlace);
			pstmt.setFloat(7, invoiceSum);
			
			String values = "Invoice Date: " + invoiceDate + "\n" +
					"Client: " + invoiceClientfname + " " + invoiceClientsname + "\n" +
					"Address: " + invoiceAddress + "\n" +
					"Place: " + invoicePostcode + " " + invoicePlace;

			announceStatementExecution(STMTCREATEINVOICE, values);
			pstmt.executeUpdate();
				
			ResultSet rs = pstmt.getGeneratedKeys();
			if( rs != null && rs.next() ){
				invoiceid = rs.getInt(1);
			}
			
			pstmt = conn.prepareStatement( STMTCREATETHERAPYUNIT );
			
			int unit = 0;
			for( TherapyUnit tu : units ){
				unit++;
				
				pstmt.setInt(1, unit);
				pstmt.setInt(2, tu.getTherapyHorseID());
				pstmt.setInt(3, invoiceid);
				pstmt.setFloat(4, tu.getTherapyHours() );
				pstmt.setFloat(5, tu.getTherapyPrice() );
				pstmt.setString(6, tu.getTherapyType().getText() );
				
				announceStatementExecution(STMTCREATEINVOICE, "");
				pstmt.executeUpdate();
		
				
			}
			conn.commit();
			newInvoice.setInvoiceID(invoiceid);
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {	}
			
			announceStatementCreationProblem(e);
			throw new PersistenceException(STMTEXECERROR);

		}finally{
			try { 
				closeStreams();
			}catch (SQLException e1) {}
		}

		announceSuccess();	
	}


	@Override
	public List<Invoice> getInvoices() throws PersistenceException{

		List<Invoice> retList = null; Invoice inv = null;
		try{
			conn = Connector.getConnection();
			pstmt = conn.prepareStatement(STMTGETALLINVOICES);

			announceStatementExecution(STMTGETALLINVOICES, null);
			resultSet = pstmt.executeQuery();

			if( resultSet != null ){
				retList = new LinkedList<Invoice>();

				while( resultSet.next() ){
					inv = new Invoice();
					
					inv.setInvoiceID(resultSet.getInt(1));
					inv.setInvoiceDate(resultSet.getDate(2).getTime());
					inv.setInvoiceClientfirstname(resultSet.getString(3));
					inv.setInvoiceClientsurname(resultSet.getString(4));
					inv.setInvoiceAddress(resultSet.getString(5));
					inv.setInvoicePostcode(resultSet.getString(6));
					inv.setInvoicePlace(resultSet.getString(7));
					inv.setInvoiceSum(resultSet.getFloat(8));
					
					retList.add(inv);
				}
			}
			
			
		}catch ( SQLException e ){
			announceStatementCreationProblem(e);
			throw new PersistenceException(STMTEXECERROR);

		}finally{
			try { 
				closeStreams();
			}catch (SQLException e1) {}
		}

		announceSuccess();
		return retList;
	}

	@Override
	public Invoice getSpecificInvoice(Invoice invoiceID) throws PersistenceException, InvoiceNotExistentException{	
		Invoice retInvoice = null;
		try{
			conn = Connector.getConnection();
			pstmt = conn.prepareStatement(STMTINVOICELISTING);
			pstmt.setInt(1, invoiceID.getInvoiceID());
			
			announceStatementExecution(STMTINVOICELISTING, "" + invoiceID.getInvoiceID());
			resultSet = pstmt.executeQuery();

			if( resultSet != null && resultSet.next() ){
				retInvoice = new Invoice();
				retInvoice.setInvoiceID(resultSet.getInt(1));
				retInvoice.setInvoiceDate(resultSet.getDate(2).getTime());
				retInvoice.setInvoiceClientfirstname(resultSet.getString(3));
				retInvoice.setInvoiceClientsurname(resultSet.getString(4));
				retInvoice.setInvoiceAddress(resultSet.getString(5));
				retInvoice.setInvoicePostcode(resultSet.getString(6));
				retInvoice.setInvoicePlace(resultSet.getString(7));
				retInvoice.setInvoiceSum(resultSet.getFloat(8));
				do{
					TherapyUnit unit = new TherapyUnit();
					unit.setTherapyID(resultSet.getInt(9));
					unit.setTherapyHorseName(resultSet.getString(10));
					unit.setTherapyHours(resultSet.getFloat(11));
					unit.setTherapyPrice(resultSet.getFloat(12));
					unit.setTherapyType(Therapytype.fromString(resultSet.getString(13)));
					retInvoice.setTherapyUnit(unit);
				}while(resultSet.next());
								
			}else{
				throw new InvoiceNotExistentException();
			}
			
		}catch ( SQLException e ){
			announceStatementCreationProblem(e);
			throw new PersistenceException(STMTEXECERROR);

		}finally{
			try { 
				closeStreams();
			}catch (SQLException e1) {}
		}

		announceSuccess();
		return retInvoice;
	}


	/** Closes Streams like PreparedStatements and ResultSets */
	private void closeStreams() throws SQLException{
		if( resultSet != null ){ resultSet.close(); resultSet = null; }				
		if( pstmt != null ){ pstmt.close(); pstmt = null; }
	}
	/** Logs Message and Stacktrace when there was a Problem with Creating or executing a Statement */
	private void announceStatementCreationProblem(Exception e) {
		logger.error("There was a Problem during statement creation!");
		StackTraceElement[] eles = e.getStackTrace();
		for( int i = 0; i < eles.length; i ++ ) logger.error(eles[i]);	
	}

	/** Info-log that gives information about the statement and its values that is executed */
	private void announceStatementExecution(String stmt, String values) {
		logger.info("Executing statement: " + stmt );
		if ( values != null ) logger.info("Used values:\n" + values );
	}
	/** a simple success announcment for the logger */
	private void announceSuccess() {
		logger.info("Execution of statement successful!");
	}

}
