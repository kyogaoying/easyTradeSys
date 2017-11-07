package org.sally.controller.sysConfig;


import org.sally.entities.UserInfo;
import org.sally.service.sysConfig.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/sysConfig")
public class SysConfigController {
	@Autowired
	private SysConfigService sysConfigService;//用于调用业务层方法

	@RequestMapping(value = "/resetPw", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String resetPw(@RequestParam("user_no") String user_no, @RequestParam("password")String password){

		return sysConfigService.resetPw(user_no,password);
	}
}