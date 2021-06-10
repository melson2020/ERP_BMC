<template>
  <div class="po-container">
    <div class="po-header">
      <div>
      <span class="title-name">待交货详情</span>
      </div>
      <div>
        <el-input
          class="po-fliter-input"
          size="small"
          placeholder="搜索待交货物料"
          v-model="searchContent"
          suffix-icon="el-icon-search"
        ></el-input>
      </div>
    </div>
    <el-table :data="prListPageShow" 
      border
      size="small"
      :header-row-style="{ height: '40px' }"
      :row-style="{ height: '40px' }"
      :cell-style="{ padding: '2px', color: '#909399' }"
      :header-cell-style="{ background: '#909399', color: 'white' }">
      <el-table-column prop="poNo" label="PO-NO" width="140px"></el-table-column>
      <el-table-column prop="purchasePlanNo" label="PR-NO" width="140px"></el-table-column>
      <el-table-column prop="materialNo" label="物料号" width="140px"> </el-table-column>
      <el-table-column prop="materialName" label="物料名称" width="180px"> </el-table-column>
      <el-table-column prop="specification" label="规格" width="180px"> </el-table-column>
      <el-table-column label="数量" width="100px">
        <template slot-scope="scope"><span>{{scope.row.count}}{{scope.row.countUnit}}</span>
        </template>
      </el-table-column>
      <el-table-column prop="requester" label="采购人" width="220px"> </el-table-column>
      <el-table-column prop="createDate" label="申请日期" width="100px"> 
        <template slot-scope="scope">
          <span>{{getFullTime(scope.row.createDate) }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="supply" label="供应商"> </el-table-column>
      <el-table-column prop="" label="操作" width="80px">
        <template slot-scope="scope">
          <el-tooltip effect="light" content="查看" placement="top">
            <el-button size="mini" @click="handleEdit(scope.$index,scope.row)" plain circle type="primary" icon="el-icon-view"/>
          </el-tooltip>
        </template>
      </el-table-column>
    </el-table>



    <el-dialog :title=PRNo :visible.sync="purchaseEditDialog" width="1024px">
      <el-form status-icon :model="editpurchase" ref="purchaseEidtForm" label-width="100px">
        <el-row>
           <el-col :span="12">
             <div class="request-div">
                <el-form-item label="申请人" prop="requester">
                  <el-input v-model="editpurchase.type" autocomplete="off" disabled style="width:400px"></el-input>
                </el-form-item>
             </div>
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
            <el-table-column prop="materialName" label="物品" width="250px" >
            </el-table-column>
              <el-table-column prop="specification" label="规格"  width="220px" >
              </el-table-column>
            <el-table-column prop="count" label="数量" width="80px" >

            </el-table-column>
            <el-table-column prop="state" label="状态" width="90px" >
            </el-table-column>

            <el-table-column prop="remark" label="备注" >
            </el-table-column>

          </el-table>
          </el-form-item>
        </el-row>
      </el-form>
    </el-dialog>



    </div>
</template>
<script>
import { mapGetters } from "vuex";
import { mapActions } from "vuex";
export default {
    data(){
      return{
        PRNo:'',
        purchaseEditDialog: false,
        searchContent: "",
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
      ...mapGetters(["prBuyingList","userInfo"]),
      prListPageShow(){
        return this.prBuyingList.filter((item)=>{
          let key=
          item.purchasePlanNo +
          item.poNo+
          item.materialNo +
          item.materialName +
          item.specification +
          item.count +
          item.countUnit +
          item.createDate +
          item.supply +
          item.requester +
          item.description ;
          let index = key.toUpperCase().indexOf(this.searchContent.toUpperCase());
          return index != -1;
        });
      },
    },
    methods:{
      ...mapActions({
      GetPRBuyingList:"GetPRBuyingList",
      QueryPurchaseObj:"QueryPurchaseObj",
    }),


    getFullTime(time) {
      return new Date(time).format("yyyy-MM-dd");
    },
    handleEdit(index,row){
console.log(row)
      this.editIndex=index;
      let cat={id:row.id,planNo:row.purchasePlanNo,index:index}
      this.QueryPurchaseObj(cat)
        .then(res=>{
          if (res.resultStatus == 1) {
            this.editpurchase=res.data;
            this.editpurchase.purchaseDetailList=res.data.purchaseDetailList;
            this.PRNo=this.editpurchase.planNo;
            this.purchaseEditDialog = true;
          }
          else{
            this.$message.error(res.message);
          }
      })
    },
  },
    beforeMount() {
      let params = {
        state: "BUYING"
      };
      this.GetPRBuyingList(params);
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
.po-fliter-input {
  width: 400px;
  float: left;
  margin-right: 8px;
}

.title-name {
  font-size: 13px;
  font-weight: bold;
  color: #409eff;
}
</style>