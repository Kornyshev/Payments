package dao.impl;

import dao.interfaces.ClientDAO;
import services.entities.Client;
import services.entities.CreditCard;

import java.util.List;

public class ClientDaoImpl implements ClientDAO {
    public int insertClient(Client client) {
        return 0;
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
