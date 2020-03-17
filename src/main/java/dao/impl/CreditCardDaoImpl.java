package dao.impl;

import dao.interfaces.CreditCardDAO;
import services.entities.CreditCard;
import services.entities.Payment;

import java.util.List;

public class CreditCardDaoImpl implements CreditCardDAO {
    public int insertCreditCard(CreditCard card) {
        return 0;
    }

    public CreditCard retrieveCardByNumber(int number) {
        return null;
    }

    public List<CreditCard> retrieveCardsByExpiryDate(String expiryDate) {
        return null;
    }

    public List<Payment> retrievePaymentsByCreditCard(int number) {
        return null;
    }

    public int updateCardAmountByNumber(int number, int amount) {
        return 0;
    }

    public int updateCreditLimitByNumber(int number, int newLimit) {
        return 0;
    }

    public int deleteCreditCardByNumber(int number) {
        return 0;
    }
}
