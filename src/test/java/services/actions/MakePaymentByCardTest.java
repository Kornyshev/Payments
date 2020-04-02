package services.actions;

import dao.impl.*;
import dao.interfaces.*;
import entities.*;
import org.mockito.*;
import org.testng.annotations.*;

import java.sql.*;

import static org.testng.Assert.*;
import static org.mockito.Mockito.*;

public class MakePaymentByCardTest {

	private static Payment incorrectPayment1; //Incorrect cards' numbers
	private static Payment incorrectPayment2; //Type - TRANSFER, destination - 0
	private static Payment correctPayment; //Correct payment

	@Mock
	PaymentDAO paymentDAOMock;

	@InjectMocks
	MakePaymentByCard makePaymentByCardActor;

	@BeforeMethod
	void initMockAndVariables() {
		MockitoAnnotations.initMocks(this);
		incorrectPayment1 = new Payment(1234L, PaymentType.ENTERTAINMENT, 0, 0);
		incorrectPayment2 = new Payment(1234123412341234L, PaymentType.TRANSFER, 0, 0);
		correctPayment = new Payment(4567123589567845L, PaymentType.GAS_STATION, 1500, 0);
	}

	@Test
	public void testMakePaymentWithIncorrectCardNumber() throws SQLException, LoginToMySQLException, ClassNotFoundException {
		when(paymentDAOMock.insert(incorrectPayment1)).thenReturn(-1);
		assertEquals(makePaymentByCardActor
				.makePayment(incorrectPayment1.cardNumber, incorrectPayment1.paymentType,
						incorrectPayment1.amount, incorrectPayment1.destination),
				-1);
	}

	@Test
	public void testMakePaymentWithIncorrectDestination() throws SQLException, LoginToMySQLException, ClassNotFoundException {
		when(paymentDAOMock.insert(incorrectPayment2)).thenReturn(-1);
		assertEquals(makePaymentByCardActor
						.makePayment(incorrectPayment2.cardNumber, incorrectPayment2.paymentType,
								incorrectPayment2.amount, incorrectPayment2.destination),
				-1);
	}

	@Test
	public void testMakePaymentWithCorrectPayment() throws SQLException, LoginToMySQLException, ClassNotFoundException {
		when(paymentDAOMock.insert(correctPayment)).thenReturn(3);
		assertEquals(makePaymentByCardActor
						.makePayment(correctPayment.cardNumber, correctPayment.paymentType,
								correctPayment.amount, correctPayment.destination),
				3);
	}
}