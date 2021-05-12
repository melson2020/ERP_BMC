<template>
    <div class="Material-container">
    <div class="Material-header">
    <el-input
      class="Material-fliter-input"
      size="small"
      placeholder="搜索基础物料"
      v-model="searchContent"
      suffix-icon="el-icon-search"
    ></el-input>
    <el-button type="primary" size="small" @click="resetForm('MaterialAddForm')">添加物料</el-button>
    </div>

    <div class="Material-content">
      <el-table :data="MaterialListPageShow" 
          border="" stripe           
          :header-row-style="{height:'40px' ,'align':'center'}"
          :row-style="{height:'40px'}"
          style="width: 100%">
        <el-table-column prop="partNo" label="物料代码" width="180px"></el-table-column>
        <el-table-column prop="name" label="物料名称"> </el-table-column>
        <el-table-column prop="unit" label="单位" width="80px"> </el-table-column>
        <el-table-column prop="storageName" label="存储仓库" width="200px"> </el-table-column>
        <el-table-column prop="manufacturer" label="制造商"> </el-table-column>
        <el-table-column prop="specification" label="规格" width="120px"> </el-table-column>
        <el-table-column label="重量" width="90px">
          <template slot-scope="scope"><span>{{scope.row.weight}}{{scope.row.weightUnit}}</span>
          </template>
          </el-table-column>
        <!-- <el-table-column prop="weightUnit" label="重量单位" width="80px"> </el-table-column> -->
        <el-table-column label="体积" width="90px"> 
          <template slot-scope="scope"><span>{{scope.row.volume}}{{scope.row.volumeUnit}}</span>
          </template>
        </el-table-column>
        <el-table-column prop="semiGoods" label="半成品" width="80px"> </el-table-column>
        <el-table-column prop="expireDate" label="失效日期" width="140px"> 
          <template slot-scope="scope">
            <span>{{getFullTime(scope.row.expireDate) }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="" label="操作" width="100px">
          <template slot-scope="scope">
            <el-tooltip effect="light" content="修改物料信息" placement="top">
              <el-button size="mini" @click="handleEdit(scope.$index,scope.row)" plain circle type="primary" icon="el-icon-edit"/>
            </el-tooltip>
            <el-tooltip effect="light" content="删除物料信息" placement="top">
              <el-button size="mini" @click="handleDelete(scope.$index, scope.row)" plain circle type="danger" icon="el-icon-delete"/>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>

    </div>

    <div class="Material-content-footer"></div>

    <el-dialog title="新建基础物料" :visible.sync="MaterialAddDialog" :close-on-click-modal="false" width="1024px">
      <el-form status-icon :model="newMaterial" :rules="rules" ref="MaterialAddForm" label-width="100px">
        <el-row>
          <el-col :span="8">
            <el-form-item label="物料名称" prop="name">
              <el-input v-model="newMaterial.name" autocomplete="off" ></el-input>
            </el-form-item>
            </el-col>
          <el-col :span="8">
            <el-form-item label="制造商" prop="manufacturer">
              <el-input v-model="newMaterial.manufacturer" autocomplete="off" ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="失效日期" prop="expireDate">
              <el-date-picker
                v-model="newMaterial.expireDate"
                type="date"
                placeholder="选择日期">
              </el-date-picker>
            </el-form-item>

            </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">              
            <el-form-item label="计量单位" prop="unit">
                <el-input v-model="newMaterial.unit" autocomplete="off" ></el-input>
            </el-form-item>

          </el-col>
          <el-col :span="6">
            <el-form-item label="物料规格" prop="specification">
              <el-input v-model="newMaterial.specification" autocomplete="off" ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="物料重量" prop="weight">
              <el-input v-model="newMaterial.weight" autocomplete="off" ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="重量单位" prop="weightUnit">
              <el-input v-model="newMaterial.weightUnit" autocomplete="off" ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <el-form-item label="物料体积" prop="volume">
              <el-input v-model="newMaterial.volume" autocomplete="off" ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="存储仓库" prop="storageCode">
              <el-select
                filterable
                v-model="newMaterial.storageCode"
                size="mini"
                placeholder="选择仓库"
                @change="selectStorageChanged">
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
            <el-form-item label="体积单位" prop="volumeUnit">
              <el-input v-model="newMaterial.volumeUnit" autocomplete="off" ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="半成品" prop="searchType">
              <el-checkbox
                v-model="newMaterial.semiGoods"
                true-label="Y"
                false-label=""
                >是否半成品</el-checkbox
              >
            </el-form-item>
          </el-col>

        </el-row>
        <el-row>
          <el-form-item label="备注信息" prop="description">
            <el-input
              v-model="newMaterial.description"
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
            <el-button type="primary" @click="onAddMaterial('MaterialAddForm')" :loading="loading">保存</el-button>
            <el-button @click="MaterialAddDialog = false" v-if="!loading">取 消</el-button>
          </el-form-item>
        </el-row>
      </el-form>
    </el-dialog>

    <el-dialog title="编辑基础物料" :visible.sync="MaterialEditDialog" :close-on-click-modal="false" width="1024px">
      <el-form status-icon :model="editMaterial" :rules="rules" ref="MaterialEditForm" label-width="100px">
        <el-row>
          <el-col :span="8">
            <el-form-item label="物料名称" prop="name">
              <el-input v-model="editMaterial.name" autocomplete="off" ></el-input>
            </el-form-item>
            </el-col>
          <el-col :span="8">
            <el-form-item label="制造商" prop="manufacturer">
              <el-input v-model="editMaterial.manufacturer" autocomplete="off" ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="失效日期" prop="expireDate">
              <el-date-picker
                v-model="editMaterial.expireDate"
                type="date"
                placeholder="选择日期">
              </el-date-picker>
            </el-form-item>

            </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">              
            <el-form-item label="计量单位" prop="unit">
                <el-input v-model="editMaterial.unit" autocomplete="off" ></el-input>
            </el-form-item>

          </el-col>
          <el-col :span="6">
            <el-form-item label="物料规格" prop="specification">
              <el-input v-model="editMaterial.specification" autocomplete="off" ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="物料重量" prop="weight">
              <el-input v-model="editMaterial.weight" autocomplete="off" ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="重量单位" prop="weightUnit">
              <el-input v-model="editMaterial.weightUnit" autocomplete="off" ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <el-form-item label="物料体积" prop="volume">
              <el-input v-model="editMaterial.volume" autocomplete="off" ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="存储仓库" prop="storageCode">
              <el-select
                filterable
                v-model="editMaterial.storageCode"
                size="mini"
                placeholder="选择仓库">
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
            <el-form-item label="体积单位" prop="volumeUnit">
              <el-input v-model="editMaterial.volumeUnit" autocomplete="off" ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="半成品" prop="searchType">
              <el-checkbox
                v-model="editMaterial.semiGoods"
                true-label="Y"
                false-label=""
                >是否半成品</el-checkbox
              >
            </el-form-item>
          </el-col>

        </el-row>
        <el-row>
          <el-form-item label="备注信息" prop="description">
            <el-input
              v-model="editMaterial.description"
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
            <el-button type="primary" @click="onEditMaterial('MaterialEditForm')" :loading="loading">保存</el-button>
            <el-button @click="MaterialEditDialog = false" v-if="!loading">取 消</el-button>
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
        MaterialEditDialog: false,
        MaterialAddDialog: false,
        searchContent: "",
        loading: false,
        newMaterial:{
          id:'',
          partNo:'',
          name:'',
          unit:'',
          MaterialCode:'',
          manufacturer:'',
          specification:'',
          weight:'',
          weightUnit:'',
          volume:'',
          volumeUnit:'',
          semiGoods:'',
          status:'',
          expireDate:'',
          description:'',
          createBy:'',
          createDate:'',
        },
        editMaterial:{
          id:'',
          partNo:'',
          name:'',
          unit:'',
          MaterialCode:'',
          manufacturer:'',
          specification:'',
          weight:'',
          weightUnit:'',
          volume:'',
          volumeUnit:'',
          semiGoods:'',
          status:'',
          expireDate:'',
          description:'',
          createBy:'',
          createDate:'',
        },
        rules: {
        name: [
          { required: true, message: "请输入产品名称", trigger: "blur" }
        ],
        // categoryId: [
        //   { required: true, message: "请选择产品类别", trigger: "blur" }
        // ],
        // storageCode: [
        //   { required: true, message: "请选择存储仓库", trigger: "blur" }
        // ],
        },
    }

  },
  methods:{
    ...mapActions({
    GetMaterialList:"GetMaterialList",
    SaveMaterial:"SaveMaterial",
    PushMaterialList:"PushMaterialList",
    QueryMaterialObj:"QueryMaterialObj",
    DeleteMaterial:"DeleteMaterial",
          GetStorageList:"GetStorageList",
    }),
    onAddMaterial(formName){
      this.$refs[formName].validate(valid => {
        if (valid) {
          let Material=this.newMaterial;
          Material.partNo="m" + new Date().valueOf();
          Material.createDate=new Date();
          Material.createBy=""; 
          Material.status="Y"; 
          this.SaveMaterial(Material)
            .then(res => {
              if (res.resultStatus == 1) {
                this.MaterialAddDialog = false;
                // this.PushMaterialList(res.data);
                this.GetMaterialList();
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
    resetForm(formName) {
    if (this.$refs[formName]) {
      this.$refs[formName].resetFields();
    }
          this.GetStorageList();
    this.MaterialAddDialog = true;
    },
    handleEdit(index,row){
      let cat={id:row.id,partNo:row.partNo,index:index}
      this.QueryMaterialObj(cat)
        .then(res=>{
          if (res.resultStatus == 1) {
            this.editMaterial=res.data;
            this.MaterialEditDialog = true;
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
    let cat={id:row.id,partNo:row.partNo,index:index}
      this.$messageBox.confirm('确认要删除？',"提示",{
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          // this.DisableMaterial(cat)//
          this.DeleteMaterial(cat)
        })
        .catch(e=>e);
    },
    onEditMaterial(formName){
      this.$refs[formName].validate(valid=>{
        if(valid){
          this.SaveMaterial(this.editMaterial)
          .then(res=>{
            if(res.resultStatus==1){
              this.GetMaterialList();
              this.MaterialEditDialog=false;
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
    getFullTime(time) {
      return new Date(time).format("yyyy-MM-dd");
    },
    selectStorageChanged(storageCode){
      // console.log(storageCode)
    }

  },
  computed:{
    ...mapGetters(["materialList","storageList"]),
    MaterialListPageShow(){
      return this.materialList.filter((item)=>{
        let key=
        item.partNo +
        item.name +
        item.unit +
        item.MaterialName +
        item.manufacturer +
        item.specification +
        item.weight +
        item.weightUnit +
        item.volume +
        item.volumeUnit +
        item.semiGoods ;
        let index = key.toUpperCase().indexOf(this.searchContent.toUpperCase());
        return index != -1;
      });
    }
  },
  beforeMount() {
    this.GetMaterialList();
  },
};
</script>
<style>
.Material-container {
  display: flex;
  flex-direction: column;
  width: 98%;
  padding: 10px;
}
.Material-header {
  padding-bottom: 10px;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
}
.Material-fliter-input {
  width: 400px;
  float: left;
}
</style>