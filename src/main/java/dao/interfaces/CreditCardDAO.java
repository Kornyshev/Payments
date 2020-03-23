package dao.interfaces;

import services.entities.CreditCard;

import java.sql.SQLException;
import java.util.List;

public interface CreditCardDAO {
    int insertCreditCard(CreditCard card) throws SQLException, ClassNotFoundException;
    CreditCard retrieveCardByNumber(long number) throws SQLException, ClassNotFoundException;
    List<CreditCard> retrieveAllCards() throws SQLException, ClassNotFoundException;
    List<CreditCard> retrieveCardsByExpiryDate(String expiryDate) throws SQLException, ClassNotFoundException;
    int retrieveCardBalanceByNumber(long number) throws SQLException, ClassNotFoundException;
    int updateCardBalanceByNumber(long number, int balanceAfterChanging) throws SQLException, ClassNotFoundException;
    int updateCreditLimitByNumber(long number, int newLimit) throws SQLException, ClassNotFoundException;
    int forceDeleteCardByNumber(long number) throws SQLException, ClassNotFoundException;
    int cardDeletionWithCheckingByNumber(long number) throws SQLException, ClassNotFoundException;
}