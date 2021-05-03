<template>
  <div class="customer-container">
    <div class="customer-header">
      <el-input
        class="customer-fliter-input"
        size="mini"
        placeholder="搜索客户"
        suffix-icon="el-icon-search"
      ></el-input>
      <el-button type="primary" @click="resetForm('customerAddForm')">新建客户</el-button>
    </div>
    <div class="customer-content">
      <el-table :data="customerList" 
          border="" stripe           
          :header-row-style="{height:'40px' ,'align':'center'}"
          :row-style="{height:'40px'}"
          style="width: 100%">
        <el-table-column prop="customerNo" label="客户代码" width="150px"></el-table-column>
        <el-table-column prop="name" label="客户名称"> </el-table-column>
                <el-table-column prop="address" label="地址"> </el-table-column>
        <el-table-column prop="contactName" label="联系人" width="100px"> </el-table-column>
        <el-table-column prop="phone" label="电话" width="160px"> </el-table-column>
        <el-table-column prop="taxNo" label="税号" width="160px"> </el-table-column>
        <el-table-column prop="bankNo" label="账号" width="160px"> </el-table-column>
        <el-table-column prop="payTerm" label="账期" width="60px">
        </el-table-column>
        <el-table-column prop="payWay" label="付款方式" width="80px">
        </el-table-column>
        <el-table-column prop="currency" label="币种" width="60px">
        </el-table-column>
        <el-table-column prop="" label="操作" width="100px">
          <template slot-scope="scope">
            <el-tooltip effect="light" content="修改客户资料" placement="top">
              <el-button size="mini" @click="handleEdit(scope.$index,scope.row)" plain circle type="primary" icon="el-icon-edit"/>
            </el-tooltip>
            <el-tooltip effect="light" content="删除客户资料" placement="top">
              <el-button size="mini" @click="handleDelete(scope.$index, scope.row)" plain circle type="danger" icon="el-icon-delete"/>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <div class="customer-content-footer"></div>

    
    <el-dialog title="添加新客户" :visible.sync="customerAddDialog" :close-on-click-modal="false" width="60%">
      <el-form status-icon :model="newCustomer" :rules="rules" ref="customerAddForm" label-width="100px">
        <el-row>
          <el-col :span="16">
            <el-form-item label="客户名称" prop="name">
              <el-input v-model="newCustomer.name" autocomplete="off" ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="联系人" prop="contactName">
              <el-input v-model="newCustomer.contactName" autocomplete="off" ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-form-item label="客户地址" prop="address">
            <el-input autocomplete="off"  v-model="newCustomer.address"></el-input>
          </el-form-item>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="联系电话" prop="phone">
              <el-input autocomplete="off" v-model="newCustomer.phone"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="客户税号" prop="taxNo">
              <el-input autocomplete="off" v-model="newCustomer.taxNo"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="客户账号" prop="bankNo">
              <el-input autocomplete="off" v-model="newCustomer.bankNo"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="客户账期" prop="payTerm">
              <el-input autocomplete="off" v-model="newCustomer.payTerm"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="付款方式" prop="payWay">
              <el-input autocomplete="off" v-model="newCustomer.payWay"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="付款币种" prop="currency">
              <el-input autocomplete="off" v-model="newCustomer.currency"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-form-item label="备注信息" prop="description">
            <el-input
              v-model="newCustomer.description"
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
            <el-button type="primary" @click="addCustomer('customerAddForm')" :loading="loading">保存</el-button>
            <el-button @click="customerAddDialog = false" v-if="!loading">取 消</el-button>
          </el-form-item>
        </el-row>
      </el-form>
    </el-dialog>
    
    <el-dialog title="编辑客户资料" :visible.sync="customerEditDialog" :close-on-click-modal="false" width="60%">
      <el-form status-icon :model="editCustomer" :rules="rules" ref="customerEditForm" label-width="100px">
        <el-row>
          <el-col :span="16">
            <el-form-item label="客户名称" prop="name">
              <el-input v-model="editCustomer.name" autocomplete="off" ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="联系人" prop="contactName">
              <el-input v-model="editCustomer.contactName" autocomplete="off" ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-form-item label="客户地址" prop="address">
            <el-input autocomplete="off"  v-model="editCustomer.address"></el-input>
          </el-form-item>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="联系电话" prop="phone">
              <el-input autocomplete="off" v-model="editCustomer.phone"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="客户税号" prop="taxNo">
              <el-input autocomplete="off" v-model="editCustomer.taxNo"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="客户账号" prop="bankNo">
              <el-input autocomplete="off" v-model="editCustomer.bankNo"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="客户账期" prop="payTerm">
              <el-input autocomplete="off" v-model="editCustomer.payTerm"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="付款方式" prop="payWay">
              <el-input autocomplete="off" v-model="editCustomer.payWay"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="付款币种" prop="currency">
              <el-input autocomplete="off" v-model="editCustomer.currency"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-form-item label="备注信息" prop="description">
            <el-input
              v-model="editCustomer.description"
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
            <el-button type="primary" @click="onEditcustomer('customerEditForm')" :loading="loading">保存</el-button>
            <el-button @click="customerEditDialog = false" v-if="!loading">取 消</el-button>
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
    return {
      customerEditDialog: false,
      customerAddDialog: false,
      loading: false,
      newCustomer: {
          id: '',
          customerNo: '',
          name:'',
          contactName: '',
          address: '',
          phone: '',
          taxNo: '',
          bankNo: '',
          payTerm: '',
          payWay: '',
          currency: '',
          status: '',
          description: '',
          createDate:'',
          createdBy: ''
      },
      editCustomer: {
          id: '',
          customerNo: '',
          name:'',
          contactName: '',
          address: '',
          phone: '',
          taxNo: '',
          bankNo: '',
          payTerm: '',
          payWay: '',
          currency: '',
          status: '',
          description: '',
          createDate:'',
          createdBy: ''
      },
      rules: {
        name: [
          { required: true, message: "请输入客户名称", trigger: "blur" }
        ],
        contactName: [
          { required: true, message: "请输入联系人信息", trigger: "blur" }
        ],
        address: [
          { required: true, message: "请输入客户地址", trigger: "blur" }
        ],
        phone: [
          { required: true, message: "请输入联系电话", trigger: "blur" }
        ]
      },
    };
  },
  computed: {
    ...mapGetters(["customerList"]),
  },
  methods: {
    ...mapActions({
      GetCustomerList: "GetCustomerList",
      SaveCustomer: "SaveCustomer",
      PushCustomerList: "PushCustomerList",
      QueryCustomerObj:"QueryCustomerObj",
      DisableCustomer:"DisableCustomer",
    }),
    resetForm(formName) {
      if (this.$refs[formName]) {
        this.$refs[formName].resetFields();
      }
      this.customerAddDialog = true;
    },
    addCustomer(formName){
        this.$refs[formName].validate(valid => {
        if (valid) {
          let customer=this.newCustomer
          customer.customerNo="cu" + new Date().valueOf();
          customer.createDate=new Date();
          customer.status="Y";
          customer.createdBy="";     //TODO： 待添加创建人
          this.SaveCustomer(customer)
            .then(res => {
              if (res.resultStatus == 1) {
                this.customerAddDialog = false;
                this.PushCustomerList(res.data);
                this.$message({
                  showClose: true,
                  message: "创建成功",
                  type: "success"
                });
              } else {
                this.$messgae.error(res.message);
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
    handleEdit(index,row){
      let cus={id:row.id,index:index}
      this.QueryCustomerObj(cus)
        .then(res=>{
          if (res.resultStatus == 1) {
            this.editCustomer=res.data;
            this.customerEditDialog = true;
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
    onEditcustomer(formName){
      this.$refs[formName].validate(valid=>{
        if(valid){
          console.log(this.editCustomer);
          this.SaveCustomer(this.editCustomer)
          .then(res=>{
            if(res.resultStatus==1){
              this.GetCustomerList();
              this.customerEditDialog=false;
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
    handleDelete(index, row) {
      let cus={id:row.id,index:index}
       this.$messageBox.confirm('确认要删除？',"提示",{
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
          .then(() => {
            this.DisableCustomer(cus)
          })
          .catch(e=>e);
      },

  },
  beforeMount() {
    this.GetCustomerList();
  },
};
</script>
<style>
.customer-container {
  display: flex;
  flex-direction: column;
}
.customer-header {
  padding-bottom: 10px;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
}
.customer-fliter-input {
  width: 400px;
  float: left;
}
</style>
