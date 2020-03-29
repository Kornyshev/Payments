package dao.interfaces;

import dao.impl.LoginToMySQLException;
import entities.CreditCard;

import java.sql.SQLException;
import java.util.List;

public interface CreditCardDAO extends DAO<CreditCard> {
    CreditCard retrieveCardByNumber(long number) throws SQLException;
    List<CreditCard> retrieveCardsByExpiryDate(String expiryDate) throws SQLException;
    int retrieveCardBalanceByNumber(long number) throws SQLException;
    int updateCardBalanceByNumber(long number, int balanceAfterChanging) throws SQLException;
    int updateCreditLimitByNumber(long number, int newLimit) throws SQLException;
    int deleteCardByNumber(long number) throws SQLException;
    int cardDeletionWithCheckingByNumber(long number) throws SQLException, ClassNotFoundException, LoginToMySQLException;
}