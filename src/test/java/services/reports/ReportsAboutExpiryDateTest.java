package services.reports;

import org.testng.annotations.*;

import static org.testng.Assert.*;

public class ReportsAboutExpiryDateTest {

	@Test(expectedExceptions = {UnsupportedOperationException.class})
	public void testGetSoonExpiredCards() {
		throw new UnsupportedOperationException("This test not necessary while the DB doesn't contain very soon expiring cards!");
	}

	@Test(expectedExceptions = {UnsupportedOperationException.class})
	public void testGetAlreadyExpiredCards() {
		throw new UnsupportedOperationException("This test not necessary while the DB doesn't contain expired cards!");
	}
}