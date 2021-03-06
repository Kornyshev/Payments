package dao.impl;

import dao.interfaces.ClientDAO;
import dao.interfaces.DAO;
import entities.Client;
import entities.Card;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class ClientDAOImpl implements ClientDAO {

    private Connection conn;
    private PreparedStatement st;
    private ResultSet rs;

    private static final Logger logger = Logger.getLogger(ClientDAOImpl.class);

    public ClientDAOImpl() throws SQLException, ClassNotFoundException, LoginToMySQLException {
        conn = new MySQLConnectionFactory().createConnection();
        logger.info("MySQL connection successfully created");
    }

    @Override
    public int insert(Client entity) throws SQLException {
        st = conn.prepareStatement("INSERT INTO `payments_project`.`clients`(`name`,`birthday`,`cards_quantity`)VALUES(?,?,?);");
        st.setString(1, entity.name);
        st.setDate(2, Date.valueOf(entity.birthDay));
        st.setInt(3, entity.cardsQuantity);
        int res = st.executeUpdate();
        DAO.closing(st, conn);
        logger.info("Client with the id = " + entity.id + " successfully inserted");
        return res;
    }

    @Override
    public Client retrieve(int id) throws SQLException {
        Client client = null;
        st = conn.prepareStatement("SELECT * FROM payments_project.clients WHERE id = ?;");
        st.setInt(1, id);
        rs = st.executeQuery();
        while (rs.next()) {
            client = new Client();
            client.id = rs.getInt("id");
            client.name = rs.getString("name");
            client.birthDay = rs.getString("birthday");
            client.cardsQuantity = rs.getInt("cards_quantity");
        }
        DAO.closing(rs, st, conn);
        if (client == null) {
            logger.error("Something wrong with retrieving client by id - " + id);
        } else {
            logger.info("Client with the id = " + id + " successfully retrieved");
        }
        return client;
    }

    @Override
    public List<Client> retrieveAll() throws SQLException {
        List<Client> clients = new ArrayList<>();
        st = conn.prepareStatement("SELECT * FROM payments_project.clients;");
        rs = st.executeQuery();
        int id;
        String name;
        String birthDay;
        int cardsQuantity;
        while (rs.next()) {
            id = rs.getInt("id");
            name = rs.getString("name");
            birthDay = rs.getString("birthday");
            cardsQuantity = rs.getInt("cards_quantity");
            clients.add(new Client(id, name, birthDay, cardsQuantity));
        }
        DAO.closing(rs, st, conn);
        logger.info("List of all clients was retrieved, size = " + clients.size());
        return clients;
    }

    @Override
    public Client retrieveClientByName(String name) throws SQLException {
        Client client = null;
        st = conn.prepareStatement("SELECT * FROM payments_project.clients WHERE name = ?;");
        st.setString(1, name);
        rs = st.executeQuery();
        while (rs.next()) {
            client = new Client();
            client.id = rs.getInt("id");
            client.name = name;
            client.birthDay = rs.getString("birthday");
            client.cardsQuantity = rs.getInt("cards_quantity");
        }
        DAO.closing(rs, st, conn);
        if (client == null) {
            logger.error("Something wrong with retrieving client by name - " + name);
        } else {
            logger.info("Client with the name = " + name + " successfully retrieved");
        }
        return client;
    }

    @Override
    public List<Card> retrieveClientsCardsByName(String name) throws SQLException {
        List<Card> cards = new ArrayList<>();
        st = conn.prepareStatement("SELECT * FROM payments_project.credit_cards WHERE client_id = " +
                "(SELECT id FROM payments_project.clients WHERE name = ?);");
        st.setString(1, name);
        rs = st.executeQuery();
        while (rs.next()) {
            long number = rs.getLong("number");
            int id = rs.getInt("client_id");
            int limit = rs.getInt("limit");
            int balance = rs.getInt("balance");
            String expiryDate = rs.getString("expiry_date");
            cards.add(new Card(number, id, limit, balance, expiryDate));
        }
        DAO.closing(rs, st, conn);
        logger.info("List of client's credit cards was retrieved by name - " + name + ", " +
                "list's size = " + cards.size());
        return cards;
    }

    @Override
    public List<Card> retrieveClientsCardsByID(int id) throws SQLException {
        List<Card> cards = new ArrayList<>();
        st = conn.prepareStatement("SELECT * FROM payments_project.credit_cards WHERE client_id = ?;");
        st.setInt(1, id);
        rs = st.executeQuery();
        while (rs.next()) {
            long number = rs.getLong("number");
            int limit = rs.getInt("limit");
            int balance = rs.getInt("balance");
            String expiryDate = rs.getString("expiry_date");
            cards.add(new Card(number, id, limit, balance, expiryDate));
        }
        DAO.closing(rs, st, conn);
        logger.info("List of client's credit cards was retrieved by the id - " + id + ", " +
                "list's size = " + cards.size());
        return cards;
    }

    @Override
    public int update(Client entity) throws SQLException {
        st = conn.prepareStatement("UPDATE `payments_project`.`clients` " +
                "SET `id` = ?, `name` = ?, `birthday` = ?, `cards_quantity` = ? WHERE `id` = ?;");
        st.setInt(1, entity.id);
        st.setString(2, entity.name);
        st.setDate(3, Date.valueOf(entity.birthDay));
        st.setInt(4, entity.cardsQuantity);
        st.setInt(5, entity.id);
        int res = st.executeUpdate();
        DAO.closing(st, conn);
        if (res == 0) {
            logger.error("Something wrong with updating client - " + entity);
        } else {
            logger.info("Client with the id = " + entity.id + " successfully updated");
        }
        return res;
    }

    @Override
    public int updateClientsCardsQuantity(int clientID, int changing) throws SQLException {
        st = conn.prepareStatement("SELECT cards_quantity FROM payments_project.clients WHERE id = ?;");
        st.setInt(1, clientID);
        rs = st.executeQuery();
        int quantity = 0;
        while (rs.next()) {
            quantity = rs.getInt("cards_quantity") + changing;
        }
        st = conn.prepareStatement("UPDATE `payments_project`.`clients` SET `cards_quantity` = ? WHERE `id` = ?;");
        st.setInt(1, quantity);
        st.setInt(2, clientID);
        int res = st.executeUpdate();
        DAO.closing(rs, st, conn);
        if (res == 0) {
            logger.error("Something wrong with updating client's card, client ID - " + clientID);
        } else {
            logger.info("Client's cards quantity with the id = " + clientID + " successfully updated");
        }
        return res;
    }

    @Override
    public int delete(int id) throws SQLException {
        st = conn.prepareStatement("DELETE FROM payments_project.clients WHERE id = ?;");
        st.setInt(1, id);
        int res = st.executeUpdate();
        DAO.closing(st, conn);
        if (res == 0) {
            logger.error("Something wrong with deletion client by id - " + id);
        } else {
            logger.info("Client with the id = " + id + " successfully deleted (force method)");
        }
        return res;
    }

    @Override
    public int deleteClientByName(String name) throws SQLException {
        st = conn.prepareStatement("DELETE FROM payments_project.clients WHERE name = ?;");
        st.setString(1, name);
        int res = st.executeUpdate();
        DAO.closing(st, conn);
        if (res == 0) {
            logger.error("Something wrong with deletion client by name - " + name);
        } else {
            logger.info("Client with the name = " + name + " successfully deleted (force method)");;
        }
        return res;
    }

    @Override
    public int clientDeletionWithCheckingByID(int id)
            throws SQLException, ClassNotFoundException, LoginToMySQLException {
        ClientDAO clientDAO = new ClientDAOImpl();
        List<Card> cards = clientDAO.retrieveClientsCardsByID(id);
        int balanceSum = cards.stream().mapToInt(card -> card.balance).sum();
        int limitSum = cards.stream().mapToInt(card -> card.creditLimit).sum();
        if (limitSum > balanceSum) {
            return -1;
        } else {
            if (this.delete(id) > 0) {
                logger.info("Client with the id = " + id + " successfully deleted (soft method)");
                return balanceSum - limitSum;
            } else {
                logger.error("Something wrong with the client [id] -> " + id);
                throw new SQLException("Something wrong with the client [id] -> " + id);
            }
        }

    }

    @Override
    public int clientDeletionWithCheckingByName(String name)
            throws SQLException, ClassNotFoundException, LoginToMySQLException {
        ClientDAO clientDAO = new ClientDAOImpl();
        List<Card> cards = clientDAO.retrieveClientsCardsByName(name);
        int balanceSum = cards.stream().mapToInt(card -> card.balance).sum();
        int limitSum = cards.stream().mapToInt(card -> card.creditLimit).sum();
        if (limitSum > balanceSum) {
            return -1;
        } else {
            if (this.deleteClientByName(name) > 0) {
                logger.info("Client with the name = " + name + " successfully deleted (soft method)");
                return balanceSum - limitSum;
            } else {
                logger.error("Something wrong with deletion of the client [name] -> " + name);
                throw new SQLException("Something wrong with deletion of the client [name] -> " + name);
            }
        }
    }
}