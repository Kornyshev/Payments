package view;

import dao.impl.LoginToMySQLException;
import services.reports.ReportsByTables;

import java.sql.SQLException;

public class Test {
    public static void main(String[] args) throws SQLException, LoginToMySQLException, ClassNotFoundException {
        ReportsByTables reportsByTables = new ReportsByTables();
        //reportsByTables.getAllClients().forEach(client -> System.out.println(client.toFormattedString()));
        //reportsByTables.getAllCards().forEach(creditCard -> System.out.println(creditCard.toFormattedString()));
        //reportsByTables.getAllPayments().forEach(payment -> System.out.println(payment.toFormattedString()));
    }
}
