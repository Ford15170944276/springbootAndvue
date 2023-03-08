import axios from 'axios'
import * as ElementUI from "@element-plus/icons-vue";
import router from "@/router";
import {ElMessage} from "element-plus";

const request = axios.create({
    // 注意！！ 这里是全局统一加上了 '/api' 前缀，也就是说所有接口都会加上'/api'前缀在，页面里面写接口的时候就不要加 '/api'了，否则会出现2个'/api'，类似 '/api/api/user'这样的报错，切记！！！
    baseURL: '/api',
    // baseURL: `http://${serverIp}:9090`,
    timeout: 10000
})


// 请求白名单，如果请求在白名单里面，将不会被拦截校验权限
const whiteUrls = ["/admin/login/", "/admin/register/", "/email/sendphoneCode"]

// request 拦截器
// 可以自请求发送前对请求做一些处理
// 比如统一加token，对请求参数统一加密
request.interceptors.request.use(config => {
    config.headers['Content-Type'] = 'application/json;charset=utf-8';
    //如果需要token就打开设置token
    let admin = localStorage.getItem("admin") ? JSON.parse(localStorage.getItem("admin")) : null
    if (!whiteUrls.includes(config.url)) {  // 校验请求白名单
        if(!admin) {
            router.push("/login")
        } else {
            config.headers['token'] = admin.token;
        }
    }
    return config
}, error => {
    return Promise.reject(error)
});

// response 拦截器
// 可以在接口响应后统一处理结果
request.interceptors.response.use(
    response => {
        let res = response.data;
        // 如果是返回的文件
        if (response.config.responseType === 'blob') {
            return res
        }
        // 兼容服务端返回的字符串数据
        if (typeof res === 'string') {
            res = res ? JSON.parse(res) : res
        }
        // 当权限验证不通过的时候给出提示
        if (res.code === '401') {
            ElementUI.Message({
                message: res.msg,
                type: 'error'
            });
        }
        if (res.code != 0) {
            // if (res.code == 602) {
            //     router.push('/login')
            //     localStorage.clear()
            //     return
            // }
            ElMessage({
                message: res.msg || 'Error',
                type: 'error',
                duration: 5 * 1000,
                showClose: true
            })
            return Promise.reject(new Error(res.msg || 'Error'))
        } else {
            return res
        }
    },
    error => {
        console.log(error)

        // if (error.response.status) {
        //     switch (error.response.status) {
        //         // 401: 未登录
        //         // 未登录则跳转登录页面，并携带当前页面的路径
        //         // 在登录成功后返回当前页面，这一步需要在登录页操作。
        //         case 401:
        //             router.replace({
        //                 path: '/login',
        //                 query: {
        //                     redirect: router.currentRoute.fullPath
        //                 }
        //             }).then(r => {
        //                 this.$message({
        //                     type:"warning",
        //                     message:"未登录"
        //                 })
        //             });
        //             break;
        //         // 403 token过期
        //         // 登录过期对用户进行提示
        //         // 清除本地token和清空vuex中token对象
        //         // 跳转登录页面
        //         case 403:
        //             this.$message({
        //                 type:"warning",
        //                 message:"登录过期"
        //             })
        //             // 清除token
        //             localStorage.removeItem('token');
        //             // 跳转登录页面，并将要浏览的页面fullPath传过去，登录成功后跳转需要访问的页面
        //             setTimeout(() => {
        //                 router.replace({
        //                     path: '/login',
        //                     query: {
        //                         redirect: router.currentRoute.fullPath
        //                     }
        //                 });
        //             }, 1000);
        //             break;
        //
        //         // 404请求不存在
        //         case 404:
        //             this.$message({
        //                 type:"warning",
        //                 message:"请求失败"
        //             })
        //             break;
        //         // 其他错误，直接抛出错误提示
        //         default:
        //             this.$message({
        //                 type:"warning",
        //                 message:error.response.data.message
        //             })
        //     }
        //     return Promise.reject(error.response);
        // }
        return Promise.reject(error)
    }
)

export default request