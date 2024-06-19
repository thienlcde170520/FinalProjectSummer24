/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import static Controller.DriveQuickstart.APPLICATION_NAME;
import static Controller.DriveQuickstart.JSON_FACTORY;
import Model.Game;
import Model.Genre;
import Model.Publishers;
import Model.Users;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.Permission;
import jakarta.servlet.http.HttpSession;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 *
 * @author LENOVO
 */
@WebServlet(name = "UploadGame", urlPatterns = {"/UploadGame"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 500, // 500MB
        maxRequestSize = 1024 * 1024 * 500 // 500MB
)
public class UploadGame extends HttpServlet {

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
            
        ArrayList<Genre> Genres = (JavaMongo.getAllGenres());
        request.setAttribute("genres", Genres);
        request.getRequestDispatcher("UploadGame.jsp").forward(request, response);
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

        // Get form parameters
        String gameName = request.getParameter("gameName");
        String trailerLink = request.getParameter("trailerLink");
        String description = request.getParameter("description");
        String minCpu = request.getParameter("minCpu");
        String minRam = request.getParameter("minRam");
        String minGpu = request.getParameter("minGpu");
        String maxCpu = request.getParameter("maxCpu");
        String maxRam = request.getParameter("maxRam");
        String maxGpu = request.getParameter("maxGpu");
        String priceStr = request.getParameter("priceAmount");

        double price = 0;
        if (priceStr != null && !priceStr.trim().isEmpty()) {
            try {
                price = Double.parseDouble(priceStr.trim());

                // Now you have 'price' as a double
                // Use 'price' as needed, e.g., save to database or perform other operations
            } catch (NumberFormatException e) {
                e.printStackTrace(); // Log the exception or handle appropriately
                // Optionally, set a default value or show an error message to the user
            }
        }
        // Handling genre checkboxes (if multiple genres selected)
        String[] selectedGenres = request.getParameterValues("selectedGenres");

        // Initialize variables for file URLs
        String gameFileUrl = null;
        String gameAvatarUrl = null;

        try {
        // Upload game file to Google Drive if provided
         Part gameAvatarPart = request.getPart("gameAvatar"); // Assuming "gameAvatar" is the name of the file input field
        if (  gameAvatarPart != null && gameAvatarPart.getSize() > 0) {
            String mimeType = gameAvatarPart.getContentType();
            String fileName = gameName + "_avatar.jpg"; // Example: "MyGame_avatar.jpg"
            java.io.File uploadedAvatar = saveFileFromPart(gameAvatarPart, fileName);
            gameAvatarUrl = uploadFileToDrive(fileName, uploadedAvatar, mimeType);
        }
        Part gameFilePart = request.getPart("gameFile"); // Assuming "gameFile" is the name of the file input field
        if (gameName != null && !gameName.isEmpty() && gameFilePart != null && gameFilePart.getSize() > 0) {
            String mimeType = gameFilePart.getContentType();
            String fileName = gameName + ".zip"; // Example: "MyGame.zip"
            java.io.File uploadedFile = saveFileFromPart(gameFilePart, fileName);
            gameFileUrl = uploadFileToDrive(fileName, uploadedFile, mimeType);
        }

        // Upload game avatar file to Google Drive if provided
       

  

    } catch (GeneralSecurityException | IOException | ServletException e) {
        e.printStackTrace();
        response.getWriter().println("Failed to upload game files: " + e.getMessage());
    }

        String gameId = "game_" + generateRandomNumber();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String currentDate = dateFormat.format(new Date());
        Game game = new Game(
                gameId, // ID will be auto-generated by MongoDB
                gameName,
                price,
                currentDate, // Publish_day might be set by default or auto-generated
                0, // Initialize number of buyers to 0
                trailerLink,
                gameAvatarUrl,
                gameFileUrl,
                description,
                minCpu,
                minRam,
                minGpu,
                maxCpu,
                maxRam,
                maxGpu
        );
          HttpSession session = request.getSession();
         Publishers p = (Publishers) session.getAttribute("account");
        JavaMongo.publishGame(gameId, p.getId());
        JavaMongo.addGame(game);
        if (selectedGenres != null) {
            for (String genre : selectedGenres) {
                JavaMongo.addGenreToGame(gameId, genre);
            }
        }
        // Optionally, you can redirect to a success page or do other actions
        response.sendRedirect("Home.jsp");
    }

    private java.io.File saveFileFromPart(Part part, String fileName) throws IOException {
        // Determine the directory to save the file (temp directory)
        String tempDir = System.getProperty("java.io.tmpdir");

        // Construct the file path
        java.io.File file = new java.io.File(tempDir, fileName);

        // Create parent directories if they don't exist
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        // Write the input stream of the Part to the file
        try (InputStream input = part.getInputStream(); FileOutputStream output = new FileOutputStream(file)) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }

        } catch (IOException e) {
            // Handle or log the exception as needed
            throw e;
        }

        return file;
    }

    // Method to upload a file to Google Drive
    public String uploadFileToDrive(String fileName, java.io.File file, String mimeType)
            throws IOException, GeneralSecurityException {

        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        GoogleCredential credential = DriveQuickstart.getCredentials(HTTP_TRANSPORT);

        Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();

        // Create file metadata
        File fileMetadata = new File();
        fileMetadata.setName(fileName);

        // Create file content
        FileContent mediaContent = new FileContent(mimeType, file);

        // Upload file to Google Drive
        File uploadedFile = service.files().create(fileMetadata, mediaContent)
                .setFields("id")
                .execute();

        // Create permission for anyone to read
        Permission permission = new Permission()
                .setType("anyone")
                .setRole("reader");

        // Apply the permission to the uploaded file
        service.permissions().create(uploadedFile.getId(), permission).execute();

        // Return the thumbnail URL
        return "https://drive.google.com/thumbnail?id=" + uploadedFile.getId() + "&sz=w1000";
    }
    private static int generateRandomNumber() {
        Random random = new Random();
        return random.nextInt(100000); // Adjust as per your requirement for random number range
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
