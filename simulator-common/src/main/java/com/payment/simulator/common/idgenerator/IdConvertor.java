package com.payment.simulator.common.idgenerator;

import org.apache.commons.lang3.StringUtils;
/**
 * @date: 2022/5/16
 * @Description:
 */
public class IdConvertor {
    private static final int PREFIX_LENGTH = 4;
    private static final int LONG_ID_SECTION1_LENGTH = 14;
    // private static final char LONG_ID_SEPARATOR = '_';
    private static final char SHORT_ID_SEPARATOR = 'z';
    public static String toShortId(String id) throws Exception {
        if (id.length() < 5) throw new Exception("original id length must large than 4");
        int count = (id.length() - PREFIX_LENGTH) / LONG_ID_SECTION1_LENGTH;
        String prefix = id.substring(0, PREFIX_LENGTH);
        StringBuilder sb = new StringBuilder(prefix);
        String longId = id.substring(PREFIX_LENGTH);
        for (int i = 0; i < count; i++) {
            sb.append(Base61.encode(Long.parseLong(
                    reverse(longId.substring(i * LONG_ID_SECTION1_LENGTH, (i + 1) * LONG_ID_SECTION1_LENGTH))
            ))).append(SHORT_ID_SEPARATOR);
        }
        String others = reverse(longId.substring(count * LONG_ID_SECTION1_LENGTH));
        if (!StringUtils.isBlank(others)) sb.append(Base61.encode(Long.parseLong(others)));
        return sb.toString();
    }
    public static String toLongId(String shortId) throws Exception {
        if (shortId.isEmpty()) return shortId;
        if (shortId.length() < 5) throw new Exception("original id length must large than 4");
        String prefix = shortId.substring(0, PREFIX_LENGTH);
        String id = shortId.substring(PREFIX_LENGTH);
        String[] inputs = id.split(String.valueOf(SHORT_ID_SEPARATOR));
        StringBuilder sb = new StringBuilder(prefix);
        for (String input : inputs) {
            sb.append(reverse(String.format("%014d", Base61.decode(input))));
        }
        return sb.toString();
    }
    private static String reverse(String s) {
        return new StringBuilder(s).reverse().toString();
    }
}
