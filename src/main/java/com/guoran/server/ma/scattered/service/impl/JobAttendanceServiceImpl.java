package com.guoran.server.ma.scattered.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.guoran.server.common.exception.ServiceException;
import com.guoran.server.common.search.DynamicSearch;
import com.guoran.server.common.search.FilterGroup;
import com.guoran.server.common.search.PageQuery;
import com.guoran.server.ma.scattered.model.JobAttendanceEntity;
import com.guoran.server.ma.scattered.repository.JobAttendanceRepository;
import com.guoran.server.ma.scattered.service.JobAttendanceService;
import com.guoran.server.ma.scattered.vmodel.JobAttendanceVM;
import com.guoran.server.ma.scattered.vo.ScatteredQualityVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 零工出勤信息 服务实现类
 * </p>
 *
 * @author zhangjx
 * @create 2020-08-28
 * @Modify By
 */
@Service
public class JobAttendanceServiceImpl implements JobAttendanceService {

    @Autowired
    JobAttendanceRepository jobAttendanceRepository;

    /**
     * 根据id获取
     *
     * @param id
     * @return
     */
    @Override
    public JobAttendanceVM getEntryBy(long id) {
        JobAttendanceEntity jobAttendanceEntity = jobAttendanceRepository.findById(id);
        JobAttendanceVM jobAttendanceVM = new JobAttendanceVM();
        BeanUtils.copyProperties(jobAttendanceEntity, jobAttendanceVM);
        return jobAttendanceVM;
    }

    /**
     * 创建
     *
     * @param jobAttendanceVM
     * @return entity的id
     */
    @Override
    public String createEntry(JobAttendanceVM jobAttendanceVM) {
        JobAttendanceEntity jobAttendanceEntity = new JobAttendanceEntity();
        BeanUtils.copyProperties(jobAttendanceVM, jobAttendanceEntity);

        jobAttendanceEntity.setCreateTime(new Date());

        jobAttendanceRepository.insert(jobAttendanceEntity);
        return null;
    }

    /**
     * 修改
     *
     * @param jobAttendanceVM
     * @return 是否成功
     */
    @Override
    public String updateEntry(JobAttendanceVM jobAttendanceVM) throws ServiceException {
        JobAttendanceEntity jobAttendanceEntity = jobAttendanceRepository.findById(jobAttendanceVM.getId());
        jobAttendanceEntity.failWhenConcurrencyViolation(jobAttendanceVM.getConcurrencyVersion());
        BeanUtils.copyProperties(jobAttendanceVM, jobAttendanceEntity);

        jobAttendanceRepository.update(jobAttendanceEntity);
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
        boolean returnCode = false;
        String[] idString = ids.split(",");
        for (String id : idString) {
            Long idl = Long.parseLong(id);
            returnCode = jobAttendanceRepository.deleteById(idl);
        }
        return returnCode;
    }

    /**
     * 分页
     *
     * @param pageQuery
     * @return
     */
    @Override
    public Page<JobAttendanceVM> findEntrysByPage(PageQuery pageQuery) {
        FilterGroup filterGroup = pageQuery.getWhere();


        //自动转字符串
        String where = DynamicSearch.getInstance().buildWhere(filterGroup);
        PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize(), pageQuery.getOrderBy());
        return jobAttendanceRepository.findEntrysByPage(where);
    }

    @Override
    public boolean auditById(ScatteredQualityVo qualityVo) {
        boolean returnCode = false;
        Integer[] ids = qualityVo.getIds();
        Integer status = qualityVo.getStatus();
        String auditRejectReason = qualityVo.getAuditRejectReason();
        for (Integer id : ids) {
            JobAttendanceEntity jobAttendanceEntity = jobAttendanceRepository.findById(id);
            if (jobAttendanceEntity != null) {
                jobAttendanceEntity.setAuditStatus(status);

                jobAttendanceEntity.setUpdateTime(new Date());

                jobAttendanceEntity.setUpdateTime(new Date());
                if (qualityVo.getStatus() != 2 && qualityVo.getStatus() != 3) {

                    jobAttendanceEntity.setAuditTime(new Date());
                    if (qualityVo.getStatus() == 7) {
                        jobAttendanceEntity.setAuditRejectReason(qualityVo.getAuditRejectReason());
                    }
                }
                returnCode = jobAttendanceRepository.auditBy(jobAttendanceEntity);
            }

        }


        return returnCode;
    }

    @Override
    public void explort(Long[] ids, HttpServletResponse response, HttpServletRequest request) {


        List<JobAttendanceEntity> jobAttendanceEntitys = new ArrayList<>();
//            if (ids != null && ids.length > 0) {
//                for (Long id : ids) {
//                    JobAttendanceEntity warehousing = jobAttendanceRepository.findById(id);
//                    if (warehousing != null) {
//                        jobAttendanceEntitys.add(warehousing);
//                    }
//                }
//            } else {
        jobAttendanceEntitys = jobAttendanceRepository.findAllEmpolyee();
//            }

        String[] title = {"序号", "零工姓名", "身份证号", "雇佣部门", "雇佣目的", "出勤开始日", "出勤结束日", "出勤天数", "按天工资(元)", "出勤总工资(元)", "创建人", "创建时间", "审核状态",
                "驳回原因", "审核人", "审核时间"};

        String fileName = "零工出勤信息表.xls";
        String[][] content = new String[jobAttendanceEntitys.size()][];
        for (int i = 0; i < jobAttendanceEntitys.size(); i++) {

            content[i] = new String[title.length];
            content[i][0] = "" + (i + 1);
            content[i][1] = jobAttendanceEntitys.get(i).getOddJobName();
            //   目前电表表中暂时没有车间字段默认为null
            content[i][2] = jobAttendanceEntitys.get(i).getIdCard();
            content[i][3] = jobAttendanceEntitys.get(i).getHireDepartmentName();
            content[i][4] = jobAttendanceEntitys.get(i).getHireContent();
            SimpleDateFormat sdps = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat sdps1 = new SimpleDateFormat("yyyy-MM-dd");
            content[i][5] = jobAttendanceEntitys.get(i).getAttendanceBegintime() != null ? sdps1.format(jobAttendanceEntitys.get(i).getAttendanceBegintime()) : "";
            content[i][6] = jobAttendanceEntitys.get(i).getAttendanceEndttime() != null ? sdps1.format(jobAttendanceEntitys.get(i).getAttendanceEndttime()) : "";
            content[i][7] = "" + jobAttendanceEntitys.get(i).getAttendanceDays();
            content[i][8] = "" + jobAttendanceEntitys.get(i).getDayWages();
            content[i][9] = "" + jobAttendanceEntitys.get(i).getAttendanceTotleWages();
            content[i][10] = jobAttendanceEntitys.get(i).getCreateBy();
            // SimpleDateFormat sdps = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            content[i][11] = jobAttendanceEntitys.get(i).getCreateTime() != null ? sdps.format(jobAttendanceEntitys.get(i).getCreateTime()) : "";


            int auditStatus = jobAttendanceEntitys.get(i).getAuditStatus() != null ? jobAttendanceEntitys.get(i).getAuditStatus() : 3;

            if (auditStatus == 3) {
                content[i][12] = "未提交";
            }
            if (auditStatus == 2) {

                content[i][12] = "待审核";
            }
            if (auditStatus == 1) {

                content[i][12] = "审核通过";
            }
            if (auditStatus == 4) {

                content[i][12] = "撤回";
            }
            if (auditStatus == 7) {
                content[i][12] = "已驳回";
            }
            if (auditStatus == 5) {
                content[i][12] = "仓管确认中";
            }
            if (auditStatus == 6) {
                content[i][12] = "仓管确认审核中";
            }

            content[i][13] = jobAttendanceEntitys.get(i).getAuditRejectReason() == null ? "" : jobAttendanceEntitys.get(i).getAuditRejectReason();
            content[i][14] = jobAttendanceEntitys.get(i).getAuditBy();

            Date auditTime = jobAttendanceEntitys.get(i).getAuditTime();
            if (auditTime != null) {
                content[i][15] = sdps.format(auditTime);
            } else {
                content[i][15] = "";
            }


        }

        //响应到客户端
        try {
            this.setResponseHeader(request, response, fileName);
            OutputStream os = response.getOutputStream();

            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //发送响应流方法
    public void setResponseHeader(HttpServletRequest request, HttpServletResponse response, String fileName) {
        try {
          /*  try {
                String userAgent = request.getHeader("USER-AGENT");
                if(StringUtils.contains(userAgent, "MSIE")){//IE浏览器
                    fileName = new String(fileName.getBytes(), "UTF-8");
                }else if(StringUtils.contains(userAgent, "Mozilla")){//google,火狐浏览器
                    fileName = new String(fileName.getBytes(), "UTF-8");
                }else{
                    fileName = new String(fileName.getBytes(), "ISO8859-1");//其他浏览器
                }
               // fileName = new String(fileName.getBytes(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }*/
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
