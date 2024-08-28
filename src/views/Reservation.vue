<template>
  <div v-if="!isLoading" style="background-color: #f8f3e7;">
    <div style="background-color: #f8f3e7;" id="app" class="container">
      <h1 class="mb-4">Reservations</h1>
      <button style="box-shadow: 10px 10px 10px rgba(0.2, 0.2, 0.2, 0.2); background-color: #617d4b; border: #617d4b;" type="button" @click="configure" class="btn btn-danger">Configure</button>
      <!-- Add Reservation Form -->
      <form @submit.prevent="addReservation" class="mb-4">
        <div class="row g-3">
          <div class="col-sm-4">
            <label for="name" class="form-label">Court Name:</label>
            <input style="box-shadow: 10px 10px 10px rgba(0.2, 0.2, 0.2, 0.2);" type="text" id="name" class="form-control" v-model="newReservation.courtName" >
          </div>
          <div class="col-sm-4">
            <label for="date" class="form-label">Date:</label>
            <input style="box-shadow: 10px 10px 10px rgba(0.2, 0.2, 0.2, 0.2);" type="date" id="date" class="form-control" v-model="newReservation.date" required>
          </div>
          <div class="col-sm-4">
            <label for="time" class="form-label">Start Time:</label>
            <input style="box-shadow: 10px 10px 10px rgba(0.2, 0.2, 0.2, 0.2);" type="time" id="time" class="form-control" v-model="newReservation.start" required>
          </div>
          <div class="col-sm-4">
            <label for="time" class="form-label">Finish Time:</label>
            <input style="box-shadow: 10px 10px 10px rgba(0.2, 0.2, 0.2, 0.2);" type="time" id="time" class="form-control" v-model="newReservation.finish" required>
          </div>
          <div class="col-sm-4">
            <label for="text" class="form-label">Customer Id:</label>
            <input style="box-shadow: 10px 10px 10px rgba(0.2, 0.2, 0.2, 0.2);" type="number" id="text" class="form-control" v-model="newReservation.userId" required>
          </div>
          <div class="col-sm-4 align-self-end">
            <button style="box-shadow: 10px 10px 10px rgba(0.2, 0.2, 0.2, 0.2); background-color: #455936; border: #455936;" type="submit" class="btn btn-primary">Add Reservation</button>
          </div>
        </div>
      </form>
       <!-- Vue-cal to display reservations -->
    <div class="vue-cal" style="box-shadow: 10px 10px 10px rgba(0.2, 0.2, 0.2, 0.2);">
      <vue-cal class="event"
      :events="formattedEvents" />
    </div>
    
    <div>

    </div>
    <div style="box-shadow: 10px 10px 10px rgba(0.2, 0.2, 0.2, 0.2);" class="input-group">
            <input type="text" class="form-control" placeholder="Search..." v-model="searchQuery">
     <!-- <button class="btn btn-outline-secondary" type="button" @click="searchUser">Search</button> -->
          </div>
    <ul v-if="reservations.length > 0" class="list-group">
        <li class="list-group-item" v-for="reservation in reservations" :key="reservation.reservationId">
  <div class="d-flex justify-content-between align-items-center">
    <div class="d-flex flex-column align-items-start">
      <span><strong>Customer:</strong> {{ reservation.user.name }} {{ reservation.user.surname }} ,{{ reservation.user.email }} ,{{ reservation.user.number }}, Customer Id: {{ reservation.user.userId }}</span>
      <span><strong>Date(day/month/year):</strong> {{ formatDate(reservation.date) }}</span>
      <span><strong>Start Time:</strong> {{ reservation.start }}</span>
      <span><strong>Finish Time:</strong> {{ reservation.finish }}</span>
      <span><strong>Court Name:</strong> {{ reservation.courtName }}</span>
      <span><strong>Instructor:</strong> {{ reservation.company.companyName }}</span>
      <span><strong>Reservation Id:</strong> {{ reservation.reservationId }}</span>
      <span v-if="isReservToday(reservation.date)"><strong style="color: red;">This Reservation is Today</strong></span>
    </div>
    <button @click="deleteReservation(reservation.reservationId)" class="btn btn-danger btn-sm">Delete</button>
  </div>
  
</li>
</ul>
<div v-else>
      No reservations found.
    </div> 
    </div>
    </div>
    <div v-if="isLoading"><Loading></Loading></div>
  </template>
  
  <script>
  import axios from 'axios';
  import VuexStore from '../store/VuexStore';
  import VueCal from 'vue-cal';
  import 'vue-cal/dist/vuecal.css';
  import Loading from './Loading.vue';
  
  export default {
    name: 'reservation',
    data() {
      return {
        newReservation: {
          reservationId: null,
          courtName: '',
          date: '',
          start: '',
          finish: '',
          userId: ''
        },
        reservations: [],
        isLoading: false,
        searchQuery: '',
        originalReservations: [],
      };
    },
    components:{
      VueCal,
      Loading
    }, 
    computed:{
      formattedEvents() {
      // Transform your reservations data into a format that vue-cal understands
      const events = this.reservations.map(reservation => ({
        title: `Court:${reservation.courtName} (Instructor:${reservation.company.companyName}) - Customer Info:${reservation.user.name} ${reservation.user.surname} (${reservation.user.email} - ${reservation.user.number}) - Id:${reservation.reservationId}`,
        start: new Date(`${reservation.date} ${reservation.start}`),
        end: new Date( `${reservation.date} ${reservation.finish}`),
      }));
      
      return events; 
     
    }
    },
    mounted(){
     this.getReservations();
    },
    watch: {
    searchQuery: function(val) {
      if (val) {
        this.reservations = this.originalReservations.filter(reservation =>
          reservation.reservationId.toString().includes(val) ||
          reservation.courtName.toLowerCase().includes(val.toLowerCase()) ||
          reservation.date.includes(val) ||
          reservation.company.companyName.toLowerCase().includes(val.toLowerCase()) ||
          reservation.user.name.toLowerCase().includes(val.toLowerCase()) ||
          reservation.user.surname.toLowerCase().includes(val.toLowerCase()) ||
          reservation.user.email.toLowerCase().includes(val.toLowerCase()) ||
          reservation.user.userId.toString().includes(val)
          //reservation.user.number.includes(val)
        );
      } else {
        this.reservations = this.originalReservations;
      }
    }
   }, 
    methods: {
      isReservToday(requestDate) {
          const today = new Date();
          const requestDateObj = new Date(requestDate);
         return (
           today.getFullYear() === requestDateObj.getFullYear() &&
           today.getMonth() === requestDateObj.getMonth() &&
           today.getDate() === requestDateObj.getDate()
         );
     },
      configure(){
        this.$router.push({name: 'reservConfig'});
      },
     async addReservation() {
        //this.newReservation.date = this.formatDate(this.newReservation.date);
        try{
          this.isLoading=true;
        await axios.post(`https://api.golfsprint.net/golfsprint/api/reservations/add/${this.newReservation.userId}`,this.newReservation,{
            headers:{
                Authorization: `Bearer ${VuexStore.state.token} `,
            }
         }).then(res => { this.isLoading=false;
           
            window.location.reload();
            
         });
        // this.$router.push({name: 'person'});
        }
        catch(error){
          this.isLoading=false;
            
            alert(error.response.data);
        }
      },
      async deleteReservation(reservationId) {
        try{
        if (confirm('Are you sure you want to delete this reservation?')) {
          this.isLoading=true;
         await axios.delete(`https://api.golfsprint.net/golfsprint/api/reservations/delete/${reservationId}`,{
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
         alert(error.response.data);
      }
      },
      async getReservations(){
      this.isLoading=true;
      await axios.get(`https://api.golfsprint.net/golfsprint/api/reservations/getAll`,{
         headers:{
           Authorization: `Bearer ${VuexStore.state.token} `,
          // 'Cache-Control': 'no-cache',
         }
         }).then(res => { this.isLoading=false;
           this.reservations = res.data;
           this.originalReservations = res.data;
           
       });
     },
     formatDate(date) {
    const [month, day, year] = date.split('-');
    return `${year}-${day}-${month}`;
    //return new Date(date);
    },
    }
  };
  </script>
  
  <style scoped>
   h1 {
  color: #333;
}

.list-group-item {
  background-color: #f9f9f9;
  margin-bottom: 10px;
  box-shadow: 10px 10px 10px rgba(0.2, 0.2, 0.2, 0.2);
}

.list-group-item button {
  margin-left: 10px;
}

.vue-cal {
    background-color: #e3dcca;
    color: #455936;
    height: auto; /* Set your desired height */
  }
.event{
  font-size: 14px;
}
  /* Adjust the height of each event in the calendar */
  </style>
  