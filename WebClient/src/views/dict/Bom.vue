<template>
   <div class="productBom-container">
    <div class="productBom-header">
      <el-input
        class="productBom-fliter-input"
        size="small"
        placeholder="搜索产品Bom"
        v-model="searchContent"
        suffix-icon="el-icon-search"
      ></el-input>
      <!-- <el-button type="primary" size="small" @click="resetForm('productAddBoMForm')">新建Bom</el-button> -->
    </div>

    <div class="productBom-content">
      <el-table :data="productBomListPageShow" 
          border="" stripe           
          :header-row-style="{height:'40px' ,'align':'center'}"
          :row-style="{height:'40px'}"
          style="width: 100%">
        <el-table-column prop="productNo" label="产品编码" width="140px"> </el-table-column>
        <el-table-column prop="productName" label="产品名称" > </el-table-column>
        <el-table-column prop="bomNo" label="Bom编码" width="140px"> </el-table-column>
        <el-table-column prop="version" label="版本" width="80px"> </el-table-column>
        <el-table-column prop="expirationDate" label="失效日期" width="120px"> 
          <template slot-scope="scope">
            <span>{{getFullTime(scope.row.expirationDate) }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="status" label="有效" width="80px"> </el-table-column>


        <el-table-column prop="specification" label="产品规格" width="120px"> </el-table-column>
        <el-table-column prop="costPrice" label="估价" width="100px"></el-table-column>
        <el-table-column prop="description" label="描述"> </el-table-column>

        <el-table-column prop="" label="操作" width="100px">
          <template slot-scope="scope">
            <el-tooltip effect="light" content="修改Bom信息" placement="top">
              <el-button size="mini" @click="handleEdit(scope.$index,scope.row)" :disabled="scope.row.status=='N'" plain circle type="primary" icon="el-icon-edit"/>
            </el-tooltip>
            <el-tooltip effect="light" v-if="scope.row.status=='Y'" content="停用Bom" placement="top">
              <el-button size="mini" @click.prevent.stop="UpdateProductBomStatus(scope.$index, scope.row,true)" plain circle type="danger" icon="el-icon-close"/>
            </el-tooltip>
            <el-tooltip effect="light" v-else content="启用Bom" placement="top">
              <el-button size="mini" @click.prevent.stop="UpdateProductBomStatus(scope.$index, scope.row,false)" plain circle type="primary" icon="el-icon-check"/>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>

    </div>


    <div class="productBom-content-footer">

    </div>

    <!-- <el-dialog title="新建产品Bom" :visible.sync="productBomAddDialog" :close-on-click-modal="false" width="1024px">
      <el-form status-icon :model="addProductBom" ref="productAddBoMForm" label-width="100px">
        <div class="productBom-selected-header">
          <span>父项物料信息</span>
        </div>
        <div class="productBom-selected-content">
          <el-row>
            <el-col :span="6">
              <el-form-item label="商品名称" prop="productName">
                <el-select
                  filterable
                  v-model="addProductBom.productName"
                  size="small"
                  placeholder="选择工序"
                  @change="AddProductChanged">
                  <el-option
                    v-for="vo in productList"
                    :label="vo.name"
                    :value="vo"
                    :key="vo.id"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="制造工序" prop="processId">
                <el-select
                  filterable
                  v-model="addProductBom.processName"
                  size="small"
                  placeholder="选择工序"
                  @change="processChanged">
                  <el-option
                    v-for="vo in produceProcessList"
                    :label="vo.name"
                    :value="vo"
                    :key="vo.id"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="6">
            <el-form-item label="Bom版本" prop="version">
              <el-input autocomplete="off"  v-model="addProductBom.version" disabled></el-input>
            </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="失效日期" prop="expirationDate">
                <el-date-picker
                  v-model="addProductBom.expirationDate"
                  type="date"
                  style="width:147px"
                  placeholder="选择日期">
                </el-date-picker>
              </el-form-item>

            </el-col>
          </el-row>
          <el-row>
            <el-form-item label="备注信息" prop="description">
              <el-input
                v-model="addProductBom.description"
                autocomplete="off"
                style="width: 100%;"
                rows="3"
                type="textarea"
                maxlength="200"
                show-word-limit
              ></el-input>
            </el-form-item>
          </el-row>
        </div>
        <div class="productBom-selected-header">
          <span>子项物料详情</span>
        </div>
        <div class="productBom-selected-content-special">
        <el-row>
          <el-form-item label="物料信息">
            <el-button
              icon="el-icon-plus"
              plain
              class="add-productBom"
              @click="addProductBomButton"
              >添加子项物料</el-button>
            <el-table
              :data="addProductBom.materialVos"
              border
              style="width: 100%"
              size="mini"
              @cell-click="editCellClick"
              :show-header="false"
            >
              <el-table-column prop="name" label="名" width="185px" >
                <template slot-scope="scope" >
                  <div class="productBominformation">
                    <el-form-item label="名" prop="chName" label-width="20px" >
                      <el-select
                        filterable
                        v-model="scope.row.chName"
                        v-if="scope.row.seen"
                        size="mini"
                        placeholder="选择物料"
                        @change="materialChanged($event,scope.row)">
                        <el-option
                          v-for="vo in filterProductList"
                          :label="vo.name"
                          :value="vo"
                          :key="vo.id"
                        ></el-option>
                      </el-select>

                      <span v-else>{{ scope.row.chName }}</span>
                    </el-form-item>
                  </div>
                </template>
              </el-table-column>
              <el-table-column prop="specification" label="规"  width="150px" >
                <template slot-scope="scope">
                  <div class="productBominformation">
                    <el-form-item label="规" prop="specification" label-width="20px" >
                      <el-input
                        v-model="scope.row.specification"
                        v-if="scope.row.seen"
                        :disabled="scope.row.isDisable"
                        size="mini"
                      ></el-input>
                      <span v-else>{{ scope.row.specification }}</span>
                    </el-form-item>
                  </div>
                </template>
              </el-table-column>
              <el-table-column prop="chQty" label="数:" width="120px" >
                <template slot-scope="scope" >
                  <div class="productBominformation">
                    <el-form-item label="数" prop="chQty" label-width="35px" >
                      <el-input  
                        v-model="scope.row.chQty"
                        v-if="scope.row.seen"
                        :disabled="scope.row.isDisable"
                        size="mini"
                      ></el-input>
                      <span v-else>{{ scope.row.chQty }}</span>
                    </el-form-item>
                  </div>
                </template>
              </el-table-column>
              <el-table-column prop="lossRate" label="损" width="120px" >
                <template slot-scope="scope" >
                  <div class="productBominformation">
                    <el-form-item label="损" prop="lossRate" label-width="35px" >
                      <el-input  
                        v-model="scope.row.lossRate"
                        v-if="scope.row.seen"
                        :disabled="scope.row.isDisable"
                        size="mini"
                      ></el-input>
                      <span v-else>{{ scope.row.lossRate }}</span>
                    </el-form-item>
                  </div>
                </template>
              </el-table-column>
              <el-table-column prop="supplyName" label="厂家" >
                <template slot-scope="scope" >
                  <div class="productBominformation">
                      <el-input  
                        v-model="scope.row.supplyName"
                        v-if="scope.row.seen"
                        :disabled="scope.row.isDisable"
                        size="mini"
                        style="width:200px"
                      ></el-input>
                      <span v-else>{{ scope.row.supplyName }}</span>
                  </div>
                </template>
              </el-table-column>
              <el-table-column width="70px" >>
                <template slot-scope="scope">
                  <div class="productBomOperation">

                    <el-tooltip effect="light" content="确定" placement="top">
                      <el-button size="mini" circle @click.prevent.stop="addSaveProductBom(scope.$index, scope.row)" icon="el-icon-check" type="primary" ></el-button>
                    </el-tooltip>
                    <el-tooltip effect="light" content="删除" placement="top">
                      <el-button size="mini" :disabled="scope.row.isDisable" @click.prevent.stop="addDeleteProductBom(scope.$index, scope.row)" plain circle type="danger" icon="el-icon-delete"/>
                    </el-tooltip>
                  </div>
                </template>
              </el-table-column>

            </el-table>
          </el-form-item>
        </el-row>
        </div>
        <div class="productBom-selected-content">
          <el-row>
            <el-form-item>
              <el-button type="primary" @click="onAddProductBom('productAddBoMForm')" :loading="loading">保 存</el-button>
              <el-button @click="productBomAddDialog = false" v-if="!loading">取 消</el-button>
            </el-form-item>
          </el-row>
        </div>
      </el-form>
    </el-dialog> -->


    <el-dialog :title=bomNo :visible.sync="productBomEditDialog" :close-on-click-modal="false" width="1024px">
      <el-form status-icon :model="editProductBom" ref="productEditBoMForm" label-width="100px">
        <div class="productBom-selected-header">
          <span>父项物料信息</span>
        </div>
        <div class="productBom-selected-content">
          <el-row>
            <el-col :span="6">
              <el-form-item label="商品名称" prop="productName">
                <el-input v-model="editProductBom.productName" autocomplete="off" disabled></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="制造工序" prop="processId">
                <el-select
                  filterable
                  v-model="editProductBom.processName"
                  size="small"
                  placeholder="选择工序"
                  @change="processChanged">
                  <el-option
                    v-for="vo in produceProcessList"
                    :label="vo.name"
                    :value="vo"
                    :key="vo.id"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="6">
            <el-form-item label="Bom版本" prop="version">
              <el-input autocomplete="off"  v-model="editProductBom.version" disabled></el-input>
            </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="失效日期" prop="expirationDate">
                <el-date-picker
                  v-model="editProductBom.expirationDate"
                  type="date"
                  style="width:147px"
                  placeholder="选择日期">
                </el-date-picker>
              </el-form-item>

            </el-col>
          </el-row>
          <el-row>
            <el-form-item label="备注信息" prop="description">
              <el-input
                v-model="editProductBom.description"
                autocomplete="off"
                style="width: 100%;"
                rows="3"
                type="textarea"
                maxlength="200"
                show-word-limit
              ></el-input>
            </el-form-item>
          </el-row>
        </div>
        <div class="productBom-selected-header">
          <span>子项物料详情</span>
        </div>
        <div class="productBom-selected-content-special">
        <el-row>
          <el-form-item label="物料信息">
            <el-button
              icon="el-icon-plus"
              plain
              class="add-productBom"
              @click="editProductBomButton"
              >添加子项物料</el-button
            >
            <el-table
              :data="editProductBom.materialVos"
              border
              style="width: 100%"
              size="mini"
              @cell-click="editCellClick"
              :show-header="false"
            >
              <el-table-column prop="name" label="名" width="185px" >
                <template slot-scope="scope" >
                  <div class="productBominformation" >
                    <el-form-item label="名" prop="chName" label-width="20px" >
                      <el-select
                        filterable
                        v-model="scope.row.chName"
                        v-if="scope.row.seen"
                        size="mini"
                        placeholder="选择物料"
                        @change="materialChanged($event,scope.row)">
                        <!-- <el-option
                          v-for="vo in filterProductList"
                          :label="vo.alias"
                          :value="vo"
                          :key="vo.id"
                        ></el-option> -->
                        <el-option-group
                          v-for="group in options"
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

                      <span v-else>{{ scope.row.chName }}</span>
                    </el-form-item>
                  </div>
                </template>
              </el-table-column>
              <el-table-column prop="specification" label="规"  width="150px" >
                <template slot-scope="scope">
                  <div class="productBominformation">
                    <el-form-item label="规" prop="specification" label-width="20px" >
                      <el-input
                        v-model="scope.row.specification"
                        v-if="scope.row.seen"
                        disabled
                        size="mini"
                      ></el-input>
                      <span v-else>{{ scope.row.specification }}</span>
                    </el-form-item>
                  </div>
                </template>
              </el-table-column>
              <el-table-column prop="chQty" label="数:" width="120px" >
                <template slot-scope="scope" >
                  <div class="productBominformation">
                    <el-form-item label="数" prop="chQty" label-width="35px" >
                      <el-input  
                        v-model="scope.row.chQty"
                        v-if="scope.row.seen"
                        :disabled="scope.row.isDisable"
                        size="mini"
                      ></el-input>
                      <span v-else>{{ scope.row.chQty }}</span>
                    </el-form-item>
                  </div>
                </template>
              </el-table-column>
              <el-table-column prop="lossRate" label="损" width="120px" >
                <template slot-scope="scope" >
                  <div class="productBominformation">
                    <el-form-item label="损" prop="lossRate" label-width="35px" >
                      <el-input  
                        v-model="scope.row.lossRate"
                        v-if="scope.row.seen"
                        :disabled="scope.row.isDisable"
                        size="mini"
                      ></el-input>
                      <span v-else>{{ scope.row.lossRate }}</span>
                    </el-form-item>
                  </div>
                </template>
              </el-table-column>
              <el-table-column prop="supplyName" label="厂家" >
                <template slot-scope="scope" >
                  <div class="productBominformation">
                    <!-- <el-form-item label="厂家" prop="supplyName" label-width="40px" > -->
                      <el-input  
                        v-model="scope.row.supplyName"
                        v-if="scope.row.seen"
                        :disabled="scope.row.isDisable"
                        size="mini"
                        style="width:200px"
                      ></el-input>
                      <span v-else>{{ scope.row.supplyName }}</span>
                    <!-- </el-form-item> -->
                  </div>
                </template>
              </el-table-column>
              <el-table-column width="70px" >>
                <template slot-scope="scope">
                  <div class="productBomOperation">

                    <el-tooltip effect="light" content="确定" placement="top">
                      <el-button size="mini" circle @click.prevent.stop="editSaveProductBom(scope.$index, scope.row)" icon="el-icon-check" type="primary" ></el-button>
                    </el-tooltip>
                    <el-tooltip effect="light" content="删除" placement="top">
                      <el-button size="mini" :disabled="scope.row.isDisable" @click.prevent.stop="editDeleteProductBom(scope.$index, scope.row)" plain circle type="danger" icon="el-icon-delete"/>
                    </el-tooltip>
                  </div>
                </template>
              </el-table-column>

            </el-table>
          </el-form-item>
        </el-row>
        </div>
        <div class="productBom-selected-content">
          <el-row>
            <el-form-item>
              <el-button type="primary" @click="oneditProductBom('productEditBoMForm')" :loading="loading">保 存</el-button>
              <el-button @click="productBomEditDialog = false" v-if="!loading">取 消</el-button>
            </el-form-item>
          </el-row>
        </div>
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
      options: [],
      bomNo:'',
      selectPNO:'',
      productBomEditDialog: false,
      productBomAddDialog:false,
      searchContent: "",
      loading: false,
      editIndex:'',
      addProductBom:{
        id:'',
        productNo:'',
        productName:'',
        processId:'',
        processNo:'',
        processCost:'',
        specification:'',
        bomNo:'',
        bomGenericId:'',
        categoryId:'',
        supplyId:'',
        supplyName:'',
        costPrice:'',
        expirationDate:'',
        version:'',
        description:'',
        createBy:'',
        createDate:'',
        productBomList:[],
        groupProductVos:[],
        materialVos:[],
        status:'',
      },
      editProductBom:{
        id:'',
        productNo:'',
        productName:'',
        processId:'',
        processName:'',
        processNo:'',
        processCost:'',
        specification:'',
        bomNo:'',
        bomGenericId:'',
        categoryId:'',
        supplyId:'',
        supplyName:'',
        costPrice:'',
        expirationDate:'',
        version:'',
        description:'',
        createBy:'',
        createDate:'',
        productBomList:[],
        groupProductVos:[],
        materialVos:[],
        status:'',
      },
      rules: {
        version: [
          { required: true, message: "请输入Bom版本号", trigger: "blur" }
        ],
      }
    }
  },
  computed: {
    ...mapGetters(["productBomList","productList","produceProcessList"]),
    productBomListPageShow(){
      return this.productBomList.filter((item)=>{
        let key=
        item.productNo +
        item.productName +
        item.specification +
        item.bomNo +
                item.costPrice +
        item.expirationDate +
        item.version +
        item.description +
        item.status ;
        let index = key.toUpperCase().indexOf(this.searchContent.toUpperCase());
        return index != -1;
      });
    },
    filterProductList(){
      return this.productList.filter((item)=>{
        item.alias=item.name+" / "+item.category+" / "+item.specification+" / "+item.supplyName
        return item.productNo!=this.selectPNO
      })
    }
  },
  methods:{
        ...mapActions({
      GetProductBomList:"GetProductBomList",
      QueryProductBom:"QueryProductBom",
      SaveProductBom:"SaveProductBom",
      UpdateProductBom:"UpdateProductBom",
      GetProductList:"GetProductList",
      FindAllProduceProcessList: "FindAllProduceProcessList",
    }),
    addSaveProductBom(index,row){
      if(row.productNo===""|| row.chQty===""||row.lossRate===""|| isNaN(row.chQty)|| isNaN(row.lossRate))
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
    addDeleteProductBom(index,row){
      row.seen=false;
      row.notSavedFlag=false;
      this.$messageBox.confirm('确认要删除？',"提示",{
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
        .then(() => {
            this.addProductBom.materialVos.splice(index,1)
        })
        .catch(e=>e);
    },
    addCheckSavedFlag(){
      var list= this.addProductBom.materialVos.filter((item)=>{return item.notSavedFlag
      })
      if(list.length>0){
        this.$message.warning('请先完成子料编辑')
        return true;
      }
    },
    addCheckEidtable(){
      var list= this.addProductBom.materialVos.filter((item)=>{return item.seen
      })
      if(list.length>0){
        this.$message.warning('请先完成子料编辑')
        return true;
      }
    },
    addProductBomButton(){
      if(!this.addCheckSavedFlag())
      {
        if(!this.addCheckEidtable())
          {
            this.addProductBom.materialVos.push({ id:"",bomNo:"",processId:"",processNo:"",partNo:"",partName:"",chPartNo: "",chName:"",chBomStatus:"",chBomNo:"",specification:"",sIndex:"",chQty:"" ,lossRate:"0", processStation:"", supplyId:"" ,supplyName:"",materialCostStatus:"",productNo:"",categoryId: "",salesPrice:"",seen: false,notSavedFlag:true})
          }
      }
    },
    resetForm(formName) {
      if (this.$refs[formName]) {
        this.$refs[formName].resetFields();
      }
      this.GetProductList();
      this.FindAllProduceProcessList();
      this.addProductBom.expirationDate=new Date().setMonth(new Date().getMonth()+12);
      this.productBomAddDialog = true;
    },
    handleEdit(index,row){
      // this.selectPNO=row.productNo;
      let pb={bomNo:row.bomNo,productNo:row.productNo,index}
      this.QueryProductBom(pb)
          .then(res=>{
          if (res.resultStatus == 1) {
              var productList = res.data.groupProductVoList;
              var options = [];
              productList.map((group) => {
                var subList = group.list;
                if (subList.length > 0) {
                  options.push({ groupName: group.groupName, list: subList });
                }
              });
              this.options = options;
              this.editProductBom=res.data;
              this.bomNo=this.editProductBom.bomNo;
              let materials = [];
              for (let index = 0; index < res.data.materialVos.length; index++) {
                  const element = res.data.materialVos[index];
                  let bom = { id:element.id,bomNo:element.bomNo,processId:element.processId,processNo:element.processNo,partNo:element.partNo,partName:element.partName,chPartNo: element.chPartNo,chName:element.chName,chBomStatus:element.chBomStatus,chBomNo:element.chBomNo,chBomGenericId:element.chBomGenericId,specification:element.specification,salesPrice:element.salesPrice,sIndex:element.sIndex,chQty:element.chQty ,lossRate:element.lossRate, processStation:element.processStation, supplyId:element.supplyId ,supplyName:element.supplyName,materialCostStatus:element.materialCostStatus,productNo:element.chPartNo,categoryId: "",seen: false,notSavedFlag:false };
                  materials.push(bom);
              }
              this.editProductBom.materialVos=materials;
              this.productBomEditDialog=true;
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

    // onEditProductBom(formName){
    //     this.$refs[formName].validate(valid=>{
    //       if(valid){
    //         this.SaveProductBom(this.editProductBom)
    //         .then(res=>{
    //             if(res.resultStatus==1){ 
    //               this.productBomList.splice(this.editIndex,1,res.data);
    //               this.editIndex=""
    //               // this.GetProductBomList();
    //               this.productBomEditDialog=false;
    //               this.$message({
    //                 showClose:true,
    //                 message:"操作成功",
    //                 type:"success"
    //               });
    //             }
    //             else{
    //               this.$message.error(res.message);
    //             }
    //         })
    //         .catch(
    //           err=>{
    //               let alert = err.message ? err.message : err;
    //               this.$message.error(alert);
    //           });

    //       }
    //       else{
    //         this.$message.warning("请填写准确信息");
    //         return false;
    //     }

    //     })
    // },
    getFullTime(time) {
    return new Date(time).format("yyyy-MM-dd");
    },
    UpdateProductBomStatus(index, row,disable) {
      this.$messageBox.confirm('确认要操作Bom？',"提示",{
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
      .then(() => {
        row.status=disable?"N":"Y"
        this.UpdateProductBom(row)
            .then(res=>{
              if(res.resultStatus==1){
                row=res.data
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
      })
      .catch(e=>e);
    },
    processChanged(event){
        this.editProductBom.processCost=event.cost;
        this.editProductBom.processId=event.id;
        this.editProductBom.processName=event.name;
        this.editProductBom.processNo=event.processNo;
    },
    editProductBomButton(){
      if(!this.editCheckSavedFlag())
      {
        if(!this.editCheckEidtable())
          {
            this.editProductBom.materialVos.push({ id:"",bomNo:"",processId:"",processNo:"",partNo:"",partName:"",chPartNo: "",chName:"",chBomStatus:"",chBomNo:"",chBomGenericId:"",specification:"",sIndex:"",chQty:"" ,lossRate:"0", processStation:"", supplyId:"" ,supplyName:"",materialCostStatus:"",productNo:"",categoryId: "",salesPrice:"",seen: false,notSavedFlag:true})
          }
      }
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
    editCheckSavedFlag(){

      var list= this.editProductBom.materialVos.filter((item)=>{return item.notSavedFlag
      })
      if(list.length>0){
        this.$message.warning('请先完成子料编辑')
        return true;
      }
    },
    editCheckEidtable(){
      var list= this.editProductBom.materialVos.filter((item)=>{return item.seen
      })
      if(list.length>0){
        this.$message.warning('请先完成子料编辑')
        return true;
      }
    },
    checkRepeated(event){
      var list= this.editProductBom.materialVos.filter((item)=>{return item.productNo==event.productNo
      })
      if(list.length>0){
        this.$message.warning('子料重复！')
        return true;
      }
    },
    materialChanged(event,row){
        if(!this.checkRepeated(event))
        {
          row.chPartNo=event.productNo;
          row.productNo=event.productNo;
          row.chName=event.name
          row.specification=event.specification;
          row.categoryId=event.categoryId;
          row.supplyId=event.supplyId;
          row.salesPrice=event.salesPrice;
          row.supplyName=event.supplyName;
          row.chBomNo=event.bomNo;
          row.chBomGenericId=event.bomGenericId;
        }
        else{
          row.chPartNo="";
          row.productNo="";
          row.chName="";
          row.specification="";
          row.categoryId="";
          row.supplyId="";
          row.salesPrice="";
          row.supplyName="";
          row.chBomNo="";
          row.chBomGenericId="";
          event=null;
        }
    },
    // AddProductChanged(event){
    //   this.addProductBom.productName
    // },
    editSaveProductBom(index,row){
      try {
        if(row.productNo===""|| row.chQty===""||row.lossRate==="" || isNaN(row.chQty)|| isNaN(row.lossRate))
        {
          this.$message.warning("请正确填写信息");
          return false;
        }
        else
        {
          row.seen=false;
          row.notSavedFlag=false;
        }
      } catch (error) {
        this.$message({
          message: "请检查数据",
          type: "warning"
          });
      }


    },
    editDeleteProductBom(index,row){
      row.seen=false;
      row.notSavedFlag=false;
      this.$messageBox.confirm('确认要删除？',"提示",{
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
        .then(() => {
            this.editProductBom.materialVos.splice(index,1)
        })
        .catch(e=>e);
    },
    oneditProductBom(formName){
      if(!this.editCheckSavedFlag())
      {
        if(!this.editCheckEidtable())
        {
          this.$refs[formName].validate(valid=>{
            if(valid){

              if(this.editProductBom.materialVos.length>0)
              {
                  var price=this.editProductBom.processCost;
                  // debugger
                  for( var i = 0; i < this.editProductBom.materialVos.length; i++) {
                    this.editProductBom.materialVos[i].processId=this.editProductBom.processId;
                    this.editProductBom.materialVos[i].processNo=this.editProductBom.processNo;
                    this.editProductBom.materialVos[i].partNo=this.editProductBom.productNo;
                    this.editProductBom.materialVos[i].sIndex=Number(i+1);
                    this.editProductBom.materialVos[i].id="";
                    this.editProductBom.materialVos[i].partName=this.editProductBom.productName;
                    price=Number(price) + Number(this.editProductBom.materialVos[i].salesPrice) *(Number(this.editProductBom.materialVos[i].chQty)+Number(this.editProductBom.materialVos[i].lossRate))
                    if(this.editProductBom.materialVos[i].chBomNo=== "" || this.editProductBom.materialVos[i].chBomNo == null || this.editProductBom.materialVos[i].chBomNo == undefined)
                    {
                      this.editProductBom.materialVos[i].chBomStatus="N"
                    }
                    else{
                      this.editProductBom.materialVos[i].chBomStatus="Y"
                    }
                  }
                  this.editProductBom.costPrice=price;
                  this.editProductBom.status="Y";
                  this.editProductBom.createDate=new Date();
                  this.editProductBom.createBy="";
                  this.editProductBom.bomGenericId=new Date().valueOf();


                    this.SaveProductBom(this.editProductBom)
                    .then(res=>{
                      if(res.resultStatus==1){
                        // this.GetProductList();
                        this.productBomEditDialog=false;
                        this.editProductBom.materialVos=[];
                        this.$message({
                          showClose:true,
                          message:"操作成功",
                          type:"success"
                        });
                      }
                      else{
                        this.$message({
                        message: res.message,
                        type: "warning"
                      });
                      }
                    })
                    .catch(err=>{
                        let alert = err.message ? err.message : err;
                        this.$message.error(alert);
                    });


              }
              else
              {
                this.$message({
                message: "子料清单不能为空",
                type: "warning"
                });
              }
            }
            else{
              this.$message.warning("请填写准确信息");
              return false;
            }
          })
        }
      }
    },
  },
  beforeMount() {
    this.GetProductBomList();
    this.GetProductList();
    this.FindAllProduceProcessList(); 
  },
};
</script>
<style>
.productBom-container {
  display: flex;
  flex-direction: column;
  width: 98%;
  padding: 15px;
}
.productBom-header {
  padding-bottom: 10px;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
}
.productBom-fliter-input {
  width: 400px;
  float: left;
}
.productBominformation{
  display: flex;
  flex-direction: row;
  width: auto;
  justify-content: space-between;
}
.productBomOperation{
  display: flex;
  flex-direction: row;
  width: auto;
  justify-content: space-around;
}
.add-productBom {
  margin-top: 5px;
  width: 100%;
  border: #dcdfe6 dashed 1px !important;
}
.productBom-selected-header {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
  border-bottom: 1px solid lightgray;
}
.productBom-selected-content {
  padding-top: 8px;
}
</style>