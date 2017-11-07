package org.sally.service.inventory;

import java.util.List;

import org.sally.dao.inventory.InventoryInfoDao;
import org.sally.entities.Condition;
import org.sally.entities.ExecuteResult;
import org.sally.entities.inventory.InventoryInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;

/**
 * 库存信息服务对象
 * 
 * @author Sally
 * @since 2017-10-20
 *
 */
@Service
public class InventoryInfoService
{
    @Autowired
    private InventoryInfoDao inventoryInfoDao;

    /**
     * 添加库存信息
     * 
     * @param inventoryInfos 库存信息对象集合
     * @return 执行结果对象
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    public ExecuteResult add(List<InventoryInfo> inventoryInfos) throws Exception
    {
    		ExecuteResult er = new ExecuteResult();
        inventoryInfoDao.add(inventoryInfos);
        er.setResult(1);
        
        return er;
    }

    /**
     * 删除库存信息
     * 
     * @param inventoryInfos 库存信息对象集合
     * @return 执行结果对象
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    public ExecuteResult delete(List<InventoryInfo> inventoryInfos) throws Exception
    {
    		ExecuteResult er = new ExecuteResult();
    		inventoryInfoDao.delete(inventoryInfos);
    		
    		return er;
    }
    
    /**
     * 查询所有符合条件的库存信息
     * 
     * @param currPage 当前页码
     * @param conditions 条件集合
     * @return 库存信息集合
     */
    public List<InventoryInfo> find(String currPage,List<Condition> conditions) throws Exception
    {
        return inventoryInfoDao.find(Integer.parseInt(currPage),conditions);
    }

    /**
     * 查询所有符合条件的库存信息
     *
     * @param conditions 条件集合
     * @return 库存信息集合
     */
    public List<InventoryInfo> findAll(final List<Condition> conditions) throws Exception
    {
        return inventoryInfoDao.findAll(conditions);
    }

    /**
     * 修改库存信息
     * 
     * @param inventoryInfos 库存信息对象集合
     * @return 执行结果对象
     */
    @Transactional(rollbackFor=Exception.class)
    public ExecuteResult update(List<InventoryInfo> inventoryInfos) throws Exception
	{
    		ExecuteResult er = new ExecuteResult();
    		inventoryInfoDao.update(inventoryInfos);
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
    		return inventoryInfoDao.getCount(conditions);
    }
    
    /**
     * 检查该产品编号是否存在
     * 
     * @param prod_no 产品编号
     * @return true/false
     */
    public boolean exist(String prod_no)
    {
    		return inventoryInfoDao.exist(prod_no);
    }
    
    /**
     * 获取生成的ID
     * @return 生成的ID
     */
    public String getId()
    {
    		return inventoryInfoDao.getId();
    }
}
