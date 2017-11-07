package org.sally.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MenuController {
	@RequestMapping("menu/{module}/{pathUrl}")
	public String redirect(@PathVariable String module, @PathVariable String pathUrl) {
		return module+"/"+pathUrl;
	}
}
