package Controller;

import static Controller.JavaMongo.updatePassword;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class NewPassword
 */
@WebServlet("/newPassword")
public class newPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		String newPassword = request.getParameter("password");
		String confPassword = request.getParameter("confirm_password");
		
		if (newPassword != null && confPassword != null && newPassword.equals(confPassword)) {

			try {
                            updatePassword((String) session.getAttribute("email"),newPassword);
				
                            request.setAttribute("status", "resetSuccess");
                            request.getRequestDispatcher("Login.jsp").forward(request, response);
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
