package com.guoran.server.sys.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.guoran.server.auth.b.repository.DepartmentRepository;
import com.guoran.server.auth.vmodel.DepartmentVM;
import com.guoran.server.common.JsonResult;
import com.guoran.server.common.exception.ServiceException;
import com.guoran.server.common.search.DynamicSearch;
import com.guoran.server.common.search.FilterGroup;
import com.guoran.server.common.search.PageQuery;
import com.guoran.server.generator.CodeGenerator;
import com.guoran.server.security.JwtUserUtil;
import com.guoran.server.sys.a.repository.MeterDepartRepository;
import com.guoran.server.sys.a.repository.WaterMeterRepository;
import com.guoran.server.sys.model.MeterDepartEntity;
import com.guoran.server.sys.model.WaterMeterEntity;
import com.guoran.server.sys.service.MeterDepartService;
import com.guoran.server.sys.service.WaterMeterService;
import com.guoran.server.sys.vmodel.MeterDepartVM;
import com.guoran.server.sys.vmodel.WaterMeterVM;
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
 * 水表信息 服务实现类
 * </p>
 *
 * @author zhangjx
 * @create 2020-08-20
 * @Modify By
 */
@Service
@Transactional
public class WaterMeterServiceImpl implements WaterMeterService {
    @Autowired
    JwtUserUtil jwtUserUtil;
    @Autowired
    WaterMeterRepository waterMeterRepository;
    @Autowired
    MeterDepartRepository meterDepartRepository;
    @Autowired
    MeterDepartService meterDepartService;
    @Autowired
    DepartmentRepository departmentRepository;
    /**
     * 水表编码自动生成
     */
    @Autowired
    @Qualifier(value = "WATER")
    CodeGenerator waterGenerator;

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
    public WaterMeterVM getEntryBy(long id) {
        WaterMeterVM waterMeterVM = new WaterMeterVM();
        WaterMeterEntity waterMeterEntity = waterMeterRepository.findById(id);
        String where = " and type_code = '" + waterMeterEntity.getCode() + "'";
        Page<MeterDepartVM> meterDepartVM = meterDepartRepository.findEntrysByPage(where);
        List<MeterDepartVM> content = new ArrayList<>();
        BeanUtils.copyProperties(waterMeterEntity, waterMeterVM);
        for (MeterDepartVM meterDepartVM1 : meterDepartVM) {
            content.add(meterDepartVM1);
        }
        waterMeterVM.setContent(content);
        return waterMeterVM;
    }

    /**
     * 创建
     *
     * @param waterMeterVM
     * @return entity的id
     */
    @Override
    public Long createEntry(WaterMeterVM waterMeterVM) {
        List<WaterMeterVM> waterMeterVMS = waterMeterRepository.findCodes(waterMeterVM.getCode());
        if (waterMeterVMS.size() > 0) {
            throw new ServiceException("水表编号已存在，请重新输入。");
        }

        WaterMeterEntity waterMeterEntity = new WaterMeterEntity();
        BeanUtils.copyProperties(waterMeterVM, waterMeterEntity);
        //水表编号：院校编号加4位序号
        waterGenerator.setPre("GRSB");
        String waterCode = waterGenerator.generateCode();
        waterMeterEntity.setCode(waterCode);
        waterMeterEntity.setAuditStatus(3);
        waterMeterEntity.setCreateBy(jwtUserUtil.getUserName());
        waterMeterEntity.setCreateTime(new Date());
        waterMeterRepository.insert(waterMeterEntity);

        List<MeterDepartVM> content = waterMeterVM.getContent();
        if (content != null && content.size() > 0) {
            List<MeterDepartEntity> meterDepartEntityList = new ArrayList<>();
            for (MeterDepartVM conts : content) {
                String departId = conts.getDepartId();
                String where = " and department_code = '" + departId + "'";
                Page<DepartmentVM> departmentVMS = departmentRepository.findEntrysByPage(where);
                MeterDepartEntity entity = new MeterDepartEntity();
                entity.setMeterType(conts.getMeterType());
                entity.setDepartId(conts.getDepartId());
                entity.setDepartName(conts.getDepartName());
                entity.setApportionmentRatio(conts.getApportionmentRatio());
                entity.setTypeCode(waterCode);
                entity.setCreateBy(jwtUserUtil.getUserName());
                entity.setCreateTime(new Date());
                entity.setDepartName(departmentVMS.get(0).getDepartmentName());
                meterDepartEntityList.add(entity);
            }
            meterDepartRepository.insertBatch(meterDepartEntityList);
            return waterMeterEntity.getId();
        }
        return null;
    }

    /**
     * 修改
     *
     * @param waterMeterVM
     * @return 是否成功
     */
    public boolean updateEntry(WaterMeterVM waterMeterVM) {
        // 根据前端获取的id 进行查询
        WaterMeterEntity waterMeterEntity = waterMeterRepository.findById(waterMeterVM.getId());
        String code = waterMeterEntity.getCode();
        waterMeterEntity.failWhenConcurrencyViolation(waterMeterVM.getConcurrencyVersion());
        //获取到 code值进行去子表中进行查询
        String where2 = " and type_code = '" + waterMeterVM.getCode() + "'";
        // 查询到返回的数据
        Page<MeterDepartVM> meterDepartVMS = meterDepartRepository.findEntrysByPage(where2);

        List<Long> list1 = new ArrayList<>(); // 后端获取到的id
        List<Long> list2 = new ArrayList<>();
        List<MeterDepartVM> list3 = new ArrayList<>();
        for (MeterDepartVM meterDepartVMs : meterDepartVMS) {
            list1.add(meterDepartVMs.getId());
        }

        List<MeterDepartVM> content = waterMeterVM.getContent(); //获取前端传递过来的数据
        for (MeterDepartVM conts : content) {
            if (conts.getId() == null) {
                MeterDepartEntity entity = new MeterDepartEntity();
                entity.setTypeCode(code);
                entity.setDepartId(conts.getDepartId());
                entity.setMeterType(conts.getMeterType());
                entity.setDepartId(conts.getDepartId());
                entity.setDepartName(conts.getDepartName());
                entity.setCreateBy(jwtUserUtil.getUserName());
                entity.setCreateTime(new Date());
                entity.setApportionmentRatio(conts.getApportionmentRatio());
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
        BeanUtils.copyProperties(waterMeterVM, waterMeterEntity);
        waterMeterEntity.setUpdateBy(jwtUserUtil.getUserName());
        waterMeterEntity.setUpdateTime(new Date());
        return waterMeterRepository.update(waterMeterEntity);
    }


    /**
     * 删除
     *
     * @param id
     * @return 是否成功
     */
    @Override
    public boolean deleteById(long id) {
        return waterMeterRepository.deleteById(id);
    }

    /**
     * 多项删除
     *
     * @param id
     * @return 是否成功
     */
    @Override
    public void deleteById(String id) {
        String[] ids = id.split(",");
        List<WaterMeterVM> entityList = waterMeterRepository.findByIds(ids);
        List<WaterMeterEntity> waterMeterEntities = new ArrayList<>();
        for (WaterMeterVM waterMeterVM : entityList) {
            WaterMeterEntity waterMeterEntity = new WaterMeterEntity();
            waterMeterEntity.setCode(waterMeterVM.getCode());
            waterMeterEntities.add(waterMeterEntity);
        }
        meterDepartRepository.deleteByTypeCodes(waterMeterEntities);
        waterMeterRepository.deleteByIds(ids);
    }


    /**
     * 分页
     *
     * @param pageQuery
     * @return
     */
    @Override
    public Page<WaterMeterVM> findEntrysByPage(PageQuery pageQuery) {
        FilterGroup filterGroup = pageQuery.getWhere();
        //自动转字符串
        String where = DynamicSearch.getInstance().buildWhere(filterGroup);
        PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize(), pageQuery.getOrderBy());
        Page<WaterMeterVM> entrysByPage = waterMeterRepository.findEntrysByPage(where);
        for (WaterMeterVM waterMeterVM : entrysByPage) {
            waterMeterVM.setMeterReadingTime(waterMeterVM.getCreateTime());
        }

        return entrysByPage;
    }

    /**
     * 导出
     *
     * @param
     */
   /* @Override
    public void explort(String ids, HttpServletResponse response, HttpServletRequest request) {
        List<WaterMeterVM> waterMeterVMS = new ArrayList<>();
        if (ids != null && ids.length() > 0) {
            String[] split = ids.split(",");
            String where = " and id in (";
            for (int i = 0; i < split.length; i++) {
                WaterMeterEntity warehouseEntity = waterMeterRepository.findById(Long.parseLong(split[i]));
                if (warehouseEntity != null && i != split.length - 1) {
                    where = where + split[i] + ",";
                } else {
                    where = where + split[i] + ")";
                }
            }
            waterMeterVMS = waterMeterRepository.findEntrysByPage(where);
        } else {
            String where = " order by id desc";
            waterMeterVMS = waterMeterRepository.findEntrysByPage(where);
        }


        String[] title = {"序号", "水表编号", "详细位置", "期初水位", "录入人", "录入时间", "审核状态", "审核人"};
        String fileName = "水表维护.xls";
        String content[][] = new String[waterMeterVMS.size()][];
        for (int i = 0; i < waterMeterVMS.size(); i++) {
            content[i] = new String[title.length];
            content[i][0] = "" + (i + 1);
            content[i][1] = waterMeterVMS.get(i).getCode();
            content[i][2] = waterMeterVMS.get(i).getAddress();
            content[i][3] = ((waterMeterVMS.get(i).getInitialWaterLevel()) == null ? null : waterMeterVMS.get(i).getInitialWaterLevel().toString());
            content[i][4] = waterMeterVMS.get(i).getCreateBy();
            SimpleDateFormat sdp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            content[i][5] = sdp.format(waterMeterVMS.get(i).getCreateTime());
            content[i][6] = ((waterMeterVMS.get(i).getAuditStatus() == null) ? null : auditStatusEnum.getText(waterMeterVMS.get(i).getAuditStatus()));
            content[i][7] = (waterMeterVMS.get(i).getAuditBy());
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


    /**
     * 审核
     *
     * @param ids state
     * @return
     */
    @Override
    public String checkWaterMeter(String ids, Integer state, String dismissReason) {
        if (ids != null && ids.length() > 0) {
            String[] split = ids.split(",");
            List<WaterMeterEntity> ides = new ArrayList<>();
            for (String id : split) {
                WaterMeterEntity waterMeterEntity = waterMeterRepository.findById(Long.parseLong(id));
                if (state == 1) {
                    if (waterMeterEntity.getAuditStatus().equals(state)) {
                        return JsonResult.failed("审核通过的不能再次审核");
                    }
                    if (waterMeterEntity.getAuditStatus() == 2) {
                        waterMeterEntity.setAuditStatus(state);
                        waterMeterEntity.setAuditBy(jwtUserUtil.getRealName());
                        waterMeterEntity.setAuditTime(new Date());
                        ides.add(waterMeterEntity);
                    } else if (waterMeterEntity.getAuditStatus() != 2) {
                        return JsonResult.failed("只能审核已提交审核的数据");
                    }
                }
                if (state == 2) {
                    if (waterMeterEntity.getAuditStatus().equals(state)) {
                        return JsonResult.failed("重复提交审核");
                    } else if (waterMeterEntity.getAuditStatus() != 3 && waterMeterEntity.getAuditStatus() != 7) {
                        return JsonResult.failed("只能提交未提交或被驳回状态的数据");
                    } else if (waterMeterEntity.getAuditStatus() == 3 || waterMeterEntity.getAuditStatus() == 7) {
                        waterMeterEntity.setAuditStatus(state);
//                        waterMeterEntity.setAuditBy(jwtUserUtil.getRealName());
//                        waterMeterEntity.setAuditTime(new Date());
                        ides.add(waterMeterEntity);
                    }
                }
                if (state == 3) {
                    if (waterMeterEntity.getAuditStatus().equals(state)) {
                        return JsonResult.failed("未提交无法撤回");
                    } else if (waterMeterEntity.getAuditStatus() == 1 || waterMeterEntity.getAuditStatus() == 2) {
                        waterMeterEntity.setAuditStatus(state);
//                        waterMeterEntity.setAuditBy(jwtUserUtil.getRealName());
//                        waterMeterEntity.setAuditTime(new Date());
                        ides.add(waterMeterEntity);
                    } else if (waterMeterEntity.getAuditStatus() == 7) {
                        return JsonResult.failed("已驳回无法撤回");
                    }
                }
                if (state == 7) {
                    if (waterMeterEntity.getAuditStatus().equals(state)) {
                        return JsonResult.failed("不能重复驳回");
                    } else if (waterMeterEntity.getAuditStatus() == 2) {
                        waterMeterEntity.setAuditStatus(state);
                        waterMeterEntity.setAuditBy(jwtUserUtil.getRealName());
                        waterMeterEntity.setAuditTime(new Date());
                        waterMeterEntity.setDismissReason(dismissReason);
                        ides.add(waterMeterEntity);
                    } else if (waterMeterEntity.getAuditStatus() != 2) {
                        return JsonResult.failed("只能驳回审核中的数据");
                    }
                }
                if (state == 4) {
                    if (waterMeterEntity.getAuditStatus() == 2) {
                        waterMeterEntity.setAuditStatus(state);
//                        waterMeterEntity.setAuditBy(jwtUserUtil.getRealName());
//                        waterMeterEntity.setAuditTime(new Date());
                        ides.add(waterMeterEntity);
                    } else {
                        return JsonResult.failed("只能撤回审核中的数据");
                    }
                }
            }
            if (state != 4) {
                waterMeterRepository.chekcProduct(ides);
            } else {
                state = 3;
                waterMeterRepository.chekcProduct(ides);
            }
            return null;
        }
        return JsonResult.failed("没有可以进行操作的数据");
    }

}
