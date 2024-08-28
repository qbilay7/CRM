<template>
    <div>
    <div v-if="!isLoading" class="reservation-config">
      <h2>Configuration</h2>
      <div class="form-group">
        <label for="startBoundary">Start Boundary:</label>
        <input type="time" id="startBoundary" v-model="boundaryRequest.startBoundary" class="form-control" />
      </div>
      <div class="form-group">
        <label for="finishBoundary">Finish Boundary:</label>
        <input type="time" id="finishBoundary" v-model="boundaryRequest.finishBoundary" class="form-control" />
      </div>
      <button @click="saveConfiguration" class="btn btn-primary">Save Configuration</button>
      <button style="background-color: gray; border: black; margin-left: 25px;" type="button" @click="reset" class="btn btn-danger">Reset</button>
      <button style="background-color: #617d4b; border: #617d4b; margin-left: 30px;" type="button" @click="back" class="btn btn-danger">Back</button>
    </div>
    <div v-if="isLoading">
      <Loading></Loading>
    </div>
    <!-- Section to display current boundary times -->
    <div v-if="!isLoading && currentStartBoundary && currentFinishBoundary" class="current-boundaries">
      <h2>Current Boundary Times</h2>
      <div class="current-boundary-info">
        <p><strong>Start Boundary:</strong> {{ currentStartBoundary }}</p>
        <p><strong>Finish Boundary:</strong> {{ currentFinishBoundary }}</p>
      </div>
    </div>
  </div>
  </template>
  
  <script>
  import axios from 'axios';
  import VuexStore from '../store/VuexStore';
  import Loading from './Loading.vue';

  export default {
    name: 'reservConfig',
    data() {
      return {
        boundaryRequest:{
         startBoundary: null,
         finishBoundary: null,
        },
        isLoading: false,
        currentStartBoundary: null,
        currentFinishBoundary: null,
      };
    },
    mounted(){
      this.getCompany();
    },
    methods: {
      async reset() {
        try{
        this.isLoading=true;
        await axios.put(`https://api.golfsprint.net/golfsprint/api/club_profile/reset/boundary`,null,{
            headers:{
                Authorization: `Bearer ${VuexStore.state.token} `,
            }
         }).then(res => { this.isLoading=false;
            alert("Successfully reset.");
            
            window.location.href = '/reservation';
         });
      } catch(error){
        this.isLoading=false;
       
        alert(error.response.data);
      }
    },
      async saveConfiguration() {
        try{
        this.isLoading=true;
        await axios.put(`https://api.golfsprint.net/golfsprint/api/club_profile/set/boundary`,this.boundaryRequest,{
            headers:{
                Authorization: `Bearer ${VuexStore.state.token} `,
            }
         }).then(res => { this.isLoading=false;
            
            window.location.href = '/reservation';
         });
      } catch(error){
        this.isLoading=false;
        
        alert(error.response.data);
      }
    },
    back(){
        this.$router.push({name: 'reservation'});
    },
    async getCompany(email){
          this.isLoading=true;
          await axios.get(`https://api.golfsprint.net/golfsprint/api/club_profile/get`,{
            headers:{
                Authorization: `Bearer ${VuexStore.state.token} `,
            }
          }).then(res => { this.isLoading=false;
            
            this.currentStartBoundary=res.data.startBoundary;
            this.currentFinishBoundary=res.data.finishBoundary;
          });
        },
    },
    components:{Loading}
  };
  </script>
  
  <style scoped>
  /* Reservation Configuration styles */
.reservation-config {
  max-width: 400px;
  margin: 0 auto;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 5px;
  background-color: #f8f3e7;
  box-shadow: 10px 10px 10px rgba(0.2, 0.2, 0.2, 0.2);
}

.form-group {
  margin-bottom: 20px;
}

label {
  font-weight: bold;
}

.form-control {
  width: 100%;
  padding: 10px;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.btn-primary {
  background-color: #455936;
  color: #fff;
  border: none;
  border-radius: 5px;
  padding: 10px 20px;
  font-size: 16px;
  cursor: pointer;
}

.btn-primary:hover {
  background-color: black;
}

/* Current Boundary Times styles */
.current-boundaries {
  margin-top: 30px;
  padding: 20px;
  background-color: #f8f3e7;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.current-boundaries h2 {
  margin-bottom: 20px;
  color: #333;
}

.current-boundary-info p {
  margin-bottom: 10px;
}

.current-boundary-info strong {
  font-weight: bold;
  color: #455936;
}
  </style>
  