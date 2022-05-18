package com.example.boardPrac2.service;


import com.example.boardPrac2.dto.BoardDto;
import com.example.boardPrac2.dto.Criteria;
import com.example.boardPrac2.dto.FileVO;

import java.util.List;

public interface BoardService {

    List<BoardDto> getFileBoardList(); //게시글 리스트 출력
    BoardDto fileBoardDetail(int b_no); //게시글 세부내용 보기
    int fileBoardInsert(BoardDto boardDto); //게시글 생성
    int fileBoardUpdate(BoardDto boardDto); //게시글 수정
    int fileBoardDelete(int b_no); //게시글 삭제

    List<BoardDto> getFileBoardList(Criteria cri);

    int getTotal(Criteria cri);

    //파일 업로드 메서드
    int fileInsert (FileVO file);

    // 파일 다운로드
    FileVO fileDown(int b_no);

}
