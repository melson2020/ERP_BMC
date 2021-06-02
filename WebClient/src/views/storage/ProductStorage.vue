<template>
  <div class="storage-main-div">
    <div class="storage-header-div">
      <span class="colorblue">库存列表</span>
      <div>
        <el-input
          placeholder="请输入内容"
          v-model="searchValue"
          size="small"
          clearable
          @keyup.enter.native="searchOncLick"
        >
          <i slot="prefix" class="el-input__icon el-icon-search"></i>
        </el-input>
      </div>
    </div>
    <el-table
      script
      border
      size="small"
      class="storage-detail-table"
      :data="storageDetailPage.content"
      :span-method="objectSpanMethod"
    >
      <el-table-column prop="materialNo" label="编号"> </el-table-column>
      <el-table-column prop="name" label="名称"> </el-table-column>
      <el-table-column prop="specification" label="规格"> </el-table-column>
      <el-table-column prop="count" label="数量">
        <template slot-scope="scope">
          <span>{{ scope.row.count }}{{ scope.row.unit }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="" width="100px">
        <template slot-scope="scope">
          <el-tooltip
            class="item"
            effect="light"
            content="配置包装单位"
            placement="top-start"
          >
            <el-button
              icon="el-icon-setting"
              circle
              @click="packageSettingOnClick(scope.row)"
              type="primary"
              size="mini"
            ></el-button>
          </el-tooltip>
        </template>
      </el-table-column>
    </el-table>
    <div class="storage-detail-pagenation-div">
      <el-pagination
        background
        layout="prev, pager, next"
        :current-page="currentPage"
        :page-size="pageSize"
        @current-change="pageChanged"
        :page-count="storageDetailPage.totalPages"
      >
      </el-pagination>
    </div>
    <el-dialog
      title="包装配置"
      :visible.sync="storagePackageSetting"
      width="70%"
      @close="handleClose"
    >
      <el-form
        label-position="left"
        label-width="80px"
        :model="storageUnitInfo"
      >
        <el-row :gutter="10">
          <el-col :span="8">
            <el-form-item label="编号" prop="productNo">
              <el-input
                v-model="storageUnitInfo.product.productNo"
              ></el-input> </el-form-item
          ></el-col>
          <el-col :span="8">
            <el-form-item label="名称" prop="name">
              <el-input
                v-model="storageUnitInfo.product.name"
              ></el-input> </el-form-item
          ></el-col>
          <el-col :span="8">
            <el-form-item label="规格" prop="specification">
              <el-input
                v-model="storageUnitInfo.product.specification"
              ></el-input> </el-form-item
          ></el-col>
        </el-row>
        <el-form-item label="基础单位">
          <el-input v-model="storageUnitInfo.product.unit"></el-input>
        </el-form-item>
        <el-form-item label="单位换算">
          <el-table
            :data="storageUnitInfo.storageUnitList"
            border
            size="mini"
            stripe
            style="width: 100%"
          >
            <el-table-column prop="packageUnit" label="包装单位">
              <template slot-scope="scope">
                <el-input
                  v-if="scope.row.seen"
                  v-model="scope.row.packageUnit"
                  size="mini"
                ></el-input>
                <span v-else>{{ scope.row.packageUnit }}</span>
              </template>
            </el-table-column>
            <el-table-column prop="convertCount" label="换算数量">
              <template slot-scope="scope">
                <el-input
                  v-if="scope.row.seen"
                  v-model="scope.row.convertCount"
                  size="mini"
                ></el-input>
                <span v-else>{{ scope.row.convertCount }}</span>
              </template>
            </el-table-column>
            <el-table-column prop="convertUnit" label="换算单位">
              <template slot-scope="scope">
                <el-select
                  size="mini"
                  v-if="scope.row.seen"
                  v-model="scope.row.convertUnit"
                  placeholder="请选择单位"
                >
                  <el-option
                    v-for="unit in packageUnitList(scope.row.packageUnit)"
                    :label="unit.label"
                    :value="unit.value"
                    :key="unit.value"
                  ></el-option>
                </el-select>
                <span v-else>{{ scope.row.convertUnit }}</span>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="120px">
              <template slot-scope="scope">
                <div>
                  <el-button
                    v-if="scope.row.seen"
                    type="primary"
                    icon="el-icon-check"
                    size="mini"
                    @click="storageUnitSave(scope.$index, scope.row)"
                    circle
                  ></el-button>
                  <el-button
                    v-else
                    type="success"
                    icon="el-icon-edit"
                    size="mini"
                    circle
                  ></el-button>
                  <el-button
                    type="danger"
                    icon="el-icon-delete"
                    size="mini"
                    @click="deleteStorageUnit(scope.$index, scope.row)"
                    circle
                  ></el-button>
                </div>
              </template>
            </el-table-column>
          </el-table>
          <el-button
            icon="el-icon-plus"
            plain
            class="add-storage-unit-deail mt40"
            @click="addStorageUnit"
            >添加详细</el-button
          >
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
      searchValue: "",
      currentPage: 1,
      pageSize: 14,
      storagePackageSetting: false,
      storageUnitInfo: {
        product: {},
        storageUnitList: [],
      },
    };
  },

  methods: {
    ...mapActions({
      GetStorageDetail: "GetStorageDetail",
      GetStorageUnitListByProductId: "GetStorageUnitListByProductId",
      SaveStorageUnitOne: "SaveStorageUnitOne",
      DeleteStorageUnitOne: "DeleteStorageUnitOne",
    }),

    pageChanged(page) {
      var params = {
        page: page - 1,
        pageSize: this.pageSize,
        searchValue: this.searchValue,
      };
      this.GetStorageDetail(params);
    },

    handleClose() {
      var params = {
        page: this.currentPage - 1,
        pageSize: this.pageSize,
        searchValue: this.searchValue,
      };
      this.GetStorageDetail(params);
    },
    searchOncLick() {
      var params = {
        page: this.currentPage - 1,
        pageSize: this.pageSize,
        searchValue: this.searchValue,
      };
      this.GetStorageDetail(params);
    },
    packageSettingOnClick(row) {
      this.storagePackageSetting = !this.storagePackageSetting;
      this.GetStorageUnitListByProductId({ productId: row.productId })
        .then((res) => {
          if (res.resultStatus == 1) {
            if (res.data.storageUnitList) {
              res.data.storageUnitList.map((item) => {
                item.seen = false;
              });
            }
            this.storageUnitInfo = res.data;
          } else {
            this.$message.warning(res.message);
          }
        })
        .catch((err) => {
          this.$message.error(err.message);
        });
    },

    deleteStorageUnit(index, row) {
      if (row.id) {
        this.DeleteStorageUnitOne(row)
          .then((res) => {
            if (res.resultStatus == 1) {
              this.storageUnitInfo.storageUnitList.splice(index, 1);
            } else {
              this.$message.warning(res.message);
            }
          })
          .catch((err) => {
            this.$message.error(err.message);
          });
      } else {
        this.storageUnitInfo.storageUnitList.splice(index, 1);
      }
    },

    storageUnitSave(index, row) {
      if (
        row.packageUnit == "" ||
        row.convertCount == "" ||
        row.convertUnit == ""
      ) {
        this.$message.warning("请填写必要数据");
        return;
      }
      this.SaveStorageUnitOne(row)
        .then((res) => {
          if (res.resultStatus == 1) {
            res.data.seen = false;
            this.storageUnitInfo.storageUnitList.splice(index, 1, res.data);
          } else {
            this.$message.warning(res.message);
          }
        })
        .catch((err) => {
          this.$message.error(err.message);
        });
    },

    addStorageUnit() {
      if (
        this.storageUnitInfo.storageUnitList.filter((item) => {
          return item.id == null || item.id == "";
        }).length > 0
      ) {
        this.$message.warning("请先保存");
        return;
      }
      var addObj = {
        productId: this.storageUnitInfo.product.id,
        packageUnit: "",
        convertCount: "",
        convertUnit: "",
        seen: true,
      };
      this.storageUnitInfo.storageUnitList.push(addObj);
    },
    objectSpanMethod({ rowIndex, columnIndex }) {
      if (columnIndex <= 2) {
        const _row = this.spanArr[rowIndex];
        const _col = _row > 0 ? 1 : 0;
        return {
          rowspan: _row,
          colspan: _col,
        };
      }
    },
  },
  computed: {
    ...mapGetters(["storageDetailPage"]),
    packageUnitList() {
      return function (existUnit) {
        var unitList = [];
        if (this.storageUnitInfo.product != "") {
          unitList.push({
            label: this.storageUnitInfo.product.unit,
            value: this.storageUnitInfo.product.unit,
          });
        }
        this.storageUnitInfo.storageUnitList.map((item) => {
          if (item.packageUnit != existUnit) {
            unitList.push({ label: item.packageUnit, value: item.packageUnit });
          }
        });
        return unitList;
      };
    },
    spanArr() {
      let spanRowArr = [];
      let pos = 0;
      for (
        let index = 0;
        index < this.storageDetailPage.content.length;
        index++
      ) {
        if (index == 0) {
          spanRowArr.push(1);
          pos = 0;
        } else {
          var pre = this.storageDetailPage.content[index - 1];
          var current = this.storageDetailPage.content[index];
          if (pre.productId === current.productId) {
            spanRowArr[pos] += 1;
            spanRowArr.push(0);
          } else {
            spanRowArr.push(1);
            pos = index;
          }
        }
      }
      return spanRowArr;
    },
  },

  mounted() {
    var params = {
      page: 0,
      pageSize: this.pageSize,
      searchValue: this.searchValue,
    };
    this.GetStorageDetail(params);
  },
};
</script>
<style>
.storage-header-div {
  display: flex;
  justify-content: space-between;
  height: 50px;
  align-items: center;
}
.storage-main-div {
  padding: 0px 10px;
}
.storage-detail-table {
  min-height: 700px;
}
.storage-detail-pagenation-div {
  padding: 10px;
}
.add-storage-unit-deail {
  width: 100%;
  color: #259dff;
  border: #dcdfe6 dashed 1px !important;
}
</style>