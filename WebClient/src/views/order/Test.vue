<template>
  <div>{{intentionContractList.length}}
    <el-button type="primary"  @click="OnClick">Click</el-button>
  </div>
</template>
<script>
import { mapGetters } from "vuex";
import { mapActions } from "vuex";
export default {
  data(){
    return{
      title:'我就看看'
    }
  },
  computed:{
     ...mapGetters(["intentionContractList"]),
    firtChar(){
      return this.title.substring(1)
    }
  },
  methods:{
    ...mapActions({
      GetIntentionContractList: "GetIntentionContractList",
      ApproveContract: "ApproveContract",
      InvalidContract: "InvalidContract",
    }),
    OnClick(){
        this.$message.info("111")
    }
  },
  beforeMount(){

  }
};
</script>
<style></style>