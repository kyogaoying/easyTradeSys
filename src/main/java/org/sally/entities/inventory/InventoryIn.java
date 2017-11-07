package org.sally.entities.inventory;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.sally.converter.DateConverter;

@Entity
@Table(name = "inventory_in_tab")
public class InventoryIn implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8868960706048032607L;
	
	@Id
	@Column(length = 30)
	private String in_list_no;

	@Id
	@Column(length = 30)
	private String prod_no;

	@org.hibernate.annotations.Formula("(select i.prod_name from purchase_prod_tab i where i.prod_no = prod_no)")
	private String prod_name;

	private int in_qty = 0;

	@org.hibernate.annotations.Formula("(select i.inventory_loc from inventory_info_tab i where i.prod_no = prod_no)")
	private String inventory_loc;

	@Convert(converter = DateConverter.class, disableConversion = false)
	private String in_date;

	@Column(length = 30)
	private String pur_order_no;

	public String getIn_list_no()
	{
		return in_list_no;
	}

	public void setIn_list_no(String in_list_no)
	{
		this.in_list_no = in_list_no;
	}

	public String getProd_name()
	{
		return prod_name;
	}

	public String getProd_no()
	{
		return prod_no;
	}

	public void setProd_no(String prod_no)
	{
		this.prod_no = prod_no;
	}

	public int getIn_qty()
	{
		return in_qty;
	}

	public void setIn_qty(int in_qty)
	{
		this.in_qty = in_qty;
	}

	public String getInventory_loc()
	{
		return inventory_loc;
	}

	public String getIn_date()
	{
		return in_date;
	}

	public void setIn_date(String in_date)
	{
		this.in_date = in_date;
	}

	public void setProd_name(String prod_name)
	{
		this.prod_name = prod_name;
	}

	public void setInventory_loc(String inventory_loc)
	{
		this.inventory_loc = inventory_loc;
	}

	public String getPur_order_no()
	{
		return pur_order_no;
	}

	public void setPur_order_no(String pur_order_no)
	{
		this.pur_order_no = pur_order_no;
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
		result = prime * result + ((in_list_no == null) ? 0 : in_list_no.hashCode());
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
		InventoryIn other = (InventoryIn) obj;
		if (in_list_no == null)
		{
			if (other.in_list_no != null)
				return false;
		}
		else if (!in_list_no.equals(other.in_list_no))
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
