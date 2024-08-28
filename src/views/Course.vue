<template>
  <div v-if="!isLoading" class="allc">
    <div style="background-color: #f8f3e7;"  class="container">
      <h1>Golf Course Packages</h1>
      <button style=" background-color: #617d4b; border: #617d4b;" type="button" @click="request" class="btn btn-danger">Requests</button>
      <form @submit.prevent="addCourse" class="mb-4">
        <div class="row g-3">
          <div class="col-sm-4">
            <label for="text" class="form-label">Price per Hour:</label>
            <input style="box-shadow: 10px 10px 10px rgba(0.2, 0.2, 0.2, 0.2);" type="number" id="text" class="form-control" step="0.01" v-model="newCourse.price" required>
          </div>
          <div class="col-sm-4">
            <label for="name" class="form-label">Course Name:</label>
            <input style="box-shadow: 10px 10px 10px rgba(0.2, 0.2, 0.2, 0.2);"type="text" id="name" class="form-control" v-model="newCourse.courseName" required>
          </div>
          <div class="col-sm-4">
            <label for="image" class="form-label">Image:</label>
            <input style="box-shadow: 10px 10px 10px rgba(0.2, 0.2, 0.2, 0.2);" type="file" id="image" class="form-control" ref="imageInput" @change="handleImageChange" accept="image/*">
          </div>
          <div class="col-sm-4">
            <label for="currencyType">Currency Type:</label>
            <div class="currency-input">
              <input type="text" id="currencyType" v-model="newCourse.currency" @click="toggleCurrencyOptions" readonly>
              <div class="currency-options" v-show="showCurrencyOptions">
                <div class="currency-option" v-for="(currency, index) in currencyOptions" :key="index" @click="selectCurrency(currency)">
                  {{ currency }}
                </div>
              </div>
            </div>
          </div>
          <div class="col-sm-4">
            <label for="text" class="form-label">Description:</label>
            <textarea style="box-shadow: 10px 10px 10px rgba(0.2, 0.2, 0.2, 0.2);" type="text" id="text" class="form-control" v-model="newCourse.courseDescription" ></textarea>
          </div>
          <div class="col-sm-4 align-self-end">
            <button style="box-shadow: 10px 10px 10px rgba(0.2, 0.2, 0.2, 0.2); background-color: #455936; border: #455936;" type="submit" class="btn btn-primary">Add Course</button>
          </div>
        </div>
      </form>
      <div style="box-shadow: 10px 10px 10px rgba(0.2, 0.2, 0.2, 0.2);" class="input-group">
            <input type="text" class="form-control" placeholder="Search..." v-model="searchQuery">
     <!-- <button class="btn btn-outline-secondary" type="button" @click="searchUser">Search</button> -->
          </div>
      <div v-for="course in courses" :key="course.courseId" class="package">
        <div class="package-content">
          <h2>{{ course.courseName }}</h2>
          <p><strong>Instructor:</strong> {{ course.company.companyName }}</p>
          <p><strong>Instructor's Number:</strong> {{ course.company.companyNumber }}</p>
          <div class="description-container">
             <p><strong>Description:</strong> {{ course.courseDescription }}</p>
          </div>   <!--word-break: break-all;-->
          <p><strong>Price:</strong> {{ course.price }} {{ course.currency }} per hour</p>
          <p><strong>Course Id:</strong> {{ course.courseId }}</p>
          <button @click="deleteCourse(course.courseId)" class="btn btn-danger btn-sm">Delete</button>
          <RouterLink style="background-color: #455936; border: #455936; margin-left: 10px;" :to="{path:'/update/'+course.courseId+'/course'}" class="btn btn-primary btn-sm">Update</RouterLink>
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
  import { S3Client,  PutObjectCommand, DeleteObjectCommand } from '@aws-sdk/client-s3';
  import { v4 as uuidv4 } from 'uuid';
  
  //const supabase = createClient('https://lbgnrbsrxjvmzvmktvak.supabase.co', '');
  const s3Client = new S3Client({
  forcePathStyle: true,
  region: 'eu-central-1',
  endpoint: 'https://lbgnrbsrxjvmzvmktvak.supabase.co/storage/v1/s3',
  credentials: {
    accessKeyId: 'cfb76067a5d94b3c98c8f903b6dc0b92',
    secretAccessKey: '1e849258ffcf9accc3eea71bd62d37300617bbba26f4f1e0d8e3b7c3d86fdb61',
  }
})
  export default {
    name: 'course',
    data() {
      return {
        courses: [],
        newCourse:{
            courseName: '',
            courseDescription: '',
            courseImage: null,
            price: null,
            currency: '',
          },
          isLoading: false,
          searchQuery: '',
          originalCourses: [],
          currencyOptions: ['$', '£', 'TL','€'],
          showCurrencyOptions: false,
          selectedImage: null,
      };
    },
    mounted(){
      this.getCourses();
    },
    methods:{
      request(){
        this.$router.push({name: 'requestCourse'});
      },
      formatPhoneNumber(event) {
      const input = event.target;
      let phoneNumber = input.value;

      // Remove all non-numeric characters from the input
      phoneNumber = phoneNumber.replace(/\D/g, '');

      // Check if the input is empty
      if (!phoneNumber) {
        input.value = '';
        return;
      }


      // Apply the desired formatting
      let formatted = '+' + phoneNumber; /*.slice(0, 2);
      if (phoneNumber.length > 2) {
        formatted += ' ' + phoneNumber.slice(2, 5);
      }
      if (phoneNumber.length > 5) {
        formatted += ' ' + phoneNumber.slice(5, 8);
      }
      if (phoneNumber.length > 8) {
        formatted += ' ' + phoneNumber.slice(8, 10);
      }
      if (phoneNumber.length > 10) {
        formatted += ' ' + phoneNumber.slice(10);
      } */

      input.value = formatted;
    },
      toggleCurrencyOptions() {
      this.showCurrencyOptions = !this.showCurrencyOptions;
      },
      selectCurrency(currency) {
        this.newCourse.currency = currency;
        this.showCurrencyOptions = false; // Hide the currency options after selection
      },
     async getCourses(){
      this.isLoading=true;
      await axios.get(`https://api.golfsprint.net/golfsprint/api/club/course/getAll`,{
         headers:{
           Authorization: `Bearer ${VuexStore.state.token} `,
          // 'Cache-Control': 'no-cache',
         }
         }).then(res => { this.isLoading=false;
           this.courses = res.data;
           this.originalCourses = res.data;
           
       });
      },
      async addCourse(){
        try{
          this.isLoading=true;
          const imageUrl = await this.uploadImageToSupabase();
          this.newCourse.courseImage = imageUrl;
        await axios.post(`https://api.golfsprint.net/golfsprint/api/club/course/add`,this.newCourse,{
            headers:{
                Authorization: `Bearer ${VuexStore.state.token} `,
            },
            params:{
              image: this.image,
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
      async deleteImageFromSupabase(fileName) {
  const command = new DeleteObjectCommand({
    Bucket: 'images',
    Key: fileName,
  });

  try {
    await s3Client.send(command);
  } catch (err) {
    console.error('Error deleting image: ', err);
    throw new Error('Image deletion failed.');
  }
},
      async deleteCourse(courseId){
        try{
        if (confirm('Are you sure you want to delete this course?')) {
          this.isLoading=true;
          const courseToDelete = this.courses.find(course => course.courseId === courseId);
          const imageFileName = courseToDelete.courseImage.split('/').pop();
         await axios.delete(`https://api.golfsprint.net/golfsprint/api/club/course/delete/${courseId}`,{
        headers:{
          Authorization: `Bearer ${VuexStore.state.token} `,
         // 'Cache-Control': 'no-cache',
        }
      })
      if (imageFileName) {
        await this.deleteImageFromSupabase(imageFileName);
      } 
        this.isLoading=false;
        window.location.reload();
      
        }
      }catch(error){
         this.isLoading=false;
         alert(error.response.data);
      }
      },
      handleImageChange(event) {
        this.selectedImage = event.target.files[0];
    },
    async uploadImageToSupabase() {
      if (!this.selectedImage) return null;

      const fileName = `${uuidv4()}-${Date.now()}-${this.selectedImage.name}`;
      const command = new PutObjectCommand({
        Bucket: 'images',
        Key: fileName,
        Body: this.selectedImage,
        ContentType: this.selectedImage.type,
        ACL: 'public-read',
      });

      try {
        const data = await s3Client.send(command);
        return `https://lbgnrbsrxjvmzvmktvak.supabase.co/storage/v1/object/public/images/${fileName}`;
      } catch (err) {
        console.error('Error uploading image: ', err);
        throw new Error('Image upload failed.');
      }
    },
     /* handleImageChange(event) {
      
      const file = event.target.files[0];
      
      const reader = new FileReader();
      reader.onloadend = () => {
    
        const base64String = reader.result.split(',')[1];

    
       // const cleanBase64String = base64String.replace(/[\r\n]/g, '');

        this.newCourse.courseImage = base64String;
      };
      reader.readAsDataURL(file);

    } */
    },
    components: {Loading},
    watch: {
    searchQuery: function(val) {
      if (val) {
        this.courses = this.originalCourses.filter(course =>
          course.courseId.toString().includes(val) ||
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
  .allc{
    padding-bottom: 55%;
    background-color: #f8f3e7;
  }
  .container {
  width: 80%;
  margin: 0 auto;
  padding: 20px 0;
  
}

h1 {
  text-align: center;
  margin-bottom: 30px;
}

.package {
  display: flex;
  background-color: white;
  border: 1px solid #ccc;
  padding: 20px;
  margin-bottom: 20px;
  box-shadow: 10px 10px 10px rgba(0.2, 0.2, 0.2, 0.2);
}

.package-content {
  flex-grow: 1;
  flex-wrap: wrap;
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
.currency-options {
  position: absolute;
  background-color: #fff;
  border: 1px solid #ccc;
  max-height: 150px;
  overflow-y: auto;
  z-index: 1;
}

.currency-option {
  padding: 5px 10px;
  cursor: pointer;
}

.currency-option:hover {
  background-color: #f0f0f0;
}
.description-container {
  width: 90%; /* Adjust the percentage as needed */
  max-width: 700px;
  word-wrap: break-word;
}
@media (max-width: 1026px) {
  .description-container {
  width: 90%; /* Adjust the percentage as needed */
  max-width: 500px;
  word-wrap: break-word;
}
}
@media (max-width: 770px) {
  .description-container {
  width: 90%; /* Adjust the percentage as needed */
  max-width: 300px;
  word-wrap: break-word;
}
}
@media (max-width: 430px) {
  .description-container {
  width: 90%; /* Adjust the percentage as needed */
  max-width: 200px;
  word-wrap: break-word;
}
.img-thumbnail {
  max-width: 100px; /* Adjust the maximum width of the image */
  max-height: 100px; /* Adjust the maximum height of the image */
  border-radius: 50%;
}
}
@media (max-width: 380px) {
  .description-container {
  width: 90%; /* Adjust the percentage as needed */
  max-width: 150px;
  word-wrap: break-word;
}
.img-thumbnail {
  max-width: 100px; /* Adjust the maximum width of the image */
  max-height: 100px; /* Adjust the maximum height of the image */
  border-radius: 50%;
}
}
  </style>
  