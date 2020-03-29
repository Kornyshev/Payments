package services.reports;

import dao.impl.CreditCardDAOImpl;
import entities.CreditCard;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class ReportsAboutExpiryDate {

    public List<CreditCard> getSoonExpiredCards(int months) {
        List<CreditCard> cards = new ArrayList<>();
        try {
            cards = new CreditCardDAOImpl().retrieveAllCards();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return cards.
                stream().
                filter(card -> comparingDates(card.expiryDate, currentDate()) > months).
                collect(Collectors.toList());
    }

    public List<CreditCard> getAlreadyExpiredCards() {
        List<CreditCard> cards = new ArrayList<>();
        try {
            cards = new CreditCardDAOImpl().retrieveAllCards();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return cards.
                stream().
                filter(card -> comparingDates(card.expiryDate, currentDate()) == -1).
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