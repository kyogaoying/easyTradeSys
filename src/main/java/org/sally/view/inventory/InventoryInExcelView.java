package org.sally.view.inventory;

import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.sally.entities.inventory.InventoryIn;
import org.sally.view.AbstractExcelView;
import org.springframework.stereotype.Component;

@Component
public class InventoryInExcelView extends AbstractExcelView<InventoryIn>
{

	public void setRows(Sheet sheet, List<InventoryIn> list)
	{
		int rowCount = 1;
		for (InventoryIn inventoryIn : list)
		{
			Row row = sheet.createRow(rowCount++);
			row.createCell(0).setCellValue(inventoryIn.getIn_list_no());
			row.createCell(1).setCellValue(inventoryIn.getProd_no());
			row.createCell(2).setCellValue(inventoryIn.getProd_name());
			row.createCell(3).setCellValue(inventoryIn.getIn_qty());
			row.createCell(4).setCellValue(inventoryIn.getInventory_loc());
			row.createCell(5).setCellValue(inventoryIn.getIn_date());
			row.createCell(6).setCellValue(inventoryIn.getPur_order_no());
		}
	}
}
