<template>
  <el-tabs type="border-card">
    <el-tab-pane label="采购申请列表" class="purchase-tab-pane"
      ><purchase-pr></purchase-pr
    ></el-tab-pane>
    <el-tab-pane label="采购订单列表" class="purchase-tab-pane"
      ><purchase-po></purchase-po
    ></el-tab-pane>
  </el-tabs>
</template>
<script>
import PR from "./PrDetail.vue";
import PO from "./PoDetail.vue";

export default {
  components: {
    "purchase-pr": PR,
    "purchase-po": PO,
  },
};
</script>
<style>
.purchase-tab-pane {
  min-height: 1000px;
  padding: 0px !important;
}
</style>