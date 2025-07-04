-- 智能投顾子系统数据库表结构

-- 客户表
CREATE TABLE IF NOT EXISTS client (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '客户ID',
    name VARCHAR(100) NOT NULL COMMENT '客户姓名',
    phone VARCHAR(20) COMMENT '联系电话',
    email VARCHAR(100) COMMENT '邮箱地址',
    id_card VARCHAR(18) COMMENT '身份证号',
    age INT COMMENT '年龄',
    gender VARCHAR(10) COMMENT '性别',
    occupation VARCHAR(100) COMMENT '职业',
    annual_income DECIMAL(15,2) COMMENT '年收入',
    total_assets DECIMAL(15,2) COMMENT '总资产',
    risk_tolerance VARCHAR(20) COMMENT '风险承受能力：保守、稳健、积极',
    investment_goal VARCHAR(50) COMMENT '投资目标：保值、增值、养老、教育',
    investment_horizon VARCHAR(20) COMMENT '投资期限：短期、中期、长期',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    status INT DEFAULT 1 COMMENT '状态：0-禁用 1-启用'
) COMMENT '客户信息表';

-- 投资建议表
CREATE TABLE IF NOT EXISTS investment_advice (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '建议ID',
    client_id BIGINT NOT NULL COMMENT '客户ID',
    advice_type VARCHAR(50) NOT NULL COMMENT '建议类型：资产配置、基金推荐、调仓建议',
    title VARCHAR(200) NOT NULL COMMENT '建议标题',
    content TEXT COMMENT '建议内容',
    risk_level VARCHAR(20) COMMENT '风险等级：低、中、高',
    expected_return DECIMAL(5,4) COMMENT '预期收益率',
    max_drawdown DECIMAL(5,4) COMMENT '最大回撤',
    fund_recommendations TEXT COMMENT '推荐基金列表，JSON格式',
    asset_allocation TEXT COMMENT '资产配置建议，JSON格式',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    status INT DEFAULT 0 COMMENT '状态：0-草稿 1-已发布 2-已采纳',
    FOREIGN KEY (client_id) REFERENCES client(id)
) COMMENT '投资建议表';

-- 资产配置表
CREATE TABLE IF NOT EXISTS asset_allocation (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '配置ID',
    client_id BIGINT NOT NULL COMMENT '客户ID',
    allocation_name VARCHAR(100) NOT NULL COMMENT '配置名称',
    asset_class VARCHAR(50) NOT NULL COMMENT '资产类别：股票、债券、货币、商品、另类',
    allocation_ratio DECIMAL(5,4) NOT NULL COMMENT '配置比例',
    current_value DECIMAL(15,2) COMMENT '当前市值',
    target_value DECIMAL(15,2) COMMENT '目标市值',
    fund_codes VARCHAR(500) COMMENT '相关基金代码，逗号分隔',
    description TEXT COMMENT '配置说明',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    status INT DEFAULT 1 COMMENT '状态：0-禁用 1-启用',
    FOREIGN KEY (client_id) REFERENCES client(id)
) COMMENT '资产配置表';

-- 投资组合优化表
CREATE TABLE IF NOT EXISTS portfolio_optimization (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '优化ID',
    client_id BIGINT NOT NULL COMMENT '客户ID',
    optimization_name VARCHAR(100) NOT NULL COMMENT '优化方案名称',
    optimization_method VARCHAR(50) NOT NULL COMMENT '优化方法：马科维茨、风险平价、最大夏普比率',
    fund_pool TEXT COMMENT '基金池，JSON格式',
    target_return DECIMAL(5,4) COMMENT '目标收益率',
    max_risk DECIMAL(5,4) COMMENT '最大风险',
    optimized_weights TEXT COMMENT '优化权重，JSON格式',
    expected_return DECIMAL(5,4) COMMENT '预期收益率',
    expected_risk DECIMAL(5,4) COMMENT '预期风险',
    sharpe_ratio DECIMAL(5,4) COMMENT '夏普比率',
    constraints TEXT COMMENT '约束条件，JSON格式',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    status INT DEFAULT 0 COMMENT '状态：0-计算中 1-已完成 2-已应用',
    FOREIGN KEY (client_id) REFERENCES client(id)
) COMMENT '投资组合优化表';

-- 插入测试数据

-- 客户测试数据
INSERT INTO client (name, phone, email, id_card, age, gender, occupation, annual_income, total_assets, risk_tolerance, investment_goal, investment_horizon) VALUES
('张三', '13800138001', 'zhangsan@example.com', '110101199001011234', 35, '男', '工程师', 200000.00, 500000.00, '稳健', '增值', '中期'),
('李四', '13800138002', 'lisi@example.com', '110101198505051234', 40, '女', '教师', 150000.00, 300000.00, '保守', '保值', '长期'),
('王五', '13800138003', 'wangwu@example.com', '110101199203031234', 30, '男', '销售经理', 300000.00, 800000.00, '积极', '增值', '短期'),
('赵六', '13800138004', 'zhaoliu@example.com', '110101197808081234', 45, '女', '医生', 400000.00, 1200000.00, '稳健', '养老', '长期'),
('孙七', '13800138005', 'sunqi@example.com', '110101199512121234', 28, '男', '程序员', 250000.00, 400000.00, '积极', '增值', '中期');

-- 投资建议测试数据
INSERT INTO investment_advice (client_id, advice_type, title, content, risk_level, expected_return, max_drawdown, fund_recommendations, asset_allocation, status) VALUES
(1, '资产配置', '稳健型资产配置建议', '根据您的风险承受能力和投资目标，建议采用稳健型资产配置策略，重点关注债券基金和混合基金。', '中', 0.0800, 0.1500, '["000001", "000002", "000003"]', '{"股票": 0.30, "债券": 0.50, "货币": 0.20}', 1),
(2, '基金推荐', '保守型基金组合推荐', '针对您的保守型投资偏好，推荐以下低风险基金组合，适合长期持有。', '低', 0.0500, 0.0800, '["000004", "000005", "000006"]', '{"债券": 0.70, "货币": 0.30}', 1),
(3, '调仓建议', '积极型调仓建议', '基于当前市场环境，建议适当增加股票基金配置比例，把握市场机会。', '高', 0.1200, 0.2500, '["000007", "000008", "000009"]', '{"股票": 0.60, "债券": 0.30, "货币": 0.10}', 1),
(4, '资产配置', '养老型资产配置', '针对您的养老投资目标，建议采用长期稳健的资产配置策略。', '中', 0.0700, 0.1200, '["000010", "000011", "000012"]', '{"股票": 0.40, "债券": 0.45, "货币": 0.15}', 1),
(5, '基金推荐', '成长型基金推荐', '结合您的年龄和收入情况，推荐成长型基金组合，适合中期投资。', '高', 0.1000, 0.2000, '["000013", "000014", "000015"]', '{"股票": 0.55, "债券": 0.35, "货币": 0.10}', 1);

-- 资产配置测试数据
INSERT INTO asset_allocation (client_id, allocation_name, asset_class, allocation_ratio, current_value, target_value, fund_codes, description) VALUES
(1, '稳健配置方案', '股票', 0.30, 150000.00, 150000.00, '000001,000002', '股票类资产配置'),
(1, '稳健配置方案', '债券', 0.50, 250000.00, 250000.00, '000003,000004', '债券类资产配置'),
(1, '稳健配置方案', '货币', 0.20, 100000.00, 100000.00, '000005', '货币类资产配置'),
(2, '保守配置方案', '债券', 0.70, 210000.00, 210000.00, '000006,000007', '债券类资产配置'),
(2, '保守配置方案', '货币', 0.30, 90000.00, 90000.00, '000008', '货币类资产配置'),
(3, '积极配置方案', '股票', 0.60, 480000.00, 480000.00, '000009,000010', '股票类资产配置'),
(3, '积极配置方案', '债券', 0.30, 240000.00, 240000.00, '000011,000012', '债券类资产配置'),
(3, '积极配置方案', '货币', 0.10, 80000.00, 80000.00, '000013', '货币类资产配置');

-- 投资组合优化测试数据
INSERT INTO portfolio_optimization (client_id, optimization_name, optimization_method, fund_pool, target_return, max_risk, optimized_weights, expected_return, expected_risk, sharpe_ratio, constraints, status) VALUES
(1, '马科维茨优化方案', '马科维茨', '["000001", "000002", "000003", "000004", "000005"]', 0.0800, 0.1500, '{"000001": 0.25, "000002": 0.20, "000003": 0.30, "000004": 0.15, "000005": 0.10}', 0.0820, 0.1450, 0.5655, '{"min_weight": 0.05, "max_weight": 0.40}', 1),
(2, '风险平价优化方案', '风险平价', '["000006", "000007", "000008", "000009"]', 0.0500, 0.0800, '{"000006": 0.35, "000007": 0.30, "000008": 0.20, "000009": 0.15}', 0.0520, 0.0750, 0.6933, '{"min_weight": 0.10, "max_weight": 0.50}', 1),
(3, '最大夏普比率优化', '最大夏普比率', '["000010", "000011", "000012", "000013", "000014"]', 0.1200, 0.2500, '{"000010": 0.30, "000011": 0.25, "000012": 0.20, "000013": 0.15, "000014": 0.10}', 0.1250, 0.2300, 0.5435, '{"min_weight": 0.05, "max_weight": 0.35}', 1),
(4, '养老型优化方案', '马科维茨', '["000015", "000016", "000017", "000018"]', 0.0700, 0.1200, '{"000015": 0.40, "000016": 0.30, "000017": 0.20, "000018": 0.10}', 0.0720, 0.1150, 0.6261, '{"min_weight": 0.08, "max_weight": 0.45}', 1),
(5, '成长型优化方案', '最大夏普比率', '["000019", "000020", "000021", "000022"]', 0.1000, 0.2000, '{"000019": 0.35, "000020": 0.25, "000021": 0.25, "000022": 0.15}', 0.1050, 0.1850, 0.5676, '{"min_weight": 0.10, "max_weight": 0.40}', 1); 