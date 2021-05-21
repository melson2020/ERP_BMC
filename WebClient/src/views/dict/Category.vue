<template>
  <div class="category-container">
    <div class="category-header">
    <el-input
      class="category-fliter-input"
      size="small"
      placeholder="搜索产品类别"
      v-model="searchContent"
      suffix-icon="el-icon-search"
    ></el-input>
    <el-button type="primary" size="small" @click="resetForm('categoryAddForm')">新建类别</el-button>
    </div>

    <div class="category-content">
      <el-table :data="categoryListPageShow" 
          border="" stripe           
          :header-row-style="{height:'40px' ,'align':'center'}"
          :row-style="{height:'40px'}"
          style="width: 100%">
        <el-table-column prop="categoryId" label="类别代码" width="140px"></el-table-column>
        <el-table-column prop="name" label="类别名称" width="220px"> </el-table-column>
        <el-table-column prop="description" label="备注信息"> </el-table-column>
        <el-table-column prop="" label="操作" width="100px">
          <template slot-scope="scope">
            <el-tooltip effect="light" content="修改类别信息" placement="top">
              <el-button size="mini" @click="handleEdit(scope.$index,scope.row)" plain circle type="primary" icon="el-icon-edit"/>
            </el-tooltip>
            <el-tooltip effect="light" content="删除类别信息" placement="top">
              <el-button size="mini" @click="handleDelete(scope.$index, scope.row)" plain circle type="danger" icon="el-icon-delete"/>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>

    </div>

    <div class="category-content-footer"></div>

    <el-dialog title="新建类别" :visible.sync="categoryAddDialog" :close-on-click-modal="false" width="1024px">
      <el-form status-icon :model="newcategory" :rules="rules" ref="categoryAddForm" label-width="100px">
          <el-row>
            <el-form-item label="类别名称" prop="name">
              <el-input v-model="newcategory.name" autocomplete="off" ></el-input>
            </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="备注信息" prop="description">
            <el-input
              v-model="newcategory.description"
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
            <el-button type="primary" @click="onAddcategory('categoryAddForm')" :loading="loading">保存</el-button>
            <el-button @click="categoryAddDialog = false" v-if="!loading">取 消</el-button>
          </el-form-item>
        </el-row>
      </el-form>
    </el-dialog>

    <el-dialog title="编辑类别" :visible.sync="categoryEditDialog" :close-on-click-modal="false" width="1024px">
      <el-form status-icon :model="editcategory" :rules="rules" ref="categoryEditForm" label-width="100px">
          <el-row>
            <el-form-item label="类别名称" prop="name">
              <el-input v-model="editcategory.name" autocomplete="off" ></el-input>
            </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="备注信息" prop="description">
            <el-input
              v-model="editcategory.description"
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
            <el-button type="primary" @click="onEditcategory('categoryEditForm')" :loading="loading">保存</el-button>
            <el-button @click="categoryEditDialog = false" v-if="!loading">取 消</el-button>
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
        categoryEditDialog: false,
        categoryAddDialog: false,
        searchContent: "",
        loading: false,
        editIndex:'',
        newcategory:{
          id:'',
          categoryId:'',
          name:'',
          description:'',
          createBy:'',
          createDate:''
        },
        editcategory:{
          id:'',
          categoryId:'',
          name:'',
          description:'',
          createBy:'',
          createDate:''
        },
        rules: {
        name: [
          { required: true, message: "请输入类别名称", trigger: "blur" }
        ],
        },
      }

    },
    computed: {
      ...mapGetters(["categoryList"]),
      categoryListPageShow(){
        return this.categoryList.filter((item)=>{
          let key=
          item.categoryId +
          item.name +
          item.description ;
          let index = key.toUpperCase().indexOf(this.searchContent.toUpperCase());
          return index != -1;
        });
      }
    },
    methods:{
      ...mapActions({
      GetCategoryList:"GetCategoryList",
      SaveCategory:"SaveCategory",
      PushCategoryList:"PushCategoryList",
      QueryCategoryObj:"QueryCategoryObj",
      DisableCategory:"DisableCategory",
      DeleteCategory:"DeleteCategory"
      }),
      onAddcategory(formName){
        this.$refs[formName].validate(valid => {
          if (valid) {
            let category=this.newcategory;
            category.categoryId="cat" + new Date().valueOf();
            category.createDate=new Date();
            category.status="Y";
            category.createBy=""; 
            this.SaveCategory(category)
              .then(res => {
                if (res.resultStatus == 1) {
                  this.categoryAddDialog = false;
                  this.PushCategoryList(res.data);
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
      this.categoryAddDialog = true;
      },

    handleEdit(index,row){
      this.editIndex=index;
      let cat={categoryId:row.categoryId,index:index}
      this.QueryCategoryObj(cat)
        .then(res=>{
          if (res.resultStatus == 1) {
            this.editcategory=res.data;
            this.categoryEditDialog = true;
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
            // this.DisableCategory(cat)//
            this.DeleteCategory(cat)
          })
          .catch(e=>e);
      },
      onEditcategory(formName){
        this.$refs[formName].validate(valid=>{
          if(valid){
            this.SaveCategory(this.editcategory)
            .then(res=>{
              if(res.resultStatus==1){
                this.categoryList.splice(this.editIndex,1,res.data);
                  this.editIndex="";
                // this.GetCategoryList();
                this.categoryEditDialog=false;
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
      this.GetCategoryList();
    },
};
</script>
<style lang="less">
.category-container {
  display: flex;
  flex-direction: column;
  width: 98%;
  padding: 15px;
}
.category-header {
  padding-bottom: 10px;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
}
.category-fliter-input {
  width: 400px;
  float: left;
}

</style>