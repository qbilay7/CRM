<template>
    <div v-if="!isLoading" class="container mt-5">
       <div style="box-shadow: 10px 10px 10px rgba(0.2, 0.2, 0.2, 0.2); background-color: #f8f3e7;" class="card">
        <div class="card-header">
            <h4>Edit Profile</h4>
        </div>
        <div class="card-body">
            <div class="mb-3">
                <label for="">Full Name</label>
                <input type="text" v-model="model.company.companyName" class="form-control" />
            </div>
            <div class="mb-3">
                <label for="">Location</label>
                <input type="text"  v-model="model.company.companyLocation" class="form-control" />
            </div>
            <div class="mb-3">
                <label for="">Profile Image</label>
                <input type="file" id="image" class="form-control" ref="imageInput"  @change="handleImageChange" accept="image/*">
            </div>
            <div class="mb-3">
                <label for="">Phone Number</label>
                <input type="text"  v-model="model.company.companyNumber" @input="formatPhoneNumber" placeholder="ex: +71234567811" class="form-control" />
            </div>
            <div class="mb-3">
                <button style="background-color: #36462a; border: #36462a;" type="button" @click="editProfile" class="btn btn-primary">Update</button>
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
    name: 'updateCompany',
    data(){
       return {
       // companyId: null,
       selectedImage: null,
         model: {
            company: {
                companyId: null,
                companyName: '',
                companyNumber: '',
                companyLocation: '',
                companyImage: null,
            },
         },
         isLoading: false,
       }
    },
    mounted(){
       this.getCompany();
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
    handleImageChange(event) {
        this.selectedImage = event.target.files[0];
    },
      /*handleImageChange(event) {
      
      const file = event.target.files[0];
      
      const reader = new FileReader();
      reader.onloadend = () => {
    
        const base64String = reader.result.split(',')[1];

    
       // const cleanBase64String = base64String.replace(/[\r\n]/g, '');

        this.model.company.companyImage = base64String;
      };
      reader.readAsDataURL(file);

    }, */
      async getCompany(){
        this.isLoading=true,
          await axios.get(`https://api.golfsprint.net/golfsprint/api/club_profile/get`,{
            headers:{
                Authorization: `Bearer ${VuexStore.state.token} `,
            }
          }).then(res => { this.isLoading=false;
            
            this.model.company.companyId=res.data.companyId
            this.model.company.companyName=res.data.companyName
            this.model.company.companyNumber=res.data.companyNumber
            this.model.company.companyLocation=res.data.companyLocation
            this.model.company.companyImage=res.data.companyImage
          });
       },
       async editProfile() {
            try{
                this.isLoading=true;
                const imageFileName = this.model.company.companyImage ? this.model.company.companyImage.split('/').pop() : null;
            if (imageFileName) {
               await this.deleteImageFromSupabase(imageFileName);
            }
            const imageUrl = await this.uploadImageToSupabase();
           this.model.company.companyImage = imageUrl;
                await axios.put(`https://api.golfsprint.net/golfsprint/api/club_profile/update/${this.model.company.companyId}`,this.model.company,{
            headers:{
                Authorization: `Bearer ${VuexStore.state.token} `,
            }
         }).then(res => { this.isLoading=false;
            
            this.$router.push({name: 'club_profile'});
         });
        }
        catch(error){ this.isLoading=false;
            alert(error.response.data);
        }    
        },
    back(){
        this.$router.push({name: 'club_profile'});
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