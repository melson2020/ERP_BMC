<template>
  <div class="storage-stocktaking">
      <div class="storage-stocktaking-header">
      <el-input
        class="Storage-fliter-input"
        size="small"
        placeholder="搜索盘点单"
        v-model="searchContent"
        suffix-icon="el-icon-search"
      ></el-input>
      <el-button type="primary" size="small" @click="resetForm('StorageAddForm')">新建盘点单</el-button>
      </div>
      <div class="storage-stocktaking-body">
          <div class="undoing-plan-div-title colorblue">盘点单ID</div>
          <el-scrollbar class="planing-scrollbar">
            <el-table :data="inventoryOutboundList" script border size="small">
              <el-table-column prop="formNo" label="盘点单号" width="180">
              </el-table-column>
              <el-table-column prop="batchNo" label="批次号" width="180">
              </el-table-column>
              <el-table-column prop="createDate" label="盘点时间">
              </el-table-column>
              <el-table-column prop="createUser" label="盘点人">
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