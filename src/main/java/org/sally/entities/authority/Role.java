package org.sally.entities.authority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "role_tab")
public class Role
{
	@Id
	@Column(length = 30)
	private String role_no;
	private String role_name;
	@Column(length = 5)
	private boolean is_admin = false;

	public String getRole_no()
	{
		return role_no;
	}

	public void setRole_no(String role_no)
	{
		this.role_no = role_no;
	}

	public String getRole_name()
	{
		return role_name;
	}

	public void setRole_name(String role_name)
	{
		this.role_name = role_name;
	}

	public boolean isIs_admin()
	{
		return is_admin;
	}

	public void setIs_admin(boolean is_admin)
	{
		this.is_admin = is_admin;
	}

}
