<template>
  <el-tabs type="border-card">
    <el-tab-pane label="意向合同" class="contract-tab-pane"
      ><m-intent-constract></m-intent-constract
    ></el-tab-pane>
    <el-tab-pane label="合同查询" class="contract-tab-pane"
      >合同查询</el-tab-pane
    >
    <el-tab-pane label="新建意向合同" class="contract-tab-pane"
      ><m-create-intent-constract></m-create-intent-constract></el-tab-pane
    >
  </el-tabs>
</template>
<script>
import intentContract from "../../components/order/IntentContract";
import createIntentConstract from "../../components/order/CreateIntentContract";
export default {
  components: {
    'm-intent-constract':intentContract,
    'm-create-intent-constract':createIntentConstract,
  },
};
</script>
<style>
.contract-tab-pane {
  min-height: 1000px;
}
</style>