<%-- 
    Document   : displayContent
    Created on : Aug 1, 2024, 11:19:42 AM
    Author     : dbdtoan
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.Base64"%>
<%@ page import="java.nio.charset.StandardCharsets"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> <!-- Import JSTL functions --> 
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Hiển Thị Nội Dung Tải Về</title>
    <link rel="stylesheet" href="styles/styles.css">
    <style>
        .container {
            max-width: 800px;
            margin: 0 auto;
            padding: 20px;
            background-color: #fff;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            border-radius: 8px;
            text-align: center;
        }
        .preview-content {
            margin-top: 20px;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
            background-color: #f9f9f9;
            text-align: left;
            overflow-x: auto;
            white-space: pre-wrap;
            max-height: 500px;
        }
        .preview-icon {
            font-size: 100px;
            color: #888;
            display: inline-block;
            margin-top: 20px;
        }
        .preview-image {
            margin-top: 20px;
            max-width: 100%;
            height: auto;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Nội Dung Tệp Tải Về</h2>

        <c:choose>
   
            <c:when test="${not empty sessionScope.fileData && not empty sessionScope.contentType}">
                <c:set var="fileData" value="${sessionScope.fileData}" />
                <c:set var="contentType" value="${sessionScope.contentType}" />
                
                <c:choose>
                    <c:when test="${contentType.startsWith('text/')}">
                        <div class="preview-content">
                            <% 
                                // Convert byte array to String and escape XML
                                String fileData = new String((byte[])session.getAttribute("fileData"), StandardCharsets.UTF_8);
                                out.print(fn:escapeXml(fileData));
                            %>
                        </div>
                    </c:when>

                    <c:when test="${contentType.startsWith('image/')}">
                        <img class="preview-image" src="data:${contentType};base64,<%= base64Encode((byte[])session.getAttribute("fileData")) %>" alt="Image Preview" />
                    </c:when>

                    <c:otherwise>
                        <div class="preview-icon">&#128196;</div>
                        <p>Không thể hiển thị nội dung. Đây là một tệp kiểu khác.</p>
                    </c:otherwise>
                </c:choose>
            </c:when>
            <c:otherwise>
                <p>Không có nội dung để hiển thị.</p>
            </c:otherwise>
        </c:choose>
    </div>

    <%!
        // Method to base64 encode file data
        public String base64Encode(byte[] data) {
            return Base64.getEncoder().encodeToString(data);
        }
    %>
</body>
</html>
