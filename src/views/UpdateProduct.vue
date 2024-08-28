<template>
  <!--
    <div v-if="!isLoading" class="container mt-5">
       <div style="background-color: #f8f3e7;" class="card">
        <div class="card-header">
            <h4>Edit Product</h4>
        </div>
        <div class="card-body">
            <div class="mb-3">
                <label for="">Product Name</label>
                <input type="text" v-model="model.product.productName" class="form-control" />
            </div>
            <div class="mb-3">
                <label for="">Description</label>
                <input type="text"  v-model="model.product.description" class="form-control" />
            </div>
            <div class="mb-3">
                <label for="">Category</label>
                <input type="text"  v-model="model.product.category" class="form-control" />
            </div>
            <div class="mb-3">
                <label for="">Product Image</label>
                <input type="file" id="image" class="form-control" ref="imageInput"  @change="handleImageChange" accept="image/*">
            </div>
            <div class="mb-3">
                <label for="">Price</label>
                <input type="number"  v-model="model.product.price" class="form-control" />
            </div>
            <div class="mb-3">
                <label for="">Currency Type</label>
                <div class="currency-input">
              <input type="text" id="currencyType" v-model="model.product.currency" @click="toggleCurrencyOptions" readonly>
              <div class="currency-options" v-show="showCurrencyOptions">
                <div class="currency-option" v-for="(currency, index) in currencyOptions" :key="index" @click="selectCurrency(currency)">
                  {{ currency }}
                </div>
              </div>
            </div>
            </div>
            <div class="mb-3">
                <button style="background-color: #36462a; border: #36462a;" type="button" @click="updateProduct" class="btn btn-primary">Update</button>
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
    name: 'updateProduct',
    data(){
       return {
        productId: null,
         model: {
            product:{
          productName: '',
          description: '',
          price: null,
          currency: '',
          image: null,
          category: '',
        }
         },
         isLoading: false,
         currencyOptions: ['$', '£', 'TL','€'],
        showCurrencyOptions: false,
       }
    },
    mounted(){
       this.productId=this.$route.params.productId
       this.getProductData(this.productId);
    },
    methods: {
        toggleCurrencyOptions() {
      this.showCurrencyOptions = !this.showCurrencyOptions;
      },
      selectCurrency(currency) {
        this.model.product.currency = currency;
        this.showCurrencyOptions = false; // Hide the currency options after selection
      },
      handleImageChange(event) {
      
      const file = event.target.files[0];
      
      const reader = new FileReader();
      reader.onloadend = () => {
    
        const base64String = reader.result.split(',')[1];

    
       // const cleanBase64String = base64String.replace(/[\r\n]/g, '');

        this.model.product.image = base64String;
      };
      reader.readAsDataURL(file);

    },
      async getProductData(productId){
        this.isLoading=true;
         await axios.get(`https://api.golfsprint.net/golfsprint/api/shop/get/${productId}`,{
            headers:{
                Authorization: `Bearer ${VuexStore.state.token} `,
            }
          }).then(res => { this.isLoading=false;
            console.log(res.data);
            this.model.product.productName=res.data.productName
            this.model.product.description=res.data.description
            this.model.product.price=res.data.price
            this.model.product.currency=res.data.currency
            this.model.product.category = res.data.category
            this.model.product.image=res.data.image
          });
       },
      async updateProduct(){
        try{
            this.isLoading=true;
        await axios.put(`https://api.golfsprint.net/golfsprint/api/shop/update/${email}/${this.productId}`,this.model.product,{
            headers:{
                Authorization: `Bearer ${VuexStore.state.token} `,
            }
         }).then(res => { this.isLoading=false;
            console.log(res.data)
            this.$router.push({ name: 'inventory' });
         });
        // this.$router.push({name: 'person'});
        }
        catch(error){ this.isLoading=false;
            console.log(error);
            alert(error.response.data.message);
        }
    },
    back(){
        this.$router.push({name: 'inventory'});
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