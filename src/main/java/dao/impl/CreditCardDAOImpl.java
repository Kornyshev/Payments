package dao.impl;

import dao.interfaces.ClientDAO;
import dao.interfaces.CreditCardDAO;
import services.entities.Client;
import services.entities.CreditCard;
import services.entities.Payment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public CreditCard retrieveCardByNumber(int number) {
        return null;
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

    public List<CreditCard> retrieveCardsByExpiryDate(String expiryDate) {
        return null;
    }

    public List<Payment> retrievePaymentsByCreditCard(long number) {
        return null;
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

    public int updateCreditLimitByNumber(long number, int newLimit) {
        return 0;
    }

    public int deleteCreditCardByNumber(long number) {
        return 0;
    }
}
