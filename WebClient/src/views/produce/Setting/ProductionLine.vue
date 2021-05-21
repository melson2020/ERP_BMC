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
    <el-table :data="produceLineList" border style="width: 100%">
      <el-table-column prop="code" label="编号" width="200"> </el-table-column>
      <el-table-column prop="name" label="名称" width="200"> </el-table-column>
      <el-table-column prop="location" label="位置"> </el-table-column>
      <el-table-column prop="description" label="描述"> </el-table-column>
      <el-table-column prop="">
        <template slot-scope="scope">
          <el-button
            icon="el-icon-edit"
            type="primary"
            size="mini"
            @click="produceLineEdit(scope.row)"
            circle
          ></el-button>
          <el-button
            icon="el-icon-delete"
            type="danger"
            size="mini"
            @click="produceLineDelete(scope.row)"
            circle
          ></el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog
      title="填写产线信息"
      :visible.sync="productionLineAddDialog"
      width="70%"
    >
      <el-form
        :model="editProductLine"
        status-icon
        :rules="rules"
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
        <el-form-item label="工位设置" prop="workStationList">
          <el-table
            :data="editProductLine.workStationList"
            border
            style="width: 100%"
            size="mini"
            @cell-click="cellClick"
            :show-header="false"
          >
            <el-table-column prop="indexNo" label="序号">
              <template slot-scope="scope">
                <el-input
                  v-model="scope.row.indexNo"
                  v-if="scope.row.seen"
                  @blur="loseFcous(scope.$index, scope.row)"
                  size="mini"
                ></el-input>
                <span v-else>{{ scope.row.indexNo }}</span>
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
                  v-model="scope.row.produceProcessId"
                  placeholder="填入工序"
                  @change="processChange($event, scope.row)"
                  size="mini"
                >
                  <el-option
                    v-for="process in produceProcessList"
                    :label="process.name"
                    :value="process.id"
                    :key="process.id"
                  >
                  </el-option>
                </el-select>
                <!-- <span v-else>{{ scope.row.techId }}</span> -->
              </template>
            </el-table-column>
            <el-table-column prop="">
              <template slot-scope="scope">
                <el-select
                  v-model="scope.row.employeeGroupNo"
                  placeholder="分配人员"
                  size="mini"
                >
                  <el-option
                    v-for="employeeGroup in employeeGroupList"
                    :label="employeeGroup.groupName"
                    :value="employeeGroup.groupNo"
                    :key="employeeGroup.groupNo"
                  ></el-option>
                </el-select>
              </template>
            </el-table-column>
            <el-table-column prop="">
              <template slot-scope="scope">
                <el-button
                  type="danger"
                  icon="el-icon-delete"
                  size="mini"
                  circle
                  @click="
                    deleteWorkStation(
                      scope.$index,
                      scope.row.id,
                      editProductLine.workStationList
                    )
                  "
                ></el-button>
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
          <el-button type="primary" @click="submit('productionLineAddForm')"
            >保存</el-button
          >
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
      rules: {
        code: [{ required: true, message: "请输入编码", trigger: "blur" }],
        name: [{ required: true, message: "请输入名称", trigger: "blur" }],
        location: [{ required: true, message: "选择位置", trigger: "blur" }],
        workStationList: [
          { required: true, message: "请添加工位", trigger: "blur" },
        ],
      },
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
    };
  },
  computed: {
    ...mapGetters(["produceProcessList", "produceLineList"]),
  },
  methods: {
    ...mapActions({
      FindAllProduceProcessList: "FindAllProduceProcessList",
      FindAllProduceLineList: "FindAllProduceLineList",
      SaveProduceLine: "SaveProduceLine",
      AddNewLineToList: "AddNewLineToList",
      FindWorkStationList: "FindWorkStationList",
      DeleteOneWorkStation: "DeleteOneWorkStation",
      DeleteOneProduceLine: "DeleteOneProduceLine",
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
        indexNo:
          this.editProductLine.code +
          "-" +
          (this.editProductLine.workStationList.length + 1),
        name: this.editProductLine.name+(this.editProductLine.workStationList.length + 1)+'号工位',
        techId: "",
        techName: "",
        seen: false,
        employeeGroupNo: "分配组别",
        employeeGroupName: "",
      });
    },
    checkWorkStationList() {
      var pass = true;
      this.editProductLine.workStationList.map((item) => {
        if (
          item.produceProcessId == "" ||
          item.produceProcessId == null ||
          item.employeeGroupNo == "" ||
          item.employeeGroupNo == null
        ) {
          pass = false;
        }
      });
      return pass;
    },
    loseFcous(index, row) {
      row.seen = false;
    },
    cellClick(row) {
      row.seen = true;
    },
    processChange(processId, row) {
      var process = this.produceProcessList.find((t) => {
        return t.id == processId;
      });
      row.produceProcessName = process.name;
    },
    submit(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.checkWorkStationList()) {
            this.SaveProduceLine(this.editProductLine)
              .then((res) => {
                if (res.resultStatus == 1) {
                  this.$message.success("保存成功");
                  this.productionLineAddDialog = !this.productionLineAddDialog;
                  if (!this.editProductLine.id) {
                    this.AddNewLineToList(res.data);
                  }
                } else {
                  this.$message.warning(res.message);
                }
              })
              .catch((err) => {
                this.$message.error(err.message);
              });
          }else{
             this.$message.warning("请完善工位信息");
          }
        } else {
          return false;
        }
      });
    },

    produceLineEdit(row) {
      this.FindWorkStationList({ lineId: row.id })
        .then((res) => {
          if (res.resultStatus == 1) {
            res.data.map((item) => {
              item.seen = false;
            });
            row.workStationList = res.data;
            this.editProductLine = row;
            this.productionLineAddDialog = !this.productionLineAddDialog;
          } else {
            this.$message.warning(res.message);
          }
        })
        .catch((err) => {
          this.$message.error(err.message);
        });
    },

    produceLineDelete(row) {
      this.$messageBox
        .confirm("确认删除产线？", "产线删除", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        })
        .then(() => {
          this.DeleteOneProduceLine({ id: row.id });
        })
        .catch((e) => e);
    },

    deleteWorkStation(index, id, list) {
      if (id) {
        this.$messageBox
          .confirm("确认删除已存在工位？", "工位删除", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning",
          })
          .then(() => {
            this.DeleteOneWorkStation({ id: id })
              .then((res) => {
                if (res.resultStatus == 1) {
                  list.splice(index, 1);
                } else {
                  this.$message.warning(res.message);
                }
              })
              .catch((err) => {
                this.$message.error(err.message);
              });
          })
          .catch((e) => e);
      } else {
        list.splice(index, 1);
      }
    },
  },

  beforeMount() {
    this.FindAllProduceProcessList();
    this.FindAllProduceLineList();
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