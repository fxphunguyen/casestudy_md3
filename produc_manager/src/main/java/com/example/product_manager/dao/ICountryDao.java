package com.example.product_manager.dao;

import com.example.product_manager.model.Country;


import java.sql.SQLException;
import java.util.List;

public interface ICountryDao {
    void insertCountry(Country country) throws SQLException;

    Country selectCountry(int id);

    List<Country> selectAllCountry();

    boolean deleteCountry(int id) throws SQLException;

    boolean updateCountry(Country country) throws SQLException;
}
