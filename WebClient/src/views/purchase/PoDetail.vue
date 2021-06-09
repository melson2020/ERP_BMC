<template>
  <div class="poDetail-container">
    <div class="poDetail-header">
      <el-input
        class="poDetail-fliter-input"
        size="small"
        placeholder="搜索采购订单"
        v-model="searchContent"
        suffix-icon="el-icon-search"
      ></el-input>
    </div>
    <div class="poDetail-content">
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
          </template>
        </el-table-column>
      </el-table>
    </div>

    <div class="poDetail-content-footer"></div>

    <el-dialog
      title="采购单(PO)"
      :visible.sync="printTemplateVisible"
      width="1024px">
      <m-printTemplate ref="print" ></m-printTemplate>
    </el-dialog>


  </div>
</template>
<script>
import printTemplate from "./PrintTemplate.vue"
import { mapGetters } from "vuex";
import { mapActions } from "vuex";
export default {
  data() {
    return {
      printTemplateVisible: false,
      searchContent: "",
      loading: false,
      editIndex:'',
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
    };
  },
  computed: {
    ...mapGetters(["poDetailList","userInfo"]),
    poListShow(){
      return this.poDetailList.filter((item)=>{
        let key=
        item.poNo +
        item.state +
        item.supplyName +
        item.createBy +
        item.description ;
        let index = key.toUpperCase().indexOf(this.searchContent.toUpperCase());
        return index != -1;
      });
    }
  },
  methods: {
    ...mapActions({
      GetAllPODetailList: "GetAllPODetailList",
      QueryPoObj:"QueryPoObj",
    }),
    getFullTime(time) {
    return new Date(time).format("yyyy-MM-dd");
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
    "m-printTemplate":printTemplate,
  },
  beforeMount() {
    this.GetAllPODetailList();
  },
};
</script>
<style>
.poDetail-container {
  display: flex;
  flex-direction: column;
}
.poDetail-header {
  padding-bottom: 10px;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
}
.poDetail-fliter-input {
  width: 400px;
  float: left;
}
</style>
