package sepm.ss13.e0828.service.exception;

public class NotSameDateException extends Exception {

	private static final String ERRMSG = "Invoice date and today's date are not the same!";

	public NotSameDateException(){
		super(ERRMSG);
	}
	
}
