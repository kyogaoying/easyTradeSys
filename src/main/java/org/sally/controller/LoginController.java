package org.sally.controller;

import java.util.List;

import org.sally.entities.UserInfo;
import org.sally.entities.authority.UserAuth;
import org.sally.service.LoginService;
import org.sally.service.authority.RoleService;
import org.sally.service.authority.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.alibaba.fastjson.JSON;

@Controller
@SessionAttributes({ "userInfo", "userAuth","is_admin" })
public class LoginController
{
	@Autowired
	private LoginService loginService;
	@Autowired
	private UserAuthService userAuthService;
	@Autowired
	private RoleService roleService;

	@RequestMapping("/login")
	public String login(@RequestParam("username") String user_no, String password, Model model)
	{
		UserInfo userInfo = loginService.loginVerify(user_no, password);
		if (userInfo != null)
		{
			boolean is_admin = roleService.isAdmin(user_no);
			
			// 根据用户名获取用户拥有的模块功能权限
			List<UserAuth> userAuthList = null;
			try
			{
				userAuthList = userAuthService.findUserAuth(user_no);
			}
			catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 将UserInfo对象放入session中
			model.addAttribute("userInfo", userInfo);
			// 将UserAuth对象放入session中
			model.addAttribute("userAuth", JSON.toJSON(userAuthList));
			model.addAttribute("is_admin", is_admin);
			return "index";
		}
		else
		{
			model.addAttribute("warning", "用户名密码不正确！");
			return "forward:/login.jsp";
		}
	}
}
