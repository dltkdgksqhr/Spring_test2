package com.example.boardPrac2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"b_no","title","content","writer","reg_date"})
public class BoardDto {

    private int b_no;
    private String title;
    private String content;
    private String writer;
    private Date reg_date;


}
