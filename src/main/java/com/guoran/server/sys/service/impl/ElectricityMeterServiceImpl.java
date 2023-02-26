package com.guoran.server.sys.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.guoran.server.auth.b.repository.DepartmentRepository;
import com.guoran.server.auth.vmodel.DepartmentVM;
import com.guoran.server.common.exception.ServiceException;
import com.guoran.server.common.search.DynamicSearch;
import com.guoran.server.common.search.FilterGroup;
import com.guoran.server.common.search.PageQuery;
import com.guoran.server.generator.CodeGenerator;
import com.guoran.server.security.JwtUserUtil;
import com.guoran.server.sys.a.repository.ElectricityMeterRepository;
import com.guoran.server.sys.a.repository.MeterDepartRepository;
import com.guoran.server.sys.model.ElectricityMeterEntity;
import com.guoran.server.sys.model.MeterDepartEntity;
import com.guoran.server.sys.service.ElectricityMeterService;
import com.guoran.server.sys.service.MeterDepartService;
import com.guoran.server.sys.vmodel.ElectricityMeterVM;
import com.guoran.server.sys.vmodel.MeterDepartVM;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 电表信息 服务实现类
 * </p>
 *
 * @author zhangjx
 * @create 2020-08-20
 * @Modify By
 */
@Service
@Transactional
public class ElectricityMeterServiceImpl implements ElectricityMeterService {
    @Autowired
    JwtUserUtil jwtUserUtil;
    @Autowired
    ElectricityMeterRepository electricityMeterRepository;
    @Autowired
    MeterDepartRepository meterDepartRepository;
    @Autowired
    MeterDepartService meterDepartService;
    @Autowired
    DepartmentRepository departmentRepository;
    /**
     * 电表编码自动生成
     */
    @Autowired
    @Qualifier(value = "ELECTRICITY")
    CodeGenerator electricityGenerator;

/*
    AuditStatusEnum auditStatusEnum;
*/

    /**
     * 根据id获取
     *
     * @param id
     * @return
     */
    @Override
    public ElectricityMeterVM getEntryBy(long id) {
        ElectricityMeterVM electricityMeterVM = new ElectricityMeterVM();
        ElectricityMeterEntity electricityMeterEntity = electricityMeterRepository.findById(id);
        String where = " and type_code = '" + electricityMeterEntity.getCode() + "'";
        Page<MeterDepartVM> meterDepartVM = meterDepartRepository.findEntrysByPage(where);
        List<MeterDepartVM> content = new ArrayList<>();
        BeanUtils.copyProperties(electricityMeterEntity, electricityMeterVM);
        for (MeterDepartVM meterDepartVM1 : meterDepartVM) {
            content.add(meterDepartVM1);
        }
        electricityMeterVM.setContent(content);

        return electricityMeterVM;
    }

    /**
     * 创建
     *
     * @param electricityMeterVM
     * @return entity的id
     */
    @Override
    public Long createEntry(ElectricityMeterVM electricityMeterVM) {
        List<ElectricityMeterVM> electricityMeterVMS = electricityMeterRepository.findCodes(electricityMeterVM.getCode());
        if (electricityMeterVMS.size() > 0) {
            throw new ServiceException("电表编号已存在，请重新输入。");
        }
        ElectricityMeterEntity electricityMeterEntity = new ElectricityMeterEntity();
        BeanUtils.copyProperties(electricityMeterVM, electricityMeterEntity);
        //电表编号：GRDB+4位序号
        electricityGenerator.setPre("GRDB");
        String waterCode = electricityGenerator.generateCode();
        electricityMeterEntity.setCode(waterCode);
        electricityMeterEntity.setAuditStatus(3);
        electricityMeterEntity.setCreateBy(jwtUserUtil.getUserName());
        electricityMeterEntity.setCreateTime(new Date());
        electricityMeterRepository.insert(electricityMeterEntity);

        // 插入水电联合
        List<MeterDepartVM> content = electricityMeterVM.getContent();
        List<MeterDepartEntity> meterDepartEntityList = new ArrayList<>();

        if (content != null && content.size() > 0) {
            for (MeterDepartVM conts : content) {
                String departId = conts.getDepartId();
                String where = " and department_code = '" + departId + "'";
                Page<DepartmentVM> departmentVMS = departmentRepository.findEntrysByPage(where);
                MeterDepartEntity entity = new MeterDepartEntity();
                entity.setTypeCode(waterCode);
                entity.setMeterType(conts.getMeterType());
                entity.setDepartId(conts.getDepartId());
                entity.setDepartName(conts.getDepartName());
                entity.setApportionmentRatio(conts.getApportionmentRatio());
                entity.setCreateBy(jwtUserUtil.getUserName());
                entity.setCreateTime(new Date());
                entity.setDepartName(departmentVMS.get(0).getDepartmentName());
                meterDepartEntityList.add(entity);
            }
            meterDepartRepository.insertBatch(meterDepartEntityList);
            return electricityMeterEntity.getId();
        }
        return null;
    }


    /**
     * 修改
     *
     * @param electricityMeterVM
     * @return 是否成功
     */
    @Override
    public boolean updateEntry(ElectricityMeterVM electricityMeterVM) {
        ElectricityMeterEntity electricityMeterEntity = electricityMeterRepository.findById(electricityMeterVM.getId());
        String code = electricityMeterVM.getCode();
        String where2 = " and type_code = '" + electricityMeterEntity.getCode() + "'";
        Page<MeterDepartVM> meterDepartVMS = meterDepartRepository.findEntrysByPage(where2);

        List<Long> list1 = new ArrayList<>(); // 后端获取到的id
        List<Long> list2 = new ArrayList<>();
        List<MeterDepartVM> list3 = new ArrayList<>();
        for (MeterDepartVM meterDepartVMs : meterDepartVMS) {
            list1.add(meterDepartVMs.getId());
        }

        List<MeterDepartVM> content = electricityMeterVM.getContent(); //获取前端传递过来的子表数据
        for (MeterDepartVM conts : content) {
            if (conts.getId() == null) {
                MeterDepartEntity entity = new MeterDepartEntity();
                entity.setDepartId(conts.getDepartId());
                entity.setMeterType(conts.getMeterType());
                entity.setDepartId(conts.getDepartId());
                entity.setDepartName(conts.getDepartName());
                entity.setApportionmentRatio(conts.getApportionmentRatio());
                entity.setCreateBy(jwtUserUtil.getUserName());
                entity.setCreateTime(new Date());
                entity.setTypeCode(code);
                meterDepartRepository.insert(entity);
            } else {
                list2.add(conts.getId());
                list3.add(conts);
            }
        }

        for (int i = 0; i < list1.size(); i++) {
            if (!list2.contains(list1.get(i))) {
                meterDepartRepository.deleteById(list1.get(i));
            }
            if (list3.size() > 0) {
                meterDepartService.updateEntrys(list3);
            }
        }

        BeanUtils.copyProperties(electricityMeterVM, electricityMeterEntity);
        electricityMeterEntity.setUpdateBy(jwtUserUtil.getUserName());
        electricityMeterEntity.setUpdateTime(new Date());
        return electricityMeterRepository.update(electricityMeterEntity);
    }

    /**
     * 删除
     *
     * @param id
     * @return 是否成功
     */
    @Override
    public boolean deleteById(long id) {
        return electricityMeterRepository.deleteById(id);
    }

    /**
     * 多项删除
     *
     * @param id
     * @return 是否成功
     */
    @Override
    public void deleteByIds(String id) {
        String[] ids = id.split(",");
        List<ElectricityMeterVM> entityList = electricityMeterRepository.findByIds(ids);
        List<ElectricityMeterEntity> electricityMeterEntityList = new ArrayList<>();

        for (ElectricityMeterVM electricityMeterEntity : entityList) {
            ElectricityMeterEntity entity = new ElectricityMeterEntity();
            entity.setCode(electricityMeterEntity.getCode());
            electricityMeterEntityList.add(entity);
        }
        meterDepartRepository.deleteByTypeCode(electricityMeterEntityList);
        electricityMeterRepository.deleteByIds(ids);
    }

    /**
     * 分页
     *
     * @param pageQuery
     * @return
     */
    @Override
    public Page<ElectricityMeterVM> findEntrysByPage(PageQuery pageQuery) {
        FilterGroup filterGroup = pageQuery.getWhere();
        //自动转字符串
        String where = DynamicSearch.getInstance().buildWhere(filterGroup);
        PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize(), pageQuery.getOrderBy());
        Page<ElectricityMeterVM> entrysByPage = electricityMeterRepository.findEntrysByPage(where);
        for (ElectricityMeterVM electricityMeterVM : entrysByPage) {
            electricityMeterVM.setMeterReadingTime(electricityMeterVM.getCreateTime());
        }
        return entrysByPage;


    }

    /*
     */

    /**
     * 导出
     *
     * @param
     *//*

    @Override
    public void explort(String ids, HttpServletResponse response, HttpServletRequest request) {
        List<ElectricityMeterVM> electricityMeterVMS = new ArrayList<>();
        if (ids != null && ids.length() > 0) {
            String[] split = ids.split(",");
            String where = " and id in (";
            for (int i = 0; i < split.length; i++) {
                ElectricityMeterEntity electricityMeterEntity = electricityMeterRepository.findById(Long.parseLong(split[i]));
                if (electricityMeterEntity != null && i != split.length - 1) {
                    where = where + split[i] + ",";
                } else {
                    where = where + split[i] + ")";
                }
            }
            electricityMeterVMS = electricityMeterRepository.findEntrysByPage(where);
            if (electricityMeterVMS.size() == 0) {

            }
        } else {
            String where = "";
            electricityMeterVMS = electricityMeterRepository.findEntrysByPage(where);
        }

        String[] title = {"序号", "电表编号", "详细位置", "期初读数", "录入人", "录入时间", "审核状态", "审核人"};
        String fileName = "电表维护.xls";
        String content[][] = new String[electricityMeterVMS.size()][];
        for (int i = 0; i < electricityMeterVMS.size(); i++) {
            content[i] = new String[title.length];
            content[i][0] = "" + (i + 1);
            content[i][1] = electricityMeterVMS.get(i).getCode();
            content[i][2] = electricityMeterVMS.get(i).getAddress();
            content[i][3] = ((electricityMeterVMS.get(i).getInitialRead()) == null ? null : electricityMeterVMS.get(i).getInitialRead().toString());
            content[i][4] = electricityMeterVMS.get(i).getCreateBy();
            SimpleDateFormat sdp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            content[i][5] = sdp.format(electricityMeterVMS.get(i).getCreateTime());
            content[i][6] = ((electricityMeterVMS.get(i).getAuditStatus() == null) ? null : auditStatusEnum.getText(electricityMeterVMS.get(i).getAuditStatus()));
            content[i][7] = (electricityMeterVMS.get(i).getAuditBy());
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


    //发送响应流方法
    public void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            try {
                fileName = new String(fileName.getBytes(), "ISO8859-1");
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            response.setContentType("application/octet-stream;charset=ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


/*
    public String checkelecticity(String ids, Integer state, String dismissReason) {
        if (ids != null && ids.length() > 0) {
            String[] split = ids.split(",");
            List<ElectricityMeterEntity> ides = new ArrayList<>();
            for (String id : split) {
                ElectricityMeterEntity electricityMeterEntity = electricityMeterRepository.findById(Long.parseLong(id));
                if (state == 1) {
                    if (electricityMeterEntity.getAuditStatus().equals(state)) {
                        return JsonResult.failed("审核通过的不能再次审核");
                    }
                    if (electricityMeterEntity.getAuditStatus() == 2) {
                        electricityMeterEntity.setAuditStatus(state);
                        electricityMeterEntity.setAuditBy(jwtUserUtil.getRealName());
                        electricityMeterEntity.setAuditTime(new Date());
                        ides.add(electricityMeterEntity);
                    } else if (electricityMeterEntity.getAuditStatus() != 2) {
                        return JsonResult.failed("只能审核已提交审核的数据");
                    }
                }
                if (state == 2) {
                    if (electricityMeterEntity.getAuditStatus().equals(state)) {
                        return JsonResult.failed("重复提交审核");
                    } else if (electricityMeterEntity.getAuditStatus() != 3 && electricityMeterEntity.getAuditStatus() != 7) {
                        return JsonResult.failed("只能提交未提交或被驳回状态的数据");
                    } else if (electricityMeterEntity.getAuditStatus() == 3 || electricityMeterEntity.getAuditStatus() == 7) {
                        electricityMeterEntity.setAuditStatus(state);
//                        waterMeterEntity.setAuditBy(jwtUserUtil.getRealName());
//                        waterMeterEntity.setAuditTime(new Date());
                        ides.add(electricityMeterEntity);
                    }
                }
                if (state == 3) {
                    if (electricityMeterEntity.getAuditStatus().equals(state)) {
                        return JsonResult.failed("未提交无法撤回");
                    } else if (electricityMeterEntity.getAuditStatus() == 1 || electricityMeterEntity.getAuditStatus() == 2) {
                        electricityMeterEntity.setAuditStatus(state);
//                        waterMeterEntity.setAuditBy(jwtUserUtil.getRealName());
//                        waterMeterEntity.setAuditTime(new Date());
                        ides.add(electricityMeterEntity);
                    } else if (electricityMeterEntity.getAuditStatus() == 7) {
                        return JsonResult.failed("已驳回无法撤回");
                    }
                }
                if (state == 7) {
                    if (electricityMeterEntity.getAuditStatus().equals(state)) {
                        return JsonResult.failed("不能重复驳回");
                    } else if (electricityMeterEntity.getAuditStatus() == 2) {
                        electricityMeterEntity.setAuditStatus(state);
                        electricityMeterEntity.setAuditBy(jwtUserUtil.getRealName());
                        electricityMeterEntity.setAuditTime(new Date());
                        electricityMeterEntity.setDismissReason(dismissReason);
                        ides.add(electricityMeterEntity);
                    } else if (electricityMeterEntity.getAuditStatus() != 2) {
                        return JsonResult.failed("只能驳回审核中的数据");
                    }
                }
                if (state == 4) {
                    if (electricityMeterEntity.getAuditStatus() == 2) {
                        electricityMeterEntity.setAuditStatus(state);
//                        waterMeterEntity.setAuditBy(jwtUserUtil.getRealName());
//                        waterMeterEntity.setAuditTime(new Date());
                        ides.add(electricityMeterEntity);
                    } else {
                        return JsonResult.failed("只能撤回审核中的数据");
                    }
                }
            }
            if (state != 4) {
                electricityMeterRepository.chekcProduct(ides);
            } else {
                state = 3;
                electricityMeterRepository.chekcProduct(ides);
            }
            return null;
        }
        return JsonResult.failed("没有可以进行操作的数据");
    }
*/


}
