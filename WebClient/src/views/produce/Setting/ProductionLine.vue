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
    >
      <el-form status-icon ref="productionLineAddForm" label-width="100px">
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
        <el-form-item label="工位设置">
          <el-table
            :data="workStationList"
            border
            style="width: 100%"
            size="mini"
            @cell-click="cellClick"
            :show-header="false"
          >
            <el-table-column prop="no" label="序号">
              <template slot-scope="scope">
                <el-input
                  v-model="scope.row.no"
                  v-if="scope.row.seen"
                  @blur="loseFcous(scope.$index, scope.row)"
                  size="mini"
                ></el-input>
                <span v-else>{{ scope.row.no }}</span>
              </template>
            </el-table-column>
            <el-table-column prop="name" label="名称">
              <template slot-scope="scope">
                <el-input
                  v-model="scope.row.name"
                  v-if="scope.row.seen"
                 @blur="loseFcous(scope.$index, scope.row)"
                  size="mini"
                ></el-input>
                <span v-else>{{ scope.row.name }}</span>
              </template>
            </el-table-column>
            <el-table-column prop="">
              <template slot-scope="scope">
                <el-select
                  v-model="scope.row.techNo"
                  placeholder="填入工艺"
                  v-if="scope.row.seen"
                   @blur="loseFcous(scope.$index, scope.row)"
                  size="mini"
                >
                  <el-option label="工序1" value="1"> </el-option>
                  <el-option label="工序2" value="2"> </el-option>
                  <el-option label="工序3" value="3"> </el-option>
                  <el-option label="工序4" value="4"> </el-option>
                </el-select>
                <span v-else>{{ scope.row.techNo }}</span>
              </template>
            </el-table-column>
             <el-table-column prop="">
              <template slot-scope="scope">
                <el-select
                  v-model="scope.row.employeeNo"
                  placeholder="分配人员"
                  v-if="scope.row.seen"
                   @blur="loseFcous(scope.$index, scope.row)"
                  size="mini"
                >
                  <el-option label="组别1" value="1"> </el-option>
                  <el-option label="组别2" value="2"> </el-option>
                  <el-option label="组别3" value="3"> </el-option>
                  <el-option label="组别4" value="4"> </el-option>
                </el-select>
                <span v-else>{{ scope.row.employeeNo }}</span>
              </template>
            </el-table-column>
          </el-table>
          <el-button
            icon="el-icon-plus"
            plain
            class="add-detail"
            @click="addWorkStation"
            >添加工位</el-button
          >
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
      svalue: "",
      workStationList: [{ no: "1", name: "工位1", techNo: "", seen: false,employeeNo:''}],
      productionLineAddDialog: false,
      productionLineList: [
        {
          indexNo: "C01",
          name: "产线1号",
          location: "厂房1楼区域A",
          des: "生产网线,未完全启用",
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
    addWorkStation() {
        this.workStationList.push({ no: "默认", name: "", techNo: "填入工序", seen: false,employeeNo:'分配人员' })
    },
    loseFcous(index, row) {
      row.seen = false;
    },
    cellClick(row) {
      row.seen = true;
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