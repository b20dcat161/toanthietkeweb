<%-- 
    Document   : assignSubmit
    Created on : Jul 26, 2024, 12:48:47 PM
    Author     : dbdtoan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
    <%@ include file = "header.jsp" %>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Danh sách bài nộp</title>
        <link rel="stylesheet" href="styles.css">
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

            .btn-download {
                padding: 10px 20px;
                background-color: #4CAF50;
                color: #fff;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                margin-top: 10px;
                display: inline-block;
                text-decoration: none;
            }

            .btn-download:hover {
                background-color: #45a049;
            }

            .view-submissions-btn {
                padding: 10px 20px;
                background-color: #3498db;
                color: #fff;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                margin-top: 10px;
                align-self: center;
                text-decoration: none;
                display: inline-block;
            }

            .view-submissions-btn:hover {
                background-color: #2980b9;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h2>Danh sách bài nộp</h2>

            <table>
                <thead>
                    <tr>
                        <th>Mã</th>
                        <!--<th>Student Name</th>-->
                        <th>Submitted File</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="submission" items="${submissions}">
                        <tr>
                            <td>${fn:escapeXml(submission.studentId)}</td>
                            <td>${fn:escapeXml(submission.filePath)}</td>
                            <td>
                                <a href="downloadSubmission?filename=${fn:escapeXml(submission.filePath)}" class="btn-download" download>
                                    Tải về
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <div style="text-align: center; margin-top: 20px;">
                <a href="assignments" class="view-submissions-btn">Trở lại</a>
            </div>
        </div>
    </body>
</html>
