<template>
  <el-tabs
    type="border-card"
    v-model="tabName"
    @tab-click="createContractOnClick"
  >
    <el-tab-pane label="新建意向合同" class="contract-tab-pane" name="first"
      ><m-create-intent-constract></m-create-intent-constract
    ></el-tab-pane>
    <el-tab-pane label="意向合同" class="contract-tab-pane" name="second"
      ><m-intent-constract></m-intent-constract
    ></el-tab-pane>
    <el-tab-pane label="合同查询" class="contract-tab-pane" name="third"
      ><m-constract-search></m-constract-search
    ></el-tab-pane>
  </el-tabs>
</template>
<script>
import intentContract from "./contract/IntentContract";
import createIntentConstract from "./contract/CreateIntentContract";
import contractSearch from "./contract/ContractSearch";
import { mapActions } from "vuex";
export default {
  data() {
    return {
      tabName: "first",
    };
  },
  methods: {
    ...mapActions({
      GetIntentionContractList: "GetIntentionContractList",
    }),
    createContractOnClick() {
      switch (this.tabName) {
        case "1":
          this.GetIntentionContractList();
          break;
        case "2":
          console.log("新建", this.tabName);
          break;
        default:
          break;
      }
    },
  },

  components: {
    "m-intent-constract": intentContract,
    "m-create-intent-constract": createIntentConstract,
    "m-constract-search": contractSearch,
  },

  beforeMount(){
    if(this.$route.query.type){
      this.tabName="second"
    }
  }
};
</script>
<style>
.contract-tab-pane {
  min-height: 1000px;
}
</style>