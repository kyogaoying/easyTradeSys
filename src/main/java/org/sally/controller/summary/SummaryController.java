package org.sally.controller.summary;


import org.sally.entities.summary.Summary;
import org.sally.service.summary.SummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/summary")
public class SummaryController {
	@Autowired
	private SummaryService summaryService;//用于调用业务层方法

	@RequestMapping("/findAll")
	public String findAll(Model model)
	{
		List<Summary> list = summaryService.findAll();
		model.addAttribute("summary",list);
		return "summary/summaryManagement";
	}
}