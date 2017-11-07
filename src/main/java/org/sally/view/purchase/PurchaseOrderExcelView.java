package org.sally.view.purchase;

import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.sally.entities.sales.IntOrder;
import org.sally.view.AbstractExcelView;
import org.springframework.stereotype.Component;

@Component
public class PurchaseOrderExcelView extends AbstractExcelView<IntOrder>
{

	public void setRows(Sheet sheet, List<IntOrder> list)
	{
		int rowCount = 1;
		for (org.sally.entities.sales.IntOrder intOrder : list)
		{
			Row row = sheet.createRow(rowCount++);
			row.createCell(0).setCellValue(intOrder.getInt_order_no());
			row.createCell(1).setCellValue(intOrder.getProd_no());
			row.createCell(2).setCellValue(intOrder.getProd_name());
			row.createCell(3).setCellValue(intOrder.getPi_no());
			row.createCell(4).setCellValue(intOrder.getQty());
			row.createCell(5).setCellValue(intOrder.getExpected_delivery_date());
			row.createCell(6).setCellValue(intOrder.getInt_order_remark());
		}
	}
}
