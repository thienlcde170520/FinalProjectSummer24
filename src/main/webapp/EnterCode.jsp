<%-- 
    Document   : ForgetPass
    Created on : May 26, 2024, 8:23:13 PM
    Author     : ASUS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" type="text/css" href="assets/css/forget_design.css">
    </head>
    <body>
        <div class="background">
        <div class="shape"></div>
        <div class="shape"></div>
    </div>
        <%
		  			if(request.getAttribute("message")!=null)
		  			{
		  				out.print("<p class='text-danger ml-1'>"+request.getAttribute("message")+"</p>");
		  			}
		  
		  %>
        <form action ="ValidateCode" method="post">
            <h3>Check Valid Email</h3>
        
        <label for="Code">OTP</label>
        <input type="password" name="code" placeholder="Enter OTP" id="password">
        <button>Verify</button>       
    </form>
    </body>
    
    <%--
        <form action="ForgetPasswordServlet" method="post">
            <label for="email">Email:</label>
            <input type="email" id="email" name="email" placeholder="email" required >
            <button type="submit" name="action" value="send_code">Send Code</button>
        </form>
        <form action="ResetPasswordServlet" method="post">
            <label for="code">Code:</label>
            <input type="text" id="code" name="code" placeholder="code" required>
            <button type="submit" name="action" value="verify_code">Verify Code</button>
        </form>
    --%>
</html>