package org.sally.view;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 导出EXCEL的视图抽象类
 *
 * @author Sally
 * @since 2017-10-31
 *
 * @param <T> 包含数据行的集合存储的对象类型
 */
public abstract class AbstractExcelView<T> extends AbstractXlsView
{

    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook,
                                      HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        // 获取模块功能名
        String fun_desc = (String) model.get("fun_desc");
        // 设置导出EXCEL的文件名
        String fileName = setFileName(fun_desc);
        // 设置响应头
        response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes(), "iso8859-1"));
        List<T> list = (List<T>) model.get("data");
        // 创建Excel的sheet
        Sheet sheet = setSheet(workbook,fun_desc+" Detail");
        // 设置单元格样式
        CellStyle style = setCellStyle(workbook);
        // 设置字体
        Font font = setFont(workbook);
        // 将字体应用到样式
        style.setFont(font);
        // 创建头
        Row header = sheet.createRow(0);
        // 取出表的列名
        String[] colNames = (String[]) model.get("colNames");
        for(int i = 0; i < colNames.length;i++)
        {
            // 创建单元格并设置单元格的值
            header.createCell(i).setCellValue(colNames[i]);
            // 获取单元格并设置单元格的样式
            header.getCell(i).setCellStyle(style);
        }

        // 设置数据行
        setRows(sheet,list);
}

    /**
     * 设置数据行
     *
     * @param sheet sheet对象
     * @param list 包含数据的集合
     */
    public abstract void setRows(Sheet sheet,List<T> list);

    /**
     * 设置导出excel的文件名
     *
     * @param fun_desc 导出文件的所属模块功能名
     * @return
     */
    public String setFileName(String fun_desc)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String date = sdf.format(new Date());
        String fileName = date+"-"+fun_desc+".xls";

        return fileName;
    }

    /**
     * 创建Excel的sheet
     *
     * @param workbook 工作簿对象
     * @param sheetName sheet的名称
     * @return Sheet
     */
    public Sheet setSheet(Workbook workbook,String sheetName)
    {
        // 建立sheet
        Sheet sheet = workbook.createSheet(sheetName);
        // 设置sheet中每一列的默认宽度
        sheet.setDefaultColumnWidth(30);
        return sheet;
    }

    /**
     * 设置Excel的单元格样式
     *
     * @param workbook 工作簿对象
     * @return 单元格样式
     */
    public CellStyle setCellStyle(Workbook workbook)
    {
        CellStyle style = workbook.createCellStyle();
        // 设置前景色
        style.setFillForegroundColor(HSSFColor.BLUE.index);
        style.setFillPattern((short) 1);

        return style;
    }

    /**
     * 设置字体
     *
     * @param workbook 工作簿对象
     * @return 字体
     */
    public Font setFont(Workbook workbook)
    {
        Font font = workbook.createFont();
        // 设置字体名
        font.setFontName("Arial");
        // 是否粗体
        font.setBold(true);
        // 设置字体颜色
        font.setColor(HSSFColor.WHITE.index);

        return font;
    }
}
