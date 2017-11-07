package org.sally.dao.purchase;


import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.sally.constant.EasyTradeConstants;
import org.sally.entities.purchase.PurchaseProdInfo;
import org.sally.entities.purchase.PurchaseVendorInfo;
import org.sally.util.HibernateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


/*
 * 用户管理功能的Dao层
 */
@Repository
public class PurchaseProdInfoDao {
	@Autowired
	private HibernateUtil hibernateUtil;
	/**
	 * 增加一行record
	 * @param purchaseProdInfo 待增加的purchaseProdInfo对象
	 */
	public void savePurchaseProd(PurchaseProdInfo purchaseProdInfo) {
		Transaction transaction=hibernateUtil.getSession().beginTransaction();
		
		
		hibernateUtil.getSession().save(purchaseProdInfo);
		
		transaction.commit();
		close();
	}
	/**
	 * 删除一行record
	 * @param prod_no 待删除record的prod_no
	 * @return 删除的行数
	 */
	public int delete(PurchaseProdInfo purchaseProdInfo) {
		Transaction transaction=hibernateUtil.getSession().beginTransaction();
		Session session=hibernateUtil.getSession();
		
		String hql_delete="DELETE from PurchaseProdInfo p WHERE p.prod_no=?";
		int rows=session.createQuery(hql_delete).setParameter(0, purchaseProdInfo.getProd_no()).executeUpdate();
		
		transaction.commit();
		close();
		return rows;
	}
	/**
	 * 修改一行record
	 * @param purchaseProdInfo 已修改的purchaseProdInfo对象
	 */	
	public void updateAndSave(PurchaseProdInfo purchaseProdInfo) {
		Transaction transaction=hibernateUtil.getSession().beginTransaction();
		Session session=hibernateUtil.getSession();

		session.saveOrUpdate(purchaseProdInfo);
		transaction.commit();
		close();
	}
	/**
	 * 指定记录查询
	 * @param 待查询记录的id
	 */
	public List<PurchaseProdInfo> queryOne(String prod_no,int page) {
		Session session=hibernateUtil.getSession();
		
		String hql_query="FROM PurchaseProdInfo WHERE prod_no like ?";
		List<PurchaseProdInfo> purchaseProdInfos=session.createQuery(hql_query,PurchaseProdInfo.class).setParameter(0, "%"+prod_no+"%")
				.setFirstResult((page-1)*EasyTradeConstants.COUNT_PER_PAGE)
				.setMaxResults(EasyTradeConstants.COUNT_PER_PAGE).list();
		
		close();
		return purchaseProdInfos;
	}
	/**
	 * 查询符合条件的总页数
	 * @return
	 */
	public double getTotalPagesAfterFindOne(String prod_no) {
		Session session=hibernateUtil.getSession();	
		
		String hql_query="SELECT COUNT(p.prod_no) from PurchaseProdInfo p WHERE p.prod_no like ?";
		//总记录数
		long total= session.createQuery(hql_query,Long.class).setParameter(0, "%"+prod_no+"%").uniqueResult();
		
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
		
		String hql_query="SELECT COUNT(p.prod_no) from PurchaseProdInfo p";
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
	public List<PurchaseProdInfo> queryAll(int page){
		Session session=hibernateUtil.getSession();	
		
		String hql_query="from PurchaseProdInfo";
		
		List<PurchaseProdInfo> list=session.createQuery(hql_query,PurchaseProdInfo.class)
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
		
		//查询出PurchaseProdInfo表的最大prod_no
		String hql_query="select max(p.prod_no) from PurchaseProdInfo p";
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
	 * 验证关联编号是否存在
	 * @return 是否有符合条件的记录,true符合,false不符合
	 */
	public boolean verifyAddandEdit(String vendor_no) {
		Session session=hibernateUtil.getSession();	
		
		
		PurchaseVendorInfo purchaseVendorInfo = session.get(PurchaseVendorInfo.class, vendor_no);	
		close();
		
		return (purchaseVendorInfo!=null)?true:false; 
	}
	/**
	 * 关闭session
	 */
	private void close() {
		hibernateUtil.close();
	}
}

