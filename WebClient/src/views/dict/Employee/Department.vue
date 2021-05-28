<template>
  <div class="department-container">
    <div class="department-header">
      <el-input
        class="department-fliter-input"
        size="small"
        placeholder="搜索部门"
        v-model="searchContent"
        suffix-icon="el-icon-search"
      ></el-input>
      <el-button type="primary" size="small" @click="resetForm('departmentAddForm')">新建部门</el-button>
    </div>


    <div class="department-content">
      <el-table :data="departmentListPageShow" 
          border="" stripe           
          :header-row-style="{height:'40px' ,'align':'center'}"
          :row-style="{height:'40px'}"
          style="width: 100%">
        <el-table-column prop="departmentCode" label="部门代码" width="180px"></el-table-column>
        <el-table-column prop="departmentName" label="部门名称" width="340px"> </el-table-column>
        <el-table-column prop="description" label="描述"> </el-table-column>
        <el-table-column prop="" label="操作" width="100px">
          <template slot-scope="scope">
            <el-tooltip effect="light" content="修改" placement="top">
              <el-button size="mini" @click="handleEdit(scope.$index,scope.row)" plain circle type="primary" icon="el-icon-edit"/>
            </el-tooltip>
            <el-tooltip effect="light" v-if="scope.row.status=='Y'" content="停用" placement="top">
              <el-button size="mini" @click.prevent.stop="UpdateStatus(scope.$index, scope.row,true)" plain circle type="danger" icon="el-icon-close"/>
            </el-tooltip>
            <el-tooltip effect="light" v-else content="启用" placement="top">
              <el-button size="mini" @click.prevent.stop="UpdateStatus(scope.$index, scope.row,false)" plain circle type="primary" icon="el-icon-check"/>
            </el-tooltip>

          </template>
        </el-table-column>
      </el-table>
    </div>


    <div class="department-content-footer"></div>

    <el-dialog title="新建部门" :visible.sync="departmentAddDialog" :close-on-click-modal="false" width="1024px">
      <el-form status-icon :model="newDepartment" :rules="rules" ref="departmentAddForm" label-width="100px">
          <el-row>
            <el-form-item label="部门名称" prop="departmentName">
              <el-input v-model="newDepartment.departmentName" autocomplete="off" ></el-input>
            </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="备注信息" prop="description">
            <el-input
              v-model="newDepartment.description"
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
            <el-button type="primary" @click="onAddDepartment('departmentAddForm')" :loading="loading">保存</el-button>
            <el-button @click="departmentAddDialog = false" v-if="!loading">取 消</el-button>
          </el-form-item>
        </el-row>
      </el-form>
    </el-dialog>

    <el-dialog :title=departmentCode :visible.sync="departmentEditDialog" :close-on-click-modal="false" width="1024px">
      <el-form status-icon :model="editDepartment" :rules="rules" ref="departmentEditForm" label-width="100px">
          <el-row>
            <el-form-item label="部门名称" prop="departmentName">
              <el-input v-model="editDepartment.departmentName" autocomplete="off" ></el-input>
            </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="备注信息" prop="description">
            <el-input
              v-model="editDepartment.description"
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
          <el-form-item label="成员列表">
            <el-table
              :data="editDepartment.userList"
              size="mini"
              border="" stripe           
              :header-row-style="{height:'40px' ,'align':'center'}"
              :row-style="{height:'40px'}"
              style="width: 100%">
              <el-table-column prop="userName" label="用户名称" width="200px"></el-table-column>
              <el-table-column prop="loginName" label="登录名" width="180px"></el-table-column>
              <el-table-column prop="createDate" label="创建日期" width="140px"> 
                <template slot-scope="scope">
                  <span>{{getFullTime(scope.row.createDate) }}</span>
                </template>
              </el-table-column>
              <el-table-column prop="description" label="描述"></el-table-column>
            </el-table>
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item>
            <el-button type="primary" @click="onEditDepartment('departmentEditForm')" :loading="loading">保存</el-button>
            <el-button @click="departmentEditDialog = false" v-if="!loading">取 消</el-button>
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
  data() {
    return{
      departmentCode:'',
      departmentEditDialog: false,
      departmentAddDialog: false,
      searchContent: "",
      loading: false,
      editIndex:'',
      newDepartment: {
          id: '',
          companyCode: '',
          companyName:'',
          departmentCode: '',
          departmentName: '',
          description: '',
          createDate:'',
          createBy: '',
      },
      editDepartment: {
          id: '',
          companyCode: '',
          companyName:'',
          departmentCode: '',
          departmentName: '',
          description: '',
          createDate:'',
          createBy: '',          
          userList:[],
      },
      rules: {
        departmentName: [
          { required: true, message: "请输入部门名称", trigger: "blur" }
        ],
      },
    }
  },
  methods: {
    ...mapActions({
      GetDepartmentList: "GetDepartmentList",
      SaveDepartment: "SaveDepartment",
      QueryDepartmentObj:"QueryDepartmentObj",
      UpdateDepartment:"UpdateDepartment",
      PushDepartmentList:"PushDepartmentList",
      UpdateDepartmentStatus:"UpdateDepartmentStatus"
    }),
    resetForm(formName) {
      if (this.$refs[formName]) {
        this.$refs[formName].resetFields();
      }
      this.departmentAddDialog = true;
    },
    getFullTime(time) {
    return new Date(time).format("yyyy-MM-dd");
    },
    onAddDepartment(formName){
      this.$refs[formName].validate(valid => {
      if (valid) {
        let department=this.newDepartment
        department.createDate=new Date();
        department.status="Y";
        department.createBy=this.userInfo.loginName;     //TODO： 待添加创建人
        this.SaveDepartment(department)
          .then(res => {
            if (res.resultStatus == 1) {
              this.departmentAddDialog = false;
              this.PushDepartmentList(res.data);
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
    onEditDepartment(formName){
      this.$refs[formName].validate(valid=>{
        if(valid){
          this.SaveDepartment(this.editDepartment)
          .then(res=>{
            if(res.resultStatus==1){
              this.departmentList.splice(this.editIndex,1,res.data);
              this.editIndex="";
              this.departmentEditDialog=false;
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

    },
    handleEdit(index,row){
      this.editIndex=index;
      let dep={id:row.id,index:index}
      this.QueryDepartmentObj(dep)
        .then(res=>{
          if (res.resultStatus == 1) {
            this.editDepartment=res.data;
            this.departmentCode=this.editDepartment.departmentCode;
            this.departmentEditDialog = true;
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
    UpdateStatus(index, row,disable) {
       this.$messageBox.confirm('确认要操作？',"提示",{
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
          .then(() => {
            row.status=disable?"N":"Y"
            let dep={id:row.id,index:index,status:row.status}
            this.UpdateDepartmentStatus(dep)
          })
          .catch(e=>e);
      },

  },
  computed:{
    ...mapGetters(["departmentList","userInfo"]),
    departmentListPageShow(){
      return this.departmentList.filter((item)=>{
        let key=
        item.departmentName +
        item.departmentCode + 
        item.description;
        let index = key.toUpperCase().indexOf(this.searchContent.toUpperCase());
        return index != -1;
      });
    }
  },
    beforeMount() {
    this.GetDepartmentList();
  },
};
</script>
<style>
.department-container {
  display: flex;
  flex-direction: column;
}
.department-header {
  padding-bottom: 10px;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
}
.department-fliter-input {
  width: 400px;
  float: left;
}

</style>