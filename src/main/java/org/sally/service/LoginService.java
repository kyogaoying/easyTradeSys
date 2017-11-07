package org.sally.service;

import org.sally.dao.LoginDao;
import org.sally.entities.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
	@Autowired
	private LoginDao loginDao;
	/**
	 * 调用dao层验证用户名密码
	 * @param password 用户输入
	 * @return 已登陆的UserInfo对象,或者null(用户名密码错误)
	 */
	public UserInfo loginVerify(String user_no, String password) {
		return loginDao.loginVerify(user_no, password);
	}
}
