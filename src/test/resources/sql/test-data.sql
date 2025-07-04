-- 测试数据初始化脚本

-- 插入测试用户
INSERT INTO users (username, password, email, phone, real_name, status, create_time, update_time) VALUES
('testuser1', 'password123', 'test1@example.com', '13800138001', '测试用户1', 1, NOW(), NOW()),
('testuser2', 'password456', 'test2@example.com', '13800138002', '测试用户2', 1, NOW(), NOW());

-- 插入测试基金
INSERT INTO funds (fund_code, fund_name, fund_type, fund_company, fund_manager, net_value, net_value_date, total_assets, daily_return, weekly_return, monthly_return, yearly_return, max_drawdown, sharpe_ratio, risk_level, tags, status, create_time, update_time) VALUES
('000001', '华夏成长混合', '混合型', '华夏基金', '张三', 1.2345, CURDATE(), 1000000000, 0.015, 0.025, 0.085, 0.156, -0.12, 1.25, '中风险', '成长,科技,消费', 1, CURDATE(), CURDATE()),
('000002', '易方达消费行业股票', '股票型', '易方达基金', '李四', 2.5678, CURDATE(), 2000000000, 0.008, 0.018, 0.065, 0.128, -0.15, 1.18, '高风险', '消费,白酒,医药', 1, CURDATE(), CURDATE());

-- 插入测试因子
INSERT INTO factors (factor_code, factor_name, factor_desc, factor_type, data_source, calculation_method, unit, category, parent_code, level, sort_order, status, create_time, update_time) VALUES
('F001', '市盈率', '股票价格与每股收益的比率', '基础因子', '财务数据', '价格/每股收益', '倍', '估值因子', NULL, 1, 1, 1, NOW(), NOW()),
('F002', '市净率', '股票价格与每股净资产的比率', '基础因子', '财务数据', '价格/每股净资产', '倍', '估值因子', NULL, 1, 2, 1, NOW(), NOW()),
('F003', 'PEG比率', '市盈率与盈利增长率的比率', '衍生因子', '财务数据', '市盈率/盈利增长率', '倍', '估值因子', 'F001', 2, 3, 1, NOW(), NOW());

-- 插入测试基金组合
INSERT INTO fund_portfolios (portfolio_name, portfolio_desc, user_id, fund_codes, weights, status, create_time, update_time) VALUES
('成长型组合', '专注于成长型基金的组合', 1, '000001,000002', '[0.6,0.4]', 1, NOW(), NOW()),
('稳健型组合', '风险较低的稳健型组合', 1, '000001', '[1.0]', 1, NOW(), NOW()); 