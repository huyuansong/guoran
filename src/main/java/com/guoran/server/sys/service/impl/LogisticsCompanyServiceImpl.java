package com.guoran.server.sys.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.guoran.server.common.JsonResult;
import com.guoran.server.common.exception.ServiceException;
import com.guoran.server.common.search.DynamicSearch;
import com.guoran.server.common.search.FilterGroup;
import com.guoran.server.common.search.PageQuery;
import com.guoran.server.common.utils.StringUtils;
import com.guoran.server.security.JwtUserUtil;
import com.guoran.server.sys.a.repository.LogisticsCompanyRepository;
import com.guoran.server.sys.model.LogisticsCompanyEntity;
import com.guoran.server.sys.service.LogisticsCompanyService;
import com.guoran.server.sys.vmodel.LogisticsCompanyVM;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 物流公司基本信息 服务实现类
 * </p>
 *
 * @author zhangjx
 * @create 2020-09-02
 * @Modify By
 */
@Service
public class LogisticsCompanyServiceImpl implements LogisticsCompanyService {
    @Autowired
    JwtUserUtil jwtUserUtil;
    @Autowired
    LogisticsCompanyRepository logisticsCompanyRepository;

    /**
     * 根据id获取
     *
     * @param id
     * @return
     */
    @Override
    public LogisticsCompanyVM getEntryBy(long id) {
        LogisticsCompanyEntity logisticsCompanyEntity = logisticsCompanyRepository.findById(id);
        LogisticsCompanyVM logisticsCompanyVM = new LogisticsCompanyVM();
        BeanUtils.copyProperties(logisticsCompanyEntity, logisticsCompanyVM);
        return logisticsCompanyVM;
    }

    /**
     * 创建
     *
     * @param logisticsCompanyVM
     * @return entity的id
     */
    @Override
    public String createEntry(LogisticsCompanyVM logisticsCompanyVM) {
        String logisticsName = logisticsCompanyVM.getLogisticsName();
        if (StringUtils.isEmpty(logisticsName)) {
            return JsonResult.failed("物流公司名称不能为空");
        }
        LogisticsCompanyEntity entity = logisticsCompanyRepository.findByName(logisticsName);
        if (entity != null) {
            return JsonResult.failed("公司名称重复");
        }
        LogisticsCompanyEntity logisticsCompanyEntity = new LogisticsCompanyEntity();
        BeanUtils.copyProperties(logisticsCompanyVM, logisticsCompanyEntity);
        logisticsCompanyEntity.setCreateBy(jwtUserUtil.getUserName());
        logisticsCompanyEntity.setCreateTime(new Date());
        logisticsCompanyRepository.insert(logisticsCompanyEntity);
        return null;
    }

    /**
     * 修改
     *
     * @param logisticsCompanyVM
     * @return 是否成功
     */
    @Override
    public String updateEntry(LogisticsCompanyVM logisticsCompanyVM) throws ServiceException {
        String logisticsName = logisticsCompanyVM.getLogisticsName();
        LogisticsCompanyEntity logisticsCompanyEntity = logisticsCompanyRepository.findById(logisticsCompanyVM.getId());
        //比较两次公司名称是否一致
        if (!logisticsName.equals(logisticsCompanyEntity.getLogisticsName())) {
            LogisticsCompanyEntity entity = logisticsCompanyRepository.findByName(logisticsName);
            if (entity != null) {
                return JsonResult.failed("公司名称重复");
            }
        }
        logisticsCompanyEntity.failWhenConcurrencyViolation(logisticsCompanyVM.getConcurrencyVersion());
        BeanUtils.copyProperties(logisticsCompanyVM, logisticsCompanyEntity);
        logisticsCompanyEntity.setUpdateBy(jwtUserUtil.getUserName());
        logisticsCompanyEntity.setUpdateTime(new Date());
        logisticsCompanyRepository.update(logisticsCompanyEntity);
        return null;
    }

    /**
     * 删除
     *
     * @param id
     * @return 是否成功
     */
    @Override
    public boolean deleteById(long id) {
        return logisticsCompanyRepository.deleteById(id);
    }

    /**
     * 分页
     *
     * @param pageQuery
     * @return
     */
    @Override
    public Page<LogisticsCompanyVM> findEntrysByPage(PageQuery pageQuery) {
        FilterGroup filterGroup = pageQuery.getWhere();
        //自动转字符串
        String where = DynamicSearch.getInstance().buildWhere(filterGroup);
        PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize(), pageQuery.getOrderBy());
        return logisticsCompanyRepository.findEntrysByPage(where);
    }
}
