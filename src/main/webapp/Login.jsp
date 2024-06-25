<%-- 
    Document   : Login
    Created on : May 26, 2024, 5:53:50 PM
    Author     : ASUS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
        <link rel="stylesheet" type="text/css" href="assets/css/login_design.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css"> 
        <style>
            .error{
                color: red;
                font-weight: bold;
            }

        </style>
    </head>
        <body>
        <section class="container forms">
            <div class="form login">
                <div class="form-content">
                    <header>Login</header>
                    <%
                        if(request.getAttribute("status")!=null)
                            {
                                out.print("<p class='text-danger ml-1'>"+request.getAttribute("status")+"</p>");
                            }

                    %>

                    
                    <%
                        String error = (String) request.getAttribute("error");
                        Boolean red = (Boolean) request.getAttribute("red");
                        if (red != null && red) {
                    %>
                        <p class="error"><%= error %></p>
                    <%
                        }
                    %>

                    <form action="LoginServlet" method="POST">
                        <div class="field input-field">
                            <label for="email">Email Address</label>
                            <input type="email" placeholder="Email" name="email" class="input">
                        </div>
                        <div class="field input-field">
                            <label for="password">Password</label>
                            <input type="password" placeholder="Password" name="pass" class="password">
                            <i class='bx bx-hide eye-icon'></i>
                        </div>
                        
                        <div class="form-link">
                            <label for=""><input type="checkbox" ${(cookie.remember.value eq 'ON')? "checked":""} name="remember" value="ON"/>Remember</label>
                            
                                <a href="ForgetPass.jsp" class="forgot-pass">Forgot password?</a>
                            
                        </div>

                        
                        <div class="field button-field">
                            <button>Login</button>
                        </div>
                    </form>
                    <div class="form-link">
                        <span>Don't have an account? <a href="Register.jsp" class="link signup-link">Register</a></span>
                    </div>
                </div>
                <div class="line"></div>
               
                <div class="media-options">
                    <a href="https://accounts.google.com/o/oauth2/auth?scope=email&redirect_uri=http://localhost:9999/FPTeam/loginGG&response_type=code
                        &client_id=861336407219-9pau27upj8qhchdlsr9ljsr630ojfb2h.apps.googleusercontent.com&approval_prompt=force" class="field google">

                        <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS6WwgH7Nl5_AW9nDCnR2Ozb_AU3rkIbSJdAg&s" alt="" class="google-img">
                        <span>Login with Google</span>
                    </a>
                </div>
            </div>
            
            
            
        </section>
        <!-- JavaScript -->
        <script> 
            const forms = document.querySelector(".forms"),
      pwShowHide = document.querySelectorAll(".eye-icon"),
      
pwShowHide.forEach(eyeIcon => {
    eyeIcon.addEventListener("click", () => {
        let pwFields = eyeIcon.parentElement.parentElement.querySelectorAll(".password");
        
        pwFields.forEach(password => {
            if(password.type === "password"){
                password.type = "text";
                eyeIcon.classList.replace("bx-hide", "bx-show");
                return;
            }
            password.type = "password";
            eyeIcon.classList.replace("bx-show", "bx-hide");
        })
        
    })
})      
        </script>
    </body>

    
</html>