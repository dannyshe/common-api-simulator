package com.payment.simulator.common.idgenerator;

/**
 * @author: ywainlan
 * @date: 2022/5/16
 * @Description:
 */

public class Base61 {
    private static final char[] DIGITS_CHAR = "0123456789abcdefghijklmnopqrstuvwxyABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private static final int BASE = DIGITS_CHAR.length;
    private static final int FAST_SIZE = 'y';
    private static final int[] DIGITS_INDEX = new int[FAST_SIZE + 1];
    static {
        for (int i = 0; i <= FAST_SIZE; i++) {
            DIGITS_INDEX[i] = -1;
        }
        for (int i = 0; i < BASE; i++) {
            DIGITS_INDEX[DIGITS_CHAR[i]] = i;
        }
    }
    public static String encode(long number) throws Exception {
        if (number < 0) throw new Exception("Number(Base61) must be positive:" + number);
        if (number == 0L) return "0";
        StringBuilder buf = new StringBuilder();
        while (number != 0L) {
            buf.append(DIGITS_CHAR[(int) (number % BASE)]);
            number /= BASE;
        }
        return buf.reverse().toString();
    }
    public static long decode(String s) throws Exception {
        long result = 0L;
        long multiplier = 1;
        for (int pos = s.length() - 1; pos >= 0 ; pos--) {
            int index = getIndex(s, pos);
            result += index * multiplier;
            multiplier *= BASE;
        }
        return result;
    }
    private static int getIndex(String s, int pos) throws Exception {
        char c = s.charAt(pos);
        if (c > FAST_SIZE) throw new Exception("Unknown character for Base61: " + s);
        int index = DIGITS_INDEX[c];
        if (index == -1) throw new Exception("Unknown character for Base61: " + s);
        return index;
    }
}
