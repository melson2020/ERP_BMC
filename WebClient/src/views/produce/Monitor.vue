<template>
  <div class="produce-monitor-main-container">
    <el-row class="produce-monitor-summary-div" :gutter="10">
      <el-col :span="12">
        <div class="produce-monitor-summary-box">
          <span class="produce-monitor-summary-card-title color-gray"
            >待定计划</span
          >
          <el-tooltip effect="light" content="点击查看详细" placement="top">
            <el-button
              class="produce-monitor-Summary-card-content colorred"
              type="text"
            >
              {{ countSummary.confirmCount }}
            </el-button>
          </el-tooltip>
        </div>
      </el-col>
      <el-col :span="12">
        <div class="produce-monitor-summary-box">
          <span class="produce-monitor-summary-card-title color-gray"
            >计划进行中</span
          >
          <span class="produce-monitor-Summary-card-content colorred">
            {{ countSummary.processCount }}</span
          >
        </div>
      </el-col>
    </el-row>
    <div class="produce-monitor-doing-plan-div">
      <div class="undoing-plan-div-title colorblue">生产列表</div>
      <el-scrollbar class="planing-scrollbar">
        <el-table :data="processingPlanList" 
          border="" stripe
          size="small"
          :header-row-style="{ height: '40px' }"
          :row-style="{ height: '40px' }"
          :cell-style="{ padding: '2px', color: '#909399' }"
          :header-cell-style="{ background: '#909399', color: 'white' }">
          <el-table-column prop="planNo" label="单号" width="180">
          </el-table-column>
          <el-table-column prop="orderFormNo" label="订单号" width="180">
          </el-table-column>
          <el-table-column prop="createDate" label="创建时间">
          <template slot-scope="scope">
            <span>{{getFullTime(scope.row.createDate) }}</span>
          </template>
          </el-table-column>
          <el-table-column prop="endDate" label="计划完成时间">
          <template slot-scope="scope">
            <span>{{getFullTime(scope.row.endDate) }}</span>
          </template>
          </el-table-column>
          <el-table-column prop="pickingTicketNo" label="领料单号">
          </el-table-column>
          <el-table-column prop="pickingState" label="领料状态">
          </el-table-column>
          <el-table-column prop="planState" label="状态"> </el-table-column>
          <el-table-column  width="100px">
            <template slot-scope="scope">
              <el-button
                icon="el-icon-more"
                type="primary"
                size="mini"
                @click="changeTableAreaView(scope.row.id)"
                circle
              ></el-button>
              <el-tooltip effect="light" content="打印" placement="top">
                <el-button size="mini" @click="handlePrinter(scope.$index, scope.row)" plain circle icon="el-icon-printer"/>
              </el-tooltip>
            </template>
          </el-table-column>
        </el-table>
      </el-scrollbar>
    </div>

    <div class="monitor-product-line-div">
      <div class="monitor-product-line-div-header">
        <div class="undoing-plan-div-title colorblue">产线工况</div>
        <div class="tag-color-div">
          <div class="circle-border mr15"></div>
          忙碌中
        </div>
      </div>
      <div
        class="monitor-productionLine-box"
        v-for="productionLine in produceLineStateList"
        :key="productionLine.lineId"
        :gutter="10"
      >
        <div class="monitor-product-line-title color-yellow">
          {{ productionLine.lineName }}
        </div>
        <el-row :gutter="10" style="width: 100%">
          <el-col
            :span="3"
            v-for="workStation in productionLine.workStationVoList"
            :key="workStation.workStationId"
          >
            <div class="monitor-productionLine-work-station-card fz8">
              <div v-if="workStation.planInfo.length>0" class="monitor-produce-work-station-float-div">
                <el-tooltip 
                  v-for="plan in workStation.planInfo"
                  :key="plan.planNo"
                  :content="plan.planNo"
                  placement="bottom"
                  effect="light"
                >
                  <div class="monitor-work-station-plan-info"></div>
                </el-tooltip>
              </div>
              <div v-else class="monitor-produce-work-station-float-div monitor-empty-work-station"><span>闲</span></div>
              <div class="monitor-productionLine-work-station-info color9">
                <span
                  >{{ workStation.indexNo }} /
                  {{ workStation.employeeGroupNo }}</span
                >
              </div>
            </div>
          </el-col>
        </el-row>
      </div>
    </div>
    <el-dialog
      title="生产计划详细"
      :visible.sync="detailInfoShowing"
      width="70%"
    >
    <m-plan-info ref="planInfo"></m-plan-info>
    </el-dialog>
  </div>
</template>
<script>
import { mapGetters } from "vuex";
import { mapActions } from "vuex";
import planInfo from './Monitor/PlanInfo';
export default {
  data() {
    return {
      detailInfoShowing: false,
    };
  },
  computed: {
    ...mapGetters([
      "produceLineStateList",
      "countSummary",
      "processingPlanList",
    ]),
  },
  methods: {
    ...mapActions({
      GetProduceLineStateInfo: "GetProduceLineStateInfo",
      GetPlanCountSummary: "GetPlanCountSummary",
      GetProcessingPlanList: "GetProcessingPlanList",
    }),
    changeTableAreaView(planId) {
      this.detailInfoShowing = !this.detailInfoShowing;
      setTimeout(()=>{ this.$refs['planInfo'].loadPlanInfo(planId)},200)
    },
    getFullTime(time) {
    return new Date(time).format("yyyy-MM-dd");
    },
  },
  components: {
    "m-plan-info":planInfo ,
  },
  beforeMount() {
    this.GetProduceLineStateInfo();
    this.GetPlanCountSummary();
    this.GetProcessingPlanList();
  },
};
</script>
<style>
.produce-monitor-main-container {
  padding: 8px;
}
.produce-monitor-summary-div {
  display: flex;
  flex-direction: row;
  height: 80px;
}
.produce-monitor-summary-box {
  height: 100%;
  width: 100%;
  border-radius: 5px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
  display: flex;
  flex-direction: row;
  align-items: center;
  border-radius: 0.3rem;
  border: 1px solid rgb(238, 234, 234);
}
.produce-monitor-summary-card-title {
  width: 200px;
  font-size: 1.5rem;
  font-weight: bold;
}
.produce-monitor-Summary-card-content {
  width: 100%;
  font-size: xx-large;
}
.produce-monitor-doing-plan-div {
  width: 100%;
  height: 500px;
  margin-top: 20px;
  border-radius: 5px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
  border: 1px solid rgb(238, 234, 234);
}
.undoing-plan-div-title {
  display: flex;
  height: 50px;
  align-items: center;
  padding: 0px 10px;
  font-size: 18px;
  font-weight: bold;
  /* border-bottom: 1px solid rgb(238, 234, 234); */
}
.planing-scrollbar /deep/.el-scrollbar__wrap {
  overflow-x: hidden;
}
.planing-scrollbar {
  height: 450px;
}
.monitor-product-line-div {
  width: 100%;
  min-height: 300px;
  margin-top: 20px;
  border-radius: 5px;
  display: flex;
  flex-direction: column;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
  border: 1px solid rgb(238, 234, 234);
}
.monitor-productionLine-box {
  display: flex;
  flex-direction: row;
  padding: 5px;
}

.monitor-product-line-title {
  width: 100px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 18px;
}
.bkg {
  background: #67c23a;
}
.monitor-product-line-div-header {
  height: 50px;
  border-bottom: 1px solid lightgray;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
}
.circle-border {
  height: 30px;
  width: 30px;
  border-radius: 15px;
  background: #67c23a;
}
.tag-color-div {
  display: flex;
  flex-direction: row;
  align-items: center;
  padding-right: 20px;
}
.produce-plan-detail-div {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
  padding: 0px 10px;
  margin-bottom: 20px;
  border-bottom: 1px solid lightgray;
  border-top: 1px solid lightgray;
}
.monitor-produce-plan-detail-cell {
  width: 180px;
  min-height: 30px;
  margin-left: 20px;
  display: flex;
  align-items: left;
}
.monitor-produce-plan-detail-row {
  padding: 0px 10px;
}
.border {
  border: 1px solid lightgray;
}
.produce-plan-detail-title-cell {
  display: flex;
  align-items: center;
  padding: 0 10px;
  height: 30px;
  border-left: 1px solid lightgray;
  font-weight: 500;
}
.monitor-productionLine-work-station-info {
  display: flex;
  height: 30px;
  flex-direction: column;
  align-items: center;
  width: 100%;
  line-height: 30px;
  border-bottom:  1px solid lightgray;
}
.monitor-productionLine-work-station-card {
  position: relative;
  border: 1px solid lightgray;
  width: 100%;
  height: 120px;
  border-radius: 5px;
}
.monitor-produce-work-station-float-div {
  position: absolute;
  display: flex;
  flex-direction: row;
  align-items: flex-end;
  height: 100%;
  width: 100%;
}

.monitor-work-station-plan-info {
  width: 100%;
  border-right: 0.1px solid rgb(201, 195, 195);
  border-top: 0.1px solid rgb(201, 195, 195);
  border-bottom: 0.1px solid rgb(201, 195, 195);
  border-radius: 2px;
  height: 90px;
  background: #67c23a;
}
.monitor-empty-work-station{
  align-items: center;
  justify-content: center;
  font-size: 1.5rem;
  color: lightgray;
}
</style>