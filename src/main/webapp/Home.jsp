    <%-- 
    Document   : Home
    Created on : May 28, 2024, 12:58:34 PM
    Author     : LENOVO
--%>
<%@page import="DAO.ReviewDAO"%>
<%@page import="DAO.GenreDAO"%>
<%@page import="DAO.GameDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Model.Genre" %>
<%@ page import="Model.Game" %>
<%@ page import="Model.Users" %>
<%@ page import="Controller.JavaMongo" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%><!DOCTYPE html>
<html lang="en">

     <head>

        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@100;200;300;400;500;600;700;800;900&display=swap" rel="stylesheet">

        <title>Home</title>

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
                       
                                                                 

                        <%
                            Users user = (Users) session.getAttribute("account");
                            if (user != null) {
                                if (user.getRole() == 2) {
                        %>
                           
                        <li><a href="UploadGame">Upload Game</a></li>
                        <%
                                }
                                if (user.getRole() == 1) {
                        %>
                                  
                        <li><a href="PublishGameServlet">Verify Game</a></li>
                                    <li><a href="ManageUser.jsp">Manage User</a></li>
                                    <li><a href="ReportServlet">Respond Report</a></li>
                                              <li><a href="Statistic.jsp">View Profit </a></li>
                                       <li><a href="LogOutServlet">LOG OUT</a></li>
                        <%
                                }
                                if (user.getRole() == 2 || user.getRole() == 3) {
                        %>
                                     <li><a href="BestSellerServlet"> Best Game</a></li>
                                    <li><a href="DisplayGenreServlet">Genre</a></li>
                                    <li><a href="CallSupport.jsp">Report</a></li>
                                       <li><a href="LogOutServlet">LOG OUT</a></li>
                                    <li><a href="profileServlet">Profile <img src="assets/images/profile-header.jpg" alt=""></a></li>
                        <%
                                }
                        %>
                        <%
                            } else {
                        %>
                              <li><a href="BestSellerServlet"> Best Game</a></li>
                                    <li><a href="DisplayGenreServlet">Genre</a></li>
                                    <li><a href="Login.jsp">LOG IN</a></li>
                                <li><a href="Register.jsp">REGISTER</a></li>
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
                <div class="col-lg-12">
                    <div class="page-content">

                        <!-- ***** Banner Start ***** -->
                        <div class="main-banner">
                            <div class="row">
                                <div class="col-lg-7">
                                    <div class="header-text">
                                        <h6>Welcome To FPTeam</h6>
                                        <h4><em>Welcome</em>  to the  best trading platform to for gamer</h4>
                                        <div class="main-button">
                                            <a href="AboutUs.jsp">About us </a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- ***** Banner End ***** -->

                        <!-- ***** Most Popular Start ***** -->
                        <div class="most-popular">
                            <div class="row">
                                <div class="col-lg-12">
                                    <div class="heading-section">
                                        <h4><em>Most Popular</em> Right Now</h4>
                                    </div>
                                    <div class="row">
                                        <% 
                                          ArrayList<Game> Games = (ArrayList<Game>) GameDAO.getAllGames();
                                          if (Games != null) {
                                            for (Game game : Games) {
                                        %>
                                       <div class="col-lg-3 col-sm-6">
    <div class="item">
        <a href="GameDetailServlet?gameid=<%= game.getId() %>">
            <img src="<%= game.getAvatarLink()%>?usp=sharing" alt="">
            <h4><%= game.getName() %><br></h4>
            <ul>
                <li><i class="fa fa-star"></i> <%= ReviewDAO.getAverageRatingByGame(game) %></li>
                <li><i class="fa fa-download"></i> <%= game.getNumberOfBuyers() %></li>
            </ul>
        </a>
    </div>
</div>

                                        <% 
                                            }
                                          }
                                        %>
                                    </div>
                                    <div class="col-lg-12">
                                        <div class="main-button">
                                            <a href="browse.html">Discover Popular</a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- ***** Most Popular End ***** -->




                        <!-- ***** Genres Start ***** -->
                        <div class="most-popular">
                            <div class="row">
                                <div class="col-lg-12">
                                    <div class="heading-section">
                                        <h4><em>Genres</em></h4>
                                    </div>
                                    <div class="row">
                                        <% 
                                              ArrayList<Genre> genres = (ArrayList<Genre>) GenreDAO.getAllGenres();
                                          if (genres != null) {
                                            for (Genre genre : genres) {
                                        %>
                                        <div class="col-lg-3 col-sm-6">
                                            <div class="item">
                                                <h4><%= genre.getType() %><br></h4>
                                            </div>
                                        </div>
                                        <% 
                                            }
                                          }
                                        %>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- ***** Genres End ***** -->








                        <!-- ***** Explore Area Starts ***** -->
                        <section class="section" id="explore">
                            <div class=" gaming-library container ">
                                <div class="row">
                                    <div class="col-lg-6">
                                        <div class="left-content">
                                            <h2>MIHOYO </h2>
                                            <span>You are allowed to use this HexaShop HTML CSS template. You can feel free to modify or edit this layout. You can convert this template as any kind of ecommerce CMS theme as you wish.</span>
                                            <div class="quote">
                                                <i class="fa fa-quote-left"></i><p>You are not allowed to redistribute this template ZIP file on any other website.</p>
                                            </div>
                                            <p>There are 5 pages included in this HexaShop Template and we are providing it to you for absolutely free of charge at our TemplateMo website. There are web development costs for us.</p>
                                            <p>If this template is beneficial for your website or business, please kindly <a rel="nofollow" href="https://paypal.me/templatemo" target="_blank">support us</a> a little via PayPal. Please also tell your friends about our great website. Thank you.</p>
                                            <div class="main-border-button">
                                                <a href="products.html">Discover More</a>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-lg-6">
                                        <div class="right-content">
                                            <div class="row">
                                                <div class="col-lg-6">
                                                    <div class="leather">
                                                        <h4>Honkai Impact 3</h4>
                                                        <span>2017</span>
                                                    </div>
                                                </div>
                                                <div class="col-lg-6">
                                                    <div class="first-image">
                                                        <img src="assets/images/Honkai_Impact_3rd_logo.png" alt="">
                                                    </div>
                                                </div>
                                                <div class="col-lg-6">
                                                    <div class="second-image">
                                                        <img src="assets/images/Genshin_Impact_cover.jpg" alt="">
                                                    </div>
                                                </div>
                                                <div class="col-lg-6">
                                                    <div class="types">
                                                        <h4>Genshin </h4>
                                                        <span>2018</span>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </section>
                        <!-- ***** Explore Area Ends ***** -->



                        <!-- ***** Social Area Starts ***** -->
                        <section class="section" id="social">
                            <div class="container  ">
                                <div class="row">
                                    <div class="col-lg-12">
                                        <div class="section-heading">
                                            <h2>Social Media</h2>
                                            <span>Details to details is what makes Hexashop different from the other themes.</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class=" gaming-library container">
                                <div class=" row images ">
                                    <div class="col-2">
                                        <div class="thumb">
                                            <div class="icon">
                                                <a href="https://www.facebook.com/">
                                                    <h6>FaceBook</h6>
                                                </a>
                                            </div>
                                            <img src="assets/images/Facebook_Logo_(2019).png" alt="">
                                        </div>
                                    </div>
                                    <div class="col-2">
                                        <div class="thumb">
                                            <div class="icon">
                                                <a href="http://x.com">
                                                    <h6>Twitter</h6>
                                                </a>
                                            </div>
                                            <img src="assets/images/x-social-media-logo-icon.png" alt="">
                                        </div>
                                    </div>
                                    <div class="col-2">
                                        <div class="thumb">
                                            <div class="icon">
                                                <a href="http://instagram.com">
                                                    <h6>Youtube</h6>
                                                </a>
                                            </div>
                                            <img src="assets/images/vecteezy_youtube-icon-vector_11998173.jpg" alt="">
                                        </div>
                                    </div>


                                    <div class="col-2">
                                        <div class="thumb">
                                            <div class="icon">
                                                <a href="http://instagram.com">
                                                    <h6>instagram</h6>
                                                </a>
                                            </div>
                                            <img src="assets/images/Instagram_icon.png" alt="">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </section>
                        <!-- ***** Social Area Ends ***** -->








                        <!-- ***** Gaming Library Start ***** -->
                        <div class="gaming-library profile-library">
                            <div class="col-md-12">
                                <div class="heading-section">
                                    <h4><em>Transaction History</em> </h4>
                                </div>
                                <div class="item">
                                    <ul>

                                        <li><h4>MB bank</h4></li>
                                        <li><h4>Content</h4><span>ID:123</span></li>
                                        <li><h4>Date Added</h4><span>24/08/2036</span></li>
                                        <li><div class="main-border-button border-no-active"><a href="#">120.000</a></div></li>
                                    </ul>
                                </div>
                                <div class="item">
                                    <ul>
                                        <li><h4>MB bank</h4></li>

                                        <li><h4>Content</h4><span>ID:123</span></li>
                                        <li><h4>Date Added</h4><span>22/06/2036</span></li>
                                        <li><div class="main-border-button border-no-active"><a href="#">200.000</a></div></li>
                                    </ul>
                                </div>
                                <div class="item last-item">
                                    <ul>
                                        <li><h4>MB bank</h4></li>

                                        <li><h4>Content</h4><span>ID:123</span></li>
                                        <li><h4>Date Added</h4><span>21/04/2022</span></li>
                                        <li><div class="main-border-button border-no-active"><a href="#">120.000</a></div></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <!-- ***** Gaming Library End ***** -->
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

