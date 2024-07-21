/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller;

import DAO.GamerDAO;
import Model.Gamers;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashSet;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author ASUS
 */
@WebServlet(name="UpdateGamerByAdmin", urlPatterns={"/UpdateGamerByAdmin"})
public class UpdateGamerByAdmin extends HttpServlet {
   private static Set<String> generatedNames = new HashSet<>();
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String AvartaLink = "https://i.pinimg.com/736x/bc/43/98/bc439871417621836a0eeea768d60944.jpg";;
        String newN = generateRandomName();
        String UserId = request.getParameter("UserId"); 
        
            Gamers gamer = GamerDAO.getGamerByGamerId(UserId);
            if (gamer !=null){
                GamerDAO.updateDefaultGamer(newN,AvartaLink, UserId);
                String Email = gamer.getGmail();
                
                // Send mail
                
                final String from = "cyborggameplatform@gmail.com";
                            final String password ="qcwy silx xumu gsng";

                            // Get the session object
                            Properties props = new Properties();
                            props.put("mail.smtp.host","smtp.gmail.com");
                            props.put("mail.smtp.port","587");
                            props.put("mail.smtp.auth","true");
                            props.put("mail.smtp.starttls.enable","true");
                            Authenticator auth = new Authenticator() {
                                    @Override
                                    protected PasswordAuthentication getPasswordAuthentication() {
                                        return new PasswordAuthentication(from, password);
                                    }
                                };
                            // compose message
                            Session mailSession  = Session.getInstance(props, auth);
                            final String to = Email;// change accordingly
                            MimeMessage message = new MimeMessage(mailSession );
                            try {				
                                    message.setFrom(from);// change accordingly
                                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to,false));
                                    message.setSubject("Hello");
                                    message.setSentDate(new Date());
                                    message.setText("Hello, We decided to change your information because your name or image did not meet community standards." );
                                    // send message
                                    Transport.send(message);
                                    System.out.println("message sent successfully");
                            }
                            catch (Exception e) {
                                     try (PrintWriter out = response.getWriter()) {
                                            /* TODO output your page here. You may use following sample code. */
                                            out.println("<!DOCTYPE html>");
                                            out.println("<html>");
                                            out.println("<head>");
                                            out.println("<title>Servlet LoginServlet</title>");  
                                            out.println("</head>");
                                            out.println("<body>");
                                            out.println("<h1>Error at " + e.getMessage() + "</h1>");
                                            out.println("</body>");
                                            out.println("</html>");
                                        }
                            }        
            }
            //request.setAttribute("gamer", gamer);
            request.getRequestDispatcher("Home.jsp").forward(request, response);
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
        processRequest(request, response);       
    }
    
    private String generateRandomName() {
        Random random = new Random();
        String generatedName = null;
        do {
            int randomNumber = random.nextInt(1000000); // Generate a random number
            generatedName = "User_" + randomNumber;
        } while (generatedNames.contains(generatedName)); // Check if name already exists
        generatedNames.add(generatedName); // Mark the name as used
        return generatedName;
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
