package ru.hse.dz;

import java.sql.*;
import java.util.Optional;
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
            try {
                Optional<ResultSet> resultSet = DataBase.makeQuery(query);
                if (resultSet.isPresent()) {
                    printResultQuery(resultSet.get(), resultSet.get().getMetaData());
                } else {
                    System.out.println("Запрос выполнен успешно");
                }
            } catch (SQLException ignored) {
                System.out.println("В процессе выполнения запроса произошла ошибка");
            }

        } while (true);
        try {
            DataBase.closeDataBaseConnections();
        } catch (SQLException ignored) {
            System.out.println("В процессе закрытия соединений произошла ошибка!");
        }
    }

    /**
     * Вывод информации по запросу, отдающему результат
     *
     * @param resSet результат запроса
     * @param data   данные, полученные после запроса
     * @throws SQLException Когда JDBC обнаруживает ошибку во время взаимодействия с источником данных
     */
    public static void printResultQuery(ResultSet resSet, ResultSetMetaData data) throws SQLException {
        for (int i = 1; i <= data.getColumnCount(); i++) {
            System.out.print(data.getColumnLabel(i) + "    ");
        }
        System.out.println();
        while (resSet.next()) {
            for (int i = 1; i <= data.getColumnCount(); i++) {
                System.out.print(resSet.getString(i) + "    ");
            }
            System.out.println();
        }
        resSet.close();
    }
}
