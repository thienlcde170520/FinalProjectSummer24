<%@page import="DAO.TransactionBillDAO"%>
<%@page import="Model.Bill"%>
<%@page import="DAO.GamerDAO"%>
<%@page import="DAO.GameDAO"%>
<%@page import="Model.Review"%>
<%@page import="Model.Users"%>
<%@page import="Model.Game"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Model.BankTransactions"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    ArrayList<BankTransactions> transactionHistory = (ArrayList<BankTransactions>) request.getAttribute("transactionHistory");
%>
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

   <!-- ***** Header Area Start ***** -->
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
         <div class="row" >
              <%
                      Boolean isAdminObj = (Boolean) request.getAttribute("isAdmin");
            boolean isAdmin = isAdminObj != null && isAdminObj.booleanValue();
               Boolean isUpdateableObj = (Boolean) request.getAttribute("isUpdateable");
                    ArrayList<Review> reviews = (ArrayList<Review>) request.getAttribute("reviews");
            boolean isUpdateable = isUpdateableObj != null && isUpdateableObj.booleanValue();
            // Lấy thông tin người chơi từ request attribute
            Model.Gamers gamer = (Model.Gamers) request.getAttribute("gamer");
            ArrayList<Game> games = (ArrayList<Game>) request.getAttribute("games");
            if (gamer != null) {
        %>
            <div class="col-lg-12">
              <div class="main-profile ">
                <div class="row">
                  <div class="col-lg-4">
                    <img src="<%=gamer.getAvatarLink() %>" alt="" style="border-radius: 23px;">
                  </div>
                  <div class="col-lg-4 align-self-center">
                    <div class="main-info header-text">
                      <h4><%=gamer.getName() %></h4>
                      <p>Email: <%=gamer.getGmail()%></p>
                      <p>Date of Birth: <%=gamer.getDOB()%></p>
                      <p>Tham gia từ : <%=gamer.getRegistrationDate()%></p>
                      <div class="main-border-button">
                      </div>
                    </div>
                  </div>
          <div class="col-lg-4 align-self-center">
    <ul>
           <li>Balance <span><%= gamer.getMoney() %> VNĐ</span></li>
                <li><a href="FollowServlet?gamerid=<%= gamer.getId() %>">Wish Lists</a></li>
       <% if (!isAdmin && !isUpdateable) { %>
      
        </ul>
        
        <% } else if ( isAdmin) { %>
        <div class="d-flex justify-content-start align-items-center">
        
                <a href="DeleteAccountServlet?UserId=<%= gamer.getId() %>" class="btn btn-primary">Delete Account</a>
                <a href="UpdateAccountByAdmin?UserId=<%= gamer.getId() %>" class="btn btn-primary">Default Info</a>   
        </div>
      
        <% } else if (isUpdateable) {
%>

 <div class="d-flex justify-content-start align-items-center">              
            <a href="UpdateProfile.jsp" class="btn btn-primary">Update</a>            
            <a href="NewTransaction.jsp" class="btn btn-primary">Transaction</a>
            <a href="RespondReportServlet?UserId=<%=user.getId() %>" class="btn btn-primary">Send Report</a>
            <a href="DeleteAccountServlet?UserId=<%= gamer.getId() %>" class="btn btn-primary">Delete Account</a>
        </div>
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


                        <div class="col-lg-12">
                          <div class="main-button">
                            <a href="#">Load More Games</a>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
                       <% } else { %>
            <p>Không tìm thấy thông tin người chơi.</p>
        <% } %>
          </div>
          <!-- ***** Banner End ***** -->

   
     <!-- ***** Gaming Library Start ***** -->
          <div class="gaming-library profile-library">
            <div class="col-md-12">
              <div class="heading-section">
                <h4><em>Transaction History</em> </h4>
              </div>
              
                 <div class="transactions-container">
        <%
            if (transactionHistory != null) {
                for (BankTransactions transaction : transactionHistory) {
        %>
                    <div class="item <%= (transactionHistory.indexOf(transaction) == transactionHistory.size() - 1) ? "last-item" : "" %>">
                        <ul>
                            <li><h4>MoMo</h4></li>
                            <li><h4>Transaction infor</h4><span> <%= transaction.getOrderInfo() %></span></li>
                            <li><h4>Date of transaction</h4><span><%= transaction.getCreatedAt() %></span></li>
                            <li><div class="main-border-button border-no-active"><a href="#"><%= transaction.getAmount() %></a></div></li>
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
                                
                                
                                    <!-- ***** Gaming Library Start ***** -->
                                <div class="gaming-library profile-library">
                                    <div class="col-md-12">
                                        <div class="heading-section">
                                            <h4><em>Buy Game History</em></h4>
                                        </div>
                                        <div class="transactions-container">
                                            <%
                                            ArrayList<Bill> bills = new ArrayList<>();
                                            for (Game g : games){
                                                   bills.add(TransactionBillDAO.getBillByGameIDAndGamerID(g.getId(), gamer.getId()));
                                                }
                                            if (bills != null) {
                                                for (Bill bill : bills) {
                                            %>
                                                    <div class="item <%= (reviews.indexOf(bill) == bills.size() - 1) ? "last-item" : "" %>">
                                                        <ul>
                                                            <li><h4>Price <%= bill.getBuyPrice()  %></h4></li>
                                                            <li><h4>Time </h4><span><%= bill.getBuyTime() %></span></li>
                                                            <li><h4>Game Name</h4><span><a href="GameDetailServlet?gameid=<%= bill.getGameId()  %>"><%= GameDAO.getGameByGameID(bill.getGameId()) .getName() %></a></span></li>
                                                 
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
    </div>
  </div>
  
  <footer>
    <div class="container">
      <div class="row">
        <div class="col-lg-12">
          <p>Copyright © 2024 <a href="#">FPTeam</a> Company. All rights reserved. 
    
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
