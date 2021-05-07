<template>
  <div class="order-release-main-container">
    <div class="order-release-table-div">
      <el-table
        :data="orderReadyToReleaseList"
        border
        stripe
        size="small"
        style="width: 100%"
      >
        <el-table-column prop="formNo" label="订单号"> </el-table-column>
        <el-table-column prop="customerName" label="客户名称">
        </el-table-column>
        <el-table-column prop="createDate" label="日期"> </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button
              type="success"
              size="mini"
              @click="orderDecideOnclick(scope.row)"
              >决策</el-button
            >
          </template>
        </el-table-column>
      </el-table>
    </div>
    <el-drawer
      :visible.sync="tableShow"
      direction="rtl"
      :with-header="false"
      size="800px"
    >
      <div class="order-decide-detail-div" v-show="tableShow">
        <div class="order-selected-header">
          <span
            >{{ selectedOrderForm.customerName }}({{
              selectedOrderForm.formNo
            }})</span
          >
          <span>合同号：{{ selectedOrderForm.contractNo }}</span>
        </div>
        <div
          class="order-detail-component"
          v-for="product in orderFormDetaiList"
          :key="product.id"
        >
          <div class="order-product-info-bar">
            <span class="coloryellow">{{ product.productName }}</span>
            <span>{{ product.count }}{{ product.countUnit }}</span>
            <span>{{ product.specification }}</span>
            <span>{{ product.remark }}</span>
            <i class="el-icon-check color-dark-green fz12"></i>
          </div>
          <div class="order-product-checklist">
            <span>类型：</span>
            <el-radio-group v-model="product.produceType" size="mini">
              <el-radio-button label="P" @click.native="choicePOnclick(product)"
                >生产</el-radio-button
              >
              <el-radio-button label="C">采购</el-radio-button>
              <el-radio-button label="D" @click.native="choicePOnclick(product)"
                >代工</el-radio-button
              >
              <el-radio-button label="W" @click.native="choicePOnclick(product)"
                >委外</el-radio-button
              >
            </el-radio-group>
          </div>
          <div
            class="order-product-detail-bom-analysis-div"
            v-show="product.produceType !== 'C'"
          >
            <div class="product-bom-choice-div">
              <span>BOM 选择</span>
              <el-select
                size="mini"
                placeholder="选择BOM"
                v-model="product.bomNo"
                @change="productBomChanged(product)"
              >
                <el-option
                  v-for="pbom in product.pboms"
                  :key="pbom.id"
                  :label="pbom.productName + '/' + pbom.description"
                  :value="pbom.bomNo"
                ></el-option>
              </el-select>
              <!-- <el-radio-group v-model="product.bomChoice">
                <el-radio :label="1">优先半成品</el-radio>
                <el-radio :label="2">使用底层物料</el-radio>
              </el-radio-group> -->
              <!-- <el-checkbox>自动生成采购单</el-checkbox> -->
            </div>
            <el-tree
              class="product-bom-tree"
              :data="product.boms"
              :props="defaultProps"
              @node-expand="nodeExpand"
              @node-collapse="nodeCollapse"
            >
              <!--自定义tree node -->
              <div class="tree-node-bom-div" slot-scope="{ node, data }">
                <div>
                  <span>{{ node.label }}</span>
                  <span> x {{ data.chQty }} </span>
                </div>
                <div style="width: 200px">
                  <!-- <span style="width:50px;text-align:left;">库存：</span>
                <span style="width:50px;text-align:left;"> {{ data.storage }} </span> -->
                  <span style="width: 30px; text-align: left" v-if="data.seen"
                    >总数：</span
                  >
                  <span
                    style="width: 30px; text-align: left"
                    v-if="data.seen"
                    >{{ $my.NumberMul(data.chQty, product.count) }}</span
                  >
                </div>
              </div>
            </el-tree>
          </div>
          <div v-show="product.produceType == 'C'">
            <span class="colorred"
              >对外采购{{ product.count }}{{ product.countUnit }}</span
            >
          </div>
        </div>
        <el-button class="order-confirm-button" type="primary" @click="orderConfirm">订单下达</el-button>
      </div>
    </el-drawer>
  </div>
</template>
<script>
import { mapGetters } from "vuex";
import { mapActions } from "vuex";
export default {
  data() {
    return {
      tableShow: false,
      selectedOrderForm: {},
      defaultProps: {
        children: "childList",
        label: "name",
      },
    };
  },
  computed: {
    ...mapGetters(["orderReadyToReleaseList", "orderFormDetaiList"]),
  },
  methods: {
    ...mapActions({
      GetCreatedOrderList: "GetCreatedOrderList",
      GetOrderFormDetailList: "GetOrderFormDetailList",
      GetProductBomList: "GetProductBomList",
      GetProductBomInfo: "GetProductBomInfo",
      OrderFormConfirm:'OrderFormConfirm'
    }),
    orderDecideOnclick(form) {
      this.GetOrderFormDetailList({ orderFormId: form.id });
      this.selectedOrderForm = form;
      this.tableShow = !this.tableShow;
    },
    orderConfirm() {
      console.log('订单下达',this.selectedOrderForm,this.orderFormDetaiList)
      this.OrderFormConfirm({orderForm:this.selectedOrderForm,orderFormDetails:this.orderFormDetaiList})
    },
    choicePOnclick(product) {
      this.GetProductBomList({ productId: product.productId }).then((res) => {
        if (res.resultStatus == 1) {
          product.pboms = res.data;
        }
      });
    },
    productBomChanged(p) {
      this.GetProductBomInfo({ bomNo: p.bomNo }).then((res) => {
        if (res.resultStatus == 1) {
          res.data.map((item) => {
            item.seen = true;
          });
          p.boms = res.data;
        }
      });
    },
    nodeExpand(item) {
      item.seen = false;
      if (item.childList) {
        item.childList.map((child) => {
          child.seen = true;
        });
      }
    },
    nodeCollapse(item) {
      item.seen = true;
    },
  },
  beforeMount() {
    this.GetCreatedOrderList();
  },
};
</script>
<style>
.margin-left-20 {
  margin-left: 30px;
}
.order-release-main-container {
  display: flex;
  flex-direction: row;
}
.order-release-table-div {
  width: 100%;
  padding: 5px;
}
.order-decide-detail-div {
  display: flex;
  flex-direction: column;
  width: 100%;
  font-size: 0.9rem;
  /* border: 1px solid lightgray;
  border-radius: 5px;
  margin: 5px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04); */
}
.order-product-info-bar {
  display: flex;
  flex-direction: row;
  justify-items: center;
  justify-content: space-between;
  padding: 0 10px;
}
.order-detail-component {
  font-size: 0.8rem;
  padding: 10px 0;
  border-bottom: 1px solid lightgray;
  /* box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04); */
}
.order-selected-header {
  height: 60px;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
  padding: 0 10px;
  border-bottom: 1px solid lightgray;
}
.order-product-checklist {
  display: flex;
  flex-direction: row;
  padding: 10px;
  align-items: center;
}
.tree-node-bom-div {
  width: 100%;
  font-size: 0.8rem;
  display: flex;
  align-items: center;
  flex-direction: row;
  justify-content: space-between;
}
.product-bom-tree {
  margin-top: 10px;
}
.width-100-text-left {
  width: 50px;
  text-align: left;
  background: red;
}
.order-product-detail-bom-analysis-div {
  text-align: left;
  padding: 10px;
}
.product-bom-choice-div {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.order-confirm-button{
  height: 40px;
  margin: 10px;
}
</style>