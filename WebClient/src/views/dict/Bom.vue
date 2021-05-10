<template>
  <el-tabs type="border-card">
    <el-tab-pane label="产品BoM表" class="customer-tab-pane"
      ><bom-Bom></bom-Bom
    ></el-tab-pane>
    <el-tab-pane label="基础物料表" class="customer-tab-pane"
      ><bom-Material></bom-Material
    ></el-tab-pane>
  </el-tabs>
</template>
<script>
import Bom from "./Bom/Bom";
import Material from "./Bom/Material";

export default {
  components: {
    "bom-Bom": Bom,
    "bom-Material": Material,
    // "customer-deliverAddress": DeliverAddress,
  },
};
</script>
<style>
.customer-tab-pane {
  min-height: 1000px;
  padding: 0px;
}
</style>