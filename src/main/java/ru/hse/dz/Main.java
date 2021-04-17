package ru.hse.dz;

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            DataBase.connectToDataBase();
        } catch (ClassNotFoundException | SQLException ignored) {
            System.out.println("Не удалось подключиться к базе данных!");
        }
        Scanner sc = new Scanner(System.in);
        System.out.println("Для работы с программой вводите запросы к базе данных\nДля выхода введите запрос \"EXIT\"");
        do {
            String query = sc.nextLine();
            if ("EXIT".equals(query)) {
                break;
            }
            DataBase.makeQuery(query);
        } while (true);
        try {
            DataBase.closeDataBaseConnections();
        } catch (SQLException ignored) {
            System.out.println("В процессе закрытия соединений произошла ошибка!");
        }
    }
}
