package sepm.ss13.e0828.dao;

import java.util.List;

import sepm.ss13.e0828.dao.exceptions.HorseNotExistentException;
import sepm.ss13.e0828.dao.exceptions.NoClassGivenException;
import sepm.ss13.e0828.dao.exceptions.PersistenceException;
import sepm.ss13.e0828.dao.exceptions.UnacceptedValueException;
import sepm.ss13.e0828.domain.Horse;
import sepm.ss13.e0828.domain.HorseQueryData;

public interface HorseDAO {
	/** creates a new Horse
	 * 
	 * @param newHorse The new Horse with its containing parameters
	 * @throws PersistenceException if there was any kind of persistency problem
	 * @throws NoClassGivenException if no class is given as argument
	 * @throws UnacceptedValueException if the Horse contains values that can't be accepted like negative therapy prices
	 */
	public void createHorse( HorseQueryData newHorse ) throws PersistenceException, NoClassGivenException, UnacceptedValueException;
	/** updates an existent Horse
	 * 
	 * @param updateValues An Object with arbitrary update values, empty fields in the object will be ignored during update
	 * @throws PersistenceException if there was any kind of persistency problem
	 * @throws NoClassGivenException if no class is given as argument
	 * @throws HorseNotExistentException if the horse that was supposed to be updated is not available
	 * @throws UnacceptedValueException if the updateValues contains values that can't be accepted like negative therapy prices 
	 * */
	public void updateHorse( HorseQueryData updateValues ) throws PersistenceException, NoClassGivenException, HorseNotExistentException, UnacceptedValueException;
	/** deletes an existent Horse
	 * 
	 * @param delHorse The Horse that is going to be deleted
	 * @throws PersistenceException if there was any kind of persistency problem
	 * @throws NoClassGivenException if no class is given as argument
	 * @throws HorseNotExistentException if the horse that was supposed to be deleted is not available
	 */
	public void deleteHorse( HorseQueryData delHorse ) throws PersistenceException, NoClassGivenException, HorseNotExistentException;	
	/** returns all Horses available
	 * 
	 * @return a list of all Horses in the persistence unit
	 * @throws PersistenceException if there was any kind of persistency problem
	 */
	public List<Horse> getHorses() throws PersistenceException;
	/** returns all Horses that match the given searchValues
	 * 
	 * @param searchValues the Values or filter that is used for the search
	 * @return a list of all Horses that match the searchValues 
	 * @throws PersistenceException if there was any kind of persistency problem
	 * @throws NoClassGivenException if no class is given as argument
	 */
	public List<Horse> getHorsesBy( HorseQueryData searchValues ) throws PersistenceException, NoClassGivenException;
	/** returns a list with horses that match the given therapyType. The horses are sorted ascending by 
	 * their amount of therapyhours
	 * @param therapyType the therapytype which is used for the search
	 * @return a list of horses with which the given therapy is possible
	 * @throws PersistenceException if there was any kind of persistency problem
	 * @throws NoClassGivenException if no class is given as argument
	 */
	public List<Horse> getHorsesByUsage(HorseQueryData therapyType) throws PersistenceException, NoClassGivenException;
	
}
