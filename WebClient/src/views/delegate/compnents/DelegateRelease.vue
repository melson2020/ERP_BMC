<template>
  <div>
    <div class="delegate-release-title-div">
      <span class="colorblue">委外下达</span>
      <el-select v-model="delegateType" placeholder="委外来源" size="small" clearable>
        <el-option label="生产委外" value="PLAN"> </el-option>
        <el-option label="订单委外" value="ORDER"> </el-option>
      </el-select>
    </div>
    <el-table :data="delegateReleaseListShow" script border>
      <el-table-column prop="ticketNo" label="单号"> </el-table-column>
      <el-table-column prop="customerName" label="客户名称"> </el-table-column>
      <el-table-column prop="type" label="类型">
        <template slot-scope="scope">
          <span>{{ convertDelegateType(scope.row.type) }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="sourceNo" label="来源单号"> </el-table-column>
      <el-table-column prop="createDate" label="创建时间">
        <template slot-scope="scope">
          <span>{{ getFullTime(scope.row.createDate) }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="state" label="状态">
        <template slot-scope="scope">
          <span>{{ converDelegateState(scope.row.state) }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="" width="100px">
        <template slot-scope="scope">
          <el-button
            icon="el-icon-s-release"
            type="primary"
            size="mini"
            circle
          ></el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>
<script>
import { mapGetters } from "vuex";
import { mapActions } from "vuex";
export default {
  data() {
    return {
      delegateType: "",
    };
  },
  methods: {
    ...mapActions({
      FindDelegateReleaseList: "FindDelegateReleaseList",
    }),
    getFullTime(time) {
      return new Date(time).format("yyyy-MM-dd hh:mm:ss");
    },
    convertDelegateType(type) {
      switch (type) {
        case "ORDER":
          return "订单委外";
        case "PLAN":
          return "生产委外";
        default:
          return type;
      }
    },
    converDelegateState(state) {
      switch (state) {
        case "1":
          return "带下达";
        default:
          return state;
      }
    },
  },
  computed: {
    ...mapGetters(["delegateReleaseList"]),
    delegateReleaseListShow(){
      if(this.delegateType==""){
        return this.delegateReleaseList
      }else{
        return this.delegateReleaseList.filter(item=>{
          return item.type==this.delegateType
        })
      }
    }
  },

  mounted() {
    this.FindDelegateReleaseList();
  },
};
</script>
<style>
.delegate-release-title-div {
  display: flex;
  height: 50px;
  align-items: center;
  justify-content: space-between;
}
</style>