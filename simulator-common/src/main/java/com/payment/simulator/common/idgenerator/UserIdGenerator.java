package com.payment.simulator.common.idgenerator;

import org.apache.commons.lang3.RandomStringUtils;

public class UserIdGenerator {
    private static final String USER_ID_PREFIX = "usr_";
    private static final String SPACE = " ";

    public static String generateUserId(){
        String systemTime = String.valueOf(System.currentTimeMillis()); //长度13
        String postFix1 = String.valueOf((char)(int)(Math.random()*26+97)); //随机产生一个小写字母
        String postFix2 = String.valueOf((char)(int)(Math.random()*26+97)); //随机产生一个小写字母
        String postFix3 = String.valueOf((char)(int)(Math.random()*26+97)); //随机产生一个小写字母
        int index = systemTime.length()-1;
        StringBuilder idBuilder = new StringBuilder(systemTime);
        idBuilder.insert((int)(Math.random()*(index++)), postFix1); //进行随机混淆，防止用户猜出来id生成策略，提供系统安全性
        idBuilder.insert((int)(Math.random()*(index++)), postFix2); //进行随机混淆，防止用户猜出来id生成策略，提供系统安全性
        idBuilder.insert((int)(Math.random()*(index)), postFix3); //进行随机混淆，防止用户猜出来id生成策略，提供系统安全性
        return USER_ID_PREFIX + idBuilder.toString(); //要求前台用一个特殊的字体，方便用户区分字母O和数字0
    }

    public static String toReference(String userId){
        StringBuilder idBuilder = new StringBuilder(userId.substring(4));
        idBuilder.insert(4, SPACE);
        idBuilder.insert(9, SPACE);
        idBuilder.insert(14, SPACE);
        return idBuilder.toString().toUpperCase();
    }

    /**
     * 生成12位附言
     * @return
     */
    public static String genReference(){
        Long currentTime = System.currentTimeMillis();
        String hex= Long.toHexString(currentTime);
        String random = RandomStringUtils.randomAlphanumeric(1);
        int index = hex.length()-1;
        StringBuilder idBuilder = new StringBuilder(hex);
        idBuilder.insert((int)(Math.random()*(index++)), random);
        return idBuilder.toString().toUpperCase();
    }

    public static String toId(String reference){
        return USER_ID_PREFIX + reference.replaceAll(SPACE,"").toLowerCase();
    }


}
