import Vue from 'vue'
import VueRouter from 'vue-router'
import store from "@/store";


Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        name: 'Management',
        redirect: "/login",
        component: () => import(/* webpackChunkName: "about" */ '@/views/Manager/Manage.vue'),
        children: [
            {
                path: '/Management/semester',
                name: '学期管理',
                component: () => import(/* webpackChunkName: "about" */ '@/views/Manager/Semester.vue'),
            },
            {
                path: '/Management/classScheduling',
                name: '实验排课',
                component: () => import(/* webpackChunkName: "about" */ '@/views/Manager/ClassScheduling.vue')
            },
            {
                path: '/Management/allLab',
                name: '实验排课',
                component: () => import(/* webpackChunkName: "about" */ '@/views/Manager/AllLab.vue')
            },
            {
                path: '/Management/manager',
                name: '管理员管理',
                component: () => import(/* webpackChunkName: "about" */ '@/views/Manager/Manager.vue')
            },
            {
                path: '/Management/tester',
                name: '实验员管理',
                component: () => import(/* webpackChunkName: "about" */ '@/views/Manager/Tester.vue')
            },
            {
                path: '/Management/student',
                name: '学生管理',
                component: () => import(/* webpackChunkName: "about" */ '@/views/Manager/Student.vue')
            },
            {
                path: '/Management/teacher',
                name: '教师管理',
                component: () => import(/* webpackChunkName: "about" */ '@/views/Manager/Teacher.vue')
            },
            {
                path: '/Management/home',
                name: '主页',
                component: () => import(/* webpackChunkName: "about" */ '@/views/Manager/Home.vue')
            },
            {
                path: '/Management/scrutinize',
                name: '实验室借用审批',
                component: () => import(/* webpackChunkName: "about" */ '@/views/Manager/Scrutinize.vue')
            },
            {
                path: '/Management/person',
                name: '个人中心',
                component: () => import(/* webpackChunkName: "about" */ '../views/Person.vue')
            },
            {
                path: '/Management/Application',
                name: '申请登记',
                component: () => import(/* webpackChunkName: "about" */ '@/views/Teacher/Application.vue')
            },
            {
                path: '/Management/Warranty',
                name: '设备报修',
                component: () => import(/* webpackChunkName: "about" */ '@/views/Teacher/Warranty.vue')
            },
            {
                path: '/Management/maintenance',
                name: '设备维修',
                component: () => import(/* webpackChunkName: "about" */ '@/views/Tester/Maintenance.vue')
            },
            {
                path: '/Management/AllEligibleLab',
                name: '所有符合条件的实验室',
                component: () => import(/* webpackChunkName: "about" */ '@/views/Manager/AllEligibleLab.vue')
            },
            {
                path: '/Management/AdminSelectLab',
                name: '选择实验室',
                component: () => import(/* webpackChunkName: "about" */ '@/views/Manager/AdminSelectLab.vue')
            },
        ]
    },
    {
        path: '/',
        name: 'Student',
        component: () => import(/* webpackChunkName: "about" */ '@/views/Student/Reserve.vue'),
        children: [
            {
                path: '/Student/home',
                name: '主页',
                component: () => import(/* webpackChunkName: "about" */ '@/views/Student/Home.vue')
            },
            {
                path: '/Student/Application',
                name: '借用申请',
                component: () => import(/* webpackChunkName: "about" */ '@/views/Student/Application.vue')
            },
            {
                path: '/Student/Person',
                name: '个人中心',
                component: () => import(/* webpackChunkName: "about" */ '../views/StudentPerson.vue')
            },
        ]
    },
    {
        path: '/',
        name: 'TimeTable',
        component: () => import(/* webpackChunkName: "about" */ '@/views/TimeTable/Table.vue'),
        children: [
            {
                path: '/TimeTable/home',
                name: '主页',
                component: () => import(/* webpackChunkName: "about" */ '@/views/TimeTable/Home.vue')
            },
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
