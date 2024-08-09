package controller;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author dbdtoan
 */
public class FileUtils {

    public static String uploadFile(HttpServletRequest request, String uploadPath, String part, boolean uuid) {
        try {
            Part filePart = request.getPart(part);
            String fileName = extractFileName(filePart);
            if(uuid) fileName = UUID.randomUUID().toString() + "_" + fileName;
            String filePath = uploadPath + fileName;
            filePart.write(filePath);
            return fileName;
        } catch (ServletException | IOException e) {
        }
        return null;
    }

    public static String extractFileName(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        String[] items = contentDisposition.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return "";
    }
    public static String getFileContent(String filePath) throws IOException{
        return  new String(Files.readAllBytes(Paths.get(filePath)));
    }
}
