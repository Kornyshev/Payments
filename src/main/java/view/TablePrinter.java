package view;

import entities.Entity;

import java.util.List;

public class TablePrinter<T extends Entity> {
    public void print(List<T> list) {
        String header = list.get(0).headerForTable();
        System.out.println(header);
        for (int i = 0; i < header.length(); i++) {
            System.out.print("-");
        }
        System.out.println();
        list.forEach(el -> System.out.println(el.toFormattedString()));
        System.out.println();
    }
}