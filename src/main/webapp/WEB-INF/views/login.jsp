<%--
  Created by IntelliJ IDEA.
  User: slimechan
  Date: 8/12/20
  Time: 3:51 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix='c' %>
.
<html>
<head>
    <title>Login page</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
          integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
</head>
<body>
<div class="container w-75">
    <div class="container w-100 p-5 d-flex justify-content-between">
        <a href="/" class="text-dark"><h3><img src="https://hhcdn.ru/employer-logo/1929775.png"
                                               style="width: 48px; height: 48px;"/> AVSoft </h3></a>
        <div>
            <a href="/login" class="text-dark">Login</a>|<a href="/register" class="text-dark">Register</a>
        </div>

    </div>
    <div class="container w-50">
        <form class="card" action="/api/login" method="post">
            <div class="card-header text-center">
                <h4>Login</h4>
            </div>
            <div class="card-body text-center">
                <div class="form-group">
                    <label for="usernameInput">Username</label>
                    <input type="text" name="username" class="form-control" id="usernameInput"
                           aria-describedby="usernameHelp">
                    <small id="usernameHelp" class="form-text text-muted">Input your username</small>
                </div>
                <div class="form-group">
                    <label for="passwordinput">Password</label>
                    <input type="password" name="password" class="form-control" id="passwordinput"
                           aria-describedby="passwordHelp">
                    <small id="passwordHelp" class="form-text text-muted">Input your password</small>
                </div>
            </div>
            <div class="card-footer text-center">
                <input type="submit" class="btn btn-primary" value="Login"/>
            </div>
        </form>
    </div>
</div>
</body>
<footer class="container p-5 d-flex justify-content-between">
    <a>© AVSoft 2020. All Rights Reserved.</a>
    <a href="https://t.me/slime_chan">designed & developed by @SlimeChan_</a>
</footer>
</html>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
        integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"
        integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"
        integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV"
        crossorigin="anonymous"></script>