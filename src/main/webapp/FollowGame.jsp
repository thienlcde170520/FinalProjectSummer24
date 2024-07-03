    <%@page import="Model.Follow"%>
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

        <title>Cyborg - Awesome HTML5 Template</title>

       
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
                                <li><a href="browse.html">Browse</a></li>
                                <li><a href="DisplayGenreServlet">Genre</a></li>
                                
                                <%
    Users user = (Users) session.getAttribute("account");
    if (user != null && user.getRole()== 2 ) {
%>
        <li><a href="UploadGame">Upload Game</a></li>
<%
    }
%>
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

        <!-- ***** Header Area End ***** -->

        <!-- ***** Game Presentation Start ***** -->
        <section class="game-presentation">
            <div class="container">
                <div class="row">
                    <div class="col-12">
                        <div class="section-heading">
                            <h1>Follow Game</h1>
                        </div>
                     

                   <div class="game-results">
    <%
        ArrayList<Game> games = (ArrayList<Game>) request.getAttribute("games");
        ArrayList<Follow> follows = (ArrayList<Follow>) request.getAttribute("follows");

        if (follows != null && !follows.isEmpty()) {
            for (Follow follow : follows) {
                for (Game game : games) {
                    if (game.getId().equals(follow.getIdGame())) {
    %>
    <div class="game-box">
        <a href="GameDetailServlet?gameid=<%= game.getId() %>">
            <img src="<%= game.getAvatarLink() %>" alt="Game Avatar" class="game-avatar">
        </a>
        <div class="game-description">
            <h2><%= game.getName() %></h2>
            <p><%= game.getDescription() %></p>
            <p>Price: <%= game.getPrice() %> VNĐ</p>
            <p>Follow Price: <%= follow.getFollowPrice() %> VNĐ</p>
            <p>Follow Time: <%= follow.getFollowTime() %></p>
        </div>
    </div>
    <%
                    }
                }
            }
        } else {
    %>
    <p>No follow history found.</p>
    <%
        }
    %>
</div>
                    </div>
                </div>
            </div>
        </section>
        <!-- ***** Game Presentation End ***** -->
        
        
        
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

