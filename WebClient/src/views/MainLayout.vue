<template>
  <el-container class="operation-main-container">
    <el-aside width="auto" class="operation-aside">
      <el-menu
        class="el-menu-vertical"
        background-color="#142d4d"
        text-color="#fff"
        router
        unique-opened
        active-text-color="#ffd04b"
        :collapse="isCollapse"
      >
        <template v-for="menu in userInfo.menuList">
          <!--第一层菜单，如果有子菜单则使用submenu 没有则使用menu-item-->
          <el-submenu
            v-if="menu.subMenuList!=null && menu.subMenuList.length > 0"
            :key="menu.id"
            :index="menu.url"
          >
            <template slot="title">
              <i :class="'el-icon-' + menu.menuIcon"></i>
              <span>{{ menu.menuName }}</span>
            </template>
            <!--第二层菜单，如果有子菜单则使用submenu 没有则使用menu-item-->
            <template v-for="subMenu in menu.subMenuList">
              <el-submenu
                v-if="subMenu.subMenuList!=null && subMenu.subMenuList.length > 0"
                class="el-submenu-item"
                :index="subMenu.url"
                :key="subMenu.id"
              >
                <template slot="title">
                  <i :class="'el-icon-' + subMenu.menuIcon"></i>
                  <span>{{ subMenu.menuName }}</span>
                </template>
                <el-menu-item
                  v-for="thirdLev in subMenu.subMenuList"
                  :index="thirdLev.url"
                  :key="thirdLev.id"
                  class="el-submenu-item"
                >
                  <i :class="'el-icon-' + thirdLev.menuIcon"></i>
                  <span slot="title">{{ thirdLev.menuName }}</span>
                </el-menu-item>
              </el-submenu>
              <el-menu-item v-else :index="subMenu.url" :key="subMenu.id">
                <i :class="'el-icon-' + subMenu.menuIcon"></i>
                <span slot="title">{{ subMenu.menuName }}</span>
              </el-menu-item>
            </template>
          </el-submenu>
          <el-menu-item
            v-else
            :index="menu.url"
            :key="menu.id"
            class="el-submenu-item"
          >
            <i :class="'el-icon-' + menu.menuIcon"></i>
            <span slot="title">{{ menu.menuName }}</span>
          </el-menu-item>
        </template>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header height="auto" class="oepration-el-header">
        <div class="operation-header-div">
          <i
            class="operation-icon operation-icon-left"
            :class="menuArrow"
            @click="menuCollapseChange"
          ></i>
          <el-dropdown trigger="click">
            <span class="operation-user-info">
              <i class="el-icon-user operation-icon"></i>
              {{ userInfo.userName }}
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item @click.native="logout">注销</el-dropdown-item>
              <el-dropdown-item @click.native="resetPass"
                >修改密码</el-dropdown-item
              >
            </el-dropdown-menu>
          </el-dropdown>

          <el-dialog
            title="重置密码"
            :visible.sync="restPassFormVisible"
            @close="dailogOnClose('resetform')"
            :close-on-click-modal="false"
            :show-close="false"
          >
            <el-form :model="systemUser" :rules="rules" ref="resetform">
              <el-form-item label="登录账户" label-width="120px" prop="loginName">
                <el-input v-model="systemUser.loginName" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="原始密码" label-width="120px" prop="oldPass">
                <el-input type="password" v-model="systemUser.oldPass" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="用户新密码" label-width="120px" prop="newPass">
                <el-input type="password" v-model="systemUser.newPass" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="确认新密码" label-width="120px" prop="checkNewPass">
                <el-input type="password" v-model="systemUser.checkNewPass" autocomplete="off"></el-input>
              </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
              <el-button @click="restPassFormVisible = false">取 消</el-button>
              <el-button type="primary" @click="resetConfirm('resetform')">确 定</el-button>
            </div>
          </el-dialog>

        </div></el-header>
      <el-main class="mainlayout-el-main"> <router-view /></el-main>
    </el-container>
  </el-container>
</template>
<script>
import { mapGetters } from "vuex";
import { mapActions } from "vuex";
export default {
  data() {
    var validateNewPass = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入密码"));
      } else {
        if (this.systemUser.checkNewPass !== "") {
          this.$refs.resetform.validateField("checkNewPass");
        }
        callback();
      }
    };
    var validateCheckNewPass = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请再次输入密码"));
      } else if (value !== this.systemUser.newPass) {
        callback(new Error("两次输入密码不一致!"));
      } else {
        callback();
      }
    };
    return { 
      menuArrow: "el-icon-s-fold", 
      isCollapse: false,
      restPassFormVisible: false,
      systemUser: {
        userId: "",
        oldPass: "",
        newPass: "",
        checkNewPass: "",
        loginName: ""
      },
      rules: {
        loginName: [
          { required: true, message: "请输入登录账户", trigger: "blur" }
        ],
        oldPass: [{ required: true, message: "请输入原始密码", trigger: "blur" }],
        newPass: [{ validator: validateNewPass, trigger: "blur" }],
        checkNewPass: [{ validator: validateCheckNewPass, trigger: "blur" }]
      }
    };
  },
  methods: {
     ...mapActions({
      SetLoginUserInfo: "SetLoginUserInfo",
      ResetPassword: "ResetPassword",

    }),
     logout: function() {
      this.$messageBox
        .confirm("确定退出？", "系统退出", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "info"
        })
        .then(() => {
          localStorage.removeItem("userInfo");
          localStorage.removeItem("menu");
          this.$router.replace({ path: "/" });
        })
        .catch(e => e);
    },
    resetPass: function() {
      this.systemUser.loginName = this.userInfo.loginName;
      this.systemUser.userId = this.userInfo.userId;
      this.restPassFormVisible = true;
    },
    menuCollapseChange() {
      this.isCollapse = !this.isCollapse;
      this.menuArrow = this.isCollapse ? "el-icon-s-unfold" : "el-icon-s-fold";
    },
    resetConfirm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          this.ResetPassword(this.systemUser)
            .then(res => {
              if (res.resultStatus == 1) {
                this.$message.success("重置成功");
                this.userInfo.loginName=this.systemUser.loginName
                // this.SetLoginStatus(this.userInfo)
                localStorage.setItem("userInfo",JSON.stringify(this.userInfo))


                this.restPassFormVisible = false;
              } else {
                this.$message.error("密码错误");
              }
            })
            .catch(error => {
              this.$message.error(error.message ? error.message : error);
            });
        } else {
          this.$message.warning("请填写准确信息");
          return false;
        }
      });
    },
  },
  computed: {
    ...mapGetters(["userInfo"]),
  },
  beforeMount() {
    // 页面刷新后，store 中的数据会丢失不见，这个时候需要从 session 中获取
    // const menu = JSON.parse(sessionStorage.getItem('menu'));
    // if(menu) this.$store.commit('SET_ROUTES', menu);
    let userInfo = localStorage.getItem("userInfo");
    if (userInfo) {
      this.SetLoginUserInfo(JSON.parse(userInfo));
    }
  },
};
</script>
<style lang="less">
.operation-main-container {
  height: 100vh;
}

.el-menu-vertical:not(.el-menu--collapse) {
  width: 15rem;
  letter-spacing: 0.2rem;
  font-size: 1.3rem;
  align-items: center;
  text-align: center;
  // font-weight: bold;
}

// .el-submenu-item {
//   background-color: #142d4d !important;
//   letter-spacing: 0.1rem;
//   font-weight: 100;
// }

// .el-submenu-item:hover {
//   outline: 0 !important;
//   background: #142d4d !important;
// }

.operation-aside {
  background: #142d4d;
}
.operation-icon-left {
  float: left;
}
.operation-icon {
  font-size: 1.8rem;
  color: rgb(180, 176, 176);
}
.operation-header-div {
  height: 3rem;
  padding: 0.2rem;
  display: flex;
  align-items: center;
  justify-content: space-between;
}
.oepration-el-header {
  border-bottom: rgb(224, 222, 222) solid 0.1px;
  padding: 0;
  margin: 0;
  padding-right: 20px;
}
.operation-user-info {
  color: #909399;
  display: flex;
  align-items: center;
  font-size: 1.1rem;
  letter-spacing: 5px;
}
.mainlayout-el-main{
  padding:0;
}
</style>