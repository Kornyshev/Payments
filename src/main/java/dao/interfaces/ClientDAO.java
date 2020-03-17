package dao.interfaces;

import services.entities.Client;
import services.entities.CreditCard;

import java.util.List;

public interface ClientDAO {
    int insertClient(Client client);
    Client retrieveClientByID(int id);
    Client retrieveClientByName(String name);
    List<CreditCard> retrieveCardsBelongingClient(String name);
    int updateClient(int cardsAmount);
    int deleteClientByID(int id);
    int deleteClientByName(String name);
}
