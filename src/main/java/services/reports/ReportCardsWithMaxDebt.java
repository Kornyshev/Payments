package services.reports;

import dao.impl.CardDAOImpl;
import dao.impl.LoginToMySQLException;
import dao.interfaces.CardDAO;
import entities.Card;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReportCardsWithMaxDebt {

    public static final int CARDS_QUANTITY = 50;
    private static final Logger logger = Logger.getLogger(ReportCardsWithMaxDebt.class);
    private CardDAO cardDAO = new CardDAOImpl();

    public ReportCardsWithMaxDebt()
            throws SQLException, LoginToMySQLException, ClassNotFoundException {
    }

    public List<Card> getReport() {
        List<Card> res = new ArrayList<>();
        try {
            res = cardDAO.retrieveAll()
                    .stream()
                    .sorted((o1, o2) -> {
                int debt1 = o1.creditLimit - o1.balance;
                int debt2 = o2.creditLimit - o2.balance;
                if (debt1 == debt2 || (debt1 < 0 && debt2 < 0)) return 0;
                if (debt1 > debt2) return -1;
                return 1;
            })
                    .limit(CARDS_QUANTITY)
                    .collect(Collectors.toList());
            logger.info("List of " + CARDS_QUANTITY + " credit cards with Max debt is generated");
        } catch (SQLException e) {
            logger.error("Something wrong with list of credit cards with Max debt", e);
            e.printStackTrace();
        }
        return res;
    }
}