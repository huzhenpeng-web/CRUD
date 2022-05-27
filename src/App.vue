<template>
  <div id="#app">
    <!-- 标签页 -->
    <el-tabs v-model="activeName">
      <el-tab-pane label="顾客管理" name="first">
        <keep-alive>
          <Customer></Customer>
        </keep-alive>
      </el-tab-pane>
      <el-tab-pane label="商品管理" name="two">
        <keep-alive>
          <Product></Product>
        </keep-alive>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import Customer from '@/views/Customer.vue'
import Product from '@/views/Product.vue'
export default {
  data () {
    return {
      activeName: 'first'
    }
  },
  components: {
    Customer,
    Product
  }
}
</script>

<style lang="less" scoped>
* {
  padding: 0;
  margin: 0;
}
</style>
