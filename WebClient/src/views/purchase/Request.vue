<template>
  <div class="request-container">
    <div class="request-header">
    <el-input
      class="request-fliter-input"
      size="small"
      placeholder="搜索采购记录"
      v-model="searchContent"
      suffix-icon="el-icon-search"
    ></el-input>
    <el-button type="primary" size="small" @click="resetForm('purchaseAddForm')">采购申请</el-button>
    </div>

    <div class="request-content">
      <el-table :data="purchaseListPageShow" 
          border="" stripe           
          :header-row-style="{height:'40px' ,'align':'center'}"
          :row-style="{height:'40px'}"
          style="width: 100%">
        <el-table-column prop="planNo" label="PR-NO" width="180px"></el-table-column>
        <el-table-column prop="requester" label="采购人" width="280px"> </el-table-column>
        <el-table-column prop="type" label="采购类型" width="180px"> </el-table-column>
        <el-table-column prop="state" label="状态" width="180px"> </el-table-column>
        <el-table-column prop="pickingNo" label="领料单号" width="180px"> </el-table-column>
        <el-table-column prop="createDate" label="创建日期" width="140px"> 
          <template slot-scope="scope">
            <span>{{getFullTime(scope.row.createDate) }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="description" label="备注理由"> </el-table-column>
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

    <div class="request-content-footer"></div>

    <el-dialog title="新建采购单" :visible.sync="purchaseAddDialog" :close-on-click-modal="false" :show-close="false" width="1024px">
      <el-form status-icon :model="newpurchase" :rules="rules" ref="purchaseAddForm" label-width="100px">
        <el-row>
           <el-col :span="12">
             <div class="request-div">
                <el-form-item label="申请人" prop="requester">
                  <el-select
                    filterable
                    v-model="newpurchase.requester"
                    size="small"
                    style="width:400px"
                    placeholder="选择类别"
                    @change="userChanged">
                    <el-option
                      v-for="vo in userVos"
                      :label="vo.alias"
                      :value="vo"
                      :key="vo.id"
                    ></el-option>
                  </el-select>
                </el-form-item>
             </div>
            </el-col>
            <el-col :span="6">
              <el-form-item label="采购类型" prop="type">
                <el-input v-model="newpurchase.type" autocomplete="off" disabled></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="采购状态" prop="state">
                <el-input v-model="newpurchase.state" autocomplete="off" disabled></el-input>
              </el-form-item>
            </el-col>
        </el-row>
        <el-row>
          <el-form-item label="备注理由" prop="description">
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
          <el-form-item label="采购明细">
          <el-table
            :data="newpurchase.purchaseDetailList"
            border
            style="width: 100%"
            size="mini"
            @cell-click="newCellClick"
            :show-header="false"
          >
            <el-table-column prop="materialName" label="物品:" width="270px" >
              <template slot-scope="scope" >
                <div class="roleinformation" >
                  <el-form-item label="物品：" prop="materialName" label-width="65px" >
                    <el-select
                      filterable
                      v-model="scope.row.materialName"
                      v-if="scope.row.seen"
                      size="mini"
                      placeholder="选择物料"
                      @change="addMaterialChanged($event,scope.row)">
                      <el-option-group
                        v-for="group in productVos"
                        :key="group.groupName"
                        :label="group.groupName"
                      >
                        <el-option
                          v-for="item in group.list"
                          :key="item.id"
                          :label="item.alias"
                          :value="item"
                        >
                        </el-option>
                      </el-option-group>
                    </el-select>
                    <span v-else>{{ scope.row.materialName }}</span>
                  </el-form-item>
                </div>
              </template>
            </el-table-column>
              <el-table-column prop="specification" label="规："  width="144px" >
                <template slot-scope="scope">
                  <div class="purchaseInformation">
                    <el-form-item label="规" prop="specification" label-width="35px" >
                      <el-input
                        v-model="scope.row.specification"
                        v-if="scope.row.seen"
                        size="mini"
                      ></el-input>
                      <span v-else>{{ scope.row.specification }}</span>
                    </el-form-item>
                  </div>
                </template>
              </el-table-column>
            <el-table-column prop="count" label="数:" width="120px" >
              <template slot-scope="scope" >
                <div class="purchaseInformation">
                  <el-form-item label="数" prop="count" label-width="35px" >
                    <el-input  
                      v-model="scope.row.count"
                      v-if="scope.row.seen"
                      size="mini"
                    ></el-input>
                    <span v-else>{{ scope.row.count }}{{ scope.row.countUnit }}</span>
                  </el-form-item>
                </div>
              </template>
            </el-table-column>

            <el-table-column prop="remark" label="备注" >
              <template slot-scope="scope" >
                <div class="purchaseInformation">
                  <el-form-item label="备注" prop="remark" label-width="45px" >
                    <el-input  
                      v-model="scope.row.remark"
                      v-if="scope.row.seen"
                      size="mini"
                      style="width:190px"
                    ></el-input>
                    <span v-else>{{ scope.row.remark }}</span>
                  </el-form-item>
                </div>
              </template>
            </el-table-column>
            <el-table-column width="90px" >>
              <template slot-scope="scope">
                <div class="purchaseOperation">

                  <el-tooltip effect="light" content="确定" placement="top">
                    <el-button size="mini" :disabled="!scope.row.isEditable" circle @click.prevent.stop="newSavePR(scope.$index, scope.row)" icon="el-icon-check" type="primary" ></el-button>
                  </el-tooltip>
                  <el-tooltip effect="light" content="删除" placement="top">
                    <el-button size="mini" @click.prevent.stop="newDeletePR(scope.$index, scope.row)" plain circle type="danger" icon="el-icon-delete"/>
                  </el-tooltip>
                </div>
              </template>
            </el-table-column>

          </el-table>
          <el-button
            icon="el-icon-plus"
            plain
            class="add-PR"
            @click="addPR"
            >添加清单</el-button
          >
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item>
            <el-button type="primary" @click="onAddpurchase('purchaseAddForm')" :loading="loading">保存</el-button>
            <el-button @click="closeWindows()" v-if="!loading">取 消</el-button>
          </el-form-item>
        </el-row>
      </el-form>
    </el-dialog>

    <el-dialog :title=PRNo :visible.sync="purchaseEditDialog" :close-on-click-modal="false" :show-close="false" width="1024px">
      <el-form status-icon :model="editpurchase" :rules="rules" ref="purchaseEidtForm" label-width="100px">
        <el-row>
           <el-col :span="12">
             <div class="request-div">
                <el-form-item label="申请人" prop="requester">
                  <el-select
                    filterable
                    v-model="editpurchase.requester"
                    size="small"
                    style="width:400px"
                    placeholder="选择类别"
                    @change="userChanged">
                    <el-option
                      v-for="vo in userVos"
                      :label="vo.alias"
                      :value="vo"
                      :key="vo.id"
                    ></el-option>
                  </el-select>
                </el-form-item>
             </div>
            </el-col>
            <el-col :span="6">
              <el-form-item label="采购类型" prop="type">
                <el-input v-model="editpurchase.type" autocomplete="off" disabled></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="采购状态" prop="state">
                <el-input v-model="editpurchase.state" autocomplete="off" disabled></el-input>
              </el-form-item>
            </el-col>
        </el-row>
        <el-row>
          <el-form-item label="备注理由" prop="description">
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
          <el-form-item label="采购明细">
          <el-table
            :data="editpurchase.purchaseDetailList"
            border
            style="width: 100%"
            size="mini"
            @cell-click="editCellClick"
            :show-header="false"
          >
            <el-table-column prop="materialName" label="物品:" width="270px" >
              <template slot-scope="scope" >
                <div class="roleinformation" >
                  <el-form-item label="物品：" prop="materialName" label-width="65px" >
                    <el-select
                      filterable
                      v-model="scope.row.materialName"
                      v-if="scope.row.seen"
                      size="mini"
                      placeholder="选择物料"
                      @change="editMaterialChanged($event,scope.row)">
                      <el-option-group
                        v-for="group in productVos"
                        :key="group.groupName"
                        :label="group.groupName"
                      >
                        <el-option
                          v-for="item in group.list"
                          :key="item.id"
                          :label="item.alias"
                          :value="item"
                        >
                        </el-option>
                      </el-option-group>
                    </el-select>
                    <span v-else>{{ scope.row.materialName }}</span>
                  </el-form-item>
                </div>
              </template>
            </el-table-column>
              <el-table-column prop="specification" label="规："  width="144px" >
                <template slot-scope="scope">
                  <div class="purchaseInformation">
                    <el-form-item label="规" prop="specification" label-width="35px" >
                      <el-input
                        v-model="scope.row.specification"
                        v-if="scope.row.seen"
                        size="mini"
                      ></el-input>
                      <span v-else>{{ scope.row.specification }}</span>
                    </el-form-item>
                  </div>
                </template>
              </el-table-column>
            <el-table-column prop="count" label="数:" width="120px" >
              <template slot-scope="scope" >
                <div class="purchaseInformation">
                  <el-form-item label="数" prop="count" label-width="35px" >
                    <el-input  
                      v-model="scope.row.count"
                      v-if="scope.row.seen"
                      size="mini"
                    ></el-input>
                    <span v-else>{{ scope.row.count }}{{ scope.row.countUnit }}</span>
                  </el-form-item>
                </div>
              </template>
            </el-table-column>

            <el-table-column prop="remark" label="备注" >
              <template slot-scope="scope" >
                <div class="purchaseInformation">
                  <el-form-item label="备注" prop="remark" label-width="45px" >
                    <el-input  
                      v-model="scope.row.remark"
                      v-if="scope.row.seen"
                      size="mini"
                      style="width:190px"
                    ></el-input>
                    <span v-else>{{ scope.row.remark }}</span>
                  </el-form-item>
                </div>
              </template>
            </el-table-column>
            <el-table-column width="90px" >
              <template slot-scope="scope" >
                <div class="purchaseOperation">

                  <el-tooltip effect="light" content="确定" placement="top">
                    <el-button size="mini" :disabled="scope.row.state=='BUYING' || scope.row.state=='COMPLETE'" circle @click.prevent.stop="newSavePR(scope.$index, scope.row)" icon="el-icon-check" type="primary" ></el-button>
                  </el-tooltip>
                  <el-tooltip effect="light" content="删除" placement="top">
                    <el-button size="mini" :disabled="scope.row.state=='BUYING' || scope.row.state=='COMPLETE'" @click.prevent.stop="editDeletePR(scope.$index, scope.row)" plain circle type="danger" icon="el-icon-delete"/>
                  </el-tooltip>
                </div>
              </template>
            </el-table-column>

          </el-table>
          <el-button
            icon="el-icon-plus"
            plain
            class="add-PR"
            :disabled="editpurchase.state=='BUYING' || editpurchase.state=='COMPLETE'"
            @click="editPR"
            >添加清单</el-button
          >
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item>
            <el-button type="primary" :disabled="editpurchase.state=='BUYING' || editpurchase.state=='COMPLETE'" @click="onEditpurchase('purchaseEidtForm')" :loading="loading">保存</el-button>
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
        PRNo:'',
        options: [],
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
          requesterId:'',
          planNo:'',
          type:'INDIRECT',
          state:'CREATE',
          createDate:new Date(),
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
          requesterId:'',
          planNo:'',
          type:'',
          state:'',
          createDate:'',
          createBy:'',
          description:'',
          purchaseDetailList:[],
        },
        rules: {
        requester: [
          { required: true, message: "请选择申请人", trigger: "blur" }
        ],
        },
      }

    },
    computed: {
      ...mapGetters(["purchaseList","userInfo","userVos","productVos"]),
      purchaseListPageShow(){
        return this.purchaseList.filter((item)=>{
          let key=
          item.planNo +
          item.type +
          item.state +
          item.requester +
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
      DeletePurchase:"DeletePurchase",
      QueryProductVos: "QueryProductVos",
      GetEmployeeVos:"GetEmployeeVos",
      DeleteDetailPurchase:"DeleteDetailPurchase",
      }),
    userChanged(event){
        this.newpurchase.requester=event.alias;
        this.newpurchase.requesterId=event.id;
    },
    closeWindows(){
        this.newpurchase.purchaseDetailList=[];
        this.purchaseAddDialog=false;
    },
    addPR() {
      if(!this.newCheckSavedFlag())
      {
        if(!this.newCheckEidtable())
        {
          this.newpurchase.purchaseDetailList.push({ id: "", type:"ORDER",materialNo:"",materialName:"",specification:"",remark:"",count:"",countUnit:"",purchasePlanNo:"",createEmployeeNo:"",createBy:"",createDate:"",state:"CREATE",sourceId:"",sourceNo:"",requester:"",requesterId:"",supplyId:"",seen: false, isEditable:true ,notSavedFlag:true})
        }
      }
    },
    editPR() {
      if(!this.editCheckSavedFlag())
      {
        if(!this.editCheckEidtable())
        {
          this.editpurchase.purchaseDetailList.push({ id: "", type:"ORDER",materialNo:"",materialName:"",specification:"",remark:"",count:"",countUnit:"",purchasePlanNo:"",createEmployeeNo:"",createBy:"",createDate:"",state:"CREATE",sourceId:"",sourceNo:"",requester:"",requesterId:"",supplyId:"",seen: false, isEditable:true ,notSavedFlag:true})
        }
      }
    },
    newCheckEidtable(){
      var list= this.newpurchase.purchaseDetailList.filter((item)=>{return item.seen
      })
      if(list.length>0){
        this.$message.warning('请先完成编辑')
        return true;
      }
    },
    editCheckEidtable(){
      var list= this.editpurchase.purchaseDetailList.filter((item)=>{return item.seen
      })
      if(list.length>0){
        this.$message.warning('请先完成编辑')
        return true;
      }
    },
    newCheckSavedFlag(){
      var list= this.newpurchase.purchaseDetailList.filter((item)=>{return item.notSavedFlag
      })
      if(list.length>0){
        this.$message.warning('请先保存数据')
        return true;
      }
    },
    editCheckSavedFlag(){
      var list= this.editpurchase.purchaseDetailList.filter((item)=>{return item.notSavedFlag
      })
      if(list.length>0){
        this.$message.warning('请先保存数据')
        return true;
      }
    },
    addCheckRepeated(event){
      var list= this.newpurchase.purchaseDetailList.filter((item)=>{return item.materialNo==event.productNo
      })
      if(list.length>0){
        this.$message.warning('子料重复！')
        return true;
      }
    },
    editCheckRepeated(event){
      var list= this.editpurchase.purchaseDetailList.filter((item)=>{return item.materialNo==event.productNo
      })
      if(list.length>0){
        this.$message.warning('子料重复！')
        return true;
      }
    },
    newCellClick(row) {
      if(!row.seen)
      {
        if(!this.newCheckEidtable())
        {
          row.seen=true;
        }
      }
    },
    editCellClick(row) {
      if(!row.seen)
      {
        if(!this.editCheckEidtable())
        {
          row.seen=true;
        }
      }
    },
    addMaterialChanged(event,row){
        if(!this.addCheckRepeated(event))
        {
          row.materialNo=event.productNo;
          row.materialName=event.name;
          row.specification=event.specification;
          row.supplyId=event.supplyId;
          row.countUnit=event.unit;
        }
        else{
          row.materialNo="";
          row.materialName="";
          row.specification="";
          row.supplyId="";
          row.countUnit="";
          event=null;
        }
    },
    editMaterialChanged(event,row){
        if(!this.editCheckRepeated(event))
        {
          row.materialNo=event.productNo;
          row.materialName=event.name;
          row.specification=event.specification;
          row.supplyId=event.supplyId;
          row.countUnit=event.unit;
        }
        else{
          row.materialNo="";
          row.materialName="";
          row.specification="";
          row.supplyId="";
          row.countUnit="";
          event=null;
        }
    },
    newSavePR(index,row){
        if(row.materialNo===""|| row.count===""|| isNaN(row.count))
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
    newDeletePR(index,row){
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
          this.DeleteDetailPurchase(row)
          this.newpurchase.purchaseDetailList.splice(index,1)
        })
        .catch(e=>e);
      }
      else{
        this.newpurchase.purchaseDetailList.splice(index,1)
      }
    },
    editDeletePR(index,row){
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
          if(this.editpurchase.purchaseDetailList.length==1)
          {
            this.$messageBox('最后一个物料不能删除')
          }
          else
          {
            this.DeleteDetailPurchase(row)
            this.editpurchase.purchaseDetailList.splice(index,1)
          }
        })
        .catch(e=>e);
      }
      else{
        this.editpurchase.purchaseDetailList.splice(index,1)
      }
    },
    onAddpurchase(formName){
      if(!this.newCheckSavedFlag())
      {
        if(!this.newCheckEidtable())
        {
          this.$refs[formName].validate(valid => {
            if (valid) {
              this.newpurchase.createBy=this.userInfo.id;
              this.SavePurchase(this.newpurchase)
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
        }
      }
    },
    resetForm(formName) {
      if (this.$refs[formName]) {
        this.$refs[formName].resetFields();
      }
      this.purchaseAddDialog = true;
    },
    getFullTime(time) {
    return new Date(time).format("yyyy-MM-dd");
    },
    handleEdit(index,row){
      this.editIndex=index;
      let cat={id:row.id,planNo:row.planNo,index:index}
      this.QueryPurchaseObj(cat)
        .then(res=>{
          if (res.resultStatus == 1) {
            this.editpurchase=res.data;
            let list = [];
            for (let index = 0; index < res.data.purchaseDetailList.length; index++) {
                const element = res.data.purchaseDetailList[index];
                let con = { id: element.id, type:element.type,materialNo:element.materialNo,materialName:element.materialName,specification:element.specification,remark:element.remark,count:element.count,countUnit:element.countUnit,purchasePlanNo:element.purchasePlanNo,createEmployeeNo:element.createEmployeeNo,createBy:element.createBy,createDate:element.createDate,state:element.state,sourceId:element.sourceId,sourceNo:element.sourceNo,requester:element.requester,requesterId:element.requesterId,supplyId:element.supplyId,seen: false, isEditable:false ,notSavedFlag:false};
                list.push(con);
            }
            this.PRNo=this.editpurchase.planNo;
            this.editpurchase.purchaseDetailList=list;
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
      let cat={id:row.id,planNo:row.planNo,index:index}
       this.$messageBox.confirm('确认要删除？',"提示",{
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
          .then(() => {
            if(row.state==="CREATE" || row.state==="REJECT")
            {
              this.DeletePurchase(cat)
            }
            else
            {
              this.$message.warning("该状态无法删除");
              return false;
            }
          })
          .catch(e=>e);
      },
      onEditpurchase(formName){
        this.$refs[formName].validate(valid=>{
          if(valid){
            this.editpurchase.state="CREATE";
            this.editpurchase.createDate=new Date();
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
      this.GetEmployeeVos();
      this.QueryProductVos()
    },
};
</script>
<style lang="less">
.request-container {
  display: flex;
  flex-direction: column;
  width: 98%;
  padding: 15px;
}
.request-header {
  padding-bottom: 10px;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
}
.request-fliter-input {
  width: 400px;
  float: left;
}
.add-PR {
  margin-top: 5px;
  width: 100%;
  border: #dcdfe6 dashed 1px !important;
}
.request-div{
  display: flex;
  flex-direction: row;
  width: auto;
  justify-content: space-between;
}
.purchaseInformation{
  display: flex;
  flex-direction: row;
  width: auto;
  justify-content: space-between;
}
.purchaseOperation{
    display: flex;
  flex-direction: row;
  width: auto;
  justify-content: space-around;
}

</style>