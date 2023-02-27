package com.guoran.server.he.production.service.impl;

import com.guoran.server.common.enumeration.InnerMeasurementEnum;
import com.guoran.server.common.utils.ExcelUtil;
import com.guoran.server.he.production.model.ManagementAnnualEntity;
import com.guoran.server.he.production.repository.ManagementAnnualRepository;
import com.guoran.server.he.production.service.ManagementAnnualService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @time 2023/2/2414:24
 * @outhor zhou
 */
@Service
public class ManagementAnnualServiceImpl implements ManagementAnnualService {

    @Autowired
    ManagementAnnualRepository managementAnnualRepository;

    InnerMeasurementEnum innerMeasurementEnum;


    public String deleteBanch(List<Long> ids) {
        managementAnnualRepository.deleteBanch(ids);
        return null;
    }

    @Override
    public void explort(Long[] ids, HttpServletResponse response, HttpServletRequest request) {


        List<ManagementAnnualEntity> managementAnnuals = new ArrayList<>();
//        if(ids!=null&&ids.length>0){
//            for(Long id : ids){
//                ManagementAnnualEntity electricity = managementAnnualRepository.findById(id);
//                if(electricity!=null){
//                    managementAnnuals.add(electricity);
//                }
//            }
//        }else {
        managementAnnuals = managementAnnualRepository.findAllEmpolyee();
//        }
        String[] title = {"序号", "预算年度", "主题信息", "审核状态", "审核人", "审核时间", "创建人", "创建时间", "商品编码", "商品名称", "商品规格", "计量单位(内部)", "总数量", "总均价",
                "总金额", "一月份数量", "一月份均价", "一月份金额", "二月份数量", "二月份均价", "二月份金额", "三月份数量", "三月份均价", "三月份金额", "四月份数量",
                "四月份均价", "四月份金额", "五月份数量", "五月份均价", "五月份金额", "六月份数量", "六月份均价", "六月份金额", "七月份数量", "七月份均价", "七月份金额",
                "八月份数量", "八月份均价", "八月份金额", "九月份数量", "九月份均价", "九月份金额", "十月份数量", "十月份均价", "十月份金额", "十一月份数量", "十一月份均价",
                "十一月份金额", "十二月份数量", "十二月份均价", "十二月份金额", "年度计划驳回原因"};

        SimpleDateFormat sdp = new SimpleDateFormat("yyyyMMddHH:mm:ss");
        String fileName = "年度计划列表.xls";
        String content[][] = new String[managementAnnuals.size()][];
        SimpleDateFormat sdps = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        for (int i = 0; i < managementAnnuals.size(); i++) {

            content[i] = new String[title.length];
            content[i][0] = "" + (i + 1);
            //预算年度
            content[i][1] = "" + managementAnnuals.get(i).getBudgetYear();
            //主题信息
            content[i][2] = "" + managementAnnuals.get(i).getTopicInformation();
            int auditStatus = managementAnnuals.get(i).getAnnualProductionAuditStatus() != null ? managementAnnuals.get(i).getAnnualProductionAuditStatus() : 3;
            if (auditStatus == 3) {
                content[i][3] = "未提交";
            }
            if (auditStatus == 2) {

                content[i][3] = "待审核";
            }
            if (auditStatus == 1) {

                content[i][3] = "审核通过";
            }
            if (auditStatus == 4) {

                content[i][3] = "撤回";
            }
            if (auditStatus == 7) {
                content[i][3] = "已驳回";
            }
            if (auditStatus == 6) {
                content[i][3] = "仓管确认中";
            }
            if (auditStatus == 6) {
                content[i][3] = "仓管确认审核中";
            }

            //审核人
            content[i][4] = managementAnnuals.get(i).getAnnualProductionReviewer();

            //审核时间
            content[i][5] = managementAnnuals.get(i).getAnnualProductionAuditDate() != null ? sdps.format(managementAnnuals.get(i).getAnnualProductionAuditDate()) : "";
            //创建人
            content[i][6] = managementAnnuals.get(i).getCreateBy();
            //创建时间
            content[i][7] = managementAnnuals.get(i).getCreateTime() != null ? sdps.format(managementAnnuals.get(i).getCreateTime()) : "";
            //商品编码
            content[i][8] = managementAnnuals.get(i).getCommodityCode();
            //商品名称
            content[i][9] = managementAnnuals.get(i).getCommodityName();
            //商品规格
            content[i][10] = managementAnnuals.get(i).getCommoditySpecifications();
            //计量单位(内部)
            content[i][11] = innerMeasurementEnum.getText(Integer.parseInt(managementAnnuals.get(i).getCommodityCompany()));
            //总数量
            content[i][12] = "" + managementAnnuals.get(i).getCommodityCount();
            //总均价
            content[i][13] = "" + managementAnnuals.get(i).getTotalAveragePrice();
            //总金额
            content[i][14] = "" + managementAnnuals.get(i).getTotalAmount();
            //一月
            content[i][15] = "" + managementAnnuals.get(i).getJanuaryNumber();
            content[i][16] = "" + managementAnnuals.get(i).getJanuaryAveragePrice();
            content[i][17] = "" + managementAnnuals.get(i).getJanuaryAmountOfMoney();
            //二月
            content[i][18] = "" + managementAnnuals.get(i).getFebruaryNumber();
            content[i][19] = "" + managementAnnuals.get(i).getFebruaryAveragePrice();
            content[i][20] = "" + managementAnnuals.get(i).getFebruaryAmountOfMoney();
            //三月
            content[i][21] = "" + managementAnnuals.get(i).getMarchNumber();
            content[i][22] = "" + managementAnnuals.get(i).getMarchAveragePrice();
            content[i][23] = "" + managementAnnuals.get(i).getMarchAmountOfMoney();
            //四月
            content[i][24] = "" + managementAnnuals.get(i).getAprilNumber();
            content[i][25] = "" + managementAnnuals.get(i).getAprilAveragePrice();
            content[i][26] = "" + managementAnnuals.get(i).getAprilAmountOfMoney();
            //五月
            content[i][27] = "" + managementAnnuals.get(i).getMayAmountOfNumber();
            content[i][28] = "" + managementAnnuals.get(i).getMayAveragePrice();
            content[i][29] = "" + managementAnnuals.get(i).getMayAmountOfMoney();
            //六月
            content[i][30] = "" + managementAnnuals.get(i).getJuneNumber();
            content[i][31] = "" + managementAnnuals.get(i).getJuneAveragePrice();
            content[i][32] = "" + managementAnnuals.get(i).getJuneAmountOfMoney();
            //七月
            content[i][33] = "" + managementAnnuals.get(i).getJulyNumber();
            content[i][34] = "" + managementAnnuals.get(i).getJulyAveragePrice();
            content[i][35] = "" + managementAnnuals.get(i).getJulyAmountOfMoney();
            //八月
            content[i][36] = "" + managementAnnuals.get(i).getAugustNumber();
            content[i][37] = "" + managementAnnuals.get(i).getAugustAveragePrice();
            content[i][38] = "" + managementAnnuals.get(i).getAugustAmountOfMoney();
            //九月
            content[i][39] = "" + managementAnnuals.get(i).getSeptembertNumber();
            content[i][40] = "" + managementAnnuals.get(i).getSeptembertAveragePrice();
            content[i][41] = "" + managementAnnuals.get(i).getSeptembertAmountOfMoney();
            //十月
            content[i][42] = "" + managementAnnuals.get(i).getOctobertNumber();
            content[i][43] = "" + managementAnnuals.get(i).getOctobertAveragePrice();
            content[i][44] = "" + managementAnnuals.get(i).getOctobertAmountOfMoney();
            //十一月
            content[i][45] = "" + managementAnnuals.get(i).getNovembertNumber();
            content[i][46] = "" + managementAnnuals.get(i).getNovembertAveragePrice();
            content[i][47] = "" + managementAnnuals.get(i).getNovembertAmountOfMoney();
            //十二月
            content[i][48] = "" + managementAnnuals.get(i).getDecembertNumber();
            content[i][49] = "" + managementAnnuals.get(i).getDecembertAveragePrice();
            content[i][50] = "" + managementAnnuals.get(i).getDecembertAmountOfMoney();
            //驳回原因
            content[i][51] = managementAnnuals.get(i).getAuditRejectReason();

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

    private void setResponseHeader(HttpServletRequest request, HttpServletResponse response, String fileName) {
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
