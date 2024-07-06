/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller;

import DAO.ReportDAO;
import Model.Report;
import Model.Users;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author LENOVO
 */
@WebServlet(name="ReportServlet", urlPatterns={"/ReportServlet"})
public class ReportServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
       ArrayList<Report> reports = (ArrayList<Report>) ReportDAO.getAllReports();
           HttpSession session = request.getSession();
    Users user = (Users) session.getAttribute("account");
    if (user.getRole() == 1 ){
        request.setAttribute("isAdmin", true); 
    }else {
             request.setAttribute("isAdmin", false); 
    }
             // Assuming you set isAdmin somewhere
            request.setAttribute("reports", reports);
        request.getRequestDispatcher("RespondReport.jsp").forward(request, response);
       
       
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
        String action = request.getParameter("action");

        if ("search".equals(action)) {
            String problemName = request.getParameter("problemName");
            List<Report> reports = ReportDAO.searchReportsByProblemName(problemName);
            request.setAttribute("reports", reports);
            RequestDispatcher dispatcher = request.getRequestDispatcher("RespondReport.jsp");
            dispatcher.forward(request, response);
        } else {
            processRequest(request,response);
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
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get form parameters
        String overallProblem = request.getParameter("OverallProblem");
        String supportReason = request.getParameter("supportReason");
         HttpSession session = request.getSession();
    Users user = (Users) session.getAttribute("account");
        ReportDAO.addReport(supportReason, overallProblem, user.getId());
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
