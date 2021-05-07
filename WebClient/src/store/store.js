import Vue from "vue";
import Vuex from "vuex";
import com from "./modules/com";
import login from "./modules/login"
import contract from "./modules/contract"
<<<<<<< HEAD
import customer from "./modules/customer"
=======
import order from './modules/order'
>>>>>>> b5b24c3fef02469a741d410ab5f1b5f3ab6ce12a
import {setAsyncRoutes} from "../router/async"
Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    routes: []
  },
  mutations: {
    resetAllState(state, payload) {
      Object.assign(state, payload);
    },
    SET_ROUTES(state, routes) {
      // 动态配置路由
      setAsyncRoutes(routes);
      // 为了防止用户刷新页面导致动态创建的路由失效，将其存储在本地中
      // 这里见一个好用的 session 工具，vue-session，可直接安装，使用方式可以在 gitub 上搜索
      sessionStorage.setItem('menu', JSON.stringify(routes));
      // 将路由存储在 store 中
      state.routes = routes;
    }
  },
  modules: {
    com,
    login,
    contract,
<<<<<<< HEAD
    customer
=======
    order
>>>>>>> b5b24c3fef02469a741d410ab5f1b5f3ab6ce12a
  }
});
