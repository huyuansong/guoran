package com.guoran.server.common.model;


import com.guoran.server.common.exception.ServiceException;
import com.guoran.server.common.i18n.MessageUtils;
import io.swagger.annotations.ApiModelProperty;

/**
 * 并发安全实体类基类
 */
public class BaseOfConcurrencySafeEntity extends BaseEntity {
    /*
     * 并发版本号
     * */
    @ApiModelProperty(hidden = true)
    protected Integer concurrencyVersion;

    public BaseOfConcurrencySafeEntity() {
        super();
    }

    public Integer getConcurrencyVersion() {
        return this.concurrencyVersion;
    }

    public void setConcurrencyVersion(Integer aVersion) {
        this.failWhenConcurrencyViolation(aVersion);
        this.concurrencyVersion = aVersion;
    }

    /**
     * 并发冲突检测。
     *
     * @param aVersion the a version
     */
    public void failWhenConcurrencyViolation(Integer aVersion) throws ServiceException {
        if(this.getConcurrencyVersion()!=null) {
            if (!this.getConcurrencyVersion().equals(aVersion)) {
                throw new ServiceException(MessageUtils.get("concurrent.conflict"));
            }
        }
    }
}
