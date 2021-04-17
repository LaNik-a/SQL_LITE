package ru.hse.dz;

import java.sql.*;


public class DataBase {
    private static Connection connection;
    private static Statement statement;

    /**
     * Подключение к базе данных
     *
     * @throws ClassNotFoundException Выбрасывается, когда приложение пытается загрузить класс через его строковое имя
     * @throws SQLException           Когда JDBC обнаруживает ошибку во время взаимодействия с источником данных
     */
    public static void connectToDataBase() throws ClassNotFoundException, SQLException {
        connection = null;
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/users.db");
        System.out.println("База Подключена!");
        statement = connection.createStatement();
    }

    /**
     * Выполнение запроса
     *
     * @param query строка запроса
     */
    public static void makeQuery(String query) {
        try {
            if (statement.execute(query)) {
                ResultSet resSet = statement.getResultSet();
                ResultSetMetaData data = resSet.getMetaData();
                printResultQuery(resSet, data);
            } else {
                System.out.println("Запрос выполнен успешно");
            }
        } catch (SQLException ex) {
            System.out.println("Ошибка в запросе");
        }

    }

    /**
     * Вывод информации по запросу, отдающему результат
     *
     * @param resSet результат запроса
     * @param data   данные, полученные после запроса
     * @throws SQLException Когда JDBC обнаруживает ошибку во время взаимодействия с источником данных
     */
    private static void printResultQuery(ResultSet resSet, ResultSetMetaData data) throws SQLException {
        for (int i = 1; i <= data.getColumnCount(); i++) {
            System.out.print(data.getColumnLabel(i) + "    ");
        }
        System.out.println();
        while (resSet.next()) {
            for (int i = 1; i <= data.getColumnCount(); i++) {
                System.out.println(resSet.getString(i) + "    ");
            }
            System.out.println();
        }
        resSet.close();
    }

    /**
     * Закрытие подключений к базе sql lite
     *
     * @throws SQLException Когда JDBC обнаруживает ошибку во время взаимодействия с источником данных
     */
    public static void closeDataBaseConnections() throws SQLException {
        connection.close();
        statement.close();
        System.out.println("Соединения успешно закрыты");

    }

}