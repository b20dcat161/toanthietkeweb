/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 *
 * @author dbdtoan
 */
public class SessionUtils {

    public static boolean checkLogin(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null && session.getAttribute("username") != null;
    }

    public static String getRole(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return (String) session.getAttribute("role");
    }

    public static String getId(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return (String) session.getAttribute("id");
    }

    public static void redirectToLogin(HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response)
            throws IOException {
        response.sendRedirect("login");
    }
}
