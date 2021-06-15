<template>
  <div>
    <div class="produce-confirm-plan-header-div">
      <span class="produce-confirm-plan-header-title colorblue">待定计划</span>
      <el-select v-model="value" placeholder="计划类型" size="small">
        <el-option label="生产" value="produce"> </el-option>
        <el-option label="代加工" value="replace"> </el-option>
      </el-select>
    </div>
    <el-table :data="producePlanList" 
        border="" stripe
        size="small"
        :header-row-style="{ height: '40px' }"
        :row-style="{ height: '40px' }"
        :cell-style="{ padding: '2px', color: '#909399' }"
        :header-cell-style="{ background: '#909399', color: 'white' }">
      <el-table-column prop="planNo" label="单号"> </el-table-column>
      <el-table-column prop="customerName" label="客户名称"> </el-table-column>
      <el-table-column prop="type" label="类型">
        <template slot-scope="scope">
          <span>{{ convertPlanType(scope.row.type) }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="orderFormNo" label="订单号"> </el-table-column>
      <el-table-column prop="createDate" label="创建时间">
        <template slot-scope="scope">
          <span>{{ getFullTime(scope.row.createDate) }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="pickingTikcetNo" label="领料单号">
      </el-table-column>
      <el-table-column prop="state" label="状态">
        <template slot-scope="scope">
          <span>{{ converPlanState(scope.row.state) }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="" width="100px">
        <template slot-scope="scope">
          <el-button type="primary" @click="plneDetail(scope.row)" size="mini"
            >配置</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <el-dialog title="计划详情" :visible.sync="detailDialogVisible" width="80%">
      <m-plan-detail ref="planDetai"></m-plan-detail>
    </el-dialog>
  </div>
</template>
<script>
import planDetail from "./PlanDetail";
import { mapGetters } from "vuex";
import { mapActions } from "vuex";
export default {
  data() {
    return {
      value: "",
      detailDialogVisible: false,
    };
  },
  methods: {
    ...mapActions({
      FindUnConfirmPlanList: "FindUnConfirmPlanList",
    }),
    getFullTime(time) {
      return new Date(time).format("yyyy-MM-dd hh:mm:ss");
    },
    convertPlanType(type) {
      return type.replace("P", "生产").replace("D", "代工");
    },
    converPlanState(state) {
      switch (state) {
        case "1":
          return "待决策";
        case "2":
          return "进行中";
        case "3":
          return "已完成";
        default:
          return state;
      }
    },
    plneDetail(row) {
      this.detailDialogVisible = !this.detailDialogVisible;
      setTimeout(() => {
        this.$refs["planDetai"].loadPlanDetails(row);
      }, 200);
    },
  },
  computed: {
    ...mapGetters(["producePlanList"]),
  },
  components: {
    "m-plan-detail": planDetail,
  },
  beforeMount() {
    this.FindUnConfirmPlanList({ state: "1" });
  },
};
</script>
<style>
.produce-confirm-plan-header-div {
  height: 50px;
  display: flex;
  align-items: center;
  justify-content: space-between;
}
.produce-confirm-plan-header-title {
  font-size: 18px;
  font-weight: bold;
}
</style>