package sepm.ss13.e0828.dao.exceptions;

/** This Exception is used when a given horse is not existent in the persistence unit
 * 
 * @author Maurizio Rinder u0828852
 *
 */

public class InvoiceNotExistentException extends Exception {

	private static final String NONEXISTENT =  "Error: The reuqested Invoice doesn't exist!";

	public InvoiceNotExistentException(){
		super(NONEXISTENT);
	}
	
}
