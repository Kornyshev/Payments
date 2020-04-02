package mockitoExperiments.forMockito;

public class MainClass {
	public NetworkDAO networkDAO;
	public DatabaseDAO databaseDAO;

	public int someProcess(String msg1, String msg2) {
		int res = 0;
		res += networkDAO.send(msg1);
		System.out.println("-----------");
		res += databaseDAO.save(msg2);
		return res;
	}
}
