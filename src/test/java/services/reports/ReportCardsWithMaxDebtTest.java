package services.reports;

import dao.impl.*;
import dao.interfaces.*;
import entities.*;
import org.testng.annotations.*;

import java.sql.*;
import java.util.*;

import static org.testng.Assert.*;

public class ReportCardsWithMaxDebtTest {

	private static final int CARDS_QUANTITY = ReportCardsWithMaxDebt.CARDS_QUANTITY;
	private CardDAO cardDAO;
	private ReportCardsWithMaxDebt reportCardsWithMaxDebt;

	@BeforeClass
	void initMocks() throws SQLException, LoginToMySQLException, ClassNotFoundException {
		cardDAO = new CardDAOImpl();
		reportCardsWithMaxDebt = new ReportCardsWithMaxDebt();
	}

	@Test
	public void testReportListSize() throws SQLException {
		assertTrue(cardDAO.retrieveAll().size() > 2000);
		assertEquals(CARDS_QUANTITY, reportCardsWithMaxDebt.getReport().size());
	}

	@Test
	public void testOrderInReport() {
		List<Card> cards = reportCardsWithMaxDebt.getReport();
		int current;
		int next;
		for (int i = 0; i < cards.size() - 1; i++) {
			current = cards.get(i).creditLimit - cards.get(i).balance;
			next = cards.get(i+1).creditLimit - cards.get(i+1).balance;
			assertTrue(current >= next);
		}
	}
}