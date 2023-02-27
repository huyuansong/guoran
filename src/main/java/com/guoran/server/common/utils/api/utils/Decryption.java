package com.guoran.server.common.utils.api.utils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

/**
 * ������
 *
 * @author liyang
 */
public class Decryption {

    // RSA���������Ĵ�С
    private static final int MAX_DECRYPT_BLOCK = 128;

    /**
     * symDecrypt �Գƽ���
     *
     * @param strkey �Գ���Կ
     * @param src    ����
     * @return ԭ��
     * @throws IOException
     * @throws Exception
     */
    public static String symDecrypt(String strkey, String src) throws Exception {

        String target = null;

        try {
            Key key = KeysFactory.getSymKey(strkey);
            // ����
            Cipher cipher = Cipher.getInstance(CipherConstant.ALGORITHM_AES);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decodeResult = cipher.doFinal(Base64Util.decryptBASE64(src));
            target = new String(decodeResult);

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
            e.printStackTrace();
            throw new Exception("����ʧ��" + e.getMessage());
        }

        return target;
    }

    /**
     * priDecrypt ˽Կ����
     *
     * @param priKey ˽Կ
     * @param src    ����
     * @return ԭ��
     * @throws IOException
     * @throws Exception
     */
    public static String priDecrypt(String priKey, String src) throws Exception {

        String target = null;
        ByteArrayOutputStream out = null;
        try {
            Key key = KeysFactory.getPrivateKey(priKey);

            // ����
            Cipher cipher = Cipher.getInstance(CipherConstant.ALGORITHM_RSA);
            cipher.init(Cipher.DECRYPT_MODE, key);
            // byte[] decodeResult = cipher.doFinal(src.getBytes(CHARSET));
            // target = new String(decodeResult);

            byte[] data = Base64Util.decryptBASE64(src);
            int inputLen = data.length;
            out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // �����ݷֶν���
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                    cache = cipher.doFinal(data, offSet, MAX_DECRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_DECRYPT_BLOCK;
            }
            target = new String(out.toByteArray());

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
