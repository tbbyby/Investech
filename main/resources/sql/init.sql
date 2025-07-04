-- 创建数据库
CREATE DATABASE IF NOT EXISTS investech DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE investech;

-- 用户表
CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号',
  `real_name` varchar(50) DEFAULT NULL COMMENT '真实姓名',
  `status` int DEFAULT '1' COMMENT '状态 0-禁用 1-正常',
  `create_time` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 基金表
CREATE TABLE IF NOT EXISTS `fund` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `fund_code` varchar(20) NOT NULL COMMENT '基金代码',
  `fund_name` varchar(100) NOT NULL COMMENT '基金名称',
  `fund_type` varchar(50) DEFAULT NULL COMMENT '基金类型',
  `fund_company` varchar(100) DEFAULT NULL COMMENT '基金公司',
  `fund_manager` varchar(50) DEFAULT NULL COMMENT '基金经理',
  `net_value` decimal(10,4) DEFAULT NULL COMMENT '最新净值',
  `net_value_date` date DEFAULT NULL COMMENT '净值日期',
  `total_assets` decimal(20,2) DEFAULT NULL COMMENT '总资产规模',
  `daily_return` decimal(10,4) DEFAULT NULL COMMENT '日收益率',
  `weekly_return` decimal(10,4) DEFAULT NULL COMMENT '周收益率',
  `monthly_return` decimal(10,4) DEFAULT NULL COMMENT '月收益率',
  `yearly_return` decimal(10,4) DEFAULT NULL COMMENT '年收益率',
  `max_drawdown` decimal(10,4) DEFAULT NULL COMMENT '最大回撤',
  `sharpe_ratio` decimal(10,4) DEFAULT NULL COMMENT '夏普比率',
  `risk_level` varchar(20) DEFAULT NULL COMMENT '风险等级',
  `tags` text COMMENT '基金标签',
  `status` int DEFAULT '1' COMMENT '状态 0-停售 1-正常',
  `create_time` date DEFAULT CURRENT_DATE COMMENT '创建时间',
  `update_time` date DEFAULT CURRENT_DATE ON UPDATE CURRENT_DATE COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_fund_code` (`fund_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='基金表';

-- 基金组合表
CREATE TABLE IF NOT EXISTS `fund_portfolio` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `portfolio_name` varchar(100) NOT NULL COMMENT '组合名称',
  `portfolio_desc` text COMMENT '组合描述',
  `user_id` bigint NOT NULL COMMENT '创建用户ID',
  `fund_codes` text COMMENT '基金代码列表',
  `weights` text COMMENT '权重列表',
  `status` int DEFAULT '1' COMMENT '状态 0-禁用 1-正常',
  `create_time` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='基金组合表';

-- 因子表
CREATE TABLE IF NOT EXISTS `factor` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `factor_code` varchar(50) NOT NULL COMMENT '因子代码',
  `factor_name` varchar(100) NOT NULL COMMENT '因子名称',
  `factor_desc` text COMMENT '因子描述',
  `factor_type` varchar(50) DEFAULT NULL COMMENT '因子类型：基础因子、衍生因子、风格因子',
  `data_source` varchar(100) DEFAULT NULL COMMENT '数据来源',
  `calculation_method` text COMMENT '计算方法',
  `unit` varchar(20) DEFAULT NULL COMMENT '单位',
  `category` varchar(50) DEFAULT NULL COMMENT '因子分类',
  `parent_code` varchar(50) DEFAULT NULL COMMENT '父因子代码',
  `level` int DEFAULT '1' COMMENT '因子层级',
  `sort_order` int DEFAULT '0' COMMENT '排序',
  `status` int DEFAULT '1' COMMENT '状态 0-禁用 1-正常',
  `create_time` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_factor_code` (`factor_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='因子表';

-- 因子树表
CREATE TABLE IF NOT EXISTS `factor_tree` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `tree_name` varchar(100) NOT NULL COMMENT '树名称',
  `tree_desc` text COMMENT '树描述',
  `business_scene` varchar(50) DEFAULT NULL COMMENT '业务场景：基金研究、量化投研、特色数据等',
  `factor_codes` text COMMENT '因子代码列表，JSON格式',
  `status` int DEFAULT '1' COMMENT '状态 0-禁用 1-正常',
  `create_time` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='因子树表';

-- 衍生因子表
CREATE TABLE IF NOT EXISTS `derived_factor` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `factor_code` varchar(50) NOT NULL COMMENT '衍生因子代码',
  `factor_name` varchar(100) NOT NULL COMMENT '衍生因子名称',
  `factor_desc` text COMMENT '衍生因子描述',
  `base_factors` text COMMENT '基础因子列表，JSON格式',
  `weights` text COMMENT '权重配置，JSON格式',
  `formula` text COMMENT '计算公式',
  `category` varchar(50) DEFAULT NULL COMMENT '因子分类',
  `creator_id` bigint NOT NULL COMMENT '创建者ID',
  `status` int DEFAULT '1' COMMENT '状态 0-禁用 1-正常',
  `create_time` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_factor_code` (`factor_code`),
  KEY `idx_creator_id` (`creator_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='衍生因子表';

-- 策略表
CREATE TABLE IF NOT EXISTS `strategy` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `strategy_code` varchar(50) NOT NULL COMMENT '策略代码',
  `strategy_name` varchar(100) NOT NULL COMMENT '策略名称',
  `strategy_desc` text COMMENT '策略描述',
  `strategy_type` varchar(50) DEFAULT NULL COMMENT '策略类型：股票策略、债券策略、混合策略等',
  `investment_style` varchar(50) DEFAULT NULL COMMENT '投资风格：价值型、成长型、平衡型等',
  `risk_level` varchar(20) DEFAULT NULL COMMENT '风险等级：低风险、中风险、高风险',
  `target_return` varchar(20) DEFAULT NULL COMMENT '目标收益率',
  `max_drawdown` varchar(20) DEFAULT NULL COMMENT '最大回撤限制',
  `benchmark` varchar(50) DEFAULT NULL COMMENT '基准指数',
  `factor_codes` text COMMENT '使用的因子代码，JSON格式',
  `parameters` text COMMENT '策略参数，JSON格式',
  `creator_id` bigint NOT NULL COMMENT '创建者ID',
  `status` int DEFAULT '1' COMMENT '状态 0-禁用 1-正常',
  `create_time` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_strategy_code` (`strategy_code`),
  KEY `idx_creator_id` (`creator_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='策略表';

-- 回测表
CREATE TABLE IF NOT EXISTS `backtest` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `backtest_code` varchar(50) NOT NULL COMMENT '回测代码',
  `backtest_name` varchar(100) NOT NULL COMMENT '回测名称',
  `strategy_code` varchar(50) NOT NULL COMMENT '策略代码',
  `start_date` timestamp NULL DEFAULT NULL COMMENT '回测开始日期',
  `end_date` timestamp NULL DEFAULT NULL COMMENT '回测结束日期',
  `initial_capital` decimal(20,2) DEFAULT NULL COMMENT '初始资金',
  `final_capital` decimal(20,2) DEFAULT NULL COMMENT '最终资金',
  `total_return` decimal(10,4) DEFAULT NULL COMMENT '总收益率',
  `annual_return` decimal(10,4) DEFAULT NULL COMMENT '年化收益率',
  `max_drawdown` decimal(10,4) DEFAULT NULL COMMENT '最大回撤',
  `sharpe_ratio` decimal(10,4) DEFAULT NULL COMMENT '夏普比率',
  `volatility` decimal(10,4) DEFAULT NULL COMMENT '波动率',
  `win_rate` decimal(10,4) DEFAULT NULL COMMENT '胜率',
  `profit_loss_ratio` decimal(10,4) DEFAULT NULL COMMENT '盈亏比',
  `benchmark_return` varchar(20) DEFAULT NULL COMMENT '基准收益率',
  `benchmark_max_drawdown` varchar(20) DEFAULT NULL COMMENT '基准最大回撤',
  `benchmark_sharpe_ratio` varchar(20) DEFAULT NULL COMMENT '基准夏普比率',
  `status` varchar(20) DEFAULT NULL COMMENT '回测状态：运行中、已完成、失败',
  `result_data` text COMMENT '回测结果数据，JSON格式',
  `creator_id` bigint NOT NULL COMMENT '创建者ID',
  `create_time` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_backtest_code` (`backtest_code`),
  KEY `idx_strategy_code` (`strategy_code`),
  KEY `idx_creator_id` (`creator_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='回测表';

-- 风险管理表
CREATE TABLE IF NOT EXISTS `risk_management` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `risk_code` varchar(50) NOT NULL COMMENT '风险代码',
  `risk_name` varchar(100) NOT NULL COMMENT '风险名称',
  `risk_type` varchar(50) DEFAULT NULL COMMENT '风险类型：市场风险、信用风险、流动性风险等',
  `risk_level` varchar(20) DEFAULT NULL COMMENT '风险等级：低、中、高',
  `risk_value` decimal(10,4) DEFAULT NULL COMMENT '风险值',
  `risk_desc` text COMMENT '风险描述',
  `measurement_method` text COMMENT '测量方法',
  `risk_limit` varchar(50) DEFAULT NULL COMMENT '风险限额',
  `alert_threshold` varchar(50) DEFAULT NULL COMMENT '预警阈值',
  `mitigation_measures` text COMMENT '缓解措施',
  `related_strategy` varchar(50) DEFAULT NULL COMMENT '相关策略',
  `related_portfolio` varchar(50) DEFAULT NULL COMMENT '相关组合',
  `creator_id` bigint NOT NULL COMMENT '创建者ID',
  `status` int DEFAULT '1' COMMENT '状态 0-禁用 1-正常',
  `create_time` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_risk_code` (`risk_code`),
  KEY `idx_creator_id` (`creator_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='风险管理表';

-- 绩效评估表
CREATE TABLE IF NOT EXISTS `performance_evaluation` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `evaluation_code` varchar(50) NOT NULL COMMENT '评估代码',
  `evaluation_name` varchar(100) NOT NULL COMMENT '评估名称',
  `strategy_code` varchar(50) DEFAULT NULL COMMENT '策略代码',
  `portfolio_code` varchar(50) DEFAULT NULL COMMENT '组合代码',
  `evaluation_date` timestamp NULL DEFAULT NULL COMMENT '评估日期',
  `total_return` decimal(10,4) DEFAULT NULL COMMENT '总收益率',
  `annual_return` decimal(10,4) DEFAULT NULL COMMENT '年化收益率',
  `benchmark_return` decimal(10,4) DEFAULT NULL COMMENT '基准收益率',
  `excess_return` decimal(10,4) DEFAULT NULL COMMENT '超额收益',
  `sharpe_ratio` decimal(10,4) DEFAULT NULL COMMENT '夏普比率',
  `sortino_ratio` decimal(10,4) DEFAULT NULL COMMENT '索提诺比率',
  `calmar_ratio` decimal(10,4) DEFAULT NULL COMMENT '卡玛比率',
  `information_ratio` decimal(10,4) DEFAULT NULL COMMENT '信息比率',
  `max_drawdown` decimal(10,4) DEFAULT NULL COMMENT '最大回撤',
  `volatility` decimal(10,4) DEFAULT NULL COMMENT '波动率',
  `beta` decimal(10,4) DEFAULT NULL COMMENT '贝塔系数',
  `alpha` decimal(10,4) DEFAULT NULL COMMENT '阿尔法系数',
  `treynor_ratio` decimal(10,4) DEFAULT NULL COMMENT '特雷诺比率',
  `jensen_alpha` decimal(10,4) DEFAULT NULL COMMENT '詹森阿尔法',
  `risk_adjusted_return` varchar(50) DEFAULT NULL COMMENT '风险调整后收益',
  `performance_ranking` varchar(50) DEFAULT NULL COMMENT '业绩排名',
  `evaluation_result` varchar(20) DEFAULT NULL COMMENT '评估结果：优秀、良好、一般、较差',
  `evaluation_report` text COMMENT '评估报告，JSON格式',
  `evaluator_id` bigint NOT NULL COMMENT '评估者ID',
  `create_time` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_evaluation_code` (`evaluation_code`),
  KEY `idx_strategy_code` (`strategy_code`),
  KEY `idx_portfolio_code` (`portfolio_code`),
  KEY `idx_evaluator_id` (`evaluator_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='绩效评估表';

-- 插入测试数据
INSERT INTO `user` (`username`, `password`, `email`, `phone`, `real_name`, `status`) VALUES
('admin', '123456', 'admin@investech.com', '13800138000', '管理员', 1),
('user1', '123456', 'user1@investech.com', '13800138001', '测试用户1', 1),
('user2', '123456', 'user2@investech.com', '13800138002', '测试用户2', 1);

INSERT INTO `fund` (`fund_code`, `fund_name`, `fund_type`, `fund_company`, `fund_manager`, `net_value`, `net_value_date`, `total_assets`, `daily_return`, `weekly_return`, `monthly_return`, `yearly_return`, `max_drawdown`, `sharpe_ratio`, `risk_level`, `tags`, `status`) VALUES
('000001', '华夏成长混合', '混合型', '华夏基金', '张三', 1.2345, '2024-01-15', 1000000000.00, 0.0150, 0.0250, 0.0800, 0.1500, -0.1200, 1.2500, '中风险', '["成长型","混合型","明星基金"]', 1),
('000002', '易方达消费行业股票', '股票型', '易方达基金', '李四', 2.3456, '2024-01-15', 2000000000.00, 0.0200, 0.0300, 0.1000, 0.2000, -0.1500, 1.5000, '高风险', '["消费行业","股票型","热门基金"]', 1),
('000003', '嘉实稳健债券', '债券型', '嘉实基金', '王五', 1.1234, '2024-01-15', 500000000.00, 0.0050, 0.0100, 0.0300, 0.0800, -0.0500, 0.8000, '低风险', '["债券型","稳健型","低风险"]', 1),
('000004', '南方中证500ETF', '指数型', '南方基金', '赵六', 1.5678, '2024-01-15', 1500000000.00, 0.0180, 0.0280, 0.0900, 0.1800, -0.1300, 1.3500, '中风险', '["指数型","ETF","中证500"]', 1),
('000005', '工银瑞信货币', '货币型', '工银瑞信基金', '钱七', 1.0000, '2024-01-15', 3000000000.00, 0.0010, 0.0050, 0.0200, 0.0500, 0.0000, 0.2000, '低风险', '["货币型","流动性","低风险"]', 1);

INSERT INTO `fund_portfolio` (`portfolio_name`, `portfolio_desc`, `user_id`, `fund_codes`, `weights`, `status`) VALUES
('稳健配置组合', '适合稳健型投资者的基金组合', 1, '["000001","000003","000005"]', '{"000001":0.4,"000003":0.4,"000005":0.2}', 1),
('成长配置组合', '适合成长型投资者的基金组合', 1, '["000001","000002","000004"]', '{"000001":0.3,"000002":0.4,"000004":0.3}', 1),
('平衡配置组合', '适合平衡型投资者的基金组合', 2, '["000001","000002","000003","000004"]', '{"000001":0.25,"000002":0.25,"000003":0.25,"000004":0.25}', 1);

-- 插入基础因子数据
INSERT INTO `factor` (`factor_code`, `factor_name`, `factor_desc`, `factor_type`, `data_source`, `calculation_method`, `unit`, `category`, `parent_code`, `level`, `sort_order`, `status`) VALUES
('F001', '收益率因子', '基金历史收益率表现', '基础因子', '基金数据库', '历史收益率计算', '%', '收益类', NULL, 1, 1, 1),
('F002', '波动率因子', '基金收益波动性指标', '基础因子', '基金数据库', '标准差计算', '%', '风险类', NULL, 1, 2, 1),
('F003', '夏普比率因子', '风险调整后收益指标', '基础因子', '基金数据库', '夏普比率计算', '无', '风险调整类', NULL, 1, 3, 1),
('F004', '最大回撤因子', '基金最大回撤指标', '基础因子', '基金数据库', '最大回撤计算', '%', '风险类', NULL, 1, 4, 1),
('F005', '规模因子', '基金资产规模指标', '基础因子', '基金数据库', '总资产计算', '亿元', '规模类', NULL, 1, 5, 1),
('F006', '费率因子', '基金费率水平指标', '基础因子', '基金数据库', '费率统计', '%', '成本类', NULL, 1, 6, 1),
('F007', '成立时间因子', '基金成立时间长短', '基础因子', '基金数据库', '成立时间计算', '年', '时间类', NULL, 1, 7, 1),
('F008', '换手率因子', '基金换手率指标', '基础因子', '基金数据库', '换手率计算', '%', '交易类', NULL, 1, 8, 1);

-- 插入因子树数据
INSERT INTO `factor_tree` (`tree_name`, `tree_desc`, `business_scene`, `factor_codes`, `status`) VALUES
('基金研究因子树', '用于基金研究和分析的因子分类', '基金研究', '["F001","F002","F003","F004","F005","F006","F007","F008"]', 1),
('量化投研因子树', '用于量化投资研究的因子分类', '量化投研', '["F001","F002","F003","F004","F005"]', 1),
('特色数据因子树', '特色数据输出因子分类', '特色数据', '["F006","F007","F008"]', 1);

-- 插入衍生因子数据
INSERT INTO `derived_factor` (`factor_code`, `factor_name`, `factor_desc`, `base_factors`, `weights`, `formula`, `category`, `creator_id`, `status`) VALUES
('DF001', '综合收益因子', '综合收益率和风险调整收益的复合因子', '["F001","F003"]', '{"F001":0.6,"F003":0.4}', '0.6*收益率 + 0.4*夏普比率', '综合类', 1, 1),
('DF002', '风险控制因子', '综合波动率和最大回撤的风险控制因子', '["F002","F004"]', '{"F002":0.5,"F004":0.5}', '-(0.5*波动率 + 0.5*最大回撤)', '风险控制类', 1, 1),
('DF003', '性价比因子', '综合考虑收益、风险和成本的性价比因子', '["F001","F003","F006"]', '{"F001":0.4,"F003":0.4,"F006":0.2}', '0.4*收益率 + 0.4*夏普比率 - 0.2*费率', '性价比类', 2, 1);

-- 插入策略数据
INSERT INTO `strategy` (`strategy_code`, `strategy_name`, `strategy_desc`, `strategy_type`, `investment_style`, `risk_level`, `target_return`, `max_drawdown`, `benchmark`, `factor_codes`, `parameters`, `creator_id`, `status`) VALUES
('S001', '价值投资策略', '基于价值因子的投资策略', '股票策略', '价值型', '中风险', '15%', '10%', '沪深300', '["F001","F003","F005"]', '{"rebalance_frequency":"monthly","max_position":0.1}', 1, 1),
('S002', '成长投资策略', '基于成长因子的投资策略', '股票策略', '成长型', '高风险', '20%', '15%', '创业板指', '["F001","F002","F008"]', '{"rebalance_frequency":"weekly","max_position":0.15}', 1, 1),
('S003', '平衡配置策略', '平衡收益和风险的配置策略', '混合策略', '平衡型', '中风险', '12%', '8%', '中证500', '["F001","F003","F004","F006"]', '{"rebalance_frequency":"monthly","max_position":0.08}', 2, 1),
('S004', '低风险债券策略', '低风险债券投资策略', '债券策略', '稳健型', '低风险', '6%', '3%', '中债总财富', '["F001","F002","F006"]', '{"rebalance_frequency":"quarterly","max_position":0.05}', 1, 1);

-- 插入回测数据
INSERT INTO `backtest` (`backtest_code`, `backtest_name`, `strategy_code`, `start_date`, `end_date`, `initial_capital`, `final_capital`, `total_return`, `annual_return`, `max_drawdown`, `sharpe_ratio`, `volatility`, `win_rate`, `profit_loss_ratio`, `benchmark_return`, `benchmark_max_drawdown`, `benchmark_sharpe_ratio`, `status`, `result_data`, `creator_id`) VALUES
('BT001', '价值策略回测2023', 'S001', '2023-01-01 00:00:00', '2023-12-31 23:59:59', 1000000.00, 1150000.00, 0.1500, 0.1500, -0.0800, 1.2000, 0.1200, 0.6500, 1.8000, '0.1200', '-0.1000', '1.1000', '已完成', '{"daily_returns":[],"positions":[],"trades":[]}', 1),
('BT002', '成长策略回测2023', 'S002', '2023-01-01 00:00:00', '2023-12-31 23:59:59', 1000000.00, 1180000.00, 0.1800, 0.1800, -0.1200, 1.3500, 0.1500, 0.6000, 1.5000, '0.1200', '-0.1000', '1.1000', '已完成', '{"daily_returns":[],"positions":[],"trades":[]}', 1),
('BT003', '平衡策略回测2023', 'S003', '2023-01-01 00:00:00', '2023-12-31 23:59:59', 1000000.00, 1120000.00, 0.1200, 0.1200, -0.0600, 1.1000, 0.1000, 0.7000, 2.0000, '0.1200', '-0.1000', '1.1000', '已完成', '{"daily_returns":[],"positions":[],"trades":[]}', 2);

-- 插入风险管理数据
INSERT INTO `risk_management` (`risk_code`, `risk_name`, `risk_type`, `risk_level`, `risk_value`, `risk_desc`, `measurement_method`, `risk_limit`, `alert_threshold`, `mitigation_measures`, `related_strategy`, `related_portfolio`, `creator_id`, `status`) VALUES
('R001', '市场风险监控', '市场风险', '中', 0.0800, '监控市场波动对投资组合的影响', 'VaR模型', '10%', '8%', '动态调整仓位，增加对冲工具', 'S001,S002', 'P001', 1, 1),
('R002', '流动性风险控制', '流动性风险', '低', 0.0300, '控制投资组合的流动性风险', '流动性比率', '5%', '3%', '保持充足现金，控制持仓集中度', 'S003', 'P002', 1, 1),
('R003', '信用风险评估', '信用风险', '低', 0.0200, '评估债券投资的信用风险', '信用评级', '3%', '2%', '选择高信用等级债券，分散投资', 'S004', 'P003', 2, 1);

-- 插入绩效评估数据
INSERT INTO `performance_evaluation` (`evaluation_code`, `evaluation_name`, `strategy_code`, `portfolio_code`, `evaluation_date`, `total_return`, `annual_return`, `benchmark_return`, `excess_return`, `sharpe_ratio`, `sortino_ratio`, `calmar_ratio`, `information_ratio`, `max_drawdown`, `volatility`, `beta`, `alpha`, `treynor_ratio`, `jensen_alpha`, `risk_adjusted_return`, `performance_ranking`, `evaluation_result`, `evaluation_report`, `evaluator_id`) VALUES
('PE001', '价值策略2023年评估', 'S001', NULL, '2023-12-31 23:59:59', 0.1500, 0.1500, 0.1200, 0.0300, 1.2000, 1.3500, 1.8750, 0.2500, -0.0800, 0.1200, 0.9500, 0.0250, 0.1580, 0.0200, '优秀', '前20%', '优秀', '{"summary":"策略表现优秀","details":{}}', 1),
('PE002', '成长策略2023年评估', 'S002', NULL, '2023-12-31 23:59:59', 0.1800, 0.1800, 0.1200, 0.0600, 1.3500, 1.5000, 1.5000, 0.4000, -0.1200, 0.1500, 1.2000, 0.0450, 0.1500, 0.0350, '良好', '前30%', '良好', '{"summary":"策略表现良好","details":{}}', 1),
('PE003', '平衡策略2023年评估', 'S003', NULL, '2023-12-31 23:59:59', 0.1200, 0.1200, 0.1200, 0.0000, 1.1000, 1.2000, 2.0000, 0.0000, -0.0600, 0.1000, 0.8500, 0.0100, 0.1410, 0.0080, '一般', '前50%', '一般', '{"summary":"策略表现一般","details":{}}', 2); 