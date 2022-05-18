package com.example.boardPrac2.mapper;

//import com.example.boardPrac2.controller.BoardController;
import com.example.boardPrac2.dto.BoardDto;
import com.example.boardPrac2.dto.Criteria;
import com.example.boardPrac2.dto.FileVO;
import com.example.boardPrac2.dto.PageDTO;
import net.bytebuddy.implementation.bytecode.assign.TypeCasting;
import org.apache.juli.logging.Log;
import org.assertj.core.api.Assert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
@Rollback(value = false)
class BoardMapperTest {

    @Autowired
    BoardMapper boardMapper;


    @Test
    @DisplayName("R test")
    void selectTest() {
        List<BoardDto> boardDto = boardMapper.getFileBoardList();
        for (BoardDto boardDtos : boardDto) {
            System.out.println("boardDto = " + boardDtos);
        }

    }

    @Test
    @DisplayName("C test")
    void insertTest() {
        BoardDto boardDto = new BoardDto();

        boardDto.setContent("test");
        boardDto.setWriter("tes");
        boardDto.setTitle("test");

        boardMapper.fileBoardInsert(boardDto);
    }

    @Test
    @DisplayName("u Test")
    void editTest() {
        BoardDto boardDto = new BoardDto();

        boardDto.setTitle("happy");
        boardDto.setWriter("dog");
        boardDto.setContent("메롱");

        boardMapper.fileBoardUpdate(boardDto);
    }

    @Test
    @DisplayName("d Tesdt")
    void deleteTest() {
        BoardDto boardDto = new BoardDto();

        boardMapper.fileBoardDetail(125);

    }

    @Test
    public void testPaging() {

        Criteria cri = new Criteria();
        List<BoardDto> list = boardMapper.getListWithPaging(cri);

        System.out.println("list = " + list);
    }

    @Test
    public void testPageDTO() {
        Criteria cri = new Criteria();
        cri.setPageNum(25);

        PageDTO pageDTO = new PageDTO(cri, 251);
        System.out.println("pageDTO = " + pageDTO);

    }

    @Test
    public void testSearch() {
        Map<String, String> map = new HashMap<>();
        //검색 조건
        map.put("T", "TTT");
        map.put("C", "CCC");
        map.put("W", "WWW");

        Map<String, Map<String, String>> outer = new HashMap<>();
        outer.put("map", map);

        List<BoardDto> list = boardMapper.searchTest(outer);

    }

    @Test
    public void testSearchPaging() {

        Criteria cri = new Criteria();
        cri.setType("TCW");
        cri.setKeyword("test");

        List<BoardDto> list = boardMapper.getListWithPaging(cri);
    }


    @Test
    @DisplayName("uploadTest")
    public void uploadTest() {
        FileVO fileVO = new FileVO();

        fileVO.setFno(1);
        fileVO.setB_no(109);
        fileVO.setFileName("ASD");
        fileVO.setFileOriginName("test");
        fileVO.setFileUrl("www.naver.com");
     

        boardMapper.fileInsert(fileVO);

        System.out.println("fileVO = " + fileVO);

    }
}