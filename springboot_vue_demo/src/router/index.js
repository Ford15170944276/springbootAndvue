import { createRouter, createWebHistory } from 'vue-router'
import User from '@/views/User.vue'
import Login from "@/views/Login";
import Layout from "@/layout/Layout";
import Register from "@/views/Register.vue";
import Admin from "@/views/Admin.vue";
import Commodity from "@/views/Commodity.vue";
import News from "@/views/News.vue";

const routes = [
  {
    path: '/',
    name: 'layout',
    component: Layout,
    redirect: "/login",   // /默认进入
    children: [
      {
        path: 'user',
        name: 'User',
        component: User
      },
      {
        path: 'admin',
        name: 'Admin',
        component: Admin
      },
      {
        path: 'commodity',
        name: 'Commodity',
        component: Commodity
      },
      {
        path: 'news',
        name: 'News',
        component: News
      },
    ]
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/register',
    name: 'Register',
    component: Register
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

router.beforeEach((to, from, next) => {
  if (to.path === '/login' || to.path === '/register') {
    next()
    return
  }
  let admin = localStorage.getItem("admin") ? JSON.parse(localStorage.getItem("admin")) : {}
  if (!admin || admin.length === 0) {
    next('/login')
  } else {
    next()
  }
})

export default router
