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
          <div>
            <el-col :span="8">
              <span class="printProduceOrder-form-title">客户名称：</span>
              <div class="printProduceOrder-form-content">{{ planInfo.producePlan.customerName }}</div>
            </el-col>
            <el-col :span="8">
              <span class="printProduceOrder-form-title">创建日期</span>
              <div class="printProduceOrder-form-content">{{ planInfo.producePlan.customerName }}</div>
            </el-col>
            <el-col :span="8">
              <span class="printProduceOrder-form-title">客户名称：</span>
              <div class="printProduceOrder-form-content">{{ planInfo.producePlan.customerName }}</div>
            </el-col>
          </div>
          <div>
            <el-col :span="12">
              <span class="tempate-text-left-center"
                >联系人员：{{ planInfo.supply.contact }}</span
              >
            </el-col>
            <el-col :span="12">
              <span class="tempate-text-left-center"
                >联系电话：{{ planInfo.supply.phone }}</span
              >
            </el-col>
          </div>
        </div>
      </div>
      <div class="printProduceOrder-buyer-info-div-left-secfloor">
        <div class="title-div-gray flex justify-content">
          生产工序:
        </div>
        <div class="printProduceOrder-conten">
          <div>
            <el-col :span="12">
              <span class="tempate-text-left-center"
                >买方名称：{{ userInfo.company.companyName }}</span
              >
            </el-col>
            <el-col :span="12">
              <span class="tempate-text-left-center"
                >买方地址：{{ userInfo.company.provinceName
                }}{{ userInfo.company.cityName }}{{ userInfo.company.areaName
                }}{{ userInfo.company.address }}</span
              >
            </el-col>
          </div>
          <div>
            <el-col :span="12">
              <span class="tempate-text-left-center"
                >联系人员：{{ userInfo.company.communicateName }}</span
              >
            </el-col>
            <el-col :span="12">
              <span class="tempate-text-left-center"
                >联系电话：{{ userInfo.company.phoneNumber }}</span
              >
            </el-col>
          </div>
        </div>
      </div>

      <div class="printProduceOrder-product-detail-div">
        <div class="title-div-gray flex justify-content">
          领料详情:
        </div>
        <el-table
          border
          :data="poDetailList"
          style="width: 100%"
          size="mini"
          :header-cell-style="{
            background: 'lightgray',
            color: 'black',
            weight: 'bold',
          }"
        >
          <el-table-column prop="materialName" label="名称"> </el-table-column>
          <el-table-column prop="materialName" label="名称"> </el-table-column>
          <el-table-column prop="specification" label="规格"> </el-table-column>
          <el-table-column prop="count" label="数量" width="130px">
              <template slot-scope="scope">
                <span>{{ scope.row.count }}{{ scope.row.countUnit }}</span>
              </template>
          </el-table-column>
          <el-table-column label="签字确认" width="130px"></el-table-column>
          <!-- <el-table-column prop="remark" label="备注">
          </el-table-column> -->
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
console.log(this.planInfo);
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
  beforeMount() {
    this.GetContractTemplate();
  },
};
</script>
<style lang="less">
.printProduceOrder-div {
  width: 950px;
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
.printProduceOrder-form-title {
  width: 120px;
  text-align: left;
  height: 30px;
  line-height: 30px;
  float: left;
}
.printProduceOrder-form-content {
  text-align: left;
  height: 30px;
  line-height: 30px;
}
</style>
