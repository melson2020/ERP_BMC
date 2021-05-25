<template>
  <el-tabs type="border-card">
    <el-tab-pane label="部门列表" class="employee-tab-pane"
      ><department-department></department-department
    ></el-tab-pane>
    <el-tab-pane label="人员列表" class="employee-tab-pane"
      ><employee-employee></employee-employee
    ></el-tab-pane>
  </el-tabs>
</template>
<script>
import Department from "./Employee/Department";
import Employee from "./Employee/Employee";

export default {
  components: {
    "department-department": Department,
    "employee-employee": Employee,
  },
};
</script>
<style>
.employee-tab-pane {
  min-height: 1000px;
  padding: 0px !important;
}
</style>