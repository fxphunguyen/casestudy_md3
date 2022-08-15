package com.example.product_manager.controller;

import com.example.product_manager.model.User;
import com.example.product_manager.service.LoginService;
import com.example.product_manager.service.LoginServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "HomeServlet", urlPatterns = "/login")
public class loginServlet extends HttpServlet {
    private static  LoginService loginservice;
    @Override
    public void init() throws ServletException {
        loginservice = new LoginServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/login/login.jsp");
        requestDispatcher.forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/login/login.jsp");
        List<String> error = null;
        try {
            String user_name = req.getParameter("username");
            String user_password = req.getParameter("password");
            LoginServiceImpl loginService = new LoginServiceImpl();
            User check = loginService.login(user_name, user_password);
            error = new ArrayList<>();
            if(user_name.equals("")){
                error.add("Tên tài khoản không được dể trống");
            }
            if(user_password.equals("")){
                error.add("Mật khẩu không được để trống");
            }
            if (check == null) {
                error.add("Sai tên tài khoản hoặc mật khẩu ");
                error.add("Vui lòng đăng nhập lại ");
                req.setAttribute("error", error);
                dispatcher.forward(req,resp);
            } else {
                Cookie admin = new Cookie("user_name", user_name);
                Cookie pass = new Cookie("user_password"    , user_password);
                admin.setMaxAge(6000);
                pass.setMaxAge(6000);
                resp.addCookie(admin);
                resp.addCookie(pass);
                resp.sendRedirect("/products");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (error.size() > 0) {
            req.setAttribute("error", error);
        }
    }
}
