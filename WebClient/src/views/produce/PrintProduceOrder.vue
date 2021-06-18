<template>
  <div>
    <div id="printProduceOrder" ref="printProduceOrder" class="printProduceOrder-div">
      <div class="printProduceOrder-header">
        <span class="printProduceOrder-header-left">{{ planInfo.producePlan.planNo }}</span>
        <span class="printProduceOrder-header-title">生产工单</span>
      </div>
      <div class="printProduceOrder-buyer-info-div-left-secfloor">
        <div class="title-div-gray flex justify-content">
          基础信息：
        </div>
        <div class="printProduceOrder-conten">
          <el-row class="printProduceOrder-row">
            <el-col :span="8" class="printProduceOrder-col">
              <div class="printProduceOrder-form-content" style="width:75px">生产单号 : </div>
              <div class="printProduceOrder-form-content" style="width:280px">{{ planInfo.producePlan.planNo }}</div>
            </el-col>
            <el-col :span="8" class="printProduceOrder-col">
              <div class="printProduceOrder-form-content" style="width:75px">创建日期 : </div>
              <div class="printProduceOrder-form-content" style="width:280px">{{ getFullTime(planInfo.producePlan.createDate) }}</div>
            </el-col>
            <el-col :span="8" class="printProduceOrder-col">
              <div class="printProduceOrder-form-content" style="width:75px">客户名称 : </div>
              <div class="printProduceOrder-form-content" style="width:280px">{{ planInfo.producePlan.customerName }}</div>
            </el-col>
          </el-row>
          <el-row class="printProduceOrder-row">
            <el-col :span="8" class="printProduceOrder-col">
              <div class="printProduceOrder-form-content" style="width:75px">产品名称 : </div>
              <div class="printProduceOrder-form-content" style="width:280px">{{ planDetail.productName }}</div>
            </el-col>
            <el-col :span="8" class="printProduceOrder-col">
              <div class="printProduceOrder-form-content" style="width:75px">产品规格 : </div>
              <div class="printProduceOrder-form-content" style="width:280px">{{ planDetail.specification }}</div>
            </el-col>
            <el-col :span="8" class="printProduceOrder-col">
              <div class="printProduceOrder-form-content" style="width:75px">产品料号 : </div>
              <div class="printProduceOrder-form-content" style="width:280px">{{ planDetail.materialNo }}</div>
            </el-col>
          </el-row>
          <el-row class="printProduceOrder-row">
            <el-col :span="8" class="printProduceOrder-col">
              <div class="printProduceOrder-form-content" style="width:75px">指令数量 : </div>
              <div class="printProduceOrder-form-content" style="width:280px">{{ planDetail.count }}{{planDetail.countUnit}}</div>
            </el-col>
            <el-col :span="8" class="printProduceOrder-col">
              <div class="printProduceOrder-form-content" style="width:75px">完工日期 : </div>
              <div class="printProduceOrder-form-content" style="width:280px">{{ getFullTime(planInfo.producePlan.endDate) }}</div>
            </el-col>
            <el-col :span="8" class="printProduceOrder-col">
              <div class="printProduceOrder-form-content" style="width:75px">领料单号 : </div>
              <div class="printProduceOrder-form-content" style="width:280px">{{ planInfo.producePlan.pickingTicketNo }}</div>
            </el-col>
          </el-row>
        </div>
      </div>
      <div class="printProduceOrder-buyer-info-div-left-secfloor">
        <div class="title-div-gray flex justify-content">
          生产工序:
        </div>
            <el-table
              :data="planDetail.processList"
              border
              size="mini"
              style="width: 100%"
              stripe
            >
              <el-table-column label="名称" prop="processName" width="150px">
                <template slot-scope="scope">
                  <span>{{ scope.row.processName }}</span>
                </template>
              </el-table-column>
              <el-table-column label="物料需求">
                <template slot-scope="scope">
                  <el-tag
                    class="ml10"
                    style="width:110px"
                    type="warning"
                    effect="plain"
                    v-for="item in scope.row.materialVos"
                    :key="item.materialNo"
                    >{{ item.materialName }}X{{ item.count }}</el-tag
                  >
                </template>
              </el-table-column>
              <el-table-column label="工作组"  width="350px">
                <template slot-scope="scope">
                  <el-tag
                    class="ml10"
                    style="width:300px"
                    effect="plain"
                    v-for="item in scope.row.workStationList"
                    :key="item.workStationId"
                    >{{ item.produceLineName }}|{{ item.workStationName }}|{{
                      item.employeeGroupName
                    }}</el-tag
                  >
                </template>
              </el-table-column>
              <el-table-column label="签字" width="80px"></el-table-column>
            </el-table>
      </div>

      <div class="printProduceOrder-product-detail-div">
        <div class="title-div-gray flex justify-content">
          领料详情:
        </div>
        <el-table
          border
          :data="planInfo.pickingTicketDetails"
          style="width: 100%"
          size="mini"
          stripe
        >
          <el-table-column prop="materialNo" label="物料号"> </el-table-column>
          <el-table-column prop="materialName" label="物料名称"> </el-table-column>
          <el-table-column prop="specification" label="规格"> </el-table-column>
          <el-table-column prop="count" label="数量" width="130px">
              <template slot-scope="scope">
                <span>{{ scope.row.count }}{{ scope.row.countUnit }}</span>
              </template>
          </el-table-column>
          <el-table-column prop="remark" label="备注"></el-table-column>
          <el-table-column label="签字确认" ></el-table-column>

        </el-table>
      </div>
    </div>
    <div class="edit-printProduceOrder-button-div">
      <el-button type="primary" @click="printPdf">打印合同</el-button>
    </div>
  </div>
</template>
<script>
import { mapGetters } from "vuex";
import { mapActions } from "vuex";
import print from "print-js";
export default {
  name: "m-create-intent-constract",
  data() {
    return {
      Datetime:new Date().format("yyyy-MM-dd"),
      poDetailList:[],
      planDetail:[],
      planInfo: {
        producePlan: {},
        planDetails: [],
        delegateTicket: {},
        delegateDetailList: [],
        pickingTicket: {},
        pickingTicketDetails: [],
      },

    };
  },
  computed: {
    ...mapGetters(["contractTemplate", "userInfo", "supplyList","prList","poList"]),
    poSum:function() {
      var price = 0 ;
      var total = 0;
      this.planInfo.poDetailList.map((item) => {
        total = this.$my.NumberAdd(total, this.$my.NumberMul(item.count, item.costPrice));
      });
      price = total.toFixed(2);
      return price;
    },

  },
  methods: {
    ...mapActions({
      LoadPlanInfo: "LoadPlanInfo",
    }),
    getFullTime(time) {
    return new Date(time).format("yyyy-MM-dd");
    },
    printPdf() {
      print({
        printable: "printProduceOrder",
        type: "html",
        maxWidth: "100%",
        targetStyles: ["*"],
      });
    },
    loadPlanInfo(planId) {
      this.loading = true;
      this.LoadPlanInfo({ planId: planId })
        .then((res) => {
          if (res.resultStatus == 1) {
            this.planInfo = res.data;
            this.planDetail=res.data.planDetails[0];
            this.loading = false;
          } else {
            this.$message.warning(res.message);
            this.loading = false;
          }
        })
        .catch((err) => {
          this.$message.error(err.message);
          this.loading = false;
        });
    },

  },
  // beforeMount() {
  //   this.GetContractTemplate();
  // },
};
</script>
<style lang="less">
.printProduceOrder-div {
  padding: 1rem;
  border: 1px solid rgb(233, 229, 229);
  display: flex;
  flex-direction: column;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
}

.printProduceOrder-header {
  height: 60px;
  display: inline;
  line-height: 60px;
  vertical-align: center;
}
.printProduceOrder-header-left {
  margin-right: 10px;
  font-size: 1.5rem;
  color: #606266;
  float: left;
}
.printProduceOrder-header-title {
  float: right;
  margin-right: 10px;
  letter-spacing: 7px;
  font-size: 1.5rem;
  color: #606266;
}
.printProduceOrder-buyer-info-div {
  display: flex;
  flex-direction: row;
  border: 1px solid rgb(209, 205, 205);
}
// .printProduceOrder-buyer-info-div-left {
//   height: 100%;
//   border-right: 1px solid rgb(209, 205, 205);
//   width: 100%;
// }
.printProduceOrder-buyer-info-div-left-firstfloor {
  margin-top: 15px;
  height: 50%;
  display: flex;
  flex-direction: column;
  border: 1px solid rgb(209, 205, 205);
}
.printProduceOrder-buyer-info-div-left-secfloor {
  margin-top: 15px;
  display: flex;
  flex-direction: column;
  height: 50%;
  border: 1px solid rgb(209, 205, 205);
}
.title-div-gray {
  background: lightgray;
  height: 35px;
  display: flex;
  align-items: center;
  padding: 0px 10px;
  font-weight: bold;
}

.printProduceOrder-product-detail-div {
  margin-top: 15px;
  border: 1px solid rgb(209, 205, 205);
}

.printProduceOrder-content {
  text-align: left;
}
.printProduceOrder-remark-div {
  display: flex;
  flex-direction: row;
}
.printProduceOrder-remark-p {
  text-align: left;
  vertical-align: top;
}
.remark-title-div {
  min-width: 50px;
  text-align: left;
}

.edit-printProduceOrder-button-div {
  padding: 1rem 0;
  text-align: right;
}
.justify-content {
  justify-content: space-between;
}
.add-printProduceOrder-deail {
  margin-top: 2px;
  width: 100%;
  color: #259dff;
  border: #dcdfe6 dashed 1px !important;
}
.printProduceOrder-product-detail-select-div {
  display: flex;
  align-items: center;
  justify-content: space-between;
}
.printProduceOrder-product-seen-div {
  display: flex;
  align-items: center;
}
.form-input {
  width: 350px;
  float: left;
}
.tempate-text-left-center {
  float: left;
  font-size: 0.8rem;
  padding: 0.3rem;
  text-align: left;
  width: 400px;
}
.printProduceOrder-conten {
  padding: 8px;
}
.printProduceOrder-supply{
   padding-left: 10px;
   padding-top:15px;
   padding-right: 10px;
}
.printProduceOrder-form-content {
  text-align: left;
  height: 30px;
  line-height: 30px;
  float: left;
  padding-top: 5px;
}
.printProduceOrder-row{
  border: 1px solid rgb(209, 205, 205);
  width: 100%;
  padding: 3px;
}
.printProduceOrder-col{
  border: 1px solid rgb(209, 205, 205);
}
</style>
