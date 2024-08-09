<%-- 
    Document   : tools
    Created on : Aug 1, 2024, 1:42:44 PM
    Author     : dbdtoan
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.Base64" %>
<%@ page import="java.nio.charset.StandardCharsets" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Công cụ</title>
        <link rel="stylesheet" href="styles/styles.css">
        <style>
            .container {
                max-width: 800px;
                margin: 50px auto;
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
            .iframe{
                width: 800;
                height: 500;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <form class="convert-form" action="tools" method="post" enctype="multipart/form-data">
                <h3>MP4 to M4A</h3>
                <input type="hidden" name="option" value="convert">
                <input type="file" name="mp4File" accept=".mp4" required>
                <button type="submit" class="submit-btn">Convert</button>
            </form>

            <form class="preview-form" action="tools" method="get" enctype="multipart/form-data">
                <h3>Preview</h3>
                <input type="hidden" name="option" value="preview">
                <input type="url" name="target" />
                <button type="submit" class="submit-btn">Preview</button>
            </form>

            <c:choose>
                <c:when test="${not empty requestScope.fileData && not empty requestScope.contentType}">
                    <c:set var="fileData" value="${requestScope.fileData}" />
                    <c:set var="contentType" value="${requestScope.contentType}" />

                    <c:choose>
                        <c:when test="${contentType.startsWith('image/')}">
                            <img class="preview-image" src="data:${contentType};base64,<%= base64Encode((byte[])request.getAttribute("fileData")) %>" alt="Image Preview" />
                        </c:when>
                        <c:when test="${contentType == 'application/pdf'}">
                            <div class="preview-content">
                                <iframe src="data:${contentType};base64,<%= base64Encode((byte[])request.getAttribute("fileData")) %>" height="800" width="500"></iframe>
                            </div>
                        </c:when>
                        <c:when test="${contentType.startsWith('application/')}">
                            <div class="preview-content">
                                <iframe src="data:${contentType};base64,<%= base64Encode((byte[])request.getAttribute("fileData")) %>" height="800" width="500"></iframe>
                            </div>
                        </c:when>
                        <c:otherwise>   
                            <div class="preview-icon">&#128196;</div>
                            <p>Không thể hiển thị nội dung. Đây là một tệp kiểu khác.</p>
                        </c:otherwise>
                    </c:choose>
                </c:when>
                <c:otherwise>
                    <p>IMG, PDF.</p>
                </c:otherwise>
            </c:choose>
            <c:if test="${not empty errMess}">
                <p class="err">${errMess}</p>
            </c:if>
        </div>

        <%!

            public String base64Encode(byte[] data) {
                return Base64.getEncoder().encodeToString(data);
            }
        %>
    </body>
</html>
