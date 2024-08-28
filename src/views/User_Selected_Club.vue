<template>
    <div v-if="!isLoading" class="pro-shop">
      <!-- Sidebar -->
      <div class="sidebar">
        <div class="sidebar-avatar">
             <img v-if="company.companyImage" :src=" company.companyImage" alt="Profile Picture">
        </div>
        <h2>{{ company.companyName }}</h2>
        <ul>
          <li><router-link :to="{path:'/user/club'}">Instructor</router-link></li>
          <li><router-link :to="{path:'/user/reservation'}">Reservation</router-link></li>
          <li><router-link :to="{path:'/user/course'}">Courses</router-link></li>
         <!-- <li><router-link :to="{path:'/user/'+companyId+'/event'}">Events</router-link></li>
          <li><router-link :to="{path:'/user/'+companyId+'/shop'}">Pro-Shop</router-link></li> -->
        </ul>
      </div>
  
      <!-- Main Content Area -->
      <div class="main-content">
        <!-- Page Header -->
        <div class="page-header">
          <h2 class="tit2">Greetings from {{ company.companyName }}</h2>
        </div>
        
        <div class="profile"> 
      <div class="profile-header">
        <p>Location: {{ company.companyLocation }}</p>
      </div>
      <div class="profile-details">
        <div class="profile-info">
          <h2 class="tit">Contact Information</h2>
          <ul>
            <li><strong class="tit3">Phone:</strong> {{ company.companyNumber }}</li>
            <li class="tit3"><strong class="tit3">Email:</strong> {{ company.companyEmail }} </li>
          </ul>
        </div>
      </div>
    </div>

        <!-- Router View to Render Sub-Pages -->
        <router-view></router-view>
      </div>
    </div>
    <div v-if="isLoading"><Loading></Loading></div>
  </template>
  
  <script>
  import { RouterLink, RouterView } from 'vue-router'
  import axios from 'axios';
  import VuexStore from '../store/VuexStore';
  import Loading from './Loading.vue';
  
  export default {
    name: 'selected',
    data(){
      return{
        isLoading: false,
        company: {
            companyName: '',
            companyEmail: '',
            companyNumber: '',
            companyLocation: '',
            companyImage: null,
        }
        
      }
    },
    mounted(){
      // this.companyId=this.$route.params.companyId;
       this.getCompany();
    },
    components:{Loading},
    methods:{
    async getCompany(){
       try{
        this.isLoading=true;
         await axios.get(`https://api.golfsprint.net/golfsprint/api/club_profile/getByUser`,{
            headers:{
                Authorization: `Bearer ${VuexStore.state.token} `,
            }
         }).then(res => { this.isLoading=false;
            
            this.company.companyName=res.data.companyName;
            this.company.companyEmail=res.data.companyEmail;
            this.company.companyLocation=res.data.companyLocation;
            this.company.companyNumber=res.data.companyNumber;
            this.company.companyImage=res.data.companyImage;
         });
       }
       catch(error){
        this.isLoading=false;
        
        alert(error.data.message);
       }
    }
    },
  }
  </script>
  
  <style scoped>
  /* Global styles */
  body {
  font-family: 'Arial', sans-serif;
  margin: 0;
  padding: 0;
}

/* Container for the pro-shop page */
.pro-shop {
  display: flex;
  min-height: 100vh;
  background-color: #f8f3e7; 
}

/* Sidebar styles */
.sidebar {
  background-color: #37472c;
  color: #ffffff;
  padding: 20px;
  width: 250px;
  flex-direction: column;
}

.sidebar h2 {
  margin-bottom: 20px;
  color: #ffffff;
  white-space: nowrap; /* Prevent text from wrapping */
  overflow: hidden; /* Hide overflowed text */
  text-overflow: ellipsis; /* Display ellipsis (...) for overflowed text */

}

.sidebar ul {
  list-style-type: none;
  padding: 0;
}

.sidebar li {
  margin-bottom: 10px;
}

.sidebar a {
  color: #ffffff;
  text-decoration: none;
  transition: color 0.3s ease;
}

.sidebar a:hover {
  color: black;
}
.sidebar-avatar img {
    width: 100px;
    height: auto;
    border-radius: 50%;
  }

/* Main Content Area styles */
.main-content {
  flex-grow: 1;
  padding: 20px;
}

/* Page Header styles */
.page-header {
  margin-bottom: 40px;
}

.page-header h2 {
  margin-bottom: 10px;
  color: #37474f;
}

.page-header p {
  color: #607d8b;
}

.profile {
    max-width: 800px;
    margin: 0 auto;
    padding: 20px;
    border: 1px solid #ccc;
    border-radius: 8px;
    background-color: #f9f9f9;
    box-shadow: 10px 10px 10px rgba(0.2, 0.2, 0.2, 0.2);
  }
  
  .profile-header {
    text-align: center;
  }
  
  .profile-header h1 {
    margin-bottom: 5px;
  }
  
  .profile-header p {
    color: #666;
  }
  
  .profile-details {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-top: 20px;
  }
  
  
  .profile-info {
    flex: auto;
    margin-left: 50px;
    justify-content: center;
  }
  
  .profile-info h2 {
    margin-bottom: 10px;
  }
  @media (max-width: 430px) {
    .tit{
    font-size: 15px;
    text-align: left;
  }
  .tit2{
    font-size: 15px;
    text-align: left;
  }
  .tit3{
    font-size: 12px;
    text-align: left;
  }
    .sidebar-avatar img {
    width: 90px;
    height: auto;
    border-radius: 50%;
  }
  .sidebar {
  background-color: #37472c;
  color: #ffffff;
  padding: 10px;
  width: 120px;
  flex-direction: column;
  }
.sidebar h2 {
  font-size: 20px;
  margin-bottom: 10px;
  color: #ffffff;
  white-space: nowrap; /* Prevent text from wrapping */
  overflow: hidden; /* Hide overflowed text */
  text-overflow: ellipsis; /* Display ellipsis (...) for overflowed text */

}
.main-content {
  flex-grow: 1;
  padding: 15px;
}

/* Page Header styles */
.page-header {
  margin-bottom: 40px;
}

.page-header h2 {
  margin-bottom: 8px;
  color: #37474f;
}
.profile {
    max-width: 500px;
    margin: 0 auto;
    padding: 20px;
    border: 1px solid #ccc;
    border-radius: 8px;
    background-color: #f9f9f9;
  }
  
  .profile-header {
    text-align: center;
  }
  
  .profile-header h1 {
    margin-bottom: 5px;
    text-align: left;
  }
  
  .profile-header p {
    color: #666;
  }
  
  .profile-details {
    display: flex;
    justify-content: space-between;
    align-items: left;
    margin-top: 20px;
    width: 10px;
    text-align: left;
  }
  
  
  .profile-info {
    flex: auto;
    margin-left: 5px;
    justify-content: left;
    text-align: left;
  }
  
  .profile-info h2 {
    margin-bottom: 10px;
    text-align: left;
  }
  }
  @media (max-width: 380px) {
    .tit{
    font-size: 15px;
  }
  .tit2{
    font-size: 15px;
  }
  .tit3{
    font-size: 12px;
  }
    .sidebar-avatar img {
    width: 90px;
    height: auto;
    border-radius: 50%;
  }
  .sidebar {
  background-color: #37472c;
  color: #ffffff;
  padding: 10px;
  width: 120px;
  flex-direction: column;
}

.sidebar h2 {
  font-size: 20px;
  margin-bottom: 10px;
  color: #ffffff;
  white-space: nowrap; /* Prevent text from wrapping */
  overflow: hidden; /* Hide overflowed text */
  text-overflow: ellipsis; /* Display ellipsis (...) for overflowed text */

}
.main-content {
  flex-grow: 1;
  padding: 15px;
}

/* Page Header styles */
.page-header {
  margin-bottom: 40px;
}

.page-header h2 {
  margin-bottom: 8px;
  color: #37474f;
}
.profile {
    max-width: 500px;
    margin: 0 auto;
    padding: 20px;
    border: 1px solid #ccc;
    border-radius: 8px;
    background-color: #f9f9f9;
  }
  
  .profile-header {
    text-align: center;
  }
  
  .profile-header h1 {
    margin-bottom: 5px;
  }
  
  .profile-header p {
    color: #666;
  }
  
  .profile-details {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-top: 20px;
    width: 10px;
  }
  
  
  .profile-info {
    flex: auto;
    margin-left: 0px;
    justify-content: center;
  }
  
  .profile-info h2 {
    margin-bottom: 10px;
  }
  
  }
  @media (max-width: 321px) {
    .sidebar-avatar img {
    width: 80px;
    height: auto;
    border-radius: 50%;
  }
  .sidebar {
  background-color: #37472c;
  color: #ffffff;
  padding: 10px;
  width: 100px;
  flex-direction: column;
}

.sidebar h2 {
  font-size: 20px;
  margin-bottom: 10px;
  color: #ffffff;
  white-space: nowrap; /* Prevent text from wrapping */
  overflow: hidden; /* Hide overflowed text */
  text-overflow: ellipsis; /* Display ellipsis (...) for overflowed text */

}
.main-content {
  flex-grow: 1;
  padding: 15px;
}

/* Page Header styles */
.page-header {
  margin-bottom: 40px;
}

.page-header h2 {
  margin-bottom: 8px;
  color: #37474f;
}
.profile {
    max-width: 500px;
    margin: 0 auto;
    padding: 20px;
    border: 1px solid #ccc;
    border-radius: 8px;
    background-color: #f9f9f9;
  }
  
  .profile-header {
    text-align: center;
  }
  
  .profile-header h1 {
    margin-bottom: 5px;
  }
  
  .profile-header p {
    color: #666;
  }
  
  .profile-details {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-top: 20px;
    width: 10px;
  }
  
  
  .profile-info {
    flex: auto;
    margin-left: 0px;
    justify-content: center;
  }
  
  .profile-info h2 {
    margin-bottom: 10px;
  }
  .tit{
    font-size: 10px;
  }
  .tit2{
    font-size: 10px;
  }
  .tit3{
    font-size: 10px;
  }
  }
  </style>
  