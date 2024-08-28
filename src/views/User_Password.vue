<template>
    <div v-if="!isLoading" class="container mt-5">
       <div style="box-shadow: 10px 10px 10px rgba(0.2, 0.2, 0.2, 0.2); background-color: #f8f3e7;" class="card">
        <div class="card-header">
            <h4>Change Password</h4>
        </div>
        <div class="card-body">
            <div class="mb-3">
        <label for="password" style="color: black;">Old Password :</label>
        <input v-model="passwords.oldPassword" :type="showPassword ? 'text' : 'password'" id="old_password" name="password" required>
        <div style="color: #577044;" class="show-password-checkbox" @click="toggleShowPassword">
            <span v-if="showPassword">Hide</span>
            <span v-else>Show</span> Password
        </div>
      </div>
      <div class="mb-3">
        <label for="password" style="color: black;">New Password :</label>
        <input v-model="passwords.password" :type="showPassword ? 'text' : 'password'" id="new_password" name="password" required>
        <div style="color: #577044;" class="show-password-checkbox" @click="toggleShowPassword">
            <span v-if="showPassword">Hide</span>
            <span v-else>Show</span> Password
        </div>
      </div>
      <div class="mb-3">
        <label for="password" style="color: black;">New Password (Validate) : </label>
        <input v-model="passwords.validPassword" :type="showPassword ? 'text' : 'password'" id="valid_password" name="password" required>
        <div style="color: #577044;" class="show-password-checkbox" @click="toggleShowPassword">
            <span v-if="showPassword">Hide</span>
            <span v-else>Show</span> Password
        </div>
      </div>
            <div class="mb-3">
                <button style="background-color: #36462a; border: #36462a;" type="button" @click="change" class="btn btn-primary">Update</button>
            </div>
        </div>
       </div>
       <button style="background-color: #617d4b; border: #617d4b;" type="button" @click="back" class="btn btn-danger">Back</button>
    </div>
    <div v-if="isLoading"><Loading></Loading></div>
</template>
<script>
import axios from 'axios';
import VuexStore from '../store/VuexStore';
import Loading from './Loading.vue';

export default {
    name: 'userPassword',
    data(){
       return {
       // companyId: null,
        passwords:{
         oldPassword:'',
         password:'',
         validPassword:'',
        },
         isLoading: false,
         showPassword: false,
       }
    },
    methods: {
        toggleShowPassword() {
      this.showPassword = !this.showPassword;
       },
       async change(){
        try{
        this.isLoading=true,
          await axios.put(`https://api.golfsprint.net/golfsprint/api/users/change/password`,this.passwords,{
            headers:{
                Authorization: `Bearer ${VuexStore.state.token} `,
            }
          }).then(res => { this.isLoading=false;
            alert("Password is successfully changed. Please logout and login again.");
            this.$router.push({name: 'user_profile'});
          });
        }catch(error){
            
            alert(error.response.data);
        }
       },
    back(){
        this.$router.push({name: 'user_profile'});
    }
    },
    components: {Loading}
}
</script>
<style scoped>
.currency-options {
  position: absolute;
  background-color: #fff;
  border: 1px solid #ccc;
  max-height: 150px;
  overflow-y: auto;
  z-index: 1;
}

.currency-option {
  padding: 5px 10px;
  cursor: pointer;
}

.currency-option:hover {
  background-color: #f0f0f0;
}
.currency-input {
  position: relative;
}
</style>