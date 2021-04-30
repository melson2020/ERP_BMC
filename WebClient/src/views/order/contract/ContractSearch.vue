<template>
  <div class="contract-search-main-container">
    <div class="contract-search-div">
      <el-button type="primary" size="small" class="ml24" @click="searchOnClick"
        >查询</el-button
      >
      <el-popover placement="left" width="500" trigger="click">
        <div class="contract-search-popover-items-area">
          <el-date-picker
            v-model="searchValue.formalDate"
            type="date"
            format="yyyy 年 MM 月 dd 日"
            placeholder="选择日期"
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
            v-model="searchValue.customerName"
            size="small"
            :clearable="true"
          >
            <el-option
              v-for="vo in customerVoList"
              :label="vo.name"
              :value="vo.name"
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
            <el-option value="3" label="已完成"> </el-option>
            <el-option value="2" label="进行中"> </el-option>
          </el-select>
        </div>
        <el-button slot="reference" type="primary" size="small"
          >查询选项</el-button
        >
      </el-popover>
    </div>
    <el-table :data="formalContractList" border stripe style="width: 100%">
      <el-table-column prop="contractNo" label="合同号"> </el-table-column>
      <el-table-column prop="orderTicketNo" label="订单号"> </el-table-column>
      <el-table-column prop="customerName" label="客户名称"> </el-table-column>
      <el-table-column prop="formalDate" label="合同确认日期">
        <template slot-scope="scope">
          <span>{{getFullTime(scope.row.formalDate)}}</span>
        </template>
      </el-table-column>
      <el-table-column prop="createEmployee" label="制单人员">
      </el-table-column>
      <el-table-column prop="remarks" label="备注"> </el-table-column>
      <el-table-column prop="state" label="状态" width="80px">
        <template slot-scope="scope">
          <span class="coloryellow">{{ getState(scope.row.state) }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="" label="操作" width="100px">
        <template slot-scope="scope">
          <el-button
            icon="el-icon-more"
            type="info"
            size="mini"
            @click="detailOnClick(scope.row.id)"
            circle
          ></el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog ref="dialog" title="合同详细" :visible.sync="dialogVisible" width="1100px">
      <m-contractTemplate ref="child"></m-contractTemplate>
    </el-dialog>
  </div>
</template>
<script>
import contractTemplate from "./ContractTemplate";
import { mapGetters } from "vuex";
import { mapActions } from "vuex";
export default {
  data() {
    return {
      dialogVisible: false,
      searchValue: {
        formalDate: "",
        contractNo: "",
        orderTicketNo: "",
        customerName: "",
        state: "",
      },
    };
  },
  computed: {
    ...mapGetters(["formalContractList", "customerVoList"]),
  },
  components: {
    "m-contractTemplate": contractTemplate,
  },
  methods: {
    ...mapActions({
      GetFormalContractList: "GetFormalContractList",
      GetCustomerVoList: "GetCustomerVoList",
    }),
    getFullTime(time) {
      return new Date(time).format("yyyy-MM-dd hh:mm:ss");
    },
    detailOnClick(id) {
      this.dialogVisible=!this.dialogVisible
      setTimeout(()=>{ this.$refs['child'].loadContract(id)},200)
    },
    searchOnClick() {
      if (
        this.searchValue.formalDate == "" &&
        this.searchValue.contractNo == "" &&
        this.searchValue.orderTicketNo == "" &&
        this.searchValue.customerName == "" &&
        this.searchValue.state == ""
      )
        return;
      this.GetFormalContractList(this.searchValue);
    },

    getState(state) {
      switch (state) {
        case "0":
          return "已作废";
        case "1":
          return "待处理";
        case "2":
          return "进行中";
        case "3":
          return "意完成";
        default:
          return "未知";
      }
    },
  },
  beforeMount() {
    var param = { formalDate: new Date().format("yyyy-MM-dd") };
    this.GetFormalContractList(param);
    this.GetCustomerVoList();
  },
};
</script>
<style>
.contract-search-div {
  display: flex;
  flex-direction: row-reverse;
  padding-bottom: 10px;
}
.content {
  display: flex;
  flex-direction: column;
  letter-spacing: 2px;
}
.contract-search-main-container {
  display: flex;
  flex-direction: column;
}
.contract-search-popover-items-area {
  display: flex;
  flex-direction: column;
  padding: 10px;
}
</style>