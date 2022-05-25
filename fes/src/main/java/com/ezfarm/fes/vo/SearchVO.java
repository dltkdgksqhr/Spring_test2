package com.ezfarm.fes.vo;

import lombok.Data;

@Data
public class SearchVO {
    //일, 주, 월 별 조건
    private String gcCondition;
    //시작 일자
    private String startCondition;
    //종료 일자
    private String endCondition;
}
