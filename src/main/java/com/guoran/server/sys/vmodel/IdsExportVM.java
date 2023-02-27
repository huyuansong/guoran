package com.guoran.server.sys.vmodel;

import com.guoran.server.common.search.PageQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhangmengyu
 * @create 2020/8/24 15:48
 * @Modify By
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IdsExportVM {
    /**
     * id的数组
     */
    private long[] ids;
    /**
     * 分页情况
     */
    private PageQuery pageQuery;
}
