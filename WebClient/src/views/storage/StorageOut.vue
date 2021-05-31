<template>
  <div class="storage-out">
      <div class="storage-out-header">
      <el-input
        class="Storage-fliter-input"
        size="small"
        placeholder="搜索出库单"
        v-model="searchContent"
        suffix-icon="el-icon-search"
      ></el-input>
      <el-button type="primary" size="small" @click="resetForm('StorageAddForm')">新建出库单</el-button>
      </div>
      <div class="storage-out-body">
          <div class="undoing-plan-div-title colorblue">出库单ID</div>
          <el-scrollbar class="planing-scrollbar">
            <el-table :data="inventoryOutboundList" script border size="small">
              <el-table-column prop="formNo" label="出库单号" width="180">
              </el-table-column>
              <el-table-column prop="batchNo" label="批次号" width="180">
              </el-table-column>
              <el-table-column prop="createDate" label="出库时间">
              </el-table-column>
              <el-table-column prop="createUser" label="出库人">
              </el-table-column>
              <el-table-column  width="100px">
                <template slot-scope="scope">
                  <el-button
                    icon="el-icon-more"
                    type="primary"
                    size="mini"
                    @click="changeTableAreaView(scope.row.id)"
                    circle
                  ></el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-scrollbar>
      </div>
  </div>

</template>
<script>
export default {};
</script>
<style></style>