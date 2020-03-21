package dao.impl;

import dao.interfaces.CreditCardDAO;
import dao.interfaces.PaymentDAO;
import services.entities.Payment;
import services.entities.PaymentType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
}