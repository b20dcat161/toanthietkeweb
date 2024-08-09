<%-- 
    Document   : showContent
    Created on : Jul 26, 2024, 4:41:12 PM
    Author     : dbdtoan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <div class="container">
            <h2>Challenge Content</h2>
            <pre>${fn:escapeXml(content)}</pre>
            <a href="challenges" class="btn-download">Back to Challenges</a>
        </div>
    </body>
</html>
