package com.payment.simulator.common.utils;

import com.google.common.base.Joiner;
import com.payment.simulator.common.enums.PaymentSystem;
import com.payment.simulator.common.exception.ErrorCode;
import com.payment.simulator.common.exception.PaymentException;
import lombok.Setter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;

/**
 * 描述信息
 *
 * 
 * @createTime 2021-11-01
 */
@Setter
public class AssertUtils {

	private PaymentSystem paymentSystem;

	public void asserts(boolean boo, ErrorCode errorCode,String internalMessage, PaymentSystem paymentSystem) {
		if (!boo)
			throw new PaymentException(errorCode,internalMessage,paymentSystem);
	}

	public void asserts(boolean boo, ErrorCode errorCode, String internalMessage) {
		if (!boo)
			throw new PaymentException(errorCode,internalMessage,this.paymentSystem);
	}

	public void asserts(boolean boo, ErrorCode errorCode, PaymentSystem paymentSystem) {
		if (!boo)
			throw new PaymentException(errorCode,paymentSystem);
	}

	public void asserts(boolean boo, ErrorCode errorCode) {
		if (!boo)
			throw new PaymentException(errorCode,this.paymentSystem);
	}

	public static void notNull(Object obj, String description) {
		if (obj == null) {
			throw new PaymentException(ErrorCode.PARAM_MISS , Joiner.on(": ").join(ErrorCode.PARAM_MISS.getMsg(),description));
		}
	}

	public static void notBlank(String str, String description) {
		if (StringUtils.isBlank(str)) {
			throw new PaymentException(ErrorCode.PARAM_MISS , Joiner.on(":").join(ErrorCode.PARAM_MISS.getMsg(),description));
		}
	}

	public static void notEmpty(Collection<?> coll, String description) {
		if (CollectionUtils.isEmpty(coll)) {
			throw new PaymentException(ErrorCode.PARAM_MISS , Joiner.on(":").join(ErrorCode.PARAM_MISS.getMsg(),description));
		}
	}
	public static void notTrue(boolean result, String description) {
		if(!result){
			throw new PaymentException(ErrorCode.VALIDATE_ERROR, Joiner.on(":").join(ErrorCode.VALIDATE_ERROR.getMsg(), description));
		}
	}
}
