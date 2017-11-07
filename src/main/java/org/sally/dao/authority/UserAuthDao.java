package org.sally.dao.authority;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.sally.dao.BaseDao;
import org.sally.entities.authority.UserAuth;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.stereotype.Repository;

/**
 * 用户权限数据操作对象
 * 
 * @author SquallGao
 *
 */
@Repository
public class UserAuthDao extends BaseDao
{
	
	/**
     * 根据用户ID查找对应的模块功能权限
     * 
     * @param user_no 用户ID
     * @return 模块功能权限集合
     */
	@SuppressWarnings("unchecked")
    public List<UserAuth> findUserAuth(final String user_no) throws Exception
    {
		List<Object[]> list = getHibernateTemplate().execute(new HibernateCallback<List<Object[]>>()
		{

			public List<Object[]> doInHibernate(Session session) throws HibernateException
			{
				String hql = "select f.module_no,f.fun_flag " + 
						"from RoleUser ru " + 
						"join RoleAuthFun f " + 
						"on ru.role_no = f.role_no " + 
						"where f.active = 1 " + 
						"and ru.active = 1 " + 
						"and ru.user_no = :user_no ";
				List<Object[]> list = session.createQuery(hql).setParameter("user_no", user_no).list();
				
				return list;
			}
		});
		List<UserAuth> result = new ArrayList<UserAuth>();
    		for(Object[] objs :list )
    		{
    			UserAuth ua = new UserAuth();
    			ua.setModule_no(String.valueOf(objs[0]));
    			ua.setFun_flag(String.valueOf(objs[1]));
    			result.add(ua);
    		}
    		
    		return result;
    }
}
