<template>
  <div class="investment-advice">
    <el-card class="box-card">
      <template #header>
        <div class="card-header">
          <span>投资建议管理</span>
          <el-button type="primary" @click="showAddDialog = true">
            <el-icon><Plus /></el-icon>
            添加建议
          </el-button>
        </div>
      </template>

      <!-- 搜索区域 -->
      <div class="search-area">
        <el-form :model="searchForm" inline>
          <el-form-item label="客户ID">
            <el-input v-model="searchForm.clientId" placeholder="请输入客户ID" clearable />
          </el-form-item>
          <el-form-item label="建议类型">
            <el-select v-model="searchForm.adviceType" placeholder="请选择" clearable>
              <el-option label="资产配置" value="资产配置" />
              <el-option label="基金推荐" value="基金推荐" />
              <el-option label="调仓建议" value="调仓建议" />
            </el-select>
          </el-form-item>
          <el-form-item label="风险等级">
            <el-select v-model="searchForm.riskLevel" placeholder="请选择" clearable>
              <el-option label="低" value="低" />
              <el-option label="中" value="中" />
              <el-option label="高" value="高" />
            </el-select>
          </el-form-item>
          <el-form-item label="状态">
            <el-select v-model="searchForm.status" placeholder="请选择" clearable>
              <el-option label="草稿" :value="0" />
              <el-option label="已发布" :value="1" />
              <el-option label="已采纳" :value="2" />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="searchAdvices">搜索</el-button>
            <el-button @click="resetSearch">重置</el-button>
          </el-form-item>
        </el-form>
      </div>

      <!-- 建议列表 -->
      <el-table :data="adviceList" style="width: 100%" v-loading="loading">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="clientId" label="客户ID" width="100" />
        <el-table-column prop="adviceType" label="建议类型" width="120">
          <template #default="scope">
            <el-tag :type="getAdviceTypeTagType(scope.row.adviceType)">
              {{ scope.row.adviceType }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="title" label="建议标题" width="200" />
        <el-table-column prop="riskLevel" label="风险等级" width="100">
          <template #default="scope">
            <el-tag :type="getRiskTagType(scope.row.riskLevel)">
              {{ scope.row.riskLevel }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="expectedReturn" label="预期收益率" width="120">
          <template #default="scope">
            {{ formatPercentage(scope.row.expectedReturn) }}
          </template>
        </el-table-column>
        <el-table-column prop="maxDrawdown" label="最大回撤" width="120">
          <template #default="scope">
            {{ formatPercentage(scope.row.maxDrawdown) }}
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="100">
          <template #default="scope">
            <el-tag :type="getStatusTagType(scope.row.status)">
              {{ getStatusText(scope.row.status) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="160">
          <template #default="scope">
            {{ formatDate(scope.row.createTime) }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="scope">
            <el-button size="small" @click="viewAdvice(scope.row)">查看</el-button>
            <el-button size="small" type="primary" @click="editAdvice(scope.row)">编辑</el-button>
            <el-button size="small" type="danger" @click="deleteAdvice(scope.row.id)">删除</el-button>
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

    <!-- 添加/编辑建议对话框 -->
    <el-dialog
      v-model="showAddDialog"
      :title="isEdit ? '编辑投资建议' : '添加投资建议'"
      width="800px"
    >
      <el-form :model="adviceForm" :rules="rules" ref="adviceFormRef" label-width="120px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="客户ID" prop="clientId">
              <el-input-number v-model="adviceForm.clientId" :min="1" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="建议类型" prop="adviceType">
              <el-select v-model="adviceForm.adviceType" placeholder="请选择">
                <el-option label="资产配置" value="资产配置" />
                <el-option label="基金推荐" value="基金推荐" />
                <el-option label="调仓建议" value="调仓建议" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="24">
            <el-form-item label="建议标题" prop="title">
              <el-input v-model="adviceForm.title" placeholder="请输入建议标题" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="24">
            <el-form-item label="建议内容" prop="content">
              <el-input
                v-model="adviceForm.content"
                type="textarea"
                :rows="4"
                placeholder="请输入建议内容"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="风险等级" prop="riskLevel">
              <el-select v-model="adviceForm.riskLevel" placeholder="请选择">
                <el-option label="低" value="低" />
                <el-option label="中" value="中" />
                <el-option label="高" value="高" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="预期收益率" prop="expectedReturn">
              <el-input-number
                v-model="adviceForm.expectedReturn"
                :min="0"
                :max="1"
                :precision="4"
                :step="0.001"
              />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="最大回撤" prop="maxDrawdown">
              <el-input-number
                v-model="adviceForm.maxDrawdown"
                :min="0"
                :max="1"
                :precision="4"
                :step="0.001"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="推荐基金" prop="fundRecommendations">
              <el-input
                v-model="adviceForm.fundRecommendations"
                placeholder="请输入推荐基金列表，JSON格式"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="资产配置" prop="assetAllocation">
              <el-input
                v-model="adviceForm.assetAllocation"
                placeholder="请输入资产配置建议，JSON格式"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="状态" prop="status">
              <el-select v-model="adviceForm.status" placeholder="请选择">
                <el-option label="草稿" :value="0" />
                <el-option label="已发布" :value="1" />
                <el-option label="已采纳" :value="2" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="showAddDialog = false">取消</el-button>
          <el-button type="primary" @click="submitAdvice">确定</el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 建议详情对话框 -->
    <el-dialog v-model="showDetailDialog" title="投资建议详情" width="800px">
      <el-descriptions :column="2" border>
        <el-descriptions-item label="建议ID">{{ currentAdvice.id }}</el-descriptions-item>
        <el-descriptions-item label="客户ID">{{ currentAdvice.clientId }}</el-descriptions-item>
        <el-descriptions-item label="建议类型">
          <el-tag :type="getAdviceTypeTagType(currentAdvice.adviceType)">
            {{ currentAdvice.adviceType }}
          </el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="风险等级">
          <el-tag :type="getRiskTagType(currentAdvice.riskLevel)">
            {{ currentAdvice.riskLevel }}
          </el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="预期收益率">{{ formatPercentage(currentAdvice.expectedReturn) }}</el-descriptions-item>
        <el-descriptions-item label="最大回撤">{{ formatPercentage(currentAdvice.maxDrawdown) }}</el-descriptions-item>
        <el-descriptions-item label="状态">
          <el-tag :type="getStatusTagType(currentAdvice.status)">
            {{ getStatusText(currentAdvice.status) }}
          </el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="创建时间">{{ formatDate(currentAdvice.createTime) }}</el-descriptions-item>
        <el-descriptions-item label="建议标题" :span="2">{{ currentAdvice.title }}</el-descriptions-item>
        <el-descriptions-item label="建议内容" :span="2">{{ currentAdvice.content }}</el-descriptions-item>
        <el-descriptions-item label="推荐基金" :span="2">{{ currentAdvice.fundRecommendations }}</el-descriptions-item>
        <el-descriptions-item label="资产配置" :span="2">{{ currentAdvice.assetAllocation }}</el-descriptions-item>
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
const adviceList = ref([])
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)
const showAddDialog = ref(false)
const showDetailDialog = ref(false)
const isEdit = ref(false)
const adviceFormRef = ref()

const searchForm = reactive({
  clientId: '',
  adviceType: '',
  riskLevel: '',
  status: ''
})

const adviceForm = reactive({
  id: null,
  clientId: null,
  adviceType: '',
  title: '',
  content: '',
  riskLevel: '',
  expectedReturn: 0,
  maxDrawdown: 0,
  fundRecommendations: '',
  assetAllocation: '',
  status: 0
})

const currentAdvice = ref({})

// 表单验证规则
const rules = {
  clientId: [{ required: true, message: '请输入客户ID', trigger: 'blur' }],
  adviceType: [{ required: true, message: '请选择建议类型', trigger: 'change' }],
  title: [{ required: true, message: '请输入建议标题', trigger: 'blur' }],
  content: [{ required: true, message: '请输入建议内容', trigger: 'blur' }],
  riskLevel: [{ required: true, message: '请选择风险等级', trigger: 'change' }]
}

// 获取建议列表
const getAdviceList = async () => {
  loading.value = true
  try {
    const response = await axios.get('/api/investment-advice/list')
    if (response.data.code === 200) {
      adviceList.value = response.data.data
      total.value = response.data.data.length
    }
  } catch (error) {
    ElMessage.error('获取投资建议列表失败')
  } finally {
    loading.value = false
  }
}

// 搜索建议
const searchAdvices = async () => {
  loading.value = true
  try {
    const response = await axios.post('/api/investment-advice/search', searchForm)
    if (response.data.code === 200) {
      adviceList.value = response.data.data
      total.value = response.data.data.length
    }
  } catch (error) {
    ElMessage.error('搜索投资建议失败')
  } finally {
    loading.value = false
  }
}

// 重置搜索
const resetSearch = () => {
  Object.keys(searchForm).forEach(key => {
    searchForm[key] = ''
  })
  getAdviceList()
}

// 查看建议详情
const viewAdvice = (advice) => {
  currentAdvice.value = advice
  showDetailDialog.value = true
}

// 编辑建议
const editAdvice = (advice) => {
  isEdit.value = true
  Object.keys(adviceForm).forEach(key => {
    if (advice[key] !== undefined) {
      adviceForm[key] = advice[key]
    }
  })
  showAddDialog.value = true
}

// 删除建议
const deleteAdvice = async (id) => {
  try {
    await ElMessageBox.confirm('确定要删除这个投资建议吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    
    const response = await axios.delete(`/api/investment-advice/${id}`)
    if (response.data.code === 200) {
      ElMessage.success('删除成功')
      getAdviceList()
    } else {
      ElMessage.error(response.data.message)
    }
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('删除失败')
    }
  }
}

// 提交建议
const submitAdvice = async () => {
  try {
    await adviceFormRef.value.validate()
    
    const url = isEdit.value ? '/api/investment-advice/update' : '/api/investment-advice/add'
    const response = await axios[isEdit.value ? 'put' : 'post'](url, adviceForm)
    
    if (response.data.code === 200) {
      ElMessage.success(isEdit.value ? '更新成功' : '添加成功')
      showAddDialog.value = false
      getAdviceList()
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
  Object.keys(adviceForm).forEach(key => {
    if (key === 'clientId') {
      adviceForm[key] = null
    } else if (key === 'expectedReturn' || key === 'maxDrawdown') {
      adviceForm[key] = 0
    } else if (key === 'status') {
      adviceForm[key] = 0
    } else {
      adviceForm[key] = ''
    }
  })
}

// 分页处理
const handleSizeChange = (val) => {
  pageSize.value = val
  getAdviceList()
}

const handleCurrentChange = (val) => {
  currentPage.value = val
  getAdviceList()
}

// 工具函数
const formatPercentage = (value) => {
  if (!value) return '0.00%'
  return (value * 100).toFixed(2) + '%'
}

const formatDate = (date) => {
  if (!date) return ''
  return new Date(date).toLocaleString('zh-CN')
}

const getAdviceTypeTagType = (type) => {
  const types = {
    '资产配置': 'primary',
    '基金推荐': 'success',
    '调仓建议': 'warning'
  }
  return types[type] || 'info'
}

const getRiskTagType = (risk) => {
  const types = {
    '低': 'success',
    '中': 'warning',
    '高': 'danger'
  }
  return types[risk] || 'info'
}

const getStatusTagType = (status) => {
  const types = {
    0: 'info',
    1: 'success',
    2: 'warning'
  }
  return types[status] || 'info'
}

const getStatusText = (status) => {
  const texts = {
    0: '草稿',
    1: '已发布',
    2: '已采纳'
  }
  return texts[status] || '未知'
}

onMounted(() => {
  getAdviceList()
})
</script>

<style scoped>
.investment-advice {
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