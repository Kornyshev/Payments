package dao.impl;

import dao.interfaces.ClientDAO;
import services.entities.Client;
import services.entities.CreditCard;

import java.sql.*;
import java.util.List;

public class ClientDaoImpl implements ClientDAO {

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
        st = conn.prepareStatement("INSERT INTO payments_project.clients(name, birthday, cards_quantity) values(?, ?, ?);");
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
        if (conn == null) return null;
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
        if (conn == null) return null;
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

    public List<CreditCard> retrieveCardsBelongingClient(String name) {
        return null;
    }

    public int updateClient(int cardsAmount) {
        return 0;
    }

    public int deleteClientByID(int id) {
        return 0;
    }

    public int deleteClientByName(String name) {
        return 0;
    }
}
