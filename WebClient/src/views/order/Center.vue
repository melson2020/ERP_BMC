<template>
  <div class="center-main-div">
    <el-row :gutter="10">
      <el-col v-for="summary in sumarys" :span="6" :key="summary.number"
        ><div class="center-summary-card color-bk-gray">
          <span class="center-Summary-card-title color-gray">{{
            summary.title
          }}</span>
          <el-tooltip effect="light" content="点击查看详细" placement="top">
            <el-button class="center-Summary-card-content colorred" type="text" @click="navigationTo(summary.path)">
              {{ summary.number }}
            </el-button>
          </el-tooltip>
        </div>
      </el-col>
    </el-row>
    <div class="center-dashboard-div">
      <el-row :gutter="10"
        ><el-col :span="6"
          ><div class="center-dashborad-card">
            <div class="center-dashborad-card-header">
              <span>待处理总数</span>
              <span class="colorblue fz12">30</span>
            </div>
            <el-scrollbar class="order-status-scrollbar">
              <div
                v-for="detail in orderStatusDetails"
                :key="detail.id"
                class="order-status-detail-div"
              >
                <span>{{ detail.typeName }}</span>
                <span class="fz12 colorblue">{{ detail.numbers }}</span>
              </div>
            </el-scrollbar>
            <div id="chart-1" class="chart-1-div"></div></div
        ></el-col>
        <el-col :span="18"
          ><div class="center-table-card">
            <div class="table-header-div">
              <span>待处理订单</span>
              <div>
                <el-select placeholder="请选择" size="medium">
                  <el-option
                    v-for="item in oderStateList"
                    :key="item.value"
                    :label="item.name"
                    :value="item.value"
                  >
                  </el-option>
                </el-select>
                <el-select placeholder="请选择" class="ml24" size="medium">
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
              <el-table-column prop="customerName" label="客户" width="auto">
              </el-table-column>
              <el-table-column prop="date" label="日期" width="auto">
              </el-table-column>
              <el-table-column prop="user" label="下单人" width="auto">
              </el-table-column>
              <el-table-column prop="endDate" label="交货日" width="auto">
              </el-table-column>
              <el-table-column prop="type" label="类型" width="auto">
              </el-table-column>
              <el-table-column prop="status" label="状态" width="auto">
              </el-table-column>
              <el-table-column prop="des" label="描述" width="auto">
              </el-table-column>
              <el-table-column prop="" label="操作">
                <template slot-scope="scope">
                  <el-button
                    type="primary"
                    icon="el-icon-more"
                    size="mini"
                    circle
                  ></el-button>
                </template>
              </el-table-column>
            </el-table></div></el-col
      ></el-row>
    </div>
  </div>
</template>
<script>
import * as echarts from "echarts"; //引入echart
export default {
  data() {
    return {
      sumarys: [
        { title: "合同确认", number: 5,path:'/contract' },
        { title: "订单下达", number: 6 },
        { title: "发货确认", number: 1 },
        { title: "即将截止", number: 9 },
      ],
      oderStateList: [
        { name: "未下单", value: 11 },
        { name: "生产中", value: 12 },
        { name: "采购中", value: 13 },
        { name: "待入库", value: 14 },
      ],
      oderTypeList: [
        { name: "生产订单", value: 15 },
        { name: "贸易订单", value: 16 },
        { name: "加工订单", value: 17 },
        { name: "委外订单", value: 18 },
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
      orderStatusDetails: [
        { id: 1, typeName: "生产/贸易", numbers: 10 },
        { id: 2, typeName: "委外/生产", numbers: 9 },
        { id: 3, typeName: "代加工", numbers: 21 },
      ],
    };
  },
  methods: {
    navigationTo(path){
       this.$router.replace({ path: '/main/orderManagement'+path });
    },

    Init: function () {
      //父级div大小改变，图表跟着变化，也可以写成 Init(){}
      window.addEventListener(
        "resize",
        function () {
          this.orderChart.resize();
        }.bind(this)
      );
    },
    drawCharts() {
      var oderChart = echarts.init(document.getElementById("chart-1"));
      var option={
        backgroundColor: "white",
        title: {
          text: "订单占比",
        },
        tooltip: { trigger: "item", formatter: "{a} <br/>{b} ({d}%) " },
        series: [
          {
            name: "订单占比",
            type: "pie", // 设置图表类型为饼图
            radius: "60%", // 饼图的半径，外半径为可视区尺寸（容器高宽中较小一项）的 55% 长度。
            data: [
            ],
          },
        ],
        color: [
          "#9489fa",
          "#f06464",
          "#f7af59",
          "#16afcc",
          "#898989",
          "#c4cc38",
        ],
      }
      this.orderStatusDetails.map((item)=>{
        option.series[0].data.push({value:item.numbers,name:item.typeName})
      })
      oderChart.setOption(option);
    },
  },
  mounted() {
    this.drawCharts();
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
  height: 600px;
  display: flex;
  flex-direction: column;
  border: 1px solid rgba(0, 0, 0, 0.12);
  border-radius: 0.3rem;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
}
.center-dashborad-card-header {
  height: 50px;
  border-bottom: 1px solid lightgray;
  display: flex;
  align-items: center;
  padding: 0px 10px;
  flex-direction: row;
  justify-content: space-between;
}
.center-table-card {
  display: flex;
  flex-direction: column;
  height: 600px;
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
  height: 60px;
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
  align-items: center;
  justify-content: space-between;
  padding: 0 5px;
  height: 50px;
}
.order-status-detail-div {
  padding: 5px 10px;
  font-size: 0.9rem;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
}
.order-status-scrollbar /deep/.el-scrollbar__wrap {
  overflow-x: hidden;
}
.order-status-scrollbar {
  height: 250px;
}
.chart-1-div {
  height: 300px;
  width: 100%;
}
</style>