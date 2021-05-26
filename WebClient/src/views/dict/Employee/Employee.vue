<template>
  <div class="employee-container">
    <div class="employee-header">
      <el-input
        class="employee-fliter-input"
        size="small"
        placeholder="搜索用户"
        v-model="searchContent"
        suffix-icon="el-icon-search"
      ></el-input>
      <el-button type="primary" size="small" @click="resetForm('employeeAddForm')">新建用户</el-button>
    </div>


    <div class="employee-content">
      <el-table :data="employeeListPageShow" 
          border="" stripe           
          :header-row-style="{height:'40px' ,'align':'center'}"
          :row-style="{height:'40px'}"
          style="width: 100%">
        <el-table-column prop="userName" label="用户名称" width="200px"></el-table-column>
        <el-table-column prop="department" label="部门名称" width="200px"></el-table-column>
        <el-table-column prop="loginName" label="登录名" width="180px"></el-table-column>
        <el-table-column prop="createDate" label="创建日期" width="140px"> 
          <template slot-scope="scope">
            <span>{{getFullTime(scope.row.createDate) }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="description" label="描述"> </el-table-column>
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


    <div class="employee-content-footer"></div>

    <el-dialog title="新建用户" :visible.sync="employeeAddDialog" :close-on-click-modal="false" width="1024px">
      <el-form status-icon :model="newemployee" :rules="rules" ref="employeeAddForm" label-width="100px">
          <el-row>
            <el-col :span="8">
              <el-form-item label="用户名称" prop="userName">
                <el-input v-model="newemployee.userName" autocomplete="off" ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="登录账号" prop="loginName">
                <el-input v-model="newemployee.loginName" autocomplete="off" ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="部门选择" prop="department">
                <el-select
                  filterable
                  v-model="newemployee.department"
                  size="small"
                  placeholder="选择部门"
                  @change="addDepartmentChanged($event)">
                  <el-option
                    v-for="vo in departmentList"
                    :key="vo.id"
                    :label="vo.departmentName"
                    :value="vo"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
        </el-row>
        <el-row>
          <el-form-item label="备注信息" prop="description">
            <el-input
              v-model="newemployee.description"
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
          <el-form-item label="角色列表">
          <el-table
            :data="newemployee.userRoleList"
            border
            style="width: 100%"
            size="mini"
            @cell-click="newCellClick"
            :show-header="false"
          >
            <el-table-column prop="roleName" label="角色:" width="270px" >
              <template slot-scope="scope" >
                <div class="roleinformation" >
                  <el-form-item label="角色：" prop="roleName" label-width="65px" >
                    <el-select
                      filterable
                      v-model="scope.row.roleName"
                      v-if="scope.row.seen"
                      size="mini"
                      placeholder="选择物料"
                      @change="addRoleChanged($event,scope.row)">
                      <el-option
                        v-for="item in roleList"
                        :key="item.id"
                        :label="item.roleName"
                        :value="item"
                      >
                      </el-option>
                    </el-select>
                    <span v-else>{{ scope.row.roleName }}</span>
                  </el-form-item>
                </div>
              </template>
            </el-table-column>

            <el-table-column prop="description" label="描述:" >
              <template slot-scope="scope" >
                <div class="employeeInformation">
                  <el-form-item label="描述:" prop="description" label-width="65px" >
                    <el-input  
                      v-model="scope.row.description"
                      v-if="scope.row.seen"
                      size="mini"
                      style="width:424px"
                      disabled
                    ></el-input>
                    <span v-else>{{ scope.row.description }}</span>
                  </el-form-item>
                </div>
              </template>
            </el-table-column>
            <el-table-column width="90px" >>
              <template slot-scope="scope">
                <div class="employeeOperation">

                  <el-tooltip effect="light" content="确定" placement="top">
                    <el-button size="mini" :disabled="!scope.row.isEditable" circle @click.prevent.stop="newSaveRole(scope.$index, scope.row)" icon="el-icon-check" type="primary" ></el-button>
                  </el-tooltip>
                  <el-tooltip effect="light" content="删除" placement="top">
                    <el-button size="mini" @click.prevent.stop="newDeleteRole(scope.$index, scope.row)" plain circle type="danger" icon="el-icon-delete"/>
                  </el-tooltip>
                </div>
              </template>
            </el-table-column>

          </el-table>
          <el-button
            icon="el-icon-plus"
            plain
            class="add-role"
            @click="addrole"
            >添加角色</el-button
          >
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item>
            <el-button type="primary" @click="onAddemployee('employeeAddForm')" :loading="loading">保存</el-button>
            <el-button @click="employeeAddDialog = false" v-if="!loading">取 消</el-button>
          </el-form-item>
        </el-row>
      </el-form>
    </el-dialog>

    <el-dialog title="编辑用户" :visible.sync="employeeEditDialog" :close-on-click-modal="false" width="1024px">
      <el-form status-icon :model="editemployee" :rules="rules" ref="employeeEditForm" label-width="100px">
          <el-row>
            <el-col :span="8">
              <el-form-item label="用户名称" prop="userName">
                <el-input v-model="editemployee.userName" autocomplete="off" ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="登录账号" prop="loginName">
                <el-input v-model="editemployee.loginName" autocomplete="off" disabled></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="部门选择" prop="department">
                <el-select
                  filterable
                  v-model="editemployee.department"
                  size="small"
                  placeholder="选择部门"
                  @change="editDepartmentChanged($event)">
                  <el-option
                    v-for="vo in departmentList"
                    :key="vo.id"
                    :label="vo.departmentName"
                    :value="vo"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
        </el-row>
        <el-row>
          <el-form-item label="备注信息" prop="description">
            <el-input
              v-model="editemployee.description"
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
          <el-form-item label="角色列表">
          <el-table
            :data="editemployee.userRoleList"
            border
            style="width: 100%"
            size="mini"
            @cell-click="editCellClick"
            :show-header="false"
          >
            <el-table-column prop="roleName" label="角色:" width="270px" >
              <template slot-scope="scope" >
                <div class="roleinformation" >
                  <el-form-item label="角色：" prop="roleName" label-width="65px" >
                    <el-select
                      filterable
                      v-model="scope.row.roleName"
                      v-if="scope.row.seen"
                      size="mini"
                      placeholder="选择物料"
                      @change="editRoleChanged($event,scope.row)">
                      <el-option
                        v-for="item in roleList"
                        :key="item.id"
                        :label="item.roleName"
                        :value="item"
                      >
                      </el-option>
                    </el-select>
                    <span v-else>{{ scope.row.roleName }}</span>
                  </el-form-item>
                </div>
              </template>
            </el-table-column>

            <el-table-column prop="description" label="描述:" >
              <template slot-scope="scope" >
                <div class="employeeInformation">
                  <el-form-item label="描述:" prop="description" label-width="65px" >
                    <el-input  
                      v-model="scope.row.description"
                      v-if="scope.row.seen"
                      size="mini"
                      style="width:424px"
                      disabled
                    ></el-input>
                    <span v-else>{{ scope.row.description }}</span>
                  </el-form-item>
                </div>
              </template>
            </el-table-column>
            <el-table-column width="90px" >>
              <template slot-scope="scope">
                <div class="employeeOperation">

                  <el-tooltip effect="light" content="确定" placement="top">
                    <el-button size="mini" circle @click.prevent.stop="editSaveRole(scope.$index, scope.row)" icon="el-icon-check" type="primary" ></el-button>
                  </el-tooltip>
                  <el-tooltip effect="light" content="删除" placement="top">
                    <el-button size="mini" @click.prevent.stop="editDeleteRole(scope.$index, scope.row)" plain circle type="danger" icon="el-icon-delete"/>
                  </el-tooltip>
                </div>
              </template>
            </el-table-column>

          </el-table>
          <el-button
            icon="el-icon-plus"
            plain
            class="add-role"
            @click="editrole"
            >添加角色</el-button
          >
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item>
            <el-button type="primary" @click="onEditemployee('employeeEditForm')" :loading="loading">保存</el-button>
            <el-button @click="employeeEditDialog = false" v-if="!loading">取 消</el-button>
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
      employeeEditDialog: false,
      employeeAddDialog: false,
      searchContent: "",
      loading: false,
      editIndex:'',
      newemployee: {
          id: '',
          userId: '',
          userName:'',
          departmentId: '',
          userType: '',
          subscriptionId:'',
          companyCode:'',
          loginName:'',
          password:'',
          description: '',
          createDate:'',
          createBy: '',
          status:'',
          userRoleList:[],
          department: '',
      },
      editemployee: {
          id: '',
          userId: '',
          userName:'',
          departmentId: '',
          userType: '',
          subscriptionId:'',
          companyCode:'',
          loginName:'',
          password:'',
          description: '',
          createDate:'',
          createBy: '',
          status:'',
          userRoleList:[],
          department: '',
      },
      rules: {
        userName: [
          { required: true, message: "请输入用户名称", trigger: "blur" }
        ],
        loginName: [
          { required: true, message: "请输入登录名称", trigger: "blur" }
        ],
        departmentId: [
          { required: true, message: "请选择用户部门", trigger: "blur" }
        ],
      },
    }
  },
  methods: {
    ...mapActions({
      GetemployeeList: "GetemployeeList",
      Saveemployee: "Saveemployee",
      QueryemployeeObj:"QueryemployeeObj",
      PushemployeeList:"PushemployeeList",
      UpdateemployeeStatus:"UpdateemployeeStatus",
      GetRoleList:"GetRoleList",
      GetDepartmentList: "GetDepartmentList",
    }),
    newSaveRole(index,row){
      if(row.roleId===""|| row.roleName===""||row.description==="")
      {
        this.$message.warning("请正确填写信息");
        return false;
      }
      else
      {
        row.seen=false;
        row.notSavedFlag=false;
      }
    },
    editSaveRole(index,row){
      if(row.roleId===""|| row.roleName===""||row.description==="")
      {
        this.$message.warning("请正确填写信息");
        return false;
      }
      else
      {
        row.seen=false;
        row.notSavedFlag=false;
      }
    },
    newDeleteRole(index,row){
      row.seen=false;
      row.notSavedFlag=false;
      this.$messageBox.confirm('确认要删除？',"提示",{
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
        .then(() => {
            this.newemployee.userRoleList.splice(index,1)
        })
        .catch(e=>e);
    },
    editDeleteRole(index,row){
      row.seen=false;
      row.notSavedFlag=false;
      this.$messageBox.confirm('确认要删除？',"提示",{
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
        .then(() => {
            this.editemployee.userRoleList.splice(index,1)
        })
        .catch(e=>e);
    },
    addRoleChanged(event,row){
      if(!this.addCheckRepeated(event))
      {
        row.roleId=event.roleId;
        row.roleName=event.roleName;
        row.description=event.description;
      }
      else{
        row.roleId="";
        row.description="";
        row.roleName="";
        event=null;
      }
    },
    editRoleChanged(event,row){
      if(!this.editCheckRepeated(event))
      {
        row.roleId=event.roleId;
        row.roleName=event.roleName;
        row.description=event.description;
      }
      else{
        row.roleId="";
        row.description="";
        row.roleName="";
        event=null;
      }
    },
    addDepartmentChanged(event){
      this.newemployee.department=event.departmentName;
      this.newemployee.departmentId=event.id;
    },
    editDepartmentChanged(event){
      this.editemployee.department=event.departmentName;
      this.editemployee.departmentId=event.id;
    },
    addCheckRepeated(event){
      var list= this.newemployee.userRoleList.filter((item)=>{return item.roleId==event.roleId
      })
      if(list.length>0){
        this.$message.warning('角色重复！')
        return true;
      }
    },
    editCheckRepeated(event){
      var list= this.editemployee.userRoleList.filter((item)=>{return item.roleId==event.roleId
      })
      if(list.length>0){
        this.$message.warning('角色重复！')
        return true;
      }
    },
    addrole() {
      if(!this.newCheckSavedFlag())
      {
        if(!this.newCheckEidtable())
        {
          this.newemployee.userRoleList.push({ id: "", roleId:"", roleName: "",description:"",seen: false, isEditable:true ,notSavedFlag:true})
        }
      }
    },
    editrole(){
      if(!this.editCheckSavedFlag())
      {
        if(!this.editCheckEidtable())
        {
          this.editemployee.userRoleList.push({ id: "", roleId:"", roleName: "",description:"",seen: false, isEditable:true ,notSavedFlag:true})
        }
      }
    },
    newCheckEidtable(){
      var list= this.newemployee.userRoleList.filter((item)=>{return item.seen
      })
      if(list.length>0){
        this.$message.warning('请先完成联系人编辑')
        return true;
      }
    },
    newCheckSavedFlag(){
      var list= this.newemployee.userRoleList.filter((item)=>{return item.notSavedFlag
      })
      if(list.length>0){
        this.$message.warning('请先保存联系人数据')
        return true;
      }
    },
    editCheckSavedFlag(){
      var list= this.editemployee.userRoleList.filter((item)=>{return item.notSavedFlag
      })
      if(list.length>0){
        this.$message.warning('请先完成编辑')
        return true;
      }
    },
    editCheckEidtable(){
      var list= this.editemployee.userRoleList.filter((item)=>{return item.seen
      })
      if(list.length>0){
        this.$message.warning('请先完成编辑')
        return true;
      }
    },
    newCellClick(row) {
       row.seen=true;
    },
    editCellClick(row){
      if(!row.seen)
      {
        if(!this.editCheckEidtable())
        {
          row.seen=true;
        }
      }
    },
    getFullTime(time) {
    return new Date(time).format("yyyy-MM-dd");
    },
    resetForm(formName) {
      if (this.$refs[formName]) {
        this.$refs[formName].resetFields();
      }
      this.employeeAddDialog = true;
    },
    onAddemployee(formName){
      this.$refs[formName].validate(valid => {
      if (valid) {
        let employee=this.newemployee
        employee.createDate=new Date();
        employee.status="Y";
        employee.createBy=this.userInfo.loginName;     //TODO： 待添加创建人
        this.Saveemployee(employee)
          .then(res => {
            if (res.resultStatus == 1) {
              this.employeeAddDialog = false;
              this.PushemployeeList(res.data);
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
    onEditemployee(formName){
    if(!this.editCheckSavedFlag())
      {
        if(!this.editCheckEidtable())
        {
        this.$refs[formName].validate(valid=>{
        if(valid){
          this.Saveemployee(this.editemployee)
          .then(res=>{
            if(res.resultStatus==1){
              this.employeeList.splice(this.editIndex,1,res.data);
              this.editIndex="";
              this.employeeEditDialog=false;
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
      }
    },
    handleEdit(index,row){
      this.editIndex=index;
      let dep={id:row.id,index:index}
      this.QueryemployeeObj(dep)
        .then(res=>{
          if (res.resultStatus == 1) {
            this.editemployee=res.data;
            let userRoleList = [];
              for (let index = 0; index < res.data.userRoleList.length; index++) {
                  const element = res.data.userRoleList[index];
                  let userRole = { id:element.id,roleId:element.roleId,roleName:element.roleName,description:element.description,seen: false,notSavedFlag:false };
                  userRoleList.push(userRole);
              }
            this.editemployee.userRoleList=userRoleList;
            this.employeeEditDialog = true;
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
            this.UpdateemployeeStatus(dep)
          })
          .catch(e=>e);
      },

  },
  computed:{
    ...mapGetters(["employeeList","departmentList","roleList","userInfo"]),
    employeeListPageShow(){
      return this.employeeList.filter((item)=>{
        let key=
        item.userName +
        item.department + 
        item.loginName;
        let index = key.toUpperCase().indexOf(this.searchContent.toUpperCase());
        return index != -1;
      });
    }
  },
    beforeMount() {
      let params = {
        userType: this.userInfo.userType
      };
      this.GetemployeeList(params);
      this.GetRoleList();
  },
};
</script>
<style>
.employee-container {
  display: flex;
  flex-direction: column;
}
.employee-header {
  padding-bottom: 10px;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
}
.employee-fliter-input {
  width: 400px;
  float: left;
}
.employeeInformation{
  display: flex;
  flex-direction: row;
  width: auto;
  justify-content: space-between;
}
.add-role {
  margin-top: 5px;
  width: 100%;
  border: #dcdfe6 dashed 1px !important;
}
.roleinformation{
  display: flex;
  flex-direction: row;
  width: auto;
  justify-content: space-between;
}
</style>