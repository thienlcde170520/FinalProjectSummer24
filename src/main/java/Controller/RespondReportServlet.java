/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller;

import DAO.ReportDAO;
import Model.Report;
import Model.Users;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;

/**
 *
 * @author LENOVO
 */
@WebServlet(name="RespondReportServlet", urlPatterns={"/RespondReportServlet"})
public class RespondReportServlet extends HttpServlet {
   
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
            out.println("<title>Servlet RespondReportServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RespondReportServlet at " + request.getContextPath () + "</h1>");
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
            String UserID = request.getParameter("UserId");
            List<Report> reports= ReportDAO.getReportsByUserId(UserID);
                 HttpSession session = request.getSession();
    Users user = (Users) session.getAttribute("account");
             if (user.getRole() == 1 ){
        request.setAttribute("isAdmin", true); 
    }else {
             request.setAttribute("isAdmin", false);
             
    }
                request.setAttribute("reports", reports);
        request.getRequestDispatcher("RespondReport.jsp").forward(request, response);
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
        String action = request.getParameter("action");
        if ("respond".equals(action)) {
            String reportId = request.getParameter("reportId");
            String respondText = request.getParameter("respondText");
             HttpSession session = request.getSession();
    Users user = (Users) session.getAttribute("account");
            String adminId = user.getId(); // Replace with actual admin ID from the session or context
            boolean isSearchable = "on".equals(request.getParameter("isSearchable"));

            ReportDAO.respondToReport(reportId, respondText, adminId, isSearchable);

            // Redirect back to the report list
            response.sendRedirect("Home.jsp");
        }else if ("delete".equals(action)) {
            String reportId = request.getParameter("reportId");
            ReportDAO.deleteReport(reportId);
            response.sendRedirect("Home.jsp"); // Redirect to the reports page
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
