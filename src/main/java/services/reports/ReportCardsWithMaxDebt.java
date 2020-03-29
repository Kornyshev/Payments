package services.reports;

import dao.impl.CreditCardDAOImpl;
import dao.interfaces.CreditCardDAO;
import entities.CreditCard;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReportCardsWithMaxDebt {

    private static final int CARDS_QUANTITY = 50;

    public List<CreditCard> getReport() {
        CreditCardDAO cardDAO = new CreditCardDAOImpl();
        List<CreditCard> res = new ArrayList<>();
        try {
            res = cardDAO.retrieveAllCards()
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
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return res;
    }
}
