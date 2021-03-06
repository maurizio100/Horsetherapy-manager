package sepm.ss13.e0828.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sepm.ss13.e0828.domain.Invoice;
import sepm.ss13.e0828.domain.TherapyUnit;
import sepm.ss13.e0828.domain.Therapytype;
import sepm.ss13.e0828.service.InvoiceValidator;

public class InvoiceValidatorTest {

	private Invoice testInvoice = null;
	private InvoiceValidator validator = null;
	
	@Before
	public void setUp(){
		testInvoice = new Invoice();
		
		testInvoice.setInvoiceDate(new Date().getTime());
		testInvoice.setInvoiceClientfirstname("Max");
		testInvoice.setInvoiceClientsurname("Mustermann");
		testInvoice.setInvoiceAddress("Musterstrasse 10");
		testInvoice.setInvoicePostcode("1100");
		testInvoice.setInvoicePlace("Wien");
		
		validator = new InvoiceValidator();
	}
	
	@After
	public void tearDown(){
		testInvoice= null;
		validator = null;
		
	}
	
	@Test
	public void testInvoiceValidationCorrect() {

		TherapyUnit tu = new TherapyUnit();
		tu.setTherapyHorseID(10);
		tu.setTherapyHorseName("Jimmy");
		tu.setTherapyHours(2);
		tu.setTherapyPrice(14);
		tu.setTherapyType(Therapytype.HIPPOTHERAPIE);
		testInvoice.setTherapyUnit(tu);
		
		boolean result = validator.validateInvoiceForEmptyFields(testInvoice);
		assertTrue(result);
	}
	
	@Test
	public void testInvoiceValidationNoTherapies() {
		boolean result = true;
		result = validator.isTherapyUnitsCorrect(null);
		
		assertFalse(result);
	}
	
	@Test
	public void testInvoiceValidationNoClient() {
		testInvoice.setInvoiceClientfirstname("");
		testInvoice.setInvoiceClientsurname("");
		
		boolean result = validator.validateInvoiceForEmptyFields(testInvoice);
		assertFalse(result);
	}
	
	@Test
	public void testInvoiceDetailListingValidationCorrect(){
		testInvoice.setInvoiceID(10);
		boolean result =validator.validateInvoiceQuery(testInvoice);
		
		assertTrue(result);
	}
	
	@Test
	public void testInvoiceDetailListingValidationIncorrect(){
		testInvoice.setInvoiceID(-1);
		boolean result =validator.validateInvoiceQuery(testInvoice);
		
		assertFalse(result);
	}

	
}
