package com.guoran.server.weChat.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.guoran.server.common.JsonResult;
import com.guoran.server.common.exception.ServiceException;
import com.guoran.server.common.search.DynamicSearch;
import com.guoran.server.common.search.FilterGroup;
import com.guoran.server.common.search.PageQuery;
import com.guoran.server.common.utils.EncodeUtil;
import com.guoran.server.common.utils.StringUtils;
import com.guoran.server.security.JwtUserUtil;
import com.guoran.server.sys.a.repository.EmployeeRepository;
import com.guoran.server.sys.model.EmployeeEntity;
import com.guoran.server.weChat.SendWX;
import com.guoran.server.weChat.WeChatMsgSend;
import com.guoran.server.weChat.a.repository.AccessTokenRepository;
import com.guoran.server.weChat.a.repository.CaptchaRepository;
import com.guoran.server.weChat.model.AccessTokenEntity;
import com.guoran.server.weChat.model.CaptchaEntity;
import com.guoran.server.weChat.service.AccessTokenService;
import com.guoran.server.weChat.vmodel.AccessTokenVM;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * <p>
 * 企业微信推荐验证码及token临时存储表 服务实现类
 * </p>
 *
 * @author zhangjx
 * @create 2020-09-16
 * @Modify By
 */
@Service
public class AccessTokenServiceImpl implements AccessTokenService {
    private static final String BASE_CHECK_CODES = "qwertyupkjhgfdsazxcvbnmQWERTYUPKJHGFDSAZXCVBNM23456789";
    protected final Log logger = LogFactory.getLog(this.getClass());
    @Autowired
    JwtUserUtil jwtUserUtil;
    @Autowired
    WeChatMsgSend weChatMsgSend;
    @Autowired
    AccessTokenRepository accessTokenRepository;
    @Autowired
    CaptchaRepository captchaRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    /*    @Value("${weChat.corpid}")*/
    private String corpid;
    /*   @Value("${weChat.corpsecret}")*/
    private String corpsecret;
    /*    @Value("${weChat.msgtype}")*/
    private String msgtype;
    /*    @Value("${weChat.contentKey}")*/
    private String contentKey;
    /*    @Value("${weChat.applicationId}")*/
    private String applicationId;
    /*    @Value("${weChat.contentValue}")*/
    private String contentValue;
    /*    @Value("${weChat.contentValue2}")*/
    private String contentValue2;
    /*  @Value("${weChat.url}")*/
    private String url;
    /* @Value("${weChat.sendMessageUrl}")*/
    private String sendMessageUrl;

    @Override
    public AccessTokenEntity find() {
        return accessTokenRepository.find();
    }

    /**
     * 获取Token
     *
     * @return
     * @throws IOException
     */
    @Override
    public String getToken() throws IOException {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
//        String token = weChatMsgSend.getToken(request.getHeader(this.corpid),request.getHeader(this.corpsecret));
        return null;
    }

    /**
     * 获取Token
     *
     * @return
     * @throws IOException
     */
    private String getAccessToken() throws IOException {
        String token = null;
        //发消息分为以下几步：
        //1.获取token
        AccessTokenEntity entity = accessTokenRepository.find();
        if (entity == null) {//第一次新增加
            //刷新token
            token = SendWX.getToken(url, corpid, corpsecret);
            if (StringUtils.isEmpty(token)) {
                return JsonResult.failed("获取token失败！");
            } else {
                //保存token
                entity = new AccessTokenEntity();
                entity.setToken(token);
                //获取当前时间+两个小时
                long currentTime = System.currentTimeMillis();
                currentTime += 120 * 60 * 1000;
                Date date = new Date(currentTime);
                entity.setTokenExpiresIn(date.getTime());
                entity.setCreateBy("");
                entity.setCreateTime(new Date());
                accessTokenRepository.insert(entity);
                return token;
            }
        } else {
            //系统已经有Token,获取是否在有效期内
            Long tokenExpiresIn = entity.getTokenExpiresIn();
            //比较系统token有效时间
            if (tokenExpiresIn < new Date().getTime()) {
                //刷新Token
                token = SendWX.getToken(url, corpid, corpsecret);
                if (StringUtils.isEmpty(token)) {
                    return JsonResult.failed("获取token失败！");
                } else {
                    //保存token
                    entity.setToken(token);
                    //获取当前时间+两个小时
                    long currentTime = System.currentTimeMillis();
                    currentTime += 120 * 60 * 1000;
                    Date date = new Date(currentTime);
                    entity.setTokenExpiresIn(date.getTime());
                    entity.setUpdateBy("");
                    entity.setUpdateTime(new Date());
                    accessTokenRepository.update(entity);
                    return token;
                }
            } else {
                return entity.getToken();
            }
        }
    }


    /**
     * 发送验证码到企业微信指定账号
     *
     * @param userId
     * @return
     */
    public String send(String userId) throws IOException {

        try {
            EmployeeEntity byJobNumber = employeeRepository.findByJobNumber(userId);
            if (byJobNumber == null) {
                return "员工工号不存在，请确认工号是否正确！";
            }
        } catch (Exception e) {
            return "发生错误，请确认工号是否正确！";
        }


        //1.获取token
        String token = this.getAccessToken();
        //2.组织推送内容（消息头，验证码）
        String code = RandomUtil.randomString(BASE_CHECK_CODES, 6);
        CaptchaEntity captchaEntity = captchaRepository.findByUserId(userId);
        if (captchaEntity == null) {
            captchaEntity = new CaptchaEntity();
            captchaEntity.setUserId(userId);
            captchaEntity.setCaptcha(code);
            captchaEntity.setCaptchaExpiresIn(new Date().getTime());
            captchaEntity.setCreateBy("");
            captchaEntity.setCreateTime(new Date());
            captchaRepository.insert(captchaEntity);
        } else {
            captchaEntity.setUserId(userId);
            captchaEntity.setCaptcha(code);
            captchaEntity.setCaptchaExpiresIn(new Date().getTime());
            captchaEntity.setUpdateBy("");
            captchaEntity.setUpdateTime(new Date());
            captchaRepository.update(captchaEntity);
        }
        //转码：unicode转中文
        String contentValueStr = EncodeUtil.unicodeToString(contentValue); //EncodeUtil.convertUTF8ToString(contentValue);
        String contentValueStr2 = EncodeUtil.unicodeToString(contentValue2);
        String postdata = weChatMsgSend.createpostdata(userId, "text", applicationId, "content", contentValueStr + code + ", " + contentValueStr2);

        //3.推送信息（）
        String resp = weChatMsgSend.post("utf-8", WeChatMsgSend.CONTENT_TYPE, sendMessageUrl, postdata, token);
        logger.info("获取到的token======>" + token);
        logger.info("请求数据======>" + postdata);
        logger.info("发送微信的响应数据======>" + resp);
        Map map = JSON.parseObject(resp, Map.class);
        if ((Integer) map.get("errcode") != 0) {
            return "服务器发生错误，请检查网络是否畅通或联系系统管理员确认是否已开通账号权限！";
        }
        return null;
    }


    /**
     * 根据id获取
     *
     * @param id
     * @return
     */
    @Override
    public AccessTokenVM getEntryBy(long id) {
        AccessTokenEntity accessTokenEntity = accessTokenRepository.findById(id);
        AccessTokenVM accessTokenVM = new AccessTokenVM();
        BeanUtils.copyProperties(accessTokenEntity, accessTokenVM);
        return accessTokenVM;
    }

    /**
     * 创建
     *
     * @param accessTokenVM
     * @return entity的id
     */
    @Override
    public String createEntry(AccessTokenVM accessTokenVM) {
        AccessTokenEntity accessTokenEntity = new AccessTokenEntity();
        BeanUtils.copyProperties(accessTokenVM, accessTokenEntity);
        accessTokenEntity.setCreateBy(jwtUserUtil.getUserName());
        accessTokenEntity.setCreateTime(new Date());
        accessTokenRepository.insert(accessTokenEntity);
        return null;
    }

    /**
     * 修改
     *
     * @param accessTokenVM
     * @return 是否成功
     */
    @Override
    public String updateEntry(AccessTokenVM accessTokenVM) throws ServiceException {
        AccessTokenEntity accessTokenEntity = accessTokenRepository.findById(accessTokenVM.getId());
        accessTokenEntity.failWhenConcurrencyViolation(accessTokenVM.getConcurrencyVersion());
        BeanUtils.copyProperties(accessTokenVM, accessTokenEntity);
        accessTokenEntity.setUpdateBy(jwtUserUtil.getUserName());
        accessTokenEntity.setUpdateTime(new Date());
        accessTokenRepository.update(accessTokenEntity);
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
        return accessTokenRepository.deleteById(id);
    }

    /**
     * 分页
     *
     * @param pageQuery
     * @return
     */
    @Override
    public Page<AccessTokenVM> findEntrysByPage(PageQuery pageQuery) {
        FilterGroup filterGroup = pageQuery.getWhere();
        //自动转字符串
        String where = DynamicSearch.getInstance().buildWhere(filterGroup);
        PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize(), pageQuery.getOrderBy());
        return accessTokenRepository.findEntrysByPage(where);
    }
}
