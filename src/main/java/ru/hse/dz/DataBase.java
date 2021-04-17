package ru.hse.dz;

import java.sql.*;
import java.util.Optional;


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
     * Выполнение запроса и возвращение результата запроса
     *
     * @param query строка запроса
     */
    public static Optional<ResultSet> makeQuery(String query) throws SQLException {
        Optional<ResultSet> resSet;
        statement.execute(query);
        resSet = Optional.ofNullable(statement.getResultSet());
        return resSet;
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