<template>
  <div class="plan-detail-main-div">
    <div class="produce-plan-detail-title-div">
      <div>
        <span>订单号：</span><span>{{ producePlan.orderFormNo }}</span>
      </div>
      <div>
        <span>客户：</span><span>{{ producePlan.customerName }}</span>
      </div>
      <div>
        <span>类型：</span><span>{{ producePlan.type }}</span>
      </div>
      <div>
        <span>启动时间：</span
        ><el-date-picker
          size="mini"
          type="date"
          v-model="producePlan.startDate"
          placeholder="预计启动时间"
        >
        </el-date-picker>
      </div>
      <div>
        <span>完工时间：</span
        ><el-date-picker
          size="mini"
          type="date"
          v-model="producePlan.endDate"
          placeholder="预计结束时间"
        >
        </el-date-picker>
      </div>
    </div>
    <span class="plan-detail-title">生产详细</span>
    <div>
      <el-table
        :data="planDetails"
        border
        :loading="loading"
        style="width: 100%"
        stripe
        @expand-change="expandChanged"
        class="mt20 plan-detail-table"
      >
        <el-table-column type="expand" width="50px">
          <template slot-scope="props">
            <el-table
              :data="props.row.processList"
              border
              :show-header="false"
              size="mini"
              style="width: 100%"
              class="fz8"
              stripe
            >
              <el-table-column label="序号" prop="index" width="50px">
              </el-table-column>
              <el-table-column label="名称" prop="processName" width="200px">
              </el-table-column>
              <el-table-column label="料号">
                <template slot-scope="scope">
                  <el-tag
                    class="ml10"
                    type="warning"
                    effect="plain"
                    v-for="item in scope.row.materialVos"
                    :key="item.materialNo"
                    >{{ item.materialName }}X{{ item.count }}</el-tag
                  >
                </template>
              </el-table-column>
              <el-table-column>
                <template slot-scope="scope">
                  <el-tag
                    closable
                    class="ml10"
                    @close="workStationRemove(item.workStationId, scope.row)"
                    effect="plain"
                    v-for="item in scope.row.workStationList"
                    :key="item.workStationId"
                    >{{ item.produceLineName }}|{{ item.workStationName }}|{{
                      item.employeeGroupName
                    }}</el-tag
                  >
                  <el-popover
                    placement="right"
                    width="200"
                    size="mini"
                    trigger="click"
                    @show="prpoverOnShow(scope.row)"
                  >
                    <el-select
                      v-model="selectStationId"
                      placeholder="请选择"
                      @change="workStationChanged($event, scope.row)"
                    >
                      <el-option
                        v-for="workStation in workStationList"
                        :label="
                          workStation.produceLineName +
                          '|' +
                          workStation.name +
                          '|' +
                          workStation.employeeGroupName
                        "
                        :value="workStation.id"
                        :key="workStation.id"
                      >
                      </el-option>
                    </el-select>
                    <el-button
                      class="ml24"
                      size="mini"
                      type="primary"
                      icon="el-icon-plus"
                      plain
                      circle
                      slot="reference"
                    ></el-button>
                  </el-popover>
                </template>
              </el-table-column>
            </el-table>
          </template>
        </el-table-column>
        <el-table-column label="名称" prop="productName"> </el-table-column>
        <el-table-column label="规格" prop="specification"> </el-table-column>
        <el-table-column label="数量" prop="count">
          <template slot-scope="scope"
            ><span
              >{{ scope.row.count }}{{ scope.row.countUnit }}</span
            ></template
          >
        </el-table-column>
        <el-table-column label="状态" prop="plneState"> </el-table-column>
        <el-table-column label="备注" prop="remark"> </el-table-column>
      </el-table>
    </div>
    <div class="plan-detail-submit-div">
      <el-button
        type="primary"
        class="ml24"
        @click="SavePlanWorkStations(false)"
        >保存</el-button
      >
      <el-button type="primary">保存并下达</el-button>
    </div>
  </div>
</template>
<script>
import { mapActions } from "vuex";
export default {
  data() {
    return {
      selectStationId: "",
      producePlan: {},
      planDetails: [],
      workStationList: [],
      loading: false,
    };
  },

  methods: {
    ...mapActions({
      FindPlanDetailList: "FindPlanDetailList",
      FindPlanDetailProcessList: "FindPlanDetailProcessList",
      FindWorkStationListByProcessId: "FindWorkStationListByProcessId",
      SaveProducePlanWorkStation: "SaveProducePlanWorkStation",
      DeletePlanWorkStation: "DeletePlanWorkStation",
    }),
    loadPlanDetails(plan) {
      this.producePlan = plan;
      this.FindPlanDetailList({ planId: plan.id })
        .then((res) => {
          if (res.resultStatus == 1) {
            res.data.map((item) => {
              item.processList = [];
            });
            this.planDetails = res.data;
          } else {
            this.$message.warning(res.message);
          }
        })
        .catch((err) => {
          this.$message.error(err.message);
        });
    },
    expandChanged(row, sec) {
      let index = sec.indexOf(row);
      var expanded = index != -1; //获取是否展开 row为当前行
      if (expanded) {
        this.loading = true;
        this.FindPlanDetailProcessList({
          planId: row.producePlanId,
          productId: row.productId,
        })
          .then((res) => {
            if (res.resultStatus == 1) {
              this.loading = false;
              row.processList = res.data;
            } else {
              this.loading = false;
              this.$message.warning(res.message);
            }
          })
          .catch((err) => {
            this.loading = false;
            this.$message.error(err.message);
          });
      }
    },
    prpoverOnShow(row) {
      this.selectStationId = "";
      this.loading = true;
      this.FindWorkStationListByProcessId({ processId: row.processId })
        .then((res) => {
          if (res.resultStatus == 1) {
            this.loading = false;
            this.workStationList = res.data;
          } else {
            this.loading = false;
            this.$message.warning(res.message);
          }
        })
        .catch((err) => {
          this.loading = false;
          this.$message.error(err.message);
        });
    },

    workStationChanged(workStationId, row) {
      this.loading = true;
      var selectWorkStation = this.workStationList.find((item) => {
        return item.id == workStationId;
      });
      if (selectWorkStation) {
        var w = {
          workStationId: selectWorkStation.id,
          processId: row.processId,
          planId: this.producePlan.id,
          workStationName: selectWorkStation.name,
          produceLineName: selectWorkStation.produceLineName,
          employeeGroupName: selectWorkStation.employeeGroupName,
        };
        var index = row.workStationList.filter((item) => {
          return item.workStationId === w.workStationId;
        }).length;
        if (index > 0) {
          this.$message.warning("请勿重复添加");
          return;
        }
        row.workStationList.push(w);
      }
      this.loading = false;
    },

    SavePlanWorkStations(confirm) {
      var wsList = [];
      this.planDetails.map((product) => {
        product.processList.map((process) => {
          process.workStationList.map((station) => {
            wsList.push(station);
          });
        });
      });
      var obj = { plan: this.producePlan, workStationList: wsList };
      this.SaveProducePlanWorkStation(obj);
    },

    workStationRemove(id, row) {
      var removeItem = row.workStationList.find((item) => {
        return item.workStationId == id;
      });
      if (removeItem.id) {
        this.DeletePlanWorkStation({ id: id })
          .then((res) => {
            if (res.resultStatus == 1) {
              row.workStationList.splice(
                row.workStationList.indexOf(removeItem),
                1
              );
            } else {
              this.$message.warning(res.message);
            }
          })
          .catch((err) => {
            this.$message.error(err.message);
          });
      } else {
        row.workStationList.splice(row.workStationList.indexOf(removeItem), 1);
      }
    },
  },
};
</script>
<style>
.produce-plan-detail-title-div {
  display: flex;
  justify-content: space-between;
  flex-direction: row;
  align-items: center;
  font-size: 0.9rem;
}
.plan-detail-title {
  font-size: 1.1rem;
  color: #409eff;
  display: flex;
  margin-top: 1rem;
}
.plan-detail-main-div {
  padding: 0 10px;
}
.plan-detail-table /deep/.el-table__expanded-cell {
  padding-top: 0px !important;
  padding-right: 0px !important;
  padding-bottom: 0px !important;
}
.plan-detail-submit-div {
  height: 80px;
  display: flex;
  align-items: center;
  flex-direction: row-reverse;
}
</style>