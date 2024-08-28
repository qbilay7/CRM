<template>
    <div v-if="!isLoading" class="container mt-5">
       <div style="box-shadow: 10px 10px 10px rgba(0.2, 0.2, 0.2, 0.2); background-color: #f8f3e7;" class="card">
        <div class="card-header">
            <h4>Edit Customer</h4>
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
                <label for="">Email</label>
                <input type="email"  v-model="model.user.email" class="form-control" />
            </div>
            <div class="mb-3">
                <label for="">Phone Number</label>
                <input type="tel"  v-model="model.user.number" @input="formatPhoneNumber" class="form-control" placeholder="ex: +901234567811" />
            </div>
            <div class="mb-3">
                <button style="background-color: #36462a; border: #36462a;" type="button" @click="updatePerson" class="btn btn-primary">Update</button>
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
    name: 'editCustomer',
    data(){
       return {
        userId: null,
         model: {
            user: {
                name: '',
                surname: '',
                email: '',
                number: '',
            }
         },
         isLoading: false,
       }
    },
    mounted(){
       this.userId=this.$route.params.userId;
       this.getPersonData(this.userId);
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
      async getPersonData(userId){
        this.isLoading=true;
         await axios.get(`https://api.golfsprint.net/golfsprint/api/users/get/${userId}`,{
            headers:{
                Authorization: `Bearer ${VuexStore.state.token} `,
            }
          }).then(res => { this.isLoading=false;
            
            this.model.user.name=res.data.name
            this.model.user.surname=res.data.surname
            this.model.user.email=res.data.email
            this.model.user.number=res.data.number
          });
       },
      async updatePerson(){
        try{
            this.isLoading=true;
            const encodedNumber = encodeURIComponent(this.model.user.number);
        await axios.put(`https://api.golfsprint.net/golfsprint/api/users/update/${this.userId}?name=${this.model.user.name}&surname=${this.model.user.surname}&email=${this.model.user.email}&number=${encodedNumber}`,this.model.user,{
            headers:{
                Authorization: `Bearer ${VuexStore.state.token} `,
            }
         }).then(res => { this.isLoading=false;
            
            this.$router.push({ name: 'customers' });
         });
        // this.$router.push({name: 'person'});
        }
        catch(error){ this.isLoading=false;
            
            alert(error.response.data);
        }
    },
    back(){
        this.$router.push({name: 'customers'});
    }
    },
    components: {Loading}
}
</script>