package mockitoExperiments;

import org.mockito.*;
import org.testng.annotations.*;
import mockitoExperiments.forMockito.*;

import static org.testng.Assert.*;
import static org.mockito.Mockito.*;

public class MockitoExperiment {

	@Mock
	DatabaseDAO databaseDAO;

	@Mock
	NetworkDAO networkDAO;

	@InjectMocks
	MainClass mainClass;

	@BeforeMethod
	void initMocks() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void test() {
		when(networkDAO.send("One")).thenReturn(3);
		when(databaseDAO.save("Two")).thenReturn(3);
		assertEquals(mainClass.someProcess("One", "Two"), 6);
	}
}
