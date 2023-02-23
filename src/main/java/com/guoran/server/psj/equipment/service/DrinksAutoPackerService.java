package com.guoran.server.psj.equipment.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hisoft.pam.im.equipment.model.DrinksAutoPacker;

/**
 * 设备管理-设备运行记录-饮料车间-自动包装机运行记录数据访问层
 */
public interface DrinksAutoPackerService extends IService<DrinksAutoPacker> {

    int deleteByIds(String ids);
}
