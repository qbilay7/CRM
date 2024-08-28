<template>
  <div v-if="!isLoading" style="background-color: #f8f3e7; display: flex; min-height: 100vh;">
    <div class="sidebar">
      <div class="sidebar-avatar">
        <img v-if="companyImage" :src=" companyImage" alt="Profile Picture">
      </div>
      <h2>{{ companyName }}</h2>
      <ul>
        <li><router-link :to="{path:'/user/club'}">Instructor</router-link></li>
        <li><router-link :to="{path:'/user/reservation'}">Reservation</router-link></li>
        <li><router-link :to="{path:'/user/course'}">Courses</router-link></li>
      </ul>
    </div>
    <div style="background-color: #f8f3e7;" id="app" class="container">
      <h1 class="mb-4">Your Reservation</h1>
      <div v-show="showVueCal" class="vue-cal" style="box-shadow: 10px 10px 10px rgba(0.2, 0.2, 0.2, 0.2);">
        <vue-cal class="event" :events="formattedEvents" />
      </div>
      <ul  class="list-group">
        <li style="box-shadow: 10px 10px 10px rgba(0.2, 0.2, 0.2, 0.2);" class="list-group-item">
          <div v-if="reservation.date != null" class="d-flex justify-content-between align-items-center">
            <div class="d-flex flex-column align-items-start">
              <span><strong class="tit">Your Reservation</strong></span>
              <span><strong>Date(day/month/year):</strong> {{ formatDate(reservation.date) }}</span>
              <span><strong>Start Time:</strong> {{ reservation.start }}</span>
              <span><strong>Finish Time:</strong> {{ reservation.finish }}</span>
              <span><strong>Court Name:</strong> {{ reservation.courtName }}</span>
              <span><strong>Instructor:</strong> {{ reservation.company.companyName }}</span>
              <span><strong>Reservation Id:</strong> {{ reservation.reservationId }}</span>
            </div>
          </div>
          <div v-else>You don't have a reservation.</div>
        </li>
      </ul>
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
      reservation: {
        date: null,
        start: null,
        finish: null,
        courtName: '',
        reservationId: null,
        company: null
      },
      newReservation: {
        reservationId: null,
        courtName: '',
        date: '',
        start: '',
        finish: '',
        userId: ''
      },
      reservations: [],
      companyId: null,
      companyImage: null,
      companyName: '',
      companyEmail: '',
      isLoading: false,
      searchQuery: '',
      showVueCal: true,  // New property to control Vue Cal visibility
    };
  },
  components: {
    VueCal,
    Loading
  },
  computed: {
    formattedEvents() {
      const events = this.reservations.map(reservation => ({
        title: "Taken.",
        start: new Date(`${reservation.date} ${reservation.start}`),
        end: new Date(`${reservation.date} ${reservation.finish}`),
      }));
      return events;
    }
  },
  mounted() {
    this.checkWindowSize();
    window.addEventListener('resize', this.checkWindowSize);
    this.getCompany();
    this.getUserId();
    this.getReservations();
    this.getReservation();
  },
  beforeDestroy() {
    window.removeEventListener('resize', this.checkWindowSize);
  },
  methods: {
    async getUserId() {
      try {
        
        await axios.get(`https://api.golfsprint.net/golfsprint/api/users/getBy`, {
          headers: {
            Authorization: `Bearer ${VuexStore.state.token} `,
          }
        }).then(res => {
          
          this.newReservation.userId = res.data.userId;
        });
      } catch (error) {
        this.isLoading = false;
        alert(error.data.message);
      }
    },
    async getReservation() {
      try {
        
        await axios.get(`https://api.golfsprint.net/golfsprint/api/reservations/get`, {
          headers: {
            Authorization: `Bearer ${VuexStore.state.token} `,
          }
        }).then(res => {
          this.isLoading = false;
          this.reservation.date = res.data.date;
          this.reservation.start = res.data.start;
          this.reservation.finish = res.data.finish;
          this.reservation.courtName = res.data.courtName;
          this.reservation.reservationId = res.data.reservationId;
          this.reservation.company = res.data.company;
        });
      } catch (error) {
        this.isLoading = false;
        alert(error.data.message);
      }
    },
    async getCompany() {
      try {
        this.isLoading = true;
        await axios.get(`https://api.golfsprint.net/golfsprint/api/club_profile/getByUser`, {
          headers: {
            Authorization: `Bearer ${VuexStore.state.token} `,
          }
        }).then(res => {
          
          this.companyId = res.data.companyId;
          this.companyName = res.data.companyName;
          this.companyImage = res.data.companyImage;
          this.companyEmail = res.data.companyEmail;
        });
      } catch (error) {
        this.isLoading = false;
        alert(error.data.message);
      }
    },
    async getReservations() {
      
      await axios.get(`https://api.golfsprint.net/golfsprint/api/reservations/getAllByUser`, {
        headers: {
          Authorization: `Bearer ${VuexStore.state.token} `,
        }
      }).then(res => {
        
        this.reservations = res.data;
      });
    },
    formatDate(date) {
      const [month, day, year] = date.split('-');
      return `${year}-${day}-${month}`;
    },
    checkWindowSize() {
      this.showVueCal = window.innerWidth > 425;
    }
  }
};
</script>

<style scoped>
.tit{
  color: #455936; 
  font-size: 24px;
}
h1 {
  color: #333;
}

.list-group-item {
  background-color: #f9f9f9;
  margin-bottom: 10px;
}

.list-group-item button {
  margin-left: 10px;
}

.vue-cal {
  background-color: #e3dcca;
  color: #455936;
  height: auto;
}

.event {
  font-size: 14px;
}

/* Sidebar styles */
.sidebar {
  background-color: #37472c;
  color: #ffffff;
  padding: 20px;
  width: 250px;
}

.sidebar h2 {
  margin-bottom: 20px;
  color: #ffffff;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
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

@media (max-width: 430px) {
  h1 {
    color: #333;
    font-size: 25px;
  }

  .sidebar {
    background-color: #37472c;
    color: #ffffff;
    padding: 6px;
    width: 120px;
    flex-direction: column;
  }

  .sidebar-avatar img {
    width: 100px;
    height: auto;
    border-radius: 50%;
  }

  .sidebar h2 {
    font-size: 20px;
    margin-bottom: 20px;
    color: #ffffff;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
  }
}

@media (max-width: 380px) {
  h1 {
    color: #333;
    font-size: 25px;
  }

  .sidebar {
    background-color: #37472c;
    color: #ffffff;
    padding: 6px;
    width: 120px;
    flex-direction: column;
  }

  .sidebar-avatar img {
    width: 90px;
    height: auto;
    border-radius: 50%;
  }

  .sidebar h2 {
    font-size: 18px;
    margin-bottom: 20px;
    color: #ffffff;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
  }
}
@media (max-width: 321px) {
  h1 {
    color: #333;
    font-size: 20px;
  }

  .sidebar {
    background-color: #37472c;
    color: #ffffff;
    padding: 5px;
    width: 110px;
    flex-direction: column;
  }

  .sidebar-avatar img {
    width: 80px;
    height: auto;
    border-radius: 50%;
  }

  .sidebar h2 {
    font-size: 18px;
    margin-bottom: 20px;
    color: #ffffff;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
  }
  .tit{
  color: #455936; 
  font-size: 20px;
}
}
</style>
