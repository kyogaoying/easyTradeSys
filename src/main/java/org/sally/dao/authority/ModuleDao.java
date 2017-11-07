package org.sally.dao.authority;

import org.hibernate.query.Query;
import org.sally.constant.EasyTradeConstants;
import org.sally.dao.BaseDao;
import org.sally.entities.Condition;
import org.sally.entities.authority.Module;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 模块数据操作对象
 * 
 * @author Sally
 * @since 2017-10-18
 */
@Repository
@SuppressWarnings({"rawtypes","unchecked"})
public class ModuleDao extends BaseDao
{
    
    /**
     * 添加模块
     * 
     * @param modules 模块对象集合
     * @throws Exception
     */
    public void add(List<Module> modules) throws Exception
    {
    		for(Module module:modules)
    		{
    			getHibernateTemplate().save(module);
    		}
    }
    
    /**
     * 删除模块
     * 
     * @param modules 模块对象集合
     * @throws Exception
     */
    public void delete(List<Module> modules) throws Exception
    {
    		getHibernateTemplate().deleteAll(modules);
    }
    
    /**
     * 查询所有符合条件的模块
     * 
     * @param currPage 当前页码
     * @param conditions 条件集合
     * @return 模块集合
     */
	public List<Module> find(final int currPage,final List<Condition> conditions) throws Exception
    {
        
        List<Module> result= getHibernateTemplate().execute(new HibernateCallback<List<Module>>()
		{

			public List<Module> doInHibernate(Session session) throws HibernateException
			{
				String hql = "from Module t where 1=1 ";
				Query query = createConditionalQuery(session,hql, conditions, Module.class);
				query.setFirstResult((currPage - 1) * EasyTradeConstants.COUNT_PER_PAGE).setMaxResults(EasyTradeConstants.COUNT_PER_PAGE);
				// 获取查询结果
				List<Module> list = query.list();
				return list;
			}
		});

        return result;
    }

	/**
	 * 查询所有符合条件的模块
	 *
	 * @param conditions 条件集合
	 * @return 模块集合
	 */
	public List<Module> findAll(final List<Condition> conditions) throws Exception
	{

		List<Module> result= getHibernateTemplate().execute(new HibernateCallback<List<Module>>()
		{

			public List<Module> doInHibernate(Session session) throws HibernateException
			{
				String hql = "from Module t where 1=1 ";
				Query query = createConditionalQuery(session,hql, conditions, Module.class);
				// 获取查询结果
				List<Module> list = query.list();
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
				String hql = "select COUNT(t.module_no) from Module t where 1=1";
				Query query = createConditionalQuery(session,hql, conditions, Long.class);
				Long result = (Long) query.uniqueResult();
				return result == null ? 0 : result;
			}
		});
    		
    		return result;
    }
	
    /**
     * 修改模块
     * 
     * @param modules 模块对象集合
     */
    public void update(List<Module> modules) throws Exception
	{
    		for(Module module:modules)
    		{
    			getHibernateTemplate().update(module);
    		}
	}
}
