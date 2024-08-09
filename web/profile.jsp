<%-- 
    Document   : profile
    Created on : Jul 22, 2024, 3:41:58 PM
    Author     : dbdtoan
--%>

<%@ include file="header.jsp" %> 
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User Profile</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                margin: 20px;
            }
            .container {
                max-width: 800px;
                margin: 50px auto;
                background-color: #fff;
                padding: 20px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                border-radius: 8px;
                align-items: center;
            }
            h1,p {
                color: #333;
                text-align: center;

            }

            form {
                margin-bottom: 30px;
                border: 1px solid #ccc;
                padding: 20px;
                border-radius: 5px;
                background-color: #f9f9f9;
            }

            label {
                display: block;
                margin-bottom: 10px;
            }

            input[type="text"],
            input[type="email"],
            input[type="password"] {
                width: 100%;
                padding: 8px;
                margin-bottom: 10px;
                border: 1px solid #ccc;
                border-radius: 4px;
                box-sizing: border-box;
            }

            input[type="submit"] {
                background-color: #4CAF50;
                color: white;
                border: none;
                padding: 10px 20px;
                border-radius: 5px;
                cursor: pointer;
            }

            input[type="submit"]:hover {
                background-color: #45a049;
            }

            p.message {
                color: red;
                font-weight: bold;
            }

        </style>
    </head>
    <body>
        <div class="container">
            <h1>${fn:escapeXml(user.username)}</h1>
            <form action="profile" method="post">
                <input type="hidden" name="action" value="updateProfile">
                <label for="fullname">Họ và tên</label>
                <input type="text" name="fullname" id="fullname" value="${fn:escapeXml(user.fullname)}" required>

                <label for="email">Email:</label>
                <input type="email" name="email" id="email" value="${fn:escapeXml(user.email)}" required>

                <label for="phone">Số điện thoai: </label>
                <input type="text" name="phone" id="phone" value="${fn:escapeXml(user.phone)}" required>

                <input type="submit" value="Cập Nhật Thông Tin">
            </form>


            <h1>Thay Đổi Mật Khẩu</h1>

            <form action="profile" method="post">
                <input type="hidden" name="action" value="changePassword">
                <label for="currentPassword">Mật khẩu hiện tại:</label>
                <input type="password" name="currentPassword" id="currentPassword" required>

                <label for="newPassword">Mật Khẩu Mới:</label>
                <input type="password" name="newPassword" id="newPassword" required>

                <label for="confirmPassword">Xác Nhận Mật Khẩu Mới:</label>
                <input type="password" name="confirmPassword" id="confirmPassword" required>

                <input type="submit" value="Đổi Mật Khẩu">
            </form>

            <c:if test="${not empty Mess}">
                <p class="message">${Mess}</p>
            </c:if>
        </div>
    </body>
</html>
