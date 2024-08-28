<template>
    <div v-if="!isLoading" class="message-form">
      <h2>Send Email To {{ user.email }}</h2>
      <form @submit.prevent="sendMessage">
        <div class="form-group">
          <label for="subject">Subject:</label>
          <input type="text" id="subject" v-model="emailMessage.subject">
        </div>
        <div class="form-group">
          <label for="message">Message:</label>
          <textarea id="message" v-model="emailMessage.message" required></textarea>
        </div>
        <button type="submit">Send</button> <button style="margin-left: 400px;" @click="back">Back</button>
      </form>
      </div>
      <div v-if="isLoading"><Loading></Loading></div>
  </template>
  
  <script>
  import axios from 'axios';
  import VuexStore from '../store/VuexStore';
  import Loading from './Loading.vue';
  export default {
    name: 'sendMessage',
    data() {
      return {
        emailMessage:{
            subject: '',
            message: '',
        },
        userId: null,
        user: {
             email: '',
         },
         isLoading: false,
      };
    },
    mounted(){
       this.userId=this.$route.params.userId;
       this.getPersonData(this.userId);
    },
    methods: {
      async sendMessage() {
        try{
            this.isLoading=true;
        await axios.post(`https://api.golfsprint.net/golfsprint/api/users/send/email/to`,this.emailMessage,{
            headers:{
                Authorization: `Bearer ${VuexStore.state.token} `,
            },
            params:{
                userEmail: this.user.email
            }
         }).then(res => { this.isLoading=false;
            alert("Successfully sent.");
            this.$router.push({ name: 'customers' });
            
         });
        
        }
        catch(error){ this.isLoading=false;
            alert(error.response.data);
        }
      },
      back(){
        this.$router.push({name: 'customers'});
      },
      async getPersonData(userId){
        this.isLoading=true;
         await axios.get(`https://api.golfsprint.net/golfsprint/api/users/get/${userId}`,{
            headers:{
                Authorization: `Bearer ${VuexStore.state.token} `,
            }
          }).then(res => { this.isLoading=false;
            
            this.user.email=res.data.email
          });
       },
    },
    components: {Loading}
  };
  </script>
  
  <style scoped>
  .message-form {
    max-width: 600px;
    margin: 0 auto;
    padding: 20px;
    border: 1px solid #ccc;
    border-radius: 5px;
    background-color: #f8f3e7;
    box-shadow: 10px 10px 10px rgba(0.2, 0.2, 0.2, 0.2);
  }
  
  .form-group {
    margin-bottom: 20px;
  }
  
  label {
    display: block;
    font-weight: bold;
  }
  
  
  textarea {
    width: 100%;
    padding: 8px;
    font-size: 16px;
    border: 1px solid #ccc;
    border-radius: 5px;
  }
  
  button {
    background-color: #455936;
    color: #fff;
    border: none;
    border-radius: 5px;
    padding: 10px 20px;
    font-size: 16px;
    cursor: pointer;
  }
  
  button:hover {
    background-color: white;
    color: #455936;
  }
  </style>
  