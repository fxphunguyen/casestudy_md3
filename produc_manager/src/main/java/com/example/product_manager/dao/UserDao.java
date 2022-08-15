package com.example.product_manager.dao;

import com.example.product_manager.model.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.example.product_manager.utils.MySQLConnUtils.getConnection;

public class UserDao implements IUserDao {

    private static final String INSERT_USERS_SQL =
            "INSERT INTO users " +
                    " ( `name` , " +
                    "phone , " +
                    "email ," +
                    " idCountry , " +
                    " password ," +
                    " urlImg ," +
                    "createAt  ," +
                    "updateAt )" +
                    " VALUES ( ? , ? , ?, ? , ? , ? , ? , ? );";
    private static final String SELECT_USER_BY_ID = "SELECT" +
            " id , " +
            "`name` , " +
            " email , " +
            " phone , " +
            "idCountry , " +
            " password, " +
            "urlImg , " +
            "createAt , " +
            "updateAt " +
            "FROM users WHERE id =?";


    private static final String SELECT_ALL_USERS = "SELECT " +
            " id , `name` ,phone,  email, idCountry, password,urlImg,createAt, updateAt " +
            " FROM users";

    private static final String DELETE_USERS_SQL = "DELETE FROM " +
            "users " +
            "WHERE id = ?;";

    private static final String UPDATE_USERS_SQL = "UPDATE" +
            " users set" +
            " `name` = ?," +
            "phone = ? ," +
            "email= ?, " +
            "idCountry = ? ," +
            "password = ? ," +
            "urlImg = ? ," +
            "updateAt = ? " +
            "WHERE id = ?;";

    public boolean updateUser(User user) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(UPDATE_USERS_SQL);) {

            statement.setString(1, user.username());
            statement.setString(2, user.phone());
            statement.setString(3, user.email());
            statement.setInt(4, user.countryId());
            statement.setString(5, user.password());
            statement.setString(6, user.imageUrl());
            statement.setString(7, user.updateAt().toString());
            statement.setInt(8, user.id());
            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    public User selectUserById(int id) {
        User user = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID)) {
            preparedStatement.setInt(1, id);
            System.out.println(preparedStatement);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                user = resultSet2User(rs);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return user;
    }


    public void insertUser(User user) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {
            setUser2Statement(preparedStatement, user);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }


    public List<User> selectAllUsers() {

        List<User> users = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS);) {
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                users.add(resultSet2User(rs));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return users;
    }

    public void setUser2Statement(PreparedStatement preparedStatement, User user) throws SQLException {
        preparedStatement.setString(1, user.username());
        preparedStatement.setString(2, user.phone());
        preparedStatement.setString(3, user.email());
        preparedStatement.setInt(4, user.countryId());
        preparedStatement.setString(5, user.password());
        preparedStatement.setString(6, user.imageUrl());
        preparedStatement.setString(7, user.createAt().toString());
        preparedStatement.setString(8, user.updateAt().toString());
    }

    public User resultSet2User(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String username = rs.getString("name");
        String phone = rs.getString("phone");
        String email = rs.getString("email");
        int countryId = rs.getInt("idCountry");
        //    String password = rs.getString("password");
        String imageUrl = rs.getString("urlImg");
        LocalDateTime createAt = LocalDateTime.parse(rs.getString("createAt"));
        LocalDateTime updateAt = LocalDateTime.parse(rs.getString("updateAt"));
        return new User()
                .id(id)
                .username(username)
                .phone(phone)
                .email(email)
                .countryId(countryId)
                .imageUrl(imageUrl)
                .createAt(createAt)
                .updateAt(updateAt);
    }

    public boolean deleteUser(int id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(DELETE_USERS_SQL);) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }


    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
