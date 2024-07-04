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
        <title>Check OTP Page</title>
        <link rel="stylesheet" type="text/css" href="assets/css/forget_design.css">
        
    
    </head>
    <body>
        
        
        <form action ="ValidateCode" method="post">
        
            <h3>Check Valid Email</h3>
            <%
		if(request.getAttribute("message")!=null)
		{
		   out.print("<p class='text-danger'>"+request.getAttribute("message")+"</p>");
		}
		if(request.getAttribute("messageCode")!=null)
                {
                    out.print("<p class='mess-success'>"+request.getAttribute("messageCode")+"</p>");
                }
	%>
            <label for="Code">OTP</label>
            <div class="pass_time">
                <input type="password" name="code" placeholder="Enter OTP" id="password">
                <div class="timer" id="countdown">60s</div>
            </div>
            <button type="submit" id="submitBtn">Verify</button>    
            
        </form>
        
        
         <script>
            document.addEventListener('DOMContentLoaded', function() {
                var countdown = 60; // Thời gian đếm ngược (60 giây)
                var timer = setInterval(function() {
                    countdown--;
                    if (countdown <= 0) {
                        clearInterval(timer);
                        // Xử lý khi hết thời gian đếm ngược (ví dụ: ẩn hoặc làm gì đó)
                    }
                    document.getElementById('countdown').innerText = countdown + `s`;
                }, 1000);
            });
        </script>
    </body>
    
</html>