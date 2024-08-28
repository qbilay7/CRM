<script setup>
import { RouterLink, RouterView } from 'vue-router'
import VuexStore from './store/VuexStore'
import LogoutVue from './views/Logout.vue';
const auth=VuexStore.state.isAuth;
//const auth = localStorage.getItem('isAuth');
//const rol=VuexStore.state.role;
const role = localStorage.getItem('role');
const isClub = role === 'club';
//const isAuthenticated = localStorage.getItem('isAuth') === 'true';
</script>

<template>

<head>
  <!-- Basic -->
  <meta charset="utf-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge" />
  <!-- Mobile Metas -->
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
  <!-- Site Metas -->
  <link rel="icon" href="/assets/CSST/images/sprint_green_editted.png" type="image/png" />
  <meta name="keywords" content="" />
  <meta name="description" content="" />
  <meta name="author" content="" />

  <title>GolfSprint</title>


  <!-- fonts style -->
  <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700;900&display=swap" rel="stylesheet">

</head>

<body>

  <div>
    <!-- header section strats -->
    <header class="header_section">
      <div class="container-fluid">
        <nav class="navbar navbar-expand-lg custom_nav-container ">
          <RouterLink class="navbar-brand" to="/">
            <span><div><img class="myim" src="./assets/CSST/images/sprint.png"></div></span>
          </RouterLink>

          <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class=""> </span>
          </button>

          <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav  ml-auto">
              <li v-if="!auth" class="nav-item active">
                <RouterLink class="nav-link active fa fa-home" to="/"> Home<span class="sr-only">(current)</span></RouterLink>
              </li>
              <li v-if="!auth" class="nav-item">
                <RouterLink class="nav-link active fa fa-user" to="/about"> About</RouterLink>
              </li>
              <li v-if="!auth" class="nav-item">
                <RouterLink class="nav-link active fa fa-clone" to="/services"> Services</RouterLink>
              </li>
              <li v-if="!auth" class="nav-item">
                <RouterLink class="nav-link active fa fa-dollar" to="/pricing"> Pricing</RouterLink>
              </li>
              <li v-if="!auth" class="nav-item">
                <RouterLink class="nav-link active fa fa-envelope" to="/contact"> Contact</RouterLink>
              </li>
              <li v-if="!auth" class="nav-item">
                <RouterLink class="nav-link active fa fa-user" to="/register"> Sign Up</RouterLink>
              </li>
              <li v-if="!auth" class="nav-item">
                <RouterLink class="nav-link active fa fa-user" to="/login"> Login</RouterLink>
              </li>
              <li v-if="auth && isClub" class="nav-item">
                <RouterLink class="nav-link active fa fa-clone" to="/customers"> Customers</RouterLink>
              </li>
              <li v-if="auth && isClub" class="nav-item">
                <RouterLink class="nav-link active fa fa-calendar" to="/reservation"> Reservations</RouterLink>
              </li>
              <li v-if="auth && isClub" class="nav-item">
                <RouterLink class="nav-link active fa fa-book" to="/course"> Courses</RouterLink>
              </li>
             <!-- <li v-if="auth && isClub" class="nav-item">
                <RouterLink class="nav-link active fa fa-flag-checkered" to="/event"> Events</RouterLink>
              </li>
              <li v-if="auth && isClub" class="nav-item">
                <RouterLink class="nav-link active fa fa-shopping-cart" to="/shop"> Shop</RouterLink>
              </li> -->
              <li v-if="auth && isClub" class="nav-item">
                <RouterLink class="nav-link active fa fa-user" to="/club/profile"> Profile</RouterLink>
              </li>
              <li v-if="auth && !isClub" class="nav-item">
                <RouterLink class="nav-link active fa fa-search" to="/user/club">Your Instructor</RouterLink>
              </li>
              <li v-if="auth && !isClub" class="nav-item">
                <RouterLink class="nav-link active fa fa-user" to="/user/profile"> Profile</RouterLink>
              </li>
              <li v-if="auth" class="nav-item">
                <LogoutVue></LogoutVue>
              </li>
            </ul>
           <!-- <div v-if="auth" class="quote_btn-container">
              <form class="form-inline">
                <button class="btn   nav_search-btn" type="submit">
                  <i class="fa fa-search" aria-hidden="true"></i>
                </button>
              </form>
              
            </div> -->
          </div>
        </nav>
      </div>
    </header>
    
  </div>
</body>

  <RouterView />
</template>

<style scoped>
 /* Define the style for the navigation items #020230 */ 
 
 .navbar {
    background-color: #455936; /* Change this to the shade of blue you prefer */
}
.navbar li {
    list-style: none;
    display: inline;
    margin-right: 20px;
}

/* Style the links */
.navbar a {
    text-decoration: none;
    color: #ffffff; /* Default link color */
}

/* Add a hover effect */
.navbar a:hover {
   /* color: #536b41; */ 
    text-decoration:underline; 
   /* background-color: #536b41; */
}
.navbar a:focus{
  text-decoration: underline;
}
 /*styles.css or within your component */
.custom-dropdown {
  z-index: 9999;
  position: absolute;
  top: 100%; /* Adjust this value as needed to align the dropdown properly */
  color: #020230;
  background-color: rgb(0, 60, 255);
}
.myim{
  width: 80px;
  height: auto;
  margin-left: 10px;
  margin-right: 1px;
  margin-top: 1px;
  margin-bottom: 1px;
}


</style>
