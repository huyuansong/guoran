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
import com.guoran.server.generator.water.GasGenerator;
import com.guoran.server.security.JwtUserUtil;
import com.guoran.server.sys.a.repository.GasMeterRepository;
import com.guoran.server.sys.a.repository.MeterDepartRepository;
import com.guoran.server.sys.model.GasMeterEntity;
import com.guoran.server.sys.model.MeterDepartEntity;
import com.guoran.server.sys.service.GasMeterService;
import com.guoran.server.sys.service.MeterDepartService;
import com.guoran.server.sys.vmodel.GasMeterVM;
import com.guoran.server.sys.vmodel.MeterDepartVM;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zcq
 * @create 2020-11-27
 * @Modify By
 */
@Service
public class GasMeterServiceImpl implements GasMeterService {
    @Autowired
    GasMeterRepository gasMeterRepository;
    @Autowired
    JwtUserUtil jwtUserUtil;
    @Autowired
    MeterDepartRepository meterDepartRepository;
    @Autowired
    MeterDepartService meterDepartService;
    @Autowired
    DepartmentRepository departmentRepository;

/*
    AuditStatusEnum auditStatusEnum;
*/

    @Autowired
    @Qualifier(value = "GAS")
    GasGenerator gasGenerator;

    /**
     * 根据id获取
     *
     * @param id
     * @return
     */
    @Override
    public GasMeterVM getEntryBy(long id) {
        GasMeterEntity gasMeterEntity = gasMeterRepository.findById(id);
        String where = " and type_code = '" + gasMeterEntity.getCode() + "'";
        Page<MeterDepartVM> meterDepartVM = meterDepartRepository.findEntrysByPage(where);
        List<MeterDepartVM> content = new ArrayList<>();
        GasMeterVM gasMeterVM = new GasMeterVM();
        BeanUtils.copyProperties(gasMeterEntity, gasMeterVM);
        for (MeterDepartVM meterDepartVM1 : meterDepartVM) {
            content.add(meterDepartVM1);
        }
        gasMeterVM.setContent(content);
        return gasMeterVM;
    }

    /**
     * 创建
     *
     * @param gasMeterVM
     * @return entity的id
     */
    @Override
    public Long createEntry(GasMeterVM gasMeterVM) {
        List<GasMeterVM> gasMeterVMS = gasMeterRepository.findCodes(gasMeterVM.getCode());
        if (gasMeterVMS.size() > 0) {
            throw new ServiceException("气表编号已存在，请重新输入。");
        }
        //气表编号:GAS+4位序号
        gasGenerator.setPre("QBBH");
        String gasCode = gasGenerator.generateCode();
        GasMeterEntity gasMeterEntity = new GasMeterEntity();
        BeanUtils.copyProperties(gasMeterVM, gasMeterEntity);
        gasMeterEntity.setCode(gasCode);
        gasMeterEntity.setAuditStatus(3);
        gasMeterEntity.setCreateBy(jwtUserUtil.getUserName());
        gasMeterEntity.setCreateTime(new Date());
        gasMeterRepository.insert(gasMeterEntity);

        List<MeterDepartVM> content = gasMeterVM.getContent();
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
                entity.setTypeCode(gasCode);
                entity.setCreateBy(jwtUserUtil.getUserName());
                entity.setCreateTime(new Date());
                entity.setDepartName(departmentVMS.get(0).getDepartmentName());
                meterDepartEntityList.add(entity);
            }
            meterDepartRepository.insertBatch(meterDepartEntityList);
            return gasMeterEntity.getId();
        }
        return gasMeterEntity.getId();
    }

    /**
     * 修改
     *
     * @param gasMeterVM
     * @return 是否成功
     */
    @Override
    public boolean updateEntry(GasMeterVM gasMeterVM) throws ServiceException {
        GasMeterEntity gasMeterEntity = gasMeterRepository.findById(gasMeterVM.getId());
        String code = gasMeterEntity.getCode();

        gasMeterEntity.failWhenConcurrencyViolation(gasMeterVM.getConcurrencyVersion());

        String where2 = " and type_code = '" + gasMeterVM.getCode() + "'";

        Page<MeterDepartVM> meterDepartVMS = meterDepartRepository.findEntrysByPage(where2);

        List<Long> list1 = new ArrayList<>(); // 后端获取到的id
        List<Long> list2 = new ArrayList<>();
        List<MeterDepartVM> list3 = new ArrayList<>();
        for (MeterDepartVM meterDepartVMs : meterDepartVMS) {
            list1.add(meterDepartVMs.getId());
        }

        List<MeterDepartVM> content = gasMeterVM.getContent(); //获取前端传递过来的数据
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
        BeanUtils.copyProperties(gasMeterVM, gasMeterEntity);
        gasMeterEntity.setUpdateBy(jwtUserUtil.getUserName());
        gasMeterEntity.setUpdateTime(new Date());
        return gasMeterRepository.update(gasMeterEntity);
    }

    /**
     * 删除
     *
     * @param id
     * @return 是否成功
     */
    @Override
    public boolean deleteById(long id) {
        return gasMeterRepository.deleteById(id);
    }

    /**
     * 分页
     *
     * @param pageQuery
     * @return
     */
    @Override
    public Page<GasMeterVM> findEntrysByPage(PageQuery pageQuery) {
        FilterGroup filterGroup = pageQuery.getWhere();
        //自动转字符串
        String where = DynamicSearch.getInstance().buildWhere(filterGroup);
        PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize(), pageQuery.getOrderBy());
        Page<GasMeterVM> entrysByPage = gasMeterRepository.findEntrysByPage(where);
        for (GasMeterVM gasMeterVM : entrysByPage) {
            gasMeterVM.setMeterReadingTime(gasMeterVM.getCreateTime());
        }
        return entrysByPage;
    }

    @Override
    public String checkGasMeter(String ids, Integer state, String dismissReason) {
        if (ids != null && ids.length() > 0) {
            String[] split = ids.split(",");
            List<GasMeterEntity> ides = new ArrayList<>();
            for (String id : split) {
                GasMeterEntity gasMeterEntity = gasMeterRepository.findById(Long.parseLong(id));
                if (state == 1) {
                    if (gasMeterEntity.getAuditStatus().equals(state)) {
                        return JsonResult.failed("审核通过的不能再次审核");
                    }
                    if (gasMeterEntity.getAuditStatus() == 2) {
                        gasMeterEntity.setAuditStatus(state);
                        gasMeterEntity.setAuditBy(jwtUserUtil.getRealName());
                        gasMeterEntity.setAuditTime(new Date());
                        ides.add(gasMeterEntity);
                    } else if (gasMeterEntity.getAuditStatus() != 2) {
                        return JsonResult.failed("只能审核已提交审核的数据");
                    }
                }
                if (state == 2) {
                    if (gasMeterEntity.getAuditStatus().equals(state)) {
                        return JsonResult.failed("重复提交审核");
                    } else if (gasMeterEntity.getAuditStatus() != 3 && gasMeterEntity.getAuditStatus() != 7) {
                        return JsonResult.failed("只能提交未提交或被驳回状态的数据");
                    } else if (gasMeterEntity.getAuditStatus() == 3 || gasMeterEntity.getAuditStatus() == 7) {
                        gasMeterEntity.setAuditStatus(state);
//                        gasMeterEntity.setAuditBy(jwtUserUtil.getRealName());
//                        gasMeterEntity.setAuditTime(new Date());
                        ides.add(gasMeterEntity);
                    }
                }
                if (state == 3) {
                    if (gasMeterEntity.getAuditStatus().equals(state)) {
                        return JsonResult.failed("未提交无法撤回");
                    } else if (gasMeterEntity.getAuditStatus() == 1 || gasMeterEntity.getAuditStatus() == 2) {
                        gasMeterEntity.setAuditStatus(state);
//                        gasMeterEntity.setAuditBy(jwtUserUtil.getRealName());
//                        gasMeterEntity.setAuditTime(new Date());
                        ides.add(gasMeterEntity);
                    } else if (gasMeterEntity.getAuditStatus() == 7) {
                        return JsonResult.failed("已驳回无法撤回");
                    }
                }
                if (state == 7) {
                    if (gasMeterEntity.getAuditStatus().equals(state)) {
                        return JsonResult.failed("不能重复驳回");
                    } else if (gasMeterEntity.getAuditStatus() == 2) {
                        gasMeterEntity.setAuditStatus(state);
                        gasMeterEntity.setAuditBy(jwtUserUtil.getRealName());
                        gasMeterEntity.setAuditTime(new Date());
                        gasMeterEntity.setDismissReason(dismissReason);
                        ides.add(gasMeterEntity);
                    } else if (gasMeterEntity.getAuditStatus() != 2) {
                        return JsonResult.failed("只能驳回审核中的数据");
                    }
                }
                if (state == 4) {
                    if (gasMeterEntity.getAuditStatus() == 2) {
                        gasMeterEntity.setAuditStatus(state);
//                        gasMeterEntity.setAuditBy(jwtUserUtil.getRealName());
//                        gasMeterEntity.setAuditTime(new Date());
                        ides.add(gasMeterEntity);
                    } else {
                        return JsonResult.failed("只能撤回审核中的数据");
                    }
                }
            }
            if (state != 4) {
                gasMeterRepository.chekcProduct(ides);
            } else {
                state = 3;
                gasMeterRepository.chekcProduct(ides);
            }
            return null;
        }
        return JsonResult.failed("没有可以进行操作的数据");
    }

    /**
     * 导出
     *
     * @param
     */

/*
    public void explort(HttpServletResponse response, HttpServletRequest request) {
        List<GasMeterVM> gasMeterVMS = new ArrayList<>();
//        if (ids != null && ids.length() > 0) {
//            String[] split = ids.split(",");
//            String where = " and id in (";
//            for (int i = 0; i < split.length; i++) {
//                GasMeterEntity gasMeterEntity = gasMeterRepository.findById(Long.parseLong(split[i]));
//                if (gasMeterEntity != null && i != split.length - 1) {
//                    where = where + split[i] + ",";
//                } else {
//                    where = where + split[i] + ")";
//                }
//            }
//            gasMeterVMS = gasMeterRepository.findEntrysByPage(where);
//        } else {
        String where = " order by id desc";
        gasMeterVMS = gasMeterRepository.findEntrysByPage(where);
//        }


        String[] title = {"序号", "气表编号", "详细位置", "期初读数", "录入人", "录入时间", "审核状态", "审核人"};
        String fileName = "气费分摊.xls";
        String content[][] = new String[gasMeterVMS.size()][];
        for (int i = 0; i < gasMeterVMS.size(); i++) {
            content[i] = new String[title.length];
            content[i][0] = "" + (i + 1);
            content[i][1] = gasMeterVMS.get(i).getCode();
            content[i][2] = gasMeterVMS.get(i).getAddress();
            content[i][3] = ((gasMeterVMS.get(i).getInitialGasLevel()) == null ? null : gasMeterVMS.get(i).getInitialGasLevel().toString());
            content[i][4] = gasMeterVMS.get(i).getCreateBy();
            SimpleDateFormat sdp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            content[i][5] = sdp.format(gasMeterVMS.get(i).getCreateTime());
            content[i][6] = ((gasMeterVMS.get(i).getAuditStatus() == null) ? null : auditStatusEnum.getText(gasMeterVMS.get(i).getAuditStatus()));
            content[i][7] = (gasMeterVMS.get(i).getAuditBy());
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
}
