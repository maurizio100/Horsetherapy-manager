package sepm.ss13.e0828.service.exception;

public class LessValuesException extends Exception {

	private static final String ERRMSG = "Not enough values given for processing the operation!";

	public LessValuesException(){
		super(ERRMSG);
	}
	
}
