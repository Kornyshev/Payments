package services.reports;

import dao.impl.ClientDAOImpl;
import dao.impl.LoginToMySQLException;
import dao.interfaces.*;
import entities.Card;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class ReportCardsByClientName {

    private final static Logger logger = Logger.getLogger(String.valueOf(ReportCardsByClientName.class));
    private ClientDAO clientDAO = new ClientDAOImpl();

    public ReportCardsByClientName()
            throws SQLException, LoginToMySQLException, ClassNotFoundException {
    }

    public List<Card> getReport(String name) {
        List<Card> cards = new ArrayList<>();
        try {
            cards = clientDAO.retrieveClientsCardsByName(name);
            logger.info("List of client's credit cards is generated");
        } catch (SQLException e) {
            logger.error("Something wrong with the client [name] - " + name, e);
            e.printStackTrace();
        }
        return cards;
    }
}