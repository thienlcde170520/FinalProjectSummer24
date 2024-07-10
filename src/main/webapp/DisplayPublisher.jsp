<%@page import="DAO.GamerDAO"%>
<%@page import="DAO.GameDAO"%>
<%@page import="DAO.ReviewDAO"%>
<%@page import="Model.Review"%>
<%@page import="Model.Game"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Model.BankTransactions"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">

  <head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@100;200;300;400;500;600;700;800;900&display=swap" rel="stylesheet">

    <title>Cyborg - Awesome HTML5 Template</title>

    <!-- Bootstrap core CSS -->

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
                    <a href="index.html" class="logo">
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
                        <li><a href="Home.jsp">Home</a></li>
                        <li><a href="browse.html">Browse</a></li>
                        <li><a href="details.html">Details</a></li>
                        <li><a href="streams.html">Streams</a></li>
                        <li><a href="profile.jsp" class="active">Profile <img src="assets/images/profile-header.jpg" alt=""></a></li>
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
                <div class="row">
                    <%
                    // Lấy thông tin người chơi từ request attribute
                    Model.Publishers pub = (Model.Publishers) request.getAttribute("publisher");
                    ArrayList<Game> publishgames = (ArrayList<Game>) request.getAttribute("publishgames");
                              ArrayList<Game> unpublishgames = (ArrayList<Game>) request.getAttribute("unpublishgames");
                    ArrayList<Review> reviews = (ArrayList<Review>) request.getAttribute("reviews");
                    if (pub != null) {
                    %>
                        <div class="col-lg-12">
                            <div class="main-profile">
                                <div class="row">
                                    <div class="col-lg-4">
                                        <img src="<%= pub.getAvatarLink() %>" alt="" style="border-radius: 23px;">
                                    </div>
                                    <div class="col-lg-4 align-self-center">
                                        <div class="main-info header-text">
                                            <h4><%= pub.getName() %></h4>
                                            <p>Email: <%= pub.getGmail() %></p>
                                            <p>Tham gia từ: <%= pub.getRegistrationDate() %></p>
                                            <div class="main-border-button">
                                                <!-- Add button or other content here if needed -->
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-lg-4 align-self-center">
                                        <ul>
                                            <li>Games Published <span><%= publishgames.size() %></span></li>
                                            <li>Balance <span><%= pub.getMoney() %> VNĐ</span></li>
                                        </ul>
                                    </div>                                       
                                </div>
                                        <div class="d-flex justify-content-end align-items-center" style="padding-right: 136px;">
                                            <a href="UpdatePubProfile.jsp" class="btn btn-primary" >Update</a>
                                        </div>
                              <div class="row">
                  <div class="col-lg-12">
                    <div class="clips">
                      <div class="row">
                        <div class="col-lg-12">
                          <div class="heading-section">
                            <h4><em>published game</em></h4>
                          </div>
                        </div>
                    <div class="row">
    <% if (publishgames != null && !publishgames.isEmpty()) { %>
    <% for (Game game : publishgames) { %>
        <div class="col-lg-3 col-sm-6">
            <div class="item">
                <div class="thumb"> 
                    <img src="<%= game.getAvatarLink() %>" alt="<%= game.getName() %>" style="border-radius: 23px;">
                    <a href="<%= game.getLinkTrailer() %>" target="_blank"><i class="fa fa-play"></i></a>
                </div>
                <div class="down-content">
                    <h4><a href="GameDetailServlet?gameid=<%= game.getId() %>"><%= game.getName() %></a></h4>
                </div>
            </div>
        </div>
    <% } %>
<% } else { %>
    <p>No games found.</p>
<% } %>

</div>


                        <div class="col-lg-12">
                          <div class="main-button">
                            <a href="#">Load More Games</a>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>

  <div class="row">
                  <div class="col-lg-12">
                    <div class="clips">
                      <div class="row">
                        <div class="col-lg-12">
                          <div class="heading-section">
                            <h4><em>Unpublished game</em></h4>
                          </div>
                        </div>
                    <div class="row">
    <% if (unpublishgames != null && !unpublishgames.isEmpty()) { %>
    <% for (Game game : unpublishgames) { %>
        <div class="col-lg-3 col-sm-6">
            <div class="item">
                <div class="thumb"> 
                    <img src="<%= game.getAvatarLink() %>" alt="<%= game.getName() %>" style="border-radius: 23px;">
                    <a href="<%= game.getLinkTrailer() %>" target="_blank"><i class="fa fa-play"></i></a>
                </div>
                <div class="down-content">
                    <h4><a href="GameDetailServlet?gameid=<%= game.getId() %>"><%= game.getName() %></a></h4>
                </div>
            </div>
        </div>
    <% } %>
<% } else { %>
    <p>No games found.</p>
<% } %>

</div>


                        <div class="col-lg-12">
                          <div class="main-button">
                            <a href="#">Load More Games</a>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                                <!-- ***** Gaming Library Start ***** -->
                                <div class="gaming-library profile-library">
                                    <div class="col-md-12">
                                        <div class="heading-section">
                                            <h4><em>Review History</em></h4>
                                        </div>
                                        <div class="transactions-container">
                                            <%
                                            if (reviews != null) {
                                                for (Review review : reviews) {
                                            %>
                                                    <div class="item <%= (reviews.indexOf(review) == reviews.size() - 1) ? "last-item" : "" %>">
                                                        <ul>
                                                            <li><h4>Rating: <%= review.getRating() %></h4></li>
                                                            <li><h4>Description</h4><span><%= review.getDescription() %></span></li>
                                                            <li><h4>Game Name</h4><span><a href="GameDetailServlet?gameid=<%= GameDAO.getGameByReview(review).getId() %>"><%= GameDAO.getGameByReview(review).getName() %></a></span></li>
                                                            <li><div class="main-border-button border-no-active"><a href="profileServlet?gamerid=<%= GamerDAO.getGamerByReview(review).getId() %>"><%= GamerDAO.getGamerByReview(review).getName() %></a></div></li>
                                                        </ul>
                                                    </div>
                                            <%
                                                }
                                            }
                                            %>
                                        </div>
                                    </div>
                                </div>
                                <!-- ***** Gaming Library End ***** -->
                            </div>
                        </div>
                    <% } %>
                </div>
            </div>
        </div>
    </div>
</div>

<footer>
    <div class="container">
        <div class="row">
            <div class="col-lg-12">
                <p>Copyright © 2024 <a href="#">FPTeam</a> Company. All rights reserved.</p>
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