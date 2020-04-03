package services.reports;

import dao.impl.LoginToMySQLException;
import dao.impl.PaymentDAOImpl;
import dao.interfaces.*;
import entities.Payment;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReportPaymentsByClientName {

    private final static Logger logger = Logger.getLogger(ReportPaymentsByClientName.class);
    private PaymentDAO paymentDAO = new PaymentDAOImpl();

    public ReportPaymentsByClientName()
            throws SQLException, LoginToMySQLException, ClassNotFoundException {
    }

    public List<Payment> getReport(String name) {
        List<Payment> payments = new ArrayList<>();
        try {
            payments = paymentDAO.retrievePaymentsByClient(name);
            logger.info("List of payments by the client's name is generated");
        } catch (SQLException | ClassNotFoundException | LoginToMySQLException e) {
            logger.error("Something wrong with payments by the client's name [name] - " + name, e);
            e.printStackTrace();
        }
        return payments;
    }
}