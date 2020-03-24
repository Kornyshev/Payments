package services.reports;

import dao.impl.PaymentDAOImpl;
import services.entities.Payment;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReportPaymentsByClientName {
    public List<Payment> getReport(String name) {
        List<Payment> payments = new ArrayList<>();
        try {
            payments = new PaymentDAOImpl().retrievePaymentsByClient(name);
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Something wrong with the payments of the client - [name] = " + name);
            e.printStackTrace();
        }
        return payments;
    }
}
