package org.sally.view.authority;

import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.sally.entities.authority.Role;
import org.sally.view.AbstractExcelView;
import org.springframework.stereotype.Component;

@Component
public class RoleExcelView extends AbstractExcelView<Role>
{

	public void setRows(Sheet sheet, List<Role> list)
	{
		int rowCount = 1;
		for (Role role : list)
		{
			Row row = sheet.createRow(rowCount++);
			row.createCell(0).setCellValue(role.getRole_no());
			row.createCell(1).setCellValue(role.getRole_name());
			row.createCell(2).setCellValue(role.isIs_admin());
		}
	}
}
