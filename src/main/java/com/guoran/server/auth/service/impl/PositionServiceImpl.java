package com.guoran.server.auth.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.guoran.server.auth.b.repository.DepartmentRepository;
import com.guoran.server.auth.b.repository.PositionRepository;
import com.guoran.server.auth.model.PositionEntity;
import com.guoran.server.auth.service.PositionService;
import com.guoran.server.auth.vmodel.PositionVM;
import com.guoran.server.auth.vo.PositionVO;
import com.guoran.server.common.JsonResult;
import com.guoran.server.common.exception.ServiceException;
import com.guoran.server.common.search.DynamicSearch;
import com.guoran.server.common.search.FilterGroup;
import com.guoran.server.common.search.PageQuery;
import com.guoran.server.common.utils.NoteUtil;
import com.guoran.server.security.JwtUserUtil;
import com.guoran.server.sys.a.repository.EmployeeRepository;
import com.guoran.server.sys.a.repository.WageRatioRepository;
import com.guoran.server.sys.vmodel.EmployeeVM;
import com.guoran.server.sys.vmodel.WageRatioVM;
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
public class PositionServiceImpl implements PositionService {
    @Autowired
    JwtUserUtil jwtUserUtil;
    @Autowired
    PositionRepository positionRepository;
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    WageRatioRepository wageRatioRepository;

    @Value(value = "${hr.position.url}")
    private String positionturl;

    /**
     * 根据id获取
     *
     * @param id
     * @return
     */
    @Override
    public PositionVM getEntryBy(long id) {
        PositionEntity positionEntity = positionRepository.findById(id);
        PositionVM positionVM = new PositionVM();
        BeanUtils.copyProperties(positionEntity, positionVM);
        return positionVM;
    }

    /**
     * 创建
     *
     * @param positionVM
     * @return entity的id
     */
    @Override
    public String createEntry(PositionVM positionVM) {
        String where = " and position_code = '" + positionVM.getPositionCode() + "'";
        Page<PositionVM> entrysByPage = positionRepository.findEntrysByPage(where);
        if (entrysByPage.size() > 0) {
            return JsonResult.failed("岗位信息已存在，请勿重复添加！");
        }
        PositionEntity positionEntity = new PositionEntity();
        BeanUtils.copyProperties(positionVM, positionEntity);
        positionEntity.setCreateBy(jwtUserUtil.getUserName());
        positionEntity.setCreateTime(new Date());
        positionRepository.insert(positionEntity);
        return null;
    }

    /**
     * 修改
     *
     * @param positionVM
     * @return 是否成功
     */
    @Override
    public String updateEntry(PositionVM positionVM) throws ServiceException {
        PositionEntity positionEntity = positionRepository.findById(positionVM.getId());
        if (positionEntity.getIsAdd() != 1) {
            return JsonResult.failed("该条信息不允许被修改！");
        }
        String where = " and position_code = '" + positionVM.getPositionCode() + "' and id <> " + positionVM.getId();
        Page<PositionVM> entrysByPage = positionRepository.findEntrysByPage(where);
        if (entrysByPage.size() > 0) {
            return JsonResult.failed("岗位信息重复，修改失败！");
        }
        String where1 = " and position_id = '" + positionVM.getPositionCode() + "'";
        Page<EmployeeVM> entrysByPage1 = employeeRepository.findEntrysByPage(where1);
        String where2 = " and position_id = '" + positionVM.getPositionCode() + "'";
        Page<WageRatioVM> entrysByPage2 = wageRatioRepository.findEntrysByPage(where2);
        if (entrysByPage1.size() > 0 || entrysByPage2.size() > 0) {
            return JsonResult.failed("该岗位已存在绑定信息，无法编辑!");
        }
        positionEntity.failWhenConcurrencyViolation(positionVM.getConcurrencyVersion());
        BeanUtils.copyProperties(positionVM, positionEntity);
        positionEntity.setUpdateBy(jwtUserUtil.getUserName());
        positionEntity.setUpdateTime(new Date());
        positionRepository.update(positionEntity);
        return null;
    }

    /**
     * 删除
     *
     * @param id
     * @return 是否成功
     */
    @Override
    public String deleteById(long id) {
        PositionVM entryBy = this.getEntryBy(id);
        if (entryBy.getIsAdd() != 1) {
            return JsonResult.failed("该信息不允许被删除!");
        }
        String where = " and position_id = '" + entryBy.getPositionCode() + "'";
        Page<EmployeeVM> entrysByPage = employeeRepository.findEntrysByPage(where);
        String where1 = " and position_id = '" + entryBy.getPositionCode() + "'";
        Page<WageRatioVM> entrysByPage1 = wageRatioRepository.findEntrysByPage(where1);
        if (entrysByPage.size() > 0 || entrysByPage1.size() > 0) {
            return JsonResult.failed("该岗位已存在绑定信息，无法删除!");
        }
        positionRepository.deleteById(id);
        return null;
    }

    /**
     * 分页
     *
     * @param pageQuery
     * @return
     */
    @Override
    public Page<PositionVM> findEntrysByPage(PageQuery pageQuery) {
        FilterGroup filterGroup = pageQuery.getWhere();
        //自动转字符串
        String where = DynamicSearch.getInstance().buildWhere(filterGroup);
        PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize(), pageQuery.getOrderBy());
        return positionRepository.findEntrysByPage(where);
    }

    @Override
    public String synInfos() throws IOException {
        System.out.println(positionturl);
        NoteUtil noteUtil = new NoteUtil();
        String token = noteUtil.noteLogin();
        String msg = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        System.out.println(token);
        // 创建HttpGet请求，相当于在浏览器输入地址
        HttpGet httpGet = new HttpGet(positionturl + "?Authorization=" + token);
        CloseableHttpResponse response = null;
        try {
            // 执行请求，相当于敲完地址后按下回车。获取响应
            response = httpclient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() != 200) {
                return JsonResult.failed("当前网络较差或后台出现问题，同步失败，请稍后再试。");
            }
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                // 解析响应，获取数据
                String content = EntityUtils.toString(response.getEntity());
                Map<String, List> result = JSONObject.parseObject(content, Map.class);
                List rows = result.get("data");

                List<PositionVM> positionVMS = new ArrayList<>();
                for (int i = 0; i < rows.size(); i++) {
                    Map synInfosVM = JSONObject.parseObject(rows.get(i).toString(), Map.class);
                    PositionVM positionVM = new PositionVM();
                    positionVM.setDepartmentCode(synInfosVM.get("deptCode") == null ? null : synInfosVM.get("deptCode").toString());
                    positionVM.setDepartmentName(synInfosVM.get("deptName") == null ? null : synInfosVM.get("deptName").toString());
                    positionVM.setPositionCode(synInfosVM.get("postCode") == null ? null : synInfosVM.get("postCode").toString());
                    positionVM.setPositionName(synInfosVM.get("postName") == null ? null : synInfosVM.get("postName").toString());
                    positionVMS.add(positionVM);
                }
                msg = this.createEntryBanch(positionVMS);
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

    @Override
    public List<PositionVO> getAllPostion(String code) {

        String where = " and department_code = '" + code + "'";
        List<PositionVO> positionVOS = new ArrayList<>();
        Page<PositionVM> positionVMS = positionRepository.findEntrysByPage(where);
        for (PositionVM positionVM : positionVMS) {
            PositionVO positionVO = new PositionVO();
            String positionCode = positionVM.getPositionCode();
            String positionName = positionVM.getPositionName();
            BeanUtils.copyProperties(positionVM, positionVO);
            positionVO.setCode(positionCode);
            positionVO.setName(positionName);
            positionVO.setPositionDetail(positionVM.getPositionDetail());
            positionVOS.add(positionVO);
        }
        return positionVOS;
    }

    /**
     * 批量添加
     *
     * @param positionVMS
     * @return
     */
    private String createEntryBanch(List<PositionVM> positionVMS) {
        List<PositionEntity> positionEntities = new ArrayList<>();

        for (PositionVM positionVM : positionVMS) {
            String where = " and position_code = '" + positionVM.getPositionCode() + "'";
            Page<PositionVM> entrysByPage = positionRepository.findEntrysByPage(where);
            if (entrysByPage.size() == 1) {
                PositionEntity positionEntity = positionRepository.findById(entrysByPage.get(0).getId());
                BeanUtils.copyProperties(positionVM, positionEntity);
                positionRepository.update(positionEntity);
            } else if (entrysByPage.size() <= 0) {
                PositionEntity positionEntity = new PositionEntity();
                BeanUtils.copyProperties(positionVM, positionEntity);
                positionEntities.add(positionEntity);
            }
        }
        if (positionEntities.size() > 0) {
            positionRepository.insertBanch(positionEntities);
        }
        return null;
    }
}
