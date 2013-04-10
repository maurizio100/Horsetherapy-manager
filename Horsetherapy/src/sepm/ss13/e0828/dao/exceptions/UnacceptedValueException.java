package sepm.ss13.e0828.dao.exceptions;

/** This exception is used when the persistence unit receives
 * data that can't be stored with the given values
 * @author Maurizio Rinder u0828852
 *
 */


public class UnacceptedValueException extends Exception {

private static final String UACCEPTEDVALUEMESSAGE = "Error: This value cant be accepted as it doesn't match the context";
	
	public UnacceptedValueException() {
		super(UACCEPTEDVALUEMESSAGE);
	}
	
}
