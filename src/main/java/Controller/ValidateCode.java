/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Date;

/**
 *
 * @author ASUS
 */
@WebServlet(name="ValidateCode", urlPatterns={"/ValidateCode"})
public class ValidateCode extends HttpServlet {
   private static final long serialVersionUID = 1L;
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

		int value=Integer.parseInt(request.getParameter("code"));
		HttpSession session=request.getSession();
		Integer code=(Integer)session.getAttribute("code");
		Long otpTimestamp = (Long) session.getAttribute("otpTimestamp");
                
                if(code != null && otpTimestamp != null){
                    long currentTime = new Date().getTime();
                    if(currentTime - otpTimestamp <= 60000){
                        if(value == code){
                            request.setAttribute("mess", "OTP is Valid");
                            request.getRequestDispatcher("resetPass.jsp").forward(request, response);
                        }else{
                            request.setAttribute("message", "Invalid OTP");
                            request.getRequestDispatcher("EnterCode.jsp").forward(request, response);
                        }
                    }else {
                        request.setAttribute("message", "OTP has expired.");
                        request.getRequestDispatcher("EnterCode.jsp").forward(request, response);
                    }
                }else{
                    request.setAttribute("message", "No OTP generated.");
                    request.getRequestDispatcher("EnterCode.jsp").forward(request, response);
                }
                
                
//		if (value==code) 
//		{
//			
//                    request.setAttribute("email", request.getParameter("email"));
//                    request.setAttribute("status", "success");
//                    request.getRequestDispatcher("resetPass.jsp").forward(request, response);
//			
//		}
//		else
//		{
//			request.setAttribute("message","wrong code");			
//                        request.getRequestDispatcher("EnterCode.jsp").forward(request, response);	
//		}
		
	}
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

}
