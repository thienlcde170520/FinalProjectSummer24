/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Common;

import static Common.CheckValid.CheckEmail;
import Controller.JavaMongo;
import static DAO.GamerDAO.CreateNewGamerAccount;
import static DAO.GamerDAO.getGamerByEmail;
import static DAO.PublisherDAO.CreateNewPublisgherAccount;
import static DAO.PublisherDAO.getPublisherByEmail;


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
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ASUS
 */
@WebServlet(name="SignUpServlet", urlPatterns={"/SignUpServlet"})
public class SignUpServlet extends HttpServlet {
   static Set<String> radomaId = new HashSet<>();
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
        String rp = request.getParameter("confirm_password");
        String role = request.getParameter("role");
        
        
        
        // Generate the current date and time
        String regex = "^(?=.*[A-Za-z])(?=.*\\d).+$"; // yeu cau pass có it nhat 1 so 1 chu
       String emailPattern = "^[^ ]+@[^ ]+\\.[a-z]{2,3}$";

        if (em.isEmpty() || !em.matches(emailPattern) || p.isEmpty()|| !p.matches(regex)|| !p.matches(rp) || role.isEmpty() || role == null || n.isEmpty() || n == null) {           
                request.setAttribute("mess", "Invalid information!!!!");
                request.setAttribute("blue", true);
                
                request.getRequestDispatcher("Register.jsp").forward(request, response);
            
        }else{
            
            int roleValue;
            
            if("gamer".equals(role)){
                roleValue = 3;
            }else if("publisher".equals(role)){
                roleValue = 2;
            }else{
                request.setAttribute("mess", "Invalid role selected!");
                request.setAttribute("blue", true);
                request.getRequestDispatcher("Register.jsp").forward(request, response);
                return;
            }
            try {
                Gamers g = new Gamers();
                String idG = "gamer_"+generateRandomNumber();
                String idP = "pub_" + generateRandomNumber();
                Publishers pu = new Publishers();
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String registrationDate = now.format(formatter);
                //String Id = "game_" + generateRandomNumber();
                Users as = CheckEmail(em);
                if (as == null) {
                    try{
//                        int numberUser = JavaMongo.getAllUser().size();
//                        int numberCus = JavaMongo.getAllGamers().size();

                        if(roleValue == 3){
                            CreateNewGamerAccount( idG,n,  p,  em, roleValue,g.getMoney(),g.getAvatarLink(),registrationDate);
                            HttpSession session = request.getSession();
                            session.setAttribute("account",getGamerByEmail(em));
                        }else if (roleValue == 2){
                            CreateNewPublisgherAccount(idP, n,  p,  em,pu.getBank_account(),
                                    pu.getProfit(),pu.getDescription(),pu.getAvatarLink(),pu.getMoney() ,roleValue, registrationDate );
                            HttpSession session = request.getSession();
                            session.setAttribute("account",getPublisherByEmail(em));
                        }
                        //response.sendRedirect("Home.jsp");
                        
                        request.getRequestDispatcher("Home.jsp").forward(request, response);
                        
                    } catch (ServletException | IOException e) {
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
    //radomaId
    private static String generateRandomNumber() {
        Random random = new Random();
        String generateID = null;
        do {
            int randomNumber = random.nextInt(1000000);
            generateID = String.valueOf(randomNumber);
        } while (radomaId.contains(generateID));
        radomaId.add(generateID);
        return generateID; // Trả về một chuỗi số ngẫu nhiên
    }

  
    /*
    private String generateRandomName() {
        Random random = new Random();
        String generatedName = null;
        do {
            int randomNumber = random.nextInt(1000000); // Generate a random number
            generatedName = "User_" + randomNumber;
        } while (generatedNames.contains(generatedName)); // Check if name already exists
        generatedNames.add(generatedName); // Mark the name as used
        return generatedName;
    }
    
    */

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
