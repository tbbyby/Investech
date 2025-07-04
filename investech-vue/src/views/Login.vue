<template>
  <div class="login-container">
    <div class="login-box">
      <div class="login-header">
        <h2>智能投顾系统</h2>
        <p>欢迎使用智能投顾平台</p>
      </div>
      
      <el-form
        ref="loginFormRef"
        :model="loginForm"
        :rules="loginRules"
        class="login-form"
        @submit.prevent="handleLogin"
      >
        <el-form-item prop="username">
          <el-input
            v-model="loginForm.username"
            placeholder="请输入用户名"
            size="large"
            prefix-icon="User"
          />
        </el-form-item>
        
        <el-form-item prop="password">
          <el-input
            v-model="loginForm.password"
            type="password"
            placeholder="请输入密码"
            size="large"
            prefix-icon="Lock"
            show-password
          />
        </el-form-item>
        
        <el-form-item>
          <el-button
            type="primary"
            size="large"
            class="login-button"
            :loading="loading"
            @click="handleLogin"
          >
            登录
          </el-button>
        </el-form-item>
        
        <div class="login-footer">
          <span>还没有账号？</span>
          <el-link type="primary" @click="$router.push('/register')">立即注册</el-link>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import axios from 'axios'

const router = useRouter()
const loginFormRef = ref()
const loading = ref(false)

const loginForm = reactive({
  username: '',
  password: ''
})

const loginRules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, message: '密码长度不能少于6位', trigger: 'blur' }
  ]
}

const handleLogin = async () => {
  if (!loginFormRef.value) return
  
  try {
    await loginFormRef.value.validate()
    loading.value = true
    
    const response = await axios.post('/api/user/login', loginForm)
    
    if (response.data.code === 200) {
      ElMessage.success('登录成功')
      localStorage.setItem('token', 'logged-in')
      localStorage.setItem('userInfo', JSON.stringify(response.data.data))
      router.push('/layout')
    } else {
      ElMessage.error(response.data.message || '登录失败')
    }
  } catch (error) {
    console.error('登录错误:', error)
    ElMessage.error('登录失败，请检查网络连接')
  } finally {
    loading.value = false
  }
}
</script>

<style scoped lang="scss">
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  
  .login-box {
    width: 400px;
    padding: 40px;
    background: white;
    border-radius: 12px;
    box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
    
    .login-header {
      text-align: center;
      margin-bottom: 30px;
      
      h2 {
        color: #333;
        margin-bottom: 8px;
        font-size: 28px;
        font-weight: 600;
      }
      
      p {
        color: #666;
        font-size: 14px;
        margin: 0;
      }
    }
    
    .login-form {
      .login-button {
        width: 100%;
        height: 48px;
        font-size: 16px;
        font-weight: 500;
        border-radius: 8px;
        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        border: none;
        
        &:hover {
          background: linear-gradient(135deg, #5a6fd8 0%, #6a4190 100%);
        }
      }
      
      .login-footer {
        text-align: center;
        margin-top: 20px;
        color: #666;
        font-size: 14px;
        
        .el-link {
          margin-left: 8px;
        }
      }
    }
  }
}

@media (max-width: 480px) {
  .login-container {
    padding: 20px;
    
    .login-box {
      width: 100%;
      padding: 30px 20px;
    }
  }
}
</style> 