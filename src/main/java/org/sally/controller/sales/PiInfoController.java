package org.sally.controller.sales;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.sally.entities.Condition;
import org.sally.entities.purchase.PurchaseProdInfo;
import org.sally.entities.sales.CustomerInfo;
import org.sally.entities.sales.PiInfo;
import org.sally.entities.sales.QuotationInfo;
import org.sally.service.sales.PiInfoService;
import org.sally.service.sales.QuotationService;
import org.sally.util.HibernateUtil;
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
@RequestMapping("/piManagement")
public class PiInfoController {
	@Autowired
	private PiInfoService piInfoService;//用于调用业务层方法
	@Autowired
	private HibernateUtil hibernateUtil;//用于调用工具类方法
	
	/**
	 * 查询所有用户信息
	 * @return 视图名
	 */
	@RequestMapping("/allInfos")
	public String getAllInfos(@RequestParam("page") int page, Model model) {
		List<Object[]> list=piInfoService.getAllInfos(page);
		model.addAttribute("allInfos", list);
		//将新页数放进model和session
		model.addAttribute("page",page);
		//获取总页数，放进model和session
		int totalPages=(int)piInfoService.getTotalPages();
		model.addAttribute("totalPages",totalPages);
		return "sales/piManagement";
	}	
	/**
	 * 
	 * @param quotation_no
	 * @param model
	 * @return 视图页面
	 */
	@RequestMapping("/generatePi")
	public String generatePi(@RequestParam("quotation_no")String quotation_no,Model model) {
		List<QuotationInfo> quotationInfos=piInfoService.generatePi(quotation_no);
		model.addAttribute("quotationInfos", quotationInfos);
		
		//获取报价单流水号，设置进Model
		String pi_no=hibernateUtil.generateFlowId("PI", "pi_no", "PI_INFO_TAB", 3, true);
		model.addAttribute("flowNo",pi_no);
		//获取报价日期,设置进Model
		String pi_date=new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		model.addAttribute("generateDate", pi_date);
		
		return "sales/newPi";
	}
	/**
	 * DML操作成功后，获取全部pi信息的ajax请求被此方法处理
	 * @param 当前页数
	 * @return 包含全部报价单对象集合的JSON对象
	 */
	@RequestMapping(value="/queryAfterDML",produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public Object queryAfterDML(@RequestParam("page") int page) {
		List<Object[]> allInfos=piInfoService.getAllInfos(page);
		return allInfos;
	}
	
	/**
	 * 查询指定的pi记录(用HQL)
	 * @return  回调方法需要的对象
	 */
	@RequestMapping(value="/queryOne",produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public List<Object[]> queryOne(@RequestBody List<Condition> conditions, @RequestParam("page")Integer page) {
		return piInfoService.queryOne(conditions,page);
	}
	
	/**
	 * 查询单张单据里的全部信息
	 * @param 报价单号
	 * @return 视图名
	 */
	@RequestMapping("/queryPi")
	public String queryPi(@RequestParam("pi_no") String pi_no, Model model) {
		List<PiInfo> list=piInfoService.queryPi(pi_no);
		model.addAttribute("piInfos",list);
		return "sales/queryPi";
	}
	
	/**
	 * 删除指定的报价记录(用HQL)
	 * @return  回调方法需要的对象
	 */
	@RequestMapping(value="/deletePiByHQL",produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public int deleteQuotationByHQL(@RequestBody List<PiInfo> piInfos) {
		return piInfoService.deleteQuotationByHQL(piInfos);
	}
	/**
	 * 查询后，获取新的总页数
	 * @return 包含全部报价单对象集合的JSON对象
	 */
	@RequestMapping(value="/getNewTotalPages",produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String getNewTotalPages(@RequestBody List<Condition> conditions) {
		//获取总页数
		int newTotalPages=(int)piInfoService.getTotalPagesAfterFindOne(conditions);
		//封装成JSON对象
		String result="{\"count\":"+newTotalPages+"}";
		return result;
	}
	
	/**
	 * 保存一份PI
	 * @return  回调方法需要的对象
	 */
	@RequestMapping(value="/savePi",produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public List<PiInfo> savePi(@RequestBody List<PiInfo> piInfos){
		return piInfoService.savePi(piInfos);
	}
	
	
	
	/**
	 * 删除指定的报价记录（用session.delete()方法）
	 * @return  回调方法需要的对象
	 *//*
	@RequestMapping(value="/deletePi",produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public int deletePi(@RequestBody List<PiInfo> piInfos) {
		return piService.deletePi(piInfos);
	}
	*/
	/**
	 * 跳转至更新报价单页面
	 * @return  视图名
	 *//*
	@RequestMapping("/updateQuotation")
	public String updateQuotation(@RequestParam("quotation_no") String quotation_no, Model model) {
		model.addAttribute("quotation_no", quotation_no);
		return "sales/updateQuotation";
	}
	*//**
	 * 寻找指定客户编号是否存在
	 * @return 回调方法需要的对象
	 *//*
	@RequestMapping(value="/findCustomerNo",produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public CustomerInfo findCustomerNo(@RequestBody CustomerInfo customerInfo) {
		String customer_no=customerInfo.getCustomer_no();
		return quotationService.findCustomerNo(customer_no);
	}
	*//**
	 * 寻找指定产品编号是否存在
	 * @return  回调方法需要的对象
	 *//*
	@RequestMapping(value="/findProdNo",produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public PurchaseProdInfo findProdNo(@RequestBody PurchaseProdInfo purchaseProdInfo) {
		String prod_no=purchaseProdInfo.getProd_no();
		return quotationService.findProdNo(prod_no);
	}
	*//**
	 * 寻找指定报价单号对应的报价信息
	 * @return 回调方法需要的对象
	 *//*
	@RequestMapping(value="/findQuotationNo",produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public List<QuotationInfo> findQuotationNo(@RequestBody QuotationInfo quotationInfo, Model model) {
		String quotation_no=quotationInfo.getQuotation_no();
		List<QuotationInfo> quotationInfos=quotationService.queryQuotation(quotation_no);
		return quotationInfos;
	}
	*/
}