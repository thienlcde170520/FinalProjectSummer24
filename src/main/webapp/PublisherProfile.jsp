<%@page import="Model.Users"%>
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

                <!-- ***** Banner Start ***** -->
                <div class="row">
                    <%
                    // Lấy thông tin người chơi từ request attribute
                    Model.Publishers pub = (Model.Publishers) request.getAttribute("publisher");
                    ArrayList<Game> games = (ArrayList<Game>) request.getAttribute("games");
                    ArrayList<Review> reviews = (ArrayList<Review>) request.getAttribute("reviews");
                    Boolean isAdminObj = (Boolean) request.getAttribute("isAdmin");
            boolean isAdmin = isAdminObj != null && isAdminObj.booleanValue();
               Boolean isUpdateableObj = (Boolean) request.getAttribute("isUpdateable");
            boolean isUpdateable = isUpdateableObj != null && isUpdateableObj.booleanValue();
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
                                            <p>Joined from: <%= pub.getRegistrationDate() %></p>
                                            <p>Description: <%= pub.getDescription() %></p>
                                            <%
                                                if (user != null && (user.getRole()== 1 || user.getRole() == 2 )) {
                                            %>
                                            <p>Bank Account: <%= pub.getBank_account() %></p>
                                            <%}%>
                                            <div class="main-border-button">
                                                <!-- Add button or other content here if needed -->
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-lg-4 align-self-center">
    <ul>
           <li>Games Published <span><%= games.size() %></span></li>
       <% if (!isAdmin && !isUpdateable) { %>
      
        </ul>
        
        <% } else if ( isAdmin) { %>
        <div class="d-flex justify-content-start align-items-center">
                
          
            <a href="DeleteAccountServlet?UserId=<%= pub.getId() %>" class="btn btn-primary" onclick="return confirmDelete()">Delete Account</a>
            <a href="UpdatePublisherByAdmin?UserId=<%= pub.getId() %>" class="btn btn-primary" onclick="return confirmUpdate()">Default Info</a>
        </div>
               
      
        <% } else if (isUpdateable) {
%>
  <a href="UpdateProfile.jsp" class="btn btn-primary">Update</a>
            <a href="RespondReportServlet?UserId=<%=pub.getId() %>" class="btn btn-primary">Send Report</a>
 
<%
}
%>
</div>
                                </div>
                                 <div class="row">
                  <div class="col-lg-12">
                    <div class="clips">
                      <div class="row">
                        <div class="col-lg-12">
                          <div class="heading-section">
                            <h4><em>Recent game</em></h4>
                          </div>
                        </div>
                    <div class="row">
    <% if (games != null && !games.isEmpty()) { %>
    <% for (Game game : games) { %>
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
                                                            <li><div class="main-border-button border-no-active"><a href="profileServlet?userid=<%= GamerDAO.getGamerByReview(review).getId() %>"><%= GamerDAO.getGamerByReview(review).getName() %></a></div></li>
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
  <script>
    function confirmDelete() {
        return confirm('Are you sure you want to delete this account?');
    }

    function confirmUpdate() {
        return confirm('Are you sure you want to update the default information for this account?');
    }
</script>
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