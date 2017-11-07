package org.sally.dao.sysConfig;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.sally.util.HibernateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 修改密码
 */

@Repository
public class SysConfigDao {
	@Autowired
	private HibernateUtil hibernateUtil;

	public String resetPw(String user_no,String password){
		Session session=hibernateUtil.getSession();
		Transaction transaction=session.beginTransaction();

		String hql="UPDATE UserInfo i SET i.password =? WHERE i.user_no=?";
		//修改
		int result=session.createQuery(hql).setParameter(0,password).setParameter(1,user_no).executeUpdate();

		transaction.commit();
		session.close();

		if (result==0){
			return "fail";
		}
		else {
			return password;
		}
	}
}
