<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register Page</title>
        <link rel="stylesheet" type="text/css" href="assets/css/stylelist.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css"> 

        
        <!--bonus-->
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@100;200;300;400;500;600;700;800;900&display=swap" rel="stylesheet">
        <link href="assets/css/bootstrap.min.css" rel="stylesheet">



        <!-- Additional CSS Files -->
        <link rel="stylesheet" href="assets/css/fontawesome.css">
        <link rel="stylesheet" href="assets/css/header_design.css">
        <link rel="stylesheet" href="assets/css/owl.css">
        <link rel="stylesheet" href="assets/css/animate.css">
        <link rel="stylesheet" href="https://unpkg.com/swiper@7/swiper-bundle.min.css"/>
        <link rel="stylesheet" href="assets/css/Style.css">

        
        <link rel="stylesheet" href="assets/css/alertInput.css">

        <style>
            .mess{
                color: red;
                background-color: #ffcccc;
                margin: 17px 0;
                border-radius: 2px;
                text-align: center;
            }
            .Inputerror{
                font-size: small;
                color: red;
            }
            #rolemess{
                margin: -33px 0 0 0;
            }

        </style>

        
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
                                        <li><a href="Home.jsp">Home</a></li>

                                        <li><a href="browse.html">Best Seller</a></li>

                                        <li><a href="details.html">Genre</a></li>                                                                                                             
                                        <li><a href="Login.jsp" >Login</a></li>
        
                                    </ul>   
                                    <a class='menu-trigger'>
                                        <span>Menu</span>
                                    </a>
                                    <!-- ***** Menu End ***** -->
                                </nav>
                            </div>
                        </div>
                    </div>
                </header>

            
        <div class="register forms">
        <!-- Signup Form -->
            <div class="form signup">
                <div class="form-content">
                    <p>Register</p>
                    <%
                        String mess = (String) request.getAttribute("mess");
                        
                        if (mess != null) {
                    %>
                        <div class="mess"><%= mess %></div>
                    <%
                        }
                    %>

                    <form action="SignUpServlet" method="POST">
                            <div class="field input-field">
                                <div class="input-container">
                                <label for="name">Username</label>
                                <input type="text" id="namespace" placeholder="Name" name="name" class="input">
                            </div>
                                <div id="messageName"></div>

                                <div id="namemess" class="Inputerror"><%= request.getAttribute("namemess") != null ? request.getAttribute("namemess") : "" %></div><!--name-->

                            </div>
                            <div class="field input-field">
                                <div class="input-container">
                                    <label for="email">Email Address</label>
                                    <input type="email" id="emailInput" placeholder="Email" name="email" class="input">
                                </div>
                                <div id="message"></div>
                                <div id="emailmess" class="Inputerror"><%= request.getAttribute("emailmess") != null ? request.getAttribute("emailmess") : "" %></div><!--email-->
                            </div>
                            <div class="field input-field">

                                <div class="input-container pass">
                                <label for="password">Password</label>
                                <input type="password" id="password" placeholder="Create password" name="password" >
                                <i id="pass-toggle-btn" class="fa-solid fa-eye"></i>
                            </div>
                                <div id="passwordMessage"></div>
                                <div id="passmess" class="Inputerror"><%= request.getAttribute("passmess") != null ? request.getAttribute("passmess") : "" %></div> <!--pass-->
                            </div>
                            <div class="field input-field">
                                <div class="input-container pass">
                                <label for="password">Confirm Password</label>
                                <input type="password" id="con_password" placeholder="Confirm password" name="confirm_password" >
                                <i id="con_pass-toggle-btn" class="fa-solid fa-eye"></i> 
                                </div>
                                <div id="conPasswordMessage"></div>

                                <div id="conpassmess" class="Inputerror"><%= request.getAttribute("conpassmess") != null ? request.getAttribute("conpassmess") : "" %></div><!--con_pass-->

                            </div>
                            <div class="role-selection">
                                <label for="role" style="font-weight: bold;">Choose your role:</label>
                                <label for="gamer">Gamer</label>
                                <input type="radio" id="gamer" name="role" value="gamer">
                                <label for="publisher">Publisher</label>
                                <input type="radio" id="publisher" name="role" value="publisher">                               
                            </div>
                            <div id="rolemess" class="Inputerror"><%= request.getAttribute("rolemess") != null ? request.getAttribute("rolemess") : "" %></div>
                            
                            <div class="field button-field">
                                <button>Register</button>
                            </div>
                            <div class="form-link">
                                <span>Turn back to <a href="Login.jsp" class="link signup-link">Login</a></span>
                            </div>
                        </form>


                    
                </div>
            </div>

        </div>
                    </div>
        <!-- JavaScript -->
        
        <script>
            // Lắng nghe sự kiện click vào trường tên người dùng
            document.getElementById("namespace").onclick = function() {
                document.getElementById("namemess").innerHTML = ""; // Xóa thông báo tên người dùng
            };
            document.getElementById("emailInput").onclick = function() {
                document.getElementById("emailmess").innerHTML = ""; // Xóa thông báo tên người dùng
            };
            document.getElementById("password").onclick = function() {
                document.getElementById("passmess").innerHTML = ""; // Xóa thông báo tên người dùng
            };
            document.getElementById("con_password").onclick = function() {
                document.getElementById("conpassmess").innerHTML = ""; // Xóa thông báo tên người dùng
            };
        </script>
        <script src="assets/js/emailAlert.js"></script>
        <script src="assets/js/passwordAlert.js"></script>
        <script src="assets/js/nameAlert.js"></script>

        <script>
        const passwordInput = document.getElementById("password");
        const passToggleBtn = document.getElementById("pass-toggle-btn");
        const conpassToggleBtn = document.getElementById("con_pass-toggle-btn");
        const passconInput = document.getElementById("con_password");

       


        passToggleBtn.addEventListener('click', () => {
            passToggleBtn.className = passwordInput.type === "password" ? "fa-solid fa-eye-slash" : "fa-solid fa-eye";
            passwordInput.type = passwordInput.type === "password" ? "text" : "password";
        });

        conpassToggleBtn.addEventListener('click',() =>{
            conpassToggleBtn.className = passconInput.type === "password" ? "fa-solid fa-eye-slash" : "fa-solid fa-eye";
            passconInput.type = passconInput.type === "password" ? "text" : "password";
        });
        </script>
    </body>

    
</html>