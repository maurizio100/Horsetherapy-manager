package sepm.ss13.e0828.dao.exceptions;

/** This Exception is used when a given horse is not existent in the persistence unit
 * 
 * @author Maurizio Rinder u0828852
 *
 */

public class HorseNotExistentException extends Exception {

	private static final String NONEXISTENT =  "Error: Horse that was supposed to updated or deleted does not exist!";

	public HorseNotExistentException(){
		super(NONEXISTENT);
	}
	
}
