<template>
  <div class="contract-template-main-container">
    <div>
      <div class="contract-template-div" id="contractTeplateDiv" red="contractTeplateDiv">
        <div class="contract-template-header">
          <span class="contract-template-header-title">销售合同确认书</span>
        </div>
        <div class="contract-template-buyer-info-div">
          <div class="contract-template-buyer-info-div-left">
            <div class="contract-template-buyer-info-div-left-firstfloor">
              <div class="title-div-gray">买方:(0000001)</div>
              <span class="tempate-text-left-center"
                >名称：{{ vendeeInfo.name }}</span
              >
              <span class="tempate-text-left-center"
                >地址：{{ vendeeInfo.address }}</span
              >
              <span class="tempate-text-left-center"
                >联系：{{ vendeeInfo.contactUser }}</span
              >
              <span class="tempate-text-left-center"
                >电话：{{ vendeeInfo.phone }}</span
              >
              <span class="tempate-text-left-center"
                >税号：{{ vendeeInfo.taxNo }}</span
              >
              <span class="tempate-text-left-center"
                >账号：{{ vendeeInfo.account }}</span
              >
            </div>
            <div class="contract-template-buyer-info-div-left-secfloor">
              <div class="title-div-gray">收货单位:(0000001)</div>
              <span class="tempate-text-left-center"
                >名称：{{ goodReceveInfo.name }}</span
              >
              <span class="tempate-text-left-center"
                >地址：{{ goodReceveInfo.address }}</span
              >
              <span class="tempate-text-left-center"
                >联系：{{ goodReceveInfo.contactUser }}</span
              >
              <span class="tempate-text-left-center"
                >电话：{{ goodReceveInfo.phone }}</span
              >
              <span class="tempate-text-left-center"
                >税号：{{ goodReceveInfo.taxNo }}</span
              >
              <span class="tempate-text-left-center"
                >账号：{{ goodReceveInfo.account }}</span
              >
            </div>
          </div>
          <div class="contract-template-buyer-info-div-right">
            <div class="title-div-gray">资料</div>
            <span
              class="tempate-text-left-center"
              v-for="detail in contractTemplate.detailList"
              :key="detail.id"
              >{{ detail.value }}：{{ contractInfo[detail.bindingField] }}</span
            >
          </div>
        </div>

        <div class="contract-template-product-detail-div">
          <el-table
            border
            class="template-contract-product-table"
            :data="contractProductList"
            size="mini"
            :header-cell-style="{
              background: 'lightgray',
              color: 'black',
              weight: 'bold',
            }"
          >
            <el-table-column prop="productName" label="名称" width="200px">
            </el-table-column>
            <el-table-column prop="specification" label="规格" width="200px">
            </el-table-column>
            <el-table-column prop="remark" label="备注" width="200px">
            </el-table-column>
            <el-table-column prop="count" label="数量" width="100px">
              <template slot-scope="scope">
                <span>{{ scope.row.count }}{{ scope.row.countUnit }}</span>
              </template>
            </el-table-column>
            <el-table-column prop="taxPrice" label="含税单价" width="100px">
            </el-table-column>
            <el-table-column prop="taxTotalPrice" label="含税总价" width="190px">
            </el-table-column>
          </el-table>
          <div class="contract-template-product-detail-summary-div fz9">
            <div class="contract-template-product-detail-summary-item">
              <span style="width: 180px; text-align: left">总额(含税)</span>
              <span style="width:190px; text-align: left">{{
                contractProductTotal.price
              }}</span>
            </div>
            <div class="contract-template-product-detail-summary-item">
              <div style="width: 180px; text-align: left">销项税</div>
              <span style="width: 130px"
                >{{ contractInfo.taxRate }}%</span
              >
              <span style="width:190px;text-align: left">{{
                contractProductTotal.tax
              }}</span>
            </div>
          </div>
        </div>
        <div class="fz8">
          <p class="contract-template-content">
            {{ contractTemplate.content.value }}
          </p>
          <div class="contract-template-remark-div">
            <div class="remark-title-div">备注</div>
            <div style="width:100%">
              <div
                class="contract-remark-p"
                v-for="remark in contractTemplate.remarkList"
                :key="remark.id"
              >
                {{ remark.value }}
              </div>
            </div>
          </div>
        </div>
        <div class="contract-confirm-div fz9">
          <div class="contract-confirm-box">
            <span>卖方确认</span>
            <span>{{ companyInfo.name }}</span>
            <span>{{ companyInfo.location }}</span>
            <span>开户行：{{ companyInfo.bank }}</span>
            <span>账号：{{ companyInfo.account }}</span>
            <span>税号：{{ companyInfo.taxNo }}</span>
          </div>
          <div class="contract-confirm-box">
            <span>买方确认</span>
            <span>{{ vendeeInfo.name }}</span>
            <span>{{ vendeeInfo.location }}</span>
            <span>日期：</span>
            <span>开户行：{{ vendeeInfo.bank }}</span>
            <span>账号：{{ vendeeInfo.account }}</span>
            <span>税号：{{ vendeeInfo.taxNo }}</span>
          </div>
        </div>
        <p class="contract-description">
          {{ contractTemplate.description.value }}
        </p>
      </div>
      <div class="contract-button-div">
        <el-button type="primary" @click="printPdf">打印合同</el-button>
      </div>
    </div>
  </div>
</template>
<script>
import { mapGetters } from "vuex";
import { mapActions } from "vuex";
import print from "print-js";
export default {
  data() {
    return {
      vendeeInfo: {
        name: "桑尼泰克精密零部件有限公司",
        address: "昆山市周市镇新镇路681号",
        contactUser: "潘先生",
        phone: "0512-57861208",
        taxNo: "9S78945612368U",
        account: "62001102368944789",
      },
      goodReceveInfo: {
        name: "桑尼泰克精密零部件有限公司",
        address: "昆山市周市镇新镇路681号",
        contactUser: "潘先生",
        phone: "0512-57861208",
        taxNo: "9S78945612368U",
        account: "62001102368944789",
      },
      contractInfo: {
        contractNo: "1610670360",
        contractDate: "2020-04-06",
        diliveryWay: "60-1",
        type: "",
        orderTicketNo: "2021041405",
        orderDes: "暂不开票",
        diliveryType: "FCA CHINA",
        payDate: "2014-04-23",
        payWay: "",
        currency: "RMB",
        diliveryDate: "订单下达后20天",
        contact: " 张XX T：241699788",
        quality:
          "质量保证质量保证质量保证质量保证质量保证质量保证质量保证质量保证质量保证质量保证质量保证质量保证",
        taxRate: "13",
      },
      companyInfo: {
        name: "测试公司数据",
        address: "苏州市吴中区鹿山路001号",
        bank: "中国银行高新区狮山路分行",
        account: "pp0956743",
        taxNo: "91384792U874K9",
      },
      contractProductList: [
        {
          id: 1,
          productName: "圆珠笔",
          specification: "0.38mm*红",
          value: "圆珠笔/0.38mm*红",
          remark: "小包装",
          taxPrice: "2.7",
          count: 20,
          countUnit: "箱",
        },
        {
          id: 2,
          productName: "网线",
          specification: "3m*蓝色",
          value: "网线/3m*蓝色",
          remark: "10根一捆",
          taxPrice: "3.1",
          count: 100,
          countUnit: "包",
        },
         {
          id: 3,
          productName: "圆珠笔",
          specification: "0.38mm*红",
          value: "圆珠笔/0.38mm*红",
          remark: "小包装",
          taxPrice: "2.7",
          count: 200,
          countUnit: "箱",
        },
        {
          id: 4,
          productName: "网线",
          specification: "3m*蓝色",
          value: "网线/3m*蓝色",
          remark: "10根一捆",
          taxPrice: "3.1",
          count: 410,
          countUnit: "包",
        },
      ],
    };
  },
  methods: {
    ...mapActions({
      GetContractTemplate: "GetContractTemplate",
    }),
    printPdf() {
      print({
        printable: "contractTeplateDiv",
        type: "html",
        maxWidth: "100%",
        targetStyles: ["*"],
      });
    },
  },
  computed: {
    ...mapGetters(["contractTemplate"]),
    contractProductTotal: function () {
      var contractSummary = { price: 0, tax: 0 };
      var total = 0;
      this.contractProductList.map((item) => {
        item.taxTotalPrice = this.$my.NumberMul(item.count, item.taxPrice);
        total = this.$my.NumberAdd(total, item.taxTotalPrice);
      });
      contractSummary.price = total.toFixed(2);
      contractSummary.tax = this.$my.NumberMul(
        this.$my.NumberDiv(this.contractInfo.taxRate, 100),
        contractSummary.price
      );
      return contractSummary;
    },
  },
  props: ["contractId"],
  beforeMount() {
    this.GetContractTemplate();
  },
};
</script>
<style>
.contract-template-div {
  width: 1000px;
  padding: 1.2rem;
  border: 1px solid rgb(233, 229, 229);
  display: flex;
  flex-direction: column;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
}
.contract-template-main-container {
  display: flex;
  align-items: flex-start;
  justify-content: center;
}
.contract-template-header {
  height: 60px;
  display: inline;
  line-height: 60px;
  vertical-align: center;
}
.contract-template-header-title {
  float: right;
  margin-right: 10px;
  letter-spacing: 7px;
  font-size: 1.5rem;
  color: #606266;
}
.contract-template-buyer-info-div {
  display: flex;
  flex-direction: row;
  height: 450px;
  border: 1px solid rgb(129, 127, 127);
}
.contract-template-buyer-info-div-left {
  height: 100%;
  width: 55%;
}
.contract-template-buyer-info-div-right {
  height: 100%;
  width: 45%;
  border-left: 1px solid rgb(129, 127, 127);
  display: flex;
  flex-direction: column;
}
.contract-template-buyer-info-div-left-firstfloor {
  height: 50%;
  display: flex;
  flex-direction: column;
}
.contract-template-buyer-info-div-left-secfloor {
  display: flex;
  flex-direction: column;
  height: 50%;
}
.title-div-gray {
  background: lightgray;
  height: 35px;
  display: flex;
  align-items: center;
  padding: 0px 10px;
  font-weight: bold;
}
.tempate-text-left-center {
  float: left;
  font-size: 0.8rem;
  padding: 0.3rem;
  text-align: left;
}
.contract-template-product-detail-div {
  margin-top: 15px;
  border: 1px solid rgb(129, 127, 127);
}
.contract-template-product-detail-summary-div {
  display: flex;
  flex-direction: column;
}
.contract-template-product-detail-summary-item {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
  padding: 2px 5px;
}
.contract-template-content {
  text-align: left;
}
.contract-template-remark-div {
  display: flex;
  flex-direction: row;
  width: 100%;
}
.contract-remark-p {
  text-align: left;
  width: 100%;
  vertical-align: top;
}
.remark-title-div {
  min-width: 50px;
  text-align: left;
}
.contract-confirm-div {
  display: flex;
  flex-direction: row;
  margin-top: 1rem;
}
.contract-confirm-box {
  width: 50%;
  text-align: left;
  display: flex;
  flex-direction: column;
}
.contract-description {
  text-align: left;
  font-size: 0.3rem;
  margin-top: 2rem;
}
.contract-button-div {
  padding: 1rem 0;
  float: right;
}
.template-contract-product-table{
  width: 100%;
}
</style>