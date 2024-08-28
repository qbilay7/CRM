<template>
  <!--
  <div v-if="!isLoading" style="background-color: #f8f3e7; display: flex; min-height: 100vh; ">
    
      
      <div class="sidebar">
        <div class="sidebar-avatar">
          <img v-if="companyImage" :src="'data:image/jpeg;base64,' + companyImage" alt="Profile Picture">
        </div>
        <h2>{{ companyName }}</h2>
        <ul>
          <li><router-link :to="{path:'/user/'+companyId+'/club'}">Club</router-link></li>
          <li><router-link :to="{path:'/user/'+companyId+'/reservation'}">Reservation</router-link></li>
          <li><router-link :to="{path:'/user/'+companyId+'/course'}">Courses</router-link></li>
          <li><router-link :to="{path:'/user/'+companyId+'/event'}">Events</router-link></li>
          <li><router-link :to="{path:'/user/'+companyId+'/shop'}">Pro-Shop</router-link></li>
        </ul>
      </div>
      
      <div id="event-page" class="container">
        <h1 class="mb-4">Events</h1>
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
                </div>
              </div>
              <div class="col-md-4">
                <img v-if="event.eventImage" :src="'data:image/jpeg;base64,' + event.eventImage" class="img-fluid">
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
    
    export default {
      name:'clubEvent',
      data() {
        return {
          events: [],
          companyId: null,
          companyImage: null,
          companyName: '',
          isLoading: false,
          searchQuery: '',
          originalEvents: [],
        };
      },
      mounted(){
        this.companyId=this.$route.params.companyId;
        this.getCompany(this.companyId);
        this.getEvents(this.companyId);
      },
      methods:{
        async getCompany(companyId){
       try{
        this.isLoading=true;
         await axios.get(`https://api.golfsprint.net/golfsprint/api/club_profile/getById/${companyId}`,{
            headers:{
                Authorization: `Bearer ${VuexStore.state.token} `,
            }
         }).then(res => { this.isLoading=false;
            console.log(res.data);
            
            this.companyName=res.data.companyName;
            this.companyImage=res.data.companyImage;
         });
       }
       catch(error){
        this.isLoading=false;
        console.log(error.data);
        alert(error.data.message);
       }
    },
          async getEvents(companyId){
            this.isLoading=true;
        await axios.get(`https://api.golfsprint.net/golfsprint/api/events/getAllById/${companyId}`,{
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
      flex-grow: 1;
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
     
.sidebar {
  background-color: #37472c;
  color: #ffffff;
  padding: 20px;
  width: 250px;
}

.sidebar h2 {
  margin-bottom: 20px;
  color: #ffffff;
}

.sidebar ul {
  list-style-type: none;
  padding: 0;
}

.sidebar li {
  margin-bottom: 10px;
}

.sidebar a {
  color: #ffffff;
  text-decoration: none;
  transition: color 0.3s ease;
}

.sidebar a:hover {
  color: black;
}
.sidebar-avatar img {
    width: 100px;
    height: auto;
    border-radius: 50%;
  }
  */
    </style>
    