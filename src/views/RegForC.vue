<template>
    
    <div style="background-color: #f8f3e7;" v-if="!isLoading" class="login-container">
    <div class="login-header" style="color: black">Register Instructor Account</div>
    <form @submit.prevent="signUp">
      <div class="input-group">
        <label for="name" style="color: black">Full Name:</label>
        <input v-model="company.companyName" type="text" id="name" name="name" required>
      </div>
      <div class="input-group">
        <label for="name" style="color: black">Location:</label>
        <input v-model="company.companyLocation" type="text" id="location" name="location" required>
      </div>  
      <div class="input-group">
        <label for="email" style="color: black">Email:</label>
        <input v-model="company.companyEmail" type="email" id="email" name="email" required>
      </div>
      <div class="input-group">
        <label for="number" style="color: black">Phone Number:</label>
        <input v-model="company.companyNumber" @input="formatPhoneNumber" type="tel" id="number" name="number" placeholder="ex: +901234567811" required>
      </div>
      <div class="input-group">
        <label for="password" style="color: black">Password:</label>
        <input v-model="company.password" :type="showPassword ? 'text' : 'password'" id="password" name="password" required>
        <div style="color: #577044;" class="show-password-checkbox" @click="toggleShowPassword">
            <span v-if="showPassword">Hide</span>
            <span v-else>Show</span> Password
        </div>
      </div>
      <button style="background-color: #455936;" class="register-btn">Register</button>
    </form>
  </div>
   <div v-if="isLoading"><Loading></Loading></div>
  </template>
  <script>
  
  import axios from 'axios';
  import Loading from './Loading.vue';
  
  
  export default {
    name: 'register_club',
    data() {
        return {
            company: {
                companyName: '',
                companyEmail: '',
                companyNumber: '',
                companyLocation: '',
                password: '',
            },
            showPassword: false,
            isLoading: false,
        };
    },
    methods: {
      formatPhoneNumber(event) {
      const input = event.target;
      let phoneNumber = input.value;

      // Remove all non-numeric characters from the input
      phoneNumber = phoneNumber.replace(/\D/g, '');

      // Check if the input is empty
      if (!phoneNumber) {
        input.value = '';
        return;
      }
  
      // Apply the desired formatting
      let formatted = '+' + phoneNumber; /*.slice(0, 2);
      if (phoneNumber.length > 2) {
        formatted += ' ' + phoneNumber.slice(2, 5);
      }
      if (phoneNumber.length > 5) {
        formatted += ' ' + phoneNumber.slice(5, 8);
      }
      if (phoneNumber.length > 8) {
        formatted += ' ' + phoneNumber.slice(8, 10);
      }
      if (phoneNumber.length > 10) {
        formatted += ' ' + phoneNumber.slice(10);
      } */
      
      input.value = formatted;
    },
        async signUp() {
            
            try {
                //const store = VuexStore;
                this.isLoading=true;
                await axios.post("https://api.golfsprint.net/golfsprint/api/club/register", this.company, {
                    headers: {
                        'Content-Type': 'application/json',
                    }
                }).then(res => { 
                  alert("Successfully registered. Please confirm your email before you login. And also don't forget to check your spams if you don't see the email !");
                  this.isLoading=false;
                  
                    // VuexStore.commit('setUser', this.user);
                    // localStorage.setItem('user', this.user);
                    //localStorage.setItem('users', JSON.stringify(VuexStore.state.users));
                    //To retrieve the data:
                    //const users = JSON.parse(localStorage.getItem('users'));
                    this.$router.push({ name: 'loginC' });
                });
            }
            catch (error) {
                this.isLoading=false;
                
                alert(error.response.data);
            }
        },
        toggleShowPassword() {
            this.showPassword = !this.showPassword;
        }
    },
    components: { Loading }
}
  
  </script>
  <style>
  @media (min-width: 320px) {
    .login-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100vh;
  padding-top: 20px;
  }
  }
  @media (min-width: 375px) {
    .login-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100vh;
  padding-top: 20px;
  }
  }
  @media (min-width: 425px) {
    .login-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100vh;
  padding-top: 20px;
  }
  }
  @media (min-width: 768px) {
    .login-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100vh;
  padding-top: 20px;
  }
  }
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
  
  .register-btn {
  background-color: #455936;
  color: #fff;
  border: none;
  border-radius: 5px;
  padding: 10px;
  font-size: 16px;
  cursor: pointer;
  width: 100%;
  }
  
  .register-btn:hover {
  background-color: #577044;
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
  color: #455936;
}

.show-password-checkbox:hover {
  text-decoration: underline;
}
  </style>