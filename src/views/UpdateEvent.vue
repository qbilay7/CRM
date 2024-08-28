<template>
  <!--
    <div v-if="!isLoading" class="container mt-5">
       <div style="background-color: #f8f3e7;" class="card">
        <div class="card-header">
            <h4>Update Event</h4>
        </div>
        <div class="card-body">
            <div class="mb-3">
                <label for="">Event Title</label>
                <input type="text" v-model="model.event.eventName" class="form-control" />
            </div>
            <div class="mb-3">
                <label for="">Event Description</label>
                <input type="text"  v-model="model.event.eventDescription" class="form-control" />
            </div>
            <div class="mb-3">
                <label for="">Event Date</label>
                <input type="datetime-local"  v-model="model.event.eventDate" class="form-control" />
            </div>
            <div class="mb-3">
                <label for="">Image</label>
                <input type="file" id="image" class="form-control" ref="imageInput"  @change="handleImageChange" accept="image/*">
            </div>
            <div class="mb-3">
                <button style="background-color: #36462a; border: #36462a;" type="button" @click="updateEvent" class="btn btn-primary">Update</button>
            </div>
        </div>
       </div>
       <button style="background-color: #617d4b; border: #617d4b;" type="button" @click="back" class="btn btn-danger">Back</button>
    </div>
    <div v-if="isLoading"><Loading></Loading></div>
  -->
</template>
<script>
/*
import axios from 'axios';
import VuexStore from '../store/VuexStore';
import Loading from './Loading.vue';
const email =localStorage.getItem('companyEmail');
export default {
    name: 'updateEvent',
    data(){
       return {
        eventId: null,
         model: {
            event:{
          eventName: '',
          eventDescription: '',
          eventDate: null,
          eventImage: null,
        }
         },
         isLoading: false,
       }
    },
    mounted(){
       this.eventId=this.$route.params.eventId
       this.getEvent(this.eventId);
    },
    methods: {
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
    },
      handleImageChange(event) {
      
      const file = event.target.files[0];
      
      const reader = new FileReader();
      reader.onloadend = () => {
    
        const base64String = reader.result.split(',')[1];

    
       // const cleanBase64String = base64String.replace(/[\r\n]/g, '');

        this.model.event.eventImage = base64String;
      };
      reader.readAsDataURL(file);

    },
      async getEvent(eventId){
        this.isLoading=true;
         await axios.get(`https://api.golfsprint.net/golfsprint/api/events/get/${eventId}`,{
            headers:{
                Authorization: `Bearer ${VuexStore.state.token} `,
            }
          }).then(res => { this.isLoading=false;
            console.log(res.data);
            this.model.event.eventName=res.data.eventName
            this.model.event.eventDescription=res.data.eventDescription
            this.model.event.eventDate=res.data.eventDate
            this.model.event.eventImage=res.data.eventImage
            
          });
       },
      async updateEvent(){
        try{
            this.model.event.eventDate=this.formatEventDate(this.model.event.eventDate);
            this.isLoading=true;
        await axios.put(`https://api.golfsprint.net/golfsprint/api/events/update/${email}/${this.eventId}`,this.model.event,{
            headers:{
                Authorization: `Bearer ${VuexStore.state.token} `,
            }
         }).then(res => { this.isLoading=false;
            console.log(res.data)
            this.$router.push({ name: 'event' });
         });
        // this.$router.push({name: 'person'});
        }
        catch(error){ this.isLoading=false;
            console.log(error);
            alert(error.response.data);
        }
    },
    back(){
        this.$router.push({name: 'event'});
    }
    },
    components: {Loading}
}
*/
</script>
<style scoped>
/*
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
*/
</style>