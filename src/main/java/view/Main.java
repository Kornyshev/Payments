package view;

import dao.impl.LoginToMySQLException;
import services.reports.ReportCardsWithMaxDebt;
import services.reports.ReportsByTables;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, LoginToMySQLException, ClassNotFoundException {
        ReportCardsWithMaxDebt cardsWithMaxDebt = new ReportCardsWithMaxDebt();
        cardsWithMaxDebt.getReport().forEach(System.out::println);
    }
}