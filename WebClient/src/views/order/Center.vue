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
                size="medium"
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
                <el-select
                  clearable
                  placeholder="订单状态"
                  v-model="orderState"
                  size="medium"
                >
                  <el-option
                    v-for="item in oderStateList"
                    :key="item.value"
                    :label="item.name"
                    :value="item.value"
                  >
                  </el-option>
                </el-select>
                <el-select
                  clearable
                  placeholder="订单类型"
                  v-model="orderType"
                  class="ml24"
                  size="medium"
                >
                  <el-option
                    v-for="item in oderProduceTypeList"
                    :key="item.value"
                    :label="item.name"
                    :value="item.value"
                  >
                  </el-option>
                </el-select>
              </div>
            </div>
            <el-table
              border
              :data="orderFormProcessListShow"
              stripe
              style="width: 100%"
            >
              <el-table-column prop="formNo" label="订单号" width="180px">
              </el-table-column>
              <el-table-column prop="customerName" label="客户">
              </el-table-column>
              <el-table-column prop="createDate" label="创建日期" width="180px">
                <template slot-scope="scope">
                  <span>{{ getFullTime(scope.row.createDate) }}</span>
                </template>
              </el-table-column>
              <el-table-column prop="state" label="状态" width="80px">
                <template slot-scope="scope">
                  <span v-if="scope.row.state == '2'" class="coloryellow"
                    >已下单</span
                  >
                  <span v-else class="colorgreen">进行中</span>
                </template>
              </el-table-column>
              <el-table-column prop="produceType" label="类型" width="auto">
              </el-table-column>
              <el-table-column prop="" label="操作" width="80px">
                <template slot-scope="scope">
                  <el-button
                    type="primary"
                    icon="el-icon-more"
                    size="mini"
                    @click="orderFormDetailOnClick(scope.row.id)"
                    circle
                  ></el-button>
                </template>
              </el-table-column>
            </el-table></div></el-col
      ></el-row>
    </div>
    <el-dialog
      ref="detailDialog"
      title="订单详细"
      :visible.sync="detailDialog"
      width="70%"
    >
      <div>
        <el-form
          label-position="left"
          label-width="90px"
          class="demo-table-expand"
        >
          <el-row>
            <el-col :span="6" class="center-orderForm-info-cell-div">
              <el-form-item label="订单号：">
                <span>{{ orderFormInfo.orderForm.formNo }}</span>
              </el-form-item></el-col
            >
            <el-col :span="6" class="center-orderForm-info-cell-div">
              <el-form-item label="合同号">
                <span>{{ orderFormInfo.orderForm.contractNo }}</span>
              </el-form-item></el-col
            >
            <el-col :span="6" class="center-orderForm-info-cell-div">
              <el-form-item label="客户：">
                <span>{{ orderFormInfo.orderForm.customerName }}</span>
              </el-form-item></el-col
            >
            <el-col :span="6" class="center-orderForm-info-cell-div">
              <el-form-item label="创建日期：">
                <span>{{
                  getFullTime(orderFormInfo.orderForm.createDate)
                }}</span>
              </el-form-item></el-col
            >
          </el-row>
          <el-row>
            <el-col :span="6" class="center-orderForm-info-cell-div">
              <el-form-item label="生产类型：">
                <span>{{
                  getProduceTypeChar(orderFormInfo.orderForm.produceType)
                }}</span>
              </el-form-item></el-col
            >
            <el-col :span="6" class="center-orderForm-info-cell-div">
              <el-form-item label="状态">
                <span
                  v-if="orderFormInfo.orderForm.state == '2'"
                  class="coloryellow"
                  >已下单</span
                >
                <span v-else class="colorgreen">进行中</span>
              </el-form-item></el-col
            >
          </el-row>
        </el-form>
        <div class="center-orderForm-info-ticket-div">
          <span class="colorblue fl mb40 fz9">订单产品</span>
          <el-table
            border
            size="mini"
            :data="orderFormInfo.orderFormDetailList"
            stripe
            style="width: 100%"
          >
            <el-table-column prop="productName" label="名称"> </el-table-column>
            <el-table-column prop="specification" label="规格">
            </el-table-column>
            <el-table-column prop="remark" label="备注"> </el-table-column>
            <el-table-column prop="count" label="数量">
              <template slot-scope="scope">
                <span>{{ scope.row.count }}{{ scope.row.countUnit }}</span>
              </template>
            </el-table-column>
            <el-table-column prop="produceType" label="类型">
              <template slot-scope="scope">
                <span>{{ getProduceTypeChar(scope.row.produceType) }}</span>
              </template>
            </el-table-column>
          </el-table>
        </div>
        <div
          v-if="orderFormInfo.producePlanList.length > 0"
          class="center-orderForm-info-ticket-div"
        >
          <span class="colorblue fl mb40 fz9">生产计划</span>
          <el-table
            border
            size="mini"
            :data="orderFormInfo.producePlanList"
            stripe
            style="width: 100%"
          >
            <el-table-column prop="planNo" label="编号"> </el-table-column>
            <el-table-column prop="customerName" label="客户">
            </el-table-column>
            <el-table-column prop="startDate" label="计划时间">
              <template slot-scope="scope">
                <span
                  >{{ getFullDate(scope.row.startDate) }} 至
                  {{ getFullDate(scope.row.endDate) }}</span
                >
              </template>
            </el-table-column>
            <el-table-column prop="pickingTicketNo" label="领料单号">
            </el-table-column>
            <el-table-column prop="produceType" label="类型">
              <template slot-scope="scope">
                <span>{{ getProduceTypeChar(scope.row.type) }}</span>
              </template>
            </el-table-column>
            <el-table-column prop="state" label="状态">
              <template slot-scope="scope">
                <span class="colorred" v-if="scope.row.state == '1'"
                  >待配置</span
                >
                <span class="colorblue" v-else-if="scope.row.state == '2'"
                  >执行中</span
                >
                <span v-else class="colorgreen">已完成</span>
              </template>
            </el-table-column>
          </el-table>
        </div>

        <div
          v-if="orderFormInfo.delegateTicketList.length > 0"
          class="center-orderForm-info-ticket-div"
        >
          <span class="colorblue fl mb40 fz9">委外单</span>
          <el-table
            border
            size="mini"
            :data="orderFormInfo.delegateTicketList"
            stripe
            style="width: 100%"
          >
            <el-table-column prop="ticketNo" label="编号"> </el-table-column>
            <el-table-column prop="customerName" label="客户">
            </el-table-column>
          </el-table>
        </div>

        <div
          v-if="orderFormInfo.purchaseDetailList.length > 0"
          class="center-orderForm-info-ticket-div"
        >
          <span class="colorblue fl mb40 fz9">采购明细</span>
          <el-table
            border
            size="mini"
            :data="orderFormInfo.purchaseDetailList"
            stripe
            style="width: 100%"
          >
            <el-table-column prop="materialNo" label="编号"> </el-table-column>
            <el-table-column prop="materialName" label="名称">
            </el-table-column>
            <el-table-column prop="specification" label="规格">
            </el-table-column>
            <el-table-column prop="count" label="数量">
              <template slot-scope="scope">
                <span
                  >{{ scope.row.count }}
                  {{ scope.row.countUnit }}
                </span>
              </template>
            </el-table-column>
            <el-table-column prop="remark" label="备注"> </el-table-column>
          </el-table>
        </div>
      </div>
    </el-dialog>
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
      oderStateList: [
        { name: "已下单", value: "2" },
        { name: "进行中", value: "3" },
      ],
      oderProduceTypeList: [
        { name: "生产", value: "P" },
        { name: "代工", value: "D" },
        { name: "采购", value: "C" },
        { name: "委外", value: "W" },
      ],
    };
  },
  computed: {
    ...mapGetters([
      "orderStateSummary",
      "orderProduceTypeSummary",
      "orderFormProcessList",
    ]),
    orderFormProcessListShow() {
      return this.orderFormProcessList.filter((item) => {
        var existState = false;
        if (this.orderState == "") {
          existState = true;
        } else {
          existState = item.state.indexOf(this.orderState) != -1;
        }
        var existType = false;
        if (this.orderType == "") {
          existType = true;
        } else {
          existType = item.produceType.indexOf(this.orderType) != -1;
        }
        return existState && existType;
      });
    },
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
      GetOrderFormProcessList: "GetOrderFormProcessList",
      GetOrderFormInfo: "GetOrderFormInfo",
    }),
    getProduceTypeChar(type) {
      if (!type) return;
      return type
        .replace("P", "生产")
        .replace("D", "代工")
        .replace("C", "采购")
        .replace("W", "委外");
    },
    navigationTo(path) {
      if (path != "") {
        this.$router.push({ path: "/main/orderManagement" + path,query:{type: 'fromCenter'} });
      }
    },

    getFullTime(time) {
      if (time) {
        return new Date(time).format("yyyy-MM-dd hh:mm:ss");
      } else {
        return "";
      }
    },

    getFullDate(time) {
      if (time) {
        return new Date(time).format("yyyy-MM-dd");
      } else {
        return "";
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
    orderFormDetailOnClick(id) {
      this.GetOrderFormInfo({ id: id })
        .then((res) => {
          if (res.resultStatus == 1) {
            console.log(res.data);
            this.orderFormInfo = res.data;
            this.detailDialog = !this.detailDialog;
          } else {
            this.$message.warning(res.messgae);
          }
        })
        .catch((err) => {
          this.$message.error(err.messgae);
        });
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
    this.GetOrderFormProcessList();
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
</style>
