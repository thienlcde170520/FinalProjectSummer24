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
        <title>Register Page</title>
        <link rel="stylesheet" type="text/css" href="assets/css/register.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css"> 
        <style>
            .mess{
                color: red;
                font-weight: bold;
            }

        </style>
    </head>
        <body>
        <section class="container forms">
        <!-- Signup Form -->
            <div class="form signup">
                <div class="form-content">
                    <header>Register</header>
                    <%
                        String mess = (String) request.getAttribute("mess");
                        Boolean blue = (Boolean) request.getAttribute("blue");
                        if (blue != null && blue) {
                    %>
                        <p class="mess"><%= mess %></p>
                    <%
                        }
                    %>

                    <form action="SignUpServlet" method="POST">
                        <div class="field input-field">
                            <label for="name">Username</label>
                            <input type="text" placeholder="Name" name="name" class="input">
                        </div>
                        <div class="field input-field">
                            <label for="email">Email Address</label>
                            <input type="email" placeholder="Email" name="email" class="input">
                        </div>
                        <div class="field input-field">
                            <label for="password">Password</label>
                            <input type="password" placeholder="Create password" name="password" class="password">
                            <i class='bx bx-hide eye-icon'></i>
                        </div>
                        <div class="field input-field">
                            <label for="password">Confirm Password</label>
                            <input type="password" placeholder="Confirm password" name="confirm_password" class="password">
                            <i class='bx bx-hide eye-icon'></i>
                        </div>
                        <div class="role-selection">
                            <p class="roleText"><label for="role">Choose your role:</label></p>
                            <label for="gamer">Gamer</label>
                            <input type="radio" id="gamer" name="role" value="gamer">
                            <label for="publisher">Publisher</label>
                            <input type="radio" id="publisher" name="role" value="publisher">
                        </div>

                        <div class="field button-field">
                            <button>Register</button>
                        </div>
                    </form>
                    
                </div>
                
            </div>
        </section>
        <!-- JavaScript -->
        <script> 
            const forms = document.querySelector(".forms"),
      pwShowHide = document.querySelectorAll(".eye-icon"),
      links = document.querySelectorAll(".link");
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