<template>
  <div class="customer-container">
     <!-- 顾客搜索 -->
    <el-input placeholder="请输入内容" style="width:30%;margin-right:50px;" v-model="searchCustomer">
      <el-button slot="append" icon="el-icon-search" @click="splitQuery"></el-button>
    </el-input>
    <!-- 添加顾客 -->
    <el-button type="primary" @click="showAddDialog">添加顾客</el-button>
    <!-- 顾客列表 -->
    <el-table v-loading="loading" element-loading-text="拼命加载中" element-loading-spinner="el-icon-loading" element-loading-background="rgba(0, 0, 0, 0.8)" :data="customerList" border stripe>
      <el-table-column type="index"></el-table-column>
      <el-table-column prop="username" label="顾客姓名"></el-table-column>
      <el-table-column prop="jobs" label="职业"></el-table-column>
      <el-table-column prop="phone" label="手机号"></el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-tooltip effect="dark" content="编辑" placement="top">
            <el-button type="primary" icon="el-icon-edit" circle @click="showEditDialog(scope.row.id)"></el-button>
          </el-tooltip>
          <el-tooltip effect="dark" content="删除" placement="top">
            <el-button type="danger" icon="el-icon-delete" circle @click="deleteCustomer(scope.row.id)"></el-button>
          </el-tooltip>
        </template>
      </el-table-column>
    </el-table>
    <!-- 底部分页 -->
    <el-pagination :current-page="queryInfo.pageNum" @size-change="handleSizeChange" @current-change="handleCurrentChange" :page-sizes="[3, 5, 10, 15]" :page-size="queryInfo.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total">
    </el-pagination>
    <!-- 添加用户对话框 -->
    <el-dialog @close="resetCustomerForm" title="添加顾客" width="50%" :visible.sync="addDialogVisible">
      <el-form :model="customerForm" label-width="100px" ref="customerFormRef" :rules="customerFormRules">
        <el-form-item label="姓名:" prop="username">
          <el-input v-focus v-model="customerForm.username"></el-input>
        </el-form-item>
        <el-form-item label="职业:" prop="jobs">
          <el-input v-model="customerForm.jobs"></el-input>
        </el-form-item>
        <el-form-item label="手机号:" prop="phone">
          <el-input v-model="customerForm.phone" @keyup.enter.native="addCustomer(customerForm)"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="resetCustomerForm">重 置</el-button>
        <el-button type="primary" @click="addCustomer(customerForm)">提 交</el-button>
      </span>
    </el-dialog>
    <!-- 编辑用户对话框 -->
    <el-dialog title="编辑用户" width="50%" :visible.sync="editDialogVisible">
      <el-form :model="editCustomerForm" label-width="100px" ref="editCustomerRef" :rules="customerFormRules">
        <el-form-item label="姓名:" prop="username">
          <el-input v-focus v-model="editCustomerForm.username"></el-input>
        </el-form-item>
        <el-form-item label="职业:" prop="jobs">
          <el-input v-model="editCustomerForm.jobs"></el-input>
        </el-form-item>
        <el-form-item label="手机号:" prop="phone">
          <el-input v-model="editCustomerForm.phone"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="editCustomer(editCustomerForm)">提 交</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data () {
    return {
      loading: true,
      customerList: [],
      // 删除对话框状态
      addDialogVisible: false,
      editDialogVisible: false,
      // 顾客信息
      customerForm: {
        username: '',
        jobs: '',
        phone: ''
      },
      // 编辑顾客的信息
      editCustomerForm: {
        id: '',
        username: '',
        jobs: '',
        phone: ''
      },
      // 顾客表单校验规则
      customerFormRules: {
        username: [{ required: true, message: '请填写用户名', trigger: 'blur' }],
        jobs: [{ required: true, message: '请填写职业', trigger: 'blur' }],
        phone: [{ required: true, message: '请填写手机号', trigger: 'blur' }]
      },
      // 搜索的顾客姓名
      searchCustomer: '',
      // 顾客的总数
      total: 0,
      // 查询分页参数
      queryInfo: {
        pageNum: 1,
        pageSize: 3
      }
    }
  },
  methods: {
    // 添加顾客
    addCustomer (customerForm) {
      // 规定请求头格式...
      this.$refs.customerFormRef.validate(async validate => {
        if (!validate) return this.$message.info('请填写必要的表单项')
        const { data: res } = await this.$http.post('customer/insertCustomer', customerForm)
        if (res === 1) {
          this.$message.success('添加用户成功！')
        }
        this.addDialogVisible = false
        this.splitQuery()
      })
    },
    // 展示编辑对话框
    showEditDialog (id) {
      this.findCustomer(id)
      this.editDialogVisible = true
    },
    // 按照id查询顾客
    async findCustomer (customerId) {
      const { data: res } = await this.$http.get('customer/findCustomer', { params: { id: customerId } })
      this.editCustomerForm = res
    },
    // 编辑用户
    editCustomer (editCustomerForm) {
      this.$refs.editCustomerRef.validate(async valid => {
        if (!valid) return this.$message.info('请填写必要的表单项')
        const { data: res } = await this.$http.post('customer/updateCustomer', editCustomerForm)
        if (res === 1) {
          this.$message.success('更新用户信息成功')
          this.splitQuery()
          this.editDialogVisible = false
        } else {
          return this.$message.error('更新用户信息失败')
        }
      })
    },
    // 删除用户
    deleteCustomer (id) {
      this.$confirm('此操作将永久删除用户, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(async () => {
          const { data: res } = await this.$http.post('customer/deleteCustomerById', id)
          if (res !== 1) {
            return this.$message.error('删除用户失败')
          }
          this.$message.success('删除用户成功')
          // 刷新用户列表
          this.splitQuery()
        })
        .catch(err => {
          this.$message.info(err)
        })
    },
    // 添加用户对话框状态
    showAddDialog () {
      this.addDialogVisible = true
    },
    // 清空表单内容
    resetCustomerForm () {
      this.$refs.customerFormRef.resetFields()
    },
    // 输入姓名查找顾客
    async findCustomerByName () {
      const { data: res } = await this.$http.get('customer/findCustomerByName', { params: { customerName: this.searchCustomer } })
      this.customerList = res
      this.total = res.length
      if (res.length === 0) {
        return this.$message.info('对不起,未查询到任何顾客!')
      }
    },
    // 分页查询
    async splitQuery () {
      const { data: res } = await this.$http.get('customer/findCustomerByName', {
        params: {
          pageNum: this.queryInfo.pageNum,
          pageSize: this.queryInfo.pageSize,
          customerName: this.searchCustomer
        }
      })
      console.log(res)
      if (res.list.length === 0) {
        return this.$message.error('请求失败!')
      }
      this.customerList = res.list
      this.loading = false
      this.total = res.total
    },
    // 每页条数改变时触发
    handleSizeChange (newSize) {
      this.queryInfo.pageSize = newSize
      this.splitQuery()
    },
    // 当前页发生改变
    handleCurrentChange (newPage) {
      this.queryInfo.pageNum = newPage
      this.splitQuery()
    }
  },
  created () {
    this.splitQuery()
  },
  watch: {
    searchCustomer (newVal) {
      if (newVal === '') {
        this.splitQuery()
      }
    }
  }
}
</script>

<style lang="less" scoped>
.el-button {
  margin: 10px 0 10px 0;
}
.el-pagination{
  margin-top: 20px;
}
</style>
