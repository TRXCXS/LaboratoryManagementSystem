import axios from 'axios'
import { get } from 'lodash'
const request = axios.create({
    baseURL: 'http://localhost:8080',
    timeout: 5000
})

// request 拦截器
// 可以自请求发送前对请求做一些处理
// 比如统一加token，对请求参数统一加密
request.interceptors.request.use(config => {
    config.headers['Content-Type'] = 'application/json;charset=utf-8';
    let user = localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : null
    if (user) {
        config.headers.Authorization = "Bearer "+user.access_token
        config.headers['token'] = user.access_token;  // 设置请求头
        console.log( config.headers.Authorization)
    }
    return config
}, error => {
    return Promise.reject(error)
});

// response 拦截器
// 可以在接口响应后统一处理结果
request.interceptors.response.use(
    response => {
        // const status = get(error, 'response.status')//获取response的code
        let res = response.data;
        // 如果是返回的文件
        if (response.config.responseType === 'blob') {
            return res
        }
        // 兼容服务端返回的字符串数据
        // if (typeof res === 'string') {
        //     res = res ? JSON.parse(res) : res
        // }
        return res;
    },

    error => {
        console.log('err' + error) // for debug
        return Promise.reject(error)
    },
)


export default request