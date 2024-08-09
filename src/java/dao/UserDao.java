/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import model.User;

/**
 *
 * @author dbdtoan
 */
public class UserDao extends DBContext {
//    dang khong can su dung

    public User getUserByLogin(String username, String password) {
        String query = "Select * from users where username =? AND password=?";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1, username);
            st.setString(2, password);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new User(rs.getString("id"), username,
                        password, rs.getString("role"), rs.getString("fullname"), rs.getString("email"),
                        rs.getString("phone"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public Boolean validateUser(String username, String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String query = "Select * from users where username =? AND password=?";
        password = hashPassword(username, password);
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1, username);
            st.setString(2, password);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public List<User> getListtUsersByPage(int limit, int offset) {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users LIMIT ? OFFSET ?;";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setInt(1, limit);
            st.setInt(2, offset);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                User user = new User(rs.getString("id"), rs.getString("username"),
                        rs.getString("password"), rs.getString("role"), rs.getString("fullname"), rs.getString("email"),
                        rs.getString("phone"));
                users.add(user);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return users;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users;";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                User user = new User(rs.getString("id"), rs.getString("username"),
                        rs.getString("password"), rs.getString("role"), rs.getString("fullname"), rs.getString("email"),
                        rs.getString("phone"));
                users.add(user);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return users;
    }

    public List<User> getListStudentByPage(int limit, int offset) {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users WHERE role= 'student' LIMIT ? OFFSET ?;";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setInt(1, limit);
            st.setInt(2, offset);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                User user = new User(rs.getString("id"), rs.getString("username"),
                        rs.getString("password"), rs.getString("role"), rs.getString("fullname"), rs.getString("email"),
                        rs.getString("phone"));
                users.add(user);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return users;
    }

    public User getUserDetailByUsername(String username) {
        String query = "select id, username,role,fullname, email,phone from users where username =?;";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                return new User(rs.getString("id"), rs.getString("username"),
                        "", rs.getString("role"), rs.getString("fullname"), rs.getString("email"),
                        rs.getString("phone"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public boolean updateUser(User user) {
        String query = "UPDATE users SET fullname = ?, email = ?, phone = ? WHERE username = ?;";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1, user.getFullname());
            st.setString(2, user.getEmail());
            st.setString(3, user.getPhone());
            st.setString(4, user.getUsername());
            int rowsAffected = st.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateUserById(User user) {
        String query = "UPDATE users SET username =?, role= ?, fullname = ?, email = ?, phone = ? WHERE id = ?;";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1, user.getUsername());
            st.setString(2, user.getRole());
            st.setString(3, user.getFullname());
            st.setString(4, user.getEmail());
            st.setString(5, user.getPhone());
            st.setString(6, user.getId());
            int rowsAffected = st.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void changePassword(String username, String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String query = "UPDATE users SET password=? WHERE username = ?;";
        password = hashPassword(username, password);
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1, password);
            st.setString(2, username);
            st.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean createUser(String username, String fullname, String role, String email, String phone) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String query = "INSERT INTO users (username, password, role, fullname, email, phone) "
                + "VALUES (?, ?, ?,?,?,?);";
        String password = hashPassword(username, "123qweaA@");
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1, username);
            st.setString(2, password);
            st.setString(3, role);
            st.setString(4, fullname);
            st.setString(5, email);
            st.setString(6, phone);
            int rowsAffected = st.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteUser(String id) {
        String query = "DELETE FROM users WHERE id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setInt(1, Integer.parseInt(id));
            st.executeUpdate();
            int rowsAffected = st.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            System.out.println("Error deleting user: " + e.getMessage());
        }
        return false;

    }

    private String hashPassword(String password, String username) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] salt = username.getBytes();
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 256);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");

        byte[] hash = factory.generateSecret(spec).getEncoded();
        return Base64.getEncoder().encodeToString(hash);
    }

    public static void main(String[] args) {
        UserDao dao = new UserDao();
//        User user = dao.getUserByLogin("student1", "123456a@A");
//        if (user != null) {
//            System.out.println(user.getId());
//        }
//        List<User> users = dao.getAllUsers();
//        System.out.println(users.size());
//        for (User i : users) {
//            System.out.println(i);
//        }
////        user = dao.getUserDetailByUsername("student1");
////        dao.updateUser(user);
////        System.out.println(user.getFullname());
//        ObjectOutputStream oos = null;
//        ByteArrayOutputStream bao = new ByteArrayOutputStream();
//        try {
//            oos = new ObjectOutputStream(bao);
////            for (User i : users) {
////                oos.writeObject(i);
////            }
//            oos.writeObject(users);
//            oos.close();
//            System.out.println(bao);
//            System.out.println("Success...");
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//
//        try {
//            FileInputStream fileIn = new FileInputStream("/tmp/users_backup.ser");
//            ObjectInputStream in = new ObjectInputStream(fileIn);
////            User us = (User) in.readObject();
//            List<User> us = (List<User>) in.readObject();
//
//            System.out.println(us);
//            for (User u : us) {
//                System.out.println(u.getFullname());
//            }
//            in.close();
//            fileIn.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        try {
            System.out.println(dao.hashPassword("1", "student1"));

        } catch (Exception e) {
        }
    }
}
