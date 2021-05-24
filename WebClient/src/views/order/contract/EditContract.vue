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
              <span>买方</span>
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
            <div style="padding: 10px">
              <el-form
                ref="vendeeForm"
                class="contract-form"
                label-position="left"
                label-width="50px"
                size="mini"
                :rules="vendeeRules"
                :model="vendeeInfo"
              >
                <el-form-item label="名称" prop="name">
                  <el-input
                    class="form-input"
                    v-model="vendeeInfo.name"
                  ></el-input>
                </el-form-item>
                <el-form-item label="地址" prop="address">
                  <el-input
                    class="form-input"
                    v-model="vendeeInfo.address"
                  ></el-input>
                </el-form-item>
                <el-form-item label="联系" prop="contactUser">
                  <el-input
                    class="form-input"
                    v-model="vendeeInfo.contactUser"
                  ></el-input>
                </el-form-item>
                <el-form-item label="电话" prop="phone">
                  <el-input
                    class="form-input"
                    v-model="vendeeInfo.phone"
                  ></el-input>
                </el-form-item>
                <el-form-item label="税号" prop="taxNo">
                  <el-input
                    class="form-input"
                    v-model="vendeeInfo.taxNo"
                  ></el-input>
                </el-form-item>
                <el-form-item label="账户" prop="account">
                  <el-input
                    class="form-input"
                    v-model="vendeeInfo.account"
                  ></el-input>
                </el-form-item>
              </el-form>
            </div>
          </div>
          <div class="constract-buyer-info-div-left-secfloor">
            <div class="title-div-gray flex justify-content">
              <span>收货信息</span>
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
            <div style="padding: 10px">
              <el-form
                ref="goodReceveForm"
                class="contract-form"
                label-position="left"
                label-width="50px"
                size="mini"
                :rules="vendeeRules"
                :model="goodReceveInfo"
              >
                <el-form-item label="名称" prop="name">
                  <el-input
                    class="form-input"
                    v-model="goodReceveInfo.name"
                  ></el-input>
                </el-form-item>
                <el-form-item label="地址" prop="address">
                  <el-input
                    class="form-input"
                    v-model="goodReceveInfo.address"
                  ></el-input>
                </el-form-item>
                <el-form-item label="联系" prop="contactUser">
                  <el-input
                    class="form-input"
                    v-model="goodReceveInfo.contactUser"
                  ></el-input>
                </el-form-item>
                <el-form-item label="电话" prop="phone">
                  <el-input
                    class="form-input"
                    v-model="goodReceveInfo.phone"
                  ></el-input>
                </el-form-item>
                <el-form-item label="税号" prop="taxNo">
                  <el-input
                    class="form-input"
                    v-model="goodReceveInfo.taxNo"
                  ></el-input>
                </el-form-item>
                <el-form-item label="账户" prop="account">
                  <el-input
                    class="form-input"
                    v-model="goodReceveInfo.account"
                  ></el-input>
                </el-form-item>
              </el-form>
            </div>
          </div>
        </div>
        <div class="constract-buyer-info-div-right">
          <div class="title-div-gray">资料</div>
          <div style="padding: 10px; height: 100%">
            <el-form
              ref="contractForm"
              class="contract-form"
              label-position="left"
              label-width="80px"
              size="mini"
              :rules="contractInfoRules"
              :model="contractInfo"
            >
              <el-form-item
                :label="detail.value"
                v-for="detail in contractTemplate.detailList"
                :prop="detail.bindingField"
                :key="detail.id"
              >
                <el-input
                  class="form-input"
                  v-model="contractInfo[detail.bindingField]"
                ></el-input>
              </el-form-item>
            </el-form>
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
                      :label="item.alias"
                      :value="item"
                    >
                    </el-option>
                  </el-option-group>
                </el-select>
                <i
                  class="el-icon-delete fz12"
                  @click="removeContractProduct(scope.$index, scope.row)"
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
              <div class="contract-product-seen-div" v-if="scope.row.seen">
                <el-input
                  size="mini"
                  v-model="scope.row.count"
                  @blur="loseFcous(scope.$index, scope.row)"
                ></el-input>
                {{ scope.row.countUnit }}
              </div>
              <span v-else>{{ scope.row.count }}{{ scope.row.countUnit }}</span>
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
          <span>名称：{{ companyInfo.name }}</span>
          <span>地址：{{ companyInfo.address }}</span>
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
      <el-button @click="clearContract" type="warning">清空内容</el-button>
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
      purchaserConfirm:{},
      goodReceveInfo: {
        name: "",
        addressId: "",
        address: "",
        contactUser: "",
        phone: "",
        taxNo: "",
        account: "",
      },
      companyInfo: {
        id:'',
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
      contractProductList: [],
      taxRateList: [],
      vendeeRules: {
        name: [{ required: true, message: "请输入名称", trigger: "blur" }],
        address: [{ required: true, message: "请输入地址", trigger: "blur" }],
        contactUser: [
          { required: true, message: "请输入联系人", trigger: "blur" },
        ],
        phone: [{ required: true, message: "请输入电话", trigger: "blur" }],
        taxNo: [{ required: true, message: "请输入税号", trigger: "blur" }],
        account: [{ required: true, message: "请输入账户", trigger: "blur" }],
      },
      contractInfoRules: {
        contractNo: [
          { required: true, message: "请输入合同号", trigger: "blur" },
        ],
        orderTicketNo: [
          { required: true, message: "请输入订单号", trigger: "blur" },
        ],
        payDate: [
          { required: true, message: "请输入付款时间", trigger: "blur" },
        ],
        payWay: [
          { required: true, message: "请输入付款方式", trigger: "blur" },
        ],
        currency: [{ required: true, message: "请输入币种", trigger: "blur" }],
        deliverDate: [
          { required: true, message: "请输入交货时间", trigger: "blur" },
        ],
      },
    };
  },
  watch: {
    company(c) {
      if (!this.companyInfo.id) {
        this.companyInfo.name = c.companyName;
        this.companyInfo.address = c.address;
        this.companyInfo.bank = c.companyBankName;
        this.companyInfo.account = c.companyBankNo;
        this.companyInfo.taxNo = c.taxNo;
      }
    },
  },
  computed: {
    ...mapGetters(["contractTemplate", "customerVoList", "company"]),
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
      GetCompanyInfo: "GetCompanyInfo",
      RemoveContractStock: "RemoveContractStock",
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
            let existContract = res.data;
            existContract.productList.map((item) => {
              item.seen = false;
            });
            this.vendeeInfo = existContract.purchaser;
            this.goodReceveInfo = existContract.goodReceiveInfo;
            this.contractProductList = existContract.productList;
            this.contractInfo = existContract.contract;
            this.purchaserConfirm=existContract.purchaserConfirm
            this.companyInfo = existContract.vendorConfirm;
            console.log(existContract.vendorConfirm)
             console.log(this.companyInfo)
            loading.close();
          } else {
            loading.close();
          }
        })
        .catch(() => {
          loading.close();
        });
    },
    addContractDetail() {
      if (
        this.contractProductList.filter((item) => {
          return item.productId < 0;
        }).length > 0
      ) {
        this.$message.warning("请先完成填写");
        return;
      }
      this.contractProductList.map((item) => {
        item.seen = false;
      });
      this.contractProductList.push({
        productId: -1,
        productName: "",
        specification: "",
        remark: "",
        count: "0",
        taxPrice: "",
        taxTotalPrice: "",
        seen: true,
      });
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
                    item.alias.toLowerCase().indexOf(query.toLowerCase()) > -1
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
      row.productId = item.id;
      row.productName = item.name;
      row.productNo = item.productNo;
      row.specification = item.specification;
      row.countUnit = item.unit;
      row.seen = false;
      row.remark = item.remark;
      row.taxPrice = item.salesPrice == null ? 0 : item.salesPrice;
    },
    cellClick(row) {
      row.seen = true;
    },
    loseFcous(index, row) {
      row.seen = false;
    },
    removeContractProduct(index, row) {
      if (!row.id) {
        this.contractProductList.splice(index);
      } else {
        this.$messageBox
          .confirm("确认删除？", "删除产品", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning",
          })
          .then(() => {
            this.RemoveContractStock({ id: row.id })
              .then((res) => {
                if (res.resultStatus == 1) {
                  this.contractProductList.splice(index);
                } else {
                  this.$message.warning("删除失败");
                }
              })
              .catch((err) => {
                this.$message.error(err.message);
              });
          })
          .catch((e) => e);
      }
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
      if (this.contractInfo.contractNo == "") {
        var timeSpan = this.$my.ID2();
        this.contractInfo.contractNo = "C" + timeSpan;
        this.contractInfo.orderTicketNo = "R" + timeSpan;
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
      if (this.vendeeInfo.contactUser == "") {
        this.vendeeInfo.contactUser = address.contactName;
      }
      this.goodReceveInfo.customerNo = this.vendeeInfo.customerNo;
      this.goodReceveInfo.taxNo = this.vendeeInfo.taxNo;
      this.goodReceveInfo.account = this.vendeeInfo.account;
    },
    clearContract() {
      this.$refs["vendeeForm"].resetFields();
      this.$refs["goodReceveForm"].resetFields();
      this.$refs["contractForm"].resetFields();
      this.contractProductList = [];
      this.contractInfo.taxRate = "";
    },
    saveContract() {
      this.$refs["vendeeForm"].validate((valid) => {
        if (valid) {
          this.$refs["goodReceveForm"].validate((v2) => {
            if (v2) {
              this.$refs["contractForm"].validate((v3) => {
                if (v3) {
                  var checkRes = this.checkProductList();
                  if (checkRes) {
                    if (this.contractInfo.taxRate != "") {
                      var saveObj = {
                        contract: this.contractInfo,
                        purchaser: this.vendeeInfo,
                        goodReceiveInfo: this.goodReceveInfo,
                        productList: this.contractProductList,
                        vendorConfirm: this.companyInfo,
                        purchaserConfirm: this.purchaserConfirm.id?this.purchaserConfirm:this.vendeeInfo,
                      };
                      this.SaveIntentionContract(saveObj);
                    } else {
                      this.$message.warning("请选择税率");
                    }
                  } else {
                    this.$message.warning("请完善产品信息");
                  }
                } else {
                  this.$message.warning("请填写必要信息");
                }
              });
            } else {
              this.$message.warning("请填写必要信息");
            }
          });
        } else {
          this.$message.warning("请填写必要信息");
        }
      });
    },
    checkProductList: function () {
      if (this.contractProductList.length <= 0) return false;
      var pass = true;
      this.contractProductList.map((item) => {
        if (item.count <= 0 || item.taxPrice <= 0) {
          pass = false;
        }
      });
      return pass;
    },
  },
  beforeMount() {
    this.GetContractTemplate();
    this.GetCustomerVoList();
    this.GetCompanyInfo();
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
  // height: 480px;
  border: 1px solid rgb(209, 205, 205);
}
.constract-buyer-info-div-left {
  height: 100%;
  border-right: 1px solid rgb(209, 205, 205);
  width: 50%;
}
.constract-buyer-info-div-right {
  height: 100%;
  width: 50%;
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

.constract-product-detail-div {
  margin-top: 15px;
  border: 1px solid rgb(209, 205, 205);
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
.justify-content {
  justify-content: space-between;
}
.add-contract-deail {
  margin-top: 2px;
  width: 100%;
  color: #259dff;
  border: #dcdfe6 dashed 1px !important;
}
.contract-product-detail-select-div {
  display: flex;
  align-items: center;
  justify-content: space-between;
}
.contract-product-seen-div {
  display: flex;
  align-items: center;
}
.form-input {
  width: 350px;
  float: left;
}
</style>