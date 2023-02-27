package com.guoran.server.common.assertor;

import com.guoran.server.common.exception.ServiceException;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * @description: 自定义断言
 * @author: machao
 * @create: 2019-12-11 18:02
 * @Modify By
 **/
public class CustomeAssert extends Assert {
    /**
     * 判断字符串是否为空或为null
     *
     * @param str
     */
    public static void isEmpty(@Nullable Object str, String erroCode, String message) {
        if (StringUtils.isEmpty(str)) {
            ServiceException se = new ServiceException(erroCode, message);
            throw se;
        }
    }
}
