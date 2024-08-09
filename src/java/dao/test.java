/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package dao;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.List;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 *
 * @author dbdtoan
 */
public class test {

    private static void testHash() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String password = "123456a@A2";
        SecureRandom random = new SecureRandom();
        String username = "teacher1";
        byte[] salt = username.getBytes();

        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 256);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");

        byte[] hash = factory.generateSecret(spec).getEncoded();
        System.out.println(hash.toString());
        System.out.println(Base64.getEncoder().encodeToString(hash));

    }

    private static void testProcessbuider(String ls) throws IOException, InterruptedException {


        String mp4FilePath = "$(ls)";
        System.out.println(mp4FilePath);

        String m4aFilePath = mp4FilePath.replace(".mp4", ".m4a");
        System.out.println(m4aFilePath);
        
        ProcessBuilder processBuilder = new ProcessBuilder("ls", "-l", mp4FilePath, m4aFilePath);
        List<String> cmdlist = processBuilder.command();
        for (String c : cmdlist){
            System.out.println(c);
        }
        
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
        System.out.println("tested");
        process.waitFor();
    }

    /**
     * @param args the command line arguments
     */
    public void testHttpUrlConnect() {
        //        String command = "curl -X GET https://www.google.com";
//        ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
//        processBuilder.directory(new File("/tmp/"));
//        Process process = processBuilder.start();
//        InputStream inputStream = process.getInputStream();
//        System.out.println(inputStream.toString());
//        int exitCode = process.exitValue();
        try {
            URL url = new URL("https://podcasts.ceu.edu/sites/podcasts.ceu.edu/files/sample.doc");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

//            connection.setRequestProperty("Accept", "application/json");
            int responseCode = connection.getResponseCode();
            String content_type = connection.getContentType();

            System.out.println("Response Code: " + responseCode);
            System.out.println(content_type);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
//                in.close();

                System.out.println("Response: " + response.toString());
                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                byte[] buffer = new byte[5242880];
                int bytesRead = -1;
                InputStream is = connection.getInputStream();
                while ((bytesRead = is.read(buffer)) != -1) {
                    baos.write(buffer, 0, bytesRead);
                }
                System.out.println(baos.toString());

            } else {
                System.out.println("GET request failed.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, InterruptedException {
//        testHash();
//        String in = "123;";
//        String out =" 456";
//        String ffmpegCommand = String.format("ffmpeg -i %s -vn -acodec copy -y %s",
//                in,
//                out);
//        System.out.println(ffmpegCommand);
        testProcessbuider("ls");
    }
}
