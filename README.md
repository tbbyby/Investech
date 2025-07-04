# 智能投顾系统

这是一个基于Spring Boot + Vue3的智能投顾系统，提供基金研究、组合管理等功能。

## 技术栈

### 后端
- Spring Boot 3.4.7
- MySQL 8.0
- MyBatis 3.0.4
- Maven
- JUnit 5 (单元测试)
- JaCoCo (测试覆盖率)

### 前端
- Vue 3.5.17
- Element Plus 2.10.2
- Vue Router 4.5.1
- Axios 1.10.0
- Sass 1.89.2
- Vite 7.0.0

## 功能模块

### 1. 用户管理
- 用户注册
- 用户登录
- 用户信息管理

### 2. 基金研究子系统
- 全部公募基金查询
- 基金公司筛选
- 基金经理筛选
- 基金画像展示
- 基金组合创建

### 3. 基金组合管理
- 创建基金组合
- 查看组合列表
- 删除组合

### 4. 因子管理子系统
- 因子查询和管理
- 因子分类和层级
- 衍生因子计算

## 快速开始

### 1. 环境准备
- JDK 17+
- Node.js 16+
- MySQL 8.0+

### 2. 数据库配置
1. 创建MySQL数据库
2. 执行 `src/main/resources/sql/init.sql` 脚本
3. 修改 `src/main/resources/application.yml` 中的数据库连接信息

### 3. 后端启动
```bash
# 进入项目根目录
cd Investech

# 编译项目
mvn clean compile

# 启动应用
mvn spring-boot:run
```

后端服务将在 http://localhost:8080 启动

### 4. 前端启动
```bash
# 进入前端目录
cd investech-vue

# 安装依赖
npm install

# 启动开发服务器
npm run dev
```

前端服务将在 http://localhost:5173 启动

## 单元测试

### 运行测试
```bash
# 运行所有测试
mvn test

# 运行特定测试类
mvn test -Dtest=UserServiceTest

# 运行特定测试方法
mvn test -Dtest=UserServiceTest#testLoginSuccess
```

### 测试覆盖率
```bash
# 生成测试覆盖率报告
mvn clean test

# 查看覆盖率报告
# 打开 target/site/jacoco/index.html
```

### 在IDE中运行测试
1. **运行单个测试**：右键测试类或方法 → Run
2. **运行所有测试**：右键 `src/test/java` → Run 'All Tests'
3. **查看覆盖率**：右键测试类 → Run with Coverage

### 测试结构
```
src/test/java/com/investech/
├── service/                 # Service层单元测试
│   ├── UserServiceTest.java
│   ├── FundServiceTest.java
│   ├── FactorServiceTest.java
│   └── FundPortfolioServiceTest.java
├── common/                  # 工具类测试
│   └── ValidationUtilsTest.java
└── integration/             # 集成测试
    └── UserIntegrationTest.java
```

### 测试覆盖范围
- **用户管理**：注册、登录、查询、更新、删除
- **基金管理**：查询、搜索、增删改
- **因子管理**：因子CRUD、层级关系、分类查询
- **基金组合**：组合创建、管理、权重验证
- **数据验证**：邮箱、手机号、基金代码等格式验证
- **边界条件**：空值、null、极值等异常情况

## 默认账户

系统预置了以下测试账户：
- 用户名：admin，密码：123456
- 用户名：user1，密码：123456
- 用户名：user2，密码：123456

## 项目结构

```
Investech/
├── src/main/java/com/investech/
│   ├── controller/          # 控制器层
│   ├── service/             # 服务层
│   ├── mapper/              # 数据访问层
│   ├── entity/              # 实体类
│   └── common/              # 公共类
├── src/main/resources/
│   ├── mapper/              # MyBatis映射文件
│   ├── sql/                 # 数据库脚本
│   └── application.yml      # 配置文件
├── src/test/java/com/investech/
│   ├── service/             # Service层测试
│   ├── common/              # 工具类测试
│   └── integration/         # 集成测试
├── src/test/resources/
│   ├── sql/                 # 测试数据脚本
│   └── application-test.properties  # 测试配置
└── investech-vue/           # 前端项目
    ├── src/
    │   ├── views/           # 页面组件
    │   ├── router/          # 路由配置
    │   └── main.js          # 入口文件
    └── package.json         # 依赖配置
```

## API接口

### 用户相关
- POST `/api/user/login` - 用户登录
- POST `/api/user/register` - 用户注册
- GET `/api/user/{id}` - 获取用户信息

### 基金相关
- GET `/api/fund/list` - 获取基金列表
- GET `/api/fund/{id}` - 获取基金详情
- GET `/api/fund/search` - 搜索基金
- GET `/api/fund/company/{company}` - 按公司筛选基金
- GET `/api/fund/manager/{manager}` - 按经理筛选基金

### 组合相关
- GET `/api/portfolio/list` - 获取组合列表
- GET `/api/portfolio/user/{userId}` - 获取用户组合
- POST `/api/portfolio/create` - 创建组合
- DELETE `/api/portfolio/{id}` - 删除组合

### 因子相关
- GET `/api/factor/list` - 获取因子列表
- GET `/api/factor/{id}` - 获取因子详情
- GET `/api/factor/type/{type}` - 按类型筛选因子
- GET `/api/factor/category/{category}` - 按分类筛选因子

## 开发说明

### 后端开发
1. 遵循MVC架构模式
2. 使用MyBatis进行数据访问
3. 统一返回格式使用Result类
4. 支持跨域访问
5. 编写单元测试确保代码质量

### 前端开发
1. 使用Vue 3 Composition API
2. 使用Element Plus组件库
3. 响应式设计，支持移动端
4. 使用Vue Router进行路由管理

### 测试开发
1. 使用JUnit 5编写测试用例
2. 使用Mockito进行Mock测试
3. 测试覆盖正常流程、异常流程、边界条件
4. 使用JaCoCo生成覆盖率报告

## 部署说明

### 后端部署
```bash
# 打包
mvn clean package

# 运行jar包
java -jar target/Investech-0.0.1-SNAPSHOT.jar
```

### 前端部署
```bash
# 构建生产版本
npm run build

# 部署dist目录到Web服务器
```

## 注意事项

1. 确保MySQL服务正常运行
2. 检查数据库连接配置
3. 前端开发时需要后端服务运行
4. 生产环境部署时需要配置HTTPS
5. 运行测试前确保测试数据库配置正确

## 后续开发计划

1. 策略管理子系统
2. 组合产品管理子系统
3. 交易管理子系统
4. 用户权限管理
5. 数据可视化图表
6. 实时数据更新
7. 移动端适配
8. 性能测试和压力测试

## 联系方式

如有问题，请联系开发团队。 