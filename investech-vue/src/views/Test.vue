<template>
  <div class="test-page">
    <h1>测试页面</h1>
    <p>如果你能看到这个页面，说明路由配置正常。</p>
    <el-button type="primary" @click="goToLogin">去登录页面</el-button>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'

const router = useRouter()

const goToLogin = () => {
  router.push('/login')
}
</script>

<style scoped>
.test-page {
  padding: 50px;
  text-align: center;
}

h1 {
  color: #409eff;
  margin-bottom: 20px;
}

p {
  color: #666;
  margin-bottom: 30px;
}
</style> 