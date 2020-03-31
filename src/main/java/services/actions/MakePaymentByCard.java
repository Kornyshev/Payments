package services.actions;

import dao.impl.LoginToMySQLException;
import dao.impl.PaymentDAOImpl;
import dao.interfaces.PaymentDAO;
import entities.Payment;
import entities.PaymentType;
import org.apache.log4j.Logger;

import java.sql.SQLException;

public class MakePaymentByCard {

    private final static Logger logger = Logger.getLogger(MakePaymentByCard.class);

    public int makePayment(long number, PaymentType type, int amount, long destination) {
        Payment payment = new Payment(number, type, amount, destination);
        int res = 0;
        try {
            PaymentDAO paymentDAO = new PaymentDAOImpl();
            res = paymentDAO.insert(payment);
            if (res != -1) {
                logger.info("New payment with the card's number = " + number + " successfully inserted");
            } else {
                logger.error("Payment is impossible, check the parameters");
            }
        } catch (SQLException | LoginToMySQLException | ClassNotFoundException e) {
            logger.error("Something wrong with payment insertion", e);
            e.printStackTrace();
        }
        return res;
    }
}