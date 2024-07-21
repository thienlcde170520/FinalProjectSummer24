/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller;

import DAO.GamerDAO;
import DAO.PublisherDAO;
import Model.Gamers;
import Model.Publishers;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author LENOVO
 */
@WebServlet(name="SearchUserServlet", urlPatterns={"/SearchUserServlet"})
public class SearchUserServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
          doPost(request, response);
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
     protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nameFilter = request.getParameter("nameFilter");
        String roleFilter = request.getParameter("roleFilter");

        // Log the received parameters for debugging
        System.out.println("Name Filter: " + nameFilter);
        System.out.println("Role Filter: " + roleFilter);

        List<Gamers> gamers = new ArrayList<>();
        List<Publishers> publishers = new ArrayList<>();

        // Implement the search logic based on the role filter
        if ("Gamer".equalsIgnoreCase(roleFilter)) {
            // Search gamers based on the name filter
            gamers = GamerDAO.searchGamersByName(nameFilter);
        } else if ("Publisher".equalsIgnoreCase(roleFilter)) {
            // Search publishers based on the name filter
            publishers = PublisherDAO.getAllPublishers().stream()
                .filter(p -> p.getName().toLowerCase().contains(nameFilter.toLowerCase()))
                .collect(Collectors.toList());
        }

        // Set the search results as request attributes
        request.setAttribute("gamers", gamers);
        request.setAttribute("publishers", publishers);

        // Forward the request back to the JSP page
        request.getRequestDispatcher("ManageUser.jsp").forward(request, response);
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
