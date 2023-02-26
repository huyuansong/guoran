package com.guoran.server.auth.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.guoran.server.auth.b.repository.DepartmentRepository;
import com.guoran.server.auth.model.DepartmentEntity;
import com.guoran.server.auth.service.DepartmentService;
import com.guoran.server.auth.vmodel.DepartmentVM;
import com.guoran.server.auth.vo.DepartmentVO;
import com.guoran.server.common.JsonResult;
import com.guoran.server.common.exception.ServiceException;
import com.guoran.server.common.search.DynamicSearch;
import com.guoran.server.common.search.FilterGroup;
import com.guoran.server.common.search.PageQuery;
import com.guoran.server.common.utils.NoteUtil;
import com.guoran.server.security.JwtUserUtil;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zhangjx
 * @create 2020-09-10
 * @Modify By
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    JwtUserUtil jwtUserUtil;
    @Autowired
    DepartmentRepository departmentRepository;
    @Value(value = "${hr.department.url}")
    private String departmenturl;

    /**
     * 根据id获取
     *
     * @param id
     * @return
     */
    @Override
    public DepartmentVM getEntryBy(long id) {
        DepartmentEntity departmentEntity = departmentRepository.findById(id);
        DepartmentVM departmentVM = new DepartmentVM();
        BeanUtils.copyProperties(departmentEntity, departmentVM);
        return departmentVM;
    }

    /**
     * 创建
     *
     * @param departmentVM
     * @return entity的id
     */
    @Override
    public String createEntry(DepartmentVM departmentVM) {
        DepartmentEntity departmentEntity = new DepartmentEntity();
        BeanUtils.copyProperties(departmentVM, departmentEntity);
        departmentEntity.setCreateBy(jwtUserUtil.getUserName());
        departmentEntity.setCreateTime(new Date());
        departmentRepository.insert(departmentEntity);
        return null;
    }

    /**
     * 修改
     *
     * @param departmentVM
     * @return 是否成功
     */
    @Override
    public String updateEntry(DepartmentVM departmentVM) throws ServiceException {
        DepartmentEntity departmentEntity = departmentRepository.findById(departmentVM.getId());
        departmentEntity.failWhenConcurrencyViolation(departmentVM.getConcurrencyVersion());
        BeanUtils.copyProperties(departmentVM, departmentEntity);
        departmentEntity.setUpdateBy(jwtUserUtil.getUserName());
        departmentEntity.setUpdateTime(new Date());
        departmentRepository.update(departmentEntity);
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
        return departmentRepository.deleteById(id);
    }

    /**
     * 分页
     *
     * @param pageQuery
     * @return
     */
    @Override
    public Page<DepartmentVM> findEntrysByPage(PageQuery pageQuery) {
        FilterGroup filterGroup = pageQuery.getWhere();
        //自动转字符串
        String where = DynamicSearch.getInstance().buildWhere(filterGroup);
        PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize(), pageQuery.getOrderBy());
        return departmentRepository.findEntrysByPage(where);
    }

    @Override
    public String synInfos() throws IOException {
        NoteUtil noteUtil = new NoteUtil();
        String token = noteUtil.noteLogin();
        String msg = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        System.out.println(token);
        // 创建HttpGet请求，相当于在浏览器输入地址
        HttpGet httpGet = new HttpGet(departmenturl + "?Authorization=" + token);
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
                Map<String, List> result = JSONObject.parseObject(content, Map.class);
                List rows = result.get("data");

                List<DepartmentVM> departmentVMS = new ArrayList<>();
                for (int i = 0; i < rows.size(); i++) {
                    Map synInfosVM = JSONObject.parseObject(rows.get(i).toString(), Map.class);
                    DepartmentVM departmentVM = new DepartmentVM();

                    departmentVM.setDepartmentCode(synInfosVM.get("deptCode").toString());
                    departmentVM.setDepartmentName(synInfosVM.get("deptName").toString());
                    departmentVM.setHighDepartmentCode(synInfosVM.get("fatherDeptCode") == null ? null : synInfosVM.get("fatherDeptCode").toString());
                    departmentVM.setCompanyCode(synInfosVM.get("companyCode").toString());
                    departmentVM.setCompanyName(synInfosVM.get("companyName").toString());
                    departmentVMS.add(departmentVM);
                }
                msg = this.createEntryBanch(departmentVMS);
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

    /**
     * 获取所有部门信息
     */
    @Override
    public List<DepartmentVO> getAllDepartment(String companyCode) {
        String where = " and company_code = '" + companyCode + "'";
        Page<DepartmentVM> departmentVMS = departmentRepository.findEntrysByPage(where);
        List<DepartmentVO> departmentVOList = new ArrayList<>();
        for (DepartmentVM departmentVM : departmentVMS) {
            DepartmentVO departmentVO = new DepartmentVO();
            if (departmentVM.getHighDepartmentCode() == null || departmentVM.getHighDepartmentCode().equals("") || departmentVM.getHighDepartmentCode().length() == 0) {
                String departmentCode = departmentVM.getDepartmentCode();
                String departmentName = departmentVM.getDepartmentName();
                BeanUtils.copyProperties(departmentVM, departmentVO);
                departmentVO.setCode(departmentCode);
                departmentVO.setName(departmentName);
                departmentVOList.add(departmentVO);
            }
        }
        return departmentVOList;
    }

    /**
     * 获取部门列表
     *
     * @return
     */
    @Override
    public List<DepartmentVM> getList() {
        String where = "";
        Page<DepartmentVM> entrysByPage = departmentRepository.findEntrysByPage(where);
        List<DepartmentVM> departmentVMList = new ArrayList<>();
        for (DepartmentVM departmentVM : entrysByPage) {
            departmentVM.setCode(departmentVM.getDepartmentCode());
            departmentVM.setName(departmentVM.getDepartmentName());
            departmentVMList.add(departmentVM);
        }
        return departmentVMList;
    }

    @Override
    public String getCompanyCode(String departmentCode) {
        return departmentRepository.findbyDepartmentCode(departmentCode);
    }

    /**
     * 批量新增
     *
     * @param departmentVMS
     * @return
     */
    private String createEntryBanch(List<DepartmentVM> departmentVMS) {
        List<DepartmentEntity> departmentEntities1 = new ArrayList<>();
        List<DepartmentEntity> departmentEntities2 = new ArrayList<>();

        for (DepartmentVM departmentVM : departmentVMS) {
            String where = " and department_code = '" + departmentVM.getDepartmentCode() + "'";
            Page<DepartmentVM> entrysByPage = departmentRepository.findEntrysByPage(where);
            if (entrysByPage.size() == 1) {
                DepartmentEntity departmentEntity = new DepartmentEntity();
                Long id = entrysByPage.get(0).getId();
                BeanUtils.copyProperties(departmentVM, entrysByPage.get(0));
                BeanUtils.copyProperties(entrysByPage.get(0), departmentEntity);
                departmentEntity.setId(id);
                departmentEntities2.add(departmentEntity);
            } else if (entrysByPage.size() <= 0) {
                DepartmentEntity departmentEntity = new DepartmentEntity();
                BeanUtils.copyProperties(departmentVM, departmentEntity);
                departmentEntities1.add(departmentEntity);
            }
        }
        if (departmentEntities1.size() > 0) {
            departmentRepository.insertBanch(departmentEntities1);
        }
        if (departmentEntities2.size() > 0) {
            departmentRepository.updateBanch(departmentEntities2);
        }
        return null;
    }
}
