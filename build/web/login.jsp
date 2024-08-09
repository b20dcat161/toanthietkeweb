
<%-- 
    Document   : login
    Created on : Jul 19, 2024, 4:35:17 PM
    Author     : dbdtoan
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Login</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f4f4f9;
                margin: 0;
                padding: 0;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
            }

            .container {
                max-width: 400px;
                width: 100%;
                background-color: #fff;
                padding: 30px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                border-radius: 8px;
            }

            h2 {
                text-align: center;
                color: #333;
                margin-bottom: 20px;
            }

            form {
                display: flex;
                flex-direction: column;
                gap: 15px;
            }

            label {
                font-weight: bold;
                margin-bottom: 5px;
                color: #555;
            }

            input[type="text"],
            input[type="password"] {
                padding: 10px;
                border: 1px solid #ddd;
                border-radius: 5px;
                width: 100%;
                box-sizing: border-box;
            }

            button {
                padding: 10px 20px;
                background-color: #4CAF50;
                color: #fff;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                font-size: 16px;
                transition: background-color 0.3s ease;
                align-self: center;
            }

            button:hover {
                background-color: #45a049;
            }

            .error {
                color: #f44336;
                padding: 10px;
                border-radius: 5px;
                text-align: center;
                margin-top: 10px;
            }

            a {
                color: #3498db;
                text-decoration: none;
                display: inline-block;
                margin-top: 15px;
                text-align: center;
            }

            a:hover {
                text-decoration: underline;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h2>Đăng nhập</h2>
            <form action="login" method="post">
                <label for="username">Tài khoản:</label>
                <input type="text" id="username" name="username" required>

                <label for="password">Mật khẩu:</label>
                <input type="password" id="password" name="password" required>

                <button type="submit">Đăng nhập</button>
            </form>

            <c:if test="${not empty Mess}">
                <div class="error">${Mess}</div>
            </c:if>
        </div>
    </body>
</html>
