package org.sally.entities.sales;

import org.sally.converter.DateConverterWithoutTime;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="PI_INFO_TAB")
public class PiInfo implements Serializable {
	@Id
	@Column(length=20,nullable=false)
	private String pi_no;
	
	@Convert(converter = DateConverterWithoutTime.class, disableConversion = false)
	private String pi_date;
	
	@Column(length=20,nullable=false)
	private String quotation_no;
	
	@Column(length=20,nullable=false)
	private String customer_no;
	
	@Column(length=50)
	@org.hibernate.annotations.Formula("(select c.customer_name from CUSTOMER_INFO_TAB c where c.customer_no = customer_no)")
	private String customer_name;
	
	@Column(length=100)
	@org.hibernate.annotations.Formula("(select c.address from CUSTOMER_INFO_TAB c where c.customer_no = customer_no)")
	private String address;
	
	@Column(length=30)
	@org.hibernate.annotations.Formula("(select c.tel from CUSTOMER_INFO_TAB c where c.customer_no = customer_no)")
	private String tel;
	
	@Id
	@Column(length=20,nullable=false)
	private String prod_no;
	
	@Column(length=50)
	@org.hibernate.annotations.Formula("(select p.prod_name_eng from PURCHASE_PROD_TAB p where p.prod_no = prod_no)")
	private String prod_name_eng;
		
	@Column(length=100)
	@org.hibernate.annotations.Formula("(select q.prod_desc from QUOTATION_TAB q where q.prod_no = prod_no and q.quotation_no = quotation_no)")
	private String prod_desc;
	
	@Column(nullable=false)
	private int qty;
	
	@org.hibernate.annotations.Formula("(select q.price from QUOTATION_TAB q where q.prod_no = prod_no and q.quotation_no = quotation_no)")
	private int price;
	
	private int sub_total_price;
	
	@Convert(converter = DateConverterWithoutTime.class, disableConversion = false)
	private String expected_delivery_date;
	
	@org.hibernate.annotations.Formula("(select distinct q.quotation_remark from QUOTATION_TAB q where q.quotation_no = quotation_no)")
	private String pi_remark;

	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}

	public String getPi_no() {
		return pi_no;
	}

	public String getQuotation_no() {
		return quotation_no;
	}

	public String getCustomer_no() {
		return customer_no;
	}

	public String getCustomer_name() {
		return customer_name;
	}

	public String getAddress() {
		return address;
	}

	public String getTel() {
		return tel;
	}

	public String getProd_no() {
		return prod_no;
	}

	public String getProd_name_eng() {
		return prod_name_eng;
	}

	public String getProd_desc() {
		return prod_desc;
	}

	public int getQty() {
		return qty;
	}

	public int getPrice() {
		return price;
	}

	public int getSub_total_price() {
		return sub_total_price;
	}

	public String getExpected_delivery_date() {
		return expected_delivery_date;
	}

	public String[] getPi_remark() {
		if (pi_remark==null)
		{
			return null;
		}
		return pi_remark.split("@@");
	}
	
	
	
	public void setPi_no(String pi_no) {
		this.pi_no = pi_no;
	}

	public void setQuotation_no(String quotation_no) {
		this.quotation_no = quotation_no;
	}

	public String getPi_date() {
		return pi_date;
	}

	public void setPi_date(String pi_date) {
		this.pi_date = pi_date;
	}

	public void setCustomer_no(String customer_no) {
		this.customer_no = customer_no;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public void setSub_total_price(int sub_total_price) {
		this.sub_total_price = sub_total_price;
	}

	public void setExpected_delivery_date(String expected_delivery_date) {
		this.expected_delivery_date = expected_delivery_date;
	}

	public void setProd_no(String prod_no) {
		this.prod_no = prod_no;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pi_no == null) ? 0 : pi_no.hashCode());
		result = prime * result + ((prod_no == null) ? 0 : prod_no.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PiInfo other = (PiInfo) obj;
		if (pi_no == null) {
			if (other.pi_no != null)
				return false;
		} else if (!pi_no.equals(other.pi_no))
			return false;
		if (prod_no == null) {
			if (other.prod_no != null)
				return false;
		} else if (!prod_no.equals(other.prod_no))
			return false;
		return true;
	}

}