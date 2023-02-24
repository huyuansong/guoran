package com.guoran.server.psj.equipment.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.guoran.server.common.search.DynamicSearch;
import com.guoran.server.common.search.FilterGroup;
import com.guoran.server.common.search.PageQuery;
import com.guoran.server.common.search.PageResult;
import com.guoran.server.psj.equipment.model.DrinksCansFillingEntity;
import com.guoran.server.psj.equipment.model.vto.DrinksCansFillingVM;
import com.guoran.server.psj.equipment.repository.DrinksCansFillingRepository;
import com.guoran.server.psj.equipment.service.DrinksCansFillingService;
import com.guoran.server.psj.equipment.utils.PageUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 设备管理-设备运行记录-饮料车间-易拉罐灌装记录 的数据库操作Service实现
 */
@Service
public class DrinksCansFillingServiceImpl extends ServiceImpl<DrinksCansFillingRepository, DrinksCansFillingEntity>
        implements DrinksCansFillingService {

    @Resource
    private DrinksCansFillingRepository drinksCansFillingRepository;

    @Override
    public DrinksCansFillingVM getEntryBy(long id) {
        DrinksCansFillingEntity drinksCansFillingEntity = drinksCansFillingRepository.selectById(id);
        if (drinksCansFillingEntity == null) {
            return null;
        }
        DrinksCansFillingVM drinksCansFillingVM = new DrinksCansFillingVM();
        BeanUtils.copyProperties(drinksCansFillingEntity, drinksCansFillingVM);
        return drinksCansFillingVM;
    }

    @Override
    public PageResult findEntrysByPage(PageQuery pageQuery) {
        FilterGroup filterGroup = pageQuery.getWhere();
        //自动转字符串
        String where = DynamicSearch.getInstance().buildWhere(filterGroup);
        Page<DrinksCansFillingVM> pageInfo = drinksCansFillingRepository.findEntrysByPage(where);
        return PageUtils.PageCopy(pageQuery, pageInfo);
    }


}




