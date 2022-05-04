package com.ezfarm.fes.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ezfarm.fes.elastic.service.ElasticService;

import java.io.IOException;

@Controller
@RequestMapping("/fes")
public class FesController {

	@Autowired
	private ElasticService service;
	public static String keepSearch[][] = {{"default", "default", "default"},{"default", "default", "default"},{"default", "default", "default"}};
	Logger logger = LoggerFactory.getLogger(FesController.class);

	@GetMapping("/search")
	public String search(Model model,
			@RequestParam(value = "gcCondition", required = false, defaultValue = "default") String gcCondition,
			@RequestParam(value = "startCondition", required = false, defaultValue = "default") String startCondition,
			@RequestParam(value = "endCondition", required = false, defaultValue = "default") String endCondition)
			throws Exception {

		if (gcCondition.equals("일간 누적 표")) {
			gcCondition = "daily";
			keepSearch[0][0] = "daily";
			keepSearch[0][1] = startCondition;
			keepSearch[0][2] = endCondition;
		} else if (gcCondition.equals("주간 누적 표")) {
			gcCondition = "week";
			keepSearch[1][0] = "week";
			keepSearch[1][1] = startCondition;
			keepSearch[1][2] = endCondition;
		} else if (gcCondition.equals("월간 누적 표") ) {
			gcCondition = "month";
			keepSearch[2][0] = "month";
			keepSearch[2][1] = startCondition;
			keepSearch[2][2] = endCondition;
		}


		String condition[] = {"default","default","default"};
		model.addAttribute("all", service.fesSearch());

		if(keepSearch[0][0].equals("default")){
			model.addAttribute("day", service.fesDailySearch(condition));

			model.addAttribute("conditionD", "default");
			model.addAttribute("firstDateD", "default");
			model.addAttribute("endDateD", "default");
		}else{
			condition[0] = keepSearch[0][0];
			condition[1] = keepSearch[0][1];
			condition[2] = keepSearch[0][2];
			model.addAttribute("day", service.fesDailySearch(condition));

			model.addAttribute("conditionD", condition[0]);
			model.addAttribute("firstDateD", condition[1]);
			model.addAttribute("endDateD", condition[2]);
		}

		if(keepSearch[1][0].equals("default")){
			model.addAttribute("week", service.fesWeekSearch(condition));

			model.addAttribute("conditionW", "default");
			model.addAttribute("firstDateW", "default");
			model.addAttribute("endDateW", "default");
		}else {
			condition[0] = keepSearch[1][0];
			condition[1] = keepSearch[1][1];
			condition[2] = keepSearch[1][2];
			model.addAttribute("week", service.fesWeekSearch(condition));

			model.addAttribute("conditionW", condition[0]);
			model.addAttribute("firstDateW", condition[1]);
			model.addAttribute("endDateW", condition[2]);
		}



		if(keepSearch[2][0].equals("default")){
			model.addAttribute("month", service.fesMonthSearch(condition));

			model.addAttribute("conditionM", "default");
			model.addAttribute("firstDateM", "default");
			model.addAttribute("endDateM", "default");
		}else {
			condition[0] = keepSearch[2][0];
			condition[1] = keepSearch[2][1];
			condition[2] = keepSearch[2][2];
			model.addAttribute("month", service.fesMonthSearch(condition));

			model.addAttribute("conditionM", condition[0]);
			model.addAttribute("firstDateM", condition[1]);
			model.addAttribute("endDateM", condition[2]);
		}
		return "dashboard";
	}

}
