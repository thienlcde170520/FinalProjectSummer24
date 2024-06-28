/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller;

import DAO.GameDAO;
import DAO.TransactionBillDAO;
import Model.Game;
import Model.Gamers;
import Model.Users;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Random;

/**
 *
 * @author LENOVO
 */
@WebServlet(name="PayProcessServlet", urlPatterns={"/PayProcessServlet"})
public class PayProcessServlet extends HttpServlet {
   
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
            out.println("<title>Servlet PayProcessServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PayProcessServlet at " + request.getContextPath () + "</h1>");
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
        String gameId = request.getParameter("gameId");
        Game game = GameDAO.getGameByGameID(gameId);
        HttpSession session = request.getSession();
        Gamers gamer = (Gamers) session.getAttribute("account");
        
        // Set attributes to be forwarded to the JSP
        request.setAttribute("game", game);
        request.setAttribute("gamer", gamer);

        // Forward to paymentConfirmation.jsp
        request.getRequestDispatcher("paymentConfirmation.jsp").forward(request, response);
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
    double gamePrice = Double.parseDouble(request.getParameter("gamePrice"));
    String buyTime = request.getParameter("buyTime");

    // Generate random bill ID
    String billId = generateBillId();

    // Retrieve game details and logged-in user (gamer)
    Game game = GameDAO.getGameByGameID(gameId);
    HttpSession session = request.getSession();
    Gamers gamer = (Gamers) session.getAttribute("account");

    // Convert gamePrice to int (assuming rounding down for whole numbers)
   

    // Add purchase details to MongoDB
    TransactionBillDAO.addPurchase(billId, gamer.getId(), gameId, buyTime, gamePrice);

    // Forward to payment confirmation page or another view
  request.getRequestDispatcher("Home.jsp").forward(request, response);
}


private String generateBillId() {
    // Generate random bill ID with format "bill_" followed by a random number
    Random random = new Random();
    int randomNumber = random.nextInt(100000); // Adjust the upper limit as needed
    return "bill_" + randomNumber;
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
