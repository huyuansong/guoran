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
     * ??????id??????
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
     * ??????id??????
     *
     * @param id
     */
    @Override
    public UserEntity getEntryById(long id) {
        return userRepository.findById(id);
    }

    /**
     * ??????
     *
     * @param userVM
     */
    @Override
    @Transactional
    public Long createEntry(UserVM userVM) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userVM, userEntity);
        //????????????
        String password = AesEncryptUtil.aesDecrypt(userVM.getPassword());
        userEntity.setPassword(PasswordEncryption.encryptedValue(password));
       // userEntity.setPassword(userVM.getPassword());

        userEntity.setCreateBy(jwtUserUtil.getUserName());
        userEntity.setCreateTime(new Date());
        userRepository.insert(userEntity);
        return userEntity.getId();
    }

    /**
     * ??????
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
                throw new ServiceException("??????????????????");
            }
        }

        UserVM loginUserInfo = getLoginUserInfo();
        if(userVM.getId().equals(loginUserInfo.getId())&&!loginUserInfo.getName().equals(userVM.getName())){
            throw new ServiceException("????????????????????????????????????????????????????????????????????????");
        }

        //??????????????????????????????????????????
        if(!userEntity.getUserCode().equals(userVM.getUserCode())){
            //??????
            /*EmployeeVM employeeVM=new EmployeeVM();
            employeeVM.setBinding(1);
            employeeVM.setUserCode(userVM.getUserCode());
            boolean flag =employeeService.updateBinding(employeeVM);//??????
            if(!flag){
                throw new ServiceException("???????????????????????????????????????????????????");
            }*/
           /* //?????????
            EmployeeVM edao=new EmployeeVM();
            edao.setUserCode(userEntity.getUserCode());
            edao.setBinding(0);
            boolean flag3 =employeeService.updateBinding(edao);//??????
            if(!flag3){
                throw new ServiceException("???????????????????????????????????????????????????");
            }*/
        }else {
           /* //?????????????????????????????????
            EmployeeVM employeeVM=new EmployeeVM();
            employeeVM.setUserCode(userVM.getUserCode());
            employeeVM.setBinding(1);
            boolean flag =employeeService.updateBinding(employeeVM);
            if(!flag){
                throw new ServiceException("???????????????????????????????????????????????????");
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
        //????????????????????????
        List<UserRoleEntity> userRoleEntity =userRoleRepository.findByUserId(userVM.getId());
        if(userRoleEntity!=null && userRoleEntity.size()>0){
            //?????????????????????????????????????????????????????????
            userRoleRepository.deleteById(userVM.getId());
        }
        //??????????????????
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
     * ??????
     *
     * @param id
     */
    @Override
    @Transactional
    public void deleteById(long id) {
        userRepository.deleteById(id);
    }

    /**
     * ??????
     *
     * @param where
     * @return
     */
    @Override
    public Page<UserVM> findUsersByPage(String where) {
        return userRepository.findUsersByPage(where);
    }

    /**
     * ???????????????????????????
     *
     * @param name
     * @return
     */
    @Override
    public UserVM getEntryByName(String name) {
        return userRepository.findUserByName(name);
    }
    /**
     * ????????????????????????
     * @param userCode
     * @return
     */
    @Override
    public String getEntryByJobNo(String userCode){
        UserVM userVM = userRepository.findUserByJobNo(userCode);
        if(userVM!=null){
            return JsonResult.failed("???????????????????????????");
        }
        return null;
    }



    /**
     * ???????????????
     *
     * @param userId
     */
    @Override
    public void updateInitializePassWord(long userId) {
        String password = "Test@2019";
        userRepository.updateInitializePassWord(userId, PasswordEncryption.encryptedValue(password));
    }


    /**
     * ????????????????????????ID
     *
     * @return
     */
    @Override
    public Long getLoginUser() {
        //?????????????????????
        String userName = jwtUserUtil.getUserName();
        //??????????????????????????????ID.
        UserVM userVM = userRepository.findUserByName(userName);
        return userVM.getId();
    }

    /**
     * ????????????????????????
     *
     * @return
     */
    @Override
    public UserEmployeeVO getCurrentUserInfo() {
        //?????????????????????
        String userName = jwtUserUtil.getUserName();
        //??????????????????????????????ID.
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

        //??????????????????
        List<UserRoleVM> userRoleVMList = userRoleService.findEntityByUserId(userVM.getId());
        dao.setUserRoleVMList(userRoleVMList);
        return dao;
    }


    /**
     * ????????????????????????
     *
     * @return
     */
    @Override
    public UserVM getLoginUserInfo() {
        //?????????????????????
        String userName = jwtUserUtil.getUserName();
        //??????????????????????????????ID.
        UserVM userVM =userRepository.findUserByName(userName);
        userVM.setPassword(null);
        return userVM;
    }


    /**
     * ????????????
     *
     * @param userVM
     */
    @Override
    public String updatePassWord(UserVM userVM) {
        //?????????????????????
        String userName = jwtUserUtil.getUserName();
        //??????????????????????????????ID.
        UserVM dao = userRepository.findUserByName(userName);
        JsonResult jsonResult = new JsonResult();
        UserEntity userEntity = userRepository.findByUserCode(dao.getUserCode());
        String result = null;
        if (userEntity != null) {
            //1.???????????????
            //????????????
            String oldPassword = AesEncryptUtil.aesDecrypt(userVM.getOldPassword());
            String token = null;
            try {
                token = authService.login(userEntity.getName(), oldPassword);
                if (StringUtils.isNotEmpty(token)) {
//                    //2.??????????????????????????????
//                    if (!userVM.getNewPassword().equals(userVM.getConfirmPassword())) {
//                        return JsonResult.failed("????????????????????????");
//                    } else {
                        //3.????????????
                        //????????????
                        String confirmPassword = AesEncryptUtil.aesDecrypt(userVM.getConfirmPassword());
                        userRepository.updateInitializePassWord(userEntity.getId(), PasswordEncryption.encryptedValue(confirmPassword));
//                    }

                }
            } catch (Exception e) {
                jsonResult.setMessage("???????????????!");
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
     * ????????????
     * @param userVM
     * @return
     */
    @Override
    public String resetPassword(UserVM userVM){
        //1.????????????????????????
        UserEntity userEntity=userRepository.findByUserCode(userVM.getUserCode());
        if(userEntity==null){
            return JsonResult.failed("?????????????????????");
        }else {
            /*CaptchaEntity captchaEntity=captchaRepository.findByUserId(userVM.getUserCode());
            if(captchaEntity!=null){
                //???????????????????????????
                if(userVM.getCaptcha().equals(captchaEntity.getCaptcha())){
                    //??????????????????????????????
                    if (!userVM.getNewPassword().equals(userVM.getConfirmPassword())) {
                        return JsonResult.failed("????????????????????????");
                    } else {
                        //3.????????????
                        userRepository.updateInitializePassWord(userEntity.getId(), PasswordEncryption.encryptedValue(userVM.getConfirmPassword()));
                    }

                }else {
                    //???????????????
                    return JsonResult.failed("??????????????????");
                }
            } */
        }
        //????????????
        return null;

    }


    /**
     * ??????
     *
     * @param pageQuery
     * @return
     */
    @Override
    public Page<UserInfoVM> findUserInfoAndRoleByPage(PageQuery pageQuery) {
        FilterGroup filterGroup = pageQuery.getWhere();
        //??????????????????
        String where = DynamicSearch.getInstance().buildWhere(filterGroup);
        PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize(), pageQuery.getOrderBy());
        return userRepository.findUserInfoAndRoleByPage(where);
    }


    /**
     * ????????????
     *
     * @param id
     */
    @Override
    public String updateAccount(long id) {

        UserEntity userEntity = userRepository.findById(id);
        if (userEntity != null) {
            if(jwtUserUtil.getUserName().equals(userEntity.getName())){
                throw new ServiceException("?????????????????????");
            }
            userRepository.updateAccount(id);
        }
        return null;
    }
    /**
     * ????????????
     *
     * @param
     */
    @Override
    public String updateAccountByCode(String code) {

        UserEntity userEntity = userRepository.findByUserCode(code);
        if (userEntity != null) {
            if(jwtUserUtil.getUserName().equals(userEntity.getName())){
                throw new ServiceException("?????????????????????");
            }
            userRepository.updateAccountByCode(code);
        }
        return null;
    }


    /**
     * ??????Code??????
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
     * ??????Mobile??????
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
     * ??????email??????
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
     * ??????roleId??????
     *
     * @param roleId
     */

    @Override
    public List<UserVM> getEntrysByRoleId(long roleId) {
        return userRepository.getEntrysByRoleId(roleId);
    }


    /**
     * ???????????????????????????????????????
     *
     * @param accountType
     * @return
     */
    @Override
    public List<UserEntity> getUserByAccountType(long accountType) {
        return userRepository.getUserByAccountType(accountType);
    }

    /**
     * ????????????
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
     * ??????openId
     *
     * @param userVM
     */
    @Override
    public String updateOpenId(UserVM userVM) {
        String userName = jwtUserUtil.getUserName();
        UserVM user = userRepository.findUserByName(userName);
        if(user==null){
            throw new ServiceException("?????????????????????");
        }else {
            String password = userVM.getPassword();
            String token = authService.login(user.getName(), password);
            if (StringUtils.isNotEmpty(token)) {//????????????
                UserEntity userEntity = userRepository.findByOpenId(userVM.getOpenId());
                if(userEntity==null) {
                    userRepository.updateOpenId(userVM.getOpenId(), user.getId());
                }else {
                    if (userEntity.getName().equals(userVM.getName())) {
                        throw new ServiceException("??????????????????????????????????????????????????????");
                    } else {
                        throw new ServiceException("?????????????????????'"+userEntity.getName()+"'??????????????????????????????");
                    }
                }
            }else {
                throw new ServiceException("?????????????????????");
            }
        }
        return "?????????????????????";
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
        //???????????????????????????????????????????????????
        boolean flag = userEmployeeVO.getUserRoleVMList().stream().anyMatch(role -> role.getRoleName().contains("??????"));
        return flag;
    }


}
