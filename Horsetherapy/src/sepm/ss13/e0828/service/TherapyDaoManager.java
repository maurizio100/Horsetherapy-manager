package sepm.ss13.e0828.service;

import sepm.ss13.e0828.dao.HorseDAO;
import sepm.ss13.e0828.dao.InvoiceDAO;

/** This class is used for loading a particular pair of dao objects that 
 * can be used for accessing the data source.
 * 
 * @author Maurizio Rinder u0828852
 */

public abstract class TherapyDaoManager {

	private HorseDAO horsedao = null;
	private InvoiceDAO invoicedao = null;
	
	/** stores the dao objects coming from subclasses 
	 * 
	 * @param horsedao the method for accessing horsedata
	 * @param invoicedao the mtehod for accessing invoicedata
	 */
	public TherapyDaoManager(HorseDAO horsedao, InvoiceDAO invoicedao){
		this.horsedao = horsedao;
		this.invoicedao = invoicedao;
	}
	/** returns the horsedao object that was initialized
	 * @return the current used horsedao
	 */
	public HorseDAO getHorsedao(){
		return horsedao;
	}
	/** returns the invoicedao object that was initialized
	 * @return the current used invoicedao
	 */
	public InvoiceDAO getInvoicedao(){
		return invoicedao;
	}
	
}
