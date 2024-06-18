/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Common;

import static Common.CheckValid.CheckEmail;
import Controller.JavaMongo;

import static Controller.JavaMongo.CreateNewGamerAccount;
import static Controller.JavaMongo.CreateNewPublisgherAccount;
import Model.Gamers;
import Model.Publishers;
import Model.Users;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ASUS
 */
@WebServlet(name="SignUpServlet", urlPatterns={"/SignUpServlet"})
public class SignUpServlet extends HttpServlet {
   
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
            out.println("<title>Servlet SignUpServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SignUpServlet at " + request.getContextPath () + "</h1>");
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
        String n = request.getParameter("name");
        String em = request.getParameter("email");
        String p = request.getParameter("password");
        String role = request.getParameter("role");
        
        //int role = 3;
//        int money = 0;
//        String avatar = "https://i.pinimg.com/736x/bc/43/98/bc439871417621836a0eeea768d60944.jpg";
        
        // Generate the current date and time
        
        String emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@gmail\\.com$";
        if (em.isEmpty() || !em.matches(emailPattern) || p.isEmpty()|| p == null || role.isEmpty() || role == null || n.isEmpty() || n == null) {           
                request.setAttribute("mess", "Invalid information!!!!");
                request.setAttribute("blue", true);
               //response.sendRedirect("SignUp.jsp");
                request.getRequestDispatcher("Login.jsp").forward(request, response);
            
        }else{
            
            int roleValue;
            
            if("gamer".equals(role)){
                roleValue = 3;
            }else if("publisher".equals(role)){
                roleValue = 2;
            }else{
                request.setAttribute("mess", "Invalid role selected!");
                request.setAttribute("blue", true);
                request.getRequestDispatcher("Login.jsp").forward(request, response);
                return;
            }
            try {
                Gamers g = new Gamers();
                Publishers pu = new Publishers();
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String registrationDate = now.format(formatter);
                Users as = CheckEmail(em);
                if (as == null) {
                    try{
//                        int numberUser = JavaMongo.getAllUser().size();
//                        int numberCus = JavaMongo.getAllGamers().size();

                        if(roleValue == 3){
                            CreateNewGamerAccount( n,  p,  em, roleValue,g.getMoney(),g.getAvatarLink(),registrationDate);
                            HttpSession session = request.getSession();
                            session.setAttribute("account",JavaMongo.getGamerByEmail(em));
                        }else if (roleValue == 2){
                            CreateNewPublisgherAccount( n,  p,  em,pu.getBank_account(),
                                    pu.getProfit(),pu.getDescription(),pu.getAvatarLink(),pu.getMoney() ,roleValue, registrationDate );
                            HttpSession session = request.getSession();
                            session.setAttribute("account",JavaMongo.getPublisherByEmail(em));
                        }
                        //response.sendRedirect("Home.jsp");
                        
                        request.getRequestDispatcher("Home.jsp").forward(request, response);
                        
                    } catch (Exception e) {
                        try (PrintWriter out = response.getWriter()) {
                            /* TODO output your page here. You may use following sample code. */
                            out.println("<!DOCTYPE html>");
                            out.println("<html>");
                            out.println("<head>");
                            out.println("<title>Servlet SignUpServlet</title>");
                            out.println("</head>");
                            out.println("<body>");
                            out.println("<h1>Servlet SignUpServlet at " + e.getMessage() + "</h1>");
                            out.println("</body>");
                            out.println("</html>");
                        }
                    }
                } else {
                    request.setAttribute("mess", "An Account is Exist!!!");
                     request.setAttribute("blue", true);
                    //response.sendRedirect("SignUp.jsp");
                    request.getRequestDispatcher("Login.jsp").forward(request, response);
                }

                /*
            try (java.sql.Connection con = Java_JDBC.getConnectionWithSqlJdbc()) {
            String name = request.getParameter("name");  
            String pass = request.getParameter("pass");
            Java_JDBC.CreateAccount(con, name, pass);
            } catch (Exception e) {
            e.printStackTrace();
            }
            response.sendRedirect("index.jsp");
                 */
            } catch (Exception ex) {
                try (PrintWriter out = response.getWriter()) {
                    /* TODO output your page here. You may use following sample code. */
                    out.println("<!DOCTYPE html>");
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<title>Servlet SignUpServlet</title>");
                    out.println("</head>");
                    out.println("<body>");
                    out.println("<h1>Servlet SignUpServlet at " + ex.getMessage() + "</h1>");
                    out.println("</body>");
                    out.println("</html>");
                }
                Logger.getLogger(SignUpServlet.class.getName()).log(Level.SEVERE, null, ex);
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
