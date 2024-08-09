/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 *
 * @author dbdtoan
 */
@WebServlet(name = "tools", urlPatterns = {"/tools"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1MB
        maxFileSize = 50 * 1024 * 1024, // 50MB
        maxRequestSize = 50 * 1024 * 1024 // 50MB
)
public class ToolsServlet extends HttpServlet {

    private static final String UPLOAD_DIRECTORY = "upload";
    private static final String DOWNLOAD_DIRECTORY = "download";

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
            out.println("<title>Servlet ToolsServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ToolsServlet at " + request.getContextPath() + "</h1>");
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
        if (!SessionUtils.checkLogin(request)) {
            SessionUtils.redirectToLogin(request, response);
            return;
        }
        String option = request.getParameter("option");
        if ("preview".equals(option)) {
            getDocuments(request, response);
        }
        request.getRequestDispatcher("tools.jsp").forward(request, response);
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
        convert(request, response);

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

    private void getDocuments(HttpServletRequest request, HttpServletResponse response) throws MalformedURLException, IOException, ServletException {
        String target = request.getParameter("target");

        try {
            URL url = new URL(target);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);
            if (responseCode == HttpURLConnection.HTTP_OK) {

                //            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//            String inputLine;
//            StringBuilder sb = new StringBuilder();
//
//            while ((inputLine = in.readLine()) != null) {
//                sb.append(inputLine);
//            }
//            in.close();
//            byte[] bytes = sb.toString().getBytes();
////            BinaInputStream os = connection.getInputStream();
//            response.setContentType("image/jpeg");
                String contentType = connection.getContentType();
                response.setContentType(contentType);
//            response.setHeader("Content-Disposition", "attachment; filename=\"image.jpg\"");
//            ServletOutputStream ou = response.getOutputStream();
////            os.toString()
//            ou.write(bytes);
                InputStream is = connection.getInputStream();
//            ServletOutputStream os = response.getOutputStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                byte[] buffer = new byte[5242880];
                int bytesRead = -1;
                while ((bytesRead = is.read(buffer)) != -1) {
                    baos.write(buffer, 0, bytesRead);
                }
                is.close();
                baos.flush();
//            os.close();
//            request.getSession().setAttribute("fileData", baos.toByteArray());
//            request.getSession().setAttribute("contentType", contentType);
                request.setAttribute("fileData", baos.toByteArray());
                request.setAttribute("contentType", contentType);
                System.out.println(contentType);
                request.getRequestDispatcher("tools.jsp").forward(request, response);
            } else {
                request.setAttribute("errMess", "Không thể kết nối tới tài nguyên của bạn");
            }

//            response.sendRedirect("displayContent.jsp");
        } catch (Exception e) {
            request.setAttribute("errMess", "Có lỗi xảy ra");
        }
        response.sendRedirect("tools");
    }

    private void convert(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;
        String downloadPath = getServletContext().getRealPath("") + File.separator + DOWNLOAD_DIRECTORY;

        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        File downloadDir = new File(downloadPath);
        if (!downloadDir.exists()) {
            downloadDir.mkdir();
        }

        // Lấy file đã upload
        Part filePart = request.getPart("mp4File");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        File uploadedFile = new File(uploadPath + File.separator + fileName);

        // Lưu file vào thư mục upload
        try (InputStream inputStream = filePart.getInputStream()) {
            Files.copy(inputStream, uploadedFile.toPath());
        }

        // Chuyển đổi MP4 sang M4A
        String outputFileName = fileName.substring(0, fileName.lastIndexOf('.')) + ".m4a";
        File outputFile = new File(downloadPath + File.separator + outputFileName);
        String ffmpegCommand = String.format("ffmpeg -i %s -vn -acodec copy -y %s",
                uploadedFile.getAbsolutePath(),
                outputFile.getAbsolutePath());
        try {
            Process process = Runtime.getRuntime().exec(ffmpegCommand);
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Có lỗi xảy ra trong quá trình chuyển đổi.");
            request.getRequestDispatcher("tools.jsp").forward(request, response);
            return;
        }
        if (uploadedFile.exists()) {
            uploadedFile.delete();
        }
        try {
            response.setContentType("audio/x-m4a");
            response.setHeader("Content-Disposition", "attachment;filename=" + outputFileName);
            FileInputStream inStream = new FileInputStream(outputFile);
            OutputStream outStream = response.getOutputStream();

            byte[] buffer = new byte[40960];
            int bytesRead = -1;

            while ((bytesRead = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }

            inStream.close();
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

//        // Tạo đường dẫn tải về
//        String downloadLink = request.getContextPath() + "/download/" + outputFileName;
//        request.setAttribute("downloadLink", downloadLink);
//        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

}
