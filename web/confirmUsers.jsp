<%-- 
    Document   : confirmUsers
    Created on : Jul 29, 2024, 10:51:56 PM
    Author     : dbdtoan
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>User Management</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        body {
            background-color: #f8f9fa;
        }
        .container {
            margin-top: 50px;
        }
        .table {
            background-color: #fff;
            box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
            border-radius: 8px;
        }
        .table th, .table td {
            vertical-align: middle;
        }
        .btn {
            margin-top: 20px;
        }
    </style>
</head>
<%@ include file="header.jsp" %> 
<body>
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-lg-8">
                <h2 class="text-center mb-4">Quản lý người dùng</h2>
                <div class="table-responsive">
                    <table class="table table-striped table-bordered">
                        <thead>
                            <tr>
                                <th>Username</th>
                                <th>Họ và tên</th>
                                <th>Role</th>
                                <th>Email</th>
                                <th>Số điện thoại</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="user" items="${users}">
                                <tr>
                                    <td>${fn:escapeXml(user.username)}</td>
                                    <td>${fn:escapeXml(user.fullname)}</td>
                                    <td>${fn:escapeXml(user.role)}</td>
                                    <td>${fn:escapeXml(user.email)}</td>
                                    <td>${fn:escapeXml(user.phone)}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="d-flex justify-content-between">
                    <form action="usersManager" method="post">
                        <input type="hidden" name="action" value="confirmUsers"/>
                        <input type="hidden" name="confirm" value="yes"/>
                        <button type="submit" class="btn btn-primary">Xác nhận</button>
                    </form>
                    <form action="usersManager" method="post">
                        <input type="hidden" name="action" value="confirmUsers"/>
                        <input type="hidden" name="confirm" value="no"/>
                        <button type="submit" class="btn btn-danger">Hủy</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>