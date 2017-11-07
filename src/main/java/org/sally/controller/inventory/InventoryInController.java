package org.sally.controller.inventory;

import java.util.List;

import org.sally.constant.EasyTradeConstants;
import org.sally.entities.Condition;
import org.sally.entities.ExecuteResult;
import org.sally.entities.inventory.InventoryIn;
import org.sally.service.inventory.InventoryInService;
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
 * 入库单控制对象
 * 
 * @author Sally
 * @since 2017-10-20
 */
@Controller
@RequestMapping("/inventoryIn")
public class InventoryInController
{
	private static final Logger logger = LoggerFactory.getLogger(InventoryInController.class);

	@Autowired
	private InventoryInService inventoryInService;

	// 导出数据的集合
	private List<InventoryIn> exportList;

	// 导出的列名
	private String[] colNames;
	
	/**
	 * 添加入库单
	 * 
	 * @param inventoryIns
	 *            入库单集合
	 * @return 执行结果对象
	 */
	@RequestMapping(value = "/add", consumes = "application/json")
	@ResponseBody
	public Object add(@RequestBody List<InventoryIn> inventoryIns)
	{
		ExecuteResult er = null;
		try
		{
			er = inventoryInService.add(inventoryIns);
		}
		catch (Exception e)
		{
			er = new ExecuteResult();
			er.setMessage(e.getLocalizedMessage());
			// logger.error(e.getLocalizedMessage());
			e.printStackTrace();
		}
		return er;
	}

	/**
	 * 删除入库单
	 * 
	 * @param inventoryIns
	 *            入库单对象集合
	 * @return 执行结果对象
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public ExecuteResult delete(@RequestBody List<InventoryIn> inventoryIns)
	{
		ExecuteResult er = null;
		try
		{
			er = inventoryInService.delete(inventoryIns);
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
     * 查询所有符合条件的入库单
     * 
     * @param currPage 当前页码
     * @param conditions 条件集合
     * @return 入库单集合(JSON)
     */
	@RequestMapping(value = "/find", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String find(String currPage, @RequestBody List<Condition> conditions)
	{
		String result = "";
		try
		{
			// 查询所有符合条件的入库单集合
			List<InventoryIn> list = inventoryInService.find(currPage,conditions);
			// 查询符合条件的记录数
			long totalCount = inventoryInService.getCount(conditions);
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
	 * 修改入库单
	 * 
	 * @param inventoryIns
	 *            入库单对象集合
	 * @return 执行结果对象
	 */
	@RequestMapping(value = "/update")
	@ResponseBody
	public ExecuteResult update(@RequestBody List<InventoryIn> inventoryIns) throws Exception
	{
		ExecuteResult er = null;
		try
		{
			er = inventoryInService.update(inventoryIns);
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
		String id = inventoryInService.getId();

		String result = "{\"in_list_no\":\"" + id + "\"}";

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
			List<InventoryIn> list = inventoryInService.findAll(conditions);
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
	public String export(String colNames,@RequestBody List<InventoryIn> list)
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
		model.addAttribute("fun_desc","入库单管理");
		return "inventoryInExcelView";
	}
}
