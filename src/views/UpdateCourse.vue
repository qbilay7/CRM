<template>
    <div v-if="!isLoading" class="container mt-5">
       <div style="box-shadow: 10px 10px 10px rgba(0.2, 0.2, 0.2, 0.2); background-color: #f8f3e7;" class="card">
        <div class="card-header">
            <h4>Edit Course</h4>
        </div>
        <div class="card-body">
            <div class="mb-3">
                <label for="">Course Name</label>
                <input type="text" v-model="model.course.courseName" class="form-control" />
            </div>
            <div class="mb-3">
                <label for="">Description</label>
                <textarea type="text"  v-model="model.course.courseDescription" class="form-control" ></textarea>
            </div>
            <div class="mb-3">
                <label for="">Image</label>
                <input type="file" id="image" class="form-control" ref="imageInput"  @change="handleImageChange" accept="image/*">
            </div>
            <div class="mb-3">
                <label for="">Price per Hour</label>
                <input type="number"  v-model="model.course.price" class="form-control" />
            </div>
            <div class="mb-3">
                <label for="">Currency Type</label>
                <div class="currency-input">
              <input type="text" id="currencyType" v-model="model.course.currency" @click="toggleCurrencyOptions" readonly>
              <div class="currency-options" v-show="showCurrencyOptions">
                <div class="currency-option" v-for="(currency, index) in currencyOptions" :key="index" @click="selectCurrency(currency)">
                  {{ currency }}
                </div>
              </div>
            </div>
            </div>
            <div class="mb-3">
                <button style="background-color: #36462a; border: #36462a;" type="button" @click="updateCourse" class="btn btn-primary">Update</button>
            </div>
        </div>
       </div>
       <button style="background-color: #617d4b; border: #617d4b;" type="button" @click="back" class="btn btn-danger">Back</button>
    </div>
    <div v-if="isLoading"><Loading></Loading></div>
</template>
<script>
import axios from 'axios';
import VuexStore from '../store/VuexStore';
import Loading from './Loading.vue';
import { S3Client,  PutObjectCommand, DeleteObjectCommand } from '@aws-sdk/client-s3';
import { v4 as uuidv4 } from 'uuid';

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
    name: 'updateProduct',
    data(){
       return {
        courseId: null,
        selectedImage: null,
         model: {
            course:{
               courseName: '',
               courseDescription: '',
               courseImage: null,
               price: null,
               currency: '',
        }
         },
         isLoading: false,
         currencyOptions: ['$', '£', 'TL','€'],
        showCurrencyOptions: false,
       }
    },
    mounted(){
       this.courseId=this.$route.params.courseId;
       this.getCourse(this.courseId);
    },
    methods: {
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
        toggleCurrencyOptions() {
      this.showCurrencyOptions = !this.showCurrencyOptions;
      },
      selectCurrency(currency) {
        this.model.course.currency = currency;
        this.showCurrencyOptions = false; // Hide the currency options after selection
      },
      handleImageChange(event) {
        this.selectedImage = event.target.files[0];
    },
      /*handleImageChange(event) {
      
      const file = event.target.files[0];
      
      const reader = new FileReader();
      reader.onloadend = () => {
    
        const base64String = reader.result.split(',')[1];

    
       // const cleanBase64String = base64String.replace(/[\r\n]/g, '');

        this.model.course.courseImage = base64String;
      };
      reader.readAsDataURL(file);

    }, */
      async getCourse(courseId){
        this.isLoading=true;
         await axios.get(`https://api.golfsprint.net/golfsprint/api/club/course/get/${courseId}`,{
            headers:{
                Authorization: `Bearer ${VuexStore.state.token} `,
            }
          }).then(res => { this.isLoading=false;
            
            this.model.course.courseName=res.data.courseName
            this.model.course.courseDescription=res.data.courseDescription
            this.model.course.price=res.data.price
            this.model.course.currency=res.data.currency
            this.model.course.courseImage=res.data.courseImage
          });
       },
      async updateCourse(){
        try{
            this.isLoading=true;
            const imageFileName = this.model.course.courseImage ? this.model.course.courseImage.split('/').pop() : null;
            if (imageFileName) {
               await this.deleteImageFromSupabase(imageFileName);
            }
            const imageUrl = await this.uploadImageToSupabase();
           this.model.course.courseImage = imageUrl;
        await axios.put(`https://api.golfsprint.net/golfsprint/api/club/course/update/${this.courseId}`,this.model.course,{
            headers:{
                Authorization: `Bearer ${VuexStore.state.token} `,
            },
          
         }).then(res => { this.isLoading=false;
            
            this.$router.push({ name: 'course' });
         });
        
        }
        catch(error){ this.isLoading=false;
            
            alert(error.response.data);
        }
    },
    back(){
        this.$router.push({name: 'course'});
    }
    },
    components: {Loading}
}
</script>
<style scoped>
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
.currency-input {
  position: relative;
}
</style>