/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Common;

import Controller.JavaMongo;
import static DAO.TransactionBillDAO.insertTransaction;
import Model.Users;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.UUID;
import org.bson.Document;

/**
 *
 * @author tuanh
 */
@WebServlet(name="PaymentSuccessServlet", urlPatterns={"/paymentSuccessServlet"})
public class PaymentSuccessServlet extends HttpServlet {
   
    

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve parameters from the request URL
        String partnerCode = request.getParameter("partnerCode");
        String orderId = request.getParameter("orderId");
        String requestId = request.getParameter("requestId");
        String amount = request.getParameter("amount");
        String orderInfo = request.getParameter("orderInfo");
        String orderType = request.getParameter("orderType");
        String transId = request.getParameter("transId");
       
        String payType = request.getParameter("payType");
 
        String signature = request.getParameter("signature");
  

        // Ensure required parameters are not null
        if (partnerCode == null || orderId == null || requestId == null || amount == null
                || orderInfo == null || orderType == null || transId == null
                || payType == null
                || signature == null) {
            // Handle missing parameters error
            String errorMessage = "Missing required parameters for payment success";
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }
        
        // Get payerId based on session or user role
        HttpSession session = request.getSession();
        Users user = (Users) session.getAttribute("account");
        String payerId = "";
        if (user != null) {
            int role = user.getRole();
            if (role == 3 || role == 2) { // Assuming role 3 is gamer and role 2 is publisher
                payerId = user.getId();
            }
        }
  try {
            // Insert transaction into MongoDB using JavaMongo class
            insertTransaction(partnerCode, orderId, requestId, amount, orderInfo,
                    orderType, transId, payType,
                    signature,payerId);
      
            // Redirect or forward to a success page
            response.sendRedirect("paymentSuccess.jsp"); // Example redirect

        } catch (Exception e) {
            // Log the exception for debugging purposes
            e.printStackTrace();

            // Set error message attribute for displaying in error page or logs
            String errorMessage = "Error processing payment success: " + e.getMessage();
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
    

  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        doGet(request, response);
    }
 
  
}
