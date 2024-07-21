<%-- 
    Document   : Home
    Created on : May 28, 2024, 12:58:34 PM
    Author     : LENOVO
--%>

<%@page import="Model.Users"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%><!DOCTYPE html>
<html lang="en">

    <head>

        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@100;200;300;400;500;600;700;800;900&display=swap" rel="stylesheet">


    <title>Cyborg - Awesome HTML5 Template</title>
    <style>
        .error{
            color: red;
            font-weight: bold;
            text-align: center;
            background-color: #ffcccc;
            border-radius: 2px;
            
        }
</style>
    <!-- Bootstrap core CSS -->
       <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">



        <!-- Additional CSS Files -->
        <link rel="stylesheet" href="assets/css/bootstrap.min.css">
        <link rel="stylesheet" href="assets/css/fontawesome.css">
        <link rel="stylesheet" href="assets/css/templatemo-cyborg-gaming.css">
        <link rel="stylesheet" href="assets/css/owl.css">
        <link rel="stylesheet" href="assets/css/animate.css">
        <link rel="stylesheet" href="https://unpkg.com/swiper@7/swiper-bundle.min.css"/>
        <link rel="stylesheet" href="assets/css/Style.css">
        <link rel="stylesheet" href="assets/css/style.css">
        <!--
        
        TemplateMo 579 Cyborg Gaming
        
        https://templatemo.com/tm-579-cyborg-gaming
        
        -->
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
                                <form id="search" action="SearchGameServlet" method="get">
                                    <input type="text" placeholder="Type Something" id='searchText' name="searchKeyword" onkeypress="handle" />
                                    <i class="fa fa-search"></i>
                                </form>
                            </div>
                            <!-- ***** Search End ***** -->
                            <!-- ***** Menu Start ***** -->
                            <ul class="nav">
                                <li><a href="Home.jsp" class="active">Home</a></li>




                                <%    Users user = (Users) session.getAttribute("account");
                                %>
                                <%
                                    if (user != null && user.getRole() == 2) {
                                %>
                                <li><a href="UploadGame">Upload Game</a></li>

                                <%
                                    }
                                %>

                                <%
                                    if (user != null && user.getRole() == 1) {
                                %>
                                <li><a href="PublishGameServlet">Verify Game</a></li>
                                <li><a href="ManageUser.jsp"> Manage User</a></li>
                                <li><a href="ReportServlet">Respond Report </a></li>
                                    <%
                                        }
                                    %>

                                <li><a href="LogOutServlet">LOG OUT</a></li>
                                    <%
                                        if (user != null && user.getRole() == 2 || user.getRole() == 3) {
                                    %>    <li><a href="BestSellerServlet">Game</a></li>

                                <li><a href="DisplayGenreServlet">Genre</a></li>
                                <li><a href="CallSupport.jsp">Report </a></li>
                                <li><a href="profileServlet">Profile <img src="assets/images/profile-header.jpg" alt=""></a></li>

                                <%
                                    }
                                %>

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
        <!-- ***** Header Area End ***** -->

        <div class="container">


            <div class="row">
                <!-- Form Section -->
                <div class="col-12">
                    <div class="page-content">
                   
                        <section class="game-input-form" style="background-color: #000; color: #F0F5FF">
                                 <h2>Update Profile</h2>
                            <form action="UpdateProfileServlet" method="POST"   enctype="multipart/form-data">
                                <%
                                    String mess = (String) request.getAttribute("mess");

                                    if (mess != null) {
                                %>
                                <p class="error"><%= mess%></p>
                                <%
                                    }
                                %>


                                <div class="form-group">
                                    <label for="gamerAvatar">Gamer Avatar</label>
                                    <input type="file" class="form-control-file" id="gamerAvatar" name="gamerAvatar" multiple>

                                </div>

                                <div class="form-group">
                                    <label for="newName">Name</label>
                                    <input type="text" class="form-control" id="Name" name="newName" placeholder="Enter your new name ">
                                </div>

                                <div class="form-group">
                                    <label for="newEmail">Email</label>
                                    <input type="text" class="form-control" id="Email" name="newEmail" placeholder="Enter your new Email ">
                                </div>

                                <div class="form-group">
                                    <label for="newDOB">Date of Birth</label>
                                    <input type="date" class="form-control" id="DOB" name="newDOB" placeholder="Enter your date of birth ">
                                </div>

                                <div class="form-group">
                                    <label for="newPassWord">PassWord</label>
                                    <input type="password" class="form-control" id="PassWord" name="newPassWord" placeholder="Enter your new PassWord ">
                                </div>

                                <div class="form-group">
                                    <label for="confirmPass">Confirm Password</label>
                                    <input type="password" class="form-control" id="ConfirmPassWord" name="confirmPass" placeholder="Confirm your Password">
                                </div>




                                <!-- Submit Button -->
                                <div class="row">
                                    <div class="col-12 text-center">
                                        <button type="submit" class="btn btn-primary">Submit</button>
                                    </div>
                                </div>

                            </form>
                        </section>
                    </div>
                </div>
            </div>

        </div>




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

