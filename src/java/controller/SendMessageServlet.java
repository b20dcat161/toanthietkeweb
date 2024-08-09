/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.MessageDao;
import dao.UserDao;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import javax.sound.midi.Receiver;
import model.User;

/**
 *
 * @author dbdtoan
 */
@WebServlet(name = "sendMessage", urlPatterns = {"/sendMessage"})
public class SendMessageServlet extends HttpServlet {

    private MessageDao messageDao = new MessageDao();
    private UserDao userDao = new UserDao();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet SendMessageServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SendMessageServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String senderId = (String) session.getAttribute("id");
        if (senderId == null) {
            response.sendRedirect("login");
            return;
        }
        String receiverUsername = request.getParameter("receiver");
        String content = request.getParameter("content");
        System.out.println(receiverUsername);
        System.err.println(content);
        if (receiverUsername != null && content != null) {
            try {
                User receiver = userDao.getUserDetailByUsername(receiverUsername);
                messageDao.sendMessage(senderId,receiver.getId(),content);
            } catch (Exception e) {
                request.setAttribute("err", "Có lỗi xảy ra trong quá trình gửi tin nhắn");
            }
        }else request.setAttribute("err", "Vui lòng gửi đầy đủ nội dung");
        response.sendRedirect("userDetails?user="+receiverUsername);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
