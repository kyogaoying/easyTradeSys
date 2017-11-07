package org.sally.service.finance;

import java.util.ArrayList;
import java.util.List;

import org.sally.dao.inventory.InventoryInDao;
import org.sally.dao.inventory.InventoryOutDao;
import org.sally.dao.purchase.PurchaseOrderDao;
import org.sally.dao.sales.IntOrderDao;
import org.sally.dao.sales.PiInfoDao;
import org.sally.entities.Condition;
import org.sally.entities.ExecuteResult;
import org.sally.entities.inventory.InventoryIn;
import org.sally.entities.inventory.InventoryOut;
import org.sally.entities.purchase.PurchaseOrder;
import org.sally.entities.sales.IntOrder;
import org.sally.entities.sales.PiInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FinanceService 
{
	@Autowired
	private PiInfoDao piInfoDao;
	
	@Autowired
	private IntOrderDao intOrderDao;
	
	@Autowired
	private PurchaseOrderDao purchaseOrderDao;
	
	@Autowired
	private InventoryInDao inventoryInDao;
	
	@Autowired
	private InventoryOutDao inventoryOutDao;
	
	@Transactional(rollbackFor=Exception.class)
	public ExecuteResult generate(String pi_no) throws Exception
	{
		ExecuteResult er = new ExecuteResult();
		
		String int_order_no = generateIntOrder(pi_no);
		String pur_order_no = generatePurOrder(int_order_no);
		String in_list_no = generateInventoryIn(pur_order_no);
		String out_list_no = generateInventoryOut(in_list_no);
		er.setResult(1);
		er.setMessage("{\"int_order_no\":\"" + int_order_no + "\",\"pur_order_no\":\"" + pur_order_no + "\",\"in_list_no\":\"" + in_list_no + "\",\"out_list_no\":\"" + out_list_no + "\"}");
		
		return er;
	}
	
	/**
	 * 生成内部订单
	 * 
	 * @param pi_no 合同编号
	 * @return 内部订单号
	 * @throws Exception
	 */
	String generateIntOrder(String pi_no) throws Exception
	{
		// 通过合同编号找出对应的合同
		List<PiInfo> piInfos = piInfoDao.queryPi(pi_no);
		List<IntOrder> intOrders = new ArrayList<IntOrder>();
		String int_order_no = intOrderDao.getId();
		// 遍历所有符合条件的合同,并根据合同生成内部订单的信息
		for(PiInfo piInfo : piInfos)
		{
			IntOrder intOrder = new IntOrder();
			intOrder.setInt_order_no(int_order_no);
			intOrder.setProd_no(piInfo.getProd_no()); 
			intOrder.setPi_no(pi_no);
			intOrder.setQty(piInfo.getQty());
			intOrder.setExpected_delivery_date(piInfo.getExpected_delivery_date());
			intOrders.add(intOrder);
		}
		// 将所有生成的内部订单信息添加到数据库
		intOrderDao.add(intOrders);
		
		return int_order_no;
	}
	
	/**
	 * 生成采购订单
	 * 
	 * @param int_order_no 内部订单号
	 * @return 采购订单号
	 * @throws Exception
	 */
	String generatePurOrder(String int_order_no) throws Exception
	{
		List<Condition> conditions = new ArrayList<Condition>();
		Condition condition = new Condition();
		condition.setCondition_key("int_order_no");
		condition.setCondition_value(int_order_no);
		condition.setCondition_type("string");
		conditions.add(condition);
		// 根据内部订单号查询内部订单
		List<IntOrder> intOrders = intOrderDao.findAll(conditions);
		// 存储采购订单的集合
		List<PurchaseOrder> purchaseOrders = new ArrayList<PurchaseOrder>();
		String pur_order_no = purchaseOrderDao.getId();
		for(IntOrder intOrder : intOrders)
		{
			PurchaseOrder purchaseOrder = new PurchaseOrder();
			purchaseOrder.setOrder_no(pur_order_no);
			purchaseOrder.setProd_no(intOrder.getProd_no());
			purchaseOrder.setQty(intOrder.getQty());
			purchaseOrder.setExpected_delivery_date(intOrder.getExpected_delivery_date());
			purchaseOrder.setPi_no(intOrder.getPi_no());
			purchaseOrder.setInt_order_no(intOrder.getInt_order_no());
			purchaseOrders.add(purchaseOrder);
		}
		
		purchaseOrderDao.add(purchaseOrders);
		
		return pur_order_no;
	}
	
	/**
	 * 生成入库单
	 * 
	 * @param order_no 采购订单号
	 * @return 入库单号
	 * @throws Exception
	 */
	String generateInventoryIn(String order_no) throws Exception
	{
		List<Condition> conditions = new ArrayList<Condition>();
		Condition condition = new Condition();
		condition.setCondition_key("order_no");
		condition.setCondition_value(order_no);
		condition.setCondition_type("string");
		conditions.add(condition);
		// 根据内部订单号查询采购订单
		List<PurchaseOrder> purchaseOrders = purchaseOrderDao.findAll(conditions);
		// 存储入库单的集合
		List<InventoryIn> inventoryIns = new ArrayList<InventoryIn>();
		String in_list_no = inventoryInDao.getId();
		for(PurchaseOrder purchaseOrder : purchaseOrders)
		{
			InventoryIn inventoryIn = new InventoryIn();
			inventoryIn.setIn_list_no(in_list_no);
			inventoryIn.setProd_no(purchaseOrder.getProd_no());
			inventoryIn.setIn_qty(purchaseOrder.getQty());
			inventoryIn.setPur_order_no(order_no);
			
			inventoryIns.add(inventoryIn);
		}
		
		inventoryInDao.add(inventoryIns);
		
		return in_list_no;
	}
	
	/**
	 * 生成出库单
	 * 
	 * @param in_list_no 入库单号
	 * @return 出库单号
	 * @throws Exception
	 */
	String generateInventoryOut(String in_list_no) throws Exception
	{
		List<Condition> conditions = new ArrayList<Condition>();
		Condition condition = new Condition();
		condition.setCondition_key("in_list_no");
		condition.setCondition_value(in_list_no);
		condition.setCondition_type("string");
		conditions.add(condition);
		// 根据入库单号查询入库单
		List<InventoryIn> inventoryIns = inventoryInDao.findAll(conditions);
		// 存储出库单的集合
		List<InventoryOut> inventoryOuts = new ArrayList<InventoryOut>();
		String out_list_no = inventoryOutDao.getId();
		for(InventoryIn inventoryIn : inventoryIns)
		{
			InventoryOut inventoryOut = new InventoryOut();
			inventoryOut.setOut_list_no(out_list_no);
			inventoryOut.setProd_no(inventoryIn.getProd_no());
			inventoryOut.setOut_qty(inventoryIn.getIn_qty());
			inventoryOut.setPur_order_no(inventoryIn.getPur_order_no());
			
			inventoryOuts.add(inventoryOut);
		}
		
		inventoryOutDao.add(inventoryOuts);
		
		return out_list_no;
	}
	
}
