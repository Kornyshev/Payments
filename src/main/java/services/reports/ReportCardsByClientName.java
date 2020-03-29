package services.reports;

import dao.impl.ClientDAOImpl;
import entities.CreditCard;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReportCardsByClientName {
    public List<CreditCard> getReport(String name) {
        List<CreditCard> cards = new ArrayList<>();
        try {
            cards = new ClientDAOImpl().retrieveClientsCardsByName(name);
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Something wrong with the client - [name] = " + name);
            e.printStackTrace();
        }
        return cards;
    }
}
