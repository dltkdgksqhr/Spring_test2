package com.example.boardPrac2.service;

import com.example.boardPrac2.dto.Criteria;
import com.example.boardPrac2.dto.ReplyVO;
import com.example.boardPrac2.mapper.ReplyMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReplyServiceImpl implements ReplyService {

    @Autowired
    private ReplyMapper mapper;

    @Override
    public int register(ReplyVO vo) {
        return mapper.insert(vo);
    }

    @Override
    public ReplyVO get(Long rno) {
        return mapper.read(rno);
    }

    @Override
    public int modify(ReplyVO vo) {
        return mapper.update(vo);
    }

    @Override
    public int remove(Long rno) {
        return mapper.delete(rno);
    }

    @Override
    public List<ReplyVO> getList(Criteria cri, int bno) {
        return mapper.getListWithPaging(cri, bno);
    }

}
