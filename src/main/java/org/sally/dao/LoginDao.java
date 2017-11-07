package org.sally.dao;

import java.util.List;

import org.hibernate.Session;
import org.sally.entities.UserInfo;
import org.sally.util.HibernateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * ��¼��֤���ܵ�Dao��
 */

@Repository
public class LoginDao {
	@Autowired
	private HibernateUtil hibernateUtil;
	/**
	 * ��֤�û�������
	 * @param username �û�������û���
	 * @param password �û����������
	 * @return �ѵ�½��UserInfo����,����null(�û����������)
	 */
	public UserInfo loginVerify(String user_no,String password){
		Session session=hibernateUtil.getSession();	
		
		String hql_query="from UserInfo where user_no=? and password=?";
		//����û�����������򷵻�null
		UserInfo userInfo=(UserInfo)session.createQuery(hql_query,UserInfo.class).
				setString(0, user_no).setString(1, password).uniqueResult();
		return userInfo;
	}
}
