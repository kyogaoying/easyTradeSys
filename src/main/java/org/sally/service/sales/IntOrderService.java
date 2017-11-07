package org.sally.service.sales;

import java.util.List;

import org.sally.dao.sales.IntOrderDao;
import org.sally.entities.Condition;
import org.sally.entities.ExecuteResult;
import org.sally.entities.sales.IntOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class IntOrderService
{
	@Autowired
	private IntOrderDao intOrderDao;
	
	/**
     * 添加入库单
     * 
     * @param intOrders 入库单对象集合
     * @return 执行结果对象
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    public ExecuteResult add(List<IntOrder> intOrders) throws Exception
    {
    	ExecuteResult er = new ExecuteResult();
        intOrderDao.add(intOrders);
        er.setResult(1);
        
        return er;
    }

    /**
     * 删除入库单
     * 
     * @param intOrders 入库单对象集合
     * @return 执行结果对象
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    public ExecuteResult delete(List<IntOrder> intOrders) throws Exception
    {
    		ExecuteResult er = new ExecuteResult();
    		intOrderDao.delete(intOrders);
    		
    		return er;
    }
    
    /**
     * 查询所有符合条件的入库单
     * 
     * @param currPage 当前页码
     * @param conditions 条件集合
     * @return 入库单集合
     */
    public List<IntOrder> find(int currPage,List<Condition> conditions) throws Exception
    {
        return intOrderDao.find(currPage,conditions);
    }

    /**
     * 查询所有符合条件的入库单
     *
     * @param conditions 条件集合
     * @return 入库单集合
     */
    public List<IntOrder> findAll(final List<Condition> conditions) throws Exception
    {
        return intOrderDao.findAll(conditions);
    }

    /**
     * 修改入库单
     * 
     * @param intOrders 入库单对象集合
     * @return 执行结果对象
     */
    @Transactional(rollbackFor=Exception.class)
    public ExecuteResult update(List<IntOrder> intOrders) throws Exception
	{
		ExecuteResult er = new ExecuteResult();
		intOrderDao.update(intOrders);
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
		return intOrderDao.getCount(conditions);
    }
    
    /**
     * 获取生成的ID
     * @return 生成的ID
     */
    public String getId()
    {
		return intOrderDao.getId();
    }
    
}
