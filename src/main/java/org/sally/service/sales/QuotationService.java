package org.sally.service.sales;

import java.util.List;


import org.sally.dao.sales.QuotationInfoDao;
import org.sally.entities.Condition;
import org.sally.entities.purchase.PurchaseProdInfo;
import org.sally.entities.sales.CustomerInfo;
import org.sally.entities.sales.QuotationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuotationService {
	@Autowired
	private QuotationInfoDao quotationInfoDao;
	/**
	 * 全表查询
	 * @param page 要查的页数
	 * @return 查询出的结果
	 */
	public List<Object[]> getAllInfos(int page){
		return quotationInfoDao.queryAll(page);
	}
	/**
	 * 查询单张单据里的全部信息
	 * @param 报价单号
	 * @return 
	 */
	public List<QuotationInfo> queryQuotation(String quotation_no)
	{
		return quotationInfoDao.queryQuotation(quotation_no);
	}
	/**
	 * 根据customer_no查询客户信息
	 * @param customer_no
	 * @return CustomerInfo对象
	 */
	public CustomerInfo findCustomerNo(String customer_no) {
		return quotationInfoDao.findCustomerNo(customer_no);
	}
	/**
	 * 根据prod_no查询客户信息
	 * @param prod_no
	 * @return PurchaseProdInfo对象
	 */
	public PurchaseProdInfo findProdNo(String prod_no) {
		return quotationInfoDao.findProdNo(prod_no);
	}
	/**
	 * 保存一张报价单
	 * @param quotationInfo的list	
	 */
	public List<QuotationInfo> saveQuotation(List<QuotationInfo> quotationInfos) {
		return quotationInfoDao.saveQuotation(quotationInfos);
	}
	/**
	 * 删除指定的报价记录（用session.delete()方法）
	 * @return  回调方法需要的对象
	 */
	public int deleteQuotation(List<QuotationInfo> quotationInfos) {
		return quotationInfoDao.deleteQuotation(quotationInfos);
	}
	/**
	 * 删除指定的报价记录(用HQL)
	 * @return  回调方法需要的对象
	 */
	public int deleteQuotationByHQL(List<QuotationInfo> quotationInfos) {
		return quotationInfoDao.deleteQuotationByHQL(quotationInfos);
	}
	/**
	 * 查询指定记录
	 * @param 查询条件的集合
	 * @return 查询出的结果
	 */
	public List<Object[]> queryOne(List<Condition> conditions,int page){
		return quotationInfoDao.queryOne(conditions,page);
	}
	
	
	/**
     * 获取满足条件的总页数
     * @return 总页数
     */
	public double getTotalPagesAfterFindOne(List<Condition> conditions) {
		return quotationInfoDao.getTotalPagesAfterFindOne(conditions);
	}
    /**
     * 获取总页数
     * @return 总页数
     */
	public double getTotalPages() {
		return quotationInfoDao.getTotalPages();
	}
	/**
	 * 保存
	 * @param customerInfo 要保存的记录
	 *//*
	public void updateAndSave(QuotationInfo customerInfo) {
		quotationInfoDao.updateAndSave(customerInfo);
	}
	
	*//**
	 * 获取下一个编号
	 * @return 编号
	 *//*
	public String getNextVal() {
		int nextVal_int=quotationInfoDao.getMax()+1;
		
		return "C"+String.format("%05d", nextVal_int);
	}
	*//**
	 * 删除记录
	 * @param customerInfos 待删除的记录
	 * @return 删除的行数
	 *//*
	public int delete(List<QuotationInfo> customerInfos) {
		int count=0;
		for (QuotationInfo customerInfo : customerInfos) {
			count=count+quotationInfoDao.delete(customerInfo); 
		}
		return count;
	}*/
}
