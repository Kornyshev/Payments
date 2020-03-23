package dao.impl;

import dao.interfaces.ClientDAO;
import dao.interfaces.CreditCardDAO;
import services.entities.CreditCard;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of CreditCardDAO interface.
 * It completed on 23.03.2020.
 */
public class CreditCardDAOImpl implements CreditCardDAO {

    private Connection conn;
    private PreparedStatement st;
    private ResultSet rs;

    /**
     * Simple insertion one credit card into Database using PreparedStatement.
     * Also this method updates value cardQuantity in table Clients.
     * @param card
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public int insertCreditCard(CreditCard card) throws SQLException, ClassNotFoundException {
        conn = new MySQLConnectionFactory().createConnection();
        st = conn.prepareStatement("INSERT INTO `payments_project`.`credit_cards`" +
                "(`number`,`client_id`,`limit`,`balance`,`expiry_date`) VALUES (?, ?, ?, ?, ?);");
        ClientDAO clientDAO = new ClientDAOImpl();
        st.setLong(1, card.cardNumber);
        st.setInt(2, card.clientID);
        st.setInt(3, card.creditLimit);
        st.setInt(4, card.balance);
        st.setString(5, card.expiryDate);
        int res = st.executeUpdate();
        clientDAO.updateClientsCardsQuantity(card.clientID, 1);
        st.close();
        conn.close();
        return res;
    }

    /**
     * This method just returns the Credit card by number
     * @param number
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public CreditCard retrieveCardByNumber(long number) throws SQLException, ClassNotFoundException {
        conn = new MySQLConnectionFactory().createConnection();
        st = conn.prepareStatement("SELECT * FROM payments_project.credit_cards WHERE number = ?;");
        st.setLong(1, number);
        rs = st.executeQuery();
        int clientId = 0;
        int limit = 0;
        int balance = 0;
        String expiryDate = "";
        while (rs.next()) {
            clientId = rs.getInt("client_id");
            limit = rs.getInt("limit");
            balance = rs.getInt("balance");
            expiryDate = rs.getString("expiry_date");
        }
        rs.close();
        st.close();
        conn.close();
        return new CreditCard(number, clientId, limit, balance, expiryDate);
    }

    /**
     * Retrieving all credit cards.
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @Override
    public List<CreditCard> retrieveAllCards() throws SQLException, ClassNotFoundException {
        List<CreditCard> cards = new ArrayList<>();
        conn = new MySQLConnectionFactory().createConnection();
        st = conn.prepareStatement("SELECT * FROM payments_project.credit_cards;");
        rs = st.executeQuery();
        long number;
        int clientId;
        int limit;
        int balance;
        String expiryDate;
        while (rs.next()) {
            number = rs.getLong("number");
            clientId = rs.getInt("client_id");
            limit = rs.getInt("limit");
            balance = rs.getInt("balance");
            expiryDate = rs.getString("expiry_date");
            cards.add(new CreditCard(number, clientId, limit, balance, expiryDate));
        }
        rs.close();
        st.close();
        conn.close();
        return cards;
    }

    /**
     * Retrieving cards by those expiry dates.
     * @param expiryDate
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public List<CreditCard> retrieveCardsByExpiryDate(String expiryDate) throws SQLException, ClassNotFoundException {
        List<CreditCard> cards = new ArrayList<>();
        conn = new MySQLConnectionFactory().createConnection();
        st = conn.prepareStatement("SELECT * FROM payments_project.credit_cards WHERE expiry_date = ?;");
        st.setString(1, expiryDate);
        rs = st.executeQuery();
        long number;
        int clientId;
        int limit;
        int balance;
        while (rs.next()) {
            number = rs.getLong("number");
            clientId = rs.getInt("client_id");
            limit = rs.getInt("limit");
            balance = rs.getInt("balance");
            expiryDate = rs.getString("expiry_date");
            cards.add(new CreditCard(number, clientId, limit, balance, expiryDate));
        }
        rs.close();
        st.close();
        conn.close();
        return cards;
    }

    /**
     * Retrieving card balance by card number.
     * @param number
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @Override
    public int retrieveCardBalanceByNumber(long number) throws SQLException, ClassNotFoundException {
        int balance = 0;
        conn = new MySQLConnectionFactory().createConnection();
        st = conn.prepareStatement("SELECT balance FROM payments_project.credit_cards WHERE number = ?;");
        st.setLong(1, number);
        rs = st.executeQuery();
        while (rs.next()) {
            balance = rs.getInt("balance");
        }
        rs.close();
        st.close();
        conn.close();
        return balance;
    }


    /**
     * Updating card balance by number.
     * @param number
     * @param balanceAfterChanging
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public int updateCardBalanceByNumber(long number, int balanceAfterChanging) throws SQLException, ClassNotFoundException {
        conn = new MySQLConnectionFactory().createConnection();
        st = conn.prepareStatement("UPDATE `payments_project`.`credit_cards` SET `balance` = ? WHERE `number` = ?;");
        st.setInt(1, balanceAfterChanging);
        st.setLong(2, number);
        int res = st.executeUpdate();
        st.close();
        conn.close();
        return res;
    }

    /**
     * Setup new credit limit for card.
     * @param number
     * @param newLimit
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public int updateCreditLimitByNumber(long number, int newLimit) throws SQLException, ClassNotFoundException {
        conn = new MySQLConnectionFactory().createConnection();
        st = conn.prepareStatement("UPDATE `payments_project`.`credit_cards` SET `limit` = ? WHERE `number` = ?;");
        st.setInt(1, newLimit);
        st.setLong(2, number);
        int res = st.executeUpdate();
        st.close();
        conn.close();
        return res;
    }

    /**
     * This method to force deletion of credit card by number, without checking balance on account.
     * @param number
     * @return
     */
    public int forceDeleteCardByNumber(long number) throws SQLException, ClassNotFoundException {
        conn = new MySQLConnectionFactory().createConnection();
        st = conn.prepareStatement("DELETE FROM payments_project.credit_cards WHERE number = ?;");
        st.setLong(1, number);
        int res = st.executeUpdate();
        st.close();
        conn.close();
        return res;
    }

    /**
     * This method delete card by number quite more gently with checking balance.
     * And returns amount to receiving in cash box,
     * if it's exist. If not - method returns -1.
     * @param number
     * @return
     */
    @Override
    public int cardDeletionWithCheckingByNumber(long number) throws SQLException, ClassNotFoundException {
        CreditCardDAO cardDAO = new CreditCardDAOImpl();
        CreditCard card = cardDAO.retrieveCardByNumber(number);
        if (card.creditLimit > card.balance) {
            return -1;
        } else {
            if (cardDAO.forceDeleteCardByNumber(number) > 0) {
                return card.balance - card.creditLimit;
            } else {
                throw new SQLException("Something wrong with deletion of the card [number] -> " + number);
            }
        }
    }
}