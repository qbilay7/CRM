<template>
  <!--
    <div v-if="!isLoading" class="pro-shop">
      
      <div class="sidebar">
        <h2>Pro Shop</h2>
        <ul>
          <li><router-link to="/shop">Add Product</router-link></li>
          <li><router-link to="/shop/inventory">Inventory</router-link></li>
          <li><router-link to="/admin/pro-shop/orders">Orders</router-link></li>
          <li><router-link to="/admin/pro-shop/reports">Reports</router-link></li>
        </ul>
      </div>
  
      
      <div class="main-content">
        
        <div class="page-header">
          <h2>Welcome to the Pro Shop</h2>
          <p>Manage your inventory and orders.</p>
        </div>

        <div class="product-form">
        <h3>Add Product</h3>
        <form @submit.prevent="addProduct">
          <div class="form-group">
            <label for="productName">Product Name:</label>
            <input type="text" id="productName" v-model="newProduct.productName" required>
          </div>
          <div class="form-group">
            <label for="description">Description:</label>
            <input type="text" id="description" v-model="newProduct.description">
          </div>
          <div class="form-group">
            <label for="category">Category:</label>
            <input type="text" id="category" v-model="newProduct.category">
          </div>
          <div class="form-group">
            <label for="image" class="form-label">Product Image:</label>
            <input type="file" id="image" class="form-control" ref="imageInput"  @change="handleImageChange" accept="image/*">
          </div>
          <div class="form-group">
            <label for="productPrice">Price:</label>
            <input type="number" id="productPrice" v-model="newProduct.price" required>
          </div>
          <div class="form-group">
            <label for="currencyType">Currency Type:</label>
            <div class="currency-input">
              <input type="text" id="currencyType" v-model="newProduct.currency" @click="toggleCurrencyOptions" readonly>
              <div class="currency-options" v-show="showCurrencyOptions">
                <div class="currency-option" v-for="(currency, index) in currencyOptions" :key="index" @click="selectCurrency(currency)">
                  {{ currency }}
                </div>
              </div>
            </div>
          </div>
          <button type="submit">Add Product</button>
        </form>
      </div>
        <router-view></router-view>
      </div>
    </div>
    <div v-if="isLoading"><Loading></Loading></div>
  -->
  </template>
  
  <script>
  /*
  import { RouterLink, RouterView } from 'vue-router'
  import axios from 'axios';
  import VuexStore from '../store/VuexStore';
  import Loading from './Loading.vue';
  const email = localStorage.getItem('companyEmail')
  export default {
    name: 'ProShop',
    data(){
      return{
        isLoading: false,
        newProduct:{
          productId: null,
          productName: '',
          description: '',
          price: null,
          currency: '',
          image: null,
          category: '',
        },
        currencyOptions: ['$', '£', 'TL','€'],
        showCurrencyOptions: false,
      }
    },
    components:{Loading},
    methods:{
      toggleCurrencyOptions() {
      this.showCurrencyOptions = !this.showCurrencyOptions;
      },
      selectCurrency(currency) {
        this.newProduct.currency = currency;
        this.showCurrencyOptions = false; // Hide the currency options after selection
      },
      handleImageChange(event) {
      
      const file = event.target.files[0];
      
      const reader = new FileReader();
      reader.onloadend = () => {
    
        const base64String = reader.result.split(',')[1];

    
       // const cleanBase64String = base64String.replace(/[\r\n]/g, '');

        this.newProduct.image = base64String;
      };
      reader.readAsDataURL(file);

    },
    async addProduct(){
       try{
        this.isLoading=true;
         await axios.post(`https://api.golfsprint.net/golfsprint/api/shop/add/${email}`,this.newProduct,{
            headers:{
                Authorization: `Bearer ${VuexStore.state.token} `,
            }
         }).then(res => { this.isLoading=false;
            console.log(res.data);
            alert("Successfully added. Please check your inventory.")
            //window.location.reload();
            
         });
       }
       catch(error){
        this.isLoading=false;
        console.log(error.data);
        alert(error.data.message);
       }
    }
    },
  }
  */
  </script>
  
  <style scoped>
  /*
  body {
  font-family: 'Arial', sans-serif;
  margin: 0;
  padding: 0;
}


.pro-shop {
  display: flex;
  min-height: 100vh;
  background-color: #f8f3e7; 
}


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


.main-content {
  flex-grow: 1;
  padding: 20px;
}


.page-header {
  margin-bottom: 40px;
}

.page-header h2 {
  margin-bottom: 10px;
  color: #37474f;
}

.page-header p {
  color: #607d8b;
}


.product-form input[type="text"],
.product-form input[type="file"],
.product-form input[type="number"] {
  width: calc(100% - 70px); 
  padding: 6px; 
  box-sizing: border-box;
}
.product-form .currency-group {
  display: flex;
  align-items: center;
}

.product-form .currency-input {
  position: relative;
}
.product-form {
  margin-bottom: 20px;
}

.product-form h3 {
  margin-bottom: 10px;
  color: #37474f;
}

.product-form .form-group {
  display: grid;
  grid-template-columns: 1fr 3fr;
  gap: 10px;
}

.product-form label {
  color: #37474f;
}

.product-form input {
  width: 100%;
  padding: 6px;
  box-sizing: border-box;
}

.product-form button {
  padding: 10px 20px;
  background-color: #37472c;
  color: #ffffff;
  border: none;
  cursor: pointer;
}

.product-form button:hover {
  background-color: #283747;
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
*/
  </style>
  