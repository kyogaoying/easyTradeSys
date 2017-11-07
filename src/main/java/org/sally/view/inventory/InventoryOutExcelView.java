package org.sally.view.inventory;

import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.sally.entities.inventory.InventoryOut;
import org.sally.view.AbstractExcelView;
import org.springframework.stereotype.Component;

@Component
public class InventoryOutExcelView extends AbstractExcelView<InventoryOut>
{

	public void setRows(Sheet sheet, List<InventoryOut> list)
	{
		int rowCount = 1;
		for (InventoryOut inventoryOut : list)
		{
			Row row = sheet.createRow(rowCount++);
			row.createCell(0).setCellValue(inventoryOut.getOut_list_no());
			row.createCell(1).setCellValue(inventoryOut.getProd_no());
			row.createCell(2).setCellValue(inventoryOut.getProd_name());
			row.createCell(3).setCellValue(inventoryOut.getOut_qty());
			row.createCell(4).setCellValue(inventoryOut.getOut_date());
			row.createCell(5).setCellValue(inventoryOut.getPur_order_no());
			row.createCell(6).setCellValue(inventoryOut.getInt_order_no());
		}
	}
}
