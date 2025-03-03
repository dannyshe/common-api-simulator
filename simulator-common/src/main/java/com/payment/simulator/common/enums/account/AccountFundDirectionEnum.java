package com.payment.simulator.common.enums.account;

/**
 * 描述信息
 *
 * 
 * @createTime 2021-11-01
 */
public enum AccountFundDirectionEnum {

	/**
	 * 加款
	 */
	ADD("加款", 123),

	/**
	 * 减款
	 */
	SUB("减款", 321),

	/**
	 * 冻结
	 */
	FROZEN("冻结", 321),

	/**
	 * 解冻
	 */
	UNFROZEN("解冻", 123);

	/** 枚举值 */
	private int value;

	/** 描述 */
	private String desc;

	private AccountFundDirectionEnum(String desc, int value) {
		this.value = value;
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public int getValue() {
		return value;
	}


}
