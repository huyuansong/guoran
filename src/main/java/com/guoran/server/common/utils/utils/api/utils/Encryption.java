package com.guoran.server.common.utils.utils.api.utils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

/**
 * ������
 *
 * @author liyang
 */
public class Encryption {

    // RSA���������Ĵ�С
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * symEncrypt �ԳƼ���
     *
     * @param strkey �Գ���Կ
     * @param src    ԭ��
     * @return ����
     * @throws BizException
     */
    public static String symEncrypt(String strkey, String src) throws Exception {
        String target = null;
        try {
            Key key = KeysFactory.getSymKey(strkey);
            // ����
            Cipher cipher = Cipher.getInstance(CipherConstant.ALGORITHM_AES);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encodeResult = cipher.doFinal(src.getBytes(StandardCharsets.UTF_8));
            target = Base64Util.encryptBASE64(encodeResult);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | UnsupportedEncodingException | InvalidKeyException | IllegalBlockSizeException
                | BadPaddingException e) {
            e.printStackTrace();
            throw new Exception("����ʧ��" + e.getMessage());
        }
        return target;
    }

    /**
     * pubEncrypt ��Կ����
     *
     * @param pubKey ��Կ
     * @param src    ԭ��
     * @return ����
     * @throws IOException
     * @throws Exception
     */
    public static String pubEncrypt(String pubKey, String src) throws Exception {
        String target = null;
        ByteArrayOutputStream out = null;
        try {
            Key key = KeysFactory.getPublicKey(pubKey);

            Cipher cipher = Cipher.getInstance(CipherConstant.ALGORITHM_RSA);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            // encodeResult = cipher.doFinal(src.getBytes());
            byte[] data = src.getBytes();
            int inputLen = data.length;
            out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // �����ݷֶμ���
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_ENCRYPT_BLOCK;
            }

            target = Base64Util.encryptBASE64(out.toByteArray());
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
            throw new Exception("����ʧ��" + e.getMessage());
        } finally {
            if (out != null) {
                out.close();
            }
        }
        return target;
    }
}
