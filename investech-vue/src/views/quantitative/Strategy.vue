<template>
  <div class="strategy-page">
    <h2>策略管理</h2>
    <el-button type="primary" @click="loadData">加载数据</el-button>
    <el-table :data="strategyList" v-loading="loading">
      <el-table-column prop="strategyCode" label="策略代码" />
      <el-table-column prop="strategyName" label="策略名称" />
      <el-table-column prop="strategyType" label="策略类型" />
      <el-table-column prop="riskLevel" label="风险等级" />
      <el-table-column prop="createTime" label="创建时间" />
    </el-table>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

const loading = ref(false)
const strategyList = ref([])

const loadData = async () => {
  loading.value = true
  try {
    const response = await axios.get('/api/strategy/list')
    if (response.data.code === 200) {
      strategyList.value = response.data.data
    }
  } catch (error) {
    console.error('加载策略数据失败:', error)
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.strategy-page {
  padding: 20px;
}
</style> 