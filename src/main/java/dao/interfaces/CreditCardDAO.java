package dao.interfaces;

import services.entities.Client;
import services.entities.CreditCard;
import services.entities.Payment;

import java.sql.SQLException;
import java.util.List;

public interface CreditCardDAO {
    int insertCreditCard(CreditCard card) throws SQLException, ClassNotFoundException;
    CreditCard retrieveCardByNumber(int number);
    List<CreditCard> retrieveAllCards() throws SQLException, ClassNotFoundException;
    List<CreditCard> retrieveCardsByExpiryDate(String expiryDate);
    List<Payment> retrievePaymentsByCreditCard(long number);
    int retrieveCardBalanceByNumber(long number) throws SQLException, ClassNotFoundException;
    int updateCardBalanceByNumber(long number, int balanceAfterChanging) throws SQLException, ClassNotFoundException;
    int updateCreditLimitByNumber(long number, int newLimit);
    int deleteCreditCardByNumber(long number);
}
