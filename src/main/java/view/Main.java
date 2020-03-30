package view;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String query = sc.nextLine();
        while (!query.equals("exit")) {
            executeQuery(query);
            query = sc.nextLine();
        }
        sc.close();
    }

    private static void executeQuery(String query) {
        System.out.println("Some process " + query);
    }
}