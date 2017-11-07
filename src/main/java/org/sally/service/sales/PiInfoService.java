package org.sally.service.sales;

import org.sally.dao.sales.PiInfoDao;
import org.sally.entities.Condition;
import org.sally.entities.sales.PiInfo;
import org.sally.entities.sales.QuotationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PiInfoService {
	@Autowired
	private PiInfoDao piInfoDao;
	/**
	 * 查询下游单据所需信息，并返回对象
	 */
	public List<QuotationInfo> generatePi(String quotation_no) {
		return piInfoDao.generatePi(quotation_no);
	}
	
	/**
	 * 全表查询
	 * @param page 要查的页数
	 * @return 查询出的结果
	 */
	public List<Object[]> getAllInfos(int page){
		return piInfoDao.queryAll(page);
	}
	
	 /**
     * 获取总页数
     * @return 总页数
     */
	public double getTotalPages() {
		return piInfoDao.getTotalPages();
	}
	
	/**
	 * 查询指定记录
	 * @param 查询条件的集合
	 * @return 查询出的结果
	 */
	public List<Object[]> queryOne(List<Condition> conditions,int page){
		return piInfoDao.queryOne(conditions,page);
	}
	
	/**
	 * 查询单张单据里的全部信息
	 * @param 报价单号
	 * @return 
	 */
	public List<PiInfo> queryPi(String pi_no)
	{
		return piInfoDao.queryPi(pi_no);
	}
	
	/**
	 * 删除指定的报价记录(用HQL)
	 * @return  回调方法需要的对象
	 */
	public int deleteQuotationByHQL(List<PiInfo> piInfos) {
		return piInfoDao.deletePiByHQL(piInfos);
	}
	
	/**
     * 获取满足条件的总页数
     * @return 总页数
     */
	public double getTotalPagesAfterFindOne(List<Condition> conditions) {
		return piInfoDao.getTotalPagesAfterFindOne(conditions);
	}
	/**
	 * 保存一张PI
	 * @param piInfo的list	
	 */
	public List<PiInfo> savePi(List<PiInfo> piInfos) {
		return piInfoDao.savePi(piInfos);
	}
	
	/**
     * 查询所有符合条件的内部订单
     * 
     * @param currPage 当前页码
     * @param conditions 条件集合
     * @return 内部订单集合
     */
	public List<PiInfo> find(final int currPage,final List<Condition> conditions) throws Exception
    {
		List<Object[]> objs = piInfoDao.find(currPage, conditions);
		List<PiInfo> piInfos = new ArrayList<PiInfo>();
		for(Object[] obj : objs)
		{
			PiInfo piInfo = new PiInfo();
			piInfo.setPi_no(String.valueOf(obj[0]));
			piInfo.setCustomer_no(String.valueOf(obj[1]));
			piInfo.setCustomer_name(String.valueOf(obj[2]));

			piInfos.add(piInfo);
		}
		
		return piInfos;
    }
	
	/**
	 * 查询所有符合条件的内部订单
	 *
	 * @param conditions 条件集合
	 * @return 内部订单集合
	 */
	public List<PiInfo> findAll(final List<Condition> conditions) throws Exception
	{
		List<Object[]> objs = piInfoDao.findAll(conditions);
		List<PiInfo> piInfos = new ArrayList<PiInfo>();
		for(Object[] obj : objs)
		{
			PiInfo piInfo = new PiInfo();
			piInfo.setPi_no(String.valueOf(obj[0]));
			piInfo.setCustomer_no(String.valueOf(obj[1]));
			
			piInfos.add(piInfo);
		}
		
		return piInfos;
	}
	
	/**
     * 获取查询数据的总行数
     * 
     * @param conditions 条件集合
     * @return 总行数
     */
    public long getCount(final List<Condition> conditions)
    {
    	return piInfoDao.getCount(conditions);
    }
}
