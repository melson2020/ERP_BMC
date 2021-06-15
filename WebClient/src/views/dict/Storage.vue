<template>
  <div class="Storage-container">
    <div class="Storage-header">
    <el-input
      class="Storage-fliter-input"
      size="small"
      placeholder="搜索仓库"
      v-model="searchContent"
      suffix-icon="el-icon-search"
    ></el-input>
    <el-button type="primary" size="small" @click="resetForm('StorageAddForm')">新建仓库</el-button>
    </div>

    <div class="Storage-content">
      <el-table :data="StorageListPageShow" 
        border="" stripe
        size="small"
        :header-row-style="{ height: '40px' }"
        :row-style="{ height: '40px' }"
        :cell-style="{ padding: '2px', color: '#909399' }"
        :header-cell-style="{ background: '#909399', color: 'white' }">
        <el-table-column prop="storageCode" label="仓库代码" width="180px"></el-table-column>
        <el-table-column prop="name" label="仓库名称" width="220px"> </el-table-column>
        <el-table-column prop="location" label="仓库位置" width="220px"> </el-table-column>
        <el-table-column prop="description" label="备注信息"> </el-table-column>
        <el-table-column prop="" label="操作" width="100px">
          <template slot-scope="scope">
            <el-tooltip effect="light" content="修改" placement="top">
              <el-button size="mini" @click="handleEdit(scope.$index,scope.row)" plain circle type="primary" icon="el-icon-edit"/>
            </el-tooltip>
            <el-tooltip effect="light" content="删除" placement="top">
              <el-button size="mini" @click="handleDelete(scope.$index, scope.row)" plain circle type="danger" icon="el-icon-delete"/>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>

    </div>

    <div class="Storage-content-footer"></div>

    <el-dialog title="新建仓库" :visible.sync="StorageAddDialog" :close-on-click-modal="false" width="1024px">
      <el-form status-icon :model="newStorage" :rules="rules" ref="StorageAddForm" label-width="100px">
        <el-row>
            <el-form-item label="仓库名称" prop="name">
              <el-input v-model="newStorage.name" autocomplete="off" ></el-input>
            </el-form-item>
        </el-row>
        <el-row>
            <el-form-item label="仓库位置" prop="location">
              <el-input v-model="newStorage.location" autocomplete="off" ></el-input>
            </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="备注信息" prop="description">
            <el-input
              v-model="newStorage.description"
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
            <el-button type="primary" @click="onAddStorage('StorageAddForm')" :loading="loading">保存</el-button>
            <el-button @click="StorageAddDialog = false" v-if="!loading">取 消</el-button>
          </el-form-item>
        </el-row>
      </el-form>
    </el-dialog>

    <el-dialog title="编辑仓库" :visible.sync="StorageEditDialog" :close-on-click-modal="false" width="1024px">
      <el-form status-icon :model="editStorage" :rules="rules" ref="StorageEditForm" label-width="100px">
        <el-row>
            <el-form-item label="仓库名称" prop="name">
              <el-input v-model="editStorage.name" autocomplete="off" ></el-input>
            </el-form-item>
        </el-row>
        <el-row>
            <el-form-item label="仓库位置" prop="location">
              <el-input v-model="editStorage.location" autocomplete="off" ></el-input>
            </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="备注信息" prop="description">
            <el-input
              v-model="editStorage.description"
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
            <el-button type="primary" @click="onEditStorage('StorageEditForm')" :loading="loading">保存</el-button>
            <el-button @click="StorageEditDialog = false" v-if="!loading">取 消</el-button>
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
        StorageEditDialog: false,
        StorageAddDialog: false,
        searchContent: "",
        loading: false,
        editIndex:'',
        newStorage:{
          id:'',
          StorageCode:'',
          name:'',
          location:'',
          description:'',
          createBy:'',
          createDate:''
        },
        editStorage:{
          id:'',
          StorageCode:'',
          name:'',
          location:'',
          description:'',
          createBy:'',
          createDate:''
        },
        rules: {
        name: [
          { required: true, message: "请输入仓库名称", trigger: "blur" }
        ],
        },
      }

    },
    computed: {
      ...mapGetters(["storageList","userInfo"]),
      StorageListPageShow(){
        return this.storageList.filter((item)=>{
          let key=
          item.StorageCode +
          item.name +
          item.location +
          item.description ;
          let index = key.toUpperCase().indexOf(this.searchContent.toUpperCase());
          return index != -1;
        });
      }
    },
    methods:{
      ...mapActions({
      GetStorageList:"GetStorageList",
      SaveStorage:"SaveStorage",
      PushStorageList:"PushStorageList",
      QueryStorageObj:"QueryStorageObj",
      DeleteStorage:"DeleteStorage"
      }),
      onAddStorage(formName){
        this.$refs[formName].validate(valid => {
          if (valid) {
            let Storage=this.newStorage;
            Storage.storageCode="sc" + new Date().valueOf();
            Storage.createDate=new Date();
            Storage.createBy=this.userInfo.loginName;
            this.SaveStorage(Storage)
              .then(res => {
                if (res.resultStatus == 1) {
                  this.StorageAddDialog = false;
                  this.PushStorageList(res.data);
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
      this.StorageAddDialog = true;
      },

    handleEdit(index,row){
      this.editIndex=index;
      let cat={storageCode:row.storageCode,index:index}
      this.QueryStorageObj(cat)
        .then(res=>{
          if (res.resultStatus == 1) {
            this.editStorage=res.data;
            this.StorageEditDialog = true;
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
            // this.DisableStorage(cat)//
            this.DeleteStorage(cat)
          })
          .catch(e=>e);
      },
      onEditStorage(formName){
        this.$refs[formName].validate(valid=>{
          if(valid){
            this.SaveStorage(this.editStorage)
            .then(res=>{
              if(res.resultStatus==1){
                this.storageList.splice(this.editIndex,1,res.data);
                  this.editIndex="";
                // this.GetStorageList();
                this.StorageEditDialog=false;
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
      this.GetStorageList();
    },
};
</script>
<style lang="less">
.Storage-container {
  display: flex;
  flex-direction: column;
  width: 98%;
  padding: 15px;
}
.Storage-header {
  padding-bottom: 10px;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
}
.Storage-fliter-input {
  width: 400px;
  float: left;
}

</style>