package com.guoran.server.sys.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.guoran.server.common.JsonResult;
import com.guoran.server.common.search.DynamicSearch;
import com.guoran.server.common.search.FilterGroup;
import com.guoran.server.common.search.PageQuery;
import com.guoran.server.generator.CodeGenerator;
import com.guoran.server.security.JwtUserUtil;
import com.guoran.server.sys.a.repository.PieceRateWageRepository;
import com.guoran.server.sys.a.repository.ProductRepository;
import com.guoran.server.sys.a.repository.ProductTypeRepository;
import com.guoran.server.sys.model.ProductEntity;
import com.guoran.server.sys.model.ProductTypeEntity;
import com.guoran.server.sys.service.PieceRateWageService;
import com.guoran.server.sys.service.ProductService;
import com.guoran.server.sys.vmodel.PieceRateWageVM;
import com.guoran.server.sys.vmodel.ProductVM;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品信息表 服务实现类
 * </p>
 *
 * @author zhangjx
 * @create 2020-08-20
 * @Modify By
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    JwtUserUtil jwtUserUtil;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductTypeRepository productTypeRepository;

    //商品编码:SPBM+6位序号
    @Autowired
    @Qualifier(value = "PRODUCT")
    CodeGenerator productCodeGenerator;

    @Autowired
    PieceRateWageService pieceRateWageService;

    @Autowired
    PieceRateWageRepository pieceRateWageRepository;

    @Autowired
    ProductTypeRepository productTypeEntity;
/*
    //商品类型枚举
    ProductTypeEnum productTypeEnum;
    //主计量单位枚举
    MainMeasurementEnum mainMeasurementEnum;
    //生产渠道
    ProductionChannelsEnum productionChannelsEnum;
    //审核类型枚举
    AuditStatusEnum auditStatusEnum;
    //内部计量单位枚举
    InnerMeasurementEnum innerMeasurementEnum;*/

    /**
     * 根据id获取
     *
     * @param id
     * @return
     */
    @Override
    public ProductVM getEntryBy(long id) {
        ProductEntity productEntity = productRepository.findById(id);
        ProductVM productVM = new ProductVM();
        BeanUtils.copyProperties(productEntity, productVM);
        return productVM;
    }

    /**
     * 创建
     *
     * @param productVM
     * @return entity的id
     */
    @Override
    public String createEntry(ProductVM productVM) {
        String where = " and product_name = '" + productVM.getProductName() + "' and specifications ='" + productVM.getSpecifications() + "' ";
        Page<ProductVM> entrysByPage = productRepository.findEntrysByPage(where);
        if (entrysByPage.size() > 0) {
            return JsonResult.failed("商品名称、规格重复,新增失败");
        }
        productCodeGenerator.setPre("SPBM");
        String productCode = productCodeGenerator.generateCode();

        ProductEntity productEntity = new ProductEntity();
        BeanUtils.copyProperties(productVM, productEntity);
        productEntity.setProductCode(productCode);
        productEntity.setCreateBy(jwtUserUtil.getUserName());
        productEntity.setCreateTime(new Date());
        productEntity.setAuditStatus(3);
        productRepository.insert(productEntity);
        return null;
    }

    /**
     * 修改
     *
     * @param productVM
     * @return 是否成功
     */
    @Override
    public String updateEntry(ProductVM productVM) {
        String where = " and product_name = '" + productVM.getProductName() + "' and specifications ='" + productVM.getSpecifications() + "' and id <> " + productVM.getId();
        Page<ProductVM> entrysByPage = productRepository.findEntrysByPage(where);
        if (entrysByPage.size() > 0) {
            return JsonResult.failed("商品名称.规格重复,修改失败");
        }
        ProductEntity productEntity = productRepository.findById(productVM.getId());
        productEntity.failWhenConcurrencyViolation(productVM.getConcurrencyVersion());
        BeanUtils.copyProperties(productVM, productEntity);
        productEntity.setUpdateBy(jwtUserUtil.getUserName());
        productEntity.setUpdateTime(new Date());
        productRepository.update(productEntity);
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
        String ides = "";
        for (String id : Ids) {
            ProductVM entryBy = this.getEntryBy(Integer.parseInt(id));
            String where = " and product_code = '" + entryBy.getProductCode() + "'";
            Page<PieceRateWageVM> entrysByPage = pieceRateWageRepository.findEntrysByPage(where);
            for (PieceRateWageVM pieceRateWageVM : entrysByPage) {
                ides += pieceRateWageVM.getId();
            }
        }
        boolean b = productRepository.deleteById(Ids);
        if (b && ides.length() > 0 && !"".equals(ides)) {
            pieceRateWageService.deleteById(ides);
        }

        return b;
    }

    /**
     * 分页
     *
     * @param pageQuery
     * @return
     */
    @Override
    public Page<ProductVM> findEntrysByPage(PageQuery pageQuery) {
        FilterGroup filterGroup = pageQuery.getWhere();
        //自动转字符串
        String where = DynamicSearch.getInstance().buildWhere(filterGroup);
        PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize(), pageQuery.getOrderBy());
        return productRepository.findEntrysByPage(where);
    }

    /**
     * 导出
     *
     * @param response
     * @param request
     */
/*    @Override
    public void explort(String ids, HttpServletResponse response, HttpServletRequest request) {
        List<ProductVM> productVMS = new ArrayList<>();
//        if(ids!=null&&ids.length()>0){
//            String[] split = ids.split(",");
//            String where  = " and id in (";
//            for(int i =0 ; i<split.length;i++){
//                ProductEntity productEntity = productRepository.findById(Long.parseLong(split[i]));
//                if(productEntity !=null) {
//                    if (i != split.length - 1) {
//                        where = where + split[i] + ",";
//                    } else {
//                        where = where + split[i] + ")";
//                    }
//                }
//            }
//            productVMS = productRepository.findEntrysByPage(where);
//        }else {
//            FilterGroup filterGroup=pageQuery.getWhere();
//            //自动转字符串
//            String where= DynamicSearch.getInstance().buildWhere(filterGroup);
//            PageHelper.startPage(pageQuery.getPageNum(),pageQuery.getPageSize(),pageQuery.getOrderBy());
        String where = " order by create_time desc";
        productVMS = productRepository.findEntrysByPage(where);
//        }
        String[] title = {"序号", "商品编码", "商品名称", "规格", "主计量单位", "内部计量单位", "产品类型", "生产渠道", "出库单价", "预计销售单价", "库存上限", "库存下限", "审核状态", "审核人", "审核时间", "创建人", "创建时间"};
        String fileName = "商品信息.xls";
        String content[][] = new String[productVMS.size()][];
        for (int i = 0; i < productVMS.size(); i++) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            content[i] = new String[title.length];
            content[i][0] = "" + (i + 1);

            content[i][1] = productVMS.get(i).getProductCode();
            content[i][2] = productVMS.get(i).getProductName();
            content[i][3] = productVMS.get(i).getSpecifications();
            content[i][4] = ((productVMS.get(i).getMainMeasurement() == null) ? null : mainMeasurementEnum.getText(productVMS.get(i).getMainMeasurement()));
            content[i][5] = ((productVMS.get(i).getInnerMeasurement() == null) ? null : innerMeasurementEnum.getText(productVMS.get(i).getInnerMeasurement()));
            content[i][6] = ((productVMS.get(i).getProductType() == null) ? null : productTypeEnum.getText(productVMS.get(i).getProductType()));
            content[i][7] = ((productVMS.get(i).getProduceChannel() == null) ? null : productionChannelsEnum.getText(productVMS.get(i).getProduceChannel()));
            content[i][8] = ((productVMS.get(i).getCheckoutPrice() == null) ? null : productVMS.get(i).getCheckoutPrice().toString());
            content[i][9] = ((productVMS.get(i).getSalesPrice() == null) ? null : productVMS.get(i).getSalesPrice().toString());
            content[i][10] = productVMS.get(i).getInventoryUp() == null ? null : productVMS.get(i).getInventoryUp().toString();
            content[i][11] = productVMS.get(i).getInventoryDown() == null ? null : productVMS.get(i).getInventoryDown().toString();
            content[i][12] = ((productVMS.get(i).getAuditStatus() == null) ? null : auditStatusEnum.getText(productVMS.get(i).getAuditStatus()));
            content[i][13] = productVMS.get(i).getAuditBy();
            content[i][14] = ((productVMS.get(i).getAuditTime() == null) ? null : simpleDateFormat.format(productVMS.get(i).getAuditTime()));
            content[i][15] = productVMS.get(i).getCreateBy();
            content[i][16] = simpleDateFormat.format(productVMS.get(i).getCreateTime());
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

    /**
     * 审核
     *
     * @param ids
     * @param state
     * @return
     */
    @Override
    public String checkProduct(String ids, Integer state, String dismissReason) {
        String[] split = ids.split(",");

        if (ids != null && ids.length() > 0) {
            List<ProductEntity> productEntities = new ArrayList<>();
            for (String id : split) {
                ProductEntity product = productRepository.findById(Long.parseLong(id));
                if (state == 1) {
                    if (product.getAuditStatus().equals(state)) {
                        return JsonResult.failed("审核通过的不能再次审核");
                    }
                    if (product.getAuditStatus() == 2) {
                        product.setAuditStatus(state);
                        product.setAuditBy(jwtUserUtil.getRealName());
                        product.setAuditTime(new Date());
                        productEntities.add(product);
                    } else if (product.getAuditStatus() != 2) {
                        return JsonResult.failed("只能审核已提交审核的数据");
                    }
                }
                if (state == 2) {
                    if (product.getAuditStatus().equals(state)) {
                        return JsonResult.failed("重复提交审核");
                    } else if (product.getAuditStatus() != 3 && product.getAuditStatus() != 7) {
                        return JsonResult.failed("只能提交未提交或被驳回状态的数据");
                    } else if (product.getAuditStatus() == 3 || product.getAuditStatus() == 7) {
                        product.setAuditStatus(state);
//                        product.setAuditBy(jwtUserUtil.getRealName());
//                        product.setAuditTime(new Date());
                        productEntities.add(product);
                    }
                }
                if (state == 3) {
                    if (product.getAuditStatus().equals(state)) {
                        return JsonResult.failed("未提交无法撤回");
                    } else if (product.getAuditStatus() == 1 || product.getAuditStatus() == 2) {
                        product.setAuditStatus(state);
//                        product.setAuditBy(jwtUserUtil.getRealName());
//                        product.setAuditTime(new Date());
                        productEntities.add(product);
                    } else if (product.getAuditStatus() == 7) {
                        return JsonResult.failed("已驳回无法撤回");
                    }
                }
                if (state == 7) {
                    if (product.getAuditStatus().equals(state)) {
                        return JsonResult.failed("不能重复驳回");
                    } else if (product.getAuditStatus() == 2) {
                        product.setAuditStatus(state);
                        product.setAuditBy(jwtUserUtil.getRealName());
                        product.setAuditTime(new Date());
                        product.setDismissReason(dismissReason);
                        productEntities.add(product);
                    } else if (product.getAuditStatus() != 2) {
                        return JsonResult.failed("只能驳回审核中的数据");
                    }
                }
                if (state == 4) {
                    if (product.getAuditStatus() == 2) {
                        product.setAuditStatus(state);
                        product.setAuditBy(jwtUserUtil.getRealName());
                        product.setAuditTime(new Date());
                        productEntities.add(product);
                    } else {
                        return JsonResult.failed("只能撤回审核中的数据");
                    }
                }
            }
            if (state != 4) {
                productRepository.chekcProduct(productEntities);
            } else {
                state = 3;
                productRepository.chekcProduct(productEntities);
            }
            return null;
        }
        return JsonResult.failed("没有能够审核的数据");
    }

    /**
     * 获取所有商品信息
     *
     * @return
     */
    @Override
    public List<ProductVM> getAllProduct() {
        String where = "";
        return productRepository.findEntrysByPage(where);
    }

    @Override
    public List<String> getSpecifications() {
        return productRepository.findSpecifications();
    }

    /**
     * 同步物料分类
     *
     * @return
     */
    @Override
    public String synToGRMC(Map[] data, String op) {
        if (data.length <= 0) {
            return "无数据，请确认数据是否正确！";
        }
        if (op.equals("add") || op.equals("upd")) {
            for (int i = 0; i < data.length; i++) {
                Map<String, Object> datum = data[i];
                //ID
                Integer id = (Integer) datum.get("Id");
                //父级ID
                Integer parentId = (Integer) datum.get("Parent_id");
                //类别编号
                Integer catagoryNo = (Integer) datum.get("Catagory_no");
                //类别名称
                String catagoryName = (String) datum.get("Catagory_name");
                String companyId = (String) datum.get("Company_id");
                //平均单价
                Double avgPrice = (Double) datum.get("Avg_price");
                //平均成本
                Double avgCost = (Double) datum.get("Avg_cost");
                //平均采购提前期
                Integer avgBuyTime = (Integer) datum.get("Avg_buy_time");
                //平均成产提前期
                Integer avgProductionTime = (Integer) datum.get("Avg_production_time");
                //辅助属性结构
                String moreAttribute = (String) datum.get("More_attribute");
                //是否启用
                Integer status = (Integer) datum.get("Status");
                String isPublic = (String) datum.get("Is_public");
                //供应商分类编码
                Integer sortId = (Integer) datum.get("Sort_id");
                //质量标准
                String qualityStandard = (String) datum.get("Quality_standard");
                //质保类型
                String ftypeZhibao = (String) datum.get("FTypeZhibao");
                //预付类型
                String ftypePrepay = (String) datum.get("FTypePrepay");
                //应付类型
                String ftypePayable = (String) datum.get("FTypePayable");
                //资产类别
                Integer fixedType = (Integer) datum.get("FixedType");
                //使用月限
                Integer useMonth = (Integer) datum.get("UseMonth");


                if (op.equals("add")) {

                    ProductTypeEntity byId = productTypeEntity.findById(id);
                    if (byId != null) {
                        return "该物料分类信息已存在，请勿重复上传！";
                    }
                    ProductTypeEntity productTypeEntity = new ProductTypeEntity();

                    productTypeEntity.setId(Long.valueOf(id));
                    productTypeEntity.setParentId(Long.valueOf(parentId));
                    productTypeEntity.setCatagoryNo(catagoryNo);
                    productTypeEntity.setCatagoryName(catagoryName);
                    productTypeEntity.setCompanyId(companyId);
                    productTypeEntity.setAvgPrice(BigDecimal.valueOf(avgPrice));
                    productTypeEntity.setAvgCost(BigDecimal.valueOf(avgCost));
                    productTypeEntity.setAvgBuyTime(avgBuyTime);
                    productTypeEntity.setAvgProductionTime((avgProductionTime));
                    productTypeEntity.setMoreAttribute(moreAttribute);
                    productTypeEntity.setStatus(status);
                    productTypeEntity.setIsPublic(isPublic);
                    productTypeEntity.setSortId(sortId);
                    productTypeEntity.setQualityStandard(qualityStandard);
                    productTypeEntity.setFtypeZhibao(ftypeZhibao);
                    productTypeEntity.setFtypePrepay(ftypePrepay);
                    productTypeEntity.setFtypePayable(ftypePayable);
                    productTypeEntity.setFixedType(fixedType);
                    productTypeEntity.setUseMonth(useMonth);
                    productTypeEntity.setCreateTime(new Date());
                    boolean insert = productTypeRepository.insert(productTypeEntity);
                    if (!insert) {
                        return "上传失败！";
                    }
                } else {
                    ProductTypeEntity byId = productTypeEntity.findById(id);
                    if (byId == null) {
                        return "没有找到主键信息，修改失败！";
                    }
                    byId.setId(Long.valueOf(id));
                    byId.setParentId(Long.valueOf(parentId));
                    byId.setCatagoryNo(catagoryNo);
                    byId.setCatagoryName(catagoryName);
                    byId.setCompanyId(companyId);
                    byId.setAvgPrice(BigDecimal.valueOf(avgPrice));
                    byId.setAvgCost(BigDecimal.valueOf(avgCost));
                    byId.setAvgBuyTime(avgBuyTime);
                    byId.setAvgProductionTime((avgProductionTime));
                    byId.setMoreAttribute(moreAttribute);
                    byId.setStatus(status);
                    byId.setIsPublic(isPublic);
                    byId.setSortId(sortId);
                    byId.setQualityStandard(qualityStandard);
                    byId.setFtypeZhibao(ftypeZhibao);
                    byId.setFtypePrepay(ftypePrepay);
                    byId.setFtypePayable(ftypePayable);
                    byId.setFixedType(fixedType);
                    byId.setUseMonth(useMonth);
                    byId.setUpdateTime(new Date());
                    boolean update = productTypeRepository.update(byId);
                }
            }

        } else if (op.equals("del")) {
            for (int i = 0; i < data.length; i++) {
                String id2 = data[i].get("Id").toString();
                ProductTypeEntity id = productTypeRepository.findById(Long.parseLong(id2));
                if (id == null) {
                    return "没有找到主键信息，删除失败！";
                }
                //根据id删除
                Map<String, Object> datum = data[i];
                boolean id1 = productTypeRepository.deleteById((Integer) datum.get("Id"));
                if (!id1) {
                    return "删除失败";
                }
            }
        } else if (op.equals("enable")) {
            for (int i = 0; i < data.length; i++) {
                Integer id2 = (Integer) data[i].get("Id");
                ProductTypeEntity id = productTypeRepository.findById(id2);
                if (id == null) {
                    return "没有找到主键信息，删除失败！";
                }
                //根据id删除
                boolean id1 = productTypeRepository.enablePk(id2);
            }
        } else if (op.equals("disable")) {
            for (int i = 0; i < data.length; i++) {
                Integer id2 = (Integer) data[i].get("Id");

                ProductTypeEntity id = productTypeRepository.findById(id2);
                if (id == null) {
                    return "没有找到主键信息，删除失败！";
                }
                //根据id删除
                boolean id1 = productTypeRepository.disablePk(id2);
            }
        } else {
            return "无效的操作类型";
        }
        return null;
    }


    /**
     * 同步物料
     *
     * @return
     */
    @Override
    public String synToGRM(Map[] data, String op) {
        if (data.length <= 0) {
            return "无数据，请确认数据是否正确！";
        }
        if (op.equals("add") || op.equals("upd")) {
            for (int i = 0; i < data.length; i++) {
                Map<String, Object> datum = data[i];
                //物料编号
                String productCode = (String) datum.get("Mater_no");
                //物料名称
                String productName = (String) datum.get("Mater_name");
                //规格
                String specifications = (String) datum.get("Specs");
                //物料类别
                Integer productType = (Integer) datum.get("Catagory_id");
                Integer mainMeasurement = 1;
                if (datum.get("Unit").equals("JIAN")) {
                    mainMeasurement = 1;
                } else {
                    mainMeasurement = 2;
                }


                if (op.equals("add")) {
                    ProductEntity entrysByCode = productRepository.findEntrysByCode(productCode);
                    if (entrysByCode != null) {
                        return "该物料已存在，请勿重复上传!";
                    }
                    ProductEntity productEntity = new ProductEntity();

                    productEntity.setProductCode(productCode);
                    productEntity.setProductName(productName);
                    productEntity.setSpecifications(specifications);
                    productEntity.setProductType(productType);
                    productEntity.setCreateTime(new Date());
                    productEntity.setSalesPrice(new BigDecimal(0));
                    productEntity.setCheckoutPrice(new BigDecimal(0));
                    productEntity.setAuditStatus(3);
                    productEntity.setMainMeasurement(mainMeasurement);
                    productRepository.insert(productEntity);
                } else {

                    ProductEntity entrysByCode = productRepository.findEntrysByCode(productCode);
                    if (entrysByCode == null) {
                        return "没有找到主键信息，修改失败！";
                    }
                    entrysByCode.setProductCode(productCode);
                    entrysByCode.setProductName(productName);
                    entrysByCode.setSpecifications(specifications);
                    entrysByCode.setProductType(productType);
                    entrysByCode.setUpdateTime(new Date());
                    productRepository.updateByPk(entrysByCode);
                }
            }
        } else if (op.equals("del")) {
            String[] Ids = new String[data.length];
            for (int i = 0; i < data.length; i++) {

                ProductEntity entrysByCode = productRepository.findEntrysByCode((String) data[i].get("Mater_no"));
                if (entrysByCode == null) {
                    return "没有找到主键信息，修改失败！";
                }
                //根据id删除
                Map<String, Object> datum = data[i];
                //物料编号
                Ids[i] = (String) datum.get("Mater_no");
            }
            productRepository.deleteByPk(Ids);
        } else if (op.equals("enable")) {
            for (int i = 0; i < data.length; i++) {

                ProductEntity entrysByCode = productRepository.findEntrysByCode((String) data[i].get("Mater_no"));
                if (entrysByCode == null) {
                    return "没有找到主键信息，修改失败！";
                }
                //根据id删除
                productRepository.enablePk((String) data[i].get("Mater_no"));
                //物料编号
            }
        } else if (op.equals("disable")) {
            for (int i = 0; i < data.length; i++) {

                ProductEntity entrysByCode = productRepository.findEntrysByCode((String) data[i].get("Mater_no"));
                if (entrysByCode == null) {
                    return "没有找到主键信息，修改失败！";
                }
                productRepository.disenablePk((String) data[i].get("Mater_no"));
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
                fileName = new String(fileName.getBytes("ISO-8859-1"), "GBK");
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            response.setContentType("application/octet-stream;charset=ISO-8859-1");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
