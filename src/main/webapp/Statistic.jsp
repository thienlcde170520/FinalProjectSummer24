<%@ page import="Model.Users" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@100;200;300;400;500;600;700;800;900&display=swap"
          rel="stylesheet">

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
                            <input type="text" placeholder="Type Something" id='searchText' name="searchKeyword"
                                   onkeypress="handle"/>
                            <i class="fa fa-search"></i>
                        </form>
                    </div>
                    <!-- ***** Search End ***** -->
                    <!-- ***** Menu Start ***** -->
                    <ul class="nav">
                        <li><a href="Home.jsp" class="active">Home</a></li>


                        <% Users user = session.getAttribute("account") == null ? (Users) session.getAttribute("account") : null; %>
                        <% if (user != null) {%>
                        <%
                            if (user.getRole() == 2) {
                        %>
                        <li><a href="UploadGame">Upload Game</a></li>
                        <%
                            }
                        %>
                        <%
                            if (user.getRole() == 1) {
                        %>
                        <li><a href="PublishGameServlet">Verify Game</a></li>
                        <li><a href="ManageUser.jsp"> Manage User</a></li>
                        <li><a href="ReportServlet">Respond Report </a></li>
                        <%
                            }
                        %>

                        <li><a href="LogOutServlet">LOG OUT</a></li>
                        <%
                            if (user.getRole() == 2 || user.getRole() == 3) {
                        %>
                        <li><a href="BestSellerServlet">Game</a></li>

                        <li><a href="DisplayGenreServlet">Genre</a></li>
                        <li><a href="CallSupport.jsp">Report </a></li>
                        <li><a href="profileServlet">Profile <img src="assets/images/profile-header.jpg" alt=""></a>
                        </li>

                        <%
                            }
                        %>
                        <% } %>
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

<div class="container" id="app">
    <p style="display: none">{{key}}</p>
    <div class="row">
        <div class="col-lg-12">
            <div class="page-content">

                <!-- ***** Most Popular Start ***** -->
                <div class="most-popular">
                    <div class="row">
                        <div class="col-lg-12">
                            <div class="heading-section">
                                <h4>Revenue charts</h4>
                            </div>
                            <div class="row">
                                <div class="col-6">
                                    <div class="row">
                                        <div class="col-4">
                                            <label for="btn_select_game">Choose game</label>
                                            <button style="width: 100%" id="btn_select_game" class="btn btn-success" data-toggle="modal" data-target="#select_games_modal">
                                                Select games
                                            </button>
                                        </div>
                                        <div class="col-4">
                                            <label for="btn_select_genre">Choose genre</label>
                                            <button style="width: 100%" id="btn_select_genre" class="btn btn-success" data-toggle="modal" data-target="#select_genre_modal">
                                                Select genre
                                            </button>
                                        </div>
                                        <div class="col-4">
                                            <label for="btn_select_genre">Choose publisher</label>
                                            <button style="width: 100%" id="btn_select_publisher" class="btn btn-success" data-toggle="modal" data-target="#select_publisher_modal">
                                                Select publisher
                                            </button>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-6">
                                    <div class="row">
                                        <div class="col-5">
                                            <label for="select_time_from">Pick a time</label>
                                            <input v-model="from_date_str" type="datetime-local" class="form-control"
                                                   id="select_time_from">
                                        </div>
                                        <div class="col-5">
                                            <label for="select_time_to">Pick a time</label>
                                            <input v-model="to_date_str" type="datetime-local" class="form-control"
                                                   id="select_time_to">
                                        </div>
                                        <div class="col-2">
                                            <label for="all_time">All&nbsp</label>
                                            <button v-on:click="select_all_time" id="all_time" class="btn btn-success">
                                                All&nbsp
                                            </button>
                                        </div>
                                    </div>

                                </div>

                            </div>
                            <div class="row">
                                <canvas id="bar_chat_revenue" style="width:100%"></canvas>
                            </div>
                        </div>
                        <div class="col-lg-12 mt-2">
                            <div class="heading-section">
                                <h4>Gamers age charts</h4>
                            </div>
                            <div class="row">
                                <div class="col-6">
                                    <button type="button" v-on:click="is_bar_chart_age = !is_bar_chart_age" :class="is_bar_chart_age ? 'btn btn-success float-end' : 'btn btn-outline-success float-end'">Bar chart</button>
                                </div>
                                <div class="col-6">
                                    <button type="button" v-on:click="is_bar_chart_age = !is_bar_chart_age" :class="!is_bar_chart_age ? 'btn btn-success' : 'btn btn-outline-success'">Pie chart</button>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-5">
                                    <div class="row">
                                        <div class="col-12">
                                            <label for="btn_select_game_age_chart">Choose game</label>
                                            <button style="width: 100%" id="btn_select_game_age_chart"
                                                    class="btn btn-success"
                                                    data-toggle="modal" data-target="#select_games_modal_age_chart">
                                                Select games
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div v-show="is_bar_chart_age" class="row">
                                <canvas id="bar_chart_age" style="width:100%"></canvas>
                            </div>
                            <div v-show="!is_bar_chart_age" class="row">
                                <div class="col-7">
                                    <canvas id="pie_chart_age" style="width:100%; max-height: 500px"></canvas>
                                </div>
                                <div class="col-5">
                                    <div class="row">
                                        <div class="col-2">
                                            <button type="button" class="btn mt-1" style="background-color: #36A2EB;width: 38px;height: 38px">&nbsp&nbsp&nbsp</button>
                                        </div>
                                        <div class="col-10">
                                            <p class="mt-2" style="color: white">{{pie_info_label[0]}}&nbsp&nbsp&nbsp:&nbsp&nbsp&nbsp{{pie_info_data[0]}}&nbsp&nbsp({{pie_info_data[0]/pie_info_data_sum*100}}%)</p>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-2">
                                            <button type="button" class="btn mt-1" style="background-color: #FF6384;width: 38px;height: 38px">&nbsp&nbsp&nbsp</button>
                                        </div>
                                        <div class="col-10">
                                            <p class="mt-2" style="color: white">{{pie_info_label[1]}}&nbsp&nbsp&nbsp:&nbsp&nbsp&nbsp{{pie_info_data[1]}}&nbsp&nbsp({{pie_info_data[1]/pie_info_data_sum*100}}%)</p>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-2">
                                            <button type="button" class="btn mt-1" style="background-color: #FF9F40;width: 38px;height: 38px">&nbsp&nbsp&nbsp</button>
                                        </div>
                                        <div class="col-10">
                                            <p class="mt-2" style="color: white">{{pie_info_label[2]}}&nbsp&nbsp&nbsp:&nbsp&nbsp&nbsp{{pie_info_data[2]}}&nbsp&nbsp({{pie_info_data[2]/pie_info_data_sum*100}}%)</p>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-2">
                                            <button type="button" class="btn mt-1" style="background-color: #FFCD56;width: 38px;height: 38px">&nbsp&nbsp&nbsp</button>
                                        </div>
                                        <div class="col-10">
                                            <p class="mt-2" style="color: white">{{pie_info_label[3]}}&nbsp&nbsp&nbsp:&nbsp&nbsp&nbsp{{pie_info_data[3]}}&nbsp&nbsp({{pie_info_data[3]/pie_info_data_sum*100}}%)</p>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-2">
                                            <button type="button" class="btn mt-1" style="background-color: #4BC0C0;width: 38px;height: 38px">&nbsp&nbsp&nbsp</button>
                                        </div>
                                        <div class="col-10">
                                            <p class="mt-2" style="color: white">{{pie_info_label[4]}}&nbsp&nbsp&nbsp:&nbsp&nbsp&nbsp{{pie_info_data[4]}}&nbsp&nbsp({{pie_info_data[4]/pie_info_data_sum*100}}%)</p>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-2">
                                            <button type="button" class="btn mt-1" style="background-color: #9966FF;width: 38px;height: 38px">&nbsp&nbsp&nbsp</button>
                                        </div>
                                        <div class="col-10">
                                            <p class="mt-2" style="color: white">{{pie_info_label[5]}}&nbsp&nbsp&nbsp:&nbsp&nbsp&nbsp{{pie_info_data[5]}}&nbsp&nbsp({{pie_info_data[5]/pie_info_data_sum*100}}%)</p>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-2">
                                            <button type="button" class="btn mt-1" style="background-color: #C9CBCF;width: 38px;height: 38px">&nbsp&nbsp&nbsp</button>
                                        </div>
                                        <div class="col-10">
                                            <p class="mt-2" style="color: white">{{pie_info_label[6]}}&nbsp&nbsp&nbsp:&nbsp&nbsp&nbsp{{pie_info_data[6]}}&nbsp&nbsp({{pie_info_data[6]/pie_info_data_sum*100}}%)</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- ***** Most Popular End ***** -->

                <!-- ***** Social Area Starts ***** -->
                <section class="section" id="social">
                    <div class="container  ">
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="section-heading">
                                    <h2>Social Media</h2>
                                    <span>Details to details is what makes Hexashop different from the other themes.</span>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class=" gaming-library container">
                        <div class=" row images ">
                            <div class="col-2">
                                <div class="thumb">
                                    <div class="icon">
                                        <a href="https://www.facebook.com/">
                                            <h6>FaceBook</h6>
                                        </a>
                                    </div>
                                    <img src="assets/images/Facebook_Logo_(2019).png" alt="">
                                </div>
                            </div>
                            <div class="col-2">
                                <div class="thumb">
                                    <div class="icon">
                                        <a href="http://x.com">
                                            <h6>Twitter</h6>
                                        </a>
                                    </div>
                                    <img src="assets/images/x-social-media-logo-icon.png" alt="">
                                </div>
                            </div>
                            <div class="col-2">
                                <div class="thumb">
                                    <div class="icon">
                                        <a href="http://instagram.com">
                                            <h6>Youtube</h6>
                                        </a>
                                    </div>
                                    <img src="assets/images/vecteezy_youtube-icon-vector_11998173.jpg" alt="">
                                </div>
                            </div>


                            <div class="col-2">
                                <div class="thumb">
                                    <div class="icon">
                                        <a href="http://instagram.com">
                                            <h6>instagram</h6>
                                        </a>
                                    </div>
                                    <img src="assets/images/Instagram_icon.png" alt="">
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
                <!-- ***** Social Area Ends ***** -->


                <!-- ***** Gaming Library Start ***** -->
                <div class="gaming-library profile-library">
                    <div class="col-md-12">
                        <div class="heading-section">
                            <h4><em>Transaction History</em></h4>
                        </div>
                        <div class="item">
                            <ul>

                                <li><h4>MB bank</h4></li>
                                <li><h4>Content</h4><span>ID:123</span></li>
                                <li><h4>Date Added</h4><span>24/08/2036</span></li>
                                <li>
                                    <div class="main-border-button border-no-active"><a href="#">120.000</a></div>
                                </li>
                            </ul>
                        </div>
                        <div class="item">
                            <ul>
                                <li><h4>MB bank</h4></li>

                                <li><h4>Content</h4><span>ID:123</span></li>
                                <li><h4>Date Added</h4><span>22/06/2036</span></li>
                                <li>
                                    <div class="main-border-button border-no-active"><a href="#">200.000</a></div>
                                </li>
                            </ul>
                        </div>
                        <div class="item last-item">
                            <ul>
                                <li><h4>MB bank</h4></li>

                                <li><h4>Content</h4><span>ID:123</span></li>
                                <li><h4>Date Added</h4><span>21/04/2022</span></li>
                                <li>
                                    <div class="main-border-button border-no-active"><a href="#">120.000</a></div>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
                <!-- ***** Gaming Library End ***** -->
            </div>
        </div>
    </div>
    <div class="modal fade" id="select_games_modal" tabindex="-1" role="dialog"
         aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title text-dark" id="exampleModalLongTitle">Select games</h5>
                    <button type="button" class="close btn" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <button v-on:click="select_all_games" class="btn btn-outline-dark col-12 m-1">All games</button>
                    <template v-for="(value, key) in games">
                        <button v-on:click="select_game(key)" type="button"
                                :class="value.selected == false ? 'btn col-12 m-1' : 'btn btn-success col-12 m-1'"
                                :value="value.id">{{value.name}}
                        </button>
                    </template>
                    </select>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>
    <div class="modal fade" id="select_publisher_modal" tabindex="-1" role="dialog"
         aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title text-dark" id="exampleModalLongTitleGenre">Select publisher</h5>
                    <button type="button" class="close btn" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <template v-for="(value, key) in publishers">
                        <button v-on:click="select_publisher(key)" type="button"
                                :class="value.selected == false ? 'btn col-12 m-1' : 'btn btn-success col-12 m-1'"
                                >{{value.name}}
                        </button>
                    </template>
                    </select>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>
    <div class="modal fade" id="select_genre_modal" tabindex="-1" role="dialog"
         aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title text-dark" id="exampleModalLongTitlePublishers">Select games</h5>
                    <button type="button" class="close btn" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <template v-for="(value, key) in genres">
                        <button v-on:click="select_genre(key)" type="button"
                                :class="value.selected == false ? 'btn col-12 m-1' : 'btn btn-success col-12 m-1'"
                        >{{value.type}}
                        </button>
                    </template>
                    </select>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>
    <div class="modal fade" id="select_games_modal_age_chart" tabindex="-1" role="dialog"
         aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title text-dark" id="exampleModalLongTitleAge">Select games</h5>
                    <button type="button" class="close btn" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <template v-for="(value, key) in games_age">
                        <button v-on:click="select_game_age(key)" type="button"
                                :class="value.selected == false ? 'btn col-12 m-1' : 'btn btn-success col-12 m-1'"
                                :value="value.id">{{value.name}}
                        </button>
                    </template>
                    </select>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>
</div>
<footer>
    <div class="container">
        <div class="row">
            <div class="col-lg-12">
                <p>Copyright © 2036 <a href="#">Cyborg Gaming</a> Company. All rights reserved.

                    <br>Design: <a href="https://templatemo.com" target="_blank"
                                   title="free CSS templates">TemplateMo</a></p>
            </div>
        </div>
    </div>
</footer>


<!-- Scripts -->
<!-- Bootstrap core JavaScript -->
<!-- jQuery -->
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
<script src="https://cdnjs.cloudflare.com/ajax/libs/axios/1.7.2/axios.min.js"
        integrity="sha512-JSCFHhKDilTRRXe9ak/FJ28dcpOJxzQaCd3Xg8MyF6XFjODhy/YMCM8HW0TFDckNHWUewW+kfvhin43hKtJxAw=="
        crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<%--<script src="https://cdnjs.cloudflare.com/ajax/libs/vue/2.7.0/compiler-sfc.min.js" integrity="sha512-Wv/dnU62KAAYh3ot213iI46gHY3gvWdyd+deG5WH8rGTMxn4MxW/d7RpP+RBjcw5yaoyVyYgVnlcMnzRqjBGNQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/vue/2.7.0/vue.min.js" integrity="sha512-2v91ndX8jAw9J2IAZ4RnH+lKMOi0PzyFJ1i6D69Nx1RlY2UJpy6r1WIo35HrRkHG4ARj1Xl/xyoh0X9ze+6Y2w==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>--%>
<script src="https://cdn.jsdelivr.net/npm/vue@2.7.16/dist/vue.js"></script>
<!-- Global Init -->
<script src="assets/js/custom.js"></script>
<script>
    var app = new Vue({
        el: "#app",
        data: {
            bills: [],
            gamers: [],
            games: [],
            bar_chart: null,
            from_date_str: '',
            from_date: '',
            to_date_str: '',
            to_date: '',
            key: true,
            is_selecting_all_games: true,
            games_age: [],
            age_ranges: [
                {range: '<17', label: 'Teens', count: 0},
                {range: '18-24', label: 'Young Adults', count: 0},
                {range: '25-34', label: 'Adults', count: 0},
                {range: '35-44', label: 'Middle-aged Adults', count: 0},
                {range: '45-54', label: 'Older Adults', count: 0},
                {range: '55-64', label: 'Senior Adults', count: 0},
                {range: '>65', label: 'Seniors', count: 0}
            ],
            bar_chart_age: null,
            pie_chart_age: null,
            is_bar_chart_age : true,
            pie_info_label: [],
            pie_info_data : [],
            pie_info_data_sum : 0,
            genres: [],
            publishers: [],
            gameHasGenres: [],
            publishes: []
        },
        created() {
            this.getData();
            const currentDate = new Date();
            const firstDayOfMonth = new Date(currentDate.getFullYear(), currentDate.getMonth(), 1);
            const lastDayOfMonth = new Date(currentDate.getFullYear(), currentDate.getMonth() + 1, 0);
            lastDayOfMonth.setHours(23, 59, 59, 999);
            this.from_date = firstDayOfMonth
            this.from_date_str = this.getDateString(firstDayOfMonth)
            this.to_date = lastDayOfMonth
            this.to_date_str = this.getDateString(lastDayOfMonth)
        },
        methods: {
            getData() {
                axios.get('<%=request.getContextPath()%>' + "/get-statistic-data")
                    .then((res) => {
                        this.bills = res.data.bills
                        this.gamers = res.data.gamers
                        this.games = res.data.games
                        this.genres = res.data.genres
                        this.publishers = res.data.publishers
                        this.gameHasGenres = res.data.gameHasGenres
                        this.publishes = res.data.publishes
                        for (let i = 0; i < this.games.length; i++) {
                            this.games[i].selected = false
                        }
                        this.games_age = JSON.parse(JSON.stringify(this.games))
                        for (let i = 0; i < this.bills.length; i++) {
                            this.bills[i].buyTime = new Date(this.bills[i].buyTime.replace(' ', 'T') + 'Z')
                        }
                        this.is_selecting_all_games = false;
                        for (let i = 0; i < this.genres.length; i++) {
                            this.genres[i].selected = false
                        }
                        for (let i = 0; i < this.publishers.length; i++) {
                            this.publishers[i].selected = false
                        }
                        this.init_chart_1();
                        this.count_age_ranges();
                    })
            },
            init_chart_1() {
                let selected_games = []
                let xValues = [];
                let yValues = [];
                for (let i = 0; i < this.games.length; i++) {
                    if (this.games[i].selected) {
                        selected_games.push({
                            "id": this.games[i].id,
                            "name": this.games[i].name,
                            "amount": 0
                        })
                    }
                }
                for (let i = 0; i < selected_games; i++) {
                    selected_games[i].amount = 0
                }
                if (this.from_date_str === '' && this.to_date_str === '' && this.from_date === '' && this.to_date === '') {
                    selected_games = this.calculate_revenue_of_games_all_time(selected_games);
                } else {
                    selected_games = this.calculate_revenue_of_games_in_time(selected_games);
                }
                for (let i = 0; i < selected_games.length; i++) {
                    xValues.push(selected_games[i].name)
                    yValues.push(selected_games[i].amount)
                }
                if (this.bar_chart == null) {
                    this.bar_chart = new Chart('bar_chat_revenue', {
                        type: 'bar',
                        data: {
                            labels: xValues,
                            datasets: [{
                                data: yValues
                            }]
                        },
                        options: {
                            plugins: {
                                legend: {
                                    display: false
                                },
                                title: {
                                    display: true,
                                    text: 'Revenue charts'
                                }
                            }
                        }
                    });
                } else {
                    this.bar_chart.data.datasets[0].data = yValues
                    this.bar_chart.data.labels = xValues
                    this.bar_chart.update();
                }

            },
            calculate_revenue_of_games_in_time(xValues) {
                for (let i = 0; i < this.bills.length; i++) {
                    if (this.bills[i].buyTime > this.from_date && this.bills[i].buyTime < this.to_date) {
                        for (let j = 0; j < xValues.length; j++) {
                            if (this.bills[i].gameId === xValues[j].id) {
                                xValues[j].amount += this.bills[i].buyPrice
                            }
                        }
                    }
                }
                return xValues;
            },
            calculate_revenue_of_games_all_time(xValues) {
                for (let i = 0; i < this.bills.length; i++) {
                    for (let j = 0; j < xValues.length; j++) {
                        if (this.bills[i].gameId === xValues[j].id) {
                            xValues[j].amount += this.bills[i].buyPrice
                        }
                    }
                }
                return xValues;
            },
            select_all_time() {
                this.from_date = '';
                this.from_date_str = '';
                this.to_date = ''
                this.to_date_str = ''
                this.init_chart_1();
            },
            select_game(index) {
                this.is_selecting_all_games = false
                this.games[index].selected = !this.games[index].selected
                this.key = !this.key
                this.init_chart_1();
            },
            select_all_games() {
                for (let i = 0; i < this.games.length; i++) {
                    this.games[i].selected = true
                    this.key = !this.key
                }
                this.init_chart_1();
            },
            getDateString(date) {
                const year = date.getFullYear();
                const month = String(date.getMonth() + 1).padStart(2, '0');
                const day = String(date.getDate()).padStart(2, '0');
                const hours = String(date.getHours()).padStart(2, '0');
                const minutes = String(date.getMinutes()).padStart(2, '0');
                return year + '-' + month + '-' + day + 'T' + hours + ':' + minutes;
            },
            isValidDate(dateString) {
                const timestamp = Date.parse(dateString);
                return !isNaN(timestamp);
            },
            select_game_age(index) {
                for (let i = 0; i < this.games.length; i++) {
                    this.games_age[i].selected = false
                }
                this.games_age[index].selected = true
                this.key = !this.key
                if (this.is_bar_chart_age){
                    this.init_bar_chat_age();
                } else {
                    this.init_pie_chart_age();
                }
            },
            count_age_ranges() {
                this.resetAgeCounts();
                let game_id = null;
                for (let i = 0; i < this.games.length; i++) {
                    if (this.games_age[i].selected){
                        game_id = this.games_age[i].id
                        break
                    }
                }
                for (let j = 0; j < this.bills.length; j++) {
                    if (this.bills[j].gameId === game_id){
                        let gamer_id = this.bills[j].gamerId
                        let age = this.getAgeFromDate(this.gamers.find(gamer => gamer.id === gamer_id).DOB)
                        this.incrementAgeCount(age)
                    }
                }
                this.pie_info_label = this.age_ranges.map(function(item) { return item.range + '(' + item.label + ')'; });
                this.pie_info_data = this.age_ranges.map(item => item.count);
                this.pie_info_data_sum = this.pie_info_data.reduce((a, b) => a + b, 0)
            },
            incrementAgeCount(age) {
                if (age < 17) {
                    this.age_ranges.find(range => range.range === '<17').count++;
                } else if (age >= 18 && age <= 24) {
                    this.age_ranges.find(range => range.range === '18-24').count++;
                } else if (age >= 25 && age <= 34) {
                    this.age_ranges.find(range => range.range === '25-34').count++;
                } else if (age >= 35 && age <= 44) {
                    this.age_ranges.find(range => range.range === '35-44').count++;
                } else if (age >= 45 && age <= 54) {
                    this.age_ranges.find(range => range.range === '45-54').count++;
                } else if (age >= 55 && age <= 64) {
                    this.age_ranges.find(range => range.range === '55-64').count++;
                } else if (age > 65) {
                    this.age_ranges.find(range => range.range === '>65').count++;
                }
            },
            resetAgeCounts() {
                this.age_ranges.forEach(range => {
                    range.count = 0;
                });
            },
            getAgeFromDate(dateString) {
                const [day, month, year] = dateString.split('/').map(Number);
                const birthDate = new Date(year, month - 1, day); // Month is zero-based
                const today = new Date();
                let age = today.getFullYear() - birthDate.getFullYear();
                const monthDiff = today.getMonth() - birthDate.getMonth();
                if (monthDiff < 0 || (monthDiff === 0 && today.getDate() < birthDate.getDate())) {
                    age--;
                }
                return age;
            },
            init_bar_chat_age() {
                this.count_age_ranges();
                if (this.bar_chart_age === null){
                    const labels = this.age_ranges.map(function(item) { return item.range + '(' + item.label + ')'; });
                    const data = this.age_ranges.map(item => item.count);
                    console.log(labels)
                    console.log(data)
                    this.bar_chart_age = new Chart('bar_chart_age', {
                        type: 'bar',
                        data:{
                            labels: labels,
                            datasets: [{
                                label: 'Age ranges',
                                data: data
                            }]
                        },
                        options: {
                            scales: {
                                y: {
                                    beginAtZero: true
                                }
                            },
                            plugins: {
                                legend: {
                                    display: false
                                },
                                title: {
                                    display: true,
                                    text: 'Gamers age charts'
                                },
                            }
                        },
                    })
                } else {
                    this.bar_chart_age.data.datasets[0].data = this.age_ranges.map(item => item.count)
                    console.log(this.age_ranges.map(item => item.count))
                    this.bar_chart_age.update();
                }
            },
            init_pie_chart_age(){
                this.count_age_ranges();
                if (this.pie_chart_age === null){
                    const labels = this.age_ranges.map(function(item) { return item.range + '(' + item.label + ')'; });
                    const data = this.age_ranges.map(item => item.count);
                    console.log(labels)
                    console.log(data)
                    this.pie_chart_age = new Chart("pie_chart_age", {
                        type: "pie",
                        data: {
                            labels: labels,
                            datasets: [{
                                data: data
                            }]
                        },
                        options: {
                            title: {
                                display: true,
                                text: "World Wide Wine Production"
                            }
                        }
                    });
                } else {
                    this.pie_chart_age.data.datasets[0].data = this.age_ranges.map(item => item.count)
                    console.log(this.age_ranges.map(item => item.count))
                    this.pie_chart_age.update();
                }
            },
            select_genre(index){
                this.is_selecting_all_games = false
                for (let i = 0; i < this.genres.length; i++) {
                    this.genres[i].selected = false
                }
                for (let i = 0; i < this.games.length; i++) {
                    this.games[i].selected = false
                }
                this.genres[index].selected = true
                let genre = this.genres[index].type
                this.key = !this.key
                for (let i = 0; i < this.gameHasGenres.length; i++) {
                    if (this.gameHasGenres[i].Type_of_genres === genre){
                        this.games.find(game => game.id === this.gameHasGenres[i].ID_Game).selected = true
                        this.is_selecting_all_games = false
                    }
                }
                this.init_chart_1();
            },
            select_publisher(index){
                this.is_selecting_all_games = false
                for (let i = 0; i < this.publishers.length; i++) {
                    this.publishers[i].selected = false
                }
                for (let i = 0; i < this.games.length; i++) {
                    this.games[i].selected = false
                }
                this.publishers[index].selected = true;
                this.key = !this.key
                for (let i = 0; i < this.publishes.length; i++) {
                    if (this.publishes[i].ID_Game_Publisher === this.publishers[index].id){
                        this.games.find(game => game.id === this.publishes[i].ID_Game).selected = true
                    }
                }
                this.init_chart_1();
            }
        },
        watch: {
            from_date_str(new_value, old_value) {
                if (new_value !== '') {
                    this.from_date = new Date(new_value)
                    this.init_chart_1()
                }
            },
            to_date_str(new_value, old_value) {
                if (new_value !== '') {
                    this.to_date = new Date(new_value)
                    this.init_chart_1()
                }
            }
        }
    })
</script>
</body>

</html>

