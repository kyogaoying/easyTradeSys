package org.sally.service.sysConfig;

import org.sally.dao.sysConfig.SysConfigDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysConfigService {
	@Autowired
	private SysConfigDao sysConfigDao;

	public String resetPw(String user_no,String password){
		String result = sysConfigDao.resetPw(user_no,password);
		String result_json="{\"result\":\""+result+"\"}";
		return result_json;
	}

}
