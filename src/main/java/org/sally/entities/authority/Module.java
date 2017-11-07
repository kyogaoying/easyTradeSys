package org.sally.entities.authority;

import javax.persistence.*;

@Entity
@Table(name = "module_tab")
public class Module
{
	@Id
	@Column(length = 30)
	protected String module_no;
	@Column(length = 50)
	protected String module_name;
	protected String help;
	protected String remark;
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

	public void setModule_name(String module_name)
	{
		this.module_name = module_name;
	}

	public String getHelp()
	{
		return help;
	}

	public void setHelp(String help)
	{
		this.help = help;
	}

	public String getRemark()
	{
		return remark;
	}

	public void setRemark(String remark)
	{
		this.remark = remark;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Module module = (Module) o;

		return module_no.equals(module.module_no);
	}

	@Override
	public int hashCode()
	{
		return module_no.hashCode();
	}
}
