<template>
  <div class="intent-contract-main-container">
    <div class="intent-contract-header">
      <el-input
        class="intent-fliter-input"
        size="small"
        placeholder="搜索合同号 / 客户名称"
        suffix-icon="el-icon-search"
      ></el-input>
    </div>
    <el-table :data="intentContractList" border="" stripe style="width: 100%">
      <el-table-column prop="contractNo" label="合同号"> </el-table-column>
      <el-table-column prop="vendeeName" label="客户名称"> </el-table-column>
      <el-table-column prop="createDate" label="创建日期"> </el-table-column>
      <el-table-column prop="createEmpName" label="制单人员"> </el-table-column>
      <el-table-column prop="remarks" label="备注"> </el-table-column>
      <el-table-column prop="status" label="状态"> </el-table-column>
      <el-table-column prop="" label="操作">
        <template>
          <el-button
            icon="el-icon-more"
            type="info"
            size="mini"
            circle
            @click="detailOnClick"
          ></el-button>
          <el-button
            icon="el-icon-check"
            type="primary"
            size="mini"
            circle
          ></el-button>
          <el-button
            icon="el-icon-delete"
            type="danger"
            size="mini"
            circle
          ></el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog title="合同详细" :visible.sync="dialogVisible" width="1100px">
      <div class="intent-contract-dialog-div">
        <m-edit-contract :edit="true"></m-edit-contract>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import editContract from "./EditContract";
export default {
  name: "m-intent-constract",
  data() {
    return {
      dialogVisible: false,
      intentContractList: [
        {
          contractNo: "20210415023",
          vendeeName: "桑尼泰克精密零部件有限公司",
          createDate: "2021-04-14",
          orderNo: "2021041405",
          createEmpName: "测试账户",
          status: "待确认",
        },
        {
          contractNo: "20210415025",
          vendeeName: "测试公司数据",
          createDate: "2021-04-13",
          orderNo: "2021041403",
          createEmpName: "测试账户",
          status: "待确认",
        },
        {
          contractNo: "20210416001",
          vendeeName: "测试公司数据",
          createDate: "2021-04-13",
          orderNo: "2021041402",
          createEmpName: "测试账户",
          status: "待确认",
        },
        {
          contractNo: "20210416001",
          vendeeName: "测试公司数据",
          createDate: "2021-04-13",
          orderNo: "2021041402",
          createEmpName: "测试账户",
          status: "待确认",
        },
        {
          contractNo: "20210416001",
          vendeeName: "测试公司数据",
          createDate: "2021-04-13",
          orderNo: "2021041402",
          createEmpName: "测试账户",
          status: "待确认",
        },
      ],
    };
  },
  components: {
    "m-edit-contract": editContract,
  },
  methods: {
    detailOnClick() {
      this.dialogVisible = !this.dialogVisible;
    },
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
  padding-bottom: 10px;
}
.intent-contract-submit-div{
  padding: 10px 0;
  text-align: right;
}
.intent-contract-dialog-div{
   display: flex;
  align-items: flex-start;
  justify-content: center;
}
</style>