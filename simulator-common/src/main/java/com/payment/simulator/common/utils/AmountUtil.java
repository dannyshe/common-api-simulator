package com.payment.simulator.common.utils;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 描述信息
 *
 * @author Danny She
 * @createTime 2021-11-01
 */
public abstract class AmountUtil {

	private AmountUtil() {

	}

	/**
	 * 加法运算
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static long add(long v1, long v2) {
		BigInteger b1 = new BigInteger(Long.toString(v1));
		BigInteger b2 = new BigInteger(Long.toString(v2));
		return b1.add(b2).longValue();
	}

	/**
	 * 减法运算
	 * 
	 * @param v1 被减数
	 * @param v2 减数
	 * @return
	 */
	public static long sub(long v1, long v2) {
		BigInteger b1 = new BigInteger(Long.toString(v1));
		BigInteger b2 = new BigInteger(Long.toString(v2));
		return b1.subtract(b2).longValue();
	}

	/**
	 * 乘法运算
	 * 
	 * @param v1 被乘数
	 * @param v2 乘数
	 * @return
	 */
	public static long mul(long v1, long v2) {
		BigInteger b1 = new BigInteger(Long.toString(v1));
		BigInteger b2 = new BigInteger(Long.toString(v2));
		return b1.multiply(b2).longValue();
	}

	/**
	 * 
	 * 除法运算，当发生除不尽的情况时，精确到小数点以后2位，以后的数字四舍五入
	 * 
	 * @param v1 被除数
	 * @param v2 除数
	 * @return
	 */
	public static long div(long v1, long v2) {
		return div(v1, v2, 2);
	}

	/**
	 * 
	 * 除法运算，当发生除不尽的情况时，由scale参数指定精度，以后的数字四舍五入
	 * 
	 * @param v1 被除数
	 * @param v2 除数
	 * @param scale 精确到小数点以后几位
	 * @return
	 */
	public static long div(long v1, long v2, int scale) {

		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigInteger b1 = new BigInteger(Long.toString(v1));
		BigInteger b2 = new BigInteger(Long.toString(v2));
		return b1.divide(b2).longValue();
	}


	/**
	 * 判断 a 是否大于等于 b
	 * 
	 * @param a
	 * @param b
	 * @return a&gt;=b 返回true, a&lt;b 返回false
	 */
	public static boolean greaterThanOrEqualTo(long a, long b) {
		BigInteger v1 = BigInteger.valueOf(a);
		BigInteger v2 = BigInteger.valueOf(b);
		if (v1.compareTo(v2) >= 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断 a 是否大于 b
	 * 
	 * @param a
	 * @param b
	 * @return a&gt;b 返回true, a&lt;=b 返回 false
	 */
	public static boolean bigger(long a, long b) {
		BigInteger v1 = BigInteger.valueOf(a);
		BigInteger v2 = BigInteger.valueOf(b);
		if (v1.compareTo(v2) > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断 a 是否小于 b
	 * 
	 * @param a
	 * @param b
	 * @return a&lt;b 返回true, a&gt;=b 返回 false
	 */
	public static boolean lessThan(long a, long b) {
		BigInteger v1 = BigInteger.valueOf(a);
		BigInteger v2 = BigInteger.valueOf(b);
		if (v1.compareTo(v2) < 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 金额格式化（默认保留两位小数）
	 */
	public static BigDecimal fenToYuan(Long amount,int scale) {
		BigDecimal b1 = new BigDecimal(Long.toString(amount));
		BigDecimal b2 = new BigDecimal(Long.toString(100));

		BigDecimal result = b1.divide(b2).setScale(scale, BigDecimal.ROUND_HALF_UP);
		return  result;

	}

}
