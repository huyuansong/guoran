package com.guoran.server.weChat.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.guoran.server.common.exception.ServiceException;
import com.guoran.server.common.search.DynamicSearch;
import com.guoran.server.common.search.FilterGroup;
import com.guoran.server.common.search.PageQuery;
import com.guoran.server.security.JwtUserUtil;
import com.guoran.server.weChat.a.repository.CaptchaRepository;
import com.guoran.server.weChat.model.CaptchaEntity;
import com.guoran.server.weChat.service.CaptchaService;
import com.guoran.server.weChat.vmodel.CaptchaVM;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 企业微信推荐验证码及token临时存储表 服务实现类
 * </p>
 *
 * @author zhangjx
 * @create 2020-09-21
 * @Modify By
 */
@Service
public class CaptchaServiceImpl implements CaptchaService {
    @Autowired
    JwtUserUtil jwtUserUtil;
    @Autowired
    CaptchaRepository captchaRepository;

    /**
     * 根据id获取
     *
     * @param id
     * @return
     */
    @Override
    public CaptchaVM getEntryBy(long id) {
        CaptchaEntity captchaEntity = captchaRepository.findById(id);
        CaptchaVM captchaVM = new CaptchaVM();
        BeanUtils.copyProperties(captchaEntity, captchaVM);
        return captchaVM;
    }

    /**
     * 创建
     *
     * @param captchaVM
     * @return entity的id
     */
    @Override
    public String createEntry(CaptchaVM captchaVM) {
        CaptchaEntity captchaEntity = new CaptchaEntity();
        BeanUtils.copyProperties(captchaVM, captchaEntity);
        captchaEntity.setCreateBy(jwtUserUtil.getUserName());
        captchaEntity.setCreateTime(new Date());
        captchaRepository.insert(captchaEntity);
        return null;
    }

    /**
     * 修改
     *
     * @param captchaVM
     * @return 是否成功
     */
    @Override
    public String updateEntry(CaptchaVM captchaVM) throws ServiceException {
        CaptchaEntity captchaEntity = captchaRepository.findById(captchaVM.getId());
        captchaEntity.failWhenConcurrencyViolation(captchaVM.getConcurrencyVersion());
        BeanUtils.copyProperties(captchaVM, captchaEntity);
        captchaEntity.setUpdateBy(jwtUserUtil.getUserName());
        captchaEntity.setUpdateTime(new Date());
        captchaRepository.update(captchaEntity);
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
        return captchaRepository.deleteById(id);
    }

    /**
     * 分页
     *
     * @param pageQuery
     * @return
     */
    @Override
    public Page<CaptchaVM> findEntrysByPage(PageQuery pageQuery) {
        FilterGroup filterGroup = pageQuery.getWhere();
        //自动转字符串
        String where = DynamicSearch.getInstance().buildWhere(filterGroup);
        PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize(), pageQuery.getOrderBy());
        return captchaRepository.findEntrysByPage(where);
    }
}
