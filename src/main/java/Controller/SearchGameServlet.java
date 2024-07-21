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
@WebServlet(name = "SearchGameServlet", urlPatterns = {"/SearchGameServlet"})
public class SearchGameServlet extends HttpServlet {

   protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    String gameName = request.getParameter("searchKeyword") != null ? request.getParameter("searchKeyword") : "";
    String gamePublisher = request.getParameter("gamePublisher") != null ? request.getParameter("gamePublisher") : "";
    String year = request.getParameter("yearFilter") != null ? request.getParameter("yearFilter") : "";
    String priceAmount = request.getParameter("priceAmount") != null ? request.getParameter("priceAmount") : "";
    String priceCurrency = request.getParameter("priceCurrency") != null ? request.getParameter("priceCurrency") : "";
    String[] selectedGenres = request.getParameter("selectedGenres") != null ? request.getParameter("selectedGenres").split(",") : null;

    // New sorting parameters
    String sortBy = request.getParameter("sortBy") != null ? request.getParameter("sortBy") : "";
    String sortOrder = request.getParameter("sortOrder") != null ? request.getParameter("sortOrder") : "asc";

    ArrayList<Game> games = GameDAO.searchGames(gameName, gamePublisher, year, priceAmount, priceCurrency, selectedGenres, sortBy, sortOrder);
    ArrayList<Genre> genres = GenreDAO.getAllGenres();

    request.setAttribute("genres", genres);
    request.setAttribute("games", games);
    request.getRequestDispatcher("SearchResult.jsp").forward(request, response);
}

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}

