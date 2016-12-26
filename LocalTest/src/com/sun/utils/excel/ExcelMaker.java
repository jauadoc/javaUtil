package com.sun.utils.excel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 *
 * @author sunx(sunxin@strongit.com.cn)<br/>
 * @version V1.0.0<br/>
 * @see {@link }
 */

public abstract class ExcelMaker {

    /**
     * 当前操作的workbook
     */
    protected HSSFWorkbook workbook;

    /**
     * 当前操作的sheet
     */
    protected HSSFSheet currSheet;

    /**
     * 当前行数
     */
    protected int currRow = 0;

    /**
     * 当前列数
     */
    protected int currCol = 0;

    /**
     * 当前操作的行对象
     */
    protected HSSFRow row = null;

    /**
     * 当前操作的列对象
     */
    protected HSSFCell cell = null;

    /**
     * 跨行影响列记录
     * Map<行数,<列数,该列跨行数>>
     */
    protected Map<Integer, Map<Integer, Integer>> rowAffect = new HashMap<Integer, Map<Integer, Integer>>();

    /**
     * 通用样式
     */
    protected CellStyle commonCellStyle;

    /**
     * 通用字体
     */
    protected HSSFFont font;

    /**
     * 是否设置字体
     */
    protected boolean setCommonFont = false;

    /**
     * 是否设置样式
     */
    protected boolean setCommonStyle = false;

    /**
     * 构建HssfWorkbook对象
     */
    protected void createWorkbook() {

        workbook = new HSSFWorkbook();

        if (!setCommonFont) {
            // 设置字体
            font = workbook.createFont();
            font.setFontName("黑体");
            font.setFontHeightInPoints((short) 14);
        }

        if (!setCommonStyle) {
            commonCellStyle = workbook.createCellStyle();
            commonCellStyle.setBorderBottom(BorderStyle.THIN);
            commonCellStyle.setBorderLeft(BorderStyle.THIN);
            commonCellStyle.setBorderRight(BorderStyle.THIN);
            commonCellStyle.setBorderTop(BorderStyle.THIN);
            commonCellStyle.setAlignment(HorizontalAlignment.CENTER);
            commonCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        }
        commonCellStyle.setFont(font);
    }

    /**
     * 创建sheet
     */
    protected void createSheet() {

        currSheet = workbook.createSheet();
    }

    /**
     * 创建sheet（设置sheet页名称）
     */
    protected void createSheet(String sheetName) {
        if (null != sheetName && "" != sheetName) {
            currSheet = workbook.createSheet();
        } else {
            currSheet = workbook.createSheet(sheetName);
        }
    }

    /**
     * 统一设置当前sheet页的列宽
     * @param sheet
     */
    protected void setSheetColumnWidth(List<Integer> widths) {

        for (int i = 0; i < widths.size(); i++) {

            currSheet.setColumnWidth(currCol, widths.get(currCol) * 256);
            nextCol();
        }

        resetCol();
    }

    /**
     * 设置当前列列宽
     * @param sheet
     */
    protected void setSheetColumnWidth(int width) {

        currSheet.setColumnWidth(currCol, width * 256);
    }

    /**
     * 设置默认样式
     * @param commonCellStyle
     */
    protected void setCommonCellStyle(CellStyle commonCellStyle) {

        this.commonCellStyle = commonCellStyle;
        setCommonStyle = true;
    }

    /**
     * 设置默认字体
     * @param font
     */
    protected void setFont(HSSFFont font) {

        this.font = font;
        setCommonFont = true;
    }

    /**
     * 创建一行
     */
    protected void newLine() {
        nextLine();
        row = currSheet.createRow(currRow);
    }

    /**
     * 第一行
     */
    protected void firstLine() {

        row = currSheet.createRow(currRow);
    }

    /**
     * 重置当前列为0
     */
    protected void resetCol() {

        currCol = 0;
    }

    /**
     * 当前行数+1
     */
    protected void nextLine() {

        currRow += 1;
        resetCol();
    }

    /**
     * 列数加1，下一列
     */
    protected void nextCol() {
        currCol++;
    }

    /**
     *
     * 设置表格体中每个cell中的内容，跨行列(通用样式，可以进行通用样式设定)
     * @param content 单元格内容内容
     * @param rowspan 行数
     * @param colspan 列数
     */
    protected void setContentCell(String content, Integer rowspan, Integer colspan) {

        // 设置跨行影响的列宽度
        setRowspan(rowspan, colspan);

        // 处理跨行
        dealRowspan(commonCellStyle, content);

        // 设置cell的值
        setCellValue(content, commonCellStyle);

        // 合并处理
        dealMerge(rowspan, colspan, commonCellStyle);

        currCol += colspan;
    }

    /**
     *
     * 设置表格体中每个cell中的内容(通用样式，可以进行通用样式设定)
     * @param content 单元格内容内容
     */
    protected void setContentCell(String content) {
        setContentCell(content, 1, 1);
    }

    /**
     *
     * 设置表格体中每个cell中的内容
     * @param content 单元格内容内容
     */
    protected void setContentCell(String content, CellStyle attendStyle) {
        setContentCell(content, 1, 1, attendStyle);
    }

    /**
     *
     * 设置表格体中每个cell中的内容，跨行列
     * @param content 单元格内容内容
     * @param rowspan 行数
     * @param colspan 列数
     * @param attendStyle 自定义样式
     */
    protected void setContentCell(String content, Integer rowspan, Integer colspan, CellStyle attendStyle) {

        // 设置跨行影响的列宽度
        setRowspan(rowspan, colspan);

        // 处理跨行
        dealRowspan(attendStyle, content);

        // 设置cell的值
        setCellValue(content, attendStyle);

        // 合并处理
        dealMerge(rowspan, colspan, attendStyle);

        currCol += colspan;
    }

    /**
     * 设置跨行的信息，在处理跨行时根据该数据进行处理
     * @param rowspan 行数
     * @param colspan 列数
     */
    protected void setRowspan(Integer rowspan, Integer colspan) {

        if (rowspan > 1) {
            // 遍历跨的行数
            for (int i = 1; i < rowspan; i++) {
                // 获取各行受影响的列数
                Map<Integer, Integer> cols = rowAffect.get(currRow + i);
                if (null == cols) {
                    cols = new HashMap<Integer, Integer>();
                }
                // 设置当前行影响的列数
                cols.put(currCol, colspan);
                rowAffect.put(currRow + i, cols);
            }
        }
    }

    /**
     * 处理跨行，根据设置跨行的位置进行创建空值cell
     * @param cellStyle 样式
     * @param name 值
     */
    protected void dealRowspan(CellStyle cellStyle, String name) {
        Map<Integer, Integer> cols = rowAffect.get(currRow);
        if (null != cols && null != cols.get(currCol) && 0 != cols.get(currCol)) {
            // 当前列受影响的列数
            int colsNum = cols.get(currCol);
            for (int i = 0; i < colsNum; i++) {
                cell = row.createCell(currCol);
                setCellValue("", cellStyle);
                currCol++;
            }
            // 跨行处理在设置单元格前进行，连续的跨行需要递归处理
            dealRowspan(cellStyle, name);
        }
    }

    /**
     * 将cell添加到上下文中，并给cell进行设值
     * @param value 单元格值
     * @param cellStyle 单元格样式
     */
    protected void setCellValue(String value, CellStyle cellStyle) {

        cell = row.createCell(currCol);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(value);

    }

    /**
     * 处理合并单元格
     * @param rowspan 行数
     * @param colspan 列数
     * @param cellStyle 单元格样式
     */
    protected void dealMerge(Integer rowspan, Integer colspan, CellStyle cellStyle) {

        if (rowspan > 1 || colspan > 1) {

            CellRangeAddress rangeAddress = new CellRangeAddress(currRow, currRow + rowspan - 1, currCol, currCol + colspan - 1);
            currSheet.addMergedRegion(rangeAddress);
        }

        for (int i = 1; i < colspan; i++) {

            cell = row.createCell(currCol + i);
            cell.setCellStyle(cellStyle);
        }
    }

    protected void write(String fileName) {
        OutputStream out = null;
        try {
            out = new FileOutputStream(new File(fileName));
            workbook.write(out);
        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        } finally {
            try {
                out.close();
            } catch (IOException e) {

            }
        }
    }

    abstract void createExcel();

}
