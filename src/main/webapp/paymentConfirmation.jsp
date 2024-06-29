<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="Model.Genre" %>
<%@ page import="Model.Game" %>
<%@ page import="Model.Users" %>
<%@ page import="Model.Gamers" %>
<%@ page import="Controller.JavaMongo" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bill Page</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/css/fontawesome.css">
    <link rel="stylesheet" href="assets/css/templatemo-cyborg-gaming.css">
    <link rel="stylesheet" href="assets/css/owl.css">
    <link rel="stylesheet" href="assets/css/animate.css">
    <link rel="stylesheet" href="assets/css/Style.css">
    <link rel="stylesheet" href="https://unpkg.com/swiper@7/swiper-bundle.min.css" />
    <style>
        body {
            font-family: Arial, sans-serif;
            background: url('your-background-image.jpg') no-repeat center center fixed;
            background-size: cover;
            color: #333;
        }
        @keyframes slideDown {
            0% {
                transform: translate(-50%, -100%);
                opacity: 0;
            }
            100% {
                transform: translate(-50%, -50%);
                opacity: 1;
            }
        }
        .invoice-container {
            max-width: 800px;
            padding: 20px;
            background: rgba(255, 255, 255, 0.9);
            border: 1px solid #ddd;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            position: fixed;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            animation: slideDown 1s ease-out;
        }
        .header-area {
            position: fixed;
            top: 0;
            left: 0;
            right: 0;
            z-index: 999;
            background: #000;
            padding: 10px 0;
        }
        .header-area .main-nav .nav li a {
            color: #fff;
        }
        .header-area .main-nav .nav li a:hover,
        .header-area .main-nav .nav li a:focus {
            color: #f8f9fa;
        }
        .invoice-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
        }
        .invoice-header h1 {
            font-size: 36px;
            font-weight: bold;
            margin: 0;
        }
        .invoice-header img {
            height: 50px;
        }
        .invoice-details {
            margin-bottom: 30px;
        }
        .invoice-details .row {
            margin-bottom: 10px;
        }
        .invoice-details .row div {
            font-size: 14px;
        }
        .invoice-table {
            width: 100%;
            margin-bottom: 30px;
            border-collapse: collapse;
        }
        .invoice-table th, .invoice-table td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }
        .invoice-table th {
            background: #f8f9fa;
        }
        .invoice-total {
            display: flex;
            justify-content: flex-end;
            margin-bottom: 20px;
        }
        .invoice-total .total {
            font-size: 20px;
            font-weight: bold;
        }
        .invoice-footer {
            font-size: 14px;
            text-align: center;
            margin-top: 30px;
        }
        .pay-now-button {
            text-align: center;
            margin-top: 20px;
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

    <div class="invoice-container">
        <div class="invoice-header">
            <div>
                <img src="assets/images/logo.png" alt="Logo">
            </div>
        </div>
        <div class="invoice-details">
            <div class="row">
                <div class="col">
                    <strong>BILLED TO:</strong><br>
                    <% 
                        Gamers u = (Gamers ) session.getAttribute("account");
                       
                        
                        if (u != null) {
                            out.println(  u.getGmail()); 
                            out.println(  u.getMoney()); //// Debug logging
                    %>
               
                    <% } else {
                        out.println("Gamer not found!"); // Debug logging
                    } %>
                </div>
                <div class="col text-right">
                    
                    <%= new SimpleDateFormat("dd MMMM yyyy").format(new Date()) %>
                </div>
            </div>
        </div>
        <table class="invoice-table">
            <thead>
                <tr>
                    <th>Name Game</th>
                    <th>Price</th>
                    <th>Sale</th>
                    <th>Total</th>
                </tr>
            </thead>
            <tbody>
                <% 
                    // Get the list of games
                   Game game = (Game) request.getAttribute("game");
                %> 
               <tr>
                    <td><%= game.getName() %></td>
                    <td><%= game.getPrice() %> VNĐ</td>
                    <td>0%</td>
                    <td><%= game.getPrice() %> VNĐ</td>
                </tr>
                
                <tr>
                    <td colspan="3" class="text-right">Tax (0%)</td>
                    <td>0 VNĐ</td>
                </tr>
                <tr class="total">
                    <td colspan="3" class="text-right"><strong>Total</strong></td>
                    <td><strong><%= game.getPrice() %> VNĐ</strong></td>
                </tr>
            </tbody>
        </table>
        <div class="invoice-footer">
            Thank you!
           
        </div>
      <div class="pay-now-button">
        <form action="PayProcessServlet" method="post">
            <input type="hidden" name="gameId" value="<%= game.getId() %>">
            <input type="hidden" name="gamePrice" value="<%= game.getPrice() %>">
            <input type="hidden" name="buyTime" value="<%= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) %>">
            <button type="submit" class="btn btn-primary">Pay Now</button>
        </form>
    </div>
                 
                           
                         


                            <!-- Scripts -->
                            <!-- Bootstrap core JavaScript -->
                            <script src="vendor/jquery/jquery.min.js"></script>
                            <script src="vendor/bootstrap/js/bootstrap.min.js"></script>

                            <script src="assets/js/isotope.min.js"></script>
                            <script src="assets/js/owl-carousel.js"></script>
                            <script src="assets/js/tabs.js"></script>
                            <script src="assets/js/popup.js"></script>
                            <script src="assets/js/custom.js"></script>
                        
<script>
    function payNow() {
        <%-- Retrieve gameId from server-side or JavaScript variable --%>
        var gameId = "<%= request.getParameter("gameId") %>"; // Retrieve gameId from request parameter
        
        <%-- Redirect to PaymentPage.jsp with gameId parameter --%>
        window.location.href = "PaymentPage.jsp?gameId=" + gameId;
    }
</script>
</body>
</html>
