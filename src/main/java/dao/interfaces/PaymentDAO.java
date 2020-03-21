package dao.interfaces;

import services.entities.Payment;

import java.sql.SQLException;

public interface PaymentDAO {
    int insertPayment(Payment payment) throws SQLException, ClassNotFoundException;
    Payment retrievePaymentByID(int paymentID) throws SQLException, ClassNotFoundException;
}
