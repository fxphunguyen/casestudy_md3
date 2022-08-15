//package com.example.product_manager.utils;
//
//import com.example.product_manager.service.LoginService;
//import com.example.product_manager.service.LoginServiceImpl;
//
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.Collection;
//import java.util.Map;
//
//@WebFilter(urlPatterns = "/*")
//public class MyFilter implements Filter {
//    private static final String ATT_NAME_USER_NAME = "username";
//    private static final String ATT_NAME_USER_PASS = "password";
//     LoginService loginService;
//
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        loginService = new LoginServiceImpl();
//        HttpServletRequest req = (HttpServletRequest) request;
//        HttpServletResponse res = (HttpServletResponse) response;
//        res.setCharacterEncoding("UTF-8");
//        ServletContext servletContext = request.getServletContext();
//        String servletPath = req.getServletPath();
//        String pathInfo = req.getPathInfo();
//        String urlPattern = null;
//        if (pathInfo != null) {
//            urlPattern = servletPath + "/*";
//        }
//        urlPattern = servletPath;
//        boolean has = hasUrlPattern(servletContext, urlPattern);
//        if(has){
//            String username = "";
//            String password = "";
//            Cookie[] cookies = req.getCookies();
//            if (cookies != null) {
//                for (Cookie cookie : cookies) {
//                    if (ATT_NAME_USER_NAME.equals(cookie.getName())) {
//                        username = cookie.getValue();
//                    }
//                    if (ATT_NAME_USER_PASS.equals(cookie.getName())) {
//                        password = cookie.getValue();
//                    }
//                }
//            }
//            System.out.println("Cookie username:" + username);
//            System.out.println("Cookie password:" + password);
//            if(!req.getServletPath().equals("/login")){
//                if (loginService.login(username, password).equals("")) {
////                    if (!username.equals("") && !password.equals("")) {
//                        res.sendRedirect("/login");
////                        return;
////                    } else {
////                        return;
//                    }
//                } else {
//                    res.sendRedirect("/login");
//                    return;
//                }
//                }else{
//                    chain.doFilter(req, res);
//                    return;
//                }
//            }else{
//                chain.doFilter(req, res);
//            }
//
//        }
//
//    private static boolean hasUrlPattern(ServletContext servletContext, String urlPattern) {
//
//        Map<String, ? extends ServletRegistration> map = servletContext.getServletRegistrations();
//
//        for (String servletName : map.keySet()) {
//            ServletRegistration sr = map.get(servletName);
//
//            Collection<String> mappings = sr.getMappings();
//            if (mappings.contains(urlPattern)) {
//                return true;
//            }
//
//        }
//        return false;
//    }
//}