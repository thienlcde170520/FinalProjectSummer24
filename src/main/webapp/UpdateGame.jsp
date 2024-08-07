<%-- 
    Document   : Home
    Created on : May 28, 2024, 12:58:34 PM
    Author     : LENOVO
--%>
<%@page import="Model.Users"%>
<%@page import="Model.Game"%>
<%@ page import="Model.Genre" %>
<%@ page import="java.util.ArrayList" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%><!DOCTYPE html>


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
         <link rel="stylesheet" href="assets/css/style.css">
    <!--
    
    TemplateMo 579 Cyborg Gaming
    
    https://templatemo.com/tm-579-cyborg-gaming
    
    -->
</head>


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



<%
    Game game = (Game) request.getAttribute("game");
%>


<%-- Game Input Form Section --%>

    <div class="container">
        <div class="row">
            <!-- Form Section -->
            <div class="col-12">
                     <div class="page-content">
                
                <section class="game-input-form" style="background-color: #000; color: #F0F5FF">
                    <h2>Update Game</h2>
                <form action="UpdateGameServlet" method="post"  enctype="multipart/form-data" >

                    <div class="form-group">
                        <label for="gameName">Game Name</label>
                        <input type="text" class="form-control" id="gameName" name="gameName" placeholder="Enter game name">
                    </div>

                    <div class="form-group">
                        <label for="gameFile">Game File</label>
                        <input type="file" class="form-control-file" id="gameFile" name="gameFile" multiple>
                    </div>

                    <div class="form-group">
                        <label for="gameAvatar">Game Avatar</label>
                        <input type="file" class="form-control-file" id="gameAvatar" name="gameAvatar" multiple>

                    </div>

                    <div class="form-group">
                        <label for="language">Game Trailer link</label>
                        <input type="text" class="form-control" id="Trailer" name="trailerLink" placeholder="Enter link of the trailer game">
                    </div>

                    <div class="form-group">
                        <label for="description">Description</label>
                        <textarea class="form-control" id="description" name ="description" rows="3" placeholder="Enter game description"></textarea>
                    </div>

                    <div class="form-group">
                        <label>Minimum Configuration</label>
                        <div class="row">
                            <div class="col">
                                <input type="text" class="form-control" placeholder="Minimum CPU" id="minCpu" name="minCpu">
                            </div>
                            <div class="col">
                                <input type="text" class="form-control" placeholder="Minimum RAM" id="minRam" name="minRam">
                            </div>
                            <div class="col">
                                <input type="text" class="form-control" placeholder="Minimum GPU" id="minGpu" name="minGpu">
                            </div>
                        </div>
                    </div>

                    <div class="form-group">
                        <label>Maximum Configuration</label>
                        <div class="row">
                            <div class="col">
                                <input type="text" class="form-control" placeholder="Maximum CPU" id="maxCpu" name="maxCpu">
                            </div>
                            <div class="col">
                                <input type="text" class="form-control" placeholder="Maximum RAM" id="maxRam" name="maxRam">
                            </div>
                            <div class="col">
                                <input type="text" class="form-control" placeholder="Maximum GPU" id="maxGpu" name="maxGpu">
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="priceAmount">Price</label>
                        <div class="input-group">
                            <input type="number" class="form-control" id="priceAmount" name="priceAmount" placeholder="Enter price">
                        </div>
                    </div>


                    <!-- Genre Checkboxes -->
                    <div class="form-group">
                        <!-- <label>Category</label>
                                       <div id="relatedGameSearch" class="form-group" style="display: none;">
                                           <label for="relatedGame">Search Related Game</label>
                                           <input type="text" class="form-control" id="relatedGame" placeholder="Search related game">
                                       </div>
                                       <div class="form-check">
                                           <input class="form-check-input" type="radio" name="category" id="original" value="original">
                                           <label class="form-check-label" for="original">Original</label>
                                       </div>
                                       <div class="form-check">
                                           <input class="form-check-input" type="radio" name="category" id="prequel" value="prequel">
                                           <label class="form-check-label" for="prequel">Prequel</label>
                                       </div>
                                       <div class="form-check">
                                           <input class="form-check-input" type="radio" name="category" id="sequel" value="sequel">
                                           <label class="form-check-label" for="sequel">Sequel</label>
                                       </div>
                                       <div class="form-check">
                                           <input class="form-check-input" type="radio" name="category" id="reboot" value="reboot">
                                           <label class="form-check-label" for="reboot">Reboot</label>
                                       </div>
                                       <div class="form-check">
                                           <input class="form-check-input" type="radio" name="category" id="remake" value="remake">
                                           <label class="form-check-label" for="remake">Remake</label>
                                       </div>
                        -->
                        <label> Inclusive Genres</label>
                        <%
                            ArrayList<Genre> exclusiveGenres = (ArrayList<Genre>) request.getAttribute("exclusiveGenres");
                            if (exclusiveGenres != null) {
                                for (Genre genre : exclusiveGenres) {
                        %>
                        <div class="form-check">
                            <input class="form-check-input" type="checkbox" name="incluGenres" value="<%= genre.getType()%>">
                            <label class="form-check-label"><%= genre.getType()%></label>
                        </div>
                        <%
                                }
                            }
                        %>

                        <label> Exclusive Genres</label>
                        <%
                            ArrayList<Genre> inlusiveGenres = (ArrayList<Genre>) request.getAttribute("inlusiveGenres");
                            if (inlusiveGenres != null) {
                                for (Genre genre : inlusiveGenres) {
                        %>
                        <div class="form-check">
                            <input class="form-check-input" type="checkbox" name="excluGenres" value="<%= genre.getType()%>">
                            <label class="form-check-label"><%= genre.getType()%></label>
                        </div>
                        <%
                                }
                            }
                        %>
                    </div>



                     <input type="hidden" id="gameId" name="gameId" value="<%= game.getId() %>">

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
<!-- ***** Game Input Form End ***** -->
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
<script >
                                document.addEventListener('DOMContentLoaded', function () {
                                    const categoryRadios = document.querySelectorAll('input[name="category"]');
                                    const relatedGameSearch = document.getElementById('relatedGameSearch');

                                    categoryRadios.forEach(radio => {
                                        radio.addEventListener('change', function () {
                                            if (radio.value === 'prequel' || radio.value === 'sequel' || radio.value === 'reboot' || radio.value === 'remake') {
                                                relatedGameSearch.style.display = 'block';
                                            } else {
                                                relatedGameSearch.style.display = 'none';
                                            }
                                        });
                                    });
                                });

</script>
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






