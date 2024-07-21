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
                <input type="text" name="code" placeholder="Enter OTP" id="password">
                <%
                    //Integer null dc int ko
                    Integer  code = (Integer) session.getAttribute("code");
                    if (code != null) {
                %>
                <div class="timer" id="countdown">60s</div>
                <%}%>
            </div>
            <button type="submit" id="submitBtn">Verify</button>    
            
        </form>
        
        
       <script>
            document.addEventListener('DOMContentLoaded', function() {
                // Kiểm tra Local Storage để lấy thời gian đếm ngược
                var endTime = localStorage.getItem('endTime');
                var countdown;

                if (!endTime) {
                    // Nếu không có giá trị trong Local Storage, thiết lập thời gian mặc định
                    countdown = 60;
                    endTime = Date.now() + countdown * 1000;
                    localStorage.setItem('endTime', endTime);
                } else {
                    countdown = Math.ceil((endTime - Date.now()) / 1000);
                    if (countdown <= 0) {
                        countdown = 0;
                        localStorage.removeItem('endTime');
                    }
                }

                var countdownElement = document.getElementById('countdown');
                countdownElement.innerText = countdown + 's';

                var timer = setInterval(function() {
                    countdown--;
                    if (countdown <= 0) {
                        clearInterval(timer);
                        countdown = 0;
                        localStorage.removeItem('endTime'); // Xóa thời gian khi kết thúc
                    }
                    countdownElement.innerText = countdown + 's';
                }, 1000);
            });
        </script>

    </body>
    
</html>