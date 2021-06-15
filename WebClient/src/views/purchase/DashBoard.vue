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
        <!-- <div>
        <span class="title-name">当月采购详情</span>
        </div> -->
        <div>
          <span class="title-name">选择月份： </span>
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
        <el-col :span="12">
          <div class="pr-dashborad-card">
            <!-- <div id="chart-1" class="echart-bar-1"></div> -->
            <div id="myChart" class="echart-bar-1"></div>
          </div>
        </el-col>
        <el-col :span="12">
          <div class="pr-dashborad-card">
            <div id="myChart-2" class="echart-bar-1"></div>
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
      selectMonth: "",
    };
  },
  computed: {
    ...mapGetters([
      "prSummaries",
      "purchaseDashboardSummary",
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
    purchaseDashboardSummary() {
      this.drawLine();
    },
  },
  methods: {
    ...mapActions({
      GetSummaryCount: "GetSummaryCount",
      GetPurchaseDashboardSummary: "GetPurchaseDashboardSummary",
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
      this.GetPurchaseDashboardSummary({ date: value.format("yyyy-MM-dd") });
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
        let myChart2 = echarts.init(document.getElementById('myChart-2'))
        // 绘制图表
        var option1 = {
            title: { 
              text: 'TOP-10 物料',
              textStyle:{
                fontSize:13,
                color: "#009db2",//标题颜色
                fontWeight: "bold",//粗体
              }
            },
            tooltip: {},
            xAxis: {
                // data: ["衬衫","羊毛衫","雪纺衫","裤子","羽绒服","冲锋衣","汗衫-1000001-1"]
                data: []
            },
            yAxis: {},
            series: [{
                name: '采购数量',
                type: 'bar',
                itemStyle: {
                  //通常情况下：
                  normal: {
                    //每个柱子的颜色即为colorList数组里的每一项，如果柱子数目多于colorList的长度，则柱子颜色循环使用该数组
                    color: function (params) {
                      var colorList = [
                        "#9489fa",
                        "#f06464",
                        "#5690dd",
                        "#f0da49",
                        "#71c16f",
                        "#2aaaef",
                        "#f7af59",
                        "#bd88f5",
                        "#009db2",
                        "#FFDAB9",
                      ];
                      return colorList[params.dataIndex % colorList.length];
                    },
                  },
                  //鼠标悬停时：
                  emphasis: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: "rgba(0, 0, 0, 0.5)",
                  },
                },
                barCategoryGap: "20%",
                // data: [{value: 5, name: "衬衫"},{value: 6, name: "羊毛衫"},{value: 21, name: "雪纺衫"},{value: 23, name: "裤子"},{value: 9, name: "羽绒服"},{value: 31, name: "冲锋衣"},{value: 13, name: "汗衫"},{value: 26, name: "短裤"},{value: 20, name: "防晒服"},{value: 15, name: "雨衣"}]
                data: []
            }]
        };

        var option2 = {
            title: { 
              text: 'TOP-10 供应商',
              textStyle:{
                fontSize:13,
                color: "#009db2",//标题颜色
                fontWeight: "bold",//粗体
              }
            },
            tooltip: {},
            xAxis: {
                // data: ["衬衫","羊毛衫","雪纺衫","裤子","羽绒服","冲锋衣","汗衫-1000001-1"]
                data: []
            },
            yAxis: {},
            series: [{
                name: '采购总额',
                type: 'bar',
                itemStyle: {
                  //通常情况下：
                  normal: {
                    //每个柱子的颜色即为colorList数组里的每一项，如果柱子数目多于colorList的长度，则柱子颜色循环使用该数组
                    color: function (params) {
                      var colorList = [
                        "#9489fa",
                        "#f06464",
                        "#5690dd",
                        "#f0da49",
                        "#71c16f",
                        "#2aaaef",
                        "#f7af59",
                        "#bd88f5",
                        "#009db2",
                        "#FFDAB9",
                      ];
                      return colorList[params.dataIndex % colorList.length];
                    },
                  },
                  //鼠标悬停时：
                  emphasis: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: "rgba(0, 0, 0, 0.5)",
                  },
                },
                barCategoryGap: "20%",
                data: []
                // data: [{value: 5, name: "衬衫"},{value: 6, name: "羊毛衫"},{value: 21, name: "雪纺衫"},{value: 23, name: "裤子"},{value: 9, name: "羽绒服"},{value: 31, name: "冲锋衣"},{value: 13, name: "汗衫"},{value: 26, name: "短裤"},{value: 20, name: "防晒服"},{value: 15, name: "雨衣"}]
            }]
        };

        if (this.purchaseDashboardSummary.topProductList.length > 0) {
        this.purchaseDashboardSummary.topProductList.map((item) => {
          option1.series[0].data.push({
            value: item.itemValue,
            name: item.itemName,
          });
        });
        } else {
          option1.series[0].data.push({
            value: 0,
            name: "无",
          });
        }

        if (this.purchaseDashboardSummary.topSupplyList.length > 0) {
        this.purchaseDashboardSummary.topSupplyList.map((item) => {
          option2.series[0].data.push({
            value: item.itemValue,
            name: item.itemName,
          });
        });
        } else {
          option2.series[0].data.push({
            value: 0,
            name: "无",
          });
        }
        myChart.setOption(option1);
        myChart2.setOption(option2);
    }
  },
  beforeMount() {
    this.GetSummaryCount();
  },
  mounted() {
    // this.drawLine();
    this.selectMonth = new Date();
    this.GetPurchaseDashboardSummary({ date: new Date().format("yyyy-MM-dd") });
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
