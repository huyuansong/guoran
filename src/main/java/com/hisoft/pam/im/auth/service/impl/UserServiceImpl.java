package com.hisoft.pam.im.auth.service.impl;

import cn.hutool.core.codec.Base64Decoder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hisoft.pam.im.auth.model.UserEntity;
import com.hisoft.pam.im.auth.model.UserRoleEntity;
import com.hisoft.pam.im.auth.repository.UserRepository;
import com.hisoft.pam.im.auth.repository.UserRoleRepository;
import com.hisoft.pam.im.auth.service.AuthService;
import com.hisoft.pam.im.auth.service.UserRoleService;
import com.hisoft.pam.im.auth.service.UserService;
import com.hisoft.pam.im.auth.vmodel.UserInfoVM;
import com.hisoft.pam.im.auth.vmodel.UserRoleVM;
import com.hisoft.pam.im.auth.vmodel.UserVM;
import com.hisoft.pam.im.auth.vo.UserEmployeeVO;
import com.hisoft.pam.im.common.JsonResult;
import com.hisoft.pam.im.common.exception.ImErrorCode;
import com.hisoft.pam.im.common.exception.ServiceException;
import com.hisoft.pam.im.common.i18n.MessageUtils;
import com.hisoft.pam.im.common.search.DynamicSearch;
import com.hisoft.pam.im.common.search.FilterGroup;
import com.hisoft.pam.im.common.search.PageQuery;
import com.hisoft.pam.im.common.utils.AesEncryptUtil;
import com.hisoft.pam.im.common.utils.StringUtils;
import com.hisoft.pam.im.security.JwtUserUtil;
import com.hisoft.pam.im.security.tool.PasswordEncryption;
/*import com.hisoft.pam.im.sys.model.EmployeeEntity;
import com.hisoft.pam.im.sys.repository.EmployeeRepository;
import com.hisoft.pam.im.sys.service.EmployeeService;
import com.hisoft.pam.im.sys.vmodel.EmployeeVM;
import com.hisoft.pam.im.weChat.model.CaptchaEntity;
import com.hisoft.pam.im.weChat.repository.CaptchaRepository;*/

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    JwtUserUtil jwtUserUtil;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserRoleRepository userRoleRepository;
    @Autowired
    AuthService authService;
    @Autowired
    UserRoleService userRoleService;
    /*@Autowired
    CaptchaRepository captchaRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    EmployeeService employeeService;*/
    @Autowired
    ObjectMapper objectMapper;
    /**
     * 根据id获取
     *
     * @param id
     */
    @Override
    public UserVM getEntryBy(long id) {
        UserEntity userEntity = userRepository.findById(id);
        UserVM userVM = new UserVM();
        BeanUtils.copyProperties(userEntity, userVM);
        return userVM;
    }


    /**
     * 根据id获取
     *
     * @param id
     */
    @Override
    public UserEntity getEntryById(long id) {
        return userRepository.findById(id);
    }

    /**
     * 创建
     *
     * @param userVM
     */
    @Override
    @Transactional
    public Long createEntry(UserVM userVM) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userVM, userEntity);
        //解密密码
        String password = AesEncryptUtil.aesDecrypt(userVM.getPassword());
        userEntity.setPassword(PasswordEncryption.encryptedValue(password));
       // userEntity.setPassword(userVM.getPassword());

        userEntity.setCreateBy(jwtUserUtil.getUserName());
        userEntity.setCreateTime(new Date());
        userRepository.insert(userEntity);
        return userEntity.getId();
    }

    /**
     * 修改
     *
     * @param userVM
     */
    @Override
    @Transactional
    public void updateEntry(UserVM userVM) throws IllegalStateException {
        UserEntity userEntity = userRepository.findById(userVM.getId());
        if(!userVM.getName().equals(userEntity.getName())){
            UserVM dao =userRepository.findUserByName(userVM.getName());
            if(dao!=null){
                throw new ServiceException("用户名重复！");
            }
        }

        UserVM loginUserInfo = getLoginUserInfo();
        if(userVM.getId().equals(loginUserInfo.getId())&&!loginUserInfo.getName().equals(userVM.getName())){
            throw new ServiceException("不能修改自身的用户名，请联系其他管理员进行修改。");
        }

        //判断是否修改用户绑定登录账号
        if(!userEntity.getUserCode().equals(userVM.getUserCode())){
            //绑定
            /*EmployeeVM employeeVM=new EmployeeVM();
            employeeVM.setBinding(1);
            employeeVM.setUserCode(userVM.getUserCode());
            boolean flag =employeeService.updateBinding(employeeVM);//绑定
            if(!flag){
                throw new ServiceException("创建用户修改用户绑定登录账号失败！");
            }*/
           /* //解绑定
            EmployeeVM edao=new EmployeeVM();
            edao.setUserCode(userEntity.getUserCode());
            edao.setBinding(0);
            boolean flag3 =employeeService.updateBinding(edao);//绑定
            if(!flag3){
                throw new ServiceException("创建用户修改用户绑定登录账号失败！");
            }*/
        }else {
           /* //直接绑定（第一次绑定）
            EmployeeVM employeeVM=new EmployeeVM();
            employeeVM.setUserCode(userVM.getUserCode());
            employeeVM.setBinding(1);
            boolean flag =employeeService.updateBinding(employeeVM);
            if(!flag){
                throw new ServiceException("创建用户修改用户绑定登录账号失败！");
            }*/
        }

        userEntity.failWhenConcurrencyViolation(userVM.getConcurrencyVersion());
        BeanUtils.copyProperties(userVM, userEntity);
        userEntity.setUpdateBy(jwtUserUtil.getUserName());
        userEntity.setUpdateTime(new Date());
       String confirmPassword = AesEncryptUtil.aesDecrypt(userVM.getPassword());
        if(StringUtils.isNotEmpty(confirmPassword)) {
            confirmPassword = PasswordEncryption.encryptedValue(confirmPassword);
            userEntity.setPassword(confirmPassword);
        }
        userRepository.update(userEntity);
        //同步修改角色信息
        List<UserRoleEntity> userRoleEntity =userRoleRepository.findByUserId(userVM.getId());
        if(userRoleEntity!=null && userRoleEntity.size()>0){
            //如果用户有之前分配的角色，先删除后增加
            userRoleRepository.deleteById(userVM.getId());
        }
        //增加用户角色
        UserRoleVM userRoleVM=new UserRoleVM();
        userRoleVM.setUserId(userVM.getId());
        userRoleVM.setUserName(userVM.getName());
        userRoleVM.setRoleVMList(userVM.getRoleVMList());
        String message = userRoleService.createEntry(userRoleVM);
        if(StringUtils.isNotEmpty(message)){
            throw new ServiceException(message);
        }

    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    @Transactional
    public void deleteById(long id) {
        userRepository.deleteById(id);
    }

    /**
     * 分页
     *
     * @param where
     * @return
     */
    @Override
    public Page<UserVM> findUsersByPage(String where) {
        return userRepository.findUsersByPage(where);
    }

    /**
     * 根据用户名查找用户
     *
     * @param name
     * @return
     */
    @Override
    public UserVM getEntryByName(String name) {
        return userRepository.findUserByName(name);
    }
    /**
     * 根据工号查找用户
     * @param userCode
     * @return
     */
    @Override
    public String getEntryByJobNo(String userCode){
        UserVM userVM = userRepository.findUserByJobNo(userCode);
        if(userVM!=null){
            return JsonResult.failed("员工信息已被绑定！");
        }
        return null;
    }



    /**
     * 初始化密码
     *
     * @param userId
     */
    @Override
    public void updateInitializePassWord(long userId) {
        String password = "Test@2019";
        userRepository.updateInitializePassWord(userId, PasswordEncryption.encryptedValue(password));
    }


    /**
     * 获取当前登录用户ID
     *
     * @return
     */
    @Override
    public Long getLoginUser() {
        //获取登录用户名
        String userName = jwtUserUtil.getUserName();
        //根据登录信息获取用户ID.
        UserVM userVM = userRepository.findUserByName(userName);
        return userVM.getId();
    }

    /**
     * 获取当前登录用户
     *
     * @return
     */
    @Override
    public UserEmployeeVO getCurrentUserInfo() {
        //获取登录用户名
        String userName = jwtUserUtil.getUserName();
        //根据登录信息获取用户ID.
        UserVM userVM =userRepository.findUserByName(userName);
        UserEmployeeVO dao =new UserEmployeeVO();
        if(userVM!=null){

           /* BeanUtils.copyProperties(userVM, dao);
            EmployeeEntity entity=employeeRepository.findByJobNumber(userVM.getUserCode());
            if(entity!=null){
                BeanUtils.copyProperties(entity, dao);
            }*/
        }
        dao.setPassword(null);

        //获取角色信息
        List<UserRoleVM> userRoleVMList = userRoleService.findEntityByUserId(userVM.getId());
        dao.setUserRoleVMList(userRoleVMList);
        return dao;
    }


    /**
     * 获取当前登录用户
     *
     * @return
     */
    @Override
    public UserVM getLoginUserInfo() {
        //获取登录用户名
        String userName = jwtUserUtil.getUserName();
        //根据登录信息获取用户ID.
        UserVM userVM =userRepository.findUserByName(userName);
        userVM.setPassword(null);
        return userVM;
    }


    /**
     * 修改密码
     *
     * @param userVM
     */
    @Override
    public String updatePassWord(UserVM userVM) {
        //获取登录用户名
        String userName = jwtUserUtil.getUserName();
        //根据登录信息获取用户ID.
        UserVM dao = userRepository.findUserByName(userName);
        JsonResult jsonResult = new JsonResult();
        UserEntity userEntity = userRepository.findByUserCode(dao.getUserCode());
        String result = null;
        if (userEntity != null) {
            //1.验证旧密码
            //解密密码
            String oldPassword = AesEncryptUtil.aesDecrypt(userVM.getOldPassword());
            String token = null;
            try {
                token = authService.login(userEntity.getName(), oldPassword);
                if (StringUtils.isNotEmpty(token)) {
//                    //2.验证两次密码是否一致
//                    if (!userVM.getNewPassword().equals(userVM.getConfirmPassword())) {
//                        return JsonResult.failed("两次密码不一致！");
//                    } else {
                        //3.修改密码
                        //解密密码
                        String confirmPassword = AesEncryptUtil.aesDecrypt(userVM.getConfirmPassword());
                        userRepository.updateInitializePassWord(userEntity.getId(), PasswordEncryption.encryptedValue(confirmPassword));
//                    }

                }
            } catch (Exception e) {
                jsonResult.setMessage("原密码错误!");
                jsonResult.setCode(ImErrorCode.MSG_STR_ISEMPTY);
                try{
                    result =objectMapper.writeValueAsString(jsonResult);
                }catch (JsonProcessingException ex){
                    ex.printStackTrace();
                }
                return result;
            }
        }
        jsonResult.setIsSuccess(true);
        jsonResult.setMessage(MessageUtils.get(ImErrorCode.MSG_SUCCESS));
        try{
            result =objectMapper.writeValueAsString(jsonResult);
        }catch (JsonProcessingException ex){
            ex.printStackTrace();
        }
        return result;
    }



    /**
     * 重置密码
     * @param userVM
     * @return
     */
    @Override
    public String resetPassword(UserVM userVM){
        //1.根据工号和验证码
        UserEntity userEntity=userRepository.findByUserCode(userVM.getUserCode());
        if(userEntity==null){
            return JsonResult.failed("员工工号不存在");
        }else {
            /*CaptchaEntity captchaEntity=captchaRepository.findByUserId(userVM.getUserCode());
            if(captchaEntity!=null){
                //比较验证码的正确性
                if(userVM.getCaptcha().equals(captchaEntity.getCaptcha())){
                    //验证码相同，修改密码
                    if (!userVM.getNewPassword().equals(userVM.getConfirmPassword())) {
                        return JsonResult.failed("两次密码不一致！");
                    } else {
                        //3.修改密码
                        userRepository.updateInitializePassWord(userEntity.getId(), PasswordEncryption.encryptedValue(userVM.getConfirmPassword()));
                    }

                }else {
                    //验证码不同
                    return JsonResult.failed("验证码不对！");
                }
            } */
        }
        //更新密码
        return null;

    }


    /**
     * 分页
     *
     * @param pageQuery
     * @return
     */
    @Override
    public Page<UserInfoVM> findUserInfoAndRoleByPage(PageQuery pageQuery) {
        FilterGroup filterGroup = pageQuery.getWhere();
        //自动转字符串
        String where = DynamicSearch.getInstance().buildWhere(filterGroup);
        PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize(), pageQuery.getOrderBy());
        return userRepository.findUserInfoAndRoleByPage(where);
    }


    /**
     * 注销账号
     *
     * @param id
     */
    @Override
    public String updateAccount(long id) {

        UserEntity userEntity = userRepository.findById(id);
        if (userEntity != null) {
            if(jwtUserUtil.getUserName().equals(userEntity.getName())){
                throw new ServiceException("不能注销自己。");
            }
            userRepository.updateAccount(id);
        }
        return null;
    }
    /**
     * 注销账号
     *
     * @param
     */
    @Override
    public String updateAccountByCode(String code) {

        UserEntity userEntity = userRepository.findByUserCode(code);
        if (userEntity != null) {
            if(jwtUserUtil.getUserName().equals(userEntity.getName())){
                throw new ServiceException("不能注销自己。");
            }
            userRepository.updateAccountByCode(code);
        }
        return null;
    }


    /**
     * 根据Code获取
     *
     * @param code
     */
    @Override
    public UserVM getEntryByCode(String code) {
        UserVM userVM = null;
        UserEntity userEntity = userRepository.findUserByCode(code);
        if (userEntity != null) {
            userVM = new UserVM();
            BeanUtils.copyProperties(userEntity, userVM);
        }
        return userVM;
    }


    /**
     * 根据Mobile获取
     *
     * @param mobile
     */
    @Override
    public UserVM getEntryByMobile(String mobile) {

        UserVM userVM = null;
        UserEntity userEntity = userRepository.findUserByMobile(mobile);
        if (userEntity != null) {
            userVM = new UserVM();
            BeanUtils.copyProperties(userEntity, userVM);
        }
        return userVM;
    }

    /**
     * 根据email获取
     *
     * @param email
     */
    @Override
    public UserVM getEntryByEmail(String email) {

        UserVM userVM = null;
        UserEntity userEntity = userRepository.findUserByEmail(email);
        if (userEntity != null) {
            userVM = new UserVM();
            BeanUtils.copyProperties(userEntity, userVM);
        }
        return userVM;
    }

    /**
     * 根据roleId获取
     *
     * @param roleId
     */

    @Override
    public List<UserVM> getEntrysByRoleId(long roleId) {
        return userRepository.getEntrysByRoleId(roleId);
    }


    /**
     * 查询用户信息，根据账号类型
     *
     * @param accountType
     * @return
     */
    @Override
    public List<UserEntity> getUserByAccountType(long accountType) {
        return userRepository.getUserByAccountType(accountType);
    }

    /**
     * 启用账户
     * @param id
     * @return
     */
    @Override
    public String updateAccountUse(long id) {
        UserEntity userEntity = userRepository.findById(id);
        if (userEntity != null) {
            userRepository.updateAccountUse(id);
        }
        return null;
    }

    /**
     * 修改openId
     *
     * @param userVM
     */
    @Override
    public String updateOpenId(UserVM userVM) {
        String userName = jwtUserUtil.getUserName();
        UserVM user = userRepository.findUserByName(userName);
        if(user==null){
            throw new ServiceException("用户信息有误！");
        }else {
            String password = userVM.getPassword();
            String token = authService.login(user.getName(), password);
            if (StringUtils.isNotEmpty(token)) {//密码有效
                UserEntity userEntity = userRepository.findByOpenId(userVM.getOpenId());
                if(userEntity==null) {
                    userRepository.updateOpenId(userVM.getOpenId(), user.getId());
                }else {
                    if (userEntity.getName().equals(userVM.getName())) {
                        throw new ServiceException("此微信号已绑定本账户，不必重复绑定！");
                    } else {
                        throw new ServiceException("此微信号已绑在'"+userEntity.getName()+"'账户，不能重复绑定！");
                    }
                }
            }else {
                throw new ServiceException("密码验证失败！");
            }
        }
        return "微信绑定成功。";
    }

    /*@Override
    public EmployeeVM getEmpInfo(long id) {
        UserEntity userEntity = userRepository.findById(id);
        String userCode = userEntity.getUserCode();
        String where = " and job_number = '"+userCode+"'";
        Page<EmployeeVM> entrysByPage = employeeRepository.findEntrysByPage(where);
        return entrysByPage.get(0);
    }*/

    @Override
    public Boolean ifAdmin() {
        UserEmployeeVO userEmployeeVO = getCurrentUserInfo();
        //判断权限列表里是否有系统管理员权限
        boolean flag = userEmployeeVO.getUserRoleVMList().stream().anyMatch(role -> role.getRoleName().contains("管理"));
        return flag;
    }


}
