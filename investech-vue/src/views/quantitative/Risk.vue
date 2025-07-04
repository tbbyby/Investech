<template>
  <div class="risk-page">
    <h2>风险管理</h2>
    <el-button type="primary" @click="loadData">加载数据</el-button>
    <el-table :data="riskList" v-loading="loading">
      <el-table-column prop="riskCode" label="风险代码" />
      <el-table-column prop="riskName" label="风险名称" />
      <el-table-column prop="riskType" label="风险类型" />
      <el-table-column prop="riskLevel" label="风险等级" />
      <el-table-column prop="riskValue" label="风险值" />
      <el-table-column prop="riskLimit" label="风险限额" />
      <el-table-column prop="status" label="状态" />
      <el-table-column prop="createTime" label="创建时间" />
    </el-table>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

const loading = ref(false)
const riskList = ref([])

const loadData = async () => {
  loading.value = true
  try {
    const response = await axios.get('/api/risk/list')
    if (response.data.code === 200) {
      riskList.value = response.data.data
    }
  } catch (error) {
    console.error('加载风险管理数据失败:', error)
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.risk-page {
  padding: 20px;
}
</style> 