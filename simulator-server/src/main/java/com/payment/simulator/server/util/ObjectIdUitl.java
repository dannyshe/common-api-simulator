package com.payment.simulator.server.util;

import java.security.SecureRandom;
import java.util.UUID;

public class ObjectIdUitl {
    public static String generateObjectId(String channelId) {
        return channelId + "_" + generateShortUUID();
    }

    public static String generateShortUUID() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[8];
        random.nextBytes(bytes);

        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b & 0xff));
        }
        return sb.toString();
    }

    public static String generateUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
