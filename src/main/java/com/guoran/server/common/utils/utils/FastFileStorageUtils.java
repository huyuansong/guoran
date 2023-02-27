package com.guoran.server.common.utils.utils;

import com.github.tobato.fastdfs.domain.fdfs.MetaData;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.guoran.server.common.exception.ImErrorCode;
import com.guoran.server.common.exception.ServiceException;
import com.guoran.server.common.i18n.MessageUtils;
import com.guoran.server.security.JwtUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @description: FastDFS上传附件辅助类
 * @author: machao
 * @create: 2019-12-25 17:18
 * @Modify By
 **/
@Component
public class FastFileStorageUtils {
    @Autowired
    private FastFileStorageClient fastFileStorageClient;
    @Autowired
    private JwtUserUtil jwtUserUtil;

    /**
     * 上传文件，返回url
     *
     * @param file
     * @return
     */
    public String uploadFile(MultipartFile file) {
        String filePath = null;
        String fileName = file.getOriginalFilename();
        //String rawFileName = StringUtils.substringBefore(fileName,".");
        String fileType = StringUtils.substringAfter(fileName, ".");
        // 设置文件信息
        Set<MetaData> mataData = new HashSet<>();
        mataData.add(new MetaData("author", jwtUserUtil.getUserName()));
        mataData.add(new MetaData("description", fileName));
        try {
            // 上传   （文件上传可不填文件信息，填入null即可）
            StorePath storePath = fastFileStorageClient.uploadFile(file.getInputStream(), file.getSize(), fileType, mataData);
            filePath = storePath.getFullPath();
        } catch (IOException e) {
            String message = StringUtils.format(MessageUtils.get(ImErrorCode.FILE_UPLOAD_FAIL), fileName);
            throw new ServiceException(ImErrorCode.FILE_UPLOAD_FAIL, message);
        }
        return filePath;
    }
}
