package org.sally.dao.sales;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.sally.constant.EasyTradeConstants;
import org.sally.dao.BaseDao;
import org.sally.entities.Condition;
import org.sally.entities.sales.IntOrder;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.stereotype.Repository;

/**
 * 
 * 内部订单DAO
 * 
 * @author Sally
 * @since 2017-10-31
 */
@Repository
public class IntOrderDao extends BaseDao
{
	
	/**
     * 添加内部订单
     * 
     * @param intOrders 内部订单对象集合
     * @throws Exception
     */
    public void add(List<IntOrder> intOrders) throws Exception
    {
		for(IntOrder intOrder:intOrders)
		{
			getHibernateTemplate().save(intOrder);
		}
    }
    
    /**
     * 删除内部订单
     * 
     * @param intOrders 内部订单对象集合
     * @throws Exception
     */
    public void delete(List<IntOrder> intOrders) throws Exception
    {
    		for(IntOrder intOrder:intOrders)
    		{
    			getHibernateTemplate().delete(intOrder);
    		}
    }
    
    /**
     * 查询所有符合条件的内部订单
     * 
     * @param currPage 当前页码
     * @param conditions 条件集合
     * @return 内部订单集合
     */
	public List<IntOrder> find(final int currPage,final List<Condition> conditions) throws Exception
    {
		List<IntOrder> result = getHibernateTemplate().execute(new HibernateCallback<List<IntOrder>>()
		{

			public List<IntOrder> doInHibernate(Session session) throws HibernateException
			{
				String hql = "from IntOrder t where 1=1 ";
				Query query = createConditionalQuery(session,hql, conditions, IntOrder.class);
				query.setFirstResult((currPage - 1) * EasyTradeConstants.COUNT_PER_PAGE).setMaxResults(EasyTradeConstants.COUNT_PER_PAGE);
				// 获取查询结果
				List<IntOrder> list = query.list();
				
				return list;
			}
		});
		
		return result;
    }

	/**
	 * 查询所有符合条件的内部订单
	 *
	 * @param conditions 条件集合
	 * @return 内部订单集合
	 */
	public List<IntOrder> findAll(final List<Condition> conditions) throws Exception
	{

		List<IntOrder> result= getHibernateTemplate().execute(new HibernateCallback<List<IntOrder>>()
		{

			public List<IntOrder> doInHibernate(Session session) throws HibernateException
			{
				String hql = "from IntOrder t where 1=1 ";
				Query query = createConditionalQuery(session,hql, conditions, IntOrder.class);
				// 获取查询结果
				List<IntOrder> list = query.list();
				return list;
			}
		});

		return result;
	}
    
    /**
     * 修改内部订单
     * 
     * @param intOrders 内部订单对象集合
     */
    public void update(List<IntOrder> intOrders) throws Exception
	{
		for(IntOrder intOrder:intOrders)
		{
			getHibernateTemplate().update(intOrder);
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
				String hql = "select COUNT(t.int_order_no) from IntOrder t where 1=1";
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
				return generateFlowId(session,"IOR", "int_order_no", "INT_ORDER_TAB",3,true);
			}
		});
    		
    		return result;
    }
}
