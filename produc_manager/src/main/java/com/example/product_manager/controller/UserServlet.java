package com.example.product_manager.controller;

import com.example.product_manager.dao.CountryDao;
import com.example.product_manager.dao.ICountryDao;
import com.example.product_manager.dao.UserDao;
import com.example.product_manager.model.Country;
import com.example.product_manager.model.User;

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
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 50, // 50MB
        maxRequestSize = 1024 * 1024 * 50) // 50MB
@WebServlet(name = "UserServlet", urlPatterns = "/users")
public class UserServlet extends HttpServlet {
    private UserDao userDAO;
    private ICountryDao iCountryDao;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDao();
        iCountryDao = new CountryDao();

        if (this.getServletContext().getAttribute("countryList") == null) {
            this.getServletContext().setAttribute("countryList", iCountryDao.selectAllCountry());
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        try {
            switch (action) {
                case "create":
                    showCreateUserPage(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "delete":
                    deleteUser(request, response);
                    break;
                default:
                    showlistUserPage(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        try {
            switch (action) {
                case "create":
                    doCreateUser(request, response);
                    break;
                case "edit":
                    updateUser(request, response);
                    break;
                case "sales":
                    showSalesPage(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void showSalesPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/user/sales.jsp");
        requestDispatcher.forward(request, response);
    }


    private void showlistUserPage(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {

        List<User> listUser = userDAO.selectAllUsers();
        request.setAttribute("listUser", listUser);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/user/list.jsp");
        dispatcher.forward(request, response);
    }

    private void showCreateUserPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Country> listCountry = iCountryDao.selectAllCountry();
        request.setAttribute("listCountry", listCountry);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/user/create.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/user/edit.jsp");
        int id = Integer.parseInt(request.getParameter("id"));
        User user = userDAO.selectUserById(id);
        List<Country> countryList = iCountryDao.selectAllCountry();
        request.setAttribute("listCountry", countryList);
        request.setAttribute("user", user);
        dispatcher.forward(request, response);

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

    private void doCreateUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        User user = new User();
        boolean success = true;
        boolean flag = true;
        List<String> errors = new ArrayList<>();
        Map<String, String> hashMap = new HashMap<String, String>();
        try {
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            int idCountry = Integer.parseInt(request.getParameter("idCountry"));
            String password = request.getParameter("password");
            LocalDateTime createAt = java.time.LocalDateTime.now();
            LocalDateTime updateAt = java.time.LocalDateTime.now();
            String urlImg = null;
            for (Part part : request.getParts()) {
                if (part.getName().equals("image")) {
                    String fileName = extractFileName(part);
                    fileName = new File(fileName).getName();
                    part.write("D:\\produc_manager\\src\\main\\webapp\\image\\imageUser\\" + fileName);
                    urlImg = "image\\imageUser\\" + fileName;
                }
            }
          // User newUser = new User(name, urlImg, email, phone, idCountry, password, createAt, updateAt);
            User newUser = new User()
                    .username(name)
                    .imageUrl(urlImg)
                    .email(email)
                    .phone(phone)
                    .countryId(idCountry)
                    .password(password)
                    .createAt(createAt)
                    .updateAt(updateAt);

            ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
            Validator validator = validatorFactory.getValidator();

            Set<ConstraintViolation<User>> constraintViolations = validator.validate(newUser);

            if (!constraintViolations.isEmpty()) {
                request.setAttribute("errors", getErrorFromContraint(constraintViolations));
                request.setAttribute("user", newUser);
                List<Country> countryList = iCountryDao.selectAllCountry();
                request.setAttribute("countryList", countryList);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/user/create.jsp");
                requestDispatcher.forward(request, response);
            } else {
                if (iCountryDao.selectCountry(idCountry) == null) {
                    hashMap.put("country", "Country value invalid");
                    System.out.println(this.getClass() + " Country invalid");
                }
                if (flag) {

                    request.setAttribute("success", "Thêm mới thành công");
                    userDAO.insertUser(newUser);
                    request.getRequestDispatcher("/WEB-INF/user/create.jsp").forward(request, response);
                } else {
                    request.setAttribute("user", user);
                    System.out.println(this.getClass() + " !constraintViolations.isEmpty()");
                    request.getRequestDispatcher("/WEB-INF/user/create.jsp").forward(request, response);
                }
            }
        } catch (NumberFormatException ex) {
            request.setAttribute("user", user);

            request.getRequestDispatcher("/WEB-INF/user/create.jsp").forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        String username = request.getParameter("name");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        int countryId = Integer.parseInt(request.getParameter("idCountry"));
        String password = request.getParameter("password");
        LocalDateTime updateAt = java.time.LocalDateTime.now();

        User newUser = new User()
                .id(id)
                .username(username)
              //  .imageUrl(urlImg)
                .email(email)
                .phone(phone)
                .countryId(countryId)
                .password(password)
             //   .createAt(createAt)
                .updateAt(updateAt);
       // User newUser = new User(id, username, phone, email, countryId, password, updateAt);

        for (Part part : request.getParts()) {
            if (part.getName().equals("image")) {
                String fileName = extractFileName(part);
                if (fileName.equals("")) {
                    fileName = request.getParameter("img");
                    newUser.imageUrl(fileName);
                    break;
                }
                fileName = new File(fileName).getName();
                part.write("D:\\produc_manager\\src\\main\\webapp\\image\\imageUser\\" + fileName);
                newUser.imageUrl("image\\imageUser\\" + fileName);

            }
        }

        userDAO.updateUser(newUser);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/user/edit.jsp");
        dispatcher.forward(request, response);
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        userDAO.deleteUser(id);

        List<User> listUser = userDAO.selectAllUsers();
        request.setAttribute("listUser", listUser);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/user/list.jsp");
        dispatcher.forward(request, response);
    }

    private HashMap<String, List<String>> getErrorFromContraint(Set<ConstraintViolation<User>> constraintViolations) {
        HashMap<String, List<String>> hashMap = new HashMap<>();
        for (ConstraintViolation<User> c : constraintViolations) {
            if (hashMap.keySet().contains(c.getPropertyPath().toString())) {
                hashMap.get(c.getPropertyPath().toString()).add(c.getMessage());
            } else {
                List<String> listMessages = new ArrayList<>();
                listMessages.add(c.getMessage());
                hashMap.put(c.getPropertyPath().toString(), listMessages);
            }
        }
        return hashMap;
    }
}
