package org.sally.dao.inventory;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.sally.constant.EasyTradeConstants;
import org.sally.dao.BaseDao;
import org.sally.entities.Condition;
import org.sally.entities.inventory.InventoryIn;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 入库单数据操作对象
 * 
 * @author Sally
 * @since 2017-10-20
 */
@Repository
@SuppressWarnings({"rawtypes","unchecked"})
public class InventoryInDao extends BaseDao
{
    /**
     * 添加入库单
     * 
     * @param inventoryIns 入库单对象集合
     * @throws Exception
     */
    public void add(List<InventoryIn> inventoryIns) throws Exception
    {
    		for(InventoryIn inventoryIn:inventoryIns)
    		{
    			getHibernateTemplate().save(inventoryIn);
    		}
    }
    
    /**
     * 删除入库单
     * 
     * @param inventoryIns 入库单对象集合
     * @throws Exception
     */
    public void delete(List<InventoryIn> inventoryIns) throws Exception
    {
    		for(InventoryIn inventoryIn:inventoryIns)
    		{
    			getHibernateTemplate().delete(inventoryIn);
    		}
    }
    
    /**
     * 查询所有符合条件的入库单
     * 
     * @param currPage 当前页码
     * @param conditions 条件集合
     * @return 入库单集合
     */
	public List<InventoryIn> find(final int currPage,final List<Condition> conditions) throws Exception
    {
		List<InventoryIn> result = getHibernateTemplate().execute(new HibernateCallback<List<InventoryIn>>()
		{

			public List<InventoryIn> doInHibernate(Session session) throws HibernateException
			{
				String hql = "from InventoryIn t where 1=1 ";
				Query query = createConditionalQuery(session,hql, conditions, InventoryIn.class);
				query.setFirstResult((currPage - 1) * EasyTradeConstants.COUNT_PER_PAGE).setMaxResults(EasyTradeConstants.COUNT_PER_PAGE);
				// 获取查询结果
				List<InventoryIn> list = query.list();
				
				return list;
			}
		});
		
		return result;
    }

	/**
	 * 查询所有符合条件的入库单
	 *
	 * @param conditions 条件集合
	 * @return 入库单集合
	 */
	public List<InventoryIn> findAll(final List<Condition> conditions) throws Exception
	{

		List<InventoryIn> result= getHibernateTemplate().execute(new HibernateCallback<List<InventoryIn>>()
		{

			public List<InventoryIn> doInHibernate(Session session) throws HibernateException
			{
				String hql = "from InventoryIn t where 1=1 ";
				Query query = createConditionalQuery(session,hql, conditions, InventoryIn.class);
				// 获取查询结果
				List<InventoryIn> list = query.list();
				return list;
			}
		});

		return result;
	}
    
    /**
     * 修改入库单
     * 
     * @param inventoryIns 入库单对象集合
     */
    public void update(List<InventoryIn> inventoryIns) throws Exception
	{
    		for(InventoryIn inventoryIn:inventoryIns)
    		{
    			getHibernateTemplate().update(inventoryIn);
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
				String hql = "select COUNT(t.in_list_no) from InventoryIn t where 1=1";
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
				return generateFlowId(session,"IN", "in_list_no", "inventory_in_tab",3,true);
			}
		});
    		
    		return result;
    }
}
