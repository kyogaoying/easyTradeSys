package org.sally.entities.inventory;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.sally.converter.DateConverter;

@Entity
@Table(name = "inventory_out_tab")
public class InventoryOut implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 947464033911241014L;

	@Id
	@Column(length = 30)
	private String out_list_no;

	@Id
	@Column(length = 30)
	private String prod_no;

	@org.hibernate.annotations.Formula("(select i.prod_name from purchase_prod_tab i where i.prod_no = prod_no)")
	private String prod_name;

	private int out_qty = 0;

	@Convert(converter = DateConverter.class, disableConversion = false)
	private String out_date;

	@Column(length = 30)
	private String pur_order_no;
	
	@org.hibernate.annotations.Formula("(select distinct i.int_order_no from purchase_order_tab i where i.order_no = pur_order_no)")
	@Column(length = 30)
	private String int_order_no;

	public String getOut_list_no()
	{
		return out_list_no;
	}

	public void setOut_list_no(String out_list_no)
	{
		this.out_list_no = out_list_no;
	}

	public String getProd_no()
	{
		return prod_no;
	}

	public void setProd_no(String prod_no)
	{
		this.prod_no = prod_no;
	}

	public String getProd_name()
	{
		return prod_name;
	}

	public int getOut_qty()
	{
		return out_qty;
	}

	public void setOut_qty(int out_qty)
	{
		this.out_qty = out_qty;
	}

	public String getOut_date()
	{
		return out_date;
	}

	public void setOut_date(String out_date)
	{
		this.out_date = out_date;
	}

	public String getPur_order_no()
	{
		return pur_order_no;
	}

	public void setPur_order_no(String pur_order_no)
	{
		this.pur_order_no = pur_order_no;
	}

	public String getInt_order_no()
	{
		return int_order_no;
	}

	public void setInt_order_no(String int_order_no)
	{
		this.int_order_no = int_order_no;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((out_list_no == null) ? 0 : out_list_no.hashCode());
		result = prime * result + ((prod_no == null) ? 0 : prod_no.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InventoryOut other = (InventoryOut) obj;
		if (out_list_no == null)
		{
			if (other.out_list_no != null)
				return false;
		}
		else if (!out_list_no.equals(other.out_list_no))
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
