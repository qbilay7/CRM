import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import RegForC from '../views/RegForC.vue'
import LoginC from '../views/LoginC.vue'
import LoginU from '../views/LoginU.vue'
import Login from '../views/Login.vue'
import CustomersView from '../views/CustomersView.vue'
import CreateCustomer from '../views/Create.vue'
import EditCustomer from '../views/Edit.vue'
import Reservation from '../views/Reservation.vue'
import Course from '../views/Course.vue'
import Event from '../views/Event.vue'
import Shop from '../views/Shop.vue'
import Inventory from '../views/Inventory.vue'
import UpdateProduct from '../views/UpdateProduct.vue'
import Club_Profile from '../views/Club_Profile.vue'
import Update_Club_Profile from '../views/Update_Club_Profile.vue'
import User_Profile from '../views/User_Profile.vue'
import User_Update_Profile from '../views/User_Update_Profile.vue'
import User_Search from '../views/User_Search.vue'
import User_Selected_Club from '../views/User_Selected_Club.vue'
import User_Course from '../views/User_Course.vue'
import User_Event from '../views/User_Event.vue'
import User_Reservation from '../views/User_Reservation.vue'
import User_Shop from '../views/User_Shop.vue'
import Password from '../views/Password.vue'
import User_Password from '../views/User_Password.vue'
import UpdateEvent from '../views/UpdateEvent.vue'
import UpdateCourse from '../views/UpdateCourse.vue'
import Reserv_Config from '../views/Reserv_Config.vue'
import RequestCourse from '../views/RequestCourse.vue'
import SendMessages from '../views/SendMessages.vue'
import U_Delete from '../views/U_Delete.vue'
import C_Delete from '../views/C_Delete.vue'
import SendMessage from '../views/SendMessage.vue'
import ContactView from '../views/ContactView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/about',
      name: 'about',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/AboutView.vue')
    },
    {
      path: '/services',
      name: 'services',
      component: () => import('../views/ServicesView.vue')
    },
    {
      path: '/pricing',
      name: 'pricing',
      component: () => import('../views/PricingView.vue')
    },
    {
      path: '/contact',
      name: 'contact',
      component: ContactView
    },
    {
      path: '/register',
      name: 'register_club',
      component: RegForC
    },
    {
      path: '/loginC',
      name: 'loginC',
      component: LoginC
    },
    {
      path: '/loginU',
      name: 'loginU',
      component: LoginU
    },
    {
      path: '/login',
      name: 'login',
      component: Login
    },
    {
      path: '/customers',
      name: 'customers',
      component: CustomersView
    },
    {
      path: '/create/customer',
      name: 'createCustomer',
      component: CreateCustomer
    },
    {
      path: '/edit/:userId/customer',
      name: 'editCustomer',
      component: EditCustomer
    },
    {
      path: '/update/:productId/product',
      name: 'updateProduct',
      component: UpdateProduct
    },
    {
      path: '/reservation',
      name: 'reservation',
      component: Reservation
    },
    {
      path: '/course',
      name: 'course',
      component: Course
    },
    {
      path: '/event',
      name: 'event',
      component: Event
    },
    {
      path: '/shop',
      name: 'shop',
      component: Shop
    },
    {
      path: '/shop/inventory',
      name: 'inventory',
      component: Inventory
    },
    {
      path: '/club/profile',
      name: 'club_profile',
      component: Club_Profile
    },
    {
      path: '/update/:companyId/club',
      name: 'updateCompany',
      component: Update_Club_Profile
    },
    {
      path: '/user/profile',
      name: 'user_profile',
      component: User_Profile
    },
    {
      path: '/update/:userId/user',
      name: 'updateUser',
      component: User_Update_Profile
    },
    {
      path: '/user/search',
      name: 'user_search',
      component: User_Search
    },
    {
      path: '/user/club',
      name: 'selected',
      component: User_Selected_Club
    },
    {
      path: '/user/course',
      name: 'clubCourse',
      component: User_Course
    },
    {
      path: '/user/event',
      name: 'clubEvent',
      component: User_Event
    },
    {
      path: '/user/reservation',
      name: 'clubReservation',
      component: User_Reservation
    },
    {
      path: '/user/shop',
      name: 'clubShop',
      component: User_Shop
    },
    {
      path: '/club/password',
      name: 'password',
      component: Password
    },
    {
      path: '/user/password',
      name: 'userPassword',
      component: User_Password
    },
    {
      path: '/update/:eventId/event',
      name: 'updateEvent',
      component: UpdateEvent
    },
    {
      path: '/update/:courseId/course',
      name: 'updateCourse',
      component: UpdateCourse
    },
    {
      path: '/reservation/configure',
      name: 'reservConfig',
      component: Reserv_Config
    },
    {
      path: '/request/course',
      name: 'requestCourse',
      component: RequestCourse
    },
    {
      path: '/send/messages',
      name: 'sendMessages',
      component: SendMessages
    },
    {
      path: '/user/delete',
      name: 'userDelete',
      component: U_Delete
    },
    {
      path: '/company/delete',
      name: 'companyDelete',
      component: C_Delete
    },
    {
      path: '/send/:userId/message',
      name: 'sendMessage',
      component: SendMessage
    }
  ]
})

export default router
