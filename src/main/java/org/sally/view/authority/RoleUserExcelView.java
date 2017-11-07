package org.sally.view.authority;

import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.sally.entities.authority.RoleUser;
import org.sally.view.AbstractExcelView;
import org.springframework.stereotype.Component;

@Component
public class RoleUserExcelView extends AbstractExcelView<RoleUser>
{

	public void setRows(Sheet sheet, List<RoleUser> list)
	{
		int rowCount = 1;
		for (RoleUser roleUser : list)
		{
			Row row = sheet.createRow(rowCount++);
			row.createCell(0).setCellValue(roleUser.getRole_no());
			row.createCell(1).setCellValue(roleUser.getRole_name());
			row.createCell(2).setCellValue(roleUser.getUser_no());
			row.createCell(3).setCellValue(roleUser.getUser_name());
			row.createCell(4).setCellValue(roleUser.isActive());
		}
	}
}
