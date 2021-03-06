package sepm.ss13.e0828.service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import sepm.ss13.e0828.domain.Invoice;
import sepm.ss13.e0828.domain.TherapyUnit;

/** This is a helper class that has the task to validate
 * every object that has to do with the Invoice entity. This
 * includes creation and selection of a particular invoice
 * @author Maurizio Rinder u0828852
 */
public class InvoiceValidator extends AbstractValidator {

	/** Validates if invoice is valid for adding it to the persistence
	 * unite
	 * @param vInvoice the Invoice that is going to be validated
	 * @return returns true if the Horse is valid, false otherwise
	 */
	public boolean validateInvoiceForEmptyFields(Invoice vInvoice){
		boolean invoicevalid = true;

		if( vInvoice == null ) return (invoicevalid = false);

		
		String clientFirstname = vInvoice.getInvoiceClientfirstname();
		String clientSurname = vInvoice.getInvoiceClientsurname();
		String address = vInvoice.getInvoiceAddress();
		String place = vInvoice.getInvoicePlace();
		
		invoicevalid = !isStringEmpty(clientFirstname) &&
				!isStringEmpty(clientSurname) &&
				!isStringEmpty(address) &&
				!isStringEmpty(place);
				
		return invoicevalid;		
	}
	/** Checks if the the values needed for selecting details of an invoice are given
	 * 
	 * @param invoiceQuery the data for the selection. This object is going to be validated.
	 * @return returns true if object is valid which means that there is at least a valid id, false otherwise
	 */
	public boolean validateInvoiceQuery(Invoice invoiceQuery){
		if ( invoiceQuery == null ) return false;
		return !(invoiceQuery.getInvoiceID() < 0);
	}

	/** Checks if the values of the Therapysections are correct
	 * 
	 * @param units a list of therapyunits
	 * @return true if all units are valid, false if at least one has incorrect values
	 */
	public boolean isTherapyUnitsCorrect(List<TherapyUnit> units){
		if (units == null || units.size() <= 0){ return false; }
		for( TherapyUnit unit : units ){
			if( isStringEmpty(unit.getTherapyHorseName()) ) return false;
			if( unit.getTherapyHorseID() < 0 ) return false;
			if( !isLegalPrice(unit.getTherapyPrice())) return false;
			if( unit.getTherapyType() == null ) return false;
			if( unit.getTherapyHours() <= 0 ) return false;
		}
		return true;
	}
	
	/** Checks if the invoice date is valid, which means that is equal to today's date
	 * 
	 * @param millis the date given in milliseconds
	 * @return true if the millis-date is equal to todays date without using time for comparison
	 */
	public boolean isValidInvoiceDate( long millis ){
		Calendar c1 = GregorianCalendar.getInstance();
		c1.setTimeInMillis(millis);
		
		Calendar c2 = GregorianCalendar.getInstance();
		c2.setTimeInMillis(new Date().getTime());
		
		return isSameDateWithoutTime(c1, c2);
	}
	
	/** Checks if the given postcode is valid
	 * 
	 * @param postcode the postcode for validation
	 * @return ture if postcode is valid, false otherwise
	 */
	public boolean isPostcodeCorrect(String postcode){
		if( isStringEmpty(postcode) ) return false;
		if( postcode.length() != 4 ) return false;
		
		try{
			int transformedCode = Integer.parseInt(postcode); 
			if( transformedCode <= 0 ) return false;

		}catch( NumberFormatException nfe){
			return false;
		}
		return true;
	}

}
