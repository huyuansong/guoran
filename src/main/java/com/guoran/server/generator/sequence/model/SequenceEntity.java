package com.guoran.server.generator.sequence.model;

import com.guoran.server.common.BaseOfConcurrencySafeEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 编号序号管理
 * </p>
 *
 * @author terry.qian
 * @table generator_sequence
 * @create 2019-12-12
 * @Modify By
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SequenceEntity extends BaseOfConcurrencySafeEntity {
    /**
     * id
     */
    private Long id;
    /**
     * 编号类型
     */
    private String codeType;
    /**
     * 序号
     */
    private Integer seq;
    /**
     * 备注
     */
    private String remark;

}