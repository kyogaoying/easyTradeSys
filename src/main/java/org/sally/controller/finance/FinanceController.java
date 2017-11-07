package org.sally.controller.finance;

import java.util.List;

import org.sally.constant.EasyTradeConstants;
import org.sally.entities.Condition;
import org.sally.entities.ExecuteResult;
import org.sally.entities.sales.PiInfo;
import org.sally.service.finance.FinanceService;
import org.sally.service.sales.PiInfoService;
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
@RequestMapping("/finance")
public class FinanceController
{
	private static final Logger logger = LoggerFactory.getLogger(FinanceController.class);

	@Autowired
	private FinanceService financeService;

	@Autowired
	private PiInfoService piInfoService;

	// 导出数据的集合
	private List<PiInfo> exportList;

	// 导出的列名
	private String[] colNames;

	@RequestMapping(value = "/generate", consumes = "application/json")
	@ResponseBody
	public Object generate(String pi_no)
	{
		ExecuteResult er = null;
		try
		{
			er = financeService.generate(pi_no);
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
	 * 查询所有符合条件的入库单
	 * 
	 * @param currPage
	 *            当前页码
	 * @param conditions
	 *            条件集合
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
			List<PiInfo> list = piInfoService.find(Integer.parseInt(currPage), conditions);
			// 查询符合条件的记录数
			long totalCount = piInfoService.getCount(conditions);
			// 根据总记录数和每页显示行数计算总页数,注意要使用double计算结果
			int totalPages = (int) Math.ceil((double) totalCount / (double) EasyTradeConstants.COUNT_PER_PAGE);
			// 将数据转换为JSON
			result = JsonUtil.genQueryJsonString(list, totalPages, null);
		}
		catch (Exception e)
		{
			// er.setMessage(e.getLocalizedMessage());
			// logger.error(e.getLocalizedMessage());
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * 查询所有符合条件的入库单
	 *
	 * @param conditions
	 *            条件集合
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
			List<PiInfo> list = piInfoService.findAll(conditions);
			// 将数据转换为JSON
			result = JsonUtil.genQueryJsonString(list, null);
		}
		catch (Exception e)
		{
			// er.setMessage(e.getLocalizedMessage());
			// logger.error(e.getLocalizedMessage());
			e.printStackTrace();
		}

		return result;
	}

	@RequestMapping("/export")
	@ResponseBody
	public String export(String colNames, @RequestBody List<PiInfo> list)
	{
		this.colNames = colNames.split(",");
		exportList = list;
		return "{\"url\":" + "\"realExport\"}";
	}

	@RequestMapping("/realExport")
	public String realExport(Model model)
	{
		model.addAttribute("data", exportList);
		model.addAttribute("colNames", colNames);
		model.addAttribute("fun_desc", "內部订单管理");
		return "financeExcelView";
	}
}
