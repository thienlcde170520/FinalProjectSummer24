/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller;

import static Common.CheckValid.CheckEmail;
import static Controller.DriveQuickstart.APPLICATION_NAME;
import static Controller.DriveQuickstart.JSON_FACTORY;
import static Controller.JavaMongo.updateProfile;
import DAO.GamerDAO;
import DAO.PublisherDAO;
import Model.Gamers;
import Model.Publishers;
import Model.Users;
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
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ASUS
 */
@WebServlet(name="UpdateProfileServlet", urlPatterns={"/UpdateProfileServlet"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 500, // 500MB
        maxRequestSize = 1024 * 1024 * 500 // 500MB
)
public class UpdateProfileServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
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
            out.println("<title>Servlet UpdateProfileServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateProfileServlet at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
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
        
        String newN = request.getParameter("newName");
        String newEM = request.getParameter("newEmail");
        String newDOB = request.getParameter("newDOB");
        String newP = request.getParameter("newPassWord");
        String conP = request.getParameter("confirmPass");

        String gamerAvatarUrl = null;
        
   
        // Upload game file to Google Drive if provided
         Part gamerAvatarPart = request.getPart("gamerAvatar"); // Assuming "gameAvatar" is the name of the file input field
        if (  gamerAvatarPart != null && gamerAvatarPart.getSize() > 0) {
            String mimeType = gamerAvatarPart.getContentType();
            String fileName = newN + "_avatar.jpg"; // Example: "MyGame_avatar.jpg"
            java.io.File uploadedAvatar = saveFileFromPart(gamerAvatarPart, fileName);
            try {
                gamerAvatarUrl = uploadImageToDrive(fileName, uploadedAvatar, mimeType);
            } catch (GeneralSecurityException ex) {
                Logger.getLogger(UpdateProfileServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        String regex = "^(?=.*[A-Za-z])(?=.*\\d).+$"; // yeu cau pass có it nhat 1 so 1 chu
        String emailCheck = "^[^ ]+@[^ ]+\\.[a-z]{2,3}$";
        if(!newEM.matches(emailCheck) || !newP.equals(conP) || !newP.matches(regex) || newP.length() < 5 || newDOB.isEmpty()){
            request.setAttribute("mess", "Invalid email or password!!!!");
            //response.sendRedirect("SignUp.jsp");

            request.getRequestDispatcher("UpdateProfile.jsp").forward(request, response);
        }else
        {        
//            Users sp = new Users();
//            if(newEM.isEmpty() || newEM == null){
//                newEM = as.getGmail();
//                sp = CheckWithId(newEM,sp.getId());          
//            }
            
            Users as = CheckEmail(newEM);
            
            if(as == null )
            {
                try{
                    HttpSession session = request.getSession();
                    Users u = (Users) session.getAttribute("account");
                   
                        if( u.getRole() == 3){
                           

                        updateProfile(u.getId(),newN,newEM,newP,gamerAvatarUrl,newDOB,u.getRole());

                        Gamers gamer = GamerDAO.getGamerByEmail(newEM);
                            if(gamer != null){
                                request.setAttribute("gamer", gamer);
                                request.getRequestDispatcher("profile.jsp").forward(request, response);                     
                            }else{
                                 try (PrintWriter out = response.getWriter()) {
                                    /* TODO output your page here. You may use following sample code. */
                                    out.println("<!DOCTYPE html>");
                                    out.println("<html>");
                                    out.println("<head>");
                                    out.println("<title>Servlet profileServlet</title>");  
                                    out.println("</head>");
                                    out.println("<body>");
                                    out.println("<h1>Servlet profileServlet at " + u.getRole()+ "</h1>");
                                    out.println("</body>");
                                    out.println("</html>");
                                }                 
                            }
                        }else if( u.getRole() ==2){

                             updateProfile(u.getId(),newN,newEM,newP,gamerAvatarUrl,newDOB,u.getRole());

                             Publishers pub = PublisherDAO.getPublisherByEmail(newEM);
                             if(pub != null){
                                 request.setAttribute("pub", pub);
                                 request.getRequestDispatcher("DisplayPublisher.jsp").forward(request, response);
                             }else{
                                 try (PrintWriter out = response.getWriter()) {
                                    /* TODO output your page here. You may use following sample code. */
                                    out.println("<!DOCTYPE html>");
                                    out.println("<html>");
                                    out.println("<head>");
                                    out.println("<title>Servlet profileServlet</title>");  
                                    out.println("</head>");
                                    out.println("<body>");
                                    out.println("<h1>Servlet profileServlet at " + u.getId()+ "</h1>");
                                    out.println("</body>");
                                    out.println("</html>");
                                }                 
                            }
                        }
                   

                
                
            
            }catch (Exception e) {

                    }
            }else{
                request.setAttribute("mess", "An Account is Exist!!!");
                     
                    //response.sendRedirect("SignUp.jsp");
                    request.getRequestDispatcher("UpdateProfile.jsp").forward(request, response);
            }
        }
        
        
        
    }
    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
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

}
