package services.reports;

import dao.impl.CardDAOImpl;
import dao.impl.LoginToMySQLException;
import dao.interfaces.*;
import entities.Card;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class ReportsAboutExpiryDate {

    private final static Logger logger = Logger.getLogger(ReportsAboutExpiryDate.class);
    private CardDAO cardDAOSoonExpire = new CardDAOImpl();
    private CardDAO cardDAOAlreadyExpired = new CardDAOImpl();

    public ReportsAboutExpiryDate()
            throws SQLException, LoginToMySQLException, ClassNotFoundException {
    }

    public List<Card> getSoonExpiredCards(int months) {
        List<Card> cards = new ArrayList<>();
        try {
            cards = cardDAOSoonExpire.retrieveAll();
            logger.info("List of all credit cards is generated for filtering by Stream methods");
        } catch (SQLException e) {
            logger.error("Something wrong with retrieving all credit cards from DB", e);
            e.printStackTrace();
        }
        return cards.
                stream().
                filter(card -> comparingDates(card.expiryDate, currentDate()) <= months).
                collect(Collectors.toList());
    }

    public List<Card> getAlreadyExpiredCards() {
        List<Card> cards = new ArrayList<>();
        try {
            cards = cardDAOAlreadyExpired.retrieveAll();
            logger.info("List of all credit cards is generated for filtering by Stream methods");
        } catch (SQLException e) {
            logger.error("Something wrong with retrieving all credit cards from DB", e);
            e.printStackTrace();
        }
        return cards.
                stream().
                filter(card -> comparingDates(card.expiryDate, currentDate()) < 0).
                collect(Collectors.toList());
    }

    private static String currentDate() {
        Calendar calendar = new GregorianCalendar();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        if (month < 10) {
            return "0" + month + "/" + year;
        } else {
            return "" + month + "/" + year;
        }
    }

    private static int comparingDates(String dateOnCard, String current) {
        int[] curr = new int[]
                {Integer.parseInt(current.split("/")[0]), Integer.parseInt(current.split("/")[1])};
        int[] onCard = new int[]
                {Integer.parseInt(dateOnCard.split("/")[0]), Integer.parseInt(dateOnCard.split("/")[1])};
        if (onCard[1] < curr[1] ||
                (onCard[1] == curr[1] &&
                        onCard[0] < curr[0])) {
            return -1;
        }
        if (onCard[1] == curr[1]) {
            return onCard[0] - curr[0];
        } else {
            return
                    (onCard[1] - curr[1] - 1) * 12 +
                            (12 - curr[0]) +
                            onCard[0];
        }
    }
}