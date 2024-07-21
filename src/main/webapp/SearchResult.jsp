<%@page import="Model.Users"%>
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

    <title>Search Page</title>

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
                    <div class="col-12">
                        <div class="section-heading">
                            <h2>Search Result</h2>
                        </div>
                        <!-- Filter Form -->
                        <!-- Filter Form -->
                         <section class="game-presentation">
                        <form id="filterForm" action="SearchGameServlet" method="post">
                            <div class="form-row">
                                <!-- Genre Filter -->
                                <div class="form-group col-md-12">
                                    <label for="genreFilter">Genre</label>
                                    <div class="row">
                                        <%
                                            ArrayList<Genre> genres = (ArrayList<Genre>) request.getAttribute("genres");
                                            if (genres != null) {
                                                int genresPerColumn = (int) Math.ceil((double) genres.size() / 2);
                                                for (int i = 0; i < 2; i++) { // Two columns
                                        %>
                                        <div class="col-md-6">
                                            <%
                                                for (int j = 0; j < genresPerColumn; j++) {
                                                    int index = i * genresPerColumn + j;
                                                    if (index < genres.size()) {
                                                        Genre genre = genres.get(index);
                                            %>
                                            <div class="form-check">
                                                <input class="form-check-input" type="checkbox" name="selectedGenres" value="<%= genre.getType()%>">
                                                <label class="form-check-label"><%= genre.getType()%></label>
                                            </div>
                                            <%
                                                    }
                                                }
                                            %>
                                        </div>
                                        <%
                                                }
                                            }
                                        %>
                                    </div>
                                </div>

                                <!-- Name Filter -->
                                <div class="form-group col-md-12">
                                    <label for="nameFilter">Name</label>
                                    <input type="text" class="form-control" id="nameFilter" name="nameFilter" placeholder="Enter game name">
                                </div>
                                <!-- Game Publisher Filter -->
                                <div class="form-group col-md-12">
                                    <label for="gamePublisher">Game Publisher</label>
                                    <input type="text" class="form-control" id="gamePublisher" name="gamePublisher" placeholder="Enter Game Publisher">
                                </div>
                                <!-- Year of Publication Filter -->
                                <div class="form-group col-md-12">
                                    <label for="yearFilter">Year of Publication</label>
                                    <input type="number" class="form-control" id="yearFilter" name="yearFilter" placeholder="Enter year">
                                </div>
                            </div>
                            <div class="form-row">
                                <!-- Price Filter -->
                                <div class="form-group col-md-12">
                                    <label for="price">Price</label>
                                    <div class="input-group">
                                        <input type="number" class="form-control" id="priceAmount" name="priceAmount" placeholder="Enter price">
                                        <div class="input-group-append">
                                            <select class="custom-select" id="priceCurrency" name="priceCurrency">
                                                <option value="Lower">Lower</option>
                                                <option value="Upper">Upper</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                <!-- Submit Button -->
                                <div class="form-group col-md-2">
                                    <!-- Using an <a> tag styled as a button with JavaScript -->
                                    <a href="#" id="applyFiltersButton" class="btn btn-primary">Apply Filters</a>
                                </div>
                            </div>
                        </form>
                                     <!-- Sorting Buttons -->
                        <div class="sorting-buttons">
                            <div class="dropdown">
                                <button class="btn btn-secondary dropdown-toggle" type="button" id="sortByNameButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    Sort by Name
                                </button>
                                <div class="dropdown-menu" aria-labelledby="sortByNameButton">
                                    <a class="dropdown-item" id="sortByNameAsc"  onclick="sortGames('name', 'asc')">Ascending</a>
                                    <a class="dropdown-item" id="sortByNameDesc"  onclick="sortGames('name', 'desc')">Descending</a>
                                </div>
                            </div>
                             <!-- Sort by Year Dropdown -->
                        <div class="dropdown">
                            <button class="btn btn-secondary dropdown-toggle" type="button" id="sortByYearButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                Sort by Year
                            </button>
                            <div class="dropdown-menu" aria-labelledby="sortByYearButton">
                                <a class="dropdown-item" id="sortByYearAsc" onclick="sortGames('year', 'asc')">Ascending</a>
                                <a class="dropdown-item" id="sortByYearDesc" onclick="sortGames('year', 'desc')">Descending</a>
                            </div>
                        </div>

                        <!-- Sort by Price Dropdown -->
                        <div class="dropdown">
                            <button class="btn btn-secondary dropdown-toggle" type="button" id="sortByPriceButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                Sort by Price
                            </button>
                            <div class="dropdown-menu" aria-labelledby="sortByPriceButton">
                                <a class="dropdown-item" id="sortByPriceAsc" onclick="sortGames('price', 'asc')">Ascending</a>
                                <a class="dropdown-item" id="sortByPriceDesc" onclick="sortGames('price', 'desc')">Descending</a>
                            </div>
                        </div>
                    </div>



                        <!-- Game Results -->
                        <div class="game-results">
                            <%
                                ArrayList<Game> games = (ArrayList<Game>) request.getAttribute("games");
                                if (games != null) {
                                    for (Game game : games) {
                            %>
                       <div class="game-box">
    <a href="GameDetailServlet?gameid=<%= game.getId() %>">
        <img src="<%= game.getAvatarLink() %>" alt="Game Avatar" class="game-avatar">
    </a>
    <div class="game-description">
        <h2><%= game.getName() %></h2>
        <p><%= game.getDescription() %></p>
    </div>
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
                          </section>
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
        <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.bundle.min.js"></script>
        <script src="assets/js/isotope.min.js"></script>
        <script src="assets/js/owl-carousel.js"></script>
        <script src="assets/js/tabs.js"></script>
        <script src="assets/js/popup.js"></script>
        <script src="assets/js/custom.js"></script>
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

                function sortGames(sortBy, sortOrder) {
                    const sortByInput = document.createElement('input');
                    sortByInput.type = 'hidden';
                    sortByInput.name = 'sortBy';
                    sortByInput.value = sortBy;

                    const sortOrderInput = document.createElement('input');
                    sortOrderInput.type = 'hidden';
                    sortOrderInput.name = 'sortOrder';
                    sortOrderInput.value = sortOrder;

                    filterForm.appendChild(sortByInput);
                    filterForm.appendChild(sortOrderInput);

                    filterForm.submit();
                }

                // Attach sorting functions to buttons
                 document.getElementById('sortByNameAsc').addEventListener('click', function () {
                    sortGames('name', 'asc');
                });

                document.getElementById('sortByNameDesc').addEventListener('click', function () {
                    sortGames('name', 'desc');
                });

                document.getElementById('sortByYearAsc').addEventListener('click', function () {
                    sortGames('year', 'asc');
                });

                document.getElementById('sortByYearDesc').addEventListener('click', function () {
                    sortGames('year', 'desc');
                });

                document.getElementById('sortByPriceAsc').addEventListener('click', function () {
                    sortGames('price', 'asc');
                });

                document.getElementById('sortByPriceDesc').addEventListener('click', function () {
                    sortGames('price', 'desc');
                });
            });
        </script>
         <script>
    // JavaScript to handle applying filters
    document.getElementById('applyFiltersButton').addEventListener('click', function (event) {
        event.preventDefault(); // Prevent default link behavior

        // Get form input values
        var searchKeyword = document.getElementById('nameFilter').value;
        var gamePublisher = document.getElementById('gamePublisher').value;
        var yearFilter = document.getElementById('yearFilter').value;
        var priceAmount = document.getElementById('priceAmount').value;
        var priceCurrency = document.getElementById('priceCurrency').value;

        // Get selected genres from checkboxes
        var selectedGenres = [];
        var genreCheckboxes = document.querySelectorAll('input[name="selectedGenres"]:checked');
        genreCheckboxes.forEach(function(checkbox) {
            selectedGenres.push(checkbox.value);
        });

        // Construct the URL with query parameters
        var href = "SearchGameServlet?";
        href += "searchKeyword=" + encodeURIComponent(searchKeyword);
        href += "&gamePublisher=" + encodeURIComponent(gamePublisher);
        href += "&yearFilter=" + encodeURIComponent(yearFilter);
        href += "&priceAmount=" + encodeURIComponent(priceAmount);
        href += "&priceCurrency=" + encodeURIComponent(priceCurrency);

        // Append selectedGenres if any genres are selected
        if (selectedGenres.length > 0) {
            href += "&selectedGenres=" + encodeURIComponent(selectedGenres.join(','));
        }

        // Navigate to the constructed URL
        window.location.href = href;
    });
</script>
    </body>
</html>
