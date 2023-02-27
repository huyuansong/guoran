package com.guoran.server.generator;


import com.guoran.server.common.exception.ServiceException;

/**
 * @description: 编号生成策略接口
 * @author: machao
 * @create: 2019-12-11 14:03
 * @Modify By
 **/
public interface CodeGenerator {
    /**
     * 获取先决条件
     *
     * @return
     */
    String getPre();

    /**
     * 设置先决条件
     *
     * @return
     */
    void setPre(String pre);

    /**
     * 生成编号
     *
     * @return
     */
    String generateCode() throws ServiceException;
}
