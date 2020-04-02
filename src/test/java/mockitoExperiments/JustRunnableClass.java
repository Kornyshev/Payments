package mockitoExperiments;

import mockitoExperiments.forMockito.*;

public class JustRunnableClass {
	public static void main(String[] args) {
		MainClass mainClass = new MainClass();
		System.out.println(mainClass.someProcess("One", "Two"));
	}
}
