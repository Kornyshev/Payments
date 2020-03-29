package services.reports;

import dao.impl.ClientDAOImpl;
import dao.impl.CreditCardDAOImpl;
import dao.impl.PaymentDAOImpl;
import entities.Client;
import entities.CreditCard;
import entities.Payment;

import java.sql.SQLException;
import java.util.List;

public class ReportsByTables {
    public List<Client> getAllClients() throws SQLException, ClassNotFoundException {
        return new ClientDAOImpl().retrieveAllClients();
    }

    public List<CreditCard> getAllCards() throws SQLException, ClassNotFoundException {
        return new CreditCardDAOImpl().retrieveAllCards();
    }

    public List<Payment> getAllPayments() throws SQLException, ClassNotFoundException {
        return new PaymentDAOImpl().retrieveAllPayments();
    }
}
