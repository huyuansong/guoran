package com.guoran.server.common.utils.utils.api.utils;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Key������
 *
 * @author liyang
 */
public class KeysFactory {

    /**
     * buildAsymKey ����һ��ǶԳ���Կ
     *
     * @return KeyPair key��PublicKey��PrivateKey
     * @throws NoSuchAlgorithmException
     */
    public static KeyPairs buildAsymKey() throws Exception {

        /* ��ʼ����Կ������ */
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(CipherConstant.ALGORITHM_RSA);
        keyPairGenerator.initialize(1024, new SecureRandom());

        /* ������Կ */
        return new KeyPairs(keyPairGenerator.generateKeyPair());
    }

    /**
     * buildAsymKey ����һ���Գ���Կ
     *
     * @return �Գ���Կ
     * @throws NoSuchAlgorithmException
     * @throws Exception
     */
    public static String buildSymKey() throws Exception {
        // ����Key
        KeyGenerator keyGenerator = KeyGenerator.getInstance(CipherConstant.ALGORITHM_AES);

        keyGenerator.init(256, new SecureRandom());
        // ʹ���������ֳ�ʼ�����������ض�������������Կ���������ܺ��������Ψһ�̶��ġ�
        SecretKey secretKey = keyGenerator.generateKey();

        return Base64Util.encryptBASE64(secretKey.getEncoded());

    }

    public static Key getPublicKey(String pubKey) throws Exception {
        Key key = null;

        try {
            byte[] keyBytes = Base64Util.decryptBASE64(pubKey);
            KeyFactory keyFactory = KeyFactory.getInstance(CipherConstant.ALGORITHM_RSA);

            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
            key = keyFactory.generatePublic(x509KeySpec);

        } catch (Exception e) {
            throw new Exception("��Ч����Կ  " + e.getMessage());
        }

        return key;
    }

    public static Key getPrivateKey(String priKey) throws Exception {
        Key key = null;

        try {
            byte[] keyBytes = Base64Util.decryptBASE64(priKey);

            KeyFactory keyFactory = KeyFactory.getInstance(CipherConstant.ALGORITHM_RSA);

            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
            key = keyFactory.generatePrivate(pkcs8KeySpec);

        } catch (Exception e) {
            throw new Exception("��Ч��Կ " + e.getMessage());
        }

        return key;
    }

    public static Key getSymKey(String symKey) throws Exception {
        Key key = null;

        try {
            byte[] keyBytes = Base64Util.decryptBASE64(symKey);
            // Keyת��
            key = new SecretKeySpec(keyBytes, CipherConstant.ALGORITHM_AES);
        } catch (Exception e) {
            throw new Exception("��Ч��Կ " + e.getMessage());
        }

        return key;
    }
}
