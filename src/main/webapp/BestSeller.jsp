<%@page import="Model.Users"%>
<%@page import="java.util.List"%>
<%@page import="DAO.GenreDAO"%>
<%@page import="DAO.PublisherDAO"%>
<%@page import="Model.Publishers"%>
<%@page import="DAO.ReviewDAO"%>
<%@page import="Model.Game"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
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

                            
                           
                
                                <%    Users user = (Users) session.getAttribute("account");
%>
                                <%
    
    if (user != null && user.getRole()== 2 ) {
%>
        <li><a href="UploadGame">Upload Game</a></li>
   
<%
    }
%>

                <%
    if (user != null && user.getRole()== 1 ) {
%>
        <li><a href="PublishGameServlet">Verify Game</a></li>
          <li><a href="ManageUser.jsp"> Manage User</a></li>
           <li><a href="ReportServlet">Respond Report </a></li>
<%
    }
%>
        
           <li><a href="LogOutServlet">LOG OUT</a></li>
       <%
                               
                               if (user != null && user.getRole()== 2 ||  user.getRole()== 3 ) {
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
  
<%  ArrayList<Game> games = (ArrayList<Game>) request.getAttribute("games");%>
  <div class="container">
    <div class="row">
      <div class="col-lg-12">
        <div class="page-content">

          <!-- ***** Featured Games Start ***** -->
          <div class="row">
           <div class="col-lg-8">
  <div class="featured-games header-text">
    <div class="heading-section">
      <h4><em>Game</em> of all time</h4>
    </div>
    <div class="owl-features owl-carousel">
      <% 
        // Check if the games list is not null and has elements
        if (games != null && !games.isEmpty()) {
          int maxGamesToShow = 4;
          for (int i = 0; i < Math.min(maxGamesToShow, games.size()); i++) {
            Game game = games.get(i);
      %>
      <div class="item">
        <div class="thumb">
          <img src="<%= game.getAvatarLink() %>" alt="<%= game.getName() %>">
          <div class="hover-effect">
            <h6><%= game.getNumberOfBuyers() %> Buyers</h6>
          </div>
        </div>
        <h4><%= game.getName() %><br><span><%= game.getNumberOfBuyers() %> Downloads</span></h4>
        <ul>
            <li><i class="fa fa-star"></i> <%= ReviewDAO.getAverageRatingByGame(game) %></li>
          <li><i class="fa fa-download"></i> <%= game.getNumberOfBuyers() %></li>
        </ul>
      </div>
      <% 
          }
        } else {
      %>
      <p>No games found.</p>
      <% 
        }
      %>
    </div>
  </div>
</div>
<%  ArrayList<Publishers> publishers = (ArrayList<Publishers>) request.getAttribute("publishers"); %>
<div class="col-lg-4">
  <div class="top-streamers">
    <div class="heading-section">
      <h4><em>Top</em> Streamers</h4>
    </div>
    <ul>
      <% 
        // Check if the publishers list is not null and has elements
        if (publishers != null && !publishers.isEmpty()) {
          int maxPublishersToShow = 5;
          for (int i = 0; i < Math.min(maxPublishersToShow, publishers.size()); i++) {
            Publishers publisher = publishers.get(i);
            int rank = i + 1;
      %>
      <li>
        <span><%= String.format("%02d",rank) %></span>
        <img src="<%= publisher.getAvatarLink() %>" alt="<%= publisher.getName() %>" style="max-width: 46px; border-radius: 0%; margin-right: 10px;">
        <h6> <%= publisher.getName()%>(<%= PublisherDAO.getNumberOfGameSoughtByPublisher(publisher.getId()) %> games sold)</h6>
        <div class="main-button">
          <a href="DisplayPublisherServlet?publisherName=<%= publisher.getName() %>">Detail</a>
        </div>
      </li>
      <% 
          }
        } else {
      %>
      <li>No publishers found.</li>
      <% 
        }
      %>
    </ul>
  </div>
</div>

          </div>
          <!-- ***** Featured Games End ***** -->

<div class="live-stream">
    <div class="col-lg-12">
        <div class="heading-section">
            <h4><em>Most Popular</em> Game</h4>
        </div>
    </div>
   <div class="row">
  <div class="col-lg-12">
    <!-- Form for choosing time period and metric -->
    <form id="bestSellerForm" method="POST" action="BestSellerServlet">
        <!-- Tabs for choosing time period -->
        <ul class="nav nav-tabs">
            <li class="nav-item">
                <a class="nav-link active" id="day-tab" data-timeperiod="day" href="#">Day</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" id="week-tab" data-timeperiod="week" href="#">Week</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" id="month-tab" data-timeperiod="month" href="#">Month</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" id="year-tab" data-timeperiod="year" href="#">Year</a>
            </li>
        </ul>
        <div class="mt-3">
            <!-- Buttons for choosing metric -->
            <button type="submit" class="btn btn-secondary" name="metric" value="buyers" id="buyers-btn">Number of Buyers</button>
            <button type="submit" class="btn btn-secondary" name="metric" value="profit" id="profit-btn">Profit</button>
        </div>
        <input type="hidden" id="timeperiod" name="timeperiod" value="day">
    </form>
</div>
</div>
<div class="row mt-3" id="games-container">
   <%
    List<Game> gamesSort = (List<Game>) request.getAttribute("gamesSort");
    if (gamesSort != null && !gamesSort.isEmpty()) {
        for (Game game : gamesSort) {
    %>
        <div class="col-lg-3 col-sm-6 game-item">
            <div class="thumb">
                <img src="<%= game.getAvatarLink() %>" alt="">
                <div class="hover-effect">
                    <div class="content">
                        <ul>
                            <li><a href="#"> <%= game.getNumberOfBuyers() %> Buyers</a></li>
                            <li><a href="#"><%= game.getName() %></a></li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="down-content">
                <div class="avatar">
                    <img src="<%= PublisherDAO.getPublisherByGameId(game.getId()).getAvatarLink() %>" alt="" style="max-width: 46px; border-radius: 0%; float: left;">
                </div>
                <span> Genre : <%= GenreDAO.getGenresByGameID(game.getId()) %></span>
            </div>
        </div>
    <% 
        } 
    } else { 
    %>
        <p>No games available for the selected criteria.</p>
    <% 
    } 
    %>
   
</div>


        </div>
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

<script src="vendor/jquery/jquery.min.js"></script>
    <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
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
<script>
    // JavaScript to update the hidden field with the selected time period
    document.querySelectorAll('.nav-link').forEach(tab => {
        tab.addEventListener('click', (event) => {
            event.preventDefault();
            document.querySelectorAll('.nav-link').forEach(link => link.classList.remove('active'));
            tab.classList.add('active');
            document.getElementById('timeperiod').value = tab.getAttribute('data-timeperiod');
        });
    });
</script>
        <!-- Global Init -->
        <script src="assets/js/custom.js"></script>


  </body>

</html>
