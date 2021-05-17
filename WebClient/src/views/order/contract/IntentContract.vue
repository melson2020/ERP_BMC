<template>
  <div class="intent-contract-main-container">
    <div class="intent-contract-header">
      <el-button
        icon="el-icon-refresh-right"
        @click="refreshIntentList"
        type="primary"
        size="small"
        >刷新</el-button
      >
      <el-input
        class="intent-fliter-input"
        size="small"
        v-model="searchValue"
        placeholder="搜索合同号 / 客户名称"
        suffix-icon="el-icon-search"
      ></el-input>
    </div>
    <el-table
      :data="intentionContractListShow"
      border=""
      stripe
      style="width: 100%"
    >
      <el-table-column prop="contractNo" label="合同号" width="180px">
      </el-table-column>
      <el-table-column prop="customerName" label="客户名称"> </el-table-column>
      <el-table-column prop="createDate" label="创建日期">
        <template slot-scope="scope">
          <span>{{ getFullTime(scope.row.createDate) }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="createEmployee" label="制单人员">
      </el-table-column>
      <el-table-column prop="remarks" label="备注"> </el-table-column>
      <el-table-column prop="type" label="状态" width="80px">
        <template slot-scope="scope">
          <span class="coloryellow">{{ getType(scope.row.type) }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="" label="操作">
        <template slot-scope="scope">
          <el-button
            icon="el-icon-more"
            type="info"
            size="mini"
            circle
            @click="detailOnClick(scope.row.id)"
          ></el-button>
          <el-button
            icon="el-icon-check"
            type="primary"
            size="mini"
            circle
            @click="approveOnClick(scope.row.id)"
          ></el-button>
          <el-button
            icon="el-icon-delete"
            type="danger"
            size="mini"
            @click="deleteOnClick(scope.row.id)"
            circle
          ></el-button>
          <el-button
            icon="el-icon-tickets"
            type="success"
            size="mini"
            @click="printOnClick(scope.row.id)"
            circle
          ></el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog title="合同详细" :visible.sync="dialogVisible" width="1100px">
      <div class="intent-contract-dialog-div">
        <m-edit-contract ref="child"></m-edit-contract>
      </div>
    </el-dialog>
    <el-dialog
      ref="dialog"
      title="合同详细"
      :visible.sync="printDialogVisible"
      width="1100px"
    >
      <m-contractTemplate ref="child"></m-contractTemplate>
    </el-dialog>
  </div>
</template>
<script>
import editContract from "./EditContract";
import contractTemplate from "./ContractTemplate";
import { mapGetters } from "vuex";
import { mapActions } from "vuex";
export default {
  name: "m-intent-constract",
  data() {
    return {
      dialogVisible: false,
      printDialogVisible: false,
      searchValue: "",
    };
  },
  computed: {
    ...mapGetters(["intentionContractList"]),
    intentionContractListShow() {
      if (this.searchValue == "") {
        return this.intentionContractList;
      } else {
        return this.intentionContractList.filter((item) => {
          let key = item.contractNo + item.customerName;
          let index = key.toUpperCase().indexOf(this.searchValue.toUpperCase());
          return index != -1;
        });
      }
    },
  },
  methods: {
    ...mapActions({
      GetIntentionContractList: "GetIntentionContractList",
      ApproveContract: "ApproveContract",
      InvalidContract: "InvalidContract",
    }),
    getFullTime(time) {
      return new Date(time).format("yyyy-MM-dd hh:mm:ss");
    },
    getType(type) {
      switch (type) {
        case "1":
          return "待确认";
        case "2":
          return "已确认";
        default:
          return "未知";
      }
    },
    detailOnClick(id) {
      this.dialogVisible = !this.dialogVisible;
      setTimeout(() => {
        this.$refs["child"].loadContract(id);
      }, 200);
    },
    approveOnClick(id) {
      this.$messageBox
        .confirm("确认转正？", "合同转正", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        })
        .then(() => {
          this.ApproveContract({ id: id });
        })
        .catch((e) => e);
    },
    deleteOnClick(id) {
      this.$messageBox
        .confirm("确认作废？", "合同作废", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "error",
        })
        .then(() => {
          this.InvalidContract({ id: id });
        })
        .catch((e) => e);
    },
    refreshIntentList() {
      this.GetIntentionContractList();
    },
    printOnClick(id) {
      this.printDialogVisible = !this.printDialogVisible;
      setTimeout(() => {
        this.$refs["child"].loadContract(id);
      }, 200);
    },
  },
  components: {
    "m-edit-contract": editContract,
      "m-contractTemplate": contractTemplate,
  },
  beforeMount() {
    this.GetIntentionContractList();
  },
};
</script>
<style>
.intent-contract-main-container {
  display: flex;
  flex-direction: column;
}
.intent-fliter-input {
  width: 400px;
  float: right;
}
.intent-contract-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding-bottom: 10px;
}
.intent-contract-submit-div {
  padding: 10px 0;
  text-align: right;
}
.intent-contract-dialog-div {
  display: flex;
  align-items: flex-start;
  justify-content: center;
}
</style>