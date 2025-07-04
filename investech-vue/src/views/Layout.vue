<template>
  <div class="layout-container">
    <!-- 顶部导航栏 -->
    <el-header class="header">
      <div class="header-left">
        <h2 class="logo">智能投顾系统</h2>
      </div>
      <div class="header-right">
        <el-dropdown @command="handleCommand">
          <span class="user-info">
            <el-avatar :size="32" icon="UserFilled" />
            <span class="username">{{ userInfo.realName || userInfo.username }}</span>
            <el-icon><ArrowDown /></el-icon>
          </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item command="profile">个人中心</el-dropdown-item>
              <el-dropdown-item command="logout" divided>退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
    </el-header>
    
    <!-- 主要内容区域 -->
    <div class="main-container">
      <!-- 侧边栏 -->
      <el-aside class="sidebar" width="250px">
        <el-menu
          :default-active="activeMenu"
          class="sidebar-menu"
          @select="handleMenuSelect"
        >
          <el-sub-menu index="/fund">
            <template #title>
              <el-icon><Document /></el-icon>
              <span>基金研究</span>
            </template>
            <el-menu-item index="/fund-research">基金列表</el-menu-item>
            <el-menu-item index="/fund-portfolio">基金组合</el-menu-item>
          </el-sub-menu>
          
          <el-sub-menu index="/factor">
            <template #title>
              <el-icon><Setting /></el-icon>
              <span>因子管理</span>
            </template>
            <el-menu-item index="/factor-management">因子接入</el-menu-item>
            <el-menu-item index="/factor-tree">因子树管理</el-menu-item>
            <el-menu-item index="/derived-factor">衍生因子</el-menu-item>
          </el-sub-menu>

          <el-sub-menu index="/quantitative">
            <template #title>
              <el-icon><TrendCharts /></el-icon>
              <span>量化投研</span>
            </template>
            <el-menu-item index="/strategy-management">策略管理</el-menu-item>
            <el-menu-item index="/backtest-analysis">回测分析</el-menu-item>
            <el-menu-item index="/risk-management">风险管理</el-menu-item>
            <el-menu-item index="/performance-evaluation">绩效评估</el-menu-item>
          </el-sub-menu>

          <el-sub-menu index="/advisory">
            <template #title>
              <el-icon><User /></el-icon>
              <span>智能投顾</span>
            </template>
            <el-menu-item index="/client-management">客户管理</el-menu-item>
            <el-menu-item index="/investment-advice">投资建议</el-menu-item>
            <el-menu-item index="/asset-allocation">资产配置</el-menu-item>
            <el-menu-item index="/portfolio-optimization">投资组合优化</el-menu-item>
          </el-sub-menu>

          <el-sub-menu index="/risk">
            <template #title>
              <el-icon><TrendCharts /></el-icon>
              <span>风险管理</span>
            </template>
            <el-menu-item index="/risk-monitor">风险监控</el-menu-item>
            <el-menu-item index="/compliance-check">合规检查</el-menu-item>
            <el-menu-item index="/risk-report">风险报告</el-menu-item>
          </el-sub-menu>
        </el-menu>
      </el-aside>
      
      <!-- 内容区域 -->
      <el-main class="main-content">
        <router-view />
      </el-main>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { ArrowDown, Document, Setting, TrendCharts, User } from '@element-plus/icons-vue'

const router = useRouter()
const route = useRoute()

const userInfo = ref({})

const activeMenu = computed(() => {
  return route.path
})

onMounted(() => {
  const userInfoStr = localStorage.getItem('userInfo')
  if (userInfoStr) {
    userInfo.value = JSON.parse(userInfoStr)
  }
})

const handleMenuSelect = (index) => {
  router.push(index)
}

const handleCommand = async (command) => {
  if (command === 'logout') {
    try {
      await ElMessageBox.confirm('确定要退出登录吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
      
      localStorage.removeItem('token')
      localStorage.removeItem('userInfo')
      ElMessage.success('已退出登录')
      router.push('/login')
    } catch {
      // 用户取消
    }
  } else if (command === 'profile') {
    ElMessage.info('个人中心功能开发中...')
  }
}
</script>

<style scoped lang="scss">
.layout-container {
  height: 100vh;
  display: flex;
  flex-direction: column;
  
  .header {
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    color: white;
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 0 20px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
    
    .header-left {
      .logo {
        margin: 0;
        font-size: 24px;
        font-weight: 600;
      }
    }
    
    .header-right {
      .user-info {
        display: flex;
        align-items: center;
        cursor: pointer;
        padding: 8px 12px;
        border-radius: 6px;
        transition: background-color 0.3s;
        
        &:hover {
          background-color: rgba(255, 255, 255, 0.1);
        }
        
        .username {
          margin: 0 8px;
          font-size: 14px;
        }
      }
    }
  }
  
  .main-container {
    flex: 1;
    display: flex;
    overflow: hidden;
    
    .sidebar {
      background: white;
      border-right: 1px solid #e6e6e6;
      box-shadow: 2px 0 8px rgba(0, 0, 0, 0.1);
      
      .sidebar-menu {
        border-right: none;
        height: 100%;
        
        .el-menu-item {
          height: 50px;
          line-height: 50px;
          
          &:hover {
            background-color: #f5f7fa;
          }
          
          &.is-active {
            background-color: #ecf5ff;
            color: #409eff;
          }
        }
        
        .el-sub-menu {
          .el-menu-item {
            height: 45px;
            line-height: 45px;
            padding-left: 50px;
          }
        }
      }
    }
    
    .main-content {
      background: #f5f5f5;
      padding: 20px;
      overflow-y: auto;
    }
  }
}

@media (max-width: 768px) {
  .layout-container {
    .header {
      padding: 0 15px;
      
      .header-left .logo {
        font-size: 20px;
      }
    }
    
    .main-container {
      .sidebar {
        width: 200px !important;
      }
      
      .main-content {
        padding: 15px;
      }
    }
  }
}
</style> 