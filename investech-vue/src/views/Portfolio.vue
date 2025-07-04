<template>
  <div class="portfolio">
    <!-- 页面标题 -->
    <div class="page-header">
      <h2>基金组合</h2>
      <p>管理您的基金投资组合</p>
    </div>
    
    <!-- 组合列表 -->
    <el-card class="portfolio-card">
      <template #header>
        <div class="card-header">
          <span>我的组合 ({{ portfolioList.length }})</span>
          <el-button type="primary" @click="handleCreatePortfolio">
            <el-icon><Plus /></el-icon>
            创建新组合
          </el-button>
        </div>
      </template>
      
      <div v-if="portfolioList.length === 0" class="empty-state">
        <el-empty description="暂无基金组合">
          <el-button type="primary" @click="handleCreatePortfolio">创建第一个组合</el-button>
        </el-empty>
      </div>
      
      <el-table v-else :data="portfolioList" style="width: 100%">
        <el-table-column prop="portfolioName" label="组合名称" />
        <el-table-column prop="portfolioDesc" label="组合描述" />
        <el-table-column label="基金数量" width="100">
          <template #default="{ row }">
            {{ getFundCount(row) }}只
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间">
          <template #default="{ row }">
            {{ formatDate(row.createTime) }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200">
          <template #default="{ row }">
            <el-button type="primary" size="small" @click="viewPortfolio(row)">
              查看
            </el-button>
            <el-button type="danger" size="small" @click="deletePortfolio(row)">
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    
    <!-- 创建组合对话框 -->
    <el-dialog
      v-model="createDialogVisible"
      title="创建基金组合"
      width="600px"
    >
      <el-form :model="createForm" :rules="createRules" ref="createFormRef">
        <el-form-item label="组合名称" prop="portfolioName">
          <el-input v-model="createForm.portfolioName" placeholder="请输入组合名称" />
        </el-form-item>
        <el-form-item label="组合描述" prop="portfolioDesc">
          <el-input
            v-model="createForm.portfolioDesc"
            type="textarea"
            :rows="3"
            placeholder="请输入组合描述"
          />
        </el-form-item>
        <el-form-item label="选择基金">
          <el-select
            v-model="createForm.selectedFunds"
            multiple
            filterable
            placeholder="请选择基金"
            style="width: 100%"
          >
            <el-option
              v-for="fund in fundList"
              :key="fund.id"
              :label="`${fund.fundCode} - ${fund.fundName}`"
              :value="fund.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="基金权重" v-if="createForm.selectedFunds.length > 0">
          <div class="weights-container">
            <div
              v-for="fundId in createForm.selectedFunds"
              :key="fundId"
              class="weight-item"
            >
              <span class="fund-name">{{ getFundName(fundId) }}</span>
              <el-input-number
                v-model="fundWeights[fundId]"
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
        <el-button @click="createDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitCreate" :disabled="totalWeight !== 100">
          创建组合
        </el-button>
      </template>
    </el-dialog>

    <!-- 组合详情对话框 -->
    <el-dialog
      v-model="viewDialogVisible"
      :title="`组合详情 - ${currentPortfolio?.portfolioName}`"
      width="900px"
    >
      <div v-if="currentPortfolio" class="portfolio-detail">
        <!-- 组合基本信息 -->
        <el-card class="detail-card">
          <template #header>
            <div class="card-header">
              <span>基本信息</span>
            </div>
          </template>
          <el-descriptions :column="2" border>
            <el-descriptions-item label="组合名称">{{ currentPortfolio.portfolioName }}</el-descriptions-item>
            <el-descriptions-item label="创建时间">{{ formatDate(currentPortfolio.createTime) }}</el-descriptions-item>
            <el-descriptions-item label="基金数量">{{ getFundCount(currentPortfolio) }}只</el-descriptions-item>
            <el-descriptions-item label="组合描述">{{ currentPortfolio.portfolioDesc || '暂无描述' }}</el-descriptions-item>
          </el-descriptions>
        </el-card>

        <!-- 基金列表 -->
        <el-card class="detail-card">
          <template #header>
            <div class="card-header">
              <span>基金配置</span>
            </div>
          </template>
          <el-table :data="portfolioFunds" stripe>
            <el-table-column prop="fundCode" label="基金代码" width="120" />
            <el-table-column prop="fundName" label="基金名称" min-width="200" />
            <el-table-column prop="weight" label="权重" width="100">
              <template #default="{ row }">
                <el-tag type="primary">{{ row.weight }}%</el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="fundType" label="基金类型" width="100" />
            <el-table-column prop="nav" label="最新净值" width="100">
              <template #default="{ row }">
                {{ row.nav ? row.nav.toFixed(4) : '-' }}
              </template>
            </el-table-column>
            <el-table-column prop="returnRate" label="收益率" width="100">
              <template #default="{ row }">
                <span :class="row.returnRate >= 0 ? 'positive' : 'negative'">
                  {{ row.returnRate ? (row.returnRate * 100).toFixed(2) + '%' : '-' }}
                </span>
              </template>
            </el-table-column>
          </el-table>
        </el-card>

        <!-- 权重分布图表 -->
        <el-card class="detail-card">
          <template #header>
            <div class="card-header">
              <span>权重分布</span>
            </div>
          </template>
          <div class="weight-chart">
            <div class="weight-bars">
              <div
                v-for="fund in portfolioFunds"
                :key="fund.fundCode"
                class="weight-bar-item"
              >
                <div class="fund-info">
                  <span class="fund-code">{{ fund.fundCode }}</span>
                  <span class="fund-name">{{ fund.fundName }}</span>
                </div>
                <div class="weight-bar">
                  <div
                    class="weight-fill"
                    :style="{ width: fund.weight + '%' }"
                  ></div>
                  <span class="weight-text">{{ fund.weight }}%</span>
                </div>
              </div>
            </div>
          </div>
        </el-card>

        <!-- 收益分析 -->
        <el-card class="detail-card">
          <template #header>
            <div class="card-header">
              <span>收益分析</span>
            </div>
          </template>
          <el-row :gutter="20">
            <el-col :span="6">
              <div class="metric-item">
                <div class="metric-value positive">+12.5%</div>
                <div class="metric-label">总收益率</div>
              </div>
            </el-col>
            <el-col :span="6">
              <div class="metric-item">
                <div class="metric-value">8.2%</div>
                <div class="metric-label">年化收益率</div>
              </div>
            </el-col>
            <el-col :span="6">
              <div class="metric-item">
                <div class="metric-value">15.3%</div>
                <div class="metric-label">最大回撤</div>
              </div>
            </el-col>
            <el-col :span="6">
              <div class="metric-item">
                <div class="metric-value">1.25</div>
                <div class="metric-label">夏普比率</div>
              </div>
            </el-col>
          </el-row>
        </el-card>
      </div>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Plus } from '@element-plus/icons-vue'
import axios from 'axios'

// 数据
const portfolioList = ref([])
const fundList = ref([])
const createDialogVisible = ref(false)
const viewDialogVisible = ref(false)
const createFormRef = ref()
const currentPortfolio = ref(null)

// 创建表单
const createForm = reactive({
  portfolioName: '',
  portfolioDesc: '',
  selectedFunds: []
})

const fundWeights = ref({})

const createRules = {
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

const portfolioFunds = computed(() => {
  if (!currentPortfolio.value) return []
  
  const fundCodes = getFundCodes(currentPortfolio.value)
  const weights = getWeights(currentPortfolio.value)
  
  return fundCodes.map(fundCode => {
    const fund = fundList.value.find(f => f.fundCode === fundCode)
    // 用基金ID查找权重，然后转换为百分比显示
    const weightPercent = fund && weights[fund.id] ? (weights[fund.id] * 100) : 0
    return {
      fundCode,
      fundName: fund ? fund.fundName : '未知基金',
      fundType: fund ? fund.fundType : '-',
      nav: fund ? fund.nav : null,
      returnRate: fund ? fund.returnRate : null,
      weight: weightPercent
    }
  })
})

// 方法
const loadPortfolios = async () => {
  try {
    const userInfo = JSON.parse(localStorage.getItem('userInfo') || '{}')
    const response = await axios.get(`/api/portfolio/user/${userInfo.id}`)
    
    if (response.data.code === 200) {
      portfolioList.value = response.data.data
    }
  } catch (error) {
    console.error('加载组合失败:', error)
    ElMessage.error('加载组合失败')
  }
}

const loadFunds = async () => {
  try {
    const response = await axios.get('/api/fund/list')
    if (response.data.code === 200) {
      fundList.value = response.data.data
    }
  } catch (error) {
    console.error('加载基金失败:', error)
  }
}

const handleCreatePortfolio = () => {
  createForm.portfolioName = ''
  createForm.portfolioDesc = ''
  createForm.selectedFunds = []
  fundWeights.value = {}
  createDialogVisible.value = true
}

const updateWeights = () => {
  // 权重更新逻辑
}

const submitCreate = async () => {
  if (!createFormRef.value) return
  
  try {
    await createFormRef.value.validate()
    
    if (totalWeight.value !== 100) {
      ElMessage.warning('权重总和必须为100%')
      return
    }
    
    const userInfo = JSON.parse(localStorage.getItem('userInfo') || '{}')
    
    // 将百分比权重转换为小数形式，使用基金ID作为key
    const weightsForDB = {}
    Object.keys(fundWeights.value).forEach(fundId => {
      weightsForDB[fundId] = (fundWeights.value[fundId] || 0) / 100
    })
    
    const portfolioData = {
      portfolioName: createForm.portfolioName,
      portfolioDesc: createForm.portfolioDesc,
      userId: userInfo.id,
      fundCodes: createForm.selectedFunds.map(fundId => {
        const fund = fundList.value.find(f => f.id === fundId)
        return fund ? fund.fundCode : ''
      }).join(','),
      weights: JSON.stringify(weightsForDB)
    }
    
    const response = await axios.post('/api/portfolio/create', portfolioData)
    
    if (response.data.code === 200) {
      ElMessage.success('组合创建成功')
      createDialogVisible.value = false
      loadPortfolios()
    } else {
      ElMessage.error(response.data.message || '创建失败')
    }
  } catch (error) {
    console.error('创建组合失败:', error)
    ElMessage.error('创建组合失败')
  }
}

const viewPortfolio = (portfolio) => {
  currentPortfolio.value = portfolio
  viewDialogVisible.value = true
}

const deletePortfolio = async (portfolio) => {
  try {
    await ElMessageBox.confirm('确定要删除这个组合吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    
    const response = await axios.delete(`/api/portfolio/${portfolio.id}`)
    
    if (response.data.code === 200) {
      ElMessage.success('删除成功')
      loadPortfolios()
    } else {
      ElMessage.error(response.data.message || '删除失败')
    }
  } catch (error) {
    if (error !== 'cancel') {
      console.error('删除组合失败:', error)
      ElMessage.error('删除组合失败')
    }
  }
}

const getFundCount = (portfolio) => {
  return getFundCodes(portfolio).length
}

const getFundCodes = (portfolio) => {
  if (!portfolio.fundCodes) return []
  try {
    // 尝试解析为JSON数组
    const codes = JSON.parse(portfolio.fundCodes)
    if (Array.isArray(codes)) {
      return codes
    }
  } catch {
    // 如果不是JSON格式，按逗号分隔处理
    return portfolio.fundCodes.split(',').filter(code => code.trim())
  }
  return []
}

const getWeights = (portfolio) => {
  if (!portfolio.weights) return {}
  try {
    return JSON.parse(portfolio.weights)
  } catch {
    return {}
  }
}

const getFundWeight = (portfolio, fundCode) => {
  if (!portfolio.weights) return 0
  try {
    const weights = JSON.parse(portfolio.weights)
    return weights[fundCode] || 0
  } catch {
    return 0
  }
}

const getFundName = (fundId) => {
  const fund = fundList.value.find(f => f.id === fundId)
  return fund ? `${fund.fundCode} - ${fund.fundName}` : ''
}

const formatDate = (dateStr) => {
  if (!dateStr) return '-'
  return new Date(dateStr).toLocaleDateString()
}

onMounted(() => {
  loadPortfolios()
  loadFunds()
})
</script>

<style scoped lang="scss">
.portfolio {
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
  
  .portfolio-card {
    .card-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
    }
    
    .empty-state {
      padding: 40px 0;
    }
  }
  
  .weights-container {
    .weight-item {
      display: flex;
      align-items: center;
      margin-bottom: 10px;
      
      .fund-name {
        flex: 1;
        margin-right: 10px;
        font-size: 14px;
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

  .portfolio-detail {
    .detail-card {
      margin-bottom: 20px;
      
      .card-header {
        font-weight: 600;
        color: #333;
      }
    }

    .weight-chart {
      .weight-bars {
        .weight-bar-item {
          margin-bottom: 15px;
          
          .fund-info {
            display: flex;
            align-items: center;
            margin-bottom: 5px;
            
            .fund-code {
              font-weight: 600;
              color: #409eff;
              margin-right: 10px;
              min-width: 80px;
            }
            
            .fund-name {
              color: #666;
              font-size: 14px;
            }
          }
          
          .weight-bar {
            position: relative;
            height: 30px;
            background-color: #f5f7fa;
            border-radius: 4px;
            overflow: hidden;
            
            .weight-fill {
              height: 100%;
              background: linear-gradient(90deg, #409eff, #67c23a);
              transition: width 0.3s ease;
            }
            
            .weight-text {
              position: absolute;
              right: 10px;
              top: 50%;
              transform: translateY(-50%);
              color: #333;
              font-weight: 600;
              font-size: 12px;
            }
          }
        }
      }
    }

    .metric-item {
      text-align: center;
      padding: 20px;
      background: #f8f9fa;
      border-radius: 8px;
      
      .metric-value {
        font-size: 24px;
        font-weight: 600;
        color: #333;
        margin-bottom: 5px;
        
        &.positive {
          color: #67c23a;
        }
        
        &.negative {
          color: #f56c6c;
        }
      }
      
      .metric-label {
        font-size: 12px;
        color: #666;
      }
    }
  }
}

.positive {
  color: #67c23a;
}

.negative {
  color: #f56c6c;
}

@media (max-width: 768px) {
  .portfolio {
    .portfolio-card {
      .portfolio-grid {
        grid-template-columns: 1fr;
        
        .portfolio-item {
          .portfolio-header {
            flex-direction: column;
            align-items: flex-start;
            gap: 10px;
            
            .portfolio-actions {
              width: 100%;
              justify-content: flex-end;
            }
          }
        }
      }
    }
  }
}
</style> 