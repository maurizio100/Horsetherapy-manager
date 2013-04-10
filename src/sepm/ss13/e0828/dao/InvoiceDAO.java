package sepm.ss13.e0828.dao;

import java.util.List;

import sepm.ss13.e0828.dao.exceptions.InvoiceNotExistentException;
import sepm.ss13.e0828.dao.exceptions.NoClassGivenException;
import sepm.ss13.e0828.dao.exceptions.PersistenceException;
import sepm.ss13.e0828.domain.Invoice;

public interface InvoiceDAO {

	/** creates a new Invoice with at least one Therapyunit
	 * 
	 * @param newInvoice The new Invoice with its containing parameters including a list of therapyunits
	 * @throws PersistenceException if there was any kind of persistency problem
	 * @throws NoClassGivenException if no class is given as argument
	 */	
	public void createInvoice( Invoice newInvoice ) throws PersistenceException, NoClassGivenException;
	/** returns all Invoices available with their main information
	 * 
	 * @return a list of all Invoices in the persistence unit
	 * @throws PersistenceException if there was any kind of persistency problem
	 */
	public List<Invoice> getInvoices() throws PersistenceException;

	/** returns the invoice that matches the given invoiceID. Information inclueded are: 
	 * -- the whole invoiceinformation (id, date, client, adress .. )
	 * -- a list with all therapyunits
	 * -- the sum of the therapy
	 * @param invoiceID the invoiceID embedded in an Invoice object
	 * @return the invoice with the above mentioned information
	 * @throws PersistenceException if there was any kind of persistency problem
	 * @throws InvoiceNotExistentException if the invoice that belongs to the given invoiceID couldn't be found
	 */
	public Invoice getSpecificInvoice(Invoice invoiceID) throws PersistenceException, InvoiceNotExistentException;


}
