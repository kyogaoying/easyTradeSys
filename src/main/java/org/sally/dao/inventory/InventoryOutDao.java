package org.sally.dao.inventory;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.sally.constant.EasyTradeConstants;
import org.sally.dao.BaseDao;
import org.sally.entities.Condition;
import org.sally.entities.inventory.InventoryOut;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.stereotype.Repository;

/**
 * 出库单数据操作对象
 * 
 * @author Sally
 * @since 2017-10-25
 */
@Repository
@SuppressWarnings({"rawtypes","unchecked"})
public class InventoryOutDao extends BaseDao
{
    /**
     * 添加出库单
     * 
     * @param inventoryOuts 出库单对象集合
     * @throws Exception
     */
    public void add(List<InventoryOut> inventoryOuts) throws Exception
    {
		for(InventoryOut inventoryOut:inventoryOuts)
		{
			getHibernateTemplate().save(inventoryOut);
		}
    }
    
    /**
     * 删除出库单
     * 
     * @param inventoryOuts 出库单对象集合
     * @throws Exception
     */
    public void delete(List<InventoryOut> inventoryOuts) throws Exception
    {
		for(InventoryOut inventoryOut:inventoryOuts)
		{
			getHibernateTemplate().delete(inventoryOut);
		}
    }
    
    /**
     * 查询所有符合条件的出库单
     * 
     * @param currPage 当前页码
     * @param conditions 条件集合
     * @return 出库单集合
     */
	public List<InventoryOut> find(final int currPage,final List<Condition> conditions) throws Exception
    {
		List<InventoryOut> result = getHibernateTemplate().execute(new HibernateCallback<List<InventoryOut>>()
		{

			public List<InventoryOut> doInHibernate(Session session) throws HibernateException
			{
				String hql = "from InventoryOut t where 1=1 ";
				Query query = createConditionalQuery(session,hql, conditions, InventoryOut.class);
				query.setFirstResult((currPage - 1) * EasyTradeConstants.COUNT_PER_PAGE).setMaxResults(EasyTradeConstants.COUNT_PER_PAGE);
				// 获取查询结果
				List<InventoryOut> list = query.list();
				
				return list;
			}
		});
		
		return result;
    }

	/**
	 * 查询所有符合条件的出库单
	 *
	 * @param conditions 条件集合
	 * @return 出库单集合
	 */
	public List<InventoryOut> findAll(final List<Condition> conditions) throws Exception
	{

		List<InventoryOut> result= getHibernateTemplate().execute(new HibernateCallback<List<InventoryOut>>()
		{

			public List<InventoryOut> doInHibernate(Session session) throws HibernateException
			{
				String hql = "from InventoryOut t where 1=1 ";
				Query query = createConditionalQuery(session,hql, conditions, InventoryOut.class);
				// 获取查询结果
				List<InventoryOut> list = query.list();
				return list;
			}
		});

		return result;
	}
    
    /**
     * 修改出库单
     * 
     * @param inventoryOuts 出库单对象集合
     */
    public void update(List<InventoryOut> inventoryOuts) throws Exception
	{
    		for(InventoryOut inventoryOut:inventoryOuts)
    		{
    			getHibernateTemplate().update(inventoryOut);
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
				String hql = "select COUNT(t.out_list_no) from InventoryOut t where 1=1";
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
				return generateFlowId(session,"OU", "out_list_no", "inventory_out_tab",3,true);
			}
		});
    		
    		return result;
    }
}
