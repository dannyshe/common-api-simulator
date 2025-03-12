package com.common.api.simulator.common.utils;

import com.google.common.base.Joiner;
import com.common.api.simulator.common.exception.ErrorCode;
import com.common.api.simulator.common.exception.SimulateException;
import lombok.Setter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;

@Setter
public class AssertUtils {


	public static void notNull(Object obj, String description) {
		if (obj == null) {
			throw new SimulateException(ErrorCode.PARAM_MISS , Joiner.on(": ").join(ErrorCode.PARAM_MISS.getMsg(),description));
		}
	}

	public static void notBlank(String str, String description) {
		if (StringUtils.isBlank(str)) {
			throw new SimulateException(ErrorCode.PARAM_MISS , Joiner.on(":").join(ErrorCode.PARAM_MISS.getMsg(),description));
		}
	}

	public static void notEmpty(Collection<?> coll, String description) {
		if (CollectionUtils.isEmpty(coll)) {
			throw new SimulateException(ErrorCode.PARAM_MISS , Joiner.on(":").join(ErrorCode.PARAM_MISS.getMsg(),description));
		}
	}
	public static void notTrue(boolean result, String description) {
		if(!result){
			throw new SimulateException(ErrorCode.VALIDATE_ERROR, Joiner.on(":").join(ErrorCode.VALIDATE_ERROR.getMsg(), description));
		}
	}
}
