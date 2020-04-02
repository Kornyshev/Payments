package services.actions;

import dao.impl.*;
import dao.interfaces.*;
import entities.Card;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class OpenNewCardToClient {

    private final static Logger logger = Logger.getLogger(OpenNewCardToClient.class);
    private CardDAO cardDAO = new CardDAOImpl();
    private ClientDAO clientDAO = new ClientDAOImpl();

    public OpenNewCardToClient()
            throws SQLException, LoginToMySQLException, ClassNotFoundException {
    }

    public int openNewCreditCard(int clientId, int creditLimit, int balance) {
        if (creditLimit < 0 || balance < 0) {
            return -1;
        }
        Card card = new Card();
        List<Long> numbers = new ArrayList<>();
        int res;
        try {
            if (clientDAO.retrieve(clientId) == null) {
                logger.error("Client with the id - " + clientId + " isn't exist!");
                return -1;
            }
            new CardDAOImpl().retrieveAll().forEach(c -> numbers.add(c.cardNumber));
            long number = generateNumber();
            while (numbers.contains(number)) {
                number = generateNumber();
            }
            card.cardNumber = number;
            card.clientID = clientId;
            card.creditLimit = creditLimit;
            card.balance = balance;
            card.expiryDate = dateForNewCard();
            res = cardDAO.insert(card);
        } catch (SQLException | ClassNotFoundException | LoginToMySQLException e) {
            logger.error("Something wrong with opening cards or access to DB", e);
            e.printStackTrace();
            return -1;
        }
        return res;
    }

    private static String dateForNewCard() {
        Calendar calendar = new GregorianCalendar();
        int year = calendar.get(Calendar.YEAR) + 3;
        int month = calendar.get(Calendar.MONTH);
        if (month < 10) {
            return "0" + month + "/" + year;
        } else {
            return "" + month + "/" + year;
        }
    }

    private static long generateNumber() {
        int x1 = (int)(Math.random()*8999) + 1000;
        int x2 = (int)(Math.random()*8999) + 1000;
        int x3 = (int)(Math.random()*8999) + 1000;
        int x4 = (int)(Math.random()*8999) + 1000;
        return Long.parseLong("" + x1 + x2 + x3 + x4);
    }
}