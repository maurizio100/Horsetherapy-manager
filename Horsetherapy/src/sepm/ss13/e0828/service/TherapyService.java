package sepm.ss13.e0828.service;

import java.util.List;

import org.apache.log4j.Logger;

import sepm.ss13.e0828.dao.exceptions.HorseNotExistentException;
import sepm.ss13.e0828.dao.exceptions.InvoiceNotExistentException;
import sepm.ss13.e0828.dao.exceptions.NoClassGivenException;
import sepm.ss13.e0828.dao.exceptions.PersistenceException;
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

public class TherapyService implements TherapyManagementService {

	private Logger logger = Logger.getLogger(TherapyService.class);

	private TherapyDaoManager therapyDaoManager = null;
	private static TherapyManagementService service = new TherapyService();

	private HorseValidator horseval = new HorseValidator();
	private InvoiceValidator invoiceval = new InvoiceValidator();

	private TherapyService(){ therapyDaoManager = new TherapyDBManager(); }

	@Override
	public void createHorse(HorseQueryData newHorse) 
			throws LessValuesException, DataSourceAccessException, UnacceptedValueException {
		try {
			if( !horseval.validateHorseForEmptyFields(newHorse) ){ throw new LessValuesException(); }
			else if( !horseval.isLegalPrice(newHorse.getHorsePrice() )){ throw new UnacceptedValueException(); }
			else{ therapyDaoManager.getHorsedao().createHorse(newHorse); }

		} catch (PersistenceException e) {
			announcePersistenceError(e);
			throw new DataSourceAccessException();
		} catch (NoClassGivenException e) {
			announcePersistenceError(e);
		} catch (UnacceptedValueException e) {
			announcePersistenceError(e);
			throw new UnacceptedValueException();
		}

	}

	@Override
	public void updateHorse(HorseQueryData queryValues)
			throws DataSourceAccessException, HorseNotExistentException, UnacceptedValueException {
		try {
			if( !horseval.validateHorseForEmptyFields(queryValues) ){ throw new UnacceptedValueException(); }
			else if( !horseval.isLegalPrice(queryValues.getHorsePrice() )){ throw new UnacceptedValueException(); }
			else{ therapyDaoManager.getHorsedao().updateHorse(queryValues); }

		} catch (PersistenceException e) {
			announcePersistenceError(e);
			throw new DataSourceAccessException();
		} catch (NoClassGivenException e) {
			announcePersistenceError(e);
		} catch (HorseNotExistentException e) {
			announcePersistenceError(e);
			throw new HorseNotExistentException();
		} catch (UnacceptedValueException e) {
			announcePersistenceError(e);
			throw new UnacceptedValueException();
		}
	}

	@Override
	public void deleteHorse(HorseQueryData deleteHorse)
			throws NoHorseIdException, DataSourceAccessException, HorseNotExistentException, NoClassGivenException {

		try {
			if( horseval.validateHorsedelete(deleteHorse) ){ therapyDaoManager.getHorsedao().deleteHorse(deleteHorse); }
			else{ throw new NoHorseIdException(); }

		} catch (PersistenceException e) {
			announcePersistenceError(e);
			throw new DataSourceAccessException();
		} catch (NoClassGivenException e) {
			announcePersistenceError(e);
			throw new NoClassGivenException();
		} catch (HorseNotExistentException e) {
			announcePersistenceError(e);
			throw new HorseNotExistentException();
		}
	}

	@Override
	public List<Horse> searchHorse(HorseQueryData searchValues) throws DataSourceAccessException, NoClassGivenException {
		List<Horse> horses = null;

		try {
			if( searchValues != null ){ horses = therapyDaoManager.getHorsedao().getHorsesBy(searchValues); }
			else{ throw new NoClassGivenException(); }

		} catch (PersistenceException e) {
			announcePersistenceError(e);
			throw new DataSourceAccessException();
		} catch (NoClassGivenException e) {
			announcePersistenceError(e);
			throw new NoClassGivenException();
		}
		return horses;
	}

	@Override
	public List<Horse> listHorsebyTherapy(HorseQueryData queryValues)
			throws NoTherapytypeGivenException, DataSourceAccessException, NoClassGivenException {

		List<Horse> horses = null;

		try {
			if( queryValues != null ){
				if( horseval.validateHorseTherapySelection(queryValues) ){
					horses = therapyDaoManager.getHorsedao().getHorsesByUsage(queryValues);
				}else{

				}
			}else{ throw new NoClassGivenException(); }

		} catch (PersistenceException e) {
			announcePersistenceError(e);
			throw new DataSourceAccessException();
		} catch (NoClassGivenException e) {
			announcePersistenceError(e);
			throw new NoClassGivenException();
		}
		return horses;
	}

	@Override
	public List<Horse> listAllHorses() throws DataSourceAccessException {
		List<Horse> horses = null;

		try {
			horses = therapyDaoManager.getHorsedao().getHorses();
		} catch (PersistenceException e) {
			announcePersistenceError(e);
			throw new DataSourceAccessException();
		}

		return horses;
	}

	@Override
	public void createInvoice(Invoice newInvoice)
			throws LessValuesException, DataSourceAccessException, NotSameDateException, IncorrectTherapyUnitsException, UnacceptedValueException {

		try {
			if( !invoiceval.validateInvoiceForEmptyFields(newInvoice) ){
				throw new LessValuesException();
			}else if( !invoiceval.isValidInvoiceDate(newInvoice.getInvoiceDate())){
				throw new NotSameDateException();
			}else if( !invoiceval.isTherapyUnitsCorrect(newInvoice.getTherapyunits())){
				throw new IncorrectTherapyUnitsException();
			}else if( !invoiceval.isLegalPrice(newInvoice.getInvoiceSum() )){
				throw new UnacceptedValueException();
			}else if( !invoiceval.isPostcodeCorrect(newInvoice.getInvoicePostcode())){
				throw new UnacceptedValueException();
			}else{
				therapyDaoManager.getInvoicedao().createInvoice(newInvoice);
			}
		} catch (PersistenceException e) {
			announcePersistenceError(e);
			throw new DataSourceAccessException();
		} catch (NoClassGivenException e) {
			announcePersistenceError(e);
		}
	}

	@Override
	public Invoice getInvoiceinformation(Invoice invoiceid)
			throws NoInvoiceIDException, DataSourceAccessException {
		Invoice retInvoice = null;

		try {
			if( invoiceval.validateInvoiceQuery(invoiceid) ){
				retInvoice = therapyDaoManager.getInvoicedao().getSpecificInvoice(invoiceid);
			}else{ throw new NoInvoiceIDException(); }
			
		} catch (PersistenceException e) {
			announcePersistenceError(e);
			throw new DataSourceAccessException();
		} catch (InvoiceNotExistentException e) {
			announcePersistenceError(e);
		}

		return retInvoice;
	}

	@Override
	public List<Invoice> listAllInvoices() throws DataSourceAccessException {
		List<Invoice> invoices = null;

		try {
			invoices = therapyDaoManager.getInvoicedao().getInvoices();
		} catch (PersistenceException e) {
			announcePersistenceError(e);
			throw new DataSourceAccessException();
		}

		return invoices;
	}

	public static TherapyManagementService getInstance(){
		return service;
	}

	private void announcePersistenceError( Exception e ){
		logger.error(e.getMessage());
	}


}
