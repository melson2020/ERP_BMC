<template>
  <div class="storage-main-div">
    <div class="storage-header-div">
      <span class="colorblue">库存列表</span>
      <div>
        <el-input placeholder="请输入内容" v-model="searchValue" size="small">
          <i slot="prefix" class="el-input__icon el-icon-search"></i>
        </el-input>
      </div>
    </div>
    <el-table
      :data="producePlanList"
      script
      border
      class="storage-detail-table"
    >
      <el-table-column prop="planNo" label="单号"> </el-table-column>
      <el-table-column prop="customerName" label="客户名称"> </el-table-column>
      <el-table-column prop="" width="100px">
        <template slot-scope="scope">
          <el-button type="primary" @click="plneDetail(scope.row)" size="mini"
            >配置</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <div class="storage-detail-pagenation-div">
      <el-pagination background layout="prev, pager, next" :total="1000">
      </el-pagination>
    </div>
  </div>
</template>
<script>
import { mapGetters } from "vuex";
import { mapActions } from "vuex";
export default {
  data() {
    return { searchValue: "" };
  },

  methods: {
    ...mapActions({
      GetStorageDetail: "GetStorageDetail",
    }),
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
</style>