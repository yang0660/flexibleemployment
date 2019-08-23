package com.flexibleemployment.utils.file;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.*;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ExcelUtils {
    //默认单元格内容为数字时格式
    private static DecimalFormat df = new DecimalFormat("0");
    // 默认单元格格式化日期字符串
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    // 格式化数字
    private static DecimalFormat nf = new DecimalFormat("0.00");

    public static List<List<String>> readRows(InputStream in) {
        Workbook workbook;
        try {
            workbook = WorkbookFactory.create(in);
        } catch (Exception e) {
            log.info("创建Workbook失败", e);
            throw new RuntimeException("解析Excel文件失败", e);
        }

        //得到一个工作表
        Sheet sheet = workbook.getSheetAt(0);

        //获得表头
        Row rowHead = sheet.getRow(0);

        //获得数据的总行数
        int totalRowNum = sheet.getLastRowNum();


        List<List<String>> rowDataList = new ArrayList<>();
        //获得所有数据
        for (int i = 1; i <= totalRowNum; i++) {
            //获得第i行对象
            Row row = sheet.getRow(i);
            List<String> colDataList = new ArrayList<>();
            for(int colIdx = rowHead.getFirstCellNum(); colIdx < rowHead.getLastCellNum(); colIdx ++) {
                Cell cell = row.getCell(colIdx);
                if(cell == null) {
                    colDataList.add("");
                    continue;
                }
                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_STRING:
                        colDataList.add(cell.getStringCellValue());
                        break;
                    case Cell.CELL_TYPE_FORMULA:
                    case Cell.CELL_TYPE_NUMERIC:
                        if ("@".equals(cell.getCellStyle().getDataFormatString())) {
                            colDataList.add(df.format(cell.getNumericCellValue())) ;
                        } else if ("General".equals(cell.getCellStyle()
                                .getDataFormatString())) {
                            colDataList.add(nf.format(cell.getNumericCellValue())) ;
                        } else {
                            colDataList.add(sdf.format(HSSFDateUtil.getJavaDate(cell
                                    .getNumericCellValue()))) ;
                        }
                        break;
                    case Cell.CELL_TYPE_BOOLEAN:
                        colDataList.add(cell.getBooleanCellValue() + "") ;
                        break;
                    case Cell.CELL_TYPE_BLANK:
                        colDataList.add("");
                        break;
                    default:
                        colDataList.add(cell.getStringCellValue() == null ? "" : cell.getStringCellValue());
                }
            }
            rowDataList.add(colDataList);
        }
        return rowDataList;
    }
}
