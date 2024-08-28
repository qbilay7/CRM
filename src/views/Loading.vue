<template>
    <div style="background-color: #f8f3e7;" class="loading-container">
      <div class="loading-spinner"></div>
      <p class="loading-text">Loading...</p>
    </div>
  </template>
  
  <style scoped>
  .loading-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    height: 100vh;
  }
  
  .loading-spinner {
    border: 8px solid #f3f3f3; /* Light grey */
    border-top: 8px solid #455936; /* Blue */
    border-radius: 50%;
    width: 50px;
    height: 50px;
    animation: spin 1s linear infinite;
  }
  
  .loading-text {
    margin-top: 20px;
    font-size: 18px;
    color: #333;
  }
  
  @keyframes spin {
    0% { transform: rotate(0deg); }
    100% { transform: rotate(360deg); }
  }
  </style>
  