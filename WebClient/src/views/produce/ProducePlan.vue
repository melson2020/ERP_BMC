<template>
  <div>
    <el-tabs type="border-card">
      <el-tab-pane label="待定计划" class="produce-plan-tab-pane"
        ><m-unconfirm-plan></m-unconfirm-plan
      ></el-tab-pane>
      <el-tab-pane label="计划查询" class="produce-plan-tab-pane"
        ><m-plan-record></m-plan-record
      ></el-tab-pane>
    </el-tabs>
  </div>
</template>
<script>
import unConfirmPlane from "./ProducePlan/UnConfirmPlan";
import planRecord from "./ProducePlan/PlanRecord";
export default {
  data() {
    return {};
  },

  components: {
    "m-unconfirm-plan": unConfirmPlane,
    "m-plan-record": planRecord,
  },
};
</script>
<style>
.produce-plan-tab-pane {
  min-height: 1000px;
  padding: 0px;
}
</style>