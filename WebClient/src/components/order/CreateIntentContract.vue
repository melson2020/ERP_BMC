<template>
  <div class="create-constract-main-container">
    <div class="constract-div">
      <div class="constract-header">
        <span class="constract-header-title">销售合同确认书</span>
      </div>
      <div class="constract-buyer-info-div">
        <div class="constract-buyer-info-div-left">
          <div class="constract-buyer-info-div-left-firstfloor">
            <div class="title-div-gray">买方:(0000001)</div>
            <span class="text-left-center">{{ vendeeInfo.name }}</span>
            <span class="text-left-center">{{ vendeeInfo.location }}</span>
            <span class="text-left-center">联系：{{ vendeeInfo.contact }}</span>
            <span class="text-left-center">电话：{{ vendeeInfo.phone }}</span>
            <span class="text-left-center">税号：{{ vendeeInfo.taxNo }}</span>
            <span class="text-left-center">账号：{{ vendeeInfo.account }}</span>
          </div>
          <div class="constract-buyer-info-div-left-secfloor">
            <div class="title-div-gray">收货单位:(0000001)</div>
            <span class="text-left-center">{{ goodReceveInfo.name }}</span>
            <span class="text-left-center">{{ goodReceveInfo.location }}</span>
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
        <div class="constract-buyer-info-div-right">
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
          <span class="text-left-center">货币：{{ contractInfo.currency }}</span>
          <span class="text-left-center"
            >交货日期：{{ contractInfo.diliveryDate }}</span
          >
          <span class="text-left-center"
            >联系人：{{ contractInfo.contract }}</span
          >
          <span class="text-left-center"
            >质量保证：{{ contractInfo.quality }}</span
          >
        </div>
      </div>
    </div>
  </div>
</template>
<script>
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
    };
  },
};
</script>
<style>
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
  height: 450px;
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
  float: left;
  font-size: 0.8rem;
  padding: 0.3rem;
  text-align: left;
}
</style>