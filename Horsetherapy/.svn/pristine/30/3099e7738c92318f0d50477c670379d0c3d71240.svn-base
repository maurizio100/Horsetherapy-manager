package sepm.ss13.e0828.domain;

import java.util.LinkedList;
import java.util.List;

public class Invoice {

	private int invoiceId = -1;
	private long invoiceDate = 0l;
	private String invoiceClientfname ="";
	private String invoiceClientsname = "";
	private String invoiceAddress ="";
	private String invoicePostcode ="";
	private String invoicePlace ="";
	private List<TherapyUnit> therapies = null;
	private float invoiceSum = 0.0f;
	
	
	public int getInvoiceID() {
		return invoiceId;
	}
	
	public void setInvoiceID(int invoiceId) {
		this.invoiceId = invoiceId;
	}
	public long getInvoiceDate() {
		return invoiceDate;
	}
	public void setInvoiceDate(long invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	public String getInvoiceAddress() {
		return invoiceAddress;
	}
	public void setInvoiceAddress(String invoiceAddress) {
		this.invoiceAddress = invoiceAddress;
	}
	public String getInvoicePostcode() {
		return invoicePostcode;
	}
	public void setInvoicePostcode(String invoicePostcode) {
		this.invoicePostcode = invoicePostcode;
	}
	public String getInvoicePlace() {
		return invoicePlace;
	}
	public void setInvoicePlace(String invoicePlace) {
		this.invoicePlace = invoicePlace;
	}
	
	public void setInvoiceClientfirstname(String clientfname){
		this.invoiceClientfname = clientfname;
	}
	public String getInvoiceClientfirstname() {
		return this.invoiceClientfname;
	}
	
	public void setInvoiceClientsurname(String clientsname){
		this.invoiceClientsname = clientsname;
	}
	
	public String getInvoiceClientsurname() {
		return this.invoiceClientsname;
	}
	
	public void setTherapyUnit(TherapyUnit unit){
		if( therapies == null ){
			therapies = new LinkedList<TherapyUnit>();
		}
		
		therapies.add(unit);
	}
	
	public List<TherapyUnit> getTherapyunits(){
		return therapies;
	}

	public float getInvoiceSum() {
		return invoiceSum;
	}

	public void setInvoiceSum(float invoiceSum) {
		this.invoiceSum = invoiceSum;
	}

	
}
