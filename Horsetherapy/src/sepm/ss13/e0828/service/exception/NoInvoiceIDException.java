package sepm.ss13.e0828.service.exception;

public class NoInvoiceIDException extends Exception {

	private static final String ERRMSG = "No Identification of Invoice was given!";

	public NoInvoiceIDException(){
		super(ERRMSG);
	}
	
}
