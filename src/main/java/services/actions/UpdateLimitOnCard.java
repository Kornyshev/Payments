package services.actions;

import dao.impl.CreditCardDAOImpl;
import dao.impl.LoginToMySQLException;
import dao.interfaces.CreditCardDAO;
import org.apache.log4j.Logger;

import java.sql.SQLException;

public class UpdateLimitOnCard {

    private final static Logger logger = Logger.getLogger(UpdateLimitOnCard.class);

    public int updateLimit(long number, int newLimit) {
        int res = 0;
        try {
            CreditCardDAO cardDAO = new CreditCardDAOImpl();
            res = cardDAO.updateCreditLimitByNumber(number, newLimit);
            if (res == 1) {
                logger.info("Credit limit on the card with number = " + number + " updated");
            } else {
                logger.error("Something wrong with updating card by number = " + number);
            }
        } catch (SQLException | ClassNotFoundException | LoginToMySQLException e) {
            logger.error("Something wrong with updating of retrieving credit card by number = " + number, e);
            e.printStackTrace();
        }
        return res;
    }
}