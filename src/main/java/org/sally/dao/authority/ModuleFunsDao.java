package org.sally.dao.authority;

import org.hibernate.query.Query;
import org.sally.constant.EasyTradeConstants;
import org.sally.dao.BaseDao;
import org.sally.entities.Condition;
import org.sally.entities.authority.ModuleFuns;
import org.sally.entities.authority.ModuleFuns;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 模块功能功能数据操作对象
 * 
 * @author Sally
 * @since 2017-10-18
 */
@Repository
@SuppressWarnings({"rawtypes","unchecked"})
public class ModuleFunsDao extends BaseDao
{
    /**
     * 添加模块功能功能
     * 
     * @param moduleFuns 模块功能功能对象集合
     * @throws Exception
     */
    public void add(List<ModuleFuns> moduleFuns) throws Exception
    {
    		for(ModuleFuns moduleFunFuns:moduleFuns)
    		{
    			getHibernateTemplate().save(moduleFunFuns);
    		}
    }
    
    /**
     * 删除模块功能功能
     * 
     * @param moduleFuns 模块功能功能对象集合
     * @throws Exception
     */
    public void delete(List<ModuleFuns> moduleFuns) throws Exception
    {
    		for(ModuleFuns moduleFun:moduleFuns)
    		{
    			getHibernateTemplate().delete(moduleFun);
    		}
    }
    
    /**
     * 查询所有符合条件的模块功能
     * 
     * @param currPage 当前页码
     * @param conditions 条件集合
     * @return 模块功能集合
     */
	public List<ModuleFuns> find(final int currPage,final List<Condition> conditions) throws Exception
    {
		List<ModuleFuns> result = getHibernateTemplate().execute(new HibernateCallback<List<ModuleFuns>>()
		{

			public List<ModuleFuns> doInHibernate(Session session) throws HibernateException
			{
				// TODO Auto-generated method stub
				String hql = "from ModuleFuns t where 1=1 ";
				Query query = createConditionalQuery(session,hql, conditions, ModuleFuns.class);
				query.setFirstResult((currPage - 1) * EasyTradeConstants.COUNT_PER_PAGE).setMaxResults(EasyTradeConstants.COUNT_PER_PAGE);
				// 获取查询结果
				List<ModuleFuns> list = query.list();
				return list;
			}
		});

        return result;
    }

	/**
	 * 查询所有符合条件的模块功能
	 *
	 * @param conditions 条件集合
	 * @return 模块功能集合
	 */
	public List<ModuleFuns> findAll(final List<Condition> conditions) throws Exception
	{

		List<ModuleFuns> result= getHibernateTemplate().execute(new HibernateCallback<List<ModuleFuns>>()
		{

			public List<ModuleFuns> doInHibernate(Session session) throws HibernateException
			{
				String hql = "from ModuleFuns t where 1=1 ";
				Query query = createConditionalQuery(session,hql, conditions, ModuleFuns.class);
				// 获取查询结果
				List<ModuleFuns> list = query.list();
				return list;
			}
		});

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
    		Long result = getHibernateTemplate().execute(new HibernateCallback<Long>()
		{

			public Long doInHibernate(Session session) throws HibernateException
			{
				// TODO Auto-generated method stub
				String hql = "select COUNT(t.fun_flag) from ModuleFuns t where 1=1";
				Query query = createConditionalQuery(session,hql, conditions, Long.class);
				Long result = (Long) query.uniqueResult();
				return result == null ? 0 : result;
			}
		});
    		
    		return result;
    }
    
    /**
     * 修改模块功能
     * 
     * @param moduleFuns 模块功能对象集合
     */
    public void update(List<ModuleFuns> moduleFuns) throws Exception
	{
    		for(ModuleFuns moduleFun:moduleFuns)
    		{
    			getHibernateTemplate().update(moduleFun);
    		}
	}
}
