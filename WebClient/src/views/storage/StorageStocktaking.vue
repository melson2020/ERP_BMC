<template>
  <div class="storage-stock-tacking-main-div">
    <div class="storage-stock-tacking-header-div">
      <el-button type="primary" @click="createStorageTakingOnClick"
        >新建盘点单</el-button
      >
    </div>
    <div>
      <el-form ref="form" :model="storageStocktaking" label-width="80px">
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
                placeholder="系统生成"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="10">
          <el-col :span="12">
            <el-form-item label="盘点日期">
              <el-input
                v-model="storageStocktaking.createDate"
                disabled
                placeholder="系统生成"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="盘点对象">
              <el-input
                v-model="storageStocktaking.storageCode"
                disabled
                placeholder="系统生成"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="盘点备注">
          <el-input
            type="textarea"
            v-model="storageStocktaking.remark"
            disabled
          ></el-input>
        </el-form-item>
        <el-form-item label="盘点详细">
          <el-table script border size="mini" :data="produceInBoundList">
            <el-table-column prop="planNo" label="编号"> </el-table-column>
            <el-table-column prop="customerName" label="客户">
            </el-table-column>
            <el-table-column prop="orderFormNo" label="订单号">
            </el-table-column>
            <el-table-column label="" width="80px">
              <template>
                <el-button
                  type="success"
                  icon="el-icon-check"
                  size="mini"
                  circle
                ></el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit">提交</el-button>
        </el-form-item>
      </el-form>
    </div>
    <el-dialog
      title="新建盘点单"
      :visible.sync="storageTackingDailogVisible"
      width="70%"
      :before-close="handleClose"
    >
      <el-form ref="createForm" :model="storageStocktaking" label-width="80px">
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
            <el-form-item label="盘点对象">
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
        <el-form-item label="盘点备注">
          <el-input
            type="textarea"
            v-model="storageStocktaking.remark"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit">创建</el-button>
        </el-form-item>
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
      storageTackingDailogVisible: false,
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
    };
  },
  computed: {
    ...mapGetters(["storageList", "userInfo"]),
  },
  methods: {
    ...mapActions({
      GetStorageList: "GetStorageList",
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
  },
  mounted() {
    this.GetStorageList();
  },
};
</script>
<style>
.storage-stock-tacking-header-div {
  height: 50px;
  line-height: 50px;
  text-align: right;
}
.storage-stock-tacking-main-div {
  padding: 5px 10px;
}
</style>