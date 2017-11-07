package org.sally.service.shipment;

import org.sally.dao.shipment.PackingListDao;
import org.sally.entities.Condition;
import org.sally.entities.sales.PiInfo;
import org.sally.entities.shipment.PackingList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PackingListService {
	@Autowired
	private PackingListDao packingListDao;

	/**
	 * 查询是否还是锁定状态
	 * @return 是否还是锁定状态
	 */
	public String checkIfLocked(String pi_no){
		return packingListDao.checkIfLocked(pi_no);
	}

	/**
	 * 查询下游单据所需信息，并返回对象
	 */
	public List<PackingList> generatePackingList(String pi_no) {
		List<PiInfo> piInfos=packingListDao.getPiInfo(pi_no);
		List<Object[]> qtys=packingListDao.getQtyInfo(pi_no);
		String id=packingListDao.getID();

		List<PackingList> packingLists=new ArrayList<PackingList>();
		//查询结果包装为packingList对象,放入PackingList数组
		for (PiInfo piInfo:piInfos ) {
			PackingList packingList=new PackingList();

			packingList.setPacking_list_no(id);
			packingList.setPacking_list_date(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			packingList.setProd_no(piInfo.getProd_no());
			packingList.setGross_weight(11.3);
			packingList.setVolumn(2.5);
			packingList.setCustomer_no(piInfo.getCustomer_no());
			packingList.setPi_no(piInfo.getPi_no());
			for (Object[] objects:qtys) {
				if (piInfo.getProd_no().equals(String.valueOf(objects[0]))){
					packingList.setQty((Integer)objects[1]);
					break;
				}
			}
			packingLists.add(packingList);
		}
		//保存至数据库
		packingListDao.save(packingLists);
		return packingLists;
	}

	/**
	 * 再查一次
	 * @param packingList_no
	 * @return packingList条目
	 */
	public List<PackingList> refresh(String packingList_no){
		return packingListDao.refresh(packingList_no);
	}

	/**
	 * 查询所有符合条件的数据
	 *
	 * @param currPage 当前页码
	 * @param conditions 条件集合
	 * @return 集合
	 */
	public List<PackingList> find(final int currPage,final List<Condition> conditions) throws Exception
	{
		List<Object[]> objs = packingListDao.find(currPage, conditions);
		List<PackingList> packingLists = new ArrayList<PackingList>();
		for(Object[] obj : objs)
		{
			PackingList packingList = new PackingList();
			packingList.setPacking_list_no(String.valueOf(obj[0]));
			packingList.setCustomer_name(String.valueOf(obj[1]));
			packingList.setPacking_list_date(String.valueOf(obj[2]));

			packingLists.add(packingList);
		}

		return packingLists;
	}

	/**
	 * 查询所有符合条件的数据
	 *
	 * @param currPage 当前页码
	 * @param conditions 条件集合
	 * @return 集合
	 */
	public List<PackingList> findAll(final List<Condition> conditions) throws Exception
	{
		List<Object[]> objs = packingListDao.findAll(conditions);
		List<PackingList> packingLists = new ArrayList<PackingList>();
		for(Object[] obj : objs)
		{
			PackingList packingList = new PackingList();
			packingList.setPacking_list_no(String.valueOf(obj[0]));
			packingList.setCustomer_name(String.valueOf(obj[1]));
			packingList.setPacking_list_date(String.valueOf(obj[2]));

			packingLists.add(packingList);
		}

		return packingLists;
	}

	/**
	 * 获取查询数据的总行数
	 *
	 * @param conditions 条件集合
	 * @return 总行数
	 */
	public long getCount(final List<Condition> conditions)
	{
		return packingListDao.getCount(conditions);
	}

	/*
	*//**
	 * 全表查询
	 * @param page 要查的页数
	 * @return 查询出的结果
	 *//*
	public List<Object[]> getAllInfos(int page){
		return piInfoDao.queryAll(page);
	}
	
	 *//**
     * 获取总页数
     * @return 总页数
     *//*
	public double getTotalPages() {
		return piInfoDao.getTotalPages();
	}
	
	*//**
	 * 查询指定记录
	 * @param 查询条件的集合
	 * @return 查询出的结果
	 *//*
	public List<Object[]> queryOne(List<Condition> conditions,int page){
		return piInfoDao.queryOne(conditions,page);
	}
	
	*//**
	 * 查询单张单据里的全部信息
	 * @param 报价单号
	 * @return 
	 *//*
	public List<PiInfo> queryPi(String pi_no)
	{
		return piInfoDao.queryPi(pi_no);
	}
	
	*//**
	 * 删除指定的报价记录(用HQL)
	 * @return  回调方法需要的对象
	 *//*
	public int deleteQuotationByHQL(List<PiInfo> piInfos) {
		return piInfoDao.deletePiByHQL(piInfos);
	}
	
	*//**
     * 获取满足条件的总页数
     * @return 总页数
     *//*
	public double getTotalPagesAfterFindOne(List<Condition> conditions) {
		return piInfoDao.getTotalPagesAfterFindOne(conditions);
	}
	*//**
	 * 保存一张PI
	 * @param piInfo的list	
	 *//*
	public List<PiInfo> savePi(List<PiInfo> piInfos) {
		return piInfoDao.savePi(piInfos);
	}
	
	*//**
     * 查询所有符合条件的内部订单
     * 
     * @param currPage 当前页码
     * @param conditions 条件集合
     * @return 内部订单集合
     *//*
	public List<PiInfo> find(final int currPage,final List<Condition> conditions) throws Exception
    {
		List<Object[]> objs = piInfoDao.find(currPage, conditions);
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
	
	*//**
	 * 查询所有符合条件的内部订单
	 *
	 * @param conditions 条件集合
	 * @return 内部订单集合
	 *//*
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
	
	*//**
     * 获取查询数据的总行数
     * 
     * @param conditions 条件集合
     * @return 总行数
     *//*
    public long getCount(final List<Condition> conditions)
    {
    	return piInfoDao.getCount(conditions);
    }*/
}
