package services.actions;

import dao.impl.*;
import dao.interfaces.*;
import org.mockito.*;
import org.testng.annotations.*;

import java.sql.*;

import static org.mockito.Mockito.when;
import static org.testng.Assert.*;

public class CloseCardByNumberTest {

	private static final long CORRECT_NUMBER = 1234123412341234L;
	private static final long INCORRECT_NUMBER = 1234L;

	@Mock
	CardDAO cardDAOMock;

	@InjectMocks
	CloseCardByNumber closeCardByNumberActor;

	@BeforeClass
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testCloseCardSuccessful() throws SQLException, LoginToMySQLException, ClassNotFoundException {
		when(cardDAOMock.cardDeletionWithCheckingByNumber(CORRECT_NUMBER)).thenReturn(15000);
		assertEquals(closeCardByNumberActor.closeCard(CORRECT_NUMBER), 15000);
	}

	@Test
	public void testCloseCardUnsuccessful() throws SQLException, LoginToMySQLException, ClassNotFoundException {
		when(cardDAOMock.cardDeletionWithCheckingByNumber(INCORRECT_NUMBER)).thenReturn(-1);
		assertEquals(closeCardByNumberActor.closeCard(INCORRECT_NUMBER), -1);
	}
}