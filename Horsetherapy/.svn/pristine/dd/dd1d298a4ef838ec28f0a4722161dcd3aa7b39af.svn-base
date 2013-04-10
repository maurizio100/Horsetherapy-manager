package sepm.ss13.e0828.service;

import sepm.ss13.e0828.domain.HorseQueryData;

/** This is a helper class that has the task to validate
 * every object that has to do with the Horse entity. This
 * includes creation, deletion and updating
 * @author Maurizio Rinder u0828852
 */
public class HorseValidator extends AbstractValidator{

	/** Validates if horse values are not empty 
	 * @param vHorse the horse that is going to be validated
	 * @return returns true if the Horse's values are not empty is valid, false otherwise
	 */
	public boolean validateHorseForEmptyFields(HorseQueryData vHorse){
		boolean horsevalid = true;
		
		if( vHorse == null ) return (horsevalid = false);

		String horsePhoto = vHorse.getHorsePhoto();
		String horseName = vHorse.getHorseName();
		String horseTherapytype = vHorse.getTherapytype();
		String horseRace = vHorse.getHorseRace();
		
		horsevalid = !isStringEmpty(horsePhoto) && !isStringEmpty(horseName) && !isStringEmpty(horseTherapytype) && !isStringEmpty(horseRace);
				
		return horsevalid;		
	}
	
	/** Checks if the the values needed for deleting the horse are given
	 * 
	 * @param delValues the data for the deletion. This object is going to be validated.
	 * @return returns true if object is valid which means that there is at least a valid id, false otherwise
	 */
	public boolean validateHorsedelete(HorseQueryData delValues){
		if( delValues == null ) return false;
		return !(delValues.getHorseID() < 0);
	}
	/** Checks if the the values needed for selecting the horses by therapytype are valid
	 * 
	 * @param therapyType the data for the selection. This object is going to be validated.
	 * @return returns true if object is valid which means that there is at least the therapytype, false otherwise
	 */
	public boolean validateHorseTherapySelection(HorseQueryData therapyType) {
		if( therapyType == null ) return false;
		String therapy = therapyType.getTherapytype(); 
		return !isStringEmpty(therapy);
	}
	
}
