<template>
    <div class="sign-up-section">
    <div class="sign-up-content"></div>
    <div class="sign-up-options">
      <router-link to="/loginU" class="user-button user-sign-up">
        Login as User
      </router-link>
      <router-link to="/loginC" class="club-button user-sign-up">
        Login as Instructor
      </router-link>
    </div>
    </div>
  </template>
  <script setup>
  import { RouterLink } from 'vue-router'
</script>
<style scoped>
.sign-up-section {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background-image: url('../assets/CSST/images/reg_backround.png');
  background-size: cover;
  background-repeat: no-repeat;
  background-color: #455936;
}

.sign-up-content {
  /* Your content above the buttons */
  margin-bottom: 20px; /* Add margin to separate from buttons */
}

.sign-up-options {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 20px; /* Add margin to separate from content above */
}

.user-button,
.club-button {
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  font-size: 18px;
  cursor: pointer;
  transition: background-color 0.3s, color 0.3s;
  margin: 5px; /* Add margin to separate buttons */
}

.user-button {
  background-color: #f8f3e7;
  color: black;
}

.club-button {
  background-color: #f8f3e7;
  color: black;
}

.user-button:hover {
  background-color: #577044;
}

.club-button:hover {
  background-color: #344329;
}
</style>
