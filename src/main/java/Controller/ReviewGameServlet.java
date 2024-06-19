/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller;

import Model.Gamers;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author LENOVO
 */
@WebServlet(name="ReviewGameServlet", urlPatterns={"/ReviewGameServlet"})
public class ReviewGameServlet extends HttpServlet {
   
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
            out.println("<title>Servlet ReviewGameServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ReviewGameServlet at " + request.getContextPath () + "</h1>");
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve parameters from the request
        String reviewGameId = request.getParameter("reviewGameId");
        String reviewGamerId = request.getParameter("reviewGamerId");

        // Validate parameters (make sure they are not null or empty)
        if (reviewGameId != null && !reviewGameId.isEmpty() && reviewGamerId != null && !reviewGamerId.isEmpty()) {
            // Call a method to delete the review based on the IDs
             JavaMongo.deleteReview( reviewGamerId, reviewGameId);
              
            request.getRequestDispatcher("Home.jsp").forward(request, response);
            
        }
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
          HttpSession session = request.getSession();
        Gamers gamer = (Gamers) session.getAttribute("account");
        String gamerId = gamer.getId();
        String gameId = request.getParameter("gameId");
        Double rating = Double.parseDouble(request.getParameter("rating"));
        String reviewDescription = request.getParameter("review");
         JavaMongo.addReview(gamerId, gameId, rating, reviewDescription);

        // Redirect back to the page where the form was submitted from
        response.sendRedirect("Home.jsp");
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
