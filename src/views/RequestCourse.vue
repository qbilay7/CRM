<template>
    <div v-if="!isLoading" style="background-color: #f8f3e7;">
      <div id="app" class="container">
        <h1 class="mb-4">Course Requests</h1>
        <button style="background-color: #617d4b; border: #617d4b;" type="button" @click="back" class="btn btn-danger">Back</button>
       <div style="box-shadow: 10px 10px 10px rgba(0.2, 0.2, 0.2, 0.2);"class="input-group">
              <input type="text" class="form-control" placeholder="Search..." v-model="searchQuery">
       <!-- <button class="btn btn-outline-secondary" type="button" @click="searchUser">Search</button> -->
            </div>
      <ul v-if="courseRequests.length > 0" class="list-group">
          <li style="box-shadow: 10px 10px 10px rgba(0.2, 0.2, 0.2, 0.2);" class="list-group-item" v-for="courseRequest in courseRequests" :key="courseRequest.courseRequestId">
    <div class="d-flex justify-content-between align-items-center">
      <div class="d-flex flex-column align-items-start">
        <span><strong>Customer:</strong> {{ courseRequest.user.name }} {{ courseRequest.user.surname }} ,{{ courseRequest.user.email }} ,{{ courseRequest.user.number }}</span>
        <span><strong>Request Date(day/month/year):</strong> {{ formatDate(courseRequest.requestDate) }}</span>
        <span><strong>Course Id:</strong> {{ courseRequest.course.courseId }}</span>
        <span><strong>Course Name:</strong> {{ courseRequest.course.courseName }}</span>
       <!-- <span><strong>Request Id:</strong> {{ courseRequest.courseRequestId }}</span> -->
       <span v-if="isNewRequest(courseRequest.requestDate)"><strong style="color: red;">Today</strong></span>
      </div>
      <button @click="deleteRequest(courseRequest.courseRequestId)" class="btn btn-danger btn-sm">Delete</button>
    </div>
    
  </li>
  </ul>
  <div v-else>
        No request found.
      </div> 
      </div>
      </div>
      <div v-if="isLoading"><Loading></Loading></div>
    </template>
    
    <script>
    import axios from 'axios';
    import VuexStore from '../store/VuexStore';
    import Loading from './Loading.vue';
    
    export default {
      name: 'requestCourse',
      data() {
        return {
          courseRequests: [],
          isLoading: false,
          searchQuery: '',
          originalRequests: [],
        };
      },
      components:{
        Loading
      }, 
      mounted(){
       this.getRequests();
      },
      watch: {
      searchQuery: function(val) {
        if (val) {
          this.courseRequests = this.originalRequests.filter(courseRequest =>
            courseRequest.course.courseName.toLowerCase().includes(val.toLowerCase()) ||
            courseRequest.requestDate.includes(val) ||
            courseRequest.user.name.toLowerCase().includes(val.toLowerCase()) ||
            courseRequest.user.surname.toLowerCase().includes(val.toLowerCase()) ||
            courseRequest.user.email.toLowerCase().includes(val.toLowerCase()) ||
            courseRequest.course.courseId.toString().includes(val)
          );
        } else {
          this.courseRequests = this.originalRequests;
        }
      }
     }, 
      methods: {
        isNewRequest(requestDate) {
          const today = new Date();
          const requestDateObj = new Date(requestDate);
         return (
           today.getFullYear() === requestDateObj.getFullYear() &&
           today.getMonth() === requestDateObj.getMonth() &&
           today.getDate() === requestDateObj.getDate()
         );
     },
        back(){
        this.$router.push({name: 'course'});
       },
        async deleteRequest(courseRequestId) {
          try{
          if (confirm('Are you sure you want to delete this request?')) {
            this.isLoading=true;
           await axios.delete(`https://api.golfsprint.net/golfsprint/api/request_course/delete/${courseRequestId}`,{
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
        async getRequests(){
        this.isLoading=true;
        await axios.get(`https://api.golfsprint.net/golfsprint/api/request_course/getAll`,{
           headers:{
             Authorization: `Bearer ${VuexStore.state.token} `,
            // 'Cache-Control': 'no-cache',
           }
           }).then(res => { this.isLoading=false;
             this.courseRequests = res.data;
             this.originalRequests = res.data;
             
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
  }
  
  .list-group-item button {
    margin-left: 10px;
  }
  
  .event{
    font-size: 14px;
  }
  .container {
  position: relative;
  background-color: #f8f3e7;
  padding-bottom: 55%;
}

    </style>
    