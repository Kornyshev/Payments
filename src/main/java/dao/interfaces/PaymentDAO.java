package dao.interfaces;

import services.entities.Payment;

import java.sql.SQLException;
import java.util.List;

public interface PaymentDAO {
    int insertPayment(Payment payment) throws SQLException, ClassNotFoundException;
    Payment retrievePaymentByID(int paymentID) throws SQLException, ClassNotFoundException;
    List<Payment> retrieveAllPayments() throws SQLException, ClassNotFoundException;
    List<Payment> retrievePaymentsByCardNumber(long number) throws SQLException, ClassNotFoundException;
    List<Payment> retrievePaymentsByClient(String name) throws SQLException, ClassNotFoundException;
}