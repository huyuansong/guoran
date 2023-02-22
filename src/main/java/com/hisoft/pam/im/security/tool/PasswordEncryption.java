package com.hisoft.pam.im.security.tool;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * @description: 加密类
 * @author: machao
 * @create: 2019-12-09 14:10
 * @Modify By
 **/
public class PasswordEncryption {
    private final static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    /**
     * 字符串加密
     * @param aPlainTextValue
     * @return
     */
    public static String encryptedValue(String aPlainTextValue) {
        Assert.notNull(aPlainTextValue, ()->"不能为null.");
        Assert.isTrue(!"".equals(aPlainTextValue), ()->"不能为空.");
        String encryptedValue = null;
        try {

            /*MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(aPlainTextValue.getBytes("UTF-8"));
            BigInteger bigInt = new BigInteger(1, messageDigest.digest());
            encryptedValue = bigInt.toString(16);*/
            encryptedValue = encoder.encode(aPlainTextValue);


        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        return encryptedValue;
    }
}
