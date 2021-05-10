<template>
  <div>
    <div class="production-tech-header-div">
      <span class="colorblue production-tech-fwb">工艺字典</span
      ><el-button type="primary" size="small" icon="el-icon-plus" @click="addProduceTech"
        >添加工艺</el-button
      >
    </div>
    <el-table :data="produceTechList" border style="width: 100%" stripe>
      <el-table-column prop="code" label="编号" width="200"> </el-table-column>
      <el-table-column prop="name" label="工序名称" width="200">
      </el-table-column>
      <el-table-column prop="employeeGroupName" label="工作小组">
      </el-table-column>
      <el-table-column prop="description" label="描述"> </el-table-column>
      <el-table-column prop="">
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
            @click="productionTechDelete(scope.row)"
            circle
          ></el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog
      title="工艺"
      :visible.sync="productionTechDialog"
      width="70%"
    >
      <el-form
        :model="editProductionTech"
        ref="productionTechEditForm"
        :rules="rules"
        label-width="100px"
      >
        <el-form-item label="编号" prop="code">
          <el-input
            v-model="editProductionTech.code"
            autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item label="工序名称" prop="name">
          <el-input
            v-model="editProductionTech.name"
            autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item label="工作小组" prop="employeeGroupNo">
          <el-select
            class="production-tech-team-select"
            v-model="editProductionTech.employeeGroupNo"
            placeholder="选择工作小组"
            @change="employeeChange"
          >
            <el-option
              v-for="employeeGroup in employeeGroupList"
              :label="employeeGroup.groupName"
              :value="employeeGroup.groupNo"
              :key="employeeGroup.groupNo"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input
            v-model="editProductionTech.description"
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
        code: [{ required: true, message: "请输入编码", trigger: "blur" }],
        name: [{ required: true, message: "请输入名称", trigger: "blur" }],
        employeeGroupNo: [
          { required: true, message: "请选择组别", trigger: "blur" },
        ],
      },
      productionTechDialog: false,
      editProductionTech: {
        code: "",
        name: "",
        employeeGroupNo: "",
        employeeGroupName: "",
        description: "",
      },
      employeeGroupList: [
        { groupName: "team1", groupNo: "01" },
        { groupName: "team2", groupNo: "02" },
        { groupName: "team3", groupNo: "03" },
        { groupName: "team4", groupNo: "04" },
      ],
    };
  },
  computed: {
    ...mapGetters(["produceTechList"]),
  },
  methods: {
    ...mapActions({
      SaveProduceTechOne: "SaveProduceTechOne",
      FindAllProduceTechList: "FindAllProduceTechList",
      DeleteProduceTech: "DeleteProduceTech",
    }),
    productionTechEdit(row) {
      this.productionTechDialog = !this.productionTechDialog;
      this.editProductionTech = row;
    },
    addProduceTech() {
      this.productionTechDialog = !this.productionTechDialog;
      this.editProductionTech = {
        code: "",
        name: "",
        employeeGroupNo: "",
        employeeGroupName: "",
        description: "",
      };
    },
    onSubmit(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.SaveProduceTechOne(this.editProductionTech)
            .then((res) => {
              if (res.resultStatus == 1) {
                this.productionTechDialog = false;
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
    productionTechDelete(row) {
      this.DeleteProduceTech({ id: row.id });
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
    this.FindAllProduceTechList();
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
.production-tech-team-select {
  width: 100%;
}
</style>