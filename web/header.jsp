<%-- 
    Document   : header.jsp
    Created on : Jul 22, 2024, 7:11:51 PM
    Author     : dbdtoan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f4f4f9;
                margin: 0;
                padding: 0;
            }

            .header-container {
                width: 80%;
                margin: auto;
                overflow: hidden;
            }

            .content {
                margin: 20px 0;
                padding: 20px;
                background-color: #fff;
                border-radius: 5px;
                box-shadow: 0 0 10px rgba(0,0,0,0.1);
            }

            .nav {
                display: flex;
                justify-content: space-around;
                background-color: #444;
                padding: 10px;
            }

            .nav a {
                color: #fff;
                text-decoration: none;
                font-size: 16px;
            }

            .nav a:hover {
                background-color: #555;
                padding: 5px;
            }

            .container {
                max-width: 800px;
                margin: 50px auto;
                background-color: #fff;
                padding: 20px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                border-radius: 8px;
            }

            h2 {
                text-align: center;
                color: #333;
            }

            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 20px;
            }

            th, td {
                border: 1px solid #ddd;
                padding: 10px;
                text-align: center;
            }

            th {
                background-color: #f4f4f4;
            }

            a {
                color: #3498db;
                text-decoration: none;
            }

            a:hover {
                text-decoration: underline;
            }

            .alert, .success {
                color: white;
                padding: 10px;
                margin: 10px 0;
                border-radius: 5px;
                text-align: center;
            }

            .alert {
                background-color: #f44336;
            }

            .success {
                background-color: #4CAF50;
            }

            .btn-download, .view-submissions-btn {
                padding: 10px 20px;
                color: #fff;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                margin-top: 10px;
                display: inline-block;
                text-decoration: none;
            }

            .btn-download {
                background-color: #4CAF50;
            }
        </style>
    </head>
    <body>
        <div class="header-container">
            <div class="nav">
                <a href="profile">Hồ sơ</a>
                <a href="users">Danh sách người dùng</a>
                <a href="assignments">Bài tập</a>
                <a href="challenges">Câu đố</a>
                <c:if test="${sessionScope.role == 'teacher'}">
                    <a href="usersManager">Quản lý sinh viên</a>
                </c:if>
                <a href="tools">Công cụ</a>
                <a href="logout">Đăng xuất</a>
            </div>
            <c:choose>
                <c:when test="${sessionScope.role == 'student'}">
                </c:when>
                <c:when test="${sessionScope.role == 'teacher'}">
                </c:when>
                <c:otherwise>
                    <c:redirect url="login" />
                </c:otherwise>
            </c:choose>
            <c:if test="${not empty infoMessage}">
                <script>
                    alert("${infoMessage}");
                </script>
            </c:if>
            <c:if test="${not empty errorMessage}">
                <script>
                    alert("${errorMessage}");
                </script>
            </c:if>
            <c:if test="${not empty successMessage}">
                <script>
                    alert("${successMessage}");
                </script>
            </c:if>
        </div>
    </body>
</html>

