package com.guoran.server.he.production.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.guoran.server.auth.b.repository.DepartmentRepository;
import com.guoran.server.auth.vmodel.DepartmentVM;
import com.guoran.server.common.enumeration.AuditStatusEnum;
import com.guoran.server.common.exception.ServiceException;
import com.guoran.server.common.search.DynamicSearch;
import com.guoran.server.common.search.FilterGroup;
import com.guoran.server.common.search.PageQuery;
import com.guoran.server.common.utils.ExcelUtil;
import com.guoran.server.he.production.model.ManagementElectricityEntity;
import com.guoran.server.he.production.repository.ManagementElectricityRepository;
import com.guoran.server.he.production.service.ManagementElectricityService;
import com.guoran.server.he.production.vmodel.ManagementElectricityVM;
import com.guoran.server.he.production.vo.ProductionQualityVo;
import com.guoran.server.security.JwtUserUtil;
import com.guoran.server.sys.a.repository.ElectricityMeterRepository;
import com.guoran.server.sys.a.repository.MeterDepartRepository;
import com.guoran.server.sys.vmodel.ElectricityMeterVM;
import com.guoran.server.sys.vmodel.MeterDepartVM;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @time 2023/2/2714:32
 * @outhor zhou
 */
@Service
public class ManagementElectricityServiceImpl implements ManagementElectricityService {
    @Autowired
    JwtUserUtil jwtUserUtil;

    @Autowired
    ManagementElectricityRepository managementElectricityRepository;
    @Autowired
    MeterDepartRepository meterDepartRepository;
    @Autowired
    ElectricityMeterRepository electricityMeterRepository;
    @Autowired
    DepartmentRepository departmentRepository;

    AuditStatusEnum auditStatusEnum;

    /**
     * 根据id获取
     *
     * @param id
     * @return
     */
    @Override
    public ManagementElectricityVM getEntryBy(long id) {
        ManagementElectricityEntity managementElectricityEntity = managementElectricityRepository.findById(id);
        ManagementElectricityVM managementElectricityVM = new ManagementElectricityVM();
        BeanUtils.copyProperties(managementElectricityEntity, managementElectricityVM);
        return managementElectricityVM;
    }

    /**
     * 创建
     *
     * @param managementElectricityVM
     * @return entity的id
     */
    @Override
    public String createEntry(ManagementElectricityVM managementElectricityVM) {
        String ids = "";
        String where = " and type_code = '" + managementElectricityVM.getMeterNumber() + "'";
        Page<MeterDepartVM> meterDepartVMS = meterDepartRepository.findEntrysByPage(where);
        for (int i = 0; i < meterDepartVMS.size(); i++) {
            if (i == (meterDepartVMS.size() - 1)) {
                ids = ids + meterDepartVMS.get(i).getDepartId();
            } else {
                ids = ids + meterDepartVMS.get(i).getDepartId() + ",";
            }
        }
        ManagementElectricityEntity managementElectricityEntity = new ManagementElectricityEntity();
        BeanUtils.copyProperties(managementElectricityVM, managementElectricityEntity);
        managementElectricityEntity.setDepartmentId(ids);
        managementElectricityEntity.setCreateBy(jwtUserUtil.getUserName());
        managementElectricityEntity.setCreateTime(new Date());
        managementElectricityRepository.insert(managementElectricityEntity);
        return null;
    }

    /**
     * 修改
     *
     * @param managementElectricityVM
     * @return 是否成功
     */
    @Override
    public String updateEntry(ManagementElectricityVM managementElectricityVM) throws ServiceException {
        ManagementElectricityEntity managementElectricityEntity = managementElectricityRepository.findById(managementElectricityVM.getId());
        managementElectricityEntity.failWhenConcurrencyViolation(managementElectricityVM.getConcurrencyVersion());
        String ids = "";
        String where = " and type_code = '" + managementElectricityVM.getMeterNumber() + "'";
        Page<MeterDepartVM> meterDepartVMS = meterDepartRepository.findEntrysByPage(where);
        for (int i = 0; i < meterDepartVMS.size(); i++) {
            if (i == (meterDepartVMS.size() - 1)) {
                ids = ids + meterDepartVMS.get(i).getDepartId();
            } else {
                ids = ids + meterDepartVMS.get(i).getDepartId() + ",";
            }
        }
        BeanUtils.copyProperties(managementElectricityVM, managementElectricityEntity);
        managementElectricityEntity.setDepartmentId(ids);
        managementElectricityEntity.setUpdateBy(jwtUserUtil.getUserName());
        managementElectricityEntity.setUpdateTime(new Date());
        managementElectricityRepository.update(managementElectricityEntity);
        return null;
    }

    /**
     * 删除
     *
     * @param ids
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String ids) {
        String[] idString = ids.split(",");
        return managementElectricityRepository.deleteBanch(idString);
    }

    /**
     * 分页
     *
     * @param pageQuery
     * @return
     */
    @Override
    public Page<ManagementElectricityVM> findEntrysByPage(PageQuery pageQuery) {
        FilterGroup filterGroup = pageQuery.getWhere();
        //自动转字符串
        String where = DynamicSearch.getInstance().buildWhere(filterGroup);
        PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize(), pageQuery.getOrderBy());
        return managementElectricityRepository.findEntrysByPage(where);
    }

    @Override
    public boolean auditById(ProductionQualityVo auditUpdate) {
        boolean returnCorde = false;
        Long[] ids = auditUpdate.getIds();
        int status = auditUpdate.getStatus();
        String auditRejectReason = auditUpdate.getAuditRejectReason();
        for (Long id : ids) {
            ManagementElectricityEntity managementElectricityEntity = managementElectricityRepository.findById(id);


            managementElectricityEntity.setAuditStatus(status);

            if (status == 1 || status == 7) {
                managementElectricityEntity.setAuditName(jwtUserUtil.getRealName());
                managementElectricityEntity.setAuditTime(new Date());
            }
            if (status == 7) {
                managementElectricityEntity.setAuditRejectReason(auditRejectReason);
            }
            managementElectricityEntity.setUpdateBy(jwtUserUtil.getUserName());
            managementElectricityEntity.setUpdateTime(new Date());
            returnCorde = managementElectricityRepository.auditBy(managementElectricityEntity);
        }
        return returnCorde;
    }

    @Override
    public void explort(Long[] ids, HttpServletResponse response, HttpServletRequest request) {

        List<ManagementElectricityEntity> managementAnnuals = new ArrayList<>();
//        if(ids!=null&&ids.length>0){
//            for(Long id : ids){
//                ManagementElectricityEntity electricity = managementElectricityRepository.findById(id);
//                if(electricity!=null){
//                    managementAnnuals.add(electricity);
//                }
//            }
//        }else {
        managementAnnuals = managementElectricityRepository.findAllEmpolyee();
//        }

        String[] title = {"序号", "车间", "电表编号", "详细位置", "抄表时间", "上次读数", "本次读数", "增量", "倍率", "用电量", "抄表人", "录入人", "录入日期", "审核状态"};

        String fileName = "电费分摊表.xls";
        String content[][] = new String[managementAnnuals.size()][];
        for (int i = 0; i < managementAnnuals.size(); i++) {

            content[i] = new String[title.length];
            content[i][0] = "" + (i + 1);
            content[i][1] = managementAnnuals.get(i).getDepartmentName();
            //   目前电表表中暂时没有车间字段默认为null
            content[i][2] = managementAnnuals.get(i).getMeterNumber();
            content[i][3] = managementAnnuals.get(i).getDetailedLocation();
            SimpleDateFormat sdps = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            content[i][4] = managementAnnuals.get(i).getMeterReadingTime() != null ? sdps.format(managementAnnuals.get(i).getMeterReadingTime()) : "";
            content[i][5] = managementAnnuals.get(i).getLastReading() == null ? null : managementAnnuals.get(i).getLastReading().toString();
            content[i][6] = managementAnnuals.get(i).getThisReading() == null ? null : managementAnnuals.get(i).getThisReading().toString();
            content[i][7] = managementAnnuals.get(i).getIncrement() == null ? null : managementAnnuals.get(i).getIncrement().toString();
            content[i][8] = managementAnnuals.get(i).getOverride() == null ? null : new BigDecimal(String.valueOf(managementAnnuals.get(i).getOverride())).setScale(0, BigDecimal.ROUND_DOWN).toString();
            content[i][9] = managementAnnuals.get(i).getElectricityConsumption() == null ? null : managementAnnuals.get(i).getElectricityConsumption().toString();
            content[i][10] = managementAnnuals.get(i).getMeterReader();
            content[i][11] = managementAnnuals.get(i).getCreateBy();
            content[i][12] = managementAnnuals.get(i).getCreateTime() != null ? sdps.format(managementAnnuals.get(i).getCreateTime()) : "";
            content[i][13] = managementAnnuals.get(i).getAuditStatus() == null ? null : auditStatusEnum.getText(managementAnnuals.get(i).getAuditStatus());


        }
        HSSFWorkbook hssfWorkbook = ExcelUtil.getHSSFWorkbook(fileName, title, content, null);
        //响应到客户端
        try {
            this.setResponseHeader(request, response, fileName);
            OutputStream os = response.getOutputStream();
            hssfWorkbook.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public ManagementElectricityVM getLastReading(String code) {
        String where = " and audit_status = 1  and meter_number = '" + code + "' ORDER BY this_reading desc";
        Page<ManagementElectricityVM> ManagementElectricityVMS = managementElectricityRepository.findEntrysByPage(where);
        if (ManagementElectricityVMS.size() > 0) {
            ManagementElectricityVM managementElectricityVM = ManagementElectricityVMS.get(0);
            managementElectricityVM.setLastReading(managementElectricityVM.getThisReading());
            managementElectricityVM.setMeterReadingTime(managementElectricityVM.getMeterReadingTime());
            return managementElectricityVM;
        } else {
            ManagementElectricityVM managementElectricityVM = new ManagementElectricityVM();
            String where1 = " and code = '" + code + "'";
            Page<ElectricityMeterVM> ElectricityMeterVMS = electricityMeterRepository.findEntrysByPage(where1);
            String where2 = " and type_code = '" + code + "'";
            Page<MeterDepartVM> meterDepartVMS = meterDepartRepository.findEntrysByPage(where2);
            managementElectricityVM.setLastReading(ElectricityMeterVMS.get(0).getInitialRead());
            managementElectricityVM.setDetailedLocation(ElectricityMeterVMS.get(0).getAddress());
            managementElectricityVM.setMeterReadingTime(ElectricityMeterVMS.get(0).getCreateTime());
            String departmentCode = "";
            String departmentName = "";
            for (int i = 0; i < meterDepartVMS.size(); i++) {
                if (!departmentCode.contains(meterDepartVMS.get(i).getDepartId())) {
                    departmentCode += meterDepartVMS.get(i).getDepartId() + ",";
                    String where3 = " and department_code = '" + meterDepartVMS.get(i).getDepartId() + "'";
                    Page<DepartmentVM> DepartmentVMS = departmentRepository.findEntrysByPage(where3);
                    departmentName += DepartmentVMS.get(0).getDepartmentName() + "'";
                }
            }
            managementElectricityVM.setDepartmentId(departmentCode);
            managementElectricityVM.setOverride(ElectricityMeterVMS.get(0).getOverride());
            managementElectricityVM.setDepartmentName(departmentName);
            managementElectricityVM.setMeterNumber(ElectricityMeterVMS.get(0).getCode());
            return managementElectricityVM;
        }
    }

    //发送响应流方法
    public void setResponseHeader(HttpServletRequest request, HttpServletResponse response, String fileName) {
        try {

            fileName = new String(fileName.getBytes(), "ISO8859-1");
            System.err.println(fileName);
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
