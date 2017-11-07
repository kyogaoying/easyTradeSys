package org.sally.service.purchase;

import java.util.List;

import org.sally.dao.purchase.PurchaseProdInfoDao;
import org.sally.entities.purchase.PurchaseProdInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseProdInfoService {
	@Autowired
	private  PurchaseProdInfoDao purchaseProdInfoDao;
	/**
	 * 全表查询
	 * @param page 要查的页数
	 * @return 查询出的结果
	 */
	public List<PurchaseProdInfo> getAllInfos(int page){
		return purchaseProdInfoDao.queryAll(page);
	}
	/**
	 * 查询指定记录
	 * @param vendor_name 查询条件
	 * @return 查询出的结果
	 */
	public List<PurchaseProdInfo> getOne(String vendor_name,int page){	
		return purchaseProdInfoDao.queryOne(vendor_name,page);
	}
	/**
     * 获取满足条件的总页数
     * @return 总页数
     */
	public double getTotalPagesAfterFindOne(String vendor_name) {
		return purchaseProdInfoDao.getTotalPagesAfterFindOne(vendor_name);
	}
    /**
     * 获取总页数
     * @return 总页数
     */
	public double getTotalPages() {
		return purchaseProdInfoDao.getTotalPages();
	}
	/**
	 * 保存
	 * @param purchaseProdInfo 要保存的记录
	 */
	public void updateAndSave(PurchaseProdInfo purchaseProdInfo) {
		purchaseProdInfoDao.updateAndSave(purchaseProdInfo);
	}
	
	/**
	 * 获取下一个编号
	 * @return 编号
	 */
	public String getNextVal() {
		int nextVal_int=purchaseProdInfoDao.getMax()+1;
		
		return "P"+String.format("%06d", nextVal_int);
	}
	/**
	 * 删除记录
	 * @param purchaseProdInfos 待删除的记录
	 * @return 删除的行数
	 */
	public int delete(List<PurchaseProdInfo> purchaseProdInfos) {
		int count=0;
		for (PurchaseProdInfo purchaseProdInfo : purchaseProdInfos) {
			count=count+purchaseProdInfoDao.delete(purchaseProdInfo); 
		}
		return count;
	}
	/**
	 * 验证关联编号是否存在
	 * @return 存在返回true,不存在返回false
	 */
	public boolean verifyAddandEdit(String vendor_no) {
		return purchaseProdInfoDao.verifyAddandEdit(vendor_no);
	}
}
