package com.hisoft.pam.im.common.utils;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

import java.io.OutputStream;
import java.util.List;

/**
 * @author zhangmengyu
 * @create 2020/8/25 18:19
 * @Modify By
 */
public class ExportExcelUtil {
    public void exportExcel(XSSFWorkbook workbook, int sheetNum,List<Integer[]> titleCell,
                            String sheetTitle, String[][] headers, List<String [][]> values,
                            OutputStream out) throws Exception {
        // 生成一个表格
        XSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(sheetNum, sheetTitle);
        // 设置表格默认列宽度为20个字节
        sheet.setDefaultColumnWidth((short) 20);
        // 生成一个样式
        XSSFCellStyle style = workbook.createCellStyle();
        // 设置这些样式
        style.setAlignment(HorizontalAlignment.CENTER); //水平居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
        // 生成一个字体


        // 指定当单元格内容显示不下时自动换行
        style.setWrapText(true);

        for(int i = 0 ;i<titleCell.size();i++){
            Integer[] integers = titleCell.get(i);
            CellRangeAddress callRangeAddress = new CellRangeAddress(integers[0],integers[1],integers[2],integers[3]);
            sheet.addMergedRegion(callRangeAddress);
        }

        // 产生表格标题行
        XSSFRow row = sheet.createRow(0);
        for(int i = 0 ; i< headers.length;i++){
            row = sheet.createRow(i);
            XSSFCell cell = null;
            for(int j = 0; j<headers[i].length;j++){
                    cell = row.createCell(j);
                    cell.setCellStyle(style);
                    HSSFRichTextString text = new HSSFRichTextString(headers[i][j]);
                    cell.setCellValue(text.toString());
                }

            }
        // 遍历集合数据，产生数据行
//        if (result != null) {
//            int index = 1;
//            for (List<String> m : result) {
//                row = sheet.createRow(index);
//                int cellIndex = 0;
//                for (String str : m) {
//                    XSSFCell cell = row.createCell((short) cellIndex);
//                    cell.setCellValue(str);
//                    cellIndex++;
//                }
//                index++;
//            }
//        }
        //创建内容
        for(int x = 0;x<values.size();x++){

            for(int i=0;i<values.get(x).length;i++){
                row = sheet.createRow(i + 2);
                for(int j=0;j<values.get(x)[i].length;j++){
                    //将内容按顺序赋给对应的列对象
                    row.createCell(j).setCellValue(values.get(x)[i][j]);
                }
            }
        }
    }
}
