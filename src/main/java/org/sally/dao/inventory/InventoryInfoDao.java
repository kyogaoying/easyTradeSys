package org.sally.dao.inventory;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.sally.constant.EasyTradeConstants;
import org.sally.dao.BaseDao;
import org.sally.entities.Condition;
import org.sally.entities.inventory.InventoryInfo;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.stereotype.Repository;

/**
 * 库存信息数据操作对象
 * 
 * @author Sally
 * @since 2017-10-20
 */
@Repository
@SuppressWarnings({"rawtypes","unchecked"})
public class InventoryInfoDao extends BaseDao
{
    /**
     * 添加库存信息
     * 
     * @param inventoryInfos 库存信息对象集合
     * @throws Exception
     */
    public void add(List<InventoryInfo> inventoryInfos) throws Exception
    {
		for(InventoryInfo inventoryInfo:inventoryInfos)
		{
			getHibernateTemplate().save(inventoryInfo);
		}
    }
    
    /**
     * 删除库存信息
     * 
     * @param inventoryInfos 库存信息对象集合
     * @throws Exception
     */
    public void delete(List<InventoryInfo> inventoryInfos) throws Exception
    {
		for(InventoryInfo inventoryInfo:inventoryInfos)
		{
			getHibernateTemplate().delete(inventoryInfo);
		}
    }
    
    /**
     * 查询所有符合条件的库存信息
     * 
     * @param currPage 当前页码
     * @param conditions 条件集合
     * @return 库存信息集合
     */
	public List<InventoryInfo> find(final int currPage,final List<Condition> conditions) throws Exception
    {
		List<InventoryInfo> result = getHibernateTemplate().execute(new HibernateCallback<List<InventoryInfo>>()
		{

			public List<InventoryInfo> doInHibernate(Session session) throws HibernateException
			{
				String hql = "from InventoryInfo t where 1=1 ";
				Query query = createConditionalQuery(session,hql, conditions, InventoryInfo.class);
				query.setFirstResult((currPage - 1) * EasyTradeConstants.COUNT_PER_PAGE).setMaxResults(EasyTradeConstants.COUNT_PER_PAGE);
				// 获取查询结果
				List<InventoryInfo> list = query.list();
				
				return list;
			}
		});
		
		return result;
    }

	/**
	 * 查询所有符合条件的库存信息
	 *
	 * @param conditions 条件集合
	 * @return 库存信息集合
	 */
	public List<InventoryInfo> findAll(final List<Condition> conditions) throws Exception
	{

		List<InventoryInfo> result= getHibernateTemplate().execute(new HibernateCallback<List<InventoryInfo>>()
		{

			public List<InventoryInfo> doInHibernate(Session session) throws HibernateException
			{
				String hql = "from InventoryInfo t where 1=1 ";
				Query query = createConditionalQuery(session,hql, conditions, InventoryInfo.class);
				// 获取查询结果
				List<InventoryInfo> list = query.list();
				return list;
			}
		});

		return result;
	}
    
    /**
     * 修改库存信息
     * 
     * @param inventoryInfos 库存信息对象集合
     */
    public void update(List<InventoryInfo> inventoryInfos) throws Exception
	{
		for(InventoryInfo inventoryInfo:inventoryInfos)
		{
			getHibernateTemplate().update(inventoryInfo);
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
				String hql = "select COUNT(t.prod_no) from InventoryInfo t where 1=1";
				Query query = createConditionalQuery(session,hql, conditions, Long.class);
				Long result = (Long) query.uniqueResult();
				return result == null ? 0 : result;
			}
		});
    		
    		return result;
    }
    
    /**
     * 检查该产品编号是否存在
     * 
     * @param prod_no 产品编号
     * @return true/false
     */
    public boolean exist(final String prod_no)
    {
    		Boolean result = getHibernateTemplate().execute(new HibernateCallback<Boolean>()
		{

			public Boolean doInHibernate(Session session) throws HibernateException
			{
				InventoryInfo info = session.get(InventoryInfo.class, prod_no);
				return info == null ? false : true;
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
				return generateFlowId(session,"P", "prod_no", "inventory_info_tab",6,false);
			}
		});
    		
    		return result;
    }
}
