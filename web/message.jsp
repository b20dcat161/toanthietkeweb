<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Tin Nhắn</title>
        <link rel="stylesheet" href="styles/styles.css">
        <style>
            .chat-box {
                border: 1px solid #ddd;
                padding: 20px;
                margin: 20px 0;
                max-height: 400px;
                overflow-y: auto;
                background-color: #f9f9f9;
                border-radius: 5px;
            }
            .chat-message {
                margin-bottom: 15px;
                padding: 10px;
                border-radius: 5px;
                position: relative;
                word-wrap: break-word;
                box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            }
            .chat-message .timestamp {
                font-size: 0.8em;
                color: #888;
                position: absolute;
                bottom: 5px;
                right: 10px;
            }
            .chat-message.sender {
                text-align: right;
                background-color: #e0ffe0;
                border: 1px solid #b3ddb3;
            }
            .chat-message.receiver {
                text-align: left;
                background-color: #ffe0e0;
                border: 1px solid #ddb3b3;
            }
            .message-actions {
                margin-top: 10px;
            }
            .message-actions button {
                background-color: #3498db;
                color: white;
                border: none;
                border-radius: 5px;
                padding: 5px 10px;
                cursor: pointer;
                margin-right: 5px;
                transition: background-color 0.3s ease;
            }
            .message-actions button:hover {
                background-color: #2980b9;
            }
            textarea[name="content"] {
                width: 100%;
                height: 60px;
                border-radius: 5px;
                padding: 10px;
                border: 1px solid #ddd;
                box-sizing: border-box;
                margin-top: 10px;
            }
            .send-button {
                background-color: #4CAF50;
                color: white;
                border: none;
                border-radius: 5px;
                padding: 10px 20px;
                cursor: pointer;
                transition: background-color 0.3s ease;
                margin-top: 10px;
                display: block;
                width: 100%;
                text-align: center;
            }
            .send-button:hover {
                background-color: #45a049;
            }
            .message-input {
                width: calc(100% - 30px);
                border: 1px solid #ddd;
                border-radius: 5px;
                padding: 5px;
                margin-right: 10px;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h2 class="text-center">Tin Nhắn</h2>
            <div class="chat-box">
                <c:forEach var="message" items="${requestScope.messages}">
                    <div class="chat-message ${message.sender == sessionScope.username ? 'sender' : 'receiver'}">

                        <c:choose>
                            <c:when test="${sessionScope.username == message.sender}">
                                <div class="timestamp">${message.timestamp}</div>
                                <form action="editMessage" method="post" class="message-actions">
                                    <input type="hidden" name="messageId" value="${message.id}">
                                    <input type="hidden" name="receiver" value="${message.receiver}">
                                    
                                    <input type="text" name="newContent" class="message-input" value="${message.content}">
                                    <button type="submit">Lưu</button>
                                    <button type="button" onclick="document.location.href='deleteMessage?messageId=${message.id}'">Xóa</button>
                                </form>
                            </c:when>
                            <c:otherwise>
                                <div>
                                    <strong>${message.sender}</strong>: 
                                    <span>${message.content}</span>
                                </div>
                                <div class="timestamp">${message.timestamp}</div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </c:forEach>
            </div>
            <form action="sendMessage" method="post">
                <input type="hidden" name="receiver" value="${user.username}">
                <textarea name="content" placeholder="Nhập tin nhắn..."></textarea>
                <button type="submit" class="send-button">Gửi</button>
            </form>
        </div>
    </body>
</html>
