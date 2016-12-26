package com.sun.utils.excel;

/**
 *
 * @author sunx(sunxin@strongit.com.cn)<br/>
 * @version V1.0.0<br/>
 * @see {@link }
 */

public class Test extends ExcelMaker{

    @Override
    void createExcel() {
        createWorkbook();
        createSheet();

        firstLine();
        setContentCell("2-2", 2, 2);
        setContentCell("1-1");
        setContentCell("3-1", 3, 1);
        setContentCell("1-1");

        newLine();
        setContentCell("1-1");
        setContentCell("1-1");
        setContentCell("1-3", 1, 3);

        newLine();
        setContentCell("1-3", 1, 3);
        setContentCell("1-1");
        setContentCell("1-5", 1, 5);

        write("C:\\dd.xls");
    }

    public static void main(String[] args) {
        Test test = new Test();
        test.createExcel();
    }
}
