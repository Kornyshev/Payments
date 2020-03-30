package services.reports;

import dao.impl.ClientDAOImpl;
import dao.impl.CreditCardDAOImpl;
import dao.impl.LoginToMySQLException;
import dao.impl.PaymentDAOImpl;
import entities.Client;
import entities.CreditCard;
import entities.Payment;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

public class ReportsByTables {

    private final static Logger logger = Logger.getLogger(ReportsByTables.class);

    public List<Client> getAllClients() throws SQLException, ClassNotFoundException, LoginToMySQLException {
        logger.info("Method ReportsByTable.getAllClients() was called");
        return new ClientDAOImpl().retrieveAll();
    }

    public List<CreditCard> getAllCards() throws SQLException, ClassNotFoundException, LoginToMySQLException {
        logger.info("Method ReportsByTable.getAllCards() was called");
        return new CreditCardDAOImpl().retrieveAll();
    }

    public List<Payment> getAllPayments() throws SQLException, ClassNotFoundException, LoginToMySQLException {
        logger.info("Method ReportsByTable.getAllPayments() was called");
        return new PaymentDAOImpl().retrieveAll();
    }
}