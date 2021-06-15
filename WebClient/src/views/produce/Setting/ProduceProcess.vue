<template>
  <div>
    <div class="production-tech-header-div">
      <span class="colorblue production-tech-fwb">工序字典</span
      ><el-button
        type="primary"
        size="small"
        icon="el-icon-plus"
        @click="addProduceTech"
        >添加工序</el-button
      >
    </div>
    <el-table :data="produceProcessList" border style="width: 100%" stripe>
      <el-table-column prop="processNo" label="编号" width="200">
      </el-table-column>
      <el-table-column prop="name" label="工序名称" width="200">
      </el-table-column>
      <el-table-column prop="time" label="用时" width="200">
        <template slot-scope="scope">
          <span>{{ scope.row.time }}{{ scope.row.timeUnit }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="cost" label="成本" width="100"></el-table-column>
      <el-table-column prop="delegate" label="是否委外" width="100">
        <template slot-scope="scope">
          <span v-if="scope.row.delegateFlag == 'Y'">是</span>
          <span v-else>否</span>
        </template>
      </el-table-column>
      <el-table-column prop="description" label="描述"> </el-table-column>
      <el-table-column prop="" width="200">
        <template slot-scope="scope">
          <el-button
            icon="el-icon-edit"
            type="primary"
            size="mini"
            @click="productionTechEdit(scope.row)"
            circle
          ></el-button>
          <el-button
            icon="el-icon-delete"
            type="danger"
            size="mini"
            @click="productionProcessDelete(scope.row)"
            circle
          ></el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog title="工序" :visible.sync="productionTechDialog" width="70%">
      <el-form
        :model="editProduceProcess"
        ref="productionTechEditForm"
        :rules="rules"
        label-width="100px"
      >
        <el-form-item label="工序名称" prop="name">
          <el-input
            v-model="editProduceProcess.name"
            autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item label="成本" prop="cost">
          <el-input
            v-model="editProduceProcess.cost"
            autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item label="工序用时" required>
          <el-col :span="11">
            <el-form-item prop="time">
              <el-input-number
                class="produce-process-edit-select"
                v-model="editProduceProcess.time"
                controls-position="right"
              ></el-input-number>
            </el-form-item>
          </el-col>
          <el-col class="line" :span="2">单位</el-col>
          <el-col :span="11">
            <el-form-item prop="timeUnit">
              <el-select
                class="produce-process-edit-select"
                v-model="editProduceProcess.timeUnit"
                placeholder="单位"
                @change="employeeChange"
              >
                <el-option
                  v-for="timeUnit in timeUnitList"
                  :label="timeUnit.label"
                  :value="timeUnit.value"
                  :key="timeUnit.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-form-item>
        <el-form-item label="是否委外" prop="delegateFlag">
          <el-select
            class="produce-process-edit-select"
            v-model="editProduceProcess.delegateFlag"
            placeholder="是否委外"
          >
            <el-option label="是" value="Y"></el-option>
            <el-option label="否" value="N"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input
            v-model="editProduceProcess.description"
            maxlength="150"
            show-word-limit
            type="textarea"
            autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit('productionTechEditForm')"
            >保存</el-button
          >
          <el-button @click="cancelOnClick">取消</el-button>
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
        cost: [{ required: true, message: "请输入成本", trigger: "blur" }],
        delegateFlag: [
          { required: true, message: "选择委外", trigger: "blur" },
        ],
        name: [{ required: true, message: "请输入名称", trigger: "blur" }],
        time: [{ required: true, message: "选择时间", trigger: "blur" }],
        timeUnit: [{ required: true, message: "选择单位", trigger: "blur" }],
      },
      productionTechDialog: false,
      editProduceProcess: {
        name: "",
        time: "",
        timeUnit: "",
        cost: "",
        delegateFlag: "",
        description: "",
      },
      timeUnitList: [
        { label: "小时", value: "H" },
        { label: "分钟", value: "M" },
      ],
    };
  },
  computed: {
    ...mapGetters(["produceProcessList"]),
  },
  methods: {
    ...mapActions({
      SaveProduceProcessOne: "SaveProduceProcessOne",
      FindAllProduceProcessList: "FindAllProduceProcessList",
      DeleteProduceProcess: "DeleteProduceProcess",
    }),
    productionTechEdit(row) {
      this.productionTechDialog = !this.productionTechDialog;
      this.editProduceProcess = row;
    },
    addProduceTech() {
      this.productionTechDialog = !this.productionTechDialog;
      this.editProduceProcess = {
        name: "",
        time: "",
        timeUnit: "",
        cost: "",
        delegateFlag: "",
        description: "",
      };
    },
    onSubmit(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.SaveProduceProcessOne(this.editProduceProcess)
            .then((res) => {
              if (res.resultStatus == 1) {
                this.productionTechDialog = false;
                this.FindAllProduceProcessList();
                this.$message.success("保存成功");
              } else {
                this.$message.warning(res.message);
              }
            })
            .catch((err) => {
              this.$message.error(err.message);
            });
        } else {
          return false;
        }
      });
    },
    productionProcessDelete(row) {
      this.$confirm("是否删除?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.DeleteProduceProcess({ id: row.id });
        })
        .catch(() => {});
    },
    cancelOnClick() {
      this.productionTechDialog = !this.productionTechDialog;
    },
    employeeChange(item) {
      var selectGroup = this.employeeGroupList.find((g) => {
        return g.groupNo == item;
      });
      this.editProductionTech.employeeGroupName = selectGroup.groupName;
    },
  },

  beforeMount() {
    this.FindAllProduceProcessList();
  },
};
</script>
<style>
.production-tech-header-div {
  display: flex;
  height: 50px;
  align-items: center;
  justify-content: space-between;
}
.production-tech-fwb {
  font-weight: bold;
}
.produce-process-edit-select {
  width: 100%;
}
.produce-process-edit-div {
  width: 100%;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
}
</style>