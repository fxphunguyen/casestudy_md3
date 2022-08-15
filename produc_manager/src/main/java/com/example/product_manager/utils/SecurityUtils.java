//package com.example.product_manager.utils;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.List;
//import java.util.Set;
//
//public class SecurityUtils {
//    public static boolean isSecurityPage(HttpServletRequest request) {
//        System.out.println(SecurityUtils.class + " isSecurityPage(...): contextPath: " + request.getContextPath()+
//                " servletPath: " + request.getServletPath() + " pathInfo: " + request.getPathInfo() +
//                " pathURI: " + request.getRequestURI()
//        );
//        String urlPattern = UrlPatternUtils.getUrlPattern(request);
//        System.out.println(SecurityUtils.class + " isSecurityPage(...): urlPattern: " + urlPattern);
//        Set<String> roles = SecurityConfig.getAllAppRoles();
//        for (String role : roles) {
//            List<String> urlPatterns = SecurityConfig.getUrlPatternsForRole(role);
//            if (urlPatterns != null && urlPatterns.contains(urlPattern)) {
//                return true;
//            }
//        }
//        System.out.println(SecurityUtils.class + " urlPattern of request: " + urlPattern);
//        return false;
//    }
//    public static boolean hasPermission(HttpServletRequest request) {
//        String urlPattern = UrlPatternUtils.getUrlPattern(request);
//        Set<String> allRoles = SecurityConfig.getAllAppRoles();
//        for (String role : allRoles) {
//            if (!request.isUserInRole(role)) {
//                continue;
//            }
//            List<String> urlPatterns = SecurityConfig.getUrlPatternsForRole(role);
//            if (urlPatterns != null && urlPatterns.contains(urlPattern)) {
//                return true;
//            }
//        }
//        return false;
//    }
//}