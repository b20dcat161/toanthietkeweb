/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.UserDao;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import model.User;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author dbdtoan
 */
@WebServlet(name = "usersManager", urlPatterns = {"/usersManager"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50) // 50MB
public class UsersManagerServlet extends HttpServlet {

    private final UserDao userDao = new UserDao();

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
            out.println("<title>Servlet UsersManager</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UsersManager at " + request.getContextPath() + "</h1>");
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
        if ("teacher".equals(SessionUtils.getRole(request))) {
            try {
                List<User> students = userDao.getListStudentByPage(20, 0);
                request.setAttribute("students", students);
            } catch (Exception e) {
            }
            request.getRequestDispatcher("usersManager.jsp").forward(request, response);
        } else {
            response.sendRedirect("profile");
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
        if (!SessionUtils.checkLogin(request)) {
            SessionUtils.redirectToLogin(request, response);
        }
        String action = request.getParameter("action");
        System.out.println(action);
        if (null != action) {
            switch (action) {
                case "createUser":
                {
                    try {
                        createUser(request, response);
                    } catch (NoSuchAlgorithmException ex) {
                        Logger.getLogger(UsersManagerServlet.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InvalidKeySpecException ex) {
                        Logger.getLogger(UsersManagerServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                    break;

                case "createUsers": {
                    System.out.println("controller.UsersManagerServlet.doPost()+createusers");
                    try {
                        handleXMLUpload(request, response);
                    } catch (ParserConfigurationException | SAXException ex) {
                        Logger.getLogger(UsersManagerServlet.class.getName()).log(Level.SEVERE, null, ex);
                        request.setAttribute("errorMessage", "Error processing XML file: " + ex.getMessage());
                    }
                    break;
                }
                case "confirmUsers": {
                try {
                    checkConfirm(request, response);
                } catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(UsersManagerServlet.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvalidKeySpecException ex) {
                    Logger.getLogger(UsersManagerServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                }
                case "edit":
                    // bo xung them check user do co phai teacher khong
                    updateUser(request, response);
                    break;
                case "delete":
                    deleteUser(request, response);
                    break;
                case "exportUsers": 
                    try {
                        exportUsers(request, response);
                    } catch (ParserConfigurationException ex) {
                        Logger.getLogger(UsersManagerServlet.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (TransformerException ex) {
                        Logger.getLogger(UsersManagerServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                
                case "backupUsers": 
                    bakupUsers(request, response);
                    break;
                    
                case "restoreUsers":
                    restoreUsers(request, response);
                    break;

                default:
                    break;
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
    }// </editor-fold>

    private void createUser(HttpServletRequest request, HttpServletResponse response) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        String username = request.getParameter("username");
        String role = request.getParameter("role");
        String email = request.getParameter("email");
        String fullname = request.getParameter("fullname");
        String phone = request.getParameter("phone");
        boolean createUserResult = userDao.createUser(username, fullname, role, email, phone);
        if (createUserResult) {
            response.sendRedirect("usersManager");
        } else {
            response.sendRedirect("usersManager");
        }

    }

    private void handleXMLUpload(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, ParserConfigurationException, SAXException {

        Part filePart = request.getPart("xmlFile");
        if (filePart == null) {
            request.setAttribute("errorMessage", "No file selected.");
            request.getRequestDispatcher("/usersManager.jsp").forward(request, response);
            return;
        }

        try (InputStream fileContent = filePart.getInputStream()) {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

//            Các Feat của dbf mặc định có thể bị xxe, thay đổi các giá trị đó thành:
//            dbf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
//            dbf.setFeature("http://xml.org/sax/features/external-general-entities", false);
//            dbf.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
//            dbf.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", false);
//            dbf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            DocumentBuilder db = dbf.newDocumentBuilder();
            org.w3c.dom.Document doc = db.parse(new InputSource(fileContent));

            List<User> users = handleXmlUsers(doc);
            request.setAttribute("users", users);
            HttpSession session = request.getSession();
            session.setAttribute("users", users);

            request.getRequestDispatcher("confirmUsers.jsp").forward(request, response);
        } catch (ParserConfigurationException | SAXException e) {
            System.out.println("Error parsing XML: " + e.getMessage());
            Logger.getLogger(UsersManagerServlet.class.getName()).log(Level.SEVERE, "Error parsing XML", e);
            response.sendRedirect("usersManager");
        }
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String fullname = request.getParameter("fullname");
        String phone = request.getParameter("phone");
        if (userDao.updateUser(new User(null, username, null, null, fullname, email, phone))) {
            request.setAttribute("successMessage", "Users confirmed and created successfully.");
            response.sendRedirect("usersManager");
        } else {
            response.sendRedirect("usersManager");
        }
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        if (userDao.deleteUser(id)) {
            request.setAttribute("successMessage", "Users confirmed and created successfully.");
            response.sendRedirect("usersManager");
        } else {
            response.sendRedirect("usersManager");
        }
    }

    private List<User> handleXmlUsers(Document xmlDocument) {

        List<User> users = new ArrayList<>();
        NodeList userNodes = xmlDocument.getElementsByTagName("user");
        for (int i = 0; i < userNodes.getLength(); i++) {
            Node userNode = userNodes.item(i);
            if (userNode.getNodeType() == Node.ELEMENT_NODE) {
                Element userElement = (Element) userNode;
                String username = userElement.getElementsByTagName("username").item(0).getTextContent();
                String fullname = userElement.getElementsByTagName("fullname").item(0).getTextContent();
                String role = userElement.getElementsByTagName("role").item(0).getTextContent();
                String email = userElement.getElementsByTagName("email").item(0).getTextContent();
                String phone = userElement.getElementsByTagName("phone").item(0).getTextContent();
                System.out.println(username + fullname + role);
                users.add(new User(null, username, null, role, fullname, email, phone));
            }
        }
        return users;
    }

    private void checkConfirm(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, NoSuchAlgorithmException, InvalidKeySpecException {
        String confirm = request.getParameter("confirm");

        HttpSession session = request.getSession();
        List<User> users = (List<User>) session.getAttribute("users");

        if (users == null || users.isEmpty()) {
            request.setAttribute("errorMessage", "No users found to confirm.");
            response.sendRedirect("usersManager");
            return;
        }

        if ("yes".equals(confirm)) {
            for (User user : users) {
                System.out.println("Creating user: " + user.getFullname());
                userDao.createUser(user.getUsername(), user.getRole(), user.getFullname(), user.getEmail(), user.getPhone());
            }
            session.removeAttribute("users");
            request.setAttribute("successMessage", "Users confirmed and created successfully.");
        } else {
            System.out.println("User creation cancelled.");
            request.setAttribute("infoMessage", "User creation was cancelled.");
        }

//        response.sendRedirect("usersManager");
    }

    private void exportUsers(HttpServletRequest request, HttpServletResponse response) throws ParserConfigurationException, TransformerConfigurationException, TransformerException, IOException {
        List<User> users = userDao.getAllUsers();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.newDocument();
        Element rootElement = doc.createElement("users");
        doc.appendChild(rootElement);
        for (User user : users) {
            Element userElement = doc.createElement("user");
            Element usernameElement = doc.createElement("username");
            usernameElement.appendChild(doc.createTextNode(user.getUsername()));
            Element roleElement = doc.createElement("role");
            roleElement.appendChild(doc.createTextNode(user.getRole()));
            Element fullnameElement = doc.createElement("fullname");
            fullnameElement.appendChild(doc.createTextNode(user.getFullname()));
            Element emailElement = doc.createElement("email");
            emailElement.appendChild(doc.createTextNode(user.getEmail()));
            Element phoneElement = doc.createElement("phone");
            phoneElement.appendChild(doc.createTextNode(user.getPhone()));
            userElement.appendChild(usernameElement);
            userElement.appendChild(roleElement);
            userElement.appendChild(fullnameElement);
            userElement.appendChild(emailElement);
            userElement.appendChild(phoneElement);
            rootElement.appendChild(userElement);
        }
        response.setContentType("application/xml");
        response.setHeader("Content-Disposition", "attachment; filename=\"users_backup.xml\"");

        // Transform and write XML to response output
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(response.getOutputStream());
        transformer.transform(source, result);
    }

    private void bakupUsers(HttpServletRequest request, HttpServletResponse response) {
        List<User> users = userDao.getAllUsers();
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            oos = new ObjectOutputStream(baos);
            oos.writeObject(users);
            oos.close();
            response.setContentType("application/x-java-object");
            response.setHeader("Content-Disposition", "attachment;filename=users_backup.ser");
            ServletOutputStream out = response.getOutputStream();
            baos.writeTo(out);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    private void restoreUsers(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Part filePart = request.getPart("backupFile");
        if(filePart !=null){
            try {
                InputStream fileContent = filePart.getInputStream();
                ObjectInputStream in = new ObjectInputStream(fileContent);
                List<User> users = (List<User>) in.readObject();
                for(User user : users ){
                    userDao.updateUserById(user);
                }
                response.sendRedirect("usersManager");
            } catch (Exception e) {
                
            }
        }
    }
}

