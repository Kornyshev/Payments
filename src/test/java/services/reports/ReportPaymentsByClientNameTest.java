package services.reports;

import dao.impl.*;
import dao.interfaces.*;
import entities.*;
import org.mockito.*;
import org.testng.annotations.*;

import java.sql.*;
import java.util.*;

import static org.mockito.Mockito.when;
import static org.testng.Assert.*;

public class ReportPaymentsByClientNameTest {

	private static final String CORRECT_NAME = "John Jones";
	private static final String INCORRECT_NAME = "Unknown Client";

	@Mock
	PaymentDAO paymentDAOMock;

	@Mock
	List<Payment> listMock;

	@InjectMocks
	ReportPaymentsByClientName reportPaymentsByClientName;

	@BeforeClass
	void initMocks() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetReportWithCorrectName()
			throws SQLException, LoginToMySQLException, ClassNotFoundException {
		when(listMock.size()).thenReturn(10);
		when(paymentDAOMock.retrievePaymentsByClient(CORRECT_NAME)).thenReturn(listMock);
		assertFalse(reportPaymentsByClientName.getReport(CORRECT_NAME).isEmpty());
	}

	@Test
	public void testGetReportWithIncorrectNumber()
			throws SQLException, LoginToMySQLException, ClassNotFoundException {
		when(paymentDAOMock.retrievePaymentsByClient(INCORRECT_NAME)).thenReturn(Collections.emptyList());
		assertTrue(reportPaymentsByClientName.getReport(INCORRECT_NAME).isEmpty());
	}
}