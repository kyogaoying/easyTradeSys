package org.sally.view.authority;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.sally.entities.authority.Module;
import org.sally.view.AbstractExcelView;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ModuleExcelView extends AbstractExcelView<Module> {

    public void setRows(Sheet sheet, List<Module> list)
    {
        int rowCount = 1;
        for (Module module : list) {
            Row row = sheet.createRow(rowCount++);
            row.createCell(0).setCellValue(module.getModule_no());
            row.createCell(1).setCellValue(module.getModule_name());
            row.createCell(2).setCellValue(module.getHelp());
            row.createCell(3).setCellValue(module.getRemark());
        }
    }
}
