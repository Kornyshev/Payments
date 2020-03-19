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

    public Client retrieveClientByID(int id) {
        return null;
    }

    public Client retrieveClientByName(String name) {
        return null;
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
