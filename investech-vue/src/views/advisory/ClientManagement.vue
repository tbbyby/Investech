<template>
  <div class="client-management">
    <el-card class="box-card">
      <template #header>
        <div class="card-header">
          <span>客户管理</span>
          <el-button type="primary" @click="showAddDialog = true">
            <el-icon><Plus /></el-icon>
            添加客户
          </el-button>
        </div>
      </template>

      <!-- 搜索区域 -->
      <div class="search-area">
        <el-form :model="searchForm" inline>
          <el-form-item label="客户姓名">
            <el-input v-model="searchForm.name" placeholder="请输入客户姓名" clearable />
          </el-form-item>
          <el-form-item label="联系电话">
            <el-input v-model="searchForm.phone" placeholder="请输入联系电话" clearable />
          </el-form-item>
          <el-form-item label="风险承受能力">
            <el-select v-model="searchForm.riskTolerance" placeholder="请选择" clearable>
              <el-option label="保守" value="保守" />
              <el-option label="稳健" value="稳健" />
              <el-option label="积极" value="积极" />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="searchClients">搜索</el-button>
            <el-button @click="resetSearch">重置</el-button>
          </el-form-item>
        </el-form>
      </div>

      <!-- 客户列表 -->
      <el-table :data="clientList" style="width: 100%" v-loading="loading">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="name" label="客户姓名" width="120" />
        <el-table-column prop="phone" label="联系电话" width="130" />
        <el-table-column prop="email" label="邮箱" width="180" />
        <el-table-column prop="age" label="年龄" width="80" />
        <el-table-column prop="gender" label="性别" width="80" />
        <el-table-column prop="occupation" label="职业" width="120" />
        <el-table-column prop="annualIncome" label="年收入" width="120">
          <template #default="scope">
            {{ formatMoney(scope.row.annualIncome) }}
          </template>
        </el-table-column>
        <el-table-column prop="totalAssets" label="总资产" width="120">
          <template #default="scope">
            {{ formatMoney(scope.row.totalAssets) }}
          </template>
        </el-table-column>
        <el-table-column prop="riskTolerance" label="风险承受能力" width="120">
          <template #default="scope">
            <el-tag :type="getRiskTagType(scope.row.riskTolerance)">
              {{ scope.row.riskTolerance }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="investmentGoal" label="投资目标" width="100" />
        <el-table-column prop="investmentHorizon" label="投资期限" width="100" />
        <el-table-column prop="createTime" label="创建时间" width="160">
          <template #default="scope">
            {{ formatDate(scope.row.createTime) }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="scope">
            <el-button size="small" @click="viewClient(scope.row)">查看</el-button>
            <el-button size="small" type="primary" @click="editClient(scope.row)">编辑</el-button>
            <el-button size="small" type="danger" @click="deleteClient(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
      <div class="pagination">
        <el-pagination
          v-model:current-page="currentPage"
          v-model:page-size="pageSize"
          :page-sizes="[10, 20, 50, 100]"
          :total="total"
          layout="total, sizes, prev, pager, next, jumper"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
    </el-card>

    <!-- 添加/编辑客户对话框 -->
    <el-dialog
      v-model="showAddDialog"
      :title="isEdit ? '编辑客户' : '添加客户'"
      width="600px"
    >
      <el-form :model="clientForm" :rules="rules" ref="clientFormRef" label-width="120px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="客户姓名" prop="name">
              <el-input v-model="clientForm.name" placeholder="请输入客户姓名" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系电话" prop="phone">
              <el-input v-model="clientForm.phone" placeholder="请输入联系电话" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="邮箱地址" prop="email">
              <el-input v-model="clientForm.email" placeholder="请输入邮箱地址" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="身份证号" prop="idCard">
              <el-input v-model="clientForm.idCard" placeholder="请输入身份证号" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="年龄" prop="age">
              <el-input-number v-model="clientForm.age" :min="18" :max="100" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="性别" prop="gender">
              <el-select v-model="clientForm.gender" placeholder="请选择性别">
                <el-option label="男" value="男" />
                <el-option label="女" value="女" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="职业" prop="occupation">
              <el-input v-model="clientForm.occupation" placeholder="请输入职业" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="年收入" prop="annualIncome">
              <el-input-number v-model="clientForm.annualIncome" :min="0" :precision="2" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="总资产" prop="totalAssets">
              <el-input-number v-model="clientForm.totalAssets" :min="0" :precision="2" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="风险承受能力" prop="riskTolerance">
              <el-select v-model="clientForm.riskTolerance" placeholder="请选择">
                <el-option label="保守" value="保守" />
                <el-option label="稳健" value="稳健" />
                <el-option label="积极" value="积极" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="投资目标" prop="investmentGoal">
              <el-select v-model="clientForm.investmentGoal" placeholder="请选择">
                <el-option label="保值" value="保值" />
                <el-option label="增值" value="增值" />
                <el-option label="养老" value="养老" />
                <el-option label="教育" value="教育" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="投资期限" prop="investmentHorizon">
              <el-select v-model="clientForm.investmentHorizon" placeholder="请选择">
                <el-option label="短期" value="短期" />
                <el-option label="中期" value="中期" />
                <el-option label="长期" value="长期" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="showAddDialog = false">取消</el-button>
          <el-button type="primary" @click="submitClient">确定</el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 客户详情对话框 -->
    <el-dialog v-model="showDetailDialog" title="客户详情" width="600px">
      <el-descriptions :column="2" border>
        <el-descriptions-item label="客户姓名">{{ currentClient.name }}</el-descriptions-item>
        <el-descriptions-item label="联系电话">{{ currentClient.phone }}</el-descriptions-item>
        <el-descriptions-item label="邮箱地址">{{ currentClient.email }}</el-descriptions-item>
        <el-descriptions-item label="身份证号">{{ currentClient.idCard }}</el-descriptions-item>
        <el-descriptions-item label="年龄">{{ currentClient.age }}</el-descriptions-item>
        <el-descriptions-item label="性别">{{ currentClient.gender }}</el-descriptions-item>
        <el-descriptions-item label="职业">{{ currentClient.occupation }}</el-descriptions-item>
        <el-descriptions-item label="年收入">{{ formatMoney(currentClient.annualIncome) }}</el-descriptions-item>
        <el-descriptions-item label="总资产">{{ formatMoney(currentClient.totalAssets) }}</el-descriptions-item>
        <el-descriptions-item label="风险承受能力">
          <el-tag :type="getRiskTagType(currentClient.riskTolerance)">
            {{ currentClient.riskTolerance }}
          </el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="投资目标">{{ currentClient.investmentGoal }}</el-descriptions-item>
        <el-descriptions-item label="投资期限">{{ currentClient.investmentHorizon }}</el-descriptions-item>
        <el-descriptions-item label="创建时间">{{ formatDate(currentClient.createTime) }}</el-descriptions-item>
        <el-descriptions-item label="更新时间">{{ formatDate(currentClient.updateTime) }}</el-descriptions-item>
      </el-descriptions>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Plus } from '@element-plus/icons-vue'
import axios from 'axios'

// 响应式数据
const loading = ref(false)
const clientList = ref([])
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)
const showAddDialog = ref(false)
const showDetailDialog = ref(false)
const isEdit = ref(false)
const clientFormRef = ref()

const searchForm = reactive({
  name: '',
  phone: '',
  riskTolerance: ''
})

const clientForm = reactive({
  id: null,
  name: '',
  phone: '',
  email: '',
  idCard: '',
  age: 30,
  gender: '',
  occupation: '',
  annualIncome: 0,
  totalAssets: 0,
  riskTolerance: '',
  investmentGoal: '',
  investmentHorizon: ''
})

const currentClient = ref({})

// 表单验证规则
const rules = {
  name: [{ required: true, message: '请输入客户姓名', trigger: 'blur' }],
  phone: [{ required: true, message: '请输入联系电话', trigger: 'blur' }],
  email: [{ type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur' }],
  riskTolerance: [{ required: true, message: '请选择风险承受能力', trigger: 'change' }],
  investmentGoal: [{ required: true, message: '请选择投资目标', trigger: 'change' }],
  investmentHorizon: [{ required: true, message: '请选择投资期限', trigger: 'change' }]
}

// 获取客户列表
const getClientList = async () => {
  loading.value = true
  try {
    const response = await axios.get('/api/client/list')
    if (response.data.code === 200) {
      clientList.value = response.data.data
      total.value = response.data.data.length
    }
  } catch (error) {
    ElMessage.error('获取客户列表失败')
  } finally {
    loading.value = false
  }
}

// 搜索客户
const searchClients = async () => {
  loading.value = true
  try {
    const response = await axios.post('/api/client/search', searchForm)
    if (response.data.code === 200) {
      clientList.value = response.data.data
      total.value = response.data.data.length
    }
  } catch (error) {
    ElMessage.error('搜索客户失败')
  } finally {
    loading.value = false
  }
}

// 重置搜索
const resetSearch = () => {
  Object.keys(searchForm).forEach(key => {
    searchForm[key] = ''
  })
  getClientList()
}

// 查看客户详情
const viewClient = (client) => {
  currentClient.value = client
  showDetailDialog.value = true
}

// 编辑客户
const editClient = (client) => {
  isEdit.value = true
  Object.keys(clientForm).forEach(key => {
    if (client[key] !== undefined) {
      clientForm[key] = client[key]
    }
  })
  showAddDialog.value = true
}

// 删除客户
const deleteClient = async (id) => {
  try {
    await ElMessageBox.confirm('确定要删除这个客户吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    
    const response = await axios.delete(`/api/client/${id}`)
    if (response.data.code === 200) {
      ElMessage.success('删除成功')
      getClientList()
    } else {
      ElMessage.error(response.data.message)
    }
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('删除失败')
    }
  }
}

// 提交客户信息
const submitClient = async () => {
  try {
    await clientFormRef.value.validate()
    
    const url = isEdit.value ? '/api/client/update' : '/api/client/add'
    const response = await axios[isEdit.value ? 'put' : 'post'](url, clientForm)
    
    if (response.data.code === 200) {
      ElMessage.success(isEdit.value ? '更新成功' : '添加成功')
      showAddDialog.value = false
      getClientList()
      resetForm()
    } else {
      ElMessage.error(response.data.message)
    }
  } catch (error) {
    ElMessage.error('操作失败')
  }
}

// 重置表单
const resetForm = () => {
  isEdit.value = false
  Object.keys(clientForm).forEach(key => {
    if (key === 'age') {
      clientForm[key] = 30
    } else if (key === 'annualIncome' || key === 'totalAssets') {
      clientForm[key] = 0
    } else {
      clientForm[key] = ''
    }
  })
}

// 分页处理
const handleSizeChange = (val) => {
  pageSize.value = val
  getClientList()
}

const handleCurrentChange = (val) => {
  currentPage.value = val
  getClientList()
}

// 工具函数
const formatMoney = (value) => {
  if (!value) return '0.00'
  return new Intl.NumberFormat('zh-CN', {
    style: 'currency',
    currency: 'CNY'
  }).format(value)
}

const formatDate = (date) => {
  if (!date) return ''
  return new Date(date).toLocaleString('zh-CN')
}

const getRiskTagType = (risk) => {
  const types = {
    '保守': 'success',
    '稳健': 'warning',
    '积极': 'danger'
  }
  return types[risk] || 'info'
}

// 监听对话框关闭
const handleDialogClose = () => {
  resetForm()
}

onMounted(() => {
  getClientList()
})
</script>

<style scoped>
.client-management {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.search-area {
  margin-bottom: 20px;
  padding: 20px;
  background-color: #f5f7fa;
  border-radius: 4px;
}

.pagination {
  margin-top: 20px;
  text-align: right;
}

.dialog-footer {
  text-align: right;
}
</style> 