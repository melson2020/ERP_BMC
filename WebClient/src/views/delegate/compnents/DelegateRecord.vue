<template>
  <div>
    <div class="delegate-record-title-div">
      <span class="colorblue">委外单查询</span>
      <div class="delegate-record-search-div">
        <el-popover placement="left" width="500" trigger="click">
          <div class="delegate-record-search-popover-items-area">
            <span class="colorblue fz6">*只查询完成的委外单</span>
            <el-date-picker
              v-model="searchValue.delegateDate"
              type="date"
              clearable
              class="mt40"
              format="yyyy 年 MM 月 dd 日"
              placeholder="下单日期"
              size="small"
              value-format="yyyy-MM-dd"
            >
            </el-date-picker>
            <el-input
              placeholder="订单号/生产计划号"
              v-model="searchValue.sourceNo"
              size="small"
              clearable
              class="mt40"
            ></el-input>
            <el-input
              placeholder="委外单号"
              v-model="searchValue.ticketNo"
              size="small"
              clearable
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
              placeholder="供应商名称"
              class="mt40"
              filterable
              v-model="searchValue.supplyId"
              size="small"
              :clearable="true"
            >
              <el-option
                v-for="supply in supplyList"
                :label="supply.name"
                :value="supply.id"
                :key="supply.id"
              ></el-option>
            </el-select>
            <el-select
              placeholder="类型"
              class="mt40"
              filterable
              v-model="searchValue.type"
              size="small"
              :clearable="true"
            >
              <el-option value="ORDER" label="订单委外"> </el-option>
              <el-option value="PLAN" label="生产委外"> </el-option>
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
    <el-table :data="delegateRecordList" script border>
      <el-table-column prop="ticketNo" label="单号" width="150px">
      </el-table-column>
      <el-table-column prop="customerName" label="客户名称"> </el-table-column>
      <el-table-column prop="supplyName" label="供应商"> </el-table-column>
      <el-table-column prop="type" label="类型" width="100px">
        <template slot-scope="scope">
          <span>{{ convertDelegateType(scope.row.type) }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="sourceNo" label="来源单号" width="150px">
      </el-table-column>
      <el-table-column prop="delegateDate" label="下单时间" width="180px">
        <template slot-scope="scope">
          <span>{{ getFullTime(scope.row.delegateDate) }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="pickingTicketNo" label="领料单号" width="150px">
      </el-table-column>
      <el-table-column prop="inboundTicketNo" label="入库单号" width="150px">
      </el-table-column>
      <el-table-column prop="state" label="状态" width="100px">
        <template slot-scope="scope">
          <span>{{ converDelegateState(scope.row.state) }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="" width="80px">
        <template slot-scope="scope">
          <el-button
            icon="el-icon-s-release"
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
      delegateTicketInfoDialog: false,
      searchValue: {
        delegateDate: "",
        sourceNo: "",
        ticketNo: "",
        customerNo: "",
        supplyId: "",
        type: "",
      },
    };
  },
  methods: {
    ...mapActions({
      GetCustomerVoList: "GetCustomerVoList",
      GetSupplyList: "GetSupplyList",
      FindDelegateRecordList: "FindDelegateRecordList",
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
    searchOnClick() {
      if (
        this.searchValue.delegateDate == "" &&
        this.searchValue.sourceNo == "" &&
        this.searchValue.ticketNo == "" &&
        this.searchValue.customerNo == "" &&
        this.searchValue.supplyId == "" &&
        this.searchValue.type == ""
      ) {
        this.$message.info("请输入查询条件");
        return;
      }
      this.FindDelegateRecordList(this.searchValue);
    },
    detailInfoOnClick(ticketId) {
      this.delegateTicketInfoDialog = !this.delegateTicketInfoDialog;
      setTimeout(() => {
        this.$refs["delegateTciketInfo"].loadDelegateInfo(ticketId);
      }, 200);
    },
  },
  computed: {
    ...mapGetters(["customerVoList", "supplyList", "delegateRecordList"]),
  },
  components: {
    "m-delegate-ticket-info": delegateTicketInfo,
  },

  mounted() {
    this.GetCustomerVoList();
    this.GetSupplyList();
  },
};
</script>
<style>
.delegate-record-title-div {
  display: flex;
  align-items: center;
  height: 50px;
  justify-content: space-between;
}
.delegate-record-search-div {
  display: flex;
  padding-bottom: 10px;
}
.delegate-record-search-popover-items-area {
  display: flex;
  flex-direction: column;
  padding: 10px;
}
</style>