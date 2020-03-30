package dao.interfaces;

import dao.impl.LoginToMySQLException;
import entities.Payment;

import java.sql.SQLException;
import java.util.List;

public interface PaymentDAO extends DAO<Payment>{
    List<Payment> retrievePaymentsByCardNumber(long number) throws SQLException;
    List<Payment> retrievePaymentsByClient(String name) throws SQLException, ClassNotFoundException, LoginToMySQLException;
}