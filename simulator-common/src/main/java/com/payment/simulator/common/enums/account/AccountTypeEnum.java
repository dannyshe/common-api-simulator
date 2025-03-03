package com.payment.simulator.common.enums.account;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述信息
 *
 * @author Grayson
 * @createTime 2021-11-01
 */
public enum AccountTypeEnum {
	BALANCE("可提现户", 1),
	SETTLEMENT("待结算户", 2),
	MARGIN("保证金户", 3),
	DECLARABLE("可申报金额户",4),
	ARREARS("欠费金额户",5);

	/** 描述 */
	private String desc;
	/** 枚举值 */
	private int value;

	private AccountTypeEnum(String desc, int value) {
		this.desc = desc;
		this.value = value;
	}

	public int getValue() {
		return value;
	}


	public String getDesc() {
		return desc;
	}


	/**
	 * 根据枚举值获取枚举属性.
	 * 
	 * @param value
	 *            枚举值.
	 * @return enum 枚举属性.
	 */
	public static AccountTypeEnum getEnum(int value) {
		AccountTypeEnum resultEnum = null;
		AccountTypeEnum[] enumAry = AccountTypeEnum.values();
		for (int num = 0; num < enumAry.length; num++) {
			if (enumAry[num].getValue() == value) {
				resultEnum = enumAry[num];
				break;
			}
		}
		return resultEnum;
	}

	/**
	 * 将枚举类转换为map.
	 * 
	 * @return Map<key, Map<attr, value>>
	 */
	public static Map<String, Map<String, Object>> toMap() {
		AccountTypeEnum[] ary = AccountTypeEnum.values();
		Map<String, Map<String, Object>> enumMap = new HashMap<String, Map<String, Object>>();
		for (int num = 0; num < ary.length; num++) {
			Map<String, Object> map = new HashMap<String, Object>();
			String key = String.valueOf(getEnum(ary[num].getValue()));
			map.put("value", ary[num].getValue());
			map.put("desc", ary[num].getDesc());
			enumMap.put(key, map);
		}
		return enumMap;
	}

	/**
	 * 将枚举类转换为list.
	 * 
	 * @return List<Map<String, Object>> list.
	 */
	public static List<Map<String, Object>> toList() {
		AccountTypeEnum[] ary = AccountTypeEnum.values();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < ary.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("value", ary[i].getValue());
			map.put("desc", ary[i].getDesc());
			list.add(map);
		}
		return list;
	}
}
