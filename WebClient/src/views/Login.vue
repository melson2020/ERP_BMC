<template>
  <div class="login-container" :style="loginBackground">
    <el-col :span="14"
      ><div><span class="fz24 colorF">数 据 驱 动 生 产</span></div></el-col
    >
    <el-col :span="10"
      ><div class="login-box-card">
        <div class="login-box-card-header">
          <span class="fz20 color9">用户登录</span>
        </div>
        <div class="login-box-card-content">
          <el-form label-position="right" label-width="0" :model="loginUser">
            <el-form-item>
              <el-input
                class="login-input placeHolder-gray"
                v-model="loginUser.loginName"
                prefix-icon="el-icon-user"
                size="large"
                placeholder="用 户 名"
              ></el-input>
            </el-form-item>
            <el-form-item>
              <el-input
                type="password"
                class="login-input placeHolder-gray"
                v-model="loginUser.password"
                prefix-icon="el-icon-lock"
                size="large"
                placeholder="密 码"
                @keydown.enter.native="onSubmit"
              ></el-input>
            </el-form-item>
            <el-form-item>
              <el-button
                class="login-submit-button"
                type="primary"
                @click="onSubmit"
                @keydown.enter="onSubmit"
                >登录</el-button
              >
            </el-form-item>
          </el-form>
          <!-- <el-button type="text" @click="goRegister">无账户? 注册</el-button> -->
        </div>
      </div></el-col
    >
  </div>
</template>
<script>
import { mapGetters } from "vuex";
import { mapActions } from "vuex";
export default {
  data() {
    return {
      loginUser: {},
      loginBackground: {
        backgroundImage:
          "url(" + require("../assets/login-background-1.jpg") + ")",
        backgroundRepeat: "no-repeat",
      },
    };
  },
  methods: {
    ...mapActions({
      SystemLogin: "SystemLogin",
      SetLoginUserInfo: "SetLoginUserInfo",
      ReSetAllStates: "ReSetAllStates",
      SetRoutes: "SetRoutes",
    }),
    goRegister() {
      this.$router.push({ path: "/register" });
    },
    onSubmit() {
      this.SystemLogin(this.loginUser)
        .then((res) => {
          if (res.resultStatus == 1) {
            var initState = JSON.parse(localStorage.getItem("initState"));
            console.log('存入缓存',res.data)
            localStorage.setItem("userInfo", JSON.stringify(res.data));
            this.ReSetAllStates(initState);
            this.SetLoginUserInfo(res.data);
            this.addRoutes(res.data.menuList);
          } else {
            this.$message.error(res.message);
          }
        })
        .catch((error) => {
          this.$message.error(error.message);
        });
    },
    //根据用户menuList 生成对应的routes 添加进路由中
    addRoutes(userMenuList) {
      const userRoutes = [];
      var mainRoute = {
        path: "/main",
        name: "MainLayout",
        meta: {
          require: false,
        },
        componentPath: "MainLayout.vue",
        children: [],
      };
      userMenuList.map((firstItem) => {
        var route = {
          path: firstItem.path,
          name: firstItem.name,
          meta: {
            require: false,
          },
          componentPath: firstItem.viewPath
        };
        if (firstItem.subMenuList.length > 0) {
          route.children = this.getChildren(firstItem);
        }
        mainRoute.children.push(route);
      });
      userRoutes.push(mainRoute);
      this.$store.commit("SET_ROUTES", userRoutes);
      this.$router.push({ path: "/main" });
    },

    getChildren(menu) {
      var children = [];
      menu.subMenuList.map((subMenu) => {
        var subRoute = {
          path: subMenu.path,
          name: subMenu.name,
          meta: {
            require: false,
          },
          componentPath:subMenu.viewPath,
        };
        children.push(subRoute);
        if (subMenu.subMenuList.length > 0) {
          subRoute.children = this.getChildren(subMenu);
        }
      });
      return children;
    },
  },
  computed: {
    ...mapGetters(["userInfo"]),
  },
  created(){
    console.log('登录页面')
  }
};
</script>
<style lang="less">
.login-container {
  min-height: 100vh;
  min-width: 100vw;
  height: auto;
  width: auto;
  margin: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  background-size: 100% 100%;
}
.login-box-card {
  max-width: 30rem;
  width: auto;
  border-radius: 0.5rem;
  background: rgba(0, 0, 0, 0.4);
  display: flex;
  flex-direction: column;
  height: auto;
  border: 0.1px solid #79bbff;
}
.login-box-card-header {
  padding: 1rem;
  letter-spacing: 0.5rem;
}
.login-box-card-content {
  padding: 3rem;
}
.login-submit-button {
  font-size: 1.3rem;
  width: 100%;
  letter-spacing: 0.5rem;
}
.login-input {
  font-size: 1.2rem;
  letter-spacing: 0.2rem;
}
.login-input .el-input__inner {
  background: rgba(31, 30, 30, 0.1) !important;
  color: white;
  border: 0.1px solid lightgray !important;
}
.placeHolder-gray {
  /deep/ .el-input__inner::-webkit-input-placeholder {
    color: #666;
  }
}
</style>