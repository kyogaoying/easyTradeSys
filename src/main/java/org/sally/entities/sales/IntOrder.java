package org.sally.entities.sales;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.sally.converter.DateConverterWithoutTime;

@Entity
@Table(name = "INT_ORDER_TAB")
public class IntOrder implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4338850716747107003L;

	@Id
	@Column(length=30)
	private String int_order_no;
	
	@Id
	@Column(length=30)
	private String prod_no;
	
	@org.hibernate.annotations.Formula("(select i.prod_name from PURCHASE_PROD_TAB i where i.prod_no = prod_no)")
	private String prod_name;
	
	@Column(length=30)
	private String pi_no;
	
	private int qty = 0;
	
	@Convert(converter=DateConverterWithoutTime.class,disableConversion=false)
	private String expected_delivery_date;
	
	private String int_order_remark;

	public String getInt_order_no()
	{
		return int_order_no;
	}

	public String getProd_no()
	{
		return prod_no;
	}

	public String getPi_no()
	{
		return pi_no;
	}

	public int getQty()
	{
		return qty;
	}

	public String getExpected_delivery_date()
	{
		return expected_delivery_date;
	}

	public String getInt_order_remark()
	{
		return int_order_remark;
	}
	
	public String getProd_name()
	{
		return prod_name;
	}

	public void setInt_order_no(String int_order_no)
	{
		this.int_order_no = int_order_no;
	}

	public void setProd_no(String prod_no)
	{
		this.prod_no = prod_no;
	}

	public void setPi_no(String pi_no)
	{
		this.pi_no = pi_no;
	}

	public void setQty(int qty)
	{
		this.qty = qty;
	}

	public void setExpected_delivery_date(String expected_delivery_date)
	{
		this.expected_delivery_date = expected_delivery_date;
	}

	public void setInt_order_remark(String int_order_remark)
	{
		this.int_order_remark = int_order_remark;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((int_order_no == null) ? 0 : int_order_no.hashCode());
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
		IntOrder other = (IntOrder) obj;
		if (int_order_no == null)
		{
			if (other.int_order_no != null)
				return false;
		}
		else if (!int_order_no.equals(other.int_order_no))
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
