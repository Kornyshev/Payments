package dao.impl;

import dao.interfaces.ClientDAO;
import dao.interfaces.CreditCardDAO;
import dao.interfaces.DAO;
import entities.CreditCard;

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

    public CreditCardDAOImpl() throws SQLException, ClassNotFoundException, LoginToMySQLException {
        conn = new MySQLConnectionFactory().createConnection();
    }

    @Override
    public int insert(CreditCard entity) throws SQLException, ClassNotFoundException, LoginToMySQLException {
        st = conn.prepareStatement("INSERT INTO `payments_project`.`credit_cards`" +
                "(`number`,`client_id`,`limit`,`balance`,`expiry_date`) VALUES (?, ?, ?, ?, ?);");
        ClientDAO clientDAO = new ClientDAOImpl();
        st.setLong(1, entity.cardNumber);
        st.setInt(2, entity.clientID);
        st.setInt(3, entity.creditLimit);
        st.setInt(4, entity.balance);
        st.setString(5, entity.expiryDate);
        int res = st.executeUpdate();
        clientDAO.updateClientsCardsQuantity(entity.clientID, 1);
        DAO.closing(st, conn);
        return res;
    }

    @Override
    public CreditCard retrieve(int id) throws SQLException {
        CreditCard card = new CreditCard();
        st = conn.prepareStatement("SELECT * FROM payments_project.credit_cards WHERE id = ?;");
        st.setLong(1, id);
        rs = st.executeQuery();
        while (rs.next()) {
            card.id = id;
            card.cardNumber = rs.getLong("number");
            card.clientID = rs.getInt("client_id");
            card.creditLimit = rs.getInt("limit");
            card.balance = rs.getInt("balance");
            card.expiryDate = rs.getString("expiry_date");
        }
        DAO.closing(rs, st, conn);
        return card;
    }

    @Override
    public List<CreditCard> retrieveAll() throws SQLException {
        List<CreditCard> cards = new ArrayList<>();
        st = conn.prepareStatement("SELECT * FROM payments_project.credit_cards;");
        rs = st.executeQuery();
        int id;
        long number;
        int clientId;
        int limit;
        int balance;
        String expiryDate;
        while (rs.next()) {
            id = rs.getInt("id");
            number = rs.getLong("number");
            clientId = rs.getInt("client_id");
            limit = rs.getInt("limit");
            balance = rs.getInt("balance");
            expiryDate = rs.getString("expiry_date");
            cards.add(new CreditCard(id, number, clientId, limit, balance, expiryDate));
        }
        DAO.closing(rs, st, conn);
        return cards;
    }

    @Override
    public CreditCard retrieveCardByNumber(long number) throws SQLException {
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
        DAO.closing(rs, st, conn);
        return new CreditCard(number, clientId, limit, balance, expiryDate);
    }

    @Override
    public List<CreditCard> retrieveCardsByExpiryDate(String expiryDate) throws SQLException {
        List<CreditCard> cards = new ArrayList<>();
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
        DAO.closing(rs, st, conn);
        return cards;
    }

    @Override
    public int retrieveCardBalanceByNumber(long number) throws SQLException {
        int balance = 0;
        st = conn.prepareStatement("SELECT balance FROM payments_project.credit_cards WHERE number = ?;");
        st.setLong(1, number);
        rs = st.executeQuery();
        while (rs.next()) {
            balance = rs.getInt("balance");
        }
        DAO.closing(rs, st, conn);
        return balance;
    }

    @Override
    public int update(CreditCard entity) throws SQLException {
        st = conn.prepareStatement("UPDATE `payments_project`.`credit_cards` SET " +
                "`id` = ?, `number` = ?, `client_id` = ?, `limit` = ?, `balance` = ?, `expiry_date` = ? WHERE `id` = ?;");
        st.setInt(1, entity.id);
        st.setLong(2, entity.cardNumber);
        st.setInt(3, entity.clientID);
        st.setInt(4, entity.creditLimit);
        st.setInt(5, entity.balance);
        st.setString(6, entity.expiryDate);
        st.setInt(7, entity.id);
        int res = st.executeUpdate();
        DAO.closing(st, conn);
        return res;
    }

    @Override
    public int updateCardBalanceByNumber(long number, int balanceAfterChanging) throws SQLException {
        st = conn.prepareStatement("UPDATE `payments_project`.`credit_cards` SET `balance` = ? WHERE `number` = ?;");
        st.setInt(1, balanceAfterChanging);
        st.setLong(2, number);
        int res = st.executeUpdate();
        DAO.closing(st, conn);
        return res;
    }

    @Override
    public int updateCreditLimitByNumber(long number, int newLimit) throws SQLException {
        st = conn.prepareStatement("UPDATE `payments_project`.`credit_cards` SET `limit` = ? WHERE `number` = ?;");
        st.setInt(1, newLimit);
        st.setLong(2, number);
        int res = st.executeUpdate();
        DAO.closing(st, conn);
        return res;
    }

    @Override
    public int delete(int id) throws SQLException, ClassNotFoundException, LoginToMySQLException {
        st = conn.prepareStatement("DELETE FROM payments_project.credit_cards WHERE id = ?;");
        st.setInt(1, id);
        int res = st.executeUpdate();
        ClientDAO clientDAO = new ClientDAOImpl();
        CreditCardDAO cardDAO = new CreditCardDAOImpl();
        clientDAO.updateClientsCardsQuantity(cardDAO.retrieve(id).clientID, -1);
        DAO.closing(st, conn);
        return res;
    }

    @Override
    public int deleteCardByNumber(long number) throws SQLException {
        st = conn.prepareStatement("DELETE FROM payments_project.credit_cards WHERE number = ?;");
        st.setLong(1, number);
        int res = st.executeUpdate();
        DAO.closing(st, conn);
        return res;
    }

    @Override
    public int cardDeletionWithCheckingByNumber(long number)
            throws SQLException, ClassNotFoundException, LoginToMySQLException {
        CreditCardDAO cardDAO = new CreditCardDAOImpl();
        CreditCard card = cardDAO.retrieveCardByNumber(number);
        if (card.creditLimit > card.balance) {
            return -1;
        } else {
            if (this.deleteCardByNumber(number) > 0) {
                return card.balance - card.creditLimit;
            } else {
                throw new SQLException("Something wrong with deletion of the card [number] -> " + number);
            }
        }
    }
}