<template>
  <div>
    <el-table :data="productionLineList" border style="width: 100%">
      <el-table-column prop="indexNo" label="编号" width="200">
      </el-table-column>
      <el-table-column prop="name" label="名称" width="200"> </el-table-column>
      <el-table-column prop="location" label="位置"> </el-table-column>
      <el-table-column prop="des" label="描述"> </el-table-column>
      <el-table-column prop="">
        <template>
          <el-button
            icon="el-icon-edit"
            type="primary"
            size="mini"
            circle
          ></el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-button
      icon="el-icon-plus"
      plain
      class="add-detail"
      @click="addProductionLine"
      >添加产线</el-button
    >
    <el-dialog
      title="添加产线"
      :visible.sync="productionLineAddDialog"
      width="60%"
      :before-close="handleClose"
    >
      <el-form status-icon ref="ruleForm" label-width="100px">
        <el-form-item label="编号" prop="pass">
          <el-input autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="名称" prop="checkPass">
          <el-input autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="位置" prop="pass">
          <el-input autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="描述" prop="checkPass">
          <el-input autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary">保存</el-button>
          <el-button>重置</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>
<script>
export default {
  data() {
    return {
      productionLineAddDialog: false,
      productionLineList: [
        {
          indexNo: "C01",
          name: "产线1号",
          location: "厂房1楼区域A",
          des: "生产网页,未完全启用",
        },
        {
          indexNo: "X02",
          name: "产线2号",
          location: "厂房2楼区域B",
          des: "生产USB,全线运行中",
        },
        {
          indexNo: "S01",
          name: "产线3号",
          location: "厂房1楼区域A",
          des: "生产路由器,未完全启用",
        },
        {
          indexNo: "S02",
          name: "产线4号",
          location: "厂房1楼区域A",
          des: "生产路由器,未完全启用",
        },
      ],
    };
  },
  methods: {
    addProductionLine() {
      this.productionLineAddDialog = !this.productionLineAddDialog;
    },
  },
};
</script>
<style>
.add-detail {
  margin-top: 5px;
  width: 100%;
  border: #dcdfe6 dashed 1px !important;
}
</style>