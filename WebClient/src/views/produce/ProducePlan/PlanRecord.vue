<template>
  <div>
    <div class="plan-record-header-div">
      <span class="plan-record-header-title colorblue">计划查询</span>
      <div class="plan-record-search-div">
        <el-popover placement="left" width="500" trigger="click">
          <div class="plan-record-search-popover-items-area">
            <el-date-picker
              v-model="searchValue.createDate"
              type="date"
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
        <el-button type="primary" size="small" class="ml24">查询</el-button>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      searchValue: {
        createDate: "",
        contractNo: "",
        orderTicketNo: "",
        customerName: "",
        state: "",
      },
    };
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