<template>
  <el-form
    :model="releaseInfo.delegateTicket"
    label-width="60px"
    label-position="left"
    size="small"
  >
    <div class="delegate-ticket-detail-form-row-div">
      <el-form-item label="甲方：">
        <span>{{ company.companyName }}</span>
      </el-form-item>
    </div>
    <div class="delegate-ticket-detail-form-row-div">
      <el-form-item prop="supplyId" label="乙方">
        <span>{{ releaseInfo.delegateTicket.supplyName }}</span>
      </el-form-item>
      <!-- <el-form-item prop="taxRate" label="税率">
        <span>{{ releaseInfo.delegateTicket.taxRate }}%</span>
      </el-form-item> -->
      <el-form-item label="">
        <div>
          <span class="mr40">{{
            convertDelegateType(releaseInfo.delegateTicket.type)
          }}</span
          ><span class="ml40">{{
            getFullTime(releaseInfo.delegateTicket.delegateDate)
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
      </el-table-column>
      <el-table-column prop="delegaetPriceTotal" label="总价" width="180px">
      </el-table-column>
    </el-table>
    <div class="delegate-release-price-summary-div">
      <span>税率：{{ releaseInfo.delegateTicket.taxRate }}%</span>
      <span class="ml40">税额：</span>{{ delegateSummary.tax }}
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
  </el-form>
</template>
<script>
import { mapGetters } from "vuex";
import { mapActions } from "vuex";
export default {
  data() {
    return {
      releaseInfo: {
        delegateTicket: {},
        delegateDetailList: [],
        pickingTicketDetailList: [],
      },
    };
  },

  methods: {
    ...mapActions({
      GetCompanyInfo: "GetCompanyInfo",
      FindDelegaetReleaseInfo: "FindDelegaetReleaseInfo",
    }),
    getFullTime(time) {
      if (time) {
        return new Date(time).format("yyyy-MM-dd hh:mm:ss");
      } else {
        return "";
      }
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
    loadDelegateInfo(ticketId) {
      this.FindDelegaetReleaseInfo({ ticketId: ticketId })
        .then((res) => {
          this.dialogLoading = false;
          if (res.resultStatus == 1) {
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
  },
  computed: {
    ...mapGetters(["company"]),
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
};
</script>
<style>
.delegate-ticket-detail-form-row-div {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>