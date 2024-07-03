
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
            <label for="Code">OTP</label>
            <div class="pass_time">
                <input type="password" name="code" placeholder="Enter OTP" id="password">
                <div class="timer" id="countdown">60s</div>
            </div>
            <button type="submit" id="submitBtn">Verify</button>    
            
        </form>
        
        
         <script>
            document.addEventListener('DOMContentLoaded', function() {
                var countdown = 60; // Th?i gian ??m ng??c (60 gi�y)
                var timer = setInterval(function() {
                    countdown--;
                    if (countdown <= 0) {
                        clearInterval(timer);
                        // X? l� khi h?t th?i gian ??m ng??c (v� d?: ?n ho?c l�m g� ?�)
                    }
                    document.getElementById('countdown').innerText = countdown + `s`;
                }, 1000);
            });
        </script>
    </body>
    
</html>