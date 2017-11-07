package org.sally.controller;

import org.sally.constant.EasyTradeConstants;
import org.sally.entities.Condition;
import org.sally.entities.UserInfo;
import org.sally.service.UserService;
import org.sally.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
@SessionAttributes({"page","totalPages"})
@RequestMapping("/userManagement")
public class UserController {
	@Autowired
	private UserService userService;//用于调用业务层方法
	/**
	 * 查询所有用户信息
	 * @return 视图名
	 */
	@RequestMapping("/userInfos")
	public String getUserInfos(@RequestParam("page") int page, Model model) {
		List<UserInfo> allUsers=userService.getUserInfos(page);
		model.addAttribute("allUsers", allUsers);
		//将新页数放进model和session
		model.addAttribute("page",page);
		//获取总页数，放进model和session
		int totalPages=(int)userService.getTotalPages();
		model.addAttribute("totalPages",totalPages);
		return "shortcut/userManagement";
	}
	/**
	 * DML操作成功后，获取全部用户信息的ajax请求被此方法处理
	 * @param 当前页数
	 * @return 包含全部用户对象集合的JSON对象
	 */
	@RequestMapping(value="/queryAfterDML",produces = { "application/json;charset=UTF-8" })
	@ResponseBody
		public Object queryAfterDML(@RequestParam("page") int page) {
			List<UserInfo> userInfos=userService.getUserInfos(page);
			return userInfos;
		}
	/**
	 * 点击“开始检索”按钮后，获取指定用户信息的ajax请求被此方法处理
	 * @param page 页码
	 * @param userInfo 封装的待查询的UserInfo对象
	 * @return 包含指定用户对象集合的JSON对象
	 */
	@RequestMapping(value="/findUser",produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public Object findUser(@RequestParam("page") int page, @RequestBody UserInfo userInfo) {
		String user_name=userInfo.getUser_name();
		List<UserInfo> userInfoList=userService.getUser(user_name,page);
		return userInfoList;
	}
	/**
	 * 点击“开始检索”按钮后，请求获取总页数的ajax请求被此方法处理
	 * @return 包含更新行数的JSON对象
	 */
	@RequestMapping(value="/getNewTotalPages",produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String getNewTotalPages(@RequestBody UserInfo userInfo) {
		//获取总页数
		int newTotalPages=(int)userService.getTotalPagesAfterFindOne(userInfo.getUser_name());
		//封装成JSON对象
		String result="{\"count\":"+newTotalPages+"}";
		return result;
	}
	
	/**
	 * 修改用户时，修改用户的ajax请求被此方法处理
	 * @return 包含更新行数的JSON对象
	 */
	@RequestMapping(value="/updateAndSaveUser",produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String updateAndSaveUser(@RequestBody List<UserInfo> userInfos) {
		int count=0;
		for (UserInfo userInfo : userInfos) {
			userService.updateAndSaveUser(userInfo);
			//修改一行加1
			count++;
		}
		String result= "{\"count\":"+count+"}";
		return result;
	}
	/**
	 * 添加用户时，获取nextVal的ajax请求被此方法处理
	 * @return 包含下一个用户编号的JSON对象
	 */
	@RequestMapping(value="/getNextVal",produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String getNextVal() {
		String nextVal=userService.getNextVal();
		String result = "{\"user_no\":"+"\""+nextVal+"\"}";
		return result;
	}
	/**
	 * 删除用户时，向数据库发送删除信息的ajax请求被此方法处理
	 * @return 包含影响行数的JSON对象
	 */
	@RequestMapping(value="/deleteUser",produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String deleteUser(@RequestBody List<UserInfo> userInfos) {	
		userService.deleteUser(userInfos);
		return "{\"count\":"+userInfos.size()+"}";
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
			List<UserInfo> list = userService.find(Integer.parseInt(currPage),conditions);
			// 查询符合条件的记录数
			long totalCount = userService.getCount(conditions);
			// 根据总记录数和每页显示行数计算总页数,注意要使用double计算结果
			int totalPages = (int)Math.ceil((double)totalCount / (double) EasyTradeConstants.COUNT_PER_PAGE);
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
}