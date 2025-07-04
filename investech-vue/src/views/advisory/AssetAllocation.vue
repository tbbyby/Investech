<template>
  <div class="asset-allocation">
    <el-card class="box-card">
      <template #header>
        <div class="card-header">
          <span>资产配置管理</span>
          <el-button type="primary" @click="showAddDialog = true">
            <el-icon><Plus /></el-icon>
            添加配置
          </el-button>
        </div>
      </template>

      <!-- 搜索区域 -->
      <div class="search-area">
        <el-form :model="searchForm" inline>
          <el-form-item label="客户ID">
            <el-input v-model="searchForm.clientId" placeholder="请输入客户ID" clearable />
          </el-form-item>
          <el-form-item label="配置名称">
            <el-input v-model="searchForm.allocationName" placeholder="请输入配置名称" clearable />
          </el-form-item>
          <el-form-item label="资产类别">
            <el-select v-model="searchForm.assetClass" placeholder="请选择" clearable>
              <el-option label="股票" value="股票" />
              <el-option label="债券" value="债券" />
              <el-option label="货币" value="货币" />
              <el-option label="商品" value="商品" />
              <el-option label="另类" value="另类" />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="searchAllocations">搜索</el-button>
            <el-button @click="resetSearch">重置</el-button>
          </el-form-item>
        </el-form>
      </div>

      <!-- 配置列表 -->
      <el-table :data="allocationList" style="width: 100%" v-loading="loading">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="clientId" label="客户ID" width="100" />
        <el-table-column prop="allocationName" label="配置名称" width="150" />
        <el-table-column prop="assetClass" label="资产类别" width="120">
          <template #default="scope">
            <el-tag :type="getAssetClassTagType(scope.row.assetClass)">
              {{ scope.row.assetClass }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="allocationRatio" label="配置比例" width="120">
          <template #default="scope">
            {{ formatPercentage(scope.row.allocationRatio) }}
          </template>
        </el-table-column>
        <el-table-column prop="currentValue" label="当前市值" width="120">
          <template #default="scope">
            {{ formatMoney(scope.row.currentValue) }}
          </template>
        </el-table-column>
        <el-table-column prop="targetValue" label="目标市值" width="120">
          <template #default="scope">
            {{ formatMoney(scope.row.targetValue) }}
          </template>
        </el-table-column>
        <el-table-column prop="fundCodes" label="相关基金" width="200" />
        <el-table-column prop="createTime" label="创建时间" width="160">
          <template #default="scope">
            {{ formatDate(scope.row.createTime) }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="scope">
            <el-button size="small" @click="viewAllocation(scope.row)">查看</el-button>
            <el-button size="small" type="primary" @click="editAllocation(scope.row)">编辑</el-button>
            <el-button size="small" type="danger" @click="deleteAllocation(scope.row.id)">删除</el-button>
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

    <!-- 添加/编辑配置对话框 -->
    <el-dialog
      v-model="showAddDialog"
      :title="isEdit ? '编辑资产配置' : '添加资产配置'"
      width="600px"
    >
      <el-form :model="allocationForm" :rules="rules" ref="allocationFormRef" label-width="120px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="客户ID" prop="clientId">
              <el-input-number v-model="allocationForm.clientId" :min="1" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="配置名称" prop="allocationName">
              <el-input v-model="allocationForm.allocationName" placeholder="请输入配置名称" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="资产类别" prop="assetClass">
              <el-select v-model="allocationForm.assetClass" placeholder="请选择">
                <el-option label="股票" value="股票" />
                <el-option label="债券" value="债券" />
                <el-option label="货币" value="货币" />
                <el-option label="商品" value="商品" />
                <el-option label="另类" value="另类" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="配置比例" prop="allocationRatio">
              <el-input-number
                v-model="allocationForm.allocationRatio"
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
            <el-form-item label="当前市值" prop="currentValue">
              <el-input-number
                v-model="allocationForm.currentValue"
                :min="0"
                :precision="2"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="目标市值" prop="targetValue">
              <el-input-number
                v-model="allocationForm.targetValue"
                :min="0"
                :precision="2"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="相关基金" prop="fundCodes">
              <el-input
                v-model="allocationForm.fundCodes"
                placeholder="请输入基金代码，逗号分隔"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="24">
            <el-form-item label="配置说明" prop="description">
              <el-input
                v-model="allocationForm.description"
                type="textarea"
                :rows="3"
                placeholder="请输入配置说明"
              />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="showAddDialog = false">取消</el-button>
          <el-button type="primary" @click="submitAllocation">确定</el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 配置详情对话框 -->
    <el-dialog v-model="showDetailDialog" title="资产配置详情" width="600px">
      <el-descriptions :column="2" border>
        <el-descriptions-item label="配置ID">{{ currentAllocation.id }}</el-descriptions-item>
        <el-descriptions-item label="客户ID">{{ currentAllocation.clientId }}</el-descriptions-item>
        <el-descriptions-item label="配置名称">{{ currentAllocation.allocationName }}</el-descriptions-item>
        <el-descriptions-item label="资产类别">
          <el-tag :type="getAssetClassTagType(currentAllocation.assetClass)">
            {{ currentAllocation.assetClass }}
          </el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="配置比例">{{ formatPercentage(currentAllocation.allocationRatio) }}</el-descriptions-item>
        <el-descriptions-item label="当前市值">{{ formatMoney(currentAllocation.currentValue) }}</el-descriptions-item>
        <el-descriptions-item label="目标市值">{{ formatMoney(currentAllocation.targetValue) }}</el-descriptions-item>
        <el-descriptions-item label="相关基金">{{ currentAllocation.fundCodes }}</el-descriptions-item>
        <el-descriptions-item label="创建时间">{{ formatDate(currentAllocation.createTime) }}</el-descriptions-item>
        <el-descriptions-item label="更新时间">{{ formatDate(currentAllocation.updateTime) }}</el-descriptions-item>
        <el-descriptions-item label="配置说明" :span="2">{{ currentAllocation.description }}</el-descriptions-item>
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
const allocationList = ref([])
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)
const showAddDialog = ref(false)
const showDetailDialog = ref(false)
const isEdit = ref(false)
const allocationFormRef = ref()

const searchForm = reactive({
  clientId: '',
  allocationName: '',
  assetClass: ''
})

const allocationForm = reactive({
  id: null,
  clientId: null,
  allocationName: '',
  assetClass: '',
  allocationRatio: 0,
  currentValue: 0,
  targetValue: 0,
  fundCodes: '',
  description: ''
})

const currentAllocation = ref({})

// 表单验证规则
const rules = {
  clientId: [{ required: true, message: '请输入客户ID', trigger: 'blur' }],
  allocationName: [{ required: true, message: '请输入配置名称', trigger: 'blur' }],
  assetClass: [{ required: true, message: '请选择资产类别', trigger: 'change' }],
  allocationRatio: [{ required: true, message: '请输入配置比例', trigger: 'blur' }]
}

// 获取配置列表
const getAllocationList = async () => {
  loading.value = true
  try {
    const response = await axios.get('/api/asset-allocation/list')
    if (response.data.code === 200) {
      allocationList.value = response.data.data
      total.value = response.data.data.length
    }
  } catch (error) {
    ElMessage.error('获取资产配置列表失败')
  } finally {
    loading.value = false
  }
}

// 搜索配置
const searchAllocations = async () => {
  loading.value = true
  try {
    const response = await axios.post('/api/asset-allocation/search', searchForm)
    if (response.data.code === 200) {
      allocationList.value = response.data.data
      total.value = response.data.data.length
    }
  } catch (error) {
    ElMessage.error('搜索资产配置失败')
  } finally {
    loading.value = false
  }
}

// 重置搜索
const resetSearch = () => {
  Object.keys(searchForm).forEach(key => {
    searchForm[key] = ''
  })
  getAllocationList()
}

// 查看配置详情
const viewAllocation = (allocation) => {
  currentAllocation.value = allocation
  showDetailDialog.value = true
}

// 编辑配置
const editAllocation = (allocation) => {
  isEdit.value = true
  Object.keys(allocationForm).forEach(key => {
    if (allocation[key] !== undefined) {
      allocationForm[key] = allocation[key]
    }
  })
  showAddDialog.value = true
}

// 删除配置
const deleteAllocation = async (id) => {
  try {
    await ElMessageBox.confirm('确定要删除这个资产配置吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    
    const response = await axios.delete(`/api/asset-allocation/${id}`)
    if (response.data.code === 200) {
      ElMessage.success('删除成功')
      getAllocationList()
    } else {
      ElMessage.error(response.data.message)
    }
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('删除失败')
    }
  }
}

// 提交配置
const submitAllocation = async () => {
  try {
    await allocationFormRef.value.validate()
    
    const url = isEdit.value ? '/api/asset-allocation/update' : '/api/asset-allocation/add'
    const response = await axios[isEdit.value ? 'put' : 'post'](url, allocationForm)
    
    if (response.data.code === 200) {
      ElMessage.success(isEdit.value ? '更新成功' : '添加成功')
      showAddDialog.value = false
      getAllocationList()
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
  Object.keys(allocationForm).forEach(key => {
    if (key === 'clientId') {
      allocationForm[key] = null
    } else if (key === 'allocationRatio' || key === 'currentValue' || key === 'targetValue') {
      allocationForm[key] = 0
    } else {
      allocationForm[key] = ''
    }
  })
}

// 分页处理
const handleSizeChange = (val) => {
  pageSize.value = val
  getAllocationList()
}

const handleCurrentChange = (val) => {
  currentPage.value = val
  getAllocationList()
}

// 工具函数
const formatPercentage = (value) => {
  if (!value) return '0.00%'
  return (value * 100).toFixed(2) + '%'
}

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

const getAssetClassTagType = (assetClass) => {
  const types = {
    '股票': 'danger',
    '债券': 'success',
    '货币': 'info',
    '商品': 'warning',
    '另类': 'primary'
  }
  return types[assetClass] || 'info'
}

onMounted(() => {
  getAllocationList()
})
</script>

<style scoped>
.asset-allocation {
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