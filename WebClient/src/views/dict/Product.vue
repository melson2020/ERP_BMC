<template>
  <el-tabs type="border-card">
    <el-tab-pane label="产品列表" class="customer-tab-pane"
      ><product-product></product-product
    ></el-tab-pane>
    <el-tab-pane label="产品BOM表" class="customer-tab-pane"
      ><product-productBom></product-productBom
    ></el-tab-pane>
    <!-- <el-tab-pane label="送货地址" class="customer-tab-pane"
      ><customer-deliverAddress></customer-deliverAddress
    ></el-tab-pane> -->
  </el-tabs>
</template>
<script>
import Product from "./Product/Product";
import ProductBom from "./Product/ProductBom";

export default {
  components: {
    "product-product": Product,
    "product-productBom": ProductBom,
  },
};
</script>
<style>
.customer-tab-pane {
  min-height: 1000px;
  padding: 0px;
}
</style>