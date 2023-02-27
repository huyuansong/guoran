package com.guoran.server.liu.financial.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.guoran.server.auth.service.UserService;
import com.guoran.server.auth.vmodel.UserVM;
import com.guoran.server.common.enumeration.FileCompanyTypeEnum;
import com.guoran.server.common.enumeration.voucher.BusinessTypeEnum;
import com.guoran.server.common.enumeration.voucher.UploadStateEnum;
import com.guoran.server.common.enumeration.voucher.VoucherTypeEnum;
import com.guoran.server.common.exception.ServiceException;
import com.guoran.server.common.search.DynamicSearch;
import com.guoran.server.common.search.FilterGroup;
import com.guoran.server.common.search.PageQuery;
import com.guoran.server.common.utils.utils.ExcelUtil;
import com.guoran.server.common.utils.utils.api.utils.SynBase;
import com.guoran.server.liu.financial.model.VoucherEntity;
import com.guoran.server.liu.financial.repository.VoucherDetailsRepository;
import com.guoran.server.liu.financial.repository.VoucherRepository;
import com.guoran.server.liu.financial.service.VoucherDetailsService;
import com.guoran.server.liu.financial.service.VoucherService;
import com.guoran.server.liu.financial.vmodel.VoucherDetailsVM;
import com.guoran.server.liu.financial.vmodel.VoucherVM;
import com.guoran.server.security.JwtUserUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Transactional
@Service
public class VoucherServiceImpl implements VoucherService {

    @Autowired
    JwtUserUtil jwtUserUtil;
    @Resource
    VoucherRepository voucherRepository;
    @Resource
    VoucherDetailsRepository voucherDetailsRepository;
    @Resource
    VoucherDetailsService voucherDetailsService;

    @Autowired
    UserService userService;

    VoucherTypeEnum voucherTypeEnum;
    //业务类型
    BusinessTypeEnum businessTypeEnum;
    //上传状态
    UploadStateEnum uploadStateEnum;
    //核算主体
    FileCompanyTypeEnum fileCompanyTypeEnum;

    @Override
    public VoucherVM getEntryBy(long id) {
        VoucherEntity voucherEntity = voucherRepository.findById(id);
        VoucherVM voucherVM = new VoucherVM();
        BeanUtils.copyProperties(voucherEntity, voucherVM);
        String where = " and voucher_id = " + id;
        Page<VoucherDetailsVM> entrysByPage = voucherDetailsRepository.findEntrysByPage(where);
        voucherVM.setVoucherDetailsVMS(entrysByPage);
        return voucherVM;
    }

    /**
     * 创建
     *
     * @param voucherVM
     * @return entity的id
     */
    @Override
    public String createEntry(VoucherVM voucherVM) {
        VoucherEntity voucherEntity = new VoucherEntity();
        BeanUtils.copyProperties(voucherVM, voucherEntity);
        /* voucherEntity.setCreateBy(jwtUserUtil.getUserName());*/
        List<VoucherDetailsVM> voucherDetailsVMS = voucherVM.getVoucherDetailsVMS();
        BigDecimal debitAmount = new BigDecimal("0");
        BigDecimal creditAmount = new BigDecimal("0");
        for (VoucherDetailsVM voucherDetailsVM : voucherDetailsVMS) {
            debitAmount = debitAmount.add(voucherDetailsVM.getDebitAmount());
            creditAmount = creditAmount.add(voucherDetailsVM.getCreditAmount());

        }
        voucherEntity.setDebitAmount(debitAmount);
        voucherEntity.setCreditAmount(creditAmount);
        voucherRepository.insert(voucherEntity);
        for (VoucherDetailsVM voucherDetailsVM : voucherDetailsVMS) {
            voucherDetailsVM.setVoucherId(voucherEntity.getId());
        }
        voucherDetailsService.createEntryBanch(voucherDetailsVMS);
        return null;
    }

    /**
     * 修改
     *
     * @param voucherVM
     * @return 是否成功
     */
    @Override
    public String updateEntry(VoucherVM voucherVM) throws ServiceException {
        VoucherEntity voucherEntity = voucherRepository.findById(voucherVM.getId());
        voucherEntity.failWhenConcurrencyViolation(voucherVM.getConcurrencyVersion());
        BeanUtils.copyProperties(voucherVM, voucherEntity);
        /*voucherEntity.setUpdateBy(jwtUserUtil.getUserName());*/
        voucherEntity.setUpdateTime(new Date());
        if (voucherVM.getVoucherDetailsVMS().size() > 0) {
            //借方金额
            BigDecimal debitAmount = new BigDecimal(0);
            //贷方金额
            BigDecimal creditAmount = new BigDecimal(0);
            for (VoucherDetailsVM voucherDetailsVM : voucherVM.getVoucherDetailsVMS()) {
                debitAmount = debitAmount.add(voucherDetailsVM.getDebitAmount());
                creditAmount = creditAmount.add(voucherDetailsVM.getCreditAmount());
            }
            if (debitAmount.compareTo(new BigDecimal(999999.99)) == 1 || creditAmount.compareTo(new BigDecimal(999999.99)) == 1) {
                /*    return JsonResult.failed("对不起，借贷金额计算过大超出限制");*/
            }
            voucherDetailsService.checkData(voucherVM.getId(), voucherVM.getVoucherDetailsVMS());
            voucherEntity.setDebitAmount(debitAmount);
            voucherEntity.setCreditAmount(creditAmount);
        }
        voucherRepository.update(voucherEntity);
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
        String where = "and voucher_id = " + id;
        Page<VoucherDetailsVM> entrysByPage = voucherDetailsRepository.findEntrysByPage(where);
        List<Long> ids = new ArrayList<>();
        for (VoucherDetailsVM vvm : entrysByPage) {
            ids.add(vvm.getId());
        }
        voucherDetailsService.deleteEntryBanch(ids);
        return voucherRepository.deleteById(id);
    }

    /**
     * 分页
     *
     * @param pageQuery
     * @return
     */
    @Override
    public Page<VoucherVM> findEntrysByPage(PageQuery pageQuery) {
        FilterGroup filterGroup = pageQuery.getWhere();
        //自动转字符串
        String where = DynamicSearch.getInstance().buildWhere(filterGroup);
        PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize(), pageQuery.getOrderBy());
        return voucherRepository.findEntrysByPage(where);
    }


    @Override
    public void export(String ids, HttpServletResponse response, HttpServletRequest request) {
        List<VoucherVM> voucherVMS = new ArrayList<>();
//        if(ids!=null && ids.length()>0 && !"".equals(ids)){
//            String[] split = ids.split(",");
//            String where = " and id in ( ";
//            for(int i = 0; i <split.length;i++){
//                VoucherEntity voucherEntity = voucherRepository.findById(Long.parseLong(split[i]));
//                if(voucherEntity!=null){
//                    if(i!= split.length-1){
//                        where = where + split[i] + ",";
//                    }else {
//                        where = where + split[i] + ")";
//                    }
//                }
//            }
//            voucherVMS = voucherRepository.findEntrysByPage(where);
//        }else {
        String where = " order by create_time desc";
        voucherVMS = voucherRepository.findEntrysByPage(where);
//        }
        String[] title = {"序号", "上传状态", "上传错误原因", "业务类型", "编码", "核算主体", "供应商", "凭证类型", "摘要", "借方金额", "贷方金额", "制单日期", "会计期间", "凭证编号", "制单人",};
        String fileName = "凭证信息.xls";
        String[][] content = new String[voucherVMS.size()][];
        for (int i = 0; i < voucherVMS.size(); i++) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            content[i] = new String[title.length];
            content[i][0] = "" + (i + 1);

            content[i][1] = ((voucherVMS.get(i).getUploadState() == null) ? null : uploadStateEnum.getText(voucherVMS.get(i).getUploadState()));
            content[i][2] = voucherVMS.get(i).getUploadErrorContent();
            content[i][3] = ((voucherVMS.get(i).getBusinessType() == null) ? null : businessTypeEnum.getText(voucherVMS.get(i).getBusinessType()));
            content[i][4] = ((voucherVMS.get(i).getOrderCode() == null) ? null : voucherVMS.get(i).getOrderCode());
            if (voucherVMS.get(i).getAccountingEntity() != null && voucherVMS.get(i).getAccountingEntity() != "" && voucherVMS.get(i).getAccountingEntity().length() > 0) {
                content[i][5] = ((voucherVMS.get(i).getAccountingEntity() == null) ? null : fileCompanyTypeEnum.getText(Integer.parseInt(voucherVMS.get(i).getAccountingEntity())));
            } else {
                content[i][5] = "";
            }
            content[i][6] = ((voucherVMS.get(i).getSupplier() == null) ? null : voucherVMS.get(i).getSupplier());
            content[i][7] = ((voucherVMS.get(i).getDocumentType() == null) ? null : voucherTypeEnum.getText(Integer.parseInt(voucherVMS.get(i).getDocumentType())));
            content[i][8] = ((voucherVMS.get(i).getRemark() == null) ? null : voucherVMS.get(i).getRemark());
            content[i][9] = ((voucherVMS.get(i).getDebitAmount() == null) ? null : voucherVMS.get(i).getDebitAmount().toString());
            content[i][10] = ((voucherVMS.get(i).getCreditAmount() == null) ? null : voucherVMS.get(i).getCreditAmount().toString());
            content[i][11] = ((voucherVMS.get(i).getPreparationDate() == null) ? null : simpleDateFormat.format(voucherVMS.get(i).getPreparationDate()));
            content[i][12] = ((voucherVMS.get(i).getAccountingPeriod() == null) ? null : simpleDateFormat.format(voucherVMS.get(i).getAccountingPeriod()));
            content[i][13] = ((voucherVMS.get(i).getVoucherCode() == null) ? null : voucherVMS.get(i).getVoucherCode());
            content[i][14] = ((voucherVMS.get(i).getCreator() == null) ? null : voucherVMS.get(i).getCreator());
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

    @Override
    public Object synNCC(Integer id) {
        VoucherVM entryBy = this.getEntryBy(id);
        List<VoucherDetailsVM> voucherDetailsVMS = entryBy.getVoucherDetailsVMS();

        Map map1 = new HashMap();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String com = null;
        String companyCode = entryBy.getCompanyCode();
        if (companyCode != null) {
            if (companyCode.equals("MY18")) {
                com = "JT0016";
            } else {
                com = "JT0021";
            }
        }
        UserVM entryByName = userService.getEntryByName(entryBy.getCreator());
        map1.put("accbookCode", com);
        map1.put("prepareddate", simpleDateFormat.format(entryBy.getPreparationDate()));
        map1.put("year", new SimpleDateFormat("yyyy").format(entryBy.getPreparationDate()));
        map1.put("period", new SimpleDateFormat("MM").format(entryBy.getPreparationDate()));
        map1.put("vouchertype", "3");
        map1.put("prepared", entryByName.getUserCode());
        map1.put("attachment", 0);
        map1.put("pk_system", "F0GR");
//        map1.put("pk_system","F0OA");
//        Map[] maps = new Map[1];
        Map[] maps = new Map[voucherDetailsVMS.size()];
        for (int i = 0; i < voucherDetailsVMS.size(); i++) {
            Map map2 = new HashMap();
            map2.put("detailindex", voucherDetailsVMS.get(i).getSequence());
            map2.put("explanation", (voucherDetailsVMS.get(i).getRemark() == null || voucherDetailsVMS.get(i).getRemark().equals("")) ? "无摘要" : voucherDetailsVMS.get(i).getRemark());
            map2.put("accountCode", voucherDetailsVMS.get(i).getSubjectCode());
            map2.put("currtypeCode", "CNY");

            if (voucherDetailsVMS.get(i).getBorrowingDirection().equals("0")) { //借方
                map2.put("amount", voucherDetailsVMS.get(i).getDebitAmount());
                map2.put("localdebitamount", voucherDetailsVMS.get(i).getDebitAmount());
                map2.put("groupdebitamount", voucherDetailsVMS.get(i).getDebitAmount());
                map2.put("globaldebitamount", voucherDetailsVMS.get(i).getDebitAmount());
            } else {     //贷方
                map2.put("amount", voucherDetailsVMS.get(i).getCreditAmount());
                map2.put("localcreditamount", voucherDetailsVMS.get(i).getCreditAmount());
                map2.put("groupcreditamount", voucherDetailsVMS.get(i).getCreditAmount());
                map2.put("globalcreditamoun", voucherDetailsVMS.get(i).getCreditAmount());
            }

            map2.put("busidate", simpleDateFormat.format(entryBy.getPreparationDate()));

            if (voucherDetailsVMS.get(i).getAssistAccounting() != null) {
                Map map3 = new HashMap();
                map3.put("checktypecode", voucherDetailsVMS.get(i).getAssistAccounting());
                map3.put("checkvaluecode", com);
                Map[] maps1 = new Map[1];
                maps1[0] = map3;
                map2.put("ass", maps1);
            }

            maps[i] = map2;
        }
        map1.put("detail", maps);
        String requestBody = JSON.toJSONString(map1);
        ResourceBundle resourceBundle = ResourceBundle.getBundle("application", Locale.CHINA);
        //传入test.url配置名称，获取配置信息并赋值给url
        String apiUrl = resourceBundle.getString("voucherInsert");
        Object o = null;
        try {
            o = new SynBase().SynNCC(apiUrl, requestBody.replaceAll("\\\\", ""));
            Map<String, Object> map = JSON.parseObject((String) o, Map.class);
            if ((Boolean) map.get("success")) {
                Map data = (Map) map.get("data");
                String pkVoucher = (String) data.get("pk_voucher");
                int status = 0;
                boolean b = voucherRepository.updatePK(id, pkVoucher, status);
                if (b) {
                    String json = "{code: \"00000000\", data: \"上传成功\", success: true, message: \"上传成功\"}";
                    JSONObject jsonObject = JSONObject.parseObject(json);
                    return jsonObject;
                }
            }
        } catch (Exception e) {
            if (o != null) {
                return o;
            } else {
                String json = "{code: \"00000000\", data: \"服务器异常，上传失败\", success: false, message: \"服务器异常，上传失败\"}";
                JSONObject jsonObject = JSONObject.parseObject(json);
                return jsonObject;
            }
        }
        return o;

    }

    @Override
    public Object deleteNCC(String pk) {
        VoucherVM entryBy = this.getEntryBy(Integer.parseInt(pk));
        String pkVoucher = entryBy.getPkVoucher();
        Map map1 = new HashMap();
        String[] pks = new String[1];
        pks[0] = pkVoucher;
        map1.put("pk_voucher", pks);
        String requestBody = JSON.toJSONString(map1);
        ResourceBundle resourceBundle = ResourceBundle.getBundle("application", Locale.CHINA);
        //传入test.url配置名称，获取配置信息并赋值给url
        String apiUrl = resourceBundle.getString("voucherDelete");
        String s = null;
        try {
            s = new SynBase().SynNCC(apiUrl, requestBody.replaceAll("\\\\", ""));
            Map<String, Object> map = JSON.parseObject((String) s, Map.class);
            if (map.get("success").equals("true")) {
                int status = 1;
                boolean b = voucherRepository.updatePK(Integer.parseInt(pk), pkVoucher, status);
                if (b) {
                    return s;
                }
            }
        } catch (NumberFormatException e) {
            return s;
        }
        return s;
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
