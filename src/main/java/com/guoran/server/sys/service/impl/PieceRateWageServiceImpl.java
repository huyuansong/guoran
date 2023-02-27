package com.guoran.server.sys.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.guoran.server.auth.b.repository.DepartmentRepository;
import com.guoran.server.auth.b.repository.PositionRepository;
import com.guoran.server.auth.model.DepartmentEntity;
import com.guoran.server.auth.vmodel.DepartmentVM;
import com.guoran.server.auth.vmodel.PositionVM;
import com.guoran.server.common.JsonResult;
import com.guoran.server.common.exception.ServiceException;
import com.guoran.server.common.search.DynamicSearch;
import com.guoran.server.common.search.FilterGroup;
import com.guoran.server.common.search.PageQuery;
import com.guoran.server.common.utils.StringUtils;
import com.guoran.server.security.JwtUserUtil;
import com.guoran.server.sys.a.repository.PieceRateWageRepository;
import com.guoran.server.sys.a.repository.WageRatioRepository;
import com.guoran.server.sys.model.PieceRateWageEntity;
import com.guoran.server.sys.service.PieceRateWageService;
import com.guoran.server.sys.service.WageRatioService;
import com.guoran.server.sys.vmodel.PieceRateWageVM;
import com.guoran.server.sys.vmodel.WageRatioVM;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 计件工资表 服务实现类
 * </p>
 *
 * @author zhangjx
 * @create 2020-08-21
 * @Modify By
 */
@Service
@Transactional
public class PieceRateWageServiceImpl implements PieceRateWageService {
    @Autowired
    JwtUserUtil jwtUserUtil;
    @Autowired
    PieceRateWageRepository pieceRateWageRepository;
    @Autowired
    WageRatioService wageRatioService;
    @Autowired
    WageRatioRepository wageRatioRepository;
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    PositionRepository positionRepository;
/*    //审核类型枚举
    AuditStatusEnum auditStatusEnum;*/

    /**
     * 根据id获取
     *
     * @param id
     * @return
     */
    @Override
    public PieceRateWageVM getEntryBy(long id) {
        PieceRateWageVM pieceRateWageVM = new PieceRateWageVM();
        PieceRateWageEntity pieceRateWageEntity = pieceRateWageRepository.findById(id);
        String where = " and depart_id = '" + pieceRateWageEntity.getDepartId() + "' and product_code = '" + pieceRateWageEntity.getProductCode() + "'";
        Page<WageRatioVM> entrysByPage = wageRatioRepository.findEntrysByPage(where);
        WageRatioVM[] wageRatioVMS = new WageRatioVM[entrysByPage.size()];
        for (int i = 0; i < entrysByPage.size(); i++) {
            wageRatioVMS[i] = entrysByPage.get(i);
        }
        pieceRateWageVM.setWageRatioVMS(wageRatioVMS);
        BeanUtils.copyProperties(pieceRateWageEntity, pieceRateWageVM);
        return pieceRateWageVM;
    }

    /**
     * 创建
     *
     * @param pieceRateWageVM
     * @return entity的id
     */
    @Override
    public String createEntry(PieceRateWageVM pieceRateWageVM) {
        String where = " and depart_id = '" + pieceRateWageVM.getDepartId() + "' and product_code = '" + pieceRateWageVM.getProductCode() + "'";
        Page<PieceRateWageVM> entrysByPage = pieceRateWageRepository.findEntrysByPage(where);
        if (entrysByPage.size() > 0) {
            return JsonResult.failed("部门或商品信息重复，添加失败");
        }
        WageRatioVM[] wageRatioVMS = pieceRateWageVM.getWageRatioVMS();
        List<WageRatioVM> wageRatioVMList = new ArrayList<>();
        if (wageRatioVMS.length > 0) {

            for (int i = 0; i < wageRatioVMS.length; i++) {

                wageRatioVMS[i].setDepartId(pieceRateWageVM.getDepartId());
                String where1 = " and department_code = '" + pieceRateWageVM.getDepartId() + "'";
                Page<DepartmentVM> entrysByPage1 = departmentRepository.findEntrysByPage(where1);
                wageRatioVMS[i].setDepartName(entrysByPage1.get(0).getDepartmentName());
                wageRatioVMS[i].setProductCode(pieceRateWageVM.getProductCode());
                wageRatioVMS[i].setPieceRates(pieceRateWageVM.getPieceRates());
                String where2 = " and position_code = '" + wageRatioVMS[i].getPositionId() + "'";
                Page<PositionVM> entrysByPage2 = positionRepository.findEntrysByPage(where2);
                wageRatioVMS[i].setPositionName(entrysByPage2.get(0).getPositionName());
                wageRatioVMList.add(wageRatioVMS[i]);
            }
            wageRatioService.createEntryBanch(wageRatioVMList);
        }
        PieceRateWageEntity pieceRateWageEntity = new PieceRateWageEntity();
        String where3 = " and department_code = '" + pieceRateWageVM.getDepartId() + "'";
        Page<DepartmentVM> entrysByPage1 = departmentRepository.findEntrysByPage(where3);
        BeanUtils.copyProperties(pieceRateWageVM, pieceRateWageEntity);
        pieceRateWageEntity.setCreateBy(jwtUserUtil.getUserName());
        pieceRateWageEntity.setCreateTime(new Date());
        pieceRateWageEntity.setAuditStatus(3);
        pieceRateWageEntity.setDepartName(entrysByPage1.get(0).getDepartmentName());
        pieceRateWageRepository.insert(pieceRateWageEntity);
        return null;
    }

    /**
     * 修改
     *
     * @param pieceRateWageVM
     * @return 是否成功
     */
    @Override
    public String updateEntry(PieceRateWageVM pieceRateWageVM) throws ServiceException {
        PieceRateWageEntity pieceRateWageEntity = pieceRateWageRepository.findById(pieceRateWageVM.getId());
        pieceRateWageEntity.failWhenConcurrencyViolation(pieceRateWageVM.getConcurrencyVersion());
        String where1 = " and depart_id = '" + pieceRateWageVM.getDepartId() + "' and product_code = '" + pieceRateWageVM.getProductCode() + "' and id <> " + pieceRateWageVM.getId();
        Page<PieceRateWageVM> entrysByPage1 = pieceRateWageRepository.findEntrysByPage(where1);
        if (entrysByPage1.size() > 0) {
            return JsonResult.failed("部门或商品信息重复，添加失败");
        }
        String where2 = " and depart_id = '" + pieceRateWageEntity.getDepartId() + "' and product_code = '" + pieceRateWageEntity.getProductCode() + "'";
        Page<WageRatioVM> entrysByPage2 = wageRatioRepository.findEntrysByPage(where2);

        List<Long> list1 = new ArrayList<>();//后台获取到的id
        List<Long> list2 = new ArrayList<>();//前段传过来的数据的到的id
        List<WageRatioVM> list3 = new ArrayList<>();//需要修改的子表数据
        List<WageRatioVM> list4 = new ArrayList<>();
        for (int i = 0; i < entrysByPage2.size(); i++) {
            list1.add(entrysByPage2.get(i).getId());
        }
        WageRatioVM[] wageRatioVMS = pieceRateWageVM.getWageRatioVMS();//前段传过来的数据
        for (int i = 0; i < wageRatioVMS.length; i++) {
//            if(wageRatioVMS[i].getId()==null){
            //如果传过来的数据没有id 就创建
            wageRatioVMS[i].setDepartId(pieceRateWageVM.getDepartId());
            wageRatioVMS[i].setDepartName(pieceRateWageVM.getDepartName());
            wageRatioVMS[i].setProductCode(pieceRateWageVM.getProductCode());
            wageRatioVMS[i].setPieceRates(pieceRateWageVM.getPieceRates().multiply(wageRatioVMS[i].getWageRatio()));
//                String where = " and product_code = '"+wageRatioVMS[i].getProductCode()+"' and depart_id = '"+wageRatioVMS[i].getDepartId()+"' and position_id = '"+wageRatioVMS[i].getPositionId()+"'";
//                Page<WageRatioVM> entrysByPage = wageRatioRepository.findEntrysByPage(where);
//                if(entrysByPage!=null&&entrysByPage.size()>0){
//                    return JsonResult.failed("此部门商品下岗位工资比例信息重复，添加失败");
//                }
            list4.add(wageRatioVMS[i]);
//            }else {
//                //如果传过来的数据有id 将id和数据存入集合中
//                list2.add(wageRatioVMS[i].getId());
//                list3.add(wageRatioVMS[i]);
//            }
        }
        if (list1.size() > 0) {
            wageRatioService.deleteBanch(entrysByPage2);
        }
        if (list3.size() > 0) {
            String errorMes1 = wageRatioService.updateBanch(list3);
            if (StringUtils.isNotEmpty(errorMes1)) {
                return errorMes1;
            }
        }
        if (list4.size() > 0) {
            String errorMes2 = wageRatioService.createEntryBanch(list4);
            if (StringUtils.isNotEmpty(errorMes2)) {
                return errorMes2;
            }
        }
        DepartmentEntity bycode = departmentRepository.findBycode(pieceRateWageVM.getDepartId());
        BeanUtils.copyProperties(pieceRateWageVM, pieceRateWageEntity);
        pieceRateWageEntity.setUpdateBy(jwtUserUtil.getUserName());
        pieceRateWageEntity.setUpdateTime(new Date());
        pieceRateWageEntity.setDepartName(bycode.getDepartmentName());
        pieceRateWageRepository.update(pieceRateWageEntity);
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
        String[] Ids = ids.split(",");
        for (String id : Ids) {
            PieceRateWageEntity pieceRateWageEntity = pieceRateWageRepository.findById(Long.parseLong(id));
            String where = " and depart_id = '" + pieceRateWageEntity.getDepartId() + "' and product_code = '" + pieceRateWageEntity.getProductCode() + "'";
            Page<WageRatioVM> entrysByPage = wageRatioRepository.findEntrysByPage(where);
            wageRatioService.deleteBanch(entrysByPage);
        }
        return pieceRateWageRepository.deleteById(Ids);
    }

    /**
     * 分页
     *
     * @param pageQuery
     * @return
     */
    @Override
    public Page<PieceRateWageVM> findEntrysByPage(PageQuery pageQuery) {
        FilterGroup filterGroup = pageQuery.getWhere();
        //自动转字符串
        String where = DynamicSearch.getInstance().buildWhere(filterGroup);
        PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize(), pageQuery.getOrderBy());
        return pieceRateWageRepository.findEntrysByPage(where);
    }

    /**
     * 导出
     *
     * @param ids
     * @param
     * @param
     * @return
     */
//    @Override
//    public boolean explort(String ids, HttpServletResponse response, HttpServletRequest request) {
//        List<PieceRateWageVM> pieceRateWageVMS = new ArrayList<>();
////        if(ids!=null&&ids.length()>0){
////            String[] split = ids.split(",");
////            String where  = " and id in (";
////            for(int i =0 ; i<split.length;i++){
////
////                PieceRateWageEntity pieceRateWageEntity = pieceRateWageRepository.findById(Long.parseLong(split[i]));
////                if(pieceRateWageEntity!=null){
////                    if(i!=split.length-1){
////                        where = where +split[i]+",";
////                    }else {
////                        where = where + split[i]+")";
////                    }
////                }
////            }
////            pieceRateWageVMS = pieceRateWageRepository.findEntrysByPage(where);
////        }else {
////            FilterGroup filterGroup=pageQuery.getWhere();
////            //自动转字符串
////            String where= DynamicSearch.getInstance().buildWhere(filterGroup);
////            PageHelper.startPage(pageQuery.getPageNum(),pageQuery.getPageSize(),pageQuery.getOrderBy());
//        String where = "  order by create_time desc";
//        pieceRateWageVMS = pieceRateWageRepository.findEntrysByPage(where);
////        }
//        String[] title = {"序号", "车间", "生产商品编码", "商品名称", "规格", "计价单价", "审核状态", "审核人", "审核时间", "创建人", "创建时间"};
//        String fileName = "计件信息.xls";
//        String content[][] = new String[pieceRateWageVMS.size()][];
//        for (int i = 0; i < pieceRateWageVMS.size(); i++) {
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            content[i] = new String[title.length];
//            content[i][0] = "" + (i + 1);
//            content[i][1] = pieceRateWageVMS.get(i).getDepartName();
//            content[i][2] = pieceRateWageVMS.get(i).getProductCode();
//            content[i][3] = pieceRateWageVMS.get(i).getProductName();
//            content[i][4] = pieceRateWageVMS.get(i).getSpecifications();
//            content[i][5] = ((pieceRateWageVMS.get(i).getPieceRates()) == null ? null : pieceRateWageVMS.get(i).getPieceRates().toString());
//            content[i][6] = ((pieceRateWageVMS.get(i).getAuditStatus() == null) ? null : auditStatusEnum.getText(pieceRateWageVMS.get(i).getAuditStatus()));
//
//            content[i][7] = pieceRateWageVMS.get(i).getAuditBy();
//            content[i][8] = pieceRateWageVMS.get(i).getAuditTime() == null ? null : simpleDateFormat.format(pieceRateWageVMS.get(i).getAuditTime());
//
//            content[i][9] = pieceRateWageVMS.get(i).getCreateBy();
//            content[i][10] = simpleDateFormat.format(pieceRateWageVMS.get(i).getCreateTime());
//        }
//        HSSFWorkbook hssfWorkbook = ExcelUtil.getHSSFWorkbook(fileName, title, content, null);
//        //响应到客户端
//        try {
//            this.setResponseHeader(response, fileName);
//            OutputStream os = response.getOutputStream();
//            hssfWorkbook.write(os);
//            os.flush();
//            os.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return false;
//    }
    @Override
    public String checkPieceRateWage(String ids, Integer state, String dismissReason) {
        if (ids != null && ids.length() > 0) {
            String[] split = ids.split(",");
            List<PieceRateWageEntity> pieceRateWageEntities = new ArrayList<>();

            for (String id : split) {
                PieceRateWageEntity pieceRateWage = pieceRateWageRepository.findById(Long.parseLong(id));
                if (state == 1) {
                    if (pieceRateWage.getAuditStatus().equals(state)) {
                        return JsonResult.failed("审核通过的不能再次审核");
                    } else if (pieceRateWage.getAuditStatus() == 2) {
                        pieceRateWage.setAuditStatus(state);
                        pieceRateWage.setAuditBy(jwtUserUtil.getRealName());
                        pieceRateWage.setAuditTime(new Date());
                        pieceRateWageEntities.add(pieceRateWage);
                    } else if (pieceRateWage.getAuditStatus() != 2) {
                        return JsonResult.failed("只能审核已提交审核的数据");
                    }
                }
                if (state == 2) {
                    if (pieceRateWage.getAuditStatus().equals(state)) {
                        return JsonResult.failed("重复提交审核");
                    } else if (pieceRateWage.getAuditStatus() != 3 && pieceRateWage.getAuditStatus() != 7) {
                        return JsonResult.failed("只能提交未提交或被驳回状态的数据");
                    } else if (pieceRateWage.getAuditStatus() == 3 || pieceRateWage.getAuditStatus() == 7) {
                        pieceRateWage.setAuditStatus(state);
//                        pieceRateWage.setAuditBy(jwtUserUtil.getRealName());
//                        pieceRateWage.setAuditTime(new Date());
                        pieceRateWageEntities.add(pieceRateWage);
                    }
                }
                if (state == 3) {
                    if (pieceRateWage.getAuditStatus().equals(state)) {
                        return JsonResult.failed("未提交无法撤回");
                    } else if (pieceRateWage.getAuditStatus() == 1 || pieceRateWage.getAuditStatus() == 2) {
                        pieceRateWage.setAuditStatus(state);
                        pieceRateWageEntities.add(pieceRateWage);
                    } else if (pieceRateWage.getAuditStatus() == 7) {
                        return JsonResult.failed("已驳回无法撤回");
                    }
                }
                if (state == 7) {
                    if (pieceRateWage.getAuditStatus().equals(state)) {
                        return JsonResult.failed("不能重复驳回");
                    } else if (pieceRateWage.getAuditStatus() == 2) {
                        pieceRateWage.setAuditStatus(state);
                        pieceRateWage.setAuditBy(jwtUserUtil.getRealName());
                        pieceRateWage.setAuditTime(new Date());
                        pieceRateWage.setDismissReason(dismissReason);
                        pieceRateWageEntities.add(pieceRateWage);
                    } else if (pieceRateWage.getAuditStatus() != 2) {
                        return JsonResult.failed("只能驳回审核中的数据");
                    }
                }
                if (state == 4) {
                    if (pieceRateWage.getAuditStatus() == 2) {
                        pieceRateWage.setAuditStatus(state);
                        pieceRateWage.setAuditBy(jwtUserUtil.getRealName());
                        pieceRateWage.setAuditTime(new Date());
                        pieceRateWageEntities.add(pieceRateWage);
                    } else {
                        return JsonResult.failed("只能撤回审核中的数据");
                    }
                }

            }
            if (state != 4) {
                pieceRateWageRepository.checkPieceRateWage(pieceRateWageEntities);
            } else {
                state = 3;
                pieceRateWageRepository.checkPieceRateWage(pieceRateWageEntities);
            }
            return null;
        }
        return JsonResult.failed("没有可以进行操作的数据");
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
}
