package dao.impl;

import dao.interfaces.ClientDAO;
import dao.interfaces.CreditCardDAO;
import dao.interfaces.DAO;
import entities.CreditCard;
import org.apache.log4j.Logger;

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

    private final static Logger logger = Logger.getLogger(CreditCardDAOImpl.class);

    public CreditCardDAOImpl() throws SQLException, ClassNotFoundException, LoginToMySQLException {
        conn = new MySQLConnectionFactory().createConnection();
        logger.info("MySQL connection successfully created");
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
        logger.info("Credit card with the id = " + entity.id + " successfully inserted");
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
        logger.info("Credit card with the id = " + id + " successfully retrieved");
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
        logger.info("List of all credit cards was retrieved");
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
        logger.info("Credit card with the number = " + number + " successfully retrieved");
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
        logger.info("List of credit cards with expiry date = " + expiryDate + " retrieved");
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
        logger.info("Balance of credit card with number = " + number + " retrieved");
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
        logger.info("Credit card with the id = " + entity.id + " successfully updated");
        return res;
    }

    @Override
    public int updateCardBalanceByNumber(long number, int balanceAfterChanging) throws SQLException {
        st = conn.prepareStatement("UPDATE `payments_project`.`credit_cards` SET `balance` = ? WHERE `number` = ?;");
        st.setInt(1, balanceAfterChanging);
        st.setLong(2, number);
        int res = st.executeUpdate();
        DAO.closing(st, conn);
        logger.info("Balance of credit card with the number = " + number + " successfully updated");
        return res;
    }

    @Override
    public int updateCreditLimitByNumber(long number, int newLimit) throws SQLException {
        st = conn.prepareStatement("UPDATE `payments_project`.`credit_cards` SET `limit` = ? WHERE `number` = ?;");
        st.setInt(1, newLimit);
        st.setLong(2, number);
        int res = st.executeUpdate();
        DAO.closing(st, conn);
        logger.info("Credit limit of credit card with the number = " + number + " successfully updated");
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
        logger.info("Credit card with the id = " + id + " successfully deleted (force method)");
        return res;
    }

    @Override
    public int deleteCardByNumber(long number) throws SQLException, LoginToMySQLException, ClassNotFoundException {
        st = conn.prepareStatement("DELETE FROM payments_project.credit_cards WHERE number = ?;");
        st.setLong(1, number);
        int res = st.executeUpdate();
        ClientDAO clientDAO = new ClientDAOImpl();
        CreditCardDAO cardDAO = new CreditCardDAOImpl();
        clientDAO.updateClientsCardsQuantity
                (cardDAO.retrieveCardByNumber(number).clientID, -1);
        DAO.closing(st, conn);
        logger.info("Credit card with the number = " + number + " successfully deleted (force method)");
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
                logger.info("Credit card with the number = " + number + " successfully deleted (soft method)");
                return card.balance - card.creditLimit;
            } else {
                logger.error("Something wrong with the credit card [number] -> " + number);
                throw new SQLException("Something wrong with deletion of the card [number] -> " + number);
            }
        }
    }
}