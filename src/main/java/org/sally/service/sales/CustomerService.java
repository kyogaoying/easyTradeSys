package org.sally.service.sales;

import java.util.List;


import org.sally.dao.sales.CustomerInfoDao;
import org.sally.entities.sales.CustomerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
	@Autowired
	private CustomerInfoDao customerInfoDao;
	/**
	 * 全表查询
	 * @param page 要查的页数
	 * @return 查询出的结果
	 */
	public List<CustomerInfo> getAllInfos(int page){
		return customerInfoDao.queryAll(page);
	}
	/**
	 * 查询指定记录
	 * @param customer_name 查询条件
	 * @return 查询出的结果
	 */
	public List<CustomerInfo> getOne(String customer_name,int page){	
		return customerInfoDao.queryOne(customer_name,page);
	}
	/**
     * 获取满足条件的总页数
     * @return 总页数
     */
	public double getTotalPagesAfterFindOne(String customer_name) {
		return customerInfoDao.getTotalPagesAfterFindOne(customer_name);
	}
    /**
     * 获取总页数
     * @return 总页数
     */
	public double getTotalPages() {
		return customerInfoDao.getTotalPages();
	}
	/**
	 * 保存
	 * @param customerInfo 要保存的记录
	 */
	public void updateAndSave(CustomerInfo customerInfo) {
		customerInfoDao.updateAndSave(customerInfo);
	}
	
	/**
	 * 获取下一个编号
	 * @return 编号
	 */
	public String getNextVal() {
		int nextVal_int=customerInfoDao.getMax()+1;
		
		return "C"+String.format("%05d", nextVal_int);
	}
	/**
	 * 删除记录
	 * @param customerInfos 待删除的记录
	 * @return 删除的行数
	 */
	public int delete(List<CustomerInfo> customerInfos) {
		int count=0;
		for (CustomerInfo customerInfo : customerInfos) {
			count=count+customerInfoDao.delete(customerInfo); 
		}
		return count;
	}
}
