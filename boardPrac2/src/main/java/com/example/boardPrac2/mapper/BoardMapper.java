package com.example.boardPrac2.mapper;

import com.example.boardPrac2.dto.BoardDto;
import com.example.boardPrac2.dto.Criteria;
import com.example.boardPrac2.dto.FileVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface BoardMapper {

    // 읽기
    List<BoardDto> getFileBoardList();
    // 자세히 읽기
    BoardDto fileBoardDetail(int b_no);
    // 삽입하기
    int fileBoardInsert(BoardDto boardDto);
    // 수정하기
    int fileBoardUpdate(BoardDto boardDto);
    //삭제하기
    int fileBoardDelete(int b_no);

    //페이징
    List<BoardDto> getListWithPaging(Criteria cri);

    // 테이블 카운트
    int getTotalCount(Criteria cri);


    //동적 검색을 위한것
    List<BoardDto> searchTest(Map<String, Map<String, String>> map);
    // map안에 map을 한이유는 xml sql문 작성 시
    // 이름으로  전달되는 데이터 안에 map이라는게 필요해서 한번 더 감싸서 한것

    //파일 업로드
    int fileInsert(FileVO fileVO);

    //파일 다운로드
    FileVO fileDown(int b_no);





}
