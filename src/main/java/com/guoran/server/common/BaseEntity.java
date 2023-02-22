package com.guoran.server.common;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 *  实体类基础类，定义了每个实体必须实现的字段
 */
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间",hidden = true)
    protected Date createTime;
    /**
     * 创建人
     */
    @ApiModelProperty(value="创建人",hidden = true)
    protected String createBy;
    /**
     * 修改时间
     */
    @ApiModelProperty(value="修改时间",hidden = true)
    protected Date updateTime;
    /**
     * 修改人
     */
    @ApiModelProperty(value="修改人",hidden = true)
    protected String updateBy;


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }
}
