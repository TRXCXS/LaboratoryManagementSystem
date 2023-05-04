import Vue from 'vue'
import VueRouter from 'vue-router'
import store from "@/store";


Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Management',
    redirect:"/login",
    component: () => import(/* webpackChunkName: "about" */ '../views/Management/Manage.vue'),
    children:[
      {path: '/Management/section', name: '区域管理', component: () => import(/* webpackChunkName: "about" */ '../views/Management/Section.vue'),},
      {path: '/Management/credit', name: '信用管理', component: () => import(/* webpackChunkName: "about" */ '../views/Management/Credit.vue')},
      {path: '/Management/manager', name: '管理员管理', component: () => import(/* webpackChunkName: "about" */ '../views/Management/Manager.vue')},
      {path: '/Management/user', name: '用户管理', component: () => import(/* webpackChunkName: "about" */ '../views/Management/User.vue')},
      {path: '/Management/reservation', name: '预约管理', component: () => import(/* webpackChunkName: "about" */ '../views/Management/Reservation.vue')},
      {path: '/Management/home', name: '主页', component: () => import(/* webpackChunkName: "about" */ '../views/Management/Home.vue')},
      {path: '/Management/report', name: '举报处理', component: () => import(/* webpackChunkName: "about" */ '../views/Management/Report.vue')},
      {path: '/Management/appeal', name: '申诉处理', component: () => import(/* webpackChunkName: "about" */ '../views/Management/Appeal.vue')},
      {path: '/Management/section/desk', name: '桌子管理', component: () => import(/* webpackChunkName: "about" */ '../views/Management/Desk.vue')},
      {path: '/Management/section/desk/seat', name: '椅子管理', component: () => import(/* webpackChunkName: "about" */ '../views/Management/Seat.vue')},
      // {path: '/Management/credit/check/:id', name: '信用分明细', component: () => import(/* webpackChunkName: "about" */ '../views/Management/Check_credit.vue')}
      {path: '/Management/credit/check', name: '信用分明细', component: () => import(/* webpackChunkName: "about" */ '../views/Management/Check_credit.vue')},
      {path: '/Management/person', name: '个人中心', component: () => import(/* webpackChunkName: "about" */ '../views/Person.vue')}
    ]
  },
  {
    path: '/',
    name: 'Reservation',
    component: () => import(/* webpackChunkName: "about" */ '../views/Reservation/Reserve.vue'),
    children:[
      {path: '/Reservation/home', name: '主页', component: () => import(/* webpackChunkName: "about" */ '../views/Reservation/Home.vue')},
      {path: '/Reservation/MyAppointment', name: '我的预约', component: () => import(/* webpackChunkName: "about" */ '../views/Reservation/CheckMyAppointment.vue')},
      {path: '/Reservation/CreditRecord', name: '信用记录', component: () => import(/* webpackChunkName: "about" */ '../views/Reservation/CreditRecord.vue')},
      {path: '/Reservation/SelectSection', name: '选择区域', component: () => import(/* webpackChunkName: "about" */ '../views/Reservation/SelectSection.vue')},
      {path: '/Reservation/SelectDesk', name: '选择桌子', component: () => import(/* webpackChunkName: "about" */ '../views/Reservation/SelectDesk.vue')},
      {path: '/Reservation/SelectSeat', name: '选择椅子', component: () => import(/* webpackChunkName: "about" */ '../views/Reservation/SelectSeat.vue')},
      {path: '/Reservation/MyAppeal', name: '我的申诉', component: () => import(/* webpackChunkName: "about" */ '../views/Reservation/CheckMyAppeal.vue')},
      {path: '/Reservation/MyReport', name: '我的举报', component: () => import(/* webpackChunkName: "about" */ '../views/Reservation/CheckMyReport.vue')},
      {path: '/Reservation/Report', name: '举报', component: () => import(/* webpackChunkName: "about" */ '../views/Reservation/Report.vue')},
      {path: '/Reservation/Appeal', name: '申诉', component: () => import(/* webpackChunkName: "about" */ '../views/Reservation/Appeal.vue')},
      {path: '/Reservation/Person', name: '个人中心', component: () => import(/* webpackChunkName: "about" */ '../views/Person.vue')},
    ]
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import(/* webpackChunkName: "about" */ '../views/Login.vue')
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import(/* webpackChunkName: "about" */ '../views/Register.vue')
  },
  {
    path: '/entryScanner',
    name: 'entryScanner',
    component: () => import(/* webpackChunkName: "about" */ '../views/EntryScanner.vue')
  },

  {
    path: '/chooseScanner',
    name: 'chooseScanner',
    component: () => import(/* webpackChunkName: "about" */ '../views/ChooseScanner.vue')
  },  {
    path: '/exitScanner',
    name: 'exitScanner',
    component: () => import(/* webpackChunkName: "about" */ '../views/ExitScanner.vue')
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

// 路由守卫
router.beforeEach((to, from, next) => {
  localStorage.setItem("currentPathName", to.name)  // 设置当前的路由名称，为了在Header组件中去使用
  store.commit("setPath")  // 触发store的数据更新
  next()  // 放行路由
})

export default router
