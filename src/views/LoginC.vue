<template>
    <div style="background-color: #f8f3e7;" v-if="!isLoading" class="login-container">
    <div class="login-header" style="color: black;">Login as Instructor</div>
    <form @submit.prevent="login">
      <div class="input-group">
        <label for="email" style="color: black;">Email:</label>
        <input v-model="company.companyEmail" type="email" id="email" name="email" required>
      </div>
      <div class="input-group">
        <label for="password" style="color: black;">Password:</label>
        <input v-model="company.password" :type="showPassword ? 'text' : 'password'" id="password" name="password" required>
        <div style="color: #577044;" class="show-password-checkbox" @click="toggleShowPassword">
            <span v-if="showPassword">Hide</span>
            <span v-else>Show</span> Password
        </div>
      </div>
      <button style="background-color: #455936;" class="login-btn">Login</button>
    </form>
  </div>
  <div v-if="isLoading"><Loading></Loading></div>
  </template>
  <script>
  import axios from 'axios';
  import VuexStore from '../store/VuexStore'
  import Loading from './Loading.vue';
  
  export default {
    name:'loginC',
    data() {
    return {
        result:{},
        company:{
          companyEmail: '',
          password: '',
        },
        showPassword: false,
        isLoading: false,
    };
    },
    methods:{
       async login(){
           try{
            //const store = VuexStore; 
                    this.isLoading=true;
                    await axios.post("https://api.golfsprint.net/golfsprint/api/auth/CL/login",this.company,
                    { headers: {
                           'Content-Type': 'application/json',
                         }}).then(res => { this.isLoading=false;
                      
                    axios.defaults.headers.common['Authorization']=`Bearer ${res.data}`

                    localStorage.setItem('token', res.data);
                    localStorage.setItem('isAuth', true);
                    localStorage.setItem('role', 'club');
                    
                    VuexStore.state.token = res.data;
                    VuexStore.state.isAuth = true;
                    window.location.reload();
                    window.location.href = '/customers';
                         })    
            }
            catch(error){
              this.isLoading=false;
                alert(error.response.data);
            }
        },
        toggleShowPassword() {
      this.showPassword = !this.showPassword;
       }
    },
    components: {Loading}
  }
  </script>
  <style>
  .login-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100vh;
  }
  
  .login-header {
  font-size: 24px;
  margin-bottom: 20px;
  }
  
  .login-form {
  width: 300px;
  }
  
  .input-group {
  margin-bottom: 15px;
  }
  
  .input-group label {
  display: block;
  font-weight: bold;
  margin-bottom: 5px;
  }
  
  .input-group input {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  }
  
  .login-btn {
  color: #fff;
  border: none;
  border-radius: 5px;
  padding: 10px;
  font-size: 16px;
  cursor: pointer;
  width: 100%;
  }
  
  .login-btn:hover {
  background-color: #0056b3;
  }

  .password-input {
  position: relative;
}

.show-password-checkbox {
  position: absolute;
  top: 50%;
  right: 10px;
  transform: translateY(-50%);
  cursor: pointer;
}

.show-password-checkbox:hover {
  text-decoration: underline;
}
  </style>