<template>
  <div class="contract-template-main-container">
    <div>
      <div
        class="contract-template-div"
        id="contractTeplateDiv"
        red="contractTeplateDiv"
      >
        <div class="contract-template-header">
          <span class="contract-template-header-title">销售合同确认书</span>
        </div>
        <div class="contract-template-buyer-info-div">
          <div class="contract-template-buyer-info-div-left">
            <div class="contract-template-buyer-info-div-left-firstfloor">
              <div class="title-div-gray">
                买方:{{ contractInfo.purchaser.customerNo }}
              </div>
              <span class="tempate-text-left-center"
                >名称：{{ contractInfo.purchaser.name }}</span
              >
              <span class="tempate-text-left-center"
                >地址：{{ contractInfo.purchaser.address }}</span
              >
              <span class="tempate-text-left-center"
                >联系：{{ contractInfo.purchaser.contactUser }}</span
              >
              <span class="tempate-text-left-center"
                >电话：{{ contractInfo.purchaser.phone }}</span
              >
              <span class="tempate-text-left-center"
                >税号：{{ contractInfo.purchaser.taxNo }}</span
              >
              <span class="tempate-text-left-center"
                >账号：{{ contractInfo.purchaser.account }}</span
              >
            </div>
            <div class="contract-template-buyer-info-div-left-secfloor">
              <div class="title-div-gray">
                收货单位:{{ contractInfo.goodReceiveInfo.customerNo }}
              </div>
              <span class="tempate-text-left-center"
                >名称：{{ contractInfo.goodReceiveInfo.name }}</span
              >
              <span class="tempate-text-left-center"
                >地址：{{ contractInfo.goodReceiveInfo.address }}</span
              >
              <span class="tempate-text-left-center"
                >联系：{{ contractInfo.goodReceiveInfo.contactUser }}</span
              >
              <span class="tempate-text-left-center"
                >电话：{{ contractInfo.goodReceiveInfo.phone }}</span
              >
              <span class="tempate-text-left-center"
                >税号：{{ contractInfo.goodReceiveInfo.taxNo }}</span
              >
              <span class="tempate-text-left-center"
                >账号：{{ contractInfo.goodReceiveInfo.account }}</span
              >
            </div>
          </div>
          <div class="contract-template-buyer-info-div-right">
            <div class="title-div-gray">资料</div>
            <span
              class="tempate-text-left-center"
              v-for="detail in contractTemplate.detailList"
              :key="detail.id"
              >{{ detail.value }}：{{
                contractInfo.contract[detail.bindingField]
              }}</span
            >
          </div>
        </div>

        <div class="contract-template-product-detail-div">
          <el-table
            border
            class="template-contract-product-table"
            :data="contractInfo.productList"
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
            <el-table-column
              prop="taxTotalPrice"
              label="含税总价"
              width="190px"
            >
            </el-table-column>
          </el-table>
          <div class="contract-template-product-detail-summary-div fz9">
            <div class="contract-template-product-detail-summary-item">
              <span style="width: 180px; text-align: left">总额(含税)</span>
              <span style="width: 190px; text-align: left">{{
                contractProductTotal.price
              }}</span>
            </div>
            <div class="contract-template-product-detail-summary-item">
              <div style="width: 180px; text-align: left">销项税</div>
              <span style="width: 130px"
                >{{ contractInfo.contract.taxRate }}%</span
              >
              <span style="width: 190px; text-align: left">{{
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
            <div style="width: 100%">
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
            <span>名称：{{ contractInfo.vendorConfirm.name }}</span>
            <span>地址：{{ contractInfo.vendorConfirm.address }}</span>
            <span>账号：{{ contractInfo.vendorConfirm.account }}</span>
            <span>税号：{{ contractInfo.vendorConfirm.taxNo }}</span>
            <span>开户行：{{ contractInfo.vendorConfirm.bank }}</span>
          </div>
          <div class="contract-confirm-box">
            <span>买方确认</span>
            <span>名称：{{ contractInfo.purchaserConfirm.name }}</span>
            <span>地址：{{ contractInfo.purchaserConfirm.address }}</span>
            <span>日期：</span>
            <span>账号：{{ contractInfo.purchaserConfirm.account }}</span>
            <span>税号：{{ contractInfo.purchaserConfirm.taxNo }}</span>
            <span>开户行：{{ contractInfo.purchaserConfirm.bank }}</span>
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
      contractInfo: {
        purchaser: {},
        goodReceiveInfo: {},
        contract: {},
        vendorConfirm: {},
        purchaserConfirm: {},
        productList: [],
      },
    };
  },
  methods: {
    ...mapActions({
      GetContractTemplate: "GetContractTemplate",
      GetContractOne: "GetContractOne",
    }),
    loadContract(contractId) {
      const loading = this.$loading({
        lock: true,
        text: "Loading",
        spinner: "el-icon-loading",
        background: "rgba(0, 0, 0, 0.7)",
      });
      this.GetContractOne({ id: contractId })
        .then((res) => {
          if (res.resultStatus == 1) {
            this.contractInfo = res.data;
            loading.close();
          } else {
            loading.close();
          }
        })
        .catch((e) => {
          loading.close();
        });
    },
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
      if (!this.contractInfo) return contractSummary;
      var total = 0;
      this.contractInfo.productList.map((item) => {
        item.taxTotalPrice = this.$my.NumberMul(item.count, item.taxPrice);
        total = this.$my.NumberAdd(total, item.taxTotalPrice);
      });
      contractSummary.price = total.toFixed(2);
      contractSummary.tax = this.$my.NumberMul(
        this.$my.NumberDiv(parseInt(this.contractInfo.contract.taxRate), 100),
        contractSummary.price
      );
      return contractSummary;
    },
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
.template-contract-product-table {
  width: 100%;
}
</style>