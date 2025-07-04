<template>
  <div class="factor-tree">
    <div class="page-header">
      <h2>因子树管理</h2>
      <el-button type="primary" @click="showAddDialog = true">
        <el-icon><Plus /></el-icon>
        创建因子树
      </el-button>
    </div>

    <!-- 搜索区域 -->
    <div class="search-area">
      <el-form :model="searchForm" inline>
        <el-form-item label="树名称">
          <el-input v-model="searchForm.treeName" placeholder="请输入树名称" clearable />
        </el-form-item>
        <el-form-item label="业务场景">
          <el-select v-model="searchForm.businessScene" placeholder="请选择业务场景" clearable>
            <el-option label="基金研究" value="基金研究" />
            <el-option label="量化投研" value="量化投研" />
            <el-option label="特色数据" value="特色数据" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="searchTrees">搜索</el-button>
          <el-button @click="resetSearch">重置</el-button>
        </el-form-item>
      </el-form>
    </div>

    <!-- 因子树列表 -->
    <div class="tree-table">
      <el-table :data="treeList" v-loading="loading" stripe>
        <el-table-column prop="treeName" label="树名称" width="200" />
        <el-table-column prop="treeDesc" label="树描述" show-overflow-tooltip />
        <el-table-column prop="businessScene" label="业务场景" width="120" />
        <el-table-column prop="factorCodes" label="因子数量" width="100">
          <template #default="scope">
            <el-tag type="info">
              {{ getFactorCount(scope.row.factorCodes) }}个因子
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="80">
          <template #default="scope">
            <el-tag :type="scope.row.status === 1 ? 'success' : 'danger'">
              {{ scope.row.status === 1 ? '正常' : '禁用' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="180" />
        <el-table-column label="操作" width="250" fixed="right">
          <template #default="scope">
            <el-button type="primary" size="small" @click="viewTree(scope.row)">查看</el-button>
            <el-button type="success" size="small" @click="editTree(scope.row)">编辑</el-button>
            <el-button type="danger" size="small" @click="deleteTree(scope.row)">删除</el-button>
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

    <!-- 新增/编辑因子树对话框 -->
    <el-dialog
      v-model="showAddDialog"
      :title="isEdit ? '编辑因子树' : '创建因子树'"
      width="700px"
    >
      <el-form :model="treeForm" :rules="rules" ref="treeFormRef" label-width="100px">
        <el-form-item label="树名称" prop="treeName">
          <el-input v-model="treeForm.treeName" placeholder="请输入树名称" />
        </el-form-item>
        <el-form-item label="树描述" prop="treeDesc">
          <el-input
            v-model="treeForm.treeDesc"
            type="textarea"
            :rows="3"
            placeholder="请输入树描述"
          />
        </el-form-item>
        <el-form-item label="业务场景" prop="businessScene">
          <el-select v-model="treeForm.businessScene" placeholder="请选择业务场景">
            <el-option label="基金研究" value="基金研究" />
            <el-option label="量化投研" value="量化投研" />
            <el-option label="特色数据" value="特色数据" />
          </el-select>
        </el-form-item>
        <el-form-item label="选择因子" prop="factorCodes">
          <div class="factor-selector">
            <el-button type="primary" @click="showFactorSelector = true">
              选择因子
            </el-button>
            <div class="selected-factors" v-if="selectedFactors.length > 0">
              <el-tag
                v-for="factor in selectedFactors"
                :key="factor.factorCode"
                closable
                @close="removeFactor(factor)"
                style="margin: 5px"
              >
                {{ factor.factorName }}
              </el-tag>
            </div>
          </div>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="treeForm.status">
            <el-radio :label="1">正常</el-radio>
            <el-radio :label="0">禁用</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="showAddDialog = false">取消</el-button>
          <el-button type="primary" @click="saveTree">确定</el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 因子选择器对话框 -->
    <el-dialog
      v-model="showFactorSelector"
      title="选择因子"
      width="800px"
    >
      <div class="factor-selector-content">
        <div class="filter-area">
          <el-input
            v-model="factorSearchKeyword"
            placeholder="搜索因子名称或代码"
            clearable
            @input="filterFactors"
            style="width: 300px"
          />
        </div>
        <el-table
          :data="filteredFactorList"
          @selection-change="handleFactorSelectionChange"
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
          <el-button @click="showFactorSelector = false">取消</el-button>
          <el-button type="primary" @click="confirmFactorSelection">确定</el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 查看因子树对话框 -->
    <el-dialog
      v-model="showViewDialog"
      title="因子树详情"
      width="800px"
    >
      <div class="tree-detail">
        <div class="tree-info">
          <h3>{{ currentTree.treeName }}</h3>
          <p><strong>描述：</strong>{{ currentTree.treeDesc }}</p>
          <p><strong>业务场景：</strong>{{ currentTree.businessScene }}</p>
          <p><strong>创建时间：</strong>{{ currentTree.createTime }}</p>
        </div>
        <div class="tree-factors">
          <h4>包含因子</h4>
          <el-table :data="treeFactorList" stripe>
            <el-table-column prop="factorCode" label="因子代码" width="120" />
            <el-table-column prop="factorName" label="因子名称" width="150" />
            <el-table-column prop="factorType" label="因子类型" width="100" />
            <el-table-column prop="category" label="因子分类" width="100" />
            <el-table-column prop="factorDesc" label="因子描述" show-overflow-tooltip />
          </el-table>
        </div>
      </div>
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
const treeList = ref([])
const factorList = ref([])
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)
const showAddDialog = ref(false)
const showFactorSelector = ref(false)
const showViewDialog = ref(false)
const isEdit = ref(false)
const treeFormRef = ref()
const factorSearchKeyword = ref('')
const selectedFactors = ref([])
const tempSelectedFactors = ref([])
const currentTree = ref({})
const treeFactorList = ref([])

// 搜索表单
const searchForm = reactive({
  treeName: '',
  businessScene: ''
})

// 因子树表单
const treeForm = reactive({
  id: null,
  treeName: '',
  treeDesc: '',
  businessScene: '',
  factorCodes: '',
  status: 1
})

// 表单验证规则
const rules = {
  treeName: [
    { required: true, message: '请输入树名称', trigger: 'blur' }
  ],
  businessScene: [
    { required: true, message: '请选择业务场景', trigger: 'change' }
  ]
}

// 过滤后的因子列表
const filteredFactorList = computed(() => {
  if (!factorSearchKeyword.value) {
    return factorList.value
  }
  return factorList.value.filter(factor => 
    factor.factorName.includes(factorSearchKeyword.value) ||
    factor.factorCode.includes(factorSearchKeyword.value)
  )
})

// 获取因子树列表
const getTreeList = async () => {
  loading.value = true
  try {
    const response = await axios.get('/api/factor-tree/list')
    if (response.data.code === 200) {
      treeList.value = response.data.data
      total.value = response.data.data.length
    }
  } catch (error) {
    ElMessage.error('获取因子树列表失败')
  } finally {
    loading.value = false
  }
}

// 获取因子列表
const getFactorList = async () => {
  try {
    const response = await axios.get('/api/factor/list')
    if (response.data.code === 200) {
      factorList.value = response.data.data
    }
  } catch (error) {
    ElMessage.error('获取因子列表失败')
  }
}

// 搜索因子树
const searchTrees = async () => {
  loading.value = true
  try {
    const params = {
      treeName: searchForm.treeName,
      businessScene: searchForm.businessScene
    }
    const response = await axios.get('/api/factor-tree/search', { params })
    if (response.data.code === 200) {
      treeList.value = response.data.data
      total.value = response.data.data.length
    }
  } catch (error) {
    ElMessage.error('搜索因子树失败')
  } finally {
    loading.value = false
  }
}

// 重置搜索
const resetSearch = () => {
  Object.keys(searchForm).forEach(key => {
    searchForm[key] = ''
  })
  getTreeList()
}

// 获取因子数量
const getFactorCount = (factorCodes) => {
  if (!factorCodes) return 0
  try {
    const codes = JSON.parse(factorCodes)
    return codes.length
  } catch (error) {
    return 0
  }
}

// 查看因子树
const viewTree = async (row) => {
  currentTree.value = row
  try {
    const factorCodes = JSON.parse(row.factorCodes || '[]')
    const factorPromises = factorCodes.map(code => 
      axios.get(`/api/factor/code/${code}`)
    )
    const responses = await Promise.all(factorPromises)
    treeFactorList.value = responses
      .filter(res => res.data.code === 200)
      .map(res => res.data.data)
  } catch (error) {
    treeFactorList.value = []
  }
  showViewDialog.value = true
}

// 编辑因子树
const editTree = async (row) => {
  isEdit.value = true
  Object.keys(treeForm).forEach(key => {
    if (row[key] !== undefined) {
      treeForm[key] = row[key]
    }
  })
  
  // 解析因子代码并获取因子详情
  try {
    const factorCodes = JSON.parse(row.factorCodes || '[]')
    const factorPromises = factorCodes.map(code => 
      axios.get(`/api/factor/code/${code}`)
    )
    const responses = await Promise.all(factorPromises)
    selectedFactors.value = responses
      .filter(res => res.data.code === 200)
      .map(res => res.data.data)
  } catch (error) {
    selectedFactors.value = []
  }
  
  showAddDialog.value = true
}

// 删除因子树
const deleteTree = async (row) => {
  try {
    await ElMessageBox.confirm('确定要删除这个因子树吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    
    const response = await axios.delete(`/api/factor-tree/${row.id}`)
    if (response.data.code === 200) {
      ElMessage.success('删除成功')
      getTreeList()
    } else {
      ElMessage.error(response.data.message || '删除失败')
    }
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('删除失败')
    }
  }
}

// 保存因子树
const saveTree = async () => {
  try {
    await treeFormRef.value.validate()
    
    // 更新因子代码
    treeForm.factorCodes = JSON.stringify(selectedFactors.value.map(f => f.factorCode))
    
    const url = isEdit.value ? '/api/factor-tree/update' : '/api/factor-tree/create'
    const method = isEdit.value ? 'put' : 'post'
    
    const response = await axios[method](url, treeForm)
    if (response.data.code === 200) {
      ElMessage.success(isEdit.value ? '更新成功' : '创建成功')
      showAddDialog.value = false
      getTreeList()
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
  Object.keys(treeForm).forEach(key => {
    if (key === 'status') {
      treeForm[key] = 1
    } else {
      treeForm[key] = ''
    }
  })
  selectedFactors.value = []
  isEdit.value = false
}

// 过滤因子
const filterFactors = () => {
  // 通过计算属性自动过滤
}

// 处理因子选择变化
const handleFactorSelectionChange = (selection) => {
  tempSelectedFactors.value = selection
}

// 确认因子选择
const confirmFactorSelection = () => {
  selectedFactors.value = [...tempSelectedFactors.value]
  showFactorSelector.value = false
}

// 移除因子
const removeFactor = (factor) => {
  const index = selectedFactors.value.findIndex(f => f.factorCode === factor.factorCode)
  if (index > -1) {
    selectedFactors.value.splice(index, 1)
  }
}

// 分页处理
const handleSizeChange = (val) => {
  pageSize.value = val
  getTreeList()
}

const handleCurrentChange = (val) => {
  currentPage.value = val
  getTreeList()
}

onMounted(() => {
  getTreeList()
  getFactorList()
})
</script>

<style scoped>
.factor-tree {
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

.tree-table {
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

.factor-selector {
  border: 1px solid #dcdfe6;
  border-radius: 4px;
  padding: 10px;
  min-height: 60px;
}

.selected-factors {
  margin-top: 10px;
}

.factor-selector-content {
  .filter-area {
    margin-bottom: 15px;
  }
}

.tree-detail {
  .tree-info {
    margin-bottom: 20px;
    padding: 15px;
    background: #f5f7fa;
    border-radius: 8px;
    
    h3 {
      margin: 0 0 10px 0;
      color: #303133;
    }
    
    p {
      margin: 5px 0;
      color: #606266;
    }
  }
  
  .tree-factors {
    h4 {
      margin: 0 0 15px 0;
      color: #303133;
    }
  }
}
</style> 