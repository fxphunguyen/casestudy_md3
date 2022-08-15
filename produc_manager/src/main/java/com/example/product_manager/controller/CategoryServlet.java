package com.example.product_manager.controller;

import com.example.product_manager.dao.CategoryDAO;
import com.example.product_manager.dao.ICategoryDAO;
import com.example.product_manager.dao.IProductDAO;
import com.example.product_manager.dao.ProductDAO;
import com.example.product_manager.model.Category;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "CategoryServlet", urlPatterns = "/category")
public class CategoryServlet extends HttpServlet {
    private ProductDAO productDAO;
    private ICategoryDAO iCategoryDAO;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
                    deleteCategory(request, response);
                    break;
                default:
                    listCategory(request, response);
                    break;
            }
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }

    private void listCategory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Category> categoryList = iCategoryDAO.selectAllCategory();
        request.setAttribute("categoryList", categoryList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/category/list.jsp");
        dispatcher.forward(request, response);

    }

    private void deleteCategory(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        iCategoryDAO.deleteCategory(id);

        List<Category> categoryList = iCategoryDAO.selectAllCategory();
        request.setAttribute("categoryList", categoryList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/category/list.jsp");
        dispatcher.forward(request, response);

    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Category existingCategory = iCategoryDAO.selectCategory(id);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/category/edit.jsp");
        request.setAttribute("category", existingCategory);
        dispatcher.forward(request, response);

    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/category/create.jsp");
        dispatcher.forward(request, response);
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
                    insertCategory(request, response);
                    break;
                case "edit":
                    updateCategory(request, response);
                    break;
            }
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }

    private void updateCategory(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");

        Category category = new Category(id, name);
        iCategoryDAO.updateCategory(category);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/category/edit.jsp");
        dispatcher.forward(request, response);

    }

    private void insertCategory(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        String name = request.getParameter("name");

        Category category = new Category();
        category.setName(name);
        iCategoryDAO.insertCategory(category);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/category/create.jsp");
        dispatcher.forward(request, response);

    }

    @Override
    public void init() throws ServletException {
        productDAO = new ProductDAO();
        iCategoryDAO = new CategoryDAO();

        if (this.getServletContext().getAttribute("productList") == null) {
            this.getServletContext().setAttribute("productList", iCategoryDAO.selectAllCategory());
        }
    }
}
