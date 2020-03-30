package services.actions;

import dao.impl.CreditCardDAOImpl;
import dao.impl.LoginToMySQLException;
import dao.interfaces.CreditCardDAO;
import entities.CreditCard;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class OpenNewCardToClient {

    private final static Logger logger = Logger.getLogger(OpenNewCardToClient.class);

    public int openNewCreditCard(int clientId, int creditLimit, int balance) {
        CreditCard card = new CreditCard();
        List<Long> numbers = new ArrayList<>();
        int res;
        try {
            CreditCardDAO cardDAO = new CreditCardDAOImpl();
            cardDAO.retrieveAll().forEach(c -> numbers.add(c.cardNumber));
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
            logger.error("Something wrong with retrieving cards or access to DB", e);
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