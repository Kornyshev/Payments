package mockitoExperiments.forMockito;

public class DatabaseImpl implements DatabaseDAO {
	@Override
	public int save(String msg) {
		System.out.println("Saving to DB...");
		System.out.println("My message -> " + msg);
		return msg.length();
	}
}
