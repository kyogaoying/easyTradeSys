package org.sally.entities.inventory;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "inventory_info_tab")
public class InventoryInfo
{
	@Id
	@Column(length = 30)
	private String prod_no;
	@Column(length = 50)
	private String prod_name;

	private int available_qty = 0;
	@Column(length = 30)
	private String inventory_loc;

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

	public void setProd_name(String prod_name)
	{
		this.prod_name = prod_name;
	}

	public int getAvailable_qty()
	{
		return available_qty;
	}

	public void setAvailable_qty(int available_qty)
	{
		this.available_qty = available_qty;
	}

	public String getInventory_loc()
	{
		return inventory_loc;
	}

	public void setInventory_loc(String inventory_loc)
	{
		this.inventory_loc = inventory_loc;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
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
		InventoryInfo other = (InventoryInfo) obj;
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
