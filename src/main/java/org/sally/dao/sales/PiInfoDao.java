package org.sally.dao.sales;


import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.sally.constant.EasyTradeConstants;
import org.sally.dao.BaseDao;
import org.sally.entities.Condition;
import org.sally.entities.purchase.PurchaseProdInfo;
import org.sally.entities.sales.CustomerInfo;
import org.sally.entities.sales.PiInfo;
import org.sally.entities.sales.PiInfo;
import org.sally.entities.sales.QuotationInfo;
import org.sally.util.HibernateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.stereotype.Repository;


/*
 * 用户管理功能的Dao层
 */
@Repository
public class PiInfoDao extends BaseDao {
	@Autowired
	private HibernateUtil hibernateUtil;
	/**
	 * 查询下游单据所需信息，并返回对象
	 */
	public List<QuotationInfo> generatePi(String quotation_no) {
	Session session=hibernateUtil.getSession();	
		
		String hql_query="FROM QuotationInfo q WHERE q.quotation_no=?";
		List<QuotationInfo> quotationInfos=session.createQuery(hql_query, QuotationInfo.class).setParameter(0, quotation_no).list();
		
		close();
		return quotationInfos;
	}
	
	
	/**
	 * 全表查询,所有报价单列表(一个单号查询出一行记录)
	 */
	public List<Object[]> queryAll(int page){
		Session session=hibernateUtil.getSession();	
		
		String hql_query="SELECT DISTINCT p.pi_no, p.customer_no, p.customer_name FROM PiInfo p";
		
		List<Object[]> list=session.createQuery(hql_query,Object[].class).
			setFirstResult((page-1)*EasyTradeConstants.COUNT_PER_PAGE).setMaxResults(EasyTradeConstants.COUNT_PER_PAGE).
		    list();
		close();
		return list;
	}
	
	/**
	 * 查询总页数
	 * @return
	 */
	public double getTotalPages() {
		Session session=hibernateUtil.getSession();	
		
		String hql_query="SELECT DISTINCT p.pi_no from PiInfo p";
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
	 * 指定记录查询
	 * @param 待查询记录的id
	 */
	public List<Object[]> queryOne(List<Condition> conditions,int page) {
		Session session=hibernateUtil.getSession();
			
		String hql_query="SELECT DISTINCT pi_no, customer_no, customer_name FROM PiInfo WHERE 1=1";
		//将原始查询条件代入工具方法,拼接查询条件
		hql_query=hibernateUtil.conditionalHQL(hql_query, conditions, "pi_date");
		//查询
		List<Object[]> piInfos=session.createQuery(hql_query,Object[].class)
				.setFirstResult((page-1)*EasyTradeConstants.COUNT_PER_PAGE)
				.setMaxResults(EasyTradeConstants.COUNT_PER_PAGE).list();
		
		close();
		return piInfos;
	}
	
	/**
	 * 查询单张单据里的全部信息
	 * @param 报价单号
	 * @return 单张单据里的全部信息
	 */
	public List<PiInfo> queryPi(String pi_no)
	{
			Session session=hibernateUtil.getSession();	
			
			String hql_query="FROM PiInfo p WHERE p.pi_no=?";
			List<PiInfo> list=session.createQuery(hql_query, PiInfo.class).setParameter(0, pi_no).list();
			
			close();
			return list;
	}
	
	/**
	 * 删除指定的报价记录(用HQL)
	 * @return  回调方法需要的对象
	 */
	public int deletePiByHQL(List<PiInfo> piInfos) {
		Session session=hibernateUtil.getSession();	
		Transaction transaction=session.beginTransaction();
		
		String hql_delete="DELETE FROM PiInfo p WHERE p.pi_no=?";
		int result=0;
		for (PiInfo piInfo : piInfos)
		{
			session.createQuery(hql_delete).setParameter(0, piInfo.getPi_no()).executeUpdate();
		}
		
		transaction.commit();//提交
		close();
		return result;
	}
	
	/**
	 * 查询后符合条件的总页数
	 * @return
	 */
	public double getTotalPagesAfterFindOne(List<Condition> conditions) {
		Session session=hibernateUtil.getSession();	
		
		String hql_query="SELECT DISTINCT pi_no, customer_no, customer_name FROM PiInfo WHERE 1=1";
		//将原始查询条件代入工具方法,拼接查询条件
		hql_query=hibernateUtil.conditionalHQL(hql_query, conditions, "pi_date");
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
	 * 保存一张PI
	 * @param piInfo的list	
	 */
	public List<PiInfo> savePi(List<PiInfo> piInfos) {
		Session session=hibernateUtil.getSession();	
		Transaction transaction=session.beginTransaction();
		
		//将每行报价存入数据库
		for (PiInfo piInfo : piInfos)
		{
			session.saveOrUpdate(piInfo);
		}
		
		transaction.commit();//提交
		close();
		return piInfos;
	}
	
	
	/**
     * 查询所有符合条件的内部订单
     * 
     * @param currPage 当前页码
     * @param conditions 条件集合
     * @return 内部订单集合
     */
	public List<Object[]> find(final int currPage,final List<Condition> conditions) throws Exception
    {
		
		List<Object[]> result = queryOne(conditions,currPage);
		
		return result;
    }

	/**
	 * 查询所有符合条件的内部订单
	 *
	 * @param conditions 条件集合
	 * @return 内部订单集合
	 */
	public List<Object[]> findAll(final List<Condition> conditions) throws Exception
	{
		Session session=hibernateUtil.getSession();
		
		String hql_query="SELECT DISTINCT pi_no, customer_no, customer_name FROM PiInfo WHERE 1=1";
		//将原始查询条件代入工具方法,拼接查询条件
		hql_query=hibernateUtil.conditionalHQL(hql_query, conditions, "pi_date");
		//查询
		List<Object[]> result=session.createQuery(hql_query,Object[].class).list();

		return result;
	}
	
	/**
     * 获取查询数据的总行数
     * 
     * @param conditions 条件集合
     * @return 总行数
     */
    public long getCount(final List<Condition> conditions)
    {
    	Session session=hibernateUtil.getSession();	
		
		String hql_query="SELECT DISTINCT pi_no, customer_no, customer_name FROM PiInfo WHERE 1=1";
		//将原始查询条件代入工具方法,拼接查询条件
		hql_query=hibernateUtil.conditionalHQL(hql_query, conditions, "pi_date");
		//查询
		List<Object[]> quotationInfos=session.createQuery(hql_query,Object[].class).list();
		
		//总记录数
		long total= quotationInfos.size();
    		
    	return total;
    }
	
	private void close() {
		hibernateUtil.close();
	}
}

