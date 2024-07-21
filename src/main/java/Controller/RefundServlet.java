/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller;

import DAO.TransactionBillDAO;
import Model.Bill;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author LENOVO
 */
@WebServlet(name="RefundServlet", urlPatterns={"/RefundServlet"})
public class RefundServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

String gameId = request.getParameter("gameId");
        String billId = request.getParameter("billId");
        String gamerId = request.getParameter("gamerId");
        Double refundNumber = Double.valueOf(request.getParameter("refundnumber"));
            // Process the refund
            TransactionBillDAO.refundPurchase(billId, gamerId, gameId, refundNumber);

         response.sendRedirect("GameDetailServlet?gameid=" + gameId);  
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
        String gameId = request.getParameter("gameId");
        String billId = request.getParameter("billId");
        String gamerId = request.getParameter("gamerId");
      Double refundNumber = Double.valueOf(request.getParameter("refundnumber"));
            // Process the refund
            TransactionBillDAO.refundPurchase(billId, gamerId, gameId, refundNumber);
       response.sendRedirect("GameDetailServlet?gameid=" + gameId);  
    
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
