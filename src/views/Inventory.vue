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
        <h2>Inventory</h2>
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
            <button class="btn btn-danger" @click="deleteProduct(product.productId)">Delete</button>
            <RouterLink style="background-color: #455936; border: #455936;" :to="{path:'/update/'+product.productId+'/product'}" class="btn btn-primary">Update</RouterLink>
          </div>
        </div>
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
  const email = localStorage.getItem('companyEmail')
  export default {
    name:'inventory',
    data() {
      return {
        products: [],
        isLoading: false,
        originalProducts: [],
        searchQuery: '',
      };
    },
    mounted(){
      this.getProducts();
    },
    methods: {
      async getProducts(){
        this.isLoading=true;
      await axios.get(`https://api.golfsprint.net/golfsprint/api/shop/getAll/${email}`,{
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
      deleteProduct(productId){
        if (confirm('Are you sure you want to delete this product?')) {
          axios.delete(`https://api.golfsprint.net/golfsprint/api/shop/delete/${productId}`,{
        headers:{
          Authorization: `Bearer ${VuexStore.state.token} `,
         // 'Cache-Control': 'no-cache',
        }
      }).then(res => {
        console.log(res)
        window.location.reload();
      })
        }
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
*/
  </style>
  