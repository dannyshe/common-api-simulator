//package com.artbite008.common.encry;
//
//import java.io.IOException;
//
///**
// * 描述信息
// *
// * @author Danny She
// * @createTime 2021-11-01
// */
//public class Base64 {
//
//    public static String encode(byte[] data) {
//        return new sun.misc.BASE64Encoder().encode(data);
//    }
//
//    /**
//     * Decodes the given Base64 encoded String to a new byte array. The byte
//     * array holding the decoded data is returned.
//     */
//    public static byte[] decode(String s) {
//        try {
//          return new sun.misc.BASE64Decoder().decodeBuffer(s);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//}
