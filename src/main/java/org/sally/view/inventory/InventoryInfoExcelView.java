package org.sally.view.inventory;

import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.sally.entities.inventory.InventoryInfo;
import org.sally.view.AbstractExcelView;
import org.springframework.stereotype.Component;

@Component
public class InventoryInfoExcelView extends AbstractExcelView<InventoryInfo>
{

	public void setRows(Sheet sheet, List<InventoryInfo> list)
	{
		int rowCount = 1;
		for (InventoryInfo inventoryInfo : list)
		{
			Row row = sheet.createRow(rowCount++);
			row.createCell(0).setCellValue(inventoryInfo.getProd_no());
			row.createCell(1).setCellValue(inventoryInfo.getProd_name());
			row.createCell(2).setCellValue(inventoryInfo.getAvailable_qty());
			row.createCell(3).setCellValue(inventoryInfo.getInventory_loc());
		}
	}
}
