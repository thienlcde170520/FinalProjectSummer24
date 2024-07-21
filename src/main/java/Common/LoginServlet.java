/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Common;
import static Common.CheckValid.userValid;
import Controller.JavaMongo;
import static DAO.AdminDAO.getAdminByEmail;
import static DAO.GamerDAO.getGamerByEmail;
import static DAO.PublisherDAO.getPublisherByEmail;
import Model.Users;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ASUS
 */
@WebServlet(name="LoginServlet", urlPatterns={"/LoginServlet"})
public class LoginServlet extends HttpServlet {
   
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
            out.println("<title>Servlet LoginServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoginServlet at " + request.getContextPath () + "</h1>");
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
        //processRequest(request, response);
        try{
            try{
                
                String e = request.getParameter("email");/**/
                String p = request.getParameter("pass");
                String r = request.getParameter("remember");
            
                String regex = "^(?=.*[A-Za-z])(?=.*\\d).+$"; // yeu cau pass có it nhat 1 so 1 chu
                String emailPattern = "^[^ ]+@[^ ]+\\.[a-z]{2,3}$";
                boolean hasErrors = false;

                try {
                    if(e.trim().isEmpty() || e == null || p.trim().isEmpty() || p == null){
                        request.setAttribute("status", "Email and Password not be empty");                                             
                        hasErrors = true;
                    }else if(!p.matches(regex) && !e.matches(emailPattern)){
                        request.setAttribute("status", "Please enter information according to format");
                        hasErrors = true;
                    }else{
                        if(!e.matches(emailPattern)){
                            request.setAttribute("status", "Please re-enter your email "); 
                            hasErrors = true;
                        }
                        if(!p.matches(regex)|| p.length() < 5){
                            request.setAttribute("status", "Please re-enter your password ");
                            hasErrors = true;
                        }
                    }
                    
                    
                    if (hasErrors) {           
                        request.getRequestDispatcher("Login.jsp").forward(request, response);
                    }
                    else{
                        Users u = userValid(e, p);
                    if(u == null){
                        request.setAttribute("status", "An Account not Exist!!!");
                                              
                        request.getRequestDispatcher("Login.jsp").forward(request, response);
                    }
                    else {
                        
                        HttpSession session = request.getSession();
                        if (u.getRole() == 3){
                        session.setAttribute("account",getGamerByEmail(e));
                        }
                        if (u.getRole() == 2){
                        session.setAttribute("account",getPublisherByEmail(e));
                        }
                        if(u.getRole() == 1){
                        session.setAttribute("account", getAdminByEmail(e));
                        }

////                        session.setAttribute("account", JavaMongo.getAllUser());
//                        
                        /*
                        if ("Admin".equals(a.getRole())){
                            
                            session.setAttribute("account", Java_JDBC.getAdminByUserId(con, a.getUserId()));
                        }
                        */
                        
                        Cookie ce = new Cookie("emailC",e);
                        Cookie cp = new Cookie("passC",p);
                        Cookie cr = new Cookie("remember",r);
                        ce.setMaxAge(60*60);
                        if (r != null){                                                
                            cp.setMaxAge(60*60);
                            cr.setMaxAge(60*60);
                        }
                        else {                                                       
                            cp.setMaxAge(0);                                                      
                        }
                        
                        response.addCookie(ce);
                        response.addCookie(cp);
                        response.addCookie(cr);
                        // response.sendRedirect("Home.jsp");
                        response.sendRedirect("Home.jsp");
                    }
                    }
                } catch (Exception ex ) {
                      try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet LoginServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Error at " + ex.getMessage() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
                }
            }catch (Exception ex) {
                  try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet LoginServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Error at " + 456+ "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
            }
        }catch (Exception ex) {
            try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet LoginServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Error at " + 789 + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
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
