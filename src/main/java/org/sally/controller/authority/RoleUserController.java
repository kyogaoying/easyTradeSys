package org.sally.controller.authority;

import org.sally.constant.EasyTradeConstants;
import org.sally.entities.Condition;
import org.sally.entities.ExecuteResult;
import org.sally.entities.authority.RoleUser;
import org.sally.entities.authority.RoleUser;
import org.sally.service.authority.RoleService;
import org.sally.service.authority.RoleUserService;
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

@Controller
@RequestMapping("/roleUser")
public class RoleUserController
{
	private static final Logger logger = LoggerFactory.getLogger(RoleUserController.class);

	@Autowired
	private RoleUserService roleUserService;

	// 导出数据的集合
	private List<RoleUser> exportList;

	// 导出的列名
	private String[] colNames;
	
	@RequestMapping(value = "/add", consumes = "application/json")
	@ResponseBody
	public Object add(@RequestBody List<RoleUser> roleUsers)
	{
		ExecuteResult er = null;
		try
		{
			er = roleUserService.add(roleUsers);
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
	 * 删除角色
	 * 
	 * @param roleUsers
	 *            角色对象集合
	 * @return 执行结果对象
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public ExecuteResult delete(@RequestBody List<RoleUser> roleUsers)
	{
		ExecuteResult er = null;
		try
		{
			er = roleUserService.delete(roleUsers);
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
     * 查询所有符合条件的用户
     * 
     * @param currPage 当前页码
     * @param conditions 条件集合
     * @return 用户集合(JSON)
     */
	@RequestMapping(value = "/find", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String find(String currPage, @RequestBody List<Condition> conditions)
	{
		String result = "";
		try
		{
			// 查询所有符合条件的用户集合
			List<RoleUser> list = roleUserService.find(Integer.parseInt(currPage),conditions);
			// 查询符合条件的记录数
			long totalCount = roleUserService.getCount(conditions);
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
     * 修改角色
     * 
     * @param roleUsers 角色对象集合
     * @return 执行结果对象
     */
	@RequestMapping(value="/update")
	@ResponseBody
    public ExecuteResult update(@RequestBody List<RoleUser> roleUsers) throws Exception
	{
		ExecuteResult er = null;
		try
		{
			er = roleUserService.update(roleUsers);
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
			List<RoleUser> list = roleUserService.findAll(conditions);
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
	public String export(String colNames,@RequestBody List<RoleUser> list)
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
		model.addAttribute("fun_desc","角色管理");
		return "roleUserExcelView";
	}
}
