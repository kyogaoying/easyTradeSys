package org.sally.dao;


import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.sally.constant.EasyTradeConstants;
import org.sally.entities.Condition;
import org.sally.entities.UserInfo;
import org.sally.util.HibernateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.stereotype.Repository;

import java.util.List;


/*
 * 用户管理功能的Dao层
 */
@Repository
public class UserInfoDao extends BaseDao{
	@Autowired
	private HibernateUtil hibernateUtil;
	/**
	 * 增加一行record
	 * @param userInfo 待增加的userInfo对象
	 */
	public void saveUser(UserInfo userInfo) {
		Transaction transaction=hibernateUtil.getSession().beginTransaction();
		
		hibernateUtil.getSession().save(userInfo);
		
		transaction.commit();
		close();
	}
	/**
	 * 删除一行record
	 * @param user_no 待删除record的user_no
	 * @return 删除的行数
	 */
	public int delete(UserInfo userInfo) {
		Transaction transaction=hibernateUtil.getSession().beginTransaction();
		Session session=hibernateUtil.getSession();
		
		String hql_delete="DELETE from UserInfo u WHERE u.user_no=?";
		int rows=session.createQuery(hql_delete).setParameter(0, userInfo.getUser_no()).executeUpdate();
		
		transaction.commit();
		close();
		return rows;
	}
	/**
	 * 修改一行record
	 * @param userInfo 已修改的userInfo对象
	 */	
	public void updateAndSave(UserInfo userInfo) {
		Transaction transaction=hibernateUtil.getSession().beginTransaction();
		Session session=hibernateUtil.getSession();
		
		//查出密码在set进userInfo对象
		String hql_getPW="SELECT u.password FROM UserInfo u WHERE u.user_no=?";
		String password=session.createQuery(hql_getPW,String.class).setParameter(0, userInfo.getUser_no()).uniqueResult();
		//如果是新添加的用户就没有密码，此时的初始密码是888888
		if (password==null)
		{
			userInfo.setPassword("888888");
		}
		else {
			userInfo.setPassword(password);
		}
		session.saveOrUpdate(userInfo);
		transaction.commit();
		close();
	}
	/**
	 * 指定记录查询
	 * @param 待查询记录的id
	 */
	public List<UserInfo> queryOne(String user_name,int page) {
		Session session=hibernateUtil.getSession();
		
		String hql_query="FROM UserInfo WHERE user_name like ?";
		List<UserInfo> userInfos=session.createQuery(hql_query,UserInfo.class).setParameter(0, "%"+user_name+"%")
				.setFirstResult((page-1)*EasyTradeConstants.COUNT_PER_PAGE)
				.setMaxResults(EasyTradeConstants.COUNT_PER_PAGE).list();
		
		close();
		return userInfos;
	}
	/**
	 * 查询符合条件的总页数
	 * @return
	 */
	public double getTotalPagesAfterFindOne(String user_name) {
		Session session=hibernateUtil.getSession();	
		
		String hql_query="SELECT COUNT(u.user_no) from UserInfo u WHERE u.user_name like ?";
		//总记录数
		long total= session.createQuery(hql_query,Long.class).setParameter(0, "%"+user_name+"%").uniqueResult();
		
		//总页数
		double totalPages=Math.ceil((double)total/(double)EasyTradeConstants.COUNT_PER_PAGE);
		
		close();
		return totalPages;
	}
	/**
	 * 查询总页数
	 * @return
	 */
	public double getTotalPages() {
		Session session=hibernateUtil.getSession();	
		
		String hql_query="SELECT COUNT(u.user_no) from UserInfo u";
		//总记录数
		long total= session.createQuery(hql_query,Long.class).uniqueResult();
		
		//总页数
		double totalPages=Math.ceil((double)total/(double)EasyTradeConstants.COUNT_PER_PAGE);
		
		close();
		return totalPages;
	}
	/**
	 * 全表查询
	 */
	public List<UserInfo> queryAll(int page){
		Session session=hibernateUtil.getSession();	
		
		String hql_query="from UserInfo";
		
		List<UserInfo> list=session.createQuery(hql_query,UserInfo.class)
				.setFirstResult((page-1)*EasyTradeConstants.COUNT_PER_PAGE)
				.setMaxResults(EasyTradeConstants.COUNT_PER_PAGE).list();
		
		close();
		return list;
	}
	/**
	 * 获取编号最大值
	 */
	public int getMax() {
		Session session=hibernateUtil.getSession();	
		
		//查询出UserInfo表的最大user_no
		String hql_query="select max(u.user_no) from UserInfo u";
		String maxNo=session.createQuery(hql_query,String.class).uniqueResult();
		int maxNo_int=Integer.parseInt(maxNo);
		
		close();
		return maxNo_int;
	}
	/**
	 * 关闭session
	 */
	private void close() {
		hibernateUtil.close();
	}

	/**
	 * 查询所有符合条件的入库单
	 *
	 * @param currPage 当前页码
	 * @param conditions 条件集合
	 * @return 入库单集合
	 */
	public List<UserInfo> find(final int currPage, final List<Condition> conditions) throws Exception
	{
		List<UserInfo> result = getHibernateTemplate().execute(new HibernateCallback<List<UserInfo>>()
		{

			public List<UserInfo> doInHibernate(Session session) throws HibernateException
			{
				String hql = "from UserInfo t where 1=1 ";
				Query query = createConditionalQuery(session,hql, conditions, UserInfo.class);
				query.setFirstResult((currPage - 1) * EasyTradeConstants.COUNT_PER_PAGE).setMaxResults(EasyTradeConstants.COUNT_PER_PAGE);
				// 获取查询结果
				List<UserInfo> list = query.list();

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
				String hql = "select COUNT(t.user_no) from UserInfo t where 1=1";
				Query query = createConditionalQuery(session,hql, conditions, Long.class);
				Long result = (Long) query.uniqueResult();
				return result == null ? 0 : result;
			}
		});

		return result;
	}
}

