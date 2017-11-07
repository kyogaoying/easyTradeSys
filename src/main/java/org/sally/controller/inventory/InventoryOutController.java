package org.sally.controller.inventory;

import java.util.List;

import org.sally.constant.EasyTradeConstants;
import org.sally.entities.Condition;
import org.sally.entities.ExecuteResult;
import org.sally.entities.inventory.InventoryOut;
import org.sally.service.inventory.InventoryOutService;
import org.sally.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 出库单控制对象
 * 
 * @author Sally
 * @since 2017-10-25
 */
@Controller
@RequestMapping("/inventoryOut")
public class InventoryOutController
{
	private static final Logger logger = LoggerFactory.getLogger(InventoryOutController.class);

	@Autowired
	private InventoryOutService inventoryOutService;

	// 导出数据的集合
	private List<InventoryOut> exportList;

	// 导出的列名
	private String[] colNames;
	
	/**
	 * 添加出库单
	 * 
	 * @param inventoryOuts
	 *            出库单集合
	 * @return 执行结果对象
	 */
	@RequestMapping(value = "/add", consumes = "application/json")
	@ResponseBody
	public Object add(@RequestBody List<InventoryOut> inventoryOuts)
	{
		ExecuteResult er = null;
		try
		{
			er = inventoryOutService.add(inventoryOuts);
		}
		catch (Exception e)
		{
			if(er == null) er = new ExecuteResult();
			er.setMessage(e.getLocalizedMessage());
			// logger.error(e.getLocalizedMessage());
			e.printStackTrace();
		}
		return er;
	}

	/**
	 * 删除出库单
	 * 
	 * @param inventoryOuts
	 *            出库单对象集合
	 * @return 执行结果对象
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public ExecuteResult delete(@RequestBody List<InventoryOut> inventoryOuts)
	{
		ExecuteResult er = null;
		try
		{
			er = inventoryOutService.delete(inventoryOuts);
		}
		catch (Exception e)
		{
			if(er == null) er = new ExecuteResult();
			er.setMessage(e.getLocalizedMessage());
			// logger.error(e.getLocalizedMessage());
			e.printStackTrace();
		}

		return er;
	}

	/**
     * 查询所有符合条件的出库单
     * 
     * @param currPage 当前页码
     * @param conditions 条件集合
     * @return 出库单集合(JSON)
     */
	@RequestMapping(value = "/find", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String find(String currPage, @RequestBody List<Condition> conditions)
	{
		String result = "";
		try
		{
			// 查询所有符合条件的出库单集合
			List<InventoryOut> list = inventoryOutService.find(currPage,conditions);
			// 查询符合条件的记录数
			long totalCount = inventoryOutService.getCount(conditions);
			// 根据总记录数和每页显示行数计算总页数,注意要使用double计算结果
			int totalPages = (int)Math.ceil((double)totalCount / (double)EasyTradeConstants.COUNT_PER_PAGE);
			// 将数据转换为JSON
			result = JsonUtil.genQueryJsonString(list,totalPages, null);
		}
		catch (Exception e)
		{
			//er.setMessage(e.getLocalizedMessage());
			// logger.error(e.getLocalizedMessage());
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * 修改出库单
	 * 
	 * @param inventoryOuts
	 *            出库单对象集合
	 * @return 执行结果对象
	 */
	@RequestMapping(value = "/update")
	@ResponseBody
	public ExecuteResult update(@RequestBody List<InventoryOut> inventoryOuts) throws Exception
	{
		ExecuteResult er = null;
		try
		{
			er = inventoryOutService.update(inventoryOuts);
		}
		catch (Exception e)
		{
			if(er == null) er = new ExecuteResult();
			er.setMessage(e.getLocalizedMessage());
			// logger.error(e.getLocalizedMessage());
			e.printStackTrace();
		}

		return er;
	}

	/**
	 * 获取生成的ID
	 * 
	 * @return 生成的ID
	 */
	@RequestMapping(value = "/getId")
	@ResponseBody
	public String getId()
	{
		String id = inventoryOutService.getId();

		String result = "{\"out_list_no\":\"" + id + "\"}";

		return result;
	}

	/**
	 * 查询所有符合条件的入库单
	 *
	 * @param conditions 条件集合
	 * @return 入库单集合(JSON)
	 */
	@RequestMapping(value = "/findAll", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String findAll(@RequestBody List<Condition> conditions)
	{
		String result = "";
		try
		{
			// 查询所有符合条件的集合
			List<InventoryOut> list = inventoryOutService.findAll(conditions);
			// 将数据转换为JSON
			result = JsonUtil.genQueryJsonString(list, null);
		}
		catch (Exception e)
		{
			//er.setMessage(e.getLocalizedMessage());
			// logger.error(e.getLocalizedMessage());
			e.printStackTrace();
		}

		return result;
	}

	@RequestMapping("/export")
	@ResponseBody
	public String export(String colNames,@RequestBody List<InventoryOut> list)
	{
		this.colNames = colNames.split(",");
		exportList = list;
		return "{\"url\":"+"\"realExport\"}";
	}

	@RequestMapping("/realExport")
	public String realExport(Model model)
	{
		model.addAttribute("data",exportList);
		model.addAttribute("colNames",colNames);
		model.addAttribute("fun_desc","出库单管理");
		return "inventoryOutExcelView";
	}
}
