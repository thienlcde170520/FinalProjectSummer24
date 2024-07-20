/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller;

import static Controller.DriveQuickstart.APPLICATION_NAME;
import static Controller.DriveQuickstart.JSON_FACTORY;
import static Controller.UploadGame.isGameNameExists;
import DAO.GameDAO;
import DAO.GenreDAO;
import Model.Game;
import Model.Genre;
import Model.Publishers;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.Permission;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author LENOVO
 */
@WebServlet(name="UpdateGameServlet", urlPatterns={"/UpdateGameServlet"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 500, // 500MB
        maxRequestSize = 1024 * 1024 * 500 // 500MB
)
public class UpdateGameServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String gameId = request.getParameter("gameId");
        ArrayList<Genre> inlusiveGenres = GenreDAO.getGenresByGameID(gameId);
        ArrayList<Genre> exclusiveGenres = GenreDAO.getExcludeGenresByGameId(gameId);
        request.setAttribute("inlusiveGenres", inlusiveGenres);
        request.setAttribute("exclusiveGenres", exclusiveGenres);
        request.setAttribute("game", GameDAO.getGameByGameID(gameId));
        request.getRequestDispatcher("UpdateGame.jsp").forward(request, response);
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
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
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    // Retrieve parameters from the form
    String gameId = request.getParameter("gameId");
    Game game = GameDAO.getGameByGameID(gameId); // Retrieve the existing game by its ID

    // Update game details from form inputs
    String gameName = getParameterOrDefault(request, "gameName", game.getName());
    String trailerLink = getParameterOrDefault(request, "trailerLink", game.getLinkTrailer());
    String description = getParameterOrDefault(request, "description", game.getDescription());
    String minCpu = getParameterOrDefault(request, "minCpu", game.getMinimumCPU());
    String minRam = getParameterOrDefault(request, "minRam", game.getMinimumRAM());
    String minGpu = getParameterOrDefault(request, "minGpu", game.getMinimumGPU());
    String maxCpu = getParameterOrDefault(request, "maxCpu", game.getMaximumCPU());
    String maxRam = getParameterOrDefault(request, "maxRam", game.getMaximumRAM());
    String maxGpu = getParameterOrDefault(request, "maxGpu", game.getMaximumGPU());
    String priceStr = getParameterOrDefault(request, "priceAmount", Double.toString(game.getPrice()));

    double price = game.getPrice();
    if (priceStr != null && !priceStr.trim().isEmpty()) {
        try {
            price = Double.parseDouble(priceStr.trim());
        } catch (NumberFormatException e) {
            e.printStackTrace(); // Log the exception or handle appropriately
            // Optionally, set a default value or show an error message to the user
        }
    }
  
    // Handle file uploads for game avatar and game file
    String gameFileUrl = game.getGameLink(); // Initialize with existing URL
    String gameAvatarUrl = game.getAvatarLink(); // Initialize with existing URL
if (isGameNameExists(gameName)){
            String message = "Already have game with the same name";
            request.setAttribute("message", message);
             request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    try {
        Part gameAvatarPart = request.getPart("gameAvatar");
        if (gameAvatarPart != null && gameAvatarPart.getSize() > 0) {
            String mimeType = gameAvatarPart.getContentType();
            String fileName = gameName + "_avatar.jpg";
            java.io.File uploadedAvatar = saveFileFromPart(gameAvatarPart, fileName);
            gameAvatarUrl = uploadImageToDrive(fileName, uploadedAvatar, mimeType);
        }

        Part gameFilePart = request.getPart("gameFile");
        if (gameFilePart != null && gameFilePart.getSize() > 0) {
            String mimeType = gameFilePart.getContentType();
            String fileName = gameName + ".zip";
            java.io.File uploadedFile = saveFileFromPart(gameFilePart, fileName);
            gameFileUrl = uploadFileToDrive(fileName, uploadedFile, mimeType);
        }
    } catch (GeneralSecurityException | IOException | ServletException e) {
        e.printStackTrace();
        response.getWriter().println("Failed to upload game files: " + e.getMessage());
        return; // Exit the method if file upload fails
    }

    // Update the Game object with new details
   // Update the Game object with new details if they are not null
if (gameName != null) {
    game.setName(gameName);
}
if (trailerLink != null) {
    game.setLinkTrailer(trailerLink);
}
if (description != null) {
    game.setDescription(description);
}
if (minCpu != null) {
    game.setMinimumCPU(minCpu);
}
if (minRam != null) {
    game.setMinimumRAM(minRam);
}
if (minGpu != null) {
    game.setMinimumGPU(minGpu);
}
if (maxCpu != null) {
    game.setMaximumCPU(maxCpu);
}
if (maxRam != null) {
    game.setMaximumRAM(maxRam);
}
if (maxGpu != null) {
    game.setMaximumGPU(maxGpu);
}
if (price <=0 ) {
    game.setPrice(price);
}
if (gameAvatarUrl != null) {
    game.setAvatarLink(gameAvatarUrl);
}
if (gameFileUrl != null) {
    game.setGameLink(gameFileUrl);
}


    // Update the game in the database
    GameDAO.updateGame(game);

    // Handle genres (inclusive and exclusive)
    String[] inclusiveGenres = request.getParameterValues("incluGenres");
    String[] exclusiveGenres = request.getParameterValues("excluGenres");

    // Add inclusive genres
    if (inclusiveGenres != null) {
        for (String genre : inclusiveGenres) {
            GenreDAO.addGenreToGame(gameId, genre);
        }
    }

    // Add exclusive genres
    if (exclusiveGenres != null) {
        for (String genre : exclusiveGenres) {
            GenreDAO.exclusiveGenreToGame(gameId, genre);
        }
    }

    // Optionally, redirect to a success page or perform other actions
    response.sendRedirect("Home.jsp");
}

// Utility method to get a parameter or a default value if the parameter is null or empty
private String getParameterOrDefault(HttpServletRequest request, String paramName, String defaultValue) {
    String paramValue = request.getParameter(paramName);
    return (paramValue != null && !paramValue.trim().isEmpty()) ? paramValue : defaultValue;
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
   public String uploadImageToDrive(String fileName, java.io.File file, String mimeType)
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

    // Return the regular file URL
    return "https://drive.google.com/file/d/" + uploadedFile.getId() + "/view?usp=sharing";
}
 

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
