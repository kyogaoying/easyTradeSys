package org.sally.entities.authority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.web.bind.annotation.CrossOrigin;

import java.io.Serializable;

@Entity
@Table(name = "role_user_tab")
public class RoleUser implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1575476646585437899L;

	@Id
	@Column(length = 30)
	private String role_no;

	@Id
	@Column(length = 30)
	private String user_no;

	@org.hibernate.annotations.Formula("(select r.role_name from role_tab r where r.role_no = role_no)")
	private String role_name;

	@org.hibernate.annotations.Formula("(select u.user_name from user_info_tab u where u.user_no = user_no)")
	private String user_name;

	@Column(length = 5)
	private boolean active = true;

	public String getRole_no()
	{
		return role_no;
	}

	public void setRole_no(String role_no)
	{
		this.role_no = role_no;
	}

	public String getUser_no()
	{
		return user_no;
	}

	public void setUser_no(String user_no)
	{
		this.user_no = user_no;
	}

	public boolean isActive()
	{
		return active;
	}

	public String getRole_name()
	{
		return role_name;
	}

	public String getUser_name()
	{
		return user_name;
	}

	public void setActive(boolean active)
	{
		this.active = active;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		RoleUser roleUser = (RoleUser) o;

		if (!role_no.equals(roleUser.role_no))
			return false;
		return user_no.equals(roleUser.user_no);
	}

	@Override
	public int hashCode()
	{
		int result = role_no.hashCode();
		result = 31 * result + user_no.hashCode();
		return result;
	}
}
