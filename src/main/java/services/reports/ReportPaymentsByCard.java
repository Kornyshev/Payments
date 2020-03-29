package services.reports;

import dao.impl.PaymentDAOImpl;
import entities.Payment;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReportPaymentsByCard {
    public List<Payment> getReport(long number) {
        List<Payment> payments = new ArrayList<>();
        try {
            payments = new PaymentDAOImpl().retrievePaymentsByCardNumber(number);
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Something wrong with the payments by the card - [number] = " + number);
            e.printStackTrace();
        }
        return payments;
    }
}