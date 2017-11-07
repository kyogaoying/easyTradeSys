package org.sally.service.inventory;

import java.util.List;

import org.sally.dao.inventory.InventoryInDao;
import org.sally.entities.Condition;
import org.sally.entities.ExecuteResult;
import org.sally.entities.inventory.InventoryIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 入库单服务对象
 * 
 * @author Sally
 * @since 2017-10-20
 *
 */
@Service
public class InventoryInService
{
    @Autowired
    private InventoryInDao inventoryInDao;

    /**
     * 添加入库单
     * 
     * @param inventoryIns 入库单对象集合
     * @return 执行结果对象
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    public ExecuteResult add(List<InventoryIn> inventoryIns) throws Exception
    {
    	ExecuteResult er = new ExecuteResult();
        inventoryInDao.add(inventoryIns);
        er.setResult(1);
        
        return er;
    }

    /**
     * 删除入库单
     * 
     * @param inventoryIns 入库单对象集合
     * @return 执行结果对象
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    public ExecuteResult delete(List<InventoryIn> inventoryIns) throws Exception
    {
    		ExecuteResult er = new ExecuteResult();
    		inventoryInDao.delete(inventoryIns);
    		
    		return er;
    }
    
    /**
     * 查询所有符合条件的入库单
     * 
     * @param currPage 当前页码
     * @param conditions 条件集合
     * @return 入库单集合
     */
    public List<InventoryIn> find(String currPage,List<Condition> conditions) throws Exception
    {
        return inventoryInDao.find(Integer.parseInt(currPage),conditions);
    }

    /**
     * 查询所有符合条件的入库单
     *
     * @param conditions 条件集合
     * @return 入库单集合
     */
    public List<InventoryIn> findAll(final List<Condition> conditions) throws Exception
    {
        return inventoryInDao.findAll(conditions);
    }

    /**
     * 修改入库单
     * 
     * @param inventoryIns 入库单对象集合
     * @return 执行结果对象
     */
    @Transactional(rollbackFor=Exception.class)
    public ExecuteResult update(List<InventoryIn> inventoryIns) throws Exception
	{
    		ExecuteResult er = new ExecuteResult();
    		inventoryInDao.update(inventoryIns);
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
    		return inventoryInDao.getCount(conditions);
    }
    
    /**
     * 获取生成的ID
     * @return 生成的ID
     */
    public String getId()
    {
    		return inventoryInDao.getId();
    }
}
