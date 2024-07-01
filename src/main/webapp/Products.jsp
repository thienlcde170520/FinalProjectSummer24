<%-- 
    Document   : Home
    Created on : May 28, 2024, 12:58:34 PM
    Author     : LENOVO
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="Model.Genre"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.ArrayList"%>
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
                        <li><a href="details.html">Details</a></li>
                        <li><a href="streams.html">Streams</a></li>
                        <li><a href="profile.html">Profile <img src="assets/images/profile-header.jpg" alt=""></a></li>
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
                            if (selectedgenre != null) {
                            
                        %>
          <!-- ***** Banner Start ***** -->
          <div class="main-banner" style="background-image: url('assets/images/Ace-Attorney.webp'); background-size: cover; background-position: center;">
            <div class="row">
              <div class="col-lg-7">
                <div class="header-text">
                
                  <h4>   <%= selectedgenre.getType()%> </h4>
                  <h6 >Detective fiction is a genre of writing where a detective works to solve a crime. The audience is challenged to solve the crime by the clues provided before the detective reveals the answer at the end of the novel</h6>
                  
                </div>
              </div>
            </div>
          </div>
          <%}%>
         <div class="form-group">
                        <label>Genres</label>
                           <% 
                            ArrayList<Genre> genres = (ArrayList<Genre>) request.getAttribute("genres");
                            if (genres != null) {
                                for (Genre genre : genres) {
                        %>
                        <div class="form-check">
                            <input class="form-check-input" type="radio" name="selectedGenre" value="<%= genre.getType()%>">
                            <label  style="color: white" class="form-check-label"><%= genre.getType() %></label>
                        </div>
                        <% 
                                }
                            }
                        %>
                    </div>
        </div>
      </div>
    </div>
    </div>
    <!-- ***** Banner End ***** -->
    
    
              
    <!-- ***** Games Area Starts ***** -->
    <section class="section" id="games">
        <div class="container">
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
                <div class="col-lg-4 col-md-6 game-item">
                    <div class="item">
                        <div class="thumb">
                            <div class="hover-content">
                                <ul>
                                    <li><a href="single-game.html"><i class="fa fa-eye"></i></a></li>
                                    <li><a href="#"><i class="fa fa-star"></i></a></li>
                                    <li><a href="#"><i class="fa fa-shopping-cart"></i></a></li>
                                </ul>
                            </div>
                            <!-- Add your image here -->
                            <img src="assets/images/Dark_Souls_Games.jpg" alt="Epic Adventure" class="game-image">
                        </div>
                        <div class="down-content">
                            <h4>Epic Adventure</h4>
                            <span>Genre: Adventure</span>
                            <div class="buttons">
                                <button class="btn btn-success">Buy</button>
                                <button class="btn btn-danger">Refund</button>
                                <button class="btn btn-primary">Follow</button>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- Game Item End -->

                <!-- Game Item Start -->
                <div class="col-lg-4 col-md-6 game-item">
                    <div class="item">
                        <div class="thumb">
                            <div class="hover-content">
                                <ul>
                                    <li><a href="single-game.html"><i class="fa fa-eye"></i></a></li>
                                    <li><a href="#"><i class="fa fa-star"></i></a></li>
                                    <li><a href="#"><i class="fa fa-shopping-cart"></i></a></li>
                                </ul>
                            </div>
                            <!-- Add your image here -->
                            <img src="assets/images/racing-pro.jpg" alt="Racing Pro" class="game-image">
                        </div>
                        <div class="down-content">
                            <h4>Racing Pro</h4>
                            <span>Genre: Racing</span>
                            <div class="buttons">
                                <button class="btn btn-success">Buy</button>
                                <button class="btn btn-danger">Refund</button>
                                <button class="btn btn-primary">Follow</button>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- Game Item End -->

                <!-- Game Item Start -->
                <div class="col-lg-4 col-md-6 game-item">
                    <div class="item">
                        <div class="thumb">
                            <div class="hover-content">
                                <ul>
                                    <li><a href="single-game.html"><i class="fa fa-eye"></i></a></li>
                                    <li><a href="#"><i class="fa fa-star"></i></a></li>
                                    <li><a href="#"><i class="fa fa-shopping-cart"></i></a></li>
                                </ul>
                            </div>
                            <!-- Add your image here -->
                            <img src="assets/images/fivenights.jpg" alt="Five nights at Freddy's" class="game-image">
                        </div>
                        <div class="down-content">
                            <h4>Five nights at Freddy's</h4>
                            <span>Genre: Horror</span>
                            <div class="buttons">
                                <button class="btn btn-success">Buy</button>
                                <button class="btn btn-danger">Refund</button>
                                <button class="btn btn-primary">Follow</button>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- Game Item End -->

                <!-- Game Item Start -->
                <div class="col-lg-4 col-md-6 game-item">
                    <div class="item">
                        <div class="thumb">
                            <div class="hover-content">
                                <ul>
                                    <li><a href="single-game.html"><i class="fa fa-eye"></i></a></li>
                                    <li><a href="#"><i class="fa fa-star"></i></a></li>
                                    <li><a href="#"><i class="fa fa-shopping-cart"></i></a></li>
                                </ul>
                            </div>
                            <!-- Add your image here -->
                            <img src="assets/images/csgo.png" alt="CSGO" class="game-image">
                        </div>
                        <div class="down-content">
                            <h4>CSGO</h4>
                            <span>Genre: Action</span>
                            <div class="buttons">
                                <button class="btn btn-success">Buy</button>
                                <button class="btn btn-danger">Refund</button>
                                <button class="btn btn-primary">Follow</button>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- Game Item End -->
                <!-- Additional game items can be added in a similar manner -->

                <div class="col-lg-12">
                    <div class="pagination">
                        <ul>
                            <li>
                                <a href="#">1</a>
                            </li>
                            <li >
                                <a href="#">2</a>
                            </li>
                            <li>
                                <a href="#">3</a>
                            </li>
                            <li>
                                <a href="#">4</a>
                            </li>
                            <li>
                                <a href="#">></a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <!-- ***** Games Area Ends ***** -->

   <!-- ***** Game Presentation Start ***** -->
<section class="game-presentation " >
    <div class="container">
        <div class="row">
            <div class="col-12">
                <div class="section-heading">
               
                    <h2>Our BestSeller Games</h2>
                    <span>Top 3 bestseller games</span>
                            <div class="form-group col-md-12">
            <label for="categoryFilter">By time</label>
            <select class="form-control" id="categoryFilter">
                <option value="">Day</option>
                <option value="action">Week</option>
                <option value="adventure">Month</option>
                <!-- Add more categories as needed -->
            </select>
        </div>
                </div>
                <div class="game-box  "  >
                    <img src="assets/images/24252.jpg" alt="Game Avatar" class="game-avatar">
                    <div class="game-description">
                        <h2>Clannad</h2>
                        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.</p>
                    </div>
                </div>
                <div class="game-box">
                    <img src="assets/images/68753.jpg" alt="Game Avatar" class="game-avatar">
                    <div class="game-description">
                        <h2>Game Title 2</h2>
                        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.</p>
                    </div>
                </div>
                <div class="game-box">
                    <img src="assets/images/46806.jpg" alt="Game Avatar" class="game-avatar">
                    <div class="game-description">
                        <h2>Game Title 3</h2>
                        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.</p>
                    </div>
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
