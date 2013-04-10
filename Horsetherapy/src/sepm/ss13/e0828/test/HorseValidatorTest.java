package sepm.ss13.e0828.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sepm.ss13.e0828.domain.HorseQueryData;
import sepm.ss13.e0828.domain.Horserace;
import sepm.ss13.e0828.domain.Therapytype;
import sepm.ss13.e0828.service.HorseValidator;

public class HorseValidatorTest {

	private HorseQueryData testHorse = null;
	private HorseValidator validator = null;
	private HorseQueryData testquery = null;

	@Before
	public void setUp(){
		testHorse = new HorseQueryData();

		testHorse.setHorseName("Speedy");
		testHorse.setHorsePrice(20.0f);
		testHorse.setHorseTherapy(Therapytype.HIPPOTHERAPIE);
		testHorse.setHorsePhoto("speedy.jpg");
		testHorse.setHorseRace(Horserace.ABACO);
		
		testquery = new HorseQueryData();
		validator = new HorseValidator();
	}

	@After
	public void tearDowN(){
		testHorse= null;
		validator = null;
		testquery = null;

	}

	@Test
	public void testHorseValidationCorrect() {
		boolean result = true;
		
		result = validator.validateHorseForEmptyFields(testHorse);
		result = validator.isLegalPrice( testHorse.getHorsePrice() );
		
		assertTrue(result);
	}

	@Test
	public void testHorseValidationPictureMissing() {
		testHorse.setHorsePhoto(null);
		boolean result = true;
		
		result = validator.validateHorseForEmptyFields(testHorse);
		
		assertFalse(result);
	}

	@Test
	public void testHorseValidationPriceLowerZero() {
		float price = testHorse.getHorsePrice();
		testHorse.setHorsePrice( price * (-1) );
		boolean result = true;
		
		result = validator.validateHorseForEmptyFields(testHorse);
		result = validator.isLegalPrice( testHorse.getHorsePrice() );
		
		assertFalse(result);
	}

	@Test
	public void testUpdateValuesCorrect(){
		testquery.setHorseID(10);
		testquery.setHorsePhoto("speedy.png");
		testquery.setHorseName(testHorse.getHorseName());
		testquery.setHorseTherapy(Therapytype.fromString(testHorse.getTherapytype()));
		testquery.setHorsePrice(testHorse.getHorsePrice());
		testquery.setHorseRace(Horserace.fromString(testHorse.getHorseRace()));
		
		boolean result = true;
		
		result = validator.validateHorseForEmptyFields(testquery);
		result = validator.isLegalPrice( testquery.getHorsePrice());

		assertTrue(result);
	}

	@Test
	public void testUpdateValuesNoValue(){
		testquery = null;
		boolean result = true;
		
		if( !validator.validateHorseForEmptyFields(testquery) ){ result = false; }
		else if( !validator.isLegalPrice( testquery.getHorsePrice() )){ result = false; }

		assertFalse(result);
	}


	@Test
	public void testDeleteValuesCorrect(){
		testquery.setHorseID(10);
		boolean result = validator.validateHorsedelete(testquery);
		assertTrue(result);
	}

	@Test
	public void testDeleteValuesNoValue(){
		testquery = null;
		boolean result = validator.validateHorsedelete(testquery);
		assertFalse(result);
	}


}
