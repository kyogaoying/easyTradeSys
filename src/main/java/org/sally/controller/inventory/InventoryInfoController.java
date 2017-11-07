package org.sally.controller.inventory;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.sally.constant.EasyTradeConstants;
import org.sally.entities.Condition;
import org.sally.entities.ExecuteResult;
import org.sally.entities.inventory.InventoryInfo;
import org.sally.service.inventory.InventoryInfoService;
import org.sally.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 库存信息控制对象
 * 
 * @author Sally
 * @since 2017-10-20
 */
@Controller
@RequestMapping("/inventoryInfo")
public class InventoryInfoController
{
	private static final Logger logger = LoggerFactory.getLogger(InventoryInfoController.class);

	@Autowired
	private InventoryInfoService inventoryInfoService;

	// 导出数据的集合
	private List<InventoryInfo> exportList;

	// 导出的列名
	private String[] colNames;
	
	/**
	 * 添加库存信息
	 * 
	 * @param inventoryInfos
	 *            库存信息集合
	 * @return 执行结果对象
	 */
	@RequestMapping(value = "/add", consumes = "application/json")
	@ResponseBody
	public Object add(@RequestBody List<InventoryInfo> inventoryInfos)
	{
		ExecuteResult er = null;
		try
		{
			er = inventoryInfoService.add(inventoryInfos);
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
	 * 删除库存信息
	 * 
	 * @param inventoryInfos
	 *            库存信息对象集合
	 * @return 执行结果对象
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public ExecuteResult delete(@RequestBody List<InventoryInfo> inventoryInfos)
	{
		ExecuteResult er = null;
		try
		{
			er = inventoryInfoService.delete(inventoryInfos);
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
     * 查询所有符合条件的库存信息
     * 
     * @param currPage 当前页码
     * @param conditions 条件集合
     * @return 库存信息集合(JSON)
     */
	@RequestMapping(value = "/find", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String find(String currPage, @RequestBody List<Condition> conditions)
	{
		String result = "";
		try
		{
			// 查询所有符合条件的库存信息集合
			List<InventoryInfo> list = inventoryInfoService.find(currPage,conditions);
			// 查询符合条件的记录数
			long totalCount = inventoryInfoService.getCount(conditions);
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
	 * 修改库存信息
	 * 
	 * @param inventoryInfos
	 *            库存信息对象集合
	 * @return 执行结果对象
	 */
	@RequestMapping(value = "/update")
	@ResponseBody
	public ExecuteResult update(@RequestBody List<InventoryInfo> inventoryInfos) throws Exception
	{
		ExecuteResult er = null;
		try
		{
			er = inventoryInfoService.update(inventoryInfos);
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
     * 检查该产品编号是否存在
     * 
     * @param prod_no 产品编号
     * @return true/false
     */
	@RequestMapping("/exist")
	@ResponseBody
    public String exist(String prod_no)
    {
    		int exist = inventoryInfoService.exist(prod_no) ? 1 : 0;
    		
    		String result = "{\"exist\":"+ exist +"}";
    		
    		return result;
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
		String id = inventoryInfoService.getId();

		String result = "{\"prod_no\":\"" + id + "\"}";

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
			List<InventoryInfo> list = inventoryInfoService.findAll(conditions);
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
	public String export(String colNames,@RequestBody List<InventoryInfo> list)
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
		model.addAttribute("fun_desc","库存管理");
		return "inventoryInfoExcelView";
	}
}
