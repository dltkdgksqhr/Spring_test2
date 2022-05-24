package com.example.boardPrac2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@ToString(of = {"b_no","title","content","writer","reg_date"}) 원본
@ToString(of = {"no","ttl","cntnt","rgdte"})
public class BoardDto {

    /* private int b_no;
    private String title;
    private String content;
    private String writer;
    private Date reg_date;*/
	
	private int no; //글번호
    private String ttl; //제목
    private String cntnt; //글내용
    private Date rgdte; //등록일

}
