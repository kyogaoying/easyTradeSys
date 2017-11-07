package org.sally.controller.authority;

import org.sally.constant.EasyTradeConstants;
import org.sally.entities.Condition;
import org.sally.entities.ExecuteResult;
import org.sally.entities.authority.RoleAuthFun;
import org.sally.service.authority.RoleAuthFunService;
import org.sally.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 角色权限控制对象
 * 
 * @author Sally
 * @since 2017-10-18
 */
@Controller
@RequestMapping("/roleAuthFun")
public class RoleAuthFunController
{
	private static final Logger logger = LoggerFactory.getLogger(RoleAuthFunController.class);

	@Autowired
	private RoleAuthFunService roleAuthFunService;

	// 导出数据的集合
	private List<RoleAuthFun> exportList;

	// 导出的列名
	private String[] colNames;

	/**
	 * 添加角色权限
	 * 
	 * @param roleAuthFuns 角色权限集合
	 * @return 执行结果对象
	 */
	@RequestMapping(value = "/add", consumes = "application/json")
	@ResponseBody
	public Object add(@RequestBody List<RoleAuthFun> roleAuthFuns)
	{
		ExecuteResult er = null;
		try
		{
			er = roleAuthFunService.add(roleAuthFuns);
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
	 * 删除角色权限
	 * 
	 * @param roleAuthFuns
	 *            角色权限对象集合
	 * @return 执行结果对象
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public ExecuteResult delete(@RequestBody List<RoleAuthFun> roleAuthFuns)
	{
		ExecuteResult er = null;
		try
		{
			er = roleAuthFunService.delete(roleAuthFuns);
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
     * 查询所有符合条件的模块功能
     * 
     * @param currPage 当前页码
     * @param conditions 条件集合
     * @return 模块功能集合(JSON)
     */
	@RequestMapping(value = "/find", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String find(String currPage, @RequestBody List<Condition> conditions)
	{
		String result = "";
		try
		{
			// 查询所有符合条件的模块功能集合
			List<RoleAuthFun> list = roleAuthFunService.find(Integer.parseInt(currPage),conditions);
			// 查询符合条件的记录数
			long totalCount = roleAuthFunService.getCount(conditions);
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
     * 修改角色权限
     * 
     * @param roleAuthFuns 角色权限对象集合
     * @return 执行结果对象
     */
	@RequestMapping(value="/update")
	@ResponseBody
    public ExecuteResult update(@RequestBody List<RoleAuthFun> roleAuthFuns) throws Exception
	{
		ExecuteResult er = null;
		try
		{
			er = roleAuthFunService.update(roleAuthFuns);
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
	 * 查询所有符合条件的角色功能权限
	 *
	 * @param conditions 条件集合
	 * @return 模块集合(JSON)
	 */
	@RequestMapping(value = "/findAll", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String findAll(@RequestBody List<Condition> conditions)
	{
		String result = "";
		try
		{
			// 查询所有符合条件的集合
			List<RoleAuthFun> list = roleAuthFunService.findAll(conditions);
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
	public String export(String colNames,@RequestBody List<RoleAuthFun> list)
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
		model.addAttribute("fun_desc","角色权限管理");
		return "roleAuthFunExcelView";
	}
}
