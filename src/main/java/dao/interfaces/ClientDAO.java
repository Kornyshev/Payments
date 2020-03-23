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
    List<CreditCard> retrieveClientsCardsByName(String name) throws SQLException, ClassNotFoundException;
    List<CreditCard> retrieveClientsCardsByID(int id) throws SQLException, ClassNotFoundException;
    int updateClientsCardsQuantity(int clientID, int changing) throws SQLException, ClassNotFoundException;
    int forceDeleteClientByID(int id) throws SQLException, ClassNotFoundException;
    int forceDeleteClientByName(String name) throws SQLException, ClassNotFoundException;
    int clientDeletionWithCheckingByID(int id) throws SQLException, ClassNotFoundException;
    int clientDeletionWithCheckingByName(String name) throws SQLException, ClassNotFoundException;
}