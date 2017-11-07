package org.sally.controller.sales;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.sally.entities.Condition;
import org.sally.entities.purchase.PurchaseProdInfo;
import org.sally.entities.sales.CustomerInfo;
import org.sally.entities.sales.QuotationInfo;
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
@RequestMapping("/quotationManagement")
public class QuotationInfoController {
	@Autowired
	private QuotationService quotationService;//用于调用业务层方法
	@Autowired
	private HibernateUtil hibernateUtil;//用于调用工具类方法
	/**
	 * 查询所有用户信息
	 * @return 视图名
	 */
	@RequestMapping("/allInfos")
	public String getAllInfos(@RequestParam("page") int page, Model model) {
		List<Object[]> list=quotationService.getAllInfos(page);
		model.addAttribute("allInfos", list);
		//将新页数放进model和session
		model.addAttribute("page",page);
		//获取总页数，放进model和session
		int totalPages=(int)quotationService.getTotalPages();
		model.addAttribute("totalPages",totalPages);
		return "sales/quotationManagement";
	}
	/**
	 * 跳转至更新报价单页面
	 * @return  视图名
	 */
	@RequestMapping("/updateQuotation")
	public String updateQuotation(@RequestParam("quotation_no") String quotation_no, Model model) {
		model.addAttribute("quotation_no", quotation_no);
		return "sales/updateQuotation";
	}
	/**
	 * 查询单张单据里的全部信息
	 * @param 报价单号
	 * @return 视图名
	 */
	@RequestMapping("/queryQuotation")
	public String queryQuotation(@RequestParam("quotation_no") String quotation_no, Model model) {
		List<QuotationInfo> list=quotationService.queryQuotation(quotation_no);
		model.addAttribute("quotationInfos",list);
		return "sales/queryQuotation";
	}
	/**
	 * 新建一张报价单
	 * @return 视图名
	 */
	@RequestMapping("/newQuotation")
	public String newQuotation(Model model) {
		//获取报价单流水号，设置进Model
		String quotation_no=hibernateUtil.generateFlowId("Q", "quotation_no", "quotation_tab", 3, true);
		model.addAttribute("quotation_no", quotation_no);
		//获取报价日期,设置进Model
		String quotation_date=new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		model.addAttribute("quotation_date", quotation_date);
		return "sales/newQuotation";
	}
	/**
	 * 寻找指定客户编号是否存在
	 * @return 回调方法需要的对象
	 */
	@RequestMapping(value="/findCustomerNo",produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public CustomerInfo findCustomerNo(@RequestBody CustomerInfo customerInfo) {
		String customer_no=customerInfo.getCustomer_no();
		return quotationService.findCustomerNo(customer_no);
	}
	/**
	 * 寻找指定产品编号是否存在
	 * @return  回调方法需要的对象
	 */
	@RequestMapping(value="/findProdNo",produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public PurchaseProdInfo findProdNo(@RequestBody PurchaseProdInfo purchaseProdInfo) {
		String prod_no=purchaseProdInfo.getProd_no();
		return quotationService.findProdNo(prod_no);
	}
	/**
	 * 寻找指定报价单号对应的报价信息
	 * @return 回调方法需要的对象
	 */
	@RequestMapping(value="/findQuotationNo",produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public List<QuotationInfo> findQuotationNo(@RequestBody QuotationInfo quotationInfo, Model model) {
		String quotation_no=quotationInfo.getQuotation_no();
		List<QuotationInfo> quotationInfos=quotationService.queryQuotation(quotation_no);
		return quotationInfos;
	}
	/**
	 * 保存一份报价单
	 * @return  回调方法需要的对象
	 */
	@RequestMapping(value="/saveQuotation",produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public List<QuotationInfo> saveQuotation(@RequestBody List<QuotationInfo> quotationInfos){
		return quotationService.saveQuotation(quotationInfos);
	}
	/**
	 * DML操作成功后，获取全部报价信息的ajax请求被此方法处理
	 * @param 当前页数
	 * @return 包含全部报价单对象集合的JSON对象
	 */
	@RequestMapping(value="/queryAfterDML",produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public Object queryAfterDML(@RequestParam("page") int page) {
		List<Object[]> allInfos=quotationService.getAllInfos(page);
		return allInfos;
	}
	/**
	 * 删除指定的报价记录（用session.delete()方法）
	 * @return  回调方法需要的对象
	 */
	@RequestMapping(value="/deleteQuotation",produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public int deleteQuotation(@RequestBody List<QuotationInfo> quotationInfos) {
		return quotationService.deleteQuotation(quotationInfos);
	}
	/**
	 * 删除指定的报价记录(用HQL)
	 * @return  回调方法需要的对象
	 */
	@RequestMapping(value="/deleteQuotationByHQL",produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public int deleteQuotationByHQL(@RequestBody List<QuotationInfo> quotationInfos) {
		return quotationService.deleteQuotationByHQL(quotationInfos);
	}
	/**
	 * 删除指定的报价记录(用HQL)
	 * @return  回调方法需要的对象
	 */
	@RequestMapping(value="/queryOne",produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public List<Object[]> queryOne(@RequestBody List<Condition> conditions, @RequestParam("page")Integer page) {
		return quotationService.queryOne(conditions,page);
	}
	/**
	 * 查询后，获取新的总页数
	 * @return 包含全部报价单对象集合的JSON对象
	 */
	@RequestMapping(value="/getNewTotalPages",produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String getNewTotalPages(@RequestBody List<Condition> conditions) {
		//获取总页数
		int newTotalPages=(int)quotationService.getTotalPagesAfterFindOne(conditions);
		//封装成JSON对象
		String result="{\"count\":"+newTotalPages+"}";
		return result;
	}
}