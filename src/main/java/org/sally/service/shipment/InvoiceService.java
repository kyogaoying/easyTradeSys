package org.sally.service.shipment;

import org.sally.dao.shipment.InvoiceDao;
import org.sally.entities.Condition;
import org.sally.entities.sales.PiInfo;
import org.sally.entities.shipment.Invoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class InvoiceService {
	@Autowired
	private InvoiceDao invoiceDao;

	/**
	 * 查询是否还是锁定状态
	 * @return 是否还是锁定状态
	 */
	public String checkIfLocked(String pi_no){
		return invoiceDao.checkIfLocked(pi_no);
	}

	/**
	 * 查询下游单据所需信息，并返回对象
	 */
	public List<Invoice> generateInvoice(String pi_no) {
		List<PiInfo> piInfos=invoiceDao.getPiInfo(pi_no);
		List<Object[]> qtys=invoiceDao.getQtyInfo(pi_no);
		String id=invoiceDao.getID();

		List<Invoice> invoices=new ArrayList<Invoice>();
		//查询结果包装为invoice对象,放入Invoice数组
		for (PiInfo piInfo:piInfos ) {
			Invoice invoice=new Invoice();

			invoice.setInvoice_no(id);
			invoice.setInvoice_date(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			invoice.setProd_no(piInfo.getProd_no());
			invoice.setSub_total_price(piInfo.getSub_total_price());
			invoice.setCustomer_no(piInfo.getCustomer_no());
			invoice.setPi_no(piInfo.getPi_no());
			for (Object[] objects:qtys) {
				if (piInfo.getProd_no().equals(String.valueOf(objects[0]))){
					invoice.setQty((Integer)objects[1]);
					break;
				}
			}
			invoices.add(invoice);
		}
		//保存至数据库
		invoiceDao.save(invoices);
		return invoices;
	}

	/**
	 * 再查一次
	 * @param invoice_no
	 * @return invoice条目
	 */
	public List<Invoice> refresh(String invoice_no){
		return invoiceDao.refresh(invoice_no);
	}

	/**
	 * 查询所有符合条件的数据
	 *
	 * @param currPage 当前页码
	 * @param conditions 条件集合
	 * @return 集合
	 */
	public List<Invoice> find(final int currPage,final List<Condition> conditions) throws Exception
	{
		List<Object[]> objs = invoiceDao.find(currPage, conditions);
		List<Invoice> invoices = new ArrayList<Invoice>();
		for(Object[] obj : objs)
		{
			Invoice invoice = new Invoice();
			invoice.setInvoice_no(String.valueOf(obj[0]));
			invoice.setCustomer_name(String.valueOf(obj[1]));
			invoice.setInvoice_date(String.valueOf(obj[2]));

			invoices.add(invoice);
		}

		return invoices;
	}

	/**
	 * 查询所有符合条件的数据
	 *
	 * @param currPage 当前页码
	 * @param conditions 条件集合
	 * @return 集合
	 */
	public List<Invoice> findAll(final List<Condition> conditions) throws Exception
	{
		List<Object[]> objs = invoiceDao.findAll(conditions);
		List<Invoice> invoices = new ArrayList<Invoice>();
		for(Object[] obj : objs)
		{
			Invoice invoice = new Invoice();
			invoice.setInvoice_no(String.valueOf(obj[0]));
			invoice.setCustomer_name(String.valueOf(obj[1]));
			invoice.setInvoice_date(String.valueOf(obj[2]));

			invoices.add(invoice);
		}

		return invoices;
	}

	/**
	 * 获取查询数据的总行数
	 *
	 * @param conditions 条件集合
	 * @return 总行数
	 */
	public long getCount(final List<Condition> conditions)
	{
		return invoiceDao.getCount(conditions);
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
