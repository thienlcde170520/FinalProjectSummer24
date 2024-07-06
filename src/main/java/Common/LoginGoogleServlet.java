/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Common;

import static Common.CheckValid.CheckEmail;

import static DAO.GamerDAO.CreateNewGamerAccount;
import Model.Gamers;

import Model.Users;
import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author ASUS
 */
@WebServlet(name="/loginGG", urlPatterns={"/loginGG"})
public class LoginGoogleServlet extends HttpServlet {
   
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
        String code = request.getParameter("code");
        GoogleLogin gg = new GoogleLogin();
        String accessToken = gg.getToken(code);
        GooglePojo acc = gg.getUserInfo(accessToken);
        //System.out.print(acc);
        int role =3;

        Users a = CheckEmail(acc.getEmail());

        Gamers g = new Gamers();
        HttpSession session = request.getSession();
        
        
        if (a != null){
            session.setAttribute("account", a);
        }else {
            // If user does not exist, create a new account
            CreateNewGamerAccount(acc.getId(),acc.getName(), acc.getPassword(), acc.getEmail(),role,0.0,acc.getAvatarLink(),acc.getRegistrationDate(),g.getDOB()); // Set yourRoleValue accordingly
            // Retrieve the newly created user to set in session
            a = CheckEmail(acc.getEmail()); // Check again after account creation
            session.setAttribute("account", a);
        }
        
        // Redirect to the homepage or dashboard after login
        response.sendRedirect("Home.jsp");
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
