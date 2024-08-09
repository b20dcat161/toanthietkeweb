<%-- 
    Document   : userDetails
    Created on : Jul 22, 2024, 1:46:13 PM
    Author     : dbdtoan
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${fn:escapeXml(user.username)}</title>
        <link rel="stylesheet" href="styles/styles.css">
    </head>
    <body>
        <h2>${fn:escapeXml(user.fullname)}</h2>
        <table border="1" cellpadding="10">
            <tr>
                <th>Tên user</th>
                <td>${fn:escapeXml(user.username)}</td>
            </tr>
            <tr>
                <th>Vị trí</th>
                <td>${fn:escapeXml(user.role)}</td>
            </tr>
            <tr>
                <th>Email</th>
                <td>${fn:escapeXml(user.email)}</td>
            </tr>
            <tr>
                <th>Số điện thoại</th>
                <td>${fn:escapeXml(user.phone)}</td>
            </tr>
        </table>
        <br>
        <a href="users">Quay lại</a>
        <br>
        <%@ include file="message.jsp" %> 
        <c:if test="${not empty err}">
            <p class="message">${err}</p>
        </c:if>
    </body>
</html>
