<%--
  Created by IntelliJ IDEA.
  User: FX
  Date: 8/10/2022
  Time: 8:58 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Login</title>
    <link rel="shortcut icon" href="/assets\images\favicon.ico">
    <link rel="stylesheet" href="/assets/css/login.css">
</head>
<body>
<div class="bg-img">
    <div class="content">
        <header>Login Form</header>
        <form method="post">
            <div class="field">
                <span class="fa fa-user"></span>
                <input type="text" required placeholder="Email or User" name="username">
            </div>
            <div class="field space">
                <span class="fa fa-lock"></span>
                <input type="password" class="pass-key" required placeholder="Password" name="password">
                <span class="show">SHOW</span>
            </div>
            <div class="pass">
                <a href="#">Forgot Password?</a>
            </div>
            <div class="field">
                <input type="submit" value="LOGIN">
            </div>
        </form>
        <div class="login">
            Or login with</div>
        <div class="links">
            <div class="facebook">
                <i class="fab fa-facebook-f"><span>Facebook</span></i>
            </div>
            <div class="instagram">
                <i class="fab fa-instagram"><span>Instagram</span></i>
            </div>
        </div>
        <div class="signup">
            Don't have account?
            <a href="#">Signup Now</a>
        </div>
    </div>
</div>
</body>
</html>
