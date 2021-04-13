import Vue from "vue";
import VueRouter from "vue-router";
import { setAsyncRoutes } from "./async"

Vue.use(VueRouter);

const originalPush = VueRouter.prototype.push;
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
};

VueRouter.prototype.replace = function replace(location) {
  return originalPush.call(this, location).catch(err => err)
}

export const defaultRoutes = [
  {
    path: "/login",
    name: "Login",
    component: () => import('../views/Login.vue')
  },
  {
    path: "/register",
    name: "Register",
    component: () => import('../views/Register.vue')
  },
  {
    path: "/",
    name: "root",
    component: () => import('../views/Login.vue')
  },
  {
    path: "*",
    name: "404",
    component: () => import('../views/404.vue')
  }
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes: [
    ...defaultRoutes
  ]
});

const noPermissionPage = ['/', "/login", '/register', '/404'];

// 在 router 被导出前，添加一个路由拦截器
router.beforeEach((to, from, next) => {
  var index = noPermissionPage.indexOf(to.path);

  if (index >= 0) {
    next()
  } else
  // 当路由没被配置的时候，meta 中的 require 字段为 undefined
  {
    let userInfo = localStorage.getItem("userInfo");
    // 从 session 中获取菜单
    const menu = JSON.parse(sessionStorage.getItem('menu'));
    var mainIndex = dynamicRouteExist(router.options.routes);
    if (userInfo && menu) {
      //动态路由中不存在，添加动态路由，防止死循环
      if (!to.meta.require&&!mainIndex) {
        // 重新配置路由
        setAsyncRoutes(menu);
        //添加动态路由后 不要使用netx().使用router.replace()
        router.replace(to.path);
      } else next();
    } else {
      next({ path: '/login' })
    }

  };
});

//判断路由中是否已存在动态路由
const dynamicRouteExist = function (routes) {
  return routes.filter((route) => {
    return route.path.indexOf('/main') >= 0
  }).length > 0
}

export default router;
