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
        
        <style>
            .timer {
            margin-top: 10px;
            font-size: 18px;
            color: #d9534f;
        }
        </style>
    
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
        <div class="timer" id="timer">Time left: 60s</div>
        
        <script>
        let timeLeft = 60;
        const timerElement = document.getElementById('timer');
        const submitBtn = document.getElementById('submitBtn');

        const countdown = setInterval(() => {
            if (timeLeft <= 0) {
                clearInterval(countdown);
                timerElement.textContent = 'OTP has expired';
                submitBtn.disabled = true;
            } else {
                timerElement.textContent = `Time left: ${timeLeft}s`;
                timeLeft--;
            }
        }, 1000);
    </script>
    </body>
    
</html>