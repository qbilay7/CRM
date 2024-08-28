<template>
    <div v-if="!isLoading" class="container mt-5">
       <div style="box-shadow: 10px 10px 10px rgba(0.2, 0.2, 0.2, 0.2); background-color: #f8f3e7;" class="card">
        <div class="card-header">
            <h4>Edit Profile</h4>
        </div>
        <div class="card-body">
            <div class="mb-3">
                <label for="">Name</label>
                <input type="text" v-model="model.user.name" class="form-control" />
            </div>
            <div class="mb-3">
                <label for="">Surname</label>
                <input type="text"  v-model="model.user.surname" class="form-control" />
            </div>
            <div class="mb-3">
                <label for="">Number</label>
                <input type="text"  v-model="model.user.number" @input="formatPhoneNumber" placeholder="+90 555 555 55 56" class="form-control" />
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

export default {
    name: 'updateUser',
    data(){
       return {
       // companyId: null,
         model: {
            user: {
                userId: null,
                name: '',
                surname: '',
                number: '',
            },
         },
         isLoading: false,
       }
    },
    mounted(){
       this.getUser();
    },
    methods: {
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
      async getUser(){
        this.isLoading=true,
          await axios.get(`https://api.golfsprint.net/golfsprint/api/users/getBy`,{
            headers:{
                Authorization: `Bearer ${VuexStore.state.token} `,
            }
          }).then(res => { this.isLoading=false;
            
            this.model.user.userId=res.data.userId
            this.model.user.name=res.data.name
            this.model.user.surname=res.data.surname
            this.model.user.number=res.data.number
          });
       },
       async editProfile() {
            try{
                this.isLoading=true;
                await axios.put(`https://api.golfsprint.net/golfsprint/api/users/edit`,this.model.user,{
            headers:{
                Authorization: `Bearer ${VuexStore.state.token} `,
            }
         }).then(res => { this.isLoading=false;
            
            this.$router.push({name: 'user_profile'});
         });
        }
        catch(error){ this.isLoading=false;
           
            alert(error.response.data.message);
        }    
        },
    back(){
        this.$router.push({name: 'user_profile'});
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