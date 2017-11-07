package org.sally.controller.purchase;

import java.util.List;

import org.sally.entities.purchase.PurchaseProdInfo;
import org.sally.service.purchase.PurchaseProdInfoService;
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
@RequestMapping("/purchaseProdManagement")
public class PurchaseProdInfoController {
	@Autowired
	private PurchaseProdInfoService purchaseProdService;//用于调用业务层方法
	/**
	 * 查询所有用户信息
	 * @return 视图名
	 */
	@RequestMapping("/allInfos")
	public String getAllInfos(@RequestParam("page") int page, Model model) {
		List<PurchaseProdInfo> allInfos=purchaseProdService.getAllInfos(page);
		model.addAttribute("allInfos", allInfos);
		//将新页数放进model和session
		model.addAttribute("page",page);
		//获取总页数，放进model和session
		int totalPages=(int)purchaseProdService.getTotalPages();
		model.addAttribute("totalPages",totalPages);
		return "purchase/purchaseProdManagement";
	}
	/**
	 * DML操作成功后，获取全部用户信息的ajax请求被此方法处理
	 * @param 当前页数
	 * @return 包含全部用户对象集合的JSON对象
	 */
	@RequestMapping(value="/queryAfterDML",produces = { "application/json;charset=UTF-8" })
	@ResponseBody
		public Object queryAfterDML(@RequestParam("page") int page) {
			List<PurchaseProdInfo> allInfos=purchaseProdService.getAllInfos(page);
			return allInfos;
		}
	/**
	 * 点击“开始检索”按钮后，获取指定用户信息的ajax请求被此方法处理
	 * @param page 页码
	 * @param purchaseProdInfo 封装的待查询的PurchaseVendorInfo对象
	 * @return 包含指定用户对象集合的JSON对象
	 */
	@RequestMapping(value="/findSpecified",produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public Object findSpecified(@RequestParam("page") int page, @RequestBody PurchaseProdInfo purchaseProdInfo) {
		String prod_no=purchaseProdInfo.getProd_no();
		List<PurchaseProdInfo> purchaseProdInfoList=purchaseProdService.getOne(prod_no,page);
		return purchaseProdInfoList;
	}
	/**
	 * 点击“开始检索”按钮后，请求获取总页数的ajax请求被此方法处理
	 * @return 包含更新行数的JSON对象
	 */
	@RequestMapping(value="/getNewTotalPages",produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String getNewTotalPages(@RequestBody PurchaseProdInfo purchaseProdInfo) {
		//获取总页数
		int newTotalPages=(int)purchaseProdService.getTotalPagesAfterFindOne(purchaseProdInfo.getProd_no());
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
	public String updateAndSave(@RequestBody List<PurchaseProdInfo> specifiedInfos) {
		int count=0;
		for (PurchaseProdInfo purchaseProdInfo : specifiedInfos) {
			purchaseProdService.updateAndSave(purchaseProdInfo);
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
		String nextVal=purchaseProdService.getNextVal();
		String result = "{\"prod_no\":"+"\""+nextVal+"\"}";
		return result;
	}
	/**
	 * 删除用户时，向数据库发送删除信息的ajax请求被此方法处理
	 * @return 包含影响行数的JSON对象
	 */
	@RequestMapping(value="/delete",produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String delete(@RequestBody List<PurchaseProdInfo> specifiedInfos) {	
		purchaseProdService.delete(specifiedInfos);
		return "{\"count\":"+specifiedInfos.size()+"}";
	}
	/**
	 * 添加和修改用户时，向数据库发送查询信息的ajax请求被此方法处理
	 * @return 包含查询结果的JSON对象
	 */
	@RequestMapping(value="/verifyAddandEdit",produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String verifyAddandEdit(@RequestBody PurchaseProdInfo purchaseProdInfo) {
		String vendor_no=purchaseProdInfo.getVendor_no();
		//查询是否此vendor_no存在于供应商表中,存在返回true,不存在返回false
		boolean result=purchaseProdService.verifyAddandEdit(vendor_no);
		return "{\"result\":"+result+"}";
	}
}