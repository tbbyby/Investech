import { createRouter, createWebHistory } from 'vue-router'
import Login from '../views/Login.vue'
import Register from '../views/Register.vue'
import Layout from '../views/Layout.vue'
import Test from '../views/Test.vue'

const routes = [
  {
    path: '/',
    name: 'Test',
    component: Test
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/register',
    name: 'Register',
    component: Register
  },
  {
    path: '/layout',
    component: Layout,
    redirect: '/fund-research',
    children: [
      {
        path: '/fund-research',
        name: 'FundResearch',
        component: () => import('../views/FundResearch.vue'),
        meta: { title: '基金研究' }
      },
      {
        path: '/fund-detail/:id',
        name: 'FundDetail',
        component: () => import('../views/FundDetail.vue'),
        meta: { title: '基金详情' }
      },
      {
        path: '/fund-portfolio',
        name: 'FundPortfolio',
        component: () => import('../views/Portfolio.vue'),
        meta: { title: '基金组合' }
      },
      {
        path: '/factor-management',
        name: 'FactorManagement',
        component: () => import('../views/factor/FactorList.vue'),
        meta: { title: '因子管理' }
      },
      {
        path: '/factor-tree',
        name: 'FactorTree',
        component: () => import('../views/factor/FactorTree.vue'),
        meta: { title: '因子树管理' }
      },
      {
        path: '/derived-factor',
        name: 'DerivedFactor',
        component: () => import('../views/factor/DerivedFactor.vue'),
        meta: { title: '衍生因子管理' }
      },
      {
        path: '/strategy-management',
        name: 'StrategyManagement',
        component: () => import('../views/quantitative/Strategy.vue'),
        meta: { title: '策略管理' }
      },
      {
        path: '/backtest-analysis',
        name: 'BacktestAnalysis',
        component: () => import('../views/quantitative/Backtest.vue'),
        meta: { title: '回测分析' }
      },
      {
        path: '/risk-management',
        name: 'RiskManagement',
        component: () => import('../views/quantitative/Risk.vue'),
        meta: { title: '风险管理' }
      },
      {
        path: '/performance-evaluation',
        name: 'PerformanceEvaluation',
        component: () => import('../views/quantitative/Performance.vue'),
        meta: { title: '绩效评估' }
      },
      // 智能投顾子系统路由
      {
        path: '/client-management',
        name: 'ClientManagement',
        component: () => import('../views/advisory/ClientManagement.vue'),
        meta: { title: '客户管理' }
      },
      {
        path: '/investment-advice',
        name: 'InvestmentAdvice',
        component: () => import('../views/advisory/InvestmentAdvice.vue'),
        meta: { title: '投资建议' }
      },
      {
        path: '/asset-allocation',
        name: 'AssetAllocation',
        component: () => import('../views/advisory/AssetAllocation.vue'),
        meta: { title: '资产配置' }
      },
      {
        path: '/portfolio-optimization',
        name: 'PortfolioOptimization',
        component: () => import('../views/advisory/PortfolioOptimization.vue'),
        meta: { title: '投资组合优化' }
      },
      // 风险管理与合规模块
      {
        path: '/risk-monitor',
        name: 'RiskMonitor',
        component: () => import('../views/risk/RiskMonitor.vue'),
        meta: { title: '风险监控' }
      },
      {
        path: '/compliance-check',
        name: 'ComplianceCheck',
        component: () => import('../views/risk/ComplianceCheck.vue'),
        meta: { title: '合规检查' }
      },
      {
        path: '/risk-report',
        name: 'RiskReport',
        component: () => import('../views/risk/RiskReport.vue'),
        meta: { title: '风险报告' }
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 路由守卫
router.beforeEach((to, from, next) => {
  console.log('路由跳转:', to.path)
  const token = localStorage.getItem('token')
  
  if (to.path === '/login' || to.path === '/register') {
    next()
  } else if (to.path === '/') {
    if (token) {
      next('/fund-research')
    } else {
      next('/login')
    }
  } else {
    if (token) {
      next()
    } else {
      next('/login')
    }
  }
})

export default router 