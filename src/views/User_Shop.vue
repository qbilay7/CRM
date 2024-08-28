<template>
  <!--
    <div v-if="!isLoading" class="pro-shop">
        <div class="sidebar">
        <div class="sidebar-avatar">
             <img v-if="companyImage" :src="'data:image/jpeg;base64,' + companyImage" alt="Profile Picture">
        </div>
        <h2>{{ companyName }}</h2>
        <ul>
          <li><router-link :to="{path:'/user/'+companyId+'/club'}">Club</router-link></li>
          <li><router-link :to="{path:'/user/'+companyId+'/reservation'}">Reservation</router-link></li>
          <li><router-link :to="{path:'/user/'+companyId+'/course'}">Courses</router-link></li>
          <li><router-link :to="{path:'/user/'+companyId+'/event'}">Events</router-link></li>
          <li><router-link :to="{path:'/user/'+companyId+'/shop'}">Pro-Shop</router-link></li> 
        </ul>
      </div>
      <div class="main-content">
        <div class="page-header">
        <h2>Pro-Shop</h2>
    </div>
    <div class="input-group">
            <input type="text" class="form-control" placeholder="Search..." v-model="searchQuery">
     
          </div>
      <div class="product-list">
        <div v-for="product in products" :key="product.productId" class="product">
          <div class="product-image-container">
            <img v-if="product.image" :src="'data:image/jpeg;base64,' + product.image"  class="product-image">
          </div>
          <div class="product-details">
            <h3>{{ product.productName }}</h3>
            <p>{{ product.description }}</p>
            <p class="price">{{ product.price }}{{ product.currency }}</p>
            <p>Category: {{ product.category }} - Product Id: {{ product.productId }}</p>
            <button @click="addToCart(product)">Add to Card</button>
            
          </div>
        </div>
      </div>
      <div class="cart" v-if="cart.length > 0">
        <h2>Your Cart</h2>
        <ul>
          <li v-for="(item, index) in cart" :key="index">
            {{ item.name }} - {{ item.price }}$
            <button @click="removeFromCart(index)">Remove</button>
          </li>
        </ul>
        <button @click="checkout">Checkout</button>
      </div>
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

  export default {
    name:'inventory',
    data() {
      return {
        products: [],
        isLoading: false,
        companyId: null,
        companyImage: null,
        companyName: '',
        originalProducts: [],
        searchQuery: '',
        cart:[],
      };
    },
    mounted(){
      this.companyId=this.$route.params.companyId;
      this.getCompany(this.companyId);
      this.getProducts(this.companyId);
    },
    methods: {
        addToCart(product) {
        this.cart.push(product);
      },
      removeFromCart(index) {
        this.cart.splice(index, 1);
      },
      checkout() {
        // Implement checkout logic here
        alert('Thank you for your purchase!');
        this.cart = [];
      },
        async getCompany(companyId){
       try{
        this.isLoading=true;
         await axios.get(`https://api.golfsprint.net/golfsprint/api/club_profile/getById/${companyId}`,{
            headers:{
                Authorization: `Bearer ${VuexStore.state.token} `,
            }
         }).then(res => { this.isLoading=false;
            console.log(res.data);
            
            this.companyName=res.data.companyName;
            this.companyImage=res.data.companyImage;
         });
       }
       catch(error){
        this.isLoading=false;
        console.log(error.data);
        alert(error.data.message);
       }
    },
      async getProducts(companyId){
        this.isLoading=true;
      await axios.get(`https://api.golfsprint.net/golfsprint/api/shop/getAllById/${companyId}`,{
         headers:{
           Authorization: `Bearer ${VuexStore.state.token} `,
          // 'Cache-Control': 'no-cache',
         }
         }).then(res => { this.isLoading=false;
           this.products = res.data;
           this.originalProducts = res.data;
           console.log(this.events);
       });
      },
      
    },
    components:{Loading},
    watch: {
    searchQuery: function(val) {
      if (val) {
        this.products = this.originalProducts.filter(product =>
          product.productName.toLowerCase().includes(val.toLowerCase()) ||
          product.price.toString().includes(val) ||
          product.category.toLowerCase().includes(val.toLowerCase())
        );
      } else {
        this.products = this.originalProducts;
      }
    }
   }
  };
  */
  </script>
  
  <style scoped>
  /*
  .pro-shop {
  display: flex;
  min-height: 100vh;
  background-color: #f8f3e7; 
}
.product-image-container {
  display: flex;
  justify-content: center; 
  align-items: flex-start; 
  height: 200px; 
}
  .product-list {
    display: flex;
    flex-wrap: wrap;
    justify-content: space-between;
  }
  
  .product {
    width: 30%;
    margin-bottom: 20px;
    border: 1px solid #ccc;
    padding: 10px;
    background-color: white;
  }
  
  .product-image {
    width: 200px;
    height: auto;
  }
  
  .product-details {
    text-align: center;
  }
  .product-details p{
    margin: 1.5px;
  }
  .product-details h3{
    font-size: 1.2rem;
  }
  .price {
    font-weight: bold;
  }
   
.sidebar {
  background-color: #37472c;
  color: #ffffff;
  padding: 20px;
  width: 250px;
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
.sidebar-avatar img {
    width: 100px;
    height: auto;
    border-radius: 50%;
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
.cart {
    margin-top: 30px;
    border-top: 1px solid #ccc;
    padding-top: 20px;
  }
  
  .cart button {
    margin-top: 10px;
  }
  */
  </style>
  