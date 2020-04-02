package services.actions;

import dao.impl.CardDAOImpl;
import dao.impl.LoginToMySQLException;
import dao.interfaces.CardDAO;
import org.apache.log4j.Logger;

import java.sql.SQLException;

public class CloseCardByNumber {

    private final static Logger logger = Logger.getLogger(CloseCardByNumber.class);
    private CardDAO cardDAO = new CardDAOImpl();

    public CloseCardByNumber()
            throws SQLException, LoginToMySQLException, ClassNotFoundException {
    }

    public int closeCard(long number) {
        int res = -1;
        try {
            res = cardDAO.cardDeletionWithCheckingByNumber(number);
            if (res != -1) {
                logger.info("Credit cards with number = " + number + " deleted and result calculated");
            } else {
                logger.error("Closing card is impossible, check balance on card");
            }
        } catch (SQLException | ClassNotFoundException | LoginToMySQLException e) {
            logger.error("Something wrong with deletion card by number = " + number, e);
            e.printStackTrace();
        }
        return res;
    }
}