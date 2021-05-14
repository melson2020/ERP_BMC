<template>
   <div class="contact-container">
    <div class="contact-header">
      <el-input
        class="contact-fliter-input"
        size="small"
        placeholder="搜索联系人"
        v-model="searchContent"
        suffix-icon="el-icon-search"
      ></el-input>
    </div>

    <div class="contact-content">
      <el-table :data="contactListPageShow" 
          border="" stripe           
          :header-row-style="{height:'40px' ,'align':'center'}"
          :row-style="{height:'40px'}"
          style="width: 100%">
        <el-table-column prop="contactName" label="联系人" width="120px"> </el-table-column>
        <el-table-column prop="phone" label="联系电话" width="220px"> </el-table-column>
        <el-table-column prop="customerNo" label="客户代码" width="140px"></el-table-column>
        <el-table-column prop="name" label="客户名称"> </el-table-column>
        <el-table-column prop="deliverAddress" label="送货地址"> </el-table-column>

        <el-table-column prop="" label="操作" width="100px">
          <template slot-scope="scope">
            <el-tooltip effect="light" content="修改联系人资料" placement="top">
              <el-button size="mini" @click="handleEdit(scope.$index,scope.row)" plain circle type="primary" icon="el-icon-edit"/>
            </el-tooltip>
            <el-tooltip effect="light" content="删除联系人资料" placement="top">
              <el-button size="mini" @click="handleDelete(scope.$index, scope.row)" plain circle type="danger" icon="el-icon-delete"/>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>

    </div>


    <div class="contact-content-footer">

    </div>


    <el-dialog title="编辑联系人资料" :visible.sync="contactEditDialog" :close-on-click-modal="false" width="1024px">
      <el-form status-icon :model="editContact" :rules="rules" ref="contactEditForm" label-width="100px">
          <el-row>
          <el-col :span="12">
            <el-form-item label="联系人" prop="contactName">
              <el-input v-model="editContact.contactName" autocomplete="off" ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系电话" prop="phone">
              <el-input autocomplete="off" v-model="editContact.phone"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-form-item label="客户名称">
            <el-input v-model="editContact.customerName" autocomplete="off" disabled></el-input>
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="送货地址" prop="deliverAddress">
            <el-input autocomplete="off"  v-model="editContact.deliverAddress"></el-input>
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="备注信息" prop="description">
            <el-input
              v-model="editContact.description"
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
            <el-button type="primary" @click="onEditContact('contactEditForm')" :loading="loading">保存</el-button>
            <el-button @click="contactEditDialog = false" v-if="!loading">取 消</el-button>
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
      // var validatePhone = (rule, value, callback) => {
      // var myreg = /^[1][3,4,5,7,8][0-9]{9}$/;
      // if (value === "") {
      //   callback(new Error("请输入联系电话"));
      // }  
      // if (!myreg.test(value)) {
      //   callback(new Error("请输入正确的手机号码"));
      // } 
      // };
    return{
      contactEditDialog: false,
      searchContent: "",
      loading: false,
      editIndex:'',
      editContact:{
          id: '',
          customerNo: '',
          contactName: '',
          phone: '',
          deliverAddress: '',
          description:'',
          tags:'',
          createBy:'',
          createDate:'',
          status:'',
          customerName:'',
      },
      rules: {
        contactName: [
          { required: true, message: "请输入联系人信息", trigger: "blur" }
        ],
        phone: [
          { required: true, message: "请输入联系电话", trigger: "blur" }
        ],
        // phone: [
        //   {
        //     validator: validatePhone,
        //     trigger: "blur",
        //   },
        // ],
        deliverAddress: [
          { required: true, message: "请输入客户地址", trigger: "blur" }
        ],
      }
    }
  },
  computed: {
    ...mapGetters(["contactList"]),
    contactListPageShow(){
      return this.contactList.filter((item)=>{
        let key=
        item.customerNo +
        item.name +
        item.deliverAddress +
        item.phone +
        item.contactName ;
        let index = key.toUpperCase().indexOf(this.searchContent.toUpperCase());
        return index != -1;
      });
    }
  },
  methods:{
        ...mapActions({
      GetContactList:"GetContactList",
      QueryContactObj:"QueryContactObj",
      SaveContact:"SaveContact",
      DeleteContact:"DeleteContact"
    }),
    handleEdit(index,row){
      this.editIndex=index;
      let contact={id:row.id,index:index}
      this.QueryContactObj(contact)
          .then(res=>{
          if (res.resultStatus == 1) {
              this.editContact=res.data;
              this.contactEditDialog=true;
          }
          else
          {
              this.$message.error(res.message);
          }
      })
      .catch(err=>{
            let alert = err.message ? err.message : err;
            this.$message.error(alert);
      });

    },
    handleDelete(index, row) {
      let contact={id:row.id,index:index}
       this.$messageBox.confirm('确认要删除？',"提示",{
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
          .then(() => {
            this.DeleteContact(contact)
          })
          .catch(e=>e);
      },
      onEditContact(formName){
         this.$refs[formName].validate(valid=>{
           if(valid){
              this.SaveContact(this.editContact)
              .then(res=>{
                  if(res.resultStatus==1){ 
                    this.contactList.splice(this.editIndex,1,res.data);
                  this.editIndex="";
                    // this.GetContactList();
                    this.contactEditDialog=false;
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
              .catch(
                err=>{
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
    this.GetContactList();
  },
};
</script>
<style>
.contact-container {
  display: flex;
  flex-direction: column;
}
.contact-header {
  padding-bottom: 10px;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
}
.contact-fliter-input {
  width: 400px;
  float: left;
}

</style>