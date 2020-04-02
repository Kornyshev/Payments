package services.reports;

import dao.impl.ClientDAOImpl;
import dao.impl.LoginToMySQLException;
import entities.Card;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class ReportCardsByClientName {

    private final static Logger logger = Logger.getLogger(String.valueOf(ReportCardsByClientName.class));

    public List<Card> getReport(String name) {
        List<Card> cards = new ArrayList<>();
        try {
            cards = new ClientDAOImpl().retrieveClientsCardsByName(name);
            logger.info("List of client's credit cards is generated");
        } catch (SQLException | ClassNotFoundException | LoginToMySQLException e) {
            logger.error("Something wrong with the client [name] - " + name, e);
            e.printStackTrace();
        }
        return cards;
    }
}