package org.sally.dao;

import java.util.List;

import org.hibernate.Session;
import org.sally.entities.UserInfo;
import org.sally.util.HibernateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 登录验证功能的Dao层
 */

@Repository
public class LoginDao {
	@Autowired
	private HibernateUtil hibernateUtil;
	/**
	 * 验证用户名密码
	 * @param username 用户输入的用户名
	 * @param password 用户输入的密码
	 * @return 已登陆的UserInfo对象,或者null(用户名密码错误)
	 */
	public UserInfo loginVerify(String user_no,String password){
		Session session=hibernateUtil.getSession();	
		
		String hql_query="from UserInfo where user_no=? and password=?";
		//如果用户名密码错误则返回null
		UserInfo userInfo=(UserInfo)session.createQuery(hql_query,UserInfo.class).
				setString(0, user_no).setString(1, password).uniqueResult();
		return userInfo;
	}
}
