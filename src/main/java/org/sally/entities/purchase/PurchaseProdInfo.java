package org.sally.entities.purchase;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="PURCHASE_PROD_TAB")
public class PurchaseProdInfo
{
	@Id
	@Column(length=10)
	private String prod_no;
	private String prod_name;
	private String prod_name_eng;
	@Column(precision=10,scale=2)
	private double pur_price;
	private String vendor_no;
	private String prod_status;
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
	public String getVendor_no()
	{
		return vendor_no;
	}
	public void setVendor_no(String vendor_no)
	{
		this.vendor_no = vendor_no;
	}
	public String getProd_status()
	{
		return prod_status;
	}
	public void setProd_status(String prod_status)
	{
		this.prod_status = prod_status;
	}
	public String getProd_name_eng() {
		return prod_name_eng;
	}
	public void setProd_name_eng(String prod_name_eng) {
		this.prod_name_eng = prod_name_eng;
	}
	public double getPur_price() {
		return pur_price;
	}
	public void setPur_price(double pur_price) {
		this.pur_price = pur_price;
	}
	
}
