package services.actions;

import dao.interfaces.*;
import org.mockito.*;
import org.testng.annotations.*;

import java.sql.*;

import static org.testng.Assert.*;
import static org.mockito.Mockito.*;

public class UpdateLimitOnCardTest {

	private static long correctNumber = 1234123412341234L;

	@Mock
	CardDAO cardDAOMock;

	@InjectMocks
	UpdateLimitOnCard updateLimitOnCardActor;

	@BeforeClass
	void initMocks() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testUpdateWithNegativeLimit() throws SQLException {
		int negativeLimit = -1000;
		when(cardDAOMock.updateCreditLimitByNumber(correctNumber, negativeLimit)).thenReturn(-1);
		assertEquals(updateLimitOnCardActor.updateLimit(correctNumber, negativeLimit), -2);
	}

	@Test
	public void testUpdateWithIncorrectNumber() throws SQLException {
		long incorrectNumber = 1234L;
		when(cardDAOMock.updateCreditLimitByNumber(incorrectNumber, 5000)).thenReturn(0);
		assertEquals(updateLimitOnCardActor.updateLimit(incorrectNumber, 5000), 0);
	}

	@Test
	public void testUpdateWithCorrectData() throws SQLException {
		when(cardDAOMock.updateCreditLimitByNumber(correctNumber, 5000)).thenReturn(1);
		assertEquals(updateLimitOnCardActor.updateLimit(correctNumber, 5000), 1);
	}
}