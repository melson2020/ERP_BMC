<template>
  <div class="contract-search-main-container">
    <div class="contract-search-div">
      <el-button type="primary" size="small" class="ml24">查询</el-button>
      <el-popover placement="left" width="500" trigger="click">
        <div class="contract-search-popover-items-area">
          <el-date-picker type="date" placeholder="选择日期" size="small">
          </el-date-picker>
          <el-input placeholder="合同号" size="small" class="mt40"></el-input>
          <el-input placeholder="订单号" size="small" class="mt40"></el-input>
          <el-select
            v-model="customerId"
            placeholder="制单人"
            class="mt40"
            filterable
            size="small"
            :clearable="true"
          >
            <el-option value="1" label="张某某"> </el-option>
            <el-option value="2" label="李某某"> </el-option>
          </el-select>
          <el-select
            placeholder="状态"
            class="mt40"
            filterable
            size="small"
            :clearable="true"
          >
            <el-option value="finished" label="已完成"> </el-option>
            <el-option value="processing" label="进行中"> </el-option>
          </el-select>
        </div>
        <el-button slot="reference" type="primary" size="small"
          >查询选项</el-button
        >
      </el-popover>
    </div>
    <el-table :data="contractList" border stripe style="width: 100%">
      <el-table-column prop="contractNo" label="合同号"> </el-table-column>
      <el-table-column prop="orderTicketNo" label="订单号"> </el-table-column>
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
            @click="detailOnClick"
            circle
          ></el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog title="合同详细" :visible.sync="dialogVisible" width="1100px">
      <m-contractTemplate></m-contractTemplate>
    </el-dialog>
  </div>
</template>
<script>
import contractTemplate from "../order/ContractTemplate";
export default {
  data() {
    return {
      dialogVisible: false,
      contractList: [
        {
          contractNo: "20210415023",
          orderTicketNo: "订单号01",
          vendeeName: "桑尼泰克精密零部件有限公司",
          createDate: "2021-04-14",
          orderNo: "2021041405",
          createEmpName: "测试账户",
          status: "进行中",
        },
        {
          contractNo: "20210415025",
          orderTicketNo: "订单号02",
          vendeeName: "测试公司数据",
          createDate: "2021-04-13",
          orderNo: "2021041403",
          createEmpName: "测试账户",
          status: "进行中",
        },
        {
          contractNo: "20210416001",
          orderTicketNo: "订单号03",
          vendeeName: "测试公司数据",
          createDate: "2021-04-13",
          orderNo: "2021041402",
          createEmpName: "测试账户",
          status: "已完成",
        },
        {
          contractNo: "20210416001",
          orderTicketNo: "订单号04",
          vendeeName: "测试公司数据",
          createDate: "2021-04-13",
          orderNo: "2021041402",
          createEmpName: "测试账户",
          status: "已完成",
        },
        {
          contractNo: "20210416001",
          orderTicketNo: "订单号05",
          vendeeName: "测试公司数据",
          createDate: "2021-04-13",
          orderNo: "2021041402",
          createEmpName: "测试账户",
          status: "进行中",
        },
      ],
    };
  },
  components: {
    "m-contractTemplate": contractTemplate,
  },
  methods: {
    detailOnClick() {
      this.dialogVisible = !this.dialogVisible;
    },
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