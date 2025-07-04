<template>
  <div class="derived-factor">
    <div class="page-header">
      <h2>衍生因子管理</h2>
      <el-button type="primary" @click="showAddDialog = true">
        <el-icon><Plus /></el-icon>
        创建衍生因子
      </el-button>
    </div>

    <!-- 搜索区域 -->
    <div class="search-area">
      <el-form :model="searchForm" inline>
        <el-form-item label="因子代码">
          <el-input v-model="searchForm.factorCode" placeholder="请输入因子代码" clearable />
        </el-form-item>
        <el-form-item label="因子名称">
          <el-input v-model="searchForm.factorName" placeholder="请输入因子名称" clearable />
        </el-form-item>
        <el-form-item label="因子分类">
          <el-select v-model="searchForm.category" placeholder="请选择因子分类" clearable>
            <el-option label="综合类" value="综合类" />
            <el-option label="风险控制类" value="风险控制类" />
            <el-option label="性价比类" value="性价比类" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="searchFactors">搜索</el-button>
          <el-button @click="resetSearch">重置</el-button>
        </el-form-item>
      </el-form>
    </div>

    <!-- 衍生因子列表 -->
    <div class="factor-table">
      <el-table :data="factorList" v-loading="loading" stripe>
        <el-table-column prop="factorCode" label="因子代码" width="120" />
        <el-table-column prop="factorName" label="因子名称" width="150" />
        <el-table-column prop="factorDesc" label="因子描述" show-overflow-tooltip />
        <el-table-column prop="category" label="因子分类" width="120" />
        <el-table-column prop="baseFactors" label="基础因子" width="150">
          <template #default="scope">
            <el-tag v-for="factor in getBaseFactors(scope.row.baseFactors)" :key="factor" size="small" style="margin: 2px">
              {{ factor }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="formula" label="计算公式" show-overflow-tooltip />
        <el-table-column prop="status" label="状态" width="80">
          <template #default="scope">
            <el-tag :type="scope.row.status === 1 ? 'success' : 'danger'">
              {{ scope.row.status === 1 ? '正常' : '禁用' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="180" />
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="scope">
            <el-button type="primary" size="small" @click="editFactor(scope.row)">编辑</el-button>
            <el-button type="danger" size="small" @click="deleteFactor(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

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

    <!-- 新增/编辑衍生因子对话框 -->
    <el-dialog
      v-model="showAddDialog"
      :title="isEdit ? '编辑衍生因子' : '创建衍生因子'"
      width="700px"
    >
      <el-form :model="factorForm" :rules="rules" ref="factorFormRef" label-width="120px">
        <el-form-item label="因子代码" prop="factorCode">
          <el-input v-model="factorForm.factorCode" placeholder="请输入因子代码" />
        </el-form-item>
        <el-form-item label="因子名称" prop="factorName">
          <el-input v-model="factorForm.factorName" placeholder="请输入因子名称" />
        </el-form-item>
        <el-form-item label="因子描述" prop="factorDesc">
          <el-input
            v-model="factorForm.factorDesc"
            type="textarea"
            :rows="3"
            placeholder="请输入因子描述"
          />
        </el-form-item>
        <el-form-item label="基础因子" prop="baseFactors">
          <div class="base-factor-selector">
            <el-button type="primary" @click="showBaseFactorSelector = true">
              选择基础因子
            </el-button>
            <div class="selected-base-factors" v-if="selectedBaseFactors.length > 0">
              <el-tag
                v-for="factor in selectedBaseFactors"
                :key="factor.factorCode"
                closable
                @close="removeBaseFactor(factor)"
                style="margin: 5px"
              >
                {{ factor.factorName }}
              </el-tag>
            </div>
          </div>
        </el-form-item>
        <el-form-item label="权重配置" prop="weights">
          <div class="weights-config" v-if="selectedBaseFactors.length > 0">
            <div v-for="factor in selectedBaseFactors" :key="factor.factorCode" class="weight-item">
              <span>{{ factor.factorName }}：</span>
              <el-input-number
                v-model="factorWeights[factor.factorCode]"
                :min="0"
                :max="1"
                :precision="2"
                :step="0.1"
                style="width: 120px"
              />
            </div>
          </div>
        </el-form-item>
        <el-form-item label="计算公式" prop="formula">
          <el-input
            v-model="factorForm.formula"
            type="textarea"
            :rows="3"
            placeholder="请输入计算公式"
          />
        </el-form-item>
        <el-form-item label="因子分类" prop="category">
          <el-select v-model="factorForm.category" placeholder="请选择因子分类">
            <el-option label="综合类" value="综合类" />
            <el-option label="风险控制类" value="风险控制类" />
            <el-option label="性价比类" value="性价比类" />
          </el-select>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="factorForm.status">
            <el-radio :label="1">正常</el-radio>
            <el-radio :label="0">禁用</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="showAddDialog = false">取消</el-button>
          <el-button type="primary" @click="saveFactor">确定</el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 基础因子选择器对话框 -->
    <el-dialog
      v-model="showBaseFactorSelector"
      title="选择基础因子"
      width="800px"
    >
      <div class="base-factor-selector-content">
        <div class="filter-area">
          <el-input
            v-model="baseFactorSearchKeyword"
            placeholder="搜索因子名称或代码"
            clearable
            style="width: 300px"
          />
        </div>
        <el-table
          :data="filteredBaseFactorList"
          @selection-change="handleBaseFactorSelectionChange"
          height="400"
        >
          <el-table-column type="selection" width="55" />
          <el-table-column prop="factorCode" label="因子代码" width="120" />
          <el-table-column prop="factorName" label="因子名称" width="150" />
          <el-table-column prop="factorType" label="因子类型" width="100" />
          <el-table-column prop="category" label="因子分类" width="100" />
          <el-table-column prop="factorDesc" label="因子描述" show-overflow-tooltip />
        </el-table>
      </div>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="showBaseFactorSelector = false">取消</el-button>
          <el-button type="primary" @click="confirmBaseFactorSelection">确定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, computed } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Plus } from '@element-plus/icons-vue'
import axios from 'axios'

// 响应式数据
const loading = ref(false)
const factorList = ref([])
const baseFactorList = ref([])
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)
const showAddDialog = ref(false)
const showBaseFactorSelector = ref(false)
const isEdit = ref(false)
const factorFormRef = ref()
const baseFactorSearchKeyword = ref('')
const selectedBaseFactors = ref([])
const tempSelectedBaseFactors = ref([])
const factorWeights = ref({})

// 搜索表单
const searchForm = reactive({
  factorCode: '',
  factorName: '',
  category: ''
})

// 衍生因子表单
const factorForm = reactive({
  id: null,
  factorCode: '',
  factorName: '',
  factorDesc: '',
  baseFactors: '',
  weights: '',
  formula: '',
  category: '',
  creatorId: 1,
  status: 1
})

// 表单验证规则
const rules = {
  factorCode: [
    { required: true, message: '请输入因子代码', trigger: 'blur' }
  ],
  factorName: [
    { required: true, message: '请输入因子名称', trigger: 'blur' }
  ],
  category: [
    { required: true, message: '请选择因子分类', trigger: 'change' }
  ]
}

// 过滤后的基础因子列表
const filteredBaseFactorList = computed(() => {
  if (!baseFactorSearchKeyword.value) {
    return baseFactorList.value.filter(f => f.factorType === '基础因子')
  }
  return baseFactorList.value.filter(factor => 
    factor.factorType === '基础因子' && (
      factor.factorName.includes(baseFactorSearchKeyword.value) ||
      factor.factorCode.includes(baseFactorSearchKeyword.value)
    )
  )
})

// 获取衍生因子列表
const getFactorList = async () => {
  loading.value = true
  try {
    const response = await axios.get('/api/derived-factor/list')
    if (response.data.code === 200) {
      factorList.value = response.data.data
      total.value = response.data.data.length
    }
  } catch (error) {
    ElMessage.error('获取衍生因子列表失败')
  } finally {
    loading.value = false
  }
}

// 获取基础因子列表
const getBaseFactorList = async () => {
  try {
    const response = await axios.get('/api/factor/list')
    if (response.data.code === 200) {
      baseFactorList.value = response.data.data
    }
  } catch (error) {
    ElMessage.error('获取基础因子列表失败')
  }
}

// 搜索衍生因子
const searchFactors = async () => {
  loading.value = true
  try {
    const params = {
      factorCode: searchForm.factorCode,
      factorName: searchForm.factorName,
      category: searchForm.category
    }
    const response = await axios.get('/api/derived-factor/search', { params })
    if (response.data.code === 200) {
      factorList.value = response.data.data
      total.value = response.data.data.length
    }
  } catch (error) {
    ElMessage.error('搜索衍生因子失败')
  } finally {
    loading.value = false
  }
}

// 重置搜索
const resetSearch = () => {
  Object.keys(searchForm).forEach(key => {
    searchForm[key] = ''
  })
  getFactorList()
}

// 获取基础因子列表
const getBaseFactors = (baseFactors) => {
  if (!baseFactors) return []
  try {
    return JSON.parse(baseFactors)
  } catch (error) {
    return []
  }
}

// 编辑衍生因子
const editFactor = async (row) => {
  isEdit.value = true
  Object.keys(factorForm).forEach(key => {
    if (row[key] !== undefined) {
      factorForm[key] = row[key]
    }
  })
  
  // 解析基础因子并获取详情
  try {
    const baseFactorCodes = JSON.parse(row.baseFactors || '[]')
    const factorPromises = baseFactorCodes.map(code => 
      axios.get(`/api/factor/code/${code}`)
    )
    const responses = await Promise.all(factorPromises)
    selectedBaseFactors.value = responses
      .filter(res => res.data.code === 200)
      .map(res => res.data.data)
    
    // 解析权重
    const weights = JSON.parse(row.weights || '{}')
    selectedBaseFactors.value.forEach(factor => {
      factorWeights.value[factor.factorCode] = weights[factor.factorCode] || 0
    })
  } catch (error) {
    selectedBaseFactors.value = []
    factorWeights.value = {}
  }
  
  showAddDialog.value = true
}

// 删除衍生因子
const deleteFactor = async (row) => {
  try {
    await ElMessageBox.confirm('确定要删除这个衍生因子吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    
    const response = await axios.delete(`/api/derived-factor/${row.id}`)
    if (response.data.code === 200) {
      ElMessage.success('删除成功')
      getFactorList()
    } else {
      ElMessage.error(response.data.message || '删除失败')
    }
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('删除失败')
    }
  }
}

// 保存衍生因子
const saveFactor = async () => {
  try {
    await factorFormRef.value.validate()
    
    // 更新基础因子和权重
    factorForm.baseFactors = JSON.stringify(selectedBaseFactors.value.map(f => f.factorCode))
    factorForm.weights = JSON.stringify(factorWeights.value)
    
    const url = isEdit.value ? '/api/derived-factor/update' : '/api/derived-factor/create'
    const method = isEdit.value ? 'put' : 'post'
    
    const response = await axios[method](url, factorForm)
    if (response.data.code === 200) {
      ElMessage.success(isEdit.value ? '更新成功' : '创建成功')
      showAddDialog.value = false
      getFactorList()
      resetForm()
    } else {
      ElMessage.error(response.data.message || (isEdit.value ? '更新失败' : '创建失败'))
    }
  } catch (error) {
    ElMessage.error(isEdit.value ? '更新失败' : '创建失败')
  }
}

// 重置表单
const resetForm = () => {
  Object.keys(factorForm).forEach(key => {
    if (key === 'status') {
      factorForm[key] = 1
    } else if (key === 'creatorId') {
      factorForm[key] = 1
    } else {
      factorForm[key] = ''
    }
  })
  selectedBaseFactors.value = []
  factorWeights.value = {}
  isEdit.value = false
}

// 处理基础因子选择变化
const handleBaseFactorSelectionChange = (selection) => {
  tempSelectedBaseFactors.value = selection
}

// 确认基础因子选择
const confirmBaseFactorSelection = () => {
  selectedBaseFactors.value = [...tempSelectedBaseFactors.value]
  // 初始化权重
  selectedBaseFactors.value.forEach(factor => {
    if (!factorWeights.value[factor.factorCode]) {
      factorWeights.value[factor.factorCode] = 0
    }
  })
  showBaseFactorSelector.value = false
}

// 移除基础因子
const removeBaseFactor = (factor) => {
  const index = selectedBaseFactors.value.findIndex(f => f.factorCode === factor.factorCode)
  if (index > -1) {
    selectedBaseFactors.value.splice(index, 1)
    delete factorWeights.value[factor.factorCode]
  }
}

// 分页处理
const handleSizeChange = (val) => {
  pageSize.value = val
  getFactorList()
}

const handleCurrentChange = (val) => {
  currentPage.value = val
  getFactorList()
}

onMounted(() => {
  getFactorList()
  getBaseFactorList()
})
</script>

<style scoped>
.derived-factor {
  padding: 20px;
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.page-header h2 {
  margin: 0;
  color: #303133;
}

.search-area {
  background: #f5f7fa;
  padding: 20px;
  border-radius: 8px;
  margin-bottom: 20px;
}

.factor-table {
  margin-bottom: 20px;
}

.pagination {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}

.base-factor-selector {
  border: 1px solid #dcdfe6;
  border-radius: 4px;
  padding: 10px;
  min-height: 60px;
}

.selected-base-factors {
  margin-top: 10px;
}

.weights-config {
  border: 1px solid #dcdfe6;
  border-radius: 4px;
  padding: 10px;
  
  .weight-item {
    display: flex;
    align-items: center;
    margin-bottom: 10px;
    
    span {
      width: 120px;
      margin-right: 10px;
    }
  }
}

.base-factor-selector-content {
  .filter-area {
    margin-bottom: 15px;
  }
}
</style> 