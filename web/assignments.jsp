<%-- 
    Document   : assignment
    Created on : Jul 25, 2024, 10:32:39 AM
    Author     : dbdtoan
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file = "header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Assignment</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f4f4f9;
                margin: 0;
                padding: 0;
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

            form {
                display: flex;
                flex-direction: column;
                gap: 10px;
            }

            label {
                font-weight: bold;
                margin-bottom: 5px;
            }

            input[type="text"],
            input[type="file"] {
                padding: 10px;
                border: 1px solid #ddd;
                border-radius: 5px;
                width: 100%;
            }

            input[type="submit"],
            .submit-btn,view-submission-btn {
                padding: 10px 20px;
                background-color: #4CAF50;
                color: #fff;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                margin-top: 10px;
                align-self: center;
            }

            input[type="submit"]:hover,
            .submit-btn:hover {
                background-color: #45a049;
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
        </style>
    </head>
    <body>
        <div class="container">
            <h2>Bài tập</h2>

            <c:if test="${sessionScope.role eq 'teacher'}">
                <h3>Tải lên bài tập</h3>
                <form action="uploadAssignment" method="post" enctype="multipart/form-data">
                    <label for="title">Tiêu đề: </label>
                    <input type="text" id="title" name="title" required>

                    <label for="file">Chọn file:</label>
                    <input type="file" id="file" name="file" required>

                    <input type="submit" value="Upload">
                </form>

                <c:if test="${not empty uploadMessage}">
                    <div class="${uploadMessage.contains('success') ? 'success' : 'alert'}">
                        ${uploadMessage}
                    </div>
                </c:if>
            </c:if>

            <h3>Danh sách bài tập</h3>
            <table>
                <thead>
                    <tr>
                        <th>Giáo viên</th>
                        <th>Tiêu đề</th>
                        <th>Chi tiết</th>
                        <th>Bài nộp</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="assignment" items="${assignments}">
                        <tr>
                            <td>${fn:escapeXml(assignment.teacher)}</td>
                            <td>${fn:escapeXml(assignment.title)}</td>
                            <td>
                                <a href="downloadAssignment?filename=${fn:escapeXml(assignment.filePath)}" download>Download</a>
                            </td>
                            <td>
                                <c:if test="${sessionScope.role == 'student'}">
                                    <c:set var="isSubmitted" value="false" />

                                    <c:forEach var="submit" items="${submits}">
                                        <c:if test="${submit.assId eq assignment.id}">
                                            <c:set var="isSubmitted" value="true" />
                                            <span>Đã nộp: ${fn:escapeXml(submit.filePath)}</span>
                                        </c:if>
                                    </c:forEach>

                                    <c:choose>
                                        <c:when test="${isSubmitted == 'false'}">
                                            <form action="uploadAssignment" method="post" enctype="multipart/form-data">
                                                <input type="hidden" name="assignmentId" value="${assignment.id}">
                                                <input type="file" id="file" name="file" required>
                                                <button type="submit" class="submit-btn">Submit</button>
                                            </form>
                                        </c:when>
                                    </c:choose>
                                </c:if>
                                <c:if test="${sessionScope.role eq 'teacher'}">
                                    <form action="assignmentSubmit" method="get">
                                        <input type="hidden" name="assignmentId" value="${assignment.id}">
                                        <button type="submit" class="view-submissions-btn">Xem các bài nộp</button>
                                    </form>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table> 
        </div>
    </body>
</html>