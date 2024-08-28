<template>
  <!--
    <div v-if="!isLoading" style="background-color: #f8f3e7; flex-direction: column; min-height: 100vh;">
      <div  id="event-page" class="container">
        <h1 class="mb-4">Search for Golf Clubs</h1>
        
        <div class="input-group">
              <input type="text" class="form-control" placeholder="Search..." v-model="searchQuery">
       
            </div>
        <div v-if="companies.length > 0">
        <router-link v-for="company in companies" :key="company.companyId" :to="{path:'/user/'+company.companyId+'/club'}" class="card mb-3">
          <div class="row g-0">
            <div class="col-md-8">
              <div class="card-body">
                <h5 class="card-title">{{ company.companyName }}</h5>
                <p class="card-text"><strong>Contact Info:</strong> {{ company.companyEmail }} - {{ company.companyNumber }}</p>
                <p class="card-text"><strong>Address:</strong> {{ company.companyLocation }}</p>
              </div>
            </div>
            <div class="col-md-4">
              <img v-if="company.companyImage" :src="'data:image/jpeg;base64,' + company.companyImage"  class="img-fluid">
            </div>
          </div>
        </router-link>
      </div>
        <div v-else>
          No club found.
        </div>
      </div>
    </div>
      <div v-if="isLoading"><Loading></Loading></div>
  -->
    </template>
    
    <script>
    /*
    import axios from 'axios';
    import VuexStore from '../store/VuexStore';
    import Loading from './Loading.vue';
    
    export default {
      name:'user_search',
      data() {
        return {
          companies: [],
          isLoading: false,
          searchQuery: '',
          originalCompanies: [],
        };
      },
      mounted(){
        this.getClubs();
      },
      methods:{
          async getClubs(){
            this.isLoading=true;
        await axios.get(`https://api.golfsprint.net/golfsprint/api/club_profile/user/getAll`,{
           headers:{
             Authorization: `Bearer ${VuexStore.state.token} `,
            // 'Cache-Control': 'no-cache',
           }
           }).then(res => { this.isLoading=false;
             this.companies = res.data;
             this.originalCompanies = res.data;
             console.log(this.companies);
         });
        
        }
  
      },
      components: {Loading},
      watch: {
      searchQuery: function(val) {
        if (val) {
          this.companies = this.originalCompanies.filter(company =>
            company.companyName.toLowerCase().includes(val.toLowerCase()) ||
            company.companyEmail.toLowerCase().includes(val.toLowerCase()) ||
            company.companyNumber.includes(val) ||
            company.companyLocation.toLowerCase().includes(val.toLowerCase())
          );
        } else {
          this.companies = this.originalCompanies;
        }
      }
     }
    };
    */
    </script>
    
    <style scoped>
    /*
    #event-page {
      max-width: 800px;
      margin: auto;
    }
    
    .card {
        border: 1px solid #ccc;
        border-radius: 15px; 
        margin-bottom: 20px;
        transition: background-color 0.3s;
    }
    
    .card-title {
      font-size: 1.5em;
      color: #36452a;
    }
    
    .card-text {
      color: #333;
    }
    .card:hover {
      background-color: #f8f3e7; 
    }
    .card:hover .card-title{
  text-decoration-line: underline; 
  text-decoration-color: #455936; 
}
.card:hover .card-text{
  text-decoration-line: underline; 
  text-decoration-color: #455936; 
}
.col-md-4 img{
  border-radius: 50%;
  padding: 10px;
}
 */
    </style>
    
