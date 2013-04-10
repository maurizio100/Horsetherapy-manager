package sepm.ss13.e0828.gui.horse;

import sepm.ss13.e0828.domain.HorseQueryData;
import sepm.ss13.e0828.domain.Horserace;
import sepm.ss13.e0828.domain.Therapytype;
import sepm.ss13.e0828.gui.exception.NoCorrectFormatException;
import sepm.ss13.e0828.gui.exception.UnknownRaceException;
import sepm.ss13.e0828.gui.exception.UnknownTherapytypeException;

public class HorseQueryValueParser {

	private HorseQueryData query = null;

	public HorseQueryValueParser(){
		query = new HorseQueryData();
	}

	public void setHorseID( String id ) throws NoCorrectFormatException{
		if( !isEmpty(id) ){
			try{
				query.setHorseID( Integer.parseInt(id) );
			}catch(NumberFormatException nfe){
				throw new NoCorrectFormatException("Horse ID");
			}
		}
	}

	public void setHorseName( String name ){
		if( !isEmpty(name) ){
			query.setHorseName(name);
		}
	}

	public void setTherapytype( String therapytype) throws UnknownTherapytypeException{
		if( !isEmpty(therapytype) ){
			Therapytype type = Therapytype.fromString(therapytype);
			if(  type != null ){			
				query.setHorseTherapy( type );
			}else{
				throw new UnknownTherapytypeException();
			}
		}
	}

	public void setHorseTherapyprice( String therapyprice ) throws NoCorrectFormatException{
		if( !isEmpty(therapyprice) ){
			try{
				query.setHorsePrice( Float.parseFloat(therapyprice) );
			}catch(NumberFormatException nfe){
				throw new NoCorrectFormatException("Preis/h");
			}		
		}
	}

	public void setHorsePhoto( String photo ){
		query.setHorsePhoto(photo);
	}

	public HorseQueryData getQuery(){
		return query;
	}

	private boolean isEmpty(String content){
		return content == null || content.isEmpty();
	}

	public void setHorserace(String race) throws UnknownRaceException {
		if( !isEmpty(race) ){
			Horserace type = Horserace.fromString(race);
			if(  type != null ){			
				query.setHorseRace( type );
			}else{
				throw new UnknownRaceException();
			}
		}	
	}
}
