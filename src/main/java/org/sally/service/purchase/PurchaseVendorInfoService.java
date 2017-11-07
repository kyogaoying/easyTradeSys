package org.sally.service.purchase;

import java.util.List;


import org.sally.dao.purchase.PurchaseVendorInfoDao;

import org.sally.entities.purchase.PurchaseVendorInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseVendorInfoService {
	@Autowired
	private  PurchaseVendorInfoDao purchaseVendorInfoDao;
	/**
	 * 全表查询
	 * @param page 要查的页数
	 * @return 查询出的结果
	 */
	public List<PurchaseVendorInfo> getAllInfos(int page){
		return purchaseVendorInfoDao.queryAll(page);
	}
	/**
	 * 查询指定记录
	 * @param vendor_name 查询条件
	 * @return 查询出的结果
	 */
	public List<PurchaseVendorInfo> getOne(String vendor_name,int page){	
		return purchaseVendorInfoDao.queryOne(vendor_name,page);
	}
	/**
     * 获取满足条件的总页数
     * @return 总页数
     */
	public double getTotalPagesAfterFindOne(String vendor_name) {
		return purchaseVendorInfoDao.getTotalPagesAfterFindOne(vendor_name);
	}
    /**
     * 获取总页数
     * @return 总页数
     */
	public double getTotalPages() {
		return purchaseVendorInfoDao.getTotalPages();
	}
	/**
	 * 保存
	 * @param purchaseVendorInfo 要保存的记录
	 */
	public void updateAndSave(PurchaseVendorInfo purchaseVendorInfo) {
		purchaseVendorInfoDao.updateAndSave(purchaseVendorInfo);
	}
	
	/**
	 * 获取下一个编号
	 * @return 编号
	 */
	public String getNextVal() {
		int nextVal_int=purchaseVendorInfoDao.getMax()+1;
		
		return "V"+String.format("%05d", nextVal_int);
	}
	/**
	 * 删除记录
	 * @param purchaseVendorInfos 待删除的记录
	 * @return 删除的行数
	 */
	public int delete(List<PurchaseVendorInfo> purchaseVendorInfos) {
		int count=0;
		for (PurchaseVendorInfo purchaseVendorInfo : purchaseVendorInfos) {
			count=count+purchaseVendorInfoDao.delete(purchaseVendorInfo); 
		}
		return count;
	}
}
