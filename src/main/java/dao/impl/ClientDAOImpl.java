package dao.impl;

import dao.interfaces.ClientDAO;
import services.entities.Client;
import services.entities.CreditCard;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of ClientDAO interface.
 * It completed on 23.03.2020.
 */
public class ClientDAOImpl implements ClientDAO {

    private Connection conn;
    private PreparedStatement st;
    private ResultSet rs;

    /**
     * Simple insertion one client into Database using PreparedStatement.
     * @param client
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public int insertClient(Client client) throws SQLException, ClassNotFoundException {
        conn = new MySQLConnectionFactory().createConnection();
        st = conn.prepareStatement("INSERT INTO `payments_project`.`clients`(`id`,`name`,`birthday`,`cards_quantity`)VALUES(?,?,?);");
        st.setString(1, client.name);
        st.setDate(2, Date.valueOf(client.birthDay));
        st.setInt(3, client.cardsQuantity);
        int res = st.executeUpdate();
        st.close();
        conn.close();
        return res;
    }

    /**
     * This method gets a client from DB by ID
     * @param id
     * @return
     */
    public Client retrieveClientByID(int id) throws SQLException, ClassNotFoundException {
        Client client = new Client();
        conn = new MySQLConnectionFactory().createConnection();
        st = conn.prepareStatement("SELECT * FROM payments_project.clients WHERE id = ?;");
        st.setInt(1, id);
        rs = st.executeQuery();
        while (rs.next()) {
            client.id = rs.getInt("id");
            client.name = rs.getString("name");
            client.birthDay = rs.getString("birthday");
            client.cardsQuantity = rs.getInt("cards_quantity");
        }
        rs.close();
        st.close();
        conn.close();
        return client;
    }

    /**
     * This method gets a client from DB by name
     * @param name
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public Client retrieveClientByName(String name) throws SQLException, ClassNotFoundException {
        Client client = new Client();
        conn = new MySQLConnectionFactory().createConnection();
        st = conn.prepareStatement("SELECT * FROM payments_project.clients WHERE name = ?;");
        st.setString(1, name);
        rs = st.executeQuery();
        while (rs.next()) {
            client.id = rs.getInt("id");
            client.name = name;
            client.birthDay = rs.getString("birthday");
            client.cardsQuantity = rs.getInt("cards_quantity");
        }
        rs.close();
        st.close();
        conn.close();
        return client;
    }


    /**
     * This method just returns list of all clients
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public List<Client> retrieveAllClients() throws SQLException, ClassNotFoundException {
        List<Client> clients = new ArrayList<>();
        conn = new MySQLConnectionFactory().createConnection();
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
        rs.close();
        st.close();
        conn.close();
        return clients;
    }

    /**
     * This method returns all client's cards by his name
     * @param name
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public List<CreditCard> retrieveClientsCardsByName(String name) throws SQLException, ClassNotFoundException {
        List<CreditCard> cards = new ArrayList<>();
        conn = new MySQLConnectionFactory().createConnection();
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
            cards.add(new CreditCard(number, id, limit, balance, expiryDate));
        }
        rs.close();
        st.close();
        conn.close();
        return cards;
    }


    /**
     * This method returns all client's cards by his ID
     * @param id
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @Override
    public List<CreditCard> retrieveClientsCardsByID(int id) throws SQLException, ClassNotFoundException {
        List<CreditCard> cards = new ArrayList<>();
        conn = new MySQLConnectionFactory().createConnection();
        st = conn.prepareStatement("SELECT * FROM payments_project.credit_cards WHERE client_id = ?;");
        st.setInt(1, id);
        rs = st.executeQuery();
        while (rs.next()) {
            long number = rs.getLong("number");
            int limit = rs.getInt("limit");
            int balance = rs.getInt("balance");
            String expiryDate = rs.getString("expiry_date");
            cards.add(new CreditCard(number, id, limit, balance, expiryDate));
        }
        rs.close();
        st.close();
        conn.close();
        return cards;
    }

    /**
     * Updating client's cards quantity. It could be +1 or -1.
     * Depends on the parameter Changing.
     * @param clientID
     * @param changing
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public int updateClientsCardsQuantity(int clientID, int changing) throws SQLException, ClassNotFoundException {
        conn = new MySQLConnectionFactory().createConnection();
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
        rs.close();
        st.close();
        conn.close();
        return res;
    }

    /**
     * This method to force deletion of client by ID, without checking balances on his cards.
     * @param id
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public int forceDeleteClientByID(int id) throws SQLException, ClassNotFoundException {
        conn = new MySQLConnectionFactory().createConnection();
        st = conn.prepareStatement("DELETE FROM payments_project.clients WHERE id = ?;");
        st.setInt(1, id);
        int res = st.executeUpdate();
        st.close();
        conn.close();
        return res;
    }

    /**
     * This method to force deletion of client by name, without checking balances on his cards.
     * @param name
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public int forceDeleteClientByName(String name) throws SQLException, ClassNotFoundException {
        conn = new MySQLConnectionFactory().createConnection();
        st = conn.prepareStatement("DELETE FROM payments_project.clients WHERE name = ?;");
        st.setString(1, name);
        int res = st.executeUpdate();
        st.close();
        conn.close();
        return res;
    }

    /**
     * This method delete client by ID quite more gently,
     * with checking his balances on cards.
     * And returns amount to receiving in cash box,
     * if it's exist. If not - method returns -1.
     * @param id
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @Override
    public int clientDeletionWithCheckingByID(int id) throws SQLException, ClassNotFoundException {
        ClientDAO clientDAO = new ClientDAOImpl();
        List<CreditCard> cards = clientDAO.retrieveClientsCardsByID(id);
        int balanceSum = cards.stream().mapToInt(card -> card.balance).sum();
        int limitSum = cards.stream().mapToInt(card -> card.creditLimit).sum();
        if (limitSum > balanceSum) {
            return -1;
        } else {
            if (clientDAO.forceDeleteClientByID(id) > 0) {
                return balanceSum - limitSum;
            } else {
                throw new SQLException("Something wrong with the client [id] -> " + id);
            }
        }
    }

    /**
     * This method delete client by Name quite more gently,
     * with checking his balances on cards.
     * And returns amount to receiving in cash box,
     * if it's exist. If not - method returns -1.
     * @param name
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @Override
    public int clientDeletionWithCheckingByName(String name) throws SQLException, ClassNotFoundException {
        ClientDAO clientDAO = new ClientDAOImpl();
        List<CreditCard> cards = clientDAO.retrieveClientsCardsByName(name);
        int balanceSum = cards.stream().mapToInt(card -> card.balance).sum();
        int limitSum = cards.stream().mapToInt(card -> card.creditLimit).sum();
        if (limitSum > balanceSum) {
            return -1;
        } else {
            if (clientDAO.forceDeleteClientByName(name) > 0) {
                return balanceSum - limitSum;
            } else {
                throw new SQLException("Something wrong with deletion of the client [name] -> " + name);
            }
        }
    }
}