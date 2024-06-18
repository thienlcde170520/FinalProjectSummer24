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
        <title>JSP Page</title>
        <link rel="stylesheet" type="text/css" href="assets/css/login_design.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <style>
        .error mess{
            color: red;
            font-weight: bold;
        }
        .role-selection {
            display: flex;
            justify-content: center;
            align-items: center;
            margin-bottom: 20px;
        }
        .role-selection label {
            margin-right: 10px;
        }
        .roleText{
            font-weight: bold;            
        }
        #gamer {
             margin-right: 20px;
        }
    </style>
    </head>
    <body>
        
    <div class="container">
        <div class="box">
            <div class="form sign_in">
                <h3>Sign In</h3>
                <span>or use your account</span>

                <form action="LoginServlet" id="form_input" method="POST">
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
                    <div class="type">
                        <input type="email" placeholder="Email" name="email" id="email">

                    </div>
                    <div class="type">
                        <input type="password" placeholder="Password" name="pass" id="password">

                    </div>
                    
                    <div class="forgot">
                        <label for=""><input type="checkbox" ${(cookie.remember.value eq 'ON')? "checked":""} name="remember" value="ON"/>Remember</label>
                        <span><a href="ForgetPass.jsp">Forgot the password?</a></span>
                    </div>

                    <button class="btn bkg">Sign In</button>
                         
                </form>
                        <form id="form_gg">
                        <a href="https://accounts.google.com/o/oauth2/auth?scope=email&redirect_uri=http://localhost:9999/FPTeam/loginGG&response_type=code
		&client_id=861336407219-9pau27upj8qhchdlsr9ljsr630ojfb2h.apps.googleusercontent.com&approval_prompt=force">Login With Google</a>
                    </form>
            </div>
    
            <div class="form sign_up">
                <h3>Sign Up</h3>
                <span>or use your email for register</span>

                <form action="SignUpServlet" id="form_input" method="POST">
                    <%
        String mess = (String) request.getAttribute("mess");
        Boolean blue = (Boolean) request.getAttribute("blue");
        if (blue != null && blue) {
    %>
        <p class="mess"><%= mess %></p>
    <%
        }
    %>
                    <div class="type">

                        <input type="text" name="name" placeholder="Name" id="name">
                    </div>
                    <div class="type">
                        
                        <input type="email" name="email" placeholder="Email" id="email">
                    </div>
                    <div class="type">

                        <input type="password" name="password" placeholder="Password" id="password">
                    </div>
                    
                    <div class="role-selection">
                        <p class="roleText"><label for="role">Choose your role:</label></p>
                        <label for="gamer">Gamer</label>
                        <input type="radio" id="gamer" name="role" value="gamer">
                        <label for="publisher">Publisher</label>
                        <input type="radio" id="publisher" name="role" value="publisher">
                    </div>
                    
                    <button class="btn bkg">Sign Up</button>
                </form>
                    
            </div>
        </div>

        <div class="overlay">
            <div class="page page_signIn">
                <h3>Welcome Back!</h3>
                <p>Please log in with your personal information and have a nice time</p>

                <button class="btn btnSign-in">Sign Up <i class="bi bi-arrow-right"></i></button>
            </div>

            <div class="page page_signUp">
                <h3>Hello Friend!</h3>
                <p>Enter your personal details and start journey with us</p>

                <button class="btn btnSign-up">
                    <i class="bi bi-arrow-left"></i> Sign In</button>
            </div>
        </div>
    </div>
    
    
    <!-- link script -->
    <script>
        const container = document.querySelector('.container');
        const btnSignIn = document.querySelector('.btnSign-in');
        const btnSignUp = document.querySelector('.btnSign-up');

        btnSignIn.addEventListener('click', () => {
            container.classList.add('active');
        });

        btnSignUp.addEventListener('click', () => {
            container.classList.remove('active');
        });
    </script>
    
    
    

    
</body>
    
</html>