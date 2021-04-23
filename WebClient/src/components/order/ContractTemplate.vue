<template>
  <div class="contract-template-main-container">
    <div>
      <div class="contract-template-div">
        <div class="contract-template-header">
          <span class="contract-template-header-title">销售合同确认书</span>
        </div>
        <div class="contract-template-buyer-info-div">
          <div class="contract-template-buyer-info-div-left">
            <div class="contract-template-buyer-info-div-left-firstfloor">
              <div class="title-div-gray">买方:(0000001)</div>
              <span class="text-left-center">{{ vendeeInfo.name }}</span>
              <span class="text-left-center">{{ vendeeInfo.location }}</span>
              <span class="text-left-center"
                >联系：{{ vendeeInfo.contact }}</span
              >
              <span class="text-left-center">电话：{{ vendeeInfo.phone }}</span>
              <span class="text-left-center">税号：{{ vendeeInfo.taxNo }}</span>
              <span class="text-left-center"
                >账号：{{ vendeeInfo.account }}</span
              >
            </div>
            <div class="contract-template-buyer-info-div-left-secfloor">
              <div class="title-div-gray">收货单位:(0000001)</div>
              <span class="text-left-center">{{ goodReceveInfo.name }}</span>
              <span class="text-left-center">{{
                goodReceveInfo.location
              }}</span>
              <span class="text-left-center"
                >联系：{{ goodReceveInfo.contact }}</span
              >
              <span class="text-left-center"
                >电话：{{ goodReceveInfo.phone }}</span
              >
              <span class="text-left-center"
                >税号：{{ goodReceveInfo.taxNo }}</span
              >
              <span class="text-left-center"
                >账号：{{ goodReceveInfo.account }}</span
              >
            </div>
          </div>
          <div class="contract-template-buyer-info-div-right">
            <div class="title-div-gray">资料</div>
            <span class="text-left-center"
              >销售单编号：{{ contractInfo.contractNo }}</span
            >
            <span class="text-left-center"
              >销售单日期：{{ contractInfo.contractDate }}</span
            >
            <span class="text-left-center"
              >送货路线：{{ contractInfo.diliveryWay }}</span
            >
            <span class="text-left-center"
              >客户订单号码：{{ contractInfo.orderTicketNo }}</span
            >
            <span class="text-left-center"
              >运输方式：{{ contractInfo.diliveryType }}</span
            >
            <span class="text-left-center"
              >付款时间：{{ contractInfo.payDate }}</span
            >
            <span class="text-left-center"
              >付款方式：{{ contractInfo.payWay }}</span
            >
            <span class="text-left-center"
              >货币：{{ contractInfo.currency }}</span
            >
            <span class="text-left-center"
              >交货日期：{{ contractInfo.diliveryDate }}</span
            >
            <span class="text-left-center"
              >联系人：{{ contractInfo.contact }}</span
            >
            <span class="text-left-center"
              >质量保证：{{ contractInfo.quality }}</span
            >
          </div>
        </div>

        <div class="contract-template-product-detail-div">
          <el-table
            border
            style="width: 100%"
            size="mini"
            :header-cell-style="{
              background: 'lightgray',
              color: 'black',
              weight: 'bold',
            }"
          >
            <el-table-column prop="productName" label="名称" width="180px">
            </el-table-column>
            <el-table-column prop="props" label="属性" width="180px">
            </el-table-column>
            <el-table-column prop="remark" label="备注" width="200px">
            </el-table-column>
            <el-table-column prop="number" label="数量" width="130px">
            </el-table-column>
            <el-table-column prop="taxPrice" label="含税单价" width="130px">
            </el-table-column>
            <el-table-column prop="taxTotalPrice" label="含税总价">
            </el-table-column>
          </el-table>
          <div class="contract-template-product-detail-summary-div fz9">
            <div class="contract-template-product-detail-summary-item">
              <span style="width: 180px; text-align: left">总额(含税)</span>
              <span style="width: 130px; margin-left: 380px">1</span>
              <span style="margin-left: 130px; text-align: right">3103.21</span>
            </div>
            <div class="contract-template-product-detail-summary-item">
              <div style="width: 180px; text-align: left">销项税</div>
              <span style="width: 130px; margin-left: 510px">13%</span>
              <span style="text-align: right">357.00</span>
            </div>
          </div>
        </div>
        <div class="fz8">
          <p class="contract-template-content">{{ contractContent }}</p>
          <div class="contract-template-remark-div">
            <div class="remark-title-div">备注</div>
            <div>
              <div
                class="contract-remark-p"
                v-for="remark in contractRemarks"
                :key="remark.id"
              >
                {{ remark.remark }}
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
          合同说明，合同说明，合同说明，合同说明，合同说明，合同说明，合同说明，合同说明，合同说明，合同说明，合同说明，合同说明，合同说明，合同说明，合同说明，合同说明合同说明，合同说明，合同说明，合同说明
        </p>
      </div>
      <div class="contract-button-div">
        <el-button type="primary">保存合同</el-button>
        <el-button type="primary">打印合同</el-button>
        <el-button type="warning">清空内容</el-button>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      contractContent:
        "这是测试数据这是测试数据这是测试数据这是测试数据这是测试数据这是测试数据这是测试数据这是测试数据，这是测试数据这是测试数据这是测试数据这是测试数据这是测试数据这是测试数据这是测试数据这是测试数据，这是测试数据这是测试数据这是测试数据这是测试数据这是测试数据这是测试数据这是测试数据这是测试数据，这是测试数据这是测试数据这是测试数据这是测试数据这是测试数据这是测试数据这是测试数据这是测试数据，这是测试数据这是测试数据这是测试数据这是测试数据这是测试数据这是测试数据这是测试数据这是测试数据，",
      contractRemarks: [
        {
          remark:
            "1.若无异议，这是测试数据这是测试数据这是测试数据这是测试数据这是测试数据这是测试数据这是测试数据这是测试数据，这是测试数据这是测试数据这是测试数据这是测试数据这是测试数这是测试数据这是测试数据这是测试数据这是测试数据这是测试数据这是测试数据这是测试数据这是测试数据，这是测试数据这是测试数据这是测试数据这是测试数据这是测试数据这是测试数据这是测试数据这是测试数据",
        },
        { remark: "2.若无异议，则2222222222" },
        { remark: "3.若无异议，则2222222222" },
        { remark: "4.若无异议，则2222222222" },
        { remark: "5.若无异议，则2222222222" },
      ],
      vendeeInfo: {
        name: "桑尼泰克精密零部件有限公司",
        location: "昆山市周市镇新镇路681号",
        contact: "潘先生",
        phone: "0512-57861208",
        taxNo: "9S78945612368U",
        account: "62001102368944789",
      },
      goodReceveInfo: {
        name: "桑尼泰克精密零部件有限公司",
        location: "昆山市周市镇新镇路681号",
        contact: "潘先生",
        phone: "0512-57861208",
        taxNo: "9S78945612368U",
        account: "62001102368944789",
      },
      contractInfo: {
        contractNo: "1610670360",
        contractDate: "2020-04-06",
        diliveryWay: "60-1",
        orderTicketNo: "2021041405",
        orderDes: "暂不开票",
        diliveryType: "FCA CHINA",
        payDate: "月结",
        payWay: "",
        currency: "RMB",
        diliveryDate: "订单下达后20天",
        contact: " 张XX T：241699788",
        quality: "",
      },
      companyInfo: {
        name: "测试公司数据",
        location: "苏州市吴中区鹿山路001号",
        bank: "中国银行高新区狮山路分行",
        account: "pp0956743",
        taxNo: "91384792U874K9",
      },
    };
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
.text-left-center {
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
}
.contract-template-content {
  text-align: left;
}
.contract-template-remark-div {
  display: flex;
  flex-direction: row;
}
.contract-remark-p {
  text-align: left;
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
</style>