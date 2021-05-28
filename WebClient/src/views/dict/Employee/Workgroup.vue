<template>
  <div class="workGroup-container">
    <div class="workGroup-header">
      <el-input
        class="workGroup-fliter-input"
        size="small"
        placeholder="搜索组别"
        v-model="searchContent"
        suffix-icon="el-icon-search"
      ></el-input>
      <el-button type="primary" size="small" @click="resetForm('workGroupAddForm')">新建工作组</el-button>
    </div>


    <div class="workGroup-content">
      <el-table :data="workGroupListPageShow" 
          border="" stripe           
          :header-row-style="{height:'40px' ,'align':'center'}"
          :row-style="{height:'40px'}"
          style="width: 100%">
        <el-table-column prop="workGroupCode" label="组别代码" width="180px"></el-table-column>
        <el-table-column prop="name" label="工作组名称" width="340px"> </el-table-column>
        <el-table-column prop="description" label="描述"> </el-table-column>
        <el-table-column prop="" label="操作" width="100px">
          <template slot-scope="scope">
            <el-tooltip effect="light" content="修改" placement="top">
              <el-button size="mini" @click="handleEdit(scope.$index,scope.row)" plain circle type="primary" icon="el-icon-edit"/>
            </el-tooltip>
            <el-tooltip effect="light" content="删除" placement="top">
              <el-button size="mini" @click.prevent.stop="handleDelete(scope.$index, scope.row)" plain circle type="danger" icon="el-icon-close"/>
            </el-tooltip>

          </template>
        </el-table-column>
      </el-table>
    </div>


    <div class="workGroup-content-footer"></div>

    <el-dialog title="新建组别" :visible.sync="workGroupAddDialog" :close-on-click-modal="false" width="1024px">
      <el-form status-icon :model="newworkGroup" :rules="rules" ref="workGroupAddForm" label-width="100px">
          <el-row>
            <el-form-item label="组别名称" prop="name">
              <el-input v-model="newworkGroup.name" autocomplete="off" ></el-input>
            </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="备注信息" prop="description">
            <el-input
              v-model="newworkGroup.description"
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
            <el-button type="primary" @click="onAddworkGroup('workGroupAddForm')" :loading="loading">保存</el-button>
            <el-button @click="workGroupAddDialog = false" v-if="!loading">取 消</el-button>
          </el-form-item>
        </el-row>
      </el-form>
    </el-dialog>

    <el-dialog :title=groupCode :visible.sync="workGroupEditDialog" :close-on-click-modal="false" width="1024px">
      <el-form status-icon :model="editworkGroup" :rules="rules" ref="workGroupEditForm" label-width="100px">
          <el-row>
            <el-form-item label="组别名称" prop="name">
              <el-input v-model="editworkGroup.name" autocomplete="off" ></el-input>
            </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="备注信息" prop="description">
            <el-input
              v-model="editworkGroup.description"
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
            <el-button
            icon="el-icon-plus"
            plain
            class="add-workgroup"
            @click="editWrokGroup"
            >添加成员</el-button
            >
            <el-table
              :data="editworkGroup.userGroups"
              size="mini"
              border="" stripe   
              @cell-click="editCellClick"        
              :header-row-style="{height:'40px' ,'align':'center'}"
              :row-style="{height:'40px'}"
              style="width: 100%">
              <el-table-column prop="userName" label="用户名称" width="200px">
                <template slot-scope="scope" >
                    <el-select
                      filterable
                      v-model="scope.row.userName"
                      v-if="scope.row.seen"
                      size="mini"
                      placeholder="选择员工"
                      @change="editUserChanged($event,scope.row)">
                      <el-option
                        v-for="item in employeeList"
                        :key="item.id"
                        :label="item.userName"
                        :value="item"
                      >
                      </el-option>
                    </el-select>
                    <span v-else>{{ scope.row.userName }}</span>
                </template>
              </el-table-column>
              <el-table-column prop="loginName" label="登录名" width="140px">
                <template slot-scope="scope" >
                  <div class="workGroupInformation">
                    <el-input  
                      v-model="scope.row.loginName"
                      v-if="scope.row.seen"
                      size="mini"
                      disabled
                    ></el-input>
                    <span v-else>{{ scope.row.loginName }}</span>
                  </div>
                </template>
              </el-table-column>
              <el-table-column prop="createDate" label="添加日期" width="100px"> 
                <template slot-scope="scope">
                  <span>{{getFullTime(scope.row.createDate) }}</span>
                </template>
              </el-table-column>
              <el-table-column prop="description" label="描述">
                <template slot-scope="scope" >
                  <div class="workGroupInformation">
                    <el-input  
                      v-model="scope.row.description"
                      v-if="scope.row.seen"
                      size="mini"
                      style="width:324px"
                    ></el-input>
                    <span v-else>{{ scope.row.description }}</span>
                  </div>
                </template>
              </el-table-column>
              <el-table-column width="90px" >>
                <template slot-scope="scope">
                  <div class="employeeOperation">

                    <el-tooltip effect="light" content="确定" placement="top">
                      <el-button size="mini" circle @click.prevent.stop="editSaveGroup(scope.$index, scope.row)" icon="el-icon-check" type="primary" ></el-button>
                    </el-tooltip>
                    <el-tooltip effect="light" content="删除" placement="top">
                      <el-button size="mini" @click.prevent.stop="editDeleteGroup(scope.$index, scope.row)" plain circle type="danger" icon="el-icon-delete"/>
                    </el-tooltip>
                  </div>
                </template>
              </el-table-column>
            </el-table>
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item>
            <el-button type="primary" @click="onEditworkGroup('workGroupEditForm')" :loading="loading">保存</el-button>
            <el-button @click="workGroupEditDialog = false" v-if="!loading">取 消</el-button>
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
      groupCode:'',
      workGroupEditDialog: false,
      workGroupAddDialog: false,
      searchContent: "",
      loading: false,
      editIndex:'',
      newworkGroup: {
          id: '',
          workGroupCode:'',
          name: '',
          description: '',
          status:'',
          createDate:'',
          createBy: '',
      },
      editworkGroup: {
          id: '',
          workGroupCode:'',
          name: '',
          description: '',
          status:'',
          createDate:'',
          createBy: '',      
          userList:[],
          userGroups:[],
      },
      rules: {
        name: [
          { required: true, message: "请输入工作组名称", trigger: "blur" }
        ],
      },
    }
  },
  methods: {
    ...mapActions({
      GetworkGroupList: "GetworkGroupList",
      SaveworkGroup: "SaveworkGroup",
      QueryworkGroupObj:"QueryworkGroupObj",
      PushworkGroupList:"PushworkGroupList",
      DeleteworkGroup:"DeleteworkGroup",
      SaveUserGroup: "SaveUserGroup",
      DeleteUserGroup: "DeleteUserGroup",
    }),
    resetForm(formName) {
      if (this.$refs[formName]) {
        this.$refs[formName].resetFields();
      }
      this.workGroupAddDialog = true;
    },
    editUserChanged(event,row){
      if(!this.editCheckRepeated(event))
      {
        row.userId=event.userId;
        row.workGroupId=this.editworkGroup.id;
        row.userName=event.userName;
        row.loginName=event.loginName;
      }
      else{
        row.userId="";
        row.workGroupId="";
        row.userName="";
        row.loginName="";
        row.description="";
        event=null;
      }
    },
    editCheckRepeated(event){
      var list= this.editworkGroup.userGroups.filter((item)=>{return item.userId==event.userId
      })
      if(list.length>0){
        this.$message.warning('用户重复！')
        return true;
      }
    },
    editSaveGroup(index,row){
      if(row.userId==="")
      {
        this.$message.warning("请正确填写信息");
        return false;
      }
      else
      {
        row.seen=false;
        row.notSavedFlag=false;
        this.SaveUserGroup(row)
      }
    },
    editDeleteGroup(index,row){
      row.seen=false;
      row.notSavedFlag=false;
      if(row.id!=="")
      {
      this.$messageBox.confirm('确认要删除？',"提示",{
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
        .then(() => {
          this.DeleteUserGroup(row)
          this.editworkGroup.userGroups.splice(index,1)
        })
        .catch(e=>e);
        }
        else
        {
          this.editworkGroup.userGroups.splice(index,1)
        }

    },
    editWrokGroup(){
      if(!this.editCheckSavedFlag())
      {
        if(!this.editCheckEidtable())
        {
          this.editworkGroup.userGroups.push({ id: "",workGroupId: "",userId: "",description: "",createDate: new Date(),userName: "",loginName: "",seen: false, isEditable:true ,notSavedFlag:true})
        }
      }
    },
    editCheckSavedFlag(){
      var list= this.editworkGroup.userGroups.filter((item)=>{return item.notSavedFlag
      })
      if(list.length>0){
        this.$message.warning('请先完成编辑')
        return true;
      }
    },
    editCheckEidtable(){
      var list= this.editworkGroup.userGroups.filter((item)=>{return item.seen
      })
      if(list.length>0){
        this.$message.warning('请先完成编辑')
        return true;
      }
    },
    getFullTime(time) {
    return new Date(time).format("yyyy-MM-dd");
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
    onAddworkGroup(formName){
      this.$refs[formName].validate(valid => {
      if (valid) {
        let workGroup=this.newworkGroup
        workGroup.createDate=new Date();
        workGroup.status="Y";
        workGroup.createBy=this.userInfo.loginName;     //TODO： 待添加创建人
        this.SaveworkGroup(workGroup)
          .then(res => {
            if (res.resultStatus == 1) {
              this.workGroupAddDialog = false;
              this.PushworkGroupList(res.data);
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
    onEditworkGroup(formName){
    if(!this.editCheckSavedFlag())
      {
        if(!this.editCheckEidtable())
        {
          this.$refs[formName].validate(valid=>{
        if(valid){
          this.SaveworkGroup(this.editworkGroup)
          .then(res=>{
            if(res.resultStatus==1){
              this.workGroupList.splice(this.editIndex,1,res.data);
              this.editIndex="";
              this.workGroupEditDialog=false;
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
      this.QueryworkGroupObj(dep)
        .then(res=>{
          if (res.resultStatus == 1) {
            this.editworkGroup=res.data;
            this.groupCode=this.editworkGroup.workGroupCode;

            let userGroups = [];
              for (let index = 0; index < res.data.userGroups.length; index++) {
                  const element = res.data.userGroups[index];
                  let user = { id:element.id,workGroupId:element.workGroupId,userId:element.userId,description:element.description,createDate:element.createDate,userName:element.userName,loginName:element.loginName,seen: false,notSavedFlag:false };
                  userGroups.push(user);
              }
            this.editworkGroup.userGroups=userGroups;

            this.workGroupEditDialog = true;
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
       this.$messageBox.confirm('确认要操作？',"提示",{
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
          .then(() => {
            // row.status=disable?"N":"Y"
            let dep={id:row.id,index:index,status:row.status}
            this.DeleteworkGroup(dep)
          })
          .catch(e=>e);
      },

  },
  computed:{
    ...mapGetters(["employeeList","workGroupList","userInfo"]),
    workGroupListPageShow(){
      return this.workGroupList.filter((item)=>{
        let key=
        item.workGroupName +
        item.workGroupCode + 
        item.description;
        let index = key.toUpperCase().indexOf(this.searchContent.toUpperCase());
        return index != -1;
      });
    }
  },
    beforeMount() {
    this.GetworkGroupList();
  },
};
</script>
<style>
.workGroup-container {
  display: flex;
  flex-direction: column;
}
.workGroup-header {
  padding-bottom: 10px;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
}
.workGroup-fliter-input {
  width: 400px;
  float: left;
}
.add-workgroup {
  margin-top: 5px;
  width: 100%;
  border: #dcdfe6 dashed 1px !important;
}
.workGroupInformation{
  display: flex;
  flex-direction: row;
  width: auto;
  justify-content: space-between;
}
</style>