package com.payment.simulator.common.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.commons.lang3.StringUtils;

import com.payment.simulator.common.exception.ErrorCode;
import com.payment.simulator.common.exception.PaymentException;
import com.payment.simulator.common.enums.PayCurrency;

/**
 * 描述信息
 *
 * @author Grayson
 * @createTime 2021-11-01
 */
public class BigDecimalUtils {


    /** 计算过程中精度，6位 */
    public static final int MIDDLE_PROCESS_PERCISION = 6;
    /** 默认四舍五入规则 */
    public static final RoundingMode DEFAULT_ROUNDING = RoundingMode.HALF_EVEN;

    public static BigDecimal currencyParse(Long amount, String payCurrency) {
        AssertUtils.notNull(amount, "金额");
        PayCurrency currency = PayCurrency.valueOf(payCurrency);
        BigDecimal currencyAmount =
                new BigDecimal(amount).divide(new BigDecimal(10).pow(currency.getReservedDecimalNum()))
                        .setScale(currency.getReservedDecimalNum());
        return currencyAmount;
    }

    /**
     * 将本地的金额转化为对应国际货币
     * 
     * @param value1
     * @param value2
     * @return
     */
    public static boolean isEquals(BigDecimal value1, BigDecimal value2) {
        AssertUtils.notNull(value1, "金额");
        AssertUtils.notNull(value2, "金额");
        if (value1.compareTo(value2) == 0) {
            return true;
        }
        return false;
    }

    /**
     * 将国际金额转成本地无小数金额
     * 
     * @param amount
     * @param payCurrency
     * @return
     */
    public static BigDecimal currencyParseLocal(String amount, String payCurrency) {
        AssertUtils.notNull(amount, "金额");
        PayCurrency currency = PayCurrency.valueOf(payCurrency);
        BigDecimal currencyAmount =
                (new BigDecimal(amount)).multiply((new BigDecimal(10)).pow(currency.getReservedDecimalNum()))
                        .setScale(currency.getReservedDecimalNum(), DEFAULT_ROUNDING);
        return currencyAmount;
    }

    /**
     * 连乘
     *
     * @param decimals
     * @return
     */
    public static BigDecimal multiply(PayCurrency payCurrency, BigDecimal... decimals) {
        return multiply(payCurrency.getReservedDecimalNum(), decimals);
    }

    /**
     * 连乘
     *
     * @param scale
     * @param decimals
     * @return
     */
    public static BigDecimal multiply(int scale, BigDecimal... decimals) {
        BigDecimal result = BigDecimal.ONE;
        for (BigDecimal decimal : decimals) {
            result = result.multiply(decimal);
        }
        return result.setScale(scale, DEFAULT_ROUNDING);
    }

    /**
     * 计算过程除,保留6位小数
     *
     * @param decimal 总数
     * @param divisor 除数
     * @return
     */
    public static BigDecimal divide(BigDecimal decimal, BigDecimal divisor) {
        return decimal.divide(divisor, BigDecimalUtils.MIDDLE_PROCESS_PERCISION, BigDecimalUtils.DEFAULT_ROUNDING);
    }

    /**
     * 如果expr1不是NULL，ifNull()返回expr1，否则它返回expr2
     *
     * @param expr1
     * @param expr2
     * @return
     */
    public static BigDecimal ifNull(BigDecimal expr1, BigDecimal expr2) {
        if (expr1 == null) {
            return expr2;
        }
        return expr1;
    }

    /**
     * 如果expr1不是NULL，ifNull()返回expr1，否则它返回0
     *
     * @param expr1
     * @return
     */
    public static BigDecimal ifNullDefaultZero(BigDecimal expr1) {
        return ifNull(expr1, BigDecimal.ZERO);
    }

    /**
     * 相加［如果为空，则按０处理］
     *
     * @param expr1
     * @param expr2
     * @return
     */
    public static BigDecimal add(BigDecimal expr1, BigDecimal expr2) {
        return ifNullDefaultZero(expr1).add(ifNullDefaultZero(expr2));
    }

    /**
     * 连加
     *
     * @param decimals
     * @return
     */
    public static BigDecimal add(BigDecimal... decimals) {
        BigDecimal result = BigDecimal.ZERO;
        for (BigDecimal decimal : decimals) {
            result = result.add(ifNullDefaultZero(decimal));
        }
        return result;
    }

    /**
     * 相减［如果为空，则按０处理］
     *
     * @param expr1
     * @param expr2
     * @return
     */
    public static BigDecimal subtract(BigDecimal expr1, BigDecimal expr2) {
        return ifNullDefaultZero(expr1).subtract(ifNullDefaultZero(expr2));
    }

    public static BigDecimal parseBigDecimal(String decimal, String paras) {
        try {
            return new BigDecimal(decimal);
        } catch (Exception e) {
            throw new PaymentException(ErrorCode.PARAM_ERROR);
        }
    }

    public static BigDecimal parseBigDecimalWithBlankZero(String decimal, String paras) {
        try {
            if (StringUtils.isBlank(decimal)) {
                return BigDecimal.ZERO;
            }
            return new BigDecimal(decimal);
        } catch (Exception e) {
            throw new PaymentException(ErrorCode.PARAM_ERROR);
        }
    }
}
