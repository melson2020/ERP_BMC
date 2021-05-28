<template>
  <el-tabs type="border-card" v-model="tabName">
    <el-tab-pane label="委外下达" class="delegate-tab-pane"
      ><m-delegate-release></m-delegate-release
    ></el-tab-pane>
    <el-tab-pane label="委外执行中" class="delegate-tab-pane"
      ><m-delegate-processing></m-delegate-processing
    ></el-tab-pane>
    <el-tab-pane label="委外查询" class="delegate-tab-pane"
      ><m-delegate-record></m-delegate-record
    ></el-tab-pane>
  </el-tabs>
</template>
<script>
import record from "./compnents/DelegateRecord";
import release from "./compnents/DelegateRelease";
import processing from "./compnents/DelegateProcessing";
export default {
  data() {
    return {
      tabName: "",
    };
  },
  components: {
    "m-delegate-release": release,
    "m-delegate-record": record,
    "m-delegate-processing": processing,
  },
};
</script>
<style>
.delegate-tab-pane {
  min-height: 1000px;
}
</style>