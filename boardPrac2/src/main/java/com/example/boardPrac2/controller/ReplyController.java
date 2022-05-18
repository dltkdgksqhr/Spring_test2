package com.example.boardPrac2.controller;

import com.example.boardPrac2.dto.Criteria;
import com.example.boardPrac2.dto.ReplyVO;
import com.example.boardPrac2.service.ReplyService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController     // @RestController은 반환값이 기본이 json
@RequiredArgsConstructor
@RequestMapping("/replies")
public class ReplyController {

    @Autowired
    ReplyService service;

    //댓글 등록
    @PostMapping(value = "/new")
    public ResponseEntity<String> create(@RequestBody ReplyVO vo) {

        int insertCount = service.register(vo);

        return new ResponseEntity<>("success", HttpStatus.OK) ;
    }

    // 게시물의댓글 목록 확인
    @GetMapping(value = "/pages/{bno}/{page}")
//                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ReplyVO>> getList(
            @PathVariable("page") int page,
            @PathVariable("bno") int bno) {

        Criteria cri = new Criteria(page, 10);

        return new ResponseEntity<>(service.getList(cri, bno), HttpStatus.OK);
    }

    //댓글 조회

    @GetMapping(value = "/{rno}")
    public ResponseEntity<ReplyVO> get(@PathVariable("rno") Long rno) {

        return new ResponseEntity<>(service.get(rno), HttpStatus.OK);
    }

    //댓글 삭제
    @DeleteMapping(value = "/{rno}")
    public ResponseEntity<String> remove(@PathVariable("rno") Long rno) {

        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    //댓글 수정
    @PutMapping("/{rno}")
    public ResponseEntity<String> modify(@RequestBody ReplyVO vo) {

        service.modify(vo);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
}
