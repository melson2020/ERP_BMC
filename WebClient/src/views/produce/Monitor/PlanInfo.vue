<template>
  <div v-loading="loading">
    <div>
      <el-form
        class="demo-form-inline"
        label-position="left"
        label-width="80px"
      >
        <el-row gutter="10">
          <el-col :span="6">
            <el-form-item label="客户名称">
              <el-input
                v-model="planInfo.producePlan.customerName"
              ></el-input></el-form-item
          ></el-col>
          <el-col :span="6">
            <el-form-item label="创建时间">
              <el-input
                v-model="planInfo.producePlan.createDate"
              ></el-input> </el-form-item
          ></el-col>
          <el-col :span="6">
            <el-form-item label="计划结束">
              <el-input
                v-model="planInfo.producePlan.endDate"
              ></el-input> </el-form-item
          ></el-col>
          <el-col :span="6">
            <el-form-item label="计划类型">
              <el-input
                v-model="planInfo.producePlan.type"
              ></el-input> </el-form-item
          ></el-col>
        </el-row>
        <el-row gutter="10">
          <el-col :span="6">
            <el-form-item label="订单编号">
              <el-input
                v-model="planInfo.producePlan.orderFormNo"
              ></el-input> </el-form-item
          ></el-col>
          <el-col :span="6">
            <el-form-item label="合同编号">
              <el-input
                v-model="planInfo.producePlan.contractNo"
              ></el-input> </el-form-item
          ></el-col>
          <el-col :span="6">
            <el-form-item label="领料单号">
              <el-input
                v-model="planInfo.producePlan.pickingTicketNo"
              ></el-input> </el-form-item
          ></el-col>
          <el-col :span="6">
            <el-form-item label="计划单号">
              <el-input
                v-model="planInfo.producePlan.planNo"
              ></el-input> </el-form-item
          ></el-col>
        </el-row>
      </el-form>
    </div>

    <div>
      <span class="colorblue fl mb40 fz9">生产详细</span>
      <el-table
        :data="planInfo.planDetails"
        border
        style="width: 100%"
        stripe
        size="mini"
        class="mt20 plan-detail-table"
      >
        <el-table-column label="名称" prop="productName"> </el-table-column>
        <el-table-column label="规格" prop="specification"> </el-table-column>
        <el-table-column label="数量" prop="count">
          <template slot-scope="scope"
            ><span
              >{{ scope.row.count }}{{ scope.row.countUnit }}</span
            ></template
          >
        </el-table-column>
        <el-table-column label="备注" prop="remark"> </el-table-column>
        <el-table-column label="类型" prop="produceType"> </el-table-column>
      </el-table>
    </div>

    <div v-if="planInfo.pickingTicketDetails.length > 0" class="mt20">
      <div class="plan-info-delegate-title-div">
        <span class="colorblue">领料详细</span>
        <span class="fz9">( {{planInfo.pickingTicket.ticketNo}} )</span>
      </div>
      <el-table
        :data="planInfo.pickingTicketDetails"
        border
        style="width: 100%"
        stripe
        size="mini"
        class="mt20 plan-detail-table"
      >
        <el-table-column label="编号" prop="materialNo"> </el-table-column>
        <el-table-column label="名称" prop="materialName"> </el-table-column>
        <el-table-column label="规格" prop="specification"> </el-table-column>
        <el-table-column label="数量" prop="count">
          <template slot-scope="scope"
            ><span
              >{{ scope.row.count }}{{ scope.row.countUnit }}</span
            ></template
          >
        </el-table-column>
        <el-table-column label="备注" prop="remark"> </el-table-column>
        <el-table-column label="是否委外" prop="delegateFlag">
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>
<script>
import { mapActions } from "vuex";
export default {
  data() {
    return {
      loading: false,
      planInfo: {
        producePlan: {},
        planDetails: [],
        delegateTicket: {},
        pickingTicket: {},
        pickingTicketDetails: [],
      },
    };
  },
  methods: {
    ...mapActions({
      LoadPlanInfo: "LoadPlanInfo",
    }),
    loadPlanInfo(planId) {
      this.loading = true;
      this.LoadPlanInfo({ planId: planId })
        .then((res) => {
          if (res.resultStatus == 1) {
            this.planInfo = res.data;
            this.loading = false;
          } else {
            this.$message.warning(res.message);
            this.loading = false;
          }
        })
        .catch((err) => {
          this.$message.error(err.message);
          this.loading = false;
        });
    },
  },
};
</script>
<style>
.plan-info-delegate-title-div{
    display: flex;
    align-items: center;
    margin-top: 10px;
}
</style>