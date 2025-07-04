<template>
  <div class="portfolio-optimization">
    <el-card class="box-card">
      <template #header>
        <div class="card-header">
          <span>投资组合优化管理</span>
          <el-button type="primary" @click="showAddDialog = true">
            <el-icon><Plus /></el-icon>
            添加优化方案
          </el-button>
        </div>
      </template>

      <!-- 搜索区域 -->
      <div class="search-area">
        <el-form :model="searchForm" inline>
          <el-form-item label="客户ID">
            <el-input v-model="searchForm.clientId" placeholder="请输入客户ID" clearable />
          </el-form-item>
          <el-form-item label="优化方案名称">
            <el-input v-model="searchForm.optimizationName" placeholder="请输入方案名称" clearable />
          </el-form-item>
          <el-form-item label="优化方法">
            <el-select v-model="searchForm.optimizationMethod" placeholder="请选择" clearable>
              <el-option label="马科维茨" value="马科维茨" />
              <el-option label="风险平价" value="风险平价" />
              <el-option label="最大夏普比率" value="最大夏普比率" />
            </el-select>
          </el-form-item>
          <el-form-item label="状态">
            <el-select v-model="searchForm.status" placeholder="请选择" clearable>
              <el-option label="计算中" :value="0" />
              <el-option label="已完成" :value="1" />
              <el-option label="已应用" :value="2" />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="searchOptimizations">搜索</el-button>
            <el-button @click="resetSearch">重置</el-button>
          </el-form-item>
        </el-form>
      </div>

      <!-- 优化方案列表 -->
      <el-table :data="optimizationList" style="width: 100%" v-loading="loading">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="clientId" label="客户ID" width="100" />
        <el-table-column prop="optimizationName" label="方案名称" width="180" />
        <el-table-column prop="optimizationMethod" label="优化方法" width="120">
          <template #default="scope">
            <el-tag :type="getMethodTagType(scope.row.optimizationMethod)">
              {{ scope.row.optimizationMethod }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="targetReturn" label="目标收益率" width="120">
          <template #default="scope">
            {{ formatPercentage(scope.row.targetReturn) }}
          </template>
        </el-table-column>
        <el-table-column prop="maxRisk" label="最大风险" width="120">
          <template #default="scope">
            {{ formatPercentage(scope.row.maxRisk) }}
          </template>
        </el-table-column>
        <el-table-column prop="expectedReturn" label="预期收益率" width="120">
          <template #default="scope">
            {{ formatPercentage(scope.row.expectedReturn) }}
          </template>
        </el-table-column>
        <el-table-column prop="expectedRisk" label="预期风险" width="120">
          <template #default="scope">
            {{ formatPercentage(scope.row.expectedRisk) }}
          </template>
        </el-table-column>
        <el-table-column prop="sharpeRatio" label="夏普比率" width="120">
          <template #default="scope">
            {{ scope.row.sharpeRatio?.toFixed(4) || '0.0000' }}
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
            <el-button size="small" @click="viewOptimization(scope.row)">查看</el-button>
            <el-button size="small" type="primary" @click="editOptimization(scope.row)">编辑</el-button>
            <el-button size="small" type="danger" @click="deleteOptimization(scope.row.id)">删除</el-button>
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

    <!-- 添加/编辑优化方案对话框 -->
    <el-dialog
      v-model="showAddDialog"
      :title="isEdit ? '编辑投资组合优化' : '添加投资组合优化'"
      width="800px"
    >
      <el-form :model="optimizationForm" :rules="rules" ref="optimizationFormRef" label-width="120px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="客户ID" prop="clientId">
              <el-input-number v-model="optimizationForm.clientId" :min="1" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="方案名称" prop="optimizationName">
              <el-input v-model="optimizationForm.optimizationName" placeholder="请输入方案名称" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="优化方法" prop="optimizationMethod">
              <el-select v-model="optimizationForm.optimizationMethod" placeholder="请选择">
                <el-option label="马科维茨" value="马科维茨" />
                <el-option label="风险平价" value="风险平价" />
                <el-option label="最大夏普比率" value="最大夏普比率" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="状态" prop="status">
              <el-select v-model="optimizationForm.status" placeholder="请选择">
                <el-option label="计算中" :value="0" />
                <el-option label="已完成" :value="1" />
                <el-option label="已应用" :value="2" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="目标收益率" prop="targetReturn">
              <el-input-number
                v-model="optimizationForm.targetReturn"
                :min="0"
                :max="1"
                :precision="4"
                :step="0.001"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="最大风险" prop="maxRisk">
              <el-input-number
                v-model="optimizationForm.maxRisk"
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
            <el-form-item label="预期收益率" prop="expectedReturn">
              <el-input-number
                v-model="optimizationForm.expectedReturn"
                :min="0"
                :max="1"
                :precision="4"
                :step="0.001"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="预期风险" prop="expectedRisk">
              <el-input-number
                v-model="optimizationForm.expectedRisk"
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
            <el-form-item label="夏普比率" prop="sharpeRatio">
              <el-input-number
                v-model="optimizationForm.sharpeRatio"
                :precision="4"
                :step="0.001"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="基金池" prop="fundPool">
              <el-input
                v-model="optimizationForm.fundPool"
                placeholder="请输入基金池，JSON格式"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="优化权重" prop="optimizedWeights">
              <el-input
                v-model="optimizationForm.optimizedWeights"
                placeholder="请输入优化权重，JSON格式"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="24">
            <el-form-item label="约束条件" prop="constraints">
              <el-input
                v-model="optimizationForm.constraints"
                type="textarea"
                :rows="3"
                placeholder="请输入约束条件，JSON格式"
              />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="showAddDialog = false">取消</el-button>
          <el-button type="primary" @click="submitOptimization">确定</el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 优化方案详情对话框 -->
    <el-dialog v-model="showDetailDialog" title="投资组合优化详情" width="800px">
      <el-descriptions :column="2" border>
        <el-descriptions-item label="优化ID">{{ currentOptimization.id }}</el-descriptions-item>
        <el-descriptions-item label="客户ID">{{ currentOptimization.clientId }}</el-descriptions-item>
        <el-descriptions-item label="方案名称">{{ currentOptimization.optimizationName }}</el-descriptions-item>
        <el-descriptions-item label="优化方法">
          <el-tag :type="getMethodTagType(currentOptimization.optimizationMethod)">
            {{ currentOptimization.optimizationMethod }}
          </el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="目标收益率">{{ formatPercentage(currentOptimization.targetReturn) }}</el-descriptions-item>
        <el-descriptions-item label="最大风险">{{ formatPercentage(currentOptimization.maxRisk) }}</el-descriptions-item>
        <el-descriptions-item label="预期收益率">{{ formatPercentage(currentOptimization.expectedReturn) }}</el-descriptions-item>
        <el-descriptions-item label="预期风险">{{ formatPercentage(currentOptimization.expectedRisk) }}</el-descriptions-item>
        <el-descriptions-item label="夏普比率">{{ currentOptimization.sharpeRatio?.toFixed(4) || '0.0000' }}</el-descriptions-item>
        <el-descriptions-item label="状态">
          <el-tag :type="getStatusTagType(currentOptimization.status)">
            {{ getStatusText(currentOptimization.status) }}
          </el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="创建时间">{{ formatDate(currentOptimization.createTime) }}</el-descriptions-item>
        <el-descriptions-item label="更新时间">{{ formatDate(currentOptimization.updateTime) }}</el-descriptions-item>
        <el-descriptions-item label="基金池" :span="2">{{ currentOptimization.fundPool }}</el-descriptions-item>
        <el-descriptions-item label="优化权重" :span="2">{{ currentOptimization.optimizedWeights }}</el-descriptions-item>
        <el-descriptions-item label="约束条件" :span="2">{{ currentOptimization.constraints }}</el-descriptions-item>
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
const optimizationList = ref([])
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)
const showAddDialog = ref(false)
const showDetailDialog = ref(false)
const isEdit = ref(false)
const optimizationFormRef = ref()

const searchForm = reactive({
  clientId: '',
  optimizationName: '',
  optimizationMethod: '',
  status: ''
})

const optimizationForm = reactive({
  id: null,
  clientId: null,
  optimizationName: '',
  optimizationMethod: '',
  fundPool: '',
  targetReturn: 0,
  maxRisk: 0,
  optimizedWeights: '',
  expectedReturn: 0,
  expectedRisk: 0,
  sharpeRatio: 0,
  constraints: '',
  status: 0
})

const currentOptimization = ref({})

// 表单验证规则
const rules = {
  clientId: [{ required: true, message: '请输入客户ID', trigger: 'blur' }],
  optimizationName: [{ required: true, message: '请输入方案名称', trigger: 'blur' }],
  optimizationMethod: [{ required: true, message: '请选择优化方法', trigger: 'change' }]
}

// 获取优化方案列表
const getOptimizationList = async () => {
  loading.value = true
  try {
    const response = await axios.get('/api/portfolio-optimization/list')
    if (response.data.code === 200) {
      optimizationList.value = response.data.data
      total.value = response.data.data.length
    }
  } catch (error) {
    ElMessage.error('获取投资组合优化列表失败')
  } finally {
    loading.value = false
  }
}

// 搜索优化方案
const searchOptimizations = async () => {
  loading.value = true
  try {
    const response = await axios.post('/api/portfolio-optimization/search', searchForm)
    if (response.data.code === 200) {
      optimizationList.value = response.data.data
      total.value = response.data.data.length
    }
  } catch (error) {
    ElMessage.error('搜索投资组合优化失败')
  } finally {
    loading.value = false
  }
}

// 重置搜索
const resetSearch = () => {
  Object.keys(searchForm).forEach(key => {
    searchForm[key] = ''
  })
  getOptimizationList()
}

// 查看优化方案详情
const viewOptimization = (optimization) => {
  currentOptimization.value = optimization
  showDetailDialog.value = true
}

// 编辑优化方案
const editOptimization = (optimization) => {
  isEdit.value = true
  Object.keys(optimizationForm).forEach(key => {
    if (optimization[key] !== undefined) {
      optimizationForm[key] = optimization[key]
    }
  })
  showAddDialog.value = true
}

// 删除优化方案
const deleteOptimization = async (id) => {
  try {
    await ElMessageBox.confirm('确定要删除这个投资组合优化方案吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    
    const response = await axios.delete(`/api/portfolio-optimization/${id}`)
    if (response.data.code === 200) {
      ElMessage.success('删除成功')
      getOptimizationList()
    } else {
      ElMessage.error(response.data.message)
    }
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('删除失败')
    }
  }
}

// 提交优化方案
const submitOptimization = async () => {
  try {
    await optimizationFormRef.value.validate()
    
    const url = isEdit.value ? '/api/portfolio-optimization/update' : '/api/portfolio-optimization/add'
    const response = await axios[isEdit.value ? 'put' : 'post'](url, optimizationForm)
    
    if (response.data.code === 200) {
      ElMessage.success(isEdit.value ? '更新成功' : '添加成功')
      showAddDialog.value = false
      getOptimizationList()
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
  Object.keys(optimizationForm).forEach(key => {
    if (key === 'clientId') {
      optimizationForm[key] = null
    } else if (key === 'targetReturn' || key === 'maxRisk' || key === 'expectedReturn' || key === 'expectedRisk' || key === 'sharpeRatio') {
      optimizationForm[key] = 0
    } else if (key === 'status') {
      optimizationForm[key] = 0
    } else {
      optimizationForm[key] = ''
    }
  })
}

// 分页处理
const handleSizeChange = (val) => {
  pageSize.value = val
  getOptimizationList()
}

const handleCurrentChange = (val) => {
  currentPage.value = val
  getOptimizationList()
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

const getMethodTagType = (method) => {
  const types = {
    '马科维茨': 'primary',
    '风险平价': 'success',
    '最大夏普比率': 'warning'
  }
  return types[method] || 'info'
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
    0: '计算中',
    1: '已完成',
    2: '已应用'
  }
  return texts[status] || '未知'
}

onMounted(() => {
  getOptimizationList()
})
</script>

<style scoped>
.portfolio-optimization {
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