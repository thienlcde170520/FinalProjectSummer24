<%@page import="Model.Gamers"%>
<%@page import="Controller.JavaMongo"%>
<%@page import="Model.Genre"%>
<%@page import="Model.Publishers"%>
<%@page import="Model.Review"%>
<%@page import="Model.Game"%>
<%@page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Fortnite - Game Details</title>
        <!-- Bootstrap core CSS -->
        <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <!-- Additional CSS Files -->
        <link rel="stylesheet" href="assets/css/fontawesome.css">
        <link rel="stylesheet" href="assets/css/templatemo-cyborg-gaming.css">
        <link rel="stylesheet" href="assets/css/owl.css">
        <link rel="stylesheet" href="assets/css/animate.css">
        <link rel="stylesheet" href="assets/css/Style.css">
        <link rel="stylesheet" href="https://unpkg.com/swiper@7/swiper-bundle.min.css" />
        <!-- Custom CSS -->
        <style>
            /* Add your custom styles here */
        </style>
    </head>

    <body>
        <!-- Preloader Start -->
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
        <!-- Preloader End -->
        <%
            // Retrieve attributes from request
            ArrayList<Genre> genres = (ArrayList<Genre>) request.getAttribute("genres");
            Game game = (Game) request.getAttribute("game");
            ArrayList<Review> reviews = (ArrayList<Review>) request.getAttribute("reviews");
            Double rating = (Double) request.getAttribute("rating");
            Publishers publisher = (Publishers) request.getAttribute("publisher");

            Boolean hasBuyObj = (Boolean) request.getAttribute("hasBuy");
            boolean hasBuy = hasBuyObj != null && hasBuyObj.booleanValue();
        %>

        <!-- Header Area Start -->
        <header class="header-area header-sticky">
            <div class="container">
                <div class="row">
                    <div class="col-12">
                        <nav class="main-nav">
                            <a href="index.html" class="logo">
                                <img src="assets/images/logo.png" alt="">
                            </a>
                            <div class="search-input">
                                <form id="search" action="#">
                                    <input type="text" placeholder="Type Something" id="searchText" name="searchKeyword"
                                           onkeypress="handle" />
                                    <i class="fa fa-search"></i>
                                </form>
                            </div>
                            <ul class="nav">
                                <li><a href="index.html">Home</a></li>
                                <li><a href="browse.html">Browse</a></li>
                                <li><a href="details.html" class="active">Details</a></li>
                                <li><a href="streams.html">Streams</a></li>
                                <li><a href="profile.html">Profile <img src="assets/images/profile-header.jpg"
                                                                        alt=""></a></li>
                            </ul>
                            <a class="menu-trigger">
                                <span>Menu</span>
                            </a>
                        </nav>
                    </div>
                </div>
            </div>
        </header>
        <!-- Header Area End -->

        <div class="container">
            <div class="row">
                <div class="col-lg-12">
                    <div class="page-content">
                        <!-- Featured Start -->
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="feature-banner header-text">
                                    <div class="row">
                                        <div class="col-lg-4">
                                            <img src="<%= game.getAvatarLink()%>" alt=""
                                                 style="border-radius: 23px;">
                                        </div>
                                        <div class="col-lg-8">
                                            <div class="thumb">
                                                <img src="https://coffective.com/wp-content/uploads/2018/06/default-featured-image.png.jpg" alt=""
                                                     style="border-radius: 23px;">
                                                <a href="<%= game.getLinkTrailer()%>" target="_blank"><i
                                                        class="fa fa-play"></i></a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- Featured End -->

                        <!-- Details Start -->
                        <div class="game-details">
                            <div class="row">
                                <div class="col-lg-12">
                                    <h2><%= game.getName()%> Details</h2>
                                </div>
                                <div class="col-lg-6">
                                    <div class="left-info">
                                        <div class="left">
                                            <h4><%= game.getName()%></h4>

                                            <%-- Display genres --%>
                                            <p class="genre-p">Genres:
                                                <%
                                                    for (Genre genre : genres) {
                                                        out.println(genre.getType());
                                                        if (genres.indexOf(genre) < genres.size() - 1) {
                                                            out.print(", ");
                                                        }
                                                    }
                                                %>
                                            </p>

                                            <%-- Display publisher --%>
                                            <p class="publisher-p">Game Publisher: <%= publisher.getName()%></p>
                                        </div>
                                        <% if (!hasBuy) {%>
                                        <a id="buyNowButton" class="btn btn-primary" href="PayProcessServlet?gameId=<%= game.getId()%>">Buy Now</a>
                                        <button type="button" class="btn btn-outline-primary">Follow</button>
                                        <% } else {%>
                                        <a id="DowloadNowButton" class="btn btn-primary" href="<%= game.getGameLink()%>">Install Game</a>
                                        <% }%>
                                    </div>
                                </div>
                                <div class="col-lg-6">
                                    <div class="right-info " style="color: #F0F5FF">
                                        <ul>
                                            <li><i class="fa fa-star"></i> <%= rating%> </li>
                                            <li><i class="fa fa-download"></i><%= game.getNumberOfBuyers()%></li>

                                        </ul>
                                    </div>
                                </div>
                                <div class="col-lg-12">
                                    <h4>Description</h4>
                                    <p><%= game.getDescription()%></p>
                                </div>
                                <div class="col-lg-6">
                                    <div class="minimum-requirements" style="padding-right: 20px; color: #F0F5FF ; padding-top: 50px; padding-bottom: 50px">
                                        <h4>Minimum System Requirements</h4>
                                        <ul>
                                            <li>CPU: <%=game.getMinimumCPU()%></li>
                                            <li>Memory: <%= game.getMinimumRAM()%></li>
                                            <li>Graphics: <%= game.getMinimumGPU()%></li>

                                        </ul>
                                    </div>
                                </div>
                                <div class="col-lg-6">
                                    <div class="recommended-requirements" style="padding-left: 80px;color: #F0F5FF ; padding-top: 50px;  padding-bottom:  50px ">
                                        <h4>Recommended System Requirements</h4>
                                        <ul>
                                            <li>CPU: <%=game.getMaximumCPU()%></li>
                                            <li>Memory: <%= game.getMaximumRAM()%></li>
                                            <li>Graphics: <%= game.getMaximumGPU()%></li>
                                            <!-- Add more recommended requirements here -->
                                        </ul>
                                    </div>
                                </div>
                            </div>
                            <!-- Gamer Reviews Start -->
                            <div class="gamer-reviews ">
                                <div class="row">
                                    <div class="col-lg-12">
                                        <h3>Gamer Reviews</h3>
                                    </div>
                                    <div class="col-lg-12">
                                      
                                        <form id="review-form" action="ReviewGameServlet" method="post">
                                            <!-- Hidden input fields for gamer ID and game ID -->
                                            <input type="hidden" id="gameId" name="gameId" value="<%= game.getId()%>">

                                            <div class="form-group">
                                                <label for="rating">Rating (0-5)</label>
                                                <input type="number" class="form-control" id="rating" name="rating" min="0" max="5" step="0.1" required>
                                            </div>
                                            <div class="form-group">
                                                <label for="review">Review Description</label>
                                                <textarea class="form-control" id="review" name="review" rows="3" required></textarea>
                                            </div>
                                            <button type="submit" class="btn btn-primary">Submit Review</button>
                                        </form>
                                    </div>

                                </div>


                                <!-- Display user reviews -->
                                <div class="row mt-4">
  <div class="col-lg-12">
    <h4>User Reviews:</h4>
    <ul class="list-unstyled" style="color: #F0F5FF">
        <% 
        for (Review review : reviews) {
            // Get the user (gamer) associated with this review
            Gamers user = JavaMongo.getGamerByGamerId(review.getIdGamer());
        %>
        <li>
            <strong><%= user.getName() %>:</strong> "<%= review.getDescription() %>" 
            <br> Rating: <%= review.getRating() %>
            
            <!-- Delete button form -->
            <form action="ReviewGameServlet" method="get" style="display: inline;">
                <input type="hidden" name="reviewGameId" value="<%= review.getIdGame() %>">
                <input type="hidden" name="reviewGamerId" value="<%= review.getIdGamer() %>">
                <button type="submit" class="btn btn-sm btn-danger">Delete</button>
            </form>
        </li>
        <% } %>
    </ul>
</div>

</div>

                            </div>


                        </div>
                    </div>
                </div>
            </div>

        </div>
        <!-- Gamer Reviews End -->
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
        <script src="vendor/bootstrap/js/bootstrap.min.js"></script>

        <script src="assets/js/isotope.min.js"></script>
        <script src="assets/js/owl-carousel.js"></script>
        <script src="assets/js/tabs.js"></script>
        <script src="assets/js/popup.js"></script>
        <script src="assets/js/custom.js"></script>
    </body>
</html>