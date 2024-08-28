<template>
  <!--
  <div style="background-color: #f8f3e7;">
    <div v-if="!isLoading" id="event-page" class="container">
      <h1 class="mb-4">Events</h1>
      <form @submit.prevent="addEvent" class="mb-4">
        <div class="row g-3">
          <div class="col-sm-4">
            <label for="name" class="form-label">Event Title:</label>
            <input type="text" id="name" class="form-control" v-model="newEvent.eventName" >
          </div>
          <div class="col-sm-4">
            <label for="text" class="form-label">Description:</label>
            <input type="text" id="text" class="form-control" v-model="newEvent.eventDescription" required>
          </div>
          <div class="col-sm-4">
            <label for="eventDate" class="form-label">Event Date:</label>
            <input type="datetime-local" id="eventDate" class="form-control" v-model="newEvent.eventDate" required>
          </div>
          <div class="col-sm-4">
            <label for="image" class="form-label">Image:</label>
            <input type="file" id="image" class="form-control" ref="imageInput"  @change="handleImageChange" accept="image/*">
          </div>
          <div class="col-sm-4 align-self-end">
            <button style="background-color: #455936; border: #455936;" type="submit" class="btn btn-primary">Add Event</button>
          </div>
        </div>
      </form>
      
      <div class="input-group">
            <input type="text" class="form-control" placeholder="Search..." v-model="searchQuery">
     
          </div>
      <div v-if="events.length > 0">
      <div v-for="event in events" :key="event.eventId" class="card mb-3">
        <div class="row g-0">
          <div class="col-md-8">
            <div class="card-body">
              <h5 class="card-title">{{ event.eventName }}</h5>
              <p class="card-text">{{ event.eventDescription }}</p>
              <p class="card-text"><strong>Date:</strong> {{ event.eventDate }}</p>
              <button @click="deleteEvent(event.eventId)" class="btn btn-danger btn-sm">Delete</button>
              <RouterLink style="background-color: #455936; border: #455936; margin-left: 10px;" :to="{path:'/update/'+event.eventId+'/event'}" class="btn btn-primary">Update</RouterLink>
            </div>
          </div>
          <div class="col-md-4">
            <img v-if="event.eventImage" :src="'data:image/jpeg;base64,' + event.eventImage"  class="img-fluid">
          </div>
        </div>
      </div>
    </div>
      <div v-else>
        No upcoming events.
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
  const email = localStorage.getItem('companyEmail')
  export default {
    name:'event',
    data() {
      return {
        events: [],
        newEvent:{
            eventName: '',
            eventDescription: '',
            eventDate: '',
            eventImage: null
        },
        isLoading: false,
        searchQuery: '',
        originalEvents: [],
      };
    },
    mounted(){
      this.getEvents();
    },
    methods:{
        async getEvents(){
          this.isLoading=true;
      await axios.get(`https://api.golfsprint.net/golfsprint/api/events/getAll/${email}`,{
         headers:{
           Authorization: `Bearer ${VuexStore.state.token} `,
          // 'Cache-Control': 'no-cache',
         }
         }).then(res => { this.isLoading=false;
           this.events = res.data;
           this.originalEvents = res.data;
           console.log(this.events);
       });
      },
     async addEvent(){
        try{
         // this.newEvent.eventDate=`${this.newEvent.eventDate}:00`;
          this.newEvent.eventDate=this.formatEventDate(this.newEvent.eventDate);
          this.isLoading=true;
         await axios.post(`https://api.golfsprint.net/golfsprint/api/events/add/${email}`,this.newEvent,{
            headers:{
                Authorization: `Bearer ${VuexStore.state.token} `,
            }
         }).then(res => { this.isLoading=false;
            console.log(res.data);
            window.location.reload();
            
         });
        // this.$router.push({name: 'person'});
        }
        catch(error){ this.isLoading=false;
           console.log(error);
           alert(error.response.data);
        }
      },
      deleteEvent(eventId){
        if (confirm('Are you sure you want to delete this event?')) {
          axios.delete(`https://api.golfsprint.net/golfsprint/api/events/delete/${eventId}`,{
        headers:{
          Authorization: `Bearer ${VuexStore.state.token} `,
         // 'Cache-Control': 'no-cache',
        }
      }).then(res => {
        console.log(res)
        window.location.reload();
      })
        }
      },
      handleImageChange(event) {
      
      const file = event.target.files[0];
      
      const reader = new FileReader();
      reader.onloadend = () => {
    
        const base64String = reader.result.split(',')[1];

    
       // const cleanBase64String = base64String.replace(/[\r\n]/g, '');

        this.newEvent.eventImage = base64String;
      };
      reader.readAsDataURL(file);

    },
    formatEventDate(rawDate) {
      // Format the raw date string to "dd-MM-yyyy HH:mm"
      const date = new Date(rawDate);
      const formattedDate = date.toLocaleDateString('en-GB', {
        day: '2-digit',
        month: '2-digit',
        year: 'numeric',
        hour: '2-digit',
        minute: '2-digit',
        hour12: false,
      });

      return formattedDate;
    }

    },
    components: {Loading},
    watch: {
    searchQuery: function(val) {
      if (val) {
        this.events = this.originalEvents.filter(event =>
          event.eventName.toLowerCase().includes(val.toLowerCase()) ||
          event.eventDate.includes(val)
        );
      } else {
        this.events = this.originalEvents;
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
    border-radius: 5px;
    margin-bottom: 20px;
  }
  
  .card-title {
    font-size: 1.5em;
  }
  
  .card-text {
    color: #333;
  }
  */
  </style>
  