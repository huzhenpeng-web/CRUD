import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui'
import axios from 'axios'
import qs from 'qs'
import 'element-ui/lib/theme-chalk/index.css'

Vue.use(ElementUI)

Vue.config.productionTip = false

axios.defaults.baseURL = 'http://localhost:8080/ssm_crud/'
axios.interceptors.request.use(config => {
  config.headers['Content-Type'] = 'application/json'
  config.data = JSON.stringify(config.data)
  return config
})
Vue.prototype.$http = axios
Vue.prototype.qs = qs

Vue.directive('focus', { // 获取焦点
  inserted: function (el) {
    if (el.querySelector('input')) {
      el.querySelector('input').focus()
    }
  }
})

// 全局时间过滤器
Vue.filter('dateFormat', function (originVal) {
  const dt = new Date(originVal)
  const y = dt.getFullYear()
  const m = (dt.getMonth() + 1 + '').padStart(2, '0')
  const d = (dt.getDate() + '').padStart(2, '0')
  const hh = (dt.getHours() + '').padStart(2, '0')
  const mm = (dt.getMinutes() + '').padStart(2, '0')
  const ss = (dt.getSeconds() + '').padStart(2, '0')
  return `${y}-${m}-${d} ${hh}:${mm}:${ss}`
})

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
