<template>
  <div class="center-main-div">
    <el-row :gutter="10">
      <el-col
        v-for="summary in prSummaries"
        :span="6"
        :key="summary.number"
        ><div class="center-summary-card color-bk-gray">
          <span class="center-Summary-card-title color-gray">{{
            summary.title
          }}</span>
          <el-tooltip effect="light" content="点击查看详细" placement="top">
            <el-button
              class="center-Summary-card-content colorred"
              type="text"
              @click="navigationTo(summary.path)"
            >
              {{ summary.count }}
            </el-button>
          </el-tooltip>
        </div>
      </el-col>
    </el-row>
    <div class="center-dashboard-div">
      <div class="po-header">
        <div>
        <span class="title-name">待交货详情</span>
        </div>
        <div>
          <span >选择日期： </span>
          <el-date-picker
            v-model="selectMonth"
            type="month"
            @change="monthChange"
            size="medium"
            placeholder="选择月"
          >
          </el-date-picker>
        </div>
      </div>
      <el-row :gutter="10">
        <el-col :span="10">
          <div class="pr-dashborad-card">
            <span >card 1</span>
            <!-- <div id="chart-1" class="echart-bar-1"></div> -->
            <div id="myChart" :style="{width: '400px', height: '400px'}"></div>
          </div>
        </el-col>
        <el-col :span="14">
          <div class="pr-dashborad-card">
            <span >card 2</span>
            <div id="chart-2" class="dashboard-main-right-content"></div>
          </div>
        </el-col>
      </el-row>
    </div>
  </div>
</template>
<script>
import * as echarts from "echarts"; //引入echart
import { mapGetters } from "vuex";
import { mapActions } from "vuex";
export default {
  data() {
    return {
      orderType: "",
      orderState: "",
      selectMonth: "",
      detailDialog: false,
      orderFormInfo: {
        orderForm: {},
        orderFormDetailList: [],
        producePlanList: [],
        delegateTicketList: [],
        purchaseDetailList: [],
      },
    };
  },
  computed: {
    ...mapGetters([
      "prSummaries",
      "orderProduceTypeSummary",
      "orderFormProcessList",
    ]),
    // orderFormProcessListShow() {
    //   return this.orderFormProcessList.filter((item) => {
    //     var existState = false;
    //     if (this.orderState == "") {
    //       existState = true;
    //     } else {
    //       existState = item.state.indexOf(this.orderState) != -1;
    //     }
    //     var existType = false;
    //     if (this.orderType == "") {
    //       existType = true;
    //     } else {
    //       existType = item.produceType.indexOf(this.orderType) != -1;
    //     }
    //     return existState && existType;
    //   });
    // },
    // produceTypeTotalCount() {
    //   var count = "0";
    //   this.orderProduceTypeSummary.map((item) => {
    //     count = this.$my.NumberAdd(item.count, count);
    //   });
    //   return count;
    // },
  },
  watch: {
    orderProduceTypeSummary() {
      this.drawCharts();
    },
  },
  methods: {
    ...mapActions({
      GetSummaryCount: "GetSummaryCount",
      // GetOrderProduceTypeSummary: "GetOrderProduceTypeSummary",
      // GetOrderFormProcessList: "GetOrderFormProcessList",
      GetOrderFormInfo: "GetOrderFormInfo",
    }),
    navigationTo(path) {
      if (path != "") {
        this.$router.push({ path: "/main/purchaseCenter" + path});
      }
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
    monthChange(value) {
      this.GetOrderProduceTypeSummary({ date: value.format("yyyy-MM-dd") });
    },

    drawCharts() {
      var oderChart = echarts.init(document.getElementById("chart-1"));
      var option = {
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
            data: [],
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
      };
      if (this.orderProduceTypeSummary.length > 0) {
        this.orderProduceTypeSummary.map((item) => {
          option.series[0].data.push({
            value: item.count,
            name: item.title,
          });
        });
      } else {
        option.series[0].data.push({
          value: 0,
          name: "无",
        });
      }
      oderChart.setOption(option);
    },
    drawLine(){
        // 基于准备好的dom，初始化echarts实例
        let myChart = echarts.init(document.getElementById('myChart'))
        // 绘制图表
        myChart.setOption({
            title: { text: 'TOP-10 物料' },
            tooltip: {},
            xAxis: {
                data: ["衬衫","羊毛衫","雪纺衫","裤子","高跟鞋","袜子"]
            },
            yAxis: {},
            series: [{
                name: '销量',
                type: 'bar',
                data: [5, 20, 36, 10, 10, 20]
            }]
        });
    }
  },
  beforeMount() {
    this.GetSummaryCount();
  },
  mounted() {
    // this.GetOrderCenterSummaryCount();
    // this.selectMonth = new Date();
    this.drawLine();
    // this.GetOrderProduceTypeSummary({ date: new Date().format("yyyy-MM-dd") });
    // this.GetOrderFormProcessList();
  },
};
</script>
<style>
.center-main-div {
  padding: 0px 5px;
  display: flex;
  flex-direction: column;
}
.center-dashboard-div {
  padding-top: 1rem;
}
.pr-dashborad-card {
  height: 600px;
  display: flex;
  padding-right: 5px;
  flex-direction: column;
  border: 1px solid rgba(0, 0, 0, 0.12);
  border-radius: 0.3rem;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
}
.pr-dashborad-card-header {
  height: 50px;
  border-bottom: 1px solid lightgray;
  display: flex;
  align-items: center;
  padding: 0px 5px;
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
  padding: 0 10px;
  height: 50px;
}
.order-status-detail-div {
  padding: 10px 15px;
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

.center-dashborad-header {
  display: flex;
  padding: 0 5px;
  align-items: center;
}
.center-font-bold {
  font-weight: bold;
  font-size: 1.1rem;
}
.center-orderForm-info-cell-div {
  display: flex;
}

.center-orderForm-info-ticket-div {
  padding: 10px 0;
}
.demo-table-expand .el-form-item {
  margin-right: 0;
  margin-bottom: 0;
}
.demo-table-expand label {
  color: #99a9bf;
}
.po-header {
  padding-bottom: 10px;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
}
.title-name {
  font-size: 13px;
  font-weight: bold;
  color: #409eff;
}
.dashboard-main-right-content {
  height: 100%;
  width: 100%;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
  /* border:1px solid #E9E9EB;
  margin-left: 10px;
  margin-top: 10px; */
}
.echart-bar-1 {
  height: 100%;
  width: 100%;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
  /* border:1px solid #E9E9EB;
  margin-left: 10px;
  margin-top: 10px; */
}
</style>
