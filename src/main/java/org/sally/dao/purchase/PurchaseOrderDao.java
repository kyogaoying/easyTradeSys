package org.sally.dao.purchase;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.sally.constant.EasyTradeConstants;
import org.sally.dao.BaseDao;
import org.sally.entities.Condition;
import org.sally.entities.purchase.PurchaseOrder;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.stereotype.Repository;

/**
 * 
 * 采购订单DAO
 * 
 * @author Sally
 * @since 2017-10-31
 */
@Repository
public class PurchaseOrderDao extends BaseDao
{
	
	/**
     * 添加采购订单
     * 
     * @param intOrders 采购订单对象集合
     * @throws Exception
     */
    public void add(List<PurchaseOrder> intOrders) throws Exception
    {
		for(PurchaseOrder purchaseOrder:intOrders)
		{
			getHibernateTemplate().save(purchaseOrder);
		}
    }
    
    /**
     * 删除采购订单
     * 
     * @param intOrders 采购订单对象集合
     * @throws Exception
     */
    public void delete(List<PurchaseOrder> intOrders) throws Exception
    {
    		for(PurchaseOrder purchaseOrder:intOrders)
    		{
    			getHibernateTemplate().delete(purchaseOrder);
    		}
    }
    
    /**
     * 查询所有符合条件的采购订单
     * 
     * @param currPage 当前页码
     * @param conditions 条件集合
     * @return 采购订单集合
     */
	public List<PurchaseOrder> find(final int currPage,final List<Condition> conditions) throws Exception
    {
		List<PurchaseOrder> result = getHibernateTemplate().execute(new HibernateCallback<List<PurchaseOrder>>()
		{

			public List<PurchaseOrder> doInHibernate(Session session) throws HibernateException
			{
				String hql = "from PurchaseOrder t where 1=1 ";
				Query query = createConditionalQuery(session,hql, conditions, PurchaseOrder.class);
				query.setFirstResult((currPage - 1) * EasyTradeConstants.COUNT_PER_PAGE).setMaxResults(EasyTradeConstants.COUNT_PER_PAGE);
				// 获取查询结果
				List<PurchaseOrder> list = query.list();
				
				return list;
			}
		});
		
		return result;
    }

	/**
	 * 查询所有符合条件的采购订单
	 *
	 * @param conditions 条件集合
	 * @return 采购订单集合
	 */
	public List<PurchaseOrder> findAll(final List<Condition> conditions) throws Exception
	{

		List<PurchaseOrder> result= getHibernateTemplate().execute(new HibernateCallback<List<PurchaseOrder>>()
		{

			public List<PurchaseOrder> doInHibernate(Session session) throws HibernateException
			{
				String hql = "from PurchaseOrder t where 1=1 ";
				Query query = createConditionalQuery(session,hql, conditions, PurchaseOrder.class);
				// 获取查询结果
				List<PurchaseOrder> list = query.list();
				return list;
			}
		});

		return result;
	}
    
    /**
     * 修改采购订单
     * 
     * @param intOrders 采购订单对象集合
     */
    public void update(List<PurchaseOrder> intOrders) throws Exception
	{
		for(PurchaseOrder purchaseOrder:intOrders)
		{
			getHibernateTemplate().update(purchaseOrder);
		}
	}
    
    /**
     * 获取查询数据的总行数
     * 
     * @param conditions 条件集合
     * @return 总行数
     */
    public long getCount(final List<Condition> conditions)
    {
    		Long result = getHibernateTemplate().execute(new HibernateCallback<Long>()
		{

			public Long doInHibernate(Session session) throws HibernateException
			{
				String hql = "select COUNT(t.order_no) from PurchaseOrder t where 1=1";
				Query query = createConditionalQuery(session,hql, conditions, Long.class);
				Long result = (Long) query.uniqueResult();
				return result == null ? 0 : result;
			}
		});
    		
    		return result;
    }
	
    /**
     * 获取生成的ID
     * @return 生成的ID
     */
    public String getId()
    {
    		String result = getHibernateTemplate().execute(new HibernateCallback<String>()
		{

			public String doInHibernate(Session session) throws HibernateException
			{
				// TODO Auto-generated method stub
				return generateFlowId(session,"P", "order_no", "PURCHASE_ORDER_TAB",3,true);
			}
		});
    		
    		return result;
    }
}
