import Vue from 'vue'
import Router from 'vue-router'
import Login from '../view/Login'
import SignUp from '../view/SignUp'
import Home from '../view/Home'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Login',
      component: Login
    },
    {
      path: '/login',
      name: 'Login',
      component: Login
    },
    {
      path: '/signUp',
      name: 'SignUp',
      component: SignUp
    },
    {
      path: '/home',
      name: 'Home',
      component: Home
    }
  ]
})
