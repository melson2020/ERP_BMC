<template>
  <div class="order-delivery-mian-div">
    <div class="order-delivery-search-div">
      <el-input
        size="small"
        placeholder="搜索订单"
        class="order-delivery-search-input"
        v-model="searchContent"
        suffix-icon="el-icon-search"
      ></el-input>
    </div>
    <el-table
      :data="deliveryOrderList"
      border
      stripe
      class="mt40"
      size="small"
      :header-row-style="{ height: '40px' }"
      :row-style="{ height: '40px' }"
      :cell-style="{ padding: '2px', color: '#909399' }"
      :header-cell-style="{ background: '#909399', color: 'white' }"
    >
      <el-table-column prop="formNo" label="订单号"> </el-table-column>
      <el-table-column prop="contractNo" label="合同号"> </el-table-column>
      <el-table-column prop="customerName" label="客户名称"> </el-table-column>
      <el-table-column prop="createDate" label="创建日期">
        <template slot-scope="scope">
          <span>{{ getFullTime(scope.row.createDate) }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="state" label="状态" width="120px">
      </el-table-column>
      <el-table-column label="操作" width="120px">
        <template slot-scope="scope">
          <el-button
            type="warning"
            size="mini"
            plain
            icon="el-icon-folder"
            circle
            @click="getOrderDelivery(scope.row.formNo)"
          ></el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog title="发货确认" :visible.sync="deliveryDialogVisible" width="1100px">
      <div id="deliveryPrintTable">
      <span class="fw600 mb40">发货单</span>
      <el-row class="mt40">
        <el-col :span="8">
          <span class="delivery-form-title">发货单号：</span>
          <div class="delivery-form-content">{{ orderDelivery.ticketNo }}</div>
        </el-col>
        <el-col :span="8">
          <span class="delivery-form-title">合同单号：</span>
          <div class="delivery-form-content">
            {{ orderDelivery.contractNo }}
          </div>
        </el-col>
        <el-col :span="8">
          <span class="delivery-form-title">客户名称：</span>
          <div class="delivery-form-content">
            {{ orderDelivery.customerName }}
          </div>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <span class="delivery-form-title">联系人员：</span>
          <div class="delivery-form-content">
            {{ orderDelivery.contactName }}
          </div>
        </el-col>
        <el-col :span="8">
          <span class="delivery-form-title">联系电话：</span>
          <div class="delivery-form-content">{{ orderDelivery.phone }}</div>
        </el-col>
      </el-row>
      <div class="flex">
        <span class="delivery-form-title">送货地址：</span>
        <div class="delivery-form-content">{{ orderDelivery.address }}</div>
      </div>
      <div class="flex mt40">
        <span class="delivery-form-title">送货方式：</span>
        <el-input
          class="ws100"
          size="mini"
          v-model="orderDelivery.deliveryWay"
          maxlength="80"
        ></el-input>
      </div>
      <div class="flex mt40">
        <span class="delivery-form-title">送货备注：</span>
        <el-input
          size="mini"
           class="ws100"
          v-model="orderDelivery.remark"
          maxlength="80"
        ></el-input>
      </div>
      <el-table
        :data="orderDelivery.deliveryDetails"
        border
        class="mt40"
        size="mini"
      >
        <el-table-column prop="name" label="名称"> </el-table-column>
        <el-table-column prop="specification" label="规格"> </el-table-column>
        <el-table-column prop="createDate" label="数量">
          <template slot-scope="scope">
            <span>{{ scope.row.count }}{{ scope.row.unit }}</span>
          </template>
        </el-table-column>
      </el-table>
      </div>
      <div class="delivery-confirm-button-div">
         <el-button type="primary" size="small" @click="confirmOnClick">确认发货</el-button>
          <el-button type="primary" size="small" @click="printOnClick">打印</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import { mapGetters } from "vuex";
import { mapActions } from "vuex";
import print from "print-js";
export default {
  data() {
    return {
      searchContent: "",
      orderDelivery: {},
      deliveryDialogVisible: false,
    };
  },
  computed: {
    ...mapGetters(["deliveryOrderList"]),
  },
  methods: {
    ...mapActions({
      GetOrderFormDeliveryList: "GetOrderFormDeliveryList",
      GetOrderDelviery: "GetOrderDelviery",
      OrderDeliveryConfirm:'OrderDeliveryConfirm'
    }),
    getFullTime(time) {
      return new Date(time).format("yyyy-MM-dd hh:mm:ss");
    },
    getOrderDelivery(formNo) {
      this.GetOrderDelviery({ formNo: formNo })
        .then((res) => {
          if (res.resultStatus == 1) {
            this.orderDelivery = res.data;
            this.deliveryDialogVisible = true;
          } else {
            this.$message.warning(res.message);
          }
        })
        .catch((err) => {
          this.$message.error(err.message);
        });
    },
     printOnClick() {
      print({
        printable: "deliveryPrintTable",
        type: "html",
        maxWidth: "100%",
        targetStyles: ["*"],
      });
    },
    confirmOnClick(){
       this.OrderDeliveryConfirm(this.orderDelivery) .then((res) => {
          if (res.resultStatus == 1) {
            this.GetOrderFormDeliveryList();
            this.deliveryDialogVisible = false;
          } else {
            this.$message.warning(res.message);
          }
        })
        .catch((err) => {
          this.$message.error(err.message);
        });
    }
  },
  beforeMount() {
    this.GetOrderFormDeliveryList();
  },
};
</script>
<style>
.order-delivery-search-input {
  width: 400px;
  text-align: right;
}
.order-delivery-search-div {
  display: flex;
  flex-direction: row-reverse;
}
.order-delivery-mian-div {
  padding: 5px 10px;
}
.fw600 {
  font-weight: 600;
}
.delivery-form-title {
  width: 120px;
  text-align: left;
  height: 30px;
  line-height: 30px;
  float: left;
}
.delivery-form-content {
  text-align: left;
  height: 30px;
  line-height: 30px;
}
.delivery-confirm-button-div{
  padding: 10px;
}
</style>