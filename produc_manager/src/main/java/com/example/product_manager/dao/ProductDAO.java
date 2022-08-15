package com.example.product_manager.dao;

import com.example.product_manager.model.Product;
import com.example.product_manager.model.User;
import com.example.product_manager.utils.MySQLConnUtils;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.example.product_manager.utils.MySQLConnUtils.getConnection;

public class ProductDAO implements IProductDAO {
    private static final String INSERT_PRODUCTS_SQL =
            "INSERT INTO products (" +
            "`name` , " +
            "urlImg , " +
            "price, " +
            "quantity, " +
            "idCategory, " +
            "createAt, " +
            "updateAt ) " +
            " VALUES (?, ?, ?, ? , ?, ?, ?);";
    private static final String SELECT_PRODUCT_BY_ID =
            "select id,  " +
                "`name` ," +
                " `urlImg` , " +
                "price ," +
                "quantity ," +
                "idCategory ," +
                "createAt ," +
                "updateAt " +
            "from products where id =?";

    private static final String SELECT_ALL_PRODUCTS = "" +
            "select" +
            " id ," +
            " `name` , " +
            "urlImg , " +
            "price , " +
            "quantity , " +
            "idCategory , " +
            " createAt , " +
            "updateAt " +
            "from products";
    private static final String DELETE_PRODUCTS_SQL = "delete from products where id = ?;";
    private static final String UPDATE_PRODUCTS_SQL = "update products set `name` = ?,price= ?, quantity=?, idCategory =?,urlImg =?, updateAt =?  where id = ?;";

    private static final String  SEARCH_BY_KEY_PRODUCT = "select * from products where name like ?";

    @Override
    public List<Product> selectAllProductSearch(String search) {
        String query = SEARCH_BY_KEY_PRODUCT;
        List<Product> list= new ArrayList<Product>();
        Product product=null;
        Connection connection=null;
        PreparedStatement stmt=null;
        try{
            connection =getConnection();
            stmt = connection.prepareStatement(query);
            stmt.setString(1, '%' + search + '%');
            System.out.println(stmt);
            ResultSet resultSet=stmt.executeQuery();
            while (resultSet.next()){
                product=new Product();
                product.setId(resultSet.getLong("id"));
                product.setName(resultSet.getString("name"));
                product.setPrice(resultSet.getBigDecimal("price"));
                product.setQuantity(resultSet.getLong("quantity"));
                product.setIdCategory(resultSet.getInt("idCategory"));
                list.add(product);
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
                if (connection!= null)
                    connection.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
        return list;

    }
    public ProductDAO() {
    }

    @Override
    public List<Product> selectAllProduct() {
        List<Product> products = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_PRODUCTS)) {
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                String urlImg = rs.getString("urlImg");
                BigDecimal price = rs.getBigDecimal("price");
                Long quantity = rs.getLong("quantity");
                int idCategory = rs.getInt("idCategory");
                LocalDateTime createAt = LocalDateTime.parse(rs.getString("createAt"));
                LocalDateTime updateAt = LocalDateTime.parse(rs.getString("updateAt"));
                products.add(new Product(id, name, urlImg, price, quantity, idCategory, createAt, updateAt));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public void insertProduct(Product product) throws SQLException {
        System.out.println(INSERT_PRODUCTS_SQL);
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCTS_SQL)) {
//            preparedStatement.setLong(1, product.getId());
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getUrlImg());
            preparedStatement.setBigDecimal(3, product.getPrice());
            preparedStatement.setLong(4, product.getQuantity());
            preparedStatement.setInt(5, product.getIdCategory());
            preparedStatement.setString(6, product.getCreateAt().toString());
            preparedStatement.setString(7, product.getUpdateAt().toString());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }


    @Override
    public Product selectProductById(Long id) {
        Product product = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PRODUCT_BY_ID);) {
            preparedStatement.setLong(1, id);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                String urlImg = rs.getString("urlImg");
                BigDecimal price = rs.getBigDecimal("price");
                Long quantity = rs.getLong("quantity");
                int idCategory = rs.getInt("idCategory");
                LocalDateTime createAt = LocalDateTime.parse(rs.getString("createAt"));
                LocalDateTime updateAt = LocalDateTime.parse(rs.getString("updateAt"));
                product = new Product(id, name, urlImg, price, quantity, idCategory, createAt, updateAt);

            }

        } catch (SQLException e) {
            printSQLException(e);
        }
        return product;
    }


    @Override
    public boolean deleteProduct(int id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(DELETE_PRODUCTS_SQL);) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    @Override
    public boolean updateProduct(Product product) throws SQLException {
        boolean rowUpdated = true;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_PRODUCTS_SQL);) {

            statement.setString(1, product.getName());
            statement.setBigDecimal(2, product.getPrice());
            statement.setLong(3, product.getQuantity());
            statement.setInt(4,product.getIdCategory());
            statement.setString(5,product.getUrlImg());
            statement.setString(6, product.getUpdateAt().toString());
            statement.setLong(7, product.getId());
//            System.out.println("updateUser: " + statement);
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
