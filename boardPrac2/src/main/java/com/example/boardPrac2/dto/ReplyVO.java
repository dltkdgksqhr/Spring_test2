package com.example.boardPrac2.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ReplyVO {
    private Long rno;
    private int bno;

    private String reply;
    private String replyer;
    private Date replyDate;
    private Date updateDate;


}
