<template>
  <div class="po-container">
    <div class="po-header">
      <div>
      <span class="title-name">待采购详情</span>
      </div>
      <div>
        <el-input
          class="po-fliter-input"
          size="small"
          placeholder="搜索待采购物料"
          v-model="searchContent"
          suffix-icon="el-icon-search"
        ></el-input>
        <el-button type="primary" size="small" @click="triggerSelection()">发起采购</el-button>
      </div>
    </div>

    <el-scrollbar  class="content-scrollbar scrollbar-height" wrap-style="overflow-x:hidden;">
      <el-table :data="prListPageShow" 
        ref="multipleTable"
        @selection-change="handleSelectionChange"
        border
        size="small"
        :header-row-style="{ height: '40px' }"
        :row-style="{ height: '40px' }"
        :cell-style="{ padding: '2px', color: '#909399' }"
        :header-cell-style="{ background: '#909399', color: 'white' }">
        <el-table-column type="selection" width="45px"></el-table-column>
        <el-table-column prop="purchasePlanNo" label="PR-NO" width="180px"></el-table-column>
        <el-table-column prop="materialNo" label="物料号" width="180px"> </el-table-column>
        <el-table-column prop="materialName" label="物料名称" width="180px"> </el-table-column>
        <el-table-column prop="specification" label="规格" width="180px"> </el-table-column>
        <el-table-column label="数量" width="100px">
          <template slot-scope="scope"><span>{{scope.row.count}}{{scope.row.countUnit}}</span>
          </template>
        </el-table-column>
        <el-table-column prop="requester" label="采购人" width="280px"> </el-table-column>
        <el-table-column prop="createDate" label="申请日期" width="140px"> 
          <template slot-scope="scope">
            <span>{{getFullTime(scope.row.createDate) }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="description" label="备注理由"> </el-table-column>
      </el-table>


    </el-scrollbar>


    <div class="po-down-header">
      <div>
        <span class="title-name">采购单号</span>
      </div>
      <div>
        <el-input
          class="po-fliter-input"
          size="small"
          placeholder="搜索采购单号"
          v-model="searchContent2"
          suffix-icon="el-icon-search"
        ></el-input>
      </div>
    </div>
    <el-scrollbar  class="content-scrollbar scrollbar-height" wrap-style="overflow-x:hidden;">
      <el-table :data="poListShow" 
        border
        size="small"
        :header-row-style="{ height: '40px' }"
        :row-style="{ height: '40px' }"
        :cell-style="{ padding: '2px', color: '#909399' }"
        :header-cell-style="{ background: '#909399', color: 'white' }">
        <el-table-column prop="poNo" label="PO-NO" width="180px"></el-table-column>
        <el-table-column prop="supplyName" label="供应商" width="380px"> </el-table-column>
        <el-table-column prop="amount" label="金额" width="120px"> </el-table-column>
        <el-table-column prop="state" label="状态" width="180px"> </el-table-column>
        <el-table-column prop="createDate" label="创建日期" width="140px"> 
          <template slot-scope="scope">
            <span>{{getFullTime(scope.row.createDate) }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="createName" label="创建人" width="180px"> </el-table-column>
        <el-table-column prop="description" label="备注理由"> </el-table-column>
        <el-table-column prop="" label="操作" width="60px">
          <template slot-scope="scope">
            <el-tooltip effect="light" content="查看/打印" placement="top">
              <el-button size="mini" @click="handleEdit(scope.$index,scope.row)" plain circle type="primary" icon="el-icon-edit"/>
            </el-tooltip>
            <!-- <el-tooltip effect="light" content="打印" placement="top">
              <el-button size="mini" @click="handleReject(scope.$index, scope.row)" plain circle type="danger" icon="el-icon-delete"/>
            </el-tooltip> -->
          </template>
        </el-table-column>
      </el-table>


    </el-scrollbar>


    <el-dialog
      title="生成采购单(PO)"
      :visible.sync="poTemplateVisible"
      width="1024px">
      <m-poTemplate ref="child" v-on:closePopWindow="closePopWindow"></m-poTemplate>
    </el-dialog>


    <el-dialog
      title="采购单(PO)"
      :visible.sync="printTemplateVisible"
      width="1024px">
      <m-printTemplate ref="print" ></m-printTemplate>
    </el-dialog>


    </div>
</template>
<script>
import poTemplate from "./PoTemplate";
import printTemplate from "./PrintTemplate.vue"
import { mapGetters } from "vuex";
import { mapActions } from "vuex";
export default {
    data(){
      return{
        options: [],
        multipleSelection: [],
        poTemplateVisible:false,
        printTemplateVisible: false,
        searchContent: "",
        searchContent2: "",
        loading: false,
        editIndex:'',
        editpurchase:{
          id:'',
          orderFormId:'',
          orderFormNo:'',
          customerNo:'',
          customerName:'',
          contractNo:'',
          requester:'',
          requesterId:'',
          planNo:'',
          type:'',
          state:'',
          createDate:'',
          createBy:'',
          description:'',
          purchaseDetailList:[],
        },
        newPO:{
          id:'',
          poNo:'',
          state:'',
          supplyId:'',
          supplyName:'',
          deliveryDay:'',
          payterm:'',
          createDate:'',
          createBy:'',
          description:'',
          purchaseDetailList:[],
          poDetailList:[],
          createName:'',
        },
        editPO:{
          id:'',
          poNo:'',
          state:'',
          supplyId:'',
          supplyName:'',
          deliveryDay:'',
          payterm:'',
          createDate:'',
          createBy:'',
          description:'',
          purchaseDetailList:[],
          poDetailList:[],
          createName:'',
          supply:[],
        }
      }

    },
    computed: {
      ...mapGetters(["prList","poList","userInfo"]),
      prListPageShow(){
        return this.prList.filter((item)=>{
          let key=
          item.purchasePlanNo +
          item.materialNo +
          item.materialName +
          item.specification +
          item.count +
          item.countUnit +
          item.createDate +
          item.requester +
          item.description ;
          let index = key.toUpperCase().indexOf(this.searchContent.toUpperCase());
          return index != -1;
        });
      },
      poListShow(){
        return this.poList.filter((item)=>{
          let key=
          item.poNo +
          item.amount+
          item.state +
          item.supplyName +
          item.createBy +
          item.description ;
          let index = key.toUpperCase().indexOf(this.searchContent2.toUpperCase());
          return index != -1;
        });
      },
    },
    methods:{
      ...mapActions({
      GetPRList:"GetPRList",
      GetPOList:"GetPOList",
      QueryPoObj:"QueryPoObj",
    }),
    closePopWindow(str){
      // let params = {
      //   state: "APPROVE"
      // };
      // this.GetPRList(params);
      // let arg = {
      //   state: "CREATE"
      // };
      // this.GetPOList(arg);
      this.poTemplateVisible = false;
      this.$messageBox.confirm('打印采购单？',"提示",{
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
      })
      .then(() => {
          let cat={poNo:str}
          this.QueryPoObj(cat)
            .then(res=>{
              if (res.resultStatus == 1) {
                this.editPO=res.data;
                let list=this.generatedEditList(this.editPO.purchaseDetailList);
                this.editPO.poDetailList=list;
                this.printTemplateVisible = true;
                setTimeout(() => {
                  this.$refs["print"].loadPo(this.editPO);
                }, 200);
              }
              else{
                this.$message.error(res.message);
              }
          })
          .catch(err=>{
                let alert = err.message ? err.message : err;
                this.$message.error(alert);
          });
      })
    },
    triggerSelection(){
      let list=[]
      if(this.multipleSelection.length>0)
      {
        this.newPO.poNo="PO" + new Date().valueOf(),
        list=this.generatedList(this.multipleSelection,this.newPO.poNo);
        this.newPO.purchaseDetailList=this.generatedPrDetailList(this.multipleSelection,this.newPO.poNo);
        this.newPO.poDetailList=list;
        this.poTemplateVisible = !this.poTemplateVisible;
        setTimeout(() => {
          this.$refs["child"].loadPo(this.newPO);
        }, 200);
      }
      else
      {
        this.$message.warning("请先选择物料信息");
        return false;
      }
    },
    generatedPrDetailList(arr,po){
      let list = [];
      for (let index = 0;index < arr.length;index++)
      {
        const element = arr[index];
        let con = {
            id: element.id,
            type: element.type,
            materialNo: element.materialNo,
            materialName: element.materialName,
            specification: element.specification,
            remark: element.remark,
            count: element.count,
            countUnit: element.countUnit,
            purchasePlanNo: element.purchasePlanNo,
            createEmployeeNo: element.createEmployeeNo,
            createBy: element.createBy,
            createDate: element.createDate,
            state: "BUYING",
            sourceId: element.sourceId,
            sourceNo: element.sourceNo,
            requester: element.requester,
            requesterId: element.requesterId,
            supplyId: element.supplyId,
            poNo:po,
            costPrice:'',
          };
          list.push(con);
      }
      return list;
    },
    generatedList(arr,po){
      let list = [];
      for (
        let index = 0;
        index < arr.length;
        index++
      ) {
        const element = arr[index];
        let existItem = list.find((item) => {
          return item.materialNo === element.materialNo;
        });
        if (existItem) {
          existItem.count=this.$my.NumberAdd(existItem.count, element.count)
        } else {
          let con = {
            id: element.id,
            type: element.type,
            materialNo: element.materialNo,
            materialName: element.materialName,
            specification: element.specification,
            remark: element.remark,
            count: element.count,
            countUnit: element.countUnit,
            purchasePlanNo: element.purchasePlanNo,
            createEmployeeNo: element.createEmployeeNo,
            createBy: element.createBy,
            createDate: element.createDate,
            state: "BUYING",
            sourceId: element.sourceId,
            sourceNo: element.sourceNo,
            requester: element.requester,
            requesterId: element.requesterId,
            supplyId: element.supplyId,
            poNo:po,
            costPrice:'',
            seen: false,
            isEditable: false,
            notSavedFlag: false,
          };
          list.push(con);
        }
      }
      return list;
    },
    generatedEditList(arr){
      let list = [];
      for (
        let index = 0;
        index < arr.length;
        index++
      ) {
        const element = arr[index];
        let existItem = list.find((item) => {
          return item.materialNo === element.materialNo;
        });
        if (existItem) {
          existItem.count=this.$my.NumberAdd(existItem.count, element.count)
        } else {
          let con = {
            id: element.id,
            type: element.type,
            materialNo: element.materialNo,
            materialName: element.materialName,
            specification: element.specification,
            remark: element.remark,
            count: element.count,
            countUnit: element.countUnit,
            purchasePlanNo: element.purchasePlanNo,
            createEmployeeNo: element.createEmployeeNo,
            createBy: element.createBy,
            createDate: element.createDate,
            state: element.state,
            sourceId: element.sourceId,
            sourceNo: element.sourceNo,
            requester: element.requester,
            requesterId: element.requesterId,
            supplyId: element.supplyId,
            poNo:element.poNo,
            costPrice:element.costPrice,
          };
          list.push(con);
        }
      }
      return list;
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    getFullTime(time) {
    return new Date(time).format("yyyy-MM-dd");
    },
    handleEdit(index,row){
      this.editIndex=index;
      let cat={id:row.id,poNo:row.poNo,index:index}
      this.QueryPoObj(cat)
        .then(res=>{
          if (res.resultStatus == 1) {
            this.editPO=res.data;
            let list=this.generatedEditList(this.editPO.purchaseDetailList);
            this.editPO.poDetailList=list;
            this.printTemplateVisible = true;
            setTimeout(() => {
              this.$refs["print"].loadPo(this.editPO);
            }, 200);
          }
          else{
            this.$message.error(res.message);
          }
      })
      .catch(err=>{
            let alert = err.message ? err.message : err;
            this.$message.error(alert);
      });
    },
  

    },
    components: {
      "m-poTemplate": poTemplate,
      "m-printTemplate":printTemplate,
    },
    beforeMount() {
      let params = {
        state: "APPROVE"
      };
      this.GetPRList(params);
      let arg = {
        state: "CREATE"
      };
      this.GetPOList(arg);
    },
};
</script>
<style lang="less">
.po-container {
  display: flex;
  flex-direction: column;
  width: 98%;
  padding: 15px;
}
.po-header {
  padding-bottom: 10px;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
}
.po-down-header{
  padding-top: 10px;
  padding-bottom: 10px;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
}
.po-fliter-input {
  width: 400px;
  float: left;
  margin-right: 8px;
}
.add-PR {
  margin-top: 5px;
  width: 100%;
  border: #dcdfe6 dashed 1px !important;
}
.po-div{
  display: flex;
  flex-direction: row;
  width: auto;
  justify-content: space-between;
}
.purchaseInformation{
  display: flex;
  flex-direction: row;
  width: auto;
  justify-content: space-between;
}
.purchaseOperation{
    display: flex;
  flex-direction: row;
  width: auto;
  justify-content: space-around;
}
.content-scrollbar /deep/.el-scrollbar__wrap {
  overflow-x: hidden;
  // overflow-y: hidden;
}
.scrollbar-height{
    height: 40vh;
}
.title-name {
  font-size: 16px;
  font-weight: bold;
  color: #409eff;
}
</style>