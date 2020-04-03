package services.reports;

import dao.interfaces.*;
import entities.*;
import org.mockito.*;
import org.testng.annotations.*;

import java.sql.*;
import java.util.*;

import static org.testng.Assert.*;
import static org.mockito.Mockito.*;

public class ReportPaymentsByCardTest {

	private static final long CORRECT_NUMBER = 1234123412341234L;
	private static final long INCORRECT_NUMBER = 1234L;

	@Mock
	PaymentDAO paymentDAOMock;

	@Mock
	List<Payment> listMock;

	@InjectMocks
	ReportPaymentsByCard reportPaymentsByCard;

	@BeforeClass
	void initMocks() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetReportWithCorrectNumber() throws SQLException {
		when(listMock.size()).thenReturn(10);
		when(paymentDAOMock.retrievePaymentsByCardNumber(CORRECT_NUMBER)).thenReturn(listMock);
		assertFalse(reportPaymentsByCard.getReport(CORRECT_NUMBER).isEmpty());
	}

	@Test
	public void testGetReportWithIncorrectNumber() throws SQLException {
		when(paymentDAOMock.retrievePaymentsByCardNumber(INCORRECT_NUMBER)).thenReturn(Collections.emptyList());
		assertTrue(reportPaymentsByCard.getReport(INCORRECT_NUMBER).isEmpty());
	}
}