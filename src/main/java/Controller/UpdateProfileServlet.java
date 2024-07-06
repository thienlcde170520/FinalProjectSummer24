/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller;

import static Common.CheckValid.CheckEmail;
import static Controller.JavaMongo.updateProfile;
import DAO.GamerDAO;
import DAO.PublisherDAO;
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
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ASUS
 */
@WebServlet(name="UpdateProfileServlet", urlPatterns={"/UpdateProfileServlet"})
public class UpdateProfileServlet extends HttpServlet {
   
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
            out.println("<title>Servlet UpdateProfileServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateProfileServlet at " + request.getContextPath () + "</h1>");
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
        
        String newA = request.getParameter("gameAvatar");
        String newN = request.getParameter("newName");
        String newEM = request.getParameter("newEmail");
        String newDOB = request.getParameter("newDOB");
        String newP = request.getParameter("newPassWord");
        String conP = request.getParameter("confirmPass");
       
        
        
        String regex = "^(?=.*[A-Za-z])(?=.*\\d).+$"; // yeu cau pass có it nhat 1 so 1 chu
        String emailCheck = "^[^ ]+@[^ ]+\\.[a-z]{2,3}$";
        
        if(!newEM.matches(emailCheck) || !newP.equals(conP) || !newP.matches(regex) || newP.length() < 5 || newDOB.isEmpty()){
            request.setAttribute("mess", "Invalid information!!!!");
            
            request.getRequestDispatcher("UpdateProfile.jsp").forward(request, response);
        }else
        {        
//            Users sp = new Users();
//            if(newEM.isEmpty() || newEM == null){
//                newEM = as.getGmail();
//                sp = CheckWithId(newEM,sp.getId());          
//            }
            
            Users as = CheckEmail(newEM);
            
            if(as == null )
            {
                try{
                    HttpSession session = request.getSession();
                    Users u = (Users) session.getAttribute("account");
                   
                        if( u.getRole() == 3){
                           
                        updateProfile(u.getId(),newN,newEM,newP,newA,newDOB,u.getRole());
                        Gamers gamer = GamerDAO.getGamerByEmail(newEM);
                            if(gamer != null){
                                request.setAttribute("gamer", gamer);
                                request.getRequestDispatcher("profile.jsp").forward(request, response);                     
                            }else{
                                 try (PrintWriter out = response.getWriter()) {
                                    /* TODO output your page here. You may use following sample code. */
                                    out.println("<!DOCTYPE html>");
                                    out.println("<html>");
                                    out.println("<head>");
                                    out.println("<title>Servlet profileServlet</title>");  
                                    out.println("</head>");
                                    out.println("<body>");
                                    out.println("<h1>Servlet profileServlet at " + u.getRole()+ "</h1>");
                                    out.println("</body>");
                                    out.println("</html>");
                                }                 
                            }
                        }else if( u.getRole() ==2){
                             updateProfile(u.getId(),newN,newEM,newP,newA,newDOB,u.getRole());
                             Publishers pub = PublisherDAO.getPublisherByEmail(newEM);
                             if(pub != null){
                                 request.setAttribute("pub", pub);
                                 request.getRequestDispatcher("DisplayPublisher.jsp").forward(request, response);
                             }else{
                                 try (PrintWriter out = response.getWriter()) {
                                    /* TODO output your page here. You may use following sample code. */
                                    out.println("<!DOCTYPE html>");
                                    out.println("<html>");
                                    out.println("<head>");
                                    out.println("<title>Servlet profileServlet</title>");  
                                    out.println("</head>");
                                    out.println("<body>");
                                    out.println("<h1>Servlet profileServlet at " + u.getId()+ "</h1>");
                                    out.println("</body>");
                                    out.println("</html>");
                                }                 
                            }
                        }
                   

                
                
            
            }catch (Exception e) {
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
            }else{
                request.setAttribute("mess", "An Account is Exist!!!");
                     
                    //response.sendRedirect("SignUp.jsp");
                    request.getRequestDispatcher("UpdateProfile.jsp").forward(request, response);
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
