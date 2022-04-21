package com.ezfarm.fes.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/testa")
public class HomeController {
	
	@RequestMapping(value="/", method = RequestMethod.GET)
	public String home() {
	 System.out.println("ho Controller start");
		return "index";
	}
	
	@RequestMapping(value="/test")
	public String testget() {
			return "NewFile";
	}
}
