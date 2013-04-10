package sepm.ss13.e0828.service.exception;

public class IncorrectTherapyUnitsException extends Exception {

	private static final String ERRMSG = "Some Therapyunits contain incorrect values";

	public IncorrectTherapyUnitsException(){
		super(ERRMSG);
	}
	
}
