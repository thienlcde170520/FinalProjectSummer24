/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller;

import Model.Game;
import Model.Gamers;
import Model.Genre;
import Model.Publishers;
import Model.Review;
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
           Game game = JavaMongo.getGameByGameID(gameid);
           ArrayList<Genre> genres  = JavaMongo.getGenresByGameID(gameid);
           ArrayList<Review> reviews = JavaMongo.getReviewByGame(game);
           Double rating = JavaMongo.getAverageRatingByGame(game);
           Publishers publisher = JavaMongo.getPublisherByGameId(gameid);
                
        HttpSession session = request.getSession();
        Gamers gamer = (Gamers) session.getAttribute("account");
        boolean hasBuy = JavaMongo.hasGamerBoughtGame(gamer.getId(), gameid);
        // Set attributes to be forwarded to the JSP
         request.setAttribute("hasBuy", hasBuy);
            request.setAttribute("game", game);
        request.setAttribute("genres", genres);
        request.setAttribute("reviews", reviews);
        request.setAttribute("rating", rating);
        request.setAttribute("publisher", publisher);

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
