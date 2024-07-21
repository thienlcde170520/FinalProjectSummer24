<%-- 
    Document   : login.jsp
    Created on : 4 Jun, 2024, 4:48:55 AM
    Author     : HP
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Reset Password Page</title>
        <link rel="stylesheet" type="text/css" href="assets/css/reset.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css"> 

        <!--banner-->
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@100;200;300;400;500;600;700;800;900&display=swap" rel="stylesheet">
        <link href="css/bootstrap.min.css" rel="stylesheet">


        

        <link rel="stylesheet" href="assets/css/alertInput.css">
        
        <style>
            .mess{
                color: red;
                background-color: #ffcccc;
                margin: 17px 0;
            }
            .Inputerror{
                color: red;
                font-size: small;
            }
            #rolemess{
                margin: -33px 0 0 0;
            }
            .text_danger{
                background-color:   #e6ffe6
                ;
                margin: 17px 0;
                color: #00ff00;
                font-size: 20px;
                font-weight: 600;               
                text-align: center;
                border-radius: 10px;
            }
        </style>
              
    </head>
    
    <body>
          <div class="header">
            <a href="Home.jsp" class="logo">
              <img src="assets/images/logo.png" alt="">
          </a>
          </div>     
            <div class="register forms">
            <!-- Signup Form -->
                <div class="form signup">
                    <div class="form-content">
                        <p>Reset Password</p>
                            <form action="newPassword" method="POST">
                                <%
                                        if(request.getAttribute("message")!=null)
                                        {
                                           out.print("<div class='text_danger'>"+request.getAttribute("message")+"</div>");
                                        }

                                %>
                              <div class="field input-field">
                                <div class="input-container pass">
                                <label for="password">Password</label>
                                <input type="password" id="password" placeholder="Create password" name="password" >
                                <i id="pass-toggle-btn" class="fa-solid fa-eye"></i>
                            </div>
                                <div id="passwordMessage"></div>
                            </div>
                            <div class="field input-field">
                                <div class="input-container pass">
                                <label for="password">Confirm Password</label>
                                <input type="password" id="con_password" placeholder="Confirm password" name="confirm_password" >
                                <i id="con_pass-toggle-btn" class="fa-solid fa-eye"></i> 
                                </div>
                                <div id="conPasswordMessage"></div>
                            </div>
                                                       
                            <div class="field button-field">
                                <button>Reset Password</button>
                            </div>
                            
                        </form>
                        
                    </div>
                    
                </div>
            </div>

            
        <!-- JavaScript -->
        <script>
          // Lắng nghe sự kiện click vào trường tên người dùng
        document.getElementById("password").onclick = function() {
          document.getElementById("passmess").innerHTML = ""; // Xóa thông báo tên người dùng
        };
        document.getElementById("con_password").onclick = function() {
          document.getElementById("conpassmess").innerHTML = ""; // Xóa thông báo tên người dùng
        };
      </script>
         
         <script src="assets/js/passwordAlert.js"></script>
         
        <script>
        const passwordInput = document.getElementById("password");
        const passToggleBtn = document.getElementById("pass-toggle-btn");
        const conpassToggleBtn = document.getElementById("con_pass-toggle-btn");
        const passconInput = document.getElementById("con_password");
       

        passToggleBtn.addEventListener('click', () => {
            passToggleBtn.className = passwordInput.type === "password" ? "fa-solid fa-eye-slash" : "fa-solid fa-eye";
            passwordInput.type = passwordInput.type === "password" ? "text" : "password";
        });

        conpassToggleBtn.addEventListener('click',() =>{
            conpassToggleBtn.className = passconInput.type === "password" ? "fa-solid fa-eye-slash" : "fa-solid fa-eye";
            passconInput.type = passconInput.type === "password" ? "text" : "password";
        })

        </script>

        




    </body>

    
</html>

