package org.sally.service.authority;

import org.sally.dao.authority.ModuleDao;
import org.sally.entities.Condition;
import org.sally.entities.ExecuteResult;
import org.sally.entities.authority.Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 模块服务对象
 * 
 * @author Sally
 * @since 2017-10-18
 *
 */
@Service
public class ModuleService
{
    @Autowired
    private ModuleDao moduleDao;

    /**
     * 添加模块
     * 
     * @param modules 模块对象集合
     * @return 执行结果对象
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    public ExecuteResult add(List<Module> modules) throws Exception
    {
    		ExecuteResult er = new ExecuteResult();
        moduleDao.add(modules);
        er.setResult(1);
        
        return er;
    }

    /**
     * 删除模块
     * 
     * @param modules 模块对象集合
     * @return 执行结果对象
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    public ExecuteResult delete(List<Module> modules) throws Exception
    {
    		ExecuteResult er = new ExecuteResult();
    		moduleDao.delete(modules);
    		
    		return er;
    }
    
    /**
     * 查询所有符合条件的模块
     * 
     * @param currPage 当前页码
     * @param conditions 条件集合
     * @return 模块集合
     */
	public List<Module> find(int currPage,List<Condition> conditions) throws Exception
    {
		return moduleDao.find(currPage, conditions);
    }

    /**
     * 查询所有符合条件的模块
     *
     * @param conditions 条件集合
     * @return 模块集合
     */
    public List<Module> findAll(final List<Condition> conditions) throws Exception
    {
        return moduleDao.findAll(conditions);
    }

	/**
     * 获取查询数据的总行数
     * 
     * @param conditions 条件集合
     * @return 总行数
     */
    public long getCount(List<Condition> conditions)
    {
    		return moduleDao.getCount(conditions);
    }
    
    /**
     * 修改模块
     * 
     * @param modules 模块对象集合
     * @return 执行结果对象
     */
    @Transactional(rollbackFor=Exception.class)
    public ExecuteResult update(List<Module> modules) throws Exception
	{
    		ExecuteResult er = new ExecuteResult();
    		moduleDao.update(modules);
    		er.setResult(1);
    		
    		return er;
	}
}
