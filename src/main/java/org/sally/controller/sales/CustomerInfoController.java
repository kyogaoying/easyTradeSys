package org.sally.controller.sales;

import java.util.List;

import org.sally.entities.purchase.PurchaseVendorInfo;
import org.sally.entities.sales.CustomerInfo;
import org.sally.service.purchase.PurchaseVendorInfoService;
import org.sally.service.sales.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.web.bind.annotation.SessionAttributes;
@Controller
@SessionAttributes({"page","totalPages"})
@RequestMapping("/customerManagement")
public class CustomerInfoController {
	@Autowired
	private CustomerService customerService;//用于调用业务层方法
	/**
	 * 查询所有用户信息
	 * @return 视图名
	 */
	@RequestMapping("/allInfos")
	public String getAllInfos(@RequestParam("page") int page, Model model) {
		List<CustomerInfo> allInfos=customerService.getAllInfos(page);
		model.addAttribute("allInfos", allInfos);
		//将新页数放进model和session
		model.addAttribute("page",page);
		//获取总页数，放进model和session
		int totalPages=(int)customerService.getTotalPages();
		model.addAttribute("totalPages",totalPages);
		return "sales/customerManagement";
	}
	/**
	 * DML操作成功后，获取全部用户信息的ajax请求被此方法处理
	 * @param 当前页数
	 * @return 包含全部用户对象集合的JSON对象
	 */
	@RequestMapping(value="/queryAfterDML",produces = { "application/json;charset=UTF-8" })
	@ResponseBody
		public Object queryAfterDML(@RequestParam("page") int page) {
			List<CustomerInfo> allInfos=customerService.getAllInfos(page);
			return allInfos;
		}
	/**
	 * 点击“开始检索”按钮后，获取指定用户信息的ajax请求被此方法处理
	 * @param page 页码
	 * @param customerInfo 封装的待查询的PurchaseVendorInfo对象
	 * @return 包含指定用户对象集合的JSON对象
	 */
	@RequestMapping(value="/findSpecified",produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public Object findSpecified(@RequestParam("page") int page, @RequestBody CustomerInfo customerInfo) {
		String customer_name=customerInfo.getCustomer_name();
		List<CustomerInfo> customerInfoList=customerService.getOne(customer_name,page);
		return customerInfoList;
	}
	/**
	 * 点击“开始检索”按钮后，请求获取总页数的ajax请求被此方法处理
	 * @return 包含更新行数的JSON对象
	 */
	@RequestMapping(value="/getNewTotalPages",produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String getNewTotalPages(@RequestBody CustomerInfo customerInfo) {
		//获取总页数
		int newTotalPages=(int)customerService.getTotalPagesAfterFindOne(customerInfo.getCustomer_name());
		//封装成JSON对象
		String result="{\"count\":"+newTotalPages+"}";
		return result;
	}
	
	/**
	 * 修改用户时，修改用户的ajax请求被此方法处理
	 * @return 包含更新行数的JSON对象
	 */
	@RequestMapping(value="/updateAndSave",produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String updateAndSave(@RequestBody List<CustomerInfo> specifiedInfos) {
		int count=0;
		for (CustomerInfo customerInfo : specifiedInfos) {
			customerService.updateAndSave(customerInfo);
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
		String nextVal=customerService.getNextVal();
		String result = "{\"customer_no\":"+"\""+nextVal+"\"}";
		return result;
	}
	/**
	 * 删除用户时，向数据库发送删除信息的ajax请求被此方法处理
	 * @return 包含影响行数的JSON对象
	 */
	@RequestMapping(value="/delete",produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String delete(@RequestBody List<CustomerInfo> specifiedInfos) {	
		customerService.delete(specifiedInfos);
		return "{\"count\":"+specifiedInfos.size()+"}";
	}
}