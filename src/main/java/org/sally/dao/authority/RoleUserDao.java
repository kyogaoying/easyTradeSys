package org.sally.dao.authority;

import org.hibernate.query.Query;
import org.sally.constant.EasyTradeConstants;
import org.sally.dao.BaseDao;
import org.sally.entities.Condition;
import org.sally.entities.authority.RoleUser;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 角色用户数据操作对象
 * 
 * @author Sally
 * @since 2017-10-02
 */
@Repository
@SuppressWarnings({"rawtypes","unchecked"})
public class RoleUserDao extends BaseDao
{
    /**
     * 添加角色用户
     * 
     * @param roleUsers 角色用户对象集合
     * @throws Exception
     */
    public void add(List<RoleUser> roleUsers) throws Exception
    {
    		for(RoleUser roleUser:roleUsers)
    		{
    			getHibernateTemplate().save(roleUser);
    		}
    }
    
    /**
     * 删除角色用户
     * 
     * @param roleUsers 角色用户对象集合
     * @throws Exception
     */
    public void delete(List<RoleUser> roleUsers) throws Exception
    {
    		for(RoleUser roleUser:roleUsers)
    		{
    			getHibernateTemplate().delete(roleUser);
    		}
    }
    
    /**
     * 查询所有符合条件的角色
     * 
     * @param currPage 当前页码
     * @param conditions 条件集合
     * @return 角色集合
     */
	public List<RoleUser> find(final int currPage,final List<Condition> conditions) throws Exception
    {
		List<RoleUser> result = getHibernateTemplate().execute(new HibernateCallback<List<RoleUser>>()
		{

			public List<RoleUser> doInHibernate(Session session) throws HibernateException
			{
				// TODO Auto-generated method stub
				String hql = "from RoleUser t where 1=1 ";
				Query query = createConditionalQuery(session,hql, conditions, RoleUser.class);
				query.setFirstResult((currPage - 1) * EasyTradeConstants.COUNT_PER_PAGE).setMaxResults(EasyTradeConstants.COUNT_PER_PAGE);
				// 获取查询结果
				List<RoleUser> list = query.list();
				return list;
			}
		});

        return result;
    }

	/**
	 * 查询所有符合条件的角色用户
	 *
	 * @param conditions 条件集合
	 * @return 角色用户集合
	 */
	public List<RoleUser> findAll(final List<Condition> conditions) throws Exception
	{

		List<RoleUser> result= getHibernateTemplate().execute(new HibernateCallback<List<RoleUser>>()
		{

			public List<RoleUser> doInHibernate(Session session) throws HibernateException
			{
				String hql = "from RoleUser t where 1=1 ";
				Query query = createConditionalQuery(session,hql, conditions, RoleUser.class);
				// 获取查询结果
				List<RoleUser> list = query.list();
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
				String hql = "select COUNT(t.role_no) from RoleUser t where 1=1";
				Query query = createConditionalQuery(session,hql, conditions, Long.class);
				Long result = (Long) query.uniqueResult();
				return result == null ? 0 : result;
			}
		});
    		
    		return result;
    }
    
    /**
     * 修改角色用户
     * 
     * @param roleUsers 角色用户对象集合
     */
    public void update(List<RoleUser> roleUsers) throws Exception
	{
    		for(RoleUser roleUser:roleUsers)
    		{
    			getHibernateTemplate().update(roleUser);
    		}
	}
}
