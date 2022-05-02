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

    Logger logger = LoggerFactory.getLogger(FesController.class);

//    @GetMapping("/search/{condition}")
//    public String search(Model model, @PathVariable("condition") String condition[]) throws Exception{
    @GetMapping("/search")
    public String search(Model model
            , @RequestParam(value = "gcCondition", required = false, defaultValue = "default") String gcCondition
            , @RequestParam(value = "startCondition", required = false, defaultValue = "default") String startCondition
            , @RequestParam(value = "endCondition", required = false, defaultValue = "default") String endCondition) throws Exception {


        String condition[] = { gcCondition, startCondition, endCondition };

        model.addAttribute("all", service.fesSearch());
        model.addAttribute("day", service.fesDailySearch(condition));
        model.addAttribute("week", service.fesWeekSearch(condition));
        model.addAttribute("month", service.fesMonthSearch(condition));

        return "dashboard";
    }

}
