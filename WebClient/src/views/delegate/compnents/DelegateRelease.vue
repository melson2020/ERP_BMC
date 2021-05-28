<template>
  <div>
    <div class="delegate-release-title-div">
      <span class="colorblue">委外下达</span>
      <el-select
        v-model="delegateType"
        placeholder="委外来源"
        size="small"
        clearable
      >
        <el-option label="生产委外" value="PLAN"> </el-option>
        <el-option label="订单委外" value="ORDER"> </el-option>
      </el-select>
    </div>
    <el-table :data="delegateReleaseListShow" script border>
      <el-table-column prop="ticketNo" label="单号"> </el-table-column>
      <el-table-column prop="customerName" label="客户名称"> </el-table-column>
      <el-table-column prop="type" label="类型">
        <template slot-scope="scope">
          <span>{{ convertDelegateType(scope.row.type) }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="sourceNo" label="来源单号"> </el-table-column>
      <el-table-column prop="createDate" label="创建时间">
        <template slot-scope="scope">
          <span>{{ getFullTime(scope.row.createDate) }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="state" label="状态">
        <template slot-scope="scope">
          <span>{{ converDelegateState(scope.row.state) }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="" width="100px">
        <template slot-scope="scope">
          <el-button
            icon="el-icon-s-release"
            type="primary"
            size="mini"
            @click="delegateTicketRelease(scope.row.id)"
            circle
          ></el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog title="委外单" :visible.sync="delegateTicketDialog" width="70%">
      <el-form
        ref="delegateForm"
        :model="releaseInfo.delegateTicket"
        label-width="60px"
        label-position="left"
        :rules="rules"
        size="small"
        v-loading="dialogLoading"
      >
        <div class="delegate-release-form-row-div">
          <el-form-item label="甲方：">
            <span>{{ company.companyName }}</span>
          </el-form-item>
        </div>
        <div class="delegate-release-form-row-div">
          <el-form-item prop="supplyId" label="乙方">
            <el-select
              v-model="releaseInfo.delegateTicket.supplyId"
              placeholder="选择供应商"
              size="small"
              @change="supplyChanged"
              clearable
            >
              <el-option
                v-for="supply in supplyList"
                :label="supply.name"
                :value="supply.id"
                :key="supply.id"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item prop="taxRate" label="税率">
            <el-select
              v-model="releaseInfo.delegateTicket.taxRate"
              placeholder="税率"
              size="small"
              clearable
            >
              <el-option
                v-for="tax in taxRateList"
                :label="tax.description"
                :value="tax.rate"
                :key="tax.id"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="">
            <div>
              <span class="mr40">{{
                convertDelegateType(releaseInfo.delegateTicket.type)
              }}</span
              ><span class="ml40">{{
                releaseInfo.delegateTicket.delegateDate.format("yyyy-MM-dd")
              }}</span>
            </div>
          </el-form-item>
        </div>
        <span class="fl mb40"
          >甲方（{{ company.companyName }}）委托乙方（{{
            releaseInfo.delegateTicket.supplyName
          }}）生产或加工以下物品</span
        >
        <el-table
          class="mb40"
          :data="releaseInfo.delegateDetailList"
          script
          @cell-click="cellClick"
          border
          size="mini"
        >
          <el-table-column prop="objectName" label="名称"> </el-table-column>
          <el-table-column prop="specification" label="规格"> </el-table-column>
          <el-table-column prop="count" label="数量">
            <template slot-scope="scope">
              <span>{{ scope.row.count }}{{ scope.row.countUnit }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="delegatePrice" label="含税单价" width="180px">
            <template slot-scope="scope">
              <el-input
                v-if="scope.row.seen"
                v-model="scope.row.delegatePrice"
                size="mini"
                @blur="loseFcous(scope.$index, scope.row)"
              ></el-input>
              <span v-else>{{ scope.row.delegatePrice }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="delegaetPriceTotal" label="总价" width="180px">
          </el-table-column>
        </el-table>
        <div class="delegate-release-price-summary-div">
          <span>税额：</span>{{ delegateSummary.tax }}
          <span class="ml40">总价：</span>{{ delegateSummary.price }}
        </div>
        <span class="fl mb40"
          >甲方（{{ company.companyName }}）提供给乙方（{{
            releaseInfo.delegateTicket.supplyName
          }}）以下物料</span
        >
        <el-table
          class="mb40"
          :data="releaseInfo.pickingTicketDetailList"
          script
          border
          size="mini"
        >
          <el-table-column prop="materialName" label="名称"> </el-table-column>
          <el-table-column prop="specification" label="规格"> </el-table-column>
          <el-table-column prop="count" label="数量">
            <template slot-scope="scope">
              <span>{{ scope.row.count }}{{ scope.row.countUnit }}</span>
            </template>
          </el-table-column>
        </el-table>
        <el-form-item>
          <el-button type="primary" @click="delegateSubmit('delegateForm')"
            >委外下达</el-button
          >
          <el-button>取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>
<script>
import { mapGetters } from "vuex";
import { mapActions } from "vuex";
export default {
  data() {
    return {
      dialogLoading: false,
      delegateType: "",
      supplyId: "",
      delegateTicketDialog: false,
      releaseInfo: {
        delegateTicket: { delegateDate: new Date() },
        delegateDetailList: [],
        pickingTicketDetailList: [],
      },

      rules: {
        supplyId: [
          { required: true, message: "请选择供货商", trigger: "blur" },
        ],
        taxRate: [{ required: true, message: "请选择税率", trigger: "blur" }],
      },
    };
  },
  methods: {
    ...mapActions({
      FindDelegateReleaseList: "FindDelegateReleaseList",
      GetSupplyList: "GetSupplyList",
      GetCompanyInfo: "GetCompanyInfo",
      FindDelegaetReleaseInfo: "FindDelegaetReleaseInfo",
      GetTaxRateList: "GetTaxRateList",
      DelegateReleaseTicketConfirm: "DelegateReleaseTicketConfirm",
      SetDelegateDailogFlag:'SetDelegateDailogFlag'
    }),
    getFullTime(time) {
      return new Date(time).format("yyyy-MM-dd hh:mm:ss");
    },
    convertDelegateType(type) {
      switch (type) {
        case "ORDER":
          return "订单委外";
        case "PLAN":
          return "生产委外";
        default:
          return type;
      }
    },
    converDelegateState(state) {
      switch (state) {
        case "1":
          return "待下达";
        default:
          return state;
      }
    },
    cellClick(row) {
      row.seen = true;
    },
    loseFcous(index, row) {
      row.seen = false;
    },
    supplyChanged(supplyId) {
      let supply = this.supplyList.find((item) => {
        return item.id == supplyId;
      });
      this.releaseInfo.delegateTicket.supplyId = supplyId;
      this.releaseInfo.delegateTicket.supplyName = supply.name;
    },
    delegateTicketRelease(ticketId) {
      this.delegateTicketDialog = !this.delegateTicketDialog;
      this.dialogLoading = true;
      this.FindDelegaetReleaseInfo({ ticketId: ticketId })
        .then((res) => {
          this.dialogLoading = false;
          if (res.resultStatus == 1) {
            res.data.delegateTicket.delegateDate = new Date();
            res.data.delegateDetailList.map((item) => {
              item.seen = false;
            });
            this.releaseInfo = res.data;
          } else {
            this.$message.warning(res.messgae);
          }
        })
        .catch((err) => {
          this.dialogLoading = false;
          this.$message.error(err.messgae);
        });
    },
    delegateSubmit(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          var dataPass = true;
          this.releaseInfo.delegateDetailList.map((item) => {
            if (item.delegatePrice == "" || item.delegatePrice == null) {
              dataPass = false;
            }
          });
          if (dataPass) {
            this.DelegateReleaseTicketConfirm({
              delegateTicket: this.releaseInfo.delegateTicket,
              delegateDetailList: this.releaseInfo.delegateDetailList,
            });
          } else {
            this.$message.warning("请填写委外含税单价");
          }
        } else {
          this.$message.warning("请填写必要信息");
        }
      });
    },
  },
  watch:{
    delegateDailogCloseFalg(){
      if(this.delegateDailogCloseFalg){
          this.delegateTicketDialog = !this.delegateTicketDialog;
          this.SetDelegateDailogFlag()
      }
    }
  },
  computed: {
    ...mapGetters([
      "delegateReleaseList",
      "supplyList",
      "company",
      "taxRateList",
      "delegateDailogCloseFalg"
    ]),
    delegateReleaseListShow() {
      if (this.delegateType == "") {
        return this.delegateReleaseList;
      } else {
        return this.delegateReleaseList.filter((item) => {
          return item.type == this.delegateType;
        });
      }
    },

    delegateSummary() {
      var delegateSummary = { price: 0, tax: 0 };
      var total = 0;
      this.releaseInfo.delegateDetailList.map((item) => {
        item.delegaetPriceTotal = this.$my.NumberMul(
          item.count,
          item.delegatePrice == null ? "0" : item.delegatePrice
        );
        total = this.$my.NumberAdd(total, item.delegaetPriceTotal);
      });
      delegateSummary.price = total.toFixed(2);
      delegateSummary.tax = this.$my.NumberMul(
        this.$my.NumberDiv(
          this.releaseInfo.delegateTicket.taxRate == null
            ? "0"
            : this.releaseInfo.delegateTicket.taxRate,
          100
        ),
        delegateSummary.price
      );
      return delegateSummary;
    },
  },

  mounted() {
    this.FindDelegateReleaseList();
    this.GetSupplyList();
    this.GetCompanyInfo();
    this.GetTaxRateList();
  },
};
</script>
<style>
.delegate-release-title-div {
  display: flex;
  height: 50px;
  align-items: center;
  justify-content: space-between;
}
.delegate-release-form-row-div {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.delegate-release-price-summary-div {
  text-align: right;
  height: 30px;
  line-height: 30px;
  align-items: center;
}
</style>