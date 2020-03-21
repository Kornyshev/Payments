package dao.interfaces;

import services.entities.Client;
import services.entities.CreditCard;

import java.sql.SQLException;
import java.util.List;

public interface ClientDAO {
    int insertClient(Client client) throws SQLException, ClassNotFoundException;
    Client retrieveClientByID(int id) throws SQLException, ClassNotFoundException;
    Client retrieveClientByName(String name) throws SQLException, ClassNotFoundException;
    List<Client> retrieveAllClients() throws SQLException, ClassNotFoundException;
    List<CreditCard> retrieveCardsBelongingClient(String name);
    int updateClient(int cardsAmount);
    int deleteClientByID(int id);
    int deleteClientByName(String name);
}
