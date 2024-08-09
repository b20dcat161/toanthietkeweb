/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author dbdtoan
 */
public class DBContext {
    protected Connection connection;

    public DBContext() {
        try {
            String DB_URL = "jdbc:mysql://db:3306/toanthietkeweb";
            String USER_NAME = "dbdtoan";
            String PASSWORD = "password";
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Drivers loaded!");
            connection = DriverManager.getConnection(DB_URL,USER_NAME,PASSWORD);
            System.out.println("connection thanh cong");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public static void main(String[] args) {
        DBContext dBContext = new DBContext();
    }
}
