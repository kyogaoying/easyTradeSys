package org.sally.entities.authority;

import java.io.Serializable;

public class UserAuth implements Serializable
{
	private String module_no;
	private String fun_flag;

	public UserAuth() {}
	
	public UserAuth(String module_no,String fun_flag)
	{
		this.module_no = module_no;
		this.fun_flag = fun_flag;
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

}
