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
        <el-table-column prop="orderTicketNo" label="订单号" width="100">
        </el-table-column>
        <el-table-column prop="customerName" label="客户名称" width="100">
        </el-table-column>
        <el-table-column prop="createDate" label="日期"> </el-table-column>
        <el-table-column label="操作">
          <template>
            <el-button type="primary" size="mini">决策</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="order-decide-detail-div">
      <div class="order-selected-header">
        <span>测试客户01(00001)</span>
        <span>合同号：1234567890</span>
        <el-button type="primary">订单下达</el-button>
      </div>
      <div class="order-detail-component">
        <div class="order-product-info-bar">
          <span>华为手机</span>
          <span>100只</span>
          <span>P40</span>
          <span>带原子贴膜</span>
          <i class="el-icon-check color-dark-green fz12"></i>
        </div>
        <div class="order-product-checklist">
          <el-checkbox label="生产"></el-checkbox>
          <el-input-number
            size="mini"
            controls-position="right"
            value="40"
          ></el-input-number>
          <el-checkbox label="采购" class="margin-left-20"></el-checkbox>
          <el-input-number
            size="mini"
            controls-position="right"
            value="60"
          ></el-input-number>
          <el-checkbox label="代工" class="margin-left-20"></el-checkbox>
        </div>
        <div class="order-product-detail-bom-analysis-div">
          <div>
            BOM
            <el-radio-group v-model="radio">
              <el-radio :label="1">优先半成品</el-radio>
              <el-radio :label="2">使用底层物料</el-radio>
            </el-radio-group>
             <el-checkbox >自动生成采购单</el-checkbox>
          </div>
          <el-tree :data="bomList" :props="defaultProps">
            <!--自定义tree node -->
            <div class="tree-node-bom-div" slot-scope="{ node, data }">
              <span class="ml40">{{ node.label }}</span>
              <span class="ml40">
                {{ data.name }}
              </span>
              <span class="ml40 color-yellow">
                {{ data.number }}
              </span>
              <span class="ml40">库存：</span>
              <span class="colorblue"> {{ data.storage }} </span>
            </div>
          </el-tree>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
        radio:1,
      orderReadyToReleaseList: [
        {
          orderTicketNo: "0001",
          customerName: "测试客户01",
          createDate: "2021-04-16",
        },
        {
          orderTicketNo: "0002",
          customerName: "测试客户02",
          createDate: "2021-04-15",
        },
        {
          orderTicketNo: "0003",
          customerName: "测试客户03",
          createDate: "2021-04-17",
        },
      ],
      orderDetailList: [
        {
          productName: "华为手机",
          remark: "不要贴膜",
          number: "100",
          spec: "P30",
          bom: [{ code: "", name: "", number: "" }],
        },
      ],
      bomList: [
        {
          code: "01",
          name: "CPU",
          number: "2",
          storage: "10",
        },
        {
          code: "02",
          name: "OLED显示屏",
          number: "1",
          storage: "11",
          children: [
            {
              code: "03",
              name: "显示屏原料1",
              number: "3",
              storage: "2",
              children: [
                {
                  code: "04",
                  name: "显示屏原料1子料",
                  number: "1",
                  storage: "55",
                },
              ],
            },
            {
              code: "06",
              name: "显示屏原料2",
              number: "3",
              storage: "11",
            },
          ],
        },
        {
          code: "09",
          name: "电池",
          number: "1",
          storage: "11",
        },
      ],
      defaultProps: {
        children: "children",
        label: "code",
      },
    };
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
  width: 400px;
}
.order-decide-detail-div {
  display: flex;
  flex-direction: column;
  width: 100%;
}
.order-product-info-bar {
  display: flex;
  flex-direction: row;
  justify-items: center;
  justify-content: space-between;
  padding: 0 10px;
}
.order-detail-component {
  margin: 10px;
  padding: 10px;
  border: 1px solid lightgray;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
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
  font-size: 0.8rem;
}
</style>