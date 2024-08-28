<template>
    <button class="logout-button" @click="logout">Logout</button>
</template>
<script>
import axios from 'axios';
import VuexStore from '../store/VuexStore'
const role = localStorage.getItem('role');
export default{
  methods:{
   async logout(){
      try{
      if(role==='club'){
       await axios.post('https://api.golfsprint.net/golfsprint/api/auth/CL/logout')
       .then(res => {
        
        VuexStore.state.isAuth=false;
       // VuexStore.state.email='';
       // VuexStore.state.password='';
        VuexStore.state.token='';
        VuexStore.state.role='';
       // VuexStore.state.user= {};
        localStorage.setItem('token', '');
        localStorage.setItem('isAuth', false);
        localStorage.setItem('role', '');
        localStorage.setItem('companyEmail', '');
       // localStorage.setItem('email', '');
       // localStorage.setItem('password','');
       // localStorage.setItem('user',{});
       
        window.location.reload();
        window.location.href = '/';
       });
      }
      else{ 
       await axios.post('https://api.golfsprint.net/golfsprint/api/auth/US/logout')
       .then(res => {
        
        VuexStore.state.isAuth=false;
       // VuexStore.state.email='';
       // VuexStore.state.password='';
        VuexStore.state.token='';
        VuexStore.state.role='';
       // VuexStore.state.user= {};
        localStorage.setItem('token', '');
        localStorage.setItem('isAuth', false);
        localStorage.setItem('role', '');
        localStorage.setItem('companyEmail', '');
       // localStorage.setItem('email', '');
       // localStorage.setItem('password','');
       // localStorage.setItem('user',{});
       
        window.location.reload();
        window.location.href = '/';
       });
      }
        
    }catch(err){
      
      alert("Some error occured.");
    }
    }
  }
}

</script>

<style scoped>
.logout-button{
  background-color: #f8f3e7;
  color: #455936;
  border: none;
  border-radius: 5px;
  padding: 10px 20px;
  font-size: 16px;
  cursor: pointer;
  transition: background-color 0.3s, transform 0.2s;
}
.logout-button:hover{
  background-color: black;
  color: #f8f3e7;
  transform: scale(1.05);
}
</style>