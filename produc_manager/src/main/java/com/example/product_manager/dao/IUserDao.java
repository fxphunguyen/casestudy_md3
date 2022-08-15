package com.example.product_manager.dao;

import com.example.product_manager.model.Product;
import com.example.product_manager.model.User;


import java.sql.SQLException;
import java.util.List;

public interface IUserDao {
    void insertUser(User user) throws SQLException;

    User selectUserById(int id);

    List<User> selectAllUsers();

    boolean deleteUser(int id) throws SQLException;

    boolean updateUser(User user) throws SQLException;


}
