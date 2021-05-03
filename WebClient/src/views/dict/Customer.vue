<template>
  <el-tabs type="border-card">
    <el-tab-pane label="客户列表" class="customer-tab-pane"
      ><customer-customer></customer-customer
    ></el-tab-pane>
    <el-tab-pane label="联系人列表" class="customer-tab-pane"
      ><customer-contact></customer-contact
    ></el-tab-pane>
    <el-tab-pane label="送货地址" class="customer-tab-pane"
      ><customer-deliverAddress></customer-deliverAddress
    ></el-tab-pane>
  </el-tabs>
</template>
<script>
import Customer from "./Customer/Customer";
import Contact from "./Customer/Contact";
import DeliverAddress from "./Customer/DeliverAddress";

export default {
  components: {
    "customer-customer": Customer,
    "customer-contact": Contact,
    "customer-deliverAddress": DeliverAddress,
  },
};
</script>
<style>
.customer-tab-pane {
  min-height: 1000px;
  padding: 0px;
}
</style>