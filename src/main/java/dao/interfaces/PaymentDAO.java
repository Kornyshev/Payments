package dao.interfaces;

import services.entities.Payment;

public interface PaymentDAO {
    int insertPayment(Payment payment);
    Payment retrievePaymentByID(int paymentID);
}
