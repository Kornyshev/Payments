package utils;

import services.entities.Client;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FillingAllTablesWithValues {
    public static void main(String[] args){

    }

    private static Set<Client> generateRandomClients(long n) {
        Set<Client> clients = new HashSet<>();
        List<String> names = new ArrayList<>();
        File file = new File("C:\\Projects\\PaymentsHomeWork\\Payments\\src\\main\\resources\\names.txt");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            reader.lines().forEach(names::add);
        } catch (FileNotFoundException e) {
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
