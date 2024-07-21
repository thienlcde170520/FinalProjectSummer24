<%-- 
    Document   : error
    Created on : Jun 18, 2024, 11:55:46 PM
    Author     : tuanh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>

        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@100;200;300;400;500;600;700;800;900&display=swap" rel="stylesheet">

        <title>Cyborg - Awesome HTML5 Template</title>

        <!-- Bootstrap core CSS -->
        <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">


        <!-- Additional CSS Files -->
        <link rel="stylesheet" href="assets/css/fontawesome.css">
        <link rel="stylesheet" href="assets/css/templatemo-cyborg-gaming.css">
        <link rel="stylesheet" href="assets/css/owl.css">
        <link rel="stylesheet" href="assets/css/animate.css">
        <link rel="stylesheet" href="https://unpkg.com/swiper@7/swiper-bundle.min.css"/>
        <link rel="stylesheet" href="assets/css/Style.css">
        <!--
        
        TemplateMo 579 Cyborg Gaming
        
        https://templatemo.com/tm-579-cyborg-gaming
        
        -->
        
        <!--<link rel="stylesheet" type="text/css" href="assets/css/register.css">-->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <style>
            .container{
                background: #1f2122;
            }
        </style>
    </head>
    <body>
        <!-- ***** Preloader Start ***** -->
        <div id="js-preloader" class="js-preloader">
            <div class="preloader-inner">
                <span class="dot"></span>
                <div class="dots">
                    <span></span>
                    <span></span>
                    <span></span>
                </div>
            </div>
        </div>
        <!-- ***** Preloader End ***** -->

        <!-- ***** Header Area Start ***** -->
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
                            <div class="search-input">
                                <form id="search" action="#">
                                    <input type="text" placeholder="Type Something" id='searchText' name="searchKeyword" onkeypress="handle" />
                                    <i class="fa fa-search"></i>
                                </form>
                            </div>
                            <!-- ***** Search End ***** -->
                            <!-- ***** Menu Start ***** -->
                            <ul class="nav">
                                <li><a href="Home.jsp" class="active">Home</a></li>
                                <li><a href="browse.html">Browse</a></li>
                                <li><a href="details.html">Genre</a></li>
                                
                               
                               <li><a href="LogOutServlet">LOG OUT</a></li>

                                <li><a href="profileServlet">Profile <img src="assets/images/profile-header.jpg" alt=""></a></li>

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
                               
        <section class="main forms">
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
        
        <footer>
    <div class="container">
      <div class="row">
        <div class="col-lg-12">
          <p>Copyright © 2036 <a href="#">Cyborg Gaming</a> Company. All rights reserved. 
          
          <br>Design: <a href="https://templatemo.com" target="_blank" title="free CSS templates">TemplateMo</a></p>
        </div>
      </div>
    </div>
  </footer>



        <!-- Scripts -->
        <!-- Bootstrap core JavaScript -->
        <!-- jQuery -->
        <script src="assets/js/jquery-2.1.0.min.js"></script>

        <!-- Bootstrap -->
        <script src="assets/js/popper.js"></script>
        <script src="assets/js/bootstrap.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <!-- Plugins -->
        <script src="assets/js/owl-carousel.js"></script>
        <script src="assets/js/accordions.js"></script>
        <script src="assets/js/datepicker.js"></script>
        <script src="assets/js/scrollreveal.min.js"></script>
        <script src="assets/js/waypoints.min.js"></script>
        <script src="assets/js/jquery.counterup.min.js"></script>
        <script src="assets/js/imgfix.min.js"></script>
        <script src="assets/js/slick.js"></script>
        <script src="assets/js/lightbox.js"></script>
        <script src="assets/js/isotope.js"></script>

        <!-- Global Init -->
        <script src="assets/js/custom.js"></script>
    </body>
</html>
