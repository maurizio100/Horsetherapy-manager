package sepm.ss13.e0828.dao.helper;

/** This exception is used when the Querygenerator doesn't get
 * fields for generating an update statement
 * an Object with values that have to be stored
 * @author Maurizio Rinder u0828852
 *
 */

public class EmptyFieldsException extends Exception {

	private static final String NONEXISTENT =  "Error: No update fields given!";

	public EmptyFieldsException(){
		super(NONEXISTENT);
	}

	
}
