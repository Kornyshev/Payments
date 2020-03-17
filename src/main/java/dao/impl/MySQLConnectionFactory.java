package dao.impl;

import dao.interfaces.ConnectionFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnectionFactory implements ConnectionFactory {

    private static final String MySQL_URL =
            "jdbc:mysql://localhost:3306/books_site?" +
                    "useUnicode=true&useJDBC" +
                    "CompliantTimezoneShift=true&" +
                    "useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String MySQL_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static String USER;
    private static String PASSWORD;

    public Connection createConnection() throws ClassNotFoundException, SQLException {
        try {
            getLoginSettings();
        } catch (IOException e) {
            return null;
        }
        Class.forName(MySQL_DRIVER);
        return DriverManager.getConnection(MySQL_URL, USER, PASSWORD);
    }

    private static void getLoginSettings() throws IOException {
        File file = new File("C:\\Projects\\PaymentsHomeWork\\Payments\\src\\main\\resources\\login.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        USER = reader.readLine();
        PASSWORD = reader.readLine();
    }
}
