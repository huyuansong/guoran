package com.hisoft.pam.im.common.utils;
import com.hisoft.pam.im.config.WebConfig;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * @description: 文件上传工具类
 * @author: machao
 * @create: 2019-12-10 11:32
 * @Modify By
 **/
public class FileUploadUtils {
    /**
     * 默认的文件名最大长度 100
     */
    public static final int DEFAULT_FILE_NAME_LENGTH = 100;

    /**
     * 默认上传的地址
     */
    private static String defaultBaseDir = WebConfig.getProfile();

    /**
     * 默认文件类型jpg
     */
    public static final String IMAGE_JPG_EXTENSION = ".jpg";

    private static long counter = 0;

    public static void setDefaultBaseDir(String defaultBaseDir) {
        FileUploadUtils.defaultBaseDir = defaultBaseDir;
    }

    public static String getDefaultBaseDir() {
        return defaultBaseDir;
    }

    /**
     * 根据文件路径上传图片
     *
     * @param baseDir 相对应用的基目录
     * @param file    上传的文件
     * @return 文件名称
     * @throws IOException
     */
    public static final String uploadPicture(String baseDir, MultipartFile file) throws IOException {
        try {
            //可以生成缩略图
            return upload(baseDir, file, FileUploadUtils.IMAGE_JPG_EXTENSION);
        } catch (Exception e) {
            throw new IOException(e.getMessage(), e);
        }
    }


    /**
     * 文件上传
     *
     * @param baseDir   相对应用的基目录
     * @param file      上传的文件
     * @param extension 上传文件类型
     * @return 返回上传成功的文件名
     * @throws IOException 比如读写文件出错时
     */
    public static final String  upload(String baseDir, MultipartFile file, String extension) throws IOException {

        int fileNamelength = file.getOriginalFilename().length();
        if (fileNamelength > FileUploadUtils.DEFAULT_FILE_NAME_LENGTH) {
            // 此处可进行异常处理throws
            System.out.println("文件名长度超出限定长度");
        }
        String fileName = extractFilename(file, extension);

        File desc = getAbsoluteFile(baseDir, baseDir + fileName);
        file.transferTo(desc);
        return fileName;
    }

    /**
     * 生成本地文件名
     * @param file
     * @param extension
     * @return
     */
    public static final String extractFilename(MultipartFile file, String extension) {
        String firstStr = StringUtils.substring(extension,0,1);
        if (!".".equals(firstStr)){
            extension="."+extension;
        }
        String filename = file.getOriginalFilename();
        filename = DateUtils.datePath() + "/" + encodingFilename(filename) + extension;
        return filename;
    }

    private static final File getAbsoluteFile(String uploadDir, String filename) throws IOException {
        File desc = new File(File.separator + filename);

        if (!desc.getParentFile().exists()) {
            desc.getParentFile().mkdirs();
        }
        if (!desc.exists()) {
            desc.createNewFile();
        }
        return desc;
    }

    /**
     * 编码文件名
     */
    private static final String encodingFilename(String filename) {
        filename = filename.replace("_", " ");
        filename = Md5Utils.hash(filename + System.nanoTime() + counter++);
        return filename;
    }

    public static final File getAbsoluteFile(String filename) throws IOException {
        File desc = new File(File.separator + filename);
        if (!desc.getParentFile().exists()) {
            desc.getParentFile().mkdirs();
        }
        if (!desc.exists()) {
            desc.createNewFile();
        }
        return desc;
    }

    public static void main(String[] args) {


        /*String a="/api/questionnaire/loadimg/2020/08/10/27e6c9226126edcc668151f8e5dc48a6.jpg";
        String newImage=a.replaceAll("/api/pam/loadimg/","");
        System.out.println(newImage);*/
        File file1=new File("D:/pic/upload/2020/08/10/c2d2d7699131a0b730a381b4acd529a0.png");

        File file2=new File("D:/pic/upload/copy/2020/08/10/c2d2d7699131a0b730a381b4acd529a0.png");
        if (!file2.exists()){
            file2.mkdirs();
        }
        byte[] b=new byte[(int)file1.length()];
        FileInputStream in=null;
        FileOutputStream out=null;
        try {
            in=new FileInputStream(file1);
            //没有指定文件则会创建
            out=new FileOutputStream(file2);
            //read()--int，-1表示读取完毕
            while(in.read(b)!=-1){
                out.write(b);
            }
            out.flush();
            in.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 复制图片
     * @param srcpic
     */
    public static String copyPic(String srcpic){
        String copyImage=null;
        String newImage=srcpic.replaceAll("/api/pam/loadimg/","");

        //物理地址
        File file1=new File(WebConfig.getUploadPath()+newImage);
        copyImage=newImage.replaceAll("\\.",+System.currentTimeMillis()+".");
        File file2=new File(WebConfig.getUploadPath()+copyImage);
        if (!file2.getParentFile().exists()){
            file2.getParentFile().mkdirs();
        }
        byte[] b=new byte[(int)file1.length()];
        FileInputStream in=null;
        FileOutputStream out=null;
        try {
            in=new FileInputStream(file1);
            //没有指定文件则会创建
            out=new FileOutputStream(file2);
            //read()--int，-1表示读取完毕
            while(in.read(b)!=-1){
                out.write(b);
            }
            out.flush();
            in.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/api/pam/loadimg/"+copyImage;
    }

}
