package com.example.product_manager.controller;

import com.example.product_manager.dao.CategoryDAO;
import com.example.product_manager.dao.ICategoryDAO;
import com.example.product_manager.dao.IProductDAO;
import com.example.product_manager.dao.ProductDAO;
import com.example.product_manager.model.Category;
import com.example.product_manager.model.Product;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.BiConsumer;


@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2,
        maxFileSize = 1024 * 1024 * 50,
        maxRequestSize = 1024 * 1024 * 50)
@WebServlet(name = "ProductServlet", urlPatterns = "/products")
public class ProductServlet extends HttpServlet {
    private IProductDAO iproductDAO;
    private ICategoryDAO iCategoryDAO;
    private String errors;

    @Override
    public void init() throws ServletException {
        iproductDAO = new ProductDAO();
        iCategoryDAO = new CategoryDAO();
        if (this.getServletContext().getAttribute("categoryList") == null) {
            this.getServletContext().setAttribute("categoryList", iCategoryDAO.selectAllCategory());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }

        try {
            switch (action) {
                case "create":
                    showNewForm(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "delete":
                    deleteProduct(request, response);
                    break;
                default:
                    listProduct(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        try {
            switch (action) {
                case "create":
                    insertProduct(request, response);
                    break;
                case "edit":
                    updateProduct(request, response);
                    break;
                case "sales":
                    showSalesPage(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void showListProductSearch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String search = "";
        if (request.getParameter("search") != null) {
            search = request.getParameter("search");
        }
        List<Product> listProduct = iproductDAO.selectAllProductSearch(search);
        request.setAttribute("products", listProduct);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/product/list.jsp");
        requestDispatcher.forward(request, response);
    }

    private void insertProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        Product product = new Product();
        boolean success = true;
        boolean flag = true;
        List<String> errors = new ArrayList<>();
        Map<String, String> hashMap = new HashMap<String, String>();
        try {
            String name = request.getParameter("name");
            BigDecimal price = new BigDecimal(request.getParameter("price"));
            Long quantity = Long.valueOf(request.getParameter("quantity"));
            int idCategory = Integer.parseInt(request.getParameter("idCategory"));
            LocalDateTime createAt = java.time.LocalDateTime.now();
            LocalDateTime updateAt = java.time.LocalDateTime.now();
            product = new Product(name, price, quantity, idCategory, createAt, updateAt);

            for (Part part : request.getParts()) {
                if (part.getName().equals("image")) {
                    String fileName = extractFileName(part);
                    fileName = new File(fileName).getName();
                    part.write("D:\\produc_manager\\src\\main\\webapp\\image\\imageProduct\\" + fileName);
                    product.setUrlImg("image\\imageProduct\\" + fileName);
                }
            }

            ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
            Validator validator = validatorFactory.getValidator();

            Set<ConstraintViolation<Product>> constraintViolations = validator.validate(product);

            System.out.println("Product: " + product);

            if (!constraintViolations.isEmpty()) {
                request.setAttribute("errors", getErrorFromContraint(constraintViolations));
                request.setAttribute("product", product);

                List<Category> categoryList = iCategoryDAO.selectAllCategory();
                request.setAttribute("categoryList", categoryList);

                System.out.println(this.getClass() + " !constraintViolations.isEmpty()");
                request.getRequestDispatcher("/WEB-INF/product/create.jsp").forward(request, response);
            } else {
                if (iCategoryDAO.selectCategory(idCategory) == null) {
                    flag = false;
                    hashMap.put("category", "Category value invalid");
                    System.out.println(this.getClass() + " Category invalid");
                }

                if (flag) {
                    // Create user sussceess
                    request.setAttribute("success", "Thêm mới thành công");
                    iproductDAO.insertProduct(product);

                    request.getRequestDispatcher("/WEB-INF/product/create.jsp").forward(request, response);
                } else {
                    request.setAttribute("product", product);
                    System.out.println(this.getClass() + " !constraintViolations.isEmpty()");
                    request.getRequestDispatcher("/WEB-INF/product/create.jsp").forward(request, response);
                }
            }
        } catch (NumberFormatException ex) {
            System.out.println(this.getClass() + " NumberFormatException: User info from request: " + product);
            request.setAttribute("product", product);
            request.getRequestDispatcher("/WEB-INF/product/create.jsp").forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    private void showSalesPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/product/sales.jsp");
        requestDispatcher.forward(request, response);
    }

    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return "";
    }

    private void updateProduct(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        Product product;
        Long id = Long.parseLong(request.getParameter("id"));
        String name = request.getParameter("name").trim();
        BigDecimal price = new BigDecimal(request.getParameter("price"));
        Long quantity = Long.valueOf(request.getParameter("quantity"));
        int idCategory = Integer.parseInt(request.getParameter("idCategory"));
        LocalDateTime updateAt = java.time.LocalDateTime.now();
        product = new Product(id, name, price, quantity, idCategory, updateAt);
        for (Part part : request.getParts()) {
            if (part.getName().equals("image")) {
                String fileName = extractFileName(part);
                fileName = new File(fileName).getName();
                if (fileName.equals("")) {
                    fileName = request.getParameter("img");
                    product.setUrlImg(fileName);
                    break;
                }
                part.write("D:\\produc_manager\\src\\main\\webapp\\image\\imageProduct\\" + fileName);
                product.setUrlImg("image\\imageProduct\\" + fileName);
            }
        }

        List<Category> categoryList = iCategoryDAO.selectAllCategory();
        request.setAttribute("sussceess", "Sửa thành công");
        iproductDAO.updateProduct(product);
        request.setAttribute("categoryList", categoryList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/product/edit.jsp");
        dispatcher.forward(request, response);
    }


    private void listProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Product> productList = null;
        String key = "";
        String type = "";
        if (request.getParameter("search") != null) {
            key = request.getParameter("key");
            System.out.println(key + " key day ne");
            type = request.getParameter("type");
            System.out.println(type + " loại day ne");
//            productList = iproductDAO.searchByKey(key, type);
        } else {
            productList = iproductDAO.selectAllProduct();
        }
        request.setAttribute("productList", productList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/product/list.jsp");
        dispatcher.forward(request, response);

    }

    private void deleteProduct(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        iproductDAO.deleteProduct(id);

        List<Product> productList = iproductDAO.selectAllProduct();
        request.setAttribute("productList", productList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/product/list.jsp");
        dispatcher.forward(request, response);

    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/product/edit.jsp");

        Long id = Long.valueOf(request.getParameter("id"));
        Product existingProduct = iproductDAO.selectProductById(id);
        List<Category> categoryList = iCategoryDAO.selectAllCategory();
        request.setAttribute("categoryList", categoryList);
        request.setAttribute("product", existingProduct);
        dispatcher.forward(request, response);

    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Category> categoryList = iCategoryDAO.selectAllCategory();
        request.setAttribute("categoryList", categoryList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/product/create.jsp");
        dispatcher.forward(request, response);
    }

    private HashMap<String, List<String>> getErrorFromContraint(Set<ConstraintViolation<Product>> constraintViolations) {
        HashMap<String, List<String>> hashMap = new HashMap<>();
        for (ConstraintViolation<Product> p : constraintViolations) {
            if (hashMap.keySet().contains(p.getPropertyPath().toString())) {
                hashMap.get(p.getPropertyPath().toString()).add(p.getMessage());
            } else {
                List<String> listMessages = new ArrayList<>();
                listMessages.add(p.getMessage());
                hashMap.put(p.getPropertyPath().toString(), listMessages);
            }
        }
        return hashMap;
    }
}
