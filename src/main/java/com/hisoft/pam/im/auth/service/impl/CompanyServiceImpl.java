package com.hisoft.pam.im.auth.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hisoft.pam.im.auth.model.CompanyEntity;
import com.hisoft.pam.im.auth.repository.DepartmentRepository;
import com.hisoft.pam.im.auth.repository.PositionRepository;
import com.hisoft.pam.im.auth.service.UserService;
import com.hisoft.pam.im.auth.vmodel.CompanyVM;
import com.hisoft.pam.im.auth.repository.CompanyRepository;
import com.hisoft.pam.im.auth.service.CompanyService;
import com.hisoft.pam.im.auth.vmodel.DepartmentVM;
import com.hisoft.pam.im.auth.vmodel.PositionVM;
import com.hisoft.pam.im.auth.vo.CompanyVO;
import com.hisoft.pam.im.common.JsonResult;
import com.hisoft.pam.im.common.exception.ServiceException;
import com.hisoft.pam.im.common.search.DynamicSearch;
import com.hisoft.pam.im.common.search.FilterGroup;
import com.hisoft.pam.im.common.search.PageQuery;
import com.hisoft.pam.im.common.utils.NoteUtil;
import com.hisoft.pam.im.common.utils.StringUtils;
import com.hisoft.pam.im.security.JwtUserUtil;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhangjx
 * @create 2020-09-10
 * @Modify By
 */
@Service
public class CompanyServiceImpl  implements CompanyService {
    @Autowired
    JwtUserUtil jwtUserUtil;
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    PositionRepository positionRepository;
    @Value(value = "${hr.company.url}")
    private  String companyurl;
    @Value(value = "${hr.dept.url}")
    private  String depturl;

    @Autowired
    DepartmentServiceImpl departmentService;
/*    @Autowired
    EmployeeService employeeService;*/
    @Autowired
    UserService userService;
    /**
     * 根据id获取
     *
     * @param id
     * @return
     */
    @Override
    public CompanyVM getEntryBy(long id) {
        CompanyEntity companyEntity = companyRepository.findById(id);
        CompanyVM companyVM = new CompanyVM();
        BeanUtils.copyProperties(companyEntity,companyVM);
        return companyVM ;
    }

    /**
     * 创建
     *
     * @param companyVM
     * @return entity的id
     */
    @Override
    public String createEntry(CompanyVM companyVM) {
        try {
            CompanyEntity companyEntity=new CompanyEntity();
            BeanUtils.copyProperties(companyVM,companyEntity);
            companyEntity.setCreateBy(jwtUserUtil.getUserName());
            companyEntity.setCreateTime(new Date());
            companyRepository.insert(companyEntity);
        } catch (BeansException e) {
            return "新增失败！后台发生错误！";
        }
        return null;
    }

    /**
     * 修改
     *
     * @param companyVM
     * @return 是否成功
     */
    @Override
    public String updateEntry(CompanyVM companyVM) throws ServiceException{
        try {
            CompanyEntity companyEntity=companyRepository.findById(companyVM.getId());
            companyEntity.failWhenConcurrencyViolation(companyVM.getConcurrencyVersion());
            BeanUtils.copyProperties(companyVM,companyEntity);
            companyEntity.setUpdateBy(jwtUserUtil.getUserName());
            companyEntity.setUpdateTime(new Date());
            companyRepository.update(companyEntity);
        } catch (ServiceException e) {
            return "修改失败！后台发生错误！";
        } catch (BeansException e) {
            return "修改失败！后台发生错误！";
        }
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
        try {
            return companyRepository.deleteById(id);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 分页
     *
     * @param pageQuery
     * @return
     */
    @Override
    public Page<CompanyVM> findEntrysByPage(PageQuery pageQuery) {
        FilterGroup filterGroup=pageQuery.getWhere();
        //自动转字符串
        String where= DynamicSearch.getInstance().buildWhere(filterGroup);
        PageHelper.startPage(pageQuery.getPageNum(),pageQuery.getPageSize(),pageQuery.getOrderBy());
        return companyRepository.findEntrysByPage(where);
    }

    @Override
    public String synInfos() throws IOException {
       /* NoteUtil noteUtil = new NoteUtil();
        String token = noteUtil.noteLogin();
        String msg = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 创建HttpGet请求，相当于在浏览器输入地址
        HttpGet httpGet = new HttpGet(companyurl+"?Authorization="+token);
        CloseableHttpResponse response = null;
        try {
            // 执行请求，相当于敲完地址后按下回车。获取响应
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if(response.getStatusLine().getStatusCode() != 200){
                return JsonResult.failed("当前网络较差或后台出现问题，同步失败，请稍后再试。");
            }
            if (response.getStatusLine().getStatusCode() == 200) {
                // 解析响应，获取数据
                String content = EntityUtils.toString(response.getEntity());
                Map<String, List> result = JSONObject.parseObject(content,Map.class);
                List rows = result.get("data");

                List<CompanyVM> companyVMS = new ArrayList<>();
                for(int i =0; i<rows.size();i++){
                    Map synInfosVM = JSONObject.parseObject(rows.get(i).toString(), Map.class);
                    CompanyVM companyVM = new CompanyVM();
                    String compCode = synInfosVM.get("compCode").toString();
                    String compName = synInfosVM.get("compName").toString();
                    *//**//*String token1 = noteUtil.noteLogin();
                    HttpGet httpGet1 = new HttpGet(depturl+"?Authorization="+token1+"&nccCompanyCode="+synInfosVM.get("nccCompanyCode"));
                    response = httpclient.execute(httpGet1);
                    String content1 = EntityUtils.toString(response.getEntity());
                    Map<String, List<Map<String,Object>>> result1 = JSONObject.parseObject(content1,Map.class);
                    List<Map<String,Object>> rows1 = result1.get("data");
                    List<DepartmentVM> departmentVMS = new ArrayList<>();

                    rows1.forEach(x -> {
                        DepartmentVM vm = new DepartmentVM();
                        vm.setCompanyCode(compCode);
                        vm.setCompanyName(compName);
                        vm.setDepartmentCode(x.get("nccDeptCode").toString());
                        vm.setDepartmentName(x.get("deptName").toString());
                        vm.setHighDepartmentCode(x.get("fatherCode").toString());
                        departmentVMS.add(vm);
                    });*//**//*

                    companyVM.setNccCompanyCode(synInfosVM.get("nccCompanyCode")==null? null : synInfosVM.get("nccCompanyCode").toString());
                    companyVM.setCompanyCode(compCode);
                    companyVM.setCompanyName(compName);

                    companyVMS.add(companyVM);
                }
                msg = this.createEntryBanch(companyVMS);
                if(StringUtils.isNotEmpty(msg)){
                    return msg;
                }
            }*/
        List<CompanyVO> allCompany = getAllCompany();
        String s = departmentService.synInfos(allCompany);
            if(StringUtils.isNotEmpty(s)){
                return s;
            }
        /*} catch (ClientProtocolException e) {
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
        }*/
        return null;

    }

    @Override
    public List<CompanyVO> getAllCompany() {
        List<CompanyVO> companyVOS = new ArrayList<>();
        String where = "";
        Page<CompanyVM> entrysByPage = companyRepository.findEntrysByPage(where);
        for(CompanyVM companyVM : entrysByPage){
            CompanyVO companyVO= new CompanyVO();
            String companyCode = companyVM.getCompanyCode();
            String companyName = companyVM.getCompanyName();
            BeanUtils.copyProperties(companyVM,companyVO);
            companyVO.setCode(companyCode);
            companyVO.setName(companyName);
            companyVOS.add(companyVO);
        }
        return companyVOS;
    }

    private String createEntryBanch(List<CompanyVM> companyVMS) {
        List<CompanyEntity> companyEntities1 = new ArrayList<>();
        List<CompanyEntity> companyEntities2= new ArrayList<>();

        for(CompanyVM companyVM : companyVMS){
            String where  = " and company_code = '"+companyVM.getCompanyCode()+"'";
            Page<CompanyVM> entrysByPage = companyRepository.findEntrysByPage(where);
            if(entrysByPage.size() == 1){
                CompanyEntity companyEntity = new CompanyEntity();
                Long id = entrysByPage.get(0).getId();
                BeanUtils.copyProperties(companyVM,entrysByPage.get(0));
                BeanUtils.copyProperties(entrysByPage.get(0),companyEntity);
                companyEntity.setId(id);
                companyEntities2.add(companyEntity);
            }else if(entrysByPage.size() <= 0){
                CompanyEntity companyEntity = new CompanyEntity();
                BeanUtils.copyProperties(companyVM,companyEntity);
                companyEntities1.add(companyEntity);
            }
        }
        if(companyEntities1.size()>0){
            companyRepository.insertBanch(companyEntities1);
        }
        if(companyEntities2.size()>0){
            companyRepository.updateBanch(companyEntities2);
        }
        return null;
    }

    @Override
    public List company(){
        List<CompanyVM> list=new ArrayList<>();
        String where = "";
        Page<CompanyVM> CompanyVMS = companyRepository.findEntrysByPage(where);
        for(CompanyVM companyVM : CompanyVMS){
            Map<String,String > map = new HashMap<>();
            map.put("company_code",companyVM.getCompanyCode());
            List<DepartmentVM> department = department(map);
            if(department.size()>0){
                companyVM.setChildren(department);
            }else {
                companyVM.setChildren(null);
            }

            companyVM.setCode(companyVM.getCompanyCode());
            companyVM.setName(companyVM.getCompanyName());
            list.add(companyVM);
        }
        return list;
    }
    @Override
    public List companyCopy(){
        List<CompanyVM> list=new ArrayList<>();
//        EmployeeVM employeeVM = employeeService.getEntryInfo();
        String where = "";
        if(!userService.ifAdmin()) {
//            where = " and company_code = '" + employeeVM.getArchivesInCompanyId() + "'";
        }
        Page<CompanyVM> CompanyVMS = companyRepository.findEntrysByPage(where);
        for(CompanyVM companyVM : CompanyVMS){
            Map<String,String > map = new HashMap<>();
            map.put("company_code",companyVM.getCompanyCode());
            List<DepartmentVM> department = department(map);
            if(department.size()>0){
                companyVM.setChildren(department);
            }else {
                companyVM.setChildren(null);
            }

            companyVM.setCode(companyVM.getCompanyCode());
            companyVM.setName(companyVM.getCompanyName());
            list.add(companyVM);
        }
        return list;
    }

    public List<DepartmentVM> department(Map<String,String> map){
        String where  = " and company_code = '"+map.get("company_code")+"'";
        List<DepartmentVM> list=new ArrayList<>();
        Page<DepartmentVM> departmentVMS = departmentRepository.findEntrysByPage(where);
        for(DepartmentVM departmentVM : departmentVMS){
            if(departmentVM.getHighDepartmentCode()==null||departmentVM.getHighDepartmentCode().length()<=0){
                map.put("code",departmentVM.getDepartmentCode());
                List<DepartmentVM> departmentVMList = subDepartment(map);
                if(departmentVMList.size()>0){
                    departmentVM.setChildren(departmentVMList);
                }else {
                    departmentVM.setChildren(null);
                }
                departmentVM.setCode(departmentVM.getDepartmentCode());
                departmentVM.setName(departmentVM.getDepartmentName());
//                map.put("code",departmentVM.getDepartmentCode());
//                departmentVM.setChilden(positon(map));
                list.add(departmentVM);
            }

        }
        return list;
    }
    public List<DepartmentVM> subDepartment(Map<String,String> map){
        String where = " and high_department_code = '"+map.get("code")+"'";
        List<DepartmentVM> list=new ArrayList<>();
        Page<DepartmentVM> departmentVMS = departmentRepository.findEntrysByPage(where);
        if(departmentVMS.size()>0){
            for(DepartmentVM departmentVM : departmentVMS){
                map.put("code",departmentVM.getDepartmentCode());

                List<DepartmentVM> departmentVMList = subDepartment(map);
                if(departmentVMList.size()>0){
                    departmentVM.setChildren(departmentVMList);
                }else {
                    departmentVM.setChildren(null);
                }
                departmentVM.setCode(departmentVM.getDepartmentCode());
                departmentVM.setName(departmentVM.getDepartmentName());
//                map.put("code",departmentVM.getDepartmentCode());
//                departmentVM.setChilden(positon(map));
                list.add(departmentVM);
            }
        }
        return list;
    }

//    public List<PositionVM> positon(Map<String,String> map){
//        String where = " and department_code = '"+map.get("code")+"'";
//        Page<PositionVM> entrysByPage = positionRepository.findEntrysByPage(where);
//
//        return entrysByPage;
//    }
}
