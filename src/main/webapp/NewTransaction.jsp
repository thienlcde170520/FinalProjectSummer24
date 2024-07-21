    
<%@page import="Model.Users"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Transaction Page</title>
    
       <!-- Bootstrap core CSS -->
        <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <!-- Additional CSS Files -->
        <link rel="stylesheet" href="assets/css/fontawesome.css">
        <link rel="stylesheet" href="assets/css/templatemo-cyborg-gaming.css">
        <link rel="stylesheet" href="assets/css/owl.css">
        <link rel="stylesheet" href="assets/css/animate.css">
        <link rel="stylesheet" href="https://unpkg.com/swiper@7/swiper-bundle.min.css" />
        <link rel="stylesheet" href="assets/css/Style.css">
        <link rel="stylesheet" href="assets/css/styleTest.css">
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
  
                               
  <div class="container">
        <div class="main-content">
            <p class="text">FPTeam</p>
           
        </div>
        
        
        <div class="centre-content">
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
                <button type="submit" class="submit-now-btn">Submit Payment</button>
                 <button type="button" class="home-btn" onclick="location.href='Home.jsp'">Back To Home Page</button>
            </form>
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
