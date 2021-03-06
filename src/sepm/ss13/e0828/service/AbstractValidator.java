package sepm.ss13.e0828.service;

import java.util.Calendar;

/** This class offers some abstract methods that help checking the
 * different objects that will work with the source's entities
 * @author Maurizio Rinder u0828852
 *
 */
public abstract class AbstractValidator {

	/** checks if a given String is empty or null
	 * 
	 * @param stringValue the String value that is going to be checked
	 * @return true if stringValue is empty, false otherwise
	 */

	protected boolean isStringEmpty(String stringValue){
		return (stringValue == null || stringValue.isEmpty());
	}

	/** checks if a given price value is higher than 0 which is the correct usage of price values
	 * 
	 * @param price the price value that is going to be checked
	 * @return true if price is higher than 0, false otherwise
	 */
	public boolean isLegalPrice( Float price ){
		return price != null && price > 0;
	}

	protected boolean isSameDateWithoutTime(Calendar c1, Calendar c2) {
		
		return (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)) &&
				(c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH)) &&
				(c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH));
	}

}
