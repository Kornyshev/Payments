package mockitoExperiments.forMockito;

public class NetworkImpl implements NetworkDAO {
	@Override
	public int send(String msg) {
		System.out.println("Sending some message by net...");
		System.out.println("My message -> " + msg);
		return msg.length();
	}
}
