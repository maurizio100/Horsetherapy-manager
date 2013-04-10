package sepm.ss13.e0828.service;

import java.util.List;

import sepm.ss13.e0828.dao.exceptions.HorseNotExistentException;
import sepm.ss13.e0828.dao.exceptions.NoClassGivenException;
import sepm.ss13.e0828.dao.exceptions.UnacceptedValueException;
import sepm.ss13.e0828.domain.Horse;
import sepm.ss13.e0828.domain.HorseQueryData;
import sepm.ss13.e0828.domain.Invoice;
import sepm.ss13.e0828.service.exception.DataSourceAccessException;
import sepm.ss13.e0828.service.exception.IncorrectTherapyUnitsException;
import sepm.ss13.e0828.service.exception.LessValuesException;
import sepm.ss13.e0828.service.exception.NoHorseIdException;
import sepm.ss13.e0828.service.exception.NoInvoiceIDException;
import sepm.ss13.e0828.service.exception.NoTherapytypeGivenException;
import sepm.ss13.e0828.service.exception.NoUpdateValuesException;
import sepm.ss13.e0828.service.exception.NotSameDateException;

public interface TherapyManagementService {
	/** Service method for creating a new Horse
	 * 
	 * @param newHorse the Horsedata with which the newHorse should be created
	 * @throws LessValuesException if there were not enough values given to create a correct horse
	 * @throws DataSourceAccessException if there was a problem accessing the data source
	 * @throws NoClassGivenException if no class was handed to the method
	 * @throws UnacceptedValueException if there were values that have not a correct format concerning the data context
	 */
	public void createHorse(HorseQueryData newHorse) throws LessValuesException, DataSourceAccessException, NoClassGivenException, UnacceptedValueException;
	/** Service method for updating a Horse
	 * 
	 * @param queryValues values that are used for the update
	 * @throws DataSourceAccessException if there was a problem accessing the data source
	 * @throws HorseNotExistentException if the horse that should be updated doesn't exist
	 * @throws NoClassGivenException if no class was handed to the method
	 * @throws UnacceptedValueException if there were values that have not a correct format concerning the data context
	 */
	public void updateHorse(HorseQueryData queryValues) throws DataSourceAccessException, HorseNotExistentException, NoClassGivenException, UnacceptedValueException;
	/** Service method for deleting a Horse
	 * 
	 * @param deleteHorse the horse that is going to be deleted
	 * @throws NoHorseIdException if was no possibility to identify the horse
	 * @throws DataSourceAccessException if there was a problem accessing the data source
	 * @throws HorseNotExistentException if the horse that should be deleted doesn't exist
	 * @throws NoClassGivenException if no class was handed to the method
	 */
	public void deleteHorse( HorseQueryData deleteHorse ) throws  NoHorseIdException, DataSourceAccessException, HorseNotExistentException, NoClassGivenException;
	/** Service searching a horse using particular arguments
	 * 
	 * @param queryValues the arguments that are used for the search
	 * @return a list of horses found
	 * @throws DataSourceAccessException if there was a problem accessing the data source
	 * @throws NoClassGivenException if no class was handed to the method
	 */
	public List<Horse> searchHorse(HorseQueryData queryValues) throws DataSourceAccessException, NoClassGivenException;
	/** Service for searching horses by therapytype, it also sorts the horses by workload in ascending order
	 * 
	 * @param queryValues the arguments that are used for the search
	 * @return a list of horses found
	 * @throws NoTherapytypeGivenException if ther was no thearpytype given to the search arguments
	 * @throws DataSourceAccessException if there was a problem accessing the data source
	 * @throws NoClassGivenException if no class was handed to the method
	 */
	public List<Horse> listHorsebyTherapy(HorseQueryData queryValues) throws NoTherapytypeGivenException, DataSourceAccessException, NoClassGivenException;
	/** Service for getting all horses that are existent in the datasource
	 * 
	 * @return a list of horses found
	 * @throws DataSourceAccessException if there was a problem accessing the data source
	 */
	public List<Horse> listAllHorses() throws DataSourceAccessException;
	/** Service method for creating Invoice
	 * 
	 * @param newInvoice the new invoice that should be created
	 * @throws LessValuesException if there were not enough values to crate the invoice
	 * @throws DataSourceAccessException if there was a problem accessing the data source
	 * @throws NotSameDateException if the date given is not the same as today's date
	 * @throws IncorrectTherapyUnitsException if the Therapyunits contain wrong values
	 * @throws UnacceptedValueException if there are values that cant be accepted like that
	 */
	public void createInvoice(Invoice newInvoice) throws LessValuesException, DataSourceAccessException, NotSameDateException, IncorrectTherapyUnitsException, UnacceptedValueException;
	/** Service for getting only a particular invoice
	 * 
	 * @param invoiceid the id of the invoice that is supposed to be found
	 * @return the invoice to the given invoiceid
	 * @throws NoInvoiceIDException if no invoiceid was given
	 * @throws DataSourceAccessException if there was a problem accessing the data source
	 */
	public Invoice getInvoiceinformation(Invoice invoiceid) throws NoInvoiceIDException, DataSourceAccessException;
	/** Service for getting all invoices existing in the datasource
	 * 
	 * @return a list of all invoices found 
	 * @throws DataSourceAccessException if there was a problem accessing the data source
	 */
	public List<Invoice> listAllInvoices() throws DataSourceAccessException;
}
