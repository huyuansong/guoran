package com.guoran.server.liu.financial.repository;

import com.github.pagehelper.Page;
import com.guoran.server.liu.financial.model.VoucherEntity;
import com.guoran.server.liu.financial.vmodel.VoucherVM;
import org.apache.ibatis.annotations.*;

@Mapper
public interface VoucherRepository {
    /**
     * 根据id查找
     *
     * @param id
     * @return
     */
    @Select("select * from financial_voucher where id =#{id}")
    VoucherEntity findById(@Param("id") long id);

    /**
     * 新增
     *
     * @param entity
     */

    @Insert("insert into financial_voucher (" +
            " `upload_state`, `upload_error_content`, `business_type`, `order_code`, `accounting_entity`, `supplier`, `document_type`, `remark`, `debit_amount`, `credit_amount`, `preparation_date`, `accounting_period`, `voucher_code`, `creator`,`create_time`, `create_by`,`company_code`) values(" +
            "#{entity.uploadState}, #{entity.uploadErrorContent}, #{entity.businessType}, #{entity.orderCode}, #{entity.accountingEntity}, #{entity.supplier}, #{entity.documentType}, #{entity.remark}, #{entity.debitAmount}, #{entity.creditAmount}, #{entity.preparationDate}, #{entity.accountingPeriod}, #{entity.voucherCode}, #{entity.creator}, #{entity.createTime},#{entity.createBy},#{entity.companyCode})")
    @Options(useGeneratedKeys = true, keyProperty = "entity.id", keyColumn = "id")
    Long insert(@Param("entity") VoucherEntity entity);

    /**
     * 更新
     *
     * @param entity
     */

    @Update("update financial_voucher set " +
            "upload_state=#{entity.uploadState}, upload_error_content=#{entity.uploadErrorContent}, business_type=#{entity.businessType}, order_code=#{entity.orderCode}, accounting_entity=#{entity.accountingEntity}, supplier=#{entity.supplier}, document_type=#{entity.documentType}, remark=#{entity.remark}, debit_amount=#{entity.debitAmount}, credit_amount=#{entity.creditAmount}, preparation_date=#{entity.preparationDate}, accounting_period=#{entity.accountingPeriod}, voucher_code=#{entity.voucherCode}, creator=#{entity.creator}, update_time=#{entity.updateTime},update_by=#{entity.updateBy} where id=#{entity.id}")
    boolean update(@Param("entity") VoucherEntity entity);

    /**
     * 根据id删除
     *
     * @param id
     */
    @Delete("delete from financial_voucher where id=#{id}")
    boolean deleteById(@Param("id") long id);

    /**
     * 分页查询
     *
     * @param where
     * @return
     */
    @Select("select * from financial_voucher where 1=1 ${where}")
    Page<VoucherVM> findEntrysByPage(@Param("where") String where);

    /**
     * 修改返回的PK
     */
    @Update("update financial_voucher set pk_voucher = #{pkVoucher} ,upload_state = #{status} where id =#{id} ")
    boolean updatePK(@Param("id") Integer id, @Param("pkVoucher") String pkVoucher, @Param("status") int status);

    /**
     * 修改返回的PK
     */
    @Delete("delete from financial_voucher where pk_voucher = #{pkVoucher} ")
    boolean deleteByPk(@Param("pkVoucher") String pkVoucher);
}
