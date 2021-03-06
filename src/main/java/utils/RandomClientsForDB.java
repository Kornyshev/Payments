package utils;

import dao.impl.ClientDAOImpl;
import dao.impl.LoginToMySQLException;
import dao.interfaces.ClientDAO;
import entities.Client;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RandomClientsForDB {

    public static void main(String[] args) throws SQLException, LoginToMySQLException, ClassNotFoundException {
        /*
        Method for action
         */
        insertionClientsSetIntoDB(generateRandomClients(900));
    }

    /**
     * This method inserts set of clients into Database using DAO interface.
     * As parameter, it takes Set<> which is generated by specific method.
     * @param clients
     */
    private static void insertionClientsSetIntoDB(Set<Client> clients)
            throws SQLException, LoginToMySQLException, ClassNotFoundException {
        ClientDAO clientDAO = new ClientDAOImpl();
        clients.forEach(client -> {
            try {
                clientDAO.insert(client);
            } catch (SQLException | ClassNotFoundException | LoginToMySQLException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * This method generates N-size Set of random clients.
     * It takes names from the prepared file and birthdays
     * are generate by another method.
     * @param n
     * @return
     */
    private static Set<Client> generateRandomClients(long n) {
        Set<Client> clients = new HashSet<>();
        List<String> names = new ArrayList<>();
        File file = new File("C:\\Projects\\PaymentsHomeWork\\Payments\\src\\main\\resources\\names.txt");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            reader.lines().forEach(names::add);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int tmp;
        while (!names.isEmpty()) {
            tmp = (int) (Math.random() * names.size());
            clients.add(new Client(names.get(tmp), randomBirthday()));
            names.remove(tmp);
        }
        return clients
                .stream()
                .limit(n)
                .collect(Collectors.toSet());
    }

    /**
     * This method just generates a random birthday using Math.random.
     * @return
     */
    private static String randomBirthday(){
        StringBuilder res = new StringBuilder();
        int year = 2001 - (int)(Math.random() * 60);
        int month = (int)(Math.random() * 12) + 1;
        int day = (int)(Math.random() * 28) + 1;
        res.append(year).append("-");
        if (month > 9) {
            res.append(month);
        } else {
            res.append(0).append(month);
        }
        res.append("-");
        if (day > 9) {
            res.append(day);
        } else {
            res.append(0).append(day);
        }
        return res.toString();
    }
}