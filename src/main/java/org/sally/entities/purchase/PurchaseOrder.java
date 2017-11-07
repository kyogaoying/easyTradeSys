package org.sally.entities.purchase;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.sally.converter.DateConverterWithoutTime;

@Entity
@Table(name="PURCHASE_ORDER_TAB")
public class PurchaseOrder implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(length=30)
	private String order_no;
	@Id
	@Column(length=30)
	private String prod_no;
	
	@org.hibernate.annotations.Formula("(select i.prod_name from purchase_prod_tab i where i.prod_no = prod_no)")
	private String prod_name;
	
	private int qty = 0;
	
	@Convert(converter=DateConverterWithoutTime.class,disableConversion=false)
	private String expected_delivery_date;
	
	@Column(length=30)
	private String pi_no;
	
	@org.hibernate.annotations.Formula("(select i.vendor_no from purchase_prod_tab i where i.prod_no = prod_no)")
	private String vendor_no;
	
	@Column(length=30)
	private String int_order_no;
	
	
	public String getOrder_no()
	{
		return order_no;
	}

	public String getProd_no()
	{
		return prod_no;
	}

	public int getQty()
	{
		return qty;
	}

	public String getExpected_delivery_date()
	{
		return expected_delivery_date;
	}

	public String getPi_no()
	{
		return pi_no;
	}

	public String getVendor_no()
	{
		return vendor_no;
	}

	public void setOrder_no(String order_no)
	{
		this.order_no = order_no;
	}

	public void setProd_no(String prod_no)
	{
		this.prod_no = prod_no;
	}

	public void setQty(int qty)
	{
		this.qty = qty;
	}

	public void setExpected_delivery_date(String expected_delivery_date)
	{
		this.expected_delivery_date = expected_delivery_date;
	}

	public void setPi_no(String pi_no)
	{
		this.pi_no = pi_no;
	}

	public String getProd_name()
	{
		return prod_name;
	}
	
	public String getInt_order_no()
	{
		return int_order_no;
	}

	public void setInt_order_no(String int_order_no)
	{
		this.int_order_no = int_order_no;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((order_no == null) ? 0 : order_no.hashCode());
		result = prime * result + ((prod_no == null) ? 0 : prod_no.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PurchaseOrder other = (PurchaseOrder) obj;
		if (order_no == null)
		{
			if (other.order_no != null)
				return false;
		}
		else if (!order_no.equals(other.order_no))
			return false;
		if (prod_no == null)
		{
			if (other.prod_no != null)
				return false;
		}
		else if (!prod_no.equals(other.prod_no))
			return false;
		return true;
	}
	
	
	
}
