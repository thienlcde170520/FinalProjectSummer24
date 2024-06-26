/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller;

import DAO.GameDAO;
import DAO.GenreDAO;
import Model.Game;
import Model.Genre;
import com.mongodb.client.model.Filters;
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
@WebServlet(name="SearchGameServlet", urlPatterns={"/SearchGameServlet"})
public class SearchGameServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
          String gameName = request.getParameter("searchKeyword") != null
        ? request.getParameter("searchKeyword")
        : "";
        String gamePublisher = request.getParameter("gamePublisher")!= null
        ? request.getParameter("gamePublisher")
        : "";
        
        String year = request.getParameter("yearFilter")!= null
        ? request.getParameter("yearFilter")
        : "";
        String priceAmount = request.getParameter("priceAmount")!= null
        ? request.getParameter("priceAmount")
        : "";
        String priceCurrency = request.getParameter("priceCurrency")!= null
        ? request.getParameter("priceCurrency")
        : "";
      String[] selectedGenres = request.getParameter("selectedGenres") != null
            ? request.getParameter("selectedGenres").split(",")
            : null;
        
      ArrayList<Game> games = GameDAO.searchGames(gameName, gamePublisher, year, priceAmount, priceCurrency, selectedGenres);
        ArrayList<Genre> genres = GenreDAO.getAllGenres();
        
        request.setAttribute("genres", genres);
        request.setAttribute("games", games);
        request.getRequestDispatcher("SearchResult.jsp").forward(request, response);
            
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
       processRequest(request,response);
           
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
        processRequest(request,response);
    
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
