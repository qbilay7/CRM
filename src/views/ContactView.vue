<template>
    <!-- contact section -->
    <section style="background-color: #f8f3e7;" class="contact_section layout_padding-bottom">
    <div v-if="!isLoading" class="container">
      <div class="heading_container heading_center">
        <h2>
          Get In Touch
        </h2>
      </div>
      <div class="row">
        <div class="col-md-8 col-lg-6 mx-auto">
          <div class="form_container">
            <form @submit.prevent="sendMessage">
              <div>
                <input type="text" v-model="name" placeholder="Your Name" required/>
              </div>
              <div>
                <input type="email" v-model="email" placeholder="Your Email" required/>
              </div>
              <div>
                <input type="text" v-model="phone" placeholder="Your Phone" required/>
              </div>
              <div>
                <input type="text" v-model="message" class="message-box" placeholder="Message" required/>
              </div>
              <div class="btn_box ">
                <button type="submit">
                  SEND
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
    <div v-if="isLoading"><Loading></Loading></div>
  </section>
  <!-- end contact section -->

  <!-- info section -->
  <section class="info_section layout_padding2">
    <div class="container">
      <div class="row">
        <div class="col-md-3">
          <div class="info_contact">
            <h4>
              Contact
            </h4>
            <div class="contact_link_box">
              <a>
                <i class="fa fa-map-marker" aria-hidden="true"></i>
                <span>
                  Istanbul/Türkiye
                </span>
              </a>
              <a>
                <i class="fa fa-phone" aria-hidden="true"></i>
                <span>
                  Call +90 555 688 27 03
                </span>
              </a>
              <a>
                <i class="fa fa-envelope" aria-hidden="true"></i>
                <span>
                  info@golfsprint.net
                </span>
              </a>
            </div>
          </div>
          <div class="info_social">
            <a>
              <i class="fa fa-whatsapp" aria-hidden="true"></i>
            </a>
            <a href="https://www.linkedin.com/in/kubilay-k%C3%BCrt%C3%BCr-59803a28b/">
              <i class="fa fa-linkedin" aria-hidden="true"></i>
            </a>
          </div>
        </div>
        <div class="col-md-3">
          <div class="info_link_box">
            <h4>
              Links
            </h4>
            <div class="info_links">
              <RouterLink to="/">
                <img class="bt" src="../assets/CSST/images/bullet.png" alt="">
                Home
              </RouterLink>
              <RouterLink to="/about">
                <img class="bt" src="../assets/CSST/images/bullet.png" alt="">
                About
              </RouterLink>
              <RouterLink to="/services">
                <img class="bt" src="../assets/CSST/images/bullet.png" alt="">
                Services
              </RouterLink>
              <RouterLink to="/pricing">
                <img class="bt" src="../assets/CSST/images/bullet.png" alt="">
                Pricing
              </RouterLink>
              <RouterLink to="/contact">
                <img class="bt" src="../assets/CSST/images/bullet.png" alt="">
                Contact Us
             </RouterLink>
            </div>
          </div>
        </div>
        <div class="col-md-3">
          <div class="info_detail">
            <h4>
              Info
            </h4>
            <p>
              If you want your own management system or want additional funtionailites, feel free to contact us via email or phone.
            </p>
          </div>
        </div>
        <img class="nr" src="../assets/CSST/images/sprint.png" >
      </div>
    </div>
  </section>
  
  <!-- end info section -->


  <!-- footer section -->
  <footer class="footer_section">
    <div class="container">
      <p>
        Copyright &copy; <span id="displayYear">2024</span> GolfSprint. All rights reserved.
        
      </p>
    </div>
  </footer>
  <!-- footer section -->
</template>
<script>
 import axios from 'axios';
 import Loading from './Loading.vue';
  export default {
   name:'contact',
   data(){
    return{
      name: '',
      email:'',
      phone:'',
      message:'',
      isLoading: false,
    }
   },
   methods:{
    async sendMessage() {
        try{
            this.isLoading = true;
        await axios.post(`https://api.golfsprint.net/golfsprint/api/club/send/admin`,null,{
            params:{
                subject: `${this.name},${this.email},${this.phone}`,
                message: this.message
            }
         }).then(res => { this.isLoading=false;
            alert("Successfully sent.");
          });
        
        }
        catch(error){
            this.isLoading = false;
            alert("An error occured while sending.");
        }
      }
   },
   components:{Loading}
 };
</script>

<style scoped>

.nr{
  width: 250px;
  height: auto;
}
.contact-page {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
}

.contact-info {
  display: flex;
  flex-direction: column;
}

.contact-item {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}

.icon {
  margin-right: 10px;
}

.label {
  font-weight: bold;
}

.value {
  color: #333;
}
</style>