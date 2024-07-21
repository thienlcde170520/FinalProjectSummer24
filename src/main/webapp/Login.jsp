<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
        <link rel="stylesheet" type="text/css" href="assets/css/login_design.css">
        
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css"> 
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.1/css/all.min.css" />

        
        <!--banner-->
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@100;200;300;400;500;600;700;800;900&display=swap" rel="stylesheet">
        <link href="assets/css/bootstrap.min.css" rel="stylesheet">



        <!-- Additional CSS Files -->
        <link rel="stylesheet" href="assets/css/fontawesome.css">
        <link rel="stylesheet" href="assets/css/header_design.css">
        <link rel="stylesheet" href="assets/css/owl.css">
        <link rel="stylesheet" href="assets/css/animate.css">
        <link rel="stylesheet" href="https://unpkg.com/swiper@7/swiper-bundle.min.css"/>
        <!-- <link rel="stylesheet" href="assets/css/Style.css"> -->

        
        <link rel="stylesheet" href="assets/css/alertInput.css">
    </head>
        <body>
            <div class="Main">
        <header class="header-area header-sticky">
                <div class="container">
                    <div class="row">
                        <div class="col-12">
                            <nav class="main-nav">
                                <!-- ***** Logo Start ***** -->
                                <a href="Home.jsp" class="logo">
                                    <img src="assets/images/logo.png" alt="">
                                </a>
                                <!-- ***** Logo End ***** -->
                                <!-- ***** Search End ***** -->
                                
                                <!-- ***** Search End ***** -->
                                <!-- ***** Menu Start ***** -->
                                <ul class="nav" id="listPage">
                                    <li><a href="BestSellerServlet">Best seller</a></li>
                                    <li><a href="DisplayGenreServlet">Genre</a></li>                                                                                                             
                                    <li><a href="Register.jsp" >Register</a></li>
    
                                </ul>   
                                
                                <!-- ***** Menu End ***** -->
                            </nav>
                        </div>
                    </div>
                </div>
            </header>
            
        <div class="loginMain forms">
            <div class="form login">
<div class="form-content">
                    <p>Login</p>
                    <%
                        if(request.getAttribute("status")!=null)
                            {
                                out.print("<div class='text_danger'>"+request.getAttribute("status")+"</div>");
                            }
                        if(request.getAttribute("success") != null)
                        {
                                out.print("<div class='text_success'>"+request.getAttribute("success")+"</div>");
                        }

                    %>                 

                    <form action="LoginServlet" method="POST">

                            <div class="field input-field">
                                <div class="input-container">
                                <label for="email">Email Address</label>
                                <input type="email" value="${cookie.emailC.value}" id="emailInput" placeholder="Email" name="email" >
                                </div>
                                <div id="message"></div>
                            </div>

                            <div class="field input-field">   
                                <div class="input-container pass">                          
                                <label for="password">Password</label>
                                <input type="password" value="${cookie.passC.value}" id="password" placeholder="Password" name="pass" >
                                <i id="pass-toggle-btn" class="fa-solid fa-eye"></i>   
                                </div>   
                                <div id="passwordMessage"></div>                               

                            </div>
                            
                            <div class="form-link">
                                <label><input type="checkbox" ${(cookie.remember.value eq 'ON')? "checked":""} name="remember" value="ON"/>Remember</label>
                                
                                <a href="ForgetPass.jsp" class="forgot-pass">Forgot password?</a>
                                
                            </div>
    
                            
                            <div class="field button-field">
                                <button>Login</button>
                            </div>

                        </form>

                    <div class="form-link">
                        <span>Don't have an account? <a href="Register.jsp" class="link signup-link">Register</a></span>

                    </div>
                    <div class="line"></div>
                   
                    <div class="media-options">
                        <a href="https://accounts.google.com/o/oauth2/auth?scope=email&redirect_uri=http://localhost:9999/FPTeam/loginGG&response_type=code
&client_id=507196321849-krfc104j5mdadoum3q5vcl1fapv607qb.apps.googleusercontent.com&approval_prompt=force" class="field google">
    
                            <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS6WwgH7Nl5_AW9nDCnR2Ozb_AU3rkIbSJdAg&s" alt="" class="google-img">
                            <span>Login with Google</span>
                        </a>
                    </div>
                </div> 
                
        
            </div>
            </div>
            

            
            
        </div>
                     
        <!-- JavaScript -->
        <script src="assets/js/emailAlert.js"></script>
        <script src="assets/js/passwordAlert.js"></script>

        <script>
            const passwordInput = document.getElementById("password");
            const passToggleBtn = document.getElementById("pass-toggle-btn");
            passToggleBtn.addEventListener('click', () => {
                passToggleBtn.className = passwordInput.type === "password" ? "fa-solid fa-eye-slash" : "fa-solid fa-eye";
                passwordInput.type = passwordInput.type === "password" ? "text" : "password";
            });
        </script>

    </body>

    
</html>