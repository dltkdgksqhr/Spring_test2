package com.example.boardPrac2.service;

import com.example.boardPrac2.dto.Criteria;
import com.example.boardPrac2.dto.ReplyVO;

import java.util.List;

public interface ReplyService {

    public int register(ReplyVO vo);

    public ReplyVO get(Long rno);

    public int modify(ReplyVO vo);

    public int remove(Long rno);

    public List<ReplyVO> getList(Criteria cri, int bno);


}
