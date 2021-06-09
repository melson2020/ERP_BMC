<template>
  <div>
    <div id="printTeamplate" ref="printTeamplate" class="printTeamplate-div">
      <div class="printTeamplate-header">
        <span class="printTeamplate-header-left">{{ newPO.poNo }}</span>
        <span class="printTeamplate-header-title">采购订单</span>
      </div>
      <!-- <div class="printTeamplate-buyer-info-div"> -->
      <!-- <div class="printTeamplate-buyer-info-div-left"> -->
      <div class="printTeamplate-buyer-info-div-left-secfloor">
        <div class="title-div-gray flex justify-content">
          卖方：
        </div>
        <div class="printTeamplate-conten">
          <div>
            <el-col :span="12">
              <span class="tempate-text-left-center"
                >卖方名称：{{ newPO.supply.name }}</span
              >
            </el-col>
            <el-col :span="12">
              <span class="tempate-text-left-center"
                >买方地址：{{ newPO.supply.address}}</span
              >
            </el-col>
          </div>
          <div>
            <el-col :span="12">
              <span class="tempate-text-left-center"
                >联系人员：{{ newPO.supply.contact }}</span
              >
            </el-col>
            <el-col :span="12">
              <span class="tempate-text-left-center"
                >联系电话：{{ newPO.supply.phone }}</span
              >
            </el-col>
          </div>
        </div>
      </div>
      <div class="printTeamplate-buyer-info-div-left-secfloor">
        <div class="title-div-gray flex justify-content">
          买方:
        </div>
        <div class="printTeamplate-conten">
          <div>
            <el-col :span="12">
              <span class="tempate-text-left-center"
                >买方名称：{{ userInfo.company.companyName }}</span
              >
            </el-col>
            <el-col :span="12">
              <span class="tempate-text-left-center"
                >买方地址：{{ userInfo.company.provinceName
                }}{{ userInfo.company.cityName }}{{ userInfo.company.areaName
                }}{{ userInfo.company.address }}</span
              >
            </el-col>
          </div>
          <div>
            <el-col :span="12">
              <span class="tempate-text-left-center"
                >联系人员：{{ userInfo.company.communicateName }}</span
              >
            </el-col>
            <el-col :span="12">
              <span class="tempate-text-left-center"
                >联系电话：{{ userInfo.company.phoneNumber }}</span
              >
            </el-col>
          </div>
        </div>
      </div>

      <div class="printTeamplate-product-detail-div">
        <el-table
          border
          :data="poDetailList"
          style="width: 100%"
          size="mini"
          :header-cell-style="{
            background: 'lightgray',
            color: 'black',
            weight: 'bold',
          }"
        >
          <el-table-column prop="materialName" label="名称"> </el-table-column>
          <el-table-column prop="specification" label="规格"> </el-table-column>
          <el-table-column prop="count" label="数量" width="130px">
              <template slot-scope="scope">
                <span>{{ scope.row.count }}{{ scope.row.countUnit }}</span>
              </template>
          </el-table-column>
          <el-table-column prop="costPrice" label="单价" width="130px">
            <template slot-scope="scope">
              <el-input
                v-if="scope.row.seen"
                size="mini"
                v-model="scope.row.costPrice"
              ></el-input>
              <span v-else>{{ scope.row.costPrice }}</span>
            </template>
          </el-table-column>
          <!-- <el-table-column prop="remark" label="备注">
          </el-table-column> -->
        </el-table>
        <div class="printTeamplate-product-detail-summary-div fz9">
          <div class="printTeamplate-product-detail-summary-item">
            <span style="width: 180px; text-align: left">总额</span>
            <span style="width: 150px; text-align: right">{{poSum}}</span>
          </div>
        </div>
      </div>

      <div class="fz8">
        <p class="printTeamplate-content">
          {{
            contractTemplate.content == null
              ? ""
              : contractTemplate.content.value
          }}
        </p>
        <div class="printTeamplate-remark-div">
          <div class="remark-title-div">备注</div>
          <div>
            <div
              class="printTeamplate-remark-p"
              v-for="remark in contractTemplate.remarkList"
              :key="remark.id"
            >
              {{ remark.value }}
            </div>
          </div>
        </div>
      </div>
      <div class="printTeamplate-confirm-div fz9">
        <div class="printTeamplate-confirm-box">
          <span>买方确认</span>
          <span>名称：{{ userInfo.company.companyName }}</span>
          <span
            >地址：{{ userInfo.company.provinceName
            }}{{ userInfo.company.cityName }}{{ userInfo.company.areaName
            }}{{ userInfo.company.address }}</span
          >
          <span>联系人：{{ userInfo.company.communicateName }}</span>
          <span>电话：{{ userInfo.company.phoneNumber }}</span>
          <span>日期：{{ Datetime }}</span>
        </div>
        <div class="printTeamplate-confirm-box">
          <span>卖方确认</span>
          <span>名称：{{ newPO.supply.name }}</span>
          <span>地址：{{ newPO.supply.address }}</span>
          <span>联系人：{{ newPO.supply.contact }}</span>
          <span>电话：{{ newPO.supply.phone }}</span>
          <span>日期：</span>
        </div>
      </div>
      <p class="printTeamplate-description">
        {{
          contractTemplate.description == null
            ? ""
            : contractTemplate.description.value
        }}
      </p>
    </div>
    <div class="edit-printTeamplate-button-div">
      <!-- <el-button @click="savePo('vendeeForm')" type="primary">打印采购单</el-button> -->
      <el-button type="primary" @click="printPdf">打印合同</el-button>
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
      Datetime:new Date().format("yyyy-MM-dd"),
      poDetailList:[],
      newPO: {
        id: "",
        poNo: "",
        state: "",
        supplyId: "",
        deliveryDay: "",
        payterm: "",
        createDate: "",
        createBy: "",
        description: "",
        purchaseDetailList: [],
        supplyName: "",
        poDetailList:[],
        createName:'',
        supply:[],
      },
      rules: {
        supplyId: [
          { required: true, message: "请选择商品供应商", trigger: "blur" }
        ],
      },
    };
  },
  computed: {
    ...mapGetters(["contractTemplate", "userInfo", "supplyList","prList","poList"]),
    poSum:function() {
      var price = 0 ;
      var total = 0;
      this.newPO.poDetailList.map((item) => {
        total = this.$my.NumberAdd(total, this.$my.NumberMul(item.count, item.costPrice));
      });
      price = total.toFixed(2);
      return price;
    },

  },
  methods: {
    ...mapActions({
      GetSupplyList: "GetSupplyList",
      GetContractTemplate:"GetContractTemplate",
      SavePoList:"SavePoList",
      GetPRList:"GetPRList",
      GetPOList:"GetPOList",
    }),
    getFullTime(time) {
    return new Date(time).format("yyyy-MM-dd");
    },
    printPdf() {
      print({
        printable: "printTeamplate",
        type: "html",
        maxWidth: "100%",
        targetStyles: ["*"],
      });
    },
    loadPo(str) {
      this.newPO = str;
      this.poDetailList=str.poDetailList;
    },
    savePo(formName){
      this.$refs[formName].validate(valid => {
        if (valid) {
          var checkRes = this.checkProductList();
          if (checkRes) 
          {
            var numbers = this.checkNumber();
            if(numbers)
            {
              this.newPO.supplyId=this.vendeeInfo.supplyId;
              this.newPO.state="CREATE";
              this.newPO.createDate=new Date();
              let list=this.pushbackprice(this.newPO.poDetailList,this.newPO.purchaseDetailList,this.newPO);
              this.newPO.createBy=this.userInfo.id;
              this.newPO.purchaseDetailList=list;
              this.SavePoList(this.newPO)
              let params = {
                state: "APPROVE"
              };
              this.GetPRList(params);
              let arg = {
                state: "CREATE"
              };
              this.GetPOList(arg);
              this.$emit('closePopWindow');
            }
            else
            {
              this.$message.warning("单价信息为数字");
            }
          } else {
            this.$message.warning("请完善单价信息");
          }
        } else {
          this.$message.warning("请选择供应商信息");
        }
      });
    },
    pushbackprice(arr1, arr2,po){
      let list = [];
      for (let index = 0;index < arr2.length;index++)
      {
        const element = arr2[index];
        let existItem = arr1.find((item) => {
          return item.materialNo === element.materialNo;
        });
        if (existItem) {
          element.costPrice=existItem.costPrice;
          element.supplyId=po.supplyId;
          list.push(element);
        }
      }
      return list;
    },
    checkProductList: function() {
      var pass = true;
      this.poDetailList.map((item) => {
        if (item.costPrice==null||item.costPrice==""||item.costPrice <= 0) {
          pass = false;
        }
      });
      return pass;
    },
    checkNumber(){
      var pass = true;
      this.poDetailList.map((item) => {
        if (isNaN(item.costPrice)) {
          pass = false;
        }
      });
      return pass;
    },
  },
  beforeMount() {
    this.GetContractTemplate();
  },
};
</script>
<style lang="less">
.printTeamplate-div {
  width: 950px;
  padding: 1rem;
  border: 1px solid rgb(233, 229, 229);
  display: flex;
  flex-direction: column;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
}

.printTeamplate-header {
  height: 60px;
  display: inline;
  line-height: 60px;
  vertical-align: center;
}
.printTeamplate-header-left {
  margin-right: 10px;
  font-size: 1.5rem;
  color: #606266;
  float: left;
}
.printTeamplate-header-title {
  float: right;
  margin-right: 10px;
  letter-spacing: 7px;
  font-size: 1.5rem;
  color: #606266;
}
.printTeamplate-buyer-info-div {
  display: flex;
  flex-direction: row;
  border: 1px solid rgb(209, 205, 205);
}
// .printTeamplate-buyer-info-div-left {
//   height: 100%;
//   border-right: 1px solid rgb(209, 205, 205);
//   width: 100%;
// }
.printTeamplate-buyer-info-div-left-firstfloor {
  margin-top: 15px;
  height: 50%;
  display: flex;
  flex-direction: column;
  border: 1px solid rgb(209, 205, 205);
}
.printTeamplate-buyer-info-div-left-secfloor {
  margin-top: 15px;
  display: flex;
  flex-direction: column;
  height: 50%;
  border: 1px solid rgb(209, 205, 205);
}
.title-div-gray {
  background: lightgray;
  height: 35px;
  display: flex;
  align-items: center;
  padding: 0px 10px;
  font-weight: bold;
}

.printTeamplate-product-detail-div {
  margin-top: 15px;
  border: 1px solid rgb(209, 205, 205);
}

.printTeamplate-product-detail-summary-item {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
  padding: 3px 10px;
}
.printTeamplate-content {
  text-align: left;
}
.printTeamplate-remark-div {
  display: flex;
  flex-direction: row;
}
.printTeamplate-remark-p {
  text-align: left;
  vertical-align: top;
}
.remark-title-div {
  min-width: 50px;
  text-align: left;
}
.printTeamplate-confirm-div {
  display: flex;
  flex-direction: row;
  margin-top: 3rem;
}
.printTeamplate-confirm-box {
  width: 50%;
  text-align: left;
  display: flex;
  flex-direction: column;
}
.printTeamplate-description {
  text-align: left;
  font-size: 0.3rem;
  margin-top: 2rem;
}
.edit-printTeamplate-button-div {
  padding: 1rem 0;
  text-align: right;
}
.justify-content {
  justify-content: space-between;
}
.add-printTeamplate-deail {
  margin-top: 2px;
  width: 100%;
  color: #259dff;
  border: #dcdfe6 dashed 1px !important;
}
.printTeamplate-product-detail-select-div {
  display: flex;
  align-items: center;
  justify-content: space-between;
}
.printTeamplate-product-seen-div {
  display: flex;
  align-items: center;
}
.form-input {
  width: 350px;
  float: left;
}
.tempate-text-left-center {
  float: left;
  font-size: 0.8rem;
  padding: 0.3rem;
  text-align: left;
  width: 400px;
}
.printTeamplate-conten {
  padding: 8px;
}
.printTeamplate-supply{
   padding-left: 10px;
   padding-top:15px;
   padding-right: 10px;
}
</style>
