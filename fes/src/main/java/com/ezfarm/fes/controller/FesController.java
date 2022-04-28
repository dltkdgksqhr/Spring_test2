package com.ezfarm.fes.controller;

import com.ezfarm.fes.elastic.service.ElasticService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/fes")
public class FesController {

    @Autowired
    private ElasticService service;

    Logger logger = LoggerFactory.getLogger(FesController.class);

//    @GetMapping("/search/{condition}")
//    public String search(Model model, @PathVariable("condition") String condition[]) throws Exception{
    @GetMapping("/search")
    public String search(Model model) throws Exception{


        //만약 검색 조건을 받는 다면
//        String condition[] = {"daily", "2022-01-11", "2022-04-11"};
//        String condition[] = {"week", "2022-01-11", "2022-04-11"};
//        String condition[] = {"month", "2022-01-11", "2022-04-11"};
        String condition[] = { "default", "default", "default" };

        model.addAttribute("all", service.fesSearch());
        model.addAttribute("day", service.fesDailySearch(condition));
        model.addAttribute("week", service.fesWeekSearch(condition));
        model.addAttribute("month", service.fesMonthSearch(condition));


        model.addAttribute("all", service.fesSearch());
        model.addAttribute("day", service.fesDailySearch(condition));
        model.addAttribute("week", service.fesWeekSearch(condition));
        model.addAttribute("month", service.fesMonthSearch(condition));
        
        
        
        
        return "dashboard";
    }
}