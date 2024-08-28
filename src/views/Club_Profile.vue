<template>
    <div  v-if="!isLoading" class="profile"> <!--v-for="company in company"-->
      <div class="profile-header">
        <h1>{{ company.companyName }}</h1>
        <p>{{ company.companyEmail }}</p>
      </div>
      <div class="profile-details">
        <div class="profile-avatar">
          <img v-if="company.companyImage" :src="company.companyImage" alt="Profile Picture">
        </div>
        <div class="profile-info">
          <h2>Club Information</h2>
          <ul>
            <li><strong>Phone:</strong> {{ company.companyNumber }}</li>
            <li><strong>Location:</strong> {{ company.companyLocation }}</li>
            <li><strong>Id:</strong> {{ company.companyId }}</li>
          </ul>
        </div>
      </div>
      <div class="profile-actions">
        <RouterLink style="background-color: #455936; border: #455936;" :to="{path:'/update/'+company.companyId+'/club'}" class="btn btn-primary">Edit Profile</RouterLink>
        <RouterLink style="background-color: #324127; border: #324127; margin-left: 10px;" :to="{path:'/club/password'}" class="btn btn-primary">Change Password</RouterLink>
        <LogoutVue class="btn btn-primary btn-sm" style="background-color: rgb(168, 168, 168); color: black;"></LogoutVue>
        <button class="btn btn-primary btn-sm" style="background-color: rgb(185, 0, 0);" @click="deleteCompany">Delete Account</button>
      </div>
    </div>
    <div v-if="isLoading"><Loading></Loading></div>
  </template>
  
  <script>
  import Loading from './Loading.vue';
  import LogoutVue from './Logout.vue';
  import VuexStore from '../store/VuexStore';
  import axios from 'axios';
  
  export default {
    name: 'club_profile',
    data() {
        return {
            company: {},
            isLoading: false,
        };
    },
    mounted(){
      this.getCompany();
    },
    methods: {
        async getCompany(){
          this.isLoading=true;
          await axios.get(`https://api.golfsprint.net/golfsprint/api/club_profile/get`,{
            headers:{
                Authorization: `Bearer ${VuexStore.state.token} `,
            }
          }).then(res => { this.isLoading=false;
            this.company=res.data;
          });
        },
        deleteCompany(){
          if (confirm('Are you sure you want to delete your account?')) {
            this.$router.push({name: 'companyDelete'});
      }
    },
  },
  components: { Loading, LogoutVue }
};
  </script>
  
  <style scoped>
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
  
  .profile-avatar img {
    width: 200px;
    height: auto;
    border-radius: 50%;
  }
  
  .profile-info {
    flex: auto;
    margin-left: 50px;
    justify-content: center;
  }
  @media (max-width: 450px) {
    .profile-info h2 {
    margin-bottom: 10px;
    font-size: 20px;
  }
  .profile-avatar img {
    width: 100px;
    height: auto;
    border-radius: 50%;
  }
  }
  @media (max-width: 380px) {
    .profile-info h2 {
    margin-bottom: 10px;
    font-size: 20px;
    }
    .profile-avatar img {
    width: 100px;
    height: auto;
    border-radius: 50%;
  }
  }
  .profile-info h2 {
    margin-bottom: 10px;
  }
  
  .profile-actions {
    margin-top: 20px;
    text-align: center;
  }
  
  .profile-actions button {
    margin: 0 10px;
    padding: 10px 20px;
    border: none;
    border-radius: 5px;
    background-color: #007bff;
    color: #fff;
    cursor: pointer;
    transition: background-color 0.3s;
  }
  
  .profile-actions button:hover {
    background-color: #0056b3;
  }
  </style>
  