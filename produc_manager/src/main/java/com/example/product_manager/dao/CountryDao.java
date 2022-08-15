package com.example.product_manager.dao;

import com.example.product_manager.model.Country;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.product_manager.utils.MySQLConnUtils.getConnection;

public class CountryDao implements ICountryDao {

    private static final String INSERT_COUNTRY_SQL = "INSERT INTO country (name) VALUES (?);";
    private static final String SELECT_COUNTRY_BY_ID = "select * from country where id = ?;";
    private static final String SELECT_ALL_COUNTRY = "select * from country;";
    private static final String DELETE_COUNTRY_SQL = "delete from country where id = ?;";
    private static final String UPDATE_COUNTRY_SQL = "update country set name = ? where id = ?;";

    @Override
    public void insertCountry(Country country) throws SQLException {
        System.out.println(INSERT_COUNTRY_SQL);
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(INSERT_COUNTRY_SQL)) {
            preparedStatement.setString(1, country.getName());

            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    @Override
    public Country selectCountry(int id) {
        Country country = null;
        try (Connection connection = getConnection();

             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_COUNTRY_BY_ID);) {
            preparedStatement.setInt(1, id);
            System.out.println(preparedStatement);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");

                country = new Country(id, name);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return country;
    }

    @Override
    public List<Country> selectAllCountry() {
        List<Country> countryList = new ArrayList<>();

        try (Connection connection = getConnection();

             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_COUNTRY);) {
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");

                countryList.add(new Country(id, name));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return countryList;
    }

    @Override
    public boolean deleteCountry(int id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(DELETE_COUNTRY_SQL);) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    @Override
    public boolean updateCountry(Country country) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(UPDATE_COUNTRY_SQL);) {
            statement.setString(1, country.getName());
            statement.setInt(2, country.getId());

            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
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
