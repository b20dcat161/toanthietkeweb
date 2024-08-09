<%-- 
    Document   : users
    Created on : Jul 22, 2024, 10:41:10 AM
    Author     : dbdtoan
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Danh sách người dùng</title>
        <link rel="stylesheet" href="styles/styles.css">
        <style>        table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 20px;
                background-color: #fff;
                border-radius: 5px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            }

            /* Định dạng cho tiêu đề bảng */
            thead {
                background-color: #3498db;
                color: #000;
                text-align: left;
            }

            /* Định dạng cho các ô tiêu đề */
            th {
                padding: 15px;
                font-weight: 600;
                text-transform: uppercase;
                border-bottom: 2px solid #ddd;
                border-right: 1px solid #ddd;
            }

            th:last-child {
                border-right: none; /* Loại bỏ viền phải cho ô cuối cùng */
            }

            /* Định dạng cho nội dung bảng */
            tbody tr:nth-child(even) {
                background-color: #f2f2f2; /* Màu nền xen kẽ cho các dòng chẵn */
            }

            tbody tr:hover {
                background-color: #e8f6ff; /* Hiệu ứng khi di chuột qua */
            }

            td {
                padding: 15px;
                border-bottom: 1px solid #ddd;
                border-right: 1px solid #ddd;
            }

            td:last-child {
                border-right: none; /* Loại bỏ viền phải cho ô cuối cùng */
            }

            a {
                color: #3498db;
                text-decoration: none;
                font-weight: bold;
                transition: color 0.3s ease;
            }

            a:hover {
                color: #2980b9;
            }


            .pagination {
                margin: 20px 0;
                display: flex;
                justify-content: center;
                align-items: center;
            }

            .pagination button {
                background-color: #3498db;
                color: white;
                border: none;
                border-radius: 5px;
                padding: 10px 15px;
                cursor: pointer;
                transition: background-color 0.3s ease;
                margin: 0 5px;
            }

            .pagination button:hover {
                background-color: #2980b9;
            }

            .pagination button:disabled {
                background-color: #ccc;
                cursor: not-allowed;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h2>Danh sách người dùng</h2>
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Username</th>
                        <th>Email</th>
                        <th>Phone</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="user" items="${requestScope.users}">
                        <tr>
                            <td>${user.id}</td>
                            <td>${fn:escapeXml(user.username)}</td>
                            <td>${fn:escapeXml(user.email)}</td>
                            <td>${fn:escapeXml(user.phone)}</td>
                            <td>
                                <a href="userDetails?user=${fn:escapeXml(user.username)}">Xem chi tiết</a> <!-- Fixed Vietnamese characters -->
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <div class="pagination">
                <form action="users" method="get" style="display:inline;">
                    <input type="hidden" name="page" value="${page - 1}">
                    <button type="submit" ${page <= 1 ? 'disabled' : ''}>Previous</button>
                </form>
                <span>Page</span>
                <form action="users" method="get" style="display:inline;">
                    <input type="hidden" name="page" value="${page + 1}">
                    <button type="submit">Next</button>
                </form>
            </div>
        </div>
    </body>
</html>
