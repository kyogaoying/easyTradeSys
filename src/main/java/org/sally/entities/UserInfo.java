package org.sally.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user_info_tab")
public class UserInfo
{
	@Id
	private String user_no;
	@Column(nullable=false)
	private String user_name;
	@Column(nullable=false)
	private String password;
	@Column(nullable=false)
	private String sex;
	private String tel;
	private String mobile;
	private String position;
	public String getUser_no()
	{
		return user_no;
	}
	public void setUser_no(String user_no)
	{
		this.user_no = user_no;
	}
	public String getUser_name()
	{
		return user_name;
	}
	public void setUser_name(String user_name)
	{
		this.user_name = user_name;
	}
	public String getPassword()
	{
		return password;
	}
	public void setPassword(String password)
	{
		this.password = password;
	}
	public String getSex()
	{
		return sex;
	}
	public void setSex(String sex)
	{
		this.sex = sex;
	}
	public String getTel()
	{
		return tel;
	}
	public void setTel(String tel)
	{
		this.tel = tel;
	}
	public String getMobile()
	{
		return mobile;
	}
	public void setMobile(String mobile)
	{
		this.mobile = mobile;
	}
	public String getPosition()
	{
		return position;
	}
	public void setPosition(String position)
	{
		this.position = position;
	}
	
}
