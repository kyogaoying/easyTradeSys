package org.sally.dao.authority;

import org.hibernate.query.Query;
import org.sally.constant.EasyTradeConstants;
import org.sally.dao.BaseDao;
import org.sally.entities.Condition;
import org.sally.entities.authority.Role;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 角色数据操作对象
 * 
 * @author Sally
 * @since 2017-10-02
 */
@Repository
@SuppressWarnings({"rawtypes","unchecked"})
public class RoleDao extends BaseDao
{

    /**
     * 添加角色
     * 
     * @param roles 角色对象集合
     * @throws Exception
     */
    public void add(List<Role> roles) throws Exception
    {
    		for(Role role:roles)
    		{
    			getHibernateTemplate().save(role);
    		}
    }
    
    /**
     * 删除角色
     * 
     * @param roles 角色对象集合
     * @throws Exception
     */
    public void delete(List<Role> roles) throws Exception
    {
    		for(Role role:roles)
    		{
    			getHibernateTemplate().delete(role);
    		}
    }
    
    /**
     * 查询所有符合条件的角色
     * 
     * @param currPage 当前页码
     * @param conditions 条件集合
     * @return 角色集合
     */
	public List<Role> find(final int currPage,final List<Condition> conditions) throws Exception
    {
		
		List<Role> result = getHibernateTemplate().execute(new HibernateCallback<List<Role>>()
		{

			public List<Role> doInHibernate(Session session) throws HibernateException
			{
				String hql = "from Role t where 1=1 ";
				Query query = createConditionalQuery(session,hql, conditions, Role.class);
				query.setFirstResult((currPage - 1) * EasyTradeConstants.COUNT_PER_PAGE).setMaxResults(EasyTradeConstants.COUNT_PER_PAGE);
				// 获取查询结果
				List<Role> list = query.list();
				// 关闭会话
				
				return list;
			}
		});
		
		return result;
    }

	/**
	 * 查询所有符合条件的角色
	 *
	 * @param conditions 条件集合
	 * @return 角色集合
	 */
	public List<Role> findAll(final List<Condition> conditions) throws Exception
	{

		List<Role> result= getHibernateTemplate().execute(new HibernateCallback<List<Role>>()
		{

			public List<Role> doInHibernate(Session session) throws HibernateException
			{
				String hql = "from Role t where 1=1 ";
				Query query = createConditionalQuery(session,hql, conditions, Role.class);
				// 获取查询结果
				List<Role> list = query.list();
				return list;
			}
		});

		return result;
	}
    
    /**
     * 修改角色
     * 
     * @param roles 角色对象集合
     */
    public void update(List<Role> roles) throws Exception
	{
    		for(Role role:roles)
    		{
    			getHibernateTemplate().update(role);
    		}
	}
    
    /**
     * 检查该用户是否是管理员
     * 
     * @param user_no 用户ID
     * @return true/false
     */
    public boolean isAdmin(final String user_no)
	{
    		Boolean result = getHibernateTemplate().execute(new HibernateCallback<Boolean>()
		{

			public Boolean doInHibernate(Session session) throws HibernateException
			{
				String hql = "select r.is_admin from Role r "
						+ "join RoleUser ru on r.role_no = ru.role_no and ru.active = 1"
						+ "where ru.user_no =:user_no "
						+ "and r.is_admin = 1";
				// 生成会话
				Boolean bool = session.createQuery(hql,Boolean.class).setParameter("user_no", user_no).uniqueResult();
				return bool != null ? bool : false;
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
				String hql = "select COUNT(t.role_no) from Role t where 1=1";
				Query query = createConditionalQuery(session,hql, conditions, Long.class);
				Long result = (Long) query.uniqueResult();
				return result == null ? 0 : result;
			}
		});
    		
    		return result;
    }
}
