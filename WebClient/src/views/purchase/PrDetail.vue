<template>
  <div class="prDetail-container">
    <div class="prDetail-header">
      <el-input
        class="prDetail-fliter-input"
        size="small"
        placeholder="搜索物料申请单"
        v-model="searchContent"
        suffix-icon="el-icon-search"
      ></el-input>
    </div>
    <div class="prDetail-content">
      <el-table :data="purchaseListPageShow" 
        border="" stripe
        size="small"
        :header-row-style="{ height: '40px' }"
        :row-style="{ height: '40px' }"
        :cell-style="{ padding: '2px', color: '#909399' }"
        :header-cell-style="{ background: '#909399', color: 'white' }">
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
        <el-table-column prop="" label="操作" width="60px">
          <template slot-scope="scope">
            <el-tooltip effect="light" content="查看" placement="top">
              <el-button size="mini" @click="handleEdit(scope.$index,scope.row)" plain circle type="primary" icon="el-icon-view"/>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <div class="prDetail-content-footer"></div>

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
              <!-- <template slot-scope="scope" >
                <div class="roleinformation" >
                  <el-form-item label="物品：" prop="materialName" label-width="65px" >
                    <span>{{ scope.row.materialName }}</span>
                  </el-form-item>
                </div>
              </template> -->
            </el-table-column>
              <el-table-column prop="specification" label="规格"  width="220px" >
                <!-- <template slot-scope="scope">
                  <div class="purchaseInformation">
                    <el-form-item label="规" prop="specification" label-width="35px" >
                      <span>{{ scope.row.specification }}</span>
                    </el-form-item>
                  </div>
                </template> -->
              </el-table-column>
            <!-- <el-table-column prop="count" label="数量" width="80px" >
            </el-table-column> -->
            <el-table-column prop="count" label="数量" width="100px">
              <template slot-scope="scope"><span>{{scope.row.count}}{{scope.row.countUnit}}</span></template>
            </el-table-column>
            <el-table-column prop="state" label="状态" width="90px" >
              <!-- <template slot-scope="scope" >
                <div class="purchaseInformation">
                  <el-form-item label="数" prop="count" label-width="35px" >
                    <span>{{ scope.row.count }}</span>
                  </el-form-item>
                </div>
              </template> -->
            </el-table-column>

            <el-table-column prop="remark" label="备注" >
              <!-- <template slot-scope="scope" >
                <div class="purchaseInformation">
                  <el-form-item label="备注" prop="remark" label-width="45px" >
                    <span>{{ scope.row.remark }}</span>
                  </el-form-item>
                </div>
              </template> -->
            </el-table-column>

          </el-table>
          </el-form-item>
        </el-row>
        <!-- <el-row>
          <el-form-item>
            <el-button type="primary" :disabled="editpurchase.state=='BUYING' || editpurchase.state=='COMPLETE'" @click="onEditpurchase('purchaseEidtForm')" :loading="loading">保存</el-button>
            <el-button @click="purchaseEditDialog = false" v-if="!loading">取 消</el-button>
          </el-form-item>
        </el-row> -->
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
    };
  },
  computed: {
    ...mapGetters(["prDetailList","userInfo"]),
    purchaseListPageShow(){
      return this.prDetailList.filter((item)=>{
        let key=
        item.planNo +
        item.type +
        item.state +
        item.orderFormId +
        item.requester +
        item.contractNo +
        item.description +
        item.orderFormNo;
        let index = key.toUpperCase().indexOf(this.searchContent.toUpperCase());
        return index != -1;
      });
    }
  },
  methods: {
    ...mapActions({
      GetAllPurchaseDetailList: "GetAllPurchaseDetailList",
      QueryPurchaseObj:"QueryPurchaseObj",
    }),
    getFullTime(time) {
    return new Date(time).format("yyyy-MM-dd");
    },
        handleEdit(index,row){
      this.editIndex=index;
      let cat={id:row.id,planNo:row.planNo,index:index}
      this.QueryPurchaseObj(cat)
        .then(res=>{
          if (res.resultStatus == 1) {
            this.editpurchase=res.data;
            this.PRNo=this.editpurchase.planNo;
            // let list = [];
            // for (let index = 0; index < res.data.purchaseDetailList.length; index++) {
            //     const element = res.data.purchaseDetailList[index];
            //     let con = { id: element.id, type:element.type,materialNo:element.materialNo,materialName:element.materialName,specification:element.specification,remark:element.remark,count:element.count,countUnit:element.countUnit,purchasePlanNo:element.purchasePlanNo,createEmployeeNo:element.createEmployeeNo,createBy:element.createBy,createDate:element.createDate,state:element.state,sourceId:element.sourceId,sourceNo:element.sourceNo,requester:element.requester,requesterId:element.requesterId,supplyId:element.supplyId,seen: false, isEditable:false ,notSavedFlag:false};
            //     list.push(con);
            // }
            // this.editpurchase.purchaseDetailList=list;
            this.purchaseEditDialog = true;
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
  beforeMount() {
    this.GetAllPurchaseDetailList();
  },
};
</script>
<style>
.prDetail-container {
  display: flex;
  flex-direction: column;
}
.prDetail-header {
  padding-bottom: 10px;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
}
.prDetail-fliter-input {
  width: 400px;
  float: left;
}
</style>
