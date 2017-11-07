package org.sally.service.authority;

import org.sally.dao.authority.RoleAuthFunDao;
import org.sally.dao.authority.UserAuthDao;
import org.sally.entities.ExecuteResult;
import org.sally.entities.authority.RoleAuthFun;
import org.sally.entities.authority.UserAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色权限服务对象
 * 
 * @author Sally
 * @since 2017-10-18
 *
 */
@Service
public class UserAuthService
{
    @Autowired
    private UserAuthDao userAuthFunDao;

    /**
     * 根据用户ID查找对应的模块功能权限
     * 
     * @param user_no 用户ID
     * @return 模块功能权限集合
     * @throws Exception 
     */
    public List<UserAuth> findUserAuth(String user_no) throws Exception
    {
    		return userAuthFunDao.findUserAuth(user_no);
    }
}
