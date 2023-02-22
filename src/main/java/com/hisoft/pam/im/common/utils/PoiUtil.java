package com.hisoft.pam.im.common.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.FileMagic;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @description:
 * @author: machao
 * @create: 2020-03-02 10:15
 * @Modify By
 **/
public class PoiUtil {
    /**
     * 判断是否office文件
     * @param inputStream
     * @return
     */
    public static Boolean isOfficeFile(InputStream inputStream){
        boolean result = false;
        try {
            FileMagic fileMagic = FileMagic.valueOf(inputStream);
            if (Objects.equals(fileMagic,FileMagic.OLE2)||Objects.equals(fileMagic,fileMagic.OOXML)){
                result = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 判断是否office文件
     * @param file
     * @return
     * @throws IOException
     */
    public static Boolean isOfficeFile(MultipartFile file) throws IOException {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(file.getInputStream());
        boolean result = false;
        result = isOfficeFile(bufferedInputStream);
        return result;
    }
    /**
     * 判断扩展名是否是excel扩展名
     * @param extension
     * @return
     */
    public static Boolean isExcel(String extension){
        List exts = new ArrayList<String>();
        exts.add("xls");
        exts.add("xlsx");
        exts.add("XLS");
        exts.add("XLSX");
        return exts.contains(extension);
    }

    /**
     * 判断扩展名是否是excel扩展名
     * @param file
     * @return
     */
    public static Boolean isExcelFile(MultipartFile file){
        String fileName = file.getOriginalFilename();
        String extension = fileName.substring(fileName.lastIndexOf(".")+1);
        return isExcel(extension);
    }

    /**
     * 自动判断文件类型
     * @param file
     * @return
     * @throws IOException
     */
    public static Workbook getWorkbookAuto(MultipartFile file) throws IOException {
        /** 判断文件的类型，是2003还是2007 */
        boolean isExcel2003 = true;
        if (isExcel2007(file.getOriginalFilename())) {
            isExcel2003 = false;
        }
        BufferedInputStream is = new BufferedInputStream(
                file.getInputStream());
        Workbook wb;
        if (isExcel2003) {
            wb = new HSSFWorkbook(is);
        } else {
            wb = new XSSFWorkbook(is);
        }
        return wb;
    }

    /**
     * 是否是Excel2003文件
     * @param filePath
     * @return
     */
    public static boolean isExcel2003(String filePath) {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }
    /**
     * 是否是Excel2007版以上文件
     * @param filePath
     * @return
     */
    public static boolean isExcel2007(String filePath) {
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }


}
