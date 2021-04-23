<template>
  <div class="create-constract-main-container">
    <div>
      <div class="constract-div">
        <div class="constract-header">
          <span class="constract-header-title">销售合同确认书</span>
        </div>
        <div class="constract-buyer-info-div">
          <div class="constract-buyer-info-div-left">
            <div class="constract-buyer-info-div-left-firstfloor">
              <div class="title-div-gray flex justify-content">
                <span>买方(000001)</span>
                <el-select filterable size="mini" placeholder="选择买方">
                  <el-option label="test1" value="test1"></el-option>
                  <el-option label="test2" value="test2"></el-option>
                </el-select>
              </div>
              <div class="cell-div mt40">
                <span class="cell-div-title">名称：</span>
                <el-input
                  class="text-left-center"
                  v-model="vendeeInfo.name"
                  size="mini"
                ></el-input>
              </div>
              <div class="cell-div">
                <span class="cell-div-title">地址：</span>
                <el-input
                  class="text-left-center"
                  v-model="vendeeInfo.location"
                  size="mini"
                ></el-input>
              </div>
              <div class="cell-div">
                <span class="cell-div-title">联系：</span>
                <el-input
                  class="text-left-center"
                  v-model="vendeeInfo.contact"
                  size="mini"
                ></el-input>
              </div>
              <div class="cell-div">
                <span class="cell-div-title">电话：</span>
                <el-input
                  class="text-left-center"
                  v-model="vendeeInfo.phone"
                  size="mini"
                ></el-input>
              </div>
              <div class="cell-div">
                <span class="cell-div-title">税号：</span>
                <el-input
                  class="text-left-center"
                  v-model="vendeeInfo.taxNo"
                  size="mini"
                ></el-input>
              </div>
              <div class="cell-div">
                <span class="cell-div-title">账号：</span>
                <el-input
                  class="text-left-center"
                  v-model="vendeeInfo.account"
                  size="mini"
                ></el-input>
              </div>
            </div>
            <div class="constract-buyer-info-div-left-secfloor">
              <div class="title-div-gray flex justify-content">
                <span>收货单位:(0000001)</span>
                <el-select filterable size="mini" placeholder="选择收货">
                  <el-option label="test1" value="test1"></el-option>
                  <el-option label="test2" value="test2"></el-option>
                </el-select>
              </div>
              <div class="cell-div mt40">
                <span class="cell-div-title">名称：</span>
                <el-input
                  class="text-left-center"
                  v-model="goodReceveInfo.name"
                  size="mini"
                ></el-input>
              </div>
              <div class="cell-div">
                <span class="cell-div-title">地址：</span>
                <el-input
                  class="text-left-center"
                  v-model="goodReceveInfo.location"
                  size="mini"
                ></el-input>
              </div>
              <div class="cell-div">
                <span class="cell-div-title">联系：</span>
                <el-input
                  class="text-left-center"
                  v-model="goodReceveInfo.contact"
                  size="mini"
                ></el-input>
              </div>
              <div class="cell-div">
                <span class="cell-div-title">电话：</span>
                <el-input
                  class="text-left-center"
                  v-model="goodReceveInfo.phone"
                  size="mini"
                ></el-input>
              </div>
              <div class="cell-div">
                <span class="cell-div-title">税号：</span>
                <el-input
                  class="text-left-center"
                  v-model="goodReceveInfo.taxNo"
                  size="mini"
                ></el-input>
              </div>
              <div class="cell-div">
                <span class="cell-div-title">账号：</span>
                <el-input
                  class="text-left-center"
                  v-model="goodReceveInfo.account"
                  size="mini"
                ></el-input>
              </div>
            </div>
          </div>
          <div class="constract-buyer-info-div-right">
            <div class="title-div-gray">资料</div>
            <div
              class="cell-div"
              v-for="detail in contractTemplate.detailList"
              :key="detail.id"
            >
              <span class="cell-div-title mb40">{{ detail.value }}：</span>
              <el-input
                class="text-left-center"
                v-model="contractInfo[detail.bindingField]"
                size="mini"
              ></el-input>
            </div>
          </div>
        </div>

        <div class="constract-product-detail-div">
          <el-table
            border
            :data="contractProductList"
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
          <el-button icon="el-icon-plus" plain class="add-contract-deail"
            >添加详细</el-button
          >
          <div class="constract-product-detail-summary-div fz9">
            <div class="constract-product-detail-summary-item">
              <span style="width: 180px; text-align: left">总额(含税)</span>
              <span style="width: 130px; margin-left: 380px">1</span>
              <span style="margin-left: 130px; text-align: right">3103.21</span>
            </div>
            <div class="constract-product-detail-summary-item">
              <div style="width: 180px; text-align: left">销项税</div>
              <span style="width: 130px; margin-left: 510px">13%</span>
              <span style="text-align: right">357.00</span>
            </div>
          </div>
        </div>
        <div class="fz8">
          <p class="constract-content">{{ contractTemplate.content.value }}</p>
          <div class="constract-remark-div">
            <div class="remark-title-div">备注</div>
            <div>
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
        <el-button type="primary">保存合同</el-button>
        <el-button type="primary">打印合同</el-button>
        <el-button type="warning">清空内容</el-button>
      </div>
    </div>
  </div>
</template>
<script>
import { mapGetters } from "vuex";
import { mapActions } from "vuex";
export default {
  name: "m-create-intent-constract",
  data() {
    return {
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
      contractProductList:[]
    };
  },
  computed: {
    ...mapGetters(["contractTemplate"]),
  },
  methods: {
    ...mapActions({
      GetContractTemplate: "GetContractTemplate",
    }),
  },
  beforeMount() {
    this.GetContractTemplate();
  },
};
</script>
<style lang="less">
.constract-div {
  width: 1000px;
  padding: 1.2rem;
  border: 1px solid rgb(233, 229, 229);
  display: flex;
  flex-direction: column;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
}
.create-constract-main-container {
  display: flex;
  align-items: flex-start;
  justify-content: center;
}
.constract-header {
  height: 60px;
  display: inline;
  line-height: 60px;
  vertical-align: center;
}
.constract-header-title {
  float: right;
  margin-right: 10px;
  letter-spacing: 7px;
  font-size: 1.5rem;
  color: #606266;
}
.constract-buyer-info-div {
  display: flex;
  flex-direction: row;
  height: 480px;
  border: 1px solid rgb(129, 127, 127);
}
.constract-buyer-info-div-left {
  height: 100%;
  width: 55%;
}
.constract-buyer-info-div-right {
  height: 100%;
  width: 45%;
  border-left: 1px solid rgb(129, 127, 127);
  display: flex;
  flex-direction: column;
}
.constract-buyer-info-div-left-firstfloor {
  height: 50%;
  display: flex;
  flex-direction: column;
}
.constract-buyer-info-div-left-secfloor {
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
  font-size: 0.8rem;
  padding: 0.1rem 0;
  text-align: left;
  width: 300px;
}
.constract-product-detail-div {
  margin-top: 15px;
  border: 1px solid rgb(129, 127, 127);
}
.constract-product-detail-summary-div {
  display: flex;
  flex-direction: column;
}
.constract-product-detail-summary-item {
  display: flex;
  flex-direction: row;
  align-items: center;
}
.constract-content {
  text-align: left;
}
.constract-remark-div {
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
.cell-div {
  display: flex;
  align-items: center;
  font-size: 0.7rem;
  padding: 0 10px;
}
.cell-div-title {
  width: 90px;
  text-align: left;
}
.justify-content {
  justify-content: space-between;
}
.add-contract-deail {
  margin-top: 2px;
  width: 100%;
  border: #dcdfe6 dashed 1px !important;
}
</style>