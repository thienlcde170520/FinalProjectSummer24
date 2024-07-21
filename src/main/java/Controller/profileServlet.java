/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller;

import DAO.GameDAO;
import DAO.GamerDAO;
import DAO.PublisherDAO;
import DAO.ReviewDAO;
import DAO.TransactionBillDAO;
import Model.BankTransactions;
import Model.Game;
import Model.Gamers;
import Model.Publishers;
import Model.Review;
import Model.Users;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 *
 * @author ASUS
 */
@WebServlet(name="profileServlet", urlPatterns={"/profileServlet"})
public class profileServlet extends HttpServlet {
   
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
            out.println("<title>Servlet profile 9</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet profileServlet at " + 123 + "</h1>");
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
    String userid = request.getParameter("userid");
    HttpSession session = request.getSession();
    Users user = (Users) session.getAttribute("account");
    
    if (userid == null) {
        handleUserProfile(request, response, user);
    } else {
        handleInputProfile(request, response, userid, user);
    }
}

private void handleUserProfile(HttpServletRequest request, HttpServletResponse response, Users user)
        throws ServletException, IOException {
    if (user != null) {
        int role = user.getRole();
        switch (role) {
            case 3:
                handleGamerProfileByEmail(request, response, user.getGmail());
                break;
            case 2:
                handlePublisherProfile(request, response, user.getGmail());
                break;
            default:
                showErrorPage(response, "Invalid user role");
                break;
        }
    } else {
          response.sendRedirect("Login.jsp");
    }
}

private void handleInputProfile(HttpServletRequest request, HttpServletResponse response, String userId, Users user)
        throws ServletException, IOException {
    try {
        boolean isAdmin = user != null && user.getRole() == 1;
        boolean isUpdateable = false;

        Gamers gamer = GamerDAO.getGamerByGamerId(userId);
        if (gamer != null) {
            ArrayList<BankTransactions> transactionHistory = TransactionBillDAO.getTransactionHistoryByPayerId(gamer.getId());
            ArrayList<Game> games = GameDAO.getGamesByGamerId(gamer.getId());

            if (user != null && (isAdmin || user.getId().equals(gamer.getId()))) {
                isUpdateable = true;
            }
            ArrayList<Review> reviews = ReviewDAO.getReviewByGamerID(gamer.getId());
            request.setAttribute("isUpdateable", isUpdateable);
            request.setAttribute("isAdmin", isAdmin);
            request.setAttribute("gamer", gamer);
            request.setAttribute("games", games);
            request.setAttribute("transactionHistory", transactionHistory);
             request.setAttribute("reviews", reviews);
            request.getRequestDispatcher("profile.jsp").forward(request, response);
        } else {
            Publishers pub = PublisherDAO.getPublisherByPublisherId(userId);
            if (pub != null) {
                ArrayList<Game> publishgames = GameDAO.getGamesByPublisherName(pub.getName());
              
                ArrayList<Review> reviews = ReviewDAO.getReviewsByPublisherName(pub.getName());

                if (user != null && (isAdmin || user.getId().equals(pub.getId()))) {
                    isUpdateable = true;
                }

                request.setAttribute("publisher", pub);
                request.setAttribute("isUpdateable", isUpdateable);
                request.setAttribute("isAdmin", isAdmin);
                request.setAttribute("games", publishgames);
                request.setAttribute("reviews", reviews);
                
                request.getRequestDispatcher("PublisherProfile.jsp").forward(request, response);
            } else {
                showErrorPage(response, "User not found");
            }
        }
    } catch (Exception ex) {
        showErrorPage(response, ex.getMessage());
    }
}

private void handleGamerProfileByEmail(HttpServletRequest request, HttpServletResponse response, String email)
        throws ServletException, IOException {
    try {
        Gamers gamer = GamerDAO.getGamerByEmail(email);
        if (gamer != null) {
            ArrayList<BankTransactions> transactionHistory = TransactionBillDAO.getTransactionHistoryByPayerId(gamer.getId());
            ArrayList<Game> games = GameDAO.getGamesByGamerId(gamer.getId());
            boolean isUpdateable = true; // Default to false
                    ArrayList<Review> reviews = ReviewDAO.getReviewByGamerID(gamer.getId());
            request.setAttribute("gamer", gamer);
            request.setAttribute("games", games); 
            request.setAttribute("isUpdateable", isUpdateable);
            request.setAttribute("transactionHistory", transactionHistory);
                   request.setAttribute("reviews", reviews);
            request.getRequestDispatcher("profile.jsp").forward(request, response);
        } else {
            showErrorPage(response, "Gamer not found");
        }
    } catch (Exception ex) {
        showErrorPage(response, ex.getMessage());
    }
}

private void handlePublisherProfile(HttpServletRequest request, HttpServletResponse response, String email)
        throws ServletException, IOException {
    try {
        Publishers pub = PublisherDAO.getPublisherByEmail(email);
         ArrayList<Game> publishgames = GameDAO.getGamesByPublisherName(pub.getName());
         ArrayList<Game> unpublishgames = GameDAO.getUnpublishableGamesByPublisher(pub);
            ArrayList<Review> reviews = ReviewDAO.getReviewsByPublisherName(pub.getName());
                  HttpSession session = request.getSession();
Users user = (Users) session.getAttribute("account");
             boolean isUpdateable = false; // Default to false
if (    user.getId() == null ? pub.getId() == null : user.getId().equals(pub.getId())  ){
     isUpdateable = true;
}
        if (pub != null) {
           
            request.setAttribute("publisher", pub);
              request.setAttribute("isUpdateable", isUpdateable);
          
                 request.setAttribute("publishgames", publishgames);
                      request.setAttribute("unpublishgames", unpublishgames);
                    request.setAttribute("reviews", reviews);
            request.getRequestDispatcher("DisplayPublisher.jsp").forward(request, response);
        } else {
            showErrorPage(response, "Publisher not found");
        }
    } catch (Exception ex) {
        showErrorPage(response, ex.getMessage());
    }
}

private void showErrorPage(HttpServletResponse response, String message) throws IOException {
    response.setContentType("text/html");
    try (PrintWriter out = response.getWriter()) {
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Error</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>" + message + "</h1>");
        out.println("</body>");
        out.println("</html>");
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
