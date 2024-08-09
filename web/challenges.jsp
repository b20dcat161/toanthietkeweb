<%-- 
    Document   : challenges.jsp
    Created on : Jul 26, 2024, 2:42:11 PM
    Author     : dbdtoan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
    <%@ include file="header.jsp" %> 
    <body>
        <div class="container">
            <h2>Câu đố</h2>
            <c:if test="${sessionScope.role eq 'teacher'}">
                <h3>Tạo câu đố</h3>
                <form action="uploadChallenge" method="post" enctype="multipart/form-data">
                    <label for="title">Gợi ý</label>
                    <input type="text" id="hint" name="hint" required>

                    <label for="file">File đáp án</label>
                    <input type="file" id="file" name="file" accept=".txt" required>

                    <input type="submit" value="Tải lên">
                </form>

                <c:if test="${not empty uploadMessage}">
                    <div class="${uploadMessage.contains('success') ? 'success' : 'alert'}">
                        ${uploadMessage}
                    </div>
                </c:if>
            </c:if>
            <h3>Danh sách câu đố</h3>
            <table>
                <thead>
                    <tr>
                        <th>Gợi ý</th>
                        <th>Đáp án</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="challenge" items="${challenges}">
                        <tr>
                            <td>${fn:escapeXml(challenge.hint)}</td>
                            <td>
                                <c:if test="${sessionScope.role == 'student'}">
                                     <form action="submitAns" method="get">
                                        <input type="hidden" id="challengeId" name="challengeId" value=${challenge.id}>
                                        <input type="text" id="ans" name="ans" required>
                                        <input type="submit" value="Gửi">
                                    </form>
                                </c:if>
                                 <c:if test="${sessionScope.role == 'teacher'}">
                                     ${fn:escapeXml(challenge.filename)}
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table> 
        </div>
    </body>
</html>
