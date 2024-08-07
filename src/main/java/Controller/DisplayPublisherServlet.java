/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller;

import DAO.GameDAO;
import DAO.PublisherDAO;
import DAO.ReviewDAO;
import Model.Game;
import Model.Publishers;
import Model.Review;
import Model.Users;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.concurrent.Flow.Publisher;

/**
 *
 * @author LENOVO
 */
@WebServlet(name="DisplayPublisherServlet", urlPatterns={"/DisplayPublisherServlet"})
public class DisplayPublisherServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
       String publisherName = request.getParameter("publisherName");
       Publishers publisher = PublisherDAO.getPublisherByName(publisherName);
       ArrayList<Game> games = GameDAO.getGamesByPublisherName(publisherName);
       ArrayList<Review> reviews = ReviewDAO.getReviewsByPublisherName(publisherName);
       request.setAttribute("publisher", publisher);
        request.setAttribute("games", games);
         request.setAttribute("reviews", reviews);
                       HttpSession session = request.getSession();
Users user = (Users) session.getAttribute("account");
             boolean isUpdateable = false; // Default to false
boolean isAdmin = false; // Default to false
if (user != null && user.getRole() == 1) { // Assuming role 1 means admin
    isAdmin = true;
    isUpdateable = true;
}
if (   user.getId() == null ? publisher.getId() == null : user.getId().equals(publisher.getId())  ){
     isUpdateable = true;
}     
request.setAttribute("isUpdateable", isUpdateable);
               request.setAttribute("isAdmin", isAdmin);
          
    // Forward the request to the single-game.jsp page
    request.getRequestDispatcher("PublisherProfile.jsp").forward(request, response);
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

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
