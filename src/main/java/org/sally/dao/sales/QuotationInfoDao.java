package org.sally.dao.sales;


import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.sally.constant.EasyTradeConstants;
import org.sally.entities.Condition;
import org.sally.entities.purchase.PurchaseProdInfo;
import org.sally.entities.sales.CustomerInfo;
import org.sally.entities.sales.QuotationInfo;
import org.sally.util.HibernateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


/*
 * 用户管理功能的Dao层
 */
@Repository
public class QuotationInfoDao {
	@Autowired
	private HibernateUtil hibernateUtil;
	/**
	 * 全表查询,所有报价单列表(一个单号查询出一行记录)
	 */
	public List<Object[]> queryAll(int page){
		Session session=hibernateUtil.getSession();	
		
		String hql_query="SELECT DISTINCT q.quotation_no, q.customer_no, q.customer_name FROM QuotationInfo q";
		
		List<Object[]> list=session.createQuery(hql_query,Object[].class).
			setFirstResult((page-1)*EasyTradeConstants.COUNT_PER_PAGE).setMaxResults(EasyTradeConstants.COUNT_PER_PAGE).
		    list();
		close();
		return list;
	}
	/**
	 * 查询单张单据里的全部信息
	 * @param 报价单号
	 * @return 单张单据里的全部信息
	 */
	public List<QuotationInfo> queryQuotation(String quotation_no)
	{
			Session session=hibernateUtil.getSession();	
			
			String hql_query="FROM QuotationInfo q WHERE q.quotation_no=?";
			List<QuotationInfo> list=session.createQuery(hql_query, QuotationInfo.class).setParameter(0, quotation_no).list();
			
			close();
			return list;
	}
	/**
	 * 根据customer_no查询客户信息
	 * @param customer_no
	 * @return CustomerInfo对象
	 */
	public CustomerInfo findCustomerNo(String customer_no) {
		Session session=hibernateUtil.getSession();	
		
		CustomerInfo customerInfo=session.get(CustomerInfo.class, customer_no);
		if (customerInfo==null) {
			customerInfo=new CustomerInfo();
		}
		
		close();
		return customerInfo;
	}
	/**
	 * 根据prod_no查询产品信息
	 * @param prod_no
	 * @return PurchaseProdInfo对象
	 */
	public PurchaseProdInfo findProdNo(String prod_no) {
		Session session=hibernateUtil.getSession();	
		
		PurchaseProdInfo purchaseProdInfo=session.get(PurchaseProdInfo.class, prod_no);
		if (purchaseProdInfo==null) {
			purchaseProdInfo=new PurchaseProdInfo();
		}
		close();
		return purchaseProdInfo;
	}
	/**
	 * 保存一张报价单
	 * @param quotationInfo的list	
	 */
	public List<QuotationInfo> saveQuotation(List<QuotationInfo> quotationInfos) {
		Session session=hibernateUtil.getSession();	
		Transaction transaction=session.beginTransaction();
		
		//将每行报价存入数据库
		for (QuotationInfo quotationInfo : quotationInfos)
		{
			session.saveOrUpdate(quotationInfo);
		}
		
		transaction.commit();//提交
		close();
		return quotationInfos;
	}
	/**
	 * 删除指定的报价记录(session.delete())
	 * @return  回调方法需要的对象
	 */
	public int deleteQuotation(List<QuotationInfo> quotationInfos){
		Session session=hibernateUtil.getSession();	
		Transaction transaction=session.beginTransaction();
		
		int result=0;
		//循环删除
		for (QuotationInfo quotationInfo : quotationInfos) {
			session.delete(quotationInfo);
			result++;
		}
		
		transaction.commit();//提交
		close();
		return result;
	}
	/**
	 * 删除指定的报价记录(用HQL)
	 * @return  回调方法需要的对象
	 */
	public int deleteQuotationByHQL(List<QuotationInfo> quotationInfos) {
		Session session=hibernateUtil.getSession();	
		Transaction transaction=session.beginTransaction();
		
		String hql_delete="DELETE FROM QuotationInfo q WHERE q.quotation_no=?";
		int result=0;
		for (QuotationInfo quotationInfo : quotationInfos)
		{
			session.createQuery(hql_delete).setParameter(0, quotationInfo.getQuotation_no()).executeUpdate();
		}
		
		transaction.commit();//提交
		close();
		return result;
	}
	/**
	 * 指定记录查询
	 * @param 待查询记录的id
	 */
	public List<Object[]> queryOne(List<Condition> conditions,int page) {
		Session session=hibernateUtil.getSession();
			
		String hql_query="SELECT DISTINCT quotation_no, customer_no, customer_name FROM QuotationInfo WHERE 1=1";
		//将原始查询条件代入工具方法,拼接查询条件
		hql_query=hibernateUtil.conditionalHQL(hql_query, conditions, "quotation_date");
		//查询
		List<Object[]> quotationInfos=session.createQuery(hql_query,Object[].class)
				.setFirstResult((page-1)*EasyTradeConstants.COUNT_PER_PAGE)
				.setMaxResults(EasyTradeConstants.COUNT_PER_PAGE).list();
		
		close();
		return quotationInfos;
	}
	/**
	 * 查询总页数
	 * @return
	 */
	public double getTotalPages() {
		Session session=hibernateUtil.getSession();	
		
		String hql_query="SELECT DISTINCT q.quotation_no from QuotationInfo q";
		//总记录数
		List<String> list= session.createQuery(hql_query,String.class).list();		
		double totalPages=0;
		//总页数
		if (list.size()==0)
		{
			totalPages=1;
		}
		else {
			totalPages=Math.ceil((double)list.size()/(double)EasyTradeConstants.COUNT_PER_PAGE);
		}
		close();
		return totalPages;
	}
	
	/**
	 * 查询后符合条件的总页数
	 * @return
	 */
	public double getTotalPagesAfterFindOne(List<Condition> conditions) {
		Session session=hibernateUtil.getSession();	
		
		String hql_query="SELECT DISTINCT quotation_no, customer_no, customer_name FROM QuotationInfo WHERE 1=1";
		//将原始查询条件代入工具方法,拼接查询条件
		hql_query=hibernateUtil.conditionalHQL(hql_query, conditions, "quotation_date");
		//查询
		List<Object[]> quotationInfos=session.createQuery(hql_query,Object[].class).list();
		
		//总记录数
		long total= quotationInfos.size();
		
		//总页数
		double totalPages=Math.ceil((double)total/(double)EasyTradeConstants.COUNT_PER_PAGE);
		
		close();
		return totalPages;
	}
	
	
	
	
	/**
	 * 增加一行record
	 * @param customerInfo 待增加的customerInfo对象
	 */
	public void saveCustomer(CustomerInfo customerInfo) {
		Transaction transaction=hibernateUtil.getSession().beginTransaction();
		
		hibernateUtil.getSession().save(customerInfo);
		
		transaction.commit();
		close();
	}
	/**
	 * 删除一行record
	 * @param user_no 待删除record的user_no
	 * @return 删除的行数
	 */
	public int delete(CustomerInfo customerInfo) {
		Transaction transaction=hibernateUtil.getSession().beginTransaction();
		Session session=hibernateUtil.getSession();
		
		String hql_delete="DELETE from CustomerInfo c WHERE c.customer_no=?";
		int rows=session.createQuery(hql_delete).setParameter(0, customerInfo.getCustomer_no()).executeUpdate();
		
		transaction.commit();
		close();
		return rows;
	}
	/**
	 * 修改一行record
	 * @param customerInfo 已修改的customerInfo对象
	 */	
	public void updateAndSave(CustomerInfo customerInfo) {
		Transaction transaction=hibernateUtil.getSession().beginTransaction();
		Session session=hibernateUtil.getSession();

		session.saveOrUpdate(customerInfo);
		transaction.commit();
		close();
	}
	/**
	 * 获取编号最大值
	 */
	public int getMax() {
		Session session=hibernateUtil.getSession();	
		
		//查询出CustomerInfo表的最大user_no
		String hql_query="select max(c.customer_no) from CustomerInfo c";
		String maxNo=session.createQuery(hql_query,String.class).uniqueResult();
		//如果待查的表里没有任何数据，则maxno设为“00000”,这样是为了防止带入null参数到下面的parseInt()方法出现转换异常
		if (maxNo==null)
		{
			maxNo="00000";
		}
		int maxNo_int=Integer.parseInt(maxNo.substring(1));
		
		close();
		return maxNo_int;
	}
	/**
	 * 关闭session
	 */
	private void close() {
		hibernateUtil.close();
	}
}

