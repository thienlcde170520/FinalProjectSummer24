<!doctype html>
<html>
<head>
<meta charset='utf-8'>
<meta name='viewport' content='width=device-width, initial-scale=1'>
<title>Forget Password</title>
<link
	href='https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css'
	rel='stylesheet'>
<link href='' rel='stylesheet'>
<script type='text/javascript'
	src='https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js'></script>
<style>
body {
	background-position: center;
	background-color: #1f2122;/*#eee*/
	background-repeat: no-repeat;
	background-size: cover;
	color: #505050;
	font-family: "Rubik", Helvetica, Arial, sans-serif;
	font-size: 14px;
	font-weight: normal;
	line-height: 1.5;
	text-transform: none;
    
}

.forgot {
	background-color: #fff;
	padding: 12px;
	border: 1px solid #dfdfdf;
    border-radius: 3px;
}

.padding-bottom-3x {
	padding-bottom: 72px !important
}

.card-footer {
	background-color: #fff
}

.btn {
	font-size: 13px
}

.form-control:focus {
	color: #495057;
	background-color: #fff;
	border-color: #76b7e9;
	outline: 0;
	box-shadow: 0 0 0 0px #28a745
}
.text-danger{
    color: red;
    font-weight: bold;
    background-color: #ffcccc;
    margin: 10px 20px;
    border-radius: 2px;
	text-align: center;
	font-size: 18px;
}
.logo_container{
    display: flex;
    justify-content: center;
}
.logo{
    margin: 20px 0;
}
#back{
    color: white;
    text-decoration: none;
}
</style>
</head>
<body oncontextmenu='return false' class='snippet-body'>
     <div class="logo_container">
        <a href="Home.jsp" class="logo">
            <img src="assets/images/logo.png" alt="">
        </a>
    </div>
	<div class="container padding-bottom-3x mb-2 mt-5">
		<div class="row justify-content-center">
			<div class="col-lg-8 col-md-10">
				<div class="forgot">
					<h2>Forgot your password?</h2>
					<p>Change your password in three easy steps. This will help you
						to secure your password!</p>
					<ol class="list-unstyled">
						<li><span class="text-primary text-medium">1. </span>Enter
							your email address below.</li>
						<li><span class="text-primary text-medium">2. </span>Our
							system will send you an OTP to your email</li>
						<li><span class="text-primary text-medium">3. </span>Enter the OTP on the 
						next page</li>
					</ol>
				</div>
				<form class="card mt-4" action="ForgetPassServlet" method="POST">
                                    <%
                                            if(request.getAttribute("message")!=null)
                                            {
                                               out.print("<p class='text-danger'>"+request.getAttribute("message")+"</p>");
                                            }

                                    %>
					<div class="card-body">
						<div class="form-group">
							<label for="email-for-pass">Enter your email address</label> <input
								class="form-control" type="text" name="email" id="email-for-pass" required=""><small
								class="form-text text-muted">Enter the registered email address . Then we'll
								email a OTP to this address.</small>
						</div>
					</div>
					<div class="card-footer">
						<button class="btn btn-success" type="submit">Get OTP</button>
                                                <button class="btn btn-danger" ><a href="Login.jsp" id="back">Back to Login</a>
							</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	
        <script type='text/javascript'
		src='https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.bundle.min.js'></script>
	<script type='text/javascript' src=''></script>
	<script type='text/javascript' src=''></script>
	<script type='text/Javascript'></script>
</body>
</html>