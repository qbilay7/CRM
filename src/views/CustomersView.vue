<template>
    <div v-if="!isLoading">
      <div style="background-color: #f8f3e7; flex-direction: column; min-height: 100vh;" class="card">
        <div class="card-header">
          <h4>
            Customers
            <RouterLink style="background-color: #36462a; border: black;" to="/create/customer" class="btn btn-primary float-end">
              Add Customer
            </RouterLink>
            <RouterLink style="background-color: #709157; border: black; margin-left: 10px;" to="/send/messages" class="btn btn-primary float-end">
              Send Email All
            </RouterLink>
          </h4>
          <div class="input-group">
            <input style="background-color: #f8f3e7;" type="text" class="form-control" placeholder="Search..." v-model="searchQuery">
     <!-- <button class="btn btn-outline-secondary" type="button" @click="searchUser">Search</button> -->
          </div>
        </div>
        <div class="card-body">
          <table class="table table-bordered">
             <thead>
               <tr>
                <th>Customer Id</th>
                <th>Name</th>
                <th>Surname</th>
                <th>Email</th>
                <th>Phone Number</th>
               </tr>
             </thead>
             <tbody>
            <tr v-for="userItem in user" :key="userItem.userId">
              <td>{{ userItem.userId }}</td>
              <td>{{ userItem.name }}</td>
              <td>{{ userItem.surname }}</td>
              <td>{{ userItem.email }}</td>
              <td>{{ userItem.number }}</td>
            
              <td> <RouterLink style="background-color: #2a3620; border: #2a3620;" :to="{path:'/edit/'+userItem.userId+'/customer'}" class="btn btn-success ">
              Edit
              </RouterLink>
              <RouterLink style="background-color: #709157; border: black; margin-left: 10px;" :to="{path:'/send/'+userItem.userId+'/message'}" class="btn btn-primary float-end">
              Send Email
              </RouterLink>
               <button style=" margin-left: 10px;" type="button" @click="deleteUser(userItem.userId)" class="btn btn-danger">Delete</button>
              </td>
            
            </tr>
          </tbody>
          </table>
        </div>
      </div>
    </div>
    <div v-if="isLoading"><Loading></Loading></div>
  </template>
  
  <script>
  import axios from 'axios';
  import { mapState } from 'vuex';
  import VuexStore from '../store/VuexStore';
  import Loading from './Loading.vue';
  
  export default {
   name:'customers',
   /* async created(){
      const isAuthenticated = localStorage.getItem('isAuth') === 'true';
      if(isAuthenticated){
      const response = await axios.get(`https://api.golfsprint.net/golfsprint/api/users/get/${userItem.userId}`,{
        headers:{
          Authorization: `Bearer ${VuexStore.state.token} `,
         // 'Cache-Control': 'no-cache',
        }
      });
      
      VuexStore.state.user=response.data;
    }
   }, */
   computed: {
    ...mapState(['token','isAuth'])
  },
   data(){
     return {
      user: [],
      isLoading: false,
      searchQuery: '',
      originalUser: [],
     }
   },
   mounted(){
    this.getPeople();
   },
   methods:{
   async getPeople(){
      this.isLoading=true;
     await axios.get(`https://api.golfsprint.net/golfsprint/api/users/getAll`,{
        headers:{
          Authorization: `Bearer ${VuexStore.state.token} `,
         // 'Cache-Control': 'no-cache',
        }
        }).then(res => { this.isLoading=false;
          this.user = res.data;
          this.originalUser = res.data;
          
      });
    },
    deleteUser(userId){
      try{
      if (confirm('Are you sure you want to delete this customer?')){
        this.isLoading=true;
      axios.delete(`https://api.golfsprint.net/golfsprint/api/users/delete/${userId}`,{
        headers:{
          Authorization: `Bearer ${VuexStore.state.token} `,
         // 'Cache-Control': 'no-cache',
        }
      }).then(res => { this.isLoading=false;
        
        window.location.reload();
      })
    }
  }catch(error){
     this.isLoading=false;
     alert(error.response.data)
  }
  },
 /* async searchUser(){
    try{
      await axios.get(`https://api.golfsprint.net/golfsprint/api/users/search/users/${email}`,this.searchQuery,{
        headers:{
          Authorization: `Bearer ${VuexStore.state.token} `,
         // 'Cache-Control': 'no-cache',
        }
      }).then(res => {
        
        window.location.reload();
      })
    }
    catch(error){
      alert(error.data.message);
    }
  } */
   },
   components: {Loading},
   watch: {
    searchQuery: function(val) {
      if (val) {
        this.user = this.originalUser.filter(user =>
          user.name.toLowerCase().includes(val.toLowerCase()) ||
          user.surname.toLowerCase().includes(val.toLowerCase()) ||
          user.email.toLowerCase().includes(val.toLowerCase()) ||
          user.number.includes(val) ||
          user.userId.toString().includes(val)
        );
      } else {
        this.user = this.originalUser;
      }
    }
   }
}
  </script>