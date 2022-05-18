package com.example.boardPrac2.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;


@Getter
@Setter
//@ToString
public class Criteria {

    private int pageNum;   // 보여줄 페이지 번호 = 현재 페이지
    private int amount;   // 페이지당 보여줄 게시글의 개수


    // 동적 검색을 위한 멤버 변수
    private String type;        // t, tx, tcw. cw. 이런 식으로 하려고 함 ==> 배열 필요
    private String keyword;


    public Criteria() {
        this(1, 10);
    }

    public Criteria(int pageNum, int amount) {
        this.pageNum = pageNum;
        this.amount = amount;
    }

    // loop를 돌리 기 위해 배열이 필요하다.
    public String[] getTypeArr() {
        // string 배열이 null이 안나오게 하려고 빈 배열을 준다.
        return  type == null? new String[] {} : type.split("");


    }

}