package org.sally.service.authority;

import org.sally.dao.authority.ModuleFunsDao;
import org.sally.entities.Condition;
import org.sally.entities.ExecuteResult;
import org.sally.entities.authority.ModuleFuns;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 模块功能服务对象
 * 
 * @author Sally
 * @since 2017-10-18
 *
 */
@Service
public class ModuleFunsService
{
    @Autowired
    private ModuleFunsDao moduleFunsDao;

    /**
     * 添加模块功能
     * 
     * @param moduleFunss 模块功能对象集合
     * @return 执行结果对象
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    public ExecuteResult add(List<ModuleFuns> moduleFunss) throws Exception
    {
    		ExecuteResult er = new ExecuteResult();
        moduleFunsDao.add(moduleFunss);
        er.setResult(1);
        
        return er;
    }

    /**
     * 删除模块功能
     * 
     * @param moduleFunss 模块功能对象集合
     * @return 执行结果对象
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    public ExecuteResult delete(List<ModuleFuns> moduleFunss) throws Exception
    {
    		ExecuteResult er = new ExecuteResult();
    		moduleFunsDao.delete(moduleFunss);
    		
    		return er;
    }
    
    /**
     * 查询所有符合条件的模块功能
     * 
     * @param currPage 当前页码
     * @param conditions 条件集合
     * @return 模块功能集合
     */
	public List<ModuleFuns> find(int currPage,List<Condition> conditions) throws Exception
    {
		return moduleFunsDao.find(currPage, conditions);
    }

    /**
     * 查询所有符合条件的模块功能
     *
     * @param conditions 条件集合
     * @return 模块功能集合
     */
    public List<ModuleFuns> findAll(final List<Condition> conditions) throws Exception
    {
        return moduleFunsDao.findAll(conditions);
    }

	/**
     * 获取查询数据的总行数
     * 
     * @param conditions 条件集合
     * @return 总行数
     */
    public long getCount(List<Condition> conditions)
    {
    		return moduleFunsDao.getCount(conditions);
    }
	
    /**
     * 修改模块功能
     * 
     * @param moduleFunss 模块功能对象集合
     * @return 执行结果对象
     */
    @Transactional(rollbackFor=Exception.class)
    public ExecuteResult update(List<ModuleFuns> moduleFunss) throws Exception
	{
    		ExecuteResult er = new ExecuteResult();
    		moduleFunsDao.update(moduleFunss);
    		er.setResult(1);
    		
    		return er;
	}
}
