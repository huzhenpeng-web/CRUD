<template>
  <div class="product-container">
    <!-- 搜索区域 -->
    <el-input placeholder="请输入商品名称进行搜索" style="width:30%;margin-right:50px;" v-model="searchProduct">
      <el-button @click="selectLikeProduct" slot="append" icon="el-icon-search"></el-button>
    </el-input>
    <!-- 添加商品 -->
    <el-button @click="showAddDialog" type="primary" style="margin:0 0 15px 15px;">添加商品</el-button>
    <!-- 表格数据区域 -->
    <el-table v-loading="loading" element-loading-text="拼命加载中" element-loading-spinner="el-icon-loading" element-loading-background="rgba(0, 0, 0, 0.8)" :data="productList" border stripe>
      <el-table-column type="index"></el-table-column>
      <el-table-column prop="name" label="商品名称"></el-table-column>
      <el-table-column prop="price" label="商品价格"></el-table-column>
      <el-table-column label="生产时间">
        <template slot-scope="scope">
          {{scope.row.produceTime | dateFormat}}
        </template>
      </el-table-column>
      <el-table-column prop="madeFactory" label="生产地"></el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-tooltip effect="dark" content="编辑" placement="top">
            <el-button type="primary" icon="el-icon-edit" circle @click="showEditDialog(scope.row.id)"></el-button>
          </el-tooltip>
          <el-tooltip effect="dark" content="删除" placement="top">
            <el-button type="danger" icon="el-icon-delete" circle @click="deleteProduct(scope.row.id)"></el-button>
          </el-tooltip>
        </template>
      </el-table-column>
    </el-table>
    <!-- 底部分页区域 -->
    <el-pagination :current-page="pageInfo.pageNum" @size-change="handleSizeChange" @current-change="handleCurrentChange" :page-sizes="[3, 5, 10, 15]" :page-size="pageInfo.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total">
    </el-pagination>
    <!-- 添加商品对话框 -->
    <el-dialog title="添加商品" :visible.sync="addDialogVisible" width="50%">
      <!-- 商品表单 -->
      <el-form label-width="100px" :model="productForm" ref="productFormRef" :rules="productFormRules">
        <el-form-item label="商品名称:" prop="name">
          <el-input clearable v-model="productForm.name"></el-input>
        </el-form-item>
        <el-form-item label="商品价格:" prop="price">
          <el-input clearable type="number" v-model="productForm.price"></el-input>
        </el-form-item>
        <el-form-item label="生成时间:" prop="produceTime">
          <el-date-picker v-model="productForm.produceTime" type="datetime" placeholder="选择日期时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="生产地:" prop="madeFactory">
          <el-cascader :options="options" v-model="productForm.madeFactory"></el-cascader>
        </el-form-item>
      </el-form>
      <!-- 底部按钮区域 -->
      <span slot="footer">
        <el-button @click="resetProduct">重 置</el-button>
        <el-button type="primary" @click="submitProductForm">提 交</el-button>
      </span>
    </el-dialog>
    <!-- 编辑商品对话框 -->
    <el-dialog title="编辑商品" :visible.sync="editDialogVisible" width="50%">
      <!-- 商品表单 -->
      <el-form label-width="100px" :model="queryForm" ref="editFormRef" :rules="productFormRules">
        <el-form-item label="商品名称:" prop="name">
          <el-input clearable v-model="queryForm.name"></el-input>
        </el-form-item>
        <el-form-item label="商品价格:" prop="price">
          <el-input clearable type="number" v-model="queryForm.price"></el-input>
        </el-form-item>
        <el-form-item label="生成时间:" prop="produceTime">
          <el-date-picker v-model="queryForm.produceTime" type="datetime" placeholder="选择日期时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="生产地:" prop="madeFactory">
          <el-cascader :options="options" v-model="madeFactory"></el-cascader>
        </el-form-item>
      </el-form>
      <!-- 底部按钮区域 -->
      <span slot="footer">
        <el-button @click="editDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateProduct">提 交</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { regionData, CodeToText } from 'element-china-area-data'
export default {
  data () {
    return {
      // 城市数据
      options: regionData,
      // 加载状态
      loading: true,
      // 存储选择的城市
      city: '',
      // 总共商品数量
      total: 0,
      pageInfo: {
        pageNum: 1,
        pageSize: 3
      },
      productList: [],
      // 添加商品对话框
      addDialogVisible: false,
      // 编辑商品对话框
      editDialogVisible: false,
      // 添加商品的表单
      productForm: {
        id: 0,
        name: '',
        price: 0,
        produceTime: '',
        madeFactory: []
      },
      productFormRules: {
        name: [{ required: true, message: '请输入商品名称', trigger: 'blur' }],
        price: [{ required: true, message: '请输入商品价格', trigger: 'blur' }],
        produceTime: [{ required: true, message: '请输入商品名称', trigger: 'blur' }],
        madeFactory: [{ required: true, message: '请输入生产地', trigger: 'blur' }]
      },
      // 根据id查到的商品表单
      queryForm: {
        name: '',
        price: 0,
        produceTime: ''
      },
      madeFactory: [],
      factory: '',
      // 顶部输入框
      searchProduct: ''
    }
  },
  methods: {
    // async getAllProduct () {
    //   const { data: res } = await this.$http.get('product/getAllProduct', { params: this.pageInfo })
    //   this.total = res.total
    //   this.productList = res.list
    //   this.loading = false
    // },
    // 每页条数改变时触发
    handleSizeChange (newSize) {
      this.pageInfo.pageSize = newSize
      this.selectLikeProduct()
    },
    // 当前页发生改变
    handleCurrentChange (newPage) {
      this.pageInfo.pageNum = newPage
      this.selectLikeProduct()
    },
    // 删除商品
    async deleteProduct (id) {
      this.$confirm('此操作将永久删除用户, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(async () => {
          const { data: res } = await this.$http.post('product/deleteProduct', id)
          if (res !== 1) {
            return this.$message.error('删除商品失败')
          }
          this.$message.success('删除商品成功')
          // 刷新商品列表
          this.selectLikeProduct()
        })
        .catch(err => {
          this.$message.info(err)
        })
    },
    // 展示添加商品对话框
    showAddDialog () {
      this.addDialogVisible = true
    },
    // 重置商品表单
    resetProduct () {
      this.$refs.productFormRef.resetFields()
    },
    // 提交商品表单
    submitProductForm () {
      // 区域码转地址
      for (let i = 0; i < this.productForm.madeFactory.length; i++) {
        this.city += CodeToText[this.productForm.madeFactory[i]]
      }
      const newProduct = {
        name: this.productForm.name,
        price: this.productForm.price,
        produceTime: this.productForm.produceTime,
        madeFactory: this.city
      }
      this.$refs.productFormRef.validate(async valid => {
        if (!valid) return
        const { data: res } = await this.$http.post('product/insertProduct', newProduct)
        if (res !== 1) {
          return this.$message.error('添加商品失败,请重新添加!')
        }
        // 关闭对话框
        this.addDialogVisible = false
        // 重置表单
        this.resetProduct()
        // 刷新表单
        this.selectLikeProduct()
      })
    },
    // 展示编辑商品对话框
    async showEditDialog (id) {
      this.editDialogVisible = true
      const { data: res } = await this.$http.get('product/findProductById', { params: { id: id } })
      this.queryForm = res
    },
    // 更新商品信息
    updateProduct () {
      for (let i = 0; i < this.madeFactory.length; i++) {
        this.factory += CodeToText[this.madeFactory[i]]
      }
      const productForm = {
        id: this.queryForm.id,
        name: this.queryForm.name,
        price: this.queryForm.price,
        produceTime: this.queryForm.produceTime,
        madeFactory: this.factory
      }
      this.$refs.editFormRef.validate(async valid => {
        if (!valid) return
        const { data: res } = await this.$http.post('product/updateProduct', productForm)
        if (res !== 1) {
          return this.$message.error('更新商品信息失败!')
        }
        this.$message.success('更新商品信息成功!')
        this.editDialogVisible = false
        this.selectLikeProduct()
        // 清空上一次选择的地址
        this.madeFactory = []
      })
    },
    // 模糊搜索的商品
    async selectLikeProduct () {
      const { data: res } = await this.$http.get('product/selectLikeProduct', {
        params:
        { pageNum: this.pageInfo.pageNum, pageSize: this.pageInfo.pageSize, name: this.searchProduct }
      })
      console.log(res)
      this.total = res.total
      this.productList = res.list
      this.loading = false
    }
  },
  created () {
    this.selectLikeProduct()
  },
  watch: {
    searchProduct (newVal) {
      if (newVal === '') {
        this.selectLikeProduct()
      }
    }
  }
}
</script>

<style lang="less" scoped>
.el-pagination {
  margin: 15px 0 0 0;
}
</style>
