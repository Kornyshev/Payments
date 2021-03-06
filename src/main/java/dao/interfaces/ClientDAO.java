package dao.interfaces;

import dao.impl.LoginToMySQLException;
import entities.Client;
import entities.Card;

import java.sql.SQLException;
import java.util.List;

public interface ClientDAO extends DAO<Client> {
    Client retrieveClientByName(String name) throws SQLException;
    List<Card> retrieveClientsCardsByName(String name) throws SQLException;
    List<Card> retrieveClientsCardsByID(int id) throws SQLException;
    int updateClientsCardsQuantity(int clientID, int changing) throws SQLException;
    int deleteClientByName(String name) throws SQLException;
    int clientDeletionWithCheckingByID(int id) throws SQLException, ClassNotFoundException, LoginToMySQLException;
    int clientDeletionWithCheckingByName(String name) throws SQLException, ClassNotFoundException, LoginToMySQLException;
}