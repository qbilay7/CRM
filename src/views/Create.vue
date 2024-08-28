<template>
    <div v-if="!isLoading" class="container mt-5">
       <div style="box-shadow: 10px 10px 10px rgba(0.2, 0.2, 0.2, 0.2); background-color: #f8f3e7;" class="card">
        <div class="card-header">
            <h4>Add Customer</h4>
        </div>
        <div class="card-body">
            <div class="mb-3">
                <label for="">Name</label>
                <input type="text" v-model="model.user.name" class="form-control" required>
            </div>
            <div class="mb-3">
                <label for="">Surname</label>
                <input type="text"  v-model="model.user.surname" class="form-control" required/>
            </div>
            <div class="mb-3">
                <label for="">Email</label>
                <input type="email"  v-model="model.user.email" class="form-control" required/>
            </div>
            <div class="mb-3">
                <label for="">Phone Number</label>
                <input type="tel"  v-model="model.user.number" @input="formatPhoneNumber" class="form-control" placeholder="ex: +901112223344" required/>
            </div>
            <div class="mb-3">
                <label for="">Password</label>
                <input type="text"  v-model="model.user.password" class="form-control" placeholder="! Don't forget to give this password to the user. It can only be seen once !" required/>
            </div>
            <div class="mb-3">
                <button type="button" @click="savePerson" style="background-color: #36462a; border: #36462a;" class="btn btn-primary">Save</button>
            </div>
        </div>
       </div>
       <button type="button" @click="back" style="background-color: #617d4b; border: #617d4b;" class="btn btn-danger">Back</button>
    </div>
    <div v-if="isLoading"><Loading></Loading></div>
</template>
<script>
import axios from 'axios';
import VuexStore from '../store/VuexStore';
import Loading from './Loading.vue';

export default {
    name: 'createCustomer',
    data(){
       return {
         model: {
            user: {
                name: '',
                surname: '',
                email: '',
                number: '',
                password: '',
            }
         },
         isLoading: false,
       }
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
       async savePerson(){
        try{
            this.isLoading=true;
        await axios.post(`https://api.golfsprint.net/golfsprint/api/users/add`,this.model.user,{
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