package com.guoran.server.liu.financial.repository;

import com.github.pagehelper.Page;
import com.guoran.server.liu.financial.model.VoucherDetailsEntity;
import com.guoran.server.liu.financial.vmodel.VoucherDetailsVM;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface VoucherDetailsRepository {

    /**
     * 根据id查找
     *
     * @param id
     * @return
     */
    @Select("select * from financial_voucher_details where id =#{id}")
    VoucherDetailsEntity findById(@Param("id") long id);

    /**
     * 新增
     *
     * @param entity
     */

    @Insert("insert into financial_voucher_details (" +
            " `sequence`, `borrowing_direction`, `subject`,`subject_code`,`debit_amount`, `credit_amount`, `main_cash_flow`, `deputy_cash_flow`, `assist_accounting`, `remark`,`voucher_id`,`create_time`, `create_by`) values(" +
            "#{entity.sequence}, #{entity.borrowingDirection}, #{entity.subject}, #{entity.subjectCode},#{entity.debitAmount}, #{entity.creditAmount}, #{entity.mainCashFlow}, #{entity.deputyCashFlow}, #{entity.assistAccounting}, #{entity.remark}, #{entity.voucherId} , #{entity.createTime},#{entity.createBy})")
    @Options(useGeneratedKeys = true, keyProperty = "entity.id", keyColumn = "id")
    Long insert(@Param("entity") VoucherDetailsEntity entity);

    /**
     * 更新
     *
     * @param entity
     */

    @Update("update financial_voucher_details set " +
            "sequence=#{entity.sequence}, borrowing_direction=#{entity.borrowingDirection}, subject=#{entity.subject},subject_code= #{entity.subjectCode}, debit_amount=#{entity.debitAmount}, credit_amount=#{entity.creditAmount}, main_cash_flow=#{entity.mainCashFlow}, deputy_cash_flow=#{entity.deputyCashFlow}, assist_accounting=#{entity.assistAccounting}, remark=#{entity.remark},voucher_id = #{entity.voucherId}, update_time=#{entity.updateTime},update_by=#{entity.updateBy} where id=#{entity.id}")
    boolean update(@Param("entity") VoucherDetailsEntity entity);

    /**
     * 根据id删除
     *
     * @param id
     */
    @Delete("delete from financial_voucher_details where id=#{id}")
    boolean deleteById(@Param("id") long id);

    /**
     * 分页查询
     *
     * @param where
     * @return
     */
    @Select("select * from financial_voucher_details where 1=1 ${where}")
    Page<VoucherDetailsVM> findEntrysByPage(@Param("where") String where);

    /**
     * 批量插入
     */
    @Insert({"<script>" +
            "<foreach collection='voucherDetailsEntities' item='entity' open='' close='' separator=';'>" +
            "insert into financial_voucher_details (" +
            " `sequence`, `borrowing_direction`, `subject`,`subject_code`, `debit_amount`, `credit_amount`, `main_cash_flow`, `deputy_cash_flow`, `assist_accounting`, `remark`,`voucher_id` ,`create_time`, `create_by`) values(" +
            "#{entity.sequence}, #{entity.borrowingDirection}, #{entity.subject}, #{entity.subjectCode},#{entity.debitAmount}, #{entity.creditAmount}, #{entity.mainCashFlow}, #{entity.deputyCashFlow}, #{entity.assistAccounting}, #{entity.remark},#{entity.voucherId}, #{entity.createTime},#{entity.createBy})" +
            "</foreach>" +
            "</script>"})
    void insertBanch(@Param(value = "voucherDetailsEntities") List<VoucherDetailsEntity> voucherDetailsEntities);

    /**
     * 批量修改
     *
     * @param voucherDetailsEntities
     */
    @Update({"<script>" +
            "<foreach collection='voucherDetailsEntities' item='entity' open='' close='' separator=';'>" +
            " update financial_voucher_details set " +
            " sequence=#{entity.sequence}, borrowing_direction=#{entity.borrowingDirection}, subject=#{entity.subject}, subject_code= #{entity.subjectCode},debit_amount=#{entity.debitAmount}, credit_amount=#{entity.creditAmount}, main_cash_flow=#{entity.mainCashFlow}, deputy_cash_flow=#{entity.deputyCashFlow}, assist_accounting=#{entity.assistAccounting}, remark=#{entity.remark},voucher_id = #{entity.voucherId}, update_time=#{entity.updateTime},update_by=#{entity.updateBy}" +
            " where id= #{entity.id} " +
            "</foreach>" +
            "</script>"})
    void updateBanch(@Param(value = "voucherDetailsEntities") List<VoucherDetailsEntity> voucherDetailsEntities);

    /**
     * 批量删除
     *
     * @param longs
     */
    @Delete({"<script>" +
            " delete from financial_voucher_details" +
            " where id in " +
            "<foreach collection='ids' item='id' open='(' close=')' separator=','>" +
            " #{id}" +
            "</foreach>" +
            "</script>"})
    void deleteBanch(@Param(value = "ids") List<Long> longs);
}
