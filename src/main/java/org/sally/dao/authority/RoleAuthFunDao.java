package org.sally.dao.authority;

import org.hibernate.query.Query;
import org.sally.constant.EasyTradeConstants;
import org.sally.dao.BaseDao;
import org.sally.entities.Condition;
import org.sally.entities.authority.RoleAuthFun;
import org.sally.entities.authority.RoleAuthFun;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 角色权限数据操作对象
 * 
 * @author Sally
 * @since 2017-10-18
 */
@Repository
@SuppressWarnings({"rawtypes","unchecked"})
public class RoleAuthFunDao extends BaseDao
{
    /**
     * 添加角色权限
     * 
     * @param roleAuthFuns 角色权限对象集合
     * @throws Exception 所有异常
     */
    public void add(List<RoleAuthFun> roleAuthFuns) throws Exception
    {
		for(RoleAuthFun roleAuthFun:roleAuthFuns)
		{
			getHibernateTemplate().save(roleAuthFun);
		}
    }
    
    /**
     * 删除角色权限
     * 
     * @param roleAuthFuns 角色权限对象集合
     * @throws Exception 所有异常
     */
    public void delete(List<RoleAuthFun> roleAuthFuns) throws Exception
    {
		for(RoleAuthFun roleAuthFun:roleAuthFuns)
		{
			getHibernateTemplate().delete(roleAuthFun);
		}
    }
    
    /**
     * 查询所有符合条件的模块功能
     * 
     * @param currPage 当前页码
     * @param conditions 条件集合
     * @return 模块功能集合
     */
	public List<RoleAuthFun> find(final int currPage,final List<Condition> conditions) throws Exception
    {
		List<RoleAuthFun> result = getHibernateTemplate().execute(new HibernateCallback<List<RoleAuthFun>>()
		{

			public List<RoleAuthFun> doInHibernate(Session session) throws HibernateException
			{
				String hql = "from RoleAuthFun t where 1=1 ";
				Query query = createConditionalQuery(session,hql, conditions, RoleAuthFun.class);
				query.setFirstResult((currPage - 1) * EasyTradeConstants.COUNT_PER_PAGE).setMaxResults(EasyTradeConstants.COUNT_PER_PAGE);
				// 获取查询结果
				List<RoleAuthFun> list = query.list();
				
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
	public List<RoleAuthFun> findAll(final List<Condition> conditions) throws Exception
	{

		List<RoleAuthFun> result= getHibernateTemplate().execute(new HibernateCallback<List<RoleAuthFun>>()
		{

			public List<RoleAuthFun> doInHibernate(Session session) throws HibernateException
			{
				String hql = "from RoleAuthFun t where 1=1 ";
				Query query = createConditionalQuery(session,hql, conditions, RoleAuthFun.class);
				// 获取查询结果
				List<RoleAuthFun> list = query.list();
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
				String hql = "select COUNT(t.module_no) from RoleAuthFun t where 1=1";
				Query query = createConditionalQuery(session,hql, conditions, Long.class);
				Long result = (Long) query.uniqueResult();
				return result == null ? 0 : result;
			}
		});

		return result;
    }
    
    /**
     * 修改角色权限
     * 
     * @param roleAuthFuns 角色权限对象集合
     */
    public void update(List<RoleAuthFun> roleAuthFuns) throws Exception
	{
		for(RoleAuthFun roleAuthFun:roleAuthFuns)
		{
			getHibernateTemplate().update(roleAuthFun);
		}
	}
}
