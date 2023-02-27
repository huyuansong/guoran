package com.guoran.server.sys.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.guoran.server.common.JsonResult;
import com.guoran.server.common.search.DynamicSearch;
import com.guoran.server.common.search.FilterGroup;
import com.guoran.server.common.search.PageQuery;
import com.guoran.server.generator.CodeGenerator;
import com.guoran.server.security.JwtUserUtil;
import com.guoran.server.sys.a.repository.WarehouseRepository;
import com.guoran.server.sys.model.WarehouseEntity;
import com.guoran.server.sys.service.WarehouseService;
import com.guoran.server.sys.vmodel.WarehouseVM;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 仓库信息表 服务实现类
 * </p>
 *
 * @author zhangjx
 * @create 2020-08-20
 * @Modify By
 */
@Service
public class WarehouseServiceImpl implements WarehouseService {
    @Autowired
    JwtUserUtil jwtUserUtil;
    @Autowired
    WarehouseRepository warehouseRepository;
    /**
     * 仓库编码:CK+4位序号
     */
    @Autowired
    @Qualifier(value = "WAREHOUSE")
    CodeGenerator warehouseCodeGenerator;

    //审核类型枚举
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
    public WarehouseVM getEntryBy(long id) {
        WarehouseEntity warehouseEntity = warehouseRepository.findById(id);
        WarehouseVM warehouseVM = new WarehouseVM();
        BeanUtils.copyProperties(warehouseEntity, warehouseVM);
        return warehouseVM;
    }

    /**
     * 创建
     *
     * @param warehouseVM
     * @return entity的id
     */
    @Override
    public String createEntry(WarehouseVM warehouseVM) {
        String where = "and  warehouse_name= '" + warehouseVM.getWarehouseName() + "'";
        Page<WarehouseVM> entrysByPage = warehouseRepository.findEntrysByPage(where);
        if (entrysByPage.size() > 0) {
            return JsonResult.failed("仓库名称已存在，新增失败！");
        }
        WarehouseEntity warehouseEntity = new WarehouseEntity();
        BeanUtils.copyProperties(warehouseVM, warehouseEntity);
        warehouseEntity.setCreateBy(jwtUserUtil.getUserName());
        //仓库编码:CK+4位序号
        warehouseCodeGenerator.setPre("CK");
        String warehouseCode = warehouseCodeGenerator.generateCode();
        warehouseEntity.setWarehouseCode(warehouseCode);
        warehouseEntity.setCreateTime(new Date());
        warehouseEntity.setAuditStatus(3);
        warehouseRepository.insert(warehouseEntity);
        return null;
    }

    /**
     * 修改
     *
     * @param warehouseVM
     * @return 是否成功
     */
    @Override
    public String updateEntry(WarehouseVM warehouseVM) {
        String where = "and  warehouse_name= '" + warehouseVM.getWarehouseName() + "' and id <> " + warehouseVM.getId();
        Page<WarehouseVM> entrysByPage = warehouseRepository.findEntrysByPage(where);
        if (entrysByPage.size() > 0) {
            return JsonResult.failed("仓库名称已存在，修改失败！");
        }
        WarehouseEntity warehouseEntity = warehouseRepository.findById(warehouseVM.getId());
        warehouseEntity.failWhenConcurrencyViolation(warehouseVM.getConcurrencyVersion());
        BeanUtils.copyProperties(warehouseVM, warehouseEntity);
        warehouseEntity.setUpdateBy(jwtUserUtil.getUserName());
        warehouseEntity.setUpdateTime(new Date());
        warehouseRepository.update(warehouseEntity);
        return null;
    }

    /**
     * 批量删除
     *
     * @param ids
     */
    @Override
    public boolean deleteById(String ids) {
        String[] Ids = ids.split(",");
        return warehouseRepository.deleteById(Ids);
    }

    /**
     * 分页
     *
     * @param pageQuery
     * @return
     */
    @Override
    public Page<WarehouseVM> findEntrysByPage(PageQuery pageQuery) {
        FilterGroup filterGroup = pageQuery.getWhere();
        //自动转字符串
        String where = DynamicSearch.getInstance().buildWhere(filterGroup);
        PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize(), pageQuery.getOrderBy());
        return warehouseRepository.findEntrysByPage(where);
    }

    /**
     * 查询所有仓库
     *
     * @param
     * @return
     */
    @Override
    public List<WarehouseVM> findAllWarehouse() {
        String where = " and 1 = 1 ";
        Page<WarehouseVM> entrysByPage = warehouseRepository.findEntrysByPage(where);
        return entrysByPage;
    }

    /**
     * 审核
     *
     * @param ids
     * @param state
     * @return
     */
    @Override
    public String checkProduct(String ids, Integer state, String dismissReason) {
        WarehouseEntity warehouseEntity = new WarehouseEntity();
        String[] split = ids.split(",");

        if (ids != null && ids.length() > 0) {
            List<String> ides = new ArrayList<>();
            for (String id : split) {
                WarehouseEntity warehouse = warehouseRepository.findById(Long.parseLong(id));
                if (state == 1) {
                    if (warehouse.getAuditStatus().equals(state)) {
                        return JsonResult.failed("审核通过的不能再次审核");
                    }
                    if (warehouse.getAuditStatus() == 2) {
                        warehouseEntity.setAuditBy(jwtUserUtil.getRealName());
                        warehouseEntity.setUpdateTime(new Date());
                        warehouseEntity.setUpdateBy(jwtUserUtil.getUserName());
                        ides.add(id);
                    }
                    if (warehouse.getAuditStatus() != 2) {
                        return JsonResult.failed("只能审核已提交审核的数据");
                    }
                }
                if (state == 2) {
                    if (warehouse.getAuditStatus().equals(state)) {
                        return JsonResult.failed("重复提交审核");
                    }
                    if (warehouse.getAuditStatus() != 3 && warehouse.getAuditStatus() != 7) {
                        return JsonResult.failed("只能提交未提交或被驳回状态的数据");
                    }
                    if (warehouse.getAuditStatus() == 3 || warehouse.getAuditStatus() == 7) {
                        ides.add(id);
                    }
                }
                if (state == 3) {
                    if (warehouse.getAuditStatus().equals(state)) {
                        return JsonResult.failed("未提交无法撤回");
                    }
                    if (warehouse.getAuditStatus() == 1 || warehouse.getAuditStatus() == 2) {
                        ides.add(id);
                    }
                    if (warehouse.getAuditStatus() == 7) {
                        return JsonResult.failed("已驳回无法撤回");
                    }
                }
                if (state == 7) {
                    if (warehouse.getAuditStatus().equals(state)) {
                        return JsonResult.failed("不能重复驳回");
                    }
                    if (warehouse.getAuditStatus() == 2) {
                        warehouseEntity.setAuditBy(jwtUserUtil.getRealName());
                        warehouseEntity.setUpdateTime(new Date());
                        ides.add(id);
                    }
                    if (warehouse.getAuditStatus() != 2) {
                        return JsonResult.failed("只能驳回审核中的数据");
                    }
                }
                if (state == 4) {
                    if (warehouse.getAuditStatus() == 2) {
                        ides.add(id);
                    } else {
                        return JsonResult.failed("只能撤回审核中的数据");
                    }
                }
            }
            if (state != 4) {
                warehouseRepository.chekcProduct(warehouseEntity, ides, state, dismissReason);
            } else {
                state = 3;
                warehouseRepository.chekcProduct(warehouseEntity, ides, state, dismissReason);
            }
            return null;
        }
        return JsonResult.failed("没有能够审核的数据");
    }

    /**
     * 导出
     *
     * @param
     */
/*    @Override
    public void explortWarehouse(HttpServletResponse response, HttpServletRequest request) {
        List<WarehouseVM> warehouseVMS = new ArrayList<>();
        String where = "";
        warehouseVMS = warehouseRepository.findEntrysByPage(where);
        String[] title = {"序号", "仓库编码", "仓库名称", "仓库管理人名称", "联系电话", "审核状态", "创建人", "创建时间"};
        String fileName = "仓库信息.xls";
        String content[][] = new String[warehouseVMS.size()][];
        for (int i = 0; i < warehouseVMS.size(); i++) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            content[i] = new String[title.length];
            content[i][0] = "" + (i + 1);

            content[i][1] = warehouseVMS.get(i).getWarehouseCode();
            content[i][2] = warehouseVMS.get(i).getWarehouseName();
            content[i][3] = warehouseVMS.get(i).getUserName();
            content[i][4] = ((warehouseVMS.get(i).getMobile() == null) ? null : warehouseVMS.get(i).getMobile().toString());
            content[i][5] = ((warehouseVMS.get(i).getAuditStatus() == null) ? null : auditStatusEnum.getText(warehouseVMS.get(i).getAuditStatus()));
            content[i][6] = warehouseVMS.get(i).getCreateBy();
            content[i][7] = simpleDateFormat.format(warehouseVMS.get(i).getCreateTime());
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
    }*/
    @Override
    public String synToGRW(Map[] data, String op) {
        if (data.length <= 0) {
            return "无数据，请确认数据是否正确！";
        }

        if (op.equals("add") || op.equals("upd")) {
            for (int i = 0; i < data.length; i++) {
                Map<String, Object> datum = data[i];
                //仓库主键
                String warehousePk = (String) datum.get("id");
                //所属组织主键
                String pkOrg = (String) datum.get("PK_ORG");
                //所属组织编码
                String orgCode = (String) datum.get("ORGCODE");
                //仓库编码
                String warehouseCode = (String) datum.get("CODE");
                //所属组织名称
                String orgName = (String) datum.get("ORGNAME");
                //启用状态
                Integer enableState = (Integer) datum.get("ENABLESTATE");
                //仓库名称
                String warehouseName = (String) datum.get("NAME");

                if (op.equals("add")) {
                    WarehouseEntity byPK = warehouseRepository.findByPK(warehousePk);
                    if (byPK != null) {
                        return "该仓库信息已存在，请勿重复上传！";
                    }
                    WarehouseEntity warehouseEntity = new WarehouseEntity();
                    warehouseEntity.setWarehousePk(warehousePk);
                    warehouseEntity.setPkOrg(pkOrg);
                    warehouseEntity.setOrgCode(orgCode);
                    warehouseEntity.setWarehouseCode(warehouseCode);
                    warehouseEntity.setOrgName(orgName);
                    warehouseEntity.setEnableState(enableState.longValue());
                    warehouseEntity.setWarehouseName(warehouseName);
                    warehouseEntity.setAuditStatus(3);
                    warehouseRepository.insert(warehouseEntity);
                } else {
                    WarehouseEntity byPK = warehouseRepository.findByPK(warehousePk);
                    if (byPK == null) {
                        return "没有找到主键信息，修改失败！";
                    }
                    byPK.setWarehousePk(warehousePk);
                    byPK.setPkOrg(pkOrg);
                    byPK.setOrgCode(orgCode);
                    byPK.setWarehouseCode(warehouseCode);
                    byPK.setOrgName(orgName);
                    byPK.setEnableState(enableState.longValue());
                    byPK.setWarehouseName(warehouseName);
                    byPK.setUpdateTime(new Date());
                    boolean b = warehouseRepository.updateByPk(byPK);
                    if (!b) {
                        return "修改失败！";
                    }
                }
            }

        } else if (op.equals("del")) {
            String[] Ids = new String[data.length];
            for (int i = 0; i < data.length; i++) {

                WarehouseEntity id = warehouseRepository.findByPK((String) data[i].get("id"));
                if (id == null) {
                    return "没有找到主键信息，删除失败！";
                }
                //根据主键删除
                Map<String, Object> datum = data[i];
                //ID
                Ids[i] = (String) datum.get("id");
            }
            boolean b = warehouseRepository.deleteByPk(Ids);
            if (!b) {
                return "删除失败";
            }
        } else if (op.equals("enable")) {//启用
            for (int i = 0; i < data.length; i++) {
                WarehouseEntity id = warehouseRepository.findByPK((String) data[i].get("id"));
                if (id == null) {
                    return "没有找到主键信息，删除失败！";
                }
                warehouseRepository.enableByPk((String) data[i].get("id"));
            }
        } else if (op.equals("disable")) {//停用
            for (int i = 0; i < data.length; i++) {
                WarehouseEntity id = warehouseRepository.findByPK((String) data[i].get("id"));
                if (id == null) {
                    return "没有找到主键信息，删除失败！";
                }
                warehouseRepository.disableByPk((String) data[i].get("id"));
            }
        } else {
            return "无效的操作类型";
        }
        return null;
    }

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
            response.addHeader("Pragma", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
