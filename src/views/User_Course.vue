<template>
  <div v-if="!isLoading" style="background-color: #f8f3e7; display: flex; min-height: 100vh;">

      <!-- Sidebar -->
      <div class="sidebar">
        <div class="sidebar-avatar">
          <img v-if="companyImage" :src=" companyImage" alt="Profile Picture">
        </div>
        <h2>{{ companyName }}</h2>
        <ul>
          <li><router-link :to="{path:'/user/club'}">Instructor</router-link></li>
          <li><router-link :to="{path:'/user/reservation'}">Reservation</router-link></li>
          <li><router-link :to="{path:'/user/course'}">Courses</router-link></li>
         <!-- <li><router-link :to="{path:'/user/'+companyId+'/event'}">Events</router-link></li>
          <li><router-link :to="{path:'/user/'+companyId+'/shop'}">Pro-Shop</router-link></li> -->
        </ul>
      </div>
      <!-- Main content -->
      <div class="container">
        <h1>Golf Course Packages</h1>
        <div style="box-shadow: 10px 10px 10px rgba(0.2, 0.2, 0.2, 0.2);"class="input-group">
          <input type="text" class="form-control" placeholder="Search..." v-model="searchQuery">
        </div>
        <div v-for="course in courses" :key="course.courseId" class="package">
          <div class="package-content">
            <h2>{{ course.courseName }}</h2>
            <p><strong>Instructor:</strong> {{ course.company.companyName }}</p>
            <p><strong>Instructor's Number:</strong> {{ course.company.companyNumber }}</p>
            <div class="description-container">
             <p><strong>Description:</strong> {{ course.courseDescription }}</p>
            </div>
            <p><strong>Price:</strong> {{ course.price }} {{ course.currency }} per hour</p>
            <button style="box-shadow: 8px 8px 8px rgba(0.1, 0.1, 0.1, 0.1); background-color: #455936; border: #455936; margin-left: 10px;" @click="makeRequest(course.courseId)" class="btn btn-primary">Make Request</button>
          </div>
          <div class="package-image">
            <img v-if="course.courseImage" :src="course.courseImage" class="img-thumbnail">
          </div>
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
      name: 'clubCourse',
      data() {
        return {
          companyId: null,
          courses: [],
          companyImage: null,
          companyName: '',
          isLoading: false,
          searchQuery: '',
          originalCourses: [],
        };
      },
      mounted(){
        this.getCompany();
        this.getCourses();
      },
      methods:{
        async makeRequest(courseId){
          try{
          this.isLoading=true;
        await axios.post(`https://api.golfsprint.net/golfsprint/api/request_course/add/${courseId}`,null,{
            headers:{
                Authorization: `Bearer ${VuexStore.state.token} `,
            }
         }).then(res => { this.isLoading=false;
            alert("Your request is sent successfully")
            
            window.location.reload();
            
         });
        }
        catch(error){
          this.isLoading=false;
         
            alert(error.response.data);
        }
        },
        async getCompany(){
       try{
        this.isLoading=true;
         await axios.get(`https://api.golfsprint.net/golfsprint/api/club_profile/getByUser`,{
            headers:{
                Authorization: `Bearer ${VuexStore.state.token} `,
            }
         }).then(res => { 
            
            this.companyId=res.data.companyId;
            this.companyName=res.data.companyName;
            this.companyImage=res.data.companyImage;
         });
       }
       catch(error){
        this.isLoading=false;
        
        alert(error.data.message);
       }
    },
       async getCourses(){
        
        await axios.get(`https://api.golfsprint.net/golfsprint/api/club/course/getAllByUser`,{
           headers:{
             Authorization: `Bearer ${VuexStore.state.token} `,
            // 'Cache-Control': 'no-cache',
           }
           }).then(res => { this.isLoading=false;
             this.courses = res.data;
             this.originalCourses = res.data;
             
         });
        },
      },
      components: {Loading},
      watch: {
      searchQuery: function(val) {
        if (val) {
          this.courses = this.originalCourses.filter(course =>
            course.courseName.toLowerCase().includes(val.toLowerCase()) ||
            course.price.toString().includes(val)
            
          );
        } else {
          this.courses = this.originalCourses;
        }
      }
     }
    };
    </script>
    
    <style scoped>
    .container {
    width: 80%;
    margin: 0 auto;
    padding: 30px;
  }
  
  h1 {
    text-align: center;
    margin-bottom: 30px;
  }
  
  .package {
    display: flex;
    background-color: white;
    border: 1px solid #ccc;
    padding: 10px;
    margin-bottom: 20px;
    box-shadow: 10px 10px 10px rgba(0.2, 0.2, 0.2, 0.2);
  }
  
  .package-content {
    flex-grow: 1;
  }
  
  .package h2 {
    font-size: 24px;
    margin-top: 0;
    margin-bottom: 10px;
  }
  
  .package p {
    margin: 5px 0;
  }
  
  .package strong {
    font-weight: bold;
  }
  
  .package-image {
    margin-left: 10px; /* Adjust margin between content and image */
  }
  
  .img-thumbnail {
    max-width: 250px; /* Adjust the maximum width of the image */
    max-height: 250px; /* Adjust the maximum height of the image */
    border-radius: 50%;
  }
  /* Sidebar styles */
.sidebar {
  background-color: #37472c;
  color: #ffffff;
  padding: 20px;
  width: 250px;
  flex-direction: column;
}

.sidebar h2 {
  margin-bottom: 20px;
  color: #ffffff;
  white-space: nowrap; /* Prevent text from wrapping */
  overflow: hidden; /* Hide overflowed text */
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
  .description-container {
    width: 90%; /* Adjust the percentage as needed */
   max-width: 600px;
  word-wrap: break-word;
}
@media (max-width: 1026px) {
  .description-container {
  width: 90%; /* Adjust the percentage as needed */
  max-width: 490px;
  word-wrap: break-word;
}
}
@media (max-width: 770px) {
  .description-container {
  width: 90%; /* Adjust the percentage as needed */
  max-width: 300px;
  word-wrap: break-word;
}
.img-thumbnail {
    max-width: 200px; /* Adjust the maximum width of the image */
    max-height: 200px; /* Adjust the maximum height of the image */
    border-radius: 50%;
  }
}

@media (max-width: 430px) {
  .container h1{
    font-size: 25px;
  }
  .package-image {
    margin-left: -35px; /* Adjust margin between content and image */
  }
  .description-container {
  width: 90%; /* Adjust the percentage as needed */
  max-width: 150px;
  word-wrap: break-word;
}
.img-thumbnail {
  max-width: 70px; /* Adjust the maximum width of the image */
  max-height: 70px; /* Adjust the maximum height of the image */
  border-radius: 50%;
  margin-top: 40px;
}
.sidebar {
  background-color: #37472c;
  color: #ffffff;
  padding: 10px;
  width: 115px;
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
  white-space: nowrap; /* Prevent text from wrapping */
  overflow: hidden; /* Hide overflowed text */
  text-overflow: ellipsis;
}

  .container {
    width: 80%;
    margin: 0 auto;
    padding: 35px;
    font-size: 12px;
  }
}

@media (max-width: 380px) {
  .container h1{
    font-size: 20px;
  }
  .description-container {
  width: 90%; /* Adjust the percentage as needed */
  max-width: 120px;
  word-wrap: break-word;
}
.img-thumbnail {
  max-width: 50px; /* Adjust the maximum width of the image */
  max-height: 50px; /* Adjust the maximum height of the image */
  border-radius: 50%;
}
.sidebar {
  background-color: #37472c;
  color: #ffffff;
  padding: 1px;
  width: 100px;
  flex-direction: column;
}
.sidebar-avatar img {
    width: 70px;
    height: auto;
    border-radius: 50%;
  }
  .sidebar h2 {
    font-size: 20px;
  margin-bottom: 20px;
  color: #ffffff;
  white-space: nowrap; /* Prevent text from wrapping */
  overflow: hidden; /* Hide overflowed text */
  text-overflow: ellipsis;
}
h1 {
    font-size: 30px;
    text-align: center;
    margin-bottom: 30px;
  }
  .container {
    width: 80%;
    margin: 0 auto;
    padding: 35px;
    font-size: 12px;
  }
}
@media (max-width: 321px) {
  .description-container {
  width: 90%; /* Adjust the percentage as needed */
  max-width: 100px;
  word-wrap: break-word;
}
.img-thumbnail {
  max-width: 50px; /* Adjust the maximum width of the image */
  max-height: 50px; /* Adjust the maximum height of the image */
  border-radius: 50%;
}
.sidebar {
  background-color: #37472c;
  color: #ffffff;
  padding: 1px;
  width: 85px;
  flex-direction: column;
}
.sidebar-avatar img {
    width: 60px;
    height: auto;
    border-radius: 50%;
  }
  .sidebar h2 {
    font-size: 18px;
  margin-bottom: 20px;
  color: #ffffff;
  white-space: nowrap; /* Prevent text from wrapping */
  overflow: hidden; /* Hide overflowed text */
  text-overflow: ellipsis;
}
h1 {
    font-size: 20px;
    text-align: center;
    margin-bottom: 30px;
  }
  .container {
    width: 80%;
    margin: 0 auto;
    padding: 22px;
    font-size: 12px;
  }
  .container h2{
    font-size: 20px;
  }
}
    </style>
    