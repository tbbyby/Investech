-- 风险管理与合规模块数据库表结构

-- 风险监控表
CREATE TABLE IF NOT EXISTS risk_monitor (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '监控ID',
    monitor_name VARCHAR(100) NOT NULL COMMENT '监控名称',
    monitor_type VARCHAR(50) NOT NULL COMMENT '监控类型：市场风险、信用风险、流动性风险、操作风险',
    risk_indicator VARCHAR(100) NOT NULL COMMENT '风险指标',
    threshold DECIMAL(10,4) NOT NULL COMMENT '阈值',
    current_value DECIMAL(10,4) COMMENT '当前值',
    risk_level VARCHAR(20) COMMENT '风险等级：低、中、高、极高',
    status VARCHAR(20) DEFAULT '正常' COMMENT '状态：正常、预警、超限',
    description VARCHAR(500) COMMENT '描述',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    status_code INT DEFAULT 1 COMMENT '状态：0-禁用 1-启用'
) COMMENT '风险监控表';

-- 合规检查表
CREATE TABLE IF NOT EXISTS compliance_check (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '检查ID',
    check_name VARCHAR(100) NOT NULL COMMENT '检查名称',
    check_type VARCHAR(50) NOT NULL COMMENT '检查类型：投资限制、持仓限制、交易限制、信息披露',
    check_rule VARCHAR(200) NOT NULL COMMENT '检查规则',
    check_result VARCHAR(20) DEFAULT '待检查' COMMENT '检查结果：通过、不通过、待确认',
    violation_type VARCHAR(50) COMMENT '违规类型',
    violation_description VARCHAR(500) COMMENT '违规描述',
    action_required VARCHAR(200) COMMENT '需要采取的行动',
    responsible_person VARCHAR(50) COMMENT '责任人',
    check_time TIMESTAMP COMMENT '检查时间',
    resolve_time TIMESTAMP COMMENT '解决时间',
    status VARCHAR(20) DEFAULT '待检查' COMMENT '状态：待检查、已检查、已解决',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    status_code INT DEFAULT 1 COMMENT '状态：0-禁用 1-启用'
) COMMENT '合规检查表';

-- 风险报告表
CREATE TABLE IF NOT EXISTS risk_report (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '报告ID',
    report_name VARCHAR(100) NOT NULL COMMENT '报告名称',
    report_type VARCHAR(20) NOT NULL COMMENT '报告类型：日报、周报、月报、季报、年报',
    report_period VARCHAR(50) NOT NULL COMMENT '报告期间',
    risk_summary VARCHAR(1000) COMMENT '风险摘要',
    market_risk VARCHAR(1000) COMMENT '市场风险分析',
    credit_risk VARCHAR(1000) COMMENT '信用风险分析',
    liquidity_risk VARCHAR(1000) COMMENT '流动性风险分析',
    operational_risk VARCHAR(1000) COMMENT '操作风险分析',
    risk_metrics VARCHAR(1000) COMMENT '风险指标，JSON格式',
    recommendations VARCHAR(1000) COMMENT '风险建议',
    author VARCHAR(50) COMMENT '报告作者',
    reviewer VARCHAR(50) COMMENT '报告审核人',
    report_date TIMESTAMP COMMENT '报告日期',
    status VARCHAR(20) DEFAULT '草稿' COMMENT '状态：草稿、已提交、已审核、已发布',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    status_code INT DEFAULT 1 COMMENT '状态：0-禁用 1-启用'
) COMMENT '风险报告表';

-- 风险预警表
CREATE TABLE IF NOT EXISTS risk_alert (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '预警ID',
    alert_name VARCHAR(100) NOT NULL COMMENT '预警名称',
    alert_type VARCHAR(50) NOT NULL COMMENT '预警类型：市场风险、信用风险、流动性风险、操作风险',
    alert_level VARCHAR(20) NOT NULL COMMENT '预警等级：低、中、高、极高',
    alert_source VARCHAR(100) COMMENT '预警来源',
    alert_content VARCHAR(500) NOT NULL COMMENT '预警内容',
    affected_portfolio VARCHAR(200) COMMENT '受影响组合',
    impact_assessment VARCHAR(500) COMMENT '影响评估',
    action_required VARCHAR(200) COMMENT '需要采取的行动',
    responsible_person VARCHAR(50) COMMENT '责任人',
    alert_time TIMESTAMP COMMENT '预警时间',
    acknowledge_time TIMESTAMP COMMENT '确认时间',
    resolve_time TIMESTAMP COMMENT '解决时间',
    status VARCHAR(20) DEFAULT '未确认' COMMENT '状态：未确认、已确认、处理中、已解决',
    resolution VARCHAR(500) COMMENT '解决方案',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    status_code INT DEFAULT 1 COMMENT '状态：0-禁用 1-启用'
) COMMENT '风险预警表';

-- 插入测试数据

-- 风险监控测试数据
INSERT INTO risk_monitor (monitor_name, monitor_type, risk_indicator, threshold, current_value, risk_level, status, description) VALUES
('VaR监控', '市场风险', 'VaR(95%)', 0.0500, 0.0450, '中', '正常', '投资组合95%置信度下的最大可能损失'),
('集中度监控', '市场风险', '单一资产集中度', 0.1000, 0.0850, '低', '正常', '单一资产占投资组合的最大比例'),
('久期监控', '市场风险', '投资组合久期', 5.0000, 4.2000, '低', '正常', '投资组合的平均久期'),
('流动性监控', '流动性风险', '流动性覆盖率', 1.0000, 1.1500, '低', '正常', '30天内高流动性资产与净现金流出比率'),
('信用评级监控', '信用风险', '平均信用评级', 3.0000, 2.8000, '中', '正常', '投资组合中债券的平均信用评级'),
('杠杆率监控', '市场风险', '投资组合杠杆率', 1.5000, 1.3500, '低', '正常', '投资组合的总杠杆率'),
('波动率监控', '市场风险', '投资组合波动率', 0.2000, 0.1800, '中', '正常', '投资组合的年化波动率'),
('相关性监控', '市场风险', '资产相关性', 0.8000, 0.7500, '低', '正常', '投资组合中主要资产间的平均相关性');

-- 合规检查测试数据
INSERT INTO compliance_check (check_name, check_type, check_rule, check_result, violation_type, violation_description, action_required, responsible_person, check_time, status) VALUES
('单一基金持仓限制', '持仓限制', '单一基金持仓不超过10%', '通过', NULL, NULL, NULL, '合规专员', NOW(), '已检查'),
('投资范围检查', '投资限制', '仅投资于股票型、债券型、混合型基金', '通过', NULL, NULL, NULL, '合规专员', NOW(), '已检查'),
('交易频率检查', '交易限制', '单日交易次数不超过5次', '通过', NULL, NULL, NULL, '合规专员', NOW(), '已检查'),
('信息披露检查', '信息披露', '定期披露投资组合持仓情况', '通过', NULL, NULL, NULL, '合规专员', NOW(), '已检查'),
('风险等级匹配', '投资限制', '投资产品风险等级与客户风险承受能力匹配', '不通过', '风险等级不匹配', '某客户投资了超出其风险承受能力的产品', '调整投资组合，降低高风险产品比例', '投资经理', NOW(), '已检查'),
('资金流向检查', '交易限制', '大额资金流向需审批', '通过', NULL, NULL, NULL, '合规专员', NOW(), '已检查'),
('关联交易检查', '交易限制', '避免与关联方进行交易', '通过', NULL, NULL, NULL, '合规专员', NOW(), '已检查'),
('业绩披露检查', '信息披露', '定期披露投资业绩', '通过', NULL, NULL, NULL, '合规专员', NOW(), '已检查');

-- 风险报告测试数据
INSERT INTO risk_report (report_name, report_type, report_period, risk_summary, market_risk, credit_risk, liquidity_risk, operational_risk, risk_metrics, recommendations, author, reviewer, report_date, status) VALUES
('2024年1月风险月报', '月报', '2024年1月', '本月投资组合整体风险可控，各项风险指标均在合理范围内。', '市场风险方面，受宏观经济政策影响，股票市场波动加大，但通过分散投资有效控制了风险。', '信用风险方面，债券投资以高信用等级为主，信用风险较低。', '流动性风险方面，保持了充足的现金比例，流动性状况良好。', '操作风险方面，交易流程规范，未发生重大操作风险事件。', '{"VaR": 0.045, "波动率": 0.18, "夏普比率": 1.25, "最大回撤": 0.08}', '建议继续关注市场变化，适时调整投资策略，保持风险控制的有效性。', '风险管理部', '合规部', '2024-01-31', '已发布'),
('2024年Q1风险季报', '季报', '2024年Q1', '一季度投资组合风险状况良好，各项风险指标表现稳定。', '市场风险在可控范围内，通过资产配置优化有效降低了系统性风险。', '信用风险保持低位，债券投资结构合理。', '流动性管理有效，能够满足正常赎回需求。', '操作风险控制良好，业务流程规范。', '{"VaR": 0.042, "波动率": 0.16, "夏普比率": 1.35, "最大回撤": 0.06}', '建议继续优化资产配置，加强风险监控，提升投资组合的稳定性。', '风险管理部', '合规部', '2024-03-31', '已发布'),
('2024年风险年报', '年报', '2024年', '全年风险管理工作成效显著，投资组合风险控制有效。', '全年市场风险控制良好，通过动态调整有效应对市场变化。', '信用风险持续低位，债券投资质量优良。', '流动性管理规范，风险准备金充足。', '操作风险控制体系完善，全年无重大风险事件。', '{"VaR": 0.040, "波动率": 0.15, "夏普比率": 1.40, "最大回撤": 0.05}', '建议进一步完善风险管理体系，提升风险预警能力，确保投资组合的长期稳定。', '风险管理部', '合规部', '2024-12-31', '已审核'),
('2024年2月风险月报', '月报', '2024年2月', '本月风险状况稳定，各项指标表现良好。', '市场风险可控，投资组合表现符合预期。', '信用风险保持低位，债券投资结构合理。', '流动性充足，能够满足客户需求。', '操作流程规范，风险控制有效。', '{"VaR": 0.043, "波动率": 0.17, "夏普比率": 1.30, "最大回撤": 0.07}', '建议继续关注市场动态，保持风险控制的连续性。', '风险管理部', '合规部', '2024-02-29', '已提交');

-- 风险预警测试数据
INSERT INTO risk_alert (alert_name, alert_type, alert_level, alert_source, alert_content, affected_portfolio, impact_assessment, action_required, responsible_person, alert_time, status) VALUES
('市场波动加剧预警', '市场风险', '中', '风险监控系统', '近期市场波动性显著增加，建议关注投资组合风险暴露。', '股票型基金组合', '可能影响投资组合的短期收益，但长期影响有限。', '适当降低股票仓位，增加防御性资产配置。', '投资经理', NOW(), '已确认'),
('流动性风险预警', '流动性风险', '高', '流动性监控', '某基金出现大额赎回申请，可能影响流动性。', '货币市场基金', '可能影响基金的正常运作和客户赎回。', '立即评估流动性状况，必要时启动应急预案。', '基金经理', NOW(), '处理中'),
('信用风险预警', '信用风险', '中', '信用评级机构', '某债券发行主体信用评级下调，需关注相关持仓。', '债券投资组合', '可能影响债券价格和投资收益。', '评估持仓风险，考虑减持或替换相关债券。', '债券投资经理', NOW(), '已确认'),
('操作风险预警', '操作风险', '低', '内部审计', '发现交易流程中的潜在风险点，需要加强控制。', '全投资组合', '影响较小，但需要及时整改。', '完善交易流程，加强内部控制。', '合规专员', NOW(), '已解决'),
('集中度风险预警', '市场风险', '中', '风险监控系统', '单一资产持仓比例接近限制，需要关注。', '股票投资组合', '可能增加投资组合的非系统性风险。', '适当分散投资，降低单一资产集中度。', '投资经理', NOW(), '已确认'),
('汇率风险预警', '市场风险', '高', '外汇监控', '人民币汇率波动加大，可能影响海外投资。', 'QDII基金组合', '可能显著影响海外投资的收益和风险。', '加强汇率风险管理，考虑对冲策略。', '海外投资经理', NOW(), '未确认'),
('利率风险预警', '市场风险', '中', '利率监控', '市场利率预期变化，可能影响债券投资。', '债券投资组合', '可能影响债券价格和久期管理。', '调整债券久期，优化利率风险管理。', '债券投资经理', NOW(), '已确认'),
('合规风险预警', '操作风险', '高', '合规检查', '发现潜在的合规问题，需要立即处理。', '全投资组合', '可能影响公司声誉和业务开展。', '立即停止相关操作，进行合规整改。', '合规总监', NOW(), '处理中'); 