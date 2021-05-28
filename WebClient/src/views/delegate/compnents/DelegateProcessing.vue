<template>
  <div>
    <el-row :gutter="10" class="delegate-process-summary-div">
      <el-col :span="6">
        <div class="delegate-process-summary-box">
          <span class="delegate-process-summary-card-title color-gray"
            >生产委外</span
          >
          <span class="delegate-process-Summary-card-content colorred">
            {{ SummaryCount.planCount }}</span
          >
        </div>
      </el-col>
      <el-col :span="6">
        <div class="delegate-process-summary-box">
          <span class="delegate-process-summary-card-title color-gray"
            >订单委外</span
          >
          <span class="delegate-process-Summary-card-content colorred">
            {{ SummaryCount.orderCount }}</span
          >
        </div>
      </el-col>
      <el-col :span="6">
        <div class="delegate-process-summary-box">
          <span class="delegate-process-summary-card-title color-gray"
            >已下单</span
          >
          <span class="delegate-process-Summary-card-content colorred">
            {{ SummaryCount.processCount }}</span
          >
        </div>
      </el-col>
      <el-col :span="6">
        <div class="delegate-process-summary-box">
          <span class="delegate-process-summary-card-title color-gray"
            >已领料</span
          >
          <span class="delegate-process-Summary-card-content colorred">
            {{ SummaryCount.pickingCount }}</span
          >
        </div>
      </el-col>
    </el-row>

    <div class="delegate-process-table-title-div">
      <span class="colorblue">委外单</span>
      <div>
        <el-select
          v-model="delegateType"
          placeholder="选择类型"
          size="mini"
          clearable
        >
          <el-option label="订单委外" value="ORDER"> </el-option>
          <el-option label="生产委外" value="PLAN"> </el-option>
        </el-select>
        <el-select
          v-model="delegateState"
          placeholder="选择状态"
          size="mini"
          class="ml40"
          clearable
        >
          <el-option label="已下单" value="2"> </el-option>
          <el-option label="已领料" value="3"> </el-option>
        </el-select>
        <el-button
          type="primary"
          size="mini"
          icon="el-icon-refresh-right"
          @click="refreshOnClick"
          class="ml40"
          >刷新</el-button
        >
      </div>
    </div>

    <el-table :data="delegateProcessingListshow" script border>
      <el-table-column prop="ticketNo" label="单号"> </el-table-column>
      <el-table-column prop="customerName" label="客户名称"> </el-table-column>
      <el-table-column prop="type" label="类型">
        <template slot-scope="scope">
          <span>{{ convertDelegateType(scope.row.type) }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="sourceNo" label="来源单号"> </el-table-column>
      <el-table-column prop="createDate" label="创建时间">
        <template slot-scope="scope">
          <span>{{ getFullTime(scope.row.createDate) }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="state" label="状态">
        <template slot-scope="scope">
          <span>{{ converDelegateState(scope.row.state) }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="" width="100px">
        <template slot-scope="scope">
          <el-button
            icon="el-icon-more"
            type="primary"
            size="mini"
            @click="detailInfoOnClick(scope.row.id)"
            circle
          ></el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog
      title="委外详细"
      :visible.sync="delegateTicketInfoDialog"
      width="70%"
    >
      <m-delegate-ticket-info ref="delegateTciketInfo"></m-delegate-ticket-info>
    </el-dialog>
  </div>
</template>
<script>
import { mapGetters } from "vuex";
import { mapActions } from "vuex";
import delegateTicketInfo from "./DelegateTciketDetail";
export default {
  data() {
    return {
      delegateType: "",
      delegateState: "",
      delegateTicketInfoDialog: false,
    };
  },
  methods: {
    ...mapActions({
      FindDelegateProcessingList: "FindDelegateProcessingList",
    }),
    getFullTime(time) {
      return new Date(time).format("yyyy-MM-dd hh:mm:ss");
    },
    convertDelegateType(type) {
      switch (type) {
        case "ORDER":
          return "订单委外";
        case "PLAN":
          return "生产委外";
        default:
          return type;
      }
    },
    converDelegateState(state) {
      switch (state) {
        case "1":
          return "待下达";
        case "2":
          return "已下单";
        case "3":
          return "已领料";
        case "4":
          return "已完成";
        default:
          return state;
      }
    },
    detailInfoOnClick(ticketId) {
      this.delegateTicketInfoDialog = !this.delegateTicketInfoDialog;
      setTimeout(() => {
        this.$refs["delegateTciketInfo"].loadDelegateInfo(ticketId);
      }, 200);
    },
    refreshOnClick() {
      this.FindDelegateProcessingList();
    },
  },
  computed: {
    ...mapGetters(["delegateProcessingList"]),
    delegateProcessingListshow() {
      return this.delegateProcessingList.filter((item) => {
        var existState = false;
        if (this.delegateState == "") {
          existState = true;
        } else {
          existState = item.state.indexOf(this.delegateState) != -1;
        }
        var existType = false;
        if (this.delegateType == "") {
          existType = true;
        } else {
          existType = item.type.indexOf(this.delegateType) != -1;
        }
        return existState && existType;
      });
    },

    SummaryCount() {
      let summary = {
        planCount: 0,
        orderCount: 0,
        processCount: 0,
        pickingCount: 0,
      };
      this.delegateProcessingList.map((item) => {
        if (item.type == "ORDER") {
          summary.orderCount = this.$my.NumberAdd(summary.orderCount, 1);
        } else {
          summary.planCount = this.$my.NumberAdd(summary.planCount, 1);
        }
        if (item.state == "2") {
          summary.processCount = this.$my.NumberAdd(summary.processCount, 1);
        } else {
          summary.pickingCount = this.$my.NumberAdd(summary.pickingCount, 1);
        }
      });
      return summary;
    },
  },

  components: {
    "m-delegate-ticket-info": delegateTicketInfo,
  },

  mounted() {
    this.FindDelegateProcessingList();
  },
};
</script>
<style>
.delegate-process-summary-div {
  display: flex;
  flex-direction: row;
  height: 80px;
}
.delegate-process-summary-box {
  height: 100%;
  width: 100%;
  border-radius: 5px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
  display: flex;
  flex-direction: row;
  align-items: center;
  border-radius: 0.3rem;
  border: 1px solid rgb(238, 234, 234);
}
.delegate-process-summary-card-title {
  width: 150px;
  font-size: 1.3rem;
  font-weight: 600;
}
.delegate-process-Summary-card-content {
  width: 100%;
  font-size: x-large;
}
.delegate-process-table-title-div {
  display: flex;
  padding-top: 20px;
  padding-bottom: 10px;
  align-items: center;
  justify-content: space-between;
}
</style>