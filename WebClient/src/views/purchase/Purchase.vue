<template>
  <div class="purchase-container">
    <div class="purchase-header">
    <el-input
      class="purchase-fliter-input"
      size="small"
      placeholder="搜索采购记录"
      v-model="searchContent"
      suffix-icon="el-icon-search"
    ></el-input>
    <!-- <el-button type="primary" size="small" @click="resetForm('purchaseAddForm')">新建类别</el-button> -->
    </div>

    <div class="purchase-content">
      <el-table :data="purchaseListPageShow" 
          border="" stripe           
          :header-row-style="{height:'40px' ,'align':'center'}"
          :row-style="{height:'40px'}"
          style="width: 100%">
        <el-table-column prop="purchaseId" label="类别代码" width="180px"></el-table-column>
        <el-table-column prop="name" label="类别名称" width="280px"> </el-table-column>
        <el-table-column prop="description" label="备注信息"> </el-table-column>
        <!-- <el-table-column prop="" label="操作" width="100px">
          <template slot-scope="scope">
            <el-tooltip effect="light" content="修改类别信息" placement="top">
              <el-button size="mini" @click="handleEdit(scope.$index,scope.row)" plain circle type="primary" icon="el-icon-edit"/>
            </el-tooltip>
            <el-tooltip effect="light" content="删除类别信息" placement="top">
              <el-button size="mini" @click="handleDelete(scope.$index, scope.row)" plain circle type="danger" icon="el-icon-delete"/>
            </el-tooltip>
          </template>
        </el-table-column> -->
      </el-table>

    </div>

    <div class="purchase-content-footer"></div>

    <el-dialog title="新建类别" :visible.sync="purchaseAddDialog" :close-on-click-modal="false" width="1024px">
      <el-form status-icon :model="newpurchase" :rules="rules" ref="purchaseAddForm" label-width="100px">
          <el-row>
            <el-form-item label="类别名称" prop="name">
              <el-input v-model="newpurchase.name" autocomplete="off" ></el-input>
            </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="备注信息" prop="description">
            <el-input
              v-model="newpurchase.description"
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
          <el-form-item>
            <el-button type="primary" @click="onAddpurchase('purchaseAddForm')" :loading="loading">保存</el-button>
            <el-button @click="purchaseAddDialog = false" v-if="!loading">取 消</el-button>
          </el-form-item>
        </el-row>
      </el-form>
    </el-dialog>

    <el-dialog title="编辑类别" :visible.sync="purchaseEditDialog" :close-on-click-modal="false" width="1024px">
      <el-form status-icon :model="editpurchase" :rules="rules" ref="purchaseEditForm" label-width="100px">
          <el-row>
            <el-form-item label="类别名称" prop="name">
              <el-input v-model="editpurchase.name" autocomplete="off" ></el-input>
            </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="备注信息" prop="description">
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
          <el-form-item>
            <el-button type="primary" @click="onEditpurchase('purchaseEditForm')" :loading="loading">保存</el-button>
            <el-button @click="purchaseEditDialog = false" v-if="!loading">取 消</el-button>
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
        purchaseEditDialog: false,
        purchaseAddDialog: false,
        searchContent: "",
        loading: false,
        editIndex:'',
        newpurchase:{
          id:'',
          orderFormId:'',
          orderFormNo:'',
          customerNo:'',
          customerName:'',
          contractNo:'',
          requester:'',
          planNo:'',
          type:'',
          state:'',
          createDate:'',
          createBy:'',
          description:'',
          purchaseDetailList:[],
        },
        editpurchase:{
          id:'',
          orderFormId:'',
          orderFormNo:'',
          customerNo:'',
          customerName:'',
          contractNo:'',
          requester:'',
          planNo:'',
          type:'',
          state:'',
          createDate:'',
          createBy:'',
          description:'',
          purchaseDetailList:[],
        },
        rules: {
        name: [
          { required: true, message: "请输入类别名称", trigger: "blur" }
        ],
        },
      }

    },
    computed: {
      ...mapGetters(["purchaseList","userInfo"]),
      purchaseListPageShow(){
        return this.purchaseList.filter((item)=>{
          let key=
          item.purchaseId +
          item.name +
          item.description ;
          let index = key.toUpperCase().indexOf(this.searchContent.toUpperCase());
          return index != -1;
        });
      }
    },
    methods:{
      ...mapActions({
      GetPurchaseList:"GetPurchaseList",
      SavePurchase:"SavePurchase",
      PushPurchaseList:"PushPurchaseList",
      QueryPurchaseObj:"QueryPurchaseObj",
      DisablePurchase:"DisablePurchase",
      }),
      onAddpurchase(formName){
        this.$refs[formName].validate(valid => {
          if (valid) {
            let purchase=this.newpurchase;
            purchase.purchaseId="cat" + new Date().valueOf();
            purchase.createDate=new Date();
            purchase.status="Y";
            purchase.createBy=this.userInfo.loginName;
            this.SavePurchase(purchase)
              .then(res => {
                if (res.resultStatus == 1) {
                  this.purchaseAddDialog = false;
                  this.PushPurchaseList(res.data);
                  this.$message({
                    showClose: true,
                    message: "创建成功",
                    type: "success"
                  });
                } else {
                  this.$message({
                    message: res.message,
                    type: "warning"
                  });
                }
              })
              .catch(err => {
                let alert = err.message ? err.message : err;
                this.$messgae.error(alert);
              });
          }
          else{
            this.$message.warning("请填写必要信息");
            return false;
          }
        });
      },
      resetForm(formName) {
      if (this.$refs[formName]) {
        this.$refs[formName].resetFields();
      }
      this.purchaseAddDialog = true;
      },

    handleEdit(index,row){
      this.editIndex=index;
      let cat={purchaseId:row.purchaseId,index:index}
      this.QueryPurchaseObj(cat)
        .then(res=>{
          if (res.resultStatus == 1) {
            this.editpurchase=res.data;
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
    handleDelete(index, row) {
      let cat={id:row.id,index:index}
       this.$messageBox.confirm('确认要删除？',"提示",{
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
          .then(() => {
            // this.Disablepurchase(cat)//
            this.DeletePurchase(cat)
          })
          .catch(e=>e);
      },
      onEditpurchase(formName){
        this.$refs[formName].validate(valid=>{
          if(valid){
            this.SavePurchase(this.editpurchase)
            .then(res=>{
              if(res.resultStatus==1){
                this.purchaseList.splice(this.editIndex,1,res.data);
                  this.editIndex="";
                // this.GetPurchaseList();
                this.purchaseEditDialog=false;
                this.$message({
                  showClose:true,
                  message:"操作成功",
                  type:"success"
                });
              }
              else{
                this.$message.error(res.message);
              }
            })
            .catch(err=>{
                let alert = err.message ? err.message : err;
                this.$message.error(alert);
            });
          }
          else{
            this.$message.warning("请填写准确信息");
            return false;
          }
        })
      }

    },
    beforeMount() {
      let params = {
        userId: this.userInfo.id
      };
      this.GetPurchaseList(params);
    },
};
</script>
<style lang="less">
.purchase-container {
  display: flex;
  flex-direction: column;
  width: 98%;
  padding: 15px;
}
.purchase-header {
  padding-bottom: 10px;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
}
.purchase-fliter-input {
  width: 400px;
  float: left;
}

</style>