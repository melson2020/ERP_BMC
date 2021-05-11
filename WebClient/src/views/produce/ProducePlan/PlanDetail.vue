<template>
  <div class="plan-detail-main-div">
    <div class="produce-plan-detail-title-div">
      <div><span>订单号：</span><span>订单号01</span></div>
      <div><span>客户：</span><span>测试客户</span></div>
      <div><span>类型：</span><span>生产</span></div>
      <div>
        <span>启动时间：</span
        ><el-date-picker size="mini" type="date" placeholder="选择日期">
        </el-date-picker>
      </div>
      <div>
        <span>完工时间：</span
        ><el-date-picker size="mini" type="date" placeholder="选择日期">
        </el-date-picker>
      </div>
    </div>
    <span class="plan-detail-title">生产详细</span>
    <div>
      <el-table
        :data="selectedPlan.productList"
        border
        size="mini"
        style="width: 100%"
        stripe
        class="mt20 plan-detail-table"
      >
        <el-table-column type="expand" width="50px">
          <template slot-scope="props">
            <el-table
              :data="props.row.workTechList"
              border
              :show-header="false"
              size="mini"
              style="width: 100%"
              class="fz8"
              stripe
            >
              <el-table-column label="序号" prop="index"> </el-table-column>
              <el-table-column label="名称" prop="name"> </el-table-column>
              <el-table-column label="料号">
                <template slot-scope="scope">
                  <el-tag
                    class="ml10"
                    type="info"
                    effect="plain"
                    v-for="item in scope.row.itemList"
                    :key="item.itemName"
                    >{{ item.itemName }}</el-tag
                  >
                </template>
              </el-table-column>
              <el-table-column>
                <template slot-scope="scope">
                  <el-tag
                    class="ml10"
                    effect="plain"
                    v-for="item in scope.row.workStationList"
                    :key="item.productLineName"
                    >{{ item.productLineName }}|{{ item.workStationName }}|{{
                      item.employeeName
                    }}</el-tag
                  >
                  <el-popover placement="right" width="200" trigger="click">
                    <el-select  placeholder="请选择">
                      <el-option label="产线1 工位3 组别1" value="1234">
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
        <el-table-column label="数量" prop="count"> </el-table-column>
        <el-table-column label="状态" prop="plneState"> </el-table-column>
        <el-table-column label="备注" prop="remark"> </el-table-column>
      </el-table>
    </div>
    <div class="plan-detail-submit-div">
      <el-button type="primary" class="ml24">保存</el-button>
      <el-button type="primary" >保存并下达</el-button>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      producePlan:{},
      planDetails:[],
      selectedPlan: {
        orderNo: "订单号01",
        planNo: "计划内部编码01",
        customerName: "测试客户",
        type: "生产",
        beginDate: "",
        completedDate: "",
        pickingTikcetNo: "领料单01",
        pickingTikcetState: "未领料",
        productList: [
          {
            productName: "产品A",
            specification: "100*100*200/红色",
            count: "100",
            remark: "50个/箱",
            plneState: "未配置",
            workTechList: [
              {
                index: "1",
                name: "工序1",
                itemList: [{ itemName: "物料1" }],
                workStationList: [
                  {
                    productLineName: "产线1",
                    workStationName: "工位1",
                    employeeName: "组别1",
                  },
                ],
              },
              {
                index: "2",
                name: "工序2",
                itemList: [{ itemName: "物料2" }, { itemName: "物料6" }],
                workStationList: [
                  {
                    productLineName: "产线1",
                    workStationName: "工位1",
                    employeeName: "组别1",
                  },
                ],
              },
              {
                index: "3",
                name: "工序3",
                itemList: [{ itemName: "物料3" }],
                workStationList: [
                  {
                    productLineName: "产线2",
                    workStationName: "工位1",
                    employeeName: "组别1",
                  },
                ],
              },
              {
                index: "4",
                name: "工序4",
                itemList: [{ itemName: "物料4" }],
                workStationList: [
                  {
                    productLineName: "产线3",
                    workStationName: "工位1",
                    employeeName: "组别1",
                  },
                ],
              },
              {
                index: "5",
                name: "组装",
                itemList: [{ itemName: "物料5" }],
                workStationList: [
                  {
                    productLineName: "产线4",
                    workStationName: "工位1",
                    employeeName: "组别1",
                  },
                ],
              },
            ],
          },
        ],
      },
    };
  },

  methods:{
    loadPlanDetails(plan){
      console.log(plan)
    }
  }
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
.plan-detail-submit-div{
    height: 80px;
    display: flex;
    align-items: center;
    flex-direction: row-reverse;
}
</style>