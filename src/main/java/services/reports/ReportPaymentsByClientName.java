package services.reports;

import dao.impl.LoginToMySQLException;
import dao.impl.PaymentDAOImpl;
import entities.Payment;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReportPaymentsByClientName {

    private final static Logger logger = Logger.getLogger(ReportPaymentsByClientName.class);

    public List<Payment> getReport(String name) {
        List<Payment> payments = new ArrayList<>();
        try {
            payments = new PaymentDAOImpl().retrievePaymentsByClient(name);
            logger.info("List of payments by the client's name is generated");
        } catch (SQLException | ClassNotFoundException | LoginToMySQLException e) {
            logger.error("Something wrong with payments by the client's name [name] - " + name, e);
            e.printStackTrace();
        }
        return payments;
    }
}