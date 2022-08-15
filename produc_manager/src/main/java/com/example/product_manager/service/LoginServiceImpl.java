package com.example.product_manager.service;

import com.example.product_manager.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.example.product_manager.utils.MySQLConnUtils.getConnection;
import static com.example.product_manager.utils.MySQLConnUtils.printSQLException;

public class LoginServiceImpl implements LoginService {

    private static final String CHECK_LOGIN_SQL = "SELECT * FROM users WHERE email = ? AND password = ?";

    @Override
    public User login(String admin, String pass) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CHECK_LOGIN_SQL)) {
            preparedStatement.setString(1, admin.toLowerCase());
            preparedStatement.setString(2, pass);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String email = rs.getString("email");
                String password = rs.getString("password");
                User user = new User(email, password);
                return user;
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
//        throw new RuntimeException("authencation ko thanh cong");
        return null;
    }

    @Override
    public boolean checkLogin(String user, String password) {
        boolean error = false;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CHECK_LOGIN_SQL)) {
            preparedStatement.setString(1, user.toLowerCase());
            preparedStatement.setString(2, password);
            error = preparedStatement.executeUpdate() > 0;
            System.out.println(error + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        } catch (SQLException e) {
            printSQLException(e);
        }
        return error;
    }
}
