/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.AssignmentDao;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.util.UUID;

/**
 *
 * @author dbdtoan
 */
@WebServlet(name = "uploadAssignment", urlPatterns = {"/uploadAssignment"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50) // 50MB
public class UploadAssignmentServlet extends HttpServlet {

    private final AssignmentDao assignmentDao = new AssignmentDao();
    private static final long serialVersionUID = 1L;

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
        if (!SessionUtils.checkLogin(request)) {
            SessionUtils.redirectToLogin(request, response);
            return;
        }
        if ("teacher".equals(SessionUtils.getRole(request))) {
            String title = request.getParameter("title");
            String uploadPath = getServletContext().getRealPath("/" + "assignments" + File.separator);
            try {
                Part filePart = request.getPart("file");
                String fileName = extractFileName(filePart);
                System.out.println(fileName);

                fileName = UUID.randomUUID().toString() + "_" + fileName;
                System.out.println(fileName);

                String filePath = uploadPath + fileName;
                System.out.println("path" + filePath);
                filePart.write(filePath);
                System.out.println("ghi oke");
                assignmentDao.saveAssignment(SessionUtils.getId(request), title, fileName);
                request.setAttribute("resultMess", "");
                response.sendRedirect("assignments");
            } catch (ServletException | IOException e) {
            }
        }
        if ("student".equals(SessionUtils.getRole(request))) {
            String assId = request.getParameter("assignmentId");
            String uploadPath = getServletContext().getRealPath("/" + "assignments/submits" + File.separator);
            try {
                Part filePart = request.getPart("file");
                String fileName = extractFileName(filePart);
                fileName = UUID.randomUUID().toString() + "_" + fileName;

                String filePath = uploadPath + fileName;
                filePart.write(filePath);
                
                assignmentDao.saveSubmit(assId,SessionUtils.getId(request),fileName);
                request.setAttribute("resultMess", "");
                response.sendRedirect("assignments");
            } catch (ServletException | IOException e) {
            }
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }

    private String extractFileName(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        String[] items = contentDisposition.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return "";
    }

}
