package utils;

import dao.impl.CreditCardDAOImpl;
import dao.impl.LoginToMySQLException;
import dao.impl.PaymentDAOImpl;
import dao.interfaces.CreditCardDAO;
import dao.interfaces.PaymentDAO;
import entities.Payment;
import entities.PaymentType;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RandomPaymentsForDB {

    public static void main(String[] args) throws SQLException, ClassNotFoundException, LoginToMySQLException {
        /*
        Method to action
         */
        insertionPaymentsToDB(generateRandomPayments());
    }

    /**
     * This method inserts set of payments into Database using DAO interface.
     * As parameter, it takes Set<> which is generated by specific method.
     * If this util method can`t insert payment to DB, it writes something to console.
     * @param payments
     */
    private static void insertionPaymentsToDB(Set<Payment> payments)
            throws SQLException, LoginToMySQLException, ClassNotFoundException {
        System.out.println(payments.size() + " <- set.size()");
        PaymentDAO paymentDAO = new PaymentDAOImpl();
        payments.forEach(payment -> {
            try {
                int res = paymentDAO.insert(payment);
                if (res == -1) System.out.println("Something wrong");
            } catch (SQLException | ClassNotFoundException | LoginToMySQLException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * This method generates set of payments for next work.
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    private static Set<Payment> generateRandomPayments()
            throws SQLException, ClassNotFoundException, LoginToMySQLException {
        Set<Payment> payments = new HashSet<>();
        Set<Long> cardNumbers = new HashSet<>();
        CreditCardDAO creditCardDAO = new CreditCardDAOImpl();
        creditCardDAO.retrieveAll()
                .forEach(card -> cardNumbers.add(card.cardNumber));
        for (Long number : cardNumbers) {
            int rnd = (int) (Math.random() * 10) + 1;
            for (int i = 0; i < rnd; i++) {
                payments.add(generateRandomPaymentToCardNumber(number, cardNumbers));
            }
        }
        return payments;
    }

    /**
     * This method generates random payments.
     * @param number
     * @param cardsNumbers
     * @return
     */
    private static Payment generateRandomPaymentToCardNumber(Long number, Set<Long> cardsNumbers) {
        Payment payment = new Payment();
        List<Long> numbers = new ArrayList<>(cardsNumbers);
        payment.cardNumber = number;
        numbers.remove(number);
        payment.paymentType = generateRandomPaymentType();
        payment.amount = generateRandomAmount();
        if (payment.paymentType.equals(PaymentType.TRANSFER)) {
            payment.destination = numbers.get((int) (Math.random() * numbers.size()));
        }
        return payment;
    }

    /**
     * It is just simple generation of payment amount.
     * @return
     */
    private static int generateRandomAmount() {
        int res = (int)(Math.random() * 70) * 100;
        if (Math.random() < 0.5)
            return res;
        else
            return res * (-1);
    }

    /**
     * This method generates PaymentType as ENUM object.
     * @return
     */
    private static PaymentType generateRandomPaymentType() {
        int rnd = (int) (Math.random() * 9);
        if (rnd == 3 || rnd == 4) return PaymentType.ENTERTAINMENT;
        if (rnd == 5 || rnd == 6) return PaymentType.GAS_STATION;
        if (rnd == 7 || rnd == 8) return PaymentType.STORE;
        return PaymentType.TRANSFER;
    }
}