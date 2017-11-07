package org.sally.service.authority;

import org.sally.dao.authority.RoleUserDao;
import org.sally.entities.Condition;
import org.sally.entities.ExecuteResult;
import org.sally.entities.authority.RoleUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 角色用户服务对象
 * 
 * @author Sally
 *
 */
@Service
public class RoleUserService
{
    @Autowired
    private RoleUserDao roleUserDao;

    /**
     * 添加角色
     * 
     * @param roleUsers 角色对象集合
     * @return 执行结果对象
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    public ExecuteResult add(List<RoleUser> roleUsers) throws Exception
    {
    		ExecuteResult er = new ExecuteResult();
        roleUserDao.add(roleUsers);
        er.setResult(1);
        
        return er;
    }

    /**
     * 删除角色
     * 
     * @param roleUsers 角色对象集合
     * @return 执行结果对象
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    public ExecuteResult delete(List<RoleUser> roleUsers) throws Exception
    {
    		ExecuteResult er = new ExecuteResult();
    		roleUserDao.delete(roleUsers);
    		
    		return er;
    }
    
    /**
     * 查询所有符合条件的角色
     * 
     * @param currPage 当前页码
     * @param conditions 条件集合
     * @return 角色集合
     */
	public List<RoleUser> find(int currPage,List<Condition> conditions) throws Exception
    {
		return roleUserDao.find(currPage, conditions);
    }

    /**
     * 查询所有符合条件的角色用户
     *
     * @param conditions 条件集合
     * @return 角色用户集合
     */
    public List<RoleUser> findAll(final List<Condition> conditions) throws Exception
    {
        return roleUserDao.findAll(conditions);
    }

    /**
     * 修改角色
     * 
     * @param roleUsers 角色对象集合
     * @return 执行结果对象
     */
	@Transactional(rollbackFor=Exception.class)
    public ExecuteResult update(List<RoleUser> roleUsers) throws Exception
	{
    		ExecuteResult er = new ExecuteResult();
    		roleUserDao.update(roleUsers);
    		er.setResult(1);
    		
    		return er;
	}
    
    /**
     * 获取查询数据的总行数
     * 
     * @param conditions 条件集合
     * @return 总行数
     */
    public long getCount(List<Condition> conditions)
    {
    		return roleUserDao.getCount(conditions);
    }
}
