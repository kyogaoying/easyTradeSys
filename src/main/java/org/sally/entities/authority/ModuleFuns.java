package org.sally.entities.authority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "module_funs_tab")
public class ModuleFuns implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3427486142473893401L;

	@Id
	@Column(length = 30)
	private String module_no;

	@org.hibernate.annotations.Formula("(select m.module_name from module_tab m where m.module_no = module_no)")
	private String module_name;

	@Id
	@Column(length = 10)
	private String fun_flag;

	private String fun_desc;

	@Column(length = 5)
	private boolean auth_control = false;

	public String getModule_no()
	{
		return module_no;
	}

	public void setModule_no(String module_no)
	{
		this.module_no = module_no;
	}

	public String getModule_name()
	{
		return module_name;
	}

	public String getFun_flag()
	{
		return fun_flag;
	}

	public void setFun_flag(String fun_flag)
	{
		this.fun_flag = fun_flag;
	}

	public String getFun_desc()
	{
		return fun_desc;
	}

	public void setFun_desc(String fun_desc)
	{
		this.fun_desc = fun_desc;
	}

	public boolean isAuth_control()
	{
		return auth_control;
	}

	public void setAuth_control(boolean auth_control)
	{
		this.auth_control = auth_control;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		ModuleFuns that = (ModuleFuns) o;

		if (!module_no.equals(that.module_no))
			return false;
		return fun_flag.equals(that.fun_flag);
	}

	@Override
	public int hashCode()
	{
		int result = module_no.hashCode();
		result = 31 * result + fun_flag.hashCode();
		return result;
	}
}
