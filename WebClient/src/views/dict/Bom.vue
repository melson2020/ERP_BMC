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

        <el-table-column prop="" label="操作" width="130px">
          <template slot-scope="scope">
            <el-tooltip effect="light" content="查看Bom详情" placement="top">
              <el-button size="mini" circle icon="el-icon-view" type="primary" ></el-button>
            </el-tooltip>
            <el-tooltip effect="light" content="修改Bom信息" placement="top">
              <el-button size="mini" @click="handleEdit(scope.$index,scope.row)" plain circle type="primary" icon="el-icon-edit"/>
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
    <el-dialog title="编辑产品Bom" :visible.sync="productBomEditDialog" :close-on-click-modal="false" width="1024px">
      <el-form status-icon :model="editProductBom" :rules="rules" ref="productEditBoMForm" label-width="100px">
          <el-row>
          <el-col :span="8">
            <el-form-item label="产品编码" prop="productNo">
              <el-input autocomplete="off"  v-model="editProductBom.productNo" disabled></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="产品名称" prop="productName">
              <el-input v-model="editProductBom.productName" autocomplete="off" disabled></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="产品规格" prop="specification">
              <el-input v-model="editProductBom.specification" autocomplete="off" disabled></el-input>
            </el-form-item>
          </el-col>


        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="Bom编码" prop="bomNo">
              <el-input v-model="editProductBom.bomNo" autocomplete="off" disabled></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="Bom版本" prop="version">
              <el-input autocomplete="off"  v-model="editProductBom.version"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="失效日期" prop="expirationDate">
              <el-date-picker
                v-model="editProductBom.expirationDate"
                type="date"
                style="width:227px"
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
          <el-form-item>
            <el-button type="primary" @click="onEditProductBom('productEditBoMForm')" :loading="loading">保存</el-button>
            <el-button @click="productBomEditDialog = false" v-if="!loading">取 消</el-button>
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
      productBomEditDialog: false,
      searchContent: "",
      loading: false,
      editIndex:'',
      editProductBom:{
          id: '',
          productNo: '',
          productName: '',
          specification: '',
          bomNo: '',
          costPrice:'',
          expirationDate:'',
          version:'',
          description:'',
          createBy:'',
          createDate:'',
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
    ...mapGetters(["productBomList"]),
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
    }
  },
  methods:{
        ...mapActions({
      GetProductBomList:"GetProductBomList",
      QueryProductBomObj:"QueryProductBomObj",
      SaveProductBom:"SaveProductBom",
      UpdateProductBom:"UpdateProductBom"
    }),
    handleEdit(index,row){
      this.editIndex=index;
      let productBom={id:row.id,bomNo:row.bomNo,index:index}
      this.QueryProductBomObj(productBom)
          .then(res=>{
          if (res.resultStatus == 1) {
              this.editProductBom=res.data;
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

    onEditProductBom(formName){
        this.$refs[formName].validate(valid=>{
          if(valid){
            this.SaveProductBom(this.editProductBom)
            .then(res=>{
                if(res.resultStatus==1){ 
                  this.productBomList.splice(this.editIndex,1,res.data);
                  this.editIndex=""
                  // this.GetProductBomList();
                  this.productBomEditDialog=false;
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
    },
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
  },
  beforeMount() {
    this.GetProductBomList();
  },
};
</script>
<style>
.productBom-container {
  display: flex;
  flex-direction: column;
  width: 98%;
  padding: 10px;
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

</style>