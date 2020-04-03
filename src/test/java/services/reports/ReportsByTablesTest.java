package services.reports;

import dao.impl.*;
import dao.interfaces.*;
import org.testng.annotations.*;

import java.sql.*;

import static org.testng.Assert.*;

public class ReportsByTablesTest {

	private static ClientDAO clientDAO;
	private static CardDAO cardDAO;
	private static PaymentDAO paymentDAO;

	@BeforeClass
	void initVariables() throws SQLException, LoginToMySQLException, ClassNotFoundException {
		clientDAO = new ClientDAOImpl();
		cardDAO = new CardDAOImpl();
		paymentDAO = new PaymentDAOImpl();
	}

	@Test
	public void testGetAllClients() throws SQLException {
		assertTrue(clientDAO.retrieveAll().size() > 800);
	}

	@Test
	public void testGetAllCards() throws SQLException {
		assertTrue(cardDAO.retrieveAll().size() > 2000);
	}

	@Test
	public void testGetAllPayments() throws SQLException {
		assertTrue(paymentDAO.retrieveAll().size() > 10000);
	}
}