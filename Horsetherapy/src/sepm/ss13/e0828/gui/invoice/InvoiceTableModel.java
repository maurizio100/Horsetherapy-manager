package sepm.ss13.e0828.gui.invoice;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.swing.table.DefaultTableModel;

import sepm.ss13.e0828.domain.Invoice;

public class InvoiceTableModel extends DefaultTableModel{

	private static final long serialVersionUID = 1L;
	private HashMap<Integer, Invoice> invoices = null;
	private DateFormat df = new SimpleDateFormat(InvoiceManagerConfig.DATE_FORMAT);
	private int rowcount = 0;
	private DecimalFormat decFormat = null;
	
	public InvoiceTableModel(){
		super(InvoiceManagerConfig.INVOICELISTHEADERS,0);
		invoices = new HashMap<Integer, Invoice>();

		decFormat = (DecimalFormat) InvoiceManagerConfig.NUMBERFORMAT;
		decFormat.applyPattern(InvoiceManagerConfig.NUMBERPATTERN);		
	}

	public void fillWithNewData(List<Invoice> invoices) {
		this.setRowCount(0);
		if( invoices != null && invoices.size() > 0){
			rowcount = 0; 	
			for( Invoice inv : invoices ){
				addInvoice(inv);
			}
		}
	}

	public void addInvoice(Invoice inv){
		Object[] values = new Object[InvoiceManagerConfig.INVOICELISTCOLS];
		Date date = new Date(inv.getInvoiceDate());
		
		values[0] = inv.getInvoiceID();
		values[1] = df.format(date);
		values[2] = inv.getInvoiceClientfirstname() + " " + inv.getInvoiceClientsurname();
		values[3] = decFormat.format(inv.getInvoiceSum());

		this.addRow(values);
		this.invoices.put(rowcount, inv);		
		rowcount++;

	}

	@Override
	public boolean isCellEditable(int row, int column){
		return false;
	}

	public Invoice getInvoice( int tablerow ){
		if( invoices.containsKey(tablerow) ){
			return invoices.get(tablerow);
		}

		return null;
	}


}
