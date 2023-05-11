import Vue from 'vue'
import App from './App.vue'
import router from './router'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import './assets/global.css'
import global from './baseURL/Base.vue'
import store from './store'
import request from "@/utils/request";
import $axios from "axios";
import VXETable from 'vxe-table'
import 'vxe-table/lib/style.css'

Vue.use(VXETable)

Vue.config.productionTip = false

Vue.prototype.request = request
Vue.prototype.GLOBAL = global
$axios.default.baseURL = global.BASE_URL

Vue.use(ElementUI, {size: "small"});

new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app')