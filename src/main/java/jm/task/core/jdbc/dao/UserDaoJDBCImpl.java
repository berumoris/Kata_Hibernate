package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Connection connection;
    private final String createTable = "CREATE TABLE IF NOT EXISTS users (id BIGINT AUTO_INCREMENT, name CHAR(60), lastName CHAR(80), age TINYINT, primary key (id));";
    private final String saveUser = "INSERT INTO users(name, lastname, age) values (?, ?, ?);";
    private final String dropTable = "DROP TABLE IF EXISTS users";
    private final String removeUserById = "DELETE FROM users WHERE id = ?";
    private final String getAllUsers = "SELECT * FROM users";
    private final String cleanUsersTable = "TRUNCATE users";

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        connection = Util.getConnection();
        try (PreparedStatement pst = connection.prepareStatement(createTable)) {
            pst.execute();
        } catch (SQLException throwables) {
            System.out.println("Не удалось создать новую таблицу");
        }
    }

    public void dropUsersTable() {
        connection = Util.getConnection();
        try(Statement statement = connection.createStatement()) {
            statement.execute(dropTable);
        } catch (SQLException throwables) {
            System.out.println("Не удалось удалить таблицу");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        connection = Util.getConnection();
        try (PreparedStatement pst = connection.prepareStatement(saveUser)) {
            pst.setString(1, name);
            pst.setString(2, lastName);
            pst.setByte(3, age);
            pst.executeUpdate();
        } catch (SQLException throwables) {
            System.out.println("Не удалось добавить пользователя в таблицу");
        }
    }

    public void removeUserById(long id) {
        connection = Util.getConnection();
        try (PreparedStatement pst = connection.prepareStatement(removeUserById)) {
            pst.setLong(1, id);
            pst.executeUpdate();
        } catch (SQLException throwables) {
            System.out.println("Не удалось удалить пользователя по id");
        }
    }

    public List<User> getAllUsers() {
        connection = Util.getConnection();
        List<User> userList = new ArrayList<>();
        try(Statement statement = connection.createStatement()) {
            ResultSet result = statement.executeQuery(getAllUsers);
            while (result.next()) {
                User user = new User();
                user.setId(result.getLong("id"));
                user.setName(result.getString("name"));
                user.setLastName(result.getString("lastName"));
                user.setAge(result.getByte("age"));
                userList.add(user);
            }
        } catch (SQLException throwables) {
            System.out.println("Не удалось получить список пользователей");
        }
        return userList;
    }

    public void cleanUsersTable() {
        connection = Util.getConnection();
        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate(cleanUsersTable);
        } catch (SQLException throwables) {
            System.out.println("Не удалось очистить таблицу");
        }
    }
}
