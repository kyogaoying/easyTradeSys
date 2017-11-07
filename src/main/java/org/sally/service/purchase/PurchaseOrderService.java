package org.sally.service.purchase;

import java.util.List;

import org.sally.dao.purchase.PurchaseOrderDao;
import org.sally.entities.Condition;
import org.sally.entities.ExecuteResult;
import org.sally.entities.purchase.PurchaseOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 采购订单服务对象
 * 
 * @author Sally
 * @since 2017-10-31
 */
@Service
public class PurchaseOrderService
{
	@Autowired
	private PurchaseOrderDao purchaseOrderDao;
	
	/**
     * 添加采购订单
     * 
     * @param purchaseOrders 采购订单对象集合
     * @return 执行结果对象
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    public ExecuteResult add(List<PurchaseOrder> purchaseOrders) throws Exception
    {
    	ExecuteResult er = new ExecuteResult();
        purchaseOrderDao.add(purchaseOrders);
        er.setResult(1);
        
        return er;
    }

    /**
     * 删除采购订单
     * 
     * @param purchaseOrders 采购订单对象集合
     * @return 执行结果对象
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    public ExecuteResult delete(List<PurchaseOrder> purchaseOrders) throws Exception
    {
    		ExecuteResult er = new ExecuteResult();
    		purchaseOrderDao.delete(purchaseOrders);
    		
    		return er;
    }
    
    /**
     * 查询所有符合条件的采购订单
     * 
     * @param currPage 当前页码
     * @param conditions 条件集合
     * @return 采购订单集合
     */
    public List<PurchaseOrder> find(int currPage,List<Condition> conditions) throws Exception
    {
        return purchaseOrderDao.find(currPage, conditions);
    }

    /**
     * 查询所有符合条件的采购订单
     *
     * @param conditions 条件集合
     * @return 采购订单集合
     */
    public List<PurchaseOrder> findAll(final List<Condition> conditions) throws Exception
    {
        return purchaseOrderDao.findAll(conditions);
    }

    /**
     * 修改采购订单
     * 
     * @param purchaseOrders 采购订单对象集合
     * @return 执行结果对象
     */
    @Transactional(rollbackFor=Exception.class)
    public ExecuteResult update(List<PurchaseOrder> purchaseOrders) throws Exception
	{
		ExecuteResult er = new ExecuteResult();
		purchaseOrderDao.update(purchaseOrders);
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
		return purchaseOrderDao.getCount(conditions);
    }
    
    /**
     * 获取生成的ID
     * @return 生成的ID
     */
    public String getId()
    {
		return purchaseOrderDao.getId();
    }
    
}
