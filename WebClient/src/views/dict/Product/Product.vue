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
        <el-table-column prop="productNo" label="产品代码" width="150px"></el-table-column>
        <el-table-column prop="name" label="产品名称"> </el-table-column>
        <el-table-column prop="category" label="品类"> </el-table-column>
        <el-table-column prop="specification" label="规格型号" > </el-table-column>
        <el-table-column prop="unit" label="单位" width="100px"> </el-table-column>
        <el-table-column prop="salePrice" label="售价" width="160px"> </el-table-column>
        <el-table-column prop="storageName" label="仓库"></el-table-column>
        <el-table-column prop="" label="操作" width="100px">
          <template slot-scope="scope">
            <el-tooltip effect="light" content="修改产品信息" placement="top">
              <el-button size="mini" @click="handleEdit(scope.$index,scope.row)" plain circle type="primary" icon="el-icon-edit"/>
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
                size="mini"
                placeholder="选择类别"
                @change="selectCategoryChanged"
              >
                <el-option
                  v-for="vo in categoryList"
                  :label="vo.id"
                  :value="vo.name"
                  :key="vo.id"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="产品规格">
              <el-input v-model="newProduct.specification" autocomplete="off" ></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="6">
          <el-form-item label="产品单位">
            <el-input autocomplete="off"  v-model="newProduct.unit"></el-input>
          </el-form-item>
          </el-col>
          <el-col :span="6">
          <el-form-item label="产品价格">
            <el-input v-model="newProduct.salePrice" autocomplete="off" ></el-input>
          </el-form-item>

          </el-col>
          <el-col :span="6">
            <el-form-item label="存储仓库" prop="storageCode">
              <el-select
                filterable
                v-model="newProduct.storageCode"
                size="mini"
                placeholder="选择仓库"
                @change="selectStorageChanged"
              >
                <el-option
                  v-for="vo in storageList"
                  :label="vo.storageCode"
                  :value="vo.name"
                  :key="vo.storageCode"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="产品标识">
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


    </div>
</template>
<script>
import { mapGetters } from "vuex";
import { mapActions } from "vuex";
export default {
    data(){
      return{
        productEditDialog: false,
        productAddDialog: false,
        searchContent: "",
        loading: false,
        storageList:[],
        categoryList:[],
        newProduct:{
          id:'',
          productNo:'',
          name:'',
          categoryId:'',
          specification:'',
          unit:'',
          salePrice:'',
          remark:'',
          storageCode:'',
          status:'',
          description:'',
          createBy:'',
          createDate:''
        },
        rules: {
        name: [
          { required: true, message: "请输入产品名称", trigger: "blur" }
        ],
        categoryId: [
          { required: true, message: "请选择类别", trigger: "blur" }
        ],
        storageCode: [
          { required: true, message: "请选择仓库", trigger: "blur" }
        ],
        },
      }

    },
    computed: {
      ...mapGetters(["productList"]),
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
      }),
      selectCategoryChanged(){

      },
      selectStorageChanged(){

      },
      resetForm(formName) {
      if (this.$refs[formName]) {
        this.$refs[formName].resetFields();
      }
      this.productAddDialog = true;
      },


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

</style>