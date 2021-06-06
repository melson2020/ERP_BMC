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
      <el-table :data="purchaseListPageShow" 
        border
        size="small"
        :header-row-style="{ height: '40px' }"
        :row-style="{ height: '40px' }"
        :cell-style="{ padding: '2px', color: '#909399' }"
        :header-cell-style="{ background: '#909399', color: 'white' }">
        <el-table-column prop="planNo" label="PR-NO" width="180px"></el-table-column>
        <el-table-column prop="requester" label="采购人" width="280px"> </el-table-column>
        <el-table-column prop="type" label="采购类型" width="180px"> </el-table-column>
        <el-table-column prop="state" label="状态" width="180px"> </el-table-column>
        <el-table-column prop="createDate" label="创建日期" width="140px"> 
          <template slot-scope="scope">
            <span>{{getFullTime(scope.row.createDate) }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="description" label="备注理由"> </el-table-column>
        <el-table-column prop="" label="操作" width="100px">
          <template slot-scope="scope">
            <el-tooltip effect="light" content="批准" placement="top">
              <el-button size="mini" @click="handleEdit(scope.$index,scope.row)" plain circle type="primary" icon="el-icon-edit"/>
            </el-tooltip>
            <el-tooltip effect="light" content="驳回" placement="top">
              <el-button size="mini" @click="handleReject(scope.$index, scope.row)" plain circle type="danger" icon="el-icon-delete"/>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>


    </el-scrollbar>

    <!-- <div class="po-content">
      <el-table :data="purchaseListPageShow" 
          border="" stripe           
          :header-row-style="{height:'40px' ,'align':'center'}"
          :row-style="{height:'40px'}"
          style="width: 100%">
        <el-table-column prop="planNo" label="PR-NO" width="180px"></el-table-column>
        <el-table-column prop="requester" label="采购人" width="280px"> </el-table-column>
        <el-table-column prop="type" label="采购类型" width="180px"> </el-table-column>
        <el-table-column prop="state" label="状态" width="180px"> </el-table-column>
        <el-table-column prop="createDate" label="创建日期" width="140px"> 
          <template slot-scope="scope">
            <span>{{getFullTime(scope.row.createDate) }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="description" label="备注理由"> </el-table-column>
        <el-table-column prop="" label="操作" width="100px">
          <template slot-scope="scope">
            <el-tooltip effect="light" content="批准" placement="top">
              <el-button size="mini" @click="handleEdit(scope.$index,scope.row)" plain circle type="primary" icon="el-icon-edit"/>
            </el-tooltip>
            <el-tooltip effect="light" content="驳回" placement="top">
              <el-button size="mini" @click="handleReject(scope.$index, scope.row)" plain circle type="danger" icon="el-icon-delete"/>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>

    </div> -->




    <!-- <el-dialog :title=PRNo :visible.sync="purchaseEditDialog" width="1024px">
      <el-form status-icon :model="editpurchase" ref="purchaseEidtForm" label-width="100px">
        <el-row>
           <el-col :span="12">
              <el-form-item label="申请人" prop="requester">
                <el-input v-model="editpurchase.requester" autocomplete="off" disabled></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="采购类型" prop="type">
                <el-input v-model="editpurchase.type" autocomplete="off" disabled></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="采购状态" prop="state">
                <el-input v-model="editpurchase.state" autocomplete="off" disabled></el-input>
              </el-form-item>
            </el-col>
        </el-row>
        <el-row>
          <el-form-item label="备注理由" prop="description">
            <el-input
              v-model="editpurchase.description"
              autocomplete="off"
              style="width: 100%;"
              rows="3"
              type="textarea"
              maxlength="200"
              show-word-limit
            ></el-input>
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="采购明细">
            <el-table
              :data="editpurchase.purchaseDetailList"
              border
              style="width: 100%"
              size="mini"
            >
              <el-table-column prop="materialName" label="物品" width="280px"></el-table-column>
              <el-table-column prop="specification" label="规格" width="180px"></el-table-column>
              <el-table-column prop="count" label="数量" width="180px"></el-table-column>
              <el-table-column prop="remark" label="备注"></el-table-column>

            </el-table>
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item>
            <el-button type="primary" @click="onEditpurchase('purchaseEidtForm')" :loading="loading">批 准</el-button>
            <el-button type="primary" @click="onEditreject()" :loading="loading">驳 回</el-button>
          </el-form-item>
        </el-row>
      </el-form>
    </el-dialog> -->


    <el-dialog
      ref="dialog"
      title="生成采购单(PO)"
      :visible.sync="poTemplateVisible"
      width="1100px">
      <m-poTemplate ref="child" v-bind:parentMsg="PONo"></m-poTemplate>
    </el-dialog>



    </div>
</template>
<script>
import poTemplate from "./PoTemplate";
import { mapGetters } from "vuex";
import { mapActions } from "vuex";
export default {
    data(){
      return{
        PRNo:'',
        PONo:'',
        options: [],
        multipleSelection: [],
        poTemplateVisible:false,
        purchaseEditDialog: false,
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
      }

    },
    computed: {
      ...mapGetters(["prList","userInfo"]),
      prListPageShow(){
        return this.prList.filter((item)=>{
          let key=
          item.purchasePlanNo +
          item.type +
          item.state +
          item.requester +
          item.description ;
          let index = key.toUpperCase().indexOf(this.searchContent.toUpperCase());
          return index != -1;
        });
      }
    },
    methods:{
      ...mapActions({
      GetPRList:"GetPRList",
      ApprovePurchaseObj:"ApprovePurchaseObj",
      PushPurchaseWaitList:"PushPurchaseWaitList",
      QueryPurchaseObj:"QueryPurchaseObj",
      RejectPurchaseWait:"RejectPurchaseWait",
    }),
    triggerSelection(){
      if(this.multipleSelection.length>0)
      {
        this.PONo="PO" + new Date().valueOf(),
console.log(this.PONo)
        this.poTemplateVisible = !this.poTemplateVisible;
        setTimeout(() => {
          this.$refs["child"].loadPo();
        }, 200);
      }
      else
      {
        this.$message.warning("请先选择物料信息");
        return false;
      }
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    getFullTime(time) {
    return new Date(time).format("yyyy-MM-dd");
    },
    // handleEdit(index,row){
    //   this.editIndex=index;
    //   let cat={id:row.id,planNo:row.planNo,index:index}
    //   this.QueryPurchaseObj(cat)
    //     .then(res=>{
    //       if (res.resultStatus == 1) {
    //         this.editpurchase=res.data;
    //         this.editpurchase.purchaseDetailList=res.data.purchaseDetailList;
    //         this.PRNo=this.editpurchase.planNo;
    //         this.purchaseEditDialog = true;
    //       }
    //       else{
    //         this.$message.error(res.message);
    //       }
    //   })
    //   .catch(err=>{
    //         let alert = err.message ? err.message : err;
    //         this.$message.error(alert);
    //   });
    // },
    // handleReject(index, row) {
    //   let cat={id:row.id,planNo:row.planNo,index:index}
    //    this.$messageBox.confirm('确认要驳回请求？',"提示",{
    //       confirmButtonText: '确定',
    //       cancelButtonText: '取消',
    //       type: 'warning'
    //     })
    //       .then(() => {
    //           this.RejectPurchaseWait(cat)
    //       })
    //       .catch(e=>e);
    //   },
    // onEditreject(){
    //   this.$messageBox.confirm('确认要驳回请求？',"提示",{
    //   confirmButtonText: '确定',
    //   cancelButtonText: '取消',
    //   type: 'warning'
    //   })
    //   .then(() => {
    //       this.RejectPurchaseWait(this.editpurchase);
    //       this.purchaseEditDialog=false;
    //   })
    //   .catch(e=>e);
    // },
    // onEditpurchase(formName){
    //   this.$refs[formName].validate(valid=>{
    //     if(valid){
    //       this.ApprovePurchaseObj(this.editpurchase)
    //       .then(res=>{
    //         if(res.resultStatus==1){
    //           let params = {
    //             state: "GetPRList"
    //           };
    //           this.GetPurchaseWaitList(params);
    //           this.purchaseEditDialog=false;
    //           this.$message({
    //             showClose:true,
    //             message:"操作成功",
    //             type:"success"
    //           });
    //         }
    //         else{
    //           this.$message.error(res.message);
    //         }
    //       })
    //       .catch(err=>{
    //           let alert = err.message ? err.message : err;
    //           this.$message.error(alert);
    //       });
    //     }
    //     else{
    //       this.$message.warning("请填写准确信息");
    //       return false;
    //     }
    //   })
    // }

    },
    components: {
      "m-poTemplate": poTemplate,
    },
    beforeMount() {
      let params = {
        state: "APPROVE"
      };
      this.GetPRList(params);
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