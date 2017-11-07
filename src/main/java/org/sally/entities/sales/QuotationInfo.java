package org.sally.entities.sales;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.sally.converter.DateConverter;
import org.sally.converter.DateConverterWithoutTime;

/**
 * quotation_no列和prod_no列为联合主键
 * @author Sally
 *
 */
@Entity
@Table(name="QUOTATION_TAB")
public class QuotationInfo implements Serializable
{

	@Id
	@Column(length=20,nullable=false)
	private String quotation_no;
	
	@Column(length=10,nullable=false)
	private String customer_no;
	 
	@org.hibernate.annotations.Formula("(select c.customer_name from CUSTOMER_INFO_TAB c where c.customer_no = customer_no)")
	private String customer_name;
	
	@org.hibernate.annotations.Formula("(select c.address from CUSTOMER_INFO_TAB c where c.customer_no = customer_no)")
	private String address;
	
	@org.hibernate.annotations.Formula("(select c.tel from CUSTOMER_INFO_TAB c where c.customer_no = customer_no)")
	private String tel;
	
	@Id
	@Column(length=10)
	private String prod_no;
	
	@org.hibernate.annotations.Formula("(select p.prod_name_eng from PURCHASE_PROD_TAB p where p.prod_no = prod_no)")
	private String prod_name_eng;
	
	private String prod_desc;
	
	@Column(nullable=false)
	private Double price;
	
	private String quotation_remark;
	
	@Convert(converter = DateConverterWithoutTime.class, disableConversion = false)
	private String quotation_date;

	public String getQuotation_no()
	{
		return quotation_no;
	}

	public String getCustomer_no()
	{
		return customer_no;
	}

	public String getCustomer_name()
	{
		return customer_name;
	}

	public String getAddress()
	{
		return address;
	}

	public String getTel()
	{
		return tel;
	}

	public String getProd_no()
	{
		return prod_no;
	}

	public String getProd_name_eng()
	{
		return prod_name_eng;
	}

	public String getProd_desc()
	{
		return prod_desc;
	}

	public Double getPrice()
	{
		return price;
	}

	public String[] getQuotation_remark()
	{
		if (quotation_remark==null)
		{
			return null;
		}
		return quotation_remark.split("@@");
	}

	public void setQuotation_no(String quotation_no)
	{
		this.quotation_no = quotation_no;
	}

	public void setCustomer_no(String customer_no)
	{
		this.customer_no = customer_no;
	}

	public void setProd_no(String prod_no)
	{
		this.prod_no = prod_no;
	}

	public void setProd_desc(String prod_desc)
	{
		this.prod_desc = prod_desc;
	}

	public void setPrice(Double price)
	{
		this.price = price;
	}

	public void setQuotation_remark(String quotation_remark)
	{
		this.quotation_remark = quotation_remark;
	}

	public String getQuotation_date()
	{
		//截取年月日部分
		if (quotation_date!=null)
		{
			quotation_date=quotation_date.substring(0, 10);
		}
		return quotation_date;
	}

	public void setCustomer_name(String customer_name)
	{
		this.customer_name = customer_name;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public void setTel(String tel)
	{
		this.tel = tel;
	}

	public void setProd_name_eng(String prod_name_eng)
	{
		this.prod_name_eng = prod_name_eng;
	}

	public void setQuotation_date(String quotation_date)
	{
		this.quotation_date = quotation_date;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((prod_no == null) ? 0 : prod_no.hashCode());
		result = prime * result + ((quotation_no == null) ? 0 : quotation_no.hashCode());
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
		QuotationInfo other = (QuotationInfo) obj;
		if (prod_no == null) {
			if (other.prod_no != null)
				return false;
		} else if (!prod_no.equals(other.prod_no))
			return false;
		if (quotation_no == null) {
			if (other.quotation_no != null)
				return false;
		} else if (!quotation_no.equals(other.quotation_no))
			return false;
		return true;
	}


}
