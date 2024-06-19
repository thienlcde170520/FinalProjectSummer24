/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller;

import Model.Bill;
import Model.Game;
import Model.Gamers;
import Model.Genre;
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
@WebServlet(name="GameDetailServlet", urlPatterns={"/GameDetailServlet"})
public class GameDetailServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    String gameid = request.getParameter("gameid");
    
    // Retrieve game details
    Game game = JavaMongo.getGameByGameID(gameid);
    if (game == null) {
        // Handle case where game is not found
        response.sendRedirect("error.jsp");
        return;
    }
    
    // Retrieve genres associated with the game
    ArrayList<Genre> genres = JavaMongo.getGenresByGameID(gameid);
    
    // Retrieve reviews for the game
    ArrayList<Review> reviews = JavaMongo.getReviewByGame(game);
    
    // Calculate average rating for the game
    Double rating = JavaMongo.getAverageRatingByGame(game);
    
    // Retrieve publisher details
    Publishers publisher = JavaMongo.getPublisherByGameId(gameid);
    
    // Get user session and role
    HttpSession session = request.getSession();
    Users user = (Users) session.getAttribute("account");
    
    if (user == null) {
        // Handle case where user is not logged in
        response.sendRedirect("login.jsp");
        return;
    }
    
    // Check user role
    int role = user.getRole();
    
    boolean hasBuy = false;
    boolean isRefundable = false;
    Bill bill = null;
    
    if (role == 3) { // Gamer role
        Gamers gamer = (Gamers) session.getAttribute("account");
        
        // Check if gamer has bought the game
        hasBuy = JavaMongo.hasGamerBoughtGame(gamer.getId(), gameid);
        
        if (hasBuy) {
            // Retrieve bill details
            bill = JavaMongo.getBillByGameIDAndGamerID(gameid, gamer.getId());
            
            // Check if the purchase is refundable
            isRefundable = JavaMongo.isRefundable(bill);
        }
    }
    
    // Set attributes to be forwarded to the JSP
    request.setAttribute("bill", bill);
    request.setAttribute("isRefundable", isRefundable);
    request.setAttribute("hasBuy", hasBuy);
    request.setAttribute("game", game);
    request.setAttribute("genres", genres);
    request.setAttribute("reviews", reviews);
    request.setAttribute("rating", rating);
    request.setAttribute("publisher", publisher);
    
    // Forward the request to the single-game.jsp page
    request.getRequestDispatcher("single-game.jsp").forward(request, response);
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
