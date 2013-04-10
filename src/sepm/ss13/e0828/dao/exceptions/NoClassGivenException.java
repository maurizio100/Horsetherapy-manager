package sepm.ss13.e0828.dao.exceptions;

/** This exception is used when the persistence unit doesn't get
 * an Object with values that have to be stored
 * @author Maurizio Rinder u0828852
 *
 */

public class NoClassGivenException extends Exception {

	private static final String NOCLASSERRMESSAGE = "Error: No Object available for operation.";
	
	public NoClassGivenException(){
		super(NOCLASSERRMESSAGE);
	}
	
}
