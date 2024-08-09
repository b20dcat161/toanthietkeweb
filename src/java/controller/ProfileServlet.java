package controller;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
import dao.UserDao;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;

/**
 *
 * @author dbdtoan
 */
@WebServlet(name = "profile", urlPatterns = {"/profile"})
public class ProfileServlet extends HttpServlet {

    private UserDao dao = new UserDao();

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
            out.println("<title>Servlet ProfileServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProfileServlet at " + request.getContextPath() + "</h1>");
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
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        System.out.println("profile:"+username);
        User currentUser = dao.getUserDetailByUsername(username);
        if (currentUser != null) {
            request.setAttribute("user", currentUser);
            request.getRequestDispatcher("profile.jsp").forward(request, response);
        } else {
            System.out.println("session is null!");
            response.sendRedirect("login.jsp");
        }
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
        String username = (String) session.getAttribute("username");
        User currentUser = dao.getUserDetailByUsername(username);
        if (username == null) {
            response.sendRedirect("login");
            return;
        }
        String acction = request.getParameter("action");
        if (acction.equals("updateProfile")) {
            String email = request.getParameter("email");
            String fullname = request.getParameter("fullname");
            String phone = request.getParameter("phone");
            currentUser.setEmail(email);
            currentUser.setFullname(fullname);
            currentUser.setPhone(phone);
            System.out.println("fullname");
            dao.updateUser(currentUser);
        } else if (acction.equals("changePassword")) {
            String currentPassword = request.getParameter("currentPassword");
            try {
                if (dao.validateUser(username, currentPassword)) {
                    String newPassword = request.getParameter("newPassword");
                    String confirmPassword = request.getParameter("confirmPassword");
                    if (newPassword.equals(confirmPassword)) {
                        try {
                            dao.changePassword(username, newPassword);
                            request.setAttribute("Mess", "Thay đổi mật khẩu thành công");
                        } catch (Exception e) {
                            request.setAttribute("Mess", "Có lỗi xảy ra khi thay đổi mật khẩu, vui lòng thử lại");
                        }
                    } else {
                        request.setAttribute("Mess", "Mật khẩu mới và mật khẩu xác nhận không khớp");
                    }
                } else {
                    request.setAttribute("Mess", "Mật khẩu hiện tại không đúng.");
                }
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(ProfileServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvalidKeySpecException ex) {
                Logger.getLogger(ProfileServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        request.setAttribute("user", currentUser);
        request.getRequestDispatcher("profile.jsp").forward(request, response);
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
