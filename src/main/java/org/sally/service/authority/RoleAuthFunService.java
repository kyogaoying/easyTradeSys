package org.sally.service.authority;

import org.sally.dao.authority.RoleAuthFunDao;
import org.sally.entities.Condition;
import org.sally.entities.ExecuteResult;
import org.sally.entities.authority.RoleAuthFun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 角色权限服务对象
 * 
 * @author Sally
 * @since 2017-10-18
 *
 */
@Service
public class RoleAuthFunService
{
    @Autowired
    private RoleAuthFunDao roleAuthFunDao;

    /**
     * 添加角色权限
     * 
     * @param roleAuthFuns 角色权限对象集合
     * @return 执行结果对象
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    public ExecuteResult add(List<RoleAuthFun> roleAuthFuns) throws Exception
    {
    		ExecuteResult er = new ExecuteResult();
        roleAuthFunDao.add(roleAuthFuns);
        er.setResult(1);
        
        return er;
    }

    /**
     * 删除角色权限
     * 
     * @param roleAuthFuns 角色权限对象集合
     * @return 执行结果对象
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    public ExecuteResult delete(List<RoleAuthFun> roleAuthFuns) throws Exception
    {
        ExecuteResult er = new ExecuteResult();
        roleAuthFunDao.delete(roleAuthFuns);

        return er;
    }
    
    /**
     * 查询所有符合条件的角色权限
     * 
     * @param currPage 当前页码
     * @param conditions 条件集合
     * @return 角色权限集合
     */
	public List<RoleAuthFun> find(int currPage,List<Condition> conditions) throws Exception
    {
		return roleAuthFunDao.find(currPage, conditions);
    }

    /**
     * 查询所有符合条件的角色权限
     *
     * @param conditions 条件集合
     * @return 角色权限集合
     */
    public List<RoleAuthFun> findAll(final List<Condition> conditions) throws Exception
    {
        return roleAuthFunDao.findAll(conditions);
    }

	/**
     * 获取查询数据的总行数
     * 
     * @param conditions 条件集合
     * @return 总行数
     */
    public long getCount(List<Condition> conditions)
    {
    		return roleAuthFunDao.getCount(conditions);
    }
	
    /**
     * 修改角色权限
     * 
     * @param roleAuthFuns 角色权限对象集合
     * @return 执行结果对象
     */
    @Transactional(rollbackFor=Exception.class)
    public ExecuteResult update(List<RoleAuthFun> roleAuthFuns) throws Exception
	{
    		ExecuteResult er = new ExecuteResult();
    		roleAuthFunDao.update(roleAuthFuns);
    		er.setResult(1);
    		
    		return er;
	}
}
