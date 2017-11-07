package org.sally.service.inventory;

import java.util.List;

import org.sally.dao.inventory.InventoryOutDao;
import org.sally.entities.Condition;
import org.sally.entities.ExecuteResult;
import org.sally.entities.inventory.InventoryOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 出库单服务对象
 * 
 * @author Sally
 * @since 2017-10-25
 *
 */
@Service
public class InventoryOutService
{
    @Autowired
    private InventoryOutDao inventoryOutDao;

    /**
     * 添加出库单
     * 
     * @param inventoryOuts 出库单对象集合
     * @return 执行结果对象
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    public ExecuteResult add(List<InventoryOut> inventoryOuts) throws Exception
    {
    		ExecuteResult er = new ExecuteResult();
        inventoryOutDao.add(inventoryOuts);
        er.setResult(1);
        
        return er;
    }

    /**
     * 删除出库单
     * 
     * @param inventoryOuts 出库单对象集合
     * @return 执行结果对象
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    public ExecuteResult delete(List<InventoryOut> inventoryOuts) throws Exception
    {
    		ExecuteResult er = new ExecuteResult();
    		inventoryOutDao.delete(inventoryOuts);
    		
    		return er;
    }
    
    /**
     * 查询所有符合条件的出库单
     * 
     * @param currPage 当前页码
     * @param conditions 条件集合
     * @return 出库单集合
     */
    public List<InventoryOut> find(String currPage,List<Condition> conditions) throws Exception
    {
        return inventoryOutDao.find(Integer.parseInt(currPage),conditions);
    }

    /**
     * 查询所有符合条件的出库单
     *
     * @param conditions 条件集合
     * @return 出库单集合
     */
    public List<InventoryOut> findAll(final List<Condition> conditions) throws Exception
    {
        return inventoryOutDao.findAll(conditions);
    }

    /**
     * 修改出库单
     * 
     * @param inventoryOuts 出库单对象集合
     * @return 执行结果对象
     */
    @Transactional(rollbackFor=Exception.class)
    public ExecuteResult update(List<InventoryOut> inventoryOuts) throws Exception
	{
    		ExecuteResult er = new ExecuteResult();
    		inventoryOutDao.update(inventoryOuts);
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
    		return inventoryOutDao.getCount(conditions);
    }
    
    /**
     * 获取生成的ID
     * @return 生成的ID
     */
    public String getId()
    {
    		return inventoryOutDao.getId();
    }
}
