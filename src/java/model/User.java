/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;

/**
 *
 * @author dbdtoan
 */
public class User implements Serializable{
    private static final long serialVersionUID = 1L;
    private String id,username,password,role,fullname,email,phone;

    public User() {
    }

    public User(String id, String username, String password, String role, String fullname, String email, String phone) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.fullname = fullname;
        this.email = email;
        this.phone = phone;
    }
    

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

//    public String getPassword() {
//        return password;
//    }

    public String getFullname() {
        return fullname;
    }

    public String getRole() {
        return role;
    }
    
    public String getPhone() {
        return phone;
    }
      
    public String getEmail() {
        return email;
    }

//    public void setPassword(String password) {
//        this.password = password;
//    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "User@[id="+id+", username="+username
                +",email="+email+",phone="+phone+",fullname="+fullname
                +",role="+role+"]";
    }
    

}
