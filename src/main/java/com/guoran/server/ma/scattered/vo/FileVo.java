package com.guoran.server.ma.scattered.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileVo implements Serializable {

    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 附件地址
     */
    private String fileUrl;
}
