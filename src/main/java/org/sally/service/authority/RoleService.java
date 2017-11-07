package org.sally.service.authority;

import org.sally.dao.authority.RoleDao;
import org.sally.entities.Condition;
import org.sally.entities.ExecuteResult;
import org.sally.entities.authority.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleService
{
    @Autowired
    private RoleDao roleDao;

    /**
     * 添加角色
     * 
     * @param roles 角色对象集合
     * @return 执行结果对象
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    public ExecuteResult add(List<Role> roles) throws Exception
    {
    		ExecuteResult er = new ExecuteResult();
        roleDao.add(roles);
        er.setResult(1);
        
        return er;
    }

    /**
     * 删除角色
     * 
     * @param roles 角色对象集合
     * @return 执行结果对象
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    public ExecuteResult delete(List<Role> roles) throws Exception
    {
    		ExecuteResult er = new ExecuteResult();
    		roleDao.delete(roles);
    		
    		return er;
    }
    
    /**
     * 查询所有符合条件的角色
     * 
     * @param currPage 当前页码
     * @param conditions 条件集合
     * @return 角色集合
     */
	public List<Role> find(int currPage,List<Condition> conditions) throws Exception
    {
		return roleDao.find(currPage, conditions);
    }

    /**
     * 查询所有符合条件的角色
     *
     * @param conditions 条件集合
     * @return 模块集合
     */
    public List<Role> findAll(final List<Condition> conditions) throws Exception
    {
        return roleDao.findAll(conditions);
    }

    /**
     * 修改角色
     * 
     * @param roles 角色对象集合
     * @return 执行结果对象
     */
	@Transactional(rollbackFor=Exception.class)
    public ExecuteResult update(List<Role> roles) throws Exception
	{
    		ExecuteResult er = new ExecuteResult();
    		roleDao.update(roles);
    		er.setResult(1);
    		
    		return er;
	}
    
    /**
     * 检查该用户是否是管理员
     * 
     * @param user_no 用户ID
     * @return true/false
     */
    public boolean isAdmin(String user_no)
	{
    		return roleDao.isAdmin(user_no);
	}
    
    /**
     * 获取查询数据的总行数
     * 
     * @param conditions 条件集合
     * @return 总行数
     */
    public long getCount(List<Condition> conditions)
    {
    		return roleDao.getCount(conditions);
    }
}
