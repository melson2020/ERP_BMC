<template>
  <div>
    <div class="plan-record-header-div">
      <span class="plan-record-header-title colorblue">计划查询</span>
      <div class="plan-record-search-div">
        <el-popover placement="left" width="500" trigger="click">
          <div class="plan-record-search-popover-items-area">
            <span class="colorblue fz6">*只查询已确认的生产计划</span>
            <el-date-picker
              v-model="searchValue.createDate"
              type="date"
              class="mt40"
              format="yyyy 年 MM 月 dd 日"
              placeholder="创建日期"
              size="small"
              value-format="yyyy-MM-dd"
            >
            </el-date-picker>
            <el-input
              placeholder="合同号"
              v-model="searchValue.contractNo"
              size="small"
              class="mt40"
            ></el-input>
            <el-input
              placeholder="订单号"
              v-model="searchValue.orderTicketNo"
              size="small"
              class="mt40"
            ></el-input>
            <el-select
              placeholder="客户名称"
              class="mt40"
              filterable
              v-model="searchValue.customerNo"
              size="small"
              :clearable="true"
            >
              <el-option
                v-for="vo in customerVoList"
                :label="vo.name"
                :value="vo.customerNo"
                :key="vo.customerNo"
              ></el-option>
            </el-select>
            <el-select
              placeholder="状态"
              class="mt40"
              filterable
              v-model="searchValue.state"
              size="small"
              :clearable="true"
            >
              <el-option value="2" label="执行中"> </el-option>
              <el-option value="3" label="已完成"> </el-option>
            </el-select>
          </div>
          <el-button slot="reference" type="primary" size="small"
            >查询选项</el-button
          >
        </el-popover>
        <el-button
          type="primary"
          @click="searchOnClick"
          size="small"
          class="ml24"
          >查询</el-button
        >
      </div>
    </div>
    <el-table :data="producePlanRecordList" 
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
      <el-table-column prop="pickingTicketNo" label="领料单号">
      </el-table-column>
      <el-table-column prop="state" label="状态">
        <template slot-scope="scope">
          <span>{{ converPlanState(scope.row.state) }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="" width="100px">
        <template slot-scope="scope">
          <el-button
            icon="el-icon-more"
            type="primary"
            size="mini"
            @click="planDetailOnClick(scope.row.id)"
            circle
          ></el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog
      title="生产计划详细"
      :visible.sync="detailInfoShowing"
      width="70%"
    >
      <m-plan-info ref="planInfoRef"></m-plan-info>
    </el-dialog>
  </div>
</template>
<script>
import { mapGetters } from "vuex";
import { mapActions } from "vuex";
import planInfo from "../Monitor/PlanInfo";
export default {
  data() {
    return {
      detailInfoShowing: false,
      searchValue: {
        createDate: "",
        contractNo: "",
        orderTicketNo: "",
        customerNo: "",
        state: "",
      },
    };
  },
  computed: {
    ...mapGetters(["producePlanRecordList", "customerVoList"]),
  },
  components: {
    "m-plan-info": planInfo,
  },
  methods: {
    ...mapActions({
      FindProducePlanRecord: "FindProducePlanRecord",
      GetCustomerVoList: "GetCustomerVoList",
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
    searchOnClick() {
      if (
        this.searchValue.createDate == "" &&
        this.searchValue.contractNo == "" &&
        this.searchValue.orderTicketNo == "" &&
        this.searchValue.customerNo == "" &&
        this.searchValue.state == ""
      ) {
        this.$message.info("请输入查询条件");
        return;
      }
      this.FindProducePlanRecord(this.searchValue);
    },
    planDetailOnClick(planId) {
      this.detailInfoShowing = !this.detailInfoShowing;
      setTimeout(() => {
        this.$refs["planInfoRef"].loadPlanInfo(planId);
      }, 200);
    },
  },
  mounted() {
    this.GetCustomerVoList();
  },
};
</script>
<style lang="less">
.plan-record-header-div {
  height: 50px;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
}
.plan-record-header-title {
  font-size: 18px;
  font-weight: bold;
}
.plan-record-search-div {
  display: flex;
  padding-bottom: 10px;
}
.plan-record-search-popover-items-area {
  display: flex;
  flex-direction: column;
  padding: 10px;
}
</style>