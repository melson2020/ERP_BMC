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
              <span>买方({{ vendeeInfo.customerNo }})</span>
              <el-select
                filterable
                v-model="vendeeInfo.customerNo"
                size="mini"
                placeholder="选择买方"
                @change="vendeeInfoChanged"
              >
                <el-option
                  v-for="vo in customerVoList"
                  :label="vo.name"
                  :value="vo.customerNo"
                  :key="vo.customerNo"
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
              <el-select
                v-model="selectedAddress"
                @change="diliveryAdrressChanged"
                filterable
                size="mini"
                placeholder="选择收货"
              >
                <el-option
                  v-for="address in diliveryAddressList"
                  :key="address.id"
                  :label="address.deliverAddress"
                  :value="address.id"
                ></el-option>
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
                  :key="item.id"
                  :label="item.rate"
                  :value="item.rate"
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
          <span>名称：{{ vendeeInfo.name }}</span>
          <span>地址：{{ vendeeInfo.address }}</span>
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
      <el-button  type="warning">清空内容</el-button>
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
      selectedAddress: "",
      diliveryAddressList: [],
      loading: false,
      vendeeInfo: {
        name: "",
        address: "",
        customerNo: "",
        contactUser: "",
        phone: "",
        taxNo: "",
        account: "",
      },
      goodReceveInfo: {
        name: "",
        addressId: "",
        address: "",
        contactUser: "",
        phone: "",
        taxNo: "",
        account: "",
      },
      contractInfo: {
        contractNo: "",
        contractDate: "",
        diliveryWay: "",
        type: "",
        orderTicketNo: "",
        orderDes: "",
        deliverType: "",
        payDate: "",
        payWay: "",
        currency: "",
        deliverDate: "",
        contactUser: "",
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
      taxRateList: [],
    };
  },
  computed: {
    ...mapGetters(["contractTemplate", "customerVoList"]),
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
      GetCustomerVoList: "GetCustomerVoList",
      GetContractVoByCustomerNo: "GetContractVoByCustomerNo",
      SearchContractIntentionProductList: "SearchContractIntentionProductList",
      GetContractOne: "GetContractOne",
    }),
    loadContract(contractId){
         const loading = this.$loading({
        lock: true,
        text: "Loading",
        spinner: "el-icon-loading",
        background: "rgba(0, 0, 0, 0.7)",
      });
      this.GetContractOne({ id: contractId })
        .then((res) => {
          if (res.resultStatus == 1) {
             let existContract=res.data
             this.vendeeInfo=existContract.purchaser;
             this.goodReceveInfo=existContract.goodReceiveInfo;
             this.contractProductList=existContract.productList;
             this.contractInfo=existContract.contract;
             this.companyInfo==existContract.vendorConfirm;
            loading.close();
          } else {
            loading.close();
          }
        })
        .catch((e) => {
          loading.close();
        });
    },
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
        productId:-1,
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
      if (this.vendeeInfo.customerNo == "") {
        return;
      }
      if (query !== "") {
        this.loading = true;
        this.SearchContractIntentionProductList({
          searchValue: query,
          customerNo: this.vendeeInfo.customerNo,
        })
          .then((res) => {
            this.loading = false;
            if (res.resultStatus == 1) {
              var productList = res.data;
              var options = [];
              productList.map((group) => {
                var subList = group.list.filter((item) => {
                  return (
                    item.value.toLowerCase().indexOf(query.toLowerCase()) > -1
                  );
                });
                if (subList.length > 0) {
                  options.push({ groupName: group.groupName, list: subList });
                }
              });
              this.options = options;
            }
          })
          .catch(() => {
            this.loading = false;
          });
      } else {
        this.options = [];
      }
    },
    onSelect(item, row) {
      row.id = item.id;
      row.productId=item.id;
      row.productName = item.productName;
      row.specification = item.specification;
      row.seen = false;
      row.remark = item.remark;
      row.taxPrice = item.salesPrice==null?"":item.salesPrice;
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
    vendeeInfoChanged(customerNo) {
      this.GetContractVoByCustomerNo({ customerNo: customerNo })
        .then((res) => {
          if (res.resultStatus == 1) {
            var customer = res.data.customer;
            this.vendeeInfo.name = customer.name;
            this.vendeeInfo.address = customer.address;
            this.vendeeInfo.phone = customer.phone;
            this.vendeeInfo.customerNo = customer.customerNo;
            this.vendeeInfo.contactUser = customer.contactName;
            this.vendeeInfo.taxNo = customer.taxNo;
            this.vendeeInfo.account = customer.bankNo;
            this.contractInfo.contractDate = new Date().format("yyyy-MM-dd");
            this.contractInfo.diliveryWay = customer.diliveryMethod;
            this.contractInfo.type = "1";
            this.contractInfo.orderDes = customer.description;
            this.contractInfo.payDate = customer.payTerm;
            this.contractInfo.payWay = customer.payWay;
            this.contractInfo.currency = customer.currency;
            this.contractInfo.deliverDate = customer.deliverTerm;
            this.taxRateList = res.data.taxRates;
            this.diliveryAddressList = res.data.deliverAddresses;
          } else {
            this.$message.warning(res.message);
          }
        })
        .catch((err) => {
          this.$message.error(err.message);
        });
        if(this.contractInfo.contractNo==""){
          var timeSpan=this.$my.ID2();
          this.contractInfo.contractNo='C'+timeSpan
          this.contractInfo.orderTicketNo='R'+timeSpan
        }
    },
    diliveryAdrressChanged(addressId) {
      var address = this.diliveryAddressList.find((item) => {
        return item.id == addressId;
      });
      this.goodReceveInfo.name = this.vendeeInfo.name;
      this.goodReceveInfo.address = address.deliverAddress;
      this.goodReceveInfo.contactUser = address.contactName;
      this.goodReceveInfo.phone = address.phone;
      this.goodReceveInfo.customerNo = this.vendeeInfo.customerNo;
      this.goodReceveInfo.taxNo = this.vendeeInfo.taxNo;
      this.goodReceveInfo.account = this.vendeeInfo.account;
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
    this.GetCustomerVoList();
  },
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