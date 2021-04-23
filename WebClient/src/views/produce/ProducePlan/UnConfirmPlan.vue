<template>
  <div >
    <div class="produce-confirm-plan-header-div">
      <span class="produce-confirm-plan-header-title colorblue">待定计划</span>
      <el-select  placeholder="计划类型" size="small">
        <el-option label="生产" value="produce"> </el-option>
        <el-option label="代加工" value="replace"> </el-option>
      </el-select>
    </div>
    <el-table :data="producePlanList" script border>
      <el-table-column prop="planNo" label="单号"> </el-table-column>
      <el-table-column prop="customerName" label="客户名称"> </el-table-column>
      <el-table-column prop="type" label="类型"> </el-table-column>
      <el-table-column prop="orderNo" label="订单号"> </el-table-column>
      <el-table-column prop="createDate" label="创建时间"> </el-table-column>
      <el-table-column prop="pickingTikcetNo" label="领料单号">
      </el-table-column>
      <el-table-column prop="planState" label="状态"> </el-table-column>
      <el-table-column prop="" width="100px">
        <template scope-slot="scope">
          <el-button type="primary" @click="plneDetail" size="mini">决策</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog title="计划详情" :visible.sync="detailDialogVisible" width="80%">
          <m-plan-detail></m-plan-detail>
    </el-dialog>
  </div>
</template>
<script>
import planDetail from './PlanDetail'
export default {
  data() {
    return {
      detailDialogVisible: false,
      producePlanList: [
        {
          planNo: "生产计划单号",
          orderNo: "订单号01",
          completeDate: "预计完成时间",
          createDate: "创建时间",
          customerName: "客户名称",
          pickingTikcetNo: "领料单号",
          type: "代加工",
          planState: "待决策",
        },
        {
          planNo: "生产计划单号",
          orderNo: "订单号01",
          completeDate: "预计完成时间",
          createDate: "创建时间",
          customerName: "客户名称",
          pickingTikcetNo: "领料单号",
          type: "生产",
          planState: "待下达",
        },
        {
          planNo: "生产计划单号",
          orderNo: "订单号01",
          completeDate: "预计完成时间",
          createDate: "创建时间",
          customerName: "客户名称",
          pickingTikcetNo: "领料单号",
          type: "生产",
          planState: "待决策",
        },
      ],
    };
   
  },
  methods:{
      plneDetail(){
          this.detailDialogVisible=!this.detailDialogVisible
      }
  },
   components:{
        'm-plan-detail':planDetail,
    }
};
</script>
<style>
.produce-confirm-plan-header-div {
  height: 50px;
  display: flex;
  align-items: center;
  justify-content: space-between;
}
.produce-confirm-plan-header-title {
  font-size: 18px;
  font-weight: bold;
}
</style>