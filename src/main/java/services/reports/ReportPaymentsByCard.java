package services.reports;

import dao.impl.LoginToMySQLException;
import dao.impl.PaymentDAOImpl;
import entities.Payment;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReportPaymentsByCard {

    private final static Logger logger = Logger.getLogger(ReportPaymentsByCard.class);

    public List<Payment> getReport(long number) {
        List<Payment> payments = new ArrayList<>();
        try {
            payments = new PaymentDAOImpl().retrievePaymentsByCardNumber(number);
            logger.info("List of payments by the card's number is generated");
        } catch (SQLException | ClassNotFoundException | LoginToMySQLException e) {
            logger.error("Something wrong with payment by card with number - " + number, e);
            e.printStackTrace();
        }
        return payments;
    }
}