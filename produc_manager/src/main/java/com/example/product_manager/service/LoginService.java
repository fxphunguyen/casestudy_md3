package com.example.product_manager.service;

import com.example.product_manager.model.User;

public interface LoginService {
    User login(String user, String password);
    boolean checkLogin(String user, String password);
    }

