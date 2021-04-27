<template>
  <div>
    <div id="outTicketPdf" ref="outTicketPdf" class="constract-div">
      <div class="constract-header">
        <span class="constract-header-title">销售合同确认书</span>
      </div>
      <div class="constract-buyer-info-div">
        <div class="constract-buyer-info-div-left">
          <div class="constract-buyer-info-div-left-firstfloor">
            <div class="title-div-gray flex justify-content">
              <span>买方({{vendeeInfo.customerNo}})</span>
              <el-select
                filterable
                v-model="selectedCustomer"
                size="mini"
                placeholder="选择买方"
                @change="vendeeInfoChanged"
              >
                <el-option
                  v-for="customer in customerList"
                  :label="customer.name"
                  :value="customer"
                  :key="customer.customerNo"
                ></el-option>
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
                v-model="vendeeInfo.address"
                size="mini"
              ></el-input>
            </div>
            <div class="cell-div">
              <span class="cell-div-title">联系：</span>
              <el-input
                class="text-left-center"
                v-model="vendeeInfo.contactUser"
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
              <span>收货单位(0000001)</span>
              <el-select id="es2" filterable size="mini" placeholder="选择收货">
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
                v-model="goodReceveInfo.address"
                size="mini"
              ></el-input>
            </div>
            <div class="cell-div">
              <span class="cell-div-title">联系：</span>
              <el-input
                class="text-left-center"
                v-model="goodReceveInfo.contactUser"
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
          @cell-click="cellClick"
          :header-cell-style="{
            background: 'lightgray',
            color: 'black',
            weight: 'bold',
          }"
        >
          <el-table-column prop="productName" label="名称" width="210px">
            <template slot-scope="scope">
              <div
                v-if="scope.row.seen"
                class="contract-product-detail-select-div"
              >
                <el-select
                  v-model="scope.row.productName"
                  size="mini"
                  filterable
                  remote
                  reserve-keyword
                  placeholder="请输入名称"
                  :remote-method="remoteMethod"
                  :loading="loading"
                  @change="onSelect($event, scope.row)"
                >
                  <el-option-group
                    v-for="group in options"
                    :key="group.groupName"
                    :label="group.groupName"
                  >
                    <el-option
                      v-for="item in group.list"
                      :key="item.id"
                      :label="item.value"
                      :value="item"
                    >
                    </el-option>
                  </el-option-group>
                </el-select>
                <i
                  class="el-icon-delete fz12"
                  @click="removeContractProduct(scope.$index)"
                ></i>
              </div>
              <span v-else>{{ scope.row.productName }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="specification" label="规格" width="180px">
          </el-table-column>
          <el-table-column prop="remark" label="备注" width="200px">
            <template slot-scope="scope">
              <el-input
                v-if="scope.row.seen"
                size="mini"
                v-model="scope.row.remark"
                @blur="loseFcous(scope.$index, scope.row)"
              ></el-input>
              <span v-else>{{ scope.row.remark }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="count" label="数量" width="130px">
            <template slot-scope="scope">
              <el-input
                v-if="scope.row.seen"
                size="mini"
                v-model="scope.row.count"
                @blur="loseFcous(scope.$index, scope.row)"
              ></el-input>
              <span v-else>{{ scope.row.count }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="taxPrice" label="含税单价" width="130px">
            <template slot-scope="scope">
              <el-input
                v-if="scope.row.seen"
                size="mini"
                v-model="scope.row.taxPrice"
                @blur="loseFcous(scope.$index, scope.row)"
              ></el-input>
              <span v-else>{{ scope.row.taxPrice }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="taxTotalPrice" label="含税总价">
            <!-- <template slot-scope="scope">
                <span >{{$my.NumberMul(scope.row.count,scope.row.taxPrice)}}</span>
              </template> -->
          </el-table-column>
        </el-table>
        <el-button
          id="tbb"
          icon="el-icon-plus"
          plain
          class="add-contract-deail"
          @click="addContractDetail"
          >添加详细</el-button
        >
        <div class="constract-product-detail-summary-div fz9">
          <div class="constract-product-detail-summary-item">
            <span style="width: 180px; text-align: left">总额(含税)</span>
            <span style="width: 150px; text-align: right">{{
              contractProductTotal.price
            }}</span>
          </div>
          <div class="constract-product-detail-summary-item">
            <div style="width: 180px; text-align: left">销项税</div>
            <div style="width: 130px; margin-left: 510px">
              <el-select
                v-model="contractInfo.taxRate"
                placeholder="税率"
                size="mini"
              >
                <el-option
                  v-for="item in taxRateList"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
            </div>

            <span style="width: 150px; text-align: right">{{
              contractProductTotal.tax
            }}</span>
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
          <span>{{ companyInfo.address }}</span>
          <span>开户行：{{ companyInfo.bank }}</span>
          <span>账号：{{ companyInfo.account }}</span>
          <span>税号：{{ companyInfo.taxNo }}</span>
        </div>
        <div class="contract-confirm-box">
          <span>买方确认</span>
          <span>{{ vendeeInfo.name }}</span>
          <span>{{ vendeeInfo.address }}</span>
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
    <div class="edit-contract-button-div">
      <el-button @click="saveContract" type="primary">保存合同</el-button>
      <el-button @click="printPdf" type="primary">打印合同</el-button>
      <el-button v-if="!edit" type="warning">清空内容</el-button>
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
      options: [],
      selectedCustomer: {},
      loading: false,
      vendeeInfo: {
        name: "桑尼泰克精密零部件有限公司",
        address: "昆山市周市镇新镇路681号",
        customerNo: "",
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
        quality: "",
        taxRate: "",
      },
      companyInfo: {
        name: "测试公司数据",
        address: "苏州市吴中区鹿山路001号",
        bank: "中国银行高新区狮山路分行",
        account: "pp0956743",
        taxNo: "91384792U874K9",
      },
      contractProductList: [],
      productList: [
        {
          groupName: "常用产品",
          list: [
            {
              id: 1,
              productName: "圆珠笔",
              specification: "0.38mm*红",
              value: "圆珠笔/0.38mm*红",
              remark: "小包装",
              taxPrice: "2.7",
            },
            {
              id: 2,
              productName: "网线",
              specification: "3m*蓝色",
              value: "网线/3m*蓝色",
              remark: "10根一捆",
              taxPrice: "3.1",
            },
            {
              id: 3,
              productName: "圆珠笔",
              specification: "0.38mm*黑",
              value: "圆珠笔/0.38mm*黑",
              remark: "大包装",
              taxPrice: "2.65",
            },
          ],
        },
        {
          groupName: "产品列表",
          list: [
            {
              id: 4,
              productName: "公司产品笔04",
              specification: "规格04",
              value: "公司产品笔04/规格04",
              taxPrice: "",
              remark: "",
            },
            {
              id: 5,
              productName: "公司产品笔05",
              specification: "规格05",
              value: "公司产品笔05/规格05",
              taxPrice: "",
              remark: "",
            },
            {
              id: 6,
              productName: "公司产品网线06",
              specification: "规格06",
              value: "公司产品网线06/规格06",
              taxPrice: "",
              remark: "",
            },
          ],
        },
      ],
      taxRateList: [
        { label: "3%", value: "3" },
        { label: "6%", value: "6" },
        { label: "13%", value: "13" },
      ],
    };
  },
  computed: {
    ...mapGetters(["contractTemplate", "customerList"]),
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
  methods: {
    ...mapActions({
      GetContractTemplate: "GetContractTemplate",
      SaveIntentionContract: "SaveIntentionContract",
      GetCustomerList: "GetCustomerList",
    }),
    addContractDetail() {
      if (
        this.contractProductList.filter((item) => {
          return item.id < 0;
        }).length > 0
      ) {
        this.$message.warning("请先完成填写");
        return;
      }
      this.contractProductList.push({
        id: -1,
        productName: "",
        specification: "",
        remark: "",
        count: "0",
        taxPrice: "",
        taxTotalPrice: "",
        seen: true,
      });
      this.selectPid = -1;
    },
    remoteMethod(query) {
      if (query !== "") {
        this.loading = true;
        setTimeout(() => {
          this.loading = false;
          var options = [];
          this.productList.map((group) => {
            var subList = group.list.filter((item) => {
              return item.value.toLowerCase().indexOf(query.toLowerCase()) > -1;
            });
            if (subList.length > 0) {
              options.push({ groupName: group.groupName, list: subList });
            }
          });
          this.options = options;
        }, 200);
      } else {
        this.options = [];
      }
    },
    onSelect(item, row) {
      row.id = item.id;
      row.productName = item.productName;
      row.specification = item.specification;
      row.seen = false;
      row.remark = item.remark;
      row.taxPrice = item.taxPrice;
    },
    cellClick(row) {
      row.seen = true;
      this.selectPid = row.id;
    },
    loseFcous(index, row) {
      row.seen = false;
    },
    removeContractProduct(index) {
      this.contractProductList.splice(index);
    },
    printPdf() {
      print({
        printable: "outTicketPdf",
        type: "html",
        maxWidth: "100%",
        targetStyles: ["*"],
      });
    },
    vendeeInfoChanged(item) {
      this.vendeeInfo.name = item.name;
      this.vendeeInfo.address = item.address;
      this.vendeeInfo.customerNo = item.customerNo;
      this.vendeeInfo.contactUser = item.contactName;
      this.vendeeInfo.taxNo = item.taxNo;
      this.account = item.bankNo;
      this.contractInfo.contractDate = new Date().format("yyyy-MM-dd");
      this.contractInfo.diliveryWay = item.diliveryMethod;
      this.contractInfo.type = "1";
      this.contractInfo.orderDes = item.description;
      this.contractInfo.payDate = item.payTerm;
      this.contractInfo.payWay = item.payWay;
      this.contractInfo.currencyitem.currency;
      this.contractInfo.diliveryDate - item.deliverTerm;
    },
    saveContract() {
      var saveObj = {
        contract: this.contractInfo,
        purchaser: this.vendeeInfo,
        goodReceiveInfo: this.goodReceveInfo,
        productList: this.contractProductList,
        vendorConfirm: this.companyInfo,
        purchaserConfirm: this.vendeeInfo,
      };
      this.SaveIntentionContract(saveObj);
    },
  },
  beforeMount() {
    this.GetContractTemplate();
    this.GetCustomerList();
  },
  props: ["edit"],
};
</script>
<style lang="less">
.constract-div {
  width: 1000px;
  padding: 1rem;
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
  justify-content: space-between;
  padding: 3px 10px;
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
.edit-contract-button-div {
  padding: 1rem 0;
  text-align: right;
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
.contract-product-detail-select-div {
  display: flex;
  align-items: center;
  justify-content: space-between;
}
</style>