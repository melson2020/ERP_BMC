<template>
  <div class="supply-container">
    <div class="supply-header">
    <el-input
      class="supply-fliter-input"
      size="small"
      placeholder="搜索供应商"
      v-model="searchContent"
      suffix-icon="el-icon-search"
    ></el-input>
    <el-button type="primary" size="small" @click="resetForm('supplyAddForm')">新建供应商</el-button>
    </div>

    <div class="supply-content">
      <el-table :data="supplyListPageShow" 
          border="" stripe           
          :header-row-style="{height:'40px' ,'align':'center'}"
          :row-style="{height:'40px'}"
          style="width: 100%">
        <el-table-column prop="name" label="供应商"></el-table-column>
        <el-table-column prop="contact" label="联系人" width="220px"> </el-table-column>
                <el-table-column prop="phone" label="电话" width="220px"> </el-table-column>
        <el-table-column prop="address" label="地址"> </el-table-column>
        <el-table-column prop="status" label="是否可用" width="120px"> </el-table-column>
        <el-table-column prop="" label="操作" width="100px">
          <template slot-scope="scope">
            <el-tooltip effect="light" content="修改供应商信息" placement="top">
              <el-button size="mini" @click="handleEdit(scope.$index,scope.row)" plain circle type="primary" icon="el-icon-edit"/>
            </el-tooltip>
            <el-tooltip effect="light" content="删除供应商信息" placement="top">
              <el-button size="mini" @click="handleDelete(scope.$index, scope.row)" plain circle type="danger" icon="el-icon-delete"/>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>

    </div>

    <div class="supply-content-footer"></div>

    <el-dialog title="新建供应商" :visible.sync="supplyAddDialog" :close-on-click-modal="false" width="1024px">
      <el-form status-icon :model="newsupply" :rules="rules" ref="supplyAddForm" label-width="100px">
          <el-row>
            <el-form-item label="供应商名称" prop="name">
              <el-input v-model="newsupply.name" autocomplete="off" ></el-input>
            </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="备注信息" prop="description">
            <el-input
              v-model="newsupply.description"
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
            <el-button type="primary" @click="onAddsupply('supplyAddForm')" :loading="loading">保存</el-button>
            <el-button @click="supplyAddDialog = false" v-if="!loading">取 消</el-button>
          </el-form-item>
        </el-row>
      </el-form>
    </el-dialog>

    <!-- <el-dialog title="编辑供应商" :visible.sync="supplyEditDialog" :close-on-click-modal="false" width="1024px">
      <el-form status-icon :model="editsupply" :rules="rules" ref="supplyEditForm" label-width="100px">
          <el-row>
            <el-form-item label="供应商名称" prop="name">
              <el-input v-model="editsupply.name" autocomplete="off" ></el-input>
            </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="备注信息" prop="description">
            <el-input
              v-model="editsupply.description"
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
            <el-button type="primary" @click="onEditsupply('supplyEditForm')" :loading="loading">保存</el-button>
            <el-button @click="supplyEditDialog = false" v-if="!loading">取 消</el-button>
          </el-form-item>
        </el-row>
      </el-form>
    </el-dialog> -->



    </div>
</template>
<script>
import { mapGetters } from "vuex";
import { mapActions } from "vuex";
export default {
    data(){
      return{
        supplyEditDialog: false,
        supplyAddDialog: false,
        searchContent: "",
        loading: false,
        editIndex:'',
        newsupply:{
          id:'',
          name:'',
          address:'',
          contact:'',
          phone:'',
          status:'',
          description:'',
          createBy:'',
          createDate:''
        },
        editsupply:{
          id:'',
          name:'',
          address:'',
          contact:'',
          phone:'',
          status:'',
          description:'',
          createBy:'',
          createDate:''
        },
        rules: {
        name: [
          { required: true, message: "请输入供应商名称", trigger: "blur" }
        ],
        },
      }

    },
    computed: {
      ...mapGetters(["supplyList"]),
      supplyListPageShow(){
        return this.supplyList.filter((item)=>{
          let key=
          item.name +
          item.address +
          item.contact +
          item.status +
          item.phone ;
          let index = key.toUpperCase().indexOf(this.searchContent.toUpperCase());
          return index != -1;
        });
      }
    },
    methods:{
      ...mapActions({
      GetSupplyList:"GetSupplyList",
      Savesupply:"Savesupply",
      PushsupplyList:"PushsupplyList",
      QuerysupplyObj:"QuerysupplyObj",
      Disablesupply:"Disablesupply",
      Deletesupply:"Deletesupply"
      }),
      onAddsupply(formName){
        this.$refs[formName].validate(valid => {
          if (valid) {
            let supply=this.newsupply;
            supply.supplyId="cat" + new Date().valueOf();
            supply.createDate=new Date();
            supply.status="Y";
            supply.createBy=""; 
            this.Savesupply(supply)
              .then(res => {
                if (res.resultStatus == 1) {
                  this.supplyAddDialog = false;
                  this.PushsupplyList(res.data);
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
      this.supplyAddDialog = true;
      },

    handleEdit(index,row){
      let cat={supplyId:row.supplyId,index:index}
      this.QuerysupplyObj(cat)
        .then(res=>{
          if (res.resultStatus == 1) {
            this.editsupply=res.data;
            this.supplyEditDialog = true;
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
            // this.Disablesupply(cat)//
            this.Deletesupply(cat)
          })
          .catch(e=>e);
      },
      onEditsupply(formName){
        this.$refs[formName].validate(valid=>{
          if(valid){
            this.Savesupply(this.editsupply)
            .then(res=>{
              if(res.resultStatus==1){
                this.GetsupplyList();
                this.supplyEditDialog=false;
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
      this.GetSupplyList();
    },
};
</script>
<style lang="less">
.supply-container {
  display: flex;
  flex-direction: column;
  width: 98%;
  padding: 10px;
}
.supply-header {
  padding-bottom: 10px;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
}
.supply-fliter-input {
  width: 400px;
  float: left;
}

</style>