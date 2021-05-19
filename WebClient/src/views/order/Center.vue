<template>
  <div class="center-main-div">
    <el-row :gutter="10">
      <el-col
        v-for="summary in orderStateSummary"
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
      <el-row :gutter="10"
        ><el-col :span="7"
          ><div class="center-dashborad-card">
            <div class="center-dashborad-card-header">
              <div class="center-dashborad-header">
                <span class="center-font-bold">订单汇总</span>
                <span class="colorblue fz12 ml40">{{
                  produceTypeTotalCount
                }}</span>
              </div>
              <el-date-picker
                v-model="selectMonth"
                type="month"
                @change="monthChange"
                size="small"
                placeholder="选择月"
              >
              </el-date-picker>
            </div>
            <el-scrollbar
              class="order-status-scrollbar"
              v-if="orderProduceTypeSummary.length > 0"
            >
              <div
                v-for="detail in orderProduceTypeSummary"
                :key="detail.title"
                class="order-status-detail-div"
              >
                <span>{{ detail.title }}</span>
                <span class="fz12 colorblue">{{ detail.count }}</span>
              </div>
            </el-scrollbar>
            <div v-else class="order-status-scrollbar">暂无数据</div>
            <div id="chart-1" class="chart-1-div"></div></div
        ></el-col>
        <el-col :span="17"
          ><div class="center-table-card">
            <div class="table-header-div">
              <span class="center-font-bold">订单进行中</span>
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
            <el-table border :data="orderFormProcessList" stripe style="width: 100%">
              <el-table-column prop="formNo" label="订单号"> </el-table-column>
              <el-table-column prop="customerName" label="客户" width="auto">
              </el-table-column>
              <el-table-column prop="createDate" label="日期" width="auto">
              </el-table-column>
              <el-table-column prop="produceType" label="类型" width="auto">
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
import { mapGetters } from "vuex";
import { mapActions } from "vuex";
export default {
  data() {
    return {
      selectMonth: "",
      oderStateList: [
        { name: "未下单", value: 11 },
        { name: "生产中", value: 12 },
        { name: "采购中", value: 13 },
        { name: "待入库", value: 14 },
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
  computed: {
    ...mapGetters(["orderStateSummary", "orderProduceTypeSummary","orderFormProcessList"]),
    produceTypeTotalCount() {
      var count = "0";
      this.orderProduceTypeSummary.map((item) => {
        count = this.$my.NumberAdd(item.count, count);
      });
      return count;
    },
  },
  watch: {
    orderProduceTypeSummary() {
      this.drawCharts();
    },
  },
  methods: {
    ...mapActions({
      GetOrderCenterSummaryCount: "GetOrderCenterSummaryCount",
      GetOrderProduceTypeSummary: "GetOrderProduceTypeSummary",
      GetOrderFormProcessList:'GetOrderFormProcessList'
    }),
    navigationTo(path) {
      this.$router.replace({ path: "/main/orderManagement" + path });
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
  },
  mounted() {
    this.GetOrderCenterSummaryCount();
    this.selectMonth = new Date();
    this.GetOrderProduceTypeSummary({ date: new Date().format("yyyy-MM-dd") });
    this.GetOrderFormProcessList()
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

.center-dashborad-header{
   display: flex;
   padding: 0 5px;
   align-items: center;
}
.center-font-bold{
  font-weight: bold;
   font-size: 1.1rem;
}
</style>
