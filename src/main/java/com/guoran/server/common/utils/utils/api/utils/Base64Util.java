package com.guoran.server.common.utils.utils.api.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;

/**
 * Base64������
 *
 * @author liyang
 */
public class Base64Util {

    /**
     * Base64����
     */
    public static String encryptBASE64(byte[] key) {
        return (new BASE64Encoder()).encodeBuffer(key);
    }

    /**
     * Base64����
     *
     * @throws IOException
     */
    public static byte[] decryptBASE64(String key) throws IOException {
        return (new BASE64Decoder()).decodeBuffer(key);
    }

}
