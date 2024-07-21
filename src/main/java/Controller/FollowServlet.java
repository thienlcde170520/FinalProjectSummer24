/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller;

import DAO.FollowDAO;
import DAO.GameDAO;
import DAO.GamerDAO;
import Model.Follow;
import Model.Game;
import Model.Gamers;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 *
 * @author LENOVO
 */
@WebServlet(name="FollowServlet", urlPatterns={"/FollowServlet"})
public class FollowServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
            String gamerID = request.getParameter("gamerid");
            ArrayList<Follow> follows = FollowDAO.getAllFollowsByGamerId(gamerID);
            ArrayList<Game> games = new ArrayList<>();
            for (Follow f : follows){
                games.add(GameDAO.getGameByFollow(f));
            }
            request.setAttribute("games", games);
            request.setAttribute("follows", follows);
    request.getRequestDispatcher("FollowGame.jsp").forward(request, response);
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
           String gameId = request.getParameter("gameId");
        String gamerId = request.getParameter("gamerId");
        String action = request.getParameter("action");

        Game game = GameDAO.getGameByGameID(gameId);  // Assuming you have a method to get a game by ID
        Gamers gamer = GamerDAO.getGamerByGamerId(gamerId);  // Assuming you have a method to get a gamer by ID
        if ("follow".equals(action)) {
            FollowDAO.followGame(game, gamer);
        } else if ("unfollow".equals(action)) {
   FollowDAO.unfollowGame(game, gamer);  // Assuming you have this method implemented
        }
           
    request.getRequestDispatcher("Home.jsp").forward(request, response);
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
