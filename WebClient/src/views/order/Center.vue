<template>
  <div class="center-main-div">
    <el-row :gutter="10">
      <el-col v-for="summary in sumarys" :span="6" :key="summary.number"
        ><div class="center-summary-card color-bk-gray">
          <span class="center-Summary-card-title color-gray">{{
            summary.title
          }}</span>
          <el-tooltip effect="light" content="点击查看详细" placement="top">
            <el-button class="center-Summary-card-content colorred" type="text">
              {{ summary.number }}
            </el-button>
          </el-tooltip>
        </div>
      </el-col>
    </el-row>
    <div class="center-dashboard-div">
      <el-row :gutter="10"
        ><el-col :span="6"><div class="center-dashborad-card"></div></el-col>
        <el-col :span="18"
          ><div class="center-table-card">
            <div class="table-header-div">
              <span>待处理订单</span>
              <div>
                <el-select placeholder="请选择">
                  <el-option
                    v-for="item in oderStateList"
                    :key="item.value"
                    :label="item.name"
                    :value="item.value"
                  >
                  </el-option>
                </el-select>
                <el-select placeholder="请选择" class="ml24">
                  <el-option
                    v-for="item in oderTypeList"
                    :key="item.value"
                    :label="item.name"
                    :value="item.value"
                  >
                  </el-option>
                </el-select>
              </div>
            </div>
            <el-table border :data="orderList" stripe style="width: 100%">
              <el-table-column prop="no" label="订单号"> </el-table-column>
              <el-table-column prop="customerName" label="客户" width="180">
              </el-table-column>
              <el-table-column prop="date" label="日期" width="180">
              </el-table-column>
              <el-table-column prop="user" label="下单人" width="180">
              </el-table-column>
              <el-table-column prop="endDate" label="交货日" width="180">
              </el-table-column>
              <el-table-column prop="type" label="类型" width="180">
              </el-table-column>
              <el-table-column prop="status" label="状态" width="180">
              </el-table-column>
              <el-table-column prop="des" label="描述" width="180">
              </el-table-column>
              <el-table-column prop="" label="操作"> </el-table-column>
            </el-table></div></el-col
      ></el-row>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      sumarys: [
        { title: "合同确认", number: 5 },
        { title: "订单下达", number: 6 },
        { title: "发货确认", number: 1 },
        { title: "即将截止", number: 5 },
      ],
      oderStateList: [
        { name: "未下单", value: 1 },
        { name: "生产中", value: 1 },
        { name: "采购中", value: 1 },
        { name: "待入库", value: 1 },
      ],
      oderTypeList: [
        { name: "生产订单", value: 1 },
        { name: "贸易订单", value: 1 },
        { name: "加工订单", value: 1 },
        { name: "委外订单", value: 1 },
      ],
      orderList: [
        {
          no: "00001",
          customerName: "客户01",
          date: "2021-04-13",
          user: "员工01",
          endDate: "2021-07-15",
          status: "生产中",
          des: "",
          type: "生产订单",
        },
         {
          no: "00002",
          customerName: "客户02",
          date: "2021-04-03",
          user: "员工01",
          endDate: "2021-07-15",
          status: "已出单",
          des: "",
          type: "委外订单",
        },
         {
          no: "00003",
          customerName: "客户03",
          date: "2021-03-13",
          user: "员工01",
          endDate: "2021-07-15",
          status: "生产中",
          des: "",
          type: "生产订单",
        },
      ],
    };
  },
};
</script>
<style>
.center-main-div {
  display: flex;
  flex-direction: column;
}
.center-dashboard-div {
  padding-top: 1rem;
}
.center-dashborad-card {
  height: 73vh;
  border: 1px solid rgba(0, 0, 0, 0.12);
  border-radius: 0.3rem;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
}
.center-table-card {
  display: flex;
  flex-direction: column;
  height: 73vh;
  border: 1px solid rgba(0, 0, 0, 0.12);
  border-radius: 0.3rem;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
}
.center-summary-card {
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
  display: flex;
  flex-direction: row;
  align-items: center;
  padding: 1rem;
  border-radius: 0.3rem;
  height: 7vh;
  border: 1px solid rgba(0, 0, 0, 0.12);
  min-width: 100px;
  margin-top: 1rem;
}
.center-Summary-card-title {
  width: 200px;
  font-size: 1.5rem;
  font-weight: bold;
}
.center-Summary-card-content {
  width: 100%;
  font-size: xx-large;
}
.table-header-div {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  padding: 0.5rem;
}
</style>