<template>
  <div class="product-container">
    <div class="product-header">
    <el-input
      class="product-fliter-input"
      size="small"
      placeholder="搜索产品"
      v-model="searchContent"
      suffix-icon="el-icon-search"
    ></el-input>
    <el-button type="primary" size="small" @click="resetForm('productAddForm')">新建产品</el-button>
    </div>

    <div class="product-content">
      <el-table :data="productListPageShow" 
          border="" stripe           
          :header-row-style="{height:'40px' ,'align':'center'}"
          :row-style="{height:'40px'}"
          style="width: 100%">
        <el-table-column prop="productNo" label="产品代码" width="140px"></el-table-column>
        <el-table-column prop="name" label="产品名称"> </el-table-column>
        <el-table-column prop="category" label="品类"> </el-table-column>
        <el-table-column prop="specification" label="规格型号" > </el-table-column>
        <el-table-column prop="unit" label="计量单位" width="100px"> </el-table-column>
        <el-table-column prop="salePrice" label="价格" width="160px"> </el-table-column>
        <el-table-column prop="storageName" label="仓库"></el-table-column>
        <el-table-column prop="" label="操作" width="130px">
          <template slot-scope="scope">
            <el-tooltip effect="light" content="修改产品信息" placement="top">
              <el-button size="mini" @click="handleEdit(scope.$index,scope.row)" plain circle type="primary" icon="el-icon-edit"/>
            </el-tooltip>
            <el-tooltip effect="light" content="创建产品Bom" placement="top">
              <el-button size="mini" @click="handleCreateBoM(scope.$index,scope.row)" circle type="primary" icon="el-icon-setting"/>
            </el-tooltip>
            <el-tooltip effect="light" content="删除产品信息" placement="top">
              <el-button size="mini" @click="handleDelete(scope.$index, scope.row)" plain circle type="danger" icon="el-icon-delete"/>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>

    </div>

    <div class="product-content-footer"></div>

    <el-dialog title="新建产品" :visible.sync="productAddDialog" :close-on-click-modal="false" width="1024px">
      <el-form status-icon :model="newProduct" :rules="rules" ref="productAddForm" label-width="100px">
          <el-row>
          <el-col :span="12">
            <el-form-item label="产品名称" prop="name">
              <el-input v-model="newProduct.name" autocomplete="off" ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="产品类别" prop="categoryId">
              <el-select
                filterable
                v-model="newProduct.categoryId"
                size="small"
                placeholder="选择类别"
                @change="selectCategoryChanged"
              >
                <el-option
                  v-for="vo in categoryList"
                  :label="vo.name"
                  :value="vo.categoryId"
                  :key="vo.name"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="产品规格" prop="specification">
              <el-input v-model="newProduct.specification" autocomplete="off" ></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="6">
          <el-form-item label="计量单位" prop="unit">
            <el-input autocomplete="off"  v-model="newProduct.unit"></el-input>
          </el-form-item>
          </el-col>
          <el-col :span="6">
          <el-form-item label="产品价格" prop="salesPrice">
            <el-input v-model="newProduct.salesPrice" autocomplete="off" ></el-input>
          </el-form-item>

          </el-col>
          <el-col :span="6">
            <el-form-item label="存储仓库" prop="storageCode">
              <el-select
                filterable
                v-model="newProduct.storageCode"
                size="small"
                placeholder="选择仓库"
                @change="selectStorageChanged"
              >
                <el-option
                  v-for="vo in storageList"
                  :label="vo.name"
                  :value="vo.storageCode"
                  :key="vo.name"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="产品标识" prop="remark">
              <el-input autocomplete="off"  v-model="newProduct.remark"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-form-item label="备注信息" prop="description">
            <el-input
              v-model="newProduct.description"
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
            <el-button type="primary" @click="onAddProduct('productAddForm')" :loading="loading">保存</el-button>
            <el-button @click="productAddDialog = false" v-if="!loading">取 消</el-button>
          </el-form-item>
        </el-row>
      </el-form>
    </el-dialog>


    <el-dialog title="编辑产品" :visible.sync="productEditDialog" :close-on-click-modal="false" width="1024px">
      <el-form status-icon :model="editProduct" :rules="rules" ref="productEditForm" label-width="100px">
          <el-row>
          <el-col :span="12">
            <el-form-item label="产品名称" prop="name">
              <el-input v-model="editProduct.name" autocomplete="off" ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="产品类别" prop="categoryId">
              <el-select
                filterable
                v-model="editProduct.categoryId"
                size="small"
                placeholder="选择类别"
                @change="selectCategoryChanged"
              >
                <el-option
                  v-for="vo in categoryList"
                  :label="vo.name"
                  :value="vo.categoryId"
                  :key="vo.name"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="产品规格" prop="specification">
              <el-input v-model="editProduct.specification" autocomplete="off" ></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="6">
          <el-form-item label="计量单位" prop="unit">
            <el-input autocomplete="off"  v-model="editProduct.unit"></el-input>
          </el-form-item>
          </el-col>
          <el-col :span="6">
          <el-form-item label="产品价格" prop="salesPrice">
            <el-input v-model="editProduct.salesPrice" autocomplete="off" ></el-input>
          </el-form-item>

          </el-col>
          <el-col :span="6">
            <el-form-item label="存储仓库" prop="storageCode">
              <el-select
                filterable
                v-model="editProduct.storageCode"
                size="small"
                placeholder="选择仓库"
                @change="selectStorageChanged"
              >
                <el-option
                  v-for="vo in storageList"
                  :label="vo.name"
                  :value="vo.storageCode"
                  :key="vo.name"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="产品标识" prop="remark">
              <el-input autocomplete="off"  v-model="editProduct.remark"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-form-item label="备注信息" prop="description">
            <el-input
              v-model="editProduct.description"
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
            <el-button type="primary" @click="onEditProduct('productEditForm')" :loading="loading">保存</el-button>
            <el-button @click="productEditDialog = false" v-if="!loading">取 消</el-button>
          </el-form-item>
        </el-row>
      </el-form>
    </el-dialog>



    <el-dialog title="创建产品Bom" :visible.sync="productCreateBoMDialog" :close-on-click-modal="false" width="1024px">
      <el-form status-icon :model="editProductBom" :rules="rules2" ref="productCreateBoMForm" label-width="100px">
          <el-row>
          <el-col :span="6">
            <el-form-item label="产品名称" prop="productName">
              <el-input v-model="editProductBom.productName" autocomplete="off" disabled></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="产品规格" prop="specification">
              <el-input v-model="editProductBom.specification" autocomplete="off" disabled></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
          <el-form-item label="Bom版本" prop="version">
            <el-input autocomplete="off"  v-model="editProductBom.version"></el-input>
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
        <el-row>
          <el-form-item label="Bom列表">
            <el-table
              :data="editProductBom.productBomList"
              border
              style="width: 100%"
              size="mini"
              :show-header="false"
            >
              <el-table-column prop="bomNo" label="Bom:" width="180px" >
                <template slot-scope="scope" >
                  <div class="editProductBomInformation">
                    <el-form-item label="Bom:" prop="bomNo" label-width="45px" >
                      <span>{{ scope.row.bomNo }}</span>
                    </el-form-item>
                  </div>
                </template>
              </el-table-column>
              <el-table-column prop="expirationDate" label="失效日期:" width="180px" >
                <template slot-scope="scope" >
                  <div class="editProductBomInformation">
                    <el-form-item label="失效日期:" prop="expirationDate" label-width="80px" >
                      <span>{{getFullTime(scope.row.expirationDate) }}</span>
                    </el-form-item>
                  </div>
                </template>
              </el-table-column>
              <el-table-column prop="version" label="版本:"  width="145px" >
                <template slot-scope="scope">
                  <div class="editProductBomInformation">
                    <el-form-item label="版本:" prop="version" label-width="55px" >
                      <span>{{ scope.row.version }}</span>
                    </el-form-item>
                  </div>
                </template>
              </el-table-column>
              <el-table-column prop="description" label="描述:" >
                <template slot-scope="scope" >
                  <div class="editProductBomInformation">
                    <el-form-item label="描述:" prop="description" label-width="45px" >
                      <span>{{ scope.row.description }}</span>
                    </el-form-item>
                  </div>
                </template>
              </el-table-column>
              <el-table-column width="80px" >>
                <template slot-scope="scope">
                  <div class="editProductBomOperation">
                    <el-tooltip effect="light" content="查看Bom" placement="top">
                      <el-button size="mini" circle icon="el-icon-view" type="primary" ></el-button>
                    </el-tooltip>
                    <el-tooltip effect="light" v-if="scope.row.status=='Y'" content="停用Bom" placement="top">
                      <el-button size="mini" @click.prevent.stop="UpdateProductBomStatus(scope.$index, scope.row,true)" plain circle type="danger" icon="el-icon-close"/>
                    </el-tooltip>
                    <el-tooltip effect="light" v-else content="启用Bom" placement="top">
                      <el-button size="mini" @click.prevent.stop="UpdateProductBomStatus(scope.$index, scope.row,false)" plain circle type="primary" icon="el-icon-check"/>
                    </el-tooltip>
                  </div>
                </template>
              </el-table-column>

            </el-table>

          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item>
            <el-button type="primary" @click="onEditProductBom('productCreateBoMForm')" :loading="loading">保存</el-button>
            <el-button @click="productCreateBoMDialog = false" v-if="!loading">取 消</el-button>
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
        dateNow:"",
        productEditDialog: false,
        productAddDialog: false,
        productCreateBoMDialog: false,
        searchContent: "",
        loading: false,
        editIndex:'',
        newProduct:{
          id:'',
          productNo:'',
          name:'',
          categoryId:'',
          specification:'',
          unit:'',
          salesPrice:'',
          remark:'',
          storageCode:'',
          status:'',
          description:'',
          createBy:'',
          createDate:''
        },
        editProduct:{
          id:'',
          productNo:'',
          name:'',
          categoryId:'',
          specification:'',
          unit:'',
          salesPrice:'',
          remark:'',
          storageCode:'',
          status:'',
          description:'',
          createBy:'',
          createDate:'',
        },
        editProductBom:{
          id:'',
          productNo:'',
          productName:'',
          specification:'',
          bomNo:'',
          costPrice:'',
          expirationDate:'',
          version:'',
          description:'',
          createBy:'',
          createDate:'',
          productBomList:[],
        },
        rules: {
        name: [
          { required: true, message: "请输入产品名称", trigger: "blur" }
        ],
        categoryId: [
          { required: true, message: "请选择产品类别", trigger: "blur" }
        ],
        storageCode: [
          { required: true, message: "请选择存储仓库", trigger: "blur" }
        ],
        },
        rules2: {
        version: [
          { required: true, message: "请输入Bom版本号", trigger: "blur" }
        ],
        },
      }

    },
    computed: {
      ...mapGetters(["productList","storageList","categoryList","productBomList"]),
      productListPageShow(){
        return this.productList.filter((item)=>{
          let key=
          item.productNo +
          item.name +
          item.specification +
          item.salePrice +
          item.category +
          item.storageName ;
          let index = key.toUpperCase().indexOf(this.searchContent.toUpperCase());
          return index != -1;
        });
      }
    },
    methods:{
      ...mapActions({
      GetProductList:"GetProductList",
      GetCategoryList:"GetCategoryList",
      GetStorageList:"GetStorageList",
      SaveProduct:"SaveProduct",
      PushProductList:"PushProductList",
      QueryProductObj:"QueryProductObj",
      DeleteProduct:"DeleteProduct",
      QueryProductAndBomObj:"QueryProductAndBomObj",
      SaveProductBom:"SaveProductBom",
      UpdateProductBom:"UpdateProductBom",
      GetProductBomList:"GetProductBomList",
      
      }),
      selectCategoryChanged(){


      },
      selectStorageChanged(){

      },
      resetForm(formName) {
      if (this.$refs[formName]) {
        this.$refs[formName].resetFields();
      }
      this.GetStorageList();
      this.GetCategoryList();
      this.productAddDialog = true;
      },


      onAddProduct(formName){
        this.$refs[formName].validate(valid => {
          if (valid) {
            let product=this.newProduct;
            product.productNo="p" + new Date().valueOf();
            product.createDate=new Date();
            product.createBy=""; 
            product.status="Y";
            this.SaveProduct(product)
              .then(res => {
                if (res.resultStatus == 1) {
                  this.productAddDialog = false;
                  // this.PushProductList(res.data);
                  this.GetProductList();
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
      handleDelete(index, row) {
      let prod={id:row.id,productNo:row.productNo,index:index}
       this.$messageBox.confirm('确认要删除？',"提示",{
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
          .then(() => {
            this.DeleteProduct(prod)
          })
          .catch(e=>e);
      },
      onEditProduct(formName){
        this.$refs[formName].validate(valid=>{
          if(valid){
            this.SaveProduct(this.editProduct)
            .then(res=>{
              if(res.resultStatus==1){
                this.productList.splice(this.editIndex,1,res.data);
                  this.editIndex=""
                // this.GetProductList();
                this.productEditDialog=false;
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
      this.GetStorageList();
      this.GetCategoryList();
      let prod={productNo:row.productNo,index:index}
      this.QueryProductObj(prod)
        .then(res=>{
          if (res.resultStatus == 1) {
            this.editProduct=res.data;
            this.productEditDialog = true;
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
      handleCreateBoM(index,row){
        let prod={productNo:row.productNo,index:index}
        this.QueryProductAndBomObj(prod)
        .then(res=>{
          if (res.resultStatus == 1) {
            this.editProductBom.productNo=res.data.productNo;
            this.editProductBom.productName=res.data.name;
            this.editProductBom.specification=res.data.specification;
            this.editProductBom.costPrice=res.data.salesPrice;
            this.editProductBom.description=res.data.description;
            this.editProductBom.productBomList=res.data.productBomList;
            this.editProductBom.expirationDate=new Date().setMonth(new Date().getMonth()+1);
            this.editProductBom.version="";
            this.editProductBom.bomNo="";
            this.editProductBom.id="";
            this.productCreateBoMDialog=true;
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
      getFullTime(time) {
      return new Date(time).format("yyyy-MM-dd");
      },
      onEditProductBom(formName){
          this.$refs[formName].validate(valid=>{
            if(valid){
              this.editProductBom.bomNo="b" + new Date().valueOf();
              this.editProductBom.createDate=new Date();
              this.editProductBom.createBy="";
              this.editProductBom.status="Y";
              this.SaveProductBom(this.editProductBom)
              .then(res=>{
                if(res.resultStatus==1){
                  this.GetProductList();
                  this.productCreateBoMDialog=false;
                  this.editProductBom.productBomList=[];
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
            else{
              this.$message.warning("请填写准确信息");
              return false;
            }
          })

      }



    },
    beforeMount() {
      this.GetProductList();
    },
};
</script>
<style lang="less">
.product-container {
  display: flex;
  flex-direction: column;
}
.product-header {
  padding-bottom: 10px;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
}
.product-fliter-input {
  width: 400px;
  float: left;
}
.editProductBomInformation{
  display: flex;
  flex-direction: row;
  width: auto;
  justify-content: space-between;
}
.editProductBomOperation{
  display: flex;
  flex-direction: row;
  width: auto;
  justify-content: space-around;
}

</style>