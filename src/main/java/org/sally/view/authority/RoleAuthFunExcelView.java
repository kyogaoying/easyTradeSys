package org.sally.view.authority;

import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.sally.entities.authority.RoleAuthFun;
import org.sally.view.AbstractExcelView;
import org.springframework.stereotype.Component;

@Component
public class RoleAuthFunExcelView extends AbstractExcelView<RoleAuthFun>
{

	public void setRows(Sheet sheet, List<RoleAuthFun> list)
	{
		int rowCount = 1;
		for (RoleAuthFun roleAuthFun : list)
		{
			Row row = sheet.createRow(rowCount++);
			row.createCell(0).setCellValue(roleAuthFun.getRole_no());
			row.createCell(1).setCellValue(roleAuthFun.getRole_name());
			row.createCell(2).setCellValue(roleAuthFun.getModule_no());
			row.createCell(3).setCellValue(roleAuthFun.getModule_name());
			row.createCell(4).setCellValue(roleAuthFun.getFun_flag());
			row.createCell(5).setCellValue(roleAuthFun.getFun_desc());
			row.createCell(6).setCellValue(roleAuthFun.isActive());
		}
	}
}
