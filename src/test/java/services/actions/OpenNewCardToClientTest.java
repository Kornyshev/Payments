package services.actions;

import dao.impl.*;
import dao.interfaces.*;
import entities.*;
import org.mockito.*;
import org.testng.annotations.*;

import java.sql.*;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;
import static org.testng.Assert.*;

public class OpenNewCardToClientTest {

	private static int positiveBalance = 1000;
	private static int positiveLimit = 1000;

	@Mock
	ClientDAO clientDAOMock;

	@Mock
	CardDAO cardDAOMock;

	@Mock
	Client clientMock;

	@InjectMocks
	OpenNewCardToClient openNewCardToClientActor;

	@BeforeClass
	void initMocksAndVariables() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testNegativeLimitOrBalance() {
		int negativeLimit = -1000;
		int negativeBalance = -1000;
		assertEquals(
				openNewCardToClientActor.openNewCreditCard(anyInt(), negativeLimit, negativeBalance),
				-1);
	}

	@Test
	public void testInexistentClient() throws SQLException {
		//An inexistent client's ID
		int incorrectID = 4567;
		when(clientDAOMock.retrieve(incorrectID)).thenReturn(null);
		assertEquals(
				openNewCardToClientActor.openNewCreditCard(incorrectID, positiveLimit, positiveBalance),
				-1);
	}

	@Test
	public void testCorrectCardOpening() throws SQLException, LoginToMySQLException, ClassNotFoundException {
		//An inexistent client's ID
		int correctID = 147;
		when(clientDAOMock.retrieve(correctID)).thenReturn(clientMock);
		when(cardDAOMock.insert(anyObject())).thenReturn(2);
		assertEquals(
				openNewCardToClientActor.openNewCreditCard(correctID, positiveLimit, positiveBalance),
				2);
	}
}