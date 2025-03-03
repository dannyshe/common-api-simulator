package com.payment.simulator.common.ip;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * IP工具类
 *
 * @author Grayson
 * @createTime 2021-11-01
 */
public class OSNewUtil {

    
    /** 本地IP */
    private static String LOCAL_IP = null;
    
    private static Logger logger = LoggerFactory.getLogger(OSNewUtil.class);

    /**
     * 判断是否为windows
     * 
     * @return
     */
    public static boolean isWindowsOS() {
        boolean isWindowsOS = false;
        String osName = System.getProperty("os.name");
        if (osName.toLowerCase().indexOf("windows") > -1) {
            isWindowsOS = true;
        }
        return isWindowsOS;
    }


    /**
     * 获取本地IP方式,查找eth0,如果没有，则使用127.0.0.1
     * @return
     */
    public static String getLocalIp(){
        if (LOCAL_IP == null) {
            LOCAL_IP = getLocalEth0IP();
        }
        return LOCAL_IP;
    }

    private static String getLocalEth0IP()  {
        String ip = null;
        try {
            if (isWindowsOS()) {
                return InetAddress.getLocalHost().getHostAddress();
            } else {
                Enumeration<?> e1 = (Enumeration<?>) NetworkInterface.getNetworkInterfaces();
                while (e1.hasMoreElements()) {
                    NetworkInterface ni = (NetworkInterface) e1.nextElement();
                    if (!ni.getName().equals("eth0")) {
                        continue;
                    } else {
                        Enumeration<?> e2 = ni.getInetAddresses();
                        while (e2.hasMoreElements()) {
                            InetAddress ia = (InetAddress) e2.nextElement();
                            if (ia instanceof Inet6Address) {
                                continue;
                            }
                            ip = ia.getHostAddress();
                        }
                        break;
                    }
                }
            }

        } catch (Exception e) {
            logger.error("获取IP有异常",e);
        }
        if(ip == null) {
            ip = "127.0.0.1";
            logger.error("cat not get eth0 ip");
        }
        logger.info("get eth0  ip  is :" + ip);
        return ip;
    }

    public static String getLocaleth1IP()  {
        String ip = null;
        try {
            if (isWindowsOS()) {
                return InetAddress.getLocalHost().getHostAddress();
            } else {
                Enumeration<?> e1 = (Enumeration<?>) NetworkInterface.getNetworkInterfaces();
                while (e1.hasMoreElements()) {
                    NetworkInterface ni = (NetworkInterface) e1.nextElement();
                    if (!ni.getName().equals("eth1")) {
                        continue;
                    } else {
                        Enumeration<?> e2 = ni.getInetAddresses();
                        while (e2.hasMoreElements()) {
                            InetAddress ia = (InetAddress) e2.nextElement();
                            if (ia instanceof Inet6Address) {
                                continue;
                            }
                            ip = ia.getHostAddress();
                        }
                        break;
                    }
                }
            }

        } catch (Exception e) {
            logger.error("获取IP有异常",e);
        }
        logger.info("get eth1  ip  is :" + ip);
        return ip;
    }


}
