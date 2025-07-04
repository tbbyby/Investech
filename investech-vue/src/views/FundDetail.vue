<template>
  <div class="fund-detail">
    <div class="page-header">
      <el-button @click="$router.go(-1)" icon="ArrowLeft">返回</el-button>
      <h2>基金详情</h2>
    </div>
    
    <div v-if="fund" class="fund-content">
      <!-- 基本信息 -->
      <el-card class="info-card">
        <template #header>
          <h3>基本信息</h3>
        </template>
        <el-row :gutter="20">
          <el-col :span="8">
            <div class="info-item">
              <label>基金代码：</label>
              <span>{{ fund.fundCode }}</span>
            </div>
          </el-col>
          <el-col :span="8">
            <div class="info-item">
              <label>基金名称：</label>
              <span>{{ fund.fundName }}</span>
            </div>
          </el-col>
          <el-col :span="8">
            <div class="info-item">
              <label>基金类型：</label>
              <span>{{ fund.fundType }}</span>
            </div>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="8">
            <div class="info-item">
              <label>基金公司：</label>
              <span>{{ fund.fundCompany }}</span>
            </div>
          </el-col>
          <el-col :span="8">
            <div class="info-item">
              <label>基金经理：</label>
              <span>{{ fund.fundManager }}</span>
            </div>
          </el-col>
          <el-col :span="8">
            <div class="info-item">
              <label>风险等级：</label>
              <el-tag :type="getRiskTagType(fund.riskLevel)" size="small">
                {{ fund.riskLevel }}
              </el-tag>
            </div>
          </el-col>
        </el-row>
      </el-card>
      
      <!-- 净值信息 -->
      <el-card class="info-card">
        <template #header>
          <h3>净值信息</h3>
        </template>
        <el-row :gutter="20">
          <el-col :span="6">
            <div class="info-item">
              <label>最新净值：</label>
              <span class="net-value">{{ fund.netValue?.toFixed(4) || '-' }}</span>
            </div>
          </el-col>
          <el-col :span="6">
            <div class="info-item">
              <label>净值日期：</label>
              <span>{{ fund.netValueDate || '-' }}</span>
            </div>
          </el-col>
          <el-col :span="6">
            <div class="info-item">
              <label>总资产规模：</label>
              <span>{{ formatAssets(fund.totalAssets) }}</span>
            </div>
          </el-col>
          <el-col :span="6">
            <div class="info-item">
              <label>夏普比率：</label>
              <span>{{ fund.sharpeRatio?.toFixed(4) || '-' }}</span>
            </div>
          </el-col>
        </el-row>
      </el-card>
      
      <!-- 收益表现 -->
      <el-card class="info-card">
        <template #header>
          <h3>收益表现</h3>
        </template>
        <el-row :gutter="20">
          <el-col :span="6">
            <div class="return-item">
              <label>日收益率：</label>
              <span :class="getReturnClass(fund.dailyReturn)">
                {{ formatReturn(fund.dailyReturn) }}
              </span>
            </div>
          </el-col>
          <el-col :span="6">
            <div class="return-item">
              <label>周收益率：</label>
              <span :class="getReturnClass(fund.weeklyReturn)">
                {{ formatReturn(fund.weeklyReturn) }}
              </span>
            </div>
          </el-col>
          <el-col :span="6">
            <div class="return-item">
              <label>月收益率：</label>
              <span :class="getReturnClass(fund.monthlyReturn)">
                {{ formatReturn(fund.monthlyReturn) }}
              </span>
            </div>
          </el-col>
          <el-col :span="6">
            <div class="return-item">
              <label>年收益率：</label>
              <span :class="getReturnClass(fund.yearlyReturn)">
                {{ formatReturn(fund.yearlyReturn) }}
              </span>
            </div>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <div class="return-item">
              <label>最大回撤：</label>
              <span class="negative-return">
                {{ formatReturn(fund.maxDrawdown) }}
              </span>
            </div>
          </el-col>
        </el-row>
      </el-card>
      
      <!-- 基金标签 -->
      <el-card class="info-card" v-if="fund.tags">
        <template #header>
          <h3>基金标签</h3>
        </template>
        <div class="tags-container">
          <el-tag
            v-for="tag in parseTags(fund.tags)"
            :key="tag"
            size="small"
            style="margin-right: 8px; margin-bottom: 8px;"
          >
            {{ tag }}
          </el-tag>
        </div>
      </el-card>
    </div>
    
    <div v-else class="loading-container">
      <el-empty description="基金信息加载中..." />
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
import { ArrowLeft } from '@element-plus/icons-vue'
import axios from 'axios'

const route = useRoute()
const fund = ref(null)

const loadFundDetail = async () => {
  try {
    const fundId = route.params.id
    const response = await axios.get(`/api/fund/${fundId}`)
    
    if (response.data.code === 200) {
      fund.value = response.data.data
    } else {
      ElMessage.error('基金不存在')
    }
  } catch (error) {
    console.error('加载基金详情失败:', error)
    ElMessage.error('加载基金详情失败')
  }
}

const getRiskTagType = (riskLevel) => {
  switch (riskLevel) {
    case '低风险': return 'success'
    case '中风险': return 'warning'
    case '高风险': return 'danger'
    default: return 'info'
  }
}

const formatAssets = (assets) => {
  if (!assets) return '-'
  if (assets >= 100000000) {
    return `${(assets / 100000000).toFixed(2)}亿`
  } else if (assets >= 10000) {
    return `${(assets / 10000).toFixed(2)}万`
  }
  return assets.toFixed(2)
}

const getReturnClass = (returnValue) => {
  if (!returnValue) return ''
  return returnValue > 0 ? 'positive-return' : 'negative-return'
}

const formatReturn = (returnValue) => {
  if (!returnValue) return '-'
  return `${(returnValue * 100).toFixed(2)}%`
}

const parseTags = (tags) => {
  if (!tags) return []
  try {
    return JSON.parse(tags)
  } catch {
    return tags.split(',').map(tag => tag.trim())
  }
}

onMounted(() => {
  loadFundDetail()
})
</script>

<style scoped lang="scss">
.fund-detail {
  .page-header {
    display: flex;
    align-items: center;
    margin-bottom: 20px;
    gap: 15px;
    
    h2 {
      margin: 0;
      color: #333;
      font-size: 24px;
      font-weight: 600;
    }
  }
  
  .fund-content {
    .info-card {
      margin-bottom: 20px;
      
      :deep(.el-card__header) {
        h3 {
          margin: 0;
          color: #333;
          font-size: 18px;
          font-weight: 600;
        }
      }
      
      .info-item, .return-item {
        margin-bottom: 15px;
        
        label {
          font-weight: 500;
          color: #666;
          margin-right: 8px;
        }
        
        span {
          color: #333;
        }
        
        .net-value {
          font-size: 18px;
          font-weight: 600;
          color: #409eff;
        }
      }
      
      .positive-return {
        color: #67c23a;
        font-weight: 500;
      }
      
      .negative-return {
        color: #f56c6c;
        font-weight: 500;
      }
      
      .tags-container {
        display: flex;
        flex-wrap: wrap;
      }
    }
  }
  
  .loading-container {
    display: flex;
    justify-content: center;
    align-items: center;
    min-height: 400px;
  }
}

@media (max-width: 768px) {
  .fund-detail {
    .fund-content {
      .info-card {
        .el-row {
          .el-col {
            margin-bottom: 10px;
          }
        }
      }
    }
  }
}
</style> 