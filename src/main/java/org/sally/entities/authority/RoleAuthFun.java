package org.sally.entities.authority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "role_auth_fun_tab")
public class RoleAuthFun implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1794210539008516185L;

	@Id
	@Column(length = 30)
	private String role_no;
	@Id
	@Column(length = 30)
	private String module_no;
	@Id
	@Column(length = 10)
	private String fun_flag;

	@org.hibernate.annotations.Formula("(select r.role_name from role_tab r where r.role_no = role_no)")
	private String role_name;

	@org.hibernate.annotations.Formula("(select m.module_name from module_tab m where m.module_no = module_no)")
	private String module_name;

	@org.hibernate.annotations.Formula("(select f.fun_desc from module_funs_tab f where f.module_no = module_no and f.fun_flag = fun_flag)")
	private String fun_desc;

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

	public String getModule_no()
	{
		return module_no;
	}

	public void setModule_no(String module_no)
	{
		this.module_no = module_no;
	}

	public String getFun_flag()
	{
		return fun_flag;
	}

	public void setFun_flag(String fun_flag)
	{
		this.fun_flag = fun_flag;
	}

	public String getRole_name()
	{
		return role_name;
	}

	public String getModule_name()
	{
		return module_name;
	}

	public String getFun_desc()
	{
		return fun_desc;
	}

	public boolean isActive()
	{
		return active;
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

		RoleAuthFun that = (RoleAuthFun) o;

		if (!role_no.equals(that.role_no))
			return false;
		if (!module_no.equals(that.module_no))
			return false;
		return fun_flag.equals(that.fun_flag);
	}

	@Override
	public int hashCode()
	{
		int result = role_no.hashCode();
		result = 31 * result + module_no.hashCode();
		result = 31 * result + fun_flag.hashCode();
		return result;
	}
}
