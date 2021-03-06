package sepm.ss13.e0828.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import sepm.ss13.e0828.dao.exceptions.HorseNotExistentException;
import sepm.ss13.e0828.dao.exceptions.NoClassGivenException;
import sepm.ss13.e0828.dao.exceptions.PersistenceException;
import sepm.ss13.e0828.dao.exceptions.UnacceptedValueException;
import sepm.ss13.e0828.dao.helper.DBQueryGenerationHelper;
import sepm.ss13.e0828.dao.helper.EmptyFieldsException;
import sepm.ss13.e0828.domain.Horse;
import sepm.ss13.e0828.domain.HorseQueryData;
import sepm.ss13.e0828.domain.Horserace;
import sepm.ss13.e0828.domain.Therapytype;

/**
 * This class implements the HorseDAO interface and provides
 * manipulation functionalities for the Horse class that take effect
 * on the database
 * @author Maurizio Rinder u082882
 */

public class DBHorseDAO implements HorseDAO{

	private Logger logger = Logger.getLogger(DBHorseDAO.class);

	/* ----- Horse entity information --------*/
	private static final String[] DBHORSESELECTFIELDS = {"horse_id","horse_name","horse_priceperhour", "horse_therapytype","horse_photo","horse_race","horse_deleted"}; 
	private static final String[] DBHORSEUPDATEFIELDS = {"horse_id","horse_name","horse_priceperhour", "horse_therapytype","horse_photo","horse_race"}; 

	private static final String HORSEENTITY = "horse";
	private static final String HORSETHERAPYJOIN = "horse Left Join therapy on horse_id = horse";
	
	/* ----- constants ------*/
	private static final String STMTEXECERROR = "Couldn't create or execute Statement!";

	private static final String STMTCREATE = "Insert into horse(horse_id, horse_name, horse_photo, horse_priceperhour, horse_therapytype, horse_race, horse_deleted) " +
			"Values(NULL,?,?,?,?,?,0);";
	private static final String STMTGETALLHORSES = "Select horse_id, horse_name, horse_photo, horse_priceperhour, horse_therapytype, horse_race, " +
			"horse_deleted, sum(therapy_hour) as hours " +
			"from " + HORSETHERAPYJOIN + " where horse_deleted = 0 " +
			"group by horse_id, horse_name, horse_photo, horse_priceperhour, horse_therapytype, horse_deleted";	
	private static final String SELECTHORSEID = "Select horse_id from horse where horse_id = ? and horse_deleted = 0;";
	
	private static final String SELECTBYTHERAPY = "Select horse_id, horse_name, horse_photo, horse_priceperhour, horse_race, sum(therapy_hour) as hours " +	 
			"from " + HORSETHERAPYJOIN +
			" where horse_therapytype = ? and horse_deleted = 0 " +
			"group by horse_id, horse_name, horse_photo, horse_priceperhour, horse_therapytype, horse_race, horse_deleted order by hours asc;";
	
	private static final String STMTDELETE = "Update horse set horse_deleted = 1 where horse_id = ?;";


	/** The general preparedstatement object that will be used by every method. */
	private PreparedStatement pstmt = null;
	/** The general resultset object that mainly select-methods will use*/
	ResultSet resultSet = null;
	/** the connection object that is gathered from the Connector-class */
	private Connection conn = null;
	
	@Override
	public void createHorse(HorseQueryData newHorse) throws PersistenceException, NoClassGivenException, UnacceptedValueException{

		if( newHorse == null ) throw new NoClassGivenException();
		if( newHorse.getHorsePrice() <= 0 ) throw new UnacceptedValueException();

		try{
			conn = Connector.getConnection();
			String horseName = newHorse.getHorseName();
			String horsePicture = newHorse.getHorsePhoto();
			float horsePrice = newHorse.getHorsePrice();
			String horseTherapytype = newHorse.getTherapytype();
			String horseRace = newHorse.getHorseRace();
			
			pstmt = conn.prepareStatement(STMTCREATE, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, horseName);
			pstmt.setString(2, horsePicture);
			pstmt.setFloat(3, horsePrice);
			pstmt.setString(4, horseTherapytype);
			pstmt.setString(5, horseRace);

			String values = "Horsename: " + horseName + "\n" +
					"Horsephoto: " + horsePicture + "\n" +
					"Horsename: " + horsePrice + "\n" +
					"Horsename: " + horseTherapytype;

			announceStatementExecution(STMTCREATE, values);
			pstmt.executeUpdate();
		
			ResultSet rs = pstmt.getGeneratedKeys();
			if( rs != null && rs.next() ){
				newHorse.setHorseID(rs.getInt(1));
			}

		} catch (SQLException e) {
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
	public List<Horse> getHorses() throws PersistenceException{

		List<Horse> retList = null; Horse h = null;
		try{
			conn = Connector.getConnection();
			pstmt = conn.prepareStatement(STMTGETALLHORSES);

			announceStatementExecution(STMTGETALLHORSES, null);
			resultSet = pstmt.executeQuery();

			if( resultSet != null ){
				retList = new LinkedList<Horse>();

				while( resultSet.next() ){
					h = new Horse();
					h.setHorseID(resultSet.getInt(1));
					h.setHorseName(resultSet.getString(2));
					h.setHorsePhoto(resultSet.getString(3));
					h.setHorseTherapyprice(resultSet.getFloat(4));
					h.setHorseTherapytype(Therapytype.fromString(resultSet.getString(5)));
					h.setHorseRace(Horserace.fromString(resultSet.getString(6)));
					h.setHorseUsage(resultSet.getFloat(8));
					retList.add(h);
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
	public void deleteHorse(HorseQueryData delHorse) throws PersistenceException, NoClassGivenException, HorseNotExistentException{

		if( delHorse == null ) throw new NoClassGivenException();

		try {		
			conn = Connector.getConnection();
			int horseID = delHorse.getHorseID();

			pstmt = conn.prepareStatement(SELECTHORSEID);
			pstmt.setInt(1, horseID);

			if( !pstmt.executeQuery().next() ){
				closeStreams();
				throw new HorseNotExistentException();
			}

			pstmt = conn.prepareStatement(STMTDELETE);
			pstmt.setInt(1, horseID);

			announceStatementExecution(STMTDELETE, "" + horseID);
			pstmt.executeUpdate();

		} catch (SQLException e) {
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
	public List<Horse> getHorsesBy(HorseQueryData searchValues)
			throws PersistenceException, NoClassGivenException {

		if( searchValues == null ) throw new NoClassGivenException();
		List<Horse> retList = null; Horse h = null;
		
		try{
			conn = Connector.getConnection();
			

			Object arrValues[] = prepareSearchValues(searchValues);			
			
			String entitydefinition = HORSETHERAPYJOIN;
			String query = new DBQueryGenerationHelper(entitydefinition, DBHORSESELECTFIELDS, "sum(therapy_hour)").prepareSelectQuery(arrValues);
			String values = "";
			
			logger.info("Query: " + query);
			pstmt = conn.prepareStatement(query);

			int stmtpos =1;
			for( int i = 0; i < arrValues.length;i++ ){
				if( arrValues[i] != null ){
					values += arrValues[i] + " ";
					if(arrValues[i] instanceof Float ){ 
						pstmt.setString(stmtpos, arrValues[i].toString());
					}else{
						pstmt.setObject(stmtpos, arrValues[i]);
					}
					stmtpos++;
				}
			}

			announceStatementExecution(query, values);
			resultSet = pstmt.executeQuery();

			if( resultSet != null ){
				retList = new LinkedList<Horse>();

				while( resultSet.next() ){
					h = new Horse();
					h.setHorseID(resultSet.getInt(1));
					h.setHorseName(resultSet.getString(2));
					h.setHorseTherapyprice(resultSet.getFloat(3));
					h.setHorseTherapytype(Therapytype.fromString(resultSet.getString(4)));
					h.setHorsePhoto(resultSet.getString(5));
					h.setHorseRace(Horserace.fromString(resultSet.getString(6)));
					h.setHorseUsage(resultSet.getFloat(8));
					retList.add(h);
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
	public void updateHorse(HorseQueryData updateValues) throws PersistenceException, NoClassGivenException, UnacceptedValueException, HorseNotExistentException{

		if( updateValues == null ) throw new NoClassGivenException();
		if( updateValues.getHorsePrice() != null && (updateValues.getHorsePrice() <= 0 || updateValues.getHorseID() < 0)  ) throw new UnacceptedValueException();

		try {
			conn = Connector.getConnection();
			pstmt = conn.prepareStatement(SELECTHORSEID);
			pstmt.setInt(1, updateValues.getHorseID());

			if( !pstmt.executeQuery().next() ){
				closeStreams();
				throw new HorseNotExistentException();
			}
			
			Object arrValues[] = updateValues.getValues();
			DBQueryGenerationHelper helper = new DBQueryGenerationHelper(HORSEENTITY, DBHORSEUPDATEFIELDS, DBQueryGenerationHelper.QueryMode.UPDATE);
			
			String query = helper.prepareUpdateQuery(arrValues, DBHORSESELECTFIELDS[0]);			
			String values = "";
	
			System.out.println("Query: " + query);
			pstmt = conn.prepareStatement(query);

			int stmtpos =1;
			for( int i = 0; i < arrValues.length;i++ ){
				if( arrValues[i] != null ){
					values += arrValues[i] + " ";
					pstmt.setObject(stmtpos, arrValues[i]);
					stmtpos++;
				}
			}
			pstmt.setObject( stmtpos, updateValues.getHorseID() );

			announceStatementExecution(query, values);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			announceStatementCreationProblem(e);
			throw new PersistenceException(STMTEXECERROR);

		} catch (EmptyFieldsException e) {
			logger.error("No update fields were given.");
			throw new PersistenceException(STMTEXECERROR);
		}finally{
			try { 
				closeStreams();
			}catch (SQLException e1) {}
		}		

		announceSuccess();
	}

	@Override
	public List<Horse> getHorsesByUsage(HorseQueryData therapyType)
			throws PersistenceException, NoClassGivenException {

		if( therapyType == null ) throw new NoClassGivenException();

		List<Horse> retList = null; Horse h = null;

		try{
			conn = Connector.getConnection();
			String therapytype = therapyType.getTherapytype();

			pstmt = conn.prepareStatement(SELECTBYTHERAPY);
			pstmt.setString(1, therapytype);

			announceStatementExecution(SELECTBYTHERAPY, therapytype);
			resultSet = pstmt.executeQuery();

			if( resultSet != null ){
				retList = new LinkedList<Horse>();

				while( resultSet.next() ){
					h = new Horse();
					h.setHorseID(resultSet.getInt(1));
					h.setHorseName(resultSet.getString(2));
					h.setHorsePhoto(resultSet.getString(3));
					h.setHorseTherapyprice(resultSet.getFloat(4));
					h.setHorseRace(Horserace.fromString(resultSet.getString(5)));
					h.setHorseUsage(resultSet.getFloat(6));
					h.setHorseTherapytype(Therapytype.fromString(therapytype));
					retList.add(h);
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

	private Object[] prepareSearchValues(HorseQueryData data){
		Object[] valuesFromData = data.getValues();
		Object[] values = new Object[ (valuesFromData.length + 1) ];
		
		for( int i = 0; i < valuesFromData.length; i++ ){
			values[i] = valuesFromData[i];
		}
		values[valuesFromData.length] = new Integer(0); 
		return values;
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
