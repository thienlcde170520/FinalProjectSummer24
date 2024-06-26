<%@page import="DAO.GamerDAO"%>
<%@page import="Model.Users"%>
<%@page import="Model.Bill"%>
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
    .genre-button {
        background: none;
        border: none;
        color: blue;
        text-decoration: underline;
        cursor: pointer;
        padding: 0;
        font: inherit;
    }
    .genre-button:hover {
        text-decoration: none;
    }
    .genre-container {
        display: inline;
    }
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
            Bill bill = (Bill) request.getAttribute("bill");
            Boolean hasBuyObj = (Boolean) request.getAttribute("hasBuy");
            boolean hasBuy = hasBuyObj != null && hasBuyObj.booleanValue();
            Boolean isRefundableObj = (Boolean) request.getAttribute("isRefundable");
            boolean isRefundable = isRefundableObj != null && isRefundableObj.booleanValue();
           
        %>

        <!-- Header Area Start -->

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
                                <li><a href="details.html">Genre</a></li>
                                
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

                                            <div class="genre-container">
    Genres:
    <%
        for (Genre genre : genres) {
    %>
    <form action="DisplayGenreServlet" method="post" style="display:inline;">
        <input type="hidden" name="selectedGenre" value="<%= genre.getType() %>">
        <button type="submit" class="genre-button"><%= genre.getType() %></button>
    </form>
    <%
        if (genres.indexOf(genre) < genres.size() - 1) {
            out.print(", ");
        }
    }
    %>
    
<a href="DisplayPublisherServlet?publisherName=<%= publisher.getName() %>" class="publisher-p">Game Publisher: <%= publisher.getName() %></a>


</div>
                                       <%
// Check if logged-in user is the publisher
Users loggedInUser = (Users) session.getAttribute("account");
boolean isPublisher = loggedInUser != null && loggedInUser.getId().equals(publisher.getId());
%>

<% if (isPublisher) { %>
    <!-- Display update button for publisher -->
    <a id="UpdateButton" class="btn btn-primary" href="UpdateGameServlet?gameId=<%= game.getId() %>">Update</a>
<% } else { %>
    <% if (!hasBuy) { %>
        <a id="buyNowButton" class="btn btn-primary" href="PayProcessServlet?gameId=<%= game.getId() %>">Buy Now</a>
        <button type="button" class="btn btn-outline-primary">Follow</button>
    <% } else { %>
        <a id="DownloadNowButton" class="btn btn-primary" href="<%= game.getGameLink() %>">Install Game</a>
        
        <% if (isRefundable) { %>
            <form action="RefundServlet" method="get" style="display:inline;">
                <input type="hidden" name="gameId" value="<%= bill.getGameId() %>">
                <input type="hidden" name="billId" value="<%= bill.getId() %>">
                <input type="hidden" name="gamerId" value="<%= bill.getGamerId() %>">
                <input type="hidden" name="refundnumber" value="<%= bill.getBuyPrice() %>">
                <button type="submit" class="btn btn-primary">Refund</button>
            </form>
        <% } %>
    <% } %>
    <!-- Display return to home button for non-publisher users -->
    <a class="btn btn-primary" href="Home.jsp">Return to Home</a>
<% } %>



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
                                        <% if (hasBuy) {%> <!-- Check if the gamer has bought the game -->
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
                                        <% } else { %>
                                        <p>You need to purchase the game before you can submit a review.</p>
                                        <% } %>
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

                    Gamers gamer = GamerDAO.getGamerByGamerId(review.getIdGamer());

                    
                    // Check if the logged-in user is the author of the review
                    boolean isReviewOwner = loggedInUser != null && loggedInUser.getId().equals(gamer.getId());
                    
                    // Display review details
            %>
            <li>
                <strong><%= gamer.getName()%>:</strong> "<%= review.getDescription()%>" 
                <br> Rating: <%= review.getRating()%>
                
                <!-- Delete button form (display only if logged-in user is the author of the review) -->
                <% if (isReviewOwner) { %>
                    <form action="ReviewGameServlet" method="get" style="display: inline;">
                        <input type="hidden" name="reviewGameId" value="<%= review.getIdGame()%>">
                        <input type="hidden" name="reviewGamerId" value="<%= review.getIdGamer()%>">
                        <button type="submit" class="btn btn-sm btn-danger">Delete</button>
                    </form>
                <% } %>
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