package com.investech.common;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ValidationUtils工具类单元测试
 * 测试数据验证相关的功能
 */
@SpringBootTest
class ValidationUtilsTest {

    @Test
    @DisplayName("测试邮箱格式验证-正确格式")
    void testIsValidEmailWithValidFormat() {
        // 准备测试数据
        String[] validEmails = {
            "test@example.com",
            "user.name@domain.co.uk",
            "user+tag@example.org",
            "123@test.com",
            "test.email@subdomain.example.com"
        };
        
        // 执行测试并验证结果
        for (String email : validEmails) {
            assertTrue(ValidationUtils.isValidEmail(email), 
                "邮箱格式应该有效: " + email);
        }
    }

    @Test
    @DisplayName("测试手机号格式验证-正确格式")
    void testIsValidPhoneWithValidFormat() {
        // 准备测试数据
        String[] validPhones = {
            "13800138000",
            "15912345678",
            "18612345678",
            "17712345678",
            "19912345678"
        };
        
        // 执行测试并验证结果
        for (String phone : validPhones) {
            assertTrue(ValidationUtils.isValidPhone(phone), 
                "手机号格式应该有效: " + phone);
        }
    }

    @Test
    @DisplayName("测试手机号格式验证-错误格式")
    void testIsValidPhoneWithInvalidFormat() {
        // 准备测试数据
        String[] invalidPhones = {
            "1234567890", // 10位
            "123456789012", // 12位
            "12345678901", // 11位但格式不对
            "abcdefghijk", // 包含字母
            "1380013800a", // 包含字母
            "",
            null
        };
        
        // 执行测试并验证结果
        for (String phone : invalidPhones) {
            assertFalse(ValidationUtils.isValidPhone(phone), 
                "手机号格式应该无效: " + phone);
        }
    }

    @Test
    @DisplayName("测试字符串非空验证")
    void testIsNotEmpty() {
        // 准备测试数据
        String validString = "test";
        String emptyString = "";
        String nullString = null;
        String whitespaceString = "   ";
        
        // 执行测试并验证结果
        assertTrue(ValidationUtils.isNotEmpty(validString), "非空字符串应该返回true");
        assertFalse(ValidationUtils.isNotEmpty(emptyString), "空字符串应该返回false");
        assertFalse(ValidationUtils.isNotEmpty(nullString), "null应该返回false");
        assertTrue(ValidationUtils.isNotEmpty(whitespaceString), "空白字符串应该返回true");
    }

    @Test
    @DisplayName("测试字符串非空且非空白验证")
    void testIsNotBlank() {
        // 准备测试数据
        String validString = "test";
        String emptyString = "";
        String nullString = null;
        String whitespaceString = "   ";
        String tabString = "\t";
        String newlineString = "\n";
        
        // 执行测试并验证结果
        assertTrue(ValidationUtils.isNotBlank(validString), "非空字符串应该返回true");
        assertFalse(ValidationUtils.isNotBlank(emptyString), "空字符串应该返回false");
        assertFalse(ValidationUtils.isNotBlank(nullString), "null应该返回false");
        assertFalse(ValidationUtils.isNotBlank(whitespaceString), "空白字符串应该返回false");
        assertFalse(ValidationUtils.isNotBlank(tabString), "制表符字符串应该返回false");
        assertFalse(ValidationUtils.isNotBlank(newlineString), "换行符字符串应该返回false");
    }

    @Test
    @DisplayName("测试数字范围验证")
    void testIsInRange() {
        // 准备测试数据
        int min = 1;
        int max = 100;
        
        // 执行测试并验证结果
        assertTrue(ValidationUtils.isInRange(50, min, max), "范围内的数字应该返回true");
        assertTrue(ValidationUtils.isInRange(1, min, max), "最小值应该返回true");
        assertTrue(ValidationUtils.isInRange(100, min, max), "最大值应该返回true");
        assertFalse(ValidationUtils.isInRange(0, min, max), "小于最小值应该返回false");
        assertFalse(ValidationUtils.isInRange(101, min, max), "大于最大值应该返回false");
    }

    @Test
    @DisplayName("测试字符串长度验证")
    void testIsValidLength() {
        // 准备测试数据
        String testString = "test";
        int minLength = 2;
        int maxLength = 10;
        
        // 执行测试并验证结果
        assertTrue(ValidationUtils.isValidLength(testString, minLength, maxLength), 
            "长度在范围内的字符串应该返回true");
        assertFalse(ValidationUtils.isValidLength("a", minLength, maxLength), 
            "长度小于最小值的字符串应该返回false");
        assertFalse(ValidationUtils.isValidLength("verylongstring", minLength, maxLength), 
            "长度大于最大值的字符串应该返回false");
        assertFalse(ValidationUtils.isValidLength("", minLength, maxLength), 
            "空字符串应该返回false");
        assertFalse(ValidationUtils.isValidLength(null, minLength, maxLength), 
            "null应该返回false");
    }

    @Test
    @DisplayName("测试基金代码格式验证")
    void testIsValidFundCode() {
        // 准备测试数据
        String[] validFundCodes = {
            "000001",
            "000002",
            "110001",
            "159919",
            "510300"
        };
        
        String[] invalidFundCodes = {
            "00000", // 5位
            "0000001", // 7位
            "00000a", // 包含字母
            "00000A", // 包含大写字母
            "",
            null
        };
        
        // 执行测试并验证结果
        for (String fundCode : validFundCodes) {
            assertTrue(ValidationUtils.isValidFundCode(fundCode), 
                "基金代码格式应该有效: " + fundCode);
        }
        
        for (String fundCode : invalidFundCodes) {
            assertFalse(ValidationUtils.isValidFundCode(fundCode), 
                "基金代码格式应该无效: " + fundCode);
        }
    }

    @Test
    @DisplayName("测试因子代码格式验证")
    void testIsValidFactorCode() {
        // 准备测试数据
        String[] validFactorCodes = {
            "F001",
            "F002",
            "F123",
            "F999"
        };
        
        String[] invalidFactorCodes = {
            "F00", // 3位
            "F0001", // 5位
            "f001", // 小写
            "A001", // 不是F开头
            "F00a", // 包含字母
            "",
            null
        };
        
        // 执行测试并验证结果
        for (String factorCode : validFactorCodes) {
            assertTrue(ValidationUtils.isValidFactorCode(factorCode), 
                "因子代码格式应该有效: " + factorCode);
        }
        
        for (String factorCode : invalidFactorCodes) {
            assertFalse(ValidationUtils.isValidFactorCode(factorCode), 
                "因子代码格式应该无效: " + factorCode);
        }
    }

    @Test
    @DisplayName("测试权重验证")
    void testIsValidWeight() {
        // 准备测试数据
        double[] validWeights = {0.0, 0.1, 0.5, 1.0, 0.999};
        double[] invalidWeights = {-0.1, 1.1, 2.0, -1.0};
        
        // 执行测试并验证结果
        for (double weight : validWeights) {
            assertTrue(ValidationUtils.isValidWeight(weight), 
                "权重应该有效: " + weight);
        }
        
        for (double weight : invalidWeights) {
            assertFalse(ValidationUtils.isValidWeight(weight), 
                "权重应该无效: " + weight);
        }
    }

    @Test
    @DisplayName("测试权重数组验证")
    void testIsValidWeightArray() {
        // 准备测试数据
        double[] validWeightArray = {0.4, 0.3, 0.3};
        double[] invalidWeightArray1 = {0.4, 0.3, 0.4}; // 和不等于1
        double[] invalidWeightArray2 = {0.4, -0.1, 0.7}; // 包含负数
        double[] invalidWeightArray3 = {0.4, 1.2, -0.6}; // 包含大于1的数
        
        // 执行测试并验证结果
        assertTrue(ValidationUtils.isValidWeightArray(validWeightArray), 
            "有效权重数组应该返回true");
        assertFalse(ValidationUtils.isValidWeightArray(invalidWeightArray1), 
            "权重和不等于1的数组应该返回false");
        assertFalse(ValidationUtils.isValidWeightArray(invalidWeightArray2), 
            "包含负数的权重数组应该返回false");
        assertFalse(ValidationUtils.isValidWeightArray(invalidWeightArray3), 
            "包含大于1的权重的数组应该返回false");
    }

    @Test
    @DisplayName("测试边界条件-空数组")
    void testIsValidWeightArrayWithEmptyArray() {
        // 准备测试数据
        double[] emptyArray = {};
        
        // 执行测试并验证结果
        assertFalse(ValidationUtils.isValidWeightArray(emptyArray), 
            "空数组应该返回false");
    }

    @Test
    @DisplayName("测试边界条件-null数组")
    void testIsValidWeightArrayWithNullArray() {
        // 执行测试并验证结果
        assertFalse(ValidationUtils.isValidWeightArray(null), 
            "null数组应该返回false");
    }
}

/**
 * ValidationUtils工具类
 * 提供各种数据验证方法
 */
class ValidationUtils {
    
    /**
     * 验证邮箱格式
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailRegex);
    }
    
    /**
     * 验证手机号格式
     */
    public static boolean isValidPhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            return false;
        }
        String phoneRegex = "^1[3-9]\\d{9}$";
        return phone.matches(phoneRegex);
    }
    
    /**
     * 验证字符串非空
     */
    public static boolean isNotEmpty(String str) {
        return str != null && str.length() > 0;
    }
    
    /**
     * 验证字符串非空且非空白
     */
    public static boolean isNotBlank(String str) {
        return str != null && str.trim().length() > 0;
    }
    
    /**
     * 验证数字是否在指定范围内
     */
    public static boolean isInRange(int value, int min, int max) {
        return value >= min && value <= max;
    }
    
    /**
     * 验证字符串长度是否在指定范围内
     */
    public static boolean isValidLength(String str, int minLength, int maxLength) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        return length >= minLength && length <= maxLength;
    }
    
    /**
     * 验证基金代码格式
     */
    public static boolean isValidFundCode(String fundCode) {
        if (fundCode == null || fundCode.trim().isEmpty()) {
            return false;
        }
        String fundCodeRegex = "^\\d{6}$";
        return fundCode.matches(fundCodeRegex);
    }
    
    /**
     * 验证因子代码格式
     */
    public static boolean isValidFactorCode(String factorCode) {
        if (factorCode == null || factorCode.trim().isEmpty()) {
            return false;
        }
        String factorCodeRegex = "^F\\d{3}$";
        return factorCode.matches(factorCodeRegex);
    }
    
    /**
     * 验证权重值
     */
    public static boolean isValidWeight(double weight) {
        return weight >= 0.0 && weight <= 1.0;
    }
    
    /**
     * 验证权重数组
     */
    public static boolean isValidWeightArray(double[] weights) {
        if (weights == null || weights.length == 0) {
            return false;
        }
        
        double sum = 0.0;
        for (double weight : weights) {
            if (!isValidWeight(weight)) {
                return false;
            }
            sum += weight;
        }
        
        // 权重和应该接近1.0（允许小的浮点误差）
        return Math.abs(sum - 1.0) < 0.0001;
    }
} 