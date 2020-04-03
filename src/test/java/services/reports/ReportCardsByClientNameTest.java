package services.reports;

import dao.interfaces.*;
import entities.*;
import org.mockito.*;
import org.testng.annotations.*;

import java.sql.*;
import java.util.*;

import static org.testng.Assert.*;
import static org.mockito.Mockito.*;

public class ReportCardsByClientNameTest {

	private static final String CORRECT_NAME = "James Hall";
	private static final String INCORRECT_NAME = "Unknown Client";

	@Mock
	ClientDAO clientDAOMock;

	@Mock
	ArrayList<Card> listMock;

	@InjectMocks
	ReportCardsByClientName reportCardsByClientName;

	@BeforeClass
	void initMocks() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetReportWithCorrectName() throws SQLException {
		when(listMock.size()).thenReturn(10);
		when(clientDAOMock.retrieveClientsCardsByName(CORRECT_NAME)).thenReturn(listMock);
		assertFalse(reportCardsByClientName.getReport(CORRECT_NAME).isEmpty());
	}

	@Test
	public void testGetReportWithIncorrectName() throws SQLException {
		when(clientDAOMock.retrieveClientsCardsByName(INCORRECT_NAME)).thenReturn(Collections.emptyList());
		assertTrue(reportCardsByClientName.getReport(INCORRECT_NAME).isEmpty());
	}
}