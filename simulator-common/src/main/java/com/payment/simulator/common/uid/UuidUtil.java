package com.payment.simulator.common.uid;

import java.util.UUID;

public class UuidUtil {
	/**
	 * 生成32位随机串
	 * @return
	 */
	public static String generate32RandomStr() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32);
    }
}
