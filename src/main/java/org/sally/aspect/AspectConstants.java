package org.sally.aspect;

import org.aspectj.lang.Signature;

public class AspectConstants
{

	public static String getFunDesc(Signature signature)
	{
        String typeName = signature.getDeclaringTypeName();
	    String result = "";
	    int a = typeName.lastIndexOf(".");
	    String s = typeName.substring(a+1,typeName.length()-7);
	    if(typeName.contains("authority"))
        {
            if(s.equals("RoleAuthFun"))
            {
                result = "角色权限管理";
            }
            else if(s.equals("ModuleFuns"))
            {
                result = "模块功能管理";
            }
            else if(s.equals("Module"))
            {
                result = "模块管理";
            }
            else if(s.equals("Role"))
            {
                result = "角色管理";
            }
            else if(s.equals("RoleUser"))
            {
                result = "用户角色管理";
            }
        }
	    else if(typeName.contains("inventory"))
        {
            if(s.equals("InventoryInfo"))
            {
                result = "库存管理";
            }
            else if(s.equals("InventoryIn"))
            {
                result = "入库单管理";
            }
            else if(s.equals("InventoryOut"))
            {
                result = "出库单管理";
            }
        }
	    else if(typeName.contains("purchase"))
        {
            if(s.equals("PurchaseOrder"))
            {
                result = "采购订单管理";
            }
            else if(s.equals("PurchaseProdInfo"))
            {
                result = "产品信息管理";
            }
            else if(s.equals("PurchaseVendorInfo"))
            {
                result = "供应商信息管理";
            }
        }
	    else if(typeName.contains("sales"))
        {
            if(s.equals("Customer"))
            {
                result = "客户信息管理";
            }
            else if(s.equals("Quotation"))
            {
                result = "报价单管理";
            }
            else if(s.equals("IntOrder"))
            {
                result = "内部订单管理";
            }
            else if(s.equals("PiInfo"))
            {
                result = "销售合同管理";
            }
        }
        else if(typeName.contains("finance"))
        {
            if(s.equals("Finance"))
            {
                result = "销售合同解锁";
            }
        }
        else if(typeName.contains("shipment"))
        {
            if(s.equals("PackingList"))
            {
                result = "Packing List";
            }
            else if(s.equals("Invoice"))
            {
                result = "Commercial Invoice";
            }
        }

        return result;
	}

}
