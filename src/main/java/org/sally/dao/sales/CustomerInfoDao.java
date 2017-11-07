package org.sally.dao.sales;


import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.sally.constant.EasyTradeConstants;
import org.sally.entities.sales.CustomerInfo;
import org.sally.util.HibernateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


/*
 * 用户管理功能的Dao层
 */
@Repository
public class CustomerInfoDao {
	@Autowired
	private HibernateUtil hibernateUtil;
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
	 * 指定记录查询
	 * @param 待查询记录的id
	 */
	public List<CustomerInfo> queryOne(String customer_name,int page) {
		Session session=hibernateUtil.getSession();
		
		String hql_query="FROM CustomerInfo WHERE customer_name like ?";
		List<CustomerInfo> customerInfos=session.createQuery(hql_query,CustomerInfo.class).setParameter(0, "%"+customer_name+"%")
				.setFirstResult((page-1)*EasyTradeConstants.COUNT_PER_PAGE)
				.setMaxResults(EasyTradeConstants.COUNT_PER_PAGE).list();
		
		close();
		return customerInfos;
	}
	/**
	 * 查询符合条件的总页数
	 * @return
	 */
	public double getTotalPagesAfterFindOne(String customer_name) {
		Session session=hibernateUtil.getSession();	
		
		String hql_query="SELECT COUNT(c.customer_no) from CustomerInfo c WHERE c.customer_name like ?";
		//总记录数
		long total= session.createQuery(hql_query,Long.class).setParameter(0, "%"+customer_name+"%").uniqueResult();
		
		//总页数
		double totalPages=Math.ceil((double)total/(double)EasyTradeConstants.COUNT_PER_PAGE);
		
		close();
		return totalPages;
	}
	/**
	 * 查询总页数
	 * @return
	 */
	public double getTotalPages() {
		Session session=hibernateUtil.getSession();	
		
		String hql_query="SELECT COUNT(c.customer_no) from CustomerInfo c";
		//总记录数
		long total= session.createQuery(hql_query,Long.class).uniqueResult();
		
		//总页数
		double totalPages=Math.ceil((double)total/(double)EasyTradeConstants.COUNT_PER_PAGE);
		
		close();
		return totalPages;
	}
	/**
	 * 全表查询
	 */
	public List<CustomerInfo> queryAll(int page){
		Session session=hibernateUtil.getSession();	
		
		String hql_query="from CustomerInfo";
		
		List<CustomerInfo> list=session.createQuery(hql_query,CustomerInfo.class)
				.setFirstResult((page-1)*EasyTradeConstants.COUNT_PER_PAGE)
				.setMaxResults(EasyTradeConstants.COUNT_PER_PAGE).list();
		
		close();
		return list;
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

