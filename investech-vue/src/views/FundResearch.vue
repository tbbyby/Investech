<template>
  <div class="fund-research">
    <!-- 页面标题 -->
    <div class="page-header">
      <h2>基金研究</h2>
      <p>全面的基金数据分析和组合构建工具</p>
    </div>
    
    <!-- 搜索和筛选区域 -->
    <el-card class="search-card">
      <el-form :model="searchForm" inline>
        <el-form-item label="基金代码">
          <el-input
            v-model="searchForm.fundCode"
            placeholder="请输入基金代码"
            clearable
            style="width: 150px"
          />
        </el-form-item>
        <el-form-item label="基金名称">
          <el-input
            v-model="searchForm.fundName"
            placeholder="请输入基金名称"
            clearable
            style="width: 200px"
          />
        </el-form-item>
        <el-form-item label="基金公司">
          <el-select
            v-model="searchForm.fundCompany"
            placeholder="请选择基金公司"
            clearable
            style="width: 180px"
          >
            <el-option
              v-for="company in fundCompanies"
              :key="company"
              :label="company"
              :value="company"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="基金经理">
          <el-input
            v-model="searchForm.fundManager"
            placeholder="请输入基金经理"
            clearable
            style="width: 150px"
          />
        </el-form-item>
        <el-form-item label="基金类型">
          <el-select
            v-model="searchForm.fundType"
            placeholder="请选择基金类型"
            clearable
            style="width: 150px"
          >
            <el-option label="股票型" value="股票型" />
            <el-option label="混合型" value="混合型" />
            <el-option label="债券型" value="债券型" />
            <el-option label="货币型" value="货币型" />
            <el-option label="指数型" value="指数型" />
          </el-select>
        </el-form-item>
        <el-form-item label="风险等级">
          <el-select
            v-model="searchForm.riskLevel"
            placeholder="请选择风险等级"
            clearable
            style="width: 120px"
          >
            <el-option label="低风险" value="低风险" />
            <el-option label="中风险" value="中风险" />
            <el-option label="高风险" value="高风险" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch" :loading="loading">
            <el-icon><Search /></el-icon>
            搜索
          </el-button>
          <el-button @click="handleReset">
            <el-icon><Refresh /></el-icon>
            重置
          </el-button>
        </el-form-item>
      </el-form>
    </el-card>
    
    <!-- 基金列表 -->
    <el-card class="fund-list-card">
      <template #header>
        <div class="card-header">
          <span>基金列表 ({{ fundList.length }})</span>
          <div class="header-actions">
            <el-button type="success" @click="handleCreatePortfolio" :disabled="selectedFunds.length === 0">
              <el-icon><Plus /></el-icon>
              创建组合 ({{ selectedFunds.length }})
            </el-button>
            <el-button @click="handleRefresh">
              <el-icon><Refresh /></el-icon>
              刷新
            </el-button>
          </div>
        </div>
      </template>
      
      <el-table
        :data="fundList"
        v-loading="loading"
        @selection-change="handleSelectionChange"
        style="width: 100%"
      >
        <el-table-column type="selection" width="55" />
        <el-table-column prop="fundCode" label="基金代码" width="120" />
        <el-table-column prop="fundName" label="基金名称" min-width="200" />
        <el-table-column prop="fundType" label="基金类型" width="100" />
        <el-table-column prop="fundCompany" label="基金公司" width="150" />
        <el-table-column prop="fundManager" label="基金经理" width="120" />
        <el-table-column prop="netValue" label="最新净值" width="100">
          <template #default="{ row }">
            {{ row.netValue?.toFixed(4) || '-' }}
          </template>
        </el-table-column>
        <el-table-column prop="yearlyReturn" label="年收益率" width="120">
          <template #default="{ row }">
            <span :class="getReturnClass(row.yearlyReturn)">
              {{ formatReturn(row.yearlyReturn) }}
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="riskLevel" label="风险等级" width="100">
          <template #default="{ row }">
            <el-tag :type="getRiskTagType(row.riskLevel)" size="small">
              {{ row.riskLevel }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="150" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" size="small" @click="viewFundDetail(row)">
              详情
            </el-button>
            <el-button type="success" size="small" @click="addToPortfolio(row)">
              加入组合
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      
      <!-- 分页 -->
      <div class="pagination-wrapper">
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
    
    <!-- 创建组合对话框 -->
    <el-dialog
      v-model="portfolioDialogVisible"
      title="创建基金组合"
      width="600px"
    >
      <el-form :model="portfolioForm" :rules="portfolioRules" ref="portfolioFormRef">
        <el-form-item label="组合名称" prop="portfolioName">
          <el-input v-model="portfolioForm.portfolioName" placeholder="请输入组合名称" />
        </el-form-item>
        <el-form-item label="组合描述" prop="portfolioDesc">
          <el-input
            v-model="portfolioForm.portfolioDesc"
            type="textarea"
            :rows="3"
            placeholder="请输入组合描述"
          />
        </el-form-item>
        <el-form-item label="基金权重">
          <div class="weights-container">
            <div
              v-for="fund in selectedFunds"
              :key="fund.id"
              class="weight-item"
            >
              <span class="fund-name">{{ fund.fundName }}</span>
              <el-input-number
                v-model="fundWeights[fund.id]"
                :min="0"
                :max="100"
                :precision="2"
                style="width: 120px"
                @change="updateWeights"
              />
              <span class="weight-unit">%</span>
            </div>
          </div>
          <div class="weight-total">
            总权重: {{ totalWeight }}%
            <span v-if="totalWeight !== 100" class="weight-warning">
              (权重总和应为100%)
            </span>
          </div>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="portfolioDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitPortfolio" :disabled="totalWeight !== 100">
          创建组合
        </el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, computed } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Search, Refresh, Plus } from '@element-plus/icons-vue'
import axios from 'axios'

const router = useRouter()

// 数据
const loading = ref(false)
const fundList = ref([])
const selectedFunds = ref([])
const fundCompanies = ref([])
const currentPage = ref(1)
const pageSize = ref(20)
const total = ref(0)

// 搜索表单
const searchForm = reactive({
  fundCode: '',
  fundName: '',
  fundCompany: '',
  fundManager: '',
  fundType: '',
  riskLevel: ''
})

// 组合创建
const portfolioDialogVisible = ref(false)
const portfolioFormRef = ref()
const portfolioForm = reactive({
  portfolioName: '',
  portfolioDesc: ''
})
const fundWeights = ref({})

const portfolioRules = {
  portfolioName: [
    { required: true, message: '请输入组合名称', trigger: 'blur' }
  ],
  portfolioDesc: [
    { required: true, message: '请输入组合描述', trigger: 'blur' }
  ]
}

// 计算属性
const totalWeight = computed(() => {
  return Object.values(fundWeights.value).reduce((sum, weight) => sum + (weight || 0), 0)
})

// 方法
const loadFunds = async () => {
  loading.value = true
  try {
    const params = {
      ...searchForm,
      page: currentPage.value,
      size: pageSize.value
    }
    
    const response = await axios.get('/api/fund/search', { params })
    if (response.data.code === 200) {
      fundList.value = response.data.data
      total.value = response.data.data.length
    }
  } catch (error) {
    console.error('加载基金数据失败:', error)
    ElMessage.error('加载基金数据失败')
  } finally {
    loading.value = false
  }
}

const loadFundCompanies = async () => {
  try {
    const response = await axios.get('/api/fund/list')
    if (response.data.code === 200) {
      const companies = [...new Set(response.data.data.map(fund => fund.fundCompany))]
      fundCompanies.value = companies.filter(Boolean)
    }
  } catch (error) {
    console.error('加载基金公司失败:', error)
  }
}

const handleSearch = () => {
  currentPage.value = 1
  loadFunds()
}

const handleReset = () => {
  Object.keys(searchForm).forEach(key => {
    searchForm[key] = ''
  })
  currentPage.value = 1
  loadFunds()
}

const handleRefresh = () => {
  loadFunds()
}

const handleSelectionChange = (selection) => {
  selectedFunds.value = selection
}

const handleSizeChange = (size) => {
  pageSize.value = size
  loadFunds()
}

const handleCurrentChange = (page) => {
  currentPage.value = page
  loadFunds()
}

const handleCreatePortfolio = () => {
  if (selectedFunds.value.length === 0) {
    ElMessage.warning('请先选择基金')
    return
  }
  
  portfolioForm.portfolioName = ''
  portfolioForm.portfolioDesc = ''
  fundWeights.value = {}
  
  // 初始化权重
  const avgWeight = 100 / selectedFunds.value.length
  selectedFunds.value.forEach(fund => {
    fundWeights.value[fund.id] = avgWeight
  })
  
  portfolioDialogVisible.value = true
}

const updateWeights = () => {
  // 权重更新逻辑
}

const submitPortfolio = async () => {
  if (!portfolioFormRef.value) return
  
  try {
    await portfolioFormRef.value.validate()
    
    if (totalWeight.value !== 100) {
      ElMessage.warning('权重总和必须为100%')
      return
    }
    
    const userInfo = JSON.parse(localStorage.getItem('userInfo') || '{}')
    
    // 将百分比权重转换为小数形式，使用基金ID作为key
    const weightsForDB = {}
    selectedFunds.value.forEach(fund => {
      weightsForDB[fund.id] = (fundWeights.value[fund.id] || 0) / 100
    })
    
    const portfolioData = {
      portfolioName: portfolioForm.portfolioName,
      portfolioDesc: portfolioForm.portfolioDesc,
      userId: userInfo.id,
      fundCodes: selectedFunds.value.map(fund => fund.fundCode).join(','),
      weights: JSON.stringify(weightsForDB)
    }
    
    const response = await axios.post('/api/portfolio/create', portfolioData)
    
    if (response.data.code === 200) {
      ElMessage.success('组合创建成功')
      portfolioDialogVisible.value = false
      selectedFunds.value = []
    } else {
      ElMessage.error(response.data.message || '创建失败')
    }
  } catch (error) {
    console.error('创建组合失败:', error)
    ElMessage.error('创建组合失败')
  }
}

const viewFundDetail = (fund) => {
  router.push(`/fund-detail/${fund.id}`)
}

const addToPortfolio = (fund) => {
  const index = selectedFunds.value.findIndex(f => f.id === fund.id)
  if (index === -1) {
    selectedFunds.value.push(fund)
    ElMessage.success('已添加到选择列表')
  } else {
    ElMessage.info('该基金已在选择列表中')
  }
}

const getReturnClass = (returnValue) => {
  if (!returnValue) return ''
  return returnValue > 0 ? 'positive-return' : 'negative-return'
}

const formatReturn = (returnValue) => {
  if (!returnValue) return '-'
  return `${(returnValue * 100).toFixed(2)}%`
}

const getRiskTagType = (riskLevel) => {
  switch (riskLevel) {
    case '低风险': return 'success'
    case '中风险': return 'warning'
    case '高风险': return 'danger'
    default: return 'info'
  }
}

onMounted(() => {
  loadFunds()
  loadFundCompanies()
})
</script>

<style scoped lang="scss">
.fund-research {
  .page-header {
    margin-bottom: 20px;
    
    h2 {
      margin: 0 0 8px 0;
      color: #333;
      font-size: 24px;
      font-weight: 600;
    }
    
    p {
      margin: 0;
      color: #666;
      font-size: 14px;
    }
  }
  
  .search-card {
    margin-bottom: 20px;
  }
  
  .fund-list-card {
    .card-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      
      .header-actions {
        display: flex;
        gap: 10px;
      }
    }
    
    .pagination-wrapper {
      margin-top: 20px;
      display: flex;
      justify-content: center;
    }
  }
  
  .positive-return {
    color: #67c23a;
  }
  
  .negative-return {
    color: #f56c6c;
  }
  
  .weights-container {
    .weight-item {
      display: flex;
      align-items: center;
      margin-bottom: 10px;
      
      .fund-name {
        flex: 1;
        margin-right: 10px;
      }
      
      .weight-unit {
        margin-left: 5px;
        color: #666;
      }
    }
  }
  
  .weight-total {
    margin-top: 10px;
    font-weight: 500;
    
    .weight-warning {
      color: #f56c6c;
      font-size: 12px;
    }
  }
}

@media (max-width: 768px) {
  .fund-research {
    .search-card {
      .el-form {
        .el-form-item {
          margin-bottom: 10px;
          
          .el-input,
          .el-select {
            width: 100% !important;
          }
        }
      }
    }
    
    .fund-list-card {
      .card-header {
        flex-direction: column;
        align-items: flex-start;
        gap: 10px;
        
        .header-actions {
          width: 100%;
          justify-content: space-between;
        }
      }
    }
  }
}
</style> 