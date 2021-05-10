<template>
  <div>
    <div class="product-line-header-div">
      <span class="colorblue production-tech-fwb">产线列表</span
      ><el-button
        type="primary"
        size="small"
        icon="el-icon-plus"
        @click="addProductionLine"
        >添加产线</el-button
      >
    </div>
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

    <el-dialog
      title="添加产线"
      :visible.sync="productionLineAddDialog"
      width="70%"
    >
      <el-form
        :model="editProductLine"
        status-icon
        ref="productionLineAddForm"
        label-width="100px"
      >
        <el-form-item label="编号" prop="code">
          <el-input v-model="editProductLine.code"></el-input>
        </el-form-item>
        <el-form-item label="名称" prop="name">
          <el-input v-model="editProductLine.name"></el-input>
        </el-form-item>
        <el-form-item label="位置" prop="location">
          <el-input v-model="editProductLine.location"></el-input>
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input v-model="editProductLine.description"></el-input>
        </el-form-item>
        <el-form-item label="工位设置">
          <el-table
            :data="editProductLine.workStationList"
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
                  placeholder="输入名称"
                  @blur="loseFcous(scope.$index, scope.row)"
                  size="mini"
                ></el-input>
                <span v-else>{{ scope.row.name }}</span>
              </template>
            </el-table-column>
            <el-table-column prop="">
              <template slot-scope="scope">
                <el-select
                  v-model="scope.row.techId"
                  placeholder="填入工艺"
                  v-if="scope.row.seen"
                  @change="techChang($event, scope.row)"
                  @blur="loseFcous(scope.$index, scope.row)"
                  size="mini"
                >
                  <el-option
                    v-for="tech in produceTechList"
                    :label="tech.name"
                    :value="tech.id"
                    :key="tech.id"
                  >
                  </el-option>
                </el-select>
                <span v-else>{{ scope.row.techId }}</span>
              </template>
            </el-table-column>
            <el-table-column prop="">
              <template slot-scope="scope">
                <el-select
                  v-model="scope.row.employeeGroupNo"
                  placeholder="分配人员"
                  v-if="scope.row.seen"
                  @blur="loseFcous(scope.$index, scope.row)"
                  size="mini"
                >
                  <el-option
                    v-for="employeeGroup in employeeGroupList"
                    :label="employeeGroup.groupName"
                    :value="employeeGroup.groupNo"
                    :key="employeeGroup.groupNo"
                  ></el-option>
                </el-select>
                <span v-else>{{ scope.row.employeeGroupNo }}</span>
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
          <el-button type="primary" @click="submit">保存</el-button>
          <el-button>重置</el-button>
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
      workStationList: [
        { no: "1", name: "工位1", techNo: "", seen: false, employeeNo: "" },
      ],
      productionLineAddDialog: false,
      employeeGroupList: [
        { groupName: "team1", groupNo: "01" },
        { groupName: "team2", groupNo: "02" },
        { groupName: "team3", groupNo: "03" },
        { groupName: "team4", groupNo: "04" },
      ],
      editProductLine: {
        code: "",
        name: "",
        location: "",
        description: "",
        workStationList: [],
      },
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
  computed: {
    ...mapGetters(["produceTechList"]),
  },
  methods: {
    ...mapActions({
      FindAllProduceTechList: "FindAllProduceTechList",
      SaveProduceLine:'SaveProduceLine'
    }),
    addProductionLine() {
      this.productionLineAddDialog = !this.productionLineAddDialog;
      this.editProductLine = {
        code: "",
        name: "",
        location: "",
        description: "",
        workStationList: [],
      };
    },
    addWorkStation() {
      this.editProductLine.workStationList.push({
        index: this.editProductLine.workStationList.length + 1,
        name: "请填写名称",
        techId: "",
        techName:'',
        seen: false,
        employeeGroupNo: "分配组别",
        employeeGroupName:''
      });
    },
    loseFcous(index, row) {
      row.seen = false;
    },
    cellClick(row) {
      row.seen = true;
    },
    techChang(techId,row){
       console.log('111')
       var tech=this.produceTechList.find(t=>{return t.id==techId})
       console.log(tech,row)
        row.techName=tech.name
    },
    submit(){
      this.SaveProduceLine(this.editProductLine)
    }
  },

  beforeMount() {
    this.FindAllProduceTechList();
  },
};
</script>
<style>
.product-line-header-div {
  height: 50px;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
}
.add-detail {
  margin-top: 5px;
  width: 100%;
  border: #dcdfe6 dashed 1px !important;
}
</style>