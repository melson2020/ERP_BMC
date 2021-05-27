<template>
     <el-tabs
    type="border-card"
    v-model="tabName"
  >
    <el-tab-pane label="委外下达" class="delegate-tab-pane"
      ><m-delegate-release></m-delegate-release
    ></el-tab-pane>
    <el-tab-pane label="委外记录" class="delegate-tab-pane"
      ><m-delegate-record></m-delegate-record
    ></el-tab-pane>
  </el-tabs>
</template>
<script>
import record from './compnents/DelegateRecord'
import release from './compnents/DelegateRelease'
export default {
    data(){
        return{
            tabName:''
        }
    },
     components: {
    "m-delegate-release": release,
    "m-delegate-record": record,
  },
}
</script>
<style>
.delegate-tab-pane {
  min-height: 1000px;
}
</style>