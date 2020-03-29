package dao.impl;

import dao.interfaces.ClientDAO;
import dao.interfaces.CreditCardDAO;
import dao.interfaces.DAO;
import dao.interfaces.PaymentDAO;
import entities.Payment;
import entities.PaymentType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAOImpl implements PaymentDAO {

    private Connection conn;
    private PreparedStatement st;
    private ResultSet rs;

    public PaymentDAOImpl() throws SQLException, LoginToMySQLException, ClassNotFoundException {
        conn = new MySQLConnectionFactory().createConnection();
    }

    @Override
    public int insert(Payment entity) throws SQLException, ClassNotFoundException, LoginToMySQLException {
        int res = 0;
        CreditCardDAO creditCardDAO = new CreditCardDAOImpl();
        int currentCardBalance = creditCardDAO.retrieveCardBalanceByNumber(entity.cardNumber);
        if (currentCardBalance < entity.amount) {
            System.err.println("Payment is not possible!!!");
            return -1;
        }
        if (entity.paymentType.equals(PaymentType.TRANSFER)) {
            int destinationBalance = creditCardDAO.retrieveCardBalanceByNumber(entity.destination);
            destinationBalance += entity.amount;
            res += creditCardDAO.updateCardBalanceByNumber(entity.destination, destinationBalance);
        }
        currentCardBalance -= entity.amount;
        res += creditCardDAO.updateCardBalanceByNumber(entity.cardNumber, currentCardBalance);
        st = conn.prepareStatement("INSERT INTO `payments_project`.`payments`" +
                "(`card_number`,`type`,`amount`,`destination`) VALUES (?,?,?,?);");
        st.setLong(1, entity.cardNumber);
        st.setString(2, entity.paymentType.name());
        st.setInt(3, entity.amount);
        st.setLong(4, entity.destination);
        res += st.executeUpdate();
        DAO.closing(st, conn);
        return res;
    }

    @Override
    public Payment retrieve(int id) throws SQLException {
        Payment payment = new Payment();
        st = conn.prepareStatement("SELECT * FROM `payments_project`.`payments` WHERE id = ?;");
        st.setInt(1, id);
        rs = st.executeQuery();
        while (rs.next()) {
            payment.paymentId = rs.getInt("id");
            payment.cardNumber = rs.getLong("card_number");
            payment.paymentType = PaymentType.valueOf(rs.getString("type"));
            payment.amount = rs.getInt("amount");
            payment.destination = rs.getLong("destination");
        }
        DAO.closing(rs, st, conn);
        return payment;
    }

    @Override
    public List<Payment> retrieveAll() throws SQLException {
        List<Payment> payments = new ArrayList<>();
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
        DAO.closing(rs, st, conn);
        return payments;
    }

    @Override
    public List<Payment> retrievePaymentsByCardNumber(long number) throws SQLException, ClassNotFoundException {
        List<Payment> payments = new ArrayList<>();
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
        DAO.closing(rs, st, conn);
        return payments;
    }

    @Override
    public List<Payment> retrievePaymentsByClient(String name)
            throws SQLException, ClassNotFoundException, LoginToMySQLException {
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

    @Override
    public int update(Payment entity) throws SQLException {
        throw new UnsupportedOperationException("Payment update is not allowed");
    }

    @Override
    public int delete(int id) throws SQLException, LoginToMySQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Payment delete is not allowed");
    }
}