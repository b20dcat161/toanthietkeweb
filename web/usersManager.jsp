<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="header.jsp" %> 
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Teacher User Management</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f4f4f9;
                margin: 0;
                padding: 0;
                display: flex;
                justify-content: center;
                align-items: center;
                min-height: 100vh;
                flex-direction: column;
            }

            .teacher-container {
                max-width: 1000px;
                margin: 50px auto;
                width: 100%;
                background-color: #fff;
                padding: 30px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                border-radius: 8px;
                margin-bottom: 30px;
            }

            h2 {
                text-align: center;
                color: #333;
                margin-bottom: 20px;
            }

            .teacher-form {
                display: flex;
                flex-direction: column;
                gap: 10px;
                margin-bottom: 30px;
            }

            label {
                font-weight: bold;
                margin-bottom: 5px;
                color: #555;
            }
            select,
            input[type="text"],
            input[type="file"],
            input[type="email"],
            input[type="tel"] {
                padding: 10px;
                border: 1px solid #ddd;
                border-radius: 5px;
                width: 100%;
                box-sizing: border-box;
            }

            input[type="submit"],
            .submit-btn,
            .view-submission-btn,
            .edit-btn,
            .export-btn,
            .delete-btn {
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

            input[type="submit"]:hover,
            .submit-btn:hover,
            .edit-btn:hover,
            .export-btn:hover,
            .delete-btn:hover {
                background-color: #45a049;
            }

            .teacher-table {
                width: 100%;
                border-collapse: collapse;
                margin-bottom: 30px;
            }

            th,
            td {
                border: 1px solid #ddd;
                padding: 10px;
                text-align: center;
            }

            th {
                background-color: #f4f4f4;
                color: #333;
            }

            a {
                color: #3498db;
                text-decoration: none;
            }

            a:hover {
                text-decoration: underline;
            }

            .alert {
                background-color: #f44336;
                color: white;
                padding: 10px;
                margin: 10px 0;
                border-radius: 5px;
                text-align: center;
            }

            .success {
                background-color: #4CAF50;
                color: white;
                padding: 10px;
                margin: 10px 0;
                border-radius: 5px;
                text-align: center;
            }

            .checkbox-cell {
                justify-content: center;
                align-items: center;
            }
        </style>
    </head>
    <body>
        <div class="teacher-container">
            <h2>Quản lý sinh viên</h2>
            <table class="teacher-table">
                <thead>
                    <tr>
                        <!--<th>Chọn</th>-->
                        <!--<th>ID</th>-->
                        <th>Username</th>
                        <th>Họ và tên</th>
                        <th>Email</th>
                        <th>Số điện thoại</th>
                        <th></th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="student" items="${students}">
                        <tr>
                            <!--                            <td class="checkbox-cell">
                                                            <input type="checkbox" name="selectedUsers" value="${student.id}">
                                                        </td>
                                                        <td>${student.id}</td>-->
                            <td>${fn:escapeXml(student.username)}</td>

                    <form action="usersManager" method="post">
                        <td>
                            <input type="text" name="fullname" value="${fn:escapeXml(student.fullname)}" style="width: 80%;">
                        </td>
                        <td>
                            <input type="email" name="email" value="${fn:escapeXml(student.email)}" style="width: 80%;">
                        </td>
                        <td>
                            <input type="tel" name="phone" value="${fn:escapeXml(student.phone)}" style="width: 80%;">
                        </td>
                        <td>
                            <input type="hidden" name="action" value="edit">
                            <input type="hidden" name="username" value="${fn:escapeXml(student.username)}">
                            <button type="submit" class="edit-btn">Save</button>
                        </td>
                    </form>
                    <td>   
                        <form action="usersManager" method="post">
                            <input type="hidden" name="action" value="delete">
                            <input type="hidden" name="id" value="${student.id}">
                            <button type="submit" class="delete-btn">Delete</button>
                        </form>
                    </td>
                    </tr>

                </c:forEach>
                </tbody>
            </table>
            <form action="usersManager" method="post">
                <input type="hidden" name="action" value="exportUsers">
                <button type="submit" class="submit-btn">XML Export</button>
            </form>
            <form action="usersManager" method="post">
                <input type="hidden" name="action" value="backupUsers">
                <button type="submit" class="submit-btn">Export</button>
            </form>

            <form class="teacher-form" action="usersManager" method="post">
                <h3>Tạo tài khoản</h3>
                <input type="hidden" name="action" value="createUser">
                <label for="new-username">Username:</label>
                <input type="text" id="new-username" name="username" required>
                <label for="new-fullname">Họ và tên:</label>
                <input type="text" id="new-fullname" name="fullname" required>
                <label for="new-role">Role:</label>
                <select name="role" id="new-role">
                    <option value="student">Student</option>
                    <option value="teacher">Teacher</option>
                </select>

                <label for="new-email">Email:</label>
                <input type="email" id="new-email" name="email" required>

                <label for="new-phone">Số điện thoại:</label> 
                <input type="tel" id="new-phone" name="phone" required>

                <button type="submit" class="submit-btn">Đăng ký</button>
            </form>

            <form class="teacher-form" action="usersManager" method="post" enctype="multipart/form-data">
                <h3>Import Users from XML</h3>
                <input type="hidden" name="action" value="createUsers">
                <input type="file" name="xmlFile" accept=".xml" required>
                <button type="submit" class="submit-btn">Upload XML</button>
            </form>

            <form class="teacher-form" action="usersManager" method="post" enctype="multipart/form-data">
                <h3>Restore Users from Backup</h3>
                <input type="hidden" name="action" value="restoreUsers">
                <input type="file" name="backupFile" accept=".ser" required>
                <button type="submit" class="submit-btn">Upload Backup</button>
            </form>


        </div>
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
    </body>
</html>
