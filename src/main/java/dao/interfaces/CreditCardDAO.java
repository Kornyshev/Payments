package dao.interfaces;

import services.entities.CreditCard;
import services.entities.Payment;

import java.sql.SQLException;
import java.util.List;

public interface CreditCardDAO {
    int insertCreditCard(CreditCard card) throws SQLException, ClassNotFoundException;
    CreditCard retrieveCardByNumber(int number);
    List<CreditCard> retrieveCardsByExpiryDate(String expiryDate);
    List<Payment> retrievePaymentsByCreditCard(int number);
    int updateCardAmountByNumber(int number, int amount);
    int updateCreditLimitByNumber(int number, int newLimit);
    int deleteCreditCardByNumber(int number);
}
