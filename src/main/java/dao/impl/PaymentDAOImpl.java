package dao.impl;

import dao.interfaces.ClientDAO;
import dao.interfaces.CreditCardDAO;
import dao.interfaces.PaymentDAO;
import services.entities.Client;
import services.entities.Payment;
import services.entities.PaymentType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of PaymentDAO interface.
 * It completed on 23.03.2020.
 */
public class PaymentDAOImpl implements PaymentDAO {

    private Connection conn;
    private PreparedStatement st;
    private ResultSet rs;

    /**
     * Insertion payment in DB and at the same time updating
     * balances of cards in other the table, if it necessary.
     * If this payment is not possible - return -1;
     * @param payment
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public int insertPayment(Payment payment) throws SQLException, ClassNotFoundException {
        conn = new MySQLConnectionFactory().createConnection();
        int res = 0;
        CreditCardDAO creditCardDAO = new CreditCardDAOImpl();
        int currentCardBalance = creditCardDAO.retrieveCardBalanceByNumber(payment.cardNumber);
        if (currentCardBalance < payment.amount) {
            System.err.println("Payment is not possible!!!");
            return -1;
        }
        if (payment.paymentType.equals(PaymentType.TRANSFER)) {
            int destinationBalance = creditCardDAO.retrieveCardBalanceByNumber(payment.destination);
            destinationBalance += payment.amount;
            res += creditCardDAO.updateCardBalanceByNumber(payment.destination, destinationBalance);
        }
        currentCardBalance -= payment.amount;
        res += creditCardDAO.updateCardBalanceByNumber(payment.cardNumber, currentCardBalance);
        st = conn.prepareStatement("INSERT INTO `payments_project`.`payments`" +
                "(`card_number`,`type`,`amount`,`destination`) VALUES (?,?,?,?);");
        st.setLong(1, payment.cardNumber);
        st.setString(2, payment.paymentType.name());
        st.setInt(3, payment.amount);
        st.setLong(4, payment.destination);
        res += st.executeUpdate();
        st.close();
        conn.close();
        return res;
    }

    /**
     * Retrieving payment by his ID.
     * @param paymentID
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public Payment retrievePaymentByID(int paymentID) throws SQLException, ClassNotFoundException {
        Payment payment = new Payment();
        conn = new MySQLConnectionFactory().createConnection();
        st = conn.prepareStatement("SELECT * FROM `payments_project`.`payments` WHERE id = ?;");
        st.setInt(1, paymentID);
        rs = st.executeQuery();
        while (rs.next()) {
            payment.paymentId = rs.getInt("id");
            payment.cardNumber = rs.getLong("card_number");
            payment.paymentType = PaymentType.valueOf(rs.getString("type"));
            payment.amount = rs.getInt("amount");
            payment.destination = rs.getLong("destination");
        }
        rs.close();
        st.close();
        conn.close();
        return payment;
    }

    /**
     * This method just returns all payments
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @Override
    public List<Payment> retrieveAllPayments() throws SQLException, ClassNotFoundException {
        List<Payment> payments = new ArrayList<>();
        conn = new MySQLConnectionFactory().createConnection();
        st = conn.prepareStatement("SELECT * FROM payments_project.payments;");
        rs = st.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            long cardNumber = rs.getLong("card_number");
            PaymentType type = PaymentType.valueOf(rs.getString("type"));
            int amount = rs.getInt("amount");
            long destination = rs.getLong("destination");
            payments.add(new Payment(id, cardNumber, type, amount, destination));
        }
        rs.close();
        st.close();
        conn.close();
        return payments;
    }

    /**
     * This method returns list of payments for one card by number.
     * @param number
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @Override
    public List<Payment> retrievePaymentsByCardNumber(long number) throws SQLException, ClassNotFoundException {
        List<Payment> payments = new ArrayList<>();
        conn = new MySQLConnectionFactory().createConnection();
        st = conn.prepareStatement("SELECT * FROM payments_project.payments WHERE card_number = ?;");
        st.setLong(1, number);
        rs = st.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            long cardNumber = rs.getLong("card_number");
            PaymentType type = PaymentType.valueOf(rs.getString("type"));
            int amount = rs.getInt("amount");
            long destination = rs.getLong("destination");
            payments.add(new Payment(id, cardNumber, type, amount, destination));
        }
        rs.close();
        st.close();
        conn.close();
        return payments;
    }

    /**
     * This method returns all client's payments through all his cards.
     * @param name
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @Override
    public List<Payment> retrievePaymentsByClient(String name) throws SQLException, ClassNotFoundException {
        List<Payment> payments = new ArrayList<>();
        ClientDAO clientDAO = new ClientDAOImpl();
        PaymentDAO paymentDAO = new PaymentDAOImpl();
        clientDAO.retrieveClientsCardsByName(name).forEach(card -> {
            try {
                payments.addAll(paymentDAO.retrievePaymentsByCardNumber(card.cardNumber));
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        return payments;
    }
}