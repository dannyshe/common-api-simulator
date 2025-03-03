package com.payment.simulator.common.ip;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

/**
 * 描述信息
 *
 * 
 * @createTime 2021-11-01
 */
public class IPUtils {

    private static final String IP_UNKNOWN = "unknown";

    /**
     * #func 获取IP地址<br>
     * #desc 不再简单getRemoteAddr
     */
    public static String getIpAddr(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || IP_UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || IP_UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || IP_UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        // 取X-Forwarded-For中第一个非unknown的有效IP字符串。
        if (ip.indexOf(",") != -1) {
            String[] ipList = ip.split(",");
            String tmp;
            for (int i = 0; i < ipList.length; i++) {
                tmp = ipList[i];
                if (tmp != null && !IP_UNKNOWN.equalsIgnoreCase(tmp.trim())) {
                    return tmp.trim();
                }
            }
        }
        return ip;
    }

    /**
     * @func 获得本机的机器名称，用来从配置文件中排除本机
     * @desc 在此添加实现相关说明
     * @version 4.0.0.0
     */
    public static String getLocalHostName() {
        try {
            InetAddress addr = InetAddress.getLocalHost();
            return addr.getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * #func 返回主机名的全限定域名<br>
     *
     * @version 4.0.0
     */
    public static String getFullyLocalHostName() {
        String hostName = null;
        try {
            InetAddress inet = InetAddress.getLocalHost();
            hostName = inet.getCanonicalHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return hostName;
    }

    public static String getBrowserName(HttpServletRequest request) {
        //得到用户的浏览器名
        String userAgent = request.getHeader("User-Agent").toLowerCase();
        StringTokenizer st = new StringTokenizer(userAgent, ";");
        return st.nextToken();
    }

    /**
     * #func 返回本机IP<br>
     *
     * @version 4.0.0
     */
    public static String getLocalHostAddress() {
        try {
            InetAddress inet = InetAddress.getLocalHost();
            String hostAddress = inet.getHostAddress();
            return hostAddress;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * #func 判断本机是否和传入的域名一致<br>
     *
     * @version 4.0.0
     */
    public static boolean isDomainEqualsLocal(String domainName) {
        if (StringUtils.isBlank(domainName)) {
            return false;
        }
        try {
            InetAddress host = InetAddress.getByName(domainName);
            String domainAddress = host.getHostAddress();
            if (domainAddress.equals(getLocalHostAddress())) {
                return true;
            } else {
                return false;
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return false;
    }
}
