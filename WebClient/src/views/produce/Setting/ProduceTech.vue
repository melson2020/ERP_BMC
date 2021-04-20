<template>
  <div>
    <div class="production-tech-header-div">
      <span class="colorblue production-tech-fwb">工序字典</span
      ><el-button type="primary" size="small">添加工序</el-button>
    </div>
    <el-table :data="productionTechList" border style="width: 100%" stripe>
      <el-table-column prop="code" label="编号" width="200"> </el-table-column>
      <el-table-column prop="name" label="工序名称" width="200">
      </el-table-column>
      <el-table-column prop="team" label="工作小组"> </el-table-column>
      <el-table-column prop="des" label="描述"> </el-table-column>
      <el-table-column prop="">
        <template slot-scope="scope">
          <el-button
            icon="el-icon-edit"
            type="primary"
            size="mini"
            @click="productionTechEdit(scope.row)"
            circle
          ></el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog
      title="编辑工序"
      :visible.sync="productionTechDialog"
      width="70%"
    >
      <el-form
        v-model="editProductionTech"
        ref="productionTechEditForm"
        label-width="100px"
      >
        <el-form-item label="编号" prop="pass">
          <el-input v-model="editProductionTech.code" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="工序名称" prop="pass">
          <el-input v-model="editProductionTech.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="工作小组" prop="pass">
          <el-select class="production-tech-team-select" v-model="editProductionTech.team"  placeholder="选择工作小组">
            <el-option label="team1" value="team1"></el-option>
            <el-option label="team1" value="team2"></el-option>
            <el-option label="team1" value="team3"></el-option>
            <el-option label="team1" value="team4"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="描述" prop="pass">
          <el-input v-model="editProductionTech.des" type="textarea" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>
<script>
export default {
  data() {
    return {
      productionTechDialog: false,
      editProductionTech: {},
      productionTechList: [
        {
          code: "CX1",
          name: "塑型",
          team: "塑型01（蒋师傅）",
          categroy: "网线",
          des: "",
        },
      ],
    };
  },
  methods: {
    productionTechEdit(row) {
      this.productionTechDialog = !this.productionTechDialog;
      this.editProductionTech=row;
    },
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
.production-tech-team-select{
  width: 100%;
}
</style>