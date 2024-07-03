/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller;

import DAO.GameDAO;
import DAO.PublisherDAO;
import Model.Game;
import Model.Publishers;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author LENOVO
 */
@WebServlet(name="BestSellerServlet", urlPatterns={"/BestSellerServlet"})
public class BestSellerServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
       ArrayList<Game> games = GameDAO.getAllGames();
       games.sort((g1, g2) -> Integer.compare(g2.getNumberOfBuyers(), g1.getNumberOfBuyers()));
       ArrayList<Publishers> publishers = PublisherDAO.getAllPublishers();
       request.setAttribute("games", games);
       // Sort publishers by the number of games sold in descending order
publishers.sort((p1, p2) -> Integer.compare(PublisherDAO.getNumberOfGameSoughtByPublisher(p2.getId()), PublisherDAO.getNumberOfGameSoughtByPublisher(p1.getId())));
request.setAttribute("publishers", publishers);

        request.getRequestDispatcher("BestSeller.jsp").forward(request, response);
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
     * @param req
     * @param resp
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
     @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Get the time period and metric from the form
            String timePeriod = req.getParameter("timeperiod");
            String metric = req.getParameter("metric");

            // Validate input
            if (timePeriod == null || metric == null) {
                throw new IllegalArgumentException("Time period and metric must be provided.");
            }

            // Get the most popular or profitable games based on the selected period and metric
            List<Game> gamesSort;
            switch (metric) {
                case "buyers":
                    gamesSort = GameDAO.getMostPopularGamesByPeriod(timePeriod);
                    break;
                case "profit":
                    gamesSort = GameDAO.getMostProfitableGamesByPeriod(timePeriod);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid metric specified. Valid metrics are: buyers, profit.");
            }

             ArrayList<Game> games = GameDAO.getAllGames();
       games.sort((g1, g2) -> Integer.compare(g2.getNumberOfBuyers(), g1.getNumberOfBuyers()));
       ArrayList<Publishers> publishers = PublisherDAO.getAllPublishers();
       req.setAttribute("games", games);
       // Sort publishers by the number of games sold in descending order
publishers.sort((p1, p2) -> Integer.compare(PublisherDAO.getNumberOfGameSoughtByPublisher(p2.getId()), PublisherDAO.getNumberOfGameSoughtByPublisher(p1.getId())));
req.setAttribute("publishers", publishers);
            req.setAttribute("gamesSort", gamesSort);
            req.getRequestDispatcher("BestSeller.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();  // Log the exception
            req.setAttribute("errorMessage", "An error occurred while processing the request.");
            req.getRequestDispatcher("/errorPage.jsp").forward(req, resp);  // Redirect to a generic error page
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

}
