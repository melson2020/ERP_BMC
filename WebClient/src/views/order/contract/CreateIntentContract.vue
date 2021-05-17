<template>
  <div class="create-constract-main-container">
    <m-edit-conratct></m-edit-conratct>
  </div>
</template>
<script>
import editContract from './EditContract'
export default {
  name: "m-create-intent-constract",
  data() {
    return {};
  },
  computed: {},
  methods: {},
  beforeMount() {},
  components:{
    "m-edit-conratct":editContract
  }
};
</script>
<style lang="less">
.create-constract-main-container {
  display: flex;
  align-items: flex-start;
  justify-content: center;
}

</style>