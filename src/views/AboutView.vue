<template>
<section style="background-color: #f8f3e7;" class="about_section layout_padding-bottom">
    <div class="container  ">
      <div class="row">
        <div class="col-md-6">
          <div class="detail-box">
            <div class="heading_container">
              <h2>
                About Us
              </h2>
            </div>
            <p>
              GolfSprint offers more than just software; we provide unwavering support. Our team is committed to your success. We're here to answer your questions, resolve your concerns, and ensure you harness the full potential of our system. Become a part of the GolfSprint community and experience the difference. Let us help you elevate your golf course's management to new heights. With us, it's a destination for unforgettable golfing moments. </p>
            
          </div>
        </div>
        <div class="col-md-6 ">
          <div class="img-box">
            <img src="../assets/CSST/images/why.png" alt="">
          </div>
        </div>

      </div>
    </div>
  </section>
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

<style scoped>
.nr{
  width: 250px;
  height: auto;
}
</style>
