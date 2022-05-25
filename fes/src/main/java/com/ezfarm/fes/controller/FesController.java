package com.ezfarm.fes.controller;

import com.ezfarm.fes.util.URLPath;
import com.ezfarm.fes.vo.SearchVO;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ezfarm.fes.elastic.service.ElasticService;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.util.HashMap;

@Controller
@RequestMapping("/fes")
public class FesController {

	private final ElasticService service;

	public FesController(ElasticService service){
		this.service = service;
	}

	Logger logger = LoggerFactory.getLogger(FesController.class);

	@GetMapping("/search")
	public String search(Model model) throws Exception {
		LocalDate now = LocalDate.now();
		final LocalDate LIMIT = LocalDate.of(2022,03,25);
		LocalDate pastOfDays = now.minusDays(7); // 디폴트 7일
		LocalDate pastOfWeeks = now.minusWeeks(5); // 디폴트 5주
		LocalDate pastOfMonths = (now.minusMonths(6).isBefore(LIMIT)) ? LIMIT : now.minusMonths(6);// 디폴트 6개월

		String condition[] = {"default","default","default"};
		model.addAttribute("all", service.fesSearch());

		model.addAttribute("day", service.fesDailySearch(condition));
		model.addAttribute("conditionD", "default");
		model.addAttribute("firstDateD", pastOfDays);
		model.addAttribute("endDateD", now);

		model.addAttribute("week", service.fesWeekSearch(condition));
		model.addAttribute("conditionW", "default");
		model.addAttribute("firstDateW", pastOfWeeks);
		model.addAttribute("endDateW", now);

		model.addAttribute("month", service.fesMonthSearch(condition));
		model.addAttribute("conditionM", "default");
		model.addAttribute("firstDateM", pastOfMonths);
		model.addAttribute("endDateM", now);
		return "dashboard";
	}

	@ResponseBody
	@GetMapping(value = URLPath.API_FES_SEARCH_DAILY)
	public String fesSearchDaily(SearchVO searchVO){
		logger.info("API_FES_SEARCH_DAILY", searchVO.toString());
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		try {
			resultMap.put("result", service.fesDailySearchAPI(searchVO));
		}catch(Exception e) {
			logger.error("API_FES_SEARCH_DAILY", e);
		}
		return new Gson().toJson(resultMap);
	}

	@ResponseBody
	@GetMapping(value = URLPath.API_FES_SEARCH_WEEK)
	public String fesSearchWeek(SearchVO searchVO){
		logger.info("API_FES_SEARCH_WEEK", searchVO.toString());
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		try {
			resultMap.put("result", service.fesWeekSearchAPI(searchVO));
		}catch(Exception e) {
			logger.error("API_FES_SEARCH_WEEK", e);
		}
		return new Gson().toJson(resultMap);
	}

	@ResponseBody
	@GetMapping(value = URLPath.API_FES_SEARCH_MONTH)
	public String fesSearchMonth(SearchVO searchVO){
		logger.info("API_FES_SEARCH_MONTH", searchVO.toString());
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		try {
			resultMap.put("result", service.fesMonthSearchAPI(searchVO));
		}catch(Exception e) {
			logger.error("API_FES_SEARCH_MONTH", e);
		}
		return new Gson().toJson(resultMap);
	}
}
