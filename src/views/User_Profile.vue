<template>
    <div  v-if="!isLoading" class="profile"> <!--v-for="company in company"-->
      <div class="profile-header">
        <h1>{{ user.name }} {{ user.surname }}</h1>
        <p>{{ user.email }}</p>
      </div>
      <div class="profile-details">
      <!--  <div class="profile-avatar">
          <img v-if="company.companyImage" :src="'data:image/jpeg;base64,' + company.companyImage" alt="Profile Picture">
        </div> -->
        <div class="profile-info">
          <h2>User Information</h2>
          <ul>
            <li><strong>Phone:</strong> {{ user.number }}</li>
            <li><strong>Id:</strong> {{ user.userId }}</li>
          </ul>
        </div>
      </div>
      <div class="profile-actions">
        <RouterLink style="background-color: #455936; border: #455936;" :to="{path:'/update/'+user.userId+'/user'}" class="btn btn-primary">Edit Profile</RouterLink>
        <RouterLink style="background-color: #324127; border: #324127; margin-left: 10px;" :to="{path:'/user/password'}" class="btn btn-primary">Change Password</RouterLink>
        <LogoutVue style="background-color: rgb(168, 168, 168); color: black;"></LogoutVue>
        <button class="btn btn-danger" style="background-color: rgb(185, 0, 0);" @click="deleteUser">Delete Account</button>
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
    name: 'user_profile',
    data() {
        return {
            user: {},
            isLoading: false,
        };
    },
    mounted(){
      this.getUser();
    },
    methods: {
        async getUser(){
          try{
          this.isLoading=true;
          await axios.get(`https://api.golfsprint.net/golfsprint/api/users/getBy`,{
            headers:{
                Authorization: `Bearer ${VuexStore.state.token} `,
            }
          }).then(res => { this.isLoading=false;
            
            this.user=res.data;
          });
        }
        catch(error){ this.isLoading=false;
          
          alert(error.response.data);
        }
        },
        deleteUser(){
          if (confirm('Are you sure you want to delete your account?')) {
            this.$router.push({name: 'userDelete'});
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
  