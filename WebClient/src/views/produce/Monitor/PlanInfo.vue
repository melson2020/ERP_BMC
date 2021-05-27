<template>
  <div v-loading="loading">
    <div>
      <el-form
        class="demo-form-inline"
        label-position="left"
        label-width="80px"
      >
        <el-row :gutter="10">
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
        <el-row :gutter="10">
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
        @expand-change="expandChanged"
      >
        <el-table-column type="expand" width="50px">
          <template slot-scope="props">
            <el-table
              :data="props.row.processList"
              border
              :show-header="false"
              size="mini"
              style="width: 100%"
              class="fz8"
              stripe
            >
              <el-table-column label="名称" prop="processName" width="200px">
                <template slot-scope="scope">
                  <span>{{ scope.row.processName }}</span>
                  <el-tag
                    v-if="scope.row.delegateFlag == 'Y'"
                    type="danger"
                    size="mini"
                    class="ml10"
                    >委外</el-tag
                  >
                </template>
              </el-table-column>
              <el-table-column label="料号">
                <template slot-scope="scope">
                  <el-tag
                    class="ml10"
                    type="warning"
                    effect="plain"
                    v-for="item in scope.row.materialVos"
                    :key="item.materialNo"
                    >{{ item.materialName }}X{{ item.count }}</el-tag
                  >
                </template>
              </el-table-column>
              <el-table-column>
                <template slot-scope="scope">
                  <el-tag
                    class="ml10"
                    effect="plain"
                    v-for="item in scope.row.workStationList"
                    :key="item.workStationId"
                    >{{ item.produceLineName }}|{{ item.workStationName }}|{{
                      item.employeeGroupName
                    }}</el-tag
                  >
                </template>
              </el-table-column>
            </el-table>
          </template>
        </el-table-column>
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

    <div
      v-if="
        planInfo.pickingTicketDetails != null &&
        planInfo.pickingTicketDetails.length > 0
      "
      class="mt20"
    >
      <div class="plan-info-delegate-title-div">
        <span class="colorblue">领料详细</span>
        <span class="fz9">( {{ planInfo.pickingTicket.ticketNo }} )</span>
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
        <el-table-column label="类型" prop="type"> </el-table-column>
        <el-table-column label="是否委外" prop="delegateFlag">
        </el-table-column>
      </el-table>
    </div>

    <div
      v-if="
        planInfo.delegateDetailList != null &&
        planInfo.delegateDetailList.length > 0
      "
      class="mt20"
    >
      <div class="plan-info-delegate-title-div">
        <span class="colorblue">委外明细</span>
        <span class="fz9">( {{ planInfo.delegateTicket.ticketNo }} )</span>
      </div>
      <el-table
        :data="planInfo.delegateDetailList"
        border
        style="width: 100%"
        stripe
        size="mini"
        class="mt20 plan-detail-table"
      >
        <el-table-column label="编号" prop="objectNo"> </el-table-column>
        <el-table-column label="名称" prop="objectName"> </el-table-column>
        <el-table-column label="规格" prop="specification"> </el-table-column>
        <el-table-column label="数量" prop="count">
          <template slot-scope="scope"
            ><span
              >{{ scope.row.count }}{{ scope.row.countUnit }}</span
            ></template
          >
        </el-table-column>
        <el-table-column label="备注" prop="remark"> </el-table-column>
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
        delegateDetailList: [],
        pickingTicket: {},
        pickingTicketDetails: [],
      },
    };
  },
  methods: {
    ...mapActions({
      LoadPlanInfo: "LoadPlanInfo",
      FindPlanDetailProcessList: "FindPlanDetailProcessList",
    }),
    loadPlanInfo(planId) {
      this.loading = true;
      this.LoadPlanInfo({ planId: planId })
        .then((res) => {
          if (res.resultStatus == 1) {
            res.data.planDetails.map((item) => {
              item.processList = [];
            });
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
    expandChanged(row, sec) {
      let index = sec.indexOf(row);
      var expanded = index != -1; //获取是否展开 row为当前行
      if (expanded) {
        this.loading = true;
        console.log(row.producePlanId, row.id);
        this.FindPlanDetailProcessList({
          planId: row.producePlanId,
          planDetailId: row.id,
        })
          .then((res) => {
            if (res.resultStatus == 1) {
              this.loading = false;
              row.processList = res.data;
              console.log(row);
            } else {
              this.loading = false;
              this.$message.warning(res.message);
            }
          })
          .catch((err) => {
            this.loading = false;
            this.$message.error(err.message);
          });
      }
    },
  },
};
</script>
<style>
.plan-info-delegate-title-div {
  display: flex;
  align-items: center;
  margin-top: 10px;
}
.el-table__expanded-cell {
  padding-top: 0px !important;
  padding-right: 0px !important;
  padding-bottom: 0px !important;
}
</style>