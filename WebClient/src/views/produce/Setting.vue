<template>
  <el-tabs type="border-card">
    <el-tab-pane label="产线配置" class="produce-setting-tab-pane"
      ><m-production-line-setting></m-production-line-setting
    ></el-tab-pane>
    <el-tab-pane label="工序配置" class="produce-setting-tab-pane"
      ><m-produce-process></m-produce-process
    ></el-tab-pane>
  </el-tabs>
</template>
<script>
import productionLine from "./Setting/ProductionLine";
import produceProcess from "./Setting/ProduceProcess";
export default {
  components: {
    "m-production-line-setting": productionLine,
    "m-produce-process": produceProcess,
  },
};
</script>
<style>
.produce-setting-tab-pane {
  min-height: 1000px;
  padding: 0px;
}
</style>