package com.hisoft.pam.im.common.utils;

import org.apache.poi.hssf.usermodel.*;

import java.util.regex.Pattern;

/**
 * @author lkx
 * @description 报表工具
 * @time 2020-07-21 11:49
 */
public class ExcelUtil {


    /**
     * 导出Excel
     * @param sheetName sheet名称
     * @param title 标题
     * @param values 内容
     * @param wb HSSFWorkbook对象
     * @return
     */
    public static HSSFWorkbook getHSSFWorkbook(String sheetName, String []title, String [][]values, HSSFWorkbook wb){

        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        if(wb == null){
            wb = new HSSFWorkbook();
        }

        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(0);

        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.00"));

        //声明列对象
        HSSFCell cell = null;

        //创建标题
        for(int i=0;i<title.length;i++){
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }

        //创建内容
        for(int i=0;i<values.length;i++){
            row = sheet.createRow(i + 1);
            for(int j=0;j<values[i].length;j++){
                String value = values[i][j] == null ? "" : values[i][j];
                //将内容按顺序赋给对应的列对象
                Pattern pattern = Pattern.compile("^(-|\\+)?\\d+(\\.\\d+)?$");
                if(pattern.matcher(value).matches()){
                    HSSFCell cellSpe = row.createCell(j);
                    cellSpe.setCellValue(Double.parseDouble(value));
                    cellSpe.setCellStyle(cellStyle);
                }else{
                    row.createCell(j).setCellValue(value);
                }
            }
        }
        return wb;
    }
}
