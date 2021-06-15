<template>
  <div class="storage-stock-tacking-main-div">
    <div class="storage-stock-tacking-header-div">
      <el-button
        type="primary"
        @click="createStorageTakingOnClick"
        :disabled="inventoryStocktaking.id != null"
        >新建盘点单</el-button
      >
      <el-button
        type="primary"
        @click="printStorageTakingOnClick"
        :disabled="inventoryStocktaking.id == null"
        >打印盘点单</el-button
      >
    </div>
    <div>
      <el-form ref="form" :model="inventoryStocktaking" label-width="80px">
        <el-row :gutter="10">
          <el-col :span="12">
            <el-form-item label="盘点单号">
              <el-input
                v-model="inventoryStocktaking.formNo"
                disabled
                placeholder="系统生成"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="盘点人员">
              <el-input
                v-model="inventoryStocktaking.userName"
                disabled
                placeholder="系统生成"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="10">
          <el-col :span="12">
            <el-form-item label="盘点日期">
              <span class="fl">
                {{ getFullDate(inventoryStocktaking.createDate) }}
              </span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="盘点对象">
              <el-input
                v-model="inventoryStocktaking.storageAreaName"
                disabled
                placeholder="系统生成"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="盘点备注">
          <el-input
            type="textarea"
            v-model="inventoryStocktaking.remark"
            disabled
          ></el-input>
        </el-form-item>
        <el-form-item label="盘点详细">
          <div class="storage-stock-taking-search-div">
            <el-input
              size="small"
              placeholder="搜索盘点详细"
              class="storage-stock-taking-search-input"
              v-model="searchContent"
              suffix-icon="el-icon-search"
            ></el-input>
          </div>
          <el-table
            script
            class="mt40"
            border
            size="mini"
            :data="inventoryStocktakingDetail"
          >
            <el-table-column prop="checked" label="状态" width="80px">
              <template slot-scope="scope">
                <i
                  v-if="scope.row.checked"
                  class="el-icon-check colorgreen"
                ></i>
                <i v-else class="el-icon-close colorred"></i>
              </template>
            </el-table-column>
            <el-table-column prop="materialNo" label="编号"> </el-table-column>
            <el-table-column prop="name" label="名称"> </el-table-column>
            <el-table-column prop="specification" label="规格">
            </el-table-column>
            <el-table-column prop="batchNo" label="批次号"> </el-table-column>
            <el-table-column prop="storageCount" label="库存数量">
              <template slot-scope="scope">
                <span
                  >{{ scope.row.storageCount
                  }}{{ scope.row.storageCountUnit }}</span
                >
              </template>
            </el-table-column>
            <el-table-column prop="tackingCount" label="盘点数量">
              <template slot-scope="scope">
                <div class="flex">
                  <el-input
                    v-if="!scope.row.checked"
                    size="mini"
                    v-model="scope.row.tackingCount"
                  ></el-input>
                  <span v-else>{{ scope.row.tackingCount }}</span>
                  <span class="ml40">{{ scope.row.storageCountUnit }}</span>
                </div>
              </template>
            </el-table-column>
            <el-table-column prop="tackingCount" width="80px">
              <template slot-scope="scope">
                <i
                  v-if="scope.row.flag > 0"
                  class="el-icon-top coloryellow"
                ></i>
                <i
                  v-else-if="scope.row.flag < 0"
                  class="el-icon-bottom colorred"
                ></i>
                <i v-else class="el-icon-check colorgreen"></i>
              </template>
            </el-table-column>
            <el-table-column prop="tackingCount" label="盘点确认" width="60px">
              <template slot-scope="scope">
                <el-button
                  v-if="!scope.row.checked"
                  type="success"
                  icon="el-icon-check"
                  circle
                  @click="checkedOnClick(scope.row)"
                  size="mini"
                ></el-button>
                <el-button
                  v-else
                  type="danger"
                  icon="el-icon-close"
                  circle
                  @click="checkedOnClick(scope.row)"
                  size="mini"
                ></el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="sunbmitOnClick">提交</el-button>
        </el-form-item>
      </el-form>
    </div>
    <el-dialog
      title="新建盘点单"
      :visible.sync="storageTackingDailogVisible"
      width="70%"
    >
      <el-form
        ref="createForm"
        :rules="createRules"
        :model="storageStocktaking"
        label-width="80px"
      >
        <el-row :gutter="10">
          <el-col :span="12">
            <el-form-item label="盘点单号">
              <el-input
                v-model="storageStocktaking.formNo"
                disabled
                placeholder="系统生成"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="盘点人员">
              <el-input
                v-model="storageStocktaking.userName"
                disabled
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="10">
          <el-col :span="12">
            <el-form-item label="盘点日期">
              <span class="fl">
                {{ getFullDate(storageStocktaking.createDate) }}
              </span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="storageCode" label="盘点对象">
              <el-select
                class="w100"
                v-model="storageStocktaking.storageCode"
                placeholder="选择仓库"
                @change="storageAreaChanged"
              >
                <el-option
                  v-for="storage in storageList"
                  :key="storage.id"
                  :label="storage.name"
                  :value="storage.storageCode"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item prop="remark" label="盘点备注">
          <el-input
            type="textarea"
            maxlength="150"
            show-word-limit
            v-model="storageStocktaking.remark"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onCreate('createForm')"
            >创建</el-button
          >
        </el-form-item>
      </el-form>
    </el-dialog>

    <el-dialog
      title="打印盘点单"
      :visible.sync="printDailogVisible"
      width="70%"
    >
      <div id="stockTakingDiv">
        <span>盘点单</span>
        <el-row :gutter="10">
          <el-col :span="8">
            <div class="storage-stock-print-span">
              单号：{{ inventoryStocktaking.formNo }}
            </div>
          </el-col>
          <el-col :span="8">
            <div class="storage-stock-print-span">
              人员：{{ inventoryStocktaking.userName }}
            </div>
          </el-col>
          <el-col :span="8">
            <div class="storage-stock-print-span">
              日期：{{ getFullDate(inventoryStocktaking.createDate) }}
            </div>
          </el-col>
        </el-row>
        <el-row :gutter="10">
          <el-col :span="8">
            <div class="storage-stock-print-span">
              范围：{{ inventoryStocktaking.storageAreaName }}
            </div>
          </el-col>
        </el-row>

        <div class="storage-stock-print-span">
          备注： {{ inventoryStocktaking.remark }}
        </div>

        <el-table
          script
          class="mt40 w100"
          border
          size="mini"
          :data="inventoryStocktakingDetail"
        >
          <el-table-column prop="materialNo" label="编号" width="180px"> </el-table-column>
          <el-table-column prop="name" label="名称"> </el-table-column>
          <el-table-column prop="specification" label="规格"> </el-table-column>
          <el-table-column prop="batchNo" label="批次号"> </el-table-column>
          <el-table-column prop="storageCount" label="库存数量" width="120px">
            <template slot-scope="scope">
              <span
                >{{ scope.row.storageCount
                }}{{ scope.row.storageCountUnit }}</span
              >
            </template>
          </el-table-column>
          <el-table-column prop="tackingCount" label="盘点数量">
            <template slot-scope="scope">
              <div class="flex">
                <el-input size="mini"></el-input>
                <span class="ml40">{{ scope.row.storageCountUnit }}</span>
              </div>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <el-button type="primary" @click="printOnClick">打印</el-button>
    </el-dialog>
  </div>
</template>
<script>
import { mapGetters } from "vuex";
import { mapActions } from "vuex";
import print from "print-js";
export default {
  data() {
    return {
      storageTackingDailogVisible: false,
      printDailogVisible: false,
      searchContent: "",
      storageStocktaking: {
        formNo: "",
        createDate: "",
        userId: "",
        userName: "",
        storageCode: "",
        storageAreaName: "",
        state: "",
        remark: "",
      },
      createRules: {
        remark: [{ required: true, message: "请输入备注", trigger: "blur" }],
        storageCode: [
          { required: true, message: "请选择盘点库", trigger: "blur" },
        ],
      },
    };
  },
  computed: {
    ...mapGetters([
      "storageList",
      "userInfo",
      "inventoryStocktaking",
      "createdDailogCloseFlag",
    ]),

    inventoryStocktakingDetail() {
      let list = [];
      if (this.inventoryStocktaking.detailList) {
        this.inventoryStocktaking.detailList.map((item) => {
          item.flag = item.tackingCount - item.storageCount;
          if (this.searchContent == "") {
            list.push(item);
          } else {
            let key = item.materialNo + item.name + item.specification;
            let index = key
              .toUpperCase()
              .indexOf(this.searchContent.toUpperCase());
            if (index != -1) {
              list.push(item);
            }
          }
        });
      }
      return list;
    },
  },
  watch: {
    createdDailogCloseFlag() {
      if (this.createdDailogCloseFlag) {
        this.storageTackingDailogVisible = false;
        this.SetCreatedDailog(false);
      }
    },
  },
  methods: {
    ...mapActions({
      GetStorageList: "GetStorageList",
      CreateInventoryStockTaking: "CreateInventoryStockTaking",
      SetCreatedDailog: "SetCreatedDailog",
      GetUnCompletedOne: "GetUnCompletedOne",
      SaveInventorayTaking: "SaveInventorayTaking",
      ClearInventoryStocktaking: "ClearInventoryStocktaking",
    }),
    getFullDate(time) {
      if (time) {
        return new Date(time).format("yyyy-MM-dd");
      } else {
        return "";
      }
    },
    createStorageTakingOnClick() {
      this.storageTackingDailogVisible = !this.storageTackingDailogVisible;
      this.storageStocktaking.createDate = new Date();
      this.storageStocktaking.userId = this.userInfo.id;
      this.storageStocktaking.userName = this.userInfo.userName;
    },
    storageAreaChanged(code) {
      let selectStorageArea = this.storageList.find((item) => {
        return item.storageCode == code;
      });
      this.storageStocktaking.storageAreaName = selectStorageArea.name;
    },
    onCreate(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.CreateInventoryStockTaking(this.storageStocktaking);
        } else {
          return false;
        }
      });
    },
    checkedOnClick(row) {
      row.checked = !row.checked;
    },
    sunbmitOnClick() {
      let submitList = this.inventoryStocktaking.detailList.filter((item) => {
        return item.checked;
      });
      if (submitList.length <= 0) {
        this.$message.warning("请完成盘点");
        return;
      }
      if (submitList.length < this.inventoryStocktaking.detailList.length) {
        this.inventoryStocktaking.state = "PROCESSING";
      } else {
        this.inventoryStocktaking.state = "COMPLETED";
      }
      this.SaveInventorayTaking({
        id: this.inventoryStocktaking.id,
        formNo: this.inventoryStocktaking.formNo,
        createDate: this.inventoryStocktaking.createDate,
        userId: this.inventoryStocktaking.userId,
        userName: this.inventoryStocktaking.userName,
        storageCode: this.inventoryStocktaking.storageCode,
        storageAreaName: this.inventoryStocktaking.storageAreaName,
        state: this.inventoryStocktaking.state,
        remark: this.inventoryStocktaking.remark,
        detailList: submitList,
      })
        .then((res) => {
          if (res.resultStatus == 1) {
            this.searchContent = "";
            this.ClearInventoryStocktaking({});
            this.GetUnCompletedOne();
          } else {
            this.$message.warning(res.message);
          }
        })
        .catch((err) => {
          this.$message.error(err.message);
        });
    },

    printStorageTakingOnClick() {
      this.printDailogVisible = !this.printDailogVisible;
    },
    printOnClick() {
      print({
        printable: "stockTakingDiv",
        type: "html",
        maxWidth: "100%",
        targetStyles: ["*"],
      });
    },
  },
  mounted() {
    this.GetStorageList();
    this.GetUnCompletedOne();
  },
};
</script>
<style>
@page {
 margin:0
}
.storage-stock-tacking-header-div {
  height: 50px;
  line-height: 50px;
  text-align: right;
}
.storage-stock-tacking-main-div {
  padding: 5px 10px;
}
.storage-stock-taking-search-input {
  width: 400px;
  text-align: right;
}
.storage-stock-taking-search-div {
  display: flex;
  flex-direction: row-reverse;
}
.storage-stock-print-span {
  width: 100%;
  text-align: left;
  padding: 10px 0px;
}
</style>