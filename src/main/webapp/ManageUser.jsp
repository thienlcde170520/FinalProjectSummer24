<%@page import="Model.Users"%>
<%@page import="Model.Publishers"%>
<%@page import="Model.Gamers"%>
<%@page import="Model.Game"%>
<%@page import="Model.Genre"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
     <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@100;200;300;400;500;600;700;800;900&display=swap" rel="stylesheet">

    <title>Manage User</title>

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

<!-- ***** Game Presentation Start ***** -->


                   <div class="container">
            <div class="row">
                <div class="col-lg-12">
                    <div class="page-content">

                      <section class="game-presentation">
   
       
                <div class="section-heading">
                    <h2>Search Result</h2>
                </div>
                
                  <div class="container mt-5">
        <form id="filterForm" method="post">
            <div class="form-row">
                <!-- Name Filter -->
                <div class="form-group col-md-12">
                    <label for="nameFilter">Name</label>
                    <input type="text" class="form-control" id="nameFilter" name="nameFilter" placeholder="Enter name">
                    <div class="input-group-append mt-2">
                        <select class="custom-select" id="roleFilter" name="roleFilter">
                            <option value="Gamer">Gamer</option>
                            <option value="Publisher">Game Publisher</option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="form-row">
                <!-- Submit Button -->
                <div class="form-group col-md-2">
                    <a href="#" id="applyFiltersButton" class="btn btn-primary">Apply Filters</a>
                </div>
            </div>
        </form>
    </div>
                <!-- Search Results -->
                <div class="game-results">
                    <%
                        ArrayList<Gamers> gamers = (ArrayList<Gamers>) request.getAttribute("gamers");
                        ArrayList<Publishers> publishers = (ArrayList<Publishers>) request.getAttribute("publishers");

                        if ((gamers != null && !gamers.isEmpty()) || (publishers != null && !publishers.isEmpty())) {
                            if (gamers != null && !gamers.isEmpty()) {
                                for (Gamers gamer : gamers) {
                    %>
                    <div class="game-box">
                        <a href="profileServlet?userid=<%= gamer.getId() %>">
                            <img src="<%= gamer.getAvatarLink() %>" alt="Gamer Avatar" class="game-avatar">
                        </a>
                        <div class="game-description">
                            <h2><%= gamer.getName() %></h2>
                            <p><%= gamer.getRegistrationDate() %></p>
                        </div>
                    </div>
                    <%
                                }
                            }

                            if (publishers != null && !publishers.isEmpty()) {
                                for (Publishers publisher : publishers) {
                    %>
                    <div class="game-box">
                        <a href="DisplayPublisherServlet?publisherName=<%= publisher.getName() %>">
                            <img src="<%= publisher.getAvatarLink() %>" alt="Publisher Avatar" class="game-avatar">
                        </a>
                        <div class="game-description">
                            <h2><%= publisher.getName() %></h2>
                            <p><%= publisher.getRegistrationDate() %></p>
                        </div>
                    </div>
                    <%
                                }
                            }
                        } else {
                    %>
                    <p>No results found.</p>
                    <%
                        }
                    %>
                </div>
           
   
</section>
                    </div>
                </div>
            </div>
        </div>
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
        <script src="vendor/jquery/jquery.min.js"></script>
        <script src="vendor/bootstrap/js/bootstrap.min.js"></script>
        <script>
            document.addEventListener('DOMContentLoaded', function () {
                const filterForm = document.getElementById('filterForm');
                const gameItems = document.querySelectorAll('.game-item');

                filterForm.addEventListener('submit', function (e) {
                    e.preventDefault();
                    const selectedGenres = Array.from(document.querySelectorAll('#filterForm input[type="checkbox"]:checked')).map(checkbox => checkbox.value);

                    gameItems.forEach(item => {
                        const itemGenre = item.getAttribute('data-genre');
                        if (selectedGenres.length === 0 || selectedGenres.includes(itemGenre)) {
                            item.style.display = 'block';
                        } else {
                            item.style.display = 'none';
                        }
                    });
                });
            });
        </script>
      <script>
        // JavaScript to handle applying filters
        document.addEventListener('DOMContentLoaded', function () {
            document.getElementById('applyFiltersButton').addEventListener('click', function (event) {
                event.preventDefault(); // Prevent default link behavior

                // Get form input values
                var searchKeyword = document.getElementById('nameFilter').value || '';
                var roleFilter = document.getElementById('roleFilter').value || '';

                // Construct the URL with query parameters
                var href = "SearchUserServlet?";
                href += "nameFilter=" + encodeURIComponent(searchKeyword);
                href += "&roleFilter=" + encodeURIComponent(roleFilter);

                // Navigate to the constructed URL
                window.location.href = href;
            });
        });
    </script>


        <script src="assets/js/isotope.min.js"></script>
        <script src="assets/js/owl-carousel.js"></script>
        <script src="assets/js/tabs.js"></script>
        <script src="assets/js/popup.js"></script>
        <script src="assets/js/custom.js"></script>
    </body>
</html>
