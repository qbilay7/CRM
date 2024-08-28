<template>
    <div v-if="!isLoading" class="container">
    <div class="content">
      <h1>Account Delete</h1>
      <div class="form-group">
        <label for="password">Password:</label>
        <div class="input-group">
          <input
            
            class="form-control"
            id="password"
            v-model="password"
            :type="showPassword ? 'text' : 'password' "
            placeholder="Enter your password"
            required
          >
          <button
            class="btn btn-outline-secondary"
            type="button"
            @click="togglePasswordVisibility"
          >
            {{ showPassword ? 'Hide' : 'Show' }}
          </button>
        </div>
      </div>
      <button class="btn btn-primary" style="background-color: #455936; border-color: #455936;" @click="deleteUser">Delete</button>
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
    name: 'userDelete',
    data() {
      return {
        user: {},
        isLoading: false,
        password: '',
        showPassword: false,
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
      togglePasswordVisibility() {
        this.showPassword = !this.showPassword;
      },
      async deleteUser(){
        try{
          if (confirm('Are you sure you want to delete your account?')) {
            this.isLoading=true;
         await axios.delete(`https://api.golfsprint.net/golfsprint/api/users/deleteByUser/${this.user.userId}`,{
        headers:{
          Authorization: `Bearer ${VuexStore.state.token} `,
         // 'Cache-Control': 'no-cache',
        },
        params:{
          password: this.password,
        }
      }).then(res => { this.isLoading=false;
        
        VuexStore.state.isAuth=false;
       // VuexStore.state.email='';
       // VuexStore.state.password='';
        VuexStore.state.token='';
        VuexStore.state.role='';
       // VuexStore.state.user= {};
        localStorage.setItem('token', '');
        localStorage.setItem('isAuth', false);
        localStorage.setItem('role', '');
        localStorage.setItem('userId', null);
       // localStorage.setItem('email', '');
       // localStorage.setItem('password','');
       // localStorage.setItem('user',{});
       
        window.location.reload();
        window.location.href = '/';
      })
        }
      }catch(error){
        this.isLoading=false;
        alert(error.response.data);
      }
        }
    },
    components: { Loading, LogoutVue }
  };
  </script>
  
  <style scoped>
  .container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}

.content {
  width: 400px;
  text-align: center;
}

.form-group {
  margin-bottom: 20px;
}

.input-group {
  position: relative;
  width: 100%;
}

.input-group input {
  padding-right: 35px;
}
  </style>
  