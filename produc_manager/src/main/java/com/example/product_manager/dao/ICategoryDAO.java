package com.example.product_manager.dao;

import com.example.product_manager.model.Category;

import java.sql.SQLException;
import java.util.List;

public interface ICategoryDAO {
    void insertCategory(Category category) throws SQLException;

    Category selectCategory(int id);

    List<Category> selectAllCategory();

    boolean deleteCategory(int id) throws SQLException;

    boolean updateCategory(Category category) throws SQLException;

}
