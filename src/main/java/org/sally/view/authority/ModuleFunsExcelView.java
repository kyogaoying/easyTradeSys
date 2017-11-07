package org.sally.view.authority;

import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.sally.entities.authority.ModuleFuns;
import org.sally.view.AbstractExcelView;
import org.springframework.stereotype.Component;

@Component
public class ModuleFunsExcelView extends AbstractExcelView<ModuleFuns> {

    public void setRows(Sheet sheet, List<ModuleFuns> list)
    {
        int rowCount = 1;
        for (ModuleFuns moduleFuns : list) {
            Row row = sheet.createRow(rowCount++);
            row.createCell(0).setCellValue(moduleFuns.getModule_no());
            row.createCell(1).setCellValue(moduleFuns.getModule_name());
            row.createCell(2).setCellValue(moduleFuns.getFun_flag());
            row.createCell(3).setCellValue(moduleFuns.getFun_desc());
            row.createCell(4).setCellValue(moduleFuns.isAuth_control());
        }
    }
}
