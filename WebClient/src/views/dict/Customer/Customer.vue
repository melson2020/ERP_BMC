<template>
  <div class="customer-container">
    <div class="customer-header">
      <el-input
        class="customer-fliter-input"
        size="small"
        placeholder="搜索客户"
        v-model="searchContent"
        suffix-icon="el-icon-search"
      ></el-input>
      <el-button type="primary" size="small" @click="resetForm('customerAddForm')">新建客户</el-button>
    </div>
    <div class="customer-content">
      <el-table :data="customerListPageShow" 
          border="" stripe           
          :header-row-style="{height:'40px' ,'align':'center'}"
          :row-style="{height:'40px'}"
          style="width: 100%">
        <el-table-column prop="customerNo" label="客户代码" width="140px"></el-table-column>
        <el-table-column prop="name" label="客户名称"> </el-table-column>
                <el-table-column prop="address" label="地址"> </el-table-column>
        <!-- <el-table-column prop="contactName" label="联系人" width="100px"> </el-table-column> -->
        <el-table-column prop="phone" label="传真电话" width="160px"> </el-table-column>
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
            <el-tooltip effect="light" v-if="scope.row.status=='Y'" content="停用客户资料" placement="top">
              <el-button size="mini" @click.prevent.stop="UpdateCustomerStatus(scope.$index, scope.row,true)" plain circle type="danger" icon="el-icon-close"/>
            </el-tooltip>
            <el-tooltip effect="light" v-else content="启用客户资料" placement="top">
              <el-button size="mini" @click.prevent.stop="UpdateCustomerStatus(scope.$index, scope.row,false)" plain circle type="primary" icon="el-icon-check"/>
            </el-tooltip>
            <!-- <el-tooltip effect="light" content="删除客户资料" placement="top">
              <el-button size="mini" @click="handleDelete(scope.$index, scope.row)" plain circle type="danger" icon="el-icon-delete"/>
            </el-tooltip> -->
          </template>
        </el-table-column>
      </el-table>
    </div>

    <div class="customer-content-footer"></div>

    
    <el-dialog title="添加新客户" :visible.sync="customerAddDialog" :close-on-click-modal="false" width="1024px">
      <el-form status-icon :model="newCustomer" :rules="rules" ref="customerAddForm" label-width="100px">
        <el-row>
          <!-- <el-col :span="16"> -->
            <el-form-item label="客户名称" prop="name">
              <el-input v-model="newCustomer.name" autocomplete="off" ></el-input>
            </el-form-item>
          <!-- </el-col> -->
          <!-- <el-col :span="8">
            <el-form-item label="联系人" prop="contactName">
              <el-input v-model="newCustomer.contactName" autocomplete="off" ></el-input>
            </el-form-item>
          </el-col> -->
        </el-row>
        <el-row>
          <el-form-item label="客户地址" prop="address">
            <el-input autocomplete="off"  v-model="newCustomer.address"></el-input>
          </el-form-item>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="传真电话" prop="phone">
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
          <el-form-item label="联系人信息">
          <el-table
            :data="newCustomer.contactList"
            border
            style="width: 100%"
            size="mini"
            @cell-click="newCellClick"
            :show-header="false"
          >
            <el-table-column prop="contactName" label="名称:" width="170px" >
              <template slot-scope="scope" >
                <div class="contactInformation" >
                  <el-form-item label="名称:" prop="contactName" label-width="45px" >
                    <el-input
                      v-model="scope.row.contactName"
                      v-if="scope.row.seen"
                      size="mini"
                    ></el-input>
                    <span v-else>{{ scope.row.contactName }}</span>
                  </el-form-item>
                </div>
              </template>
            </el-table-column>
            <el-table-column prop="cellPhone" label="电话:"  width="240px" >
              <template slot-scope="scope">
                <div class="contactInformation">
                  <el-form-item label="电话:" prop="cellPhone" label-width="45px" >
                    <el-input
                      v-model="scope.row.phone"
                      v-if="scope.row.seen"
                     
                      size="mini"
                    ></el-input>
                    <span v-else>{{ scope.row.phone }}</span>
                  </el-form-item>
                </div>
              </template>
            </el-table-column>
            <el-table-column prop="deliverAddress" label="地址:" >
              <template slot-scope="scope" >
                <div class="contactInformation">
                  <el-form-item label="地址:" prop="deliverAddress" label-width="45px" >
                    <el-input  
                      v-model="scope.row.deliverAddress"
                      v-if="scope.row.seen"
                     
                      size="mini"
                      style="width:324px"
                    ></el-input>
                    <span v-else>{{ scope.row.deliverAddress }}</span>
                  </el-form-item>
                </div>
              </template>
            </el-table-column>
            <el-table-column width="80px" >>
              <template slot-scope="scope">
                <div class="contactOperation">

                  <el-tooltip effect="light" content="确定" placement="top">
                    <el-button size="mini" :disabled="!scope.row.isEditable" circle @click.prevent.stop="newSaveContact(scope.$index, scope.row)" icon="el-icon-check" type="primary" ></el-button>
                  </el-tooltip>
                  <el-tooltip effect="light" content="删除" placement="top">
                    <el-button size="mini" @click.prevent.stop="newDeleteContact(scope.$index, scope.row)" plain circle type="danger" icon="el-icon-delete"/>
                  </el-tooltip>
                </div>
              </template>
            </el-table-column>

          </el-table>
          <el-button
            icon="el-icon-plus"
            plain
            class="add-contact"
            @click="addContact"
            >添加联系人</el-button
          >
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
    
    <el-dialog title="编辑客户资料" :visible.sync="customerEditDialog" :close-on-click-modal="false" width="1024px">
      <el-form status-icon :model="editCustomer" :rules="rules" ref="customerEditForm" label-width="100px">
        <el-row>
          <!-- <el-col :span="16"> -->
            <el-form-item label="客户名称" prop="name">
              <el-input v-model="editCustomer.name" autocomplete="off" ></el-input>
            </el-form-item>
          <!-- </el-col> -->
          <!-- <el-col :span="8">
            <el-form-item label="联系人" prop="contactName">
              <el-input v-model="editCustomer.contactName" autocomplete="off" ></el-input>
            </el-form-item>
          </el-col> -->
        </el-row>
        <el-row>
          <el-form-item label="客户地址" prop="address">
            <el-input autocomplete="off"  v-model="editCustomer.address"></el-input>
          </el-form-item>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="传真电话" prop="phone">
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
          <el-form-item label="联系人信息">
            <el-table
              :data="editCustomer.contactList"
              border
              style="width: 100%"
              size="mini"
              @cell-click="editCellClick"
              :show-header="false"
            >
              <el-table-column prop="contactName" label="名称:" width="170px" >
                <template slot-scope="scope" >
                  <div class="contactInformation" >
                    <el-form-item label="名称:" prop="contactName" label-width="45px" >
                      <el-input
                        v-model="scope.row.contactName"
                        v-if="scope.row.seen"
                        :disabled="scope.row.isDisable"
                        size="mini"
                      ></el-input>
                      <span v-else>{{ scope.row.contactName }}</span>
                    </el-form-item>
                  </div>
                </template>
              </el-table-column>
              <el-table-column prop="cellPhone" label="电话:"  width="240px" >
                <template slot-scope="scope">
                  <div class="contactInformation">
                    <el-form-item label="电话:" prop="cellPhone" label-width="45px" >
                      <el-input
                        v-model="scope.row.phone"
                        v-if="scope.row.seen"
                        :disabled="scope.row.isDisable"
                        size="mini"
                      ></el-input>
                      <span v-else>{{ scope.row.phone }}</span>
                    </el-form-item>
                  </div>
                </template>
              </el-table-column>
              <el-table-column prop="deliverAddress" label="地址:" >
                <template slot-scope="scope" >
                  <div class="contactInformation">
                    <el-form-item label="地址:" prop="deliverAddress" label-width="45px" >
                      <el-input  
                        v-model="scope.row.deliverAddress"
                        v-if="scope.row.seen"
                        :disabled="scope.row.isDisable"
                        size="mini"
                        style="width:324px"
                      ></el-input>
                      <span v-else>{{ scope.row.deliverAddress }}</span>
                    </el-form-item>
                  </div>
                </template>
              </el-table-column>
              <el-table-column width="80px" >>
                <template slot-scope="scope">
                  <div class="contactOperation">

                    <el-tooltip effect="light" content="确定" placement="top">
                      <el-button size="mini" circle @click.prevent.stop="editSaveContact(scope.$index, scope.row)" icon="el-icon-check" type="primary" ></el-button>
                    </el-tooltip>
                    <el-tooltip effect="light" content="删除" placement="top">
                      <el-button size="mini" :disabled="scope.row.isDisable" @click.prevent.stop="editDeleteContact(scope.$index, scope.row)" plain circle type="danger" icon="el-icon-delete"/>
                    </el-tooltip>
                  </div>
                </template>
              </el-table-column>

            </el-table>
            <el-button
              icon="el-icon-plus"
              plain
              class="add-contact"
              @click="editAddContact"
              >添加联系人</el-button
            >
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item>
            <el-button type="primary" @click="onEditcustomer('customerEditForm')" :loading="loading">保 存</el-button>
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
      searchContent: "",
      loading: false,
      editIndex:'',
      // contactList: [{ id: "1", customerNo:"C10001", contactName: "何永", phone: "18013579965", deliverAddress:"苏州工业园区展业大厦",tags:"",description:"",createBy:"",createDate:"",seen: false}],
      contactList: [],
      // newContact:{
      //   id:'',
      //   customerNo:'',
      //   contactName:'',
      //   phone:'',
      //   deliverAddress:'',
      //   tags:'',
      //   description:'',
      //   createBy:'',
      //   createDate:''
      // },
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
          createBy: '',
          contactList: [],
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
          createBy: '',
          contactList: [],
      },
      rules: {
        name: [
          { required: true, message: "请输入客户名称", trigger: "blur" }
        ],
        // contactName: [
        //   { required: true, message: "请输入联系人信息", trigger: "blur" }
        // ],
        address: [
          { required: true, message: "请输入客户地址", trigger: "blur" }
        ],
        phone: [
          { required: true, message: "请输入联系电话", trigger: "blur" }
        ],
        // deliverAddress: [
        //   { required: true, message: "请输入客户地址", trigger: "blur" }
        // ],
        // cellPhone: [
        //   { required: true, message: "请输入联系电话", trigger: "blur" }
        // ]
      },
    };
  },
  computed: {
    ...mapGetters(["customerList"]),
    customerListPageShow(){
      return this.customerList.filter((item)=>{
        let key=
        item.customerNo +
        item.name +
        item.address +
        item.phone +
        item.taxNo +
        item.bankNo +
        item.payTerm +
        item.payWay +
        item.currency;
        let index = key.toUpperCase().indexOf(this.searchContent.toUpperCase());
        return index != -1;
      });
    }
  },
  methods: {
    ...mapActions({
      GetCustomerList: "GetCustomerList",
      SaveCustomer: "SaveCustomer",
      PushCustomerList: "PushCustomerList",
      QueryCustomerObj:"QueryCustomerObj",
      UpdateCustomer:"UpdateCustomer",
      SaveContact:"SaveContact",
      GetContactList:"GetContactList",

    }),
    resetForm(formName) {
      if (this.$refs[formName]) {
        this.$refs[formName].resetFields();
      }
      this.customerAddDialog = true;
    },
    addCustomer(formName){
      if(!this.newCheckSavedFlag())
      {
        if(!this.newCheckEidtable())
        {
          this.$refs[formName].validate(valid => {
          if (valid) {
            let customer=this.newCustomer
            customer.customerNo="cu" + new Date().valueOf();
            customer.createDate=new Date();
            customer.status="Y";
            customer.createBy="";     //TODO： 待添加创建人
            // customer.contactList=this.contactList;

            this.SaveCustomer(customer)
              .then(res => {
                if (res.resultStatus == 1) {
                  this.customerAddDialog = false;
                  this.newCustomer.contactList=[];
                  this.PushCustomerList(res.data);
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
        }
      }
    },
    editSaveContact(index,row){
      if(row.contactName.trim()==="")
      {
        this.$message.warning("名称不能为空");
        return false;
      }
      else
      {
        row.seen=false;
        row.notSavedFlag=false;
      }
    },
    editDeleteContact(index, row) {
      row.seen=false;
      row.notSavedFlag=false;
       this.$messageBox.confirm('确认要删除？',"提示",{
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
        .then(() => {

          if(row.id=="")
          {
            this.editCustomer.contactList.splice(index,1)
          }
          else
          {
            row.isDisable=true;
            row.status="N";
          }

        })
        .catch(e=>e);
    },
    newSaveContact(index,row){
      if(row.contactName.trim()==="")
      {
        this.$message.warning("名称不能为空");
        return false;
      }
      else
      {
        row.seen=false;
        row.notSavedFlag=false;

        // let customerContact=this.newContact
        // customerContact.contactName=row.contactName;
        // customerContact.phone=row.phone;
        // customerContact.deliverAddress=row.deliverAddress;
        // customerContact.createDate=row.createDate? row.createDate:new Date();
        // customerContact.createBy=row.createdBy? row.createdBy:"Admin";
        // customerContact.customerNo=row.customerNo?row.customerNo:"";
        // customerContact.tags=row.tags?row.tags:"";
        // customerContact.id=row.id?row.id:"";
        // customerContact.description=row.description?row.description:"";



        // this.SaveContact(customerContact)
        //   .then(res=>{
        //     if(res.resultStatus==1){
        //       row.seen=false;
        //       row.notSavedFlag=false;
        //       // this.GetContactList(customerContact.customerNo);
        //       this.$message({
        //         showClose:true,
        //         message:"操作成功",
        //         type:"success"
        //       });
        //     }
        //     else{
        //       this.$message.error(res.message);
        //     }
        //   })
        //   .catch(err=>{
        //       let alert = err.message ? err.message : err;
        //       this.$message.error(alert);
        //   });
      }
    },
    newDeleteContact(index, row) {
      row.seen=false;
      row.notSavedFlag=false;
       this.$messageBox.confirm('确认要删除？',"提示",{
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
        .then(() => {
          this.newCustomer.contactList.splice(index,1)
        })
        .catch(e=>e);
    },
    handleEdit(index,row){
      this.editIndex=index;
      let cus={id:row.id,customerNo:row.customerNo,index:index}
      this.QueryCustomerObj(cus)
        .then(res=>{
          if (res.resultStatus == 1) {
            // let queryCustomer=this.newCustomer;
            this.editCustomer.name=res.data.name;
            this.editCustomer.address=res.data.address;
            this.editCustomer.bankNo=res.data.bankNo;
            this.editCustomer.contactName=res.data.contactName;
            this.editCustomer.createBy=res.data.createBy;
            this.editCustomer.createDate=res.data.createDate;
            this.editCustomer.currency=res.data.currency;
            this.editCustomer.customerNo=res.data.customerNo;
            this.editCustomer.description=res.data.description;
            this.editCustomer.id=res.data.id;
            this.editCustomer.payTerm=res.data.payTerm;
            this.editCustomer.payWay=res.data.payWay;
            this.editCustomer.phone=res.data.phone;
            this.editCustomer.status=res.data.status;
            this.editCustomer.taxNo=res.data.taxNo;

            let contacts = [];
            for (let index = 0; index < res.data.contactList.length; index++) {
                const element = res.data.contactList[index];
                let contact = { id: element.id, customerNo:element.customerNo, contactName: element.contactName, phone: element.phone, deliverAddress:element.deliverAddress,tags:element.tags,description:element.description,createBy:element.createBy,createDate:element.createDate,status:element.status,seen: false, isEditable:false ,notSavedFlag:false,isDisable:false };
                contacts.push(contact);
            }
            this.editCustomer.contactList=contacts;
            // this.editCustomer=res.data;
            // this.editCustomer=queryCustomer;
            // console.log(this.editCustomer);
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
      if(!this.editCheckSavedFlag())
      {
        if(!this.editCheckEidtable())
        {
            this.$refs[formName].validate(valid=>{
              if(valid){
                this.SaveCustomer(this.editCustomer)
                .then(res=>{
                  if(res.resultStatus==1){
                    this.customerList.splice(this.editIndex,1,res.data);
                  this.editIndex="";
                    // this.GetCustomerList();
                    this.customerEditDialog=false;
                    this.editCustomer.contactList=[];
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
    UpdateCustomerStatus(index, row,disable) {
      // let cus={id:row.id,index:index,status:}
       this.$messageBox.confirm('确认要操作？',"提示",{
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
          .then(() => {
            row.status=disable?"N":"Y"
            let cus={id:row.id,index:index,status:row.status}
            this.UpdateCustomer(cus)
          })
          .catch(e=>e);
      },
    addContact() {
      if(!this.newCheckSavedFlag())
      {
        if(!this.newCheckEidtable())
        {
          this.newCustomer.contactList.push({ id: "", customerNo:"", contactName: "", phone: "", deliverAddress:"",tags:"",description:"",createBy:"",createDate:"",status:"Y",seen: false, isEditable:true ,notSavedFlag:true})
        }
      }
    },
    editAddContact() {
      if(!this.editCheckSavedFlag())
      {
        if(!this.editCheckEidtable())
        {
          this.editCustomer.contactList.push({ id: "", customerNo:"", contactName: "", phone: "", deliverAddress:"",tags:"",description:"",createBy:"",createDate:"",status:"Y",seen: false, isEditable:true ,notSavedFlag:true,isDisable:false})
        }
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
    newCheckEidtable(){
      var list= this.newCustomer.contactList.filter((item)=>{return item.seen
      })
      if(list.length>0){
        this.$message.warning('请先完成联系人编辑')
        return true;
      }
    },
    newCheckSavedFlag(){
      var list= this.newCustomer.contactList.filter((item)=>{return item.notSavedFlag
      })
      if(list.length>0){
        this.$message.warning('请先保存联系人数据')
        return true;
      }
    },
    editCheckEidtable(){
      var list= this.editCustomer.contactList.filter((item)=>{return item.seen
      })
      if(list.length>0){
        this.$message.warning('请先完成联系人编辑')
        return true;
      }
    },
    editCheckSavedFlag(){
      var list= this.editCustomer.contactList.filter((item)=>{return item.notSavedFlag
      })
      if(list.length>0){
        this.$message.warning('请先保存联系人数据')
        return true;
      }
    },
    // loseFcous(index, row) {
    //   row.seen = false;
    // },


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
.contactInformation{
  display: flex;
  flex-direction: row;
  width: auto;
  justify-content: space-between;
}
.contactOperation{
  display: flex;
  flex-direction: row;
  width: auto;
  justify-content: space-around;
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
.add-contact {
  margin-top: 5px;
  width: 100%;
  border: #dcdfe6 dashed 1px !important;
}
</style>
