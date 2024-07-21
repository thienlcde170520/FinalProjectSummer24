<%@page import="Model.Users"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Gamer Transaction Page</title>
        <!-- Bootstrap core CSS -->
        <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <!-- Additional CSS Files -->
        <link rel="stylesheet" href="assets/css/fontawesome.css">
        <link rel="stylesheet" href="assets/css/templatemo-cyborg-gaming.css">
        <link rel="stylesheet" href="assets/css/owl.css">
        <link rel="stylesheet" href="assets/css/animate.css">
        <link rel="stylesheet" href="https://unpkg.com/swiper@7/swiper-bundle.min.css" />
        <link rel="stylesheet" href="assets/css/Style.css">
        <!-- Custom CSS for white text on labels -->
        <style>
            .label-white{
                color: white;
            }
        .error-message {
            color: red;
            font-size: 0.875em;
            display: none;
        }
    </style>
    <script>
        function validateForm() {
            var amount = document.forms["payment-form"]["amount"].value;
            var amountError = document.getElementById("amount-error");

            if (isNaN(amount) || amount <= 0) {
                amountError.style.display = "block";
                return false;
            } else {
                amountError.style.display = "none";
                return true;
            }
        }
    </script>
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
                                <li><a href="<%=request.getContextPath()%>/StatisticForPublisher.jsp">View your profit</a></li>
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
        <!-- ***** Header Area End ***** -->

      <div class="container">
        <div class="row">
            <div class="col-lg-12">
                <div class="page-content">

                    <h2>Gamer Transaction Page</h2>

                    <div class="make-payment">
                        <h3>Make Payment</h3>
                        <form id="payment-form" name="payment-form" action="atm_momo" method="POST" onsubmit="return validateForm()">

                            <div class="form-group">
                                <label for="amount" class="col-form-label label-white">Amount:</label>
                                <input type='text' name="amount" value="<%= (request.getAttribute("amount") != null) ? request.getAttribute("amount") : "" %>"
                                       class="form-control" />
                                <div id="amount-error" class="error-message">Please enter a valid amount greater than 0.</div>
                            </div>
                            <div class="form-group">
                                <label for="order-info" class="col-form-label label-white">Order Information:</label>
                                <input type='text' name="order-info" value="<%= (request.getAttribute("order-info") != null) ? request.getAttribute("order-info") : "" %>"
                                       class="form-control" />
                            </div>
                            <div class="form-group">
                                <label for="requestType" class="label-white">Payment Method:</label>
                                <select class="form-control" id="requestType" name="requestType" required>
                                    <option value="payWithATM">Pay with ATM</option>
                                    <option value="captureWallet">Pay with QR CODE</option>
                                </select>
                            </div>
                            <button type="submit" class="btn btn-primary">Submit Payment</button>
                        </form>

                    </div>
                </div>
            </div>
        </div>
    </div>

        <!-- Footer and Scripts -->
        <footer>
            <!-- Footer content -->
        </footer>

        <!-- Bootstrap core JavaScript -->
        <script src="vendor/jquery/jquery.min.js"></script>
        <script src="vendor/bootstrap/js/bootstrap.min.js"></script>
        <script src="assets/js/isotope.min.js"></script>
        <script src="assets/js/owl-carousel.js"></script>
        <script src="assets/js/tabs.js"></script>
        <script src="assets/js/popup.js"></script>
        <script src="assets/js/custom.js"></script>
        <script src="custom.js"></script>

    </body>

</html>