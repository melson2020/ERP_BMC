<template>
  <el-container class="operation-main-container">
    <el-aside width="auto" class="operation-aside">
      <el-menu
        default-active="2"
        class="el-menu-vertical"
        @open="handleOpen"
        @close="handleClose"
        background-color="#142d4d"
        text-color="#fff"
        active-text-color="#ffd04b"
        :collapse="isCollapse"
      >
        <el-submenu index="1">
          <template slot="title">
            <i class="el-icon-location"></i>
            <span>导航一</span>
          </template>
          <el-menu-item-group>
            <template slot="title">分组一</template>
            <el-menu-item index="1-1">选项1</el-menu-item>
            <el-menu-item index="1-2">选项2</el-menu-item>
          </el-menu-item-group>
          <el-menu-item-group title="分组2">
            <el-menu-item index="1-3">选项3</el-menu-item>
          </el-menu-item-group>
          <el-submenu index="1-4">
            <template slot="title">选项4</template>
            <el-menu-item index="1-4-1">选项1</el-menu-item>
          </el-submenu>
        </el-submenu>
        <el-menu-item index="2">
          <i class="el-icon-menu"></i>
          <span slot="title">导航二</span>
        </el-menu-item>
        <el-menu-item index="3" disabled>
          <i class="el-icon-document"></i>
          <span slot="title">导航三</span>
        </el-menu-item>
        <el-menu-item index="4">
          <i class="el-icon-setting"></i>
          <span slot="title">导航四</span>
        </el-menu-item>
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
               登录用户
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item @click.native="logout">注销</el-dropdown-item>
              <el-dropdown-item @click.native="resetPass">修改密码</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
          </div></el-header>
      <el-main> <router-view /></el-main>
    </el-container>
  </el-container>
</template>
<script>
export default {
  data() {
    return { menuArrow: "el-icon-s-fold", isCollapse: false };
  },
  methods: {
    menuCollapseChange() {
      this.isCollapse = !this.isCollapse;
      this.menuArrow = this.isCollapse ? "el-icon-s-unfold" : "el-icon-s-fold";
    },
  },
};
</script>
<style lang="less">
.operation-main-container {
  height: 100vh;
}

.el-menu-vertical:not(.el-menu--collapse) {
  width: 18rem;
  letter-spacing: 0.1rem;
  font-size: 1.3rem;
  font-weight: bold;
}

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
</style>