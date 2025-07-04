/*
 Navicat MySQL Data Transfer

 Source Server         : java
 Source Server Type    : MySQL
 Source Server Version : 80029
 Source Host           : localhost:3306
 Source Schema         : investech

 Target Server Type    : MySQL
 Target Server Version : 80029
 File Encoding         : 65001

 Date: 03/07/2025 16:40:39
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

CREATE DATABASE IF NOT EXISTS `investech`;
USE `investech`;

-- ----------------------------
-- Table structure for asset_allocation
-- ----------------------------
DROP TABLE IF EXISTS `asset_allocation`;
CREATE TABLE `asset_allocation`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '配置ID',
  `client_id` bigint NOT NULL COMMENT '客户ID',
  `allocation_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '配置名称',
  `asset_class` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '资产类别：股票、债券、货币、商品、另类',
  `allocation_ratio` decimal(5, 4) NOT NULL COMMENT '配置比例',
  `current_value` decimal(15, 2) NULL DEFAULT NULL COMMENT '当前市值',
  `target_value` decimal(15, 2) NULL DEFAULT NULL COMMENT '目标市值',
  `fund_codes` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '相关基金代码，逗号分隔',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '配置说明',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `status` int NULL DEFAULT 1 COMMENT '状态：0-禁用 1-启用',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `client_id`(`client_id`) USING BTREE,
  CONSTRAINT `asset_allocation_ibfk_1` FOREIGN KEY (`client_id`) REFERENCES `client` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '资产配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of asset_allocation
-- ----------------------------
INSERT INTO `asset_allocation` VALUES (1, 1, '稳健配置方案', '股票', 0.3000, 150000.00, 150000.00, '000001,000002', '股票类资产配置', '2025-06-28 21:32:00', '2025-06-28 21:32:00', 1);
INSERT INTO `asset_allocation` VALUES (2, 1, '稳健配置方案', '债券', 0.5000, 250000.00, 250000.00, '000003,000004', '债券类资产配置', '2025-06-28 21:32:00', '2025-06-28 21:32:00', 1);
INSERT INTO `asset_allocation` VALUES (3, 1, '稳健配置方案', '货币', 0.2000, 100000.00, 100000.00, '000005', '货币类资产配置', '2025-06-28 21:32:00', '2025-06-28 21:32:00', 1);
INSERT INTO `asset_allocation` VALUES (4, 2, '保守配置方案', '债券', 0.7000, 210000.00, 210000.00, '000006,000007', '债券类资产配置', '2025-06-28 21:32:00', '2025-06-28 21:32:00', 1);
INSERT INTO `asset_allocation` VALUES (5, 2, '保守配置方案', '货币', 0.3000, 90000.00, 90000.00, '000008', '货币类资产配置', '2025-06-28 21:32:00', '2025-06-28 21:32:00', 1);
INSERT INTO `asset_allocation` VALUES (6, 3, '积极配置方案', '股票', 0.6000, 480000.00, 480000.00, '000009,000010', '股票类资产配置', '2025-06-28 21:32:00', '2025-06-28 21:32:00', 1);
INSERT INTO `asset_allocation` VALUES (7, 3, '积极配置方案', '债券', 0.3000, 240000.00, 240000.00, '000011,000012', '债券类资产配置', '2025-06-28 21:32:00', '2025-06-28 21:32:00', 1);
INSERT INTO `asset_allocation` VALUES (8, 3, '积极配置方案', '货币', 0.1000, 80000.00, 80000.00, '000013', '货币类资产配置', '2025-06-28 21:32:00', '2025-06-28 21:32:00', 1);

-- ----------------------------
-- Table structure for backtest
-- ----------------------------
DROP TABLE IF EXISTS `backtest`;
CREATE TABLE `backtest`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `backtest_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '回测代码',
  `backtest_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '回测名称',
  `strategy_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '策略代码',
  `start_date` timestamp(0) NULL DEFAULT NULL COMMENT '回测开始日期',
  `end_date` timestamp(0) NULL DEFAULT NULL COMMENT '回测结束日期',
  `initial_capital` decimal(20, 2) NULL DEFAULT NULL COMMENT '初始资金',
  `final_capital` decimal(20, 2) NULL DEFAULT NULL COMMENT '最终资金',
  `total_return` decimal(10, 4) NULL DEFAULT NULL COMMENT '总收益率',
  `annual_return` decimal(10, 4) NULL DEFAULT NULL COMMENT '年化收益率',
  `max_drawdown` decimal(10, 4) NULL DEFAULT NULL COMMENT '最大回撤',
  `sharpe_ratio` decimal(10, 4) NULL DEFAULT NULL COMMENT '夏普比率',
  `volatility` decimal(10, 4) NULL DEFAULT NULL COMMENT '波动率',
  `win_rate` decimal(10, 4) NULL DEFAULT NULL COMMENT '胜率',
  `profit_loss_ratio` decimal(10, 4) NULL DEFAULT NULL COMMENT '盈亏比',
  `benchmark_return` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '基准收益率',
  `benchmark_max_drawdown` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '基准最大回撤',
  `benchmark_sharpe_ratio` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '基准夏普比率',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '回测状态：运行中、已完成、失败',
  `result_data` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '回测结果数据，JSON格式',
  `creator_id` bigint NOT NULL COMMENT '创建者ID',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_backtest_code`(`backtest_code`) USING BTREE,
  INDEX `idx_strategy_code`(`strategy_code`) USING BTREE,
  INDEX `idx_creator_id`(`creator_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '回测表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of backtest
-- ----------------------------
INSERT INTO `backtest` VALUES (1, 'BT001', '价值策略回测2023', 'S001', '2023-01-01 00:00:00', '2023-12-31 23:59:59', 1000000.00, 1150000.00, 0.1500, 0.1500, -0.0800, 1.2000, 0.1200, 0.6500, 1.8000, '0.1200', '-0.1000', '1.1000', '已完成', '{\"daily_returns\":[],\"positions\":[],\"trades\":[]}', 1, '2025-06-28 20:48:31', '2025-06-28 20:48:31');
INSERT INTO `backtest` VALUES (2, 'BT002', '成长策略回测2023', 'S002', '2023-01-01 00:00:00', '2023-12-31 23:59:59', 1000000.00, 1180000.00, 0.1800, 0.1800, -0.1200, 1.3500, 0.1500, 0.6000, 1.5000, '0.1200', '-0.1000', '1.1000', '已完成', '{\"daily_returns\":[],\"positions\":[],\"trades\":[]}', 1, '2025-06-28 20:48:31', '2025-06-28 20:48:31');
INSERT INTO `backtest` VALUES (3, 'BT003', '平衡策略回测2023', 'S003', '2023-01-01 00:00:00', '2023-12-31 23:59:59', 1000000.00, 1120000.00, 0.1200, 0.1200, -0.0600, 1.1000, 0.1000, 0.7000, 2.0000, '0.1200', '-0.1000', '1.1000', '已完成', '{\"daily_returns\":[],\"positions\":[],\"trades\":[]}', 2, '2025-06-28 20:48:31', '2025-06-28 20:48:31');

-- ----------------------------
-- Table structure for client
-- ----------------------------
DROP TABLE IF EXISTS `client`;
CREATE TABLE `client`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '客户ID',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '客户姓名',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '联系电话',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '邮箱地址',
  `id_card` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '身份证号',
  `age` int NULL DEFAULT NULL COMMENT '年龄',
  `gender` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '性别',
  `occupation` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '职业',
  `annual_income` decimal(15, 2) NULL DEFAULT NULL COMMENT '年收入',
  `total_assets` decimal(15, 2) NULL DEFAULT NULL COMMENT '总资产',
  `risk_tolerance` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '风险承受能力：保守、稳健、积极',
  `investment_goal` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '投资目标：保值、增值、养老、教育',
  `investment_horizon` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '投资期限：短期、中期、长期',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `status` int NULL DEFAULT 1 COMMENT '状态：0-禁用 1-启用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '客户信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of client
-- ----------------------------
INSERT INTO `client` VALUES (1, '张三', '13800138001', 'zhangsan@example.com', '110101199001011234', 35, '男', '工程师', 200000.00, 500000.00, '稳健', '增值', '中期', '2025-06-28 21:31:59', '2025-06-28 21:31:59', 1);
INSERT INTO `client` VALUES (2, '李四', '13800138002', 'lisi@example.com', '110101198505051234', 40, '女', '教师', 150000.00, 300000.00, '保守', '保值', '长期', '2025-06-28 21:31:59', '2025-06-28 21:31:59', 1);
INSERT INTO `client` VALUES (3, '王五', '13800138003', 'wangwu@example.com', '110101199203031234', 30, '男', '销售经理', 300000.00, 800000.00, '积极', '增值', '短期', '2025-06-28 21:31:59', '2025-06-28 21:31:59', 1);
INSERT INTO `client` VALUES (4, '赵六', '13800138004', 'zhaoliu@example.com', '110101197808081234', 45, '女', '医生', 400000.00, 1200000.00, '稳健', '养老', '长期', '2025-06-28 21:31:59', '2025-06-28 21:31:59', 1);
INSERT INTO `client` VALUES (5, '孙七', '13800138005', 'sunqi@example.com', '110101199512121234', 28, '男', '程序员', 250000.00, 400000.00, '积极', '增值', '中期', '2025-06-28 21:31:59', '2025-06-28 21:31:59', 1);
INSERT INTO `client` VALUES (6, 'ppp', '13001844996', '3307203596@qq.com', '130221666666666666', 30, '男', '996', 11111.00, 999999.00, '保守', '保值', '中期', '2025-06-29 12:53:49', '2025-06-29 12:53:49', 1);

-- ----------------------------
-- Table structure for compliance_check
-- ----------------------------
DROP TABLE IF EXISTS `compliance_check`;
CREATE TABLE `compliance_check`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '检查ID',
  `check_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '检查名称',
  `check_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '检查类型：投资限制、持仓限制、交易限制、信息披露',
  `check_rule` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '检查规则',
  `check_result` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '待检查' COMMENT '检查结果：通过、不通过、待确认',
  `violation_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '违规类型',
  `violation_description` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '违规描述',
  `action_required` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '需要采取的行动',
  `responsible_person` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '责任人',
  `check_time` timestamp(0) NULL DEFAULT NULL COMMENT '检查时间',
  `resolve_time` timestamp(0) NULL DEFAULT NULL COMMENT '解决时间',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '待检查' COMMENT '状态：待检查、已检查、已解决',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `status_code` int NULL DEFAULT 1 COMMENT '状态：0-禁用 1-启用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '合规检查表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of compliance_check
-- ----------------------------
INSERT INTO `compliance_check` VALUES (1, '单一基金持仓限制', '持仓限制', '单一基金持仓不超过10%', '通过', NULL, NULL, NULL, '合规专员', '2025-06-28 22:24:05', NULL, '已检查', '2025-06-28 22:24:05', '2025-06-28 22:24:05', 1);
INSERT INTO `compliance_check` VALUES (2, '投资范围检查', '投资限制', '仅投资于股票型、债券型、混合型基金', '通过', NULL, NULL, NULL, '合规专员', '2025-06-28 22:24:05', NULL, '已检查', '2025-06-28 22:24:05', '2025-06-28 22:24:05', 1);
INSERT INTO `compliance_check` VALUES (3, '交易频率检查', '交易限制', '单日交易次数不超过5次', '通过', NULL, NULL, NULL, '合规专员', '2025-06-28 22:24:05', NULL, '已检查', '2025-06-28 22:24:05', '2025-06-28 22:24:05', 1);
INSERT INTO `compliance_check` VALUES (4, '信息披露检查', '信息披露', '定期披露投资组合持仓情况', '通过', NULL, NULL, NULL, '合规专员', '2025-06-28 22:24:05', NULL, '已检查', '2025-06-28 22:24:05', '2025-06-28 22:24:05', 1);
INSERT INTO `compliance_check` VALUES (5, '风险等级匹配', '投资限制', '投资产品风险等级与客户风险承受能力匹配', '通过', '风险等级不匹配', '某客户投资了超出其风险承受能力的产品', '调整投资组合，降低高风险产品比例', '投资经理', '2025-06-28 22:24:05', NULL, '已检查', '2025-06-28 22:24:05', '2025-06-28 22:35:50', 1);
INSERT INTO `compliance_check` VALUES (6, '资金流向检查', '交易限制', '大额资金流向需审批', '通过', NULL, NULL, NULL, '合规专员', '2025-06-28 22:24:05', NULL, '已检查', '2025-06-28 22:24:05', '2025-06-28 22:24:05', 1);
INSERT INTO `compliance_check` VALUES (7, '关联交易检查', '交易限制', '避免与关联方进行交易', '通过', NULL, NULL, NULL, '合规专员', '2025-06-28 22:24:05', NULL, '已检查', '2025-06-28 22:24:05', '2025-06-28 22:24:05', 1);
INSERT INTO `compliance_check` VALUES (8, '业绩披露检查', '信息披露', '定期披露投资业绩', '通过', NULL, NULL, NULL, '合规专员', '2025-06-28 22:24:05', NULL, '已检查', '2025-06-28 22:24:05', '2025-06-28 22:24:05', 1);

-- ----------------------------
-- Table structure for derived_factor
-- ----------------------------
DROP TABLE IF EXISTS `derived_factor`;
CREATE TABLE `derived_factor`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `factor_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '衍生因子代码',
  `factor_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '衍生因子名称',
  `factor_desc` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '衍生因子描述',
  `base_factors` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '基础因子列表，JSON格式',
  `weights` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '权重配置，JSON格式',
  `formula` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '计算公式',
  `category` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '因子分类',
  `creator_id` bigint NOT NULL COMMENT '创建者ID',
  `status` int NULL DEFAULT 1 COMMENT '状态 0-禁用 1-正常',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_factor_code`(`factor_code`) USING BTREE,
  INDEX `idx_creator_id`(`creator_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '衍生因子表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of derived_factor
-- ----------------------------
INSERT INTO `derived_factor` VALUES (1, 'DF001', '综合收益因子', '综合收益率和风险调整收益的复合因子', '[\"F001\",\"F003\"]', '{\"F001\":0.6,\"F003\":0.4}', '0.6*收益率 + 0.4*夏普比率', '综合类', 1, 1, '2025-06-28 15:41:27', '2025-06-28 15:41:27');
INSERT INTO `derived_factor` VALUES (2, 'DF002', '风险控制因子', '综合波动率和最大回撤的风险控制因子', '[\"F002\",\"F004\"]', '{\"F002\":0.5,\"F004\":0.5}', '-(0.5*波动率 + 0.5*最大回撤)', '风险控制类', 1, 1, '2025-06-28 15:41:27', '2025-06-28 15:41:27');
INSERT INTO `derived_factor` VALUES (3, 'DF003', '性价比因子', '综合考虑收益、风险和成本的性价比因子', '[\"F001\",\"F003\",\"F006\"]', '{\"F001\":0.4,\"F003\":0.4,\"F006\":0.2}', '0.4*收益率 + 0.4*夏普比率 - 0.2*费率', '性价比类', 2, 1, '2025-06-28 15:41:27', '2025-06-28 15:41:27');
INSERT INTO `derived_factor` VALUES (4, '9961', '9961', '996', '[\"F003\",\"F002\"]', '{\"F003\":0,\"F002\":0}', '9961', '风险控制类', 1, 1, '2025-06-29 12:52:47', '2025-07-01 15:12:13');

-- ----------------------------
-- Table structure for factor
-- ----------------------------
DROP TABLE IF EXISTS `factor`;
CREATE TABLE `factor`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `factor_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '因子代码',
  `factor_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '因子名称',
  `factor_desc` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '因子描述',
  `factor_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '因子类型：基础因子、衍生因子、风格因子',
  `data_source` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '数据来源',
  `calculation_method` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '计算方法',
  `unit` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '单位',
  `category` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '因子分类',
  `parent_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '父因子代码',
  `level` int NULL DEFAULT 1 COMMENT '因子层级',
  `sort_order` int NULL DEFAULT 0 COMMENT '排序',
  `status` int NULL DEFAULT 1 COMMENT '状态 0-禁用 1-正常',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_factor_code`(`factor_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '因子表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of factor
-- ----------------------------
INSERT INTO `factor` VALUES (1, 'F001', '收益率因子', '基金历史收益率表现', '基础因子', '基金数据库', '历史收益率计算', '%', '收益类', NULL, 1, 1, 1, '2025-06-28 15:41:27', '2025-06-28 15:41:27');
INSERT INTO `factor` VALUES (2, 'F002', '波动率因子', '基金收益波动性指标', '基础因子', '基金数据库', '标准差计算', '%', '风险类', NULL, 1, 2, 1, '2025-06-28 15:41:27', '2025-06-28 15:41:27');
INSERT INTO `factor` VALUES (3, 'F003', '夏普比率因子', '风险调整后收益指标', '基础因子', '基金数据库', '夏普比率计算', '无', '风险调整类', NULL, 1, 3, 1, '2025-06-28 15:41:27', '2025-06-28 15:41:27');
INSERT INTO `factor` VALUES (4, 'F004', '最大回撤因子', '基金最大回撤指标', '基础因子', '基金数据库', '最大回撤计算', '%', '风险类', NULL, 1, 4, 1, '2025-06-28 15:41:27', '2025-06-28 15:41:27');
INSERT INTO `factor` VALUES (5, 'F005', '规模因子', '基金资产规模指标', '基础因子', '基金数据库', '总资产计算', '亿元', '规模类', NULL, 1, 5, 1, '2025-06-28 15:41:27', '2025-06-28 15:41:27');
INSERT INTO `factor` VALUES (6, 'F006', '费率因子', '基金费率水平指标', '基础因子', '基金数据库', '费率统计', '%', '成本类', NULL, 1, 6, 1, '2025-06-28 15:41:27', '2025-06-28 15:41:27');
INSERT INTO `factor` VALUES (7, 'F007', '成立时间因子', '基金成立时间长短', '基础因子', '基金数据库', '成立时间计算', '年', '时间类', NULL, 1, 7, 1, '2025-06-28 15:41:27', '2025-06-28 15:41:27');
INSERT INTO `factor` VALUES (8, 'F008', '换手率因子', '基金换手率指标', '基础因子', '基金数据库', '换手率计算', '%', '交易类', NULL, 1, 8, 1, '2025-06-28 15:41:27', '2025-06-28 15:41:27');
INSERT INTO `factor` VALUES (9, '996', '996', '996', '基础因子', '996', '996', '996', '时间类', 'F001', 2, 2, 1, '2025-06-29 12:51:24', '2025-06-29 12:51:45');

-- ----------------------------
-- Table structure for factor_tree
-- ----------------------------
DROP TABLE IF EXISTS `factor_tree`;
CREATE TABLE `factor_tree`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `tree_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '树名称',
  `tree_desc` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '树描述',
  `business_scene` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '业务场景：基金研究、量化投研、特色数据等',
  `factor_codes` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '因子代码列表，JSON格式',
  `status` int NULL DEFAULT 1 COMMENT '状态 0-禁用 1-正常',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '因子树表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of factor_tree
-- ----------------------------
INSERT INTO `factor_tree` VALUES (1, '基金研究因子树', '用于基金研究和分析的因子分类', '基金研究', '[\"F001\",\"F002\",\"F003\",\"F004\",\"F005\",\"F006\",\"F007\",\"F008\"]', 1, '2025-06-28 15:41:27', '2025-06-28 15:41:27');
INSERT INTO `factor_tree` VALUES (2, '量化投研因子树', '用于量化投资研究的因子分类', '量化投研', '[\"F001\",\"F002\",\"F003\",\"F004\",\"F005\"]', 1, '2025-06-28 15:41:27', '2025-06-28 15:41:27');
INSERT INTO `factor_tree` VALUES (3, '特色数据因子树', '特色数据输出因子分类', '特色数据', '[\"F006\",\"F007\",\"F008\"]', 1, '2025-06-28 15:41:27', '2025-06-28 15:41:27');
INSERT INTO `factor_tree` VALUES (4, '996', '996', '基金研究', '[\"996\",\"F001\"]', 1, '2025-06-29 12:52:15', '2025-06-29 12:52:15');

-- ----------------------------
-- Table structure for fund
-- ----------------------------
DROP TABLE IF EXISTS `fund`;
CREATE TABLE `fund`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `fund_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '基金代码',
  `fund_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '基金名称',
  `fund_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '基金类型',
  `fund_company` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '基金公司',
  `fund_manager` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '基金经理',
  `net_value` decimal(10, 4) NULL DEFAULT NULL COMMENT '最新净值',
  `net_value_date` date NULL DEFAULT NULL COMMENT '净值日期',
  `total_assets` decimal(20, 2) NULL DEFAULT NULL COMMENT '总资产规模',
  `daily_return` decimal(10, 4) NULL DEFAULT NULL COMMENT '日收益率',
  `weekly_return` decimal(10, 4) NULL DEFAULT NULL COMMENT '周收益率',
  `monthly_return` decimal(10, 4) NULL DEFAULT NULL COMMENT '月收益率',
  `yearly_return` decimal(10, 4) NULL DEFAULT NULL COMMENT '年收益率',
  `max_drawdown` decimal(10, 4) NULL DEFAULT NULL COMMENT '最大回撤',
  `sharpe_ratio` decimal(10, 4) NULL DEFAULT NULL COMMENT '夏普比率',
  `risk_level` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '风险等级',
  `tags` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '基金标签',
  `status` int NULL DEFAULT 1 COMMENT '状态 0-停售 1-正常',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_fund_code`(`fund_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '基金表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of fund
-- ----------------------------
INSERT INTO `fund` VALUES (1, '000001', '华夏成长混合', '混合型', '华夏基金', '张三', 1.2345, '2024-01-15', 1000000000.00, 0.0150, 0.0250, 0.0800, 0.1500, -0.1200, 1.2500, '中风险', '[\"成长型\",\"混合型\",\"明星基金\"]', 1, '2025-06-28 15:01:07', '2025-06-28 15:01:07');
INSERT INTO `fund` VALUES (2, '000002', '易方达消费行业股票', '股票型', '易方达基金', '李四', 2.3456, '2024-01-15', 2000000000.00, 0.0200, 0.0300, 0.1000, 0.2000, -0.1500, 1.5000, '高风险', '[\"消费行业\",\"股票型\",\"热门基金\"]', 1, '2025-06-28 15:01:07', '2025-06-28 15:01:07');
INSERT INTO `fund` VALUES (3, '000003', '嘉实稳健债券', '债券型', '嘉实基金', '王五', 1.1234, '2024-01-15', 500000000.00, 0.0050, 0.0100, 0.0300, 0.0800, -0.0500, 0.8000, '低风险', '[\"债券型\",\"稳健型\",\"低风险\"]', 1, '2025-06-28 15:01:07', '2025-06-28 15:01:07');
INSERT INTO `fund` VALUES (4, '000004', '南方中证500ETF', '指数型', '南方基金', '赵六', 1.5678, '2024-01-15', 1500000000.00, 0.0180, 0.0280, 0.0900, 0.1800, -0.1300, 1.3500, '中风险', '[\"指数型\",\"ETF\",\"中证500\"]', 1, '2025-06-28 15:01:07', '2025-06-28 15:01:07');
INSERT INTO `fund` VALUES (5, '000005', '工银瑞信货币', '货币型', '工银瑞信基金', '钱七', 1.0000, '2024-01-15', 3000000000.00, 0.0010, 0.0050, 0.0200, 0.0500, 0.0000, 0.2000, '低风险', '[\"货币型\",\"流动性\",\"低风险\"]', 1, '2025-06-28 15:01:07', '2025-06-28 15:01:07');

-- ----------------------------
-- Table structure for fund_portfolio
-- ----------------------------
DROP TABLE IF EXISTS `fund_portfolio`;
CREATE TABLE `fund_portfolio`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `portfolio_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '组合名称',
  `portfolio_desc` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '组合描述',
  `user_id` bigint NOT NULL COMMENT '创建用户ID',
  `fund_codes` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '基金代码列表',
  `weights` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '权重列表',
  `status` int NULL DEFAULT 1 COMMENT '状态 0-禁用 1-正常',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '基金组合表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of fund_portfolio
-- ----------------------------
INSERT INTO `fund_portfolio` VALUES (1, '稳健配置组合', '适合稳健型投资者的基金组合', 1, '[\"000001\",\"000003\",\"000005\"]', '{\"1\":0.4,\"3\":0.4,\"5\":0.2}', 1, '2025-06-28 15:01:07', '2025-06-28 23:20:37');
INSERT INTO `fund_portfolio` VALUES (2, '成长配置组合', '适合成长型投资者的基金组合', 1, '[\"000001\",\"000002\",\"000004\"]', '{\"1\":0.3,\"2\":0.4,\"4\":0.3}', 1, '2025-06-28 15:01:07', '2025-06-28 23:23:21');
INSERT INTO `fund_portfolio` VALUES (3, '平衡配置组合', '适合平衡型投资者的基金组合', 2, '[\"000001\",\"000002\",\"000003\",\"000004\"]', '{\"1\":0.25,\"2\":0.25,\"3\":0.25,\"4\":0.25}', 1, '2025-06-28 15:01:07', '2025-06-28 23:23:46');
INSERT INTO `fund_portfolio` VALUES (4, 'szx', '996996', 4, '000004,000003', '{\"3\":0.49,\"4\":0.51}', 1, '2025-06-28 15:59:16', '2025-06-28 23:21:16');
INSERT INTO `fund_portfolio` VALUES (5, '666', '666', 4, '000001,000002', '{\"1\":0.50,\"2\":0.50}', 1, '2025-06-28 22:34:17', '2025-06-28 23:21:26');
INSERT INTO `fund_portfolio` VALUES (6, '1', '1', 4, '000002,000003', '{\"2\":0.5,\"3\":0.5}', 1, '2025-06-28 23:18:09', '2025-06-28 23:18:09');
INSERT INTO `fund_portfolio` VALUES (7, 'qq', 'qq', 1, '000001,000002', '{\"1\":0.5,\"2\":0.5}', 1, '2025-06-29 12:50:42', '2025-06-29 12:50:42');
INSERT INTO `fund_portfolio` VALUES (8, 'kkk', 'kkk', 1, '000003,000005', '{\"3\":0.49,\"5\":0.51}', 1, '2025-07-03 10:30:34', '2025-07-03 10:30:34');

-- ----------------------------
-- Table structure for investment_advice
-- ----------------------------
DROP TABLE IF EXISTS `investment_advice`;
CREATE TABLE `investment_advice`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '建议ID',
  `client_id` bigint NOT NULL COMMENT '客户ID',
  `advice_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '建议类型：资产配置、基金推荐、调仓建议',
  `title` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '建议标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '建议内容',
  `risk_level` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '风险等级：低、中、高',
  `expected_return` decimal(5, 4) NULL DEFAULT NULL COMMENT '预期收益率',
  `max_drawdown` decimal(5, 4) NULL DEFAULT NULL COMMENT '最大回撤',
  `fund_recommendations` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '推荐基金列表，JSON格式',
  `asset_allocation` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '资产配置建议，JSON格式',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `status` int NULL DEFAULT 0 COMMENT '状态：0-草稿 1-已发布 2-已采纳',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `client_id`(`client_id`) USING BTREE,
  CONSTRAINT `investment_advice_ibfk_1` FOREIGN KEY (`client_id`) REFERENCES `client` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '投资建议表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of investment_advice
-- ----------------------------
INSERT INTO `investment_advice` VALUES (1, 1, '资产配置', '稳健型资产配置建议', '根据您的风险承受能力和投资目标，建议采用稳健型资产配置策略，重点关注债券基金和混合基金。', '中', 0.0800, 0.1500, '[\"000001\", \"000002\", \"000003\"]', '{\"股票\": 0.30, \"债券\": 0.50, \"货币\": 0.20}', '2025-06-28 21:31:59', '2025-06-28 21:31:59', 1);
INSERT INTO `investment_advice` VALUES (2, 2, '基金推荐', '保守型基金组合推荐', '针对您的保守型投资偏好，推荐以下低风险基金组合，适合长期持有。', '低', 0.0500, 0.0800, '[\"000004\", \"000005\", \"000006\"]', '{\"债券\": 0.70, \"货币\": 0.30}', '2025-06-28 21:31:59', '2025-06-28 21:31:59', 1);
INSERT INTO `investment_advice` VALUES (3, 3, '调仓建议', '积极型调仓建议', '基于当前市场环境，建议适当增加股票基金配置比例，把握市场机会。', '高', 0.1200, 0.2500, '[\"000007\", \"000008\", \"000009\"]', '{\"股票\": 0.60, \"债券\": 0.30, \"货币\": 0.10}', '2025-06-28 21:31:59', '2025-06-28 21:31:59', 1);
INSERT INTO `investment_advice` VALUES (4, 4, '资产配置', '养老型资产配置', '针对您的养老投资目标，建议采用长期稳健的资产配置策略。', '中', 0.0700, 0.1200, '[\"000010\", \"000011\", \"000012\"]', '{\"股票\": 0.40, \"债券\": 0.45, \"货币\": 0.15}', '2025-06-28 21:31:59', '2025-06-28 21:31:59', 1);
INSERT INTO `investment_advice` VALUES (5, 5, '基金推荐', '成长型基金推荐', '结合您的年龄和收入情况，推荐成长型基金组合，适合中期投资。', '高', 0.1000, 0.2000, '[\"000013\", \"000014\", \"000015\"]', '{\"股票\": 0.55, \"债券\": 0.35, \"货币\": 0.10}', '2025-06-28 21:31:59', '2025-06-28 21:31:59', 1);

-- ----------------------------
-- Table structure for performance_evaluation
-- ----------------------------
DROP TABLE IF EXISTS `performance_evaluation`;
CREATE TABLE `performance_evaluation`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `evaluation_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '评估代码',
  `evaluation_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '评估名称',
  `strategy_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '策略代码',
  `portfolio_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '组合代码',
  `evaluation_date` timestamp(0) NULL DEFAULT NULL COMMENT '评估日期',
  `total_return` decimal(10, 4) NULL DEFAULT NULL COMMENT '总收益率',
  `annual_return` decimal(10, 4) NULL DEFAULT NULL COMMENT '年化收益率',
  `benchmark_return` decimal(10, 4) NULL DEFAULT NULL COMMENT '基准收益率',
  `excess_return` decimal(10, 4) NULL DEFAULT NULL COMMENT '超额收益',
  `sharpe_ratio` decimal(10, 4) NULL DEFAULT NULL COMMENT '夏普比率',
  `sortino_ratio` decimal(10, 4) NULL DEFAULT NULL COMMENT '索提诺比率',
  `calmar_ratio` decimal(10, 4) NULL DEFAULT NULL COMMENT '卡玛比率',
  `information_ratio` decimal(10, 4) NULL DEFAULT NULL COMMENT '信息比率',
  `max_drawdown` decimal(10, 4) NULL DEFAULT NULL COMMENT '最大回撤',
  `volatility` decimal(10, 4) NULL DEFAULT NULL COMMENT '波动率',
  `beta` decimal(10, 4) NULL DEFAULT NULL COMMENT '贝塔系数',
  `alpha` decimal(10, 4) NULL DEFAULT NULL COMMENT '阿尔法系数',
  `treynor_ratio` decimal(10, 4) NULL DEFAULT NULL COMMENT '特雷诺比率',
  `jensen_alpha` decimal(10, 4) NULL DEFAULT NULL COMMENT '詹森阿尔法',
  `risk_adjusted_return` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '风险调整后收益',
  `performance_ranking` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '业绩排名',
  `evaluation_result` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '评估结果：优秀、良好、一般、较差',
  `evaluation_report` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '评估报告，JSON格式',
  `evaluator_id` bigint NOT NULL COMMENT '评估者ID',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_evaluation_code`(`evaluation_code`) USING BTREE,
  INDEX `idx_strategy_code`(`strategy_code`) USING BTREE,
  INDEX `idx_portfolio_code`(`portfolio_code`) USING BTREE,
  INDEX `idx_evaluator_id`(`evaluator_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '绩效评估表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of performance_evaluation
-- ----------------------------
INSERT INTO `performance_evaluation` VALUES (1, 'PE001', '价值策略2023年评估', 'S001', NULL, '2023-12-31 23:59:59', 0.1500, 0.1500, 0.1200, 0.0300, 1.2000, 1.3500, 1.8750, 0.2500, -0.0800, 0.1200, 0.9500, 0.0250, 0.1580, 0.0200, '优秀', '前20%', '优秀', '{\"summary\":\"策略表现优秀\",\"details\":{}}', 1, '2025-06-28 20:48:31', '2025-06-28 20:48:31');
INSERT INTO `performance_evaluation` VALUES (2, 'PE002', '成长策略2023年评估', 'S002', NULL, '2023-12-31 23:59:59', 0.1800, 0.1800, 0.1200, 0.0600, 1.3500, 1.5000, 1.5000, 0.4000, -0.1200, 0.1500, 1.2000, 0.0450, 0.1500, 0.0350, '良好', '前30%', '良好', '{\"summary\":\"策略表现良好\",\"details\":{}}', 1, '2025-06-28 20:48:31', '2025-06-28 20:48:31');
INSERT INTO `performance_evaluation` VALUES (3, 'PE003', '平衡策略2023年评估', 'S003', NULL, '2023-12-31 23:59:59', 0.1200, 0.1200, 0.1200, 0.0000, 1.1000, 1.2000, 2.0000, 0.0000, -0.0600, 0.1000, 0.8500, 0.0100, 0.1410, 0.0080, '一般', '前50%', '一般', '{\"summary\":\"策略表现一般\",\"details\":{}}', 2, '2025-06-28 20:48:31', '2025-06-28 20:48:31');
INSERT INTO `performance_evaluation` VALUES (4, 'PE004', '996', 'S004', NULL, '2025-06-28 20:59:17', 0.1100, 0.1100, 0.1100, 0.0000, 1.0000, 1.1000, 2.1000, 0.0000, -0.0500, 0.1100, 0.9600, 0.0200, 0.1600, 0.0220, '一般', '前60%', '一般', '996', 2, '2025-06-28 21:01:22', '2025-06-28 21:01:22');

-- ----------------------------
-- Table structure for portfolio_optimization
-- ----------------------------
DROP TABLE IF EXISTS `portfolio_optimization`;
CREATE TABLE `portfolio_optimization`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '优化ID',
  `client_id` bigint NOT NULL COMMENT '客户ID',
  `optimization_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '优化方案名称',
  `optimization_method` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '优化方法：马科维茨、风险平价、最大夏普比率',
  `fund_pool` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '基金池，JSON格式',
  `target_return` decimal(5, 4) NULL DEFAULT NULL COMMENT '目标收益率',
  `max_risk` decimal(5, 4) NULL DEFAULT NULL COMMENT '最大风险',
  `optimized_weights` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '优化权重，JSON格式',
  `expected_return` decimal(5, 4) NULL DEFAULT NULL COMMENT '预期收益率',
  `expected_risk` decimal(5, 4) NULL DEFAULT NULL COMMENT '预期风险',
  `sharpe_ratio` decimal(5, 4) NULL DEFAULT NULL COMMENT '夏普比率',
  `constraints` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '约束条件，JSON格式',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `status` int NULL DEFAULT 0 COMMENT '状态：0-计算中 1-已完成 2-已应用',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `client_id`(`client_id`) USING BTREE,
  CONSTRAINT `portfolio_optimization_ibfk_1` FOREIGN KEY (`client_id`) REFERENCES `client` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '投资组合优化表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of portfolio_optimization
-- ----------------------------
INSERT INTO `portfolio_optimization` VALUES (1, 1, '马科维茨优化方案', '马科维茨', '[\"000001\", \"000002\", \"000003\", \"000004\", \"000005\"]', 0.0800, 0.1500, '{\"000001\": 0.25, \"000002\": 0.20, \"000003\": 0.30, \"000004\": 0.15, \"000005\": 0.10}', 0.0820, 0.1450, 0.5655, '{\"min_weight\": 0.05, \"max_weight\": 0.40}', '2025-06-28 21:32:00', '2025-06-28 21:32:00', 1);
INSERT INTO `portfolio_optimization` VALUES (2, 2, '风险平价优化方案', '风险平价', '[\"000006\", \"000007\", \"000008\", \"000009\"]', 0.0500, 0.0800, '{\"000006\": 0.35, \"000007\": 0.30, \"000008\": 0.20, \"000009\": 0.15}', 0.0520, 0.0750, 0.6933, '{\"min_weight\": 0.10, \"max_weight\": 0.50}', '2025-06-28 21:32:00', '2025-06-28 21:32:00', 1);
INSERT INTO `portfolio_optimization` VALUES (3, 3, '最大夏普比率优化', '最大夏普比率', '[\"000010\", \"000011\", \"000012\", \"000013\", \"000014\"]', 0.1200, 0.2500, '{\"000010\": 0.30, \"000011\": 0.25, \"000012\": 0.20, \"000013\": 0.15, \"000014\": 0.10}', 0.1250, 0.2300, 0.5435, '{\"min_weight\": 0.05, \"max_weight\": 0.35}', '2025-06-28 21:32:00', '2025-06-28 21:32:00', 1);
INSERT INTO `portfolio_optimization` VALUES (4, 4, '养老型优化方案', '马科维茨', '[\"000015\", \"000016\", \"000017\", \"000018\"]', 0.0700, 0.1200, '{\"000015\": 0.40, \"000016\": 0.30, \"000017\": 0.20, \"000018\": 0.10}', 0.0720, 0.1150, 0.6261, '{\"min_weight\": 0.08, \"max_weight\": 0.45}', '2025-06-28 21:32:00', '2025-06-28 21:32:00', 1);
INSERT INTO `portfolio_optimization` VALUES (5, 5, '成长型优化方案', '最大夏普比率', '[\"000019\", \"000020\", \"000021\", \"000022\"]', 0.1000, 0.2000, '{\"000019\": 0.35, \"000020\": 0.25, \"000021\": 0.25, \"000022\": 0.15}', 0.1050, 0.1850, 0.5676, '{\"min_weight\": 0.10, \"max_weight\": 0.40}', '2025-06-28 21:32:00', '2025-06-28 21:32:00', 1);

-- ----------------------------
-- Table structure for risk_alert
-- ----------------------------
DROP TABLE IF EXISTS `risk_alert`;
CREATE TABLE `risk_alert`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '预警ID',
  `alert_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '预警名称',
  `alert_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '预警类型：市场风险、信用风险、流动性风险、操作风险',
  `alert_level` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '预警等级：低、中、高、极高',
  `alert_source` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '预警来源',
  `alert_content` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '预警内容',
  `affected_portfolio` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '受影响组合',
  `impact_assessment` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '影响评估',
  `action_required` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '需要采取的行动',
  `responsible_person` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '责任人',
  `alert_time` timestamp(0) NULL DEFAULT NULL COMMENT '预警时间',
  `acknowledge_time` timestamp(0) NULL DEFAULT NULL COMMENT '确认时间',
  `resolve_time` timestamp(0) NULL DEFAULT NULL COMMENT '解决时间',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '未确认' COMMENT '状态：未确认、已确认、处理中、已解决',
  `resolution` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '解决方案',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `status_code` int NULL DEFAULT 1 COMMENT '状态：0-禁用 1-启用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '风险预警表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of risk_alert
-- ----------------------------
INSERT INTO `risk_alert` VALUES (1, '市场波动加剧预警', '市场风险', '中', '风险监控系统', '近期市场波动性显著增加，建议关注投资组合风险暴露。', '股票型基金组合', '可能影响投资组合的短期收益，但长期影响有限。', '适当降低股票仓位，增加防御性资产配置。', '投资经理', '2025-06-28 22:24:05', NULL, NULL, '已确认', NULL, '2025-06-28 22:24:05', '2025-06-28 22:24:05', 1);
INSERT INTO `risk_alert` VALUES (2, '流动性风险预警', '流动性风险', '高', '流动性监控', '某基金出现大额赎回申请，可能影响流动性。', '货币市场基金', '可能影响基金的正常运作和客户赎回。', '立即评估流动性状况，必要时启动应急预案。', '基金经理', '2025-06-28 22:24:05', NULL, NULL, '处理中', NULL, '2025-06-28 22:24:05', '2025-06-28 22:24:05', 1);
INSERT INTO `risk_alert` VALUES (3, '信用风险预警', '信用风险', '中', '信用评级机构', '某债券发行主体信用评级下调，需关注相关持仓。', '债券投资组合', '可能影响债券价格和投资收益。', '评估持仓风险，考虑减持或替换相关债券。', '债券投资经理', '2025-06-28 22:24:05', NULL, NULL, '已确认', NULL, '2025-06-28 22:24:05', '2025-06-28 22:24:05', 1);
INSERT INTO `risk_alert` VALUES (4, '操作风险预警', '操作风险', '低', '内部审计', '发现交易流程中的潜在风险点，需要加强控制。', '全投资组合', '影响较小，但需要及时整改。', '完善交易流程，加强内部控制。', '合规专员', '2025-06-28 22:24:05', NULL, NULL, '已解决', NULL, '2025-06-28 22:24:05', '2025-06-28 22:24:05', 1);
INSERT INTO `risk_alert` VALUES (5, '集中度风险预警', '市场风险', '中', '风险监控系统', '单一资产持仓比例接近限制，需要关注。', '股票投资组合', '可能增加投资组合的非系统性风险。', '适当分散投资，降低单一资产集中度。', '投资经理', '2025-06-28 22:24:05', NULL, NULL, '已确认', NULL, '2025-06-28 22:24:05', '2025-06-28 22:24:05', 1);
INSERT INTO `risk_alert` VALUES (6, '汇率风险预警', '市场风险', '高', '外汇监控', '人民币汇率波动加大，可能影响海外投资。', 'QDII基金组合', '可能显著影响海外投资的收益和风险。', '加强汇率风险管理，考虑对冲策略。', '海外投资经理', '2025-06-28 22:24:05', NULL, NULL, '未确认', NULL, '2025-06-28 22:24:05', '2025-06-28 22:24:05', 1);
INSERT INTO `risk_alert` VALUES (7, '利率风险预警', '市场风险', '中', '利率监控', '市场利率预期变化，可能影响债券投资。', '债券投资组合', '可能影响债券价格和久期管理。', '调整债券久期，优化利率风险管理。', '债券投资经理', '2025-06-28 22:24:05', NULL, NULL, '已确认', NULL, '2025-06-28 22:24:05', '2025-06-28 22:24:05', 1);
INSERT INTO `risk_alert` VALUES (8, '合规风险预警', '操作风险', '高', '合规检查', '发现潜在的合规问题，需要立即处理。', '全投资组合', '可能影响公司声誉和业务开展。', '立即停止相关操作，进行合规整改。', '合规总监', '2025-06-28 22:24:05', NULL, NULL, '处理中', NULL, '2025-06-28 22:24:05', '2025-06-28 22:24:05', 1);

-- ----------------------------
-- Table structure for risk_management
-- ----------------------------
DROP TABLE IF EXISTS `risk_management`;
CREATE TABLE `risk_management`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `risk_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '风险代码',
  `risk_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '风险名称',
  `risk_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '风险类型：市场风险、信用风险、流动性风险等',
  `risk_level` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '风险等级：低、中、高',
  `risk_value` decimal(10, 4) NULL DEFAULT NULL COMMENT '风险值',
  `risk_desc` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '风险描述',
  `measurement_method` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '测量方法',
  `risk_limit` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '风险限额',
  `alert_threshold` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '预警阈值',
  `mitigation_measures` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '缓解措施',
  `related_strategy` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '相关策略',
  `related_portfolio` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '相关组合',
  `creator_id` bigint NOT NULL COMMENT '创建者ID',
  `status` int NULL DEFAULT 1 COMMENT '状态 0-禁用 1-正常',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_risk_code`(`risk_code`) USING BTREE,
  INDEX `idx_creator_id`(`creator_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '风险管理表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of risk_management
-- ----------------------------
INSERT INTO `risk_management` VALUES (1, 'R001', '市场风险监控', '市场风险', '中', 0.0800, '监控市场波动对投资组合的影响', 'VaR模型', '10%', '8%', '动态调整仓位，增加对冲工具', 'S001,S002', 'P001', 1, 1, '2025-06-28 20:48:31', '2025-06-28 20:48:31');
INSERT INTO `risk_management` VALUES (2, 'R002', '流动性风险控制', '流动性风险', '低', 0.0300, '控制投资组合的流动性风险', '流动性比率', '5%', '3%', '保持充足现金，控制持仓集中度', 'S003', 'P002', 1, 1, '2025-06-28 20:48:31', '2025-06-28 20:48:31');
INSERT INTO `risk_management` VALUES (3, 'R003', '信用风险评估', '信用风险', '低', 0.0200, '评估债券投资的信用风险', '信用评级', '3%', '2%', '选择高信用等级债券，分散投资', 'S004', 'P003', 2, 1, '2025-06-28 20:48:31', '2025-06-28 20:48:31');

-- ----------------------------
-- Table structure for risk_monitor
-- ----------------------------
DROP TABLE IF EXISTS `risk_monitor`;
CREATE TABLE `risk_monitor`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '监控ID',
  `monitor_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '监控名称',
  `monitor_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '监控类型：市场风险、信用风险、流动性风险、操作风险',
  `risk_indicator` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '风险指标',
  `threshold` decimal(10, 4) NOT NULL COMMENT '阈值',
  `current_value` decimal(10, 4) NULL DEFAULT NULL COMMENT '当前值',
  `risk_level` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '风险等级：低、中、高、极高',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '正常' COMMENT '状态：正常、预警、超限',
  `description` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '描述',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `status_code` int NULL DEFAULT 1 COMMENT '状态：0-禁用 1-启用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '风险监控表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of risk_monitor
-- ----------------------------
INSERT INTO `risk_monitor` VALUES (1, 'VaR监控', '市场风险', 'VaR(95%)', 0.0500, 0.0450, '中', '正常', '投资组合95%置信度下的最大可能损失', '2025-06-28 22:24:05', '2025-06-28 22:24:05', 1);
INSERT INTO `risk_monitor` VALUES (2, '集中度监控', '市场风险', '单一资产集中度', 0.1000, 0.0850, '低', '正常', '单一资产占投资组合的最大比例', '2025-06-28 22:24:05', '2025-06-28 22:24:05', 1);
INSERT INTO `risk_monitor` VALUES (3, '久期监控', '市场风险', '投资组合久期', 5.0000, 4.2000, '低', '正常', '投资组合的平均久期', '2025-06-28 22:24:05', '2025-06-28 22:24:05', 1);
INSERT INTO `risk_monitor` VALUES (4, '流动性监控', '流动性风险', '流动性覆盖率', 1.0000, 1.1500, '低', '正常', '30天内高流动性资产与净现金流出比率', '2025-06-28 22:24:05', '2025-06-28 22:24:05', 1);
INSERT INTO `risk_monitor` VALUES (5, '信用评级监控', '信用风险', '平均信用评级', 3.0000, 2.8000, '中', '正常', '投资组合中债券的平均信用评级', '2025-06-28 22:24:05', '2025-06-28 22:24:05', 1);
INSERT INTO `risk_monitor` VALUES (6, '杠杆率监控', '市场风险', '投资组合杠杆率', 1.5000, 1.3500, '低', '正常', '投资组合的总杠杆率', '2025-06-28 22:24:05', '2025-06-28 22:24:05', 1);
INSERT INTO `risk_monitor` VALUES (7, '波动率监控', '市场风险', '投资组合波动率', 0.2000, 0.1800, '中', '正常', '投资组合的年化波动率', '2025-06-28 22:24:05', '2025-06-28 22:24:05', 1);
INSERT INTO `risk_monitor` VALUES (8, '相关性监控', '市场风险', '资产相关性', 0.8000, 0.7500, '低', '正常', '投资组合中主要资产间的平均相关性', '2025-06-28 22:24:05', '2025-06-28 22:24:05', 1);

-- ----------------------------
-- Table structure for risk_report
-- ----------------------------
DROP TABLE IF EXISTS `risk_report`;
CREATE TABLE `risk_report`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '报告ID',
  `report_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '报告名称',
  `report_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '报告类型：日报、周报、月报、季报、年报',
  `report_period` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '报告期间',
  `risk_summary` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '风险摘要',
  `market_risk` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '市场风险分析',
  `credit_risk` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '信用风险分析',
  `liquidity_risk` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '流动性风险分析',
  `operational_risk` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '操作风险分析',
  `risk_metrics` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '风险指标，JSON格式',
  `recommendations` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '风险建议',
  `author` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '报告作者',
  `reviewer` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '报告审核人',
  `report_date` timestamp(0) NULL DEFAULT NULL COMMENT '报告日期',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '草稿' COMMENT '状态：草稿、已提交、已审核、已发布',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `status_code` int NULL DEFAULT 1 COMMENT '状态：0-禁用 1-启用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '风险报告表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of risk_report
-- ----------------------------
INSERT INTO `risk_report` VALUES (1, '2024年1月风险月报', '月报', '2024年1月', '本月投资组合整体风险可控，各项风险指标均在合理范围内。', '市场风险方面，受宏观经济政策影响，股票市场波动加大，但通过分散投资有效控制了风险。', '信用风险方面，债券投资以高信用等级为主，信用风险较低。', '流动性风险方面，保持了充足的现金比例，流动性状况良好。', '操作风险方面，交易流程规范，未发生重大操作风险事件。', '{\"VaR\": 0.045, \"波动率\": 0.18, \"夏普比率\": 1.25, \"最大回撤\": 0.08}', '建议继续关注市场变化，适时调整投资策略，保持风险控制的有效性。', '风险管理部', '合规部', '2024-01-31 00:00:00', '已发布', '2025-06-28 22:24:05', '2025-06-28 22:24:05', 1);
INSERT INTO `risk_report` VALUES (2, '2024年Q1风险季报', '季报', '2024年Q1', '一季度投资组合风险状况良好，各项风险指标表现稳定。', '市场风险在可控范围内，通过资产配置优化有效降低了系统性风险。', '信用风险保持低位，债券投资结构合理。', '流动性管理有效，能够满足正常赎回需求。', '操作风险控制良好，业务流程规范。', '{\"VaR\": 0.042, \"波动率\": 0.16, \"夏普比率\": 1.35, \"最大回撤\": 0.06}', '建议继续优化资产配置，加强风险监控，提升投资组合的稳定性。', '风险管理部', '合规部', '2024-03-31 00:00:00', '已发布', '2025-06-28 22:24:05', '2025-06-28 22:24:05', 1);
INSERT INTO `risk_report` VALUES (3, '2024年风险年报', '年报', '2024年', '全年风险管理工作成效显著，投资组合风险控制有效。', '全年市场风险控制良好，通过动态调整有效应对市场变化。', '信用风险持续低位，债券投资质量优良。', '流动性管理规范，风险准备金充足。', '操作风险控制体系完善，全年无重大风险事件。', '{\"VaR\": 0.040, \"波动率\": 0.15, \"夏普比率\": 1.40, \"最大回撤\": 0.05}', '建议进一步完善风险管理体系，提升风险预警能力，确保投资组合的长期稳定。', '风险管理部', '合规部', '2024-12-31 00:00:00', '已审核', '2025-06-28 22:24:05', '2025-06-28 22:24:05', 1);
INSERT INTO `risk_report` VALUES (4, '2024年2月风险月报', '月报', '2024年2月', '本月风险状况稳定，各项指标表现良好。', '市场风险可控，投资组合表现符合预期。', '信用风险保持低位，债券投资结构合理。', '流动性充足，能够满足客户需求。', '操作流程规范，风险控制有效。', '{\"VaR\": 0.043, \"波动率\": 0.17, \"夏普比率\": 1.30, \"最大回撤\": 0.07}', '建议继续关注市场动态，保持风险控制的连续性。', '风险管理部', '合规部', '2024-02-29 00:00:00', '已提交', '2025-06-28 22:24:05', '2025-06-28 22:33:10', 1);

-- ----------------------------
-- Table structure for strategy
-- ----------------------------
DROP TABLE IF EXISTS `strategy`;
CREATE TABLE `strategy`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `strategy_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '策略代码',
  `strategy_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '策略名称',
  `strategy_desc` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '策略描述',
  `strategy_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '策略类型：股票策略、债券策略、混合策略等',
  `investment_style` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '投资风格：价值型、成长型、平衡型等',
  `risk_level` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '风险等级：低风险、中风险、高风险',
  `target_return` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '目标收益率',
  `max_drawdown` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '最大回撤限制',
  `benchmark` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '基准指数',
  `factor_codes` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '使用的因子代码，JSON格式',
  `parameters` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '策略参数，JSON格式',
  `creator_id` bigint NOT NULL COMMENT '创建者ID',
  `status` int NULL DEFAULT 1 COMMENT '状态 0-禁用 1-正常',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_strategy_code`(`strategy_code`) USING BTREE,
  INDEX `idx_creator_id`(`creator_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '策略表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of strategy
-- ----------------------------
INSERT INTO `strategy` VALUES (1, 'S001', '价值投资策略', '基于价值因子的投资策略', '股票策略', '价值型', '中风险', '15%', '10%', '沪深300', '[\"F001\",\"F003\",\"F005\"]', '{\"rebalance_frequency\":\"monthly\",\"max_position\":0.1}', 1, 1, '2025-06-28 20:48:31', '2025-06-28 20:48:31');
INSERT INTO `strategy` VALUES (2, 'S002', '成长投资策略', '基于成长因子的投资策略', '股票策略', '成长型', '高风险', '20%', '15%', '创业板指', '[\"F001\",\"F002\",\"F008\"]', '{\"rebalance_frequency\":\"weekly\",\"max_position\":0.15}', 1, 1, '2025-06-28 20:48:31', '2025-06-28 20:48:31');
INSERT INTO `strategy` VALUES (3, 'S003', '平衡配置策略', '平衡收益和风险的配置策略', '混合策略', '平衡型', '中风险', '12%', '8%', '中证500', '[\"F001\",\"F003\",\"F004\",\"F006\"]', '{\"rebalance_frequency\":\"monthly\",\"max_position\":0.08}', 2, 1, '2025-06-28 20:48:31', '2025-06-28 20:48:31');
INSERT INTO `strategy` VALUES (4, 'S004', '低风险债券策略', '低风险债券投资策略', '债券策略', '稳健型', '低风险', '6%', '3%', '中债总财富', '[\"F001\",\"F002\",\"F006\"]', '{\"rebalance_frequency\":\"quarterly\",\"max_position\":0.05}', 1, 1, '2025-06-28 20:48:31', '2025-06-28 20:48:31');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '手机号',
  `real_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `status` int NULL DEFAULT 1 COMMENT '状态 0-禁用 1-正常',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 35 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', '123456', 'admin@investech.com', '13800138000', '管理员', 1, '2025-06-28 15:01:07', '2025-06-28 15:01:07');
INSERT INTO `user` VALUES (2, 'user1', '123456', 'user1@investech.com', '13800138001', '测试用户1', 1, '2025-06-28 15:01:07', '2025-06-28 15:01:07');
INSERT INTO `user` VALUES (3, 'user2', '123456', 'user2@investech.com', '13800138002', '测试用户2', 1, '2025-06-28 15:01:07', '2025-06-28 15:01:07');
INSERT INTO `user` VALUES (4, 'szx', '123456', '3307203596@qq.com', '13001844666', 'szx', 1, '2025-06-28 15:58:41', '2025-06-28 15:58:41');
INSERT INTO `user` VALUES (34, 'user3', '123456', '3307203596@qq.com', '13001844696', 'user3', 1, '2025-06-29 12:49:57', '2025-06-29 12:49:57');

SET FOREIGN_KEY_CHECKS = 1;
