package dao.interfaces;

import dao.impl.LoginToMySQLException;
import entities.Card;

import java.sql.SQLException;
import java.util.List;

public interface CardDAO extends DAO<Card> {
    Card retrieveCardByNumber(long number) throws SQLException;
    List<Card> retrieveCardsByExpiryDate(String expiryDate) throws SQLException;
    int retrieveCardBalanceByNumber(long number) throws SQLException;
    int updateCardBalanceByNumber(long number, int balanceAfterChanging) throws SQLException;
    int updateCreditLimitByNumber(long number, int newLimit) throws SQLException;
    int deleteCardByNumber(long number) throws SQLException, LoginToMySQLException, ClassNotFoundException;
    int cardDeletionWithCheckingByNumber(long number) throws SQLException, ClassNotFoundException, LoginToMySQLException;
}