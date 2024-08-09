/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Message;

/**
 *
 * @author dbdtoan
 */
public class MessageDao extends DBContext {

//    public List<Message> getAllMessage(String senderId, String receiverId) {
//        List<Message> messages = new ArrayList<>();
//        String query = "select * from messages where (senderId = ? and receiverId = ?) or (senderId = ? AND receiverId = ?) ORDER BY timestamp ASC;";
//        try {
//            PreparedStatement st = connection.prepareStatement(query);
//            st.setInt(1, Integer.parseInt(senderId));
//            st.setInt(2, Integer.parseInt(receiverId));
//            st.setInt(3, Integer.parseInt(receiverId));
//            st.setInt(4, Integer.parseInt(senderId));
//            System.out.println(st);
//            ResultSet rs = st.executeQuery();
//            while (rs.next()) {
//                Message message = new Message(
//                        rs.getString("id"),
//                        rs.getString("senderId"),
//                        rs.getString("receiverId"),
//                        rs.getString("content"),
//                        rs.getString("timestamp"));
//                messages.add(message);
//            }
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//        return messages;
//    }
    public List<Message> getAllMessage(String sender, String receiver) {
        List<Message> messages = new ArrayList<>();
        String query = "SELECT "
                + "m.id, "
                + "m.content, "
                + "m.timestamp, "
                + "sender.username AS sender_username, "
                + "receiver.username AS receiver_username "
                + "FROM messages m "
                + "JOIN users sender ON m.senderId = sender.id "
                + "JOIN users receiver ON m.receiverId = receiver.id "
                + "WHERE (sender.username = ? AND receiver.username = ?) "
                + "OR (sender.username = ? AND receiver.username = ?) "
                + "ORDER BY m.timestamp";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1, sender);
            st.setString(2, receiver);
            st.setString(3, receiver);
            st.setString(4, sender);

            System.out.println(st);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Message message = new Message(
                        rs.getString("id"),
                        rs.getString("sender_username"),
                        rs.getString("receiver_username"),
                        rs.getString("content"),
                        rs.getString("timestamp"));
                messages.add(message);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return messages;
    }

    public boolean sendMessage(String sender, String receiver, String content) {
        String query = "INSERT INTO messages (senderId, receiverId, content) VALUES (?, ?, ?)";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setInt(1, Integer.parseInt(sender));
            st.setInt(2, Integer.parseInt(receiver));
            st.setString(3, content);
            st.executeUpdate();
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    public Message getMessageById(String messageId) {
        String query = "SELECT * FROM messages WHERE id = ?";

        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setInt(1, Integer.parseInt(messageId));
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                return new Message(
                        rs.getString("id"),
                        rs.getString("senderId"),
                        rs.getString("receiverId"),
                        rs.getString("content"),
                        rs.getString("timestamp")
                );
            }
        } catch (Exception e) {
        }

        return null;
    }

    public boolean updateMessageContent(String messageId, String newContent) {
        String query = "UPDATE messages SET content = ? WHERE id = ?";

        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1, newContent);
            st.setInt(2, Integer.parseInt(messageId));
            st.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error updating message content: " + e.getMessage());
        }
        return false;
    }

    // Xóa tin nhắn
    public boolean deleteMessage(String messageId) {
        String query = "DELETE FROM messages WHERE id = ?";

        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setInt(1, Integer.parseInt(messageId));
            st.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error deleting message: " + e.getMessage());
        }
        return false;
    }

    public static void main(String[] args) {
        MessageDao messageDao = new MessageDao();

        List<Message> list = messageDao.getAllMessage("student1", "student2");
        for (Message i : list) {
            System.out.println(i.getSender());
            System.out.println(i.getContent());
        }

//       if (messageDao.sendMessage("1", "4", "hello world")){
//           System.out.println("oke");
//       }
    }

}
