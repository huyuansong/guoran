package com.guoran.server.common.utils.api.utils;

import java.security.KeyPair;

/**
 * KeyPairs ������
 *
 * @author liyang
 * @date: 2019-5-20����4:34:51
 */
public class KeyPairs {

    private KeyPair keyPair;

    public KeyPairs(KeyPair keyPair) {
        this.keyPair = keyPair;
    }

    public String getPublicKey() {
        return Base64Util.encryptBASE64(keyPair.getPublic().getEncoded());
    }

    public String getPrivateKey() {
        return Base64Util.encryptBASE64(keyPair.getPrivate().getEncoded());
    }
}
