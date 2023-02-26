package com.guoran.server.sys.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.guoran.server.auth.b.repository.UserRepository;
import com.guoran.server.common.JsonResult;
import com.guoran.server.common.exception.ServiceException;
import com.guoran.server.common.search.DynamicSearch;
import com.guoran.server.common.search.FilterGroup;
import com.guoran.server.common.search.PageQuery;
import com.guoran.server.common.utils.NoteUtil;
import com.guoran.server.security.JwtUserUtil;
import com.guoran.server.sys.a.repository.EmployeeRepository;
import com.guoran.server.sys.model.EmployeeEntity;
import com.guoran.server.sys.service.EmployeeService;
import com.guoran.server.sys.vmodel.EmployeeVM;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 员工信息表 服务实现类
 * </p>
 *
 * @author zhangjx
 * @create 2020-08-19
 * @Modify By
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    JwtUserUtil jwtUserUtil;
    @Autowired
    EmployeeRepository employeeRepository;

    /*  FileCompanyTypeEnum fileCompanyTypeEnum;

      AuditStatusEnum auditStatusEnum;*/
/*    @Autowired
    UserService userService;*/
    @Autowired
    UserRepository userRepository;
/*    @Autowired
    WagesRepository wagesRepository;
    @Autowired
    CustomerInfoRepository customerInfoRepository;
    @Autowired
    ManagementSalesPlanRepository managementSalesPlanRepository;
    @Autowired
    ManagementPostSalaryRepository managementPostSalaryRepository;*/

    /*    @Value(value = "${hr.employ.url}")*/
    private String employurl;

    /**
     * 根据id获取
     *
     * @param id
     * @return
     */
    @Override
    public EmployeeVM getEntryBy(long id) {
        EmployeeEntity employeeEntity = employeeRepository.findById(id);
        EmployeeVM employeeVM = new EmployeeVM();
        BeanUtils.copyProperties(employeeEntity, employeeVM);
        return employeeVM;
    }

    /**
     * 创建
     *
     * @param employeeVM
     * @return entity的id
     */
    @Override
    public String createEntry(EmployeeVM employeeVM) {
        String where = " and id_card = '" + employeeVM.getIdCard() + "'";
        Page<EmployeeVM> entrysByPage = employeeRepository.findEntrysByPage(where);
        if (entrysByPage.size() > 0) {
            return JsonResult.failed("身份信息重复，添加失败!");
        }
        String where1 = " and job_number = '" + employeeVM.getJobNumber() + "'";
        Page<EmployeeVM> entrysByPage1 = employeeRepository.findEntrysByPage(where1);
        if (entrysByPage1.size() > 0) {
            return JsonResult.failed("员工工号重复，添加失败!");
        }
        EmployeeEntity employeeEntity = new EmployeeEntity();
//        String companyName = employeeVM.getArchivesInCompanyId();
        BeanUtils.copyProperties(employeeVM, employeeEntity);
        employeeEntity.setCreateBy(jwtUserUtil.getUserName());
        employeeEntity.setCreateTime(new Date());
//        employeeEntity.setArchivesInCompanyName(companyName);
        employeeEntity.setAuditStatus(3);
        employeeEntity.setIsDelete(0);
        employeeEntity.setBinding(0);
        employeeRepository.insert(employeeEntity);
        return null;
    }

    /**
     * 批同步数据量处理
     *
     * @param employeeVMS
     * @return
     */
    public String createEntryBanch(List<EmployeeVM> employeeVMS) {
        List<EmployeeEntity> employeeEntities1 = new ArrayList<>();//新增集合
        List<EmployeeEntity> employeeEntities2 = new ArrayList<>();//修改集合
        for (EmployeeVM employeeVM : employeeVMS) {
            String where = " and job_number = '" + employeeVM.getJobNumber() + "'";
            Page<EmployeeVM> entrysByPage = employeeRepository.findEntrysByPage(where);
            if (entrysByPage.size() == 1) {
                EmployeeEntity employeeEntity = new EmployeeEntity();
                Long id = entrysByPage.get(0).getId();
                BeanUtils.copyProperties(employeeVM, entrysByPage.get(0));
                BeanUtils.copyProperties(entrysByPage.get(0), employeeEntity);
                employeeEntity.setId(id);
                employeeEntities2.add(employeeEntity);
            } else if (entrysByPage.size() <= 0) {
                EmployeeEntity employeeEntity = new EmployeeEntity();
                BeanUtils.copyProperties(employeeVM, employeeEntity);
                employeeEntity.setCreateBy(jwtUserUtil.getUserName());
                employeeEntity.setCreateTime(new Date());
                employeeEntities1.add(employeeEntity);
            }
        }
        if (employeeEntities1.size() > 0) {
            employeeRepository.insertBanch(employeeEntities1);
        }
        if (employeeEntities2.size() > 0) {
            employeeRepository.updateBanch(employeeEntities2);
        }
        return null;
    }

    /**
     * 修改
     *
     * @param employeeVM
     * @return 是否成功
     */
/*    @Override
    public String updateEntry(EmployeeVM employeeVM) throws ServiceException {
        String where = " and (id_card = '" + employeeVM.getIdCard() + "' or job_number = '" + employeeVM.getJobNumber() + "' ) and id <> " + employeeVM.getId();
        Page<EmployeeVM> entrysByPage = employeeRepository.findEntrysByPage(where);
        if (entrysByPage.size() > 0) {
            return JsonResult.failed("身份信息重复，修改失败");
        }
        EmployeeVM entryBy = this.getEntryBy(employeeVM.getId());
        if (entryBy.getIsAdd() != 1) {
            return JsonResult.failed("该信息不允许被修改!");
        }
        String where1 = " and user_code = '" + entryBy.getJobNumber() + "'";
        Page<UserVM> usersByPage = userRepository.findUsersByPage(where1);
        if (usersByPage.size() > 0) {
            return JsonResult.failed("该员工已绑定用户信息，无法修改！");
        }
        String where2 = " and user_Code = '" + entryBy.getJobNumber() + "'";
        Page<ManagementSalesPlanVM> entrysByPage1 = managementSalesPlanRepository.findEntrysByPage(where2);
        String where3 = " and user_code = '" + entryBy.getJobNumber() + "'";
        Page<WagesVM> entrysByPage2 = wagesRepository.findEntrysByPage(where3);
        String where4 = " and salesperson_id ='" + entryBy.getJobNumber() + "'";
        Page<CustomerInfoVM> entrysByPage3 = customerInfoRepository.findEntrysByPage(where4);
        String where5 = " and job_number like '%" + entryBy.getJobNumber() + "%'";
        Page<ManagementPostSalaryVM> entrysByPage4 = managementPostSalaryRepository.findEntrysByPage(where5);
        if (entrysByPage1.size() > 0 || entrysByPage2.size() > 0 || entrysByPage3.size() > 0 || entrysByPage4.size() > 0) {
            return JsonResult.failed("该员工已绑定相关信息，无法修改！");
        }
        EmployeeEntity employeeEntity = employeeRepository.findById(employeeVM.getId());
        employeeEntity.failWhenConcurrencyViolation(employeeVM.getConcurrencyVersion());
//        String companyName = employeeVM.getArchivesInCompanyId();
        BeanUtils.copyProperties(employeeVM, employeeEntity);
        employeeEntity.setUpdateBy(jwtUserUtil.getUserName());
        employeeEntity.setUpdateTime(new Date());
//        employeeEntity.setArchivesInCompanyName(companyName);
        employeeRepository.update(employeeEntity);
        return null;
    }*/

    /**
     * 修改职员已经绑定登录用户
     *
     * @param employeeVM
     * @return
     * @throws ServiceException
     */
    @Override
    public boolean updateBinding(EmployeeVM employeeVM) throws ServiceException {
        EmployeeEntity employeeEntity = employeeRepository.findByUserCode(employeeVM.getUserCode());
        employeeEntity.failWhenConcurrencyViolation(employeeVM.getConcurrencyVersion());
        BeanUtils.copyProperties(employeeVM, employeeEntity);
//        employeeEntity.setBinding(1);
        employeeEntity.setUpdateBy(jwtUserUtil.getUserName());
        employeeEntity.setUpdateTime(new Date());
        return employeeRepository.updateBinding(employeeEntity);
    }


    /**
     * 删除
     *
     * @param ids
     * @return 是否成功
     */
/*    @Override
    public String deleteById(String ids) {
        String[] Ids = ids.split(",");
        for (String id : Ids) {
            EmployeeVM entryBy = this.getEntryBy(Integer.parseInt(id));
            String where = " and user_code = '" + entryBy.getJobNumber() + "'";
            if (entryBy.getIsAdd() != 1) {
                return JsonResult.failed("该信息不允许删除！");
            }
            Page<UserVM> usersByPage = userRepository.findUsersByPage(where);
            if (usersByPage.size() > 0) {
                return JsonResult.failed("该员工已绑定用户信息，无法删除！");
            }
            String where2 = " and user_Code = '" + entryBy.getJobNumber() + "'";
            Page<ManagementSalesPlanVM> entrysByPage1 = managementSalesPlanRepository.findEntrysByPage(where2);
            String where3 = " and user_code = '" + entryBy.getJobNumber() + "'";
            Page<WagesVM> entrysByPage2 = wagesRepository.findEntrysByPage(where3);
            String where4 = " and salesperson_id ='" + entryBy.getJobNumber() + "'";
            Page<CustomerInfoVM> entrysByPage3 = customerInfoRepository.findEntrysByPage(where4);
            String where5 = " and job_number like '%" + entryBy.getJobNumber() + "%'";
            Page<ManagementPostSalaryVM> entrysByPage4 = managementPostSalaryRepository.findEntrysByPage(where5);

            if (entrysByPage1.size() > 0 || entrysByPage2.size() > 0 || entrysByPage3.size() > 0 || entrysByPage4.size() > 0) {
                return JsonResult.failed("该员工已绑定相关信息，无法删除！");
            }

        }
        employeeRepository.deleteById(Ids);
        return null;
    }*/

    /**
     * 改变审核状态
     *
     * @param ids state
     * @return 是否成功
     */
    @Override
    public String changeReview(String ids, Integer state, String dismissReason) {
        if (ids != null && ids.length() > 0) {
            String[] split = ids.split(",");
            List<String> ides = new ArrayList<>();
            for (String id : split) {
                EmployeeEntity employ = employeeRepository.findById(Long.parseLong(id));
                if (state == 1) {
                    if (employ.getAuditStatus().equals(state)) {
                        return JsonResult.failed("审核通过的不能再次审核");
                    }
                    if (employ.getAuditStatus() == 2) {
                        ides.add(id);
                    }
                    if (employ.getAuditStatus() != 2) {
                        return JsonResult.failed("只能审核已提交审核的数据");
                    }
                }
                if (state == 2) {
                    if (employ.getAuditStatus().equals(state)) {
                        return JsonResult.failed("重复提交审核");
                    }
                    if (employ.getAuditStatus() != 3 && employ.getAuditStatus() != 7) {
                        return JsonResult.failed("只能提交未提交或被驳回状态的数据");
                    }
                    if (employ.getAuditStatus() == 3 || employ.getAuditStatus() == 7) {
                        ides.add(id);
                    }
                }
                if (state == 3) {
                    if (employ.getAuditStatus().equals(state)) {
                        return JsonResult.failed("未提交无法撤回");
                    }
                    if (employ.getAuditStatus() == 1 || employ.getAuditStatus() == 2) {
                        ides.add(id);
                    }
                    if (employ.getAuditStatus() == 7) {
                        return JsonResult.failed("已驳回无法撤回");
                    }
                }
                if (state == 7) {
                    if (employ.getAuditStatus().equals(state)) {
                        return JsonResult.failed("不能重复驳回");
                    }
                    if (employ.getAuditStatus() == 2) {
                        ides.add(id);
                    }
                    if (employ.getAuditStatus() != 2) {
                        return JsonResult.failed("只能驳回审核中的数据");
                    }
                }
                if (state == 4) {
                    if (employ.getAuditStatus() == 2) {
                        ides.add(id);
                    } else {
                        return JsonResult.failed("只能撤回审核中的数据");
                    }
                }

            }
            if (state != 4) {
                employeeRepository.changeReview(ides, state, dismissReason);
            } else {
                state = 3;
                employeeRepository.changeReview(ides, state, dismissReason);
            }
            return null;
        }
        return JsonResult.failed("没有可以审核的数据");
    }

    /**
     * 分页
     *
     * @param pageQuery
     * @return
     */
    @Override
    public Page<EmployeeVM> findEntrysByPage(PageQuery pageQuery, String query) {
        FilterGroup filterGroup = pageQuery.getWhere();
        //自动转字符串
        String where = DynamicSearch.getInstance().buildWhere(filterGroup);
        PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize(), pageQuery.getOrderBy());
        if (!"".equals(query) && query != null) {
            where = " and (user_name like '%" + query + "%' or job_number like '%" + query + "%')" + where;
        }
        return employeeRepository.findEntrysByPage(where);
    }

    /**
     * 导出
     *
     * @param
     */
/*
    @Override
    public void explort(String ids, HttpServletResponse response, HttpServletRequest request) {
        List<EmployeeVM> employeeVMS = new ArrayList<>();
//        if(ids!=null&&ids.length()>0){
//            String[] split = ids.split(",");
//            String where  = " and id in (";
//            for(int i =0 ; i<split.length;i++){
//                EmployeeEntity employee = employeeRepository.findById(Long.parseLong(split[i]));
//                if(employee!=null){
//                    if(i!=split.length-1){
//                        where = where +split[i]+",";
//                    }else {
//                        where = where + split[i]+")";
//                    }
//                }
//            }
//            employeeVMS = employeeRepository.findEntrysByPage(where);
//        }else {
//            FilterGroup filterGroup=pageQuery.getWhere();
//            //自动转字符串
//            String where= DynamicSearch.getInstance().buildWhere(filterGroup);
//            PageHelper.startPage(pageQuery.getPageNum(),pageQuery.getPageSize(),pageQuery.getOrderBy());
        String where = " order by create_time desc";
        employeeVMS = employeeRepository.findEntrysByPage(where);
//        }

        String[] title = {"序号", "档案所在公司", "部门", "岗位", "姓名", "身份证号", "工号", "联系电话", "创建人", "创建时间"};
        String fileName = "员工信息.xls";
        String content[][] = new String[employeeVMS.size()][];
        for (int i = 0; i < employeeVMS.size(); i++) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            content[i] = new String[title.length];
            content[i][0] = "" + (i + 1);
            content[i][1] = employeeVMS.get(i).getArchivesInCompanyName();
            content[i][2] = employeeVMS.get(i).getDepartmentName();
            content[i][3] = employeeVMS.get(i).getPositionName();
            content[i][4] = employeeVMS.get(i).getUserName();
            content[i][5] = employeeVMS.get(i).getIdCard();
            content[i][6] = employeeVMS.get(i).getJobNumber();
            content[i][7] = ((employeeVMS.get(i).getMobile() == null) ? null : employeeVMS.get(i).getMobile().toString());
//            content[i][7] = ((employeeVMS.get(i).getAuditStatus() == null) ? null : auditStatusEnum.getText(employeeVMS.get(i).getAuditStatus()));
            content[i][8] = employeeVMS.get(i).getCreateBy();
            content[i][9] = simpleDateFormat.format(employeeVMS.get(i).getCreateTime());

        }
        HSSFWorkbook hssfWorkbook = ExcelUtil.getHSSFWorkbook(fileName, title, content, null);
        //响应到客户端
        try {
            this.setResponseHeader(response, fileName);
            OutputStream os = response.getOutputStream();
            hssfWorkbook.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
*/

    //获取有所员工
    @Override
    public List<EmployeeVM> findAllEmployee() {
        String where = "";
        Page<EmployeeVM> entrysByPage = employeeRepository.findEntrysByPage(where);

        return entrysByPage;
    }

    /**
     * 同步
     *
     * @return
     */
    @Override
    public String synInfos() throws IOException {

        NoteUtil noteUtil = new NoteUtil();
        String token = noteUtil.noteLogin();
        String msg = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        System.out.println(token);
        // 创建HttpGet请求，相当于在浏览器输入地址
        HttpGet httpGet = new HttpGet(employurl + "?Authorization=" + token);
        CloseableHttpResponse response = null;
        try {
            // 执行请求，相当于敲完地址后按下回车。获取响应
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() != 200) {
                return JsonResult.failed("当前网络较差或后台出现问题，同步失败，请稍后再试。");
            }
            if (response.getStatusLine().getStatusCode() == 200) {
                // 解析响应，获取数据
                String content = EntityUtils.toString(response.getEntity());
                Map<String, Map<String, List>> result = JSONObject.parseObject(content, Map.class);
                Map<String, List> data = result.get("data");
                List rows = data.get("rows");

                List<EmployeeVM> employeeVMS = new ArrayList<>();
                for (int i = 0; i < rows.size(); i++) {
                    Map synInfosVM = JSONObject.parseObject(rows.get(i).toString(), Map.class);

                    EmployeeVM employeeVM = new EmployeeVM();
                    employeeVM.setArchivesInCompanyId(synInfosVM.get("companyCode").toString());
                    employeeVM.setArchivesInCompanyName(synInfosVM.get("companyName").toString());
                    employeeVM.setDepartmentId(synInfosVM.get("deptCode").toString());
                    employeeVM.setDepartmentName(synInfosVM.get("deptName").toString());
                    employeeVM.setPositionId(synInfosVM.get("postCode").toString());
                    employeeVM.setPositionName(synInfosVM.get("postName").toString());
                    employeeVM.setIdCard(synInfosVM.get("idCard").toString());
                    employeeVM.setUserName(synInfosVM.get("name").toString());
                    employeeVM.setJobNumber(synInfosVM.get("jobNo").toString());
                    employeeVM.setMobile(Long.parseLong(synInfosVM.get("tel").toString()));
                    employeeVM.setAuditStatus(3);
                    employeeVM.setIsDelete(0);
                    employeeVM.setBinding(0);
                    employeeVMS.add(employeeVM);
                }
                msg = this.createEntryBanch(employeeVMS);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                // 关闭资源
                response.close();
            }
            // 关闭浏览器
            httpclient.close();
        }
        return msg;
    }


    //发送响应流方法
    public void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            try {
                fileName = new String(fileName.getBytes(), "GBK");
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            response.setContentType("application/octet-stream;charset=GBK");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    /**
     * 获取登录人对应的公司，部门及岗位信息
     */
/*
    @Override
    public EmployeeVM getEntryInfo() {
        UserVM userVM = userService.getLoginUserInfo();
        EmployeeEntity entity = employeeRepository.findByJobNumber(userVM.getUserCode());
        EmployeeVM dao = new EmployeeVM();
        BeanUtils.copyProperties(entity, dao);
        return dao;
    }
*/

/*    @Override
    public List<EmployeeVM> getWagesEmployees(WagesVO wagesVO) {
        List<EmployeeVM> employeeVMList = new ArrayList<>();
        Page<EmployeeVM> employeeVMS = employeeRepository.findEntrysByPage("");
        for (EmployeeVM employeeVM : employeeVMS) {
            String where = " and yearmonth LIKE '%" + wagesVO.getYearmonth() + "%' and job_number = '" + employeeVM.getJobNumber() + "'";
            Page<WagesVM> entrysByPage = wagesRepository.findEntrysByPage(where);
            if (entrysByPage.size() <= 0) {
                employeeVMList.add(employeeVM);
            }

        }

        return employeeVMList;
    }*/

}
