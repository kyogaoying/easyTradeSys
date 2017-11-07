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
	 * ����dao����֤�û�������
	 * @param password �û�����
	 * @return �ѵ�½��UserInfo����,����null(�û����������)
	 */
	public UserInfo loginVerify(String user_no, String password) {
		return loginDao.loginVerify(user_no, password);
	}
}
