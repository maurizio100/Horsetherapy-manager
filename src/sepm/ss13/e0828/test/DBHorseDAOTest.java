package sepm.ss13.e0828.test;

import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sepm.ss13.e0828.dao.Connector;
import sepm.ss13.e0828.dao.DBHorseDAO;
import sepm.ss13.e0828.dao.HorseDAO;
import sepm.ss13.e0828.dao.exceptions.HorseNotExistentException;
import sepm.ss13.e0828.dao.exceptions.NoClassGivenException;
import sepm.ss13.e0828.dao.exceptions.PersistenceException;
import sepm.ss13.e0828.dao.exceptions.UnacceptedValueException;
import sepm.ss13.e0828.domain.Horse;
import sepm.ss13.e0828.domain.HorseQueryData;
import sepm.ss13.e0828.domain.Horserace;
import sepm.ss13.e0828.domain.Therapytype;

public class DBHorseDAOTest {

	private HorseDAO horsedao;
	private HorseQueryData testHorse = null;
	private Connection conn = null;
	private HorseQueryData queryValues = null;
	
	@Before
	public void setUp() throws SQLException{
		conn = Connector.getConnection();
		conn.setAutoCommit(false);
		
		horsedao = new DBHorseDAO();		
		testHorse = new HorseQueryData();
		testHorse.setHorseName("Speedy");
		testHorse.setHorsePhoto("speedy.jpg");
		testHorse.setHorsePrice(16.90f);
		testHorse.setHorseTherapy(Therapytype.HIPPOTHERAPIE);
		testHorse.setHorseRace(Horserace.ALBANER);
	}
	
	@After
	public void tearDown() throws SQLException{
		Connector.getConnection().rollback();
		Connector.closeConnection();
		testHorse = null;
	}
	
	
	@Test
	public void testGetHorsesCorrect() throws PersistenceException {
		List<Horse> horses = null;
		horses = horsedao.getHorses();		
		assertTrue(horses != null);
	}

	@Test
	public void testCreateHorseCorrect() throws PersistenceException, NoClassGivenException, UnacceptedValueException {
		List<Horse> horses = null;
		horses = horsedao.getHorses();
		int size = horses.size();
		
		horsedao.createHorse(testHorse);
		
		horses = horsedao.getHorses();
		assertTrue( (size+1) == horses.size() );
	}

	@Test(expected = NoClassGivenException.class)
	public void testCreateHorseWithNoHorse() throws PersistenceException, NoClassGivenException, UnacceptedValueException {
		testHorse = null;
		horsedao.createHorse(testHorse);
	}

	
	@Test(expected = UnacceptedValueException.class)
	public void testCreateHorseWithNegativePrice() throws PersistenceException, NoClassGivenException, UnacceptedValueException {
		testHorse.setHorsePrice(-16.90f);
		horsedao.createHorse(testHorse);
	}

	
	@Test
	public void testDeleteHorseCorrect() throws PersistenceException, NoClassGivenException, UnacceptedValueException, HorseNotExistentException {
		horsedao.createHorse(testHorse);

		List<Horse> horses = null;
		horses = horsedao.getHorses();
		int size = horses.size();
		queryValues = new HorseQueryData();
		queryValues.setHorseID(testHorse.getHorseID());
		
		horsedao.deleteHorse( queryValues );
		assertTrue( size > horsedao.getHorses().size());
	}

	@Test(expected = HorseNotExistentException.class)
	public void testDeleteHorseObjectNotAvailable() throws PersistenceException, NoClassGivenException, UnacceptedValueException, HorseNotExistentException {
		horsedao.createHorse(testHorse);
		queryValues = new HorseQueryData();
		queryValues.setHorseID(testHorse.getHorseID()+1);
		horsedao.deleteHorse( queryValues );
	}
	
	@Test(expected = NoClassGivenException.class)
	public void testDeleteHorseNoGivenValue() throws PersistenceException, NoClassGivenException, HorseNotExistentException {
		queryValues = null;
		horsedao.deleteHorse(queryValues);
	}
	
	@Test
	public void testUpdateHorseCorrect() throws PersistenceException, NoClassGivenException, UnacceptedValueException, HorseNotExistentException {		
		horsedao.createHorse(testHorse);
		
		queryValues = new HorseQueryData();
		queryValues.setHorseID(testHorse.getHorseID());
		queryValues.setHorsePrice(testHorse.getHorsePrice() + 20.0f);
		testHorse.setHorsePrice(testHorse.getHorsePrice() + 20.0f);
		
		horsedao.updateHorse(queryValues);
		
		List<Horse> horses = null;
		horses = horsedao.getHorses();
		
		Horse updatedHorse = null;
		for( Horse h : horses ){
			if( h.getHorseID() == testHorse.getHorseID()){
				updatedHorse = h; break;
			}
		}
	
		assertTrue( testHorse.getHorsePrice() == updatedHorse.getHorseTherapyprice() );
	}
	
	@Test(expected = UnacceptedValueException.class)
	public void testUpdateHorseNoCorrectValue() throws PersistenceException, NoClassGivenException, UnacceptedValueException, HorseNotExistentException {
		horsedao.createHorse(testHorse);

		queryValues = new HorseQueryData();
		queryValues.setHorseID(testHorse.getHorseID());
		queryValues.setHorsePrice(testHorse.getHorsePrice() * (-1));
		horsedao.updateHorse(queryValues);		
	}

	@Test(expected = HorseNotExistentException.class)
	public void testUpdateHorseHorseNonExistent() throws PersistenceException, NoClassGivenException, UnacceptedValueException, HorseNotExistentException {
		horsedao.createHorse(testHorse);

		queryValues = new HorseQueryData();
		queryValues.setHorseID(testHorse.getHorseID());
		queryValues.setHorsePrice(testHorse.getHorsePrice() + 20.0f );
		queryValues.setHorseID(testHorse.getHorseID() + 1);
		
		horsedao.updateHorse(queryValues);	
	}

	@Test(expected = NoClassGivenException.class)
	public void testUpdateNoHorse() throws PersistenceException, NoClassGivenException, HorseNotExistentException, UnacceptedValueException {
		queryValues = null;
		horsedao.updateHorse(queryValues);
	}


	@Test
	public void testGetHorsesByCorrect() throws PersistenceException, NoClassGivenException, UnacceptedValueException {
		horsedao.createHorse(testHorse);
		HorseQueryData query = new HorseQueryData();
		query.setHorseID(testHorse.getHorseID());
		
		List<Horse> horses = horsedao.getHorsesBy( query );
		
		assertTrue( horses.get(0).getHorseName().equals( testHorse.getHorseName()) ); 	
	}
	
	@Test
	public void testGetHorsesByMultipleValuesCorrect() throws PersistenceException, NoClassGivenException, UnacceptedValueException {
		horsedao.createHorse(testHorse);
		queryValues = new HorseQueryData();
		queryValues.setHorseID(testHorse.getHorseID());
		queryValues.setHorseName(testHorse.getHorseName());
		
		List<Horse> horses = horsedao.getHorsesBy( queryValues );
		
		assertTrue( horses.get(0).getHorseName().equals( testHorse.getHorseName()) ); 	
	}

	
	@Test(expected = NoClassGivenException.class)
	public void testGetHorsesByNoQuery() throws PersistenceException, NoClassGivenException, UnacceptedValueException {
		HorseQueryData query = null;
		horsedao.getHorsesBy( query );
	}

	@Test
	public void testGetHorsesByUsageCorrect() throws PersistenceException, NoClassGivenException, UnacceptedValueException {
		horsedao.createHorse(testHorse);
		
		List<Horse> horses = horsedao.getHorsesByUsage( testHorse );
		Horse lessUsage = null;
		for( Horse h : horses ){
			if( h.getHorseID() == testHorse.getHorseID()){
				lessUsage = h; break;
			}
		}
		assertTrue( lessUsage.getUsage() == 0.0 ); 		
	}

	@Test(expected = NoClassGivenException.class)
	public void testGetHorsesByUsageNoClass() throws PersistenceException, NoClassGivenException, UnacceptedValueException {
		HorseQueryData query = null;
		horsedao.getHorsesByUsage(query);
	}
	
}
