<%-- 
    Document   : Home
    Created on : May 28, 2024, 12:58:34 PM
    Author     : LENOVO
--%>

<%@page import="Model.Users"%>
<%@page import="java.util.Random"%>
<%@page import="Model.Game"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Model.Genre"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%><!DOCTYPE html>
<html lang="en">

  <head>
  <style>
        .main-banner {
            position: relative;
            color: white; /* Default color */
        }
        .header-text {
            position: relative;
            z-index: 1;
        }
        .header-text h4, .header-text h6 {
            margin: 0;
            padding: 0;
        }
        .main-banner::after {
            content: "";
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: rgba(0, 0, 0, 0.5); /* Default dark overlay */
            z-index: 0;
        }
    </style>
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
  <div class="container">
    <div class="row">
        <div class="col-lg-12">
            <div class="page-content">
                <% 
                Genre selectedgenre = (Genre) request.getAttribute("selectedGenre");
                ArrayList<Game> games = (ArrayList<Game>) request.getAttribute("games");
                
                if (games != null && !games.isEmpty()) {
                    Random rand = new Random();
                    Game randomGame = games.get(rand.nextInt(games.size()));
                %>
                <div class="main-banner" style="background-image: url('<%= randomGame.getAvatarLink() %>'); background-size: cover; background-position: center;">
                    <div class="row">
                        <div class="col-lg-7">
                            <div class="header-text">
                                <h4><%=selectedgenre.getType() %></h4>
                                <h6><%= selectedgenre.getDescription() %></h6>
                            </div>
                        </div>
                    </div>
                </div>
                <%}%>

                <form action="DisplayGenreServlet" method="post">
                    <div class="form-group">
                        <label>Genres</label>
                        <% 
                        ArrayList<Genre> genres = (ArrayList<Genre>) request.getAttribute("genres");
                        if (genres != null) {
                            for (Genre genre : genres) {
                        %>
                        <div class="form-check">
                            <input class="form-check-input" type="radio" name="selectedGenre" value="<%= genre.getType() %>">
                            <label style="color: white" class="form-check-label"><%= genre.getType() %></label>
                        </div>
                        <% 
                            }
                        }
                        %>
                    </div>
                    <button type="submit" class="btn btn-primary">Submit</button>
                </form>
                     <!-- ***** Games Area Starts ***** -->
<section class="section" id="games">

        <div class="row">
            <div class="col-lg-12">
                <div class="section-heading">
                    <h2>Our Latest Games</h2>
                    <span>Check out all of our games.</span>
                </div>
            </div>
        </div>
        <div class="row">
            <!-- Game Item Start -->
            <% 
         
                if (games != null) {
                    for (Game game : games) {
            %>
            <div class="col-lg-4 col-md-6 game-item">
                <div class="item">
                    <div class="thumb">
                        <div class="hover-content">
                            <ul>
                                <li><a href="GameDetailServlet?gameid=<%= game.getId() %>"><i class="fa fa-eye"></i></a></li>
                            </ul>
                        </div>
                        <!-- Add your image here -->
                        <img src="<%= game.getAvatarLink() %>" alt="<%= game.getName() %>" class="game-image">
                    </div>
                    <div class="down-content">
                        <h4><%= game.getName() %></h4>
                       
                    </div>
                </div>
            </div>
            <% 
                    }
                }
            %>
            <!-- Game Item End -->

            <!-- Additional game items can be added in a similar manner -->

            <div class="col-lg-12">
            <div class="pagination">
                <ul id="pagination-controls">
                    <!-- Pagination controls will be dynamically inserted here by JavaScript -->
                </ul>
            </div>
        </div>
    </div>
</section>

            </div>
        </div>
    </div>
</div>


    
   
              



<!-- ***** Game Presentation End ***** -->
<canvas id="imageCanvas" style="display: none;"></canvas>

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
<script>
    document.addEventListener("DOMContentLoaded", function () {
        const banner = document.querySelector('.main-banner');
        const headerText = document.querySelector('.header-text');
        const imgSrc = banner.style.backgroundImage.slice(5, -2); // Extract URL

        const img = new Image();
        img.src = imgSrc;
        img.crossOrigin = "Anonymous";
        img.onload = function () {
            const canvas = document.getElementById('imageCanvas');
            const ctx = canvas.getContext('2d');
            canvas.width = img.width;
            canvas.height = img.height;
            ctx.drawImage(img, 0, 0, img.width, img.height);

            const imageData = ctx.getImageData(0, 0, img.width, img.height);
            const data = imageData.data;

            let r, g, b, avg;
            let colorSum = 0;

            for (let x = 0, len = data.length; x < len; x += 4) {
                r = data[x];
                g = data[x + 1];
                b = data[x + 2];
                avg = Math.floor((r + g + b) / 3);
                colorSum += avg;
            }

            const brightness = Math.floor(colorSum / (img.width * img.height));

            if (brightness > 127) { // Bright background
                headerText.style.color = "black";
                banner.querySelector('::after').style.background = "rgba(255, 255, 255, 0.5)";
            } else { // Dark background
                headerText.style.color = "white";
                banner.querySelector('::after').style.background = "rgba(0, 0, 0, 0.5)";
            }
        };
    });
</script>
<script>
    
        <% if (games != null) {
            for (Game game : games) { %>
                
                    id: "<%= game.getId() %>";
                    name: "<%= game.getName() %>";
                    avatarLink: "<%= game.getAvatarLink() %>"
                ,
        <% } } %>
    ;

    const GAMES_PER_PAGE = 12;
    let currentPage = 1;

    function displayGames(page) {
        const start = (page - 1) * GAMES_PER_PAGE;
        const end = start + GAMES_PER_PAGE;
        const paginatedGames = games.slice(start, end);

        const gamesList = document.getElementById('games-list');
        gamesList.innerHTML = '';
        paginatedGames.forEach(game => {
            const gameItem = document.createElement('div');
            gameItem.classList.add('col-lg-4', 'col-md-6', 'game-item');
            gameItem.innerHTML = `
                <div class="item">
                    <div class="thumb">
                        <div class="hover-content">
                            <ul>
                                <li><a href="GameDetailServlet?gameid=${game.id}"><i class="fa fa-eye"></i></a></li>
                            </ul>
                        </div>
                        <img src="${game.avatarLink}" alt="${game.name}" class="game-image">
                    </div>
                    <div class="down-content">
                        <h4>${game.name}</h4>
                    </div>
                </div>
            `;
            gamesList.appendChild(gameItem);
        });
    }

    function setupPagination(totalGames) {
        const totalPages = Math.ceil(totalGames / GAMES_PER_PAGE);
        const paginationControls = document.getElementById('pagination-controls');
        paginationControls.innerHTML = '';

        for (let i = 1; i <= totalPages; i++) {
            const pageItem = document.createElement('li');
            pageItem.innerHTML = `<a href="javascript:void(0)" onclick="goToPage(${i})">${i}</a>`;
            paginationControls.appendChild(pageItem);
        }
    }

    function goToPage(page) {
        currentPage = page;
        displayGames(page);
    }

    document.addEventListener('DOMContentLoaded', () => {
        setupPagination(games.length);
        displayGames(currentPage);
    });
</script>
    <!-- jQuery -->
    <script src="assets/js/jquery-2.1.0.min.js"></script>

    <!-- Bootstrap -->
    <script src="assets/js/popper.js"></script>
    <script src="assets/js/bootstrap.min.js"></script>

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
    <script>
     
    </script>
</body>

</html>
