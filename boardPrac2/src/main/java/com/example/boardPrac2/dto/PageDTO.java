package com.example.boardPrac2.dto;

import lombok.Getter;
import lombok.ToString;

//@ToString
@Getter
public class PageDTO {

    private int startPage, endPage;
    private boolean prev, next;

    private int total;
    private Criteria cri;


    public PageDTO(Criteria cri, int total) {
        this.cri = cri;
        this.total = total;


        this.endPage = (int) (Math.ceil(cri.getPageNum() / 10.0)) * 10;

        this.startPage = endPage - 9;

        this.prev = this.startPage > 1;

        // total(게시물 총수량)이 120일 경우 1.0
        //		(120 * 1.0) / 10(한페이지 게시물 수) = 12
        //		==> 12 페이지에서 끝난다. 근데 이 위에 까지 짠 코드는
        //  마지막 페이지가 20페이지 까지 나온다
        // 그래서 if(realEnd < endPage) endPage = realEnd 해줘야 한다
        int realEnd = (int) (Math.ceil((total * 1.0) / cri.getAmount()));

        this.endPage = realEnd <= endPage ? realEnd : endPage;

        this.next = this.endPage < realEnd;


    }
}
